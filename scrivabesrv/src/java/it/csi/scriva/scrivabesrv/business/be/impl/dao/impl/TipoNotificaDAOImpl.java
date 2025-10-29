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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.TipoNotificaDAO;
import it.csi.scriva.scrivabesrv.dto.TipoNotificaDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Tipo notifica dao.
 *
 * @author CSI PIEMONTE
 */
public class TipoNotificaDAOImpl extends ScrivaBeSrvGenericDAO<TipoNotificaDTO> implements TipoNotificaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_TIPO_NOTIFICA = "AND id_tipo_notifica = :idTipoNotifica\n";

    private static final String WHERE_COD_TIPO_NOTIFICA = "AND UPPER(cod_tipo_notifica) = UPPER(:codTipoNotifica)\n";

    private static final String WHERE_ATTIVI = "AND (DATE(data_inizio) <= DATE(NOW()) AND (data_fine IS NULL OR DATE(data_fine)>= DATE(NOW())))\n";

    private static final String QUERY_LOAD_TIPI_NOTIFICA = "SELECT * FROM _replaceTableName_ WHERE 1=1\n";

    /**
     * Load tipi messaggio list.
     *
     * @return List<TipoNotificaDTO>     list
     */
    @Override
    public List<TipoNotificaDTO> loadTipiNotifica() {
        logBegin(className);
        return loadTipoNotifica(null, null, Boolean.FALSE);
    }

    /**
     * Load tipo messaggio list.
     *
     * @param idTipoNotifica the id tipo notifica
     * @return List<TipoNotificaDTO>     list
     */
    @Override
    public List<TipoNotificaDTO> loadTipoNotifica(Long idTipoNotifica) {
        logBegin(className);
        return loadTipoNotifica(idTipoNotifica, null, Boolean.TRUE);
    }

    /**
     * Load tipo messaggio by code list.
     *
     * @param codTipoNotifica the cod tipo notifica
     * @return List<TipoNotificaDTO>     list
     */
    @Override
    public List<TipoNotificaDTO> loadTipoNotificaByCode(String codTipoNotifica) {
        logBegin(className);
        return loadTipoNotifica(null, codTipoNotifica, Boolean.TRUE);
    }

    /**
     * Load tipo notifica list.
     *
     * @param idTipoNotifica  the id tipo notifica
     * @param codTipoNotifica the cod tipo notifica
     * @param flgAttivi       the flg attivi
     * @return the list
     */
    private List<TipoNotificaDTO> loadTipoNotifica(Long idTipoNotifica, String codTipoNotifica, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_TIPI_NOTIFICA + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");
        if (idTipoNotifica != null) {
            map.put("idTipoNotifica", idTipoNotifica);
            query += WHERE_ID_TIPO_NOTIFICA;
        }
        if (StringUtils.isNotBlank(codTipoNotifica)) {
            map.put("codTipoNotifica", codTipoNotifica);
            query += WHERE_COD_TIPO_NOTIFICA;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return getQuery(QUERY_LOAD_TIPI_NOTIFICA + WHERE_ID_TIPO_NOTIFICA, null, null);
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNotificaDTO> getRowMapper() throws SQLException {
        return new TipoNotificaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<TipoNotificaDTO> getExtendedRowMapper() throws SQLException {
        return new TipoNotificaRowMapper();
    }

    /**
     * The type Tipo notifica row mapper.
     */
    public static class TipoNotificaRowMapper implements RowMapper<TipoNotificaDTO> {

        /**
         * Instantiates a new Tipo notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
        public TipoNotificaRowMapper() throws SQLException {
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
        public TipoNotificaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipoNotificaDTO bean = new TipoNotificaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, TipoNotificaDTO bean) throws SQLException {
            bean.setIdTipoNotifica(rs.getLong("id_tipo_notifica"));
            bean.setCodTipoNotifica(rs.getString("cod_tipo_notifica"));
            bean.setDesTipoNotifica(rs.getString("des_tipo_notifica"));
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
        }
    }
}