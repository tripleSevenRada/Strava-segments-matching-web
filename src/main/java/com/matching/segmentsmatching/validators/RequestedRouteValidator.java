package com.matching.segmentsmatching.validators;

import com.matching.segmentsmatching.resources.RequestedRoute;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@Scope("singleton") //default
public class RequestedRouteValidator implements RRValid {
    private Validator validator;

        public RequestedRouteValidator() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Override
        public boolean isValid(RequestedRoute requestedRoute) {
            Set<ConstraintViolation<RequestedRoute>> constraintViolations = validator.validate(requestedRoute);
            return (constraintViolations.size() == 0);
        }

}
