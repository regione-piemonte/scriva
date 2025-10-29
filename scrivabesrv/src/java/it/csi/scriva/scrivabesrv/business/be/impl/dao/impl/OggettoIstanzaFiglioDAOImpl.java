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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaFiglioDAO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaFiglioDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Oggetto istanza figlio dao.
 *
 * @author CSI PIEMONTE
 */
public class OggettoIstanzaFiglioDAOImpl extends ScrivaBeSrvGenericDAO<OggettoIstanzaFiglioDTO> implements OggettoIstanzaFiglioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_OGG_IST_PADRE = "AND id_oggetto_istanza_padre = :idOggettoIstanzaPadre\n";

    private static final String WHERE_ID_OGG_IST_FIGLIO = "AND id_oggetto_istanza_figlio = :idOggettoIstanzaFiglio\n";

    private static final String WHERE_GEST_UID = "AND gest_uid = :gestUid\n";

    private static final String WHERE_OR_ID_KEYS = "AND id_oggetto_istanza_padre = :idOggettoIstanza\n" +
            "OR id_oggetto_istanza_figlio = :idOggettoIstanza";

    private static final String QUERY_LOAD_OGGETTI_ISTANZA_FIGLIO = "SELECT *\n" +
            "FROM _replaceTableName_\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_INSERT_OGGETTO_ISTANZA_FIGLIO = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_istanza_padre, id_oggetto_istanza_figlio, " +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(:idOggettoIstanzaPadre, :idOggettoIstanzaFiglio,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_OGGETTO_ISTANZA_FIGLIO = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_ist_figlio_storico, id_oggetto_istanza_padre, id_oggetto_istanza_figlio,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_oggetto_ist_figlio'), id_oggetto_istanza_padre, id_oggetto_istanza_figlio,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_oggetto_ist_figlio\n" +
            "WHERE 1=1\n" +
            WHERE_ID_OGG_IST_PADRE +
            WHERE_ID_OGG_IST_FIGLIO;

    private static final String QUERY_DELETE_OGGETTI_ISTANZA_FIGLIO = "DELETE FROM _replaceTableName_\n" +
            "WHERE 1 = 1\n"; 
    
    private static final String QUERY_LOAD_OGGETTO_ISTANZA_FIGLI_BY_UID = "SELECT * \n" +
            "FROM scriva_r_oggetto_ist_figlio sroif \n" +
            "WHERE id_oggetto_istanza_padre = (\n" +
            "    SELECT id_oggetto_istanza \n" +
            "    FROM scriva_t_oggetto_istanza stoi \n" +
            "    WHERE stoi.gest_uid = :gestUID\n" +
            ")";

    /**
     * Load oggetto istanza figlio by id oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggettoIstanza(Long idOggettoIstanza) {
        logBeginInfo(className, idOggettoIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findListByQuery(className, QUERY_LOAD_OGGETTI_ISTANZA_FIGLIO + WHERE_OR_ID_KEYS, map);
    }

    /**
     * Load oggetto istanza figlio by id ogg ist padre list.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the list
     */
    @Override
    public List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggIstPadre(Long idOggettoIstanzaPadre) {
        logBeginInfo(className, idOggettoIstanzaPadre);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanzaPadre", idOggettoIstanzaPadre);
        return findListByQuery(className, QUERY_LOAD_OGGETTI_ISTANZA_FIGLIO + WHERE_ID_OGG_IST_PADRE, map);
    }

    /**
     * Load oggetto istanza figlio by id ogg ist figlio list.
     *
     * @param idOggettoIstanzaFiglio the id oggetto istanza figlio
     * @return the list
     */
    @Override
    public List<OggettoIstanzaFiglioDTO> loadOggettoIstanzaFiglioByIdOggIstFiglio(Long idOggettoIstanzaFiglio) {
        logBeginInfo(className, idOggettoIstanzaFiglio);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanzaFiglio", idOggettoIstanzaFiglio);
        return findListByQuery(className, QUERY_LOAD_OGGETTI_ISTANZA_FIGLIO + WHERE_ID_OGG_IST_FIGLIO, map);
    }
    
    /**
     * Load oggetto istanza figli by uid list.
     *
     * @param gestUID gestUID
     * @return List<OggettoIstanzaExtendedDTO> list
     */
    @Override
    public List<OggettoIstanzaExtendedDTO> loadOggettiFigli(String gestUID) {
        logBeginInfo(className, (Object) gestUID);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUID", gestUID);
        return findListByQuery(className, QUERY_LOAD_OGGETTO_ISTANZA_FIGLI_BY_UID, map);
    }

    /**
     * Save oggetto istanza figlio long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveOggettoIstanzaFiglio(OggettoIstanzaFiglioDTO dto) {
        logBeginInfo(className, dto);
        try {
            Date now = Calendar.getInstance().getTime();

            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanzaPadre", dto.getIdOggettoIstanzaPadre());
            map.put("idOggettoIstanzaFiglio", dto.getIdOggettoIstanzaFiglio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID("" + dto.getIdOggettoIstanzaPadre() + dto.getIdOggettoIstanzaFiglio() + now));
            MapSqlParameterSource params = getParameterValue(map);
            int returnValue = template.update(getQuery(QUERY_INSERT_OGGETTO_ISTANZA_FIGLIO, null, null), params);

            if (returnValue > 0) {
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO_ISTANZA_FIGLIO), params);
            }

            return (long) returnValue;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza figlio integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaFiglio(String gestUID) {
        logBeginInfo(className, gestUID);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTI_ISTANZA_FIGLIO + WHERE_GEST_UID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza figlio by id ogg ist padre integer.
     *
     * @param idOggettoIstanzaPadre the id oggetto istanza padre
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaFiglioByIdOggIstPadre(Long idOggettoIstanzaPadre) {
        logBeginInfo(className, idOggettoIstanzaPadre);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanzaPadre", idOggettoIstanzaPadre);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTI_ISTANZA_FIGLIO + WHERE_ID_OGG_IST_PADRE, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza figlio by id ogg ist figlio integer.
     *
     * @param idOggettoIstanzaFiglio the id oggetto istanza figlio
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaFiglioByIdOggIstFiglio(Long idOggettoIstanzaFiglio) {
        logBeginInfo(className, idOggettoIstanzaFiglio);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanzaFiglio", idOggettoIstanzaFiglio);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTI_ISTANZA_FIGLIO + WHERE_ID_OGG_IST_FIGLIO, null, null), params);
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
        return getQuery(QUERY_LOAD_OGGETTI_ISTANZA_FIGLIO + WHERE_ID_OGG_IST_PADRE + WHERE_ID_OGG_IST_FIGLIO, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<OggettoIstanzaFiglioDTO> getRowMapper() throws SQLException {
        return new OggettoIstanzaFiglioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<OggettoIstanzaFiglioDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoIstanzaFiglioRowMapper();
    }

    public static class OggettoIstanzaFiglioRowMapper implements RowMapper<OggettoIstanzaFiglioDTO> {

        /**
         * Instantiates a new Oggetto istanza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaFiglioRowMapper() throws SQLException {
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
        public OggettoIstanzaFiglioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaFiglioDTO bean = new OggettoIstanzaFiglioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaFiglioDTO bean) throws SQLException {
            bean.setIdOggettoIstanzaPadre(rs.getLong("id_oggetto_istanza_padre"));
            bean.setIdOggettoIstanzaFiglio(rs.getLong("id_oggetto_istanza_figlio"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));

        }

    }

}