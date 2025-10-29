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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoConfigDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoConfigDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento config dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoConfigDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoConfigDTO> implements AdempimentoConfigDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY = "ORDER BY id_informazione_scriva, chiave, ordinamento";
    private static final String SELECT_ADEMPIMENTO_CONFIG = "SELECT srac.*, sda.des_adempimento, sdis.cod_informazione_scriva\n"
            + "FROM _replaceTableName_ srac\n"
            + "INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = srac.id_adempimento\n"
            + "INNER JOIN scriva_d_informazioni_scriva sdis ON sdis.id_informazione_scriva = srac.id_informazione_scriva\n"
            + "WHERE srac.flg_attivo = 1\n";

    private static final String SELECT_ADEMPIMENTO_CONFIG_WITH_CONDICTION = "SELECT srac.*, sda.des_adempimento, sdis.cod_informazione_scriva\n"
            + "FROM _replaceTableName_ srac\n"
            + "INNER JOIN scriva_d_adempimento sda ON sda.id_adempimento = srac.id_adempimento\n"
            + "INNER JOIN scriva_d_informazioni_scriva sdis ON sdis.id_informazione_scriva = srac.id_informazione_scriva\n"
            + " __dynamic_inner_join_conditions__ \n"
            + "WHERE srac.flg_attivo = 1\n";

    private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO = SELECT_ADEMPIMENTO_CONFIG
            + "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n" + ORDER_BY;

    private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO_INFO = SELECT_ADEMPIMENTO_CONFIG
            + "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n AND UPPER(sdis.cod_informazione_scriva) = UPPER(:infoScriva)\n"
            + ORDER_BY;

    private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO_INFO_KEY = SELECT_ADEMPIMENTO_CONFIG
            + "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n"
            + "AND UPPER(sdis.cod_informazione_scriva) = UPPER(:infoScriva)\n"
            + "AND srac.chiave LIKE '%'||:chiave||'%'\n" + ORDER_BY;

   /*  private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_COMPONETE = 
            "select\n" + 
            "sda.des_adempimento as chiave,\n" + 
            "sdta.cod_tipologia_allegato as valore\n" + 
            "from\n" + 
            "scriva_r_adempi_tipo_allegato srata\n" + 
            "inner join scriva_d_adempimento sda on\n" + 
            "sda.id_adempimento = srata.id_adempimento\n" + 
            "inner join scriva_d_tipologia_allegato sdta on\n" + 
            "sdta.id_tipologia_allegato = srata.id_tipologia_allegato\n" + 
            "where\n" + 
            "srata.ind_modifica like '%' ||:componente_applicativa || '%'\n" + 
            "and UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n" + 
            "and coalesce(srata.data_fine_validita,\n" + 
            "now()) >= now()\n" + 
            "order by\n" + 
            "srata.ordinamento_adem_tipo_allega\n";*/

            private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_COMPONETE = 
            "WITH configura_adempi AS (\n" + 
            "SELECT\n" + 
            "srac.id_adempimento,\n" + 
            "note,\n" + 
            "srac.id_adempi_config,\n" + 
            "sdis.id_informazione_scriva,\n" +
            "srac.flg_attivo,\n" +
            "sdis.cod_informazione_scriva,\n" + 
            "srac.valore\n" + 
            "FROM\n" + 
            "scriva_r_adempi_config srac\n" + 
            "INNER JOIN scriva_d_adempimento sda2 ON\n" + 
            "sda2.id_adempimento = srac.id_adempimento\n" + 
            "INNER JOIN scriva_d_informazioni_scriva sdis ON\n" + 
            "sdis.id_informazione_scriva = srac.id_informazione_scriva\n" + 
            "WHERE\n" + 
            "UPPER(sda2.cod_adempimento) = UPPER(:codAdempimento)\n" + 
            "AND srac.chiave LIKE '%' || :chiave || '%'\n" + 
            "LIMIT 1\n" + 
            ")\n" + 
            "SELECT\n" + 
            "note,\n" + 
            "id_adempi_config,\n" + 
            "srata.id_adempimento,\n" + 
            "sda.des_adempimento,\n" + 
            "id_informazione_scriva,\n" + 
            "cod_informazione_scriva,\n" + 
            "ca.flg_attivo,\n" +
            ":chiave AS chiave,\n" + 
            "sdta.cod_tipologia_allegato AS valore,\n" + 
            "srata.ordinamento_adem_tipo_allega AS ordinamento\n" + 
            "FROM\n" + 
            "scriva_r_adempi_tipo_allegato srata\n" + 
            "INNER JOIN scriva_d_adempimento sda ON\n" + 
            "sda.id_adempimento = srata.id_adempimento\n" + 
            "INNER JOIN scriva_d_tipologia_allegato sdta ON\n" + 
            "sdta.id_tipologia_allegato = srata.id_tipologia_allegato\n" + 
            "INNER JOIN configura_adempi ca ON\n" + 
            "ca.id_adempimento = srata.id_adempimento\n" + 
            "WHERE\n" + 
            "srata.ind_modifica LIKE '%' || :componente_applicativa || '%'\n" + 
            "AND UPPER(sda.cod_adempimento) = UPPER(:codAdempimento)\n" + 
            "AND COALESCE(srata.data_fine_validita, NOW()) >= NOW()\n" + 
            "ORDER BY\n" + 
            "srata.ordinamento_adem_tipo_allega\n";
                
    private static final String QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_ID_ADEMPIMENTO_KEY = SELECT_ADEMPIMENTO_CONFIG
            + "AND srac.id_adempimento = :idAdempimento\n"
            + "AND srac.chiave LIKE '%'||:chiave||'%'\n" + ORDER_BY;

    private static final String WHERE_COD_TIPO_ADEMPIMENTO = "AND sdta.cod_tipo_adempimento=:codTipoAdempimento\n";

    private static final String WHERE_INFO = "AND UPPER(sdis.cod_informazione_scriva) = UPPER(:info)\n";

    private static final String WHERE_CHIAVE = "AND UPPER(srac.chiave) = UPPER(:chiave)\n";

    private static final String INNER_JOIN_TIPO_ADEMPIMENTO = "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n";

    private static final String INNER_JOIN_ISTANZA = "INNER JOIN scriva_t_istanza sti ON sti.id_adempimento = srac.id_adempimento\n";

    /**
     * Load adempimenti config list.
     *
     * @return List<AdempimentoConfigDTO> list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentiConfig(String codTipoAdempimento, String info) {
        logBegin(className);
        StringBuilder dynamicInnerJoinConditions = new StringBuilder();
        String querybase = SELECT_ADEMPIMENTO_CONFIG_WITH_CONDICTION;
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(codTipoAdempimento)) {
            map.put("codTipoAdempimento", codTipoAdempimento);
            querybase += WHERE_COD_TIPO_ADEMPIMENTO;
            dynamicInnerJoinConditions.append(INNER_JOIN_TIPO_ADEMPIMENTO);
        }
        if (StringUtils.isNotBlank(info)) {
            map.put("info", info);
            querybase += WHERE_INFO;
        }
        String query = querybase.replace("__dynamic_inner_join_conditions__",
                "\n" + dynamicInnerJoinConditions.toString());

        return findListByQuery(className, query, map);
    }

    /**
     * Load adempimento config by adempimento list.
     *
     * @param codAdempimento codAdempimento
     * @return List<AdempimentoConfigDTO> list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimento(String codAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        return findListByQuery(className, QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO, map);
    }

    /**
     * Load adempimento config by adempimento informazione list.
     *
     * @param codAdempimento codAdempimento
     * @param infoScriva     infoScriva
     * @return List<AdempimentoConfigDTO> list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoInformazione(String codAdempimento, String infoScriva) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("infoScriva", infoScriva);
        return findListByQuery(className, QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO_INFO, map);
    }

    /**
     * Load adempimento config by adempimento informazione chiave list.
     *
     * @param codAdempimento codAdempimento
     * @param infoScriva     infoScriva
     * @param chiave         chiave
     * @return List<AdempimentoConfigDTO> list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoInformazioneChiave(String codAdempimento, String infoScriva, String chiave) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("infoScriva", infoScriva);
        map.put("chiave", chiave);
        return findListByQuery(className, QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_TIPO_ADEMPIMENTO_INFO_KEY, map);
    }

    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByAdempimentoComponenteChiave(String codAdempimento, String componenteApp, String chiave ) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("codAdempimento", codAdempimento);
        map.put("componente_applicativa", componenteApp);
        map.put("chiave", chiave);
       
        return findListByQuery(className, QUERY_SELECT_ADEMPIMENTO_CONFIG_COMPONETE, map);
    }
    
    /**
     * Load adempimento config by adempimento informazione chiave list.
     *
     * @param idAdempimento the id adempimento
     * @param chiave         chiave
     * @return List<AdempimentoConfigDTO> list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByIdAdempimentoChiave(Long idAdempimento,  String chiave) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        map.put("chiave", chiave);
        return findListByQuery(className, QUERY_SELECT_ADEMPIMENTO_CONFIG_BY_ID_ADEMPIMENTO_KEY, map);
    }


    /**
     * Load adempimento config by id istanza informazione chiave list.
     *
     * @param idIstanza  the id istanza
     * @param infoScriva the info scriva
     * @param chiave     the chiave
     * @return the list
     */
    @Override
    public List<AdempimentoConfigDTO> loadAdempimentoConfigByIdIstanzaInformazioneChiave(Long idIstanza, String infoScriva, String chiave) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = SELECT_ADEMPIMENTO_CONFIG_WITH_CONDICTION;
        StringBuilder dynamicInnerJoinConditions = new StringBuilder();
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            dynamicInnerJoinConditions.append(INNER_JOIN_ISTANZA);
        }
        if (StringUtils.isNotBlank(infoScriva)) {
            map.put("info", infoScriva);
            query += WHERE_INFO;
        }
        if (StringUtils.isNotBlank(chiave)) {
            map.put("chiave", chiave);
            query += WHERE_CHIAVE;
        }
        query = query.replace("__dynamic_inner_join_conditions__", "\n" + dynamicInnerJoinConditions.toString());
        return findListByQuery(className, query, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RowMapper<AdempimentoConfigDTO> getRowMapper() throws SQLException {
        return new AdempimentoConfigRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<AdempimentoConfigDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoConfigRowMapper();
    }

    /**
     * The type Adempimento config row mapper.
     */
    public static class AdempimentoConfigRowMapper implements RowMapper<AdempimentoConfigDTO> {

        /**
         * Instantiates a new Adempimento config row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoConfigRowMapper() throws SQLException {
            // Instantiate class
        }

        /**
         * Implementations must implement this method to map each row of data in the
         * ResultSet. This method should not call {@code next()} on the ResultSet; it is
         * only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if a SQLException is encountered getting column values
         *                      (that is, there's no need to catch SQLException)
         */
        @Override
        public AdempimentoConfigDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoConfigDTO bean = new AdempimentoConfigDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoConfigDTO bean) throws SQLException {
            bean.setIdAdempimentoConfig(rs.getLong("id_adempi_config"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setIdInformazioneScriva(rs.getLong("id_informazione_scriva"));
            bean.setDesInformazioneScriva(rs.getString("cod_informazione_scriva"));
            bean.setChiave(rs.getString("chiave"));
            bean.setValore(rs.getString("valore"));
            bean.setOrdinamento(rs.getInt("ordinamento"));

            
            try {
                bean.setFlgAttivo(1 == rs.getInt("flg_attivo") ? Boolean.TRUE : Boolean.FALSE);
            } catch (Exception e) {
                LOGGER.info("[AdempimentoConfigRowMapper::populateBean]\n" + e);
            }

            bean.setNote(rs.getString("note"));
        }
    }

}