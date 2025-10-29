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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoOsservazioneDAO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Osservazione dao.
 *
 * @author CSI PIEMONTE
 */
public class StatoOsservazioneDAOImpl extends ScrivaBeSrvGenericDAO<StatoOsservazioneDTO> implements StatoOsservazioneDAO {

    private final String className = this.getClass().getSimpleName();
    private static final String QUERY_STATO_OSSERVAZIONE = "SELECT id_stato_osservazione, cod_stato_osservazione, " +
            "des_stato_osservazione FROM scriva_d_stato_osservazione"
            + " WHERE 1 =1 ";

    private static final String WHERE_COD_STATO_OSSERVAZIONE = " AND UPPER(cod_stato_osservazione) = UPPER(:codStatOsservazione) \n";

    @Override
    public List<StatoOsservazioneDTO> loadStatoOsservazioni(String codStatOsservazione) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();

        String query = QUERY_STATO_OSSERVAZIONE;

        if (codStatOsservazione != null) {
            map.put("codStatOsservazione", codStatOsservazione);
            query += WHERE_COD_STATO_OSSERVAZIONE;
        }

        return findListByQuery(className, query, map);
    }

    @Override
    public String getPrimaryKeySelect() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RowMapper<StatoOsservazioneDTO> getRowMapper() throws SQLException {
        return new StatoOsservazioneRowMapper();
    }

    @Override
    public RowMapper<?> getExtendedRowMapper() throws SQLException {
        return new StatoOsservazioneRowMapper();
    }


    public static class StatoOsservazioneRowMapper implements RowMapper<StatoOsservazioneDTO> {

        public StatoOsservazioneRowMapper() throws SQLException {
            // Instantiate class
        }

        @Override
        public StatoOsservazioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            StatoOsservazioneDTO bean = new StatoOsservazioneDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, StatoOsservazioneDTO bean) throws SQLException {

            bean.setIdStatoOsservazione(rs.getLong("id_stato_osservazione"));
            bean.setDesStatoOsservazione(rs.getString("des_stato_osservazione"));
            bean.setCodStatoOsservazione(rs.getString("cod_stato_osservazione"));

        }
    }


}