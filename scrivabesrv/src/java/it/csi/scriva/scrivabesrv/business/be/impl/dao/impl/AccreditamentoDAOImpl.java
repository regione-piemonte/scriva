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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AccreditamentoDAO;
import it.csi.scriva.scrivabesrv.dto.AccreditamentoDTO;
import it.csi.scriva.scrivabesrv.util.otp.OtpUtils;
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

/**
 * The type Accreditamento dao.
 *
 * @author CSI PIEMONTE
 */
public class AccreditamentoDAOImpl extends ScrivaBeSrvGenericDAO<AccreditamentoDTO> implements AccreditamentoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ WHERE id_richiesta_accredito = :idRichiestaAccredito";

    private static final String QUERY_LOAD_ACCREDITAMENTO_BY_UID_AND_CODE = "SELECT * FROM _replaceTableName_ WHERE gest_uid = :uid AND md5(cod_verifica) = :codedOtp";

    private static final String QUERY_LOAD_ACCREDITAMENTO_BY_UID = "SELECT *\n" +
            "FROM _replaceTableName_\n" +
            "WHERE gest_uid = :uid\n";

    private static final String QUERY_INSERT_ACCREDITAMENTO = "INSERT INTO _replaceTableName_ (id_richiesta_accredito, cf_accredito, cognome_accredito, nome_accredito, des_email_accredito, "
            + "cod_verifica, flg_autorizza_dati_personali, id_compilante, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) "
            + "VALUES(nextval('seq_scriva_r_richiesta_accredito'), :cf, :cognome, :nome, :email, :otp, :flgPrivacy, :idCompilante, :dateIns, :attoreIns, :dateUpd, :attoreUpd, :uid)";

    private static final String QUERY_UPDATE_ACCREDITAMENTO = "UPDATE _replaceTableName_ "
            + "SET cf_accredito = :cf, cognome_accredito = :cognome, nome_accredito = :nome, des_email_accredito = :email, flg_autorizza_dati_personali = :flgPrivacy, "
            + "id_compilante = :idCompilante, gest_data_upd = :dateUpd, gest_attore_upd = :attoreUpd "
            + "WHERE gest_uid = :uid";

    private static final String QUERY_DELETE_ACCREDITAMENTO = "DELETE FROM _replaceTableName_ WHERE UPPER(cf_accredito) = UPPER(:cf)";

    /**
     * Load accreditamento list.
     *
     * @param uid        uid
     * @param encodedOtp encodedOtp
     * @return List<AccreditamentoDTO> list
     */
    @Override
    public List<AccreditamentoDTO> loadAccreditamento(String uid, String encodedOtp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("codedOtp", encodedOtp);
        return findSimpleDTOListByQuery(className, QUERY_LOAD_ACCREDITAMENTO_BY_UID_AND_CODE, map);
    }

    /**
     * Load accreditamento by pk accreditamento dto.
     *
     * @param pk idRichiestaAccredito
     * @return AccreditamentoDTO accreditamento dto
     */
    @Override
    public AccreditamentoDTO loadAccreditamentoByPK(Long pk) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idRichiestaAccredito", pk);
        return findByPK(className, map);
    }

    /**
     * Load accreditamento by uid accreditamento dto.
     *
     * @param uid the uid
     * @return the accreditamento dto
     */
    @Override
    public AccreditamentoDTO loadAccreditamentoByUid(String uid) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        return findSimpleDTOByQuery(className, QUERY_LOAD_ACCREDITAMENTO_BY_UID, map);
    }

    /**
     * Save accreditamento long.
     *
     * @param dto AccreditamentoDTO
     * @return id_richiesta_accredito long
     */
    @Override
    public Long saveAccreditamento(AccreditamentoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("cf", dto.getCfAccredito());
            map.put("cognome", dto.getCognomeAccredito());
            map.put("nome", dto.getNomeAccredito());
            map.put("email", dto.getDesEmailAccredito());
            map.put("otp", OtpUtils.generateOTP(6));
            map.put("flgPrivacy", Boolean.TRUE.equals(dto.getFlgAutorizzaDatiPersonali()) ? 1 : 0);
            map.put("idCompilante", dto.getIdCompilante());
            map.put("dateIns", now);
            map.put("attoreIns", dto.getGestAttoreIns());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreIns());
            String uid = generateGestUID(dto.getCfAccredito() + dto.getDesEmailAccredito() + now);
            map.put("uid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();

            template.update(getQuery(QUERY_INSERT_ACCREDITAMENTO, null, null), params, keyHolder, new String[]{"id_richiesta_accredito"});
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
     * Update accreditamento integer.
     *
     * @param dto AccreditamentoDTO
     * @return numero record aggiornati
     */
    @Override
    public Integer updateAccreditamento(AccreditamentoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("cf", dto.getCfAccredito());
            map.put("cognome", dto.getCognomeAccredito());
            map.put("nome", dto.getNomeAccredito());
            map.put("email", dto.getDesEmailAccredito());
            map.put("flgPrivacy", Boolean.TRUE.equals(dto.getFlgAutorizzaDatiPersonali()) ? 1 : 0);
            map.put("idCompilante", dto.getIdCompilante());
            map.put("dateUpd", now);
            map.put("attoreUpd", dto.getGestAttoreUpd());
            map.put("uid", dto.getGestUID());

            MapSqlParameterSource params = getParameterValue(map);

            return template.update(getQuery(QUERY_UPDATE_ACCREDITAMENTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete by cf.
     *
     * @param cf codice fiscale
     */
    @Override
    public void deleteByCF(String cf) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("cf", cf);
            MapSqlParameterSource params = getParameterValue(map);
            template.update(getQuery(QUERY_DELETE_ACCREDITAMENTO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
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
     * @return RowMapper<AccreditamentoDTO>
     */
    @Override
    public RowMapper<AccreditamentoDTO> getRowMapper() throws SQLException {
        return new AccreditamentoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<AccreditamentoDTO>
     */
    @Override
    public RowMapper<AccreditamentoDTO> getExtendedRowMapper() throws SQLException {
        return new AccreditamentoRowMapper();
    }

    /**
     * The type Accreditamento row mapper.
     */
    public static class AccreditamentoRowMapper implements RowMapper<AccreditamentoDTO> {

        /**
         * Instantiates a new Accreditamento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AccreditamentoRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public AccreditamentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AccreditamentoDTO bean = new AccreditamentoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AccreditamentoDTO bean) throws SQLException {
            bean.setIdRichiestaAccredito(rs.getLong("id_richiesta_accredito"));
            bean.setCfAccredito(rs.getString("cf_accredito"));
            bean.setCognomeAccredito(rs.getString("cognome_accredito"));
            bean.setNomeAccredito(rs.getString("nome_accredito"));
            bean.setDesEmailAccredito(rs.getString("des_email_accredito"));
            bean.setCodeVerifica(rs.getString("cod_verifica"));
            bean.setFlgAutorizzaDatiPersonali(1 == rs.getInt("flg_autorizza_dati_personali") ? Boolean.TRUE : Boolean.FALSE);
            bean.setIdCompilante(rs.getLong("id_compilante"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}