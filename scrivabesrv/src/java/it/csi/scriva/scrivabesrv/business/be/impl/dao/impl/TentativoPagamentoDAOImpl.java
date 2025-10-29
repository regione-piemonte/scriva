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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TentativoPagamentoDAO;
import it.csi.scriva.scrivabesrv.dto.StatoTentativoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoDTO;
import it.csi.scriva.scrivabesrv.dto.TentativoPagamentoExtendedDTO;
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

public class TentativoPagamentoDAOImpl extends ScrivaBeSrvGenericDAO<TentativoPagamentoDTO> implements TentativoPagamentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_TENTATIVO_PAGAMENTO_PK = "SELECT sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id FROM _replaceTableName_ sttp WHERE sttp.id_tentativo_pagamento = :idTentativoPagamento";

    private static final String QUERY_TENTATIVO_PAGAMENTO = "SELECT sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id " +
            ", sdstp.*, sdstp.id_stato_tentativo_pag AS stato_tentativo_pag_id " +
            //", stai.*, stai.id_allegato_istanza AS allegato_istanza_id " +
            "FROM _replaceTableName_ sttp " +
            "INNER JOIN scriva_d_stato_tentativo_pag sdstp ON sdstp.id_stato_tentativo_pag = sttp.id_stato_tentativo_pag " +
            //"LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = sttp.id_allegato_istanza " +
            "WHERE 1 = 1 ";

    private static final String QUERY_TENTATIVO_PAGAMENTO_BY_ID = QUERY_TENTATIVO_PAGAMENTO +
            "AND sttp.id_tentativo_pagamento = :idTentativoPagamento";

    private static final String QUERY_TENTATIVO_PAGAMENTO_BY_IDENTIFICATIVO_PAGAMENTO_PPAY = QUERY_TENTATIVO_PAGAMENTO +
            "AND sttp.identificativo_pagamento_ppay = :identificativoPagamentoPPay";

    private static final String QUERY_TENTATIVO_PAGAMENTO_BY_ID_PAGAMENTO_ISTANZA = "SELECT sttp.*, sttp.id_tentativo_pagamento AS tentativo_pagamento_id " +
            ", sdstp.*, sdstp.id_stato_tentativo_pag AS stato_tentativo_pag_id " +
            //", stai.*, stai.id_allegato_istanza AS allegato_istanza_id " +
            "FROM _replaceTableName_ sttp " +
            "INNER JOIN scriva_r_tentativo_dettaglio srtd ON srtd.id_tentativo_pagamento = sttp.id_tentativo_pagamento " +
            "INNER JOIN scriva_d_stato_tentativo_pag sdstp ON sdstp.id_stato_tentativo_pag = sttp.id_stato_tentativo_pag " +
            //"LEFT JOIN scriva_t_allegato_istanza stai ON stai.id_allegato_istanza = sttp.id_allegato_istanza " +
            "WHERE 1 = 1 " +
            "AND srtd.id_pagamento_istanza = :idPagamentoIstanza ";

    private static final String QUERY_INSERT_TENTATIVO_PAGAMENTO = "INSERT INTO _replaceTableName_ " +
            "(id_tentativo_pagamento, id_stato_tentativo_pag, identificativo_pagamento_ppay, hash_pagamento, tipo_bollo, flg_solo_marca, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(nextval('seq_scriva_t_tentativo_pagamento'), :idStatoTentativoPag, :identificativoPagamentoPpay, :hashPagamento, :tipoBollo, :flgSoloMarca, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_TENTATIVO_PAGAMENTO = "UPDATE _replaceTableName_ " +
            "SET id_stato_tentativo_pag=:idStatoTentativoPag, identificativo_pagamento_ppay=:identificativoPagamentoPpay, hash_pagamento=:hashPagamento, tipo_bollo=:tipoBollo, " +
            "flg_solo_marca=:flgSoloMarca, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd " +
            "WHERE id_tentativo_pagamento=:idTentativoPagamento";

    /**
     * Load tentativi pagamento list.
     *
     * @return the list
     */
    @Override
    public List<TentativoPagamentoExtendedDTO> loadTentativiPagamento() {
        return null;
    }

    /**
     * Load tentativo pagamento by id list.
     *
     * @param idTentativoPagamento the id pagamento istanza
     * @return the list
     */
    @Override
    public List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoById(Long idTentativoPagamento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTentativoPagamento", idTentativoPagamento);
        return findListByQuery(className, QUERY_TENTATIVO_PAGAMENTO_BY_ID, map);
    }

    /**
     * Load tentativo pagamento by identificativo pagamento p pay list.
     *
     * @param identificativoPagamentoPPay the identificativo pagamento p pay
     * @return the list
     */
    @Override
    public List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdentificativoPagamentoPPay(String identificativoPagamentoPPay) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("identificativoPagamentoPPay", identificativoPagamentoPPay);
        return findListByQuery(className, QUERY_TENTATIVO_PAGAMENTO_BY_IDENTIFICATIVO_PAGAMENTO_PPAY, map);
    }

    /**
     * Load tentativo pagamento by id pagamento istanza list.
     *
     * @param idPagamentoIstanza the id pagamento istanza
     * @return the list
     */
    @Override
    public List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdPagamentoIstanza(Long idPagamentoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idPagamentoIstanza", idPagamentoIstanza);
        return findListByQuery(className, QUERY_TENTATIVO_PAGAMENTO_BY_ID_PAGAMENTO_ISTANZA, map);
    }

    /**
     * Load tentativo pagamento by id istanza list.
     *
     * @param idIstanza      the id istanza
     * @param idStatoTentivo the id stato tentivo
     * @return the list
     */
    @Override
    public List<TentativoPagamentoExtendedDTO> loadTentativoPagamentoByIdIstanza(Long idIstanza, Long idStatoTentivo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        map.put("idStatoTentivo", idStatoTentivo);
        return findListByQuery(className, QUERY_TENTATIVO_PAGAMENTO_BY_ID_PAGAMENTO_ISTANZA, map);
    }

    /**
     * Save tentativo pagaemnto long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveTentativoPagamento(TentativoPagamentoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idStatoTentativoPag", dto.getIdStatoTentativoPagamento());
            map.put("identificativoPagamentoPpay", dto.getIdentificativoPagamentoPpay());
            map.put("hashPagamento", dto.getHashPagamento());
            map.put("tipoBollo", dto.getTipoBollo());
            map.put("flgSoloMarca", Boolean.TRUE.equals(dto.getFlgSoloMarca()) ? 1 : 0);
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdStatoTentativoPagamento().toString() + dto.getGestAttoreIns() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_TENTATIVO_PAGAMENTO, null, null), params, keyHolder, new String[]{"id_tentativo_pagamento"});
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
     * Update tentativo pagaemnto integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateTentativoPagamento(TentativoPagamentoDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idTentativoPagamento", dto.getIdTentativoPagamento());

        try {
            TentativoPagamentoDTO tentativoPagamentoDTO = this.findByPK(map);
            if (null == tentativoPagamentoDTO) {
                logError(className, "Record non trovato con id [" + dto.getIdTentativoPagamento() + "]");
                return -1;
            }

            Date now = Calendar.getInstance().getTime();
            map.put("idStatoTentativoPag", dto.getIdStatoTentativoPagamento());
            map.put("identificativoPagamentoPpay", dto.getIdentificativoPagamentoPpay());
            map.put("hashPagamento", dto.getHashPagamento());
            map.put("tipoBollo", dto.getTipoBollo());
            map.put("flgSoloMarca", Boolean.TRUE.equals(dto.getFlgSoloMarca()) ? 1 : 0);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_TENTATIVO_PAGAMENTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete tentativo pagaemnto integer.
     *
     * @param uid the uid
     * @return the integer
     */
    @Override
    public Integer deleteTentativoPagaemnto(String uid) {
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
        return getQuery(QUERY_TENTATIVO_PAGAMENTO_PK, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TentativoPagamentoDTO> getRowMapper() throws SQLException {
        return new TentativoPagamentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TentativoPagamentoExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new TentativoPagamentoExtendedRowMapper();
    }

    public static class TentativoPagamentoRowMapper implements RowMapper<TentativoPagamentoDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TentativoPagamentoRowMapper() throws SQLException {
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
        public TentativoPagamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TentativoPagamentoDTO bean = new TentativoPagamentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TentativoPagamentoDTO bean) throws SQLException {
            bean.setIdTentativoPagamento(rs.getLong("tentativo_pagamento_id"));
            bean.setIdStatoTentativoPagamento(rs.getLong("id_stato_tentativo_pag"));
            bean.setIdentificativoPagamentoPpay(rs.getString("identificativo_pagamento_ppay"));
            bean.setHashPagamento(rs.getString("hash_pagamento"));
            bean.setTipoBollo(rs.getString("tipo_bollo"));
            bean.setFlgSoloMarca(rs.getInt("flg_solo_marca") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setGestUID(rs.getString("gest_uid"));
        }

    }

    public static class TentativoPagamentoExtendedRowMapper implements RowMapper<TentativoPagamentoExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TentativoPagamentoExtendedRowMapper() throws SQLException {
            //Instantiate class
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
        public TentativoPagamentoExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TentativoPagamentoExtendedDTO bean = new TentativoPagamentoExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TentativoPagamentoExtendedDTO bean) throws SQLException {
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

    }


}