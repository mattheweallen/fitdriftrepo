package com.fitdrift.util.validate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.fitdrift.util.regex.Time24HoursValidator;
 
/**
 *  Validates military time.
 * 
 */
@FacesValidator(value = "militaryTimeValidator")
public class MilitaryTimeValidator implements Validator {
	@Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String time = String.valueOf(value);
        boolean valid = (new Time24HoursValidator()).validate(time);
        if (!valid) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Invalid 24 hour clock time.",
                    "The time is not a valid 24 hour clock time.");
            throw new ValidatorException(message);
        }
    }
}
