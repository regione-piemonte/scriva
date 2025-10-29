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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.NotificaDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaDTO;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneDAO;
import it.csi.scriva.scrivabesrv.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface Notifica dao.
 *
 * @author CSI PIEMONTE
 */
public class NotificaDAOImpl extends ScrivaBeSrvGenericDAO<NotificaDTO> implements NotificaDAO {

    /**
     * The Configurazione dao.
     */
    @Autowired
    ConfigurazioneDAO configurazioneDAO;

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_NOTIFICA = "SELECT stn.*\n" +
            "FROM _replaceTableName_ stn\n" +
            "INNER JOIN scriva_d_stato_notifica sdsn ON sdsn.id_stato_notifica = stn.id_stato_notifica\n" +
            "INNER JOIN scriva_r_notifica_configurazione srnc ON srnc.id_notifica_configurazione = stn.id_notifica_configurazione \n" +
            "LEFT JOIN scriva_t_istanza sti ON sti.id_istanza = stn.id_istanza\n" +
            "LEFT JOIN scriva_d_componente_app sdca ON sdca.id_componente_app = stn.id_componente_app_push\n" +
            "WHERE 1=1\n ";

    private static final String QUERY_UPDATE_NOTIFICA = "UPDATE _replaceTableName_\n" +
            "SET id_stato_notifica=:idStatoNotifica, id_notifica_configurazione=:idNotificaConfigurazione, id_istanza=:idIstanza,\n" +
            "id_componente_app_push=:idComponenteAppPush, cf_destinatario=:cfDestinatario, rif_canale=:rifCanale, rif_canale_cc=:rifCanaleCC,\n" +
            "des_oggetto=:desOggetto, des_messaggio=:desMessaggio, des_segnalazione=:desSegnalazione, data_inserimento=:dataInserimento, data_invio=:dataInvio, n_tentativi_invio=:nTentativiInvio,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE id_notifica=:idNotifica";

    private static final String QUERY_INSERT_NOTIFICA = "INSERT INTO _replaceTableName_\n" +
            "(id_notifica, id_stato_notifica, id_notifica_configurazione, id_istanza,\n" +
            "id_componente_app_push, cf_destinatario, rif_canale, rif_canale_cc,\n" +
            "des_oggetto, des_messaggio, des_segnalazione, data_inserimento, data_invio, n_tentativi_invio,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(\n" +
            "nextval('seq_scriva_t_notifica'), :idStatoNotifica, :idNotificaConfigurazione, :idIstanza,\n" +
            ":idComponenteAppPush, :cfDestinatario, :rifCanale, :rifCanaleCC,\n" +
            ":desOggetto, :desMessaggio, :desSegnalazione, :dataInserimento, :dataInvio, :nTentativiInvio,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_DELETE_NOTIFICA = "DELETE FROM _replaceTableName_ stn\n";

    private static final String WHERE_ID_NOTIFICA = "AND stn.id_notifica = :idNotifica\n";

    private static final String WHERE_COD_STATO_NOTIFICA = "AND UPPER(sdsn.cod_stato_notifica) = UPPER(:codStatoNotifica)\n";

    private static final String WHERE_ID_ISTANZA = "AND stn.id_istanza = :idIstanza\n";

    private static final String WHERE_COMPONENTE = "AND sdca.cod_componente_app = :codComponenteApp\n";

    private static final String WHERE_CF_DESTINATARIO = "AND stn.cf_destinatario = :cfDestinatario\n";

    private static final String WHERE_RIF_CANALE = "AND stn.rif_canale = :rifCanale\n";

    private static final String WHERE_GESTUID = "AND stn.gest_uid = :gestUid\n";

    /**
     * Load notifica list.
     *
     * @return the list
     */
    @Override
    public List<NotificaDTO> loadNotifica() {
        return loadNotifica(null, null, null, null, null, null);
    }

    /**
     * Load notifica list.
     *
     * @param idNotifica       the id notifica
     * @param codStatoNotifica the cod stato notifica
     * @param idIstanza        the id istanza
     * @param codComponenteApp the cod componente app
     * @param cfDestinatario   the cf destinatario
     * @param rifCanale        the rif canale
     * @return the list
     */
    @Override
    public List<NotificaDTO> loadNotifica(Long idNotifica, String codStatoNotifica, Long idIstanza, String codComponenteApp, String cfDestinatario, String rifCanale) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_NOTIFICA;
        if (idNotifica != null) {
            map.put("idNotifica", idNotifica);
            query += WHERE_ID_NOTIFICA;
        }
        if (StringUtils.isNotBlank(codStatoNotifica)) {
            map.put("codStatoNotifica", codStatoNotifica);
            query += WHERE_COD_STATO_NOTIFICA;
        }
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (StringUtils.isNotBlank(codComponenteApp)) {
            map.put("codComponenteApp", codComponenteApp);
            query += WHERE_COMPONENTE;
        }
        if (StringUtils.isNotBlank(cfDestinatario)) {
            map.put("cfDestinatario", cfDestinatario);
            query += WHERE_CF_DESTINATARIO;
        }
        if (StringUtils.isNotBlank(rifCanale)) {
            map.put("rifCanale", rifCanale);
            query += WHERE_RIF_CANALE;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Save notifica long.
     *
     * @param dto the notifica
     * @return Map.Entry<Long, String> coppia di valori: idNotifica e uid. 
     *          L'uid è necessario per le notifiche verso l'app IO
     */
    @Override
    public Map.Entry<Long, String> saveNotifica(NotificaDTO dto) {
        logBegin(className);
        List<ConfigurazioneDTO> limiteOggNotifica = configurazioneDAO.loadConfigByKey(Constants.CONF_KEY_LIMITE_OGGETTO_NOTIFICA);
        int limiteOggNotificaInt = Integer.parseInt(limiteOggNotifica.get(0).getValore());
        
        try {
            String desOggetto ="";
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();

            if(dto.getDesOggetto() !=null)
            {
               desOggetto =  dto.getDesOggetto().length() > limiteOggNotificaInt ? 
                             dto.getDesOggetto().substring(0, limiteOggNotificaInt) : 
                             dto.getDesOggetto();          
            }
            map.put("idStatoNotifica", dto.getIdStatoNotifica());
            map.put("idNotificaConfigurazione", dto.getIdNotificaConfigurazione());
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idComponenteAppPush", dto.getIdComponenteAppPush());
            map.put("cfDestinatario", dto.getCfDestinatario());
            map.put("rifCanale", dto.getRifCanale());
            map.put("rifCanaleCC", dto.getRifCanaleCc());
            map.put("desOggetto", desOggetto);
            map.put("desMessaggio", dto.getDesMessaggio());
            map.put("desSegnalazione", dto.getDesSegnalazione());
            map.put("dataInserimento", dto.getDataInserimento());
            map.put("dataInvio", dto.getDataInvio());
            map.put("nTentativiInvio", dto.getTentativiInvio());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            //String uid = generateGestUID(dto.getIdStatoNotifica() + String.valueOf(dto.getIdNotificaConfigurazione()) + now);
            UUID uuid = UUID.randomUUID();
            map.put("gestUid", uuid.toString());

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(getQuery(QUERY_INSERT_NOTIFICA, null, null), params, keyHolder, new String[]{"id_notifica"});
            Number key = keyHolder.getKey();
            Long idNotifica = key != null ? key.longValue() : null;

            // Restituisce una coppia di valori: idNotifica e uid
            return new AbstractMap.SimpleEntry<>(idNotifica, uuid.toString());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update notifica integer.
     *
     * @param dto the notifica
     * @return the integer
     */
    @Override
    public Integer updateNotifica(NotificaDTO dto) {
        logBeginInfo(className, dto);
        List<ConfigurazioneDTO> limiteOggNotifica = configurazioneDAO.loadConfigByKey(Constants.CONF_KEY_LIMITE_OGGETTO_NOTIFICA);
        int limiteOggNotificaInt = Integer.parseInt(limiteOggNotifica.get(0).getValore());
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            String desOggetto ="";

            NotificaDTO notifica = this.findById(dto.getIdNotifica());
            if (notifica == null) {
                logError(className, "Record non trovato con idNotifica [" + dto.getIdNotifica() + "]");
                return -1;
            }
            if(dto.getDesOggetto() !=null)
            {
               desOggetto =  dto.getDesOggetto().length() > limiteOggNotificaInt ? 
                             dto.getDesOggetto().substring(0, limiteOggNotificaInt) : 
                             dto.getDesOggetto();  
            }
            map.put("idNotifica", dto.getIdNotifica());
            map.put("idStatoNotifica", dto.getIdStatoNotifica() != null ? dto.getIdStatoNotifica() : notifica.getIdStatoNotifica());
            map.put("idNotificaConfigurazione", notifica.getIdNotificaConfigurazione());
            map.put("idIstanza", notifica.getIdIstanza() > 0 ? notifica.getIdIstanza() : null);

            map.put("idComponenteAppPush", notifica.getIdComponenteAppPush() > 0 ? notifica.getIdComponenteAppPush() : null);
            map.put("cfDestinatario", notifica.getCfDestinatario());
            map.put("rifCanale", notifica.getRifCanale());
            map.put("rifCanaleCC", notifica.getRifCanaleCc());
            map.put("desOggetto", desOggetto);
            map.put("desMessaggio", notifica.getDesMessaggio());
            map.put("desSegnalazione", dto.getDesSegnalazione());
            map.put("dataInserimento", dto.getDataInserimento() != null ? dto.getDataInserimento() : notifica.getDataInserimento());
            map.put("dataInvio", dto.getDataInvio() != null ? dto.getDataInvio() : notifica.getDataInvio());
            map.put("nTentativiInvio", dto.getTentativiInvio() != null ? dto.getTentativiInvio() : notifica.getTentativiInvio());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            //map.put("gestUid", dto.getGestUID());
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_UPDATE_NOTIFICA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete notifica.
     *
     * @param gestUid the gest uid
     * @return int
     */
    @Override
    public Integer deleteNotifica(String gestUid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_NOTIFICA + WHERE_GESTUID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find by uid notifica dto.
     *
     * @param gestUid the gest uid
     * @return the notifica dto
     */
    public NotificaDTO findByUID(String gestUid) {
        logBeginInfo(className, gestUid);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUid", gestUid);
        return findSimpleDTOByQuery(className, QUERY_LOAD_NOTIFICA + WHERE_GESTUID, map);
    }

    /**
     * Find by id notifica dto.
     *
     * @param idNotifica the id notifica
     * @return the notifica dto
     */
    public NotificaDTO findById(Long idNotifica) {
        logBeginInfo(className, idNotifica);
        Map<String, Object> map = new HashMap<>();
        map.put("idNotifica", idNotifica);
        return findSimpleDTOByQuery(className, QUERY_LOAD_NOTIFICA + WHERE_ID_NOTIFICA, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_NOTIFICA + WHERE_ID_NOTIFICA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NotificaDTO> getRowMapper() throws SQLException {
        return new NotificaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<NotificaDTO> getExtendedRowMapper() throws SQLException {
        return new NotificaRowMapper();
    }

    /**
     * The type Notifica row mapper.
     */
    public static class NotificaRowMapper implements RowMapper<NotificaDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public NotificaRowMapper() throws SQLException {
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
        public NotificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificaDTO bean = new NotificaDTO();
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
        public void populateBean(ResultSet rs, NotificaDTO bean) throws SQLException {
            bean.setIdNotifica(rs.getLong("id_notifica"));
            bean.setIdStatoNotifica(rs.getLong("id_stato_notifica"));
            bean.setIdNotificaConfigurazione(rs.getLong("id_notifica_configurazione"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdComponenteAppPush(rs.getLong("id_componente_app_push"));
            bean.setCfDestinatario(rs.getString("cf_destinatario"));
            bean.setRifCanale(rs.getString("rif_canale"));
            bean.setRifCanaleCc(rs.getString("rif_canale_cc"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setDesMessaggio(rs.getString("des_messaggio"));
            bean.setDataInserimento(rs.getTimestamp("data_inserimento"));
            bean.setDataInvio(rs.getTimestamp("data_invio"));
            bean.setTentativiInvio(rs.getLong("n_tentativi_invio"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}