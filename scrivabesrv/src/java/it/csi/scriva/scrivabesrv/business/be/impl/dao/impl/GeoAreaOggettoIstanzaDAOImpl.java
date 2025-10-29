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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoAreaOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.GeoAreaOggettoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Geo area oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class GeoAreaOggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<GeoAreaOggettoIstanzaDTO> implements GeoAreaOggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_geo_area_ogg_istanza = :idGeoAreaOggIstanza";
    private static final String QUERY_LOAD_GEO_AREA = "SELECT * FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanza";
    private static final String QUERY_LOAD_GEO_AREA_BY_ID_GEOMETRICO = "SELECT * FROM _replaceTableName_ WHERE id_geometrico = :idGeometrico";

    private static final String QUERY_INSERT = "INSERT INTO scriva_geo_area_ogg_istanza\n" +
            "(id_geo_area_ogg_istanza, id_oggetto_istanza, id_geometrico, id_virtuale, des_geeco, json_geo_feature, geometria,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES\n" +
            "(nextval('seq_scriva_geo_area_ogg_istanza'), :idOggettoIstanza, :idGeometrico, :idVirtuale, :desGeeco, cast(:jsonGeoFeature as jsonb), public.ST_SetSRID(public.ST_GeomFromGeoJSON(:geometria),32632),\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid)";

    private static final String QUERY_UPDATE = "UPDATE scriva_geo_area_ogg_istanza\n" +
            "SET des_geeco = :desGeeco, json_geo_feature = cast(:jsonGeoFeature as jsonb), geometria = public.ST_SetSRID(public.ST_GeomFromGeoJSON(:geometria),32632), gest_data_upd = :gestDataUpd, gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE id_geometrico = :idGeometrico";

    private static final String QUERY_COPY_FROM_ID_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_ " +
            "(id_geo_area_ogg_istanza, id_oggetto_istanza, id_geometrico, id_virtuale, des_geeco, json_geo_feature, geometria, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "SELECT nextval('seq_scriva_geo_area_ogg_istanza'), :idOggettoIstanzaNew, nextval('seq_scriva_geo_id_geometrico'), id_virtuale, des_geeco, json_geo_feature, geometria, :gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid " +
            "FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanzaToCopy";

    private static final String QUERY_DELETE_GEO_AREA_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza = (SELECT id_oggetto_istanza FROM scriva_t_oggetto_istanza WHERE gest_uid = :uidOggettoIstanza)";
    private static final String QUERY_LOAD_COORDINATE_BY_ID_OGGETTO_ISTANZA = "SELECT public.st_asgeojson(geometria)::jsonb->'coordinates' coordinates  \r\n" + 
    		"FROM (SELECT geometria FROM scriva_geo_area_ogg_istanza WHERE id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		"union \r\n" + 
    		"select st_buffer(geometria,0.1) from scriva_geo_punto_ogg_istanza sgpoi where id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		"union \r\n" + 
    		"select st_buffer(geometria,0.1) from scriva_geo_linea_ogg_istanza sgpoi where id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		") AS geo_json";//"SELECT public.st_asgeojson(public.ST_Union(geometria))::jsonb->'coordinates' coordinates  FROM (SELECT geometria FROM scriva_geo_area_ogg_istanza WHERE id_oggetto_istanza = :idOggettoIstanza ) AS geo_json";
    private static final String QUERY_LOAD_GML_GEOMETRY_BY_ID_OGGETTO_ISTANZA = "SELECT public.ST_AsGML(public.ST_Union(geometria), 32632) AS gml FROM scriva_geo_area_ogg_istanza WHERE id_oggetto_istanza = :idOggettoIstanza";
//    private static final String QUERY_LOAD_GML_GEOMETRY_BY_ID_OGGETTO_ISTANZA = "SELECT public.st_asgml(public.ST_Union(geometria), 32632) AS gml FROM scriva_geo_area_ogg_istanza WHERE id_oggetto_istanza = :idOggettoIstanza";
    private static final String QUERY_LOAD_COORDINATE_BY_ID_OGGETTO_ISTANZA_FOR_COMUNI = "SELECT public.st_asgeojson(geometria)\r\n" +  
    		"FROM (SELECT geometria FROM scriva_geo_area_ogg_istanza WHERE id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		"union \r\n" + 
    		"select st_buffer(geometria,0.1) from scriva_geo_punto_ogg_istanza sgpoi where id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		"union \r\n" + 
    		"select st_buffer(geometria,0.1) from scriva_geo_linea_ogg_istanza sgpoi where id_oggetto_istanza = :idOggettoIstanza\r\n" + 
    		") AS geo_json";

    /**
     * Load geo aree oggetti istanza list.
     *
     * @return List<GeoAreaOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoAreaOggettoIstanzaDTO> loadGeoAreeOggettiIstanza() {
        return new ArrayList<>();
    }

    /**
     * Load geo area oggetto istanza list.
     *
     * @param idGeoAreaOggettoIstanza idGeoAreaOggettoIstanza
     * @return List<GeoAreaOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanza(Long idGeoAreaOggettoIstanza) {
        return new ArrayList<>();
    }

    /**
     * Load geo area oggetto istanza by id oggetto istanza list.
     *
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoAreaOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_GEO_AREA, map);
    }

    /**
     * Load geo area oggetto istanza by id geometrico list.
     *
     * @param idGeometrico idGeometrico
     * @return List<GeoAreaOggettoIstanzaDTO> list
     */
    @Override
    public List<GeoAreaOggettoIstanzaDTO> loadGeoAreaOggettoIstanzaByIdGeometrico(Long idGeometrico) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idGeometrico", idGeometrico);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_GEO_AREA_BY_ID_GEOMETRICO, map);
    }

    /**
     * Save geo area oggetto istanza long.
     *
     * @param dto GeoAreaOggettoIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveGeoAreaOggettoIstanza(GeoAreaOggettoIstanzaDTO dto) {
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

            template.update(QUERY_INSERT, params, keyHolder, new String[]{"id_geo_area_ogg_istanza"});

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
     * Update geo area oggetto istanza integer.
     *
     * @param dto GeoAreaOggettoIstanzaDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateGeoAreaOggettoIstanza(GeoAreaOggettoIstanzaDTO dto) {
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
     * Delete geo area oggetto istanza.
     *
     * @param gestUID gestUID
     */
    @Override
    public void deleteGeoAreaOggettoIstanza(String gestUID) {
        // TODO Auto-generated method stub
    }

    /**
     * Save copy geo area oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    idOggettoIstanzaNew
     * @param idOggettoIstanzaToCopy idOggettoIstanzaToCopy
     * @param gestDataIns            gestDataIns
     * @param gestAttoreIns          gestAttoreIns
     * @return id record inserito
     */
    @Override
    public Long saveCopyGeoAreaOggettoIstanza(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns) {
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

            template.update(getQuery(QUERY_COPY_FROM_ID_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_geo_area_ogg_istanza"});

            Number key = keyHolder.getKeyList().size() > 1 ? (Number) keyHolder.getKeyList().get(0).get("id_geo_area_ogg_istanza") : keyHolder.getKey();
            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete geo area oggetto istanza by uid oggetto istanza integer.
     *
     * @param uidOggettoIstanza uidOggettoIstanza
     * @return numero record cancellati
     */
    @Override
    public Integer deleteGeoAreaOggettoIstanzaByUIDOggettoIstanza(String uidOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("uidOggettoIstanza", uidOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_DELETE_GEO_AREA_OGGETTO_ISTANZA_BY_UID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    @Override
    public List<String> loadCoordinateGeoAreeByOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_COORDINATE_BY_ID_OGGETTO_ISTANZA, null, null), params, new StringRowMapper("coordinates"));
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }
    
    @Override
    public List<String> loadCoordinateGeoAreeByOggettoIstanzaForComuni(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.query(getQuery(QUERY_LOAD_COORDINATE_BY_ID_OGGETTO_ISTANZA_FOR_COMUNI, null, null), params, new StringRowMapper("st_asgeojson"));
        } catch (Exception e) {
            logError(className, e);
            return Collections.emptyList();
        } finally {
            logEnd(className);
        }
    }


    @Override
    public String loadGMLGeometryByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        List<String> queryList = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            queryList = template.query(getQuery(QUERY_LOAD_GML_GEOMETRY_BY_ID_OGGETTO_ISTANZA, null, null), params, new StringRowMapper("gml"));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return queryList != null && !queryList.isEmpty() ? queryList.get(0) : null;
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
     * @return RowMapper<GeoAreaOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoAreaOggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new GeoAreaOggettoIstanzaDTOMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<GeoAreaOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoAreaOggettoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new GeoAreaOggettoIstanzaDTOMapper();
    }

    /**
     * The type Geo area oggetto istanza dto mapper.
     */
    public static class GeoAreaOggettoIstanzaDTOMapper implements RowMapper<GeoAreaOggettoIstanzaDTO> {

        /**
         * Instantiates a new Geo area oggetto istanza dto mapper.
         *
         * @throws SQLException the sql exception
         */
        public GeoAreaOggettoIstanzaDTOMapper() throws SQLException {
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
        public GeoAreaOggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeoAreaOggettoIstanzaDTO bean = new GeoAreaOggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GeoAreaOggettoIstanzaDTO bean) throws SQLException {
            bean.setDesGeeco(rs.getString("des_geeco"));
            bean.setGeometry(rs.getString("geometria"));
            bean.setIdGeometrico(rs.getLong("id_geometrico"));
            bean.setIdGeoAreaOggettoIstanza(rs.getLong("id_geo_area_ogg_istanza"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdVirtuale(rs.getLong("id_virtuale"));
            bean.setJsonGeoFeature(rs.getString("json_geo_feature"));
        }
    }

}