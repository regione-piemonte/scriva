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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.IstanzaCompetenzaOggettoDAO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaDTO;
import it.csi.scriva.scrivabesrv.dto.IstanzaCompetenzaOggettoDTO;
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

// TODO: Auto-generated Javadoc
/**
 * The type Istanza competenza oggetto dao.
 *
 * @author CSI PIEMONTE
 */
public class IstanzaCompetenzaOggettoDAOImpl extends ScrivaBeSrvGenericDAO<IstanzaCompetenzaOggettoDTO> implements IstanzaCompetenzaOggettoDAO {

    /** The class name. */
    private final String className = this.getClass().getSimpleName();

    /** The Constant QUERY_LOAD_IST_COMPETE_OGG. */
    private static final String QUERY_LOAD_IST_COMPETE_OGG = "SELECT *\n" +
            "FROM _replaceTableName_\n" +
            "WHERE 1=1\n";

    /** The Constant QUERY_INSERT_IST_COMPETE_OGG. */
    private static final String QUERY_INSERT_IST_COMPETE_OGG = "INSERT INTO _replaceTableName_\n" +
            "(id_istanza, id_competenza_territorio, id_oggetto_istanza,\n" +
            "gest_data_ins, gest_attore_ins, gest_data_upd, gest_attore_upd, gest_uid)\n" +
            "VALUES(:idIstanza, :idCompetenzaTerritorio, :idOggettoIstanza,\n" +
            ":gestDataIns, :gestAttoreIns, :gestDataUpd, :gestAttoreUpd, :gestUid);";

    /** The Constant QUERY_DELETE_IST_COMPETE_OGG. */
    private static final String QUERY_DELETE_IST_COMPETE_OGG = "DELETE FROM _replaceTableName_ WHERE 1=1\n";

    /** The Constant WHERE_ID_ISTANZA. */
    private static final String WHERE_ID_ISTANZA = "AND id_istanza = :idIstanza\n";

    /** The Constant WHERE_ID_COMPETE_TERRITORIO. */
    private static final String WHERE_ID_COMPETE_TERRITORIO = "AND id_competenza_territorio = :idCompetenzaTerritorio\n";

    /** The Constant WHERE_ID_OGGETTO_ISTANZA. */
    private static final String WHERE_ID_OGGETTO_ISTANZA = "AND id_oggetto_istanza = :idOggettoIstanza\n";

    /** The Constant QUERY_LOAD_LIST_COMPETENZE_COMUNI_COMUNI. */
    private static final String QUERY_LOAD_LIST_COMPETENZE_COMUNI_COMUNI = "select\n" + 
    		"distinct\n" + 
    		"stoi2.id_istanza,\n" + 
    		"sruoi.id_oggetto_istanza,\n" + 
    		"srcc.id_competenza_territorio,\n" + 
    		"srac2.flg_principale\n" + 
    		"from\n" + 
    		"scriva_r_ubica_oggetto_istanza sruoi\n" + 
    		"inner join scriva_d_comune c on\n" + 
    		"c.id_comune = sruoi.id_comune\n" + 
    		"inner join scriva_r_competenza_comune srcc on\n" + 
    		"srcc.id_comune = c.id_comune\n" + 
    		"inner join scriva_t_oggetto_istanza stoi2 on\n" + 
    		"stoi2.id_oggetto_istanza = sruoi.id_oggetto_istanza\n" + 
    		"inner join scriva_t_istanza sti2 on sti2.id_istanza = stoi2.id_istanza\n" + 
    		"left join scriva_r_adempi_competenza srac2 on srac2.id_competenza_territorio  = srcc.id_competenza_territorio and srac2.id_adempimento = sti2.id_adempimento\n" + 
    		"where\n" + 
    		"sruoi.id_oggetto_istanza in (\n" + 
    		"select\n" + 
    		"id_oggetto_istanza\n" + 
    		"from\n" + 
    		"scriva_t_oggetto_istanza stoi\n" + 
    		"where\n" + 
    		"id_istanza = :idIstanza )\n" + 
    		"and (DATE(srac2.data_inizio_validita) <= DATE(NOW()) and (srac2.data_fine_validita is null or DATE(srac2.data_fine_validita)>= DATE(NOW())))\n " +
    		"and srcc.id_competenza_territorio in\n" + 
    		"(\n" + 
    		"select\n" + 
    		"distinct srac.id_competenza_territorio\n" + 
    		"from\n" + 
    		"scriva_t_istanza sti\n" + 
    		"inner join scriva_r_adempi_competenza srac on\n" + 
    		"srac.id_adempimento = sti.id_adempimento\n" + 
    		"inner join scriva_t_competenza_territorio stct on\n" + 
    		"stct.id_competenza_territorio = srac.id_competenza_territorio\n" + 
    		"inner join scriva_d_tipo_competenza sdtc on\n" + 
    		"sdtc.id_tipo_competenza = stct.id_tipo_competenza\n" + 
    		"where\n" + 
    		"1 = 1\n" + 
    		"and sti.id_istanza = :idIstanza\n" + 
    		"and srac.ind_adesione_adempimento in (1, 4)\n" + 
    		"and sdtc.cod_tipo_competenza not like 'gest_%'\n" + 
    		"and srac.data_fine_validita is null\n" + 
    		"and stct.data_fine_validita is null\n" + 
    		"and (DATE(srac.data_inizio_validita) <= DATE(NOW()) and (srac.data_fine_validita is null or DATE(srac.data_fine_validita)>= DATE(NOW())))\n " +
    		")\n";
    
    /** The Constant QUERY_LOAD_LIST_COMPETENZE_COMUNI_PROVINCIE. */
    private static final String QUERY_LOAD_LIST_COMPETENZE_COMUNI_PROVINCIE = "select\n" + 
    		"distinct\n" + 
    		"stoi2.id_istanza,\n" + 
    		"sruoi.id_oggetto_istanza,\n" + 
    		"srcp.id_competenza_territorio,\n" + 
    		"srac2.flg_principale\n" + 
    		"from\n" + 
    		"scriva_r_ubica_oggetto_istanza sruoi\n" + 
    		"inner join scriva_d_comune c on\n" + 
    		"c.id_comune = sruoi.id_comune\n" + 
    		"inner join scriva_r_competenza_provincia srcp on\n" + 
    		"srcp.id_provincia = c.id_provincia\n" + 
    		"inner join scriva_t_oggetto_istanza stoi2 on\n" + 
    		"stoi2.id_oggetto_istanza = sruoi.id_oggetto_istanza\n" + 
    		"inner join scriva_t_istanza sti2 on sti2.id_istanza = stoi2.id_istanza\n" + 
    		"left join scriva_r_adempi_competenza srac2 on srac2.id_competenza_territorio  = srcp.id_competenza_territorio and srac2.id_adempimento = sti2.id_adempimento\n" + 
    		"where\n" + 
    		"sruoi.id_oggetto_istanza in (\n" + 
    		"select\n" + 
    		"id_oggetto_istanza\n" + 
    		"from\n" + 
    		"scriva_t_oggetto_istanza stoi\n" + 
    		"where\n" + 
    		"id_istanza = :idIstanza )\n" + 
    		"and (DATE(srac2.data_inizio_validita) <= DATE(NOW()) and (srac2.data_fine_validita is null or DATE(srac2.data_fine_validita)>= DATE(NOW())))\n " + 
    		"and srcp.id_competenza_territorio in\n" + 
    		"(\n" + 
    		"select\n" + 
    		"distinct srac.id_competenza_territorio\n" + 
    		"from\n" + 
    		"scriva_t_istanza sti\n" + 
    		"inner join scriva_r_adempi_competenza srac on\n" + 
    		"srac.id_adempimento = sti.id_adempimento\n" + 
    		"inner join scriva_t_competenza_territorio stct on\n" + 
    		"stct.id_competenza_territorio = srac.id_competenza_territorio\n" + 
    		"inner join scriva_d_tipo_competenza sdtc on\n" + 
    		"sdtc.id_tipo_competenza = stct.id_tipo_competenza\n" + 
    		"where\n" + 
    		"1 = 1\n" + 
    		"and srac.ind_adesione_adempimento in (1, 4)\n" + 
    		"and sdtc.cod_tipo_competenza not like 'gest_%'\n" + 
    		"and srac.data_fine_validita is null\n" + 
    		"and stct.data_fine_validita is null\n" + 
    		"and sti.id_istanza = :idIstanza\n" + 
    		"and (DATE(srac.data_inizio_validita) <= DATE(NOW()) and (srac.data_fine_validita is null or DATE(srac.data_fine_validita)>= DATE(NOW()))) \n" +
    		")\n"; 
    
    /** The Constant QUERY_LOAD_LIST_COMPETENZE_COMUNI_REGIONE. */
    private static final String QUERY_LOAD_LIST_COMPETENZE_COMUNI_REGIONE = "select\n" + 
    		"distinct\n" + 
    		"stoi2.id_istanza,\n" + 
    		"sruoi.id_oggetto_istanza,\n" + 
    		"srcr.id_competenza_territorio,\n" + 
    		"srac2.flg_principale\n" + 
    		"from\n" + 
    		"scriva_r_ubica_oggetto_istanza sruoi\n" + 
    		"inner join scriva_d_comune c on\n" + 
    		"c.id_comune = sruoi.id_comune\n" + 
    		"inner join scriva_d_provincia sdp on\n" + 
    		"sdp.id_provincia = c.id_provincia\n" + 
    		"inner join scriva_r_competenza_regione srcr on\n" + 
    		"srcr.id_regione = sdp.id_regione\n" + 
    		"inner join scriva_t_oggetto_istanza stoi2 on\n" + 
    		"stoi2.id_oggetto_istanza = sruoi.id_oggetto_istanza\n" + 
    		"inner join scriva_t_istanza sti2 on sti2.id_istanza = stoi2.id_istanza\n" + 
    		"left join scriva_r_adempi_competenza srac2 on srac2.id_competenza_territorio  = srcr.id_competenza_territorio and srac2.id_adempimento = sti2.id_adempimento\n" + 
    		"where\n" + 
    		"sruoi.id_oggetto_istanza in (\n" + 
    		"select\n" + 
    		"id_oggetto_istanza\n" + 
    		"from\n" + 
    		"scriva_t_oggetto_istanza stoi\n" + 
    		"where\n" + 
    		"id_istanza = :idIstanza )\n" + 
    		"and (DATE(srac2.data_inizio_validita) <= DATE(NOW()) and (srac2.data_fine_validita is null or DATE(srac2.data_fine_validita)>= DATE(NOW())))\n " +
    		"and srcr.id_competenza_territorio in\n" + 
    		"(\n" + 
    		"select\n" + 
    		"distinct srac.id_competenza_territorio\n" + 
    		"from\n" + 
    		"scriva_t_istanza sti\n" + 
    		"inner join scriva_r_adempi_competenza srac on\n" + 
    		"srac.id_adempimento = sti.id_adempimento\n" + 
    		"inner join scriva_t_competenza_territorio stct on\n" + 
    		"stct.id_competenza_territorio = srac.id_competenza_territorio\n" + 
    		"inner join scriva_d_tipo_competenza sdtc on\n" + 
    		"sdtc.id_tipo_competenza = stct.id_tipo_competenza\n" + 
    		"where\n" + 
    		"1 = 1\n" + 
    		"and srac.ind_adesione_adempimento in (1, 4)\n" + 
    		"and sdtc.cod_tipo_competenza not like 'gest_%'\n" + 
    		"and srac.data_fine_validita is null\n" + 
    		"and stct.data_fine_validita is null\n" + 
    		"and sti.id_istanza = :idIstanza\n" + 
    		"and (DATE(srac.data_inizio_validita) <= DATE(NOW()) and (srac.data_fine_validita is null or DATE(srac.data_fine_validita)>= DATE(NOW()))) \n" +
    		")\n";
    
    /** The Constant QUERY_LOAD_LIST_COMPETENZE_COMUNI. */
    private static final String QUERY_LOAD_LIST_COMPETENZE_COMUNI = "SELECT * FROM ("+
    		QUERY_LOAD_LIST_COMPETENZE_COMUNI_COMUNI +
    		"UNION\n" +
    		QUERY_LOAD_LIST_COMPETENZE_COMUNI_PROVINCIE +
    		"UNION\n" +
    		QUERY_LOAD_LIST_COMPETENZE_COMUNI_REGIONE +
    		") as COMPETENZE\n"
    		+ "order by id_competenza_territorio asc, id_oggetto_istanza asc\n";

    
    
    /** The Constant QUERY_LOAD_LIST_COMPETENZE_SN2000. */
    private static final String QUERY_LOAD_LIST_COMPETENZE_SN2000 = "select distinct\n" + 
    		"stoi.id_istanza,\n" + 
    		"sron.id_oggetto_istanza,\n" + 
    		"sron.id_competenza_territorio,\n" + 
    		"srac.flg_principale\n" + 
    		"from\n" + 
    		"scriva_t_oggetto_istanza stoi\n" + 
    		"inner join \tscriva_r_ogg_natura_2000 sron on stoi.id_oggetto_istanza = sron.id_oggetto_istanza\n" + 
    		"inner join \tscriva_r_adempi_competenza srac on (srac.id_competenza_territorio = sron.id_competenza_territorio and srac.id_adempimento = (select sti.id_adempimento  from scriva_t_istanza sti where id_istanza = :idIstanza))\n" + 
    		"where\n" + 
    		"1=1\n" + 
    		"and stoi.ind_livello = 1\n";
    
    /**
     * Load istanza competenza oggetti list.
     *
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti() {
        logBegin(className);
        return this.loadIstanzaCompetenzaOggetti(null, null);
    }

    /**
     * Load istanza competenza oggetti list.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti(Long idIstanza) {
        logBegin(className);
        return this.loadIstanzaCompetenzaOggetti(idIstanza, null);
    }

    /**
     * Load istanza competenza oggetti list.
     *
     * @param idIstanza              the id istanza
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggetti(Long idIstanza, Long idCompetenzaTerritorio) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_IST_COMPETE_OGG;
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += WHERE_ID_ISTANZA;
        }
        if (idCompetenzaTerritorio != null) {
            map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
            query += WHERE_ID_COMPETE_TERRITORIO;
        }
        logEnd(className);
        return findSimpleDTOListByQuery(className, query, map);
    }

    
    /**
     * Load istanza competenza oggetti per comuni.
     *
     * @param idIstanza the id istanza
     * @return the list
     */
    @Override
    public List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggettiPerComuni(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_LIST_COMPETENZE_COMUNI;
      
            map.put("idIstanza", idIstanza);
            
       
        List<IstanzaCompetenzaOggettoDTO> result = findListByQuery(className, query, map);
        logEnd(className);
        return result;
    }
    
    
    @Override
    public List<IstanzaCompetenzaOggettoDTO> loadIstanzaCompetenzaOggettiPerSN200(Long idIstanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_LIST_COMPETENZE_SN2000;
        query += WHERE_ID_ISTANZA;
      
            map.put("idIstanza", idIstanza);
            
       
        List<IstanzaCompetenzaOggettoDTO> result = findListByQuery(className, query, map);
        logEnd(className);
        return result;
    }
    
    /**
     * Save istanza competenza oggetto long.
     *
     * @param dto the istanza competenza oggetto
     * @return the long
     */
    
    
    
    @Override
    public void saveIstanzaCompetenzaOggetto(IstanzaCompetenzaOggettoDTO dto) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            map.put("idIstanza", dto.getIdIstanza());
            map.put("idCompetenzaTerritorio", dto.getIdCompetenzaTerritorio());
            map.put("idOggettoIstanza", dto.getIdOggettoIstanza());
            map.put("gestDataIns", now);
            map.put("gestAttoreIns", dto.getGestAttoreIns());
            map.put("gestDataUpd", now);
            map.put("gestAttoreUpd", dto.getGestAttoreUpd());
            String uid = generateGestUID(dto.getIdIstanza() + String.valueOf(dto.getIdCompetenzaTerritorio()) + dto.getIdOggettoIstanza() + now);
            map.put("gestUid", uid);

            MapSqlParameterSource params = getParameterValue(map);
            
            template.update(getQuery(QUERY_INSERT_IST_COMPETE_OGG, null, null), params);
  
            
        } catch (Exception e) {
            logError(className, e);
           
        } finally {
            logEnd(className);
        }
    }
    
    /**
     * Delete istanza competenza.
     *
     * @param idIstanza              the id istanza
     * @param idCompetenzaTerritorio the id competenza territorio
     * @param idOggettoIstanza the id oggetto istanza
     * @return the int
     */
    @Override
    public int deleteIstanzaCompetenzaOggetto(Long idIstanza, Long idCompetenzaTerritorio, Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_IST_COMPETE_OGG + WHERE_ID_ISTANZA + WHERE_ID_COMPETE_TERRITORIO + WHERE_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Delete istanza competenza.
     *
     * @param idIstanza              the id istanza
     * @param idCompetenzaTerritorio the id competenza territorio
     * @return the int
     */
    @Override
    public int deleteIstanzaCompetenzaOggetto(Long idIstanza, Long idCompetenzaTerritorio) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idIstanza", idIstanza);
            map.put("idCompetenzaTerritorio", idCompetenzaTerritorio);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_IST_COMPETE_OGG + WHERE_ID_ISTANZA + WHERE_ID_COMPETE_TERRITORIO, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }
    
  


    /**
     * Delete istanza competenza.
     *
     * @param idOggettoIstanza the id oggetto istanza
     * @return the int
     */
    @Override
    public int deleteIstanzaCompetenzaOggetto(Long idOggettoIstanza) {
        logBegin(className);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("idOggettoIstanza", idOggettoIstanza);
            MapSqlParameterSource params = getParameterValue(map);
            return template.update(getQuery(QUERY_DELETE_IST_COMPETE_OGG + WHERE_ID_OGGETTO_ISTANZA, null, null), params);
        } catch (Exception e) {
            logError(className, e);
            return -1;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database.
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance.
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaCompetenzaOggettoDTO> getRowMapper() throws SQLException {
        return new IstanzaCompetenzaOggettoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance.
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<IstanzaCompetenzaOggettoDTO> getExtendedRowMapper() throws SQLException {
        return new IstanzaCompetenzaOggettoExtendedRowMapper();
    }

    /**
     * The type Istanza competenza oggetto row mapper.
     */
    public static class IstanzaCompetenzaOggettoRowMapper implements RowMapper<IstanzaCompetenzaOggettoDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaCompetenzaOggettoRowMapper() throws SQLException {
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
        public IstanzaCompetenzaOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaCompetenzaOggettoDTO bean = new IstanzaCompetenzaOggettoDTO();
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
        public void populateBean(ResultSet rs, IstanzaCompetenzaOggettoDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }

    
    /**
     * The Class IstanzaCompetenzaOggettoExtendedRowMapper.
     */
    public static class IstanzaCompetenzaOggettoExtendedRowMapper implements RowMapper<IstanzaCompetenzaOggettoDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public IstanzaCompetenzaOggettoExtendedRowMapper() throws SQLException {
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
        public IstanzaCompetenzaOggettoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            IstanzaCompetenzaOggettoDTO bean = new IstanzaCompetenzaOggettoDTO();
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
        public void populateBean(ResultSet rs, IstanzaCompetenzaOggettoDTO bean) throws SQLException {
            bean.setIdIstanza(rs.getLong("id_istanza"));
            bean.setIdCompetenzaTerritorio(rs.getLong("id_competenza_territorio"));
            bean.setIdOggettoIstanza(rs.getLong("id_oggetto_istanza"));
            bean.setFlgAutoritaPrincipale(rs.getBoolean("flg_principale"));

        }
    }
}