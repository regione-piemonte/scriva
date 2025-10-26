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

package it.csi.scriva.scrivaapisrv.business.be.helper.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import it.csi.scriva.scrivaapisrv.business.be.helper.constraints.PartitaIva;



public class PartitaIvaConstraintValidator implements ConstraintValidator<PartitaIva, String> {

    @Override
    public void initialize(PartitaIva partitaIva) {
    }

    @Override
    public boolean isValid(String token, ConstraintValidatorContext cxt) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
//        boolean valid = StringUtils.isNumeric(token) && token.trim().length() == 11;
        boolean valid = token.matches("[^\\d\\.]") && token.trim().length() == 11;
        return valid;
    }

}