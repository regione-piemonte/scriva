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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IntegrazioneIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.IntegrazioneIstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoIntegrazioneDTO;
import org.apache.commons.lang3.StringUtils;
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
 * The type Integrazione istanza dao.
 */
public class IntegrazioneIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<IntegrazioneIstanzaDTO> implements IntegrazioneIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ISTANZA = "AND stii.id_istanza = :idIstanza\n";

    private static final String WHERE_ID_INTEG_ISTANZA = "AND stii.id_integrazione_istanza = :idIntegrazioneIstanza\n";

    private static final String WHERE_ID_TIPO_INTEG = "AND stii.id_tipo_integrazione = :idTipoIntegrazione\n";

    private static final String WHERE_COD_TIPO_INTEG = "AND sdti.cod_tipo_integrazione = :codTipoIntegrazione\n";

    private static final String WHERE_DATA_INVIO_NULL = "AND stii.data_invio IS NULL\n";

    private static final String WHERE_GESTUID = "AND stii.gest_uid = :gestUid\n";

    private static final String QUERY_LOAD_INTEGRAZIONI_ISTANZA = "SELECT stii.*,\n" +
            "sdti.*, sdti.id_tipo_integrazione AS tipo_integrazione_id\n" +
            "FROM _replaceTableName_ stii\n" +
            "INNER JOIN scriva_d_tipo_integrazione sdti ON sdti.id_tipo_integrazione = stii.id_tipo_integrazione\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_INSERT_INTEG_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_integrazione_istanza, id_istanza, id_tipo_integrazione, data_inserimento, data_invio,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(\n" +
            "nextval('seq_scriva_t_integrazione_istanza'), :idIstanza, :idTipoIntegrazione, :dataInserimento, :dataInvio,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_UPDATE_INTEG_ISTANZA = "UPDATE _replaceTableName_\n" +
            "SET data_invio=:dataInvio,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_integrazione_istanza = :idIntegrazioneIstanza";

    private static final String QUERY_DELETE_INTEG_ISTANZA = "DELETE FROM _replaceTableName_ stii\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_UPDATE_DATA_PROTOC_ALLEGATI = "UPDATE scriva_t_allegato_istanza\n" +
            "SET data_integrazione = :dataIntegrazione\n" +
            ", num_protocollo_allegato = :numProtocolloAllegato\n" +
            ", data_protocollo_allegato = :dataProtocolloAllegato\n" +
            ", gest_data_upd = :gestDataUpd\n" +
            ", gest_attore_upd = :gestAttoreUpd\n" +
            "WHERE id_allegato_istanza IN (\n" +
            "    SELECT sriia.id_allegato_istanza\n" +
            "    FROM scriva_r_integra_istanza_allegato sriia\n" +
            "    WHERE sriia.id_integrazione_istanza = :idIntegrazioneIstanza\n" +
            ")\n";


    /**
     * Load integrazioni istanza list.
     *
     * @param idIstanza             the id istanza
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @param codTipoIntegrazione   the cod tipo integrazione
     * @return the list
     */
    @Override
    public List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanza(Long idIstanza, Long idIntegrazioneIstanza, String codTipoIntegrazione) {
        logBeginInfo(className, "\n idIstanza : [" + idIstanza + "]");

        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_INTEGRAZIONI_ISTANZA;
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (idIntegrazioneIstanza != null) {
            map.put("idIntegrazioneIstanza", idIntegrazioneIstanza);
            query += WHERE_ID_INTEG_ISTANZA;
        }
        if (StringUtils.isNotBlank(codTipoIntegrazione)) {
            map.put("codTipoIntegrazione", codTipoIntegrazione);
            query += WHERE_COD_TIPO_INTEG;
        }
        logEnd(className);
        return findListByQuery(className, query, map, null, null);
    }

    /**
     * Load integrazioni istanza no inviata list.
     *
     * @param idIstanza           the id istanza
     * @param codTipoIntegrazione the cod tipo integrazione
     * @return the list
     */
    @Override
    public List<IntegrazioneIstanzaExtendedDTO> loadIntegrazioniIstanzaNoInviata(Long idIstanza, String codTipoIntegrazione) {
        logBeginInfo(className, "\n idIstanza : [" + idIstanza + "] - codTipoIntegrazione : [" + codTipoIntegrazione + "]");

        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_INTEGRAZIONI_ISTANZA + WHERE_DATA_INVIO_NULL;
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (StringUtils.isNotBlank(codTipoIntegrazione)) {
            map.put("codTipoIntegrazione", codTipoIntegrazione);
            query += WHERE_COD_TIPO_INTEG;
        }
        logEnd(className);
        return findListByQuery(className, query, map, null, null);
    }

    /**
     * Save integrazione istanza long.
     *
     * @param dto     the integrazione istanza dto
     * @param dataIns the data ins
     * @return the long
     */
    @Override
    public Long saveIntegrazioneIstanza(IntegrazioneIstanzaDTO dto, Date dataIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idTipoIntegrazione", dto.getIdTipoIntegrazione());
            map.put("dataInserimento", dto.getDataInserimento() != null ? dto.getDataInserimento() : now);
            map.put("dataInvio", dto.getDataInvio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            String uid = generateGestUID(String.valueOf(dto.getIdIstanza()) + dto.getIdTipoIntegrazione() + now);
            map.put("gestUid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = getQuery(QUERY_INSERT_INTEG_ISTANZA, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            template.update(query, params, keyHolder, new String[]{"id_integrazione_istanza"});
            Number key = keyHolder.getKey();
            return key != null ? key.longValue() : null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update integrazione istanza integer.
     *
     * @param dto the integrazione istanza dto
     * @param now the now
     * @return the integer
     */
    @Override
    public Integer updateIntegrazioneIstanza(IntegrazioneIstanzaDTO dto, Date now) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();

            map.put("gestUid", dto.getGestUID());
            IntegrazioneIstanzaDTO integrazioneIstanza = this.findByUID(dto.getGestUID());
            if (integrazioneIstanza == null) {
                logError(className, "Record non trovato con uid [" + dto.getGestUID() + "]");
                return -1;
            }
            map.put("idIntegrazioneIstanza", integrazioneIstanza.getIdIntegrazioneIstanza());
            map.put("dataInvio", integrazioneIstanza.getDataInvio() == null ? dto.getDataInvio() : integrazioneIstanza.getDataInvio());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_INTEG_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete integrazione istanza integer.
     *
     * @param idIstanza             the id istanza
     * @param idIntegrazioneIstanza the id integrazione istanza
     * @param gestUid               the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteIntegrazioneIstanza(Long idIstanza, Long idIntegrazioneIstanza, String gestUid) {
        logBegin(className);
        try {
            if (idIstanza == null && idIntegrazioneIstanza == null && StringUtils.isBlank(gestUid)) {
                return -1;
            }
            Map<String, Object> map = new HashMap<>();
            String query = QUERY_DELETE_INTEG_ISTANZA;
            if (idIntegrazioneIstanza != null) {
                map.put("idIntegrazioneIstanza", idIntegrazioneIstanza);
                query += WHERE_ID_INTEG_ISTANZA;
            } else if (StringUtils.isNotBlank(gestUid)) {
                map.put("gestUid", gestUid);
                query += WHERE_GESTUID;
            } else {
                map.put("idIstanza", idIstanza);
                query += WHERE_ID_ISTANZA;
            }
            return template.update(query, getParameterValue(map));
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update data integrazione protocollo allegati integer.
     *
     * @param dto              the integrazione istanza dto
     * @param dataIntegrazione the data integrazione
     * @return the integer
     */
    @Override
    public Integer updateDataIntegrazioneProtocolloAllegati(IntegrazioneIstanzaDTO dto, Date dataIntegrazione) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();

            map.put("gestUid", dto.getGestUID());
            IntegrazioneIstanzaDTO integrazioneIstanza = this.findByUID(dto.getGestUID());
            if (integrazioneIstanza == null) {
                logError(className, "Record non trovato con uid [" + dto.getGestUID() + "]");
                return -1;
            }
            map.put("idIntegrazioneIstanza", integrazioneIstanza.getIdIntegrazioneIstanza());
            map.put("dataIntegrazione", dto.getDataInvio() != null ? dto.getDataInvio() : dataIntegrazione);
            map.put("numProtocolloAllegato", dto.getNumProtocollo());
            map.put("dataProtocolloAllegato", dto.getDataProtocollo());
            map.put("gestDataUpd", dataIntegrazione);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_DATA_PROTOC_ALLEGATI, null, null), params);
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
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Find by uid integrazione istanza dto.
     *
     * @param gestUid the gest uid
     * @return the integrazione istanza dto
     */
    public IntegrazioneIstanzaDTO findByUID(String gestUid) {
        logBeginInfo(className, gestUid);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUid", gestUid);
        return findSimpleDTOByQuery(className, getQuery(QUERY_LOAD_INTEGRAZIONI_ISTANZA + WHERE_GESTUID, null, null), map);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IntegrazioneIstanzaDTO> getRowMapper() throws SQLException {
        return new IntegrazioneIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IntegrazioneIstanzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IntegrazioneIstanzaExtendedRowMapper();
    }

    /**
     * The type Integrazione istanza row mapper.
     */
    public static class IntegrazioneIstanzaRowMapper implements RowMapper<IntegrazioneIstanzaDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IntegrazioneIstanzaRowMapper() throws SQLException {
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
        public IntegrazioneIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IntegrazioneIstanzaDTO bean = new IntegrazioneIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, IntegrazioneIstanzaDTO bean) throws SQLException {
            bean.setIdIntegrazioneIstanza(rs.getLong("id_integrazione_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipoIntegrazione(rs.getLong("tipo_integrazione_id"));
            bean.setDataInserimento(rs.getTimestamp("data_inserimento"));
            bean.setDataInvio(rs.getTimestamp("data_invio"));
            bean.setNumProtocollo(rs.getString("num_protocollo_integrazione"));
            bean.setDataProtocollo(rs.getDate("data_protocollo_integrazione"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Integrazione istanza extended row mapper.
     */
    public static class IntegrazioneIstanzaExtendedRowMapper implements RowMapper<IntegrazioneIstanzaExtendedDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IntegrazioneIstanzaExtendedRowMapper() throws SQLException {
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
        public IntegrazioneIstanzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IntegrazioneIstanzaExtendedDTO bean = new IntegrazioneIstanzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        /**
         * Populate bean.
         *
         * @param rs   the rs
         * @param bean the bean
         * @throws SQLException the sql exception
         */
        public void populateBean(ResultSet rs, IntegrazioneIstanzaExtendedDTO bean) throws SQLException {
            bean.setIdIntegrazioneIstanza(rs.getLong("id_integrazione_istanza"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            TipoIntegrazioneDTO tipoIntegrazione = new TipoIntegrazioneDTO();
            populateTipoIntegrazioneBean(rs, tipoIntegrazione);
            bean.setTipoIntegrazione(tipoIntegrazione.getIdTipoIntegrazione() != null ? tipoIntegrazione : null);
            bean.setDataInserimento(rs.getTimestamp("data_inserimento"));
            bean.setDataInvio(rs.getTimestamp("data_invio"));
            bean.setNumProtocollo(rs.getString("num_protocollo_integrazione"));
            bean.setDataProtocollo(rs.getDate("data_protocollo_integrazione"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateTipoIntegrazioneBean(ResultSet rs, TipoIntegrazioneDTO bean) throws SQLException {
            bean.setIdTipoIntegrazione(rs.getLong("tipo_integrazione_id"));
            bean.setCodTipoIntegrazione(rs.getString("cod_tipo_integrazione"));
            bean.setDesTipoIntegrazione(rs.getString("des_tipo_integrazione"));
        }
    }
}