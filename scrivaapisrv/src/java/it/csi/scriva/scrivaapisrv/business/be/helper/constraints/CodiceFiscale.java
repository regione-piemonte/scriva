/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/*
 *  SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.scriva.scrivaapisrv.business.be.helper.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import it.csi.scriva.scrivaapisrv.business.be.helper.constraints.impl.CodiceFiscaleConstraintValidator;

@ReportAsSingleViolation
@Documented
@Constraint(validatedBy = CodiceFiscaleConstraintValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodiceFiscale {

    String message() default "{validation.must.be.cf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}