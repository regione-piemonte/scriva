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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoIstanzaExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Ubicazione oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class UbicazioneOggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<UbicazioneOggettoIstanzaDTO> implements UbicazioneOggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND sruoi.id_oggetto_istanza = :idOggettoIstanza\n";

    private static final String WHERE_ID_UBICAZIONE_OGGETTO_ISTANZA = "AND sruoi.id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanza\n";

    private static final String WHERE_ID_COMUNE = "AND sruoi.id_comune = :idComune\n";

    private static final String WHERE_DEN_INDIRIZZO = "AND sruoi.den_indirizzo = :denIndirizzo\n";

    private static final String WHERE_NUM_CIVICO = "AND sruoi.num_civico = :numCivico\n";

    private static final String WHERE_DES_LOCALITA = "AND sruoi.des_localita = :desLocalita\n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanza";

    private static final String QUERY_LOAD_UBICAZIONI_OGGETTI_ISTANZA = "SELECT sruoi.*, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc,\n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp,\n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr,\n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn\n" +
            "FROM _replaceTableName_ sruoi\n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruoi.id_comune\n" +
            "INNER JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia\n" +
            "INNER JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione\n" +
            "INNER JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_LOAD_UBICAZIONE_OGGETTO_ISTANZA = QUERY_LOAD_UBICAZIONI_OGGETTI_ISTANZA + WHERE_ID_UBICAZIONE_OGGETTO_ISTANZA;

    private static final String QUERY_LOAD_UBICAZIONE_OGGETTO_BY_ID_OGGETTO_ISTANZA = QUERY_LOAD_UBICAZIONI_OGGETTI_ISTANZA + WHERE_ID_OGGETTO_ISTANZA;

    private static final String QUERY_INSERT_UBICAZIONE_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_ " +
            "(id_ubica_oggetto_istanza, id_oggetto_istanza, id_comune, den_indirizzo, num_civico, des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES (nextval('seq_scriva_r_ubica_oggetto_istanza'), :idOggettoIstanza, :idComune, :denIndirizzo, :numCivico, :desLocalita,  :indGeoProvenienza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_UBICAZIONE_OGGETTO_ISTANZA = "UPDATE _replaceTableName_ " +
            "SET id_oggetto_istanza=:idOggettoIstanza, id_comune=:idComune, den_indirizzo=:denIndirizzo, num_civico=:numCivico, des_localita=:desLocalita, ind_geo_provenienza=:indGeoProvenienza, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd " +
            "WHERE id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanza";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_ubica_oggetto_istanza = :idUbicazioneOggettoIstanza";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_ID_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_NOT_IN_ID_UBICAZIONI = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza AND id_ubica_oggetto_istanza NOT IN (:idUbicazioniOggettoIstanza)";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = (SELECT id_oggetto_istanza FROM scriva_t_oggetto_istanza WHERE gest_uid = :uidOggettoIstanza)";

    private static final String QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_ " +
            "(id_ubica_ogg_istanza_storico, id_ubica_oggetto_istanza, id_oggetto_istanza, id_comune, den_indirizzo, num_civico, des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES (nextval('seq_scriva_s_ubica_oggetto_istanza'), :idUbicazioneOggettoIstanza, :idOggettoIstanza, :idComune, :denIndirizzo, :numCivico, :desLocalita,  :indGeoProvenienza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_UBICAZIONE_OGGETTO_ISTANZA_FLAG = "UPDATE scriva_r_ubica_oggetto_istanza " + 
    		"SET ind_geo_provenienza = 'GEOD' " + 
    		"WHERE id_ubica_oggetto_istanza IN ( " + 
    		"	SELECT sruoi.id_ubica_oggetto_istanza  " + 
    		"	FROM scriva_r_ubica_oggetto_istanza sruoi  " + 
    		"	INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto_istanza = sruoi.id_oggetto_istanza " + 
    		"	INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruoi.id_comune " + 
    		"	WHERE stoi.id_istanza = :idIstanza " + 
    		"	AND stoi.id_oggetto = :idOggetto " + 
    		"	AND sdc.cod_istat_comune IN (:codIstatComuneList) " + 
    		") ";
    /**
     * Load ubicazioni oggetti istanza list.
     *
     * @return List<UbicazioneOggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioniOggettiIstanza() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONI_OGGETTI_ISTANZA, null);
    }

    /**
     * Load ubicazione oggetto istanza list.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return List<UbicazioneOggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanza(Long idUbicazioneOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idUbicazioneOgettoIstanza", idUbicazioneOggettoIstanza);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONE_OGGETTO_ISTANZA, map);
    }

    /**
     * Load ubicazione oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<UbicazioneOggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONE_OGGETTO_BY_ID_OGGETTO_ISTANZA, map);
    }

    /**
     * Load ubicazione oggetto istanza by fields list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param idComune         the id comune
     * @param denIndirizzo     the den indirizzo
     * @param numCivico        the num civico
     * @param desLocalita      the des localita
     * @return the list
     */
    @Override
    public List<UbicazioneOggettoIstanzaExtendedDTO> loadUbicazioneOggettoIstanzaByFields(Long idOggettoIstanza, Long idComune, String denIndirizzo, String numCivico, String desLocalita) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_UBICAZIONI_OGGETTI_ISTANZA;
        if (idOggettoIstanza != null) {
            map.put("idOggettoIstanza", idOggettoIstanza);
            query += WHERE_ID_OGGETTO_ISTANZA;
        }
        if (idComune != null) {
            map.put("idComune", idComune);
            query += WHERE_ID_COMUNE;
        }
        if (StringUtils.isNotBlank(denIndirizzo)) {
            map.put("denIndirizzo", denIndirizzo);
            query += WHERE_DEN_INDIRIZZO;
        }
        if (StringUtils.isNotBlank(numCivico)) {
            map.put("numCivico", numCivico);
            query += WHERE_NUM_CIVICO;
        }
        if (StringUtils.isNotBlank(desLocalita)) {
            map.put("desLocalita", desLocalita);
            query += WHERE_DES_LOCALITA;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Find by pk ubicazione oggetto istanza dto.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return UbicazioneOggettoIstanzaDTO ubicazione oggetto istanza dto
     */
    @Override
    public UbicazioneOggettoIstanzaDTO findByPK(Long idUbicazioneOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idUbicazioneOggettoIstanza", idUbicazioneOggettoIstanza);
        return findByPK(className, map);
    }

    /**
     * Save ubicazione oggetto istanza long.
     *
     * @param dto UbicazioneOggettoIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveUbicazioneOggettoIstanza(UbicazioneOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("idComune", dto.getIdComune());
            map.put("denIndirizzo", dto.getDenIndirizzo());
            map.put("numCivico", dto.getNumCivico());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("indGeoProvenienza", dto.getIndGeoProvenienza());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());

            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_UBICAZIONE_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_ubica_oggetto_istanza"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                map.put("idUbicazioneOggettoIstanza", key.longValue());
                params = getParameterValue(map);

                template.update(getQueryStorico(QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO_ISTANZA), params);
            }

            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update ubicazione oggetto istanza integer.
     *
     * @param dto UbicazioneOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateUbicazioneOggettoIstanza(UbicazioneOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            UbicazioneOggettoIstanzaDTO ubicazioneOggettoIstanza = this.findByPK(dto.getIdUbicazioneOggettoIstanza());
            if (null == ubicazioneOggettoIstanza) {
                LOGGER.error("[UbicazioneOggettoDAOImpl::updateUbicazioneOggettoIstanza] Record non trovato con id [" + dto.getIdOggettoIstanza() + "]");
                return -1;
            }
            int returnValue = 1;

            if (!dto.equals(ubicazioneOggettoIstanza)) {
                map.put("idUbicazioneOggettoIstanza", dto.getIdUbicazioneOggettoIstanza());
                map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
                map.put("idComune", dto.getIdComune());
                map.put("denIndirizzo", dto.getDenIndirizzo());
                map.put("numCivico", dto.getNumCivico());
                map.put("desLocalita", dto.getDesLocalita());
                map.put("indGeoProvenienza", dto.getIndGeoProvenienza());
                map.put("gestDataIns", ubicazioneOggettoIstanza.getGestDataIns());
                map.put("gestAttoreIns", ubicazioneOggettoIstanza.getGestAttoreIns());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("gestUid", ubicazioneOggettoIstanza.getGestUID());
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_UBICAZIONE_OGGETTO_ISTANZA, null, null), params);
                if (returnValue > 0) {
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO_ISTANZA), params);
                }
            }

            return returnValue;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }
    
    /**
     * Update ubicazione oggetto istanza integer.
     *
     * @param dto UbicazioneOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public void updateUbicazioneOggettoIstanzaFlag(Long idIstanza, Long idOggetto, List<String> codIstatComuneList) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idOggetto", idOggetto);
            map.put("codIstatComuneList", codIstatComuneList);

            MapSqlParameterSource params = getParameterValue(map);

            template.update(getQuery(QUERY_UPDATE_UBICAZIONE_OGGETTO_ISTANZA_FLAG, null, null), params);

        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto istanza integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoIstanza(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto istanza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto istanza by id integer.
     *
     * @param idUbicazioneOggettoIstanza idUbicazioneOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoIstanzaById(Long idUbicazioneOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idUbicazioneOggettoIstanza", idUbicazioneOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoIstanzaByUidOggettoIstanza(String uidOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uidOggettoIstanza", uidOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazioni oggetto istanza by not in id ubicazioni oggetto istanza integer.
     *
     * @param idOggettoIstanza           idOggettoIstanza
     * @param idUbicazioniOggettoIstanza idUbicazioniOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioniOggettoIstanzaByNotInIdUbicazioniOggettoIstanza(Long idOggettoIstanza, List<Long> idUbicazioniOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            map.put("idUbicazioniOggettoIstanza", idUbicazioniOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_ISTANZA_BY_NOT_IN_ID_UBICAZIONI, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<UbicazioneOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<UbicazioneOggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new UbicazioneOggettoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<UbicazioneOggettoIstanzaExtendedDTO>
     */
    @Override
    public RowMapper<UbicazioneOggettoIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new UbicazioneOggettoIstanzaExtendedRowMapper();
    }

    /**
     * The type Ubicazione oggetto istanza extended row mapper.
     */
    public static class UbicazioneOggettoIstanzaExtendedRowMapper implements RowMapper<UbicazioneOggettoIstanzaExtendedDTO> {

        /**
         * Instantiates a new Ubicazione oggetto istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public UbicazioneOggettoIstanzaExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public UbicazioneOggettoIstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UbicazioneOggettoIstanzaExtendedDTO bean = new UbicazioneOggettoIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, UbicazioneOggettoIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdUbicazioneOggettoIstanza(rs.getLong("id_ubica_oggetto_istanza"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            ComuneExtendedDTO comune = new ComuneExtendedDTO();
            populateBeanComune(rs, comune);
            bean.setComune(comune);
            bean.setDenIndirizzo(rs.getString("den_indirizzo"));
            bean.setNumCivico(rs.getString("num_civico"));
            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setIndGeoProvenienza(rs.getString("ind_geo_provenienza"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanComune(ResultSet rs, ComuneExtendedDTO bean) throws SQLException {
            String prefix = "_sdc";
            bean.setIdComune(rs.getLong("comune_id"));
            bean.setCodIstatComune(rs.getString("cod_istat_comune"));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune"));
            bean.setDenomComune(rs.getString("denom_comune"));

            ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
            populateBeanProvincia(rs, provincia);
            bean.setProvincia(provincia);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdComune(rs.getLong("dt_id_comune"));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev"));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next"));
            bean.setCapComune(rs.getString("cap_comune"));
        }

        private void populateBeanProvincia(ResultSet rs, ProvinciaExtendedDTO bean) throws SQLException {
            final String prefix = "_sdp";
            bean.setIdProvincia(rs.getLong("provincia_id"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanRegione(ResultSet rs, RegioneExtendedDTO bean) throws SQLException {
            final String prefix = "_sdr";
            bean.setIdRegione(rs.getLong("regione_id"));
            bean.setCodRegione(rs.getString("cod_regione"));
            bean.setDenomRegione(rs.getString("denom_regione"));

            NazioneDTO nazione = new NazioneDTO();
            populateBeanNazione(rs, nazione);
            bean.setNazione(nazione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanNazione(ResultSet rs, NazioneDTO bean) throws SQLException {
            final String prefix = "_sdn";
            bean.setIdNazione(rs.getLong("nazione_id"));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione"));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione"));
            bean.setDenomNazione(rs.getString("denom_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdStato(rs.getLong("dt_id_stato"));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev"));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next"));
            bean.setIdOrigine(rs.getLong("id_origine"));
            bean.setCodIso2(rs.getString("cod_iso2"));
        }

    }

    /**
     * The type Ubicazione oggetto istanza row mapper.
     */
    public static class UbicazioneOggettoIstanzaRowMapper implements RowMapper<UbicazioneOggettoIstanzaDTO> {

        /**
         * Instantiates a new Ubicazione oggetto istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public UbicazioneOggettoIstanzaRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public UbicazioneOggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UbicazioneOggettoIstanzaDTO bean = new UbicazioneOggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, UbicazioneOggettoIstanzaDTO bean) throws SQLException {
            bean.setIdUbicazioneOggettoIstanza(rs.getLong("id_ubica_oggetto_istanza"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdComune(rs.getLong("id_comune"));
            bean.setDenIndirizzo(rs.getString("den_indirizzo"));
            bean.setNumCivico(rs.getString("num_civico"));
            bean.setDesLocalita(rs.getString("des_localita"));
            bean.setIndGeoProvenienza(rs.getString("ind_geo_provenienza"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * Gets query storico.
     *
     * @param query the query
     * @return the query storico
     */
    public String getQueryStorico(String query) {
        return query.replace("_replaceTableName_", getStoricoTableName());
    }

    /**
     * Gets storico table name.
     *
     * @return the storico table name
     */
    public String getStoricoTableName() {
        return storicoTableName;
    }

    /**
     * Sets storico table name.
     *
     * @param storicoTableName the storico table name
     */
    public void setStoricoTableName(String storicoTableName) {
        this.storicoTableName = storicoTableName;
    }
}