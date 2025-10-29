/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.impl;

import it.csi.scriva.scrivafoweb.business.be.IstanzeApi;
import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.IstanzeApiServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivafoweb.dto.FileFoDTO;
import it.csi.scriva.scrivafoweb.dto.UserInfo;
import it.csi.scriva.scrivafoweb.util.Constants;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Date;

/**
 * The type Istanze api service.
 */
@Component
public class IstanzeApiServiceImpl extends ScrivaBeProxyApiServiceImpl implements IstanzeApi {

    private final String className = this.getClass().getSimpleName();

    /**
     * The Istanze api service helper.
     */
    @Autowired
    IstanzeApiServiceHelper istanzeApiServiceHelper;

    /**
     * Gets istanze.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanze
     * @throws GenericException the generic exception
     */
    @Override
    public Response getIstanze(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBegin(className);
        return getResponseWithSharedHeaders(istanzeApiServiceHelper.getIstanze(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest)), istanzeApiServiceHelper, 200);
    }

    /**
     * Gets istanze by cf compilante.
     *
     * @param cfCompilante    the cf compilante
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanze by cf compilante
     * @throws GenericException the generic exception
     */
    @Override
    public Response getIstanzeByCfCompilante(String cfCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "cfCompilante: [" + cfCompilante + "]");
        return getResponseWithSharedHeaders(istanzeApiServiceHelper.getIstanzeByCfCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), cfCompilante), istanzeApiServiceHelper, 200);
    }

    /**
     * Gets istanze by id compilante.
     *
     * @param idCompilante    the id compilante
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanze by id compilante
     * @throws GenericException the generic exception
     */
    @Override
    public Response getIstanzeByIdCompilante(Long idCompilante, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idCompilante);
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.getIstanzeByIdCompilante(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idCompilante), istanzeApiServiceHelper, 200);
    }

    /**
     * Gets istanza.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanza
     * @throws GenericException the generic exception
     */
    @Override
    public Response getIstanza(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.getIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza), istanzeApiServiceHelper, 200);
    }

    /**
     * Gets json data istanza.
     *
     * @param idIstanza       the id istanza
     * @param codTipoQuadro   the cod tipo quadro
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the json data istanza
     * @throws ParseException the parse exception
     */
    @Override
    public Response getJsonDataIstanza(Long idIstanza, String codTipoQuadro, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Save istanza response.
     *
     * @param istanza         the istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @Override
    public Response saveIstanza(IstanzaExtendedDTO istanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, istanza);
        UserInfo user = getSessionUser(httpRequest);
        istanza.setGestAttoreIns(user.getCodFisc());
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.saveIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanza), istanzeApiServiceHelper, 201);
    }

    /**
     * Update istanza response.
     *
     * @param flgCreaPratica  the flg crea pratica
     * @param istanza         the istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @Override
    public Response updateIstanza(Boolean flgCreaPratica, IstanzaExtendedDTO istanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, istanza);
        UserInfo user = getSessionUser(httpRequest);
        istanza.setGestAttoreUpd(user.getCodFisc());
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.updateIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), istanza, flgCreaPratica), istanzeApiServiceHelper, 200);
    }

    /**
     * Delete istanza response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @Override
    public Response deleteIstanza(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "uid: [" + uid + "]");
        istanzeApiServiceHelper.deleteIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        return Response.noContent().build();
    }

    /**
     * Create pdf response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @Override
    public Response createPDF(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        FileFoDTO fileFoDTO = istanzeApiServiceHelper.createPDF(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        logEnd(className);
        return Response.ok(fileFoDTO.getFileContent(), "application/pdf").header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
    }

    /**
     * Permette l’inserimento nello storico stato istanza di un nuovo passaggio di stato con quanto passato in input.
     * Contestualmetne aggiorna lo stato attuale sull'istanza
     *
     * @param uidIstanza      uidIstanza
     * @param idStatoIstanza  idStatoIstanza
     * @param gestAttoreUpd   gestAttoreUpd
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @Override
    public Response updateStatoIstanza(String uidIstanza, Long idStatoIstanza, String gestAttoreUpd, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "uidIstanza [" + uidIstanza + "] - idStatoIstanza [" + idStatoIstanza + "]");
        UserInfo user = getSessionUser(httpRequest);
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.updateStatoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uidIstanza, idStatoIstanza, user.getCodFisc()), istanzeApiServiceHelper, 200);
    }

    /**
     * Verifica che l’istanza abbia associate le informazioni minime previste in base alla configurazione dell’adempimento di riferimento (numero soggetti, georeferenziazione se obbligatoria, allegati obbligatori..)
     *
     * @param idIstanza       idIstanza
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @Override
    public Response checkIstanza(Long idIstanza, String codAdempimento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        istanzeApiServiceHelper.checkIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, codAdempimento);
        return Response.noContent().build();
    }

    /**
     * Download file pdf con elenco allegati associati all’istanza in corso.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response downloadPDFAllegati(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        FileFoDTO fileFoDTO = istanzeApiServiceHelper.downloadPDFAllegati(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        logEnd(className);
        return Response.ok(fileFoDTO.getFileContent(), "application/pdf").header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
    }

    /**
     * Upload del modulo firmato dell'istanza su INDEX
     *
     * @param idIstanza       idIstanza
     * @param formDataInput   File
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response
     */
    @Override
    public Response uploadPDFModuloFirmatoIstanza(Long idIstanza, MultipartFormDataInput formDataInput, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.uploadPDFModuloFirmatoIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza, formDataInput), istanzeApiServiceHelper, 200);
    }


    @Override
    public Response addReferente(ReferenteIstanzaDTO referente, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, referente);
        UserInfo user = getSessionUser(httpRequest);
        referente.setGestAttoreIns(user.getCodFisc());
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.addReferente(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), referente), istanzeApiServiceHelper, 201);
    }

    @Override
    public Response updateReferente(ReferenteIstanzaDTO referente, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, referente);
        UserInfo user = getSessionUser(httpRequest);
        referente.setGestAttoreUpd(user.getCodFisc());
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.updateReferente(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), referente), istanzeApiServiceHelper, 200);
    }

    @Override
    public Response deleteReferente(String uid, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "uid: [" + uid + "]");
        istanzeApiServiceHelper.deleteReferente(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), uid);
        return Response.noContent().build();
    }

    @Override
    public Response getReferentiByIstanza(Long idIstanza, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.getReferentiByIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza), istanzeApiServiceHelper, 200);
    }

    @Override
    public Response getReferentiByCodeIstanza(String codeIstanza, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, (Object) "codeIstanza [" + codeIstanza + "]");
        return getResponseWithSharedHeaders(className, istanzeApiServiceHelper.getReferentiByCodeIstanza(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), codeIstanza), istanzeApiServiceHelper, 200);
    }

    /**
     * Download modulo delega response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @Override
    public Response createModuloDelega(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) throws GenericException {
        logBeginInfo(className, idIstanza);
        FileFoDTO fileFoDTO = istanzeApiServiceHelper.createModuloDelega(getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest), idIstanza);
        return Response.ok(fileFoDTO.getFileContent(), MediaType.APPLICATION_OCTET_STREAM_TYPE).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
    }

    /**
     * Create doc or pd fc istanza response.
     *
     * @param idIstanza            the id istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param outputFormat         the output format
     * @param archivia             the archivia
     * @param cancella             the cancella
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @param uriInfo              the uri info
     * @return the response
     * @throws GenericException the generic exception
     */
    @Override
    public Response createDOCOrPDFcIstanza(Long idIstanza, String codTipologiaAllegato, String outputFormat, Boolean archivia, Boolean cancella, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws GenericException {
        logBeginInfo(className, getInputParams(uriInfo.getPath(), uriInfo.getQueryParameters()));
        FileFoDTO fileFoDTO = istanzeApiServiceHelper.getIstanzaDocument(uriInfo.getPath(), uriInfo.getQueryParameters(), getMultivaluedMapFromHttpHeaders(httpHeaders, httpRequest));
        return Response.ok(fileFoDTO.getFileContent(), MediaType.APPLICATION_OCTET_STREAM_TYPE).header(HttpHeaders.CONTENT_ENCODING, IDENTITY).header(Constants.HEADER_CONTENT_DISPOSITION, fileFoDTO.getContentDispositionHeader()).build();
    }

    /**
     * Create codice pratica response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     */
    @Override
    public Response createCodicePratica(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Load istanza stati response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response getIstanzaStati(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Patch data pubblicazioned istanza response.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @param uriInfo           the uri info
     * @return the response
     * @throws Exception the exception
     */
    @Override
    public Response patchDataPubblicazionedIstanza(Long idIstanza, Date dataPubblicazione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
        logBeginInfo(className, idIstanza);
        return getResponsePATCH(uriInfo.getPath(), null, securityContext, httpHeaders, httpRequest, uriInfo);
    }

    /**
     * Load istanza responsabili response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
	@Override
	public Response getIstanzaResponsabili(Long idIstanza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, UriInfo uriInfo) throws Exception {
		logBeginInfo(className, idIstanza);
        return getResponseGET(uriInfo.getPath(), securityContext, httpHeaders, httpRequest, uriInfo);
	}

}