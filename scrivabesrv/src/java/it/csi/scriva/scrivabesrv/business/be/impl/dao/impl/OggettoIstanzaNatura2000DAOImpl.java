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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaNatura2000DAO;
import it.csi.scriva.scrivabesrv.dto.CompetenzaTerritorioDTO;
import it.csi.scriva.scrivabesrv.dto.ComuneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NazioneDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000DTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaNatura2000ExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.ProvinciaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.RegioneExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipoCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.TipoNatura2000DTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Oggetto istanza natura 2000 dao.
 *
 * @author CSI PIEMONTE
 */
public class OggettoIstanzaNatura2000DAOImpl extends ScrivaBeSrvGenericDAO<OggettoIstanzaNatura2000DTO> implements OggettoIstanzaNatura2000DAO {

    private final String className = this.getClass().getSimpleName();

    private static final BigDecimal MAX_NUM_DISTANZA = new BigDecimal("99999"); //Last change SCRIVA-1152

    private static final String WHERE_ID_OGGETTO_NATURA_2000 = "AND sron.id_oggetto_natura_2000 = :idOggettoNatura2000 \n";

    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND sron.id_oggetto_istanza = :idOggettoIstanza \n";

    private static final String QUERY_PRIMARY_KEY = "SELECT * FROM _replaceTableName_ sron WHERE 1=1\n" + WHERE_ID_OGGETTO_NATURA_2000;

    private static final String QUERY_OGG_NATURA_2000 = "SELECT sron.*, \n" +
            "stoi.*, stoi.id_oggetto_istanza AS oggetto_istanza_id, \n" +
            "sdtn.*, \n" +
            "stct.*, stct.id_competenza_territorio AS competenza_territorio_id, \n" +
            "sdtc.*, sdtc.id_tipo_competenza AS tipo_competenza_id, \n" +
            "sdc.*, sdc.id_comune AS comune_id, sdc.data_inizio_validita AS data_inizio_validita_sdc, sdc.data_fine_validita AS data_fine_validita_sdc, \n" +
            "sdp.*, sdp.id_provincia AS provincia_id, sdp.data_inizio_validita AS data_inizio_validita_sdp, sdp.data_fine_validita AS data_fine_validita_sdp, \n" +
            "sdr.*, sdr.id_regione AS regione_id, sdr.data_inizio_validita AS data_inizio_validita_sdr, sdr.data_fine_validita AS data_fine_validita_sdr, \n" +
            "sdn.*, sdn.id_nazione AS nazione_id, sdn.data_inizio_validita AS data_inizio_validita_sdn, sdn.data_fine_validita AS data_fine_validita_sdn \n" +
            "FROM _replaceTableName_ sron \n" +
            "INNER JOIN scriva_t_oggetto_istanza stoi ON stoi.id_oggetto_istanza = sron.id_oggetto_istanza\n" +
            "INNER JOIN scriva_d_tipo_natura_2000 sdtn ON sdtn.id_tipo_natura_2000 = sron.id_tipo_natura_2000\n" +
            "INNER JOIN scriva_t_competenza_territorio stct ON stct.id_competenza_territorio = sron.id_competenza_territorio\n" +
            "INNER JOIN scriva_d_tipo_competenza sdtc ON sdtc.id_tipo_competenza = stct.id_tipo_competenza \n" +
            "LEFT JOIN scriva_d_comune sdc ON sdc.id_comune = stct.id_comune_competenza \n" +
            "LEFT JOIN scriva_d_provincia sdp ON sdp.id_provincia = sdc.id_provincia \n" +
            "LEFT JOIN scriva_d_regione sdr ON sdr.id_regione = sdp.id_regione \n" +
            "LEFT JOIN scriva_d_nazione sdn ON sdn.id_nazione = sdr.id_nazione\n" +
            "WHERE 1 = 1\n";

    private static final String QUERY_INSERT_OGG_NATURA_2000 = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_natura_2000, id_oggetto_istanza, id_competenza_territorio, id_tipo_natura_2000, cod_amministrativo,\n" +
            "cod_gestore_fonte, des_sito_natura_2000, num_distanza, flg_ricade, des_elemento_discontinuita,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES (nextval('seq_scriva_r_ogg_natura_2000'), :idOggettoIstanza, :idCompetenzaTerritorio, :idTipoNatura2000, :codAmministrativo,\n" +
            ":codGestoreFonte, :desSitoNatura2000, :numDistanza, :flgRicade, :desElementoDiscontinuita,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_OGG_NATURA_2000 = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_natura_2000_storico, id_oggetto_natura_2000, id_oggetto_istanza, id_competenza_territorio, \n" +
            "id_tipo_natura_2000, cod_amministrativo, cod_gestore_fonte, des_sito_natura_2000, num_distanza, flg_ricade, des_elemento_discontinuita,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_ogg_natura_2000'), id_oggetto_natura_2000, id_oggetto_istanza, id_competenza_territorio, \n" +
            "id_tipo_natura_2000, cod_amministrativo, cod_gestore_fonte, des_sito_natura_2000, num_distanza, flg_ricade, des_elemento_discontinuita,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_ogg_natura_2000\n" +
            "WHERE gest_uid = :gestUid";

    private static final String QUERY_UPDATE_OGG_NATURA_2000 = "UPDATE _replaceTableName_\n" +
            "SET id_competenza_territorio=:idCompetenzaTerritorio, id_tipo_natura_2000=:idTipoNatura2000, cod_amministrativo=:codAmministrativo,\n" +
            "cod_gestore_fonte=:codGestoreFonte, des_sito_natura_2000=:desSitoNatura2000, num_distanza=:numDistanza, flg_ricade=:flgRicade, des_elemento_discontinuita=:desElementoDiscontinuita, \n" +
            "gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd\n" +
            "WHERE gest_uid=:gestUid";

    private static final String QUERY_COPY_FROM_ID_OGGETTO_ISTANZA = "INSERT INTO _replaceTableName_\n" +
            "(id_oggetto_natura_2000, id_oggetto_istanza, id_competenza_territorio, id_tipo_natura_2000, cod_amministrativo,\n" +
            "cod_gestore_fonte, des_sito_natura_2000, num_distanza, flg_ricade, des_elemento_discontinuita,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_r_ogg_natura_2000'), :idOggettoIstanzaNew, id_competenza_territorio, id_tipo_natura_2000, cod_amministrativo,\n" +
            "cod_gestore_fonte, des_sito_natura_2000, num_distanza, flg_ricade, des_elemento_discontinuita,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataIns, :gestAttoreIns, :gestUid\n" +
            "FROM _replaceTableName_ WHERE id_oggetto_istanza = :idOggettoIstanzaToCopy";

    private static final String QUERY_DELETE_OGG_NATURA_2000 = "DELETE FROM _replaceTableName_ sron WHERE 1=1\n";

    private static final String QUERY_DELETE_OGG_NATURA_2000_BY_UID = QUERY_DELETE_OGG_NATURA_2000 + "AND sron.gest_uid = :gestUid \n";

    private static final String QUERY_DELETE_OGG_NATURA_2000_BY_ID_OGGETTO_ISTANZA = QUERY_DELETE_OGG_NATURA_2000 + WHERE_ID_OGGETTO_ISTANZA;

    /**
     * Load oggetti istanza natura 2000 list.
     *
     * @param idOggettoNatura2000 the id oggetto natura 2000
     * @param idOggettoIstanza    the id oggetto istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaNatura2000ExtendedDTO> loadOggettiIstanzaNatura2000(Long idOggettoNatura2000, Long idOggettoIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_OGG_NATURA_2000;
        if (idOggettoNatura2000 != null) {
            map.put("idOggettoNatura2000", idOggettoNatura2000);
            query += WHERE_ID_OGGETTO_NATURA_2000;
        }
        if (idOggettoIstanza != null) {
            map.put("idOggettoIstanza", idOggettoIstanza);
            query += WHERE_ID_OGGETTO_ISTANZA;
        }
        return findListByQuery(className, query, map);
    }

    /**
     * Save oggetto istanza natura 2000 long.
     *
     * @param dto the dto
     * @return the long
     */
    @Override
    public Long saveOggettoIstanzaNatura2000(OggettoIstanzaNatura2000DTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("idCompetenzaTerritorio", dto.getIdCompetenzaTerritorio());
            map.put("idTipoNatura2000", dto.getIdTipoNatura2000());
            map.put("codAmministrativo", dto.getCodAmministrativo());
            map.put("codGestoreFonte", dto.getCodGestoreFonte());
            map.put("desSitoNatura2000", dto.getDesSitoNatura2000());
            map.put("numDistanza", getNumDistanza(dto.getNumDistanza()));
            map.put("flgRicade", dto.getFlgRicade() != null && Boolean.TRUE.equals(dto.getFlgRicade()) ? 1 : 0);
            map.put("desElementoDiscontinuita", dto.getDesElementoDiscontinuita());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdOggettoIstanza() + dto.getIdCompetenzaTerritorio() + dto.getCodAmministrativo() + now));

            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_OGG_NATURA_2000, null, null), params, keyHolder, new String[]{"id_oggetto_natura_2000"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_NATURA_2000), params);
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
     * Update oggetto istanza natura 2000 integer.
     *
     * @param dto the dto
     * @return the integer
     */
    @Override
    public Integer updateOggettoIstanzaNatura2000(OggettoIstanzaNatura2000DTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();

            map.put("idOggettoNatura2000", dto.getIdOggettoNatura2000());
            OggettoIstanzaNatura2000DTO oggettoIstanzaNatura2000Old = findByPK(className, map);
            if (oggettoIstanzaNatura2000Old == null) {
                logError(className, "Record non trovato con id [" + dto.getIdOggettoNatura2000() + "]");
                return -1;
            }

            int returnValue = 1;
            if (!dto.equals(oggettoIstanzaNatura2000Old)) {
                map.put("idCompetenzaTerritorio", dto.getIdCompetenzaTerritorio() != null ? dto.getIdCompetenzaTerritorio() : oggettoIstanzaNatura2000Old.getIdCompetenzaTerritorio());
                map.put("idTipoNatura2000", dto.getIdTipoNatura2000() != null ? dto.getIdTipoNatura2000() : oggettoIstanzaNatura2000Old.getIdTipoNatura2000());
                map.put("codAmministrativo", dto.getCodAmministrativo());
                map.put("codGestoreFonte", dto.getCodGestoreFonte());
                map.put("desSitoNatura2000", dto.getDesSitoNatura2000());
                map.put("numDistanza", getNumDistanza(dto.getNumDistanza()));
                map.put("flgRicade", dto.getFlgRicade() != null && Boolean.TRUE.equals(dto.getFlgRicade()) ? 1 : 0);
                map.put("desElementoDiscontinuita", dto.getDesElementoDiscontinuita());
                map.put("gestDataUpd", now);
                map.put("gestAttoreUpd", dto.getGestAttoreUpd());
                map.put("gestUid", oggettoIstanzaNatura2000Old.getGestUID());
                MapSqlParameterSource params = getParameterValue(map);
                returnValue = template.update(getQuery(QUERY_UPDATE_OGG_NATURA_2000, null, null), params);

                if (returnValue > 0) {
                    Map<String, Object> storicoMap = new HashMap<>();
                    storicoMap.put("gestUid", map.get("gestUid"));
                    params = getParameterValue(storicoMap);
                    template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_NATURA_2000), params);
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
     * Gets num distanza.
     *
     * @param numDistanza the num distanza
     * @return the num distanza
     */
    private BigDecimal getNumDistanza(BigDecimal numDistanza) {
        if (numDistanza != null) {
            return MAX_NUM_DISTANZA.compareTo(numDistanza) < 0 ? MAX_NUM_DISTANZA : numDistanza;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Delete oggetto istanza natura 2000 integer.
     *
     * @param uid the uid
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaNatura2000(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGG_NATURA_2000_BY_UID, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete oggetto istanza natura 2000 by id oggetto istanza integer.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the integer
     */
    @Override
    public Integer deleteOggettoIstanzaNatura2000ByIdOggettoIstanza(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGG_NATURA_2000_BY_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Save copy geo area oggetto istanza long.
     *
     * @param idOggettoIstanzaNew    the id oggetto istanza new
     * @param idOggettoIstanzaToCopy the id oggetto istanza to copy
     * @param gestDataIns            the gest data ins
     * @param gestAttoreIns          the gest attore ins
     * @return the long
     */
    @Override
    public Long saveCopyOggettoIstanzaNatura2000(Long idOggettoIstanzaNew, Long idOggettoIstanzaToCopy, Date gestDataIns, String gestAttoreIns) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanzaNew", idOggettoIstanzaNew);
            map.put("idOggettoIstanzaToCopy", idOggettoIstanzaToCopy);
            map.put("gestDataIns", gestDataIns);
            map.put("gestAttoreIns", gestAttoreIns);
            map.put("gestUid", generateGestUID(String.valueOf(idOggettoIstanzaNew) + gestDataIns));
            MapSqlParameterSource params = getParameterValue(map);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_COPY_FROM_ID_OGGETTO_ISTANZA, null, null), params, keyHolder, new String[]{"id_oggetto_natura_2000"});
            Number key = keyHolder.getKeyList().size() > 1 ? (Number) keyHolder.getKeyList().get(0).get("id_oggetto_natura_2000") : keyHolder.getKey();

            if (returnValue > 0) {

                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGG_NATURA_2000), params);
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
    public RowMapper<OggettoIstanzaNatura2000DTO> getRowMapper() throws SQLException {
        return new OggettoIstanzaNatura2000RowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>    extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<OggettoIstanzaNatura2000ExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoIstanzaNatura2000ExtendedRowMapper();
    }

    /**
     * The type Oggetto istanza natura 2000 extended row mapper.
     */
    public static class OggettoIstanzaNatura2000ExtendedRowMapper implements RowMapper<OggettoIstanzaNatura2000ExtendedDTO> {

        /**
         * Instantiates a new Oggetto istanza extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaNatura2000ExtendedRowMapper() throws SQLException {
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
        public OggettoIstanzaNatura2000ExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaNatura2000ExtendedDTO bean = new OggettoIstanzaNatura2000ExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaNatura2000ExtendedDTO bean) throws SQLException {

            bean.setIdOggettoNatura2000(rs.getLong("id_oggetto_natura_2000"));
            bean.setCodAmministrativo(rs.getString("cod_amministrativo"));
            bean.setCodGestoreFonte(rs.getString("cod_gestore_fonte"));
            bean.setDesSitoNatura2000(rs.getString("des_sito_natura_2000"));
            bean.setNumDistanza(rs.getBigDecimal("num_distanza"));
            bean.setFlgRicade(rs.getInt("flg_ricade") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesElementoDiscontinuita(rs.getString("des_elemento_discontinuita"));
            bean.setGestUID(rs.getString("gest_uid"));

            OggettoIstanzaDTO oggettoIstanza = new OggettoIstanzaDTO();
            populateBeanOggettoIstanza(rs, oggettoIstanza);
            bean.setOggettoIstanza(oggettoIstanza);

            CompetenzaTerritorioDTO competenzaTerritorio = new CompetenzaTerritorioDTO();
            populateBeanCompetenzaTerritorio(rs, competenzaTerritorio);
            bean.setCompetenzaTerritorio(competenzaTerritorio);

            TipoNatura2000DTO tipoNatura2000 = new TipoNatura2000DTO();
            populateBeanTipoNatura2000(rs, tipoNatura2000);
            bean.setTipoNatura2000(tipoNatura2000);

        }

        private void populateBeanOggettoIstanza(ResultSet rs, OggettoIstanzaDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("oggetto_istanza_id"));
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

        private void populateBeanTipoNatura2000(ResultSet rs, TipoNatura2000DTO bean) throws SQLException {
            bean.setIdTipoNatura2000(rs.getLong("id_tipo_natura_2000"));
            bean.setCodTipoNatura2000(rs.getString("cod_tipo_natura_2000"));
            bean.setDesTipoNatura2000(rs.getString("des_tipo_natura_2000"));
        }

        private void populateBeanCompetenzaTerritorio(ResultSet rs, CompetenzaTerritorioDTO bean) throws SQLException {
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));

            TipoCompetenzaDTO tipoCompetenza = new TipoCompetenzaDTO();
            populateBeanTipoCompetenza(rs, tipoCompetenza);
            //bean.setTipoCompetenza(tipoCompetenza);
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));

            ComuneExtendedDTO comuneCompetenza = new ComuneExtendedDTO();
            populateBeanComuneCompetenza(rs, comuneCompetenza);
            //bean.setComuneCompetenza(comuneCompetenza);
            bean.setIdComuneCompetenza(rs.getLong("id_comune_competenza"));

            bean.setCodCompetenzaTerritorio(rs.getString("cod_competenza_territorio"));
            bean.setDesCompetenzaTerritorio(rs.getString("des_competenza_territorio"));
            bean.setDesCompetenzaTerritorioEstesa(rs.getString("des_competenza_territorio_estesa"));
            bean.setCodSuap(rs.getString("cod_suap"));
            bean.setIndirizzoCompetenza(rs.getString("indirizzo_competenza"));
            bean.setNumCivicoIndirizzo(rs.getString("num_civico_indirizzo"));
            bean.setCapCompetenza(rs.getString("cap_competenza"));
            bean.setDesEmailPec(rs.getString("des_pec"));
            bean.setDesEmail(rs.getString("des_email"));
            bean.setOrario(rs.getString("orario"));
            bean.setSitoWeb(rs.getString("sito_web"));
            //bean.setResponsabile(rs.getString("responsabile"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setCodIpa(rs.getString("cod_ipa"));
            bean.setNumCellulare(rs.getString("num_cellulare"));
            bean.setDesServizioApplicativo(rs.getString("des_servizio_applicativo"));
            bean.setNumDimensionePec(rs.getString("num_dimensione_pec"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanTipoCompetenza(ResultSet rs, TipoCompetenzaDTO bean) throws SQLException {
            bean.setIdTipoCompetenza(rs.getLong("tipo_competenza_id"));
            bean.setCodTipoCompetenza(rs.getString("cod_tipo_competenza"));
            bean.setDesTipoCompetenza(rs.getString("des_tipo_competenza"));
            bean.setDesTipoCompetenzaEstesa(rs.getString("des_tipo_competenza_estesa"));
            bean.setOrdinamentoTipoCompetenza(rs.getInt("ordinamento_tipo_competenza"));
        }

        private void populateBeanComuneCompetenza(ResultSet rs, ComuneExtendedDTO bean) throws SQLException {
            String prefix = "_sdc";
            bean.setIdComune(rs.getLong("comune_id"));
            bean.setCodIstatComune(rs.getString("cod_istat_comune"));
            bean.setCodBelfioreComune(rs.getString("cod_belfiore_comune"));
            bean.setDenomComune(rs.getString("denom_comune"));

            ProvinciaExtendedDTO provincia = new ProvinciaExtendedDTO();
            populateBeanProvincia(rs, provincia);
            bean.setProvincia(provincia);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdComune(rs.getLong("dt_id_comune"));
            bean.setDtIdComunePrev(rs.getLong("dt_id_comune_prev"));
            bean.setDtIdComuneNext(rs.getLong("dt_id_comune_next"));
            bean.setCapComune(rs.getString("cap_comune"));
        }

        private void populateBeanProvincia(ResultSet rs, ProvinciaExtendedDTO bean) throws SQLException {
            final String prefix = "_sdp";
            bean.setIdProvincia(rs.getLong("provincia_id"));
            bean.setCodProvincia(rs.getString("cod_provincia"));
            bean.setDenomProvincia(rs.getString("denom_provincia"));
            bean.setSiglaProvincia(rs.getString("sigla_provincia"));

            RegioneExtendedDTO regione = new RegioneExtendedDTO();
            populateBeanRegione(rs, regione);
            bean.setRegione(regione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanRegione(ResultSet rs, RegioneExtendedDTO bean) throws SQLException {
            final String prefix = "_sdr";
            bean.setIdRegione(rs.getLong("regione_id"));
            bean.setCodRegione(rs.getString("cod_regione"));
            bean.setDenomRegione(rs.getString("denom_regione"));

            NazioneDTO nazione = new NazioneDTO();
            populateBeanNazione(rs, nazione);
            bean.setNazione(nazione);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
        }

        private void populateBeanNazione(ResultSet rs, NazioneDTO bean) throws SQLException {
            final String prefix = "_sdn";
            bean.setIdNazione(rs.getLong("nazione_id"));
            bean.setCodIstatNazione(rs.getString("cod_istat_nazione"));
            bean.setCodBelfioreNazione(rs.getString("cod_belfiore_nazione"));
            bean.setDenomNazione(rs.getString("denom_nazione"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita" + prefix));
            bean.setDataFineValidita(rs.getDate("data_fine_validita" + prefix));
            bean.setDtIdStato(rs.getLong("dt_id_stato"));
            bean.setDtIdStatoPrev(rs.getLong("dt_id_stato_prev"));
            bean.setDtIdStatoNext(rs.getLong("dt_id_stato_next"));
            bean.setIdOrigine(rs.getLong("id_origine"));
            bean.setCodIso2(rs.getString("cod_iso2"));
        }

    }

    /**
     * The type Oggetto istanza natura 2000 row mapper.
     */
    public static class OggettoIstanzaNatura2000RowMapper implements RowMapper<OggettoIstanzaNatura2000DTO> {

        /**
         * Instantiates a new OggettoIstanza Natura 2000 row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaNatura2000RowMapper() throws SQLException {
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
        public OggettoIstanzaNatura2000DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaNatura2000DTO bean = new OggettoIstanzaNatura2000DTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaNatura2000DTO bean) throws SQLException {
            bean.setIdOggettoNatura2000(rs.getLong("id_oggetto_natura_2000"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIdTipoNatura2000(rs.getLong("id_tipo_natura_2000"));
            bean.setCodAmministrativo(rs.getString("cod_amministrativo"));
            bean.setCodGestoreFonte(rs.getString("cod_gestore_fonte"));
            bean.setDesSitoNatura2000(rs.getString("des_sito_natura_2000"));
            bean.setNumDistanza(rs.getBigDecimal("num_distanza"));
            bean.setFlgRicade(rs.getInt("flg_ricade") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDesElementoDiscontinuita(rs.getString("des_elemento_discontinuita"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }


}