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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoEstensioneAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoEstensioneAllegatoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento estensione allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoEstensioneAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoEstensioneAllegatoDTO> implements AdempimentoEstensioneAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_ALL = "SELECT sraea.* FROM _replaceTableName_ sraea ";

    private static final String QUERY_LOAD_BY_ID_ADEMPIMENTO = QUERY_LOAD_ALL + "WHERE sraea.id_adempimento = :idAdempimento";

    private static final String QUERY_LOAD_BY_COD_ADEMPIMENTO = QUERY_LOAD_ALL
            + "INNER JOIN scriva_d_adempimento sda ON sraea.id_adempimento = sda.id_adempimento "
            + "WHERE UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)";

    /**
     * @return List<AdempimentoEstensioneAllegatoDTO>
     */
    @Override
    public List<AdempimentoEstensioneAllegatoDTO> loadAdempimentiEstensioniAllegati() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_ALL, null);
    }

    /**
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoEstensioneAllegatoDTO>
     */
    @Override
    public List<AdempimentoEstensioneAllegatoDTO> loadAdempimentoEstensioneAllegatoByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_LOAD_BY_ID_ADEMPIMENTO, map);
    }

    /**
     * @param codAdempimento codice adempimento
     * @return List<AdempimentoEstensioneAllegatoDTO>
     */
    @Override
    public List<AdempimentoEstensioneAllegatoDTO> loadAdempimentoEstensioneAllegatoByCodAdempimento(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_LOAD_BY_COD_ADEMPIMENTO, map);
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
     * @return RowMapper<AdempimentoEstensioneAllegatoDTO
     */
    @Override
    public RowMapper<AdempimentoEstensioneAllegatoDTO> getRowMapper() throws SQLException {
        return new AdempimentoEstensioneAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<AdempimentoEstensioneAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoEstensioneAllegatoRowMapper();
    }


    /**
     * Inner class AdempimentoEstensioneAllegatoRowMapper
     */
    private static class AdempimentoEstensioneAllegatoRowMapper implements RowMapper<AdempimentoEstensioneAllegatoDTO> {

        /**
         * Instantiates a new Adempimento estensione allegato row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoEstensioneAllegatoRowMapper() throws SQLException {
            // Instatiate class
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
        public AdempimentoEstensioneAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoEstensioneAllegatoDTO bean = new AdempimentoEstensioneAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoEstensioneAllegatoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setEstensioneAllegato(rs.getString("estensione_allegato"));
            bean.setDesEstensioneAllegato(rs.getString("des_estensione_allegato"));
        }
    }

}