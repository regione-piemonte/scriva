/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.util.validation;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import it.csi.scriva.scrivaapisrv.dto.enumeration.ValidationResultEnum;

public class ValidationUtil {
    private final static Pattern patternItalianFiscalCode;
    static {
        patternItalianFiscalCode = Pattern.compile("^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})$|([0-9]{11})$");
    }

    private ValidationUtil() {
    }

    public static boolean isEMail(String mail) {
//        String pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String pattern = "^[\\w\\.\\-]+@\\w+[\\w\\.\\-]*?\\.\\w{1,4}$";
        return mail.matches(pattern);
    }

    public static boolean isNumber(String value) {
//        String pattern = "\\d+";
//        return value.matches(pattern);
        return StringUtils.isNumeric(value);
    }

    public static boolean isBigDecimal(String value) {
        String pattern = "^(((\\d{1,3})(?:,[0-9]{3}){0,4}|(\\d{1,12}))(\\.[0-9]{1,2})?)?$";
        return value.matches(pattern);
    }

    public static boolean hasArrayNotNullValue(Object[] paramArray) {
        if (paramArray == null)
            return false;

        for (int i = 0; i < paramArray.length; i++) {
            if (paramArray[i] != null || (paramArray[i] instanceof String && !paramArray[i].toString().isEmpty()))
                return true;
        }
        return false;
    }

    public static boolean hasNotNullValue(Object[][] arrayOfParamArrays) {
        if (arrayOfParamArrays == null)
            return false;
        for (int i = 0; i < arrayOfParamArrays.length; i++) {
            if (hasArrayNotNullValue(arrayOfParamArrays[i]))
                return true;
        }
        return false;
    }

    public static boolean isCodiceOk(String codice) {
        if (codice == null) {
            return false;
        }
        return patternItalianFiscalCode.matcher(codice).find();
    }

    public static String validateCF(String cf) {
        return CFValidator.ControllaCF(cf);
    }

    public static String isValidPIva(String piva) {
        return PivaValidator.isValid(piva);
    }

    public static String isValidDate(Date d) {

        if(null == d){
            return ValidationResultEnum.INVALID_DATE.name();
        }

        return ValidationResultEnum.VALID.name();
    }


}