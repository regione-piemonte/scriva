/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl;

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OsservazioneDAO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoOsservazioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.PubAdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.PubIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.PubStatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneDTO;
import it.csi.scriva.scrivabesrv.dto.enumeration.ComponenteAppEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Osservazione dao.
 *
 * @author CSI PIEMONTE
 */
public class OsservazioneDAOImpl extends ScrivaBeSrvGenericDAO<OggettoOsservazioneDTO> implements OsservazioneDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_OSSERVAZIONE = "select srio.id_istanza_osservazione, srio.id_funzionario as id_funzionario_srio,\r\n"
            + "srio.cf_osservazione_ins, srio.data_osservazione,\n"
            + "srio.data_pubblicazione as data_pubblicazione_srio, srio.num_protocollo_osservazione, srio.data_protocollo_osservazione,\n"
            + "srio.gest_uid as gest_uid_srio,\n"
            + "sdso.*,\n"
            + "sti.*,\n"
            + "sdsi.*,\n"
            + "sda.*,\n"
            + "sdta.*,\n"
            + "COALESCE (srac.valore, 'false') as flg_osservazioni\n"
            + "FROM scriva_r_istanza_osservazione srio\n"
            + "INNER JOIN scriva_d_stato_osservazione sdso on srio.id_stato_osservazione = sdso.id_stato_osservazione\n"
            + "INNER JOIN scriva_t_istanza sti on sti.id_istanza = srio.id_istanza\n"
            + "INNER JOIN scriva_d_stato_istanza sdsi on sdsi.id_stato_istanza = sti.id_stato_istanza\n"
            + "INNER JOIN scriva_d_adempimento sda on sda.id_adempimento = sti.id_adempimento\n"
            + "INNER JOIN scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n"
            + "LEFT JOIN scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS'\n"
            + "LEFT JOIN scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI'\n"
            + "WHERE 1 = 1\n";

    private static final String FIELDS = " srio.id_istanza, srio.id_istanza_osservazione, srio.cf_osservazione_ins, srio.data_osservazione,	srio.num_protocollo_osservazione, srio.data_protocollo_osservazione, "
    	+ " 	sti.cod_pratica, sdso.id_stato_osservazione, sdso.cod_stato_osservazione, sdso.des_stato_osservazione, sda.des_adempimento \n";
 
    
    private static final String FIELDS_ISTANZA = " sti.cod_istanza , sti.data_protocollo_istanza , sti.num_protocollo_istanza, sti.data_inserimento_istanza , sti.data_modifica_istanza, "
    	+ " sti.data_inserimento_pratica, sti.data_modifica_pratica, sti.data_pubblicazione, sti.des_stato_sintesi_pagamento, sti.data_inizio_osservazioni , \n"
        + " sti.data_fine_osservazioni , sdsi.cod_stato_istanza,	sdsi.des_stato_istanza, sdsi.label_stato, sda.cod_adempimento, \n"
        + " sdta.cod_tipo_adempimento, sdta.des_tipo_adempimento \n";
   
    
    private static final String QUERY_OSSERVAZIONE_SINTESI = ""
    	+ "select " + FIELDS + ", \n"
        + "srio.id_funzionario, \n"
        + "srio.data_pubblicazione as data_pubblicazione_srio,\n"
        + "srio.gest_uid,\n"
        + "COALESCE (srac.valore, 'false') as flg_osservazioni, \n"
        + FIELDS_ISTANZA + ", \n"
        + "STRING_AGG(DISTINCT den_oggetto, ',') AS den_oggetto \n"
        + "FROM scriva_r_istanza_osservazione srio \n"
        + "INNER JOIN scriva_d_stato_osservazione sdso on srio.id_stato_osservazione = sdso.id_stato_osservazione\n"
        + "INNER JOIN scriva_t_istanza sti on sti.id_istanza = srio.id_istanza\n"
        + "INNER JOIN scriva_d_stato_istanza sdsi on sdsi.id_stato_istanza = sti.id_stato_istanza\n"
        + "INNER JOIN scriva_d_adempimento sda on sda.id_adempimento = sti.id_adempimento\n"
        + "INNER JOIN scriva_d_tipo_adempimento sdta on sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n"
        + "LEFT JOIN scriva_r_adempi_config srac on srac.id_adempimento = sti.id_adempimento and srac.chiave = 'OSS'\n"
        + "LEFT JOIN scriva_d_informazioni_scriva sdis on sdis.id_informazione_scriva = srac.id_informazione_scriva and sdis.cod_informazione_scriva = 'OSSERVAZIONI'\n"
        + "INNER JOIN scriva_t_oggetto_istanza stoi on STOI.id_istanza = sti.id_istanza "
        + "WHERE 1 = 1\n";

    private static final String WHERE_ID_ISTANZA = "AND srio.id_istanza = :idIstanza\n";

    private static final String WHERE_CF_ATTORE = "AND srio.cf_osservazione_ins = :cfAttore\n";

    private static final String WHERE_COD_STATO_OSSERVAZIONE = "AND UPPER(sdso.cod_stato_osservazione) = UPPER(:codStatoOsservazione)\n";

    private static final String WHERE_ID_OSSERVAZIONE = "AND srio.id_istanza_osservazione = :idIstanzaOsservazione\n";

    private static final String ORDER_BY_ID_ISTANZA_OSSERVAZIONE = "ORDER BY srio.id_istanza_osservazione";
    
    private static final String ORDER_BY_DATA_OSSERVAZIONE = "ORDER BY srio.data_osservazione ";
    
    private static final String ORDER_BY_DATA_PUBBLICAZIONE = "ORDER BY data_pubblicazione_srio ";
    
    private static final String ORDER_BY_DES_STATO_OSSERVAZIONE = "ORDER BY sdso.des_stato_osservazione ";
    
    private static final String ORDER_BY_DES_ADEMPIMENTO = "ORDER BY sda.des_adempimento ";
    
    private static final String ORDER_BY_COD_PRATICA = "ORDER BY sti.cod_pratica ";
    
    private static final String ORDER_BY_DEN_OGGETTO = "ORDER BY den_oggetto"; 
    
    private static final String GROUP_BY_CONDITION = " GROUP BY " + FIELDS + ",  srio.id_funzionario, data_pubblicazione_srio, srio.gest_uid, flg_osservazioni, " + FIELDS_ISTANZA; 
   

    //private static final String QUERY_PRIMARY_KEY = QUERY_OSSERVAZIONE + WHERE_ID_OSSERVAZIONE;
    //SOSTITUITA LA QUERY OLD CON LA NUOVA QUERY PIATTA
    private static final String QUERY_PRIMARY_KEY = QUERY_OSSERVAZIONE_SINTESI + WHERE_ID_OSSERVAZIONE + GROUP_BY_CONDITION;

    //jira - SCRIVA-833 - tolta la data pubblicazione perchè sarà aggiornata da scriva bo
    private static final String QUERY_INSERT_NEW_OSSERVAZIONE = "INSERT INTO _replaceTableName_ (id_istanza_osservazione, id_istanza, "
            + " id_stato_osservazione, id_funzionario, cf_osservazione_ins, data_osservazione, num_protocollo_osservazione, "
            + " data_protocollo_osservazione, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + " VALUES(nextval('seq_scriva_r_istanza_osservazione'), :idIstanza, :idStatoOsservazione, :idFunzionario, :cfOsservazioneIns, "
            + " :dataOsservazione, :numProtocolloOsservazione, :dataProtocolloOsservazione, :dateIns, :attoreIns, "
            + " :dateUpd, :attoreUpd, :uid)";

    private static final String QUERY_UPDATE_OSSERVAZIONE = "UPDATE _replaceTableName_\n"
            + "SET id_stato_osservazione=:idStatoOsservazione, gest_data_upd=:dateUpd, gest_attore_upd=:attoreUpd\n"
            + "WHERE id_istanza_osservazione=:idIstanzaOsservazione";

    private static final String QUERY_DELETE_OSSERVAZIONE_BY_ID_ISTANZA_OSSERVAZIONE = "DELETE FROM _replaceTableName_ WHERE id_istanza_osservazione = :idIstanzaOsservazione";

    private static final String QUERY_UPDATE_DATA_PUB_OSSERVAZIONE_BY_ALLEG = "UPDATE _replaceTableName_ AS srio\n" +
            "SET data_pubblicazione = :dataPubblicazione,\n" +
            "id_stato_osservazione = (SELECT id_stato_osservazione FROM scriva_d_stato_osservazione WHERE UPPER(cod_stato_osservazione) = 'PUBBLICATA'),\n" +
            "gest_attore_upd = :attoreUpd,\n" +
            "gest_data_upd = now()\n" +
            "FROM scriva_t_allegato_istanza stai\n" +
            "WHERE srio.id_istanza_osservazione = stai.id_istanza_osservazione\n" +
            "AND srio.data_pubblicazione IS NULL\n" +
            "AND stai.id_allegato_istanza = :idAllegatoIstanza";

    private static final String QUERY_UPDATE_STATO_OSSERVAZIONE_BY_ALLEG = "UPDATE _replaceTableName_ AS srio\n" +
            "SET data_pubblicazione = NULL,\n" +
            "id_stato_osservazione = (SELECT id_stato_osservazione FROM scriva_d_stato_osservazione WHERE UPPER(cod_stato_osservazione) = 'INVIATA'),\n" +
            "gest_attore_upd = :attoreUpd,\n" +
            "gest_data_upd = now()\n" +
            "FROM scriva_t_allegato_istanza stai\n" +
            "WHERE srio.id_istanza_osservazione = stai.id_istanza_osservazione \n" +
            "AND stai.id_allegato_istanza = :idAllegatoIstanza\n" +
            "AND 0 = (\n" +
                //n° allegati (legati all'osservazione con data_pubblicazione valorizzata) che hanno data_pubblicazione valorizzata
            "    SELECT COUNT(1) FROM scriva_t_allegato_istanza\n" +
            "    WHERE data_pubblicazione IS NOT NULL\n" +
            "    AND id_istanza_osservazione IN\n" +
            "    (\n" +
                     // osservazione legata all'allegato con data_pubblicazione valorizzata
            "        SELECT io.id_istanza_osservazione\n" +
            "        FROM scriva_r_istanza_osservazione io \n" +
            "        INNER JOIN scriva_t_allegato_istanza ai ON ai.id_istanza_osservazione = io.id_istanza_osservazione\n" +
            "        AND io.data_pubblicazione IS NOT NULL\n" +
            "        AND ai.id_allegato_istanza = :idAllegatoIstanza\n" +
            "    )\n" +
            ")";

    /**
     * Load osservazioni list.
     *
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param idOsservazione       the id osservazione
     * @param attoreScriva         the attore scriva
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @return the list
     */
    @Override
    public List<OggettoOsservazioneExtendedDTO> loadOsservazioni(
    	Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort
    ) {
        logBegin(className);
        if (idOsservazione != null) {
            return this.loadOsservazioneById(idOsservazione, attoreScriva);
        }
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_OSSERVAZIONE;

        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            map.put("codStatoOsservazione", "Pubblicata");
            query += WHERE_ID_ISTANZA + (attoreScriva != null && !ComponenteAppEnum.PUBWEB.name().equalsIgnoreCase(attoreScriva.getComponente()) ? "" : WHERE_COD_STATO_OSSERVAZIONE);

        } else if (attoreScriva != null && StringUtils.isNotBlank(attoreScriva.getCodiceFiscale())) {
            map.put("cfAttore", attoreScriva.getCodiceFiscale());
            query += WHERE_CF_ATTORE;
        }

        if (StringUtils.isNotBlank(codStatoOsservazione)) {
            map.put("codStatoOsservazione", codStatoOsservazione);
            query += WHERE_COD_STATO_OSSERVAZIONE;
        }
        if (StringUtils.isBlank(sort)) {
        	query += ORDER_BY_ID_ISTANZA_OSSERVAZIONE;
        } else { 
        	String orderByParameter = valorizzaOrderByParameter(sort);
        	query += orderByParameter;
        }
        	
        
        return findListByQuery(className, query, map, offset, limit, false);
    }
    
    /**
     * Load osservazioni list sintesi.
     *
     * @param idIstanza            the id istanza
     * @param codStatoOsservazione the cod stato osservazione
     * @param idOsservazione       the id osservazione
     * @param attoreScriva         the attore scriva
     * @param offset               the offset
     * @param limit                the limit
     * @param sort                 the sort
     * @return the list
     */
    @Override
    public List<OggettoOsservazioneExtendedDTO> loadOsservazioniSintesi(
    	Long idIstanza, String codStatoOsservazione, Long idOsservazione, AttoreScriva attoreScriva, String offset, String limit, String sort
    ) {
        logBegin(className);
        if (idOsservazione != null) {
            return this.loadOsservazioneById(idOsservazione, attoreScriva);
        }
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_OSSERVAZIONE_SINTESI;

        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            map.put("codStatoOsservazione", "Pubblicata");
            query += WHERE_ID_ISTANZA + (attoreScriva != null && !ComponenteAppEnum.PUBWEB.name().equalsIgnoreCase(attoreScriva.getComponente()) ? "" : WHERE_COD_STATO_OSSERVAZIONE);

        } else if (attoreScriva != null && StringUtils.isNotBlank(attoreScriva.getCodiceFiscale())) {
            map.put("cfAttore", attoreScriva.getCodiceFiscale());
            query += WHERE_CF_ATTORE;
        }

        if (StringUtils.isNotBlank(codStatoOsservazione)) {
            map.put("codStatoOsservazione", codStatoOsservazione);
            query += WHERE_COD_STATO_OSSERVAZIONE;
        }
        
        query += GROUP_BY_CONDITION;
        
        if (StringUtils.isBlank(sort)) {
        	query += ORDER_BY_ID_ISTANZA_OSSERVAZIONE;
        } else { 
        	String orderByParameter = valorizzaOrderByParameter(sort);
        	query += orderByParameter;
        }
        	
        
        return findListByQuery(className, query, map, offset, limit, false);
    }

    private String valorizzaOrderByParameter(String sort) {
    	char order = '-';
        String sortField = sort.charAt(0)== order ? sort.substring(1) : sort;
        String tipoOrder = sort.charAt(0)== order ? " desc" : " asc";
        
        switch (sortField) {
        	case "data_osservazione":
        		return ORDER_BY_DATA_OSSERVAZIONE + tipoOrder;
        	case "data_pubblicazione":
        		return ORDER_BY_DATA_PUBBLICAZIONE + tipoOrder;
        	case "den_oggetto":
        		return ORDER_BY_DEN_OGGETTO + tipoOrder;
        	case "des_stato_osservazione":
       			return ORDER_BY_DES_STATO_OSSERVAZIONE + tipoOrder; 
        	case "cod_pratica":
    			return ORDER_BY_COD_PRATICA + tipoOrder; 
            case "des_adempimento": 
    			return ORDER_BY_DES_ADEMPIMENTO + tipoOrder; 
    		default:
    			return ORDER_BY_ID_ISTANZA_OSSERVAZIONE;
        }
	}

	/**
     * Load osservazione by id list.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     * @param attoreScriva          the attore scriva
     * @return the list
     */
    @Override
    public List<OggettoOsservazioneExtendedDTO> loadOsservazioneById(Long idIstanzaOsservazione, AttoreScriva attoreScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaOsservazione", idIstanzaOsservazione);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Save osservazione long.
     *
     * @param osservazione the osservazione
     * @return the long
     */
    @Override
    public Long saveOsservazione(OggettoOsservazioneDTO osservazione) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idIstanza", osservazione.getIdIstanza());
            map.put("idStatoOsservazione", osservazione.getIdStatoOsservazione());// INCORSO
            map.put("idFunzionario", osservazione.getIdFunzionario()); //
            map.put("cfOsservazioneIns", osservazione.getCfOsservazioneIns());
            map.put("dataOsservazione", now);
            //jira - SCRIVA-833 - tolta la data pubblicazione perchè sarà aggiornata da scriva bo 
            //map.put("dataPubblicazione", now);
            map.put("numProtocolloOsservazione", osservazione.getNumProtocolloOsservazione());
            map.put("dataProtocolloOsservazione", osservazione.getDataProtocolloOsservazione());
            map.put("dateIns", now);
            map.put("attoreIns", osservazione.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", osservazione.getGestAttoreIns());
            String uid = generateGestUID(osservazione.getIdIstanza() + osservazione.getCfOsservazioneIns() + now);
            map.put("uid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_NEW_OSSERVAZIONE, null, null), params, keyHolder, new String[]{"id_istanza_osservazione"});

            Number key = keyHolder.getKey();
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update osservazione integer.
     *
     * @param osservazione the osservazione
     * @return the integer
     */
    @Override
    public Integer updateOsservazione(OggettoOsservazioneDTO osservazione) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idStatoOsservazione", osservazione.getIdStatoOsservazione());
            map.put("idFunzionario", osservazione.getIdFunzionario()); //
            map.put("dataPubblicazione", osservazione.getDataPubblicazione());
            map.put("numProtocolloOsservazione", osservazione.getNumProtocolloOsservazione());
            map.put("dataProtocolloOsservazione", osservazione.getDataProtocolloOsservazione());
            map.put("dateUpd", now);
            map.put("attoreUpd", osservazione.getGestAttoreUpd());
            map.put("idIstanzaOsservazione", osservazione.getIdIstanzaOsservazione());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_OSSERVAZIONE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete osservazione by id istanza osservazione integer.
     *
     * @param idIstanzaOsservazione the id istanza osservazione
     * @return the integer
     */
    @Override
    public Integer deleteOsservazioneByIdIstanzaOsservazione(Long idIstanzaOsservazione) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanzaOsservazione", idIstanzaOsservazione);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OSSERVAZIONE_BY_ID_ISTANZA_OSSERVAZIONE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update data pubblicazione osservazione by id allegato integer.
     *
     * @param idAllegatoIstanza    the id allegato
     * @param dataPubblicazione    the data pubblicazione
     * @param codStatoOsservazione the cod stato osservazione
     * @param attoreScriva         the attore scriva
     * @return the integer
     */
    @Override
    public Integer updateDataPubbOsservazioneByIdAllegato(Long idAllegatoIstanza, Timestamp dataPubblicazione, String codStatoOsservazione, AttoreScriva attoreScriva) {
        logBegin(className);
        try {
            String query = dataPubblicazione != null ? QUERY_UPDATE_DATA_PUB_OSSERVAZIONE_BY_ALLEG : QUERY_UPDATE_STATO_OSSERVAZIONE_BY_ALLEG;
            Map<String, Object> map = new HashMap<>();
            map.put("idAllegatoIstanza", idAllegatoIstanza);
            map.put("dataPubblicazione", dataPubblicazione);
            map.put("codStatoOsservazione", codStatoOsservazione);
            map.put("attoreUpd", attoreScriva.getCodiceFiscale());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(query, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }


    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    @Override
    public RowMapper<OggettoOsservazioneDTO> getRowMapper() throws SQLException {
        return new OsservazioneRowMapper();
    }

    @Override
    public RowMapper<OggettoOsservazioneExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OsservazioneExtendedRowMapper();
    }

    public static class OsservazioneRowMapper implements RowMapper<OggettoOsservazioneDTO> {

        public OsservazioneRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public OggettoOsservazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoOsservazioneDTO bean = new OggettoOsservazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoOsservazioneDTO bean) throws SQLException {
            bean.setIdIstanzaOsservazione(rs.getLong("id_istanza_osservazione"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdStatoOsservazione(rs.getLong("id_stato_osservazione"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setCfOsservazioneIns(rs.getString("cf_osservazione_ins"));
            bean.setDataOsservazione(rs.getTimestamp("data_osservazione"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setNumProtocolloOsservazione(rs.getString("num_protocollo_osservazione"));
            bean.setDataProtocolloOsservazione(rs.getTimestamp("data_protocollo_osservazione"));
            bean.setGestUID(rs.getString("gest_uid"));
            
            //aggiunti
            bean.setIdStatoOsservazione(rs.getLong("id_stato_osservazione"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setCodStatoOsservazione(rs.getString("cod_stato_osservazione"));
            bean.setDesStatoOsservazione(rs.getString("des_stato_osservazione"));
           	bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
        }
    }

    public static class OsservazioneExtendedRowMapper implements RowMapper<OggettoOsservazioneExtendedDTO> {

        public OsservazioneExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public OggettoOsservazioneExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoOsservazioneExtendedDTO bean = new OggettoOsservazioneExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoOsservazioneExtendedDTO bean) throws SQLException {
            bean.setIdIstanzaOsservazione(rs.getLong("id_istanza_osservazione"));
            PubIstanzaDTO pubIstanzaDTO = new PubIstanzaDTO();
            populateBeanPubIstanza(rs, pubIstanzaDTO);
            bean.setIstanza(pubIstanzaDTO);

            StatoOsservazioneDTO statoOsservazioneDTO = new StatoOsservazioneDTO();
            populateBeanStatoOsservazione(rs, statoOsservazioneDTO);
            bean.setStatoOsservazione(statoOsservazioneDTO);

            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setCfOsservazioneIns(rs.getString("cf_osservazione_ins"));
            bean.setDataOsservazione(rs.getTimestamp("data_osservazione"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione_srio"));
            bean.setNumProtocolloOsservazione(rs.getString("num_protocollo_osservazione"));
            bean.setDataProtocolloOsservazione(rs.getTimestamp("data_protocollo_osservazione"));
            bean.setGestUID(rs.getString("gest_uid"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
        }

        private void populateBeanPubIstanza(ResultSet rs, PubIstanzaDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setCodIstanza(rs.getString("cod_istanza"));

            PubStatoIstanzaDTO statoIstanza = new PubStatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanza(statoIstanza);

            PubAdempimentoDTO adempimento = new PubAdempimentoDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento);

            bean.setDataProtocollo(rs.getTimestamp("data_protocollo_istanza"));
            bean.setNumeroProtocollo(rs.getString("num_protocollo_istanza"));
            bean.setDataInserimentoIstanza(rs.getTimestamp("data_inserimento_istanza"));
            bean.setDataModificaIstanza(rs.getTimestamp("data_modifica_istanza"));
            bean.setDataPubblicazione(rs.getTimestamp("data_pubblicazione"));
            bean.setCodPratica(rs.getString("cod_pratica"));
            bean.setDataInserimentoPratica(rs.getTimestamp("data_inserimento_pratica"));
            bean.setDataModificaPratica(rs.getTimestamp("data_modifica_pratica"));
            bean.setDesStatoSintesiPagamento(rs.getString("des_stato_sintesi_pagamento"));
            
            //SCRIVA-945
            List<Timestamp> dataInizioOsservazione = new ArrayList<Timestamp>();
            dataInizioOsservazione.add(rs.getTimestamp("data_inizio_osservazioni"));
            bean.setDataInizioOsservazione(dataInizioOsservazione);
            
            bean.setDataFineOsservazione(rs.getTimestamp("data_fine_osservazioni"));
            bean.setFlgOsservazione(rs.getString("flg_osservazioni").equalsIgnoreCase("false") ? Boolean.FALSE : Boolean.TRUE);
        }

        private void populateBeanStatoIstanza(ResultSet rs, PubStatoIstanzaDTO bean) throws SQLException {
            bean.setCodStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDesStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
        }

        private void populateBeanAdempimento(ResultSet rs, PubAdempimentoDTO bean) throws SQLException {
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));

        }

        private void populateBeanStatoOsservazione(ResultSet rs, StatoOsservazioneDTO bean) throws SQLException {
            bean.setCodStatoOsservazione(rs.getString("cod_stato_osservazione"));
            bean.setDesStatoOsservazione(rs.getString("des_stato_osservazione"));
        }

    }


}