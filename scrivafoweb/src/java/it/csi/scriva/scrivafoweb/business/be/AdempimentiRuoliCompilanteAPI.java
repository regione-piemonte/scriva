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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


 /**
  * The interface Adempimenti ruoli compilante api.
  */
 @Path("/adempimenti-ruoli-compilante")
@Produces(MediaType.APPLICATION_JSON)
public interface AdempimentiRuoliCompilanteAPI {

     /**
      * Gets adempimenti ruoli compilante.
      *
      * @param securityContext getAdempimentiRuoliCompilante
      * @param httpHeaders     HttpHeaders
      * @param httpRequest     HttpRequest
      * @return Response adempimenti ruoli compilante
      */
     @GET
    Response getAdempimentiRuoliCompilante(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

     /**
      * Gets adempimenti ruoli compilante by ruolo compilante.
      *
      * @param idRuoloCompilante idRuoloCompilante
      * @param securityContext   getAdempimentiRuoliCompilante
      * @param httpHeaders       HttpHeaders
      * @param httpRequest       HttpRequest
      * @return Response adempimenti ruoli compilante by ruolo compilante
      */
     @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}")
    Response getAdempimentiRuoliCompilanteByRuoloCompilante(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

     /**
      * Gets adempimenti ruoli compilante by adempimento.
      *
      * @param idAdempimento   idAdempimento
      * @param securityContext getAdempimentiRuoliCompilante
      * @param httpHeaders     HttpHeaders
      * @param httpRequest     HttpRequest
      * @return Response adempimenti ruoli compilante by adempimento
      */
     @GET
    @Path("/id-adempimento/{idAdempimento}")
    Response getAdempimentiRuoliCompilanteByAdempimento(@PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

     /**
      * Gets adempimento ruolo compilante by ruolo compilante adempimento.
      *
      * @param idRuoloCompilante idRuoloCompilante
      * @param idAdempimento     idAdempimento
      * @param securityContext   getAdempimentiRuoliCompilante
      * @param httpHeaders       HttpHeaders
      * @param httpRequest       HttpRequest
      * @return Response adempimento ruolo compilante by ruolo compilante adempimento
      */
     @GET
    @Path("/id-ruolo-compilante/{idRuoloCompilante}/id-adempimento/{idAdempimento}")
    Response getAdempimentoRuoloCompilanteByRuoloCompilanteAdempimento(@PathParam("idRuoloCompilante") Long idRuoloCompilante, @PathParam("idAdempimento") Long idAdempimento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

}