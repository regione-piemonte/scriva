/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.constraints.impl;

import it.csi.scriva.scrivafoweb.business.be.helper.constraints.PartitaIva;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



public class PartitaIvaConstraintValidator implements ConstraintValidator<PartitaIva, String> {

    @Override
    public void initialize(PartitaIva partitaIva) {
        // Override super
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext cxt) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        return token.matches("[^\\d\\.]") && token.trim().length() == 11;
    }

}