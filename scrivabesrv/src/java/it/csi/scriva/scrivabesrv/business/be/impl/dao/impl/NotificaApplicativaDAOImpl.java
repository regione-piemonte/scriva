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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaApplicativaDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.AmbitoDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaApplicativaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoAdempimentoExtendedDTO;
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
 * The Notifica applicativa dao.
 *
 * @author CSI PIEMONTE
 */
public class NotificaApplicativaDAOImpl extends ScrivaBeSrvGenericDAO<NotificaApplicativaDTO> implements NotificaApplicativaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_NOTIFICA_APPL = "SELECT stna.*, sti.*, sda.*, sdta.*, sdam.*\n" +
            "FROM _replaceTableName_ stna\n" +
            "INNER JOIN scriva_t_notifica stn ON stn.id_notifica = stna.id_notifica\n" +
            "INNER JOIN scriva_d_stato_notifica sdsn ON sdsn.id_stato_notifica = stn.id_stato_notifica\n" +
            "INNER JOIN scriva_r_notifica_configurazione srnc ON srnc.id_notifica_configurazione = stn.id_notifica_configurazione \n" +
            "INNER JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = stna.id_componente_app_push\n" +
            "LEFT JOIN scriva_t_istanza sti ON sti.id_istanza = stn.id_istanza\n" +
            "LEFT JOIN scriva_d_adempimento sda ON sda.id_adempimento = sti.id_adempimento\n" +
            "LEFT JOIN scriva_d_tipo_adempimento sdta ON sdta.id_tipo_adempimento = sda.id_tipo_adempimento\n" +
            "LEFT JOIN scriva_d_ambito sdam ON sdam.id_ambito = sdta.id_ambito\n" +
            "WHERE 1=1\n";

    private static final String QUERY_UPDATE_NOTIFICA_APPL = "UPDATE _replaceTableName_\n" +
            "SET data_lettura=:dataLettura, data_cancellazione=:dataCancellazione,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n\n" +
            "WHERE gest_uid = :gestUid\n" +
            "AND cf_destinatario = :cfDestinatario";

    private static final String QUERY_INSERT_NOTIFICA_APPL = "INSERT INTO _replaceTableName_\n" +
            "(id_notifica_applicativa, id_notifica, id_istanza,\n" +
            "id_componente_app_push, cf_destinatario,  \n" +
            "des_oggetto, des_messaggio, data_inserimento, \n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(\n" +
            "nextval('seq_scriva_r_notifica_applicativa'), :idNotifica, :idIstanza,\n" +
            ":idComponenteAppPush, :cfDestinatario,\n" +
            ":desOggetto, :desMessaggio, :dataInserimento, \n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_DELETE_NOTIFICA_APPL = "DELETE FROM _replaceTableName_ stna\n";

    private static final String WHERE_ID_NOTIFICA_APPL = "AND stna.id_notifica_applicativa = :idNotificaApplicativa\n";

    private static final String WHERE_COD_STATO_NOTIFICA_L = "AND data_lettura IS NOT NULL\n";

    private static final String WHERE_COD_STATO_NOTIFICA_N = "AND data_lettura IS NULL\n";

    private static final String WHERE_ID_ISTANZA = "AND stn.id_istanza = :idIstanza\n";

    private static final String WHERE_COD_ISTANZA = "AND sti.cod_istanza = :codIstanza\n";

    private static final String WHERE_COMPONENTE = "AND sdca.cod_componente_app = :codComponenteApp\n";

    private static final String WHERE_COMPONENTE_BY_COD = "AND id_componente_app_push = (SELECT id_componente_app FROM scriva_d_componente_app WHERE UPPER(cod_componente_app) = UPPER(:codComponenteApp))\n";

    private static final String WHERE_CF_DESTINATARIO = "AND stn.cf_destinatario = :cfDestinatario\n";

    private static final String WHERE_RIF_CANALE = "AND stn.rif_canale = :rifCanale\n";

    private static final String WHERE_GESTUID = "AND stna.gest_uid = :gestUid\n";

    private static final String WHERE_DATA_CANC_IS_NULL = "AND data_cancellazione IS NULL\n";

    private static final String WHERE_ID_ADEMPIMENTO_LIST = "AND sti.id_adempimento in(:idAdempimentoList)\n";

    private static final String WHERE_ID_DATAINIZIO = "AND stna.data_inserimento >= :dataInizio\n";

    private static final String WHERE_ID_DATAFINE = "AND stna.data_inserimento <= :dataFine\n";

    private static final String QUERY_UPDATE_NOTIFICA_APPL_ALL_READ = "UPDATE _replaceTableName_\n" +
            "SET data_lettura=:gestDataUpd, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE cf_destinatario=:cfDestinatario\n" +
            WHERE_COMPONENTE +
            WHERE_COD_STATO_NOTIFICA_N +
            WHERE_DATA_CANC_IS_NULL;

    private static final String QUERY_UPDATE_NOTIFICA_APPL_ALL_READ_SEARCH = "UPDATE _replaceTableName_\n" +
            "SET data_lettura=:gestDataUpd, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE cf_destinatario=:cfDestinatario\n" +
            WHERE_COMPONENTE_BY_COD +
            WHERE_COD_STATO_NOTIFICA_N +
            WHERE_DATA_CANC_IS_NULL;

    /**
     * Load notifica applicativa list.
     *
     * @return the list
     */
    @Override
    public List<NotificaApplicativaDTO> loadNotificaApplicativa() {
        return loadNotificaApplicativa(null, null, true, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Load notifica applicativa list per la ricerca.
     *
     * @param codComponenteApp      the cod componente app
     * @param cfDestinatario        the cf destinatario
     * @param ancheCancellate       the anche cancellate
     * @param idNotificaApplicativa the id notifica applicativa
     * @param codStatoNotifica      the cod stato notifica
     * @param codIstanza            the cod istanza
     * @param idAdempimentoList     the id adempimento list
     * @param dataInizio            the data inizio
     * @param dataFine              the data fine
     * @param offset                the offset
     * @param limit                 the limit
     * @param sort                  the sort
     * @return list list
     */
    @Override
    public List<NotificaApplicativaDTO> loadNotificaApplicativa(
            String codComponenteApp,
            String cfDestinatario,
            Boolean ancheCancellate,
            Long idNotificaApplicativa,
            String codStatoNotifica,
            String codIstanza,
            List<Long> idAdempimentoList,
            Date dataInizio,
            Date dataFine,
            String offset,
            String limit,
            String sort) {
        String idAdempimentoListLog = null;

        if (idAdempimentoList != null) {
            idAdempimentoListLog = idAdempimentoList.toString();
        }

        logBeginInfo(className, "\n cfDestinatario : [" + cfDestinatario + "]"
                + "\n codComponenteApp : " + codComponenteApp
                + "\n ancheCancellate : " + ancheCancellate
                + "\n idNotificaApplicativa : " + idNotificaApplicativa
                + "\n codStatoNotifica : " + codStatoNotifica
                + "\n codIstanza : " + codIstanza
                + "\n idAdempimentoList : " + idAdempimentoListLog
                + "\n dataInizio : " + dataInizio
                + "\n dataFine : " + dataFine
                + "\n offset : " + offset + "\n limit : " + limit + "\n sort : " + sort);

        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_NOTIFICA_APPL;

        if (StringUtils.isNotBlank(cfDestinatario)) {
            map.put("cfDestinatario", cfDestinatario);
            query += WHERE_CF_DESTINATARIO;
        }

        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE;
        }

        if (Boolean.FALSE.equals(ancheCancellate)) {
            query += WHERE_DATA_CANC_IS_NULL;
        }

        if (idNotificaApplicativa != null) {
            map.put("idNotificaApplicativa", idNotificaApplicativa);
            query += WHERE_ID_NOTIFICA_APPL;
        }

        if (StringUtils.isNotBlank(codStatoNotifica) && !"NC".equalsIgnoreCase(codStatoNotifica)) {
            query += "N".equalsIgnoreCase(codStatoNotifica) || "NLNC".equalsIgnoreCase(codStatoNotifica) ? WHERE_COD_STATO_NOTIFICA_N : WHERE_COD_STATO_NOTIFICA_L;
        }

        if (codIstanza != null) {
            map.put("codIstanza", codIstanza);
            query += WHERE_COD_ISTANZA;
        }

        if (idAdempimentoList != null && !idAdempimentoList.isEmpty()) {
            map.put("idAdempimentoList", idAdempimentoList);
            query += WHERE_ID_ADEMPIMENTO_LIST;
        }

        if (dataInizio != null) {
            map.put("dataInizio", dataInizio);
            query += WHERE_ID_DATAINIZIO;
        }

        if (dataFine != null) {
            map.put("dataFine", dataFine);
            query += WHERE_ID_DATAFINE;
        }

        if (StringUtils.isNotBlank(sort)) {
            query += getQuerySortingSegment(sort);
        }
        logEnd(className);
        return findListByQuery(className, query, map, offset, limit);
    }

    /**
     * Save notifica applicativa long.
     *
     * @param dto the notifica
     * @return the long
     */
    @Override
    public Long saveNotificaApplicativa(NotificaApplicativaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idNotifica", dto.getIdNotifica());
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idComponenteAppPush", dto.getIdComponenteAppPush());
            map.put("cfDestinatario", dto.getCfDestinatario());
            map.put("desOggetto", dto.getDesOggetto());
            map.put("desMessaggio", dto.getDesMessaggio());
            map.put("dataInserimento", dto.getDataInserimento() != null ? dto.getDataInserimento() : now);
            map.put("dataLettura", dto.getDataLettura());
            map.put("dataCancellazione", dto.getDataCancellazione());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            String uid = generateGestUID(String.valueOf(dto.getIdNotifica()) + now);
            map.put("gestUid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String query = getQuery(QUERY_INSERT_NOTIFICA_APPL, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            template.update(query, params, keyHolder, new String[]{"id_notifica"});
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
     * Update notifica applicativa integer.
     *
     * @param dto the notifica applicativa
     * @return the integer
     */
    @Override
    public Integer updateNotificaApplicativa(NotificaApplicativaDTO dto) {
        logBeginInfo(className, dto);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("gestUid", dto.getGestUID());
            NotificaApplicativaDTO notificaApplicativa = this.findByUID(dto.getGestUID());
            if (notificaApplicativa == null) {
                logError(className, "Record non trovato con idNotificaApp [" + dto.getIdNotificaApplicativa() + "]");
                return -1;
            }
            map.put("dataLettura", dto.getDataLettura() != null ? now : notificaApplicativa.getDataLettura());
            map.put("dataCancellazione", dto.getDataCancellazione() != null ? now : notificaApplicativa.getDataCancellazione());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("cfDestinatario", dto.getGestAttoreUpd());

            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_UPDATE_NOTIFICA_APPL, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            return template.update(query, params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update notifiche tutte lette integer.
     *
     * @param codiceFiscale    the codice fiscale
     * @param codComponenteApp the cod componente app
     * @return the integer
     */
    @Override
    public Integer updateNotificheTutteLette(String codiceFiscale, String codComponenteApp) {
        logBeginInfo(className, codiceFiscale);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("cfDestinatario", codiceFiscale);
            map.put("codComponenteApp", codComponenteApp);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", codiceFiscale);
            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_UPDATE_NOTIFICA_APPL_ALL_READ, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            return template.update(query, params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update notifiche tutte lette integer.
     *
     * @param codComponenteApp  the cod componente app
     * @param cfDestinatario    the cf destinatario
     * @param codStatoNotifica  the cod stato notifica
     * @param codIstanza        the cod istanza
     * @param idAdempimentoList the id adempimento list
     * @param dataInizio        the data inizio
     * @param dataFine          the data fine
     * @return the integer
     */
    @Override
    public Integer updateNotificheTutteLette(String codComponenteApp, String cfDestinatario, String codStatoNotifica, String codIstanza, List<Long> idAdempimentoList, Date dataInizio, Date dataFine) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("cfDestinatario", cfDestinatario);
            map.put("codComponenteApp", codComponenteApp);
            map.put("codStatoNotifica", codStatoNotifica);
            map.put("codIstanza", codIstanza);
            map.put("idAdempimentoList", idAdempimentoList);
            map.put("dataInizio", dataInizio);
            map.put("dataFine", dataFine);
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", cfDestinatario);
            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_UPDATE_NOTIFICA_APPL_ALL_READ_SEARCH, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            return template.update(query, params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete notifica applicativa.
     *
     * @param gestUid the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteNotificaApplicativa(String gestUid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUid);
            MapSqlParameterSource params = getParameterValue(map);
            String query = getQuery(QUERY_DELETE_NOTIFICA_APPL + WHERE_GESTUID, null, null);
            logDebug(className, "\nquery executed : \n" + query + "\n");
            return template.update(query, params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by uid notifica applicativa dto.
     *
     * @param gestUid the gest uid
     * @return the notifica applicativa dto
     */
    public NotificaApplicativaDTO findByUID(String gestUid) {
        logBeginInfo(className, gestUid);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUid", gestUid);
        return findSimpleDTOByQuery(className, getQuery(QUERY_LOAD_NOTIFICA_APPL + WHERE_GESTUID, null, null), map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_NOTIFICA_APPL + WHERE_ID_NOTIFICA_APPL, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NotificaApplicativaDTO> getRowMapper() throws SQLException {
        return new NotificaApplicativaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NotificaApplicativaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new NotificaApplicativaExtendedRowMapper();
    }

    /**
     * The type Notifica applicativa row mapper.
     */
    public static class NotificaApplicativaRowMapper implements RowMapper<NotificaApplicativaDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotificaApplicativaRowMapper() throws SQLException {
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
        public NotificaApplicativaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificaApplicativaDTO bean = new NotificaApplicativaDTO();
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
        public void populateBean(ResultSet rs, NotificaApplicativaDTO bean) throws SQLException {
            bean.setIdNotificaApplicativa(rs.getLong("id_notifica_applicativa"));
            bean.setIdNotifica(rs.getLong("id_notifica"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdComponenteAppPush(rs.getLong("id_componente_app_push"));
            bean.setCfDestinatario(rs.getString("cf_destinatario"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setDesMessaggio(rs.getString("des_messaggio"));
            bean.setDataInserimento(rs.getTimestamp("data_inserimento"));
            bean.setDataLettura(rs.getTimestamp("data_lettura"));
            bean.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Notifica applicativa extended row mapper.
     */
    public static class NotificaApplicativaExtendedRowMapper implements RowMapper<NotificaApplicativaExtendedDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotificaApplicativaExtendedRowMapper() throws SQLException {
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
        public NotificaApplicativaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificaApplicativaExtendedDTO bean = new NotificaApplicativaExtendedDTO();
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
        public void populateBean(ResultSet rs, NotificaApplicativaExtendedDTO bean) throws SQLException {
            bean.setIdNotificaApplicativa(rs.getLong("id_notifica_applicativa"));
            bean.setIdNotifica(rs.getLong("id_notifica"));

            IstanzaExtendedDTO istanza = new IstanzaExtendedDTO();
            populateIstanzaBean(rs, istanza);
            bean.setIstanza(istanza);

            bean.setIdComponenteAppPush(rs.getLong("id_componente_app_push"));
            bean.setCfDestinatario(rs.getString("cf_destinatario"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setDesMessaggio(rs.getString("des_messaggio"));
            bean.setDataInserimento(rs.getTimestamp("data_inserimento"));
            bean.setDataLettura(rs.getTimestamp("data_lettura"));
            bean.setDataCancellazione(rs.getTimestamp("data_cancellazione"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateIstanzaBean(ResultSet rs, IstanzaExtendedDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            AdempimentoExtendedDTO adempimento = new AdempimentoExtendedDTO();
            populateAdempimentoBean(rs, adempimento);
            bean.setAdempimento(adempimento.getIdAdempimento() != null ? adempimento : null);

            bean.setCodIstanza(rs.getString("cod_istanza"));
            bean.setCodPratica(rs.getString("cod_pratica"));
        }

        private void populateAdempimentoBean(ResultSet rs, AdempimentoExtendedDTO bean) throws SQLException {
            bean.setIdAdempimento(rs.getLong("id_adempimento"));
            TipoAdempimentoExtendedDTO tipoAdempimento = new TipoAdempimentoExtendedDTO();
            pupulateTipoAdempimentoBean(rs, tipoAdempimento);
            bean.setTipoAdempimento(tipoAdempimento.getIdTipoAdempimento() != null ? tipoAdempimento : null);
            bean.setCodAdempimento(rs.getString("cod_adempimento"));
            bean.setDesAdempimento(rs.getString("des_adempimento"));
            bean.setDesEstesaAdempimento(rs.getString("des_estesa_adempimento"));
        }

        private void pupulateTipoAdempimentoBean(ResultSet rs, TipoAdempimentoExtendedDTO tipoAdempimento) throws SQLException {
            tipoAdempimento.setIdTipoAdempimento(rs.getLong("id_tipo_adempimento"));
            AmbitoDTO ambito = new AmbitoDTO();
            populateAmbitoBean(rs, ambito);
            tipoAdempimento.setAmbito(ambito.getIdAmbito() != null ? ambito : null);
            tipoAdempimento.setDesTipoAdempimento(rs.getString("des_tipo_adempimento"));
            tipoAdempimento.setDesEstesaTipoAdempimento(rs.getString("des_estesa_tipo_adempimento"));
            tipoAdempimento.setCodTipoAdempimento(rs.getString("cod_tipo_adempimento"));

        }

        private void populateAmbitoBean(ResultSet rs, AmbitoDTO bean) throws SQLException {
            bean.setIdAmbito(rs.getLong("id_ambito"));
            bean.setCodAmbito(rs.getString("cod_ambito"));
            bean.setDesAmbito(rs.getString("des_ambito"));
            bean.setDesEstesaAmbito(rs.getString("des_estesa_ambito"));
        }
    }

}