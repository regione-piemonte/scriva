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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateQuadroDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoQuadroDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Template quadro dao.
 *
 * @author CSI PIEMONTE
 */
public class TemplateQuadroDAOImpl extends ScrivaBeSrvGenericDAO<TemplateQuadroDTO> implements TemplateQuadroDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_IND_VISIBILE = "AND srtq.ind_visibile LIKE '%'||:codComponenteApp||'%'\n";

    private static final String QUERY_TEMPLATE_QUADRI_ATTIVI = "SELECT srtq.*, \n" +
            "sdt.*, sdt.id_template AS template_id, \n" +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, \n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo, \n" +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo, \n" +
            "sdq.*, sdq.id_quadro AS quadro_id, \n" +
            "sdtq.*, sdtq.id_tipo_quadro AS tipo_quadro_id \n" +
            "FROM _replaceTableName_ srtq \n" +
            "INNER JOIN scriva_d_template sdt ON sdt.id_template = srtq.id_template \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdt.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n" +
            "INNER JOIN scriva_d_quadro sdq ON sdq.id_quadro = srtq.id_quadro \n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdtq.id_tipo_quadro = sdq.id_tipo_quadro \n" +
            "WHERE sdt.flg_attivo = 1 \n" +
            "AND sdq.flg_attivo = 1 \n";

    private static final String QUERY_QUADRI_ATTIVI = "SELECT srtq.*, \n" +
            "sdt.*, sdt.id_template AS template_id, \n" +
            "sdad.*, sdad.id_adempimento AS adempimento_id, sdad.flg_attivo AS adempimento_attivo, \n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo, \n" +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo, \n" +
            "sdq.*, sdq.id_quadro AS quadro_id, \n" +
            "sdtq.*, sdtq.id_tipo_quadro AS tipo_quadro_id \n" +
            "FROM _replaceTableName_ srtq \n" +
            "INNER JOIN scriva_d_template sdt ON sdt.id_template = srtq.id_template \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdt.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n" +
            "INNER JOIN scriva_d_quadro sdq ON sdq.id_quadro = srtq.id_quadro \n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdtq.id_tipo_quadro = sdq.id_tipo_quadro \n" +
            "WHERE (1=1) \n" +
            "AND sdq.flg_attivo = 1 \n";        

    private static final String CLAUSE_ORDER_BY = " ORDER BY srtq.ordinamento_template_quadro";

    private static final String QUERY_PRIMARY_KEY = QUERY_TEMPLATE_QUADRI_ATTIVI + " AND id_template_quadro = :idTemplateQuadro\n";

    private static final String QUERY_TEMPLATE_QUADRO_BY_ID_TEMPLATE_AND_ID_QUADRO = QUERY_TEMPLATE_QUADRI_ATTIVI + " AND srtq.id_template = :idTemplate \n" +
            "AND srtq.id_quadro = :idQuadro \n";

    private static final String QUERY_TEMPLATE_QUADRO_BY_CODE_ADEMPIMENTO = QUERY_TEMPLATE_QUADRI_ATTIVI + " AND UPPER(sdad.cod_adempimento) = UPPER(:codeAdempimento) \n";

    private static final String QUERY_TEMPLATE_QUADRO_BY_CODE_TEMPLATE = QUERY_TEMPLATE_QUADRI_ATTIVI + " AND UPPER(sdt.cod_template) = UPPER(:codeTemplate) \n";

    /**
     * Load template quadri attivi list.
     *
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    @Override
    public List<TemplateQuadroExtendedDTO> loadTemplateQuadriAttivi(String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TEMPLATE_QUADRI_ATTIVI;
        Map<String, Object> map = new HashMap<>();
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_IND_VISIBILE;
        }
        return findListByQuery(className, query, null);
    }

    /**
     * Load template quadro by id list.
     *
     * @param idTemplateQuadro idTemplateQuadro
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    @Override
    public List<TemplateQuadroExtendedDTO> loadTemplateQuadroById(Long idTemplateQuadro, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_QUADRI_ATTIVI + " AND id_template_quadro = :idTemplateQuadro\n";
        Map<String, Object> map = new HashMap<>();
        map.put("idTemplateQuadro", idTemplateQuadro);
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_IND_VISIBILE;
        }
        return findListByQuery(className, query + CLAUSE_ORDER_BY, map);
    }

    /**
     * Load template quadro by id template id quadro list.
     *
     * @param idTemplate       idTemplate
     * @param idQuadro         idQuadro
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    @Override
    public List<TemplateQuadroExtendedDTO> loadTemplateQuadroByIdTemplateIdQuadro(Long idTemplate, Long idQuadro, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TEMPLATE_QUADRO_BY_ID_TEMPLATE_AND_ID_QUADRO;
        Map<String, Object> map = new HashMap<>();
        map.put("idTemplate", idTemplate);
        map.put("idQuadro", idQuadro);
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_IND_VISIBILE;
        }
        return findListByQuery(className, query + CLAUSE_ORDER_BY, map);
    }

    /**
     * Load template quadro by code adempimento list.
     *
     * @param codeAdempimento  codeAdempimento
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    @Override
    public List<TemplateQuadroExtendedDTO> loadTemplateQuadroByCodeAdempimento(String codeAdempimento, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TEMPLATE_QUADRO_BY_CODE_ADEMPIMENTO;
        Map<String, Object> map = new HashMap<>();
        map.put("codeAdempimento", codeAdempimento);
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_IND_VISIBILE;
        }
        return findListByQuery(className, query + CLAUSE_ORDER_BY, map);
    }

    /**
     * Load template quadro by code template list.
     *
     * @param codeTemplate     codeTemplate
     * @param codComponenteApp the cod componente app
     * @return List<TemplateQuadroExtendedDTO>   list
     */
    @Override
    public List<TemplateQuadroExtendedDTO> loadTemplateQuadroByCodeTemplate(String codeTemplate, String codComponenteApp) {
        logBegin(className);
        String query = QUERY_TEMPLATE_QUADRO_BY_CODE_TEMPLATE;
        Map<String, Object> map = new HashMap<>();
        map.put("codeTemplate", codeTemplate);
        map.put("codComponenteApp", codComponenteApp);
        if (StringUtils.isNotBlank(codComponenteApp)) {
            query += WHERE_IND_VISIBILE;
        }
        return findListByQuery(className, query + CLAUSE_ORDER_BY, map);
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
     * @return RowMapper<TemplateQuadroDTO>
     */
    @Override
    public RowMapper<TemplateQuadroDTO> getRowMapper() throws SQLException {
        return new TemplateQuadroRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<TemplateQuadroExtendedDTO>
     */
    @Override
    public RowMapper<TemplateQuadroExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TemplateQuadroExtendedRowMapper();
    }

    /**
     * The type Template quadro row mapper.
     */
    public static class TemplateQuadroRowMapper implements RowMapper<TemplateQuadroDTO> {

        /**
         * Instantiates a new Template quadro row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TemplateQuadroRowMapper() throws SQLException {
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
        public TemplateQuadroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateQuadroDTO bean = new TemplateQuadroDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TemplateQuadroDTO bean) throws SQLException {
            bean.setIdTemplateQuadro(rs.getLong("id_template_quadro"));
            bean.setIdTemplate(rs.getLong("id_template"));
            bean.setIdQuadro(rs.getLong("id_quadro"));
            bean.setOrdinamentoTemplateQuadro(rs.getInt("ordinamento_template_quadro"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setJsonVestizioneQuadro(rs.getString("json_vestizione_quadro"));
        }
    }

    /**
     * The type Template quadro extended row mapper.
     */
    public static class TemplateQuadroExtendedRowMapper implements RowMapper<TemplateQuadroExtendedDTO> {

        /**
         * Instantiates a new Template quadro extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TemplateQuadroExtendedRowMapper() throws SQLException {
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
        public TemplateQuadroExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TemplateQuadroExtendedDTO bean = new TemplateQuadroExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TemplateQuadroExtendedDTO bean) throws SQLException {
            bean.setIdTemplateQuadro(rs.getLong("id_template_quadro"));

            TemplateExtendedDTO template = new TemplateExtendedDTO();
            this.populateBeanTemplate(rs, template);
            bean.setTemplate(template);

            QuadroExtendedDTO quadro = new QuadroExtendedDTO();
            this.populateBeanQuadro(rs, quadro);
            bean.setQuadro(quadro);

            bean.setOrdinamentoTemplateQuadro(rs.getInt("ordinamento_template_quadro"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setJsonVestizioneQuadro(rs.getString("json_vestizione_quadro"));
        }

        private void populateBeanTemplate(ResultSet rs, TemplateExtendedDTO bean) throws SQLException {
            bean.setIdTemplate(rs.getLong("template_id"));

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimentoExtended(rs, adempimento);
            bean.setAdempimento(adempimento);

            bean.setCodTemplate(rs.getString("cod_template"));
            bean.setDesTemplate(rs.getString("des_template"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataCessazione(rs.getDate("data_cessazione"));
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
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }

        private void populateBeanQuadro(ResultSet rs, QuadroExtendedDTO bean) throws SQLException {
            bean.setIdQuadro(rs.getLong("quadro_id"));
            TipoQuadroDTO tipoQuadro = new TipoQuadroDTO();
            this.populateBeanTipoQuadro(rs, tipoQuadro);
            bean.setTipoQuadro(tipoQuadro);
            bean.setCodQuadro(rs.getString("cod_quadro"));
            bean.setDesQuadro(rs.getString("des_quadro"));
            bean.setFlgTipoGestione(rs.getString("flg_tipo_gestione"));
            bean.setJsonConfiguraQuadro(rs.getString("json_configura_quadro"));
            bean.setJsonConfiguraRiepilogo(rs.getString("json_configura_riepilogo"));
        }

        private void populateBeanTipoQuadro(ResultSet rs, TipoQuadroDTO bean) throws SQLException {
            bean.setIdTipoQuadro(rs.getLong("tipo_quadro_id"));
            bean.setCodiceTipoQuadro(rs.getString("cod_tipo_quadro"));
            bean.setDescrizioneTipoQuadro(rs.getString("des_tipo_quadro"));
        }
    }

}