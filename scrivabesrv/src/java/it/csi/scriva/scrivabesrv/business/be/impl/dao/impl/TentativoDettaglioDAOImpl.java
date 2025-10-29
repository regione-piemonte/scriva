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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TentativoDettaglioDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoTipoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AllegatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.EnteCreditoreDTO;
import it.csi.scriva.scrivabesrv.dto.GruppoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.PagamentoIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RiscossioneEnteExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.StatoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoTentativoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoDettaglioExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoPagamentoExtendedDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * The type Tentativo dettaglio dao.
 */
public class TentativoDettaglioDAOImpl extends ScrivaBeSrvGenericDAO<TentativoDettaglioDTO> implements TentativoDettaglioDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_TENTATIVO_DETTAGLIO_PK = "SELECT * FROM _replaceTableName_ WHERE id_pagamento_istanza=:idPagamentoIstanza";

    private static final String QUERY_TENTATIVO_DETTAGLIO_OLD = "SELECT srtd.*, srtd.gest_uid AS gest_uid_td, srtd.gest_attore_ins AS gest_attore_ins_td, srtd.id_pagamento_istanza AS pagamento_istanza_id " +
            ", sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id " +
            ", sdstp.*, sdstp.id_stato_tentativo_pag AS stato_tentativo_pag_id " +
            //", stai.*, stai.id_allegato_istanza AS allegato_istanza_id " +
            "FROM _replaceTableName_ srtd " +
            "INNER JOIN scriva_t_tentativo_pagamento sttp ON sttp.id_tentativo_pagamento = srtd.id_tentativo_pagamento " +
            "INNER JOIN scriva_d_stato_tentativo_pag sdstp ON sdstp.id_stato_tentativo_pag = sttp.id_stato_tentativo_pag " +
            //"LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = sttp.id_allegato_istanza " +
            "WHERE 1 = 1 ";

    private static final String QUERY_TENTATIVO_DETTAGLIO_OLD2 = "SELECT srtd.*, srtd.gest_uid AS gest_uid_td, srtd.gest_attore_ins AS gest_attore_ins_td, srtd.id_pagamento_istanza AS pagamento_istanza_id " +
            ", sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id " +
            ", sdstp.*, sdstp.id_stato_tentativo_pag AS stato_tentativo_pag_id " +
            // ", stai.*, stai.id_allegato_istanza AS allegato_istanza_id " +
            ", srpi.*, srpi.id_istanza AS istanza_id " +
            ", srre.*,  srre.id_riscossione_ente AS riscossione_ente_id " +
            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id " +
            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id " +
            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id " +
            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id " +
            ", sdad.*, sdad.id_adempimento AS adempimento_id " +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id " +
            ", sda.*, sda.id_ambito AS ambito_id " +
            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id " +
            ", sdsp.*, sdsp.id_stato_pagamento AS stato_pagamento_id " +
            "FROM scriva_r_tentativo_dettaglio srtd  " +
            "INNER JOIN scriva_r_pagamento_istanza srpi ON srpi.id_pagamento_istanza = srtd.id_pagamento_istanza " +
            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento " +
            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_riscossione_ente = srpi.id_riscossione_ente " +
            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco " +
            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_adempi_tipo_pagamento = srre.id_adempi_tipo_pagamento " +
            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento " +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento " +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = sdatp.id_ente_creditore " +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sdatp.id_adempimento " +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento " +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito " +
            "INNER JOIN scriva_t_tentativo_pagamento sttp ON sttp.id_tentativo_pagamento = srtd.id_tentativo_pagamento " +
            "INNER JOIN scriva_d_stato_tentativo_pag sdstp ON sdstp.id_stato_tentativo_pag = sttp.id_stato_tentativo_pag " +
            //"LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = sttp.id_allegato_istanza " +
            "WHERE 1 = 1 ";

    private static final String QUERY_TENTATIVO_DETTAGLIO = "SELECT srtd.*, srtd.gest_uid AS gest_uid_td, srtd.gest_attore_ins AS gest_attore_ins_td, srtd.id_pagamento_istanza AS pagamento_istanza_id \n" +
            ", sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id \n" +
            ", sdstp.*, sdstp.id_stato_tentativo_pag AS stato_tentativo_pag_id \n" +
            ", srpi.*, srpi.id_istanza AS istanza_id \n" +
            ", srre.*,  srre.id_riscossione_ente AS riscossione_ente_id \n" +
            ", sdatp.*, sdatp.id_adempi_tipo_pagamento AS adempi_tipo_pagamento_id \n" +
            ", sdtp.*, sdtp.id_tipo_pagamento AS tipo_pagamento_id \n" +
            ", sdgp.*, sdgp.id_gruppo_pagamento AS gruppo_pagamento_id \n" +
            ", sdec.*, sdec.id_ente_creditore AS ente_creditore_id \n" +
            ", sdad.*, sdad.id_adempimento AS adempimento_id \n" +
            ", sdta.*, sdta.id_tipo_adempimento AS tipo_adempimento_id \n" +
            ", sda.*, sda.id_ambito AS ambito_id \n" +
            ", sdsi_blocco.*, sdsi_blocco.id_stato_istanza AS stato_istanza_id \n" +
            ", sdsp.*, sdsp.id_stato_pagamento AS stato_pagamento_id \n" +
            "FROM scriva_r_tentativo_dettaglio srtd \n" +
            "INNER JOIN scriva_r_pagamento_istanza srpi ON srpi.id_pagamento_istanza = srtd.id_pagamento_istanza \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = srpi.id_istanza \n"+
            "INNER JOIN scriva_d_stato_pagamento sdsp ON sdsp.id_stato_pagamento = srpi.id_stato_pagamento \n" +
            "INNER JOIN scriva_r_riscossione_ente srre ON srre.id_riscossione_ente = srpi.id_riscossione_ente \n" +
            "LEFT JOIN scriva_d_stato_istanza sdsi_blocco ON sdsi_blocco.id_stato_istanza = srre.id_stato_istanza_blocco \n" +
            "INNER JOIN scriva_d_adempi_tipo_pagamento sdatp ON sdatp.id_adempi_tipo_pagamento = srre.id_adempi_tipo_pagamento \n" +
            "INNER JOIN scriva_d_tipo_pagamento sdtp ON sdtp.id_tipo_pagamento = sdatp.id_tipo_pagamento \n" +
            "INNER JOIN scriva_d_gruppo_pagamento sdgp ON sdgp.id_gruppo_pagamento = sdtp.id_gruppo_pagamento \n" +
            "INNER JOIN scriva_r_istanza_competenza sric ON sric.id_istanza = srpi.id_istanza AND sric.flg_autorita_principale = 1 AND sric.data_fine_validita IS NULL \n" +
            "INNER JOIN scriva_r_adempi_competenza srac ON srac.id_competenza_territorio = sric.id_competenza_territorio AND srac.id_adempimento = sti.id_adempimento AND srac.ind_adesione_adempimento != 0 \n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = srac.id_competenza_territorio AND (stct.data_fine_validita IS NULL OR stct.data_fine_validita > now()) \n" + // "AND stct.ind_visibile LIKE '%FO%' \n" +
            "INNER JOIN scriva_d_ente_creditore sdec ON sdec.id_ente_creditore = stct.id_ente_creditore AND sdec.flg_attivo = 1 \n" +
            "INNER JOIN scriva_d_adempimento sdad ON sdad.id_adempimento = sdatp.id_adempimento \n" +
            "INNER JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sdad.id_tipo_adempimento \n" +
            "INNER JOIN scriva_d_ambito sda ON sda.id_ambito = sdta.id_ambito \n" +
            "INNER JOIN scriva_t_tentativo_pagamento sttp ON sttp.id_tentativo_pagamento = srtd.id_tentativo_pagamento \n" +
            "INNER JOIN scriva_d_stato_tentativo_pag sdstp ON sdstp.id_stato_tentativo_pag = sttp.id_stato_tentativo_pag \n" +
            "WHERE 1 = 1 ";

    private static final String QUERY_TENTATIVO_DETTAGLIO_BY_ID_PAG_ISTANZA = QUERY_TENTATIVO_DETTAGLIO +
            "AND srtd.id_pagamento_istanza = :idPagamentoIstanza";

    private static final String QUERY_TENTATIVO_DETTAGLIO_BY_ID_TENTATIVO_PAG = QUERY_TENTATIVO_DETTAGLIO +
            "AND srtd.id_tentativo_pagamento = :idTentativoPagamento";

    private static final String QUERY_INSERT_TENTATIVO_DETTAGLIO = "INSERT INTO _replaceTableName_ " +
            "(id_pagamento_istanza, id_tentativo_pagamento, iuv_tentativo_pagamento, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(:idPagamentoIstanza, :idTentativoPagamento, :iuvTentativoPagamento, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_TENTATIVO_DETTAGLIO = "UPDATE _replaceTableName_ " +
            "SET id_tentativo_pagamento=:idTentativoPagamento, iuv_tentativo_pagamento=:iuvTentativoPagamento, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd " +
            "WHERE id_pagamento_istanza=:idPagamentoIstanza ";

    private static final String WHERE_DATA_INIZIO_FINE  =  " AND (DATE(srac.data_inizio_validita) <= DATE(NOW()) AND (srac.data_fine_validita IS NULL OR DATE(srac.data_fine_validita)>= DATE(NOW())))\n ";
    
    /**
     * Load tentativi dettaglio list.
     *
     * @return the list
     */
    @Override
    public List<TentativoDettaglioExtendedDTO> loadTentativiDettaglio() {
        return null;
    }

    /**
     * Load tentativo dettaglio by id pagamento istanza list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    @Override
    public List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdPagamentoIstanza(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        return findListByQuery(className, QUERY_TENTATIVO_DETTAGLIO_BY_ID_PAG_ISTANZA, map);
    }

    /**
     * Load tentativo dettaglio by id pagamento istanza for pag ist list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    @Override
    public List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdPagamentoIstanzaForPagIst(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        return findListByQuery(className, QUERY_TENTATIVO_DETTAGLIO_BY_ID_PAG_ISTANZA, map);
    }

    /**
     * Load tentativo dettaglio by id tentativo pagamento list.
     *
     * @param idTentativoPagamento the id tentativo pagamento
     * @return the list
     */
    @Override
    public List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioByIdTentativoPagamento(Long idTentativoPagamento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTentativoPagamento", idTentativoPagamento);
        return findListByQuery(className, QUERY_TENTATIVO_DETTAGLIO_BY_ID_TENTATIVO_PAG, map);
    }

    /**
     * Load tentativo dettaglio by id tentativo pagamento list.
     *
     * @param idTentativoPagamento the id tentativo pagamento
     * @return the list
     */
    @Override
    public List<TentativoDettaglioExtendedDTO> loadTentativoDettaglioExtendedByIdTentativoPagamento(Long idTentativoPagamento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTentativoPagamento", idTentativoPagamento);
        return findListByQuery(className, QUERY_TENTATIVO_DETTAGLIO_BY_ID_TENTATIVO_PAG, map);
    }

    /**
     * Save tentativo dettaglio long.
     *
     * @param dto the tentativo dettaglio
     * @return the long
     */
    @Override
    public Long saveTentativoDettaglio(TentativoDettaglioDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idPagamentoIstanza", dto.getIdPagamentoIstanza());
            map.put("idTentativoPagamento", dto.getIdTentativoPagamento());
            map.put("iuvTentativoPagamento", dto.getIuvTentativoPagamento());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdPagamentoIstanza().toString() + dto.getIdTentativoPagamento() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_TENTATIVO_DETTAGLIO, null, null), params, keyHolder, new String[]{"id_tentativo_pagamento"});
            Number key = keyHolder.getKey();
            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update tentativo dettaglio integer.
     *
     * @param dto the tentativo dettaglio
     * @return the integer
     */
    @Override
    public Integer updateTentativoDettaglio(TentativoDettaglioDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", dto.getIdPagamentoIstanza());
        try {
            TentativoDettaglioDTO tentativoDettaglioOld = this.findByPK(map);
            if (null == tentativoDettaglioOld) {
                logError(className, "Record non trovato con idPagamentoIStanza [" + dto.getIdPagamentoIstanza() + "]");
                return -1;
            }

            Date now = Calendar.getInstance().getTime();
            map.put("idTentativoPagamento", dto.getIdTentativoPagamento());
            map.put("iuvTentativoPagamento", dto.getIuvTentativoPagamento());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_TENTATIVO_DETTAGLIO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete tentativo dettaglio integer.
     *
     * @param uid the uid
     * @return the integer
     */
    @Override
    public Integer deleteTentativoDettaglio(String uid) {
        return null;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_TENTATIVO_DETTAGLIO_PK, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TentativoDettaglioDTO> getRowMapper() throws SQLException {
        return new TentativoDettaglioRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TentativoDettaglioExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TentativoDettaglioExtendedRowMapper(Boolean.TRUE);
    }

    /**
     * Gets extended row mapper.
     *
     * @param withPagamentoIstanza the with pagamento istanza
     * @return the extended row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<TentativoDettaglioExtendedDTO> getExtendedRowMapper(Boolean withPagamentoIstanza) throws SQLException {
        return new TentativoDettaglioExtendedRowMapper(withPagamentoIstanza);
    }


    /**
     * The type Tentativo dettaglio row mapper.
     */
    public static class TentativoDettaglioRowMapper implements RowMapper<TentativoDettaglioDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TentativoDettaglioRowMapper() throws SQLException {
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
        public TentativoDettaglioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TentativoDettaglioDTO bean = new TentativoDettaglioDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TentativoDettaglioDTO bean) throws SQLException {
            bean.setIdPagamentoIstanza(rs.getLong("id_pagamento_istanza"));
            bean.setIdTentativoPagamento(rs.getLong("id_tentativo_pagamento"));
            bean.setIuvTentativoPagamento(rs.getString("iuv_tentativo_pagamento"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

    }

    /**
     * The type Tentativo dettaglio extended row mapper.
     */
    public static class TentativoDettaglioExtendedRowMapper implements RowMapper<TentativoDettaglioExtendedDTO> {

        private Boolean withPagamentoIstanza = Boolean.TRUE;

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @param withPagamentoIstanza the with pagamento istanza
         * @throws SQLException the sql exception
         */
        public TentativoDettaglioExtendedRowMapper(Boolean withPagamentoIstanza) throws SQLException {
            this.withPagamentoIstanza = withPagamentoIstanza;
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
        public TentativoDettaglioExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TentativoDettaglioExtendedDTO bean = new TentativoDettaglioExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TentativoDettaglioExtendedDTO bean) throws SQLException {
            if (withPagamentoIstanza) {
                PagamentoIstanzaExtendedDTO pagamentoIstanza = new PagamentoIstanzaExtendedDTO();
                populateBeanPagamentoIstanza(rs, pagamentoIstanza);
                bean.setPagamentoIstanza(pagamentoIstanza);
            } else {
                bean.setIdPagamentoIstanza(rs.getLong("pagamento_istanza_id"));
            }

            TentativoPagamentoExtendedDTO tentativoPagamento = new TentativoPagamentoExtendedDTO();
            populateBeanTentativoPagamento(rs, tentativoPagamento);
            bean.setTentativoPagamento(tentativoPagamento);

            bean.setIuvTentativoPagamento(rs.getString("iuv_tentativo_pagamento"));
            bean.setGestUID(rs.getString("gest_uid_td"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_td"));
        }

        private void populateBeanTentativoPagamento(ResultSet rs, TentativoPagamentoExtendedDTO bean) throws SQLException {
            bean.setIdTentativoPagamento(rs.getLong("tentativo_pagamento_id"));

            StatoTentativoPagamentoDTO statoTentativoPagamento = new StatoTentativoPagamentoDTO();
            populateBeanStatoTentativoPagamento(rs, statoTentativoPagamento);
            bean.setStatoTentativoPagamento(statoTentativoPagamento);

            bean.setIdentificativoPagamentoPpay(rs.getString("identificativo_pagamento_ppay"));
            bean.setHashPagamento(rs.getString("hash_pagamento"));
            bean.setTipoBollo(rs.getString("tipo_bollo"));
            bean.setFlgSoloMarca(rs.getInt("flg_solo_marca") == 1 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateBeanStatoTentativoPagamento(ResultSet rs, StatoTentativoPagamentoDTO bean) throws SQLException {
            bean.setIdStatoTentativoPagamento(rs.getLong("stato_tentativo_pag_id"));
            bean.setCodiceStatoTentativoPagamento(rs.getString("cod_stato_tentativo_pag"));
            bean.setDescrizioneStatoTentativoPagamento(rs.getString("des_stato_tentativo_pag"));
        }

        private void populateBeanPagamentoIstanza(ResultSet rs, PagamentoIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdPagamentoIstanza(rs.getLong("pagamento_istanza_id"));
            bean.setIdIstanza(rs.getLong("istanza_id"));

            RiscossioneEnteExtendedDTO riscossioneEnte = new RiscossioneEnteExtendedDTO();
            populateBeanRiscossioneEnte(rs, riscossioneEnte);
            bean.setRiscossioneEnte(riscossioneEnte);

            StatoPagamentoDTO statoPagamento = new StatoPagamentoDTO();
            populateBeanStatoPagamento(rs, statoPagamento);
            bean.setStatoPagamento(statoPagamento);

            bean.setIdOnerePadre(rs.getLong("id_onere_padre") == 0L ? null : rs.getLong("id_onere_padre"));
            bean.setDataInserimentoPagamento(rs.getTimestamp("data_inserimento_pagamento"));
            bean.setImportoDovuto(rs.getBigDecimal("importo_dovuto"));
            bean.setIuv(rs.getString("iuv"));
            bean.setDataEffettivoPagamento(rs.getTimestamp("data_effettivo_pagamento"));
            bean.setImportoPagato(rs.getBigDecimal("importo_pagato"));
            bean.setIubd(rs.getString("iubd"));
            bean.setFlgNonDovuto(rs.getInt("flg_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanRiscossioneEnte(ResultSet rs, RiscossioneEnteExtendedDTO bean) throws SQLException {
            bean.setIdRiscossioneEnte(rs.getLong("riscossione_ente_id"));

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
            //bean.setFlgAttivaImportoNonDovuto(rs.getInt("flg_attiva_importo_non_dovuto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            //bean.setFlgAttivaPagamento(rs.getInt("flg_attiva_pagamento") == 1 ? Boolean.TRUE : Boolean.FALSE);
            //bean.setFlgAttivo(rs.getInt("flg_attivo") == 1 ? Boolean.TRUE : Boolean.FALSE);
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

        private void populateBeanStatoPagamento(ResultSet rs, StatoPagamentoDTO bean) throws SQLException {
            bean.setIdStatoPagamento(rs.getLong("stato_pagamento_id"));
            bean.setCodiceStatoPagamento(rs.getString("cod_stato_pagamento"));
            bean.setDescrizioneStatoPagamento(rs.getString("des_stato_pagamento"));
        }

        private void populateBeanAllegatoIstanza(ResultSet rs, AllegatoIstanzaDTO bean) throws SQLException {
            Calendar timezone = Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"));
            bean.setIdAllegatoIstanza(rs.getLong("allegato_istanza_id"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipologiaAllegato(rs.getLong("id_tipologia_allegato"));
            bean.setIdTipoIntegraAllegato(rs.getLong("id_tipo_integrazione"));
            bean.setUuidIndex(rs.getString("uuid_index"));
            bean.setFlgRiservato(1 == rs.getInt("flg_riservato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setCodAllegato(rs.getString("cod_allegato"));
            bean.setNomeAllegato(rs.getString("nome_allegato"));
            bean.setDimensioneUpload(rs.getLong("dimensione_upload"));
            bean.setDataUpload(rs.getTimestamp("data_upload", timezone));
            bean.setDataIntegrazione(rs.getTimestamp("data_integrazione", timezone));
            bean.setDataCancellazione(rs.getTimestamp("data_cancellazione", timezone));
            bean.setFlgCancellato(1 == rs.getInt("flg_cancellato") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndFirma(rs.getInt("ind_firma"));
            bean.setNote(rs.getString("note"));
        }

    }

}