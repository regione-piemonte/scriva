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

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Validation util.
 *
 * @author CSI PIEMONTE
 */
public class ValidationUtil {

    //private static final Pattern patternItalianFiscalCode = Pattern.compile("^([A-Za-z]{6}[0-9lmnpqrstuvLMNPQRSTUV]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9lmnpqrstuvLMNPQRSTUV]{2}[A-Za-z]{1}[0-9lmnpqrstuvLMNPQRSTUV]{3}[A-Za-z]{1})$|([0-9]{11})$");
    //private static final Pattern patternItalianFiscalCode = Pattern.compile("^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([\\d]{11})$", Pattern.CASE_INSENSITIVE);
    private static final Pattern patternItalianFiscalCode = Pattern.compile("^([A-Z]{6}[\\d]{2}[ABCDEHLMPRST][\\d]{2}[A-Z][\\d]{3}[A-Z])$|([\\d]{11})$", Pattern.CASE_INSENSITIVE);

    //private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(.+)@(.+)$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_ONLY_CHARS_REGEX = Pattern.compile("^[A-Z .]+$", Pattern.CASE_INSENSITIVE);


    private ValidationUtil() {
    }

    /**
     * Is number boolean.
     *
     * @param value value
     * @return booelan boolean
     */
    public static boolean isNumber(String value) {
        return StringUtils.isNumeric(value);
    }

    /**
     * Has array not null value boolean.
     *
     * @param paramArray paramArray
     * @return booelan boolean
     */
    public static boolean hasArrayNotNullValue(Object[] paramArray) {
        if (paramArray == null)
            return false;

        for (Object o : paramArray) {
            if (o != null)
                return true;
        }
        return false;
    }

    /**
     * Has not null value boolean.
     *
     * @param arrayOfParamArrays arrayOfParamArrays
     * @return boolean boolean
     */
    public static boolean hasNotNullValue(Object[][] arrayOfParamArrays) {
        if (arrayOfParamArrays == null)
            return false;
        for (Object[] arrayOfParamArray : arrayOfParamArrays) {
            if (hasArrayNotNullValue(arrayOfParamArray))
                return true;
        }
        return false;
    }

    /**
     * simple validation
     *
     * @param codiceFiscale codiceFiscale
     * @return booelan boolean
     */
    public static boolean isCodiceOk(String codiceFiscale) {
        if (codiceFiscale == null) {
            return false;
        }
        return patternItalianFiscalCode.matcher(codiceFiscale).find();
    }


    /**
     * Is valid email boolean.
     *
     * @param emailField emailField
     * @return boolean boolean
     */
    public static boolean isValidEmail(String emailField) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailField);
        return matcher.matches();
    }


    /**
     * strong validation
     *
     * @param codiceFiscale codiceFiscale
     * @return string string
     */
    public static String validateCF(String codiceFiscale) {
        return CFValidator.controllaCF(codiceFiscale);
    }

    /**
     * Validate cf string.
     *
     * @param codiceFiscale the codice fiscale
     * @param nome          the nome
     * @param cognome       the cognome
     * @return the string
     */
    public static String validateCF(String codiceFiscale, String nome, String cognome) {
        return CFValidator.controllaCF(codiceFiscale, nome, cognome);
    }

    /**
     * Is valid p iva string.
     *
     * @param piva piva
     * @return string string
     */
    public static String isValidPIva(String piva) {
        return PivaValidator.isValid(piva);
    }

    /**
     * Is valid date string.
     *
     * @param date date
     * @return string string
     */
    public static String isValidDate(Date date) {
        if (null == date) {
            return ValidationResultEnum.INVALID_DATE.name();
        }
        return ValidationResultEnum.VALID.name();
    }

    /**
     * Is valid only char boolean.
     *
     * @param validateString the validate string
     * @return the boolean
     */
    public static boolean isValidOnlyChar(String validateString) {
        Matcher matcher = VALID_ONLY_CHARS_REGEX.matcher(validateString);
        return matcher.matches();
    }
}