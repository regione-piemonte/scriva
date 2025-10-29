/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package test;

import it.csi.scriva.scrivabesrv.util.hashing.HashingUtil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PPayTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String stringDaConvertire= "prova";
        long start = System.currentTimeMillis();

        System.out.println("SHA256 : " + HashingUtil.encodeSHA256Hash(stringDaConvertire + String.valueOf(start)));
        start = System.currentTimeMillis();
        System.out.println("SHA256 : " + HashingUtil.encodeSHA256Hash(stringDaConvertire + String.valueOf(start)));
        start = System.currentTimeMillis();
        System.out.println("SHA256 : " + HashingUtil.encodeSHA256Hash(stringDaConvertire + String.valueOf(start)));
        start = System.currentTimeMillis();
        System.out.println("SHA256 : " + HashingUtil.encodeSHA256Hash(stringDaConvertire + String.valueOf(start)));
        start = System.currentTimeMillis();
        System.out.println("SHA256 : " + HashingUtil.encodeSHA256Hash(stringDaConvertire + String.valueOf(start)));


        String nome = "Elenco_allegati_<tipoAdempimento>-<id_istanza>-<data_upload>";
        String date = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        System.out.println(nome.trim().replace("<tipoAdempimento>", "ademp").replace("<id_istanza>", "940").replace("<data_upload>", date));

    }
}