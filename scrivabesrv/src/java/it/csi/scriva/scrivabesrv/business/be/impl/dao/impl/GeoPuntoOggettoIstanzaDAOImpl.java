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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPuntoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.GeoPuntoOggettoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Geo punto oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class GeoPuntoOggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<GeoPuntoOggettoIstanzaDTO> implements GeoPuntoOggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_geo_punto_ogg_istanza = :idGeoAreaOggIstanza";

    private static final String QUERY_LOAD_GEO_PUNTO = "SELECT * FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_LOAD_GEO_PUNTO_BY_ID_GEOMETRICO = "SELECT * FROM _replaceTableName_ WHERE id_geometrico = :idGeometrico";

    private static final String QUERY_INSERT = "INSERT INTO scriva_geo_punto_ogg_istanza\n" +
            "(id_geo_punto_ogg_istanza, id_oggetto_istanza, id_geometrico, id_virtuale, des_geeco, json_geo_feature, geometria,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES\n" +
            "(nextval('seq_scriva_geo_punto_ogg_istanza'), :idOggettoIstanza, :idGeometrico, :idVirtuale, :desGeeco, cast(:jsonGeoFeature as jsonb), public.ST_SetSRID(public.ST_GeomFromGeoJSON(:geometria),32632),\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid)";

    private static final String QUERY_UPDATE = "UPDATE scriva_geo_punto_ogg_istanza\n" +
            "SET des_geeco = :desGeeco, json_geo_feature = cast(:jsonGeoFeature as jsonb), geometria = public.ST_SetSRID(public.ST_GeomFromGeoJSON(:geometria),32632), gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE id_geometrico = :idGeometrico";

    private static final String QUERY_COPY_FROM_ID_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_ \n" +
            "(id_geo_punto_ogg_istanza, id_oggetto_istanza, id_geometrico, id_virtuale, des_geeco, json_geo_feature, geometria, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_geo_punto_ogg_istanza'), :idOggettoIstanzaNew, nextval('seq_scriva_geo_id_geometrico'), id_virtuale, des_geeco, json_geo_feature, geometria, :gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid\n" +
            "FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanzaToCopy";

    private static final String QUERY_DELETE_GEO_PUNTO_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = (SELECT id_oggetto_istanza FROM scriva_t_oggetto_istanza WHERE gest_uid = :uidOggettoIstanza)";

    /**
     * Load geo punti oggetti istanza list.
     *
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntiOggettiIstanza() {
        return new ArrayList<>();
    }

    /**
     * Load geo punto oggetto istanza list.
     *
     * @param idGeoPuntoOggettoIstanza idGeoPuntoOggettoIstanza
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanza(Long idGeoPuntoOggettoIstanza) {
        return new ArrayList<>();
    }

    /**
     * Load geo punto oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_GEO_PUNTO, map);
    }

    /**
     * Load geo punto oggetto istanza by id geometrico list.
     *
     * @param idGeometrico idGeometrico
     * @return List<GeoPuntoOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoPuntoOggettoIstanzaDTO> loadGeoPuntoOggettoIstanzaByIdGeometrico(Long idGeometrico) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idGeometrico", idGeometrico);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_GEO_PUNTO_BY_ID_GEOMETRICO, map);
    }

    /**
     * Save geo punto oggetto istanza long.
     *
     * @param dto GeoPuntoOggettoIstanzaDTO
     * @return id record inserito
     */
    @Override
    public Long saveGeoPuntoOggettoIstanza(GeoPuntoOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("idGeometrico", dto.getIdGeometrico());
            map.put("idVirtuale", dto.getIdVirtuale());
            map.put("desGeeco", dto.getDesGeeco());
            map.put("jsonGeoFeature", dto.getJsonGeoFeature());
            map.put("geometria", dto.getGeometry());
            map.put("gestDataIns", dto.getGestDataIns());
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            Date now = Calendar.getInstance().getTime();
            map.put("gestUid", generateGestUID(String.valueOf(dto.getIdOggettoIstanza()) + now));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(QUERY_INSERT, params, keyHolder, new String[]{"id_geo_punto_ogg_istanza"});

            Number key = keyHolder.getKey();
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return 0L;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update geo punto oggetto istanza integer.
     *
     * @param dto GeoPuntoOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateGeoPuntoOggettoIstanza(GeoPuntoOggettoIstanzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("desGeeco", dto.getDesGeeco());
            map.put("jsonGeoFeature", dto.getJsonGeoFeature());
            map.put("geometria", dto.getGeometry());
            map.put("gestDataUpd", dto.getGestDataUpd());
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("idGeometrico", dto.getIdGeometrico());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(QUERY_UPDATE, params);
        } catch (Exception e) {
            logError(className, e);
            return 0;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete geo punto oggetto istanza.
     *
     * @param gestUID gestUID
     */
    @Override
    public void deleteGeoPuntoOggettoIstanza(String gestUID) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete geo punto oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteGeoPuntoOggettoIstanzaByUIDOggettoIstanza(String uidOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uidOggettoIstanza", uidOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_GEO_PUNTO_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save copy geo punto oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    idOggettoIstanzaNew
     * @param idOggettoIstanzaToCopy idOggettoIstanzaToCopy
     * @param gestDataIns            gestDataIns
     * @param gestAttoreIns          gestAttoreIns
     * @return id record inserito
     */
    @Override
    public Long saveCopyGeoPuntoOggettoIstanza(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanzaNew", idOggettoIstanzaNew);
            map.put("idOggettoIstanzaToCopy", idOggettoIstanzaToCopy);
            map.put("gestDataIns", gestDataIns);
            map.put("gestAttoreIns", gestAttoreIns);
            map.put("gestUid", generateGestUID(String.valueOf(idOggettoIstanzaNew) + gestDataIns));
            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_COPY_FROM_ID_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_geo_punto_ogg_istanza"});

            Number key = keyHolder.getKeyList().size() > 1 ? (Number) keyHolder.getKeyList().get(0).get("id_geo_punto_ogg_istanza") : keyHolder.getKey();
            return key != null ? key.longValue() : null;
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
     * @return RowMapper<GeoPuntoOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoPuntoOggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new GeoPuntoOggettoIstanzaDTOMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<GeoPuntoOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoPuntoOggettoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new GeoPuntoOggettoIstanzaDTOMapper();
    }

    /**
     * The type Geo punto oggetto istanza dto mapper.
     */
    public static class GeoPuntoOggettoIstanzaDTOMapper implements RowMapper<GeoPuntoOggettoIstanzaDTO> {

        /**
         * Instantiates a new Geo punto oggetto istanza dto mapper.
         *
         * @throws SQLException the sql exception
         */
        public GeoPuntoOggettoIstanzaDTOMapper() throws SQLException {
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
        public GeoPuntoOggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeoPuntoOggettoIstanzaDTO bean = new GeoPuntoOggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GeoPuntoOggettoIstanzaDTO bean) throws SQLException {
            bean.setDesGeeco(rs.getString("des_geeco"));
            bean.setGeometry(rs.getString("geometria"));
            bean.setIdGeometrico(rs.getLong("id_geometrico"));
            bean.setIdGeoPuntoOggettoIstanza(rs.getLong("id_geo_punto_ogg_istanza"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdVirtuale(rs.getLong("id_virtuale"));
            bean.setJsonGeoFeature(rs.getString("json_geo_feature"));
        }
    }

}