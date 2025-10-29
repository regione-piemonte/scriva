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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.GeoPointConverterDAO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.dto.GeoPointConverterDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GeoPointConverterDAOImpl extends ScrivaBeSrvGenericDAO<GeoPointConverterDTO> implements GeoPointConverterDAO {

    private final String className = this.getClass().getSimpleName();

    private String QUERY_CONVERTER = "SELECT public.ST_AsText(public.ST_Transform(public.ST_GeomFromText('POINT(:x :y)', :from),:to)) as point";

    private static String QUERY_CONVERT_GEOM_POLYG_OLD = "SELECT ST_AsGeoJSON(buffered.geometria) as buff_geo\n" +
            "FROM (\n" +
            "    SELECT ST_buffer(ST_SetSRID(ST_GeomFromGeoJSON(:geometryGeoJSON),32632)::geometry, 1) AS geometria\n" +
            ") AS buffered";

    private static String QUERY_CONVERT_GEOM_POLYG = "SELECT public.ST_AsGeoJSON(buffered.geometria) as buff_geo\n" +
            "FROM (\n" +
            "    SELECT public.ST_buffer(public.ST_SetSRID(public.ST_GeomFromGeoJSON(:geometryGeoJSON),32632)::public.geometry, 1) AS geometria\n" +
            ") AS buffered";

//    private static final String QUERY_CONVERTER = "SELECT ST_Transform(ST_GeomFromText('POINT(7.3894 45.1223)',4326),32632);";

    /**
     * Convert GeoPointConverterDTO list.
     *
     * @return GeoPointConverterDTO point ocnverted
     */

    @Override
    public List<GeoPointConverterDTO> convert(GeoPointConverterDTO source) {
        logBegin(className);
        QUERY_CONVERTER = StringUtils.replace(QUERY_CONVERTER,":x", source.getSourceLAT().toString() );
        QUERY_CONVERTER = StringUtils.replace(QUERY_CONVERTER,":y", source.getSourceLON().toString() );
        QUERY_CONVERTER = StringUtils.replace(QUERY_CONVERTER,":from", source.getSourceEPSG().toString() );
        QUERY_CONVERTER = StringUtils.replace(QUERY_CONVERTER,":to", source.getTargetEPSG().toString() );
        return findSimpleDTOListByQuery(className, QUERY_CONVERTER, null);
    }

    /**
     * Convert multipoint to polygon string.
     *
     * @param geometry the geometry
     * @return the string
     */
    @Override
    public String convertGeometryToPolygon(String geometry) {
        logBegin(className);
        List<String> result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("geometryGeoJSON", geometry);
            MapSqlParameterSource params = getParameterValue(map);
            result = template.query(getQuery(QUERY_CONVERT_GEOM_POLYG, null, null), params, new StringRowMapper("buff_geo"));
        } catch (Exception e) {
            logError(className, e);
        } finally {
            logEnd(className);
        }
        return CollectionUtils.isNotEmpty(result) ? result.get(0) : null;
    }


    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>       row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<GeoPointConverterDTO> getRowMapper() throws SQLException {
        return new GeoPointConverterRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>       extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<GeoPointConverterDTO> getExtendedRowMapper() throws SQLException {
        return null;
    }

    /**
     * The type tipologia oggetto row mapper.
     */
    public static class GeoPointConverterRowMapper implements RowMapper<GeoPointConverterDTO> {
        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public GeoPointConverterRowMapper() throws SQLException {
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
        public GeoPointConverterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GeoPointConverterDTO bean = new GeoPointConverterDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, GeoPointConverterDTO bean) throws SQLException {
            String point = rs.getString("point");

            if (null != point) {
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+)" );
                Matcher matcher = pattern.matcher(point);
                List<String> coordinates = matcher.results().map(MatchResult::group).collect(Collectors.toList());
                bean.setTargetLAT(new BigDecimal(coordinates.get(0)));
                bean.setTargetLON(new BigDecimal(coordinates.get(1)));
            }
        }
    }


}