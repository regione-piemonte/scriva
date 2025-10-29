/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be;

import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;

/**
 * The interface Istanza api.
 *
 * @author CSI PIEMONTE
 */
@Path("/istanze")
@Produces(MediaType.APPLICATION_JSON)
public interface IstanzaApi {

    /**
     * Load istanze response.
     *
     * @param codIstanza      the cod istanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response loadIstanze(@QueryParam("cod_istanza") String codIstanza,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanze by cf compilante response.
     *
     * @param cfCompilante    codice fiscale compilante
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/compilante/cf/{cfCompilante}")
    Response loadIstanzeByCfCompilante(@PathParam("cfCompilante") String cfCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanze by id compilante response.
     *
     * @param idCompilante    idCompilante
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/compilante/{idCompilante}")
    Response loadIstanzeByIdCompilante(@PathParam("idCompilante") Long idCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanza response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/id/{idIstanza}")
    Response loadIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load json data istanza response.
     *
     * @param idIstanza       the id istanza
     * @param codTipoQuadro   the cod tipo quadro
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws ParseException the parse exception
     */
    @GET
    @Path("/{idIstanza}/json-data")
    Response loadJsonDataIstanza(@PathParam("idIstanza") Long idIstanza, @QueryParam("cod_tipo_quadro") String codTipoQuadro, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws ParseException;

    /**
     * Save istanza response.
     *
     * @param istanza         IstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response saveIstanza(@RequestBody IstanzaExtendedDTO istanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update istanza response.
     *
     * @param flgCreaPratica  the flg crea pratica
     * @param istanza         IstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIstanza(@QueryParam("flg_crea_pratica") @DefaultValue("FALSE") Boolean flgCreaPratica, @RequestBody IstanzaExtendedDTO istanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete istanza response.
     *
     * @param uid             uidIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uid}")
    Response deleteIstanza(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete istanza by id response.
     *
     * @param id              idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/id/{id}")
    Response deleteIstanzaById(@PathParam("id") Long id, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    //REFERENTI//

    /**
     * Add referente response.
     *
     * @param referenteIstanza ReferenteIstanzaExtendedDTO
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/referenti")
    Response addReferente(@RequestBody ReferenteIstanzaDTO referenteIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update referente response.
     *
     * @param referenteIstanza ReferenteIstanzaExtendedDTO
     * @param securityContext  SecurityContext
     * @param httpHeaders      HttpHeaders
     * @param httpRequest      HttpServletRequest
     * @return Response response
     */
    @PUT
    @Path("/referenti")
    Response updateReferente(@RequestBody ReferenteIstanzaDTO referenteIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete referente response.
     *
     * @param uid             uidIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/referenti/{uid}")
    Response deleteReferente(@PathParam("uid") String uid, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Delete referente by id response.
     *
     * @param idReferenteIstanza idReferenteIstanza
     * @param securityContext    SecurityContext
     * @param httpHeaders        HttpHeaders
     * @param httpRequest        HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/referenti/id/{idReferenteIstanza}")
    Response deleteReferenteById(@PathParam("idReferenteIstanza") Long idReferenteIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load referenti by istanza response.
     *
     * @param idIstanza   idIstanza
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/referenti/id-istanza/{idIstanza}")
    Response loadReferentiByIstanza(@PathParam("idIstanza") Long idIstanza, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load referenti by code istanza response.
     *
     * @param codeIstanza codeIstanza
     * @param httpHeaders HttpHeaders
     * @param httpRequest HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/referenti/codice-istanza/{codeIstanza}")
    Response loadReferentiByCodeIstanza(@PathParam("codeIstanza") String codeIstanza, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Create pdf istanza response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/pdf/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response createPDFIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Read json data response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/read/{idIstanza}")
    Response readJsonData(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Update json data response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/upd-json/{idIstanza}")
    Response updateJsonData(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Permette l’inserimento nello storico stato istanza di un nuovo passaggio di stato con quanto passato in input.
     * Contestualmente aggiorna lo stato attuale sull'istanza
     *
     * @param uidIstanza      uidIstanza
     * @param idStatoIstanza  idStatoIstanza
     * @param gestAttoreUpd   gestAttoreUpd
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Path("/stato-istanza/uid-istanza/{uidIstanza}/id-stato-istanza/{idStatoIstanza}")
    Response updateStatoIstanza(@PathParam("uidIstanza") String uidIstanza, @PathParam("idStatoIstanza") Long idStatoIstanza, @DefaultValue("SYSTEM") @QueryParam(value = "gestAttoreUpd") String gestAttoreUpd, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Verifica che l’istanza abbia associate le informazioni minime previste in base alla configurazione dell’adempimento di riferimento (numero soggetti, georeferenziazione se obbligatoria, allegati obbligatori..)
     *
     * @param idIstanza       idIstanza
     * @param codAdempimento  codAdempimento
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/check-istanza/id-istanza/{idIstanza}/adempimento/{codAdempimento}")
    Response checkIstanza(@PathParam("idIstanza") Long idIstanza, @PathParam("codAdempimento") String codAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Download file pdf con elenco allegati associati all’istanza in corso.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/pdf-allegati/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response downloadPDFAllegati(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Upload del modulo firmato dell'istanza su INDEX
     *
     * @param idIstanza       idIstanza
     * @param formDataInput   File
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/pdf-modulo-firmato-istanza/{idIstanza}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response uploadPDFModuloFirmatoIstanza(@PathParam("idIstanza") Long idIstanza, MultipartFormDataInput formDataInput, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Download modulo delega response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/doc-modulo-delega-istanza/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response createModuloDelega(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Create doc or pdf istanza response.
     *
     * @param idIstanza            the id istanza
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param outputFormat         the output format
     * @param archivia             the archivia
     * @param cancella             the cancella
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @GET
    @Path("/{id}/_download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response createDOCOrPDFcIstanza(@PathParam("id") Long idIstanza, @QueryParam("cod_tipologia_allegato") String codTipologiaAllegato,
                                    @QueryParam("output_format") @DefaultValue("pdf") String outputFormat,
                                    @QueryParam("archivia") @DefaultValue("FALSE") Boolean archivia,
                                    @QueryParam("cancella") @DefaultValue("TRUE") Boolean cancella,
                                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Create codice pratica response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{id}/codice-pratica")
    Response createCodicePratica(@PathParam("id") Long idIstanza,
                                 @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanza stato response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{id}/stati")
    Response loadIstanzaStato(@PathParam("id") Long idIstanza,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Patch data pubblicazione istanza response.
     *
     * @param idIstanza         the id istanza
     * @param dataPubblicazione the data pubblicazione
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @PATCH
    @Path("/{id}/_pubblica")
    Response patchDataPubblicazionedIstanza(@PathParam("id") Long idIstanza,
                                            @QueryParam("data_pubblicazione") Date dataPubblicazione,
                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load istanze responsabili response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/{id}/responsabili")
    Response loadIstanzaResponsabili(@PathParam("id") Long idIstanza,
                                     @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);
}