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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoTipoPagamentoDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.GruppoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoPagamentoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento tipo pagamento dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoTipoPagamentoDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoTipoPagamentoDTO> implements AdempimentoTipoPagamentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_COMPONENTE_APP = "AND sdco.ind_visibile LIKE '%'||:componenteApp||'%' ";

    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza ";

    private static final String WHERE_ID = "AND sdatp.id_adempi_tipo_pagamento = :idAdempiTipoPagamento ";

    private static final String QUERY_ADEMPIMENTO_TIPO_PAGAMENTO = "SELECT sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id " +
            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id " +
            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id " +
            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id " +
            ", sdsi.*, sdsi.id_stato_istanza AS stato_istanza_id " +
            ", sdad.*, sdad.id_adempimento AS adempimento_id " +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id " +
            ", sda.*, sda.id_ambito AS ambito_id " +
            "FROM scriva_t_istanza sti " +
            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza " +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio " +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio " +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore " +
            "INNER JOIN _replaceTableName_ sdatp ON sdatp.id_ente_creditore = sdec.id_ente_creditore " +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza " +
            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento " +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento " +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento  " +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito  " +
            "WHERE 1 = 1 " +
            "AND sdec.flg_attivo = 1 " + // ente creditore attivo
            "AND sdatp.flg_attivo = 1 " + // tipo pagamento attivo per adempimento
            "AND srac.ind_adesione_adempimento != 0 " + // la competenza territoriale ha scelto di aderire a scriva
            "AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita < now()) " +
            "AND sdatp.id_adempimento = sti.id_adempimento " +
            "AND srac.id_adempimento = sti.id_adempimento ";

    private static final String QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_BY_ID_ISTANZA = QUERY_ADEMPIMENTO_TIPO_PAGAMENTO + WHERE_ID_ISTANZA;

    private static final String QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_BY_ID = QUERY_ADEMPIMENTO_TIPO_PAGAMENTO + WHERE_ID;
    
    private static final String QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_FILTERED_BY_DATA_FINE = QUERY_ADEMPIMENTO_TIPO_PAGAMENTO + " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine IS NULL OR DATE(srac.data_fine)>= DATE(NOW())))\n " +
    		" AND (DATE(sdatp.data_inizio_validita) <= DATE(NOW()) AND (sdatp.data_fine_validita IS NULL OR DATE(sdatp.data_fine_validita)>= DATE(NOW())))\\n ";

    /**
     * Load tipo pagamento per adempimento attivi list.
     *
     * @return the list
     */
    @Override
    public List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipiPagamento() {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        return findListByQuery(className, QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_FILTERED_BY_DATA_FINE, map);
    }

    /**
     * Load tipo pagamento per adempimento by id list.
     *
     * @param idAdempiTipoPagamento the id adempi tipo pagamento
     * @return the list
     */
    @Override
    public List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipoPagamentoById(Long idAdempiTipoPagamento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempiTipoPagamento", idAdempiTipoPagamento);
        return findListByQuery(className, QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_BY_ID, map);
    }

    /**
     * Load tipo pagamento per adempimento by id istanza list.
     *
     * @param idIstanza     the id istanza
     * @param componenteApp the componente app
     * @return the list
     */
    @Override
    public List<AdempimentoTipoPagamentoExtendedDTO> loadAdempimentoTipoPagamentoIdIstanza(Long idIstanza, String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("componenteApp", componenteApp);
        return findListByQuery(className, QUERY_ADEMPIMENTO_TIPO_PAGAMENTO_BY_ID_ISTANZA, map);
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
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoTipoPagamentoDTO> getRowMapper() throws SQLException {
        return new AdempimentoTipoPagamentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoTipoPagamentoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoTipoPagamentoExtendedRowMapper();
    }

    /**
     * The type Adempimento tipo pagamento row mapper.
     */
    public static class AdempimentoTipoPagamentoRowMapper implements RowMapper<AdempimentoTipoPagamentoDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoTipoPagamentoRowMapper() throws SQLException {
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
        public AdempimentoTipoPagamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoPagamentoDTO bean = new AdempimentoTipoPagamentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoTipoPagamentoDTO bean) throws SQLException {
            bean.setIdAdempiTipoPagamento(rs.getLong("id_adempi_tipo_pagamento"));
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            bean.setIdTipoPagamento(rs.getLong("id_tipo_pagamento"));
            bean.setIdEnteCreditore(rs.getLong("id_ente_creditore"));
            bean.setCodiceVersamento(rs.getString("codice_versamento"));
            bean.setImportoPrevisto(rs.getBigDecimal("importo_previsto"));
            bean.setGiorniMaxAttesaRt(rs.getInt("giorni_max_attesa_rt"));
            bean.setIndImportoPagamento(rs.getString("ind_importo_pagamento"));
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

    }

    /**
     * The type Adempimento tipo pagamento extended row mapper.
     */
    public static class AdempimentoTipoPagamentoExtendedRowMapper implements RowMapper<AdempimentoTipoPagamentoExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoTipoPagamentoExtendedRowMapper() throws SQLException {
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
        public AdempimentoTipoPagamentoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoTipoPagamentoExtendedDTO bean = new AdempimentoTipoPagamentoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoTipoPagamentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempiTipoPagamento(rs.getLong("adempi_tipo_pagamento_id"));

            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateBeanAdempimento(rs, adempimento);
            bean.setAdempimento(adempimento);

            TipoPagamentoExtendedDTO tipoPagamento = new TipoPagamentoExtendedDTO();
            populateBeanTipoPagamento(rs, tipoPagamento);
            bean.setTipoPagamento(tipoPagamento);

            EnteCreditoreDTO enteCreditore = new EnteCreditoreDTO();
            populateBeanEnteCreditore(rs, enteCreditore);
            bean.setEnteCreditore(enteCreditore);

            bean.setCodiceVersamento(rs.getString("codice_versamento"));
            bean.setImportoPrevisto(rs.getBigDecimal("importo_previsto"));
            bean.setGiorniMaxAttesaRt(rs.getInt("giorni_max_attesa_rt"));
            bean.setIndImportoPagamento(rs.getString("ind_importo_pagamento"));
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanAdempimento(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("adempimento_id"));

            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateBeanTipoAdempimento(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento);

            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
        }

        private void pupulateBeanTipoAdempimento(ResultSet rs, TipoAdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoAdempimento(rs.getLong("tipo_adempimento_id"));

            AmbitoDTO ambito = new AmbitoDTO();
            populateBeanAmbito(rs, ambito);
            bean.setAmbito(ambito);

            bean.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            bean.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            bean.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));
        }

        private void populateBeanAmbito(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("ambito_id"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }

        private void populateBeanTipoPagamento(ResultSet rs, TipoPagamentoExtendedDTO bean) throws SQLException {
            bean.setIdTipoPagamento(rs.getLong("tipo_pagamento_id"));

            GruppoPagamentoDTO gruppoPagamento = new GruppoPagamentoDTO();
            populateBeanGruppoPagamento(rs, gruppoPagamento);
            bean.setGruppoPagamento(gruppoPagamento);

            bean.setCodiceTipoPagamento(rs.getString("cod_tipo_pagamento"));
            bean.setDescrizioneTipoPagamento(rs.getString("des_tipo_pagamento"));
        }

        private void populateBeanGruppoPagamento(ResultSet rs, GruppoPagamentoDTO bean) throws SQLException {
            bean.setIdGruppoPagamento(rs.getLong("gruppo_pagamento_id"));
            bean.setCodiceGruppoPagamento(rs.getString("cod_gruppo_pagamento"));
            bean.setDescrizioneGruppoPagamento(rs.getString("des_gruppo_pagamento"));
        }

        private void populateBeanStatoIstanzaBlocco(ResultSet rs, StatoIstanzaDTO bean) throws SQLException {
            bean.setIdStatoIstanza(rs.getLong("stato_istanza_id"));
            bean.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            bean.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            bean.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            bean.setLabelStato(rs.getString("label_stato"));
        }

        private void populateBeanEnteCreditore(ResultSet rs, EnteCreditoreDTO bean) throws SQLException {
            bean.setIdEnteCreditore(rs.getLong("ente_creditore_id"));
            bean.setCfEnteCreditore(rs.getString("cf_ente_creditore"));
            bean.setDenominazioneEnteCreditore(rs.getString("denominazione_ente_creditore"));
            bean.setIndirizzoTesoreria(rs.getString("indirizzo_tesoreria"));
            bean.setIbanAccredito(rs.getString("iban_accredito"));
            bean.setBicAccredito(rs.getString("bic_accredito"));
            bean.setFlgAderiscePiemontepay(rs.getInt("flg_aderisce_piemontepay") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

    }

}