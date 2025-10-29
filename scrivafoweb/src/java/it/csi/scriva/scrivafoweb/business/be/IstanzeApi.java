/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ReferenteIstanzaDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Date;

/**
 * The interface Istanze api.
 */
@Path("/istanze")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzeApi {

    /**
     * Gets istanze.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the istanze
     * @throws GenericException the generic exception
     */
    @GET
    Response getIstanze(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/compilante/cf/{cfCompilante}")
    Response getIstanzeByCfCompilante(@PathParam("cfCompilante") String cfCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/compilante/{idCompilante}")
    Response getIstanzeByIdCompilante(@PathParam("idCompilante") Long idCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/id/{idIstanza}")
    Response getIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
     * @throws Exception the exception
     */
    @GET
    @Path("/{idIstanza}/json-data")
    Response getJsonDataIstanza(@PathParam("idIstanza") Long idIstanza, @QueryParam("cod_tipo_quadro") String codTipoQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

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
    @POST
    @Consumes({"application/json"})
    Response saveIstanza(@RequestBody IstanzaExtendedDTO istanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @PUT
    @Consumes({"application/json"})
    Response updateIstanza(@QueryParam("flg_crea_pratica") @DefaultValue("FALSE") Boolean flgCreaPratica, @RequestBody IstanzaExtendedDTO istanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @DELETE
    @Path("/{uid}")
    Response deleteIstanza(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/pdf/{idIstanza}")
    @Produces({"application/json", "application/pdf"})
    Response createPDF(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @PUT
    @Path("/stato-istanza/uid-istanza/{uidIstanza}/id-stato-istanza/{idStatoIstanza}")
    Response updateStatoIstanza(@PathParam("uidIstanza") String uidIstanza, @PathParam("idStatoIstanza") Long idStatoIstanza, @DefaultValue("SYSTEM") @QueryParam(value = "gestAttoreUpd") String gestAttoreUpd, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/check-istanza/id-istanza/{idIstanza}/adempimento/{codAdempimento}")
    Response checkIstanza(@PathParam("idIstanza") Long idIstanza, @PathParam("codAdempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Download file pdf con elenco allegati associati all’istanza in corso.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/pdf-allegati/{idIstanza}")
    @Produces({"application/json", "application/pdf"})
    Response downloadPDFAllegati(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;


    /**
     * Upload del modulo firmato dell'istanza su INDEX
     *
     * @param idIstanza       idIstanza
     * @param formDataInput   File
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @POST
    @Path("/pdf-modulo-firmato-istanza/{idIstanza}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response uploadPDFModuloFirmatoIstanza(@PathParam("idIstanza") Long idIstanza, MultipartFormDataInput formDataInput, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;


    //REFERENTI

    /**
     * Add referente response.
     *
     * @param referente       the referente
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @POST
    @Path("/referenti")
    Response addReferente(@RequestBody ReferenteIstanzaDTO referente, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Update referente response.
     *
     * @param referente       the referente
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @PUT
    @Path("/referenti")
    Response updateReferente(@RequestBody ReferenteIstanzaDTO referente, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Delete referente response.
     *
     * @param uid             the uid
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @DELETE
    @Path("/referenti/{uid}")
    Response deleteReferente(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets referenti by istanza.
     *
     * @param idIstanza   the id istanza
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the referenti by istanza
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/referenti/id-istanza/{idIstanza}")
    Response getReferentiByIstanza(@PathParam("idIstanza") Long idIstanza, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets referenti by code istanza.
     *
     * @param codeIstanza the code istanza
     * @param httpHeaders the http headers
     * @param httpRequest the http request
     * @return the referenti by code istanza
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/referenti/codice-istanza/{codeIstanza}")
    Response getReferentiByCodeIstanza(@PathParam("codeIstanza") String codeIstanza, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Download modulo delega response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @POST
    @Path("/doc-modulo-delega-istanza/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response createModuloDelega(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

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
    @GET
    @Path("/{id}/_download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response createDOCOrPDFcIstanza(@PathParam("id") Long idIstanza,
                                    @QueryParam("cod_tipologia_allegato") String codTipologiaAllegato,
                                    @QueryParam("output_format") @DefaultValue("pdf") String outputFormat,
                                    @QueryParam("archivia") @DefaultValue("FALSE") Boolean archivia,
                                    @QueryParam("cancella") @DefaultValue("TRUE") Boolean cancella,
                                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                                    @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws GenericException;

    /**
     * Create codice pratica response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @GET
    @Path("/{id}/codice-pratica")
    Response createCodicePratica(@PathParam("id") Long idIstanza,
                                 @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                                 @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

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
    @GET
    @Path("/{id}/stati")
    Response getIstanzaStati(@PathParam("id") Long idIstanza,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                             @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

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
    @PATCH
    @Path("/{id}/_pubblica")
    Response patchDataPubblicazionedIstanza(@PathParam("id") Long idIstanza,
                                            @QueryParam("data_pubblicazione") Date dataPubblicazione,
                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                                            @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Load istanze responsabili response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @param uriInfo         the uri info
     * @return the response
     * @throws Exception the exception
     */
    @GET
    @Path("/{id}/responsabili")
    Response getIstanzaResponsabili(@PathParam("id") Long idIstanza,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

}