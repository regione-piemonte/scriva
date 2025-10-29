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

import it.csi.scriva.scrivabesrv.util.manager.MandatoryInfoIstanzaManager;

import java.util.Map;


public class CheckIstanzaTest {

    protected final static String JSON_DATA = "{\"QDR_OGGETTO\":{\"associazioniOggettoIstanza\":[{\"gestUID\":\"d06b0f008d9041f0fc87633a36bd680c15f9507bd98bef64955d67055f0dfc5b\",\"cod_scriva\":57,\"id_oggetto\":64,\"den_oggetto\":\"Denominazione opera\",\"des_oggetto\":\"Descrizione opera\",\"flg_esistente\":false,\"id_masterdata\":1,\"tipologia_oggetto\":{\"natura_oggetto\":{\"id_natura_oggetto\":2,\"cod_natura_oggetto\":\"PRO\",\"des_natura_oggetto\":\"Progetto\"},\"id_tipologia_oggetto\":0,\"cod_tipologia_oggetto\":\"GENERICA\",\"des_tipologia_oggetto\":\"Tipologia Generica\"},\"id_oggetto_istanza\":387,\"ubicazione_oggetto\":[{\"comune\":{\"toponimo\":\"AGLIE\",\"id_comune\":1,\"cap_comune\":\"10011\",\"des_provincia\":\"TORINO\",\"sigla_provincia\":\"TO\",\"codice_istat_comune\":\"001001\"},\"gestUID\":\"135da35f5e0229601dc251c22f25950000de9b80539ac47b776dec8b5735b05c\",\"id_oggetto_istanza\":387,\"ind_geo_provenienza\":\"FO\",\"id_ubicazione_oggetto_istanza\":474},{\"comune\":{\"toponimo\":\"AIRASCA\",\"id_comune\":2,\"cap_comune\":\"10060\",\"des_provincia\":\"TORINO\",\"sigla_provincia\":\"TO\",\"codice_istat_comune\":\"001002\"},\"gestUID\":\"34a9be743c2eb278e5aa7630db5b0672110df50094986877f14982a80c983290\",\"id_oggetto_istanza\":387,\"ind_geo_provenienza\":\"FO\",\"id_ubicazione_oggetto_istanza\":475}],\"id_masterdata_origine\":1},{\"gestUID\":\"d06b0f008d9041f0fc87633a36bd680c15f9507bd98bef64955d67055f0dfc5b\",\"cod_scriva\":57,\"id_oggetto\":64,\"den_oggetto\":\"Denominazione opera\",\"des_oggetto\":\"Descrizione opera\",\"flg_esistente\":false,\"id_masterdata\":1,\"tipologia_oggetto\":{\"natura_oggetto\":{\"id_natura_oggetto\":2,\"cod_natura_oggetto\":\"PRO\",\"des_natura_oggetto\":\"Progetto\"},\"id_tipologia_oggetto\":0,\"cod_tipologia_oggetto\":\"GENERICA\",\"des_tipologia_oggetto\":\"Tipologia Generica\"},\"id_oggetto_istanza\":387,\"ubicazione_oggetto\":[{\"comune\":{\"toponimo\":\"AGLIE\",\"id_comune\":1,\"cap_comune\":\"10011\",\"des_provincia\":\"TORINO\",\"sigla_provincia\":\"TO\",\"codice_istat_comune\":\"001001\"},\"gestUID\":\"135da35f5e0229601dc251c22f25950000de9b80539ac47b776dec8b5735b05c\",\"id_oggetto_istanza\":387,\"ind_geo_provenienza\":\"FO\",\"id_ubicazione_oggetto_istanza\":474}],\"id_masterdata_origine\":1}]}}";

    public static void main(String[] args) {
        try {
            Map<String, String> error = null;
            MandatoryInfoIstanzaManager istanzaManager = new MandatoryInfoIstanzaManager();

            //error = istanzaManager.checkSoggetti(JSON_DATA, null);

            //error =(istanzaManager.checkOggetti(JSON_DATA, null));

            //error = istanzaManager.checkAllegati("VER", 749L);

            error = istanzaManager.checkSoggettiDb(433L,null);

            System.out.println(error.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}