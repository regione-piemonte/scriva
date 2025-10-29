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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaResponsabiliDAO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaResponsabileExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoResponsabileDTO;
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
 * The type Istanza responsabile dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaResponsabiliDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaResponsabileDTO> implements IstanzaResponsabiliDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_ISTANZA_RESPONSABILI = "SELECT srir.*, sdtr.* \n" +
            "FROM _replaceTableName_ srir \n" +
            "INNER JOIN scriva_d_tipo_responsabile sdtr ON srir.id_tipo_responsabile = sdtr.id_tipo_responsabile";

    private static final String QUERY_LOAD_ISTANZA_RESPONSABILI_BY_ISTANZA = QUERY_LOAD_ISTANZA_RESPONSABILI + " WHERE srir.id_istanza = :idIstanza";

    private static final String QUERY_INSERT_ISTANZA_RESPONSABILE = "INSERT INTO _replaceTableName_ \n"
            + "(id_istanza_responsabile, id_istanza, id_tipo_responsabile, label_responsabile, nominativo_responsabile, recapito_responsabile, \n"
            + "flg_riservato, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n"
            + "VALUES(nextval('seq_scriva_r_istanza_responsabile'), :idIstanza, :idTipoResponsabile, :labelResponsabile, :nominativoResponsabile, :recapitoResponsabile, \n"
            + ":flgRiservato, :dateIns, :attoreIns, :dateUpd, :attoreUpd, :uid)\n";

    private static final String QUERY_UPDATE_ISTANZA_RESPONSABILE = "UPDATE _replaceTableName_\n"
            + "SET id_istanza=:idIstanza, id_tipo_responsabile=:idTipoResponsabile, label_responsabile=:labelResponsabile,\n"
            + "gest_data_upd=:dateUpd, gest_attore_upd=:attoreUpd,\n"
            + "nominativo_responsabile=:nominativoResponsabile, recapito_responsabile=:recapitoResponsabile, flg_riservato=:flgRiservato \n"
            + "WHERE id_istanza_responsabile = :idIstanzaResponsabile";

    private static final String QUERY_DELETE_ISTANZA_RESPONSABILE = "DELETE FROM _replaceTableName_ \n";

    private static final String QUERY_LOAD_ISTANZA_RESPONSABILI_BY_ID_COMPETENZA_TERRITORIO = "";

    private static final String WHERE_GESTUID = "WHERE gest_uid = :gestUid\n";

    private static final String WHERE_ID_IST_RESP_LIST = "WHERE id_istanza_responsabile IN (:idIstanzaResponsabileList)\n";

    /**
     * Load istanza responsabili by id istanza.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaResponsabileExtendedDTO>     list
     */
    @Override
    public List<IstanzaResponsabileExtendedDTO> loadIstanzaResponsabiliByIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_RESPONSABILI_BY_ISTANZA, map);
    }

    /**
     * Save istanza long.
     *
     * @param dto IstanzaResponsabileDTO
     * @return id record salvato
     */
    @Override
    public Long saveIstanzaResponsabile(IstanzaResponsabileDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();

            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idTipoResponsabile", dto.getIdTipoResponsabile());
            map.put("labelResponsabile", dto.getLabelResponsabile());
            map.put("nominativoResponsabile", dto.getNominativoResponsabile());
            map.put("recapitoResponsabile", dto.getRecapitoResponsabile());
            map.put("flgRiservato", Boolean.TRUE.equals(dto.getFlgRiservato()) ? 0 : 1);//TODO verificare con il front end - soluzione provvisoria jira SCRIVA 1258
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());

            map.put("uid", generateGestUID(dto.getLabelResponsabile() + dto.getIdIstanza() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_ISTANZA_RESPONSABILE, null, null), params, keyHolder, new String[]{"id_istanza_responsabile"});
            Number key = keyHolder.getKey();

            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza long.
     *
     * @param dto IstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateIstanzaResponsabile(IstanzaResponsabileDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Long flgRis = 0L;
            Integer result;

            Date now = Calendar.getInstance().getTime();

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idIstanzaResponsabile", dto.getIdIstanzaResponsabile());
            map.put("idTipoResponsabile", dto.getIdTipoResponsabile());
            map.put("labelResponsabile", dto.getLabelResponsabile());
            map.put("nominativoResponsabile", dto.getNominativoResponsabile());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());
            map.put("recapitoResponsabile", dto.getRecapitoResponsabile() != null ? dto.getRecapitoResponsabile() : null);
            map.put("flgRiservato", Boolean.TRUE.equals(dto.getFlgRiservato()) ? 0 : 1); // TO DO verificare con il front end - soluzione provvisoria jira SCRIVA 1258
            MapSqlParameterSource params = getParameterValue(map);

            result = template.update(getQuery(QUERY_UPDATE_ISTANZA_RESPONSABILE, null, null), params);

            return result;
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load istanza responsabili by id competenza territorio.
     *
     * @param idCompetenzaTerritorio idCompetenzaTerritorio
     * @return List<IstanzaResponsabileExtendedDTO>     list
     */
    @Override
    public List<CompetenzaTerritorioResponsabileDTO> loadIstanzaResponsabiliByIdCompetenza(Long idCompetenzaTerritorio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
        return findListByQuery(className, QUERY_LOAD_ISTANZA_RESPONSABILI_BY_ID_COMPETENZA_TERRITORIO, map);
    }

    /**
     * Delete istanza responsabile integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteIstanzaResponsabile(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_RESPONSABILE + WHERE_GESTUID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete istanza responsabile integer.
     *
     * @param idIstanzaResponsabileList the id istanza responsabile list
     * @return the integer
     */
    @Override
    public Integer deleteIstanzaResponsabile(List<Long> idIstanzaResponsabileList) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanzaResponsabileList", idIstanzaResponsabileList);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_RESPONSABILE + WHERE_ID_IST_RESP_LIST, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
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
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaResponsabileDTO>
     */
    @Override
    public RowMapper<IstanzaResponsabileDTO> getRowMapper() throws SQLException {
        return new IstanzaResponsabileRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaStatoExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaResponsabileExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaResponsabileExtendedRowMapper();
    }

    private static class IstanzaResponsabileRowMapper implements RowMapper<IstanzaResponsabileDTO> {

        /**
         * Instantiates a new Istanza responsabile row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaResponsabileRowMapper() throws SQLException {
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

        public IstanzaResponsabileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaResponsabileDTO bean = new IstanzaResponsabileDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaResponsabileDTO bean) throws SQLException {
            bean.setIdIstanzaResponsabile(rs.getLong("id_istanza_responsabile"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
            bean.setLabelResponsabile(rs.getString("label_responsabile"));
            bean.setNominativoResponsabile(rs.getString("nominativo_responsabile"));
            bean.setRecapitoResponsabile(rs.getString("recapito_responsabile"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.FALSE : Boolean.TRUE); //TO DO verificare con il front end - soluzione provvisoria jira SCRIVA 1258

        }
    }


    private static class IstanzaResponsabileExtendedRowMapper implements RowMapper<IstanzaResponsabileExtendedDTO> {

        /**
         * Instantiates a new Istanza responsabile extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaResponsabileExtendedRowMapper() throws SQLException {
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

        public IstanzaResponsabileExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaResponsabileExtendedDTO bean = new IstanzaResponsabileExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaResponsabileExtendedDTO bean) throws SQLException {

            TipoResponsabileDTO tipoResponsabile = new TipoResponsabileDTO();
            populateBeanTipoResponsabile(rs, tipoResponsabile);
            bean.setTipoResponsabile(tipoResponsabile);
            bean.setIdIstanzaResponsabile(rs.getLong("id_istanza_responsabile"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setLabelResponsabile(rs.getString("label_responsabile"));
            bean.setNominativoResponsabile(rs.getString("nominativo_responsabile"));
            bean.setRecapitoResponsabile(rs.getString("recapito_responsabile"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.FALSE : Boolean.TRUE); //TO DO verificare con il front end - soluzione provvisoria jira SCRIVA 1258

        }

        private void populateBeanTipoResponsabile(ResultSet rs, TipoResponsabileDTO bean) throws SQLException {
            bean.setIdTipoResponsabile(rs.getLong("id_tipo_responsabile"));
            bean.setCodiceTipoResponsabile(rs.getString("cod_tipo_responsabile"));
            bean.setDescrizioneTipoResponsabile(rs.getString("des_tipo_responsabile"));

        }
    }


}