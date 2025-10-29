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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.RiscossioneEnteDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.GruppoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteDTO;
import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteExtendedDTO;
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
 * The type Riscossione ente dao.
 */
public class RiscossioneEnteDAOImpl extends ScrivaBeSrvGenericDAO<RiscossioneEnteDTO> implements RiscossioneEnteDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_COMPONENTE_APP = "AND stct.ind_visibile LIKE '%'||:componenteApp||'%' ";

    private static final String WHERE_ID_ISTANZA = "AND sti.id_istanza = :idIstanza ";

//    private static final String QUERY_RISCOSSIONE_ENTE_ISTANZA = "SELECT srre.* \n" +
//            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id \n" +
//            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id \n" +
//            ", srrsi.flg_attiva_pagamento \n" +
//            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id \n" +
//            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id \n" +
//            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id \n" +
//            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
//            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
//            ", sda.*, sda.id_ambito AS ambito_id \n" +
//            ", srac.flg_principale AS flg_competenza_principale \n" +
//            "FROM scriva_t_istanza sti \n" +
//            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza AND sric.flg_autorita_principale = 1\n" +
//            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita < now()) AND srac.id_adempimento = sti.id_adempimento AND srac.flg_adesione_adempimento = 1\n" +
//            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sric.id_competenza_territorio \n" +
//            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore AND sdec.flg_attivo = 1 \n" +
//            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_ente_creditore = sdec.id_ente_creditore AND sdatp.id_adempimento = sti.id_adempimento AND sdatp.flg_attivo = 1\n" +
//            "INNER JOIN _replaceTableName_ srre ON srre.id_adempi_tipo_pagamento = sdatp.id_adempi_tipo_pagamento AND srre.flg_attivo = 1\n" +
//            "INNER JOIN scriva_r_risco_stato_istanza srrsi ON srrsi.id_riscossione_ente = srre.id_riscossione_ente AND srrsi.id_stato_istanza = sti.id_stato_istanza\n" +
//            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = sti.id_stato_istanza \n" +
//            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco \n" +
//            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento \n" +
//            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento \n" +
//            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento \n" +
//            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento  \n" +
//            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
//            "WHERE sti.id_istanza = :idIstanza \n";

    private static final String QUERY_RISCOSSIONE_ENTE_ISTANZA = "SELECT DISTINCT srre.* \n" +
            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id \n" +
            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id \n" +
            ", srrsi.flg_attiva_pagamento \n" +
            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id \n" +
            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id \n" +
            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id \n" +
            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
            ", sda.*, sda.id_ambito AS ambito_id \n" +
            ", srac.flg_principale AS flg_competenza_principale\n" +
            "FROM scriva_t_istanza sti \n" +
            //"INNER JOIN scriva_r_stato_istanza_adempi srsia ON srsia.id_adempimento = sti.id_adempimento AND srsia.id_stato_istanza = sti.id_stato_istanza\n" +
            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = sti.id_istanza AND sric.flg_autorita_principale =1 AND sric.data_fine_validita IS NULL\n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio AND srac.id_adempimento = sti.id_adempimento AND srac.ind_adesione_adempimento != 0\n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita > now())\n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore AND sdec.flg_attivo = 1\n" +
            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_competenza_territorio = stct.id_competenza_territorio AND sdatp.id_adempimento = sti.id_adempimento\n" +
            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_adempi_tipo_pagamento = sdatp.id_adempi_tipo_pagamento\n" +
            "INNER JOIN scriva_r_risco_stato_istanza srrsi ON srrsi.id_riscossione_ente = srre.id_riscossione_ente AND srrsi.id_stato_istanza = sti.id_stato_istanza \n" +
            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento\n" +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento\n" +
            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco\n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sti.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito\n" +
            "WHERE sti.id_istanza = :idIstanza \n";

    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n ";
    
    /**
     * Load riscossioni ente attivi list.
     *
     * @return the list
     */
    @Override
    public List<RiscossioneEnteExtendedDTO> loadRiscossioniEnteAttivi() {
        return null;
    }

    /**
     * Load riscossione ente by id list.
     *
     * @param idRiscossioneEnte the id riscossione ente
     * @return the list
     */
    @Override
    public List<RiscossioneEnteExtendedDTO> loadRiscossioneEnteById(Long idRiscossioneEnte) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRiscossioneEnte", idRiscossioneEnte);
        return null; // findListByQuery(className, methodName, QUERY_RISCOSSIONE_ENTE_ISTANZA, map);
    }

    /**
     * Load riscossione ente by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<RiscossioneEnteExtendedDTO> loadRiscossioneEnteByIdIstanza(Long idIstanza, String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("componenteApp", componenteApp);
        return findListByQuery(className, QUERY_RISCOSSIONE_ENTE_ISTANZA + WHERE_COMPONENTE_APP, map);
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
    public RowMapper<RiscossioneEnteDTO> getRowMapper() throws SQLException {
        return new RiscossioneEnteRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<RiscossioneEnteExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new RiscossioneEnteExtendedRowMapper();
    }

    /**
     * The type Competenza territorio row mapper.
     */
    public static class RiscossioneEnteRowMapper implements RowMapper<RiscossioneEnteDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RiscossioneEnteRowMapper() throws SQLException {
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
        public RiscossioneEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RiscossioneEnteDTO bean = new RiscossioneEnteDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RiscossioneEnteDTO bean) throws SQLException {
            bean.setIdRiscossioneEnte(rs.getLong("id_riscossione_ente"));
            bean.setIdAdempimentoTipoPagamento(rs.getLong("id_adempi_tipo_pagamento"));
            bean.setIdGruppoPagamento(rs.getLong("id_gruppo_pagamento"));
            bean.setIdStatoIstanzaBlocco(rs.getLong("id_stato_istanza_blocco"));
            bean.setDatiSpecificiRiscossione(rs.getString("dati_specifici_riscossione"));
            bean.setAccertamentoAnno(rs.getInt("accertamento_anno"));
            bean.setNumeroAccertamento(rs.getLong("numero_accertamento"));
            bean.setDesPagamentoVersoCittadino(rs.getString("des_pagamento_verso_cittadino"));
            bean.setOrdinamentoRiscossioneEnte(rs.getInt("ordinamento_riscossione_ente"));
            bean.setFlgAttivaPagamento(rs.getInt("flg_attiva_pagamento") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivaImportoNonDovuto(rs.getInt("flg_attiva_importo_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

    }

    /**
     * The type Riscossione ente extended row mapper.
     */
    public static class RiscossioneEnteExtendedRowMapper implements RowMapper<RiscossioneEnteExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public RiscossioneEnteExtendedRowMapper() throws SQLException {
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
        public RiscossioneEnteExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            RiscossioneEnteExtendedDTO bean = new RiscossioneEnteExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, RiscossioneEnteExtendedDTO bean) throws SQLException {
            bean.setIdRiscossioneEnte(rs.getLong("id_riscossione_ente"));

            AdempimentoTipoPagamentoExtendedDTO adempimentoTipoPagamento = new AdempimentoTipoPagamentoExtendedDTO();
            populateBeanAdempimentoTipoPagamento(rs, adempimentoTipoPagamento);
            bean.setAdempimentoTipoPagamento(adempimentoTipoPagamento);

            GruppoPagamentoDTO gruppoPagamento = new GruppoPagamentoDTO();
            populateBeanGruppoPagamento(rs, gruppoPagamento);
            bean.setGruppoPagamento(gruppoPagamento);

            StatoIstanzaDTO statoIstanzaBlocco = new StatoIstanzaDTO();
            populateBeanStatoIstanzaBlocco(rs, statoIstanzaBlocco);

            bean.setStatoIstanzaBlocco(statoIstanzaBlocco.getCodiceStatoIstanza() != null ? statoIstanzaBlocco : null);

            bean.setDatiSpecificiRiscossione(rs.getString("dati_specifici_riscossione"));
            bean.setAccertamentoAnno(rs.getInt("accertamento_anno"));
            bean.setNumeroAccertamento(rs.getLong("numero_accertamento"));
            bean.setDesPagamentoVersoCittadino(rs.getString("des_pagamento_verso_cittadino"));
            bean.setOrdinamentoRiscossioneEnte(rs.getInt("ordinamento_riscossione_ente"));
            bean.setFlgAttivaPagamento(rs.getInt("flg_attiva_pagamento") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivaImportoNonDovuto(rs.getInt("flg_attiva_importo_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCompetenzaPrincipale(rs.getInt("flg_competenza_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanAdempimentoTipoPagamento(ResultSet rs, AdempimentoTipoPagamentoExtendedDTO bean) throws SQLException {
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
            bean.setIndRicercaOggetto(rs.getString("ind_ricerca_oggetto"));
            bean.setIndAggiornaOggetto(rs.getString("ind_aggiorna_oggetto"));
            bean.setFlgAggiornaOggetto(rs.getBoolean("flg_aggiorna_oggetto") ? Boolean.TRUE : Boolean.FALSE);
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