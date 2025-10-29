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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaTemplateQuadroDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaTemplateQuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.QuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateQuadroExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoQuadroDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Istanza template quadro dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaTemplateQuadroDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaTemplateQuadroDTO> implements IstanzaTemplateQuadroDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_ISTANZE_TEMPLATE_QUADRO = "SELECT sti.*\n " +
            ", srtq.ordinamento_template_quadro, srtq.id_template_quadro, srtq.ind_visibile, srtq.json_vestizione_quadro\n" +
            ", sdt.* \n" +
            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
            ", sda.*, sda.id_ambito AS ambito_id \n" +
            ", sdq.* \n" +
            ", sdtq.cod_tipo_quadro, sdtq.des_tipo_quadro \n" +
            "FROM scriva_t_istanza sti \n" +
            "INNER JOIN scriva_d_template sdt ON sdt.id_template = sti.id_template \n" +
            "INNER JOIN scriva_r_template_quadro srtq ON srtq.id_template = sdt.id_template \n" +
            "INNER JOIN scriva_d_quadro sdq ON sdq.id_quadro = srtq.id_quadro \n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdtq.id_tipo_quadro = sdq.id_tipo_quadro \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdt.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n" +
            "WHERE sdq.flg_attivo = 1 \n";

    // SCRIVA-1416 con una sola query estraiamo i quadri del template corrente per l'adempimento a cui apaprtiene l'istanza se l'istanza è in stato bozza o inserita bo, 
    //diversamente vengono estratti i quadri del template storico con il quale è stata creata l'istanza a suo tempo (che puo esere diverso dal corrente)
    private static final String QUERY_ISTANZE_TEMPLATE_QUADRO_CON_STORICO = 
            "SELECT \n" +
            "sti.*, \n" +
            "srtq.ordinamento_template_quadro, srtq.id_template_quadro, srtq.ind_visibile, srtq.json_vestizione_quadro, \n" +
            "sdt.*, \n" +
            "sdad.*, sdad.id_adempimento AS adempimento_id, \n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, \n" +
            "sda.*, sda.id_ambito AS ambito_id, \n" +
            "sdq.*, \n" +
            "sdtq.cod_tipo_quadro, sdtq.des_tipo_quadro \n" +
            "FROM scriva_t_istanza sti \n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sti.id_stato_istanza = sdsi.id_stato_istanza \n" +
            "INNER JOIN scriva_d_template sdt ON (\n" +
            "    (sdsi.cod_stato_istanza IN ('BOZZA', 'INSERITA BO') AND sdt.id_adempimento = sti.id_adempimento AND sdt.flg_attivo = 1) OR \n" +
            "    (sdsi.cod_stato_istanza NOT IN ('BOZZA', 'INSERITA BO') AND sdt.id_template = sti.id_template) \n" +
            ") \n" +
            "INNER JOIN scriva_r_template_quadro srtq ON srtq.id_template = sdt.id_template \n" +
            "INNER JOIN scriva_d_quadro sdq ON sdq.id_quadro = srtq.id_quadro \n" +
            "INNER JOIN scriva_d_tipo_quadro sdtq ON sdtq.id_tipo_quadro = sdq.id_tipo_quadro \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdt.id_adempimento = sdad.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito \n" +
            "WHERE (1=1) \n";    

    private static final String QUERY_PRIMARY_KEY = QUERY_ISTANZE_TEMPLATE_QUADRO + "AND sti.id_istanza = :idIstanza \n" +
            "AND srtq.id_template_quadro = :idTemplateQuadro \n";

    private static final String QUERY_ISTANZA_TEMPLATE_QUADRO_BY_ID_ISTANZA = QUERY_ISTANZE_TEMPLATE_QUADRO_CON_STORICO + "AND sti.id_istanza = :idIstanza \n";

    private static final String QUERY_GET_ISTANZA_TEMPLATE_PATH_BY_ID_ISTANZA = "SELECT DISTINCT sdt.pdf_template \n" +
            "FROM _replaceTableName_ sritq \n" +
            "INNER JOIN scriva_r_template_quadro srtq ON srtq.id_template = sritq.id_template_quadro \n" +
            "INNER JOIN scriva_d_template sdt ON sdt.id_template =srtq.id_template \n" +
            "WHERE sritq.id_istanza = :idIstanza \n";

    private static final String QUERY_GET_ALLEGATI_TEMPLATE_PATH_BY_ID_ISTANZA = "SELECT DISTINCT sdt.pdf_template \n" +
            "FROM _replaceTableName_ sritq \n" +
            "INNER JOIN scriva_r_template_quadro srtq ON srtq.id_template = sritq.id_template_quadro \n" +
            "INNER JOIN scriva_d_template sdt ON sdt.id_template =srtq.id_template \n" +
            "WHERE sritq.id_istanza = :idIstanza \n";

    /**
     * Load istanze template quadro list.
     *
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    @Override
    public List<IstanzaTemplateQuadroExtendedDTO> loadIstanzeTemplateQuadro() {
        logBegin(className);
        return findListByQuery(className, QUERY_ISTANZE_TEMPLATE_QUADRO, null);
    }

    /**
     * Load istanza template quadro by id istanza list.
     *
     * @param idIstanza idIstanza
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    @Override
    public List<IstanzaTemplateQuadroExtendedDTO> loadIstanzaTemplateQuadroByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        List<IstanzaTemplateQuadroExtendedDTO> listQuery = findListByQuery(className, QUERY_ISTANZA_TEMPLATE_QUADRO_BY_ID_ISTANZA, map);
        return listQuery;
    }

    /**
     * Load istanza template quadro by pk list.
     *
     * @param idIstanza        idIstanza
     * @param idTemplateQuadro idTemplateQuadro
     * @return List<IstanzaTemplateQuadroExtendedDTO> list
     */
    @Override
    public List<IstanzaTemplateQuadroExtendedDTO> loadIstanzaTemplateQuadroByPK(Long idIstanza, Long idTemplateQuadro) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("idTemplateQuadro", idTemplateQuadro);
        return findListByQuery(className, getPrimaryKeySelect(), map);
    }

    /**
     * @param idIstanza the id istanza
     * @param query     the query
     * @return the string
     */
    public String loadPathTemplateByIdIstanza(Long idIstanza, String query) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, query, map);
    }

    /**
     * @param idIstanza idIstanza
     * @return percorso del template
     */
    @Override
    public String loadPathIstanzaTemplateByIdIstanza(Long idIstanza) {
        logBegin(className);
        try {
            return this.loadPathTemplateByIdIstanza(idIstanza, QUERY_GET_ISTANZA_TEMPLATE_PATH_BY_ID_ISTANZA);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param idIstanza idIstanza
     * @return percorso del template
     */
    @Override
    public String loadPathAllegatiTemplateByIdIstanza(Long idIstanza) {
        logBegin(className);
        try {
            return this.loadPathTemplateByIdIstanza(idIstanza, QUERY_GET_ALLEGATI_TEMPLATE_PATH_BY_ID_ISTANZA);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
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
     * @return RowMapper<IstanzaTemplateQuadroDTO>
     */
    @Override
    public RowMapper<IstanzaTemplateQuadroDTO> getRowMapper() throws SQLException {
        return new IstanzaTemplateQuadroRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<IstanzaTemplateQuadroExtendedDTO>
     */
    @Override
    public RowMapper<IstanzaTemplateQuadroExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaTemplateQuadroExtendedRowMapper();
    }

    /**
     * Gets string row mapper.
     *
     * @return the string row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<String> getStringRowMapper() throws SQLException {
        return new StringRowMapper();
    }

    /**
     * The type String row mapper.
     */
    public static class StringRowMapper implements RowMapper<String> {
        /**
         * Instantiates a new String row mapper.
         *
         * @throws SQLException the sql exception
         */
        public StringRowMapper() throws SQLException {
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
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("pdf_template");
        }
    }

    /**
     * The type Istanza template quadro row mapper.
     */
    public static class IstanzaTemplateQuadroRowMapper implements RowMapper<IstanzaTemplateQuadroDTO> {

        /**
         * Instantiates a new Istanza template quadro row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaTemplateQuadroRowMapper() throws SQLException {
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
        public IstanzaTemplateQuadroDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaTemplateQuadroDTO bean = new IstanzaTemplateQuadroDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaTemplateQuadroDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTemplateQuadro(rs.getLong("id_template_quadro"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
        }
    }

    /**
     * The type Istanza template quadro extended row mapper.
     */
    public static class IstanzaTemplateQuadroExtendedRowMapper implements RowMapper<IstanzaTemplateQuadroExtendedDTO> {

        /**
         * Instantiates a new Istanza template quadro extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaTemplateQuadroExtendedRowMapper() throws SQLException {
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
        public IstanzaTemplateQuadroExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaTemplateQuadroExtendedDTO bean = new IstanzaTemplateQuadroExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaTemplateQuadroExtendedDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            TemplateQuadroExtendedDTO templateQuadro = new TemplateQuadroExtendedDTO();
            populateBeanTemplateQuadro(rs, templateQuadro);
            bean.setTemplateQuadro(templateQuadro);
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
        }

        private void populateBeanTemplateQuadro(ResultSet rs, TemplateQuadroExtendedDTO bean) throws SQLException {
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
            bean.setIdTemplate(rs.getLong("id_template"));

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimentoExtended(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);

            bean.setCodTemplate(rs.getString("cod_template"));
            bean.setDesTemplate(rs.getString("des_template"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataCessazione(rs.getDate("data_cessazione"));
            bean.setJsonConfguraTemplate(rs.getString("json_configura_template"));
        }

        private void populateBeanAdempimentoExtended(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoExtended(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }

        private void pupulateTipoAdempimentoExtended(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
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
            bean.setIdQuadro(rs.getLong("id_quadro"));
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
            bean.setIdTipoQuadro(rs.getLong("id_tipo_quadro"));
            bean.setCodiceTipoQuadro(rs.getString("cod_tipo_quadro"));
            bean.setDescrizioneTipoQuadro(rs.getString("des_tipo_quadro"));
        }

    }

}