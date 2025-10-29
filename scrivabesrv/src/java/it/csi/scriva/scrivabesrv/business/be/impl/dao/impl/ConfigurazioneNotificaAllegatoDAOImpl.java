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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.ConfigurazioneNotificaAllegatoDAO;
import it.csi.scriva.scrivabesrv.dto.ConfigurazioneNotificaAllegatoDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Configurazione notifica allegato dao.
 *
 * @author CSI PIEMONTE
 */
public class ConfigurazioneNotificaAllegatoDAOImpl extends ScrivaBeSrvGenericDAO<ConfigurazioneNotificaAllegatoDTO> implements ConfigurazioneNotificaAllegatoDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ATTIVI = "AND (DATE(srnac.data_inizio) <= DATE(NOW()) AND (srnac.data_fine IS NULL OR DATE(srnac.data_fine)>= DATE(NOW())))\n";

    private static final String WHERE_ID_NOTIFICA_CONFIG = "AND id_notifica_configurazione = :idNotificaConfigurazione\n";

    private static final String WHERE_ID_NOTIFICA_CONFIG_LIST = "AND id_notifica_configurazione IN (:idNotificaConfigurazioneList)\n";

    private static final String QUERY_LOAD_NOTIFICA_ALLEGATO_CONFIG = "SELECT *\n" +
            "FROM _replaceTableName_ srnac \n" +
            "WHERE 1 = 1\n";

    /**
     * Load configurazioni notifica allegato list.
     *
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioniNotificaAllegato() {
        logBegin(className);
        return loadConfigurazioneNotificaAllegato(null, null, Boolean.TRUE);
    }

    /**
     * Load configurazione notifica allegato list.
     *
     * @param idNotificaConfigurazione the id notifica configurazione
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioneNotificaAllegato(Long idNotificaConfigurazione) {
        return loadConfigurazioneNotificaAllegato(idNotificaConfigurazione, null, Boolean.TRUE);
    }

    /**
     * Load configurazione notifica allegato list.
     *
     * @param idNotificaConfigurazioneList the id notifica configurazione list
     * @return the list
     */
    @Override
    public List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioneNotificaAllegato(List<Long> idNotificaConfigurazioneList) {
        return loadConfigurazioneNotificaAllegato(null, idNotificaConfigurazioneList, Boolean.TRUE);
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    @Override
    public String getPrimaryKeySelect() {
        return null;
    }

    private List<ConfigurazioneNotificaAllegatoDTO> loadConfigurazioneNotificaAllegato(Long idNotificaConfigurazione, List<Long> idNotificaConfigurazioneList, Boolean flgAttivi) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_NOTIFICA_ALLEGATO_CONFIG + (Boolean.TRUE.equals(flgAttivi) ? WHERE_ATTIVI : "");

        if (idNotificaConfigurazione != null) {
            map.put("idNotificaConfigurazione", idNotificaConfigurazione);
            query += WHERE_ID_NOTIFICA_CONFIG;
        } else if (CollectionUtils.isNotEmpty(idNotificaConfigurazioneList)) {
            map.put("idNotificaConfigurazioneList", idNotificaConfigurazioneList);
            query += WHERE_ID_NOTIFICA_CONFIG_LIST;
        }
        logEnd(className);
        return findListByQuery(className, query, map);
    }


    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigurazioneNotificaAllegatoDTO> getRowMapper() throws SQLException {
        return new ConfigurazioneNotificaAllegatoRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<ConfigurazioneNotificaAllegatoDTO> getExtendedRowMapper() throws SQLException {
        return new ConfigurazioneNotificaAllegatoRowMapper();
    }

    public static class ConfigurazioneNotificaAllegatoRowMapper implements RowMapper<ConfigurazioneNotificaAllegatoDTO> {

        /**
         * Instantiates a new Tipo notifica evento row mapper.
         *
         * @throws SQLException the sql exception
         */
        public ConfigurazioneNotificaAllegatoRowMapper() throws SQLException {
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
        public ConfigurazioneNotificaAllegatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConfigurazioneNotificaAllegatoDTO bean = new ConfigurazioneNotificaAllegatoDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, ConfigurazioneNotificaAllegatoDTO bean) throws SQLException {
            bean.setIdNotificaConfigAllegato(rs.getLong("id_notifica_config_allegato"));
            bean.setIdNotificaConfigurazione(rs.getLong("id_notifica_configurazione"));
            bean.setNotificaCodTipologiaAllegato(rs.getString("notifica_cod_tipologia_allegato"));
            bean.setNotificaCodCategoriaAllegato(rs.getString("notifica_cod_categoria_allegato"));
            bean.setNotificaCodClasseAllegato(rs.getString("notifica_cod_classe_allegato"));
            bean.setIndAllegaFigli(rs.getString("ind_allega_figli"));
            bean.setIndAllegaPadre(rs.getString("ind_allega_padre"));
            bean.setFlgPubblicati(rs.getInt("flg_pubblicati") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgAllegatoRifProtocollo(rs.getInt("flg_allegato_rif_protocollo") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setFlgUltimoDoc(rs.getInt("flg_ultimo_doc") > 0 ? Boolean.TRUE : Boolean.FALSE);
            bean.setDataInizio(rs.getDate("data_inizio"));
            bean.setDataFine(rs.getDate("data_fine"));
            bean.setGestDataIns(rs.getTimestamp("gest_data_ins"));
            bean.setGestAttoreIns(rs.getString("gest_attore_ins"));
            bean.setGestDataUpd(rs.getTimestamp("gest_data_upd"));
            bean.setGestAttoreUpd(rs.getString("gest_attore_upd"));
            bean.setGestUID(rs.getString("gest_uid"));
        }
    }


}