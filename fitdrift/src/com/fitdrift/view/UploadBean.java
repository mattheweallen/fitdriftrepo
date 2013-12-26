package com.fitdrift.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.model.AthleticgisFacade;
import com.fitdrift.util.file.gpx.GPXParser;
import com.fitdrift.util.file.gpx.beans.GPX;
import com.fitdrift.util.file.gpx.beans.Track;
import com.fitdrift.util.file.gpx.beans.Waypoint;

/**
 * This was originally used for file upload. It has been replaced by FilUploadController.
 * This class may be deleted in the future. Make sure it is not being used by anything. 
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@ManagedBean
@RequestScoped
public class UploadBean implements Serializable {
	private static final long serialVersionUID = -290191374294978569L;
	private Part file;
	private String fileContent;
	private String activityName;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
//	public void testHibernate() {

//	}

	public String upload() throws ParserConfigurationException, SAXException {
		
		//keep around these may be useful, especially file size
//		System.out.println("call upload...");      
//		System.out.println("content-type: " + file.getContentType());
//		System.out.println("filename: " + file.getName());
//		System.out.println("size: " + file.getSize());
        try {
           // byte[] results=new byte[(int)file.getSize()];
//            for(int i = 0; i < results.length; i++) {
//            	System.out.print(results[i]);
//            }
            
            InputStream in=file.getInputStream();
            //System.out.println(in.read(results)); 
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String sCurrentLine;
//            while ((sCurrentLine = br.readLine()) != null) {
//				System.out.println(sCurrentLine);
//			}
            GPXParser p = new GPXParser();
            GPX gpx = p.parseGPX(in);
            com.fitdrift.domain.activity.Activity a = new com.fitdrift.domain.activity.Activity();
            a.setName(activityName);
            //AthleticgisFacade af = new AthleticgisFacade();
            a.setUser(AthleticgisFacade.findUserByUsername(userInfoBean.getUsername()));
            
            List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
            for(Track t : gpx.getTracks()) {
            	for(Waypoint  wp : t.getTrackPoints()) {
            		//System.out.println(wp.getLatitude() + "," + wp.getLongitude());
            		com.fitdrift.domain.activity.ActivityPoint activityPoint = new ActivityPoint();
            		activityPoint.setLatitude(wp.getLatitude());
            		activityPoint.setLongitude(wp.getLongitude());
            		activityPoint.setTime(new Timestamp(wp.getTime().getTime()));
            		activityPoint.setElevation(wp.getElevation());
            		activityPoints.add(activityPoint);
            	}
            }
            a.setActivitypoints(activityPoints);
            
            
            AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);
            
            in.close();
            //br.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Uploaded!"));
        return null;
	}
	
//	public String save() {
//		
//		if(file != null && file.getSize() > 0 && activityName != null) {
//			InputStream in;
//		try {
//			in = file.getInputStream();
//			setFileContent(new Scanner(in)
//					.useDelimiter("\\A").next());
//			in.close();
//		} catch (IOException e) {
//			// Error handling
//		}
//		
//		
//			Activity a = new Activity();
//			a.setDate(new Timestamp(new Date().getTime()));
//			a.setName(activityName);
//			a.setUserName(userInfoBean.getName());
//			activityModel.addActivity(a);
//			activityName = null;
//			
//			return "dashboard?faces-redirect=true";
//		} else {
//			return null;
//		}
//	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		//kmlObject = value;
		file = (Part) value;
//		if (file.getSize() > 1024) {
//			msgs.add(new FacesMessage("file too big"));
//		}
//		if (!"text/plain".equals(file.getContentType())) {
//			msgs.add(new FacesMessage("not a text file"));
//		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	

}
