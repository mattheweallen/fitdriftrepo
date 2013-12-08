package com.fitdrift.view;

import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;

import com.fitdrift.util.file.FileUtil;

/**
 * This bean handles file uploads.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@ManagedBean
@RequestScoped
public class FileUploadController {
	@ManagedProperty(value = "#{userInfoBean}")
	private UserInfoBean userInfoBean;

	/**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean
	 *            the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public void handleFileUpload(FileUploadEvent event)
			throws ParserConfigurationException, SAXException {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		
		try {
			upload(event.getFile());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			msg = new FacesMessage("Whoops!", event.getFile().getFileName() + " was not uploaded. GPX file was not valid.");
		} catch (ParserConfigurationException e) {
			msg = new FacesMessage("Whoops!", event.getFile().getFileName() + " was not uploaded. Unable to parse GPX.");
		} catch (IOException e) {
			msg = new FacesMessage("Whoops!", event.getFile().getFileName() + " was not uploaded. Error reading input.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void upload(UploadedFile file) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Uploading file " + file.getFileName());
		String extension = FileUtil.fileExtension(file.getFileName()
				.toLowerCase());
		if ("gpx".equals(extension)) {
			FileUtil.uploadAcitvityPointsFromGPX(file, userInfoBean.getUsername());

		} else if ("fit".equals(extension.toLowerCase().trim())) {
			FileUtil.uploadFitFile(file, userInfoBean.getUsername());
//			try {
//				// FitDecoder d = new FitDecoder();
//				FitDecoder.decode(file.getInputstream());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}