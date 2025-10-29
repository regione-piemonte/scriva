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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipologiaOggettoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipologia oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class TipologiaOggettoDAOImpl extends ScrivaBeSrvGenericDAO<TipologiaOggettoDTO> implements TipologiaOggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_TIPOLOGIA_OGGETTO = "AND sdto.id_tipologia_oggetto = :idTipologiaOggetto\n";

    private static final String WHERE_COD_TIPOLOGIA_OGGETTO = "AND sdto.cod_tipologia_oggetto = :codTipologiaOggetto\n";

    private static final String WHERE_COD_ADEMPIMENTO = "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n";

    private static final String QUERY_TIPOLOGIA_OGGETTO = "SELECT DISTINCT sdto.id_tipologia_oggetto,\n" +
            "sdto.id_natura_oggetto, sdto.cod_tipologia_oggetto, sdto.des_tipologia_oggetto,\n " +
            "sdno.cod_natura_oggetto , sdno.des_natura_oggetto\n  " +
            "FROM scriva_d_tipologia_oggetto sdto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdno.id_natura_oggetto = sdto.id_natura_oggetto\n" +
            "WHERE 1 = 1 \n";

    private static final String QUERY_TIPOLOGIA_OGGETTO_BY_ADEMPIMENTO = "SELECT DISTINCT sdto.id_tipologia_oggetto,\n" +
            "sdto.id_natura_oggetto, sdto.cod_tipologia_oggetto, sdto.des_tipologia_oggetto,\n " +
            "sdno.cod_natura_oggetto , sdno.des_natura_oggetto,\n  " +
            "srato.id_adempimento, srato.flg_assegna\n" +
            "FROM scriva_d_tipologia_oggetto sdto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdno.id_natura_oggetto = sdto.id_natura_oggetto\n" +
            "INNER JOIN scriva_r_adempi_tipo_oggetto srato ON srato.id_tipologia_oggetto = sdto.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = srato.id_adempimento\n" +
            "WHERE 1 = 1 \n";

    private static final String WHERE_ID_ISTANZA = "AND stoi.id_istanza = :idIstanza\n";
    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND stoi.id_oggetto_istanza = :idOggettoIstanza\n";
    private static final String QUERY_TIPOLOGIA_OGGETTO_BY_OGG_ISTANZA = "SELECT DISTINCT sdto.id_tipologia_oggetto,\n" +
            "sdto.id_natura_oggetto, sdto.cod_tipologia_oggetto, sdto.des_tipologia_oggetto,\n" +
            "sdno.cod_natura_oggetto, sdno.des_natura_oggetto,\n" +
            "NULL AS id_adempimento, NULL AS flg_assegna\n" +
            "FROM scriva_t_oggetto_istanza stoi \n" +
            "INNER JOIN scriva_r_ogg_istanza_categoria sroic ON sroic.id_oggetto_istanza = stoi.id_oggetto_istanza \n" +
            "INNER JOIN scriva_r_categoria_tipo_ogg srcto ON srcto.id_categoria_oggetto = sroic.id_categoria_oggetto\n" +
            "INNER JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = srcto.id_tipologia_oggetto\n" +
            "INNER JOIN scriva_d_natura_oggetto sdno ON sdno.id_natura_oggetto = sdto.id_natura_oggetto\n" +
            "WHERE stoi.ind_livello = 1";

    private static final String QUERY_TIPOLOGIA_OGGETTO_BY_ID_LAYER = " SELECT srglv.id_virtuale, sdto.*\n" +
            " FROM scriva_d_tipologia_oggetto sdto\n" +
            " INNER JOIN scriva_r_geeco_layer_virtuali srglv ON srglv.id_tipologia_oggetto = sdto.id_tipologia_oggetto\n" +
            " WHERE srglv.id_virtuale IN (:idLayerList)\n";

    /**
     * Load tipologie oggetto list.
     *
     * @return List<TipologiaOggettoExtendedDTO>  list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadAll() {
        logBegin(className);
        return findListByQuery(className, QUERY_TIPOLOGIA_OGGETTO, null);
    }

    /**
     * Load tipologia oggetto list.
     *
     * @param idTipologiaOggetto the id tipologia oggetto
     * @return List<TipologiaOggettoExtendedDTO>  list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadTipologiaOggetto(Long idTipologiaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipologiaOggetto", idTipologiaOggetto);
        return findListByQuery(className, QUERY_TIPOLOGIA_OGGETTO + WHERE_ID_TIPOLOGIA_OGGETTO, map);
    }

    /**
     * Load tipologia oggetto by code list.
     *
     * @param codTipologiaOggetto the cod tipologia oggetto
     * @return List<TipologiaOggettoExtendedDTO>  list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCode(String codTipologiaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipologiaOggetto", codTipologiaOggetto);
        return findListByQuery(className, QUERY_TIPOLOGIA_OGGETTO + WHERE_COD_TIPOLOGIA_OGGETTO, map);
    }

    /**
     * Load tipologia oggetto by code adempimento list.
     *
     * @param codAdempimento the cod adempimento
     * @return the list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByCodeAdempimento(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_TIPOLOGIA_OGGETTO_BY_ADEMPIMENTO + WHERE_COD_ADEMPIMENTO, map);
    }

    /**
     * Load tipologia oggetto by ogg istanza categoria list.
     *
     * @param idIstanza        the id istanza
     * @param idOggettoIstanza the id oggetto istanza
     * @return the list
     */
    @Override
    public List<TipologiaOggettoExtendedDTO> loadTipologiaOggettoByOggIstanzaCategoria(Long idIstanza, Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("idOggettoIstanza", idOggettoIstanza);
        String query = QUERY_TIPOLOGIA_OGGETTO_BY_OGG_ISTANZA;
        if (idIstanza != null) {
            query += WHERE_ID_ISTANZA;
        }
        if (idOggettoIstanza != null) {
            query += WHERE_ID_OGGETTO_ISTANZA;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Load tipologia oggetto by id layers list.
     *
     * @param idLayerList the id layer list
     * @return the list
     */
    @Override
    public List<TipologiaOggettoDTO> loadTipologiaOggettoByIdLayers(List<Long> idLayerList) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idLayerList", idLayerList);
        return findSimpleDTOListByQuery(className, QUERY_TIPOLOGIA_OGGETTO_BY_ID_LAYER, map);
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
    public RowMapper<TipologiaOggettoDTO> getRowMapper() throws SQLException {
        return new TipologiaOggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>       extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipologiaOggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TipologiaOggettoExtendedRowMapper();
    }

    /**
     * The type tipologia oggetto row mapper.
     */
    public static class TipologiaOggettoRowMapper implements RowMapper<TipologiaOggettoDTO> {
        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipologiaOggettoRowMapper() throws SQLException {
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
        public TipologiaOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipologiaOggettoDTO bean = new TipologiaOggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipologiaOggettoDTO bean) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setIdNaturaOggetto(rs.getLong("id_natura_oggetto"));
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));
            try {
                bean.setIdLayer(rs.getLong("id_virtuale"));
            } catch (SQLException e) {
                bean.setIdLayer(null);
            }
        }
    }

    /**
     * The type tipologia oggetto  extended row mapper.
     */
    public static class TipologiaOggettoExtendedRowMapper implements RowMapper<TipologiaOggettoExtendedDTO> {


        /**
         * Instantiates a new Tipo competenza row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipologiaOggettoExtendedRowMapper() throws SQLException {
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
        public TipologiaOggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipologiaOggettoExtendedDTO bean = new TipologiaOggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipologiaOggettoExtendedDTO bean) throws SQLException {

            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));

            try {
                bean.setFlgAssegna(1 == rs.getInt("flg_assegna") ? Boolean.TRUE : Boolean.FALSE);
            } catch (SQLException e) {
                bean.setFlgAssegna(null);
            }

            NaturaOggettoDTO naturaOggettoDTO = new NaturaOggettoDTO();
            populateBeanNaturaOggettoDTO(rs, naturaOggettoDTO);
            bean.setNaturaOggetto(naturaOggettoDTO.getIdNaturaOggetto() != 0 ? naturaOggettoDTO : null);

/*
            AdempimentoExtendedDTO adempimentoDTO = new AdempimentoExtendedDTO();
            populateBeanAdempimentoExtendedDTO(rs, adempimentoDTO);
            bean.setAdempimento(adempimentoDTO.getIdAdempimento() != 0 ? adempimentoDTO : null);
*/
        }

        private void populateBeanAdempimentoExtendedDTO(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));

        }

        private void populateBeanNaturaOggettoDTO(ResultSet rs, NaturaOggettoDTO bean) throws SQLException {
            bean.setIdNaturaOggetto(rs.getLong("id_natura_oggetto"));
            bean.setCodNaturaOggetto(rs.getString("cod_natura_oggetto"));
            bean.setDesNaturaOggetto(rs.getString("des_natura_oggetto"));
        }

    }

}