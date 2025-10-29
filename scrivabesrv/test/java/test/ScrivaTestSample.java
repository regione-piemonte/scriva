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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jayway.jsonpath.JsonPath;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.Crs;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.CrsProperties;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PostRequestFeatureGeoJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.catasto.dto.PostRequestJSON;
import it.csi.scriva.scrivabesrv.business.be.helper.geeco.dto.internal.Feature;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.placeholder.PlaceHolderUtil;
import it.csi.scriva.scrivabesrv.util.validation.ValidationUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrivaTestSample {

    public static void main(String[] args) throws IOException {
        //createContent(token);
        /*
        String euroString = URLDecoder.decode("\u20AC", StandardCharsets.UTF_8);
        System.out.println(euroString);
        String utf8EncodedString = StringUtils.(bytes);
        */
        /*
        String testNull = null;
        if(testNull.isBlank()){
            System.out.println("Vuota");
        }
        */

        System.out.println("\nTest filtro di due liste con utilizzo stream");
        List<IstanzaResponsabileExtendedDTO> istanzaResponsabileListNew = new ArrayList<>();
        List<IstanzaResponsabileExtendedDTO> istanzaResponsabileListDB = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            IstanzaResponsabileExtendedDTO ir = new IstanzaResponsabileExtendedDTO();

            TipoResponsabileDTO tr = new TipoResponsabileDTO();
            tr.setIdTipoResponsabile((long) i);
            tr.setCodiceTipoResponsabile("COD_RESP_" + i);
            tr.setDescrizioneTipoResponsabile("DESC_RESP_" + i);

            ir.setTipoResponsabile(tr);
            ir.setIdIstanzaResponsabile((long) i);
            ir.setIdIstanza(0L);
            ir.setLabelResponsabile("Label responsabile " + i);
            ir.setNominativoResponsabile("Nominativo responsabile " + i);
            ir.setRecapitoResponsabile("Recapito responsabile " + i);
            ir.setFlgRiservato(false);
            istanzaResponsabileListNew.add(ir);
        }

        for (int i = 5; i < 15; i++) {
            IstanzaResponsabileExtendedDTO ir = new IstanzaResponsabileExtendedDTO();

            TipoResponsabileDTO tr = new TipoResponsabileDTO();
            tr.setIdTipoResponsabile((long) i);
            tr.setCodiceTipoResponsabile("COD_RESP_" + i);
            tr.setDescrizioneTipoResponsabile("DESC_RESP_" + i);

            ir.setTipoResponsabile(tr);
            ir.setIdIstanzaResponsabile((long) i);
            ir.setIdIstanza(0L);
            ir.setLabelResponsabile("Label responsabile " + i);
            ir.setNominativoResponsabile("Nominativo responsabile " + i);
            ir.setRecapitoResponsabile("Recapito responsabile " + i);
            ir.setFlgRiservato(false);
            istanzaResponsabileListDB.add(ir);
        }

        List<Long> istRespToDel = istanzaResponsabileListDB.stream()
                .filter(irDB -> istanzaResponsabileListNew.stream()
                        .noneMatch(irNew -> irDB.getTipoResponsabile().getIdTipoResponsabile().equals(irNew.getTipoResponsabile().getIdTipoResponsabile())
                                && irDB.getNominativoResponsabile().equals(irNew.getNominativoResponsabile())))
                .map(IstanzaResponsabileExtendedDTO::getIdIstanzaResponsabile)
                .collect(Collectors.toList());
        System.out.println(istRespToDel);

        List<Long> istRespToUpd = istanzaResponsabileListDB.stream()
                .filter(irDB -> istanzaResponsabileListNew.stream()
                        .anyMatch(irNew -> irDB.getTipoResponsabile().getIdTipoResponsabile().equals(irNew.getTipoResponsabile().getIdTipoResponsabile())
                                && irDB.getNominativoResponsabile().equals(irNew.getNominativoResponsabile())))
                .map(IstanzaResponsabileExtendedDTO::getIdIstanzaResponsabile)
                .collect(Collectors.toList());
        System.out.println(istRespToUpd);

        List<Long> istRespToSave = istanzaResponsabileListNew.stream()
                .filter(irNew -> istanzaResponsabileListDB.stream()
                        .noneMatch(irDB -> irDB.getTipoResponsabile().getIdTipoResponsabile().equals(irNew.getTipoResponsabile().getIdTipoResponsabile())
                                && irDB.getNominativoResponsabile().equals(irNew.getNominativoResponsabile())))
                .map(IstanzaResponsabileExtendedDTO::getIdIstanzaResponsabile)
                .collect(Collectors.toList());
        System.out.println(istRespToSave);



        System.out.println("\nTest AtomicLong vs Long");
        long l = 0L;
        AtomicLong al = new AtomicLong(0);
        System.out.println("Long l++ : " + l++);
        System.out.println("AtomicLong al.getAndIncrement() : " + al.getAndIncrement());
        System.out.println("Long l : " + l);
        System.out.println("AtomicLong al.getAndIncrement() : " + al);


        System.out.println("\nTest scorrimento json");
        try {
            String jsonString = "{\"57\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Codice univoco\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Id istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Attore\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Identificativo oggetto padre\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Componente applicativa\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Aggiorna oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Gestione oggetto istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_id_tipologia\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Tipo opera\",\"type\":\"text\",\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"map_den_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_des_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"geom\",\"type\":\"geometry\",\"geomType\":\"MultiPoint\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Attore\":\"PH_ATTORE\",\"Id istanza\":\"PH_ISTANZA\",\"Tipo opera\":\"Pozzo\",\"Codice univoco\":\"-999\",\"map_den_oggetto\":\"[[Descrizione geometria]]\",\"map_des_oggetto\":\"[[Descrizione geometria]]\",\"Aggiorna oggetto\":\"true\",\"map_id_tipologia\":\"PH_ID_TIPOLOGIA\",\"Descrizione geometria\":\"Pozzo xxx\",\"Componente applicativa\":\"PH_COMP_APP\",\"Gestione oggetto istanza\":\"NO_CHECK_OGG_IST\",\"Identificativo oggetto padre\":\"PH_ID_OGG_IST_PADRE\"},\"canDeleteFeatures\":false,\"canInsertNewFeatures\":true},\"58\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Codice univoco\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Id istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Attore\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Identificativo oggetto padre\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Componente applicativa\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Aggiorna oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Gestione oggetto istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_id_tipologia\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Tipo opera\",\"type\":\"text\",\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"map_den_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_des_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"geom\",\"type\":\"geometry\",\"geomType\":\"MultiPoint\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Attore\":\"PH_ATTORE\",\"Id istanza\":\"PH_ISTANZA\",\"Tipo opera\":\"Presa da acque superficiali\",\"Codice univoco\":\"-999\",\"map_den_oggetto\":\"[[Descrizione geometria]]\",\"map_des_oggetto\":\"[[Descrizione geometria]]\",\"Aggiorna oggetto\":\"true\",\"map_id_tipologia\":\"PH_ID_TIPOLOGIA\",\"Descrizione geometria\":\"Presa da acque superficiali xxx\",\"Componente applicativa\":\"PH_COMP_APP\",\"Gestione oggetto istanza\":\"NO_CHECK_OGG_IST\",\"Identificativo oggetto padre\":\"PH_ID_OGG_IST_PADRE\"},\"canDeleteFeatures\":false,\"canInsertNewFeatures\":true},\"59\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Codice univoco\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Id istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Attore\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Identificativo oggetto padre\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Componente applicativa\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Aggiorna oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Gestione oggetto istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_id_tipologia\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Tipo opera\",\"type\":\"text\",\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"map_den_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_des_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"geom\",\"type\":\"geometry\",\"geomType\":\"MultiPoint\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Attore\":\"PH_ATTORE\",\"Id istanza\":\"PH_ISTANZA\",\"Tipo opera\":\"Sorgente\",\"Codice univoco\":\"-999\",\"map_den_oggetto\":\"[[Descrizione geometria]]\",\"map_des_oggetto\":\"[[Descrizione geometria]]\",\"Aggiorna oggetto\":\"true\",\"map_id_tipologia\":\"PH_ID_TIPOLOGIA\",\"Descrizione geometria\":\"Sorgente xxx\",\"Componente applicativa\":\"PH_COMP_APP\",\"Gestione oggetto istanza\":\"NO_CHECK_OGG_IST\",\"Identificativo oggetto padre\":\"PH_ID_OGG_IST_PADRE\"},\"canDeleteFeatures\":false,\"canInsertNewFeatures\":true},\"60\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Codice univoco\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Id istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Attore\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Identificativo oggetto padre\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Componente applicativa\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Aggiorna oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Gestione oggetto istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_id_tipologia\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Tipo opera\",\"type\":\"text\",\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"map_den_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_des_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"geom\",\"type\":\"geometry\",\"geomType\":\"MultiPoint\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Attore\":\"PH_ATTORE\",\"Id istanza\":\"PH_ISTANZA\",\"Tipo opera\":\"Fontanile\",\"Codice univoco\":\"-999\",\"map_den_oggetto\":\"[[Descrizione geometria]]\",\"map_des_oggetto\":\"[[Descrizione geometria]]\",\"Aggiorna oggetto\":\"true\",\"map_id_tipologia\":\"PH_ID_TIPOLOGIA\",\"Descrizione geometria\":\"Fontanile xxx\",\"Componente applicativa\":\"PH_COMP_APP\",\"Gestione oggetto istanza\":\"NO_CHECK_OGG_IST\",\"Identificativo oggetto padre\":\"PH_ID_OGG_IST_PADRE\"},\"canDeleteFeatures\":false,\"canInsertNewFeatures\":true},\"61\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Codice univoco\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Id istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Attore\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Identificativo oggetto padre\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Componente applicativa\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Aggiorna oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Gestione oggetto istanza\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_id_tipologia\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"Tipo opera\",\"type\":\"text\",\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"map_den_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"map_des_oggetto\",\"type\":\"hidden\",\"readonly\":true,\"required\":false},{\"name\":\"geom\",\"type\":\"geometry\",\"geomType\":\"LineString\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Attore\":\"PH_ATTORE\",\"Id istanza\":\"PH_ISTANZA\",\"Tipo opera\":\"Trincea drenante\",\"Codice univoco\":\"-999\",\"map_den_oggetto\":\"[[Descrizione geometria]]\",\"map_des_oggetto\":\"[[Descrizione geometria]]\",\"Aggiorna oggetto\":\"true\",\"map_id_tipologia\":\"PH_ID_TIPOLOGIA\",\"Descrizione geometria\":\"Trincea drenante xxx\",\"Componente applicativa\":\"PH_COMP_APP\",\"Gestione oggetto istanza\":\"NO_CHECK_OGG_IST\",\"Identificativo oggetto padre\":\"PH_ID_OGG_IST_PADRE\"},\"canDeleteFeatures\":false,\"canInsertNewFeatures\":true}}";
            JSONObject jsonObject = new JSONObject(jsonString.trim());
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                if (jsonObject.get(key) instanceof JSONObject) {
                    // do something with jsonObject here
                    String newValue = jsonObject.get(key).toString();
                    newValue = newValue.replace("PH_ID_TIPOLOGIA", "TEST");
                    jsonObject.put(key, new JSONObject(newValue.trim()));
                    System.out.println("\n" + jsonObject.get(key) + "\n");
                }
            }
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Test split and trim
        System.out.println("\nTest split and trim");
        //String attributes = " foo boo   , faa baa   , fii bii   ,";
        //String attributes = " foo boo   ; faa baa   ; fii bii   ";
        String attributes = "";
        List<String> splitted = Arrays.stream(attributes.trim().split(",")).map(String::trim).collect(Collectors.toList());
        System.out.println(splitted);
        //return;

        //Test regular expression
        System.out.println("\nTest regular expression");
        String inputString = "COMPILANTE-EMAIL in cc valore statico {placeholder} verifica preferenze utente perchè non è un canale di {default} (scriva_r_destinatario_canale.flg_default=0) e \\r\\nsu scriva_d_destinatario.flg_verifica_preferenze_notifiche=1\\r\\nSe scriva_r_compilante_preferenza_cn no record per canale email oppure presente con flg_abilitato= 0, il sistema NON DEVE inviare la notifica email.\\r\\nSe scriva_r_compilante_preferenza_cn si record per il canale email con flg_abilitato= 1, il sistema [DEVE] inviare la notifica.";
        Pattern PH_PATTERN = Pattern.compile("\\{(.*?)\\}");
        Matcher m = PH_PATTERN.matcher(inputString);
        while (m.find()) {
            String s = m.group(1);
            System.out.println(s);
            // s now contains "BAR"
        }
        System.out.println(PlaceHolderUtil.getPlaceHolder(inputString));

        // Test merge list
        System.out.println("\nTest merge list");
        String inputString2 = "{COMPILANTE-EMAIL} in cc valore statico {placeholder} verifica preferenze utente perchè non è un canale di {default} (scriva_r_destinatario_canale.flg_default=0) e su scriva_d_destinatario.flg_verifica_preferenze_notifiche=1\\r\\nSe scriva_r_compilante_preferenza_cn no record per {canale email} oppure presente con flg_abilitato= 0, il sistema NON DEVE inviare la notifica email.\\r\\nSe scriva_r_compilante_preferenza_cn si record per il canale email con flg_abilitato= 1, il sistema [DEVE] inviare la notifica.";
        String inputString3 = "COMPILANTE-EMAIL in cc valore statico {placeholder} verifica preferenze utente perchè non è un canale di {default} (scriva_r_destinatario_canale.flg_default=0) e \\r\\nsu scriva_d_destinatario.flg_verifica_preferenze_notifiche=1\\r\\nSe scriva_r_compilante_preferenza_cn no record per canale email oppure presente con {flg_abilitato}= 0, il sistema NON DEVE inviare la notifica email.\\r\\nSe scriva_r_compilante_preferenza_cn si record per il canale email con flg_abilitato= 1, il sistema [DEVE] inviare la notifica.";
        List<String> phDB = Arrays.asList("PH_ACS_ADEMPI_PEC",
                "PH_ACS_ADEMPI_EMAIL",
                "PH_ACP_DES_COMPETENZA_TERRITORIO_ESTESA",
                "PH_ACP_SITO_WEB",
                "PH_ACP_INDIRIZZO_COMPETENZA",
                "PH_ACP_NUM_CIVICO",
                "PH_ACP_CAP_COMPETENZA",
                "PH_ACP_ADEMPI_EMAIL",
                "PH_ACP_PEC",
                "PH_ACP_EMAIL",
                "PH_ACP_DES_TIPO_COMPETENZA_ESTESA",
                "PH_ACS_DES_COMPETENZA_TERRITORIO",
                "PH_ACS_SITO_WEB",
                "PH_ACS_INDIRIZZO_COMPETENZA",
                "PH_ACS_NUM_CIVICO",
                "PH_ACS_CAP_COMPETENZA",
                "PH_ACS_EMAIL",
                "PH_ SOG_IST_CF_SOGGETTO",
                "PH_ACS_PEC",
                "PH_ACS_DES_TIPO_COMPETENZA_ESTESA",
                "PH_ SOG_IST_COGNOME",
                "PH_ SOG_IST_NOME",
                "PH_SOGG_IST_DENOM_COMUNE_NASCITA",
                "PH_SOGG_IST_CAP_COMUNE_NASCITA",
                "PH_SOGG_IST_DENOM_PROVINCIA_NASCITA",
                "PH_SOGG_IST_SIGLA_PROVINCIA_NASCITA",
                "PH_ SOG_IST_CITTA_ESTERA_NASCITA",
                "PH_ SOG_IST_DES_EMAIL ",
                "PH_ SOG_IST_DES_PEC ",
                "PH_ SOG_IST_NUM_CELLULARE",
                "PH_SOGG_IST_DENOM_COMUNE_SEDE_LEG",
                "PH_SOGG_IST_CAP_COMUNE_SEDE_LEG",
                "PH_SOGG_IST_DENOM_PROVINCIA_ SEDE_LEGALE",
                "PH_SOGG_IST_SIGLA_PROVINCIA_ SEDE_LEGALE",
                "PH_SOGG_IST_CITTA_ESTERA_SEDE_LEGALE",
                "PH_SOGG_IST_INDIRIZZO_SOGGETTO",
                "PH_SOGG_IST_NUM_CIVICO_INDIRIZZO",
                "PH_SOGG_IST_DES_LOCALITA",
                "PH_SOGG_IST_DENOM_COMUNE_RESIDENZA",
                "PH_SOGG_IST_CAP_COMUNE_ RESIDENZA",
                "PH_SOGG_IST_SIGLA_PROVINCIA_ RESIDENZA",
                "PH_SOGG_IST_CITTA_ESTERA_ RESIDENZA",
                "PH_SOGG_IST_ID_NAZIONE_ RESIDENZA",
                "PH_RICHIEDENTE_IST_DENOM_COMUNE_RESIDENZA",
                "PH_ RICHIEDENTE_IST_CAP_COMUNE_ RESIDENZA",
                "PH_ SOG_IST_DEN_SOGGETTO",
                "PH_IST_DATA_ PRESENTATA_ISTANZA",
                "PH_OGG_IST_SITO_COD_AMMINISTRATIVO_2000",
                "PH_OGG_IST_SITO_DES_SITO_NATURA_2000",
                "PH_OGG_IST_SITO_NUM_DISTANZA",
                "PH_OGG_IST_SITO_FLG_RICADE",
                "PH_OGG_IST_AREA_DES_AREA_PROTETTA",
                "PH_OGG_IST_AREA_COD_AMMINISTRATIVO_2000",
                "PH_OGG_IST_AREA_DES_SITO_NATURA_2000",
                "PH_OGG_IST_AREA_FLG_RICADE",
                "PH_MITTENTE_EMAIL",
                "PH_MITTENTE_PEC",
                "PH_OGG_IST_SITO_DES_SITO_2000",
                "PH_ACP_DES_COMPETENZA_TERRITORIO",
                "PH_ACP_ADEMPI_PEC",
                "PH_NOTIFICA_APP_DATA_CANCELLAZIONE",
                "PH_NOTIFICA_RIF_CANALE",
                "PH_NOTIFICA_APP_DATA_INSERIMENTO",
                "PH_IST_DES_ESTESA_ADEMPIMENTO",
                "PH_IST_DATA_INSERIMENTO_ISTANZA ",
                "PH_IST_DATA_INSERIMENTO_PRATICA ",
                "PH_IST_DATA_PUBBLICAZIONE",
                "PH_IST_NUM_PROTOCOLLO_ISTANZA ",
                "PH_IST_DATA_CONCLUSIONE_PROCEDIMENTO ",
                "PH_IST_COD_PRATICA ",
                "PH_NOTIFICA_RIF_CANALE_CC",
                "PH_IST_COD_ISTANZA ",
                "PH_NOTIFICA_DATA_INVIO",
                "PH_NOTIFICA_STATO",
                "PH_NOTIFICA_CF_DESTINATARIO",
                "PH_NOTIFICA_OTP",
                "PH_OGG_IST_CAP_COMUNE",
                "PH_OGG_IST_SIGLA_PROVINCIA",
                "PH_OGG_IST_DEN_INDIRIZZO ",
                "PH_NUM_CIVICO",
                "PH_DES_LOCALITA",
                "PH_COMPILANTE_COGNOME",
                "PH_OGG_IST_DENOM_COMUNE",
                "PH_COMPILANTE_NOME",
                "PH_COMPILANTE_CF ",
                "PH_ RICHIEDENTE_IST_ID_NAZIONE_ RESIDENZA",
                "PH_ RICHIEDENTE_IST_INDIRIZZO_SOGGETTO",
                "PH_ RICHIEDENTE_IST_NUM_CIVICO_INDIRIZZO",
                "PH_ RICHIEDENTE_IST_DES_LOCALITA",
                "PH_ RICHIEDENTE _IST_DES_EMAIL ",
                "PH_ RICHIEDENTE _IST_NUM_CELLULARE",
                "PH_IST_DATA_PRESENTA_INTEGRAZIONE",
                "PH_ RICHIEDENTE_IST_CITTA_ESTERA_ RESIDENZA",
                "PH_ACS_DES_COMPETENZA_TERRITORIO_ESTESA ",
                "PH_NOTIFICA_APP_CF_DESTINATARIO",
                "PH_NOTIFICA_APP_DATA_LETTURA",
                "PH_IST_DES_ADEMPIMENTO",
                "PH_OGG_IST_DENOM_PROVINCIA",
                "PH_ SOG_IST_DATA_NASCITA_SOGGETTO",
                "PH_SOGG_IST_ID_NAZIONE_SEDE_LEGALE",
                "PH_SOGG_IST_DENOM_PROVINCIA_ RESIDENZA",
                "PH_ RICHIEDENTE_IST_DENOM_PROVINCIA_ RESIDENZA",
                "PH_ RICHIEDENTE_IST_SIGLA_PROVINCIA_ RESIDENZA",
                "PH_ RICHIEDENTE _IST_DES_PEC ");
        List<String> phDesOggettoList = PlaceHolderUtil.getPlaceHolder(inputString);
        List<String> phDesMessaggioList = PlaceHolderUtil.getPlaceHolder(inputString2);
        List<String> phRifCanaleCcList = PlaceHolderUtil.getPlaceHolder(inputString3);
        List<String> phAllList = Stream
                .of(phDesOggettoList, phDesMessaggioList, phRifCanaleCcList)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(phAllList);

        //Test verifica tra due liste
        System.out.println("\nTest verifica tra due liste");
        phAllList = Arrays.asList("PH_ACS_ADEMPI_PEC",
                "PH_ACS_ADEMPI_EMAIL",
                "PH_ACP_DES_COMPETENZA_TERRITORIO_ESTESA",
                "PH_ACP_SITO_WEB",
                "PH_ACP_INDIRIZZO_COMPETENZA");
        //phAllList = Arrays.asList("PH_ACS_ADEMPI_", "PH_ACS_ADEMPI_EMAIL");
        System.out.println("Disjoint : " + !Collections.disjoint(phAllList, phDB));
        System.out.println("Stream : " + phAllList.stream().anyMatch(phDB::contains));
        System.out.println("CollectionUtils : " + CollectionUtils.containsAny(phDB, phAllList));
        System.out.println("containsAll : " + phDB.containsAll(phAllList)); //funziona
        boolean result = Boolean.TRUE;
        for (String el : phAllList) {
            if (!phDB.contains(el)) {
                result = Boolean.FALSE;
                break;
            }
        } // funziona
        System.out.println("Ciclo for : " + result);

        //phAllList.retainAll(phDB);
        //System.out.println("RetainAll : " + phAllList);

        // Test stampa valori booleani
        System.out.println("\nTest stampa valori booleani");
        System.out.println(Boolean.TRUE.toString());
        System.out.println(Boolean.FALSE.toString());

        // Test filtro su mappa
        List<String> CONF_KEYS_NOTIFY_CANALE = Arrays.asList(Constants.CONF_KEY_NOTIFY_CANALE_EMAIL,
                Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_FO,
                Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_BO,
                Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO,
                Constants.CONF_KEY_NOTIFY_CANALE_PEC);
        Map<String, String> configs = new HashMap<>();
        configs.put(Constants.CONF_KEY_NOTIFY_CANALE_EMAIL, "SI");
        configs.put(Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_FO, "SI");
        configs.put(Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_BO, "SI");
        configs.put(Constants.CONF_KEY_NOTIFY_CANALE_SCRIVA_SERVIZIO, "NO");
        configs.put(Constants.CONF_KEY_NOTIFY_CANALE_PEC, "NO");

        Map<String, String> newMap =
                configs.entrySet()
                        .stream()
                        .filter(e -> CONF_KEYS_NOTIFY_CANALE.contains(e.getKey()))
                        .filter(e -> "SI".equalsIgnoreCase(e.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> e.getValue().toString()));
        System.out.println(newMap);


        String tmpFolder = System.getProperty("java.io.tmpdir");
        System.out.println("Temp folder : " + tmpFolder);

        String json = "{\n" +
                "    \"idNotaIstanza\": 3,\n" +
                "    \"desNota\": \"Prova UPD nota\",\n" +
                "    \"indVisibile\": \"BO\"\n" +
                "}";
        System.out.println("json :\n " + json + "\n\n");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper = objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        NotaPubblicataDTO obj = objectMapper.readValue(json, NotaPubblicataDTO.class);
        System.out.println("obj :\n " + obj + "\n\n");

        /****************************
         * trasformare una lista di stringhe in un array json in formato stringa
         ****************************/
        List<String> listaString = new ArrayList<>();
        listaString.add("A");
        listaString.add("B");
        listaString.add("C");
        System.out.println("lista stringhe :\n " + listaString + "\n\n");
        System.out.println("lista stringhe Json:\n " + JSONArray.toJSONString(listaString) + "\n\n");

        /****************************
         * filtrare porzioni di json
         ****************************/
        List<Long> filterLong = Arrays.asList(17L, 18L);
        //trasformazione di una lista di Long in una lista di String
        List<String> filterString = filterLong.stream().map(Object::toString).collect(Collectors.toUnmodifiableList());
        //trasformazione di una lista di String in una stringa di array in formato JSON
        String filterJsonString = JSONArray.toJSONString(filterString);
        //sostituzione dei doppi apici con singolo apice adatto ai filtri di JsonPath
        String filterJsonPath = filterJsonString.replace("\"", "'");

        System.out.println("filterString (List<Long> to List<String>): " + filterString);
        System.out.println("filterJsonString (JSONArray.toJSONString(filterString)): " + filterJsonString);
        System.out.println("filterJsonPath (filterJsonString.replace) : " + filterJsonPath);

        json = "{\"17\":{\"schemas\":[{\"name\":\"Identificativo17\",\"type\":\"text\",\"alias\":\"Identificativo\",\"readonly\":true,\"required\":true},{\"name\":\"Codice univoco\",\"type\":\"text\",\"alias\":\"cod_opera\",\"readonly\":true,\"required\":true},{\"name\":\"Titolo Progetto\",\"type\":\"text\",\"alias\":null,\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"alias\":null,\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"geom\",\"type\":\"geometry\",\"alias\":null,\"geomType\":\"MultiPoint\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Codice univoco\":\"PH_ID_OGGETTO_ISTANZA\",\"Titolo Progetto\":\"PH_DEN_OGGETTO\"},\"canDeleteFeatures\":true,\"canInsertNewFeatures\":true},\"18\":{\"schemas\":[{\"name\":\"Identificativo18\",\"type\":\"text\",\"alias\":\"Identificativo\",\"readonly\":true,\"required\":true},{\"name\":\"Codice univoco\",\"type\":\"text\",\"alias\":\"cod_opera\",\"readonly\":true,\"required\":true},{\"name\":\"Titolo Progetto\",\"type\":\"text\",\"alias\":null,\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"alias\":null,\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"geom\",\"type\":\"geometry\",\"alias\":null,\"geomType\":\"LineString\",\"required\":true}],\"defaultStyles\":\"\",\"defaultValues\":{\"Codice univoco\":\"PH_ID_OGGETTO_ISTANZA\",\"Titolo Progetto\":\"PH_DEN_OGGETTO\"},\"canDeleteFeatures\":true,\"canInsertNewFeatures\":true},\"19\":{\"schemas\":[{\"name\":\"Identificativo\",\"type\":\"text\",\"alias\":\"Identificativo\",\"readonly\":true,\"required\":true},{\"name\":\"Codice univoco\",\"type\":\"text\",\"alias\":\"cod_opera\",\"readonly\":true,\"required\":true},{\"name\":\"Titolo Progetto\",\"type\":\"text\",\"alias\":null,\"readonly\":true,\"required\":true},{\"name\":\"Descrizione geometria\",\"type\":\"text\",\"alias\":null,\"readonly\":false,\"required\":true,\"maxLength\":30},{\"name\":\"geom\",\"type\":\"geometry\",\"alias\":null,\"geomType\":\"Polygon\",\"required\":false}],\"defaultStyles\":\"\",\"defaultValues\":{\"Codice univoco\":\"PH_ID_OGGETTO_ISTANZA\",\"Titolo Progetto\":\"PH_DEN_OGGETTO\"},\"canDeleteFeatures\":true,\"canInsertNewFeatures\":true}}";
        Map<String, Object> out = JsonPath.read(json, "$" + JSONArray.toJSONString(filterString).replace("\"", "'"));

        System.out.println("\n\nValore estratto : \n" + new ObjectMapper().writer().writeValueAsString(out) + "\n\n");


        /****************************
         * trasformazione feature Geeco in feature Catasto
         ****************************/
        String jsonFeature = "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[389820.1259,4994283.6264],[390059.9384,4994283.6264],[391258.9966,4994223.6744],[389820.1259,4994283.6264]]]}}";
        Feature feature = objectMapper.readValue(jsonFeature, Feature.class);
        String featureString = objectMapper.writer().writeValueAsString(feature);
        PostRequestFeatureGeoJSON postRequestFeatureGeoJSON = objectMapper.readValue(featureString, PostRequestFeatureGeoJSON.class);

        Crs crs = new Crs();
        crs.setType(Crs.TypeEnum.name);
        CrsProperties crsProperties = new CrsProperties();
        crsProperties.setName(CrsProperties.NameEnum.EPSG_32632.getValue());
        crs.setProperties(crsProperties);
        postRequestFeatureGeoJSON.setCrs(crs);
        PostRequestJSON postRequestJSON = new PostRequestJSON();
        postRequestJSON.setFeature(postRequestFeatureGeoJSON);
        postRequestJSON.setLimit(BigDecimal.ZERO);
        postRequestJSON.setOffset(BigDecimal.ZERO);
        System.out.println(objectMapper.writer().writeValueAsString(postRequestJSON) + "\n\n");

        String cf = "";
        String nome = "";
        String cognome = "";
        String cfValidator = ValidationUtil.validateCF(cf, nome, cognome);
        System.out.println("cfValidator = " + cfValidator + "\n\n");

    }

}