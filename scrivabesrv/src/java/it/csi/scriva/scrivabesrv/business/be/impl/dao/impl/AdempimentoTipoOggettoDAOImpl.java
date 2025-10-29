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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoOggettoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The interface Riscossione ente dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoOggettoDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoTipoOggettoDTO> implements AdempimentoTipoOggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ADEMPIMENTO = "WHERE sdad.id_adempimento = :idAdempimento";

    private static final String WHERE_COD_ADEMPIMENTO = "WHERE sdad.cod_adempimento = :codAdempimento";

    private static final String QUERY_ADEMPI_TIPO_OGGETTO = "SELECT srato.flg_assegna,\n" +
            "sdad.*,\n" +
            "sdta.*,\n" +
            "sda.*,\n" +
            "sdto.*\n" +
            "FROM _replaceTableName_ srato\n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = srato.id_adempimento\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = srato.id_tipologia_oggetto\n";

    private static final String ORDER_BY = " ORDER BY srato.ordinamento";
    
    /**
     * Load adempimento tipi oggetto list.
     *
     * @return the list
     */
    @Override
    public List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggetto() {
        logBegin(className);
        return findListByQuery(className, QUERY_ADEMPI_TIPO_OGGETTO + ORDER_BY, null);
    }

    /**
     * Load adempimento tipi oggetto by id adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_ADEMPI_TIPO_OGGETTO + WHERE_ID_ADEMPIMENTO + ORDER_BY, map);
    }

    /**
     * Load adempimento tipi oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoTipoOggettoExtendedDTO> loadAdempimentoTipiOggettoByCodeAdempimento(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_ADEMPI_TIPO_OGGETTO + WHERE_COD_ADEMPIMENTO + ORDER_BY, map);
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
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoTipoOggettoDTO> getRowMapper() throws SQLException {
        return new AdempimentoTipoOggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoTipoOggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoTipoOggettoExtendedRowMapper();
    }

    /**
     * Inner class AdempimentoTipoOggettoRowMapper
     */
    private static class AdempimentoTipoOggettoRowMapper implements RowMapper<AdempimentoTipoOggettoDTO> {

        /**
         * Instantiates a new Adempimento tipo oggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoTipoOggettoRowMapper() throws SQLException {
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
        public AdempimentoTipoOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoOggettoDTO bean = new AdempimentoTipoOggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * @param rs   the ResultSet
         * @param bean AdempimentoDTO
         */
        private void populateBean(ResultSet rs, AdempimentoTipoOggettoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setFlgAssegna(rs.getInt("flg_assegna") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    /**
     * Inner class AdempimentoTipoOggettoExtendedRowMapper
     */
    private static class AdempimentoTipoOggettoExtendedRowMapper implements RowMapper<AdempimentoTipoOggettoExtendedDTO> {

        /**
         * Instantiates a new Adempimento extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoTipoOggettoExtendedRowMapper() throws SQLException {
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
        public AdempimentoTipoOggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoOggettoExtendedDTO bean = new AdempimentoTipoOggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoTipoOggettoExtendedDTO bean) throws SQLException {

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);

            TipologiaOggettoDTO tipologiaOggetto = new TipologiaOggettoDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto);
            bean.setTipologiaOggetto(tipologiaOggetto.getIdTipologiaOggetto() != null ? tipologiaOggetto : null);

            bean.setFlgAssegna(rs.getInt("flg_assegna") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
            tipoAdempimento.setFlgAttivo(1 == rs.getInt("tipo_adempimento_attivo") ? Boolean.TRUE : Boolean.FALSE);
            tipoAdempimento.setOrdinamentoTipoAdempimento(rs.getInt("ordinamento_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO ambito) throws SQLException {
            ambito.setIdAmbito(rs.getLong("ambito_id"));
            ambito.setCodAmbito(rs.getString("cod_ambito"));
            ambito.setDesAmbito(rs.getString("des_ambito"));
            ambito.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
            ambito.setFlgAttivo(1 == rs.getInt("ambito_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanTipologiaOggetto(ResultSet rs, TipologiaOggettoDTO bean) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setIdNaturaOggetto(rs.getLong("id_natura_oggetto"));
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));
        }

    }

}