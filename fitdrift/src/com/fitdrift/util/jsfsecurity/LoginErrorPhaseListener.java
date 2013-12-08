package com.fitdrift.util.jsfsecurity;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

/**
 * This Phase Listener listens for Spring Security Exceptions, and translates them to faces exceptions.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
public class LoginErrorPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 8718514904179695384L;

	@Override
	public void beforePhase(final PhaseEvent arg0) {
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException) {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Invalid username or password",
							null));
		}
	}

	@Override
	public void afterPhase(final PhaseEvent arg0) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
