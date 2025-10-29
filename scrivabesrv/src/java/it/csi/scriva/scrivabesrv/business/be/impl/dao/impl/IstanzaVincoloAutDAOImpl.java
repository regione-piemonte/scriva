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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaVincoloAutDAO;
import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaVincoloAutExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoVincoloAutDTO;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;
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
 * The type Istanza vincolo aut dao.
 */
public class IstanzaVincoloAutDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaVincoloAutDTO> implements IstanzaVincoloAutDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ISTANZA = "AND sriva.id_istanza = :idIstanza\n";
    //private static final String WHERE_COD_VINCOLO = "AND sdtva.cod_tipo_vincolo_aut = :codVincolo\n ";
    private static final String WHERE_COD_VINCOLO = "AND sdva.cod_vincolo_autorizza = :codVincolo\n ";
    private static final String WHERE_ID = "AND sriva.id_istanza_vincolo_aut = :idIstanzaVincoloAut\n";
    private static final String WHERE_UID = "AND sriva.gest_uid = :gestUID\n";
    private static final String ORDER_BY_ADEMPI_VINCOLOD = "ORDER BY srava.ordinamento_adempi_vincolo\n";

    private static final String QUERY_ISTANZA_VINCOLO_AUT_BY_ID = "SELECT * FROM _replaceTableName_ sriva WHERE sriva.id_istanza_vincolo_aut=:idIstanzaVincoloAut ";

    private static final String QUERY_ISTANZA_VINCOLO_AUT = "SELECT sriva.*, sriva.gest_attore_ins AS gest_attore_ins_iv, sriva.gest_attore_upd AS gest_attore_upd_iv, sriva.gest_data_ins AS gest_data_ins_iv, sriva.gest_data_upd AS gest_data_upd_iv, sriva.gest_uid AS gest_uid_iv\n " +
            ", sdva.* \n" +
            ", sdtva.*, sdtva.id_tipo_vincolo_aut AS tipo_vincolo_aut_id \n" +
            ", srava.ordinamento_adempi_vincolo AS ordinamento \n" +
            "FROM _replaceTableName_ sriva \n" +
            "INNER JOIN scriva_t_istanza sti ON sti.id_istanza = sriva.id_istanza \n" +
            "INNER JOIN scriva_d_vincolo_autorizza sdva ON sdva.id_vincolo_autorizza = sriva.id_vincolo_autorizza \n" +
            "INNER JOIN scriva_d_tipo_vincolo_aut sdtva ON sdtva.id_tipo_vincolo_aut = sdva.id_tipo_vincolo_aut \n" +
            "INNER JOIN scriva_r_adempi_vincolo_aut srava ON srava.id_vincolo_autorizza = sdva.id_vincolo_autorizza \n" +
            "WHERE 1 = 1 \n" +
            "AND srava.id_adempimento = sti.id_adempimento \n";

    private static final String QUERY_ISTANZA_VINCOLO_AUT_BY_ID_ISTANZA = QUERY_ISTANZA_VINCOLO_AUT + WHERE_ID_ISTANZA + ORDER_BY_ADEMPI_VINCOLOD;

    private static final String QUERY_ISTANZA_VINCOLO_AUT_BY_ID_ISTANZA_VINCOLO = QUERY_ISTANZA_VINCOLO_AUT + WHERE_ID;

    private static final String QUERY_ISTANZA_VINCOLO_AUT_BY_UID = QUERY_ISTANZA_VINCOLO_AUT + WHERE_UID;

    private static final String QUERY_INSERT_ISTANZA_VINCOLO_AUT = "INSERT INTO _replaceTableName_ " +
            "(id_istanza_vincolo_aut, id_istanza, id_vincolo_autorizza, des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, des_motivo_richiesta_port, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(nextval('seq_scriva_r_istanza_vincolo_aut'), :idIstanza, :idVincoloAutorizza, :desVincoloCalcolato, :desEnteUtente, :desEmailPecEnteUtente, :flgRichiestaPost, :desMotivoRichiestaPort, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid) ";

    private static final String QUERY_UPDATE_ISTANZA_VINCOLO_AUT = "UPDATE _replaceTableName_ " +
            "SET id_istanza=:idIstanza, id_vincolo_autorizza=:idVincoloAutorizza, des_vincolo_calcolato=:desVincoloCalcolato, des_ente_utente=:desEnteUtente, " +
            "des_email_pec_ente_utente=:desEmailPecEnteUtente, flg_richiesta_post=:flgRichiestaPost, des_motivo_richiesta_port=:desMotivoRichiestaPort, gest_data_ins=:gestDataIns, gest_attore_ins=:gestAttoreIns, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd " +
            "WHERE id_istanza_vincolo_aut=:idIstanzaVincoloAut";

    private static final String QUERY_DELETE_ISTANZA_VINCOLO_AUT = "DELETE FROM _replaceTableName_ WHERE gest_uid=:gestUid ";

    private static final String QUERY_INSERT_STORICO_ISTANZA_VINCOLO_AUT = "INSERT INTO scriva.scriva_s_istanza_vincolo_aut " +
            "(id_istanza_vincolo_aut_storico, id_istanza_vincolo_aut, id_istanza, id_vincolo_autorizza, des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, des_motivo_richiesta_port, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid) " +
            "VALUES(nextval('seq_scriva_s_istanza_vincolo_aut'), :idIstanzaVincoloAut, :idIstanza, :idVincoloAutorizza, :desVincoloCalcolato, :desEnteUtente, :desEmailPecEnteUtente, :flgRichiestaPost, :desMotivoRichiestaPort, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid) ";

    /**
     * Load istanza vincolo autorizzazione by istanza list.
     *
     * @param idIstanza  the id istanza
     * @param codVincolo the cod vincolo
     * @return the list
     */
    @Override
    public List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneByIstanza(Long idIstanza, String codVincolo) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_ISTANZA_VINCOLO_AUT;
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (StringUtils.isNotBlank(codVincolo)) {
            map.put("codVincolo", codVincolo);
            query += WHERE_COD_VINCOLO;
        }

        return findListByQuery(className, query + ORDER_BY_ADEMPI_VINCOLOD, map);
    }

    /**
     * Load istanza vincolo autorizzazione by id list.
     *
     * @param idIstanzaVincoloAut the id istanza vincolo aut
     * @return the list
     */
    @Override
    public List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneById(Long idIstanzaVincoloAut) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaVincoloAut", idIstanzaVincoloAut);
        return findListByQuery(className, QUERY_ISTANZA_VINCOLO_AUT_BY_ID_ISTANZA_VINCOLO, map);
    }

    /**
     * Load istanza vincolo autorizzazione by id list.
     *
     * @param gestUID the gest uid
     * @return the list
     */
    @Override
    public List<IstanzaVincoloAutExtendedDTO> loadIstanzaVincoloAutorizzazioneById(String gestUID) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUID", gestUID);
        return findListByQuery(className, QUERY_ISTANZA_VINCOLO_AUT_BY_UID, map);
    }

    /**
     * Save istanza vincolo autorizzazione by istanza long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveIstanzaVincoloAutorizzazione(IstanzaVincoloAutDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idIstanza", dto.getIdIstanza());
            map.put("idVincoloAutorizza", dto.getIdVincoloAutorizza());
            map.put("desVincoloCalcolato", dto.getDesVincoloCalcolato());
            map.put("desEnteUtente", dto.getDesEnteUtente());
            map.put("desEmailPecEnteUtente", dto.getDesEmailPecEnteUtente());
            map.put("flgRichiestaPost", Boolean.TRUE.equals(dto.getFlgRichiestaPost()) ? 1 : 0);
            map.put("desMotivoRichiestaPort", dto.getDesMotivoRichiestaPort());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdIstanza().toString() + dto.getIdVincoloAutorizza().toString() + dto.getDesVincoloCalcolato() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_ISTANZA_VINCOLO_AUT, null, null), params, keyHolder, new String[]{"id_istanza_vincolo_aut"});
            Number key = keyHolder.getKey();

            // In fase di insert lo stato istanza è BOZZA pertanto non verrà inserita nello storico
            if (returnValue > 0) {
                map.put("idIstanzaVincoloAut", key.longValue());
                params = getParameterValue(map);
                template.update(QUERY_INSERT_STORICO_ISTANZA_VINCOLO_AUT, params);
            }

            return key.longValue();
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Update istanza vincolo autorizzazione by istanza integer.
     *
     * @param dto the istanza vincolo aut
     * @return the integer
     */
    @Override
    public Integer updateIstanzaVincoloAutorizzazione(IstanzaVincoloAutDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanzaVincoloAut", dto.getIdIstanzaVincoloAut());

        try {
            IstanzaVincoloAutDTO istanzaVincoloAutOld = this.findByPK(map);
            if (null == istanzaVincoloAutOld) {
                logError(className, "Record non trovato con id [" + dto.getIdIstanzaVincoloAut() + "]");
                return -1;
            }

            Date now = Calendar.getInstance().getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idVincoloAutorizza", dto.getIdVincoloAutorizza());
            map.put("desVincoloCalcolato", dto.getDesVincoloCalcolato());
            map.put("desEnteUtente", dto.getDesEnteUtente());
            map.put("desEmailPecEnteUtente", dto.getDesEmailPecEnteUtente());
            map.put("flgRichiestaPost", Boolean.TRUE.equals(dto.getFlgRichiestaPost()) ? 1 : 0);
            map.put("desMotivoRichiestaPort", dto.getDesMotivoRichiestaPort());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", istanzaVincoloAutOld.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", istanzaVincoloAutOld.getGestUID());

            MapSqlParameterSource params = getParameterValue(map);
            int returnValue = template.update(getQuery(QUERY_UPDATE_ISTANZA_VINCOLO_AUT, null, null), params);
            if (returnValue > 0) {
                template.update(QUERY_INSERT_STORICO_ISTANZA_VINCOLO_AUT, params);
            }
            return returnValue;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete istanza vincolo autorizzazione by istanza integer.
     *
     * @param gestUID the gest uid
     * @return the integer
     */
    @Override
    public Integer deleteIstanzaVincoloAutorizzazioneByIstanza(String gestUID) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", gestUID);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_ISTANZA_VINCOLO_AUT, null, null), params);
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
        return getQuery(QUERY_ISTANZA_VINCOLO_AUT_BY_ID, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaVincoloAutDTO> getRowMapper() throws SQLException {
        return new IstanzaVincoloAutRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaVincoloAutExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaVincoloAutExtendedRowMapper();
    }

    /**
     * The type Istanza vincolo aut row mapper.
     */
    public static class IstanzaVincoloAutRowMapper implements RowMapper<IstanzaVincoloAutDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaVincoloAutRowMapper() throws SQLException {
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
        public IstanzaVincoloAutDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaVincoloAutDTO bean = new IstanzaVincoloAutDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaVincoloAutDTO bean) throws SQLException {
            bean.setIdIstanzaVincoloAut(rs.getLong("id_istanza_vincolo_aut"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));
            bean.setDesVincoloCalcolato(rs.getString("des_vincolo_calcolato"));
            bean.setDesEnteUtente(rs.getString("des_ente_utente"));
            bean.setDesEmailPecEnteUtente(rs.getString("des_email_pec_ente_utente"));
            bean.setFlgRichiestaPost(rs.getInt("flg_richiesta_post") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesMotivoRichiestaPort(rs.getString("des_motivo_richiesta_port"));
            //bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Istanza vincolo aut extended row mapper.
     */
    public static class IstanzaVincoloAutExtendedRowMapper implements RowMapper<IstanzaVincoloAutExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaVincoloAutExtendedRowMapper() throws SQLException {
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
        public IstanzaVincoloAutExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaVincoloAutExtendedDTO bean = new IstanzaVincoloAutExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, IstanzaVincoloAutExtendedDTO bean) throws SQLException {
            bean.setIdIstanzaVincoloAut(rs.getLong("id_istanza_vincolo_aut"));
            bean.setIdIstanza(rs.getLong("id_istanza"));

            VincoloAutorizzaExtendedDTO vincoloAutorizza = new VincoloAutorizzaExtendedDTO();
            populateBeanVincoloAutorizza(rs, vincoloAutorizza);
            bean.setVincoloAutorizza(vincoloAutorizza);

            bean.setDesVincoloCalcolato(rs.getString("des_vincolo_calcolato"));
            bean.setDesEnteUtente(rs.getString("des_ente_utente"));
            bean.setDesEmailPecEnteUtente(rs.getString("des_email_pec_ente_utente"));
            bean.setFlgRichiestaPost(rs.getInt("flg_richiesta_post") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesMotivoRichiestaPort(rs.getString("des_motivo_richiesta_port"));
            //bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins_iv"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins_iv"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd_iv"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd_iv"));
            bean.setGestUID(rs.getString("gest_uid_iv"));
        }

        private void populateBeanVincoloAutorizza(ResultSet rs, VincoloAutorizzaExtendedDTO bean) throws SQLException {
            bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));

            TipoVincoloAutDTO tipoVincoloAut = new TipoVincoloAutDTO();
            populateBeanTipoVincoloAut(rs, tipoVincoloAut);
            bean.setTipoVincoloAut(tipoVincoloAut);

            bean.setCodVincoloAutorizza(rs.getString("cod_vincolo_autorizza"));
            bean.setDesVincoloAutorizza(rs.getString("des_vincolo_autorizza"));
            bean.setDesRifNormativo(rs.getString("des_rif_normativo"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgModifica(rs.getInt("flg_modifica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setOrdinamentoVincoloAut(rs.getLong("ordinamento"));
        }

        private void populateBeanTipoVincoloAut(ResultSet rs, TipoVincoloAutDTO bean) throws SQLException {
            bean.setIdTipoVincoloAut(rs.getLong("tipo_vincolo_aut_id"));
            bean.setCodTipoVincoloAut(rs.getString("cod_tipo_vincolo_aut"));
            bean.setDesTipoVincoloAut(rs.getString("des_tipo_vincolo_aut"));
        }
    }

}