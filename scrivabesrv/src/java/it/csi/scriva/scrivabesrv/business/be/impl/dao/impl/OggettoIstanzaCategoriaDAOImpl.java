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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.OggettoIstanzaCategoriaDAO;
import it.csi.scriva.scrivabesrv.dto.CategoriaOggettoExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.NaturaOggettoDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaDTO;
import it.csi.scriva.scrivabesrv.dto.OggettoIstanzaCategoriaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TipologiaOggettoExtendedDTO;

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
 * The type Oggetto istanza categoria dao.
 */
public class OggettoIstanzaCategoriaDAOImpl extends ScrivaBeSrvGenericDAO<OggettoIstanzaCategoriaDTO> implements OggettoIstanzaCategoriaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_COMPONENTE_APP = "AND sdco.ind_visibile LIKE '%'||:componenteApp||'%' ";
    
    private static final String WHERE_COMPONENTE_APP_ADEMPI_CAT_OGG = "AND sraco.ind_visibile LIKE '%'||:componenteApp||'%' ";

    private static final String WHERE_CATEGORIA_OGGETTO = "AND sroic.id_categoria_oggetto = :idCategoriaOggetto ";

    private static final String WHERE_DATA_FINE_VALIDITA_NULL = "AND sroic.data_fine_validita IS NULL ";

    private static final String ORDER_BY_ORD_CATEGORIA_OGGETTO = "ORDER BY sdco.ordinamento_categoria_oggetto ASC";
    
    private static final String ORDER_BY_ORD_ADEMPI_CAT_OGG = " ORDER BY sraco.ordinamento_adempi_cat_ogg";

    private static final String QUERY_OGGETTI_ISTANZA_CATEGORIE = "SELECT * FROM _replaceTableName_ sroic WHERE 1=1 ";

    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA = "SELECT sroic.*, \n" +
            "sdco.*, sdco.id_categoria_oggetto AS categoria_oggetto_id \n" +
            "FROM _replaceTableName_ sroic \n" +
            "INNER JOIN scriva_d_categoria_oggetto sdco ON sdco.id_categoria_oggetto = sroic.id_categoria_oggetto \n" +
            "WHERE 1 = 1 \n";
    
    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_REV = "SELECT sroic.*, \n" +
            "sdco.*, sdco.id_categoria_oggetto AS categoria_oggetto_id \n" +
            "FROM _replaceTableName_ sroic \n" +
            "inner join scriva_t_oggetto_istanza stoi on stoi.id_oggetto_istanza  = sroic.id_oggetto_istanza \n" + 
            "inner join scriva_t_istanza ist on ist.id_istanza  = stoi.id_istanza \n" + 
            "INNER JOIN scriva_d_categoria_oggetto sdco ON sdco.id_categoria_oggetto = sroic.id_categoria_oggetto \n" + 
            "left outer join scriva_r_adempi_categoria_ogg sraco on sraco.id_categoria_oggetto  = sroic.id_categoria_oggetto and sraco.id_adempimento  = ist.id_adempimento \n" + 
            "WHERE 1 = 1";
    
    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA = QUERY_OGGETTO_ISTANZA_CATEGORIA + WHERE_DATA_FINE_VALIDITA_NULL + "AND sroic.id_oggetto_istanza = :idOggettoIstanza ";

    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA_REV = QUERY_OGGETTO_ISTANZA_CATEGORIA_REV  + "AND sroic.id_oggetto_istanza = :idOggettoIstanza ";
    
    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_ISTANZA = QUERY_OGGETTO_ISTANZA_CATEGORIA + WHERE_DATA_FINE_VALIDITA_NULL +
            "AND sroic.id_oggetto_istanza IN (\n" +
            "SELECT id_oggetto_istanza FROM scriva_t_oggetto_istanza WHERE id_istanza = :idIstanza\n" +
            ")\n";

    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_UID_OGGETTO_ISTANZA = QUERY_OGGETTO_ISTANZA_CATEGORIA + "AND sroic.gest_uid = :gestUid ";

    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA_CATEGORIA = QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA + WHERE_DATA_FINE_VALIDITA_NULL + WHERE_CATEGORIA_OGGETTO;

    private static final String QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_CATEGORIA_OGGETTO = QUERY_OGGETTO_ISTANZA_CATEGORIA + WHERE_CATEGORIA_OGGETTO;

    private static final String QUERY_INSERT_OGGETTO_ISTANZA_CATEGORIA = "INSERT INTO _replaceTableName_" +
            "(id_oggetto_istanza, id_categoria_oggetto, data_inizio_validita, data_fine_validita, flg_cat_nuovo_oggetto, flg_cat_modifica_oggetto, flg_cat_principale, ordinamento_istanza_competenza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)" +
            "VALUES(:idOggettoIstanza, :idCategoriaOggetto, :dataInizioValidita, :dataFineValidita, :flgCatNuovoOggetto, :flgCatModificaOggetto, :flgCatPrincipale, :ordinamentoIstanzaCompetenza, :gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid)";

    private static final String QUERY_INSERT_STORICO_OGGETTO_ISTANZA_CATEGORIA = "INSERT INTO _replaceTableName_\n" +
            "(id_ogg_istanza_cat_storico, id_oggetto_istanza, id_categoria_oggetto, data_inizio_validita, \n" +
            "data_fine_validita, flg_cat_nuovo_oggetto, flg_cat_modifica_oggetto, flg_cat_principale, \n" +
            "ordinamento_istanza_competenza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "SELECT nextval('seq_scriva_s_ogg_istanza_categoria') ,id_oggetto_istanza, id_categoria_oggetto, data_inizio_validita, data_fine_validita, \n" +
            "flg_cat_nuovo_oggetto, flg_cat_modifica_oggetto, flg_cat_principale, \n" +
            "ordinamento_istanza_competenza, gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid\n" +
            "FROM scriva_r_ogg_istanza_categoria\n" +
            "WHERE gest_uid = :gestUid ";

    private static final String QUERY_UPDATE_OGGETTO_ISTANZA_CATEGORIA = "UPDATE _replaceTableName_ " +
            "SET data_inizio_validita=:dataInizioValidita, data_fine_validita=:dataFineValidita, " +
            "flg_cat_nuovo_oggetto=:flgCatNuovoOggetto, flg_cat_modifica_oggetto=:flgCatModificaOggetto, flg_cat_principale=:flgCatPrincipale, ordinamento_istanza_competenza=:ordinamentoIstanzaCompetenza, " +
            "gest_data_ins=:gestDataIns, gest_attore_ins=:gestAttoreIns, gest_data_upd=:gestDataUpd, gest_attore_upd=:gestAttoreUpd, gest_uid=:gestUid " +
            "WHERE id_oggetto_istanza=:idOggettoIstanza AND id_categoria_oggetto=:idCategoriaOggetto";

    private static final String QUERY_DELETE_OGGETTO_ISTANZA_CATEGORIA = "DELETE FROM _replaceTableName_ WHERE gest_uid=:gestUid";

    private static final String QUERY_DELETE_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA = "DELETE FROM _replaceTableName_ WHERE id_oggetto_istanza=:idOggettoIstanza";

    /**
     * @return List<OggettoIstanzaCategoriaExtendedDTO>
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettiIstanzaCategoria() {
        logBegin(className);
        return findListByQuery(className, QUERY_OGGETTO_ISTANZA_CATEGORIA + ORDER_BY_ORD_CATEGORIA_OGGETTO, null);
    }

    /**
     * @param idOggettoIstanza idOggettoIstanza
     * @return List<OggettoIstanzaCategoriaExtendedDTO>
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdOggettoIstanza(Long idOggettoIstanza,String componenteApp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        //String query=QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA;
        String query=QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA_REV;
        map.put("idOggettoIstanza", idOggettoIstanza);
        map.put("componenteApp", componenteApp);
        if(StringUtils.isNotBlank(componenteApp)) {
        	query+=WHERE_COMPONENTE_APP_ADEMPI_CAT_OGG;
        }
        return findListByQuery(className, query + ORDER_BY_ORD_ADEMPI_CAT_OGG, map);
    }

    /**
     * Load oggetto istanza categoria by id istanza list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdIstanza(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idIstanza", idIstanza);
        return findListByQuery(className, QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_ISTANZA + ORDER_BY_ORD_CATEGORIA_OGGETTO, map);
    }

    /**
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<OggettoIstanzaCategoriaExtendedDTO>
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdCategoriaOggetto(Long idCategoriaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idCategoriaOggetto", idCategoriaOggetto);
        return findListByQuery(className, QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA + ORDER_BY_ORD_CATEGORIA_OGGETTO, map);
    }

    /**
     * @param idOggettoIstanza   idOggettoIstanza
     * @param idCategoriaOggetto idCategoriaOggetto
     * @return List<OggettoIstanzaCategoriaExtendedDTO>
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByIdOggettoIstanzaIdCategoria(Long idOggettoIstanza, Long idCategoriaOggetto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", idOggettoIstanza);
        map.put("idCategoriaOggetto", idCategoriaOggetto);
        return findListByQuery(className, QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA_CATEGORIA, map);

    }

    /**
     * Load oggetto istanza categoria by uid list.
     *
     * @param uid the uid
     * @return the list
     */
    @Override
    public List<OggettoIstanzaCategoriaExtendedDTO> loadOggettoIstanzaCategoriaByUID(String uid) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("gestUid", uid);
        return findListByQuery(className, QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_UID_OGGETTO_ISTANZA, map);
    }

    /**
     * @param dto SoggettoIstanzaDTO
     * @return id record salvato
     */
    @Override
    public Long saveOggettoIstanzaCategoria(OggettoIstanzaCategoriaDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Date now = Calendar.getInstance().getTime();
            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("idCategoriaOggetto", dto.getIdCategoriaOggetto());
            map.put("dataInizioValidita", now);
            map.put("dataFineValidita", dto.getDataFineValidita());
            map.put("flgCatNuovoOggetto", Boolean.TRUE.equals(dto.getFlgCatNuovoOggetto()) ? 1 : 0);
            map.put("flgCatModificaOggetto", Boolean.TRUE.equals(dto.getFlgCatModificaOggetto()) ? 1 : 0);
            map.put("flgCatPrincipale", Boolean.TRUE.equals(dto.getFlgCatPrincipale()) ? 1 : 0);
            map.put("ordinamentoIstanzaCompetenza", dto.getOrdinamentoIstanzaCompetenza());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreIns());
            map.put("gestUid", generateGestUID(dto.getIdOggettoIstanza().toString() + dto.getIdCategoriaOggetto().toString() + now));

            MapSqlParameterSource params = getParameterValue(map);

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int returnValue = template.update(getQuery(QUERY_INSERT_OGGETTO_ISTANZA_CATEGORIA, null, null), params, keyHolder, new String[]{"id_oggetto_istanza"});
            Number key = keyHolder.getKey();

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO_ISTANZA_CATEGORIA), params);
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
     * @param dto SoggettoIstanzaDTO
     * @return numero record inseriti
     */
    @Override
    public Integer updateOggettoIstanzaCategoria(OggettoIstanzaCategoriaDTO dto) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
        map.put("idCategoriaOggetto", dto.getIdCategoriaOggetto());
        try {
            int returnValue = 1;
            OggettoIstanzaCategoriaDTO oggettoIstanzaCategoriaOld = this.findByPK(map);
            if (null == oggettoIstanzaCategoriaOld) {
                logError(className, "Record non trovato con id [" + dto.getIdOggettoIstanza() + "]");
                return -1;
            }

            Date now = Calendar.getInstance().getTime();
            map.put("dataInizioValidita", dto.getDataInizioValidita() == null ? oggettoIstanzaCategoriaOld.getDataInizioValidita() : dto.getDataInizioValidita());
            map.put("dataFineValidita", dto.getDataFineValidita());
            map.put("flgCatNuovoOggetto", Boolean.TRUE.equals(dto.getFlgCatNuovoOggetto()) ? 1 : 0);
            map.put("flgCatModificaOggetto", Boolean.TRUE.equals(dto.getFlgCatModificaOggetto()) ? 1 : 0);
            map.put("flgCatPrincipale", Boolean.TRUE.equals(dto.getFlgCatPrincipale()) ? 1 : 0);
            map.put("ordinamentoIstanzaCompetenza", dto.getOrdinamentoIstanzaCompetenza());
            map.put("gestDataIns", oggettoIstanzaCategoriaOld.getGestDataIns());
            map.put("gestAttoreIns", oggettoIstanzaCategoriaOld.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            map.put("gestUid", oggettoIstanzaCategoriaOld.getGestUID());

            MapSqlParameterSource params = getParameterValue(map);
            returnValue = template.update(getQuery(QUERY_UPDATE_OGGETTO_ISTANZA_CATEGORIA, null, null), params);

            if (returnValue > 0) {
                Map<String, Object> storicoMap = new HashMap<>();
                storicoMap.put("gestUid", map.get("gestUid"));
                params = getParameterValue(storicoMap);
                template.update(getQueryStorico(QUERY_INSERT_STORICO_OGGETTO_ISTANZA_CATEGORIA), params);
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
     * @param uid uidOggettoIstanzaCategoria
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoIstanzaCategoria(String uid) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("gestUid", uid);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTO_ISTANZA_CATEGORIA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * @param uid uidOggettoIstanzaCategoria
     * @return numero record cancellati
     */
    @Override
    public Integer deleteOggettoIstanzaCategoria(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA, null, null), params);
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
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_OGGETTO_ISTANZA_CATEGORIA_BY_ID_OGGETTO_ISTANZA_CATEGORIA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<OggettoIstanzaCategoriaDTO> getRowMapper() throws SQLException {
        return new OggettoIstanzaCategoriaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>
     */
    @Override
    public RowMapper<OggettoIstanzaCategoriaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new OggettoIstanzaCategoriaExtendedRowMapper();
    }

    /**
     * The type Oggetto istanza categoria row mapper.
     */
    public static class OggettoIstanzaCategoriaRowMapper implements RowMapper<OggettoIstanzaCategoriaDTO> {

        /**
         * Instantiates a new Oggetto istanza categoria row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaCategoriaRowMapper() throws SQLException {
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
        public OggettoIstanzaCategoriaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaCategoriaDTO bean = new OggettoIstanzaCategoriaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaCategoriaDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setIdCategoriaOggetto(rs.getLong("id_categoria_oggetto"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgCatNuovoOggetto(rs.getInt("flg_cat_nuovo_oggetto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCatModificaOggetto(rs.getInt("flg_cat_modifica_oggetto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCatPrincipale(rs.getInt("flg_cat_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoIstanzaCompetenza(rs.getInt("ordinamento_istanza_competenza"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    /**
     * The type Oggetto istanza categoria extended row mapper.
     */
    public static class OggettoIstanzaCategoriaExtendedRowMapper implements RowMapper<OggettoIstanzaCategoriaExtendedDTO> {

        /**
         * Instantiates a new Oggetto istanza categoria extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public OggettoIstanzaCategoriaExtendedRowMapper() throws SQLException {
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
        public OggettoIstanzaCategoriaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            OggettoIstanzaCategoriaExtendedDTO bean = new OggettoIstanzaCategoriaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, OggettoIstanzaCategoriaExtendedDTO bean) throws SQLException {
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));

            CategoriaOggettoExtendedDTO categoriaOggetto = new CategoriaOggettoExtendedDTO();
            populateBeanCategoriaOggetto(rs, categoriaOggetto);
            bean.setCategoriaOggetto(categoriaOggetto);

            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgCatNuovoOggetto(rs.getInt("flg_cat_nuovo_oggetto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCatModificaOggetto(rs.getInt("flg_cat_modifica_oggetto") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgCatPrincipale(rs.getInt("flg_cat_principale") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setOrdinamentoIstanzaCompetenza(rs.getInt("ordinamento_istanza_competenza"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }

        private void populateBeanCategoriaOggetto(ResultSet rs, CategoriaOggettoExtendedDTO bean) throws SQLException {
            bean.setIdCategoriaOggetto(rs.getLong("categoria_oggetto_id"));
            /*
            TipologiaOggettoExtendedDTO tipologiaOggetto = new TipologiaOggettoExtendedDTO();
            populateBeanTipologiaOggetto(rs, tipologiaOggetto);
            bean.setTipologiaOggetto(tipologiaOggetto);
            */
            bean.setCodCategoriaOggetto(rs.getString("cod_categoria_oggetto"));
            bean.setDesCategoriaOggetto(rs.getString("des_categoria_oggetto"));
            bean.setDesCategoriaOggettoEstesa(rs.getString("des_categoria_oggetto_estesa"));
            bean.setOrdinamentoCategoriaOggetto(rs.getInt("ordinamento_categoria_oggetto"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setIndVisibile(rs.getString("ind_visibile"));
        }

        private void populateBeanTipologiaOggetto(ResultSet rs, TipologiaOggettoExtendedDTO bean) throws SQLException {
            bean.setIdTipologiaOggetto(rs.getLong("tipologia_oggetto_id"));
            NaturaOggettoDTO naturaOggetto = new NaturaOggettoDTO();
            populateBeanNaturaOggetto(rs, naturaOggetto);
            bean.setNaturaOggetto(naturaOggetto);
            bean.setCodTipologiaOggetto(rs.getString("cod_tipologia_oggetto"));
            bean.setDesTipologiaOggetto(rs.getString("des_tipologia_oggetto"));
        }

        private void populateBeanNaturaOggetto(ResultSet rs, NaturaOggettoDTO bean) throws SQLException {
            bean.setIdNaturaOggetto(rs.getLong("natura_oggetto_id"));
            bean.setCodNaturaOggetto(rs.getString("cod_natura_oggetto"));
            bean.setDesNaturaOggetto(rs.getString("des_natura_oggetto"));
        }

    }

}