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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.QuadroDAO;
import it.csi.scriva.scrivabesrv.dto.QuadroDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoQuadroDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Quadro dao.
 *
 * @author CSI PIEMONTE
 */
public class QuadroDAOImpl extends ScrivaBeSrvGenericDAO<QuadroDTO> implements QuadroDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_COMPONENTE_APP = "AND srtq.ind_visibile = :indVisibile\n";

    private static final String ORDER_BY_DEFAULT = "ORDER BY srtq.ordinamento_template_quadro\n";

    private static final String QUERY_QUADRI_ATTIVI = "SELECT quadro.*, tipo_quadro.cod_tipo_quadro, tipo_quadro.des_tipo_quadro\n" +
            "FROM _replaceTableName_ AS quadro\n" +
            "INNER JOIN scriva_d_tipo_quadro AS tipo_quadro ON quadro.id_tipo_quadro = tipo_quadro.id_tipo_quadro\n" +
            "WHERE quadro.flg_attivo = 1\n";

    private static final String QUERY_PRIMARY_KEY = QUERY_QUADRI_ATTIVI + " AND quadro.id_quadro = :idQuadro\n";

    private static final String QUERY_BY_TIPO_QUADRO = QUERY_QUADRI_ATTIVI + " AND UPPER(tipo_quadro.cod_tipo_quadro) = UPPER(:codTipoQuadro)\n";

    private static final String QUERY_BY_TIPO_QUADRO_AND_ISTANZA = "select sdq.*, sdtq.cod_tipo_quadro, sdtq.des_tipo_quadro from _replaceTableName_ sdq \r\n" + 
    		"    		inner join scriva_r_template_quadro srtq on srtq.id_quadro = sdq.id_quadro \r\n" + 
    		"    		inner join scriva_t_istanza sti on  sti.id_template = srtq.id_template\r\n" + 
    		"    		inner join scriva_d_tipo_quadro sdtq on sdtq.id_tipo_quadro = sdq.id_tipo_quadro \r\n" + 
    		"    		where sti.id_istanza = :idIstanza\r\n" + 
    		"    		and UPPER(sdtq.cod_tipo_quadro) = UPPER(:codTipoQuadro)";
    
    private static final String QUERY_BY_ID_TEMPLATE = "SELECT sdq.*, sdtq.*\n" +
            "FROM _replaceTableName_ sdq \n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdq.id_tipo_quadro = sdtq.id_tipo_quadro\n" +
            "INNER JOIN scriva_r_template_quadro srtq ON srtq.id_quadro = sdq.id_quadro \n" +
            "WHERE sdq.flg_attivo = 1\n" +
            "AND srtq.id_template = :idTemplate\n";

    /**
     * Load quadri attivi list.
     *
     * @return List<QuadroExtendedDTO> list
     */
    @Override
    public List<QuadroExtendedDTO> loadQuadriAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_QUADRI_ATTIVI, null);
    }

    /**
     * Load quadro by id list.
     *
     * @param idQuadro idQuadro
     * @return List<QuadroExtendedDTO> list
     */
    @Override
    public List<QuadroExtendedDTO> loadQuadroById(Long idQuadro) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idQuadro", idQuadro);
        return findListByQuery(className, getPrimaryKeySelect(), null);
    }

    /**
     * Load quadro by id tipo quadro list.
     *
     * @param codTipoQuadro codTipoQuadro
     * @param idIstanza idIstanza
     * @return List<QuadroExtendedDTO> list
     */
    @Override
    public List<QuadroExtendedDTO> loadQuadroByCodeTipoQuadroAndIstanza(String codTipoQuadro, Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoQuadro", codTipoQuadro);
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_BY_TIPO_QUADRO_AND_ISTANZA, map);
    }
    
    /**
     * Load quadro by id code tipo quadro and id istanza.
     *
     * @param codTipoQuadro codTipoQuadro
     * @return List<QuadroExtendedDTO> list
     */
    @Override
    public List<QuadroExtendedDTO> loadQuadroByCodeTipoQuadro(String codTipoQuadro) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoQuadro", codTipoQuadro);
        return findListByQuery(className, QUERY_BY_TIPO_QUADRO, map);
    }

    /**
     * Load quadro by id template list.
     *
     * @param idTemplate       the id template
     * @param codComponenteApp the cod componente app
     * @return the list
     */
    @Override
    public List<QuadroExtendedDTO> loadQuadroByIdTemplate(Long idTemplate, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_BY_ID_TEMPLATE;
        Map<String, Object> map = new HashMap<>();
        map.put("idTemplate", idTemplate);
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_COMPONENTE_APP;
        }
        return findListByQuery(className, query + ORDER_BY_DEFAULT, map);
    }

    /**
     * Save quadro long.
     *
     * @param formIOSezioneDTO QuadroDTO
     * @return id record salvato
     */
    @Override
    public Long saveQuadro(QuadroDTO formIOSezioneDTO) {
        return null;
    }

    /**
     * Update quadro int.
     *
     * @param formIOSezioneDTO QuadroDTO
     * @return numero record aggiornati
     */
    @Override
    public int updateQuadro(QuadroDTO formIOSezioneDTO) {
        return 0;
    }

    /**
     * Delete quadro.
     *
     * @param idFormioSezione idFormioSezione
     */
    @Override
    public void deleteQuadro(Long idFormioSezione) {
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
     * @return RowMapper<QuadroDTO>
     */
    @Override
    public RowMapper<QuadroDTO> getRowMapper() throws SQLException {
        return new QuadroRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<QuadroExtendedDTO>
     */
    @Override
    public RowMapper<QuadroExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new QuadroExtendedRowMapper();
    }

    /**
     * The type Quadro row mapper.
     */
    public static class QuadroRowMapper implements RowMapper<QuadroDTO> {

        /**
         * Instantiates a new Quadro row mapper.
         *
         * @throws SQLException the sql exception
         */
        public QuadroRowMapper() throws SQLException {
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
        public QuadroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuadroDTO bean = new QuadroDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, QuadroDTO bean) throws SQLException {
            bean.setIdQuadro(rs.getLong("id_quadro"));
            bean.setIdTipoQuadro(rs.getLong("id_tipo_quadro"));
            bean.setCodQuadro(rs.getString("cod_quadro"));
            bean.setDesQuadro(rs.getString("des_quadro"));
            bean.setFlgTipoGestione(rs.getString("flg_tipo_gestione"));
            bean.setJsonConfiguraQuadro(rs.getString("json_configura_quadro"));
            bean.setJsonConfiguraRiepilogo(rs.getString("json_configura_riepilogo"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    /**
     * The type Quadro extended row mapper.
     */
    public static class QuadroExtendedRowMapper implements RowMapper<QuadroExtendedDTO> {

        /**
         * Instantiates a new Quadro extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public QuadroExtendedRowMapper() throws SQLException {
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
        public QuadroExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            QuadroExtendedDTO bean = new QuadroExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, QuadroExtendedDTO bean) throws SQLException {
            bean.setIdQuadro(rs.getLong("id_quadro"));
            TipoQuadroDTO tipoQuadro = new TipoQuadroDTO();
            this.populateBeanTipoQuadro(rs, tipoQuadro);
            bean.setTipoQuadro(tipoQuadro);
            bean.setCodQuadro(rs.getString("cod_quadro"));
            bean.setDesQuadro(rs.getString("des_quadro"));
            bean.setFlgTipoGestione(rs.getString("flg_tipo_gestione"));
            bean.setJsonConfiguraQuadro(rs.getString("json_configura_quadro"));
            bean.setJsonConfiguraRiepilogo(rs.getString("json_configura_riepilogo"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanTipoQuadro(ResultSet rs, TipoQuadroDTO bean) throws SQLException {
            bean.setIdTipoQuadro(rs.getLong("id_tipo_quadro"));
            bean.setCodiceTipoQuadro(rs.getString("cod_tipo_quadro"));
            bean.setDescrizioneTipoQuadro(rs.getString("des_tipo_quadro"));
        }
    }

}