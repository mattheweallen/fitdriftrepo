package com.fitdrift.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fitdrift.domain.activity.ActivityPoint;
import com.fitdrift.model.AthleticgisFacade;

/**
 * This servlet is no longer being used. May be deleted.
 * 
 * Servlet implementation class ActivityServlet
 */
@WebServlet("/ActivityServlet")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String idStr = request.getParameter("activityId");
		
		AthleticgisFacade athleticgisFacade = new AthleticgisFacade();
		List<ActivityPoint> waypoints = AthleticgisFacade.findActivityPointsByActivityId(Long.parseLong(idStr));
		
		int size = waypoints.size();
		int ndx = 0;
		StringBuilder sb = new StringBuilder("[");
		while(ndx < size - 1) {
			sb.append("{\"lat\":");
			sb.append(waypoints.get(ndx).getLatitude());
			sb.append(",\"lon\":");
			sb.append(waypoints.get(ndx).getLongitude());
			sb.append("},");
			ndx++;
		}
		sb.append("{\"lat\":");
		sb.append(waypoints.get(ndx).getLatitude());
		sb.append(",\"lon\":");
		sb.append(waypoints.get(ndx).getLongitude());
		sb.append("}]");
		
		out.println(sb.toString());
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
