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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Template dao.
 *
 * @author CSI PIEMONTE
 */
public class TemplateDAOImpl extends ScrivaBeSrvGenericDAO<TemplateDTO> implements TemplateDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_TEMPLATE_ATTIVI = "SELECT sdt.*, " +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, " +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo, " +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo " +
            "FROM _replaceTableName_ sdt  " +
            "INNER JOIN scriva_d_adempimento sdad ON sdt.id_adempimento = sdad.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito " +
            "WHERE sdt.flg_attivo = 1 ";

    private static final String QUERY_PRIMARY_KEY = QUERY_TEMPLATE_ATTIVI + " AND sdt.id_template = :idTemplate";

    private static final String QUERY_TEMPLATE_BY_CODE = QUERY_TEMPLATE_ATTIVI + " AND UPPER(sdt.cod_template) = UPPER(:codTemplate)";

    private static final String QUERY_TEMPLATE_BY_CODE_ADEMPIMENTO = QUERY_TEMPLATE_ATTIVI + " AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento)";

    /**
     * Load template attivi list.
     *
     * @return List<TemplateExtendedDTO> list
     */
    @Override
    public List<TemplateExtendedDTO> loadTemplateAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_TEMPLATE_ATTIVI, null);
    }

    /**
     * Load template by id list.
     *
     * @param idTemplate idTemplate
     * @return List<TemplateExtendedDTO> list
     */
    @Override
    public List<TemplateExtendedDTO> loadTemplateById(Long idTemplate) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTemplate", idTemplate);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * Load template by code list.
     *
     * @param codTemplate codTemplate
     * @return List<TemplateExtendedDTO> list
     */
    @Override
    public List<TemplateExtendedDTO> loadTemplateByCode(String codTemplate) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTemplate", codTemplate);
        return findListByQuery(className, QUERY_TEMPLATE_BY_CODE, map);
    }

    /**
     * Load template by cod adempimento list.
     *
     * @param codAdempimento codAdempimento
     * @return List<TemplateExtendedDTO> list
     */
    @Override
    public List<TemplateExtendedDTO> loadTemplateByCodAdempimento(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_TEMPLATE_BY_CODE_ADEMPIMENTO, map);
    }

    /**
     * Save template long.
     *
     * @param templateDTO TemplateDTO
     * @return id record salvato
     */
    @Override
    public Long saveTemplate(TemplateDTO templateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Update template int.
     *
     * @param templateDTO TemplateDTO
     * @return numero record aggiornati
     */
    @Override
    public int updateTemplate(TemplateDTO templateDTO) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * Delete template.
     *
     * @param idTemplate idTemplate
     */
    @Override
    public void deleteTemplate(Long idTemplate) {
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
     * @return RowMapper<TemplateDTO>
     */
    @Override
    public RowMapper<TemplateDTO> getRowMapper() throws SQLException {
        return new TemplateRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TemplateExtendedDTO>
     */
    @Override
    public RowMapper<TemplateExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TemplateExtendedRowMapper();
    }

    /**
     * The type Template row mapper.
     */
    public static class TemplateRowMapper implements RowMapper<TemplateDTO> {

        /**
         * Instantiates a new Template row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TemplateRowMapper() throws SQLException {
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
        public TemplateDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateDTO bean = new TemplateDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TemplateDTO bean) throws SQLException {
            bean.setIdTemplate(rs.getLong("id_template"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setCodTemplate(rs.getString("cod_template"));
            bean.setDesTemplate(rs.getString("des_template"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataCessazione(rs.getDate("data_cessazione"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setJsonConfguraTemplate(rs.getString("json_configura_template"));
        }
    }

    /**
     * The type Template extended row mapper.
     */
    public static class TemplateExtendedRowMapper implements RowMapper<TemplateExtendedDTO> {

        /**
         * Instantiates a new Template extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TemplateExtendedRowMapper() throws SQLException {
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
        public TemplateExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateExtendedDTO bean = new TemplateExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TemplateExtendedDTO bean) throws SQLException {
            bean.setIdTemplate(rs.getLong("id_template"));

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimentoExtended(rs, adempimento);
            bean.setAdempimento(adempimento);

            bean.setCodTemplate(rs.getString("cod_template"));
            bean.setDesTemplate(rs.getString("des_template"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataCessazione(rs.getDate("data_cessazione"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setJsonConfguraTemplate(rs.getString("json_configura_template"));
        }

        private void populateBeanAdempimentoExtended(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtendedDTO(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }

        private void pupulateTipoAdempimentoExtendedDTO(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }
    }

}