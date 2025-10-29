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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaVincoloAutorizzaDAO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaVincoloAutorizzaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoVincoloAutDTO;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;
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
 * The type Oggetto istanza vincolo autorizza dao.
 */
public class OggettoIstanzaVincoloAutorizzaDAOImpl extends ScrivaBeSrvGenericDAO<OggettoIstanzaVincoloAutorizzaDTO> implements OggettoIstanzaVincoloAutorizzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String ORDER_BY_ID = "ORDER BY srova.id_oggetto_vincolo_aut";

    private static final String WHERE_ID_OGGETTO_VINCOLO_AUT = "AND srova.id_oggetto_vincolo_aut = :idOggettoVincoloAutorizza \n";

    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND srova.id_oggetto_istanza = :idOggettoIstanza \n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ srova WHERE 1=1\n" + WHERE_ID_OGGETTO_VINCOLO_AUT;

    private static final String QUERY_OGG_VINCOLO_AUTORIZZA = "SELECT srova.*, \n" +
            "stoi.*, stoi.id_oggetto_istanza AS oggetto_istanza_id, \n" +
            "sdva.*, \n" +
            "sdtva.*\n" +
            "FROM _replaceTableName_ srova \n" + //scriva_r_ogg_vincolo_autorizza
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto_istanza = srova.id_oggetto_istanza\n" +
            "INNER JOIN scriva_d_vincolo_autorizza sdva ON sdva.id_vincolo_autorizza = srova.id_vincolo_autorizza\n" +
            "LEFT JOIN scriva_d_tipo_vincolo_aut sdtva ON sdtva.id_tipo_vincolo_aut = sdva.id_vincolo_autorizza\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_INSERT_OGG_VINCOLO_AUTORIZZA = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_vincolo_aut, id_oggetto_istanza, id_vincolo_autorizza, des_vincolo_calcolato, " +
            "des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, des_motivo_richiesta_post, " +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES (nextval('seq_scriva_r_ogg_vincolo_autorizza'), :idOggettoIstanza, :idVincoloAutorizza, :desVincoloCalcolato, " +
            ":desEnteUtente, :desEmailPecEnteUtente, :flgRichiestaPost, :desMotivoRichiestaPost, " +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_OGG_VINCOLO_AUTORIZZA = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_vincolo_aut_storico, id_oggetto_vincolo_aut, id_oggetto_istanza, id_vincolo_autorizza, \n" +
            "des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, \n" +
            "des_motivo_richiesta_post, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_ogg_vincolo_autorizza'),id_oggetto_vincolo_aut, id_oggetto_istanza, id_vincolo_autorizza, \n" +
            "des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, \n" +
            "des_motivo_richiesta_post, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_ogg_vincolo_autorizza\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_OGG_VINCOLO_AUTORIZZA = "UPDATE _replaceTableName_\n" +
            "SET id_vincolo_autorizza=:idVincoloAutorizza, des_vincolo_calcolato=:desVincoloCalcolato, des_ente_utente=:desEnteUtente,\n" +
            "des_email_pec_ente_utente=:desEmailPecEnteUtente, flg_richiesta_post=:flgRichiestaPost, des_motivo_richiesta_post=:desMotivoRichiestaPost,\n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE gest_uid=:gestUid";

    private static final String QUERY_COPY_FROM_ID_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_vincolo_aut, id_oggetto_istanza, id_vincolo_autorizza, des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, des_motivo_richiesta_post, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_r_ogg_vincolo_autorizza'), :idOggettoIstanzaNew, id_vincolo_autorizza, des_vincolo_calcolato, des_ente_utente, des_email_pec_ente_utente, flg_richiesta_post, des_motivo_richiesta_post, :gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid\n" +
            "FROM _replaceTableName_ srova \n" +
            "INNER JOIN scriva_r_adempi_vincolo_aut srava ON srava.id_vincolo_autorizza = srova.id_vincolo_autorizza\n" +
            "WHERE srova.id_oggetto_istanza = :idOggettoIstanzaToCopy\n" +
            "AND srava.id_adempimento = :idAempimento";

    private static final String QUERY_DELETE_OGG_VINCOLO_AUTORIZZA = "DELETE FROM _replaceTableName_ srova WHERE 1=1\n";

    private static final String QUERY_DELETE_OGG_VINCOLO_AUTORIZZA_BY_UID = QUERY_DELETE_OGG_VINCOLO_AUTORIZZA + "AND srova.gest_uid = :gestUid \n";

    private static final String QUERY_DELETE_OGG_VINCOLO_AUTORIZZA_BY_ID_OGGETTO_ISTANZA = QUERY_DELETE_OGG_VINCOLO_AUTORIZZA + WHERE_ID_OGGETTO_ISTANZA;

    /**
     * Load oggetti istanza vincolo autorizza list.
     *
     * @param idOggettoVincoloAutorizza the id oggetto vincolo autorizza
     * @param idOggettoIstanza          the id oggetto istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaVincoloAutorizzaExtendedDTO> loadOggettiIstanzaVincoloAutorizza(Long idOggettoVincoloAutorizza, Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_OGG_VINCOLO_AUTORIZZA;
        if (idOggettoVincoloAutorizza != null) {
            map.put("idOggettoVincoloAutorizza", idOggettoVincoloAutorizza);
            query += WHERE_ID_OGGETTO_VINCOLO_AUT;
        }
        if (idOggettoIstanza != null) {
            map.put("idOggettoIstanza", idOggettoIstanza);
            query += WHERE_ID_OGGETTO_ISTANZA;
        }
        return findListByQuery(className, query + ORDER_BY_ID, map);
    }

    /**
     * Save oggetto istanza vincolo autorizza long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveOggettoIstanzaVincoloAutorizza(OggettoIstanzaVincoloAutorizzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("idVincoloAutorizza", dto.getIdVincoloAutorizza());
            map.put("desVincoloCalcolato", dto.getDesVincoloCalcolato());
            map.put("desEnteUtente", dto.getDesEnteUtente());
            map.put("desEmailPecEnteUtente", dto.getDesEmailPecEnteUtente());
            map.put("flgRichiestaPost", dto.getFlgRichiestaPost() != null && Boolean.TRUE.equals(dto.getFlgRichiestaPost()) ? 1 : 0);
            map.put("desMotivoRichiestaPost", dto.getDesMotivoRichiestaPost());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdOggettoIstanza().toString() + dto.getIdVincoloAutorizza() + now));

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_OGG_VINCOLO_AUTORIZZA, null, null), params, keyHolder, new String[]{"id_oggetto_vincolo_aut"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_VINCOLO_AUTORIZZA), params);
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
     * Update oggetto istanza vincolo autorizza integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateOggettoIstanzaVincoloAutorizza(OggettoIstanzaVincoloAutorizzaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idOggettoVincoloAutorizza", dto.getIdOggettoVincoloAut());
            OggettoIstanzaVincoloAutorizzaDTO oggettoIstanzaVincoloAutorizzaOld = findByPK(className, map);
            if (oggettoIstanzaVincoloAutorizzaOld == null) {
                logError(className, "Record non trovato con id [" + dto.getIdOggettoVincoloAut() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(oggettoIstanzaVincoloAutorizzaOld)) {
                map.put("idVincoloAutorizza", dto.getIdVincoloAutorizza() != null ? dto.getIdVincoloAutorizza() : oggettoIstanzaVincoloAutorizzaOld.getIdVincoloAutorizza());
                map.put("desVincoloCalcolato", dto.getDesVincoloCalcolato());
                map.put("desEnteUtente", dto.getDesEnteUtente());
                map.put("desEmailPecEnteUtente", dto.getDesEmailPecEnteUtente());
                map.put("flgRichiestaPost", dto.getFlgRichiestaPost() != null && Boolean.TRUE.equals(dto.getFlgRichiestaPost()) ? 1 : 0);
                map.put("desMotivoRichiestaPost", dto.getDesMotivoRichiestaPost());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("gestUid", oggettoIstanzaVincoloAutorizzaOld.getGestUID());
                MapSqlParameterSource params = getParameterValue(map);
                returnValue = template.update(getQuery(QUERY_UPDATE_OGG_VINCOLO_AUTORIZZA, null, null), params);

                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", map.get("gestUid"));
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_VINCOLO_AUTORIZZA), params);
                }

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
     * Delete oggetto istanza vincolo autorizza integer.
     *
     * @param uid the uid
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaVincoloAutorizza(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGG_VINCOLO_AUTORIZZA_BY_UID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza vincolo autorizza by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaVincoloAutorizzaByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGG_VINCOLO_AUTORIZZA_BY_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save copy oggetto istanza vincolo autorizza long.
     *
     * @param idAdempimento          the id adempimento
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestDataIns            the gest data ins
     * @param gestAttoreIns          the gest attore ins
     * @return the long
     */
    @Override
    public Long saveCopyOggettoIstanzaVincoloAutorizza(Long idAdempimento, Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idAempimento", idAdempimento);
            map.put("idOggettoIstanzaNew", idOggettoIstanzaNew);
            map.put("idOggettoIstanzaToCopy", idOggettoIstanzaToCopy);
            map.put("gestDataIns", gestDataIns);
            map.put("gestAttoreIns", gestAttoreIns);
            map.put("gestUid", generateGestUID(String.valueOf(idOggettoIstanzaNew) + gestDataIns));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_COPY_FROM_ID_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_oggetto_vincolo_aut"});
            Number key = keyHolder.getKeyList().size() > 1 ? (Number) keyHolder.getKeyList().get(0).get("id_geo_area_ogg_istanza") : keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_VINCOLO_AUTORIZZA), params);
            }
            return key != null ? key.longValue() : null;
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
        return getQuery(QUERY_PRIMARY_KEY, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>    row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<OggettoIstanzaVincoloAutorizzaDTO> getRowMapper() throws SQLException {
        return new OggettoIstanzaVincoloAutorizzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>    extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<OggettoIstanzaVincoloAutorizzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoIstanzaVincoloAutorizzaExtendedRowMapper();
    }

    /**
     * The type Oggetto istanza area protetta extended row mapper.
     */
    public static class OggettoIstanzaVincoloAutorizzaExtendedRowMapper implements RowMapper<OggettoIstanzaVincoloAutorizzaExtendedDTO> {

        /**
         * Instantiates a new Oggetto istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaVincoloAutorizzaExtendedRowMapper() throws SQLException {
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
        public OggettoIstanzaVincoloAutorizzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaVincoloAutorizzaExtendedDTO bean = new OggettoIstanzaVincoloAutorizzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaVincoloAutorizzaExtendedDTO bean) throws SQLException {
            bean.setIdOggettoVincoloAut(rs.getLong("id_oggetto_vincolo_aut"));
            //bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));
            bean.setDesVincoloCalcolato(rs.getString("des_vincolo_calcolato"));
            bean.setDesEnteUtente(rs.getString("des_ente_utente"));
            bean.setDesEmailPecEnteUtente(rs.getString("des_email_pec_ente_utente"));
            bean.setFlgRichiestaPost(rs.getInt("flg_richiesta_post") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesMotivoRichiestaPost(rs.getString("des_motivo_richiesta_post"));
            bean.setGestUID(rs.getString("gest_uid"));

            OggettoIstanzaDTO oggettoIstanza = new OggettoIstanzaDTO();
            populateBeanOggettoIstanza(rs, oggettoIstanza);
            bean.setOggettoIstanza(oggettoIstanza);

            VincoloAutorizzaExtendedDTO vincoloAutorizza = new VincoloAutorizzaExtendedDTO();
            populateBeanVincoloAutorizza(rs, vincoloAutorizza);
            bean.setVincoloAutorizza(vincoloAutorizza);

        }

        private void populateBeanOggettoIstanza(ResultSet rs, OggettoIstanzaDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdOggetto(rs.getLong("id_oggetto"));
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdTipologiaOggetto(rs.getLong("id_tipologia_oggetto"));
            bean.setIndGeoStato(rs.getString("ind_geo_stato"));
            bean.setDenOggetto(rs.getString("den_oggetto"));
            bean.setDesOggetto(rs.getString("des_oggetto"));
            bean.setCoordinataX(rs.getBigDecimal("coordinata_x"));
            bean.setCoordinataY(rs.getBigDecimal("coordinata_y"));
            bean.setIdMasterdataOrigine(rs.getLong("id_masterdata_origine"));
            bean.setIdMasterdata(rs.getLong("id_masterdata"));
            bean.setGestUID(rs.getString("gest_uid"));
            bean.setIdIstanzaAttore(rs.getLong("id_istanza_attore"));
            bean.setIdFunzionario(rs.getLong("id_funzionario"));
            bean.setCodOggettoFonte(rs.getString("cod_oggetto_fonte"));
            bean.setCodUtenza(rs.getString("cod_utenza"));
            bean.setNoteAttoPrecedente(rs.getString("note_atto_precedente"));
        }

        private void populateBeanVincoloAutorizza(ResultSet rs, VincoloAutorizzaExtendedDTO bean) throws SQLException {
            bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));
            bean.setCodVincoloAutorizza(rs.getString("cod_vincolo_autorizza"));
            bean.setDesVincoloAutorizza(rs.getString("des_vincolo_autorizza"));
            bean.setDesRifNormativo(rs.getString("des_rif_normativo"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgModifica(rs.getInt("flg_modifica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setOrdinamentoVincoloAut(rs.getLong("ordinamento_vincolo_aut"));

            TipoVincoloAutDTO tipoVincoloAut = new TipoVincoloAutDTO();
            populateBeanTipoVincoloAut(rs, tipoVincoloAut);
            bean.setTipoVincoloAut(tipoVincoloAut);

        }

        private void populateBeanTipoVincoloAut(ResultSet rs, TipoVincoloAutDTO bean) throws SQLException {
            bean.setIdTipoVincoloAut(rs.getLong("id_tipo_vincolo_aut"));
            bean.setCodTipoVincoloAut(rs.getString("cod_tipo_vincolo_aut"));
            bean.setDesTipoVincoloAut(rs.getString("des_tipo_vincolo_aut"));
        }
    }

    /**
     * The type Oggetto istanza area protetta row mapper.
     */
    public static class OggettoIstanzaVincoloAutorizzaRowMapper implements RowMapper<OggettoIstanzaVincoloAutorizzaDTO> {

        /**
         * Instantiates a new OggettoIstanza Natura 2000 row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaVincoloAutorizzaRowMapper() throws SQLException {
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
        public OggettoIstanzaVincoloAutorizzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaVincoloAutorizzaDTO bean = new OggettoIstanzaVincoloAutorizzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaVincoloAutorizzaDTO bean) throws SQLException {
            bean.setIdOggettoVincoloAut(rs.getLong("id_oggetto_vincolo_aut"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));
            bean.setDesVincoloCalcolato(rs.getString("des_vincolo_calcolato"));
            bean.setDesEnteUtente(rs.getString("des_ente_utente"));
            bean.setDesEmailPecEnteUtente(rs.getString("des_email_pec_ente_utente"));
            bean.setFlgRichiestaPost(rs.getInt("flg_richiesta_post") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesMotivoRichiestaPost(rs.getString("des_motivo_richiesta_post"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

}