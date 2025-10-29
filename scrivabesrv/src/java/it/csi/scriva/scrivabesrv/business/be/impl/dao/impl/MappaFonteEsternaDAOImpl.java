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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.MappaFonteEsternaDAO;
import it.csi.scriva.scrivabesrv.dto.MappaFonteEsternaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo natura giuridica dao.
 *
 * @author CSI PIEMONTE
 */
public class MappaFonteEsternaDAOImpl extends ScrivaBeSrvGenericDAO<MappaFonteEsternaDTO> implements MappaFonteEsternaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_MAPPE_FONTE_ESTERNA = "SELECT * FROM _replaceTableName_\n";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_MAPPE_FONTE_ESTERNA +
            "WHERE id_mappa_fonte_esterna = :idMappaFonteEsterna";

    private static final String QUERY_LOAD_MAPPE_FONTE_ESTERNA_BY_COD_MASTERDATA_FONTE = QUERY_LOAD_MAPPE_FONTE_ESTERNA +
            "WHERE UPPER(cod_masterdata) = UPPER(:codMasterdata)\n" +
            "AND UPPER(info_fonte) = :infoFonte \n" +
            "AND UPPER(cod_fonte) = UPPER(:codFonte)\n";

    /**
     * Load tipi natura giuridica list.
     *
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    @Override
    public List<MappaFonteEsternaDTO> loadMappeFonteEsterna() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_MAPPE_FONTE_ESTERNA, null);
    }

    /**
     * Load tipo natura giuridica by id list.
     *
     * @param idMappaFonteEsterna the id mappa fonte esterna
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    @Override
    public List<MappaFonteEsternaDTO> loadMappaFonteEsternaById(Long idMappaFonteEsterna) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idMappaFonteEsterna", idMappaFonteEsterna);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load tipo natura giuridica by code list.
     *
     * @param codMasterdata the cod masterdata
     * @param codFonte      the cod fonte
     * @return List<TipoNaturaGiuridicaDTO>  list
     */
    @Override
    public List<MappaFonteEsternaDTO> loadMappaFonteEsternaByCodMasterdataFonte(String codMasterdata, String infoFonte, String codFonte) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codMasterdata", codMasterdata);
        map.put("infoFonte", infoFonte);
        map.put("codFonte", codFonte);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_MAPPE_FONTE_ESTERNA_BY_COD_MASTERDATA_FONTE, map);
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
     * @return RowMapper<TipoNaturaGiuridicaDTO>
     */
    @Override
    public RowMapper<MappaFonteEsternaDTO> getRowMapper() throws SQLException {
        return new MappaFonteEsternaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoNaturaGiuridicaDTO>
     */
    @Override
    public RowMapper<MappaFonteEsternaDTO> getExtendedRowMapper() throws SQLException {
        return new MappaFonteEsternaRowMapper();
    }

    /**
     * The type Tipo natura giuridica row mapper.
     */
    public static class MappaFonteEsternaRowMapper implements RowMapper<MappaFonteEsternaDTO> {

        /**
         * Instantiates a new Tipo natura giuridica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public MappaFonteEsternaRowMapper() throws SQLException {
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
        public MappaFonteEsternaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MappaFonteEsternaDTO bean = new MappaFonteEsternaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, MappaFonteEsternaDTO bean) throws SQLException {
            bean.setIdMappaFonteEsterna(rs.getLong("id_mappa_fonte_esterna"));
            bean.setCodMasterdata(rs.getString("cod_masterdata"));
            bean.setInfoFonte(rs.getString("info_fonte"));
            bean.setCodFonte(rs.getString("cod_fonte"));
            bean.setCodScriva(rs.getString("cod_scriva"));
        }
    }

}