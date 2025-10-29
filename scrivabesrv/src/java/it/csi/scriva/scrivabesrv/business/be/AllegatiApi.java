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


import it.csi.scriva.scrivabesrv.business.be.exception.CosmoException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.GenericInputParDTO;
import it.csi.scriva.scrivabesrv.dto.SearchAllegatoDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

/**
 * The interface Allegati api.
 *
 * @author CSI PIEMONTE
 */
@Path("/allegati")
@Produces(MediaType.APPLICATION_JSON)
public interface AllegatiApi {

    /**
     * Load allegati response.
     *
     * @param xRequestAuth          the x request auth
     * @param xRequestId            the x request id
     * @param idIstanza             the id istanza
     * @param codAllegato           the cod allegato
     * @param codClasseAllegato     the cod classe allegato
     * @param codCategoriaAllegato  the cod categoria allegato
     * @param codTipologiaAllegato  the cod tipologia allegato
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param flgCancLogica         the flg canc logica
     * @param flgLinkDocumento      the flg link documento
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param system                the system
     * @param securityContext       the security context
     * @param httpHeaders           the http headers
     * @param httpRequest           the http request
     * @return the response
     * @throws GenericException the generic exception
     */
    @GET
    Response loadAllegati(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                          @QueryParam("id_istanza") Long idIstanza,
                          @QueryParam("cod_allegato") String codAllegato,
                          @QueryParam("cod_classe_allegato") String codClasseAllegato,
                          @QueryParam("cod_categoria_allegato") String codCategoriaAllegato,
                          @QueryParam("cod_tipologia_allegato") String codTipologiaAllegato,
                          @QueryParam("id_osservazione") Long idIstanzaOsservazione,
                          @DefaultValue("FALSE") @QueryParam("flg_canc_logica") String flgCancLogica,
                          @DefaultValue("FALSE") @QueryParam("flg_link_documento") String flgLinkDocumento,
                          @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                          @DefaultValue("20") @QueryParam(value = "limit") Integer limit,
                          @DefaultValue("") @QueryParam(value = "sort") String sort,
                          @DefaultValue("false") @QueryParam(value = "sys") Boolean system,
                          @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;



    /**
     * Search allegati response.
     *
     * @param searchCriteria  SearchAllegatoDTO
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Path("/_search")
    Response searchAllegati(@RequestBody SearchAllegatoDTO searchCriteria,
    						@DefaultValue("1") @QueryParam(value = "offset") Integer offset,
    						@DefaultValue("99999") @QueryParam(value = "limit") Integer limit,
    						@DefaultValue("") @QueryParam(value = "sort") String sort,
    						@Context SecurityContext securityContext,
    						@Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;


    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Caricamento su index del pdf con l'elenco degli allegati.
     * Generazione zip con file allegati associati all’istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codAllegato     the cod allegato
     * @param idOsservazione  the id osservazione
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/_download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response loadFileAllegati(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @QueryParam("id_istanza") Long idIstanza, @QueryParam("cod_allegato") String codAllegato, @QueryParam("id_osservazione") Long idOsservazione, @DefaultValue("CSV") @QueryParam("output_format") String outputFormat,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Caricamento su index del pdf con l'elenco degli allegati.
     * Generazione zip con file allegati associati all’istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param codAllegato     the cod allegato
     * @param idOsservazione  the id osservazione
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Path("/_download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response loadFileAllegatiSelezionati(
    	@RequestBody GenericInputParDTO codiciAllegato,
    	@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
        @QueryParam("id_istanza") Long idIstanza, @QueryParam("cod_allegato") String codAllegato,
        @QueryParam("id_osservazione") Long idOsservazione, @DefaultValue("CSV") @QueryParam("output_format") String outputFormat,
        @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Rappresenta il download da INDEX di un documento specifico.
     * Oltre al file binario vengono restituiti in output i metadati del documento, letti dalla SCRIVA_T_ALLEGATO_ISTANZA.
     *
     * @param uuidIndex       uuidIndex
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/{uuidIndex}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response loadAllegatiByUuidIndex(@PathParam("uuidIndex") String uuidIndex,
                                     @HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                     @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Servizio per il caricamento di un documento allegato all’istanza su piattaforma INDEX.
     * Il servizio si occupa delle seguenti attività:
     * - Caricamento documento su INDEX
     * - Eventuale verifica firma documento e aggiornamento aspect firma
     * - Recupero metadati documento su INDEX
     * - Inserimento metadati documento su SCRIVA
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param formDataInput   MultipartFormDataInput
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response uploadIndexFile(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                             MultipartFormDataInput formDataInput,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Questo metodo serve per aggiornare i metadati di un documento già presente su INDEX e su SCRIVA.
     * Può essere utilizzato anche per la cancellazione logica di un documento, in quanto è rappresentata dall’aggiornamento dei metadati DATA_CANCELLAZIONE = sysdate e FLG_CANCELLATO = 1 su scriva e dell’aspect "scriva:cancellato" del documento corrispondente su INDEX
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param allegatoIstanza AllegatoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIndexFile(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                             AllegatoIstanzaExtendedDTO allegatoIstanza,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio permette di eliminare FISICAMENTE un documento da SCRIVA e da INDEX.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uuidIndex}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteIndexFile(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                             @PathParam("uuidIndex") String uuidIndex,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Delete index file codice allegato response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param codAllegato     the cod allegato
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteIndexFileCodiceAllegato(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                           @QueryParam("cod_allegato") String codAllegato,
                                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle tipologie obbligatorie previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/_mandatory-categoria")
    Response loadCategoriaAllegatoMandatoryByIdIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                                       @QueryParam("id_istanza") Long idIstanza,
                                                       @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Check allegato mandatory delega firmata by id istanza response.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/_mandatory-delega")
    Response checkAllegatoMandatoryDelegaFirmataByIdIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                                            @QueryParam("id_istanza") Long idIstanza,
                                                            @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle categorie non previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/categorie-adempimento")
    Response loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                                                  @QueryParam("idIstanza") Long idIstanza,
                                                                  @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Load allegati by url cosmo response.
     *
     * @param urlCosmo        the url cosmo
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     * @throws CosmoException the cosmo exception
     */
    @GET
    @Path("/cosmo")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response loadAllegatiByUrlCosmo(@QueryParam("url_cosmo") String urlCosmo,
                                    @HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                    @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws CosmoException;

}