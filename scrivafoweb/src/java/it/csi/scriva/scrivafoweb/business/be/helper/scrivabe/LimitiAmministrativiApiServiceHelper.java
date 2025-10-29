/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivafoweb.business.be.helper.scrivabe;

import it.csi.scriva.scrivafoweb.business.be.exception.GenericException;




import it.csi.scriva.scrivafoweb.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ErrorDTO;

import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.NazioneDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivafoweb.business.be.helper.scrivabe.dto.RegioneExtendedDTO;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.sql.Date;

import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The type Limiti amministrativi api service helper.
 *
 * @author CSI PIEMONTE
 */
public class LimitiAmministrativiApiServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();

    private static final String ENDPOINT_CLASS = "/limiti-amministrativi";

    /**
     * Instantiates a new Limiti amministrativi api service helper.
     *
     * @param hostname     the hostname
     * @param endpointBase the endpoint base
     */
    public LimitiAmministrativiApiServiceHelper(String hostname, String endpointBase) {
        this.hostname = hostname;
        this.endpointBase = hostname + endpointBase + ENDPOINT_CLASS;
    }

    /**
     * Gets nazioni.
     *
     * @param requestHeaders the request headers
     * @param codIstat       the cod istat
     * @return the nazioni
     * @throws GenericException the generic exception
     */
    public List<NazioneDTO> getNazioni(MultivaluedMap<String, Object> requestHeaders, String codIstat) throws GenericException {
        logBegin(className);
        List<NazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/nazioni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets nazioni attive.
     *
     * @param requestHeaders the request headers
     * @return the nazioni attive
     * @throws GenericException the generic exception
     */
    public List<NazioneDTO> getNazioniAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<NazioneDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/nazioni/attive";
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets regioni.
     *
     * @param requestHeaders the request headers
     * @return the regioni
     * @throws GenericException the generic exception
     */
    public List<RegioneExtendedDTO> getRegioni(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatNazione) throws GenericException {
        logBegin(className);
        List<RegioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/regioni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_nazione", codIstatNazione);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets regioni attive.
     *
     * @param requestHeaders the request headers
     * @return the regioni attive
     * @throws GenericException the generic exception
     */
    public List<RegioneExtendedDTO> getRegioniAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<RegioneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/regioni/attive";
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets province.
     *
     * @param requestHeaders the request headers
     * @return the province
     * @throws GenericException the generic exception
     */
    public List<ProvinciaExtendedDTO> getProvince(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatRegione, Long idAdempimento) throws GenericException {
        logBegin(className);
        List<ProvinciaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_regione", codIstatRegione);
            queryParameters = buildQueryParameters(queryParameters, "id_adempimento", idAdempimento);
            return setGetResult(className, targetUrl + queryParameters, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets province attive.
     *
     * @param requestHeaders the request headers
     * @return the province attive
     * @throws GenericException the generic exception
     */
    public List<ProvinciaExtendedDTO> getProvinceAttive(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<ProvinciaExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/province/attive";
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets comuni.
     *
     * @param requestHeaders the request headers
     * @return the comuni
     * @throws GenericException the generic exception
     */
    public List<ComuneExtendedDTO> getComuni(MultivaluedMap<String, Object> requestHeaders, String codIstat, String codIstatProvincia, String attiva) throws GenericException {
        logBegin(className);
        List<ComuneExtendedDTO> resultFiltered = new ArrayList<>();
        String targetUrl = this.endpointBase + "/comuni";
        String queryParameters = "";
        try {
            queryParameters = buildQueryParameters(queryParameters, "cod_istat", codIstat);
            queryParameters = buildQueryParameters(queryParameters, "cod_istat_provincia", codIstatProvincia);
            if (attiva != null) {
                // Ottieni la risposta JSON come stringa
                String jsonResponse = getJsonResponse(targetUrl + queryParameters, requestHeaders);
                // Parsea la risposta JSON
                JSONParser parser = new JSONParser();
                JSONArray jsonArray = (JSONArray) parser.parse(jsonResponse);

                for (Object obj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) obj;
                    ComuneExtendedDTO comune = new ComuneExtendedDTO();
                    comune.setIdComune((Long) jsonObject.get("id_comune"));
                    comune.setCodIstatComune((String) jsonObject.get("cod_istat_comune")); 
                    comune.setCodBelfioreComune((String) jsonObject.get("cod_belfiore_comune"));
                    comune.setDenomComune((String) jsonObject.get("denom_comune"));             
                    comune.setIdProvincia((Long) jsonObject.get("id_provincia"));
                    

                    if(jsonObject.get("data_inizio_validita") != null) {
                    	Date dataInVal = null;
						try {
							dataInVal = Date.valueOf((String) jsonObject.get("data_inizio_validita"));
							comune.setDataInizioValidita(dataInVal);
						} catch (Exception e) {

						}
                    	
                    }
                    
                    if(jsonObject.get("data_fine_validita") != null) {
                    	Date dataFineVal = null;
						try {
							dataFineVal = Date.valueOf((String) jsonObject.get("data_fine_validita"));
							comune.setDataFineValidita(dataFineVal);
						} catch (Exception e) {

							
						}
                    	
                    }

                    comune.setDtIdComune((Long) jsonObject.get("dt_id_comune"));
                    comune.setDtIdComunePrev((Long) jsonObject.get("dt_id_comune_prev"));
                    comune.setDtIdComuneNext((Long) jsonObject.get("dt_id_comune_next"));
                    comune.setCapComune((String) jsonObject.get("cap_comune"));

                    JSONObject provinciaJson = (JSONObject) jsonObject.get("provincia");
                    if (provinciaJson != null) {
                        ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
                        provincia.setCodProvincia((String) provinciaJson.get("cod_provincia"));
                       
                        if(jsonObject.get("data_inizio_validita") != null) {
                        	Date dataInVal = null;
    						try {
    							dataInVal = Date.valueOf((String) provinciaJson.get("data_inizio_validita"));
    							provincia.setDataInizioValidita(dataInVal);
    						} catch (Exception e) {

    						}
                        	
                        }
                        
                        if(jsonObject.get("data_fine_validita") != null) {
                        	Date dataFineVal = null;
    						try {
    							dataFineVal = Date.valueOf((String) provinciaJson.get("data_fine_validita"));
    							provincia.setDataFineValidita(dataFineVal);
    						} catch (Exception e) {

    							
    						}
                        	
                        }
                        
                        provincia.setDenomProvincia((String) provinciaJson.get("denom_provincia"));
                        provincia.setFlgLimitrofa((Boolean) provinciaJson.get("flg_limitrofa"));
                        provincia.setIdProvincia((Long) provinciaJson.get("id_provincia"));
                        provincia.setIdRegione((Long) provinciaJson.get("id_regione"));
                        provincia.setOrdinamentoAdempiProvincia((Integer) provinciaJson.get("ordinamento_adempi_provincia"));
                        provincia.setSiglaProvincia((String) provinciaJson.get("sigla_provincia"));
                        
                        JSONObject regioneJson = (JSONObject) provinciaJson.get("regione");
                        if (regioneJson != null) {
                        	RegioneExtendedDTO regione = new RegioneExtendedDTO();
                        	regione.setCodRegione((String) regioneJson.get("cod_regione"));
                        	
                            if(jsonObject.get("data_inizio_validita") != null) {
                            	Date dataInVal = null;
        						try {
        							dataInVal = Date.valueOf((String) regioneJson.get("data_inizio_validita"));
        							regione.setDataInizioValidita(dataInVal);
        						} catch (Exception e) {

        						}
                            	
                            }
                            
                            if(jsonObject.get("data_fine_validita") != null) {
                            	Date dataFineVal = null;
        						try {
        							dataFineVal = Date.valueOf((String) regioneJson.get("data_fine_validita"));
        							regione.setDataFineValidita(dataFineVal);
        						} catch (Exception e) {

        							
        						}
                            	
                            }
                        	regione.setDenomRegione((String) regioneJson.get("denom_regione"));
                        	regione.setIdNazione((Long) regioneJson.get("id_nazione"));
                        	regione.setIdRegione((Long) regioneJson.get("id_regione"));
                        	
                        	JSONObject nazioneJson = (JSONObject) regioneJson.get("nazione");
                        	if (nazioneJson != null) {
                        		NazioneDTO nazione = new NazioneDTO();
                        		nazione.setCodBelfioreNazione((String) nazioneJson.get("cod_belfiore_nazione"));
                        		nazione.setCodIso2((String) nazioneJson.get("cod_iso2"));
                        		nazione.setCodIstatNazione((String) nazioneJson.get("cod_istat_nazione"));
                                if(jsonObject.get("data_inizio_validita") != null) {
                                	Date dataInVal = null;
            						try {
            							dataInVal = Date.valueOf((String) nazioneJson.get("data_inizio_validita"));
            							nazione.setDataInizioValidita(dataInVal);
            						} catch (Exception e) {

            						}
                                	
                                }
                                
                                if(jsonObject.get("data_fine_validita") != null) {
                                	Date dataFineVal = null;
            						try {
            							dataFineVal = Date.valueOf((String) nazioneJson.get("data_fine_validita"));
            							nazione.setDataFineValidita(dataFineVal);
            						} catch (Exception e) {

            							
            						}
                                	
                                }
                        		nazione.setDenomNazione((String) nazioneJson.get("denom_nazione"));
                        		nazione.setDtIdStato((Long) nazioneJson.get("dt_id_stato"));
                        		nazione.setDtIdStatoNext((Long) nazioneJson.get("dt_id_stato_next"));
                        		nazione.setDtIdStatoPrev((Long) nazioneJson.get("dt_id_stato_prev"));
                        		nazione.setIdNazione((Long) nazioneJson.get("id_nazione"));
                        		nazione.setIdOrigine((Long) nazioneJson.get("id_origine"));
                        		
                        		regione.setNazione(nazione);
                        	}
                        	
                        	provincia.setRegione(regione);
                        }
                  
                        comune.setProvincia(provincia);
                    }

                    if ((comune.getDataFineValidita() == null && comune.getProvincia().getDataFineValidita() == null &&
                    		comune.getProvincia().getRegione().getDataFineValidita() == null &&
                    		comune.getProvincia().getRegione().getNazione().getDataFineValidita() == null)) {
                        resultFiltered.add(comune);
                    }
                }

                return resultFiltered;
            } else {
                return setGetResult(className, targetUrl + queryParameters, requestHeaders, resultFiltered);
            }
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } catch (ParseException e) {
            logError(className, e);
            throw new GenericException("Errore nella deserializzazione della risposta JSON");
        } finally {
            logEnd(className);
        }
    }

    private String getJsonResponse(String url, MultivaluedMap<String, Object> requestHeaders) throws ProcessingException {
        Response resp = getInvocationBuilder(url, requestHeaders).get();
        if (resp.getStatus() >= 400) {
            ErrorDTO err = getError(resp);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + url + " ]");
        }
        return resp.readEntity(String.class);
    }


    /**
     * Gets comuni attivi.
     *
     * @param requestHeaders the request headers
     * @return the comuni attivi
     * @throws GenericException the generic exception
     */
    public List<ComuneExtendedDTO> getComuniAttivi(MultivaluedMap<String, Object> requestHeaders) throws GenericException {
        logBegin(className);
        List<ComuneExtendedDTO> result = new ArrayList<>();
        String targetUrl = this.endpointBase + "/comuni/attivi";
        try {
            return setGetResult(className, targetUrl, requestHeaders, result);
        } catch (ProcessingException e) {
            logError(className, e);
            throw new ProcessingException(ERRORE_NELLA_CHIAMATA_AL_SERVIZIO + targetUrl + " ]");
        } finally {
            logEnd(className);
        }
    }

}