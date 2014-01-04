package com.fitdrift.view;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fitdrift.domain.user.User;
import com.fitdrift.model.AthleticgisFacade;

import java.io.Serializable;
import java.util.Collection;

/**
 * This bean holds user info for each user's session.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 5066455190365405534L;
	private String username;
	private User user;
	private String theme;
	
	@PostConstruct
	public void initialize() {
		
	}
	
	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		if(!isAuthenticated()) {
			this.theme = "sam";
		} else if(getUser().getTheme() == null) {
			AthleticgisFacade.updateUserTheme(getUser().getUser_id(), "sam");
			this.theme = "sam";
		} else {
			this.theme = getUser().getTheme();
		}
		return theme;
	}

	/**
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(String theme) {
		if (!user.getTheme().equals(theme)) {
			AthleticgisFacade.updateUserTheme(user.getUser_id(), theme);
			user.setTheme(theme);
		}
	}

	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		if (user == null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = AthleticgisFacade.findUserByUsername(username);
		}
		return user.getUser_id();
	}

	public User getUser() {
		if (user == null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = AthleticgisFacade.findUserByUsername(username);
		}
		return user;
	}

	/**
	 * This is the username of the logged in user.
	 * 
	 * @return
	 */
	public String getUsername() {
		if (username == null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
		}
		return username;
	}

	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails
				.getAuthorities();
		for (GrantedAuthority ga : authorities) {
			if ("ROLE_ADMIN".equals(ga.getAuthority())) {
				return true;
			}
		}
		return false;
	}
}
