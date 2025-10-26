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

import it.csi.scriva.scrivaapisrv.business.be.helper.constraints.CodiceFiscale;


public class CodiceFiscaleConstraintValidator implements ConstraintValidator<CodiceFiscale, String> {
    

    @Override
    public void initialize(CodiceFiscale codiceFiscale) {
    }
    

    @Override
    public boolean isValid(String token, ConstraintValidatorContext cxt) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        String result = CFValidator.ControllaCF(token);
        boolean valid = result.equals(CFValidator.CFValidatorResultEnum.VALID.name());

        if (!valid) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate("CodiceFiscale.invalid." + result).addConstraintViolation();
        }

        return valid;
    }

}