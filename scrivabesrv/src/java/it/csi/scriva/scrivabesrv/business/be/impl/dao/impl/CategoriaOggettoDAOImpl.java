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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.CategoriaOggettoDAO;
import it.csi.scriva.scrivabesrv.dto.CategoriaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Categoria oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class CategoriaOggettoDAOImpl extends ScrivaBeSrvGenericDAO<CategoriaOggettoDTO> implements CategoriaOggettoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_COMPONENTE_APP = "AND sdco.ind_visibile LIKE '%'||:componenteApp||'%'\n";
    
    private static final String WHERE_COMPONENTE_APP_ADEMPI_CAT_OGG = "AND sraco.ind_visibile LIKE '%'||:componenteApp||'%'\n";

    private static final String WHERE_ID_ADEMPIMENTO = "AND sraco.id_adempimento = :idAdempimento\n";

    private static final String WHERE_NATURA_OGGETTO_PRO = "AND sdno.cod_natura_oggetto = 'PRO'\n";

    private static final String WHERE_SEARCH_CRITERIA = "AND (UPPER(sdco.cod_categoria_oggetto) LIKE '%'||UPPER(:searchCriteria)||'%' OR UPPER(sdco.des_categoria_oggetto) LIKE '%'||UPPER(:searchCriteria)||'%')\n";

    private static final String WHERE_DATA_FINE_VALIDITA_NULL = "AND sraco.data_fine_validita IS NULL ";
    
    private static final String WHERE_DATA_INIZIO_FINE_VALIDITA  =  " AND (DATE(sraco.data_inizio_validita) <= DATE(NOW()) AND (coalesce(sraco.data_fine_validita, now()))>= DATE(NOW()))\n ";
    
    private static final String ORDER_BY_ORD_CATEGORIA_OGGETTO = "ORDER BY sdco.ordinamento_categoria_oggetto ASC";
    
    private static final String ORDER_BY_ORD_ADEMPI_CAT_OGG = " ORDER BY sraco.ordinamento_adempi_cat_ogg ASC";

    private static final String QUERY_CATEGORIE_OGGETTO = "SELECT * FROM _replaceTableName_ sdco WHERE 1=1 ";

    private static final String QUERY_CATEGORIA_OGGETTO = "SELECT sdco.*,\n" +
            "sdto.*, sdto.id_tipologia_oggetto AS tipologia_oggetto_id,\n " +
            "sdno.*, sdno.id_natura_oggetto AS natura_oggetto_id\n" +
            "FROM _replaceTableName_ sdco\n" +
            "INNER JOIN scriva_r_adempi_categoria_ogg sraco ON sraco.id_categoria_oggetto = sdco.id_categoria_oggetto\n" +
            "LEFT JOIN scriva_r_categoria_tipo_ogg srcto ON srcto.id_categoria_oggetto = sdco.id_categoria_oggetto \n" +
            "LEFT JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = srcto.id_tipologia_oggetto \n" +
            "LEFT JOIN scriva_d_natura_oggetto sdno ON sdno.id_natura_oggetto = sdto.id_natura_oggetto\n" +
            "WHERE 1 = 1\n";
    
    private static final String QUERY_CATEGORIA_OGGETTO_TIPO_COMPETENZE = "SELECT sdco.*, sdtc.des_tipo_competenza,\n" +
            "sdto.*, sdto.id_tipologia_oggetto AS tipologia_oggetto_id,\n " +
            "sdno.*, sdno.id_natura_oggetto AS natura_oggetto_id\n" +
            "FROM _replaceTableName_ sdco\n" +
            "INNER JOIN scriva_r_adempi_categoria_ogg sraco ON sraco.id_categoria_oggetto = sdco.id_categoria_oggetto\n" +
            "LEFT JOIN scriva_r_categoria_tipo_ogg srcto ON srcto.id_categoria_oggetto = sdco.id_categoria_oggetto \n" +
            "LEFT JOIN scriva_d_tipologia_oggetto sdto ON sdto.id_tipologia_oggetto = srcto.id_tipologia_oggetto \n" +
            "LEFT JOIN scriva_d_natura_oggetto sdno ON sdno.id_natura_oggetto = sdto.id_natura_oggetto \n" +
            "LEFT JOIN scriva_r_tipo_competenza_cat srtcc ON srtcc.id_categoria_oggetto = sdco.id_categoria_oggetto \n" + 
            "LEFT JOIN scriva_d_tipo_competenza sdtc  on sdtc.id_tipo_competenza  = srtcc.id_tipo_competenza \n" +
            "WHERE 1 = 1 \n";

    private static final String QUERY_CATEGORIA_OGGETTO_BY_ID = QUERY_CATEGORIA_OGGETTO + "AND sraco.id_categoria_oggetto = :idCategoriaOggetto\n";

    private static final String QUERY_CATEGORIA_OGGETTO_BY_CODE = QUERY_CATEGORIA_OGGETTO +
            "AND UPPER(sdco.cod_categoria_oggetto) = UPPER(:codeCategoriaOggetto)\n" +
            "AND sraco.id_adempimento = :idAdempimento\n";

    private static final String QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO = QUERY_CATEGORIA_OGGETTO + WHERE_ID_ADEMPIMENTO;
    
    private static final String QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_REV = QUERY_CATEGORIA_OGGETTO_TIPO_COMPETENZE + WHERE_ID_ADEMPIMENTO;

    private static final String QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_COMPONENTE = QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO + WHERE_COMPONENTE_APP;
    
    private static final String QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_COMPONENTE_REV = QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_REV + WHERE_COMPONENTE_APP_ADEMPI_CAT_OGG + WHERE_DATA_INIZIO_FINE_VALIDITA;
    
    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(sdco.data_inizio_validita) <= DATE(NOW()) AND (sdco.data_fine_validita IS NULL OR DATE(sdco.data_fine_validita)>= DATE(NOW())))\n ";


    /**
     * @return List<CategoriaOggettoExtendedDTO>
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategorieOggetto(String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("componenteApp", componenteApp);
        return findListByQuery(className, QUERY_CATEGORIA_OGGETTO + WHERE_COMPONENTE_APP + WHERE_DATA_INIZIO_FINE + ORDER_BY_ORD_CATEGORIA_OGGETTO, map);
    }

    /**
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<CategoriaOggettoExtendedDTO>
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoById(Long idCategoriaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCategoriaOggetto", idCategoriaOggetto);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load categoria oggetto by code list.
     *
     * @param codeCategoriaOggetto the code categoria oggetto
     * @return the list
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByCode(String codeCategoriaOggetto, Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codeCategoriaOggetto", codeCategoriaOggetto);
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_CATEGORIA_OGGETTO_BY_CODE, map);
    }

    /**
     * @param idAdempimento idAdempimento
     * @return List<CategoriaOggettoExtendedDTO>
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO + WHERE_NATURA_OGGETTO_PRO + ORDER_BY_ORD_CATEGORIA_OGGETTO, map);
    }

    /**
     * @param idAdempimento idAdempimento
     * @param componenteApp componenteApp
     * @return List<CategoriaOggettoExtendedDTO>
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimentoComponente(Long idAdempimento, String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("componenteApp", componenteApp);
        return findListByQuery(className, QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_COMPONENTE + WHERE_NATURA_OGGETTO_PRO + ORDER_BY_ORD_CATEGORIA_OGGETTO, map);
    }

    /**
     * @param idAdempimento  idAdempimento
     * @param componenteApp  componenteApp
     * @param searchCriteria searchCriteria
     * @return List<CategoriaOggettoExtendedDTO>
     */
    @Override
    public List<CategoriaOggettoExtendedDTO> loadCategoriaOggettoByIdAdempimentoComponenteSearchCriteria(Long idAdempimento, String componenteApp, String searchCriteria) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_CATEGORIA_OGGETTO_BY_ID_ADEMPIMENTO_COMPONENTE_REV;
        map.put("idAdempimento", idAdempimento);
        map.put("componenteApp", componenteApp);
        if (StringUtils.isNotBlank(searchCriteria)) {
            map.put("searchCriteria", searchCriteria);
            query += WHERE_SEARCH_CRITERIA;
        }
        return findListByQuery(className, query + ORDER_BY_ORD_ADEMPI_CAT_OGG, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_CATEGORIA_OGGETTO_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<CategoriaOggettoDTO>
     */
    @Override
    public RowMapper<CategoriaOggettoDTO> getRowMapper() throws SQLException {
        return new CategoriaOggettoRowMapper();
    }

    @Override
    public RowMapper<CategoriaOggettoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new CategoriaOggettoExtendedRowMapper();
    }

    /**
     * The type Categoria oggetto row mapper.
     */
    public static class CategoriaOggettoRowMapper implements RowMapper<CategoriaOggettoDTO> {

        /**
         * Instantiates a new Categoria oggetto row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CategoriaOggettoRowMapper() throws SQLException {
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
        public CategoriaOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoriaOggettoDTO bean = new CategoriaOggettoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CategoriaOggettoDTO bean) throws SQLException {
            bean.setIdCategoriaOggetto(rs.getLong("id_categoria_oggetto"));
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setCodCategoriaOggetto(rs.getString("cod_categoria_oggetto"));
            bean.setDesCategoriaOggetto(rs.getString("des_categoria_oggetto"));
            bean.setDesCategoriaOggettoEstesa(rs.getString("des_categoria_oggetto_estesa"));
            bean.setOrdinamentoCategoriaOggetto(rs.getInt("ordinamento_categoria_oggetto"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
        }
    }

    /**
     * The type Categoria oggetto extended row mapper.
     */
    public static class CategoriaOggettoExtendedRowMapper implements RowMapper<CategoriaOggettoExtendedDTO> {

        /**
         * Instantiates a new Categoria oggetto extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public CategoriaOggettoExtendedRowMapper() throws SQLException {
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
        public CategoriaOggettoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoriaOggettoExtendedDTO bean = new CategoriaOggettoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, CategoriaOggettoExtendedDTO bean) throws SQLException {
            bean.setIdCategoriaOggetto(rs.getLong("id_categoria_oggetto"));
            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto);
            if (tipologiaOggetto.getIdTipologiaOggetto() > 0) {
                bean.setTipologiaOggetto(tipologiaOggetto);
            }
            bean.setCodCategoriaOggetto(rs.getString("cod_categoria_oggetto"));
            bean.setDesCategoriaOggetto(rs.getString("des_categoria_oggetto"));
            bean.setDesCategoriaOggettoEstesa(rs.getString("des_categoria_oggetto_estesa"));
            bean.setOrdinamentoCategoriaOggetto(rs.getInt("ordinamento_categoria_oggetto"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
        }

        private void populateBeanTipologiaOggetto(ResultSet rs, TipologiaOggettoExtendedDTO bean) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("tipologia_oggetto_id"));
            NaturaOggettoDTO naturaOggetto = new NaturaOggettoDTO();
            populateBeanNaturaOggetto(rs, naturaOggetto);
            if (naturaOggetto.getIdNaturaOggetto() > 0) {
                bean.setNaturaOggetto(naturaOggetto);
            }
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));
        }

        private void populateBeanNaturaOggetto(ResultSet rs, NaturaOggettoDTO bean) throws SQLException {
            bean.setIdNaturaOggetto(rs.getLong("natura_oggetto_id"));
            bean.setCodNaturaOggetto(rs.getString("cod_natura_oggetto"));
            bean.setDesNaturaOggetto(rs.getString("des_natura_oggetto"));
        }

    }

}