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

import it.csi.scriva.scrivabesrv.business.be.exception.DuplicateRecordException;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotaIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.FunzionarioDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataDTO;
import it.csi.scriva.scrivabesrv.dto.NotaPubblicataExtendedDTO;
import org.springframework.dao.DuplicateKeyException;
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
 * The type Nota istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class NotaIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<NotaPubblicataDTO> implements NotaIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_NOTA_PUBBLICATA = "SELECT srni.*, stf.nome_funzionario, stf.cognome_funzionario\n" +
            "FROM _replaceTableName_ srni\n" +
            "INNER JOIN scriva_t_funzionario stf ON stf.id_funzionario = srni.id_funzionario\n" +
            "WHERE 1=1\n";

    private static final String WHERE_COMPONENTE = "AND ind_visibile like '%'||:componenteApplicativa||'%'\n";

    private static final String WHERE_ID_ISTANZA = "AND srni.id_istanza =:idIstanza\n";

    private static final String WHERE_ID_NOTA_ISTANZA = "AND srni.id_nota_istanza=:idNotaIstanza\n";

    private static final String WHERE_UID_NOTA_ISTANZA = "AND srni.gest_uid=:gestUid\n";

    private static final String QUERY_PRIMARY_KEY = QUERY_LOAD_NOTA_PUBBLICATA + WHERE_ID_NOTA_ISTANZA;

    private static final String QUERY_GEST_UID = QUERY_LOAD_NOTA_PUBBLICATA + WHERE_UID_NOTA_ISTANZA;

    private static final String QUERY_INSERT_NOTA_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_nota_istanza, id_istanza, id_funzionario, data_nota, des_nota, ind_visibile,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(nextval('seq_scriva_r_nota_istanza'), :idIstanza, :idFunzionario, :dataNota, :desNota, :indVisibile,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_NOTA_ISTANZA = "UPDATE _replaceTableName_ srni\n" +
            "SET des_nota=:desNota, ind_visibile=:indVisibile,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE 1=1\n " +
            WHERE_UID_NOTA_ISTANZA;

    private static final String QUERY_DELETE_NOTA_ISTANZA = "DELETE FROM _replaceTableName_ srni\n" +
            "WHERE 1=1\n " +
            WHERE_UID_NOTA_ISTANZA;

    private static final String ORDER_BY_DATA_NOTA = " ORDER BY srni.data_nota ";
    /**
     * Search note pubblicate list.
     *
     * @param componenteApplicativa the componente applicativa
     * @param idIstanza             the id istanza
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @return the list
     */
    @Override
    public List<NotaPubblicataExtendedDTO> searchNotePubblicate(String componenteApplicativa, Long idIstanza, String offset, String limit, String sort) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("componenteApplicativa", componenteApplicativa);
        String query = QUERY_LOAD_NOTA_PUBBLICATA + WHERE_COMPONENTE;
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        query += ORDER_BY_DATA_NOTA;
        return findListByQuery(className, query, map);
    }

    /**
     * Save nota istanza long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveNotaIstanza(NotaPubblicataDTO dto) throws DuplicateRecordException {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idFunzionario", dto.getIdFunzionario());
            map.put("dataNota", dto.getDataNota() != null ? dto.getDataNota() : now);
            map.put("desNota", dto.getDes_nota());
            map.put("indVisibile", dto.getIndVisibile());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getDes_nota() + dto.getIdNotaIstanza() + dto.getIdFunzionario() + now));

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_NOTA_ISTANZA, null, null), params, keyHolder, new String[]{"id_nota_istanza"});
            Number key = keyHolder.getKey();
            return key.longValue();
        } catch (DuplicateKeyException e) {
            logError(className, e);
            throw new DuplicateRecordException();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update nota istanza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateNotaIstanza(NotaPubblicataDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            NotaPubblicataDTO notaPubblicata = this.findByUID(dto.getGestUID());
            if (null == notaPubblicata) {
                logError(className, "Record non trovato con idNotaIstanza [" + dto.getIdNotaIstanza() + "]");
                return -1;
            }
            map.put("idNotaIstanza", dto.getIdNotaIstanza());
            map.put("desNota", dto.getDes_nota());
            map.put("indVisibile", dto.getIndVisibile());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", dto.getGestUID());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_NOTA_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete nota istanza integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteNotaIstanza(String gestUID) {
        logBeginInfo(className, gestUID);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_NOTA_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by pk nota pubblicata dto.
     *
     * @param idNotaIstanza the id nota istanza
     * @return the nota pubblicata dto
     */
    @Override
    public NotaPubblicataExtendedDTO findByPK(Long idNotaIstanza) {
        logBeginInfo(className, idNotaIstanza);
        Map<String, Object> map = new HashMap<>();
        map.put("idNotaIstanza", idNotaIstanza);
        try {
            return template.queryForObject(getPrimaryKeySelect(), getParameterValue(map), getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by uid nota pubblicata extended dto.
     *
     * @param gestUID the uid nota istanza
     * @return the nota pubblicata extended dto
     */
    @Override
    public NotaPubblicataExtendedDTO findByUID(String gestUID) {
        logBeginInfo(className, gestUID);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUid", gestUID);
        try {
            return template.queryForObject(getQuery(QUERY_GEST_UID, null, null), getParameterValue(map), getExtendedRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    @Override
    public RowMapper<NotaPubblicataDTO> getRowMapper() throws SQLException {
        return new NotaPubblicataRowMapper();
    }

    @Override
    public RowMapper<NotaPubblicataExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new NotaPubblicataExtendedRowMapper();
    }

    /**
     * The type Nota pubblicata row mapper.
     */
    public static class NotaPubblicataRowMapper implements RowMapper<NotaPubblicataDTO> {
        /**
         * Instantiates a new Nota pubblicata row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotaPubblicataRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public NotaPubblicataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotaPubblicataDTO bean = new NotaPubblicataDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, NotaPubblicataDTO bean) throws SQLException {
            bean.setIdNotaIstanza(rs.getLong("id_nota_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setDataNota(rs.getTimestamp("data_nota"));
            bean.setDes_nota(rs.getString("des_nota"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Nota pubblicata extended row mapper.
     */
    public static class NotaPubblicataExtendedRowMapper implements RowMapper<NotaPubblicataExtendedDTO> {
        /**
         * Instantiates a new Nota pubblicata row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotaPubblicataExtendedRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public NotaPubblicataExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotaPubblicataExtendedDTO bean = new NotaPubblicataExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, NotaPubblicataExtendedDTO bean) throws SQLException {
            bean.setIdNotaIstanza(rs.getLong("id_nota_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setDataNota(rs.getTimestamp("data_nota"));
            bean.setDes_nota(rs.getString("des_nota"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));

            FunzionarioDTO funzionario = new FunzionarioDTO();
            populateFunzionarioBean(rs, funzionario);
            bean.setFunzionario(funzionario);
        }

        private void populateFunzionarioBean(ResultSet rs, FunzionarioDTO bean) throws SQLException {
            bean.setNomeFunzionario(rs.getString("nome_funzionario"));
            bean.setCognomeFunzionario(rs.getString("cognome_funzionario"));
        }

    }

}