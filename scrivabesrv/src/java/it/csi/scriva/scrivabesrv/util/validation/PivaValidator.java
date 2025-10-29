/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.validation;

import it.csi.scriva.scrivabesrv.dto.enumeration.ValidationResultEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * The type Piva validator.
 *
 * @author CSI PIEMONTE
 */
public class PivaValidator {

    /**
     * validate a piva
     *
     * @param piva piva
     * @return string string
     */
    public static String isValid(String piva) {
        if (StringUtils.isBlank(piva)) {
            return ValidationResultEnum.VALID.name();
        }

        if (piva.trim().length() != 11) {
            return ValidationResultEnum.INVALID_LENGTH.name();
        }

        int i, c, s;
        for (i = 0; i < 11; i++) {
            if (piva.charAt(i) < '0' || piva.charAt(i) > '9') {
                return ValidationResultEnum.INVALID_CHARS.name();
            }
        }

        s = 0;
        for (i = 0; i <= 9; i += 2) {
            s += piva.charAt(i) - '0';
        }

        for (i = 1; i <= 9; i += 2) {
            c = 2 * (piva.charAt(i) - '0');
            if (c > 9) {
                c = c - 9;
            }
            s += c;
        }

        boolean validChecksum = (10 - s % 10) % 10 == piva.charAt(10) - '0';
        if(!validChecksum) {
            return ValidationResultEnum.INVALID_CONTROL_CODE.name();
        }

        return ValidationResultEnum.VALID.name();
    }
}