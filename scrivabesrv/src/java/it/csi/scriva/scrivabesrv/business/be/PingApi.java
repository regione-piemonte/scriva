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

import com.fasterxml.jackson.core.JsonProcessingException;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaExtendedDTO;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.Date;
import java.util.List;

/**
 * The interface Ping api.
 *
 * @author CSI PIEMONTE
 */
@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public interface PingApi {

    /**
     * Ping response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    Response ping(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test anagamb response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/anagamb")
    Response testAnagamb(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Test email response.
     *
     * @param email           email
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/email/{email}")
    Response testEmail(@PathParam("email") String email, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test pec response.
     *
     * @param email           the email
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/pec/{email}")
    Response testPec(@PathParam("email") String email, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test spid response.
     *
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/spid")
    Response testSPID(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test pdf response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     */
    @GET
    @Path("/pdf/{idIstanza}/{nomeFile}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response testPDF(@PathParam("idIstanza") Long idIstanza, @PathParam("nomeFile") String  nomeFile, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Compila doc response.
     *
     * @param idIstanza       idIstanza
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/doc/{idIstanza}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response compilaDoc(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Test json istanza response.
     *
     * @param idIstanza       idIstanza
     * @param key             the key
     * @param value           the value
     * @param securityContext SecurityContext
     * @param httpHeaders     HttpHeaders
     * @param httpRequest     HttpServletRequest
     * @return Response response
     * @throws JsonProcessingException the json processing exception
     */
    @GET
    @Path("/json/{idIstanza}/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    Response testJsonIstanza(@PathParam("idIstanza") Long idIstanza, @PathParam("key") String key,
                             @QueryParam("value") String value,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws JsonProcessingException;


    /**
     * Test transaction response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/trans")
    Response testTransaction(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test riscossione ente response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/riscossione/{idIstanza}")
    Response testRiscossioneEnte(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Adempimento tipo pagamento list response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/tipo-pagamento/{idIstanza}")
    Response testAdempiTipoPagamento(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test pagamento istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/pagamento-istanza/{idIstanza}")
    Response testPagamentoIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Test index scheduled response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/index")
    Response testIndexScheduled(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test set stato pagamento istanza response.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/stato-pagamento/{idIstanza}")
    Response testSetStatoPagamentoIstanza(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test trace evento istanza response.
     *
     * @param idIstanza       the id istanza
     * @param codEvento       the cod evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/trace-evento/{idIstanza}/{codEvento}")
    Response testTraceEventoIstanza(@PathParam("idIstanza") Long idIstanza, @PathParam("codEvento") String codEvento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test chiamata cosmo response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/cosmo")
    Response testChiamataCosmo(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test chiamata cosmo response.
     *
     * @param uuidIndex       the uuid index
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/link/{uuidIndex}")
    Response testLinkIndex(@PathParam("uuidIndex") String uuidIndex, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test loc csi response.
     *
     * @param query           the query
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/loccsi")
    Response testLocCSI(@QueryParam("q") String query, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test vincolo idrogeologico response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/idro")
    Response testVincoloIdrogeologico(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Test upload response.
     *
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/upload")
    Response testUpload(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Gets profilo app.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the profilo app
     * @throws GenericException the generic exception
     */
    @GET
    @Path("/profilo/{idIstanza}")
    Response getProfiloApp(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException;

    /**
     * Gets json data.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the json data
     * @throws GenericException the generic exception
     * @throws ParseException   the parse exception
     */
    @GET
    @Path("/json-data/{idIstanza}")
    Response getJsonData(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest) throws GenericException, ParseException;

    /**
     * Gets csv.
     *
     * @param idIstanza       the id istanza
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the csv
     */
    @GET
    @Path("/csv/{idIstanza}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response getCSV(@PathParam("idIstanza") Long idIstanza, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Test get tipo notifica evento response.
     *
     * @param codTipoEvento   the cod tipo evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return response response
     */
    @GET
    @Path("/test/notifica/{codTipoEvento}")
    Response testGetTipoNotificaEvento(@PathParam("codTipoEvento") String codTipoEvento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
                                       @Context HttpServletRequest httpRequest);

    /**
     * Test notifica response.
     *
     * @param idIstanza       the id istanza
     * @param codEvento       the cod evento
     * @param securityContext the security context
     * @param httpHeaders     the http headers
     * @param httpRequest     the http request
     * @return the response
     */
    @GET
    @Path("/notifica")
    @Produces(MediaType.APPLICATION_JSON)
    Response testNotifica(@QueryParam("idIstanza") Long idIstanza, @QueryParam("evento") String codEvento, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Create notifiche from conf response.
     *
     * @param listConfigNotifica the list config notifica
     * @param idIstanza          the id istanza
     * @param codCanaleNotifica  the cod canale notifica
     * @param rifCanaleNotifica  the rif canale notifica
     * @param cfAttoreInLinea    the cf attore in linea
     * @param securityContext    the security context
     * @param httpHeaders        the http headers
     * @param httpRequest        the http request
     * @return the response
     */
    @POST
    @Path("/notifica/genera")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response createNotificheFromConf(@RequestBody List<ConfigurazioneNotificaExtendedDTO> listConfigNotifica,
                                     @QueryParam("idIstanza") Long idIstanza,
                                     @QueryParam("codCanaleNotifica") String codCanaleNotifica,
                                     @QueryParam("rifCanaleNotifica") String rifCanaleNotifica,
                                     @QueryParam("cfAttoreInLinea") String cfAttoreInLinea,
                                     @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


    /**
     * Manager notifiche response.
     *
     * @param idIstanza         the id istanza
     * @param codTipoevento     the cod tipoevento
     * @param codCanaleNotifica the cod canale notifica
     * @param rifCanaleNotifica the rif canale notifica
     * @param dataIntegrazione  the data integrazione
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return response response
     */
    @GET
    @Path("/notifica/manager")
    Response managerNotifiche(@QueryParam("idIstanza") Long idIstanza,
                              @QueryParam("codTipoevento") String codTipoevento,
                              @QueryParam("codCanaleNotifica") String codCanaleNotifica,
                              @QueryParam("rifCanaleNotifica") String rifCanaleNotifica,
                              @QueryParam("uidRichiesta") String uidRichiesta,
                              @QueryParam("desTipoRichiesta") String desTipoRichiesta,
                              @QueryParam("dataIntegrazione") Date dataIntegrazione,
                              @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);

    /**
     * Manager notifiche response.
     *
     * @param codiciIstatComuni the codici istat comuni
     * @param securityContext   the security context
     * @param httpHeaders       the http headers
     * @param httpRequest       the http request
     * @return the response
     */
    @GET
    @Path("/aree-protette")
    Response getAreeProtette(@QueryParam("cod_istat") String codiciIstatComuni,
                             @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest httpRequest);


}