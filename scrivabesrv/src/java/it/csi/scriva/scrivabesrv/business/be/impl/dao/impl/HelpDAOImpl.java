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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.HelpDAO;
import it.csi.scriva.scrivabesrv.dto.HelpDTO;
import it.csi.scriva.scrivabesrv.dto.HelpExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.LivelloHelpDTO;
import it.csi.scriva.scrivabesrv.dto.TipoHelpDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Help dao.
 *
 * @author CSI PIEMONTE
 */
public class HelpDAOImpl extends ScrivaBeSrvGenericDAO<HelpDTO> implements HelpDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_LOAD_HELP = "SELECT sdh.*, " +
            "sdth.*, sdth.id_tipo_help AS tipo_help_id, " +
            "sdlh.*, sdlh.id_livello_help AS livello_help_id " +
            "FROM scriva_d_help sdh " +
            "INNER JOIN scriva_d_tipo_help sdth ON sdh.id_tipo_help = sdth.id_tipo_help " +
            "INNER JOIN scriva_d_livello_help sdlh ON sdh.id_livello_help = sdlh.id_livello_help";

    private static final String QUERY_LOAD_HELP_BY_ID = QUERY_LOAD_HELP + " WHERE sdh.id_help = :idHelp";

    private static final String QUERY_LOAD_HELP_BY_CHIAVE = QUERY_LOAD_HELP + " WHERE sdh.chiave_help LIKE '%'||:chiaveHelp||'%'";

    /**
     * @return List<HelpExtendedDTO>
     */
    @Override
    public List<HelpExtendedDTO> loadHelp() {
        logBegin(className);
        return findListByQuery(className, QUERY_LOAD_HELP, null);
    }

    /**
     * @param idHelp idHelp
     * @return List<HelpExtendedDTO>
     */
    @Override
    public List<HelpExtendedDTO> loadHelpById(Long idHelp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idHelp", idHelp);
        return findListByQuery(className, QUERY_LOAD_HELP_BY_ID, map);
    }

    /**
     * @param chiaveHelp chiaveHelp
     * @return List<HelpExtendedDTO>
     */
    @Override
    public List<HelpExtendedDTO> loadHelpByChiave(String chiaveHelp) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("chiaveHelp", chiaveHelp);
        return findListByQuery(className, QUERY_LOAD_HELP_BY_CHIAVE, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<HelpDTO>
     */
    @Override
    public RowMapper<HelpDTO> getRowMapper() throws SQLException {
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<HelpExtendedDTO>
     */
    @Override
    public RowMapper<HelpExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new HelpExtendedRowMapper();
    }

    /**
     * The type Help extended row mapper.
     */
    public static class HelpExtendedRowMapper implements RowMapper<HelpExtendedDTO> {

        /**
         * Instantiates a new Help extended row mapper.
         *
         * @throws SQLException the sql exception
         */
        public HelpExtendedRowMapper() throws SQLException {
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
        public HelpExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            HelpExtendedDTO bean = new HelpExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, HelpExtendedDTO bean) throws SQLException {
            bean.setIdHelp(rs.getLong("id_help"));
            bean.setChiaveHelp(rs.getString("chiave_help"));
            bean.setValoreCampoHelp(rs.getString("valore_campo_help"));
            bean.setDesTestoHelp(rs.getString("des_testo_help"));
            bean.setNoteHelp(rs.getString("note_help"));

            TipoHelpDTO tipoHelp = new TipoHelpDTO();
            populateBeanTipoHelp(rs, tipoHelp);
            bean.setTipoHelp(tipoHelp);

            LivelloHelpDTO livelloHelp = new LivelloHelpDTO();
            populateBeanLivelloHelp(rs, livelloHelp);
            bean.setLivelloHelp(livelloHelp);

        }

        private void populateBeanTipoHelp(ResultSet rs, TipoHelpDTO bean) throws SQLException {
            bean.setIdTipoHelp(rs.getLong("tipo_help_id"));
            bean.setCodTipoHelp(rs.getString("cod_tipo_help"));
            bean.setDesTipoHelp(rs.getString("des_tipo_help"));
        }

        private void populateBeanLivelloHelp(ResultSet rs, LivelloHelpDTO bean) throws SQLException {
            bean.setIdLivelloHelp(rs.getLong("livello_help_id"));
            bean.setCodLivelloHelp(rs.getString("cod_livello_help"));
            bean.setDesLivellooHelp(rs.getString("des_livello_help"));
        }

    }
}