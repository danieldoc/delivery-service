package br.com.danieldoc.deliveryservice.domain.constraintvalidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CPFValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {

    String message() default "CPF inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}