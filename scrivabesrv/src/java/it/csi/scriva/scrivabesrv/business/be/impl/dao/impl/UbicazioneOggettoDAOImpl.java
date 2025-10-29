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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.UbicazioneOggettoDAO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.UbicazioneOggettoExtendedDTO;
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
 * The type Ubicazione oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class UbicazioneOggettoDAOImpl extends ScrivaBeSrvGenericDAO<UbicazioneOggettoDTO> implements UbicazioneOggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_ubica_oggetto = :idUbicaOggetto";

    private static final String QUERY_LOAD_UBICAZIONI_OGGETTI = "SELECT sruo.*, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \n" +
            "FROM _replaceTableName_ sruo \n" +
            "INNER JOIN scriva_d_comune sdc ON sdc.id_comune = sruo.id_comune \n" +
            "INNER JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "INNER JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \n" +
            "INNER JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione \n";

    private static final String QUERY_LOAD_UBICAZIONE_OGGETTO = QUERY_LOAD_UBICAZIONI_OGGETTI + "  WHERE id_ubica_oggetto = :idUbicaOggetto";

    private static final String QUERY_LOAD_UBICAZIONE_OGGETTO_BY_ID_OGGETTO = QUERY_LOAD_UBICAZIONI_OGGETTI + "  WHERE id_oggetto = :idOggetto";

    private static final String QUERY_INSERT_UBICAZIONE_OGGETTO = "INSERT INTO _replaceTableName_ " +
            "(id_ubica_oggetto, id_oggetto, id_comune, den_indirizzo, num_civico, des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES (nextval('seq_scriva_r_ubica_oggetto'), :idOggetto, :idComune, :denIndirizzo, :numCivico, :desLocalita,  :indGeoProvenienza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_UBICAZIONE_OGGETTO = "UPDATE _replaceTableName_ " +
            "SET id_oggetto=:idOggetto, id_comune=:idComune, den_indirizzo=:denIndirizzo, num_civico=:numCivico, des_localita=:desLocalita, ind_geo_provenienza=:indGeoProvenienza, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd " +
            "WHERE id_ubica_oggetto = :idUbicaOggetto";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO = "DELETE FROM _replaceTableName_ WHERE gest_uid = :gestUID";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_BY_ID = "DELETE FROM _replaceTableName_ WHERE id_ubica_oggetto = :idUbicaOggetto";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_BY_ID_OGGETTO = "DELETE FROM _replaceTableName_ WHERE id_oggetto = :idOggetto";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_BY_UID_OGGETTO = "DELETE FROM _replaceTableName_ WHERE id_oggetto = (SELECT id_oggetto FROM scriva_t_oggetto WHERE gest_uid = :gestUID)";

    private static final String QUERY_DELETE_UBICAZIONE_OGGETTO_BY_NOT_IN_ID_UBICAZIONI = "DELETE FROM _replaceTableName_ WHERE id_oggetto = :idOggetto AND id_ubica_oggetto NOT IN (:idUbicazioniOggetto)";

    private static final String QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO = "INSERT INTO _replaceTableName_\n" +
            "(id_ubica_oggetto_storico, id_ubica_oggetto, id_oggetto, id_comune, den_indirizzo, num_civico,\n " +
            "des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES (nextval('seq_scriva_s_ubica_oggetto'), :idUbicaOggetto, :idOggetto, :idComune, :denIndirizzo, :numCivico,\n" +
            ":desLocalita, :indGeoProvenienza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_COPY_UBICAZIONE_OGGETTO_BY_ID_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_ubica_oggetto, id_oggetto, id_comune, den_indirizzo, num_civico, des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_r_ubica_oggetto'), :idOggetto, id_comune, den_indirizzo, num_civico, des_localita, ind_geo_provenienza, now(), gest_attore_upd, now(), gest_attore_upd, gen_random_uuid()\n" +
            "FROM scriva.scriva_r_ubica_oggetto_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_INSERT_STORICO_BY_ID_OGGETTO = "INSERT INTO _replaceTableName_ \n" +
            "(id_ubica_oggetto_storico, id_ubica_oggetto, id_oggetto, id_comune, den_indirizzo, num_civico,\n" +
            "des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_ubica_oggetto'), id_ubica_oggetto, id_oggetto, id_comune, den_indirizzo, num_civico,\n" +
            "des_localita, ind_geo_provenienza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_ubica_oggetto\n" +
            "WHERE id_oggetto = :idOggetto\n";

    /**
     * Load ubicazioni oggetti list.
     *
     * @return List<UbicazioneOggettoExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoExtendedDTO> loadUbicazioniOggetti() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONI_OGGETTI, null);
    }

    /**
     * Load ubicazione oggetto list.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return List<UbicazioneOggettoExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoExtendedDTO> loadUbicazioneOggetto(Long idUbicaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idUbicaOggetto", idUbicaOggetto);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONE_OGGETTO, map);
    }

    /**
     * Load ubicazione oggetto by id oggetto list.
     *
     * @param idOggetto idOggetto
     * @return List<UbicazioneOggettoExtendedDTO> list
     */
    @Override
    public List<UbicazioneOggettoExtendedDTO> loadUbicazioneOggettoByIdOggetto(Long idOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggetto", idOggetto);
        return findListByQuery(className, QUERY_LOAD_UBICAZIONE_OGGETTO_BY_ID_OGGETTO, map);
    }

    /**
     * Find by pk ubicazione oggetto dto.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return UbicazioneOggettoDTO ubicazione oggetto dto
     */
    @Override
    public UbicazioneOggettoDTO findByPK(Long idUbicaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idUbicaOggetto", idUbicaOggetto);
        return findByPK(className, map);
    }

    /**
     * Save ubicazione oggetto long.
     *
     * @param dto UbicazioneOggettoDTO
     * @return id record inserito
     */
    @Override
    public Long saveUbicazioneOggetto(UbicazioneOggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idOggetto", dto.getIdOggetto());
            map.put("idComune", dto.getIdComune());
            map.put("denIndirizzo", dto.getDenIndirizzo());
            map.put("numCivico", dto.getNumCivico());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("indGeoProvenienza", dto.getIndGeoProvenienza());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getGestAttoreIns() + dto.getIdComune().toString() + dto.getIdOggetto().toString() + now));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_UBICAZIONE_OGGETTO, null, null), params, keyHolder, new String[]{"id_ubica_oggetto"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                map.put("idUbicaOggetto", key.longValue());
                params = getParameterValue(map);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO), params);
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
     * Update ubicazione oggetto integer.
     *
     * @param dto UbicazioneOggettoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateUbicazioneOggetto(UbicazioneOggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            UbicazioneOggettoDTO ubicazioneOggetto = this.findByPK(dto.getIdUbicazioneOggetto());
            if (null == ubicazioneOggetto) {
                LOGGER.error("[UbicazioneOggettoDAOImpl::updateUbicazioneOggetto] Record non trovato con id [" + dto.getIdOggetto() + "]");
                return -1;
            }
            map.put("idUbicaOggetto", dto.getIdUbicazioneOggetto());
            map.put("idOggetto", dto.getIdOggetto());
            map.put("idComune", dto.getIdComune());
            map.put("denIndirizzo", dto.getDenIndirizzo());
            map.put("numCivico", dto.getNumCivico());
            map.put("desLocalita", dto.getDesLocalita());
            map.put("indGeoProvenienza", dto.getIndGeoProvenienza());
            map.put("gestDataIns", ubicazioneOggetto.getGestDataIns());
            map.put("gestAttoreIns", ubicazioneOggetto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", ubicazioneOggetto.getGestUID());

            MapSqlParameterSource params = getParameterValue(map);
            int returnValue = template.update(getQuery(QUERY_UPDATE_UBICAZIONE_OGGETTO, null, null), params);
            if (returnValue > 0) {
                template.update(getQueryStorico(QUERY_INSERT_STORICO_UBICAZIONE_OGGETTO), params);
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
     * Delete ubicazione oggetto integer.
     *
     * @param gestUID gestUID
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggetto(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto by id integer.
     *
     * @param idUbicaOggetto idUbicaOggetto
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoById(Long idUbicaOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idUbicaOggetto", idUbicaOggetto);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_BY_ID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto by id oggetto integer.
     *
     * @param idOggetto idOggetto
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioneOggettoByIdOggetto(Long idOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_BY_ID_OGGETTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazione oggetto by uid oggetto integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteUbicazioneOggettoByUidOggetto(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUID", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_BY_UID_OGGETTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete ubicazioni oggetto by not in id ubicazioni oggetto integer.
     *
     * @param idOggetto           idOggetto
     * @param idUbicazioniOggetto idUbicazioniOggetto
     * @return numero record cancellati
     */
    @Override
    public Integer deleteUbicazioniOggettoByNotInIdUbicazioniOggetto(Long idOggetto, List<Long> idUbicazioniOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggetto", idOggetto);
            map.put("idUbicazioniOggetto", idUbicazioniOggetto);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_UBICAZIONE_OGGETTO_BY_NOT_IN_ID_UBICAZIONI, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Copy ubicazioni oggetto by id oggetto istanza long.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @param idOggetto        the id oggetto
     * @return the long
     */
    @Override
    public Integer copyUbicazioniOggettoByIdOggettoIstanza(Long idOggettoIstanza, Long idOggetto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            map.put("idOggetto", idOggetto);
            MapSqlParameterSource params = getParameterValue(map);
            int returnValue = template.update(getQuery(QUERY_COPY_UBICAZIONE_OGGETTO_BY_ID_OGGETTO_ISTANZA, null, null), params);
            if (returnValue > 0) {
                template.update(getQueryStorico(QUERY_INSERT_STORICO_BY_ID_OGGETTO), params);
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
     * @return RowMapper<UbicazioneOggettoDTO>
     */
    @Override
    public RowMapper<UbicazioneOggettoDTO> getRowMapper() throws SQLException {
        return new UbicazioneOggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<UbicazioneOggettoExtendedDTO>
     */
    @Override
    public RowMapper<UbicazioneOggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new UbicazioneOggettoExtendedRowMapper();
    }

    /**
     * The type Ubicazione oggetto extended row mapper.
     */
    public static class UbicazioneOggettoExtendedRowMapper implements RowMapper<UbicazioneOggettoExtendedDTO> {

        /**
         * Instantiates a new Ubicazione oggetto extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public UbicazioneOggettoExtendedRowMapper() throws SQLException {
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
        public UbicazioneOggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UbicazioneOggettoExtendedDTO bean = new UbicazioneOggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, UbicazioneOggettoExtendedDTO bean) throws SQLException {
            bean.setIdUbicazioneOggetto(rs.getLong("id_ubica_oggetto"));
            bean.setIdOggetto(rs.getLong("id_oggetto"));
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
     * The type Ubicazione oggetto row mapper.
     */
    public static class UbicazioneOggettoRowMapper implements RowMapper<UbicazioneOggettoDTO> {

        /**
         * Instantiates a new Ubicazione oggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public UbicazioneOggettoRowMapper() throws SQLException {
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
        public UbicazioneOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UbicazioneOggettoDTO bean = new UbicazioneOggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, UbicazioneOggettoDTO bean) throws SQLException {
            bean.setIdUbicazioneOggetto(rs.getLong("id_ubica_oggetto"));
            bean.setIdOggetto(rs.getLong("id_oggetto"));
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

}