/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.util.manager;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CatastoUbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaAllegatoDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.QuadroDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ReferenteIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.SoggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.CatastoUbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.CategoriaAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.ErrorDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ReferenteIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.SoggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.NumeroComuniOggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.NumeroOperaEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.NumeroReferenteEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.NumeroSoggettoEnum;
import it.csi.scriva.scrivabesrv.dto.enumeration.TipoSoggettoEnum;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Mandatory info istanza manager.
 *
 * @author CSI PIEMONTE
 */
@Component
public class MandatoryInfoIstanzaManager {

    /**
     * The constant BEGIN.
     */
    public static final String BEGIN = "BEGIN";
    /**
     * The constant EXCEPTION.
     */
    public static final String EXCEPTION = "Exception : ";
    /**
     * The constant IND_TIPO_SOGGETTO_STR.
     */
    public static final String IND_TIPO_SOGGETTO_STR = "__IndTipoSoggetto__";
    /**
     * The constant OGGETTI_EXCEPTION_PATH_NOT_FOUND_EXCEPTION.
     */
    public static final String OGGETTI_EXCEPTION_PATH_NOT_FOUND_EXCEPTION = "[MandatoryInfoIstanzaManager::checkOggetti] EXCEPTION PathNotFoundException : ";
    /**
     * The constant PH_DES_TIPO_QUADRO.
     */
    public static final String PH_DES_TIPO_QUADRO = "{PH_DES_TIPO_QUADRO}";
    public static final String PROGETTO = "PROGETTO";
    /**
     * The constant LOGGER.
     */
    static Logger logger = Logger.getLogger(Constants.LOGGER_NAME + ".business");
    /**
     * The constant INFO.
     */
    static final String INFO = "ADEMPIMENTO";
    /**
     * The constant IND_TIPO_SOGGETTO.
     */
    static final String IND_TIPO_SOGGETTO = "IndTipoSoggetto";
    /**
     * The constant IND_NUM_SOGGETTO.
     */
    static final String IND_NUM_SOGGETTO = "IndNumSoggetto";
    /**
     * The constant IND_NUM_REFERENTE.
     */
    static final String IND_NUM_REFERENTE = "IndNumReferente";
    /**
     * The constant IND_NUM_OGGETTO.
     */
    static final String IND_NUM_OGGETTO = "IndNumOggetto";
    /**
     * The constant IND_GEO_MODE.
     */
    static final String IND_GEO_MODE = "IndGeoMode";
    /**
     * The constant IND_NUM_COMUNI_OGGETTO.
     */
    static final String IND_NUM_COMUNI_OGGETTO = "IndNumComuniOggetto";

    /**
     * The Mandatory dati catastali.
     */
    static final String DATI_CATASTALI_MANDATORY = "DatiCatastaliObbligatori";

    /**
     * The constant CAT_ALLEGATI_MANDATORY.
     */
    static final String CAT_ALLEGATI_MANDATORY = "AllegatiObbligatori";
    /**
     * The constant CAT_ALLEGATI_WRONG.
     */
    static final String CAT_ALLEGATI_WRONG = "AllegatiErrati";

    /**
     * The constant QDR_SOGGETTO_KEY.
     */
// JSON KEYS
    static final String QDR_SOGGETTO_KEY = "QDR_SOGGETTO";
    /**
     * The constant QDR_OGGETTO_KEY.
     */
    static final String QDR_OGGETTO_KEY = "QDR_OGGETTO";
    /**
     * The constant SOGGETTI_ISTANZA_KEY.
     */
    static final String SOGGETTI_ISTANZA_KEY = "soggettiIstanza";
    /**
     * The constant TIPO_SOGGETTO_KEY.
     */
    static final String TIPO_SOGGETTO_KEY = "tipo_soggetto";
    /**
     * The constant COD_TIPO_SOGGETTO_KEY.
     */
    static final String COD_TIPO_SOGGETTO_KEY = "cod_tipo_soggetto";
    /**
     * The constant CONFIG_PATH.
     */
// JSON PATH
    static final String CONFIG_PATH = "$.QDR_CONFIG";
    /**
     * The constant COD_TIPO_SOGGETTO_PATH_NOT_EQUAL.
     */
    static final String COD_TIPO_SOGGETTO_PATH_NOT_EQUAL = "$.QDR_SOGGETTO.soggettiIstanza[*][*].tipo_soggetto[?(@.cod_tipo_soggetto!='__IndTipoSoggetto__')].cod_tipo_soggetto";
    /**
     * The constant COD_TIPO_SOGGETTO_PATH_EQUAL.
     */
    static final String COD_TIPO_SOGGETTO_PATH_EQUAL = "$.QDR_SOGGETTO.soggettiIstanza[*][*].tipo_soggetto[?(@.cod_tipo_soggetto=='__IndTipoSoggetto__')].cod_tipo_soggetto";
    /**
     * The constant SOGGETTI_ISTANZA_PATH.
     */
    static final String SOGGETTI_ISTANZA_PATH = "$.QDR_SOGGETTO.soggettiIstanza[*][*]";
    /**
     * The constant SOGGETTI_ISTANZA_PRINCIPALE_PATH.
     */
    static final String SOGGETTI_ISTANZA_PRINCIPALE_PATH = "$.QDR_SOGGETTO.soggettiIstanza[*][*].[?(!@.id_soggetto_padre)]";
    /**
     * The constant REFERENTI_ISTANZA_PATH.
     */
    static final String REFERENTI_ISTANZA_PATH = "$.QDR_SOGGETTO.referenti[*]";
    /**
     * The constant OGGETTI_ISTANZA_PATH.
     */
    static final String OGGETTI_ISTANZA_PATH = "$.QDR_OGGETTO.oggettiIstanza[*]";
    /**
     * The constant OGGETTI_ISTANZA_GEOREF_PATH.
     */
    static final String OGGETTI_ISTANZA_GEOREF_PATH = "$.QDR_OGGETTO.oggettiIstanza[?(@.ind_geo_stato!='__IndGeoStato__')]";
    /**
     * The constant COMUNI_OGGETTI_ISTANZA_PATH.
     */
    static final String COMUNI_OGGETTI_ISTANZA_PATH = "$.QDR_OGGETTO.oggettiIstanza[__index__].ubicazione_oggetto[*]";

    /**
     * The constant ERR_NOT_VALID.
     */
// ERRORS
    static final String ERR_NOT_VALID = "Dati non validi";
    
    /**
     * The constant E2GIS.
     */
    static final String E2GIS = "E2GIS";
    
    /**
     * The constant ERR_IND_GEO_MODE.
     */
    static final String ERR_IND_GEO_MODE = "Attenzione: non e' stata eseguita la georeferenziazione che permette di usufruire di alcune compilazioni e controlli automatici: torna al passo di riferimento e seleziona il pulsante GEOREFERISCI";

    @Autowired
    private AdempimentoConfigDAO adempimentoConfigDAO;

    @Autowired
    private CategoriaAllegatoDAO categoriaAllegatoDAO;

    @Autowired
    private IstanzaDAO istanzaDAO;

    @Autowired
    private OggettoIstanzaDAO oggettoIstanzaDAO;

    @Autowired
    private ReferenteIstanzaDAO referenteIstanzaDAO;

    @Autowired
    private SoggettoIstanzaDAO soggettoIstanzaDAO;

    @Autowired
    private UbicazioneOggettoIstanzaDAO ubicazioneOggettoIstanzaDAO;

    @Autowired
    private CatastoUbicazioneOggettoIstanzaDAO catastoUbicazioneOggettoIstanzaDAO;

    @Autowired
    private ErrorManager errorManager;
    
    @Autowired
    private QuadroDAO quadroDAO;

    /**
     * Check istanza error dto.
     *
     * @param codAdempimento codAdempimento
     * @param idIstanza      idIStanza
     * @return ErrorDTO error dto
     */
    public ErrorDTO checkIstanza(String codAdempimento, Long idIstanza) {
        logger.debug("[MandatoryInfoIstanzaManager::checkIstanza] BEGIN");
        ErrorDTO error = null;
        List<IstanzaExtendedDTO> istanza = istanzaDAO.loadIstanza(idIstanza);
        if (istanza != null && !istanza.isEmpty())
            switch (codAdempimento.toUpperCase()) {
                case "VAL":
                    error = this.checkIstanzaVIAVALDb(istanza.get(0));
                    break;
                case "CONS":
                case "OTT":
                case "PRO":
                case "SCO":
                case "VER":
                case "VPR":
                    error = this.checkIstanzaVIADb(istanza.get(0));
                    break;
                case "AUA":
                    break;
                case "VINCA":
                    break;
                case "SCR":
                    error = this.checkIstanzaVIAVALDb(istanza.get(0));
                    break;
                case "APP":
                    error = this.checkIstanzaVIAVALDb(istanza.get(0));
                    break;
                default:
                    break;
            }
        logger.debug("[MandatoryInfoIstanzaManager::checkIstanza] END");
        return error;
    }

    /**
     * Check istanza via db error dto.
     *
     * @param istanza the istanza
     * @return the error dto
     */
    public ErrorDTO checkIstanzaVIADb(IstanzaExtendedDTO istanza) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkIstanzaVIADb] ";
        logger.debug(methodInfo + BEGIN);
        String desTipoQuadro = "";
        ErrorDTO error = null;
        Map<String, String> errors = new HashMap<>();
        String jsonData = istanza.getJsonData();
        Map<String, Object> config = JsonPath.read(jsonData, CONFIG_PATH);
        
        //Label quadro soggetti
        List<QuadroExtendedDTO> quadroDtSogg = quadroDAO.loadQuadroByCodeTipoQuadroAndIstanza(QDR_SOGGETTO_KEY, istanza.getIdIstanza());
        String jsonStringSogg = quadroDtSogg.get(0).getJsonConfiguraQuadro();
        
        String labelSoggetti = null;

		JSONObject jsonObjectSogg = new JSONObject(jsonStringSogg);
			
		labelSoggetti = (String) jsonObjectSogg.getString("label");
		
		//Label quadro oggetto
		List<QuadroExtendedDTO> quadroDtOgg = quadroDAO.loadQuadroByCodeTipoQuadroAndIstanza(QDR_OGGETTO_KEY, istanza.getIdIstanza());
		//String jsonStringOgg = new String();
		//for(int i=0; i<quadroDtOgg.size(); i++) {
		//	if(quadroDtOgg.get(i).getDesQuadro().equals("Oggetto"))
	    String	jsonStringOgg = quadroDtOgg.get(0).getJsonConfiguraQuadro();
				
		//}
       
        
        String labelOggetto = null;

		JSONObject jsonObjectOgg = new JSONObject(jsonStringOgg);
			
		labelOggetto = (String) jsonObjectOgg.getString("label");

        
        if (StringUtils.isNotBlank(jsonData)) {
            Map<String, String> errorsSoggetti = this.checkSoggettiDb(istanza.getIdIstanza(), config);
            desTipoQuadro = errorsSoggetti.size() > 0 ? labelSoggetti : ""; //"SOGGETTI" : "";
            Map<String, String> errorsOggetti = this.checkOggettiDb(istanza.getIdIstanza(), config);
            if(errorsOggetti.containsKey(E2GIS)) {
            	error = errorManager.getError("400", E2GIS, ERR_IND_GEO_MODE, null, null);
            	return error;
            }
            	
            
            //desTipoQuadro = errorsOggetti.size() > 0 ? ("".equals(desTipoQuadro) ? "" : desTipoQuadro + ", PROGETTO") : desTipoQuadro;
            if(errorsOggetti.size() > 0 && !desTipoQuadro.isEmpty())
            	desTipoQuadro = desTipoQuadro.concat(", " + labelOggetto); //PROGETTO);
            else if(desTipoQuadro.equals("") || desTipoQuadro == null)
            	desTipoQuadro = labelOggetto;  //PROGETTO;
            errors.putAll(errorsSoggetti);
            errors.putAll(errorsOggetti);
            
        }

        if (!errors.isEmpty()) {
            error = errorManager.getError("400", "A006", ERR_NOT_VALID, errors, null);
            error.setTitle(error.getTitle().replace(PH_DES_TIPO_QUADRO, desTipoQuadro));
        }
        logger.debug(methodInfo + "END");
        return error;
    }

    /**
     * Check istanza viaval db error dto.
     *
     * @param istanza the istanza
     * @return the error dto
     */
    public ErrorDTO checkIstanzaVIAVALDb(IstanzaExtendedDTO istanza) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkIstanzaVIAVALDb] ";
        logger.debug(methodInfo + BEGIN);
        ErrorDTO error = this.checkIstanzaVIADb(istanza);
        if(error != null) {
	        if(error.getCode().equals(E2GIS)) {
	            logger.debug(methodInfo + "END");
	            return error;
	        }
        }

        //Label quadro oggetto
  		List<QuadroExtendedDTO> quadroDtOgg = quadroDAO.loadQuadroByCodeTipoQuadroAndIstanza(QDR_OGGETTO_KEY, istanza.getIdIstanza());

  	    String	jsonStringOgg = quadroDtOgg.get(0).getJsonConfiguraQuadro();

        String labelOggetto = null;

  		JSONObject jsonObjectOgg = new JSONObject(jsonStringOgg);
  			
  		labelOggetto = (String) jsonObjectOgg.getString("label");
        
        Map<String, String> errors = error != null ? error.getDetail() : new HashMap<>();
        //errors.putAll(this.checkMandatoryDatiCatastaliDb(istanza.getIdIstanza()));
        if (!errors.isEmpty()) {
            String oldTitle = error != null ? error.getTitle() : null;
            error = errorManager.getError("400", "A006", ERR_NOT_VALID, errors, null);
//            if (StringUtils.isNotBlank(oldTitle)) {
//                error.setTitle(oldTitle.contains(PROGETTO) ? oldTitle : oldTitle + ", PROGETTO"); // error.getTitle().replace(PH_DES_TIPO_QUADRO, PROGETTO));
//            } else {
//                error.setTitle(error.getTitle().replace(PH_DES_TIPO_QUADRO, PROGETTO));
//            }
            if (!StringUtils.isNotBlank(oldTitle)) {
            	error.setTitle(error.getTitle().replace(PH_DES_TIPO_QUADRO, labelOggetto)); //PROGETTO));
            } else {
            	error.setTitle(oldTitle);
            }
        }
        logger.debug(methodInfo + "END");
        return error;
    }

    /**
     * Check soggetti db map.
     *
     * @param idIstanza the id istanza
     * @param config    the config
     * @return the map
     */
    public Map<String, String> checkSoggettiDb(Long idIstanza, Map<String, Object> config) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkSoggettiDb] ";
        logger.debug(methodInfo + BEGIN);
        Map<String, String> errorDetails = new HashMap<>();
        String indTipoSoggetto = TipoSoggettoEnum.PF.name();
        String indNumSoggetto = NumeroSoggettoEnum.M.name();
        String indNumReferente = NumeroReferenteEnum.N.name();
        if (config != null && !config.isEmpty()) {
            indTipoSoggetto = config.get(IND_TIPO_SOGGETTO).toString();
            logger.debug(methodInfo + IND_TIPO_SOGGETTO + " : [" + indTipoSoggetto + "]");

            indNumSoggetto = config.get(IND_NUM_SOGGETTO).toString();
            logger.debug(methodInfo + IND_NUM_SOGGETTO + " : [" + indNumSoggetto + "]");

            indNumReferente = config.get(IND_NUM_REFERENTE).toString();
            logger.debug(methodInfo + IND_NUM_REFERENTE + " : [" + indNumReferente + "]");
        }
        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList = soggettoIstanzaDAO.loadSoggettoIstanzaByIdIstanza(idIstanza);
        checkTipoSoggettoDb(soggettoIstanzaList, errorDetails, indTipoSoggetto);
        checkNumSoggettoDb(soggettoIstanzaList, errorDetails, indTipoSoggetto, indNumSoggetto);
        checkNumReferenteDb(idIstanza, errorDetails, indNumReferente);
        logger.debug(methodInfo + "END");
        return errorDetails;
    }

    /**
     * Check tipo soggetto db.
     *
     * @param soggettoIstanzaList the soggetto istanza list
     * @param errorDetails        the error details
     * @param indTipoSoggetto     the ind tipo soggetto
     */
    public void checkTipoSoggettoDb(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList, Map<String, String> errorDetails, String indTipoSoggetto) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkTipoSoggettoDb] ";
        logger.debug(methodInfo + BEGIN);
        try {
            if (StringUtils.isNotBlank(indTipoSoggetto)) {
                if (soggettoIstanzaList != null && !soggettoIstanzaList.isEmpty()) {
                    String[] tipiSoggetto = indTipoSoggetto.split("_");
                    int errors = 0;
                    for (String tipoSoggetto : tipiSoggetto) {
                        List<SoggettoIstanzaExtendedDTO> soggettoIstanzaFilterList = soggettoIstanzaList.stream()
                                .filter(s -> tipoSoggetto.equalsIgnoreCase(s.getTipoSoggetto().getCodiceTipoSoggetto()))
                                .collect(Collectors.toList());
                        if (soggettoIstanzaFilterList.isEmpty()) {
                            ++errors;
                        }
                    }
                    if (tipiSoggetto.length == errors) {
                        errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
                    }
                } else {
                    errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID + " (istanza)");
                }
            }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }

    /**
     * Check num soggetto db.
     *
     * @param soggettoIstanzaList the soggetto istanza list
     * @param errorDetails        the error details
     * @param indTipoSoggetto     the ind tipo soggetto
     * @param indNumSoggetto      the ind num soggetto
     */
    public void checkNumSoggettoDb(List<SoggettoIstanzaExtendedDTO> soggettoIstanzaList, Map<String, String> errorDetails, String indTipoSoggetto, String indNumSoggetto) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkNumSoggettoDb] ";
        logger.debug(methodInfo + BEGIN);
        try {
            if (StringUtils.isNotBlank(indNumSoggetto) && soggettoIstanzaList != null) {
                if ("M".equals(indNumSoggetto)) {
                    if ((soggettoIstanzaList.isEmpty() || soggettoIstanzaList.size() > NumeroSoggettoEnum.getValue(indNumSoggetto))) {
                        errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
                    }
                } else {

                    if ((indTipoSoggetto.contains("PG") || indTipoSoggetto.contains("PB"))
                            &&
                            soggettoIstanzaList.size() > NumeroSoggettoEnum.getValue(indNumSoggetto) + 1) {
                        errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }

    /**
     * Check num referente db.
     *
     * @param idIstanza       the id istanza
     * @param errorDetails    the error details
     * @param indNumReferente the ind num referente
     */
    public void checkNumReferenteDb(Long idIstanza, Map<String, String> errorDetails, String indNumReferente) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkNumReferenteDb] ";
        logger.debug(methodInfo + BEGIN);
        try {
            List<ReferenteIstanzaDTO> referenteIstanzaList = referenteIstanzaDAO.loadReferentiIstanzaByIdIstanza(idIstanza);
            if (StringUtils.isNotBlank(indNumReferente) && referenteIstanzaList != null) {
                if ("M".equals(indNumReferente) || "S".equals(indNumReferente)) { //VERIFICARE
                    if (referenteIstanzaList.isEmpty() || referenteIstanzaList.size() > NumeroReferenteEnum.getValue(indNumReferente)) {
                        errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
                    }
                } else {
                    if (!"O".equalsIgnoreCase(indNumReferente) && !"MO".equalsIgnoreCase(indNumReferente)) {
                        if (referenteIstanzaList.size() != NumeroReferenteEnum.getValue(indNumReferente)) {
                            errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }

    /**
     * Check oggetti db map.
     *
     * @param idIstanza the id istanza
     * @param config    the config
     * @return the map
     */
    public Map<String, String> checkOggettiDb(Long idIstanza, Map<String, Object> config) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkOggettiDb] ";
        logger.debug(methodInfo + BEGIN);
        Map<String, String> errorDetails = new HashMap<>();
        String indGeoMode = "M";
        String indNumOggetto = "M";
        String indNumComuniOggetto = "M";
        if (config != null && !config.isEmpty()) {
            indGeoMode = config.get(IND_GEO_MODE).toString();
            logger.debug(methodInfo + IND_GEO_MODE + " : [" + indGeoMode + "]");

            indNumOggetto = config.get(IND_NUM_OGGETTO).toString();
            logger.debug(methodInfo + IND_NUM_OGGETTO + " : [" + indNumOggetto + "]");

            indNumComuniOggetto = config.get(IND_NUM_COMUNI_OGGETTO).toString();
            logger.debug(methodInfo + IND_NUM_COMUNI_OGGETTO + " : [" + indNumComuniOggetto + "]");
        }

        List<OggettoIstanzaExtendedDTO> oggettoIstanzaList = oggettoIstanzaDAO.loadOggettoIstanzaByIdIstanza(idIstanza);
        checkGeoModeDb(oggettoIstanzaList, errorDetails, indGeoMode);
        checkNumOggettoDb(oggettoIstanzaList, errorDetails, indNumOggetto);
        checkNumComuniOggettoDb(oggettoIstanzaList, errorDetails, indNumComuniOggetto);
        logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] END");
        return errorDetails;
    }

    /**
     * Check geo mode db.
     *
     * @param oggettoIstanzaList the oggetto istanza list
     * @param errorDetails       the error details
     * @param indGeoMode         the ind geo mode
     */
    private void checkGeoModeDb(List<OggettoIstanzaExtendedDTO> oggettoIstanzaList, Map<String, String> errorDetails, String indGeoMode) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkGeoModeDb] ";
        logger.debug(methodInfo + BEGIN);
        List<OggettoIstanzaExtendedDTO> oggettoIstanzaGeorefList = new ArrayList<OggettoIstanzaExtendedDTO>();
        try {
            // Check IndGeoMode
        	if(!oggettoIstanzaList.isEmpty()) {
            if (StringUtils.isNotBlank(indGeoMode) && oggettoIstanzaList != null) {
                switch (indGeoMode) {
                    case "N": // Georeferenziazione non richiesta
                        break;
                    case "O": // Georeferenziazione richiesta ma opzionale
                        break;
                    case "M":  // Georeferenziazione obbligatoria
                        oggettoIstanzaGeorefList = oggettoIstanzaList.stream()
                                .filter(o -> "G".equalsIgnoreCase(o.getIndGeoStato()))
                                .collect(Collectors.toList());
                        if (oggettoIstanzaGeorefList.isEmpty()) {
                            errorDetails.put(E2GIS, ERR_NOT_VALID);
                        }
                        break;
                    case "P":  // Georeferenziazione posticipabile
                        oggettoIstanzaGeorefList = oggettoIstanzaList.stream()
                                .filter(o -> "G".equalsIgnoreCase(o.getIndGeoStato()))
                                .collect(Collectors.toList());
                        if (oggettoIstanzaGeorefList.isEmpty()) {
                            errorDetails.put(E2GIS, ERR_NOT_VALID);
                        }
                        break;
                    case "A":  // Georeferenziazione opzionale con avviso
                        break;
                    default:
                        break;
                }
            }
        } else {
        	errorDetails.put(IND_GEO_MODE, ERR_NOT_VALID);
        }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_GEO_MODE, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }

    /**
     * Check num oggetto.
     *
     * @param oggettoIstanzaList the oggetto istanza list
     * @param errorDetails       the error details
     * @param indNumOggetto      the ind num oggetto
     */
    private void checkNumOggettoDb(List<OggettoIstanzaExtendedDTO> oggettoIstanzaList, Map<String, String> errorDetails, String indNumOggetto) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkNumOggettoDb] ";
        logger.debug(methodInfo + BEGIN);
        try {
            if (StringUtils.isNotBlank(indNumOggetto) && oggettoIstanzaList != null) {
                if (("S".equalsIgnoreCase(indNumOggetto) || "N".equalsIgnoreCase(indNumOggetto)) && oggettoIstanzaList.size() != NumeroOperaEnum.getValue(indNumOggetto)) {
                    errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                }
            }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }

    /**
     * Check num comuni oggetto.
     *
     * @param oggettoIstanzaList  the oggetto istanza list
     * @param errorDetails        the error details
     * @param indNumComuniOggetto the ind num comuni oggetto
     */
    public void checkNumComuniOggettoDb(List<OggettoIstanzaExtendedDTO> oggettoIstanzaList, Map<String, String> errorDetails, String indNumComuniOggetto) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkNumComuniOggettoDb] ";
        logger.debug(methodInfo + BEGIN);
        try {
            // Check IndNumComuniOggetto
            if (StringUtils.isNotBlank(indNumComuniOggetto) && oggettoIstanzaList != null) {
                for (OggettoIstanzaExtendedDTO oggettoIstanza : oggettoIstanzaList) {
                    List<UbicazioneOggettoIstanzaExtendedDTO> ubicazioniOggettoIstanzaList = ubicazioneOggettoIstanzaDAO.loadUbicazioneOggettoIstanzaByIdOggettoIstanza(oggettoIstanza.getIdOggettoIstanza());
                    if ("M".equalsIgnoreCase(indNumComuniOggetto)) {
                        if (ubicazioniOggettoIstanzaList == null || (ubicazioniOggettoIstanzaList.isEmpty() || ubicazioniOggettoIstanzaList.size() > NumeroComuniOggettoEnum.getValue(indNumComuniOggetto))) {
                            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                        }
                    } else {
                        if (ubicazioniOggettoIstanzaList != null && ubicazioniOggettoIstanzaList.size() != NumeroComuniOggettoEnum.getValue(indNumComuniOggetto)) {
                            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(methodInfo + EXCEPTION, e);
            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
        } finally {
            logger.debug(methodInfo + "END");
        }
    }


    /**
     * Check mandatory dati catastali db error dto.
     *
     * @param idIstanza the id istanza
     * @return the map
     */
    public Map<String, String> checkMandatoryDatiCatastaliDb(Long idIstanza) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkMandatoryDatiCatastaliDb] ";
        logger.debug(methodInfo + BEGIN);
        Map<String, String> errorDetails = new HashMap<>();
        List<CatastoUbicazioneOggettoIstanzaDTO> catastoUbicazioneOggettoIstanzaList = catastoUbicazioneOggettoIstanzaDAO.loadCatastoUbicazioneOggettoIstanza(null, null, null, idIstanza);
        if (CollectionUtils.isEmpty(catastoUbicazioneOggettoIstanzaList)) {
            errorDetails.put(DATI_CATASTALI_MANDATORY, ERR_NOT_VALID);
        }
        logger.debug(methodInfo + "END");
        return errorDetails;
    }

/******* OLD CHECK ISTANZA WITH JSON DATA *******/

    /**
     * Check istanza via error dto.
     *
     * @param istanza istanza
     * @return ErrorDTO error dto
     */
    public ErrorDTO checkIstanzaVIA(IstanzaExtendedDTO istanza) {
        String methodInfo = "[MandatoryInfoIstanzaManager::checkIstanzaVIA] ";
        logger.debug(methodInfo + BEGIN);
        String desTipoQuadro = "";
        ErrorDTO error = null;
        Map<String, String> errors = new HashMap<>();
        String jsonData = istanza.getJsonData();
        Map<String, Object> config = JsonPath.read(jsonData, CONFIG_PATH);
        if (StringUtils.isNotBlank(jsonData)) {
            Map<String, String> errorsSoggetti = this.checkSoggettiFromJsonData(jsonData, config);
            desTipoQuadro = errorsSoggetti.size() > 0 ? "SOGGETTI" : "";
            Map<String, String> errorsOggetti = this.checkOggetti(jsonData, config);
            desTipoQuadro = errorsOggetti.size() > 0 ? (StringUtils.isNotBlank(desTipoQuadro) ? desTipoQuadro + ", PROGETTO" : PROGETTO) : desTipoQuadro;
            errors.putAll(errorsSoggetti);
            errors.putAll(errorsOggetti);
        }

        if (!errors.isEmpty()) {
            error = errorManager.getError("400", "A006", ERR_NOT_VALID, errors, null);
            error.setTitle(error.getTitle().replace(PH_DES_TIPO_QUADRO, desTipoQuadro));
        }
        logger.debug(methodInfo + "END");
        return error;
    }

    /**
     * Check soggetti map.
     *
     * @param jsonData jsonData
     * @param config   config
     * @return Map<String, String>                       dettagli errori
     */
    public Map<String, String> checkSoggettiFromJsonData(String jsonData, Map<String, Object> config) {
        logger.debug("[MandatoryInfoIstanzaManager::checkSoggettiFromJsonData] BEGIN");
        Map<String, String> errorDetails = new HashMap<>();
        String indTipoSoggetto = TipoSoggettoEnum.PF.name();
        String indNumSoggetto = NumeroSoggettoEnum.M.name();
        String indNumReferente = NumeroReferenteEnum.N.name();
        if (config != null && !config.isEmpty()) {
            indTipoSoggetto = config.get(IND_TIPO_SOGGETTO).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkSoggettiFromJsonData] indTipoSoggetto : [" + indTipoSoggetto + "]");

            indNumSoggetto = config.get(IND_NUM_SOGGETTO).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkSoggettiFromJsonData] indNumSoggetto : [" + indNumSoggetto + "]");

            indNumReferente = config.get(IND_NUM_REFERENTE).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkSoggettiFromJsonData] indNumReferente : [" + indNumReferente + "]");
        }
        checkTipoSoggettoFromJsonData(jsonData, errorDetails, indTipoSoggetto);
        checkNumSoggettoFromJsonData(jsonData, errorDetails, indNumSoggetto);
        checkNumReferenteFromJsonData(jsonData, errorDetails, indNumReferente);
        logger.debug("[MandatoryInfoIstanzaManager::checkSoggettiFromJsonData] END");
        return errorDetails;
    }

    /**
     * Check tipo soggetto from json data.
     *
     * @param jsonData        the json data
     * @param errorDetails    the error details
     * @param indTipoSoggetto the ind tipo soggetto
     */
    private void checkTipoSoggettoFromJsonData(String jsonData, Map<String, String> errorDetails, String indTipoSoggetto) {
        try {
            // Check IndTipoSoggetto
            if (StringUtils.isNotBlank(indTipoSoggetto)) {
                List<String> tipiSoggettoPF = null;
                List<String> tipiSoggettoPG = null;
                List<String> tipiSoggettoPB = null;
                switch (indTipoSoggetto) {
                    case "PF":
                        tipiSoggettoPF = JsonPath.read(jsonData, COD_TIPO_SOGGETTO_PATH_NOT_EQUAL.replace(IND_TIPO_SOGGETTO_STR, indTipoSoggetto));
                        if (tipiSoggettoPF != null && !tipiSoggettoPF.isEmpty()) {
                            errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
                        }
                        break;
                    case "PG":
                        tipiSoggettoPG = JsonPath.read(jsonData, COD_TIPO_SOGGETTO_PATH_EQUAL.replace(IND_TIPO_SOGGETTO_STR, indTipoSoggetto));
                        if (tipiSoggettoPG == null || tipiSoggettoPG.isEmpty()) {
                            errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
                        }
                        break;
                    //case "GF": //  SCRIVA-459 : Aggiunta verifica casista PF_PB_.
                    default:
                        tipiSoggettoPF = JsonPath.read(jsonData, COD_TIPO_SOGGETTO_PATH_EQUAL.replace(IND_TIPO_SOGGETTO_STR, TipoSoggettoEnum.PF.name()));
                        tipiSoggettoPG = JsonPath.read(jsonData, COD_TIPO_SOGGETTO_PATH_EQUAL.replace(IND_TIPO_SOGGETTO_STR, TipoSoggettoEnum.PG.name()));
                        tipiSoggettoPB = JsonPath.read(jsonData, COD_TIPO_SOGGETTO_PATH_EQUAL.replace(IND_TIPO_SOGGETTO_STR, TipoSoggettoEnum.PB.name()));
                        if ((tipiSoggettoPG == null || tipiSoggettoPG.isEmpty())
                                && (tipiSoggettoPF == null || tipiSoggettoPF.isEmpty())
                                && (tipiSoggettoPB == null || tipiSoggettoPB.isEmpty())
                        ) {
                            errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
                        }
                        break;
                }
            }
        } catch (PathNotFoundException e) {
            logger.error("[MandatoryInfoIstanzaManager::checkTipoSoggettoFromJsonData] EXCEPTION PathNotFoundException : ", e);
            errorDetails.put(IND_TIPO_SOGGETTO, ERR_NOT_VALID);
        }
    }

    /**
     * Check num soggetto from json data.
     *
     * @param jsonData       the json data
     * @param errorDetails   the error details
     * @param indNumSoggetto the ind num soggetto
     */
    private void checkNumSoggettoFromJsonData(String jsonData, Map<String, String> errorDetails, String indNumSoggetto) {
        try {
            // Check IndNumSoggetto
            if (StringUtils.isNotBlank(indNumSoggetto)) {
                List<Object> soggettiIstanza = JsonPath.read(jsonData, SOGGETTI_ISTANZA_PRINCIPALE_PATH); //SCRIVA-466

                if (NumeroSoggettoEnum.M.name().equalsIgnoreCase(indNumSoggetto)) {
                    if (soggettiIstanza != null && soggettiIstanza.isEmpty()) {
                        errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
                    }
                } else {
                    // SCRIVA-466
                    if (soggettiIstanza != null && soggettiIstanza.size() != 1) {
                        errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
                    }
                    //  SCRIVA-459 : Aggiunta verifica casista PF_PB_....
                    /*
                    String[] indTipoSoggettoSplit = indTipoSoggetto.split("_");
                    long countIndTipoSoggettoPF = indTipoSoggettoSplit.length > 0 ?
                            Arrays.stream(indTipoSoggettoSplit).filter(tipoSoggetto -> TipoSoggettoEnum.PF.name().equalsIgnoreCase(tipoSoggetto)).count() :
                            0L;
                    //if (soggettiIstanza != null && soggettiIstanza.size() != (NumeroSoggettoEnum.getValue(indNumSoggetto) + (TipoSoggettoEnum.PF.name().equalsIgnoreCase(indTipoSoggetto) ? 0 : 1))) {
                    if (soggettiIstanza != null && soggettiIstanza.size() != (NumeroSoggettoEnum.getValue(indNumSoggetto) + (countIndTipoSoggettoPF > 0 ? 0 : 1))) {
                        errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
                    }

                     */
                }
            }
        } catch (PathNotFoundException e) {
            logger.error("[MandatoryInfoIstanzaManager::checkNumSoggettoFromJsonData] EXCEPTION PathNotFoundException : ", e);
            errorDetails.put(IND_NUM_SOGGETTO, ERR_NOT_VALID);
        }
    }

    /**
     * Check num referente from json data.
     *
     * @param jsonData        the json data
     * @param errorDetails    the error details
     * @param indNumReferente the ind num referente
     */
    private void checkNumReferenteFromJsonData(String jsonData, Map<String, String> errorDetails, String indNumReferente) {
        try {
            // Check IndNumReferente
            if (StringUtils.isNotBlank(indNumReferente) && !NumeroReferenteEnum.O.name().equalsIgnoreCase(indNumReferente)) {
                List<Object> referentiIstanza = JsonPath.read(jsonData, REFERENTI_ISTANZA_PATH);
                if (NumeroReferenteEnum.M.name().equalsIgnoreCase(indNumReferente)) {
                    if (referentiIstanza != null && (referentiIstanza.isEmpty() || referentiIstanza.size() > NumeroReferenteEnum.getValue(indNumReferente))) {
                        errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
                    }
                } else {
                    if (referentiIstanza != null && referentiIstanza.size() != NumeroReferenteEnum.getValue(indNumReferente)) {
                        errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
                    }
                }
            }
        } catch (PathNotFoundException e) {
            logger.error("[MandatoryInfoIstanzaManager::checkNumReferenteFromJsonData] EXCEPTION PathNotFoundException : ", e);
            errorDetails.put(IND_NUM_REFERENTE, ERR_NOT_VALID);
        }
    }

    /**
     * Check oggetti map.
     *
     * @param jsonData jsonData
     * @param config   config
     * @return Map<String, String>                       dettagli errori
     */
    public Map<String, String> checkOggetti(String jsonData, Map<String, Object> config) {
        logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] BEGIN");
        Map<String, String> errorDetails = new HashMap<>();
        List<Object> oggettiIstanza = null;
        String indGeoMode = "M";
        String indNumOggetto = "M";
        String indNumComuniOggetto = "M";
        if (config != null && !config.isEmpty()) {
            indGeoMode = config.get(IND_GEO_MODE).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] indGeoMode : [" + indGeoMode + "]");

            indNumOggetto = config.get(IND_NUM_OGGETTO).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] indNumOggetto : [" + indNumOggetto + "]");

            indNumComuniOggetto = config.get(IND_NUM_COMUNI_OGGETTO).toString();
            logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] indNumComuniOggetto : [" + indNumComuniOggetto + "]");
        }

        try {
            // Check IndGeoMode
            if (StringUtils.isNotBlank(indGeoMode)) {
                switch (indGeoMode) {
                    case "N": // Georeferenziazione non richiesta
                        break;
                    case "O": // Georeferenziazione richiesta ma opzionale
                        break;
                    case "M":  // Georeferenziazione obbligatoria
                        List<Object> oggettiIstanzaGeoRef = JsonPath.read(jsonData, OGGETTI_ISTANZA_GEOREF_PATH.replace("__IndGeoStato__", "G"));
                        if (CollectionUtils.isNotEmpty(oggettiIstanzaGeoRef)) {
                            errorDetails.put(IND_GEO_MODE, ERR_NOT_VALID); //QUA VA MESSO L'ERRORE E2GIS???
                        }
                        break;
                    case "A":  // Georeferenziazione opzionale con avviso
                        break;
                    default:
                        break;
                }
            }
        } catch (PathNotFoundException e) {
            logger.debug(OGGETTI_EXCEPTION_PATH_NOT_FOUND_EXCEPTION, e);
            errorDetails.put(IND_GEO_MODE, ERR_NOT_VALID);
        }

        try {
            // Check IndNumOggetto
            if (StringUtils.isNotBlank(indNumOggetto)) {
                oggettiIstanza = JsonPath.read(jsonData, OGGETTI_ISTANZA_PATH);
                if ("S".equalsIgnoreCase(indNumOggetto) || "N".equalsIgnoreCase(indNumOggetto)) { //M al posto di N?
                    if (oggettiIstanza != null && oggettiIstanza.size() != NumeroOperaEnum.getValue(indNumOggetto)) {
                        errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                    }
                }
            }
        } catch (PathNotFoundException e) {
            logger.debug(OGGETTI_EXCEPTION_PATH_NOT_FOUND_EXCEPTION, e);
            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
        }

        try {
            // Check IndNumComuniOggetto
            if (StringUtils.isNotBlank(indNumComuniOggetto)) {
                if (oggettiIstanza == null) {
                    oggettiIstanza = JsonPath.read(jsonData, OGGETTI_ISTANZA_PATH);
                }
                if (oggettiIstanza != null) {
                    for (int i = 0; i < oggettiIstanza.size(); i++) {
                        List<Object> comuniOggettiIstanza = JsonPath.read(jsonData, COMUNI_OGGETTI_ISTANZA_PATH.replace("__index__", String.valueOf(i)));
                        if ("M".equals(indNumComuniOggetto)) {
                            if (comuniOggettiIstanza != null && (comuniOggettiIstanza.isEmpty() || comuniOggettiIstanza.size() > NumeroComuniOggettoEnum.getValue(indNumComuniOggetto))) {
                                errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                            }
                        } else {
                            if (comuniOggettiIstanza != null && (comuniOggettiIstanza.size() != NumeroComuniOggettoEnum.getValue(indNumComuniOggetto))) {
                                errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
                            }
                        }
                    }
                }
            }
        } catch (PathNotFoundException e) {
            logger.debug(OGGETTI_EXCEPTION_PATH_NOT_FOUND_EXCEPTION, e);
            errorDetails.put(IND_NUM_OGGETTO, ERR_NOT_VALID);
        }
        logger.debug("[MandatoryInfoIstanzaManager::checkOggetti] END");
        return errorDetails;
    }

    /**
     * Check allegati map.
     *
     * @param jsonData  jsonData
     * @param idIstanza idIstanza
     * @return Map<String, String>                       dettagli errori
     */
    public Map<String, String> checkAllegati(String jsonData, Long idIstanza) {
        logger.debug("[MandatoryInfoIstanzaManager::checkAllegati] BEGIN");
        Map<String, String> errorDetails = new HashMap<>();
        String separator = "";
        List<CategoriaAllegatoDTO> listCategoriaAllegati = categoriaAllegatoDAO.loadCategoriaAllegatoMandatoryByIdIstanza(idIstanza, null);
        StringBuilder elencoCategorieErrate = new StringBuilder();
        if (listCategoriaAllegati != null && !listCategoriaAllegati.isEmpty()) {
            for (CategoriaAllegatoDTO categoriaAllegato : listCategoriaAllegati) {
                elencoCategorieErrate.append(separator);
                elencoCategorieErrate.append(categoriaAllegato.getDesCategoriaAllegato());
                separator = ",";
            }
            errorDetails.put(CAT_ALLEGATI_MANDATORY, elencoCategorieErrate.toString());
        }

        separator = "";
        listCategoriaAllegati = categoriaAllegatoDAO.loadCategoriaAllegatoMandatoryAdempimentoByIdIstanza(idIstanza);
        if (listCategoriaAllegati != null && !listCategoriaAllegati.isEmpty()) {
            elencoCategorieErrate = new StringBuilder();
            for (CategoriaAllegatoDTO categoriaAllegato : listCategoriaAllegati) {
                elencoCategorieErrate.append(separator);
                elencoCategorieErrate.append(categoriaAllegato.getDesCategoriaAllegato());
                separator = "|";
            }
            errorDetails.put(CAT_ALLEGATI_WRONG, elencoCategorieErrate.toString());
        }
        logger.debug("[MandatoryInfoIstanzaManager::checkAllegati] END");
        return errorDetails;
    }


}