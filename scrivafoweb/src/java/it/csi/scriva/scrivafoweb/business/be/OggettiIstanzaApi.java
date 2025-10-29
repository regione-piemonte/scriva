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

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.OggettoIstanzaUbicazioneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.SearchOggettoDTO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.UriInfo;

/**
 * The interface Oggetti istanza api.
 */
@Path("/oggetti-istanza")
@Produces(MediaType.APPLICATION_JSON)
public interface OggettiIstanzaApi {

    /**
     * Gets oggetti istanza.
     *
     * @param xRequestAuth    the x request auth
     * @param xRequestId      the x request id
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the oggetti istanza
     */
    @GET
    Response getOggettiIstanza(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                      @QueryParam("id_istanza") Long idIstanza,
                                      @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Gets oggetto istanza by id.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @return the oggetto istanza by id
     */
    @GET
    @Path("/id/{idOggettoIstanza}")
    Response getOggettoIstanzaById(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Gets oggetto istanza by uid.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the oggetto istanza by uid
     */
    @GET
    @Path("/{uidOggettoIstanza}")
    Response getOggettoIstanzaByUID(@PathParam("uidOggettoIstanza") String uidOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Search oggetto istanza response.
     *
     * @param searchOggettoIstanza the search oggetto istanza
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the response
     */
    @POST
    @Path("/search")
    @Consumes({ "application/json" })
    Response searchOggettoIstanza(@RequestBody SearchOggettoDTO searchOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Save oggetto istanza response.
     *
     * @param oggettoIstanza  the oggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @POST
    @Consumes({ "application/json" })
    Response saveOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Update oggetto istanza response.
     *
     * @param oggettoIstanza  the oggetto istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @PUT
    @Consumes({ "application/json" })
    Response updateOggettoIstanza(@RequestBody OggettoIstanzaUbicazioneExtendedDTO oggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Delete oggetto istanza response.
     *
     * @param uidOggettoIstanza the uid oggetto istanza
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @DELETE
    @Path("/{uidOggetto}")
    Response deleteOggettoIstanza(@PathParam("uidOggetto") String uidOggettoIstanza, @QueryParam("check_figli") String checkFigli, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);

    /**
     * Verifica georeferenziazione response.
     *
     * @param codAdempimento  the cod adempimento
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/adempimento/{codAdempimento}/id-istanza/{idIstanza}")
    Response verificaGeoreferenziazione(@PathParam("codAdempimento") String codAdempimento, @PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest);


    /**
     * Gets aree protette by comuni ubicazione oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette by comuni ubicazione oggetto istanza
     */
    @GET
    @Path("/{idOggettoIstanza}/aree-protette/comuni")
    Response getAreeProtetteByComuniUbicazioneOggettoIstanza(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Gets aree protette by geometrie oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the aree protette by geometrie oggetto istanza
     */
    @GET
    @Path("/{idOggettoIstanza}/aree-protette/geometrie")
    Response getAreeProtetteByGeometrieOggettoIstanza(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Gets siti rete natura 2000 by comuni ubicazione oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the siti rete natura 2000 by comuni ubicazione oggetto istanza
     */
    @GET
    @Path("/{idOggettoIstanza}/rn2000/comuni")
    Response getSitiReteNatura2000ByComuniUbicazioneOggettoIstanza(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

    /**
     * Gets siti rete natura 2000 by geometrie oggetto istanza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param securityContext  the security context
     * @param httpHeaders      the http headers
     * @param httpRequest      the http request
     * @param uriInfo          the uri info
     * @return the siti rete natura 2000 by geometrie oggetto istanza
     */
    @GET
    @Path("/{idOggettoIstanza}/rn2000/geometrie")
    Response getSitiReteNatura2000ByGeometrieOggettoIstanza(@PathParam("idOggettoIstanza") Long idOggettoIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest, @Context UriInfo uriInfo) throws Exception;

}