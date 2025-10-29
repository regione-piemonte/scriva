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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoOggettoIstanzaDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.GeoOggettoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Geo oggetto istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class GeoOggettoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<GeoOggettoIstanzaDTO> implements GeoOggettoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "select * from _replaceTableName_ where id_preferenza = :idPreferenza";

    private static final String QUERY_SELECT_NEXT_ID_GEOMETRICO = "select nextval('seq_scriva_geo_id_geometrico')";

    private static final String QUERY_DELETE_GEO_PUNTO_OGGETTO_ISTANZA = "delete from scriva_geo_punto_ogg_istanza where id_geometrico = :idGeometrico";
    private static final String QUERY_DELETE_GEO_LINEA_OGGETTO_ISTANZA = "delete from scriva_geo_linea_ogg_istanza where id_geometrico = :idGeometrico";
    private static final String QUERY_DELETE_GEO_AREA_OGGETTO_ISTANZA = "delete from scriva_geo_area_ogg_istanza where id_geometrico = :idGeometrico";


    private static final String QUERY_SELECT_OGGETTI = "select id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria,\r\n" +
            "    des_geeco, json_geo_feature from scriva_geo_punto_ogg_istanza \r\n" +
            "   where  id_oggetto_istanza = :idOggettoIstanza" +
            "    union\r\n" +
            "   select id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria,\r\n" +
            "    des_geeco, json_geo_feature from scriva_geo_linea_ogg_istanza \r\n" +
            "   where   id_oggetto_istanza = :idOggettoIstanza" +
            "    union \r\n" +
            "   select id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria,\r\n" +
            "    des_geeco, json_geo_feature from scriva_geo_area_ogg_istanza \r\n" +
            "   where id_oggetto_istanza = :idOggettoIstanza";

    private static final String QUERY_SELECT_OGGETTI_FROM_LIST = "SELECT id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria, des_geeco, json_geo_feature\n" +
            "FROM scriva_geo_punto_ogg_istanza\n" +
            "WHERE id_virtuale = :idVirtuale AND id_oggetto_istanza IN (:idOggettoIstanzaList)\n" +
            "UNION\n" +
            "SELECT id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria, des_geeco, json_geo_feature\n" +
            "FROM scriva_geo_linea_ogg_istanza\n" +
            "WHERE id_virtuale = :idVirtuale AND id_oggetto_istanza IN (:idOggettoIstanzaList)\n" +
            "UNION\n" +
            "SELECT id_geometrico, public.ST_AsGeoJSON(geometria, 6, 0) as json_geo_geometria, des_geeco, json_geo_feature\n" +
            "FROM scriva_geo_area_ogg_istanza\n" +
            "WHERE id_virtuale = :idVirtuale AND id_oggetto_istanza IN (:idOggettoIstanzaList)\n";

    private static final String QUERY_LOAD_COORDINATE_BY_ID_OGGETTO_ISTANZA = "SELECT public.st_asgeojson(public.ST_Union(geometria))::jsonb->'coordinates' coordinates\n" +
            "FROM (\n" +
            "SELECT geometria\n" +
            "FROM scriva_geo_area_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            "UNION\n" +
            "SELECT public.ST_buffer(geometria, 1) AS geometria\n" +
            "FROM scriva_geo_linea_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            "UNION\n" +
            "SELECT public.ST_buffer(geometria, 1) AS geometria\n" +
            "FROM scriva_geo_punto_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            ") AS geo_json";

    private static final String QUERY_LOAD_GML_GEOMETRY_BY_ID_OGGETTO_ISTANZA = "SELECT public.ST_AsGML(public.ST_Union(geometria), 32632) AS gml\n" +
            "FROM (\n" +
            "SELECT public.ST_Union(geometria) AS geometria\n" +
            "FROM scriva_geo_area_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            "UNION\n" +
            "SELECT public.ST_Union(public.ST_buffer(geometria, 1)) AS geometria\n" +
            "FROM scriva_geo_linea_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            "UNION\n" +
            "SELECT public.ST_Union(public.ST_buffer(geometria, 1)) AS geometria\n" +
            "FROM scriva_geo_punto_ogg_istanza\n" +
            "WHERE id_oggetto_istanza = :idOggettoIstanza\n" +
            ") AS geo";

    /**
     * Gets oggetti.
     *
     * @param idVirtuale       idVirtuale
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<GeoOggettoIstanzaDTO> oggetti
     */
    @Override
    public List<GeoOggettoIstanzaDTO> getOggetti(Long idVirtuale, Long idOggettoIstanza) {
        logBegin(className);
        List<Long> idOggettoIstanzaList = new ArrayList<>();
        idOggettoIstanzaList.add(idOggettoIstanza);
        return getOggetti(idVirtuale, idOggettoIstanzaList);
    }

    /**
     * Gets oggetti.
     *
     * @param idVirtuale           the id virtuale
     * @param idOggettoIstanzaList the id oggetto istanza list
     * @return the oggetti
     */
    @Override
    public List<GeoOggettoIstanzaDTO> getOggetti(Long idVirtuale, List<Long> idOggettoIstanzaList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idVirtuale", idVirtuale);
        map.put("idOggettoIstanzaList", idOggettoIstanzaList);
        return findSimpleDTOListByQuery(className, QUERY_SELECT_OGGETTI_FROM_LIST, map);
    }


    @Override
    public List<GeoOggettoIstanzaDTO> getOggetti(Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
      
        map.put("idOggettoIstanza", idOggettoIstanza);
        return findSimpleDTOListByQuery(className, QUERY_SELECT_OGGETTI, map);
    }
    /**
     * Gets next id geometrico.
     *
     * @return sequence successiva per idGeometrico
     */
    @Override
    public Long getNextIdGeometrico() {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource params = getParameterValue(map);
        return template.queryForObject(QUERY_SELECT_NEXT_ID_GEOMETRICO, params, Long.class);
    }

    /**
     * Delete oggetto istanza integer.
     *
     * @param idGeometrico idGeometrico
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoIstanza(Long idGeometrico) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idGeometrico", idGeometrico);
            MapSqlParameterSource params = getParameterValue(map);
            int ret = template.update(QUERY_DELETE_GEO_PUNTO_OGGETTO_ISTANZA, params);
            if (ret > 0) {
                return ret;
            } else {
                ret = template.update(QUERY_DELETE_GEO_LINEA_OGGETTO_ISTANZA, params);
                if (ret > 0) {
                    return ret;
                } else {
                    return template.update(QUERY_DELETE_GEO_AREA_OGGETTO_ISTANZA, params);
                }
            }
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Load coordinate geo aree by oggetto istanza list.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
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

    /**
     * Load gml geometry by id oggetto istanza string.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the string
     */
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
     * @return RowMapper<GeoOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoOggettoIstanzaDTO> getRowMapper() throws SQLException {
        return new GeoOggIstanzaDTORowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<GeoOggettoIstanzaDTO>
     */
    @Override
    public RowMapper<GeoOggettoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new GeoOggIstanzaDTORowMapper();
    }


    /**
     * The type Geo ogg istanza dto row mapper.
     */
    public static class GeoOggIstanzaDTORowMapper implements RowMapper<GeoOggettoIstanzaDTO> {

        /**
         * Instantiates a new Geo ogg istanza dto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public GeoOggIstanzaDTORowMapper() throws SQLException {
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
        public GeoOggettoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeoOggettoIstanzaDTO bean = new GeoOggettoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GeoOggettoIstanzaDTO bean) throws SQLException {
            bean.setDesGeeco(rs.getString("des_geeco"));
            bean.setIdGeometrico(rs.getLong("id_geometrico"));
            bean.setJsonGeoFeature(rs.getString("json_geo_feature"));
            bean.setJsonGeoGeometria(rs.getString("json_geo_geometria"));
        }
    }

}