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

import it.csi.scriva.scrivabesrv.util.service.integration.anagamb.AnagambsrvAnagambsrvServiceLocator;

public class AnagambTest {

    public static void main(String[] args) {
        try {
            AnagambsrvAnagambsrvServiceLocator locator = new AnagambsrvAnagambsrvServiceLocator();
//            AnagambsrvAnagambsrv server = locator.getanagambsrvAnagambsrv();
//            SedeOperativa[] so = server.getSediOperative(null, null, null, "09205650154", null, null, null, null, null);
//            List<Persona> pList =  aaepService.cercaPersoneInfoc(null, impresa.getCodiceFiscale(), false);
//            if(!pList.isEmpty()) {
//                PersonaINFOC p = (PersonaINFOC) aaepService.getDettaglioPersonaInfoc(null, pList.get(0));
//
//                System.out.println(p.getDescrComuneNascita());
//            }
//            System.out.println(so.length);
//            for (int i = 0; i < so.length; i++) {
//                System.out.println(so[i].getDenominazione());
//            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}