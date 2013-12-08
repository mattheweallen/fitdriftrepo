package com.fitdrift.util.jsfsecurity;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

/**
 * This class is needed to make spring security remember me functionality work with JSF.
 * 
 * @author Matthew Allen
 * @version 20131208
 */
public class RememberMePostProcessor implements BeanPostProcessor {
	public Object postProcessAfterInitialization(Object bean, String name) {
		if (bean instanceof AbstractRememberMeServices) {
			AbstractRememberMeServices rememberMe = (AbstractRememberMeServices) bean;
			rememberMe.setParameter("_spring_security_remember_me_input");
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String name) {
		return bean;
	}
}