package com.fitdrift.view;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fitdrift.model.user.UserDao;

/**
 * This bean handles the interaction between jsf and spring security. doLogin is
 * called from the login page, when user is logging into application.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@Component
@Scope("request")
@ManagedBean
@RequestScoped
public class LoginBean {
	// managed properties for the login page, username/password/etc...
	// private static final Log logger = LogFactory.getLog(LoginBean.class);

	// @ManagedProperty(value = "#{userInfoBean}")
	// private UserInfoBean userInfoBean;

	// This is the action method called when the user clicks the "login" button

	public String doLogin() throws IOException, ServletException {
		// logger.info("Start login sequence");
		// logger.info("Credentials: " + username + ":" + password
		// + ", rememberMe:" + rememberMe);

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		// ServletRequest request = ((ServletRequest) context.getRequest());

		// String checkboxValue = request
		// .getParameter("_spring_security_remember_me_input");

		// logger.info("Value of rememberMe option: " + checkboxValue);

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	// for development
	public String setupDB() {
		UserDao userDao = new UserDao();
		userDao.initializeDB();

		return null;
	}
}
