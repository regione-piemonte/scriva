/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;


import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.scriva.scrivaapisrv.business.be.helper.scrivabe.dto.AllegatoIstanzaExtendedDTO;

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
     * Estrazione degli allegati presenti su SCRIVA. Nessun filtro applicato
     * Estrazione dei METADATI dei documenti alleati ad una specifica istanza passata in input (scriva_t_allegato_istanza)
     *
     * @param idIstanza       the id istanza
     * @param offset          the offset
     * @param limit           the limit
     * @param sort            the sort
     * @param system          the system
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response allegati
     */
    @GET
    Response getAllegati(@QueryParam("id_istanza") Long idIstanza,
                         @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                         @DefaultValue("20") @QueryParam(value = "limit") Integer limit,
                         @DefaultValue("") @QueryParam(value = "sort") String sort,
                         @DefaultValue("false") @QueryParam(value = "sys") Boolean system,
                         @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Generazione csv con elenco allegati associati all’istanza.
     * Caricamento su index del pdf con l'elenco degli allegati.
     * Generazione zip con file allegati associati all’istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param outputFormat    the output format
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/_download")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response getFileAllegati(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                              @QueryParam("id_istanza") Long idIstanza, @QueryParam("output_format") String outputFormat,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Rappresenta il download da INDEX di un documento specifico.
     * Oltre al file binario vengono restituiti in output i metadati del documento, letti dalla SCRIVA_T_ALLEGATO_ISTANZA.
     *
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response allegati by uuid index
     */
    @GET
    @Path("/{uuidIndex}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response getAllegatiByUuidIndex(@PathParam("uuidIndex") String uuidIndex, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Servizio per il caricamento di un documento allegato all’istanza su piattaforma INDEX.
     * Il servizio si occupa delle seguenti attività:
     * - Caricamento documento su INDEX
     * - Eventuale verifica firma documento e aggiornamento aspect firma
     * - Recupero metadati documento su INDEX
     * - Inserimento metadati documento su SCRIVA
     *
     * @param formDataInput   MultipartFormDataInput
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    Response uploadIndexFile(MultipartFormDataInput formDataInput, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Questo metodo serve per aggiornare i metadati di un documento già presente su INDEX e su SCRIVA.
     * Può essere utilizzato anche per la cancellazione logica di un documento, in quanto è rappresentata dall’aggiornamento dei metadati DATA_CANCELLAZIONE = sysdate e FLG_CANCELLATO = 1 su scriva e dell’aspect "scriva:cancellato" del documento corrispondente su INDEX
     *
     * @param allegatoIstanza AllegatoIstanzaExtendedDTO
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateIndexFile(AllegatoIstanzaExtendedDTO allegatoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio permette di eliminare FISICAMENTE un documento da SCRIVA e da INDEX.
     *
     * @param uuidIndex       uuidIndex
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @DELETE
    @Path("/{uuidIndex}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteIndexFile(@PathParam("uuidIndex") String uuidIndex, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle tipologie obbligatorie previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response categoria allegato mandatory by id istanza
     */
    @GET
    @Path("/_mandatory-categoria")
    Response getCategoriaAllegatoMandatoryByIdIstanza(@QueryParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti di delega qualora tra i soggetti vi sia un delegato con firma.
     * Se l’esito è negativo sarà presentato un errore.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/_mandatory-delega")
    Response checkAllegatoMandatoryDelegaFirmataByIdIstanza(@QueryParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Il servizio verifica se sono stai associati all’istanza i documenti appartenenti alle categorie non previste per l’adempimento.
     * Se l’esito è positivo la lista è vuota, diversamente saranno presenti le categorie obbligatorie mancanti.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response categoria allegato mandatory adempimento by id istanza
     */
    @GET
    @Path("/categorie-adempimento")
    Response getCategoriaAllegatoMandatoryAdempimentoByIdIstanza(@QueryParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}