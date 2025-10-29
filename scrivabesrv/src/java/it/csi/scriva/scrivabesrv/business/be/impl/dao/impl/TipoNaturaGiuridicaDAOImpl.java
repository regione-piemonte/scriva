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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNaturaGiuridicaDAO;
import it.csi.scriva.scrivabesrv.dto.TipoNaturaGiuridicaDTO;
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
public class TipoNaturaGiuridicaDAOImpl extends ScrivaBeSrvGenericDAO<TipoNaturaGiuridicaDTO> implements TipoNaturaGiuridicaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_ORDINAMENTO = " ORDER BY sdtng.ordinamento_tipo_natura_giu";

    private static final String QUERY_LOAD_TIPI_NATURA_GIURIDICA = "SELECT * FROM _replaceTableName_ sdtng\n";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_TIPI_NATURA_GIURIDICA +
            "WHERE sdtng.id_tipo_natura_giuridica = :idTipoNaturaGiuridica";

    private static final String QUERY_LOAD_TIPI_NATURA_GIURIDICA_BY_CODE = QUERY_LOAD_TIPI_NATURA_GIURIDICA +
            "WHERE UPPER(sdtng.cod_tipo_natura_giuridica) = UPPER(:codTipoNaturaGiuridica)";

    private static final String QUERY_LOAD_TIPI_NATURA_GIURIDICA_BY_CODE_MASTERDATA_FONTE = QUERY_LOAD_TIPI_NATURA_GIURIDICA +
            "INNER JOIN scriva_d_mappa_fonte_esterna sdmfe ON sdmfe.cod_scriva = sdtng.cod_tipo_natura_giuridica\n" +
            "AND UPPER(sdmfe.cod_masterdata) = UPPER(:codMasterdata)\n" +
            "AND sdmfe.info_fonte = :infoFonte \n" +
            "AND UPPER(sdmfe.cod_fonte) = UPPER(:codFonte)\n" +
            ORDER_BY_ORDINAMENTO;

    /**
     * Load tipi natura giuridica list.
     *
     * @return List<TipoNaturaGiuridicaDTO> list
     */
    @Override
    public List<TipoNaturaGiuridicaDTO> loadTipiNaturaGiuridica() {
        logBegin(className);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_NATURA_GIURIDICA + ORDER_BY_ORDINAMENTO, null);
    }

    /**
     * Load tipo natura giuridica by id list.
     *
     * @param idTipoNaturaGiuridica idTipoNaturaGiuridica
     * @return List<TipoNaturaGiuridicaDTO> list
     */
    @Override
    public List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaById(Long idTipoNaturaGiuridica) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoNaturaGiuridica", idTipoNaturaGiuridica);
        return findSimpleDTOListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load tipo natura giuridica by code list.
     *
     * @param codTipoNaturaGiuridica codTipoNaturaGiuridica
     * @return List<TipoNaturaGiuridicaDTO> list
     */
    @Override
    public List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaByCode(String codTipoNaturaGiuridica) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoNaturaGiuridica", codTipoNaturaGiuridica);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_NATURA_GIURIDICA_BY_CODE + ORDER_BY_ORDINAMENTO, map);
    }

    /**
     * Load tipo natura giuridica by code masterdata fonte list.
     *
     * @param codMasterdata the cod masterdata
     * @param infoFonte     the info fonte
     * @param codFonte      the cod fonte
     * @return the list
     */
    @Override
    public List<TipoNaturaGiuridicaDTO> loadTipoNaturaGiuridicaByCodeMasterdataFonte(String codMasterdata, String infoFonte, String codFonte) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codMasterdata", codMasterdata);
        map.put("infoFonte", infoFonte);
        map.put("codFonte", codFonte);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_TIPI_NATURA_GIURIDICA_BY_CODE_MASTERDATA_FONTE, map);
    }

    /**
     * Save tipo natura giuridica.
     *
     * @param dto TipoNaturaGiuridicaDTO
     */
    @Override
    public void saveTipoNaturaGiuridica(TipoNaturaGiuridicaDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Update tipo natura giuridica.
     *
     * @param dto TipoNaturaGiuridicaDTO
     */
    @Override
    public void updateTipoNaturaGiuridica(TipoNaturaGiuridicaDTO dto) {
        // TODO Auto-generated method stub
    }

    /**
     * Delete tipo natura giuridica.
     *
     * @param id TipoNaturaGiuridicaDTO
     */
    @Override
    public void deleteTipoNaturaGiuridica(Long id) {
        // TODO Auto-generated method stub
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
    public RowMapper<TipoNaturaGiuridicaDTO> getRowMapper() throws SQLException {
        return new TipoNaturaGiuridicaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TipoNaturaGiuridicaDTO>
     */
    @Override
    public RowMapper<TipoNaturaGiuridicaDTO> getExtendedRowMapper() throws SQLException {
        return new TipoNaturaGiuridicaRowMapper();
    }

    /**
     * The type Tipo natura giuridica row mapper.
     */
    public static class TipoNaturaGiuridicaRowMapper implements RowMapper<TipoNaturaGiuridicaDTO> {

        /**
         * Instantiates a new Tipo natura giuridica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoNaturaGiuridicaRowMapper() throws SQLException {
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
        public TipoNaturaGiuridicaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoNaturaGiuridicaDTO bean = new TipoNaturaGiuridicaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoNaturaGiuridicaDTO bean) throws SQLException {
            bean.setIdTipoNaturaGiuridica(rs.getLong("id_tipo_natura_giuridica"));
            bean.setCodiceTipoNaturaGiuridica(rs.getString("cod_tipo_natura_giuridica"));
            bean.setDescrizioneTipoNaturaGiuridica(rs.getString("des_tipo_natura_giuridica"));
            bean.setSiglaTipoNaturaGiuridica(rs.getString("sigla_tipo_natura_giuridica"));
            bean.setOrdinamentoTipoNaturaGiuridica(rs.getLong("ordinamento_tipo_natura_giu"));
            bean.setFlgPubblico(rs.getInt("flg_pubblico") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

}