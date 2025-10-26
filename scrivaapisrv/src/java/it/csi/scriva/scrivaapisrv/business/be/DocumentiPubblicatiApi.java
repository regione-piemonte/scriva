/**
 * ========================LICENSE_START=================================
 * Copyright (C) 2025 Regione Piemonte
 * SPDX-FileCopyrightText: Copyright 2025 | Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivaapisrv.business.be;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * The interface Documenti pubblicati api.
 *
 * @author CSI PIEMONTE
 */
@Path("/documenti-pubblicati")
@Produces(MediaType.APPLICATION_JSON)
public interface DocumentiPubblicatiApi {

    /**
     * Gets documenti pubblicati.
     *
     * @param xRequestAuth         the x request auth
     * @param xRequestId           the x request id
     * @param idIstanza            the id istanza
     * @param codAllegato          the cod allegato
     * @param codClasseAllegato    the cod classe allegato
     * @param codCategoriaAllegato the cod categoria allegato
     * @param codTipologiaAllegato the cod tipologia allegato
     * @param flgLinkDocumento     the flg link documento
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @param securityContext      the security context
     * @param httpHeaders          the http headers
     * @param httpRequest          the http request
     * @return the documenti pubblicati
     */
    @GET
    public Response getDocumentiPubblicati(@HeaderParam("X-Request-Auth") String xRequestAuth, @HeaderParam("X-Request-Id") String xRequestId,
                                           @QueryParam("id_istanza") Long idIstanza,
                                           @QueryParam("cod_allegato") String codAllegato,
                                           @QueryParam("cod_classe_allegato") String codClasseAllegato,
                                           @QueryParam("cod_categoria_allegato") String codCategoriaAllegato,
                                           @QueryParam("cod_tipologia_allegato") String codTipologiaAllegato,
                                           @DefaultValue("FALSE") @QueryParam("flg_link_documento") String flgLinkDocumento,
                                           @DefaultValue("1") @QueryParam(value = "offset") Integer offset,
                                           @DefaultValue("20") @QueryParam(value = "limit") Integer limit,
                                           @DefaultValue("") @QueryParam(value = "sort") String sort,
                                           @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest,
                                           @Context UriInfo uriInfo);

}