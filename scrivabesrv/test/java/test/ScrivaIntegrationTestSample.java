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

import it.csi.scriva.scrivabesrv.dto.PraticaIstruttoriaDTO;
import it.csi.scriva.scrivabesrv.dto.PubDocIstanzaLinkDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaAllegatoDTO;
import it.csi.scriva.scrivabesrv.util.oauth2.OauthHelper;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

public class ScrivaIntegrationTestSample {

    private static final String endPointUrl = "http://t";
    private static final String endPointScrivaApi = endPointUrl + "/ambiente/scriva-scrivaapisrv-rp-01/v1";
    private static final String xRequestAuth = "eyJjb21wb25lbnRlQXBwbGljYXRpdmEiOiAiQ09TTU8iLCJydW9sbyI6ICJDT1NNT19SVyIsInBhc3N3b3JkIjogIjM3NTRkNDgwMzQ3YjU0MjQ0OWEyMThkMTc4Yzk1ZmM0In0=";

    private static final String endPointScrivaApiTSTDiretto = "http://tst-scrivaapisrv.csi.it/scrivaapisrv/api/v1";
    private static final String endPointScrivaApiDEVDiretto = "http://dev-scrivaapisrv.csi.it/scrivaapisrv/api/v1";
    private static final String endPointScrivaApiLCLDiretto = "http://127.0.0.1:8080/scrivaapisrv/api/v1";
    private static final String username = "";
    private static final String password = "";
    private static final String codIstanzaTest = "VIA-VAL-488";
    private static final String linkDocumento = "/documenti/e9b70bdf-2a0c-4501-8cc6-987b4b99ac71/contenuto";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
/*
        String token = getToken();
        System.out.println("token : " + token);
*/
        
        praticheIstruttorieLCL("EV");
        //praticheIstruttorieLCL("EV_PUBB");
        //praticheIstruttorieLCL("EV_UPDALL");
        //praticheIstruttorieLCL("EV_UPDALL_COD");
        //praticheIstruttorieLCL("EV_INSALL");
        //praticheIstruttorieLCL("EV_INSALL_UPDIST");
    }

    private static String getToken() {
        String tokenUrl = endPointUrl + "/token";
        OauthHelper oauthHelper = new OauthHelper(
                tokenUrl,
                "consumerscrivabesrvScrivaApiInt01Test",
                "CXDWyEA_Mt0jdltXyyBEYdodNy8a",
                10000);
        return oauthHelper.getToken();
    }

    private static void ping(String accessToken) {
        String url = endPointScrivaApi + "/ping";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get();
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata PING :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("\n********************>>>> Response chiamata PING :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void pingNew(String accessToken) throws URISyntaxException, IOException, InterruptedException {
        String url = endPointScrivaApi + "/ping";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata PING :\n" + response.statusCode() + " : " + response.body() + "\n");
        } else {
            System.out.println("\n********************>>>> Response chiamata PING :\n" + response.body() + "\n");
        }
    }

    private static void soggetti(String accessToken) {
        String url = endPointScrivaApi + "/soggetti?codice_fiscale=LNEGRT09T43H816G&tipo_soggetto=PF";
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get();
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Soggetti :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Soggetti :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorie(String accessToken) {
        String url = endPointScrivaApi + "/pratiche-istruttoria?cod_istanza=VIA-VAL-785&cod_tipo_evento=IN_CARICO";
        PraticaIstruttoriaDTO praticaIstruttoria = null;
        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                //.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader("apimanager", "api07"))
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    /**
     * PRATICHE ISTRUTTORIA
     **/

    private static void praticheIstruttorieDEV(String tipoEvento) throws ParseException {
        praticheIstruttorieDirect(tipoEvento, endPointScrivaApiDEVDiretto, username, password, codIstanzaTest);
    }

    private static void praticheIstruttorieTST(String tipoEvento) throws ParseException {
        praticheIstruttorieDirect(tipoEvento, endPointScrivaApiTSTDiretto, username, password, codIstanzaTest);
    }

    private static void praticheIstruttorieLCL(String tipoEvento) throws ParseException {
        praticheIstruttorieDirect(tipoEvento, endPointScrivaApiLCLDiretto, username, password, codIstanzaTest);
    }

    private static void praticheIstruttorieDirect(String tipoEvento, String endPoint, String username, String password, String codIstanza) throws ParseException {
        if (StringUtils.isNotBlank(tipoEvento)) {
            switch (tipoEvento) {
                case "EV":
                    praticheIstruttorieEvento(endPoint, username, password, codIstanza);
                    break;
                case "EV_PUBB":
                    praticheIstruttorieEventoPubb(endPoint, username, password, codIstanza);
                    break;
                case "EV_UPDALL":
                    praticheIstruttorieEventoUpdAll(endPoint, username, password, codIstanza);
                    break;
                case "EV_UPDALL_COD":
                    praticheIstruttorieEventoUpdAllCodPratica(endPoint, username, password, codIstanza);
                    break;
                case "EV_INSALL":
                    praticheIstruttorieEventoInsAll(endPoint, username, password, codIstanza);
                    break;
                case "EV_INSALL_UPDIST":
                    praticheIstruttorieEventoInsAllUpdIst(endPoint, username, password, codIstanza);
                    break;
                default:
                    break;
            }
        }
    }

    private static void praticheIstruttorieEvento(String endPoint, String username, String password, String codIstanza) {
        String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=IN_CARICO&cod_istanza=" + codIstanza;
        PraticaIstruttoriaDTO praticaIstruttoria = null;
        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorieEventoPubb(String endPoint, String username, String password, String codIstanza) {
        String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=PUBBLICA&flag_pubblica_pratica=true&cod_istanza=" + codIstanza;
        PraticaIstruttoriaDTO praticaIstruttoria = null;
        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorieEventoUpdAll(String endPoint, String username, String password, String codIstanza) throws ParseException {
        String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=PROT_INT&cod_istanza=" + codIstanza;

        Calendar calendar = Calendar.getInstance();
        Timestamp now = new Timestamp(calendar.getTimeInMillis());

        List<PubDocIstanzaLinkDTO> pubDocIstanzaList = new ArrayList<>();
        PubDocIstanzaLinkDTO pubDocIstanza = new PubDocIstanzaLinkDTO();
        pubDocIstanza.setCodAllegato("uuidCOSMO_02");
        pubDocIstanza.setNumProtocolloAllegato("Prot112022");
        pubDocIstanza.setDataProtocolloAllegato(now);
        pubDocIstanzaList.add(pubDocIstanza);

        PraticaIstruttoriaDTO praticaIstruttoria = new PraticaIstruttoriaDTO();
        praticaIstruttoria.setDatiDocumento(pubDocIstanzaList);

        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorieEventoUpdAllCodPratica(String endPoint, String username, String password, String codIstanza) {
        String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=PROT_IST&flag_codice_Pratica=TRUE&cod_istanza=" + codIstanza;

        Calendar calendar = Calendar.getInstance();
        Timestamp now = new Timestamp(calendar.getTimeInMillis());

        List<PubDocIstanzaLinkDTO> pubDocIstanzaList = new ArrayList<>();
        PubDocIstanzaLinkDTO pubDocIstanza = new PubDocIstanzaLinkDTO();
        pubDocIstanza.setCodAllegato("SYS-273");
        pubDocIstanza.setNumProtocolloAllegato("Prot202022");
        pubDocIstanza.setDataProtocolloAllegato(now);
        pubDocIstanzaList.add(pubDocIstanza);

        PraticaIstruttoriaDTO praticaIstruttoria = new PraticaIstruttoriaDTO();
        praticaIstruttoria.setDatiDocumento(pubDocIstanzaList);

        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorieEventoInsAll(String endPoint, String username, String password, String codIstanza) {
        String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=NOTA_OTR&cod_istanza=" + codIstanza;

        Calendar calendar = Calendar.getInstance();
        Timestamp now = new Timestamp(calendar.getTimeInMillis());

        TipologiaAllegatoDTO tipologiaAllegato = new TipologiaAllegatoDTO();
        tipologiaAllegato.setCodTipologiaAllegato("NOTA_OSS");

        List<PubDocIstanzaLinkDTO> pubDocIstanzaList = new ArrayList<>();
        PubDocIstanzaLinkDTO pubDocIstanza = new PubDocIstanzaLinkDTO();
        pubDocIstanza.setTipologiaAllegato(tipologiaAllegato);
        pubDocIstanza.setFlgRiservato(true);
        pubDocIstanza.setCodAllegato("uuidCOSMO_0001");
        pubDocIstanza.setNomeAllegato("pippo_0001.pdf");
        pubDocIstanza.setNote("Inserimento nota aggiuntiva");
        pubDocIstanza.setFlgDaPubblicare(false);
        pubDocIstanza.setNumProtocolloAllegato("Prot152022");
        pubDocIstanza.setDataProtocolloAllegato(now);
        pubDocIstanza.setTitoloAllegato("Documento allegato");
        pubDocIstanza.setAutoreAllegato("Pippo");
        pubDocIstanza.setLinkDocumento(linkDocumento);
        pubDocIstanzaList.add(pubDocIstanza);

        PraticaIstruttoriaDTO praticaIstruttoria = new PraticaIstruttoriaDTO();
        praticaIstruttoria.setDatiDocumento(pubDocIstanzaList);

        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static void praticheIstruttorieEventoInsAllUpdIst(String endPoint, String username, String password, String codIstanza) {
        //String url = endPoint + "/pratiche-istruttoria?cod_tipo_evento=NOTA_OTR&cod_istanza=" + codIstanza;
        String url = endPoint + "/pratiche-istruttoria?cod_istanza=" + codIstanza;

        Calendar calendar = Calendar.getInstance();
        Timestamp now = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE, 15);
        Timestamp end = new Timestamp(calendar.getTimeInMillis());

        PubIstanzaDTO pubIstanza = new PubIstanzaDTO();
        pubIstanza.setCodIstanza(codIstanza);
        pubIstanza.setDataInizioOsservazione((List<Timestamp>) now);
        pubIstanza.setDataFineOsservazione(end);

        TipologiaAllegatoDTO tipologiaAllegato = new TipologiaAllegatoDTO();
        tipologiaAllegato.setCodTipologiaAllegato("NOTA_OSS");

        List<PubDocIstanzaLinkDTO> pubDocIstanzaList = new ArrayList<>();
        PubDocIstanzaLinkDTO pubDocIstanza = new PubDocIstanzaLinkDTO();
        pubDocIstanza.setTipologiaAllegato(tipologiaAllegato);
        pubDocIstanza.setFlgRiservato(true);
        pubDocIstanza.setCodAllegato("uuidCOSMO_0005");
        pubDocIstanza.setNomeAllegato("uuidCOSMO_0005_01.pdf");
        pubDocIstanza.setNote("Inserimento nota aggiuntiva");
        pubDocIstanza.setFlgDaPubblicare(false);
        pubDocIstanza.setNumProtocolloAllegato("Prot1002022");
        pubDocIstanza.setDataProtocolloAllegato(now);
        pubDocIstanza.setTitoloAllegato("Titolo allegato 0005");
        pubDocIstanza.setAutoreAllegato("Autore allegato 0005");
        pubDocIstanza.setCodAllegatoPadre(null);
        pubDocIstanza.setLinkDocumento(linkDocumento);
        pubDocIstanzaList.add(pubDocIstanza);

        PubDocIstanzaLinkDTO pubDocIstanza2 = new PubDocIstanzaLinkDTO();
        pubDocIstanza2.setTipologiaAllegato(tipologiaAllegato);
        pubDocIstanza2.setFlgRiservato(true);
        pubDocIstanza2.setCodAllegato("uuidCOSMO_0008");
        pubDocIstanza2.setNomeAllegato("uuidCOSMO_0008.pdf");
        pubDocIstanza2.setNote("Inserimento nota aggiuntiva");
        pubDocIstanza2.setFlgDaPubblicare(false);
        pubDocIstanza2.setNumProtocolloAllegato("Prot1012022");
        pubDocIstanza2.setDataProtocolloAllegato(now);
        pubDocIstanza2.setTitoloAllegato("Titolo allegato 0008");
        pubDocIstanza2.setAutoreAllegato("Autore allegato 0008");
        pubDocIstanza2.setCodAllegatoPadre("uuidCOSMO_0005");
        pubDocIstanza2.setLinkDocumento(linkDocumento);
        pubDocIstanzaList.add(pubDocIstanza2);

        PraticaIstruttoriaDTO praticaIstruttoria = new PraticaIstruttoriaDTO();
        praticaIstruttoria.setDatiDocumento(pubDocIstanzaList);
        praticaIstruttoria.setDatiIstanza(pubIstanza);

        Entity<PraticaIstruttoriaDTO> entity = Entity.json(praticaIstruttoria);
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(url);
        Response resp = target.request()
                .header(HttpHeaders.AUTHORIZATION, getBasicAuthenticationHeader(username, password))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header("X-Request-Auth", xRequestAuth)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .post(entity);
        if (resp.getStatus() >= 400) {
            System.out.println("********************>>>> ERRORE chiamata Pratiche :\n" + resp.getStatus() + " : " + resp.readEntity(String.class) + "\n");
        } else {
            System.out.println("********************>>>> RESPONSE chiamata Pratiche :\n" + resp.readEntity(String.class) + "\n");
        }
    }

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        String encodedString = "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
        System.out.println(encodedString);
        return encodedString;
    }


}