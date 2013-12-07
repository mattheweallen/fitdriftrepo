package com.fitdrift.view;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fitdrift.model.user.UserDao;

@Component
@Scope("request")
@ManagedBean
@RequestScoped
public class LoginBean {
	// managed properties for the login page, username/password/etc...
	private static final Log logger = LogFactory.getLog(LoginBean.class);

	private String username = "";
	private String password = "";
	private boolean rememberMe = false;
	private boolean loggedIn = false;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;

	// This is the action method called when the user clicks the "login" button

	public String doLogin() throws IOException, ServletException {
		logger.info("Start login sequence");
		logger.info("Credentials: " + username + ":" + password
				+ ", rememberMe:" + rememberMe);

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");

		ServletRequest request = ((ServletRequest) context.getRequest());

		String checkboxValue = request
				.getParameter("_spring_security_remember_me_input");

		logger.info("Value of rememberMe option: " + checkboxValue);

		dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());

		FacesContext.getCurrentInstance().responseComplete();

		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return this.rememberMe;
	}

	public void setRememberMe(final boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isLoggedIn() {
		return this.loggedIn;
	}

	public void setLoggedIn(final boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	// public static void redirect(String url) throws IOException {
	// ExternalContext ctx = FacesContext.getCurrentInstance()
	// .getExternalContext();
	// if (url.startsWith("http://") || url.startsWith("https://")
	// || url.startsWith("/")) {
	// ctx.redirect(url);
	// } else {
	// ctx.redirect(ctx.getRequestContextPath() + "/" + url);
	// }
	// }

	// for development
	public String setupDB() {
		UserDao userDao = new UserDao();
		userDao.initializeDB();

		return null;
	}

	/**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}
}
