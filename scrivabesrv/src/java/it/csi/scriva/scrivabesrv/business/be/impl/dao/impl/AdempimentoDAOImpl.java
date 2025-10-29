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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.AttoreScriva;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoDTO> implements AdempimentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_FLG_ATTIVI = "AND sda.flg_attivo = 1 AND sdta.flg_attivo = 1 AND sdad.flg_attivo = 1\n";

    private static final String WHERE_ID_AMBITO = "AND sda.id_ambito = :idAmbito\n";

    private static final String WHERE_COD_AMBITO = "AND UPPER(sda.cod_ambito) = UPPER(:codAmbito)\n";

    private static final String WHERE_ID_TIPO_ADEMPIMENTO = "AND sdta.id_tipo_adempimento = :idTipoAdempimento\n";

    private static final String WHERE_COD_TIPO_ADEMPIMENTO = "AND UPPER(sdta.cod_tipo_adempimento) = UPPER(:codTipoAdempimento)\n";

    private static final String WHERE_COD_ADEMPIMENTO = "AND UPPER(sdad.cod_adempimento) = UPPER(:codAdempimento)\n";

    private static final String WHERE_ID_COMPILANTE = "AND \n";

    private static final String ORDER_BY_ORDINAMENTO_ADEMPIMENTO = "ORDER BY sdad.ordinamento_adempimento";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ where id_adempimento = :idAdempimento\n";

    private static final String WHERE_COMPONENTE = "AND sdad.ind_visibile LIKE '%'||:codComponenteApp||'%'\n";

    private static final String QUERY_LOAD_ADEMPIMENTI_ATTIVI = "SELECT sdad.*, sdad.flg_attivo AS adempimento_attivo,\n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo,\n" +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo\n" +
            "FROM _replaceTableName_ sdad\n " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sdta.id_ambito = sda.id_ambito\n" +
            "WHERE 1 = 1\n" +
            WHERE_FLG_ATTIVI;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO = "SELECT sdad.*, sdad.flg_attivo AS adempimento_attivo,\n" +
            "sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id, sdta.flg_attivo AS tipo_adempimento_attivo,\n" +
            "sda.*, sda.id_ambito AS ambito_id, sda.flg_attivo AS ambito_attivo\n" +
            "FROM scriva_t_funzionario AS stf\n" +
            "INNER JOIN scriva_r_funzionario_profilo srfp ON srfp.id_funzionario = stf.id_funzionario AND srfp.data_fine_validita IS NULL\n" +
            "INNER JOIN scriva_r_tipo_adempi_profilo srtap ON srtap.id_profilo_app = srfp.id_profilo_app\n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = srtap.id_tipo_adempimento\n" +
            "INNER JOIN _replaceTableName_ sdad ON sdad.id_tipo_adempimento = sdta.id_tipo_adempimento\n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            WHERE_FLG_ATTIVI +
            "AND stf.cf_funzionario = :cfFunzionario\n";

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_ID = QUERY_LOAD_ADEMPIMENTI_ATTIVI + "AND sdad.id_adempimento = :idAdempimento\n";

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CODE = QUERY_LOAD_ADEMPIMENTI_ATTIVI + WHERE_COD_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_ID_TIPO_ADEMPIMENTO = QUERY_LOAD_ADEMPIMENTI_ATTIVI +
            WHERE_ID_TIPO_ADEMPIMENTO +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_ID_TIPO_ADEMPIMENTO_CF_FUNZIONARIO = QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO +
            WHERE_ID_TIPO_ADEMPIMENTO +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CODE_TIPO_ADEMPIMENTO = QUERY_LOAD_ADEMPIMENTI_ATTIVI +
            WHERE_COD_TIPO_ADEMPIMENTO +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CODE_TIPO_ADEMPIMENTO_CF_FUNZIONARIO = QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO +
            WHERE_COD_TIPO_ADEMPIMENTO +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_ID_AMBITO = QUERY_LOAD_ADEMPIMENTI_ATTIVI + "AND sda.id_ambito = :idAmbito " + ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_ID_AMBITO_CF_FUNZIONARIO = QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO +
            WHERE_ID_AMBITO +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CODE_AMBITO = QUERY_LOAD_ADEMPIMENTI_ATTIVI + "AND UPPER(sda.cod_ambito) = UPPER(:codAmbito) " + ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    private static final String QUERY_LOAD_ADEMPIMENTI_BY_CODE_AMBITO_CF_FUNZIONARIO = QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO +
            "AND UPPER(sda.cod_ambito) = UPPER(:codAmbito) " +
            ORDER_BY_ORDINAMENTO_ADEMPIMENTO;

    /**
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentiAttivi() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_ATTIVI + ORDER_BY_ORDINAMENTO_ADEMPIMENTO, null);
    }

    /**
     * Load adempimenti attivi list.
     *
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @param codComponenteApp   the cod componente app
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimenti(Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante, String codComponenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_LOAD_ADEMPIMENTI_ATTIVI;
        if (idAmbito != null) {
            map.put("idAmbito", idAmbito);
            query += WHERE_ID_AMBITO;
        }
        if (StringUtils.isNotBlank(codAmbito)) {
            map.put("codAmbito", codAmbito);
            query += WHERE_COD_AMBITO;
        }
        if (idTipoAdempimento != null) {
            map.put("idTipoAdempimento", idTipoAdempimento);
            query += WHERE_ID_TIPO_ADEMPIMENTO;
        }
        if (StringUtils.isNotBlank(codTipoAdempimento)) {
            map.put("codTipoAdempimento", codTipoAdempimento);
            query += WHERE_COD_TIPO_ADEMPIMENTO;
        }
        if (StringUtils.isNotBlank(codAdempimento)) {
            map.put("codAdempimento", codAdempimento);
            query += WHERE_COD_ADEMPIMENTO;
        }
        if (idCompilante != null) {
            map.put("idCompilante", idCompilante);
            query += WHERE_ID_COMPILANTE;
        }

        if (codComponenteApp != null) {
        	map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE;
        }

        return findListByQuery(className, query + ORDER_BY_ORDINAMENTO_ADEMPIMENTO, map);
    }

    /**
     * Load adempimento by cf funzionario list.
     *
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCfFunzionario(String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO + ORDER_BY_ORDINAMENTO_ADEMPIMENTO, map);
    }

    /**
     * Load adempimento by cf funzionario list.
     *
     * @param attoreScriva       the attore scriva
     * @param idAmbito           the id ambito
     * @param codAmbito          the cod ambito
     * @param idTipoAdempimento  the id tipo adempimento
     * @param codTipoAdempimento the cod tipo adempimento
     * @param codAdempimento     the cod adempimento
     * @param idCompilante       the id compilante
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCfFunzionario(AttoreScriva attoreScriva, Long idAmbito, String codAmbito, Long idTipoAdempimento, String codTipoAdempimento, String codAdempimento, Long idCompilante) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_LOAD_ADEMPIMENTI_BY_CF_FUNZIONARIO;
        if (idAmbito != null) {
            map.put("idAmbito", idAmbito);
            query += WHERE_ID_AMBITO;
        }
        if (StringUtils.isNotBlank(codAmbito)) {
            map.put("codAmbito", codAmbito);
            query += WHERE_COD_AMBITO;
        }
        if (idTipoAdempimento != null) {
            map.put("idTipoAdempimento", idTipoAdempimento);
            query += WHERE_ID_TIPO_ADEMPIMENTO;
        }
        if (StringUtils.isNotBlank(codTipoAdempimento)) {
            map.put("codTipoAdempimento", codTipoAdempimento);
            query += WHERE_COD_TIPO_ADEMPIMENTO;
        }
        if (StringUtils.isNotBlank(codAdempimento)) {
            map.put("codAdempimento", codAdempimento);
            query += WHERE_COD_ADEMPIMENTO;
        }
        if (idCompilante != null) {
            map.put("idCompilante", idCompilante);
            query += WHERE_ID_COMPILANTE;
        }

        map.put("codComponenteApp", attoreScriva.getComponente());
        query += WHERE_COMPONENTE;

        map.put("cfFunzionario", attoreScriva.getCodiceFiscale());

        return findListByQuery(className, query + ORDER_BY_ORDINAMENTO_ADEMPIMENTO, map);
    }

    /**
     * @param idAdempimento idAdempimento
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoById(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_ID, map);
    }

    /**
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCode(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CODE, map);
    }

    /**
     * @param idTipoAdempimento idTipoAdempimento
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByIdTipoAdempimento(Long idTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimento", idTipoAdempimento);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_ID_TIPO_ADEMPIMENTO, map);
    }

    /**
     * Load adempimento by id tipo adempimento list.
     *
     * @param idTipoAdempimento the id tipo adempimento
     * @param cfFunzionario     the cf funzionario
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByIdTipoAdempimentoCfFunzionario(Long idTipoAdempimento, String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTipoAdempimento", idTipoAdempimento);
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_ID_TIPO_ADEMPIMENTO_CF_FUNZIONARIO, map);
    }


    /**
     * @param codTipoAdempimento codTipoAdempimento
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCodeTipoAdempimento(String codTipoAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoAdempimento", codTipoAdempimento);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CODE_TIPO_ADEMPIMENTO, map);
    }

    /**
     * Load adempimento by code tipo adempimento cf funzionario list.
     *
     * @param codTipoAdempimento the cod tipo adempimento
     * @param cfFunzionario      the cf funzionario
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCodeTipoAdempimentoCfFunzionario(String codTipoAdempimento, String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codTipoAdempimento", codTipoAdempimento);
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CODE_TIPO_ADEMPIMENTO_CF_FUNZIONARIO, map);

    }

    /**
     * @param idAmbito idAmbito
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByIdAmbito(Long idAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAmbito", idAmbito);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_ID_AMBITO, map);
    }

    /**
     * Load adempimento by id ambito cf funzionario list.
     *
     * @param idAmbito      the id ambito
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByIdAmbitoCfFunzionario(Long idAmbito, String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAmbito", idAmbito);
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_ID_AMBITO_CF_FUNZIONARIO, map);
    }

    /**
     * @param codAmbito codAmbito
     * @return List<AdempimentoExtendedDTO>
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCodeAmbito(String codAmbito) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAmbito", codAmbito);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CODE_AMBITO, map);
    }

    /**
     * Load adempimento by code ambito cf funzionario list.
     *
     * @param codAmbito     the cod ambito
     * @param cfFunzionario the cf funzionario
     * @return the list
     */
    @Override
    public List<AdempimentoExtendedDTO> loadAdempimentoByCodeAmbitoCfFunzionario(String codAmbito, String cfFunzionario) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAmbito", codAmbito);
        map.put("cfFunzionario", cfFunzionario);
        return findListByQuery(className, QUERY_LOAD_ADEMPIMENTI_BY_CODE_AMBITO_CF_FUNZIONARIO, map);
    }

    /**
     * @param idAdempimento idAdempimento
     * @return AdempimentoDTO
     */
    @Override
    public AdempimentoDTO findByPK(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findByPK(className, map);
    }

    /**
     * @param adempimentoDTO AdempimentoDTO
     * @return id record salvato
     */
    @Override
    public Long saveAdempimento(AdempimentoDTO adempimentoDTO) {
        return null;
    }

    /**
     * @param adempimentoDTO AdempimentoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateAdempimento(AdempimentoDTO adempimentoDTO) {
        return null;
    }

    /**
     * @param idAdempimento idAdempimento
     * @return numero record cancellati
     */
    @Override
    public Integer deleteAdempimento(Long idAdempimento) {
        return null;
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
     * @return RowMapper<AdempimentoDTO>
     * @throws SQLException SQLException
     */
    @Override
    public RowMapper<AdempimentoDTO> getRowMapper() throws SQLException {
        return new AdempimentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<AdempimentoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoExtendedRowMapper();
    }

    /**
     * Inner class AdempimentoRowMapper
     */
    private static class AdempimentoRowMapper implements RowMapper<AdempimentoDTO> {

        /**
         * Instantiates a new Adempimento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoRowMapper() throws SQLException {
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
        public AdempimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoDTO bean = new AdempimentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * @param rs   the ResultSet
         * @param bean AdempimentoDTO
         */
        private void populateBean(ResultSet rs, AdempimentoDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
            bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoAdempimento(rs.getInt("ordinamento_adempimento"));
        }
    }

    /**
     * Inner class AdempimentoExtendedRowMapper
     */
    private static class AdempimentoExtendedRowMapper implements RowMapper<AdempimentoExtendedDTO> {

        /**
         * Instantiates a new Adempimento extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoExtendedRowMapper() throws SQLException {
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
        public AdempimentoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoExtendedDTO bean = new AdempimentoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
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
    }
}