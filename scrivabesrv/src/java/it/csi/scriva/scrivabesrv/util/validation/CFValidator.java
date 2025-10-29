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
 * The type Cf validator.
 *
 * @author CSI PIEMONTE
 */
public class CFValidator {

    private static final char[] CONSONANTI = {'b', 'c', 'd', 'f', 'g', 'h', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'j', 'k', 'z'};
    private static final char[] VOCALI = {'a', 'e', 'i', 'o', 'u'};

    CFValidator() {

    }

    /**
     * validate the cf
     *
     * @param codiceFiscale codiceFiscale
     * @return string string
     */
    public static String controllaCF(String codiceFiscale) {
        if (null == codiceFiscale) {
            codiceFiscale = "";
        }
        int i;
        int s = 0;
        int c;
        String cf2;
        int[] setdisp = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        if (codiceFiscale.length() == 0) {
            return ValidationResultEnum.INVALID_LENGTH.name();
        }
        if (codiceFiscale.length() != 16) {
            return ValidationResultEnum.INVALID_LENGTH.name();
        }
        cf2 = codiceFiscale.toUpperCase();
        for (i = 0; i < 16; i++) {
            c = cf2.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                return ValidationResultEnum.INVALID_CHARS.name();
            }
        }
        for (i = 1; i <= 13; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                s = s + c - '0';
            } else {
                s = s + c - 'A';
            }
        }
        for (i = 0; i <= 14; i += 2) {
            c = cf2.charAt(i);
            if (c >= '0' && c <= '9') {
                c = c - '0' + 'A';
            }
            s = s + setdisp[c - 'A'];
        }
        if (s % 26 + 'A' != cf2.charAt(15)) {
            return ValidationResultEnum.INVALID_CF.name();
        }
        return ValidationResultEnum.VALID.name();
    }

    /**
     * Controlla cf string.
     *
     * @param codiceFiscale the codice fiscale
     * @param nome          the nome
     * @param cognome       the cognome
     * @return the string
     */
    public static String controllaCF(String codiceFiscale, String nome, String cognome) {
        String checkDigitResult = controllaCF(codiceFiscale);
        if (!ValidationResultEnum.VALID.name().equalsIgnoreCase(checkDigitResult)) {
            return checkDigitResult;
        }
        String checkCognomeResult = verifyCognome(codiceFiscale, cognome);
        if (!ValidationResultEnum.VALID.name().equalsIgnoreCase(checkCognomeResult)) {
            return checkCognomeResult;
        }
        String checkNomeResult = verifyNome(codiceFiscale, nome);
        if (!ValidationResultEnum.VALID.name().equalsIgnoreCase(checkNomeResult)) {
            return checkNomeResult;
        }
        return ValidationResultEnum.VALID.name();
    }

    private static String verifyCognome(String codiceFiscale, String cognome) {
        String cognomeCF = StringUtils.isNotBlank(codiceFiscale) ? codiceFiscale.substring(0, 3) : null;
        String cognomeCalc = calcolaCognome(cognome);
        if (StringUtils.isNotBlank(cognomeCF) && StringUtils.isNotBlank(cognomeCalc) && !cognomeCF.equalsIgnoreCase(cognomeCalc)) {
            return ValidationResultEnum.INVALID_CF_COGNOME.name();
        }
        return ValidationResultEnum.VALID.name();
    }

    private static String verifyNome(String codiceFiscale, String nome) {
        String nomeCF = StringUtils.isNotBlank(codiceFiscale) ? codiceFiscale.substring(3, 6) : null;
        String nomeCalc = calcolaNome(nome);
        if (StringUtils.isNotBlank(nomeCF) && StringUtils.isNotBlank(nomeCalc) && !nomeCF.equalsIgnoreCase(nomeCalc)) {
            return ValidationResultEnum.INVALID_CF_NOME.name();
        }
        return ValidationResultEnum.VALID.name();
    }

    /* METODO PER IL CALCOLO DEL COGNOME
     * Si effettua la reiterazione in quanto è possibile avere dei cognomi con meno di 3 consonanti ed in tal caso si ricercano le vocali.
     * Qualora non siano presenti abbastanza vocali da raggiungere una lunghezza totale di 3 lettere allora si aggiunge "X".
     */
    private static String calcolaCognome(String cognome) {
        String cognomeCalc = charEqualsArray(cognome, 3, CONSONANTI);
        if (cognomeCalc.length() < 3) {
            cognomeCalc += charEqualsArray(cognome, 3 - cognomeCalc.length(), VOCALI);
        }
        if (cognomeCalc.length() < 3) cognomeCalc += "x";
        return cognomeCalc.toUpperCase();
    }

    /* METODO PER IL CALCOLO DEL NOME
     * Ha la stessa procedura del metodo per il cognome con l'unica differenza che richiede solo la prima, terza e quarta consonante.
     * Bisogna quindi usare il secondo metodo per la verifica della stringa in input che permette di saltare la seconda consonante trovata.
     */
    private static String calcolaNome(String nome) {
        String nomeCalc = charEqualsArray2(nome, 3, CONSONANTI);
        if (nomeCalc.length() < 3)
            nomeCalc = calcolaCognome(nome);
        return nomeCalc.toUpperCase();
    }

    private int calcolaAnnoNascita(String dataDiNascita) {
        return Integer.parseInt(dataDiNascita.substring(8));
    }

    private String calcolaMeseNascita(String dataDiNascita) {
        String cfMeseNascita;
        String mese = dataDiNascita.substring(3, 5);
        switch (mese) {
            case "01":
                cfMeseNascita = "A";
                break;
            case "02":
                cfMeseNascita = "B";
                break;
            case "03":
                cfMeseNascita = "C";
                break;
            case "04":
                cfMeseNascita = "D";
                break;
            case "05":
                cfMeseNascita = "E";
                break;
            case "06":
                cfMeseNascita = "H";
                break;
            case "07":
                cfMeseNascita = "L";
                break;
            case "08":
                cfMeseNascita = "M";
                break;
            case "09":
                cfMeseNascita = "P";
                break;
            case "10":
                cfMeseNascita = "R";
                break;
            case "11":
                cfMeseNascita = "S";
                break;
            case "12":
                cfMeseNascita = "T";
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + mese);
        }
        return cfMeseNascita;
    }

    private String calcolaGiorno_Sesso(String dataDiNascita, String sesso) {
        int i = StringUtils.isNotBlank(sesso) && sesso.equalsIgnoreCase("F") ? 40 : 0;
        String cfGiornoNascitaSesso = dataDiNascita.substring(0, 2);
        i += Integer.parseInt(cfGiornoNascitaSesso);
        cfGiornoNascitaSesso = String.format("%d", i);
        return cfGiornoNascitaSesso;
    }

    /**
     * Char equals array string.
     *
     * @param inputString  the input string
     * @param outputLength the output length
     * @param array        the array
     * @return the string
     */
    /* METODI PER CONFRONTARE UNA STRINGA CON UN ARRAY DI CONSONANTI O VOCALI.
     * Si crea una nuova stringa contenente solo i valori ricercati, della lunghezza richiesta con "outputLength"
     */
    private static String charEqualsArray(String inputString, int outputLength, char[] array) {
        StringBuilder outputString = new StringBuilder();
        inputString = inputString.toLowerCase();
        for (int i = 0; i < inputString.length() && outputString.length() < outputLength; i++) {
            for (int j = 0; j < array.length && outputString.length() < outputLength; j++) {
                if (inputString.charAt(i) == array[j]) {
                    outputString.append(inputString.charAt(i));
                }
            }
        }
        return outputString.toString();
    }

    /**
     * Char equals array 2 string.
     *
     * @param inputString  the input string
     * @param outputLength the output length
     * @param array        the array
     * @return the string
     */
    private static String charEqualsArray2(String inputString, int outputLength, char[] array) {
        StringBuilder outputString = new StringBuilder();
        inputString = inputString.toLowerCase();
        boolean b = true;
        for (int i = 0; i < inputString.length() && outputString.length() < outputLength; i++) {
            for (int j = 0; j < array.length && outputString.length() < outputLength; j++) {
                if (inputString.charAt(i) == array[j]) {
                    if ((b) && (outputString.length() == 1)) {
                        b = false;
                        continue;
                    }
                    outputString.append(inputString.charAt(i));
                }
            }
        }
        return outputString.toString();
    }

}