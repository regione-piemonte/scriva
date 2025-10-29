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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaEventoDAO;
import it.csi.scriva.scrivabesrv.dto.ComponenteAppDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaEventoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoEventoExtendedDTO;
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
 * The type Istanza evento dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaEventoDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaEventoDTO> implements IstanzaEventoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_CLAUSE = "ORDER BY data_evento";

    private static final String WHERE_COMPONENTE_APP = "AND sdte.ind_visibile LIKE '%'||:codComponenteApp||'%' \n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_";

    private static final String QUERY_ISTANZE_EVENTO = "SELECT * \n" +
            "FROM _replaceTableName_ srie \n" +
            "INNER JOIN scriva_d_tipo_evento sdte ON sdte.id_tipo_evento = srie.id_tipo_evento\n" +
            "LEFT JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sdte.id_stato_istanza_evento \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = srie.id_componente_app \n" +
            "WHERE 1 = 1 \n";

    private static final String QUERY_ISTANZA_EVENTO_BY_ID_ISTANZA = QUERY_ISTANZE_EVENTO + "AND srie.id_istanza = :idIstanza \n";

    private static final String QUERY_INSERT_ISTANZA_EVENTO = "INSERT INTO _replaceTableName_ " +
            "(id_istanza_evento, id_istanza, id_tipo_evento, data_evento, id_istanza_attore, id_funzionario, id_componente_app, \n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) \n" +
            "VALUES(nextval('seq_scriva_r_istanza_evento'), :idIstanza, :idTipoEvento, :dataEvento, :idIstanzaAttore, :idFunzionario, :idComponenteApp, \n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)\n";

    private static final String QUERY_UPDATE_ISTANZA_EVENTO = "UPDATE _replaceTableName_ " +
            "SET id_tipo_evento=:idTipoEvento, data_evento=:dataEvento, id_istanza_attore=:idIstanzaAttore, id_funzionario=:idFunzionario, id_componente_app=:idComponenteApp, \n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_istanza_evento=:idIstanzaEvento\n";

    private static final String QUERY_DELETE_ISTANZA_EVENTO = "DELETE FROM _replaceTableName_ WHERE gest_uid=:gestUid ";

    /**
     * Load istanze eventi list.
     *
     * @return the list
     */
    @Override
    public List<IstanzaEventoExtendedDTO> loadIstanzeEventi() {
        logBegin(className);
        return findListByQuery(className, QUERY_ISTANZE_EVENTO, null);
    }

    /**
     * Load istanza evento by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaEventoExtendedDTO> loadIstanzaEventoByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_ISTANZA_EVENTO_BY_ID_ISTANZA, map);
    }

    /**
     * Find by pk istanza evento dto.
     *
     * @param idIstanzaEvento the id istanza evento
     * @return the istanza evento dto
     */
    @Override
    public IstanzaEventoDTO findByPK(Long idIstanzaEvento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaEvento", idIstanzaEvento);
        return findListByQuery(className, QUERY_ISTANZA_EVENTO_BY_ID_ISTANZA, map);
    }

    /**
     * Save istanza evento long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveIstanzaEvento(IstanzaEventoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idTipoEvento", dto.getIdTipoEvento());
            map.put("dataEvento", dto.getDataEvento() != null ? dto.getDataEvento() : now);
            map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("idComponenteApp", dto.getIdComponenteApp());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());

            map.put("gestUid", generateGestUID(dto.getIdIstanza().toString() + dto.getIdTipoEvento() + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            int returnValue = template.update(getQuery(QUERY_INSERT_ISTANZA_EVENTO, null, null), params, keyHolder, new String[]{"id_istanza_evento"});
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
     * Update istanza evento integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateIstanzaEvento(IstanzaEventoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            IstanzaEventoDTO istanzaEventoOld = this.findByPK(dto.getIdIstanzaEvento());
            if (null == istanzaEventoOld) {
                logError(className, "Record non trovato con id [" + dto.getIdIstanzaEvento() + "]");
                return -1;
            }
            int returnValue = 1;
            if (!dto.equals(istanzaEventoOld)) {
                map.put("idIstanzaEvento", dto.getIdIstanzaEvento());
                map.put("idTipoEvento", dto.getIdTipoEvento());
                map.put("dataEvento", dto.getDataEvento());
                map.put("idIstanzaAttore", dto.getIdIstanzaAttore());
                map.put("idFunzionario", dto.getIdFunzionario());
                map.put("idComponenteApp", dto.getIdComponenteApp());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                MapSqlParameterSource params = getParameterValue(map);

                returnValue = template.update(getQuery(QUERY_UPDATE_ISTANZA_EVENTO, null, null), params);
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
     * Delete istanza evento integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteIstanzaEvento(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_EVENTO, null, null), params);
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
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaEventoDTO> getRowMapper() throws SQLException {
        return new IstanzaEventoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>   extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaEventoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaEventoExtendedRowMapper();
    }

    /**
     * The type Istanza evento row mapper.
     */
    public static class IstanzaEventoRowMapper implements RowMapper<IstanzaEventoDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaEventoRowMapper() throws SQLException {
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
        public IstanzaEventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaEventoDTO bean = new IstanzaEventoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaEventoDTO bean) throws SQLException {
            bean.setIdIstanzaEvento(rs.getLong("id_istanza_evento"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));
            bean.setDataEvento(rs.getTimestamp("data_evento"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Istanza evento extended row mapper.
     */
    public static class IstanzaEventoExtendedRowMapper implements RowMapper<IstanzaEventoExtendedDTO> {

        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaEventoExtendedRowMapper() throws SQLException {
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
        public IstanzaEventoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaEventoExtendedDTO bean = new IstanzaEventoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaEventoExtendedDTO bean) throws SQLException {
            bean.setIdIstanzaEvento(rs.getLong("id_istanza_evento"));
            bean.setIdIstanza(rs.getLong("id_istanza"));

            TipoEventoExtendedDTO tipoEvento = new TipoEventoExtendedDTO();
            populateBeanTipoEvento(rs, tipoEvento);
            bean.setTipoEvento(tipoEvento);

            bean.setDataEvento(rs.getTimestamp("data_evento"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));

            ComponenteAppDTO componenteApp = new ComponenteAppDTO();
            populateBeanComponentApp(rs, componenteApp);
            bean.setComponenteApp(componenteApp);

            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipoEvento(ResultSet rs, TipoEventoExtendedDTO bean) throws SQLException {
            bean.setIdTipoEvento(rs.getLong("id_tipo_evento"));

            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateBeanStatoIstanza(rs, statoIstanza);
            bean.setStatoIstanzaEvento(statoIstanza);

            bean.setCodTipoEvento(rs.getString("cod_tipo_evento"));
            bean.setDesTipoEvento(rs.getString("des_tipo_evento"));
            bean.setIdComponenteGestoreProcesso(rs.getLong("id_componente_gestore_processo"));
        }

        private void populateBeanStatoIstanza(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanComponentApp(ResultSet rs, ComponenteAppDTO bean) throws SQLException {
            bean.setIdComponenteApp(rs.getLong("id_componente_app"));
            bean.setCodComponenteApp(rs.getString("cod_componente_app"));
            bean.setDesComponenteApp(rs.getString("des_componente_app"));
        }

    }

}