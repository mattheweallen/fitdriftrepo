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

@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 5066455190365405534L;
	private String username;
	private String password;
	private Boolean rememberMe;
	private Boolean isAdmin;
	private Boolean isGeneral;
	private Long user_id;
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
			this.theme = "bootstrap";
		} else if(getUser().getTheme() == null) {
			AthleticgisFacade.updateUserTheme(getUser().getUser_id(), "bootstrap");
			this.theme = "bootstrap";
		} else {
			this.theme = getUser().getTheme();
		}
		
		
//		if (user == null) {
//			this.theme = "bootstrap";
//		} else if (user.getTheme() == null) {
//			AthleticgisFacade.updateUserTheme(user.getUser_id(), "bootstrap");
//		} else {
//			this.theme = user.getTheme();
//		}
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
		if (user_id == null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = AthleticgisFacade.findUserByUsername(username);
		}
		return user.getUser_id();
	}

	public User getUser() {
		if (user_id == null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			username = userDetails.getUsername();
			user = AthleticgisFacade.findUserByUsername(username);
		}
		return user;
	}

	// /**
	// * @param user_id the user_id to set
	// */
	// public void setUser_id(Long user_id) {
	// this.user_id = user_id;
	// }

	// public Boolean getAdmin() {
	// //return isAdmin;
	//
	// //hardcoded
	// return "admin".equals(name);
	// }
	// public void setAdmin(Boolean admin) {
	// this.admin = admin;
	// }
	public Boolean getIsGeneral() {
		return isGeneral;
	}

	public void setIsGeneral(Boolean isGeneral) {
		this.isGeneral = isGeneral;
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

	// public void setName(String name) {
	// this.name = name;
	// }
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public Boolean getLoggedIn() {
		return username != null;
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

	/**
	 * @param isAdmin
	 *            the isAdmin to set
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
