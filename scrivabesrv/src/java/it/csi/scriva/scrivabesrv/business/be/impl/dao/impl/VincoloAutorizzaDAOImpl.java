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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.VincoloAutorizzaDAO;
import it.csi.scriva.scrivabesrv.dto.TipoVincoloAutDTO;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaDTO;
import it.csi.scriva.scrivabesrv.dto.VincoloAutorizzaExtendedDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VincoloAutorizzaDAOImpl extends ScrivaBeSrvGenericDAO<VincoloAutorizzaDTO> implements VincoloAutorizzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String QUERY_VINCOLO_AUTORIZZA_BY_ID_ADEMPIMENTO = "SELECT sdva.*\n" +
            ", sdtva.*, sdtva.id_tipo_vincolo_aut AS tipo_vincolo_aut_id\n" +
            ", srava.ordinamento_adempi_vincolo AS ordinamento\n" +
            "FROM _replaceTableName_ sdva\n" +
            "INNER JOIN scriva_d_tipo_vincolo_aut sdtva ON sdtva.id_tipo_vincolo_aut = sdva.id_tipo_vincolo_aut\n" +
            "INNER JOIN scriva_r_adempi_vincolo_aut srava ON srava.id_vincolo_autorizza = sdva.id_vincolo_autorizza\n" +
            "WHERE 1 = 1\n" +
            "AND sdva.data_fine_validita IS NULL\n" +
            "AND srava.id_adempimento = :idAdempimento\n" +
            "ORDER BY srava.ordinamento_adempi_vincolo";

    private static final String QUERY_VINCOLO_AUTORIZZA = "SELECT sdva.*, sdva.ordinamento_vincolo_aut AS ordinamento\n" +
            ", sdtva.*, sdtva.id_tipo_vincolo_aut AS tipo_vincolo_aut_id\n" +
            "FROM _replaceTableName_ sdva\n" +
            "INNER JOIN scriva_d_tipo_vincolo_aut sdtva ON sdtva.id_tipo_vincolo_aut = sdva.id_tipo_vincolo_aut\n" +
            "WHERE 1 = 1\n" +
            "AND sdva.data_fine_validita IS NULL\n" +
            "ORDER BY sdva.ordinamento_vincolo_aut";

    private static final String QUERY_VINCOLO_IDRO = "SELECT sdva.*, sdtva.*, sdtva.id_tipo_vincolo_aut AS tipo_vincolo_aut_id,\n" +
            "srava.ordinamento_adempi_vincolo AS ordinamento\n" +
            "FROM scriva_r_adempi_vincolo_aut srava\n" +
            "INNER JOIN scriva_d_vincolo_autorizza sdva ON sdva.id_vincolo_autorizza = srava.id_vincolo_autorizza\n" +
            "INNER JOIN scriva_d_tipo_vincolo_aut sdtva ON sdtva.id_tipo_vincolo_aut = sdva.id_tipo_vincolo_aut\n" +
            "WHERE sdva.cod_vincolo_autorizza = 'VIDR'\n" +
            "AND sdva.data_fine_validita IS NULL\n" +
            "AND sdva.ind_visibile LIKE '%FO%'\n" +
            "AND sdtva.cod_tipo_vincolo_aut = 'V'\n" +
            "AND srava.id_adempimento = (SELECT sti.id_adempimento FROM scriva_t_oggetto_istanza stoi \n" +
            "\t\t\t\t\t\t\tINNER JOIN scriva_t_istanza sti ON sti.id_istanza = stoi.id_istanza \n" +
            "\t\t\t\t\t\t\tWHERE stoi.id_oggetto_istanza = :id_oggetto_istanza )" + 
            " AND (DATE(srava.data_inizio_validita) <= DATE(NOW()) AND (srava.data_fine_validita IS NULL OR DATE(srava.data_fine_validita)>= DATE(NOW())))\n " +
            " ORDER BY ordinamento_adempi_vincolo";
    
    /**
     * Load vincoli autorizzazioni list.
     *
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoliAutorizzazioni() {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        return findListByQuery(className, QUERY_VINCOLO_AUTORIZZA, map);
    }

    /**
     * Load vincolo autorizzazione by adempimento list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoloAutorizzazioneByAdempimento(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("idAdempimento", idAdempimento);
        return findListByQuery(className, QUERY_VINCOLO_AUTORIZZA_BY_ID_ADEMPIMENTO, map);
    }

    /**
     * Load vincolo idrogeologico by id_oggetto_istanza list.
     *
     * @param id_oggetto_istanza the id_oggetto_istanza
     * @return the list
     */
    @Override
    public List<VincoloAutorizzaExtendedDTO> loadVincoloIdrogeologico(Long id_oggetto_istanza) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        map.put("id_oggetto_istanza", id_oggetto_istanza);
        return findListByQuery(className, QUERY_VINCOLO_IDRO, map);
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

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<VincoloAutorizzaDTO> getRowMapper() throws SQLException {
        return new VincoloAutorizzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>  extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<VincoloAutorizzaExtendedDTO> getExtendedRowMapper() throws SQLException {
        return new VincoloAutorizzaExtendedRowMapper();
    }

    public static class VincoloAutorizzaRowMapper implements RowMapper<VincoloAutorizzaDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public VincoloAutorizzaRowMapper() throws SQLException {
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
        public VincoloAutorizzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            VincoloAutorizzaDTO bean = new VincoloAutorizzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, VincoloAutorizzaDTO bean) throws SQLException {
            bean.setIdVincoloAutorizza(rs.getLong("id_vincolo_autorizza"));
            bean.setIdTipoVincoloAut(rs.getLong("id_tipo_vincolo_aut"));
            bean.setCodVincoloAutorizza(rs.getString("cod_vincolo_autorizza"));
            bean.setDesVincoloAutorizza(rs.getString("des_vincolo_autorizza"));
            bean.setDesRifNormativo(rs.getString("des_rif_normativo"));
            bean.setDataInizioValidita(rs.getDate("data_inizio_validita"));
            bean.setDataFineValidita(rs.getDate("data_fine_validita"));
            bean.setFlgModifica(rs.getInt("flg_modifica") == 1 ? Boolean.TRUE : Boolean.FALSE);
            bean.setIndVisibile(rs.getString("ind_visibile"));
            bean.setOrdinamentoVincoloAut(rs.getLong("ordinamento"));
        }
    }

    public static class VincoloAutorizzaExtendedRowMapper implements RowMapper<VincoloAutorizzaExtendedDTO> {

        /**
         * Instantiates a new Competenza territorio row mapper.
         *
         * @throws SQLException the sql exception
         */
        public VincoloAutorizzaExtendedRowMapper() throws SQLException {
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
        public VincoloAutorizzaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            VincoloAutorizzaExtendedDTO bean = new VincoloAutorizzaExtendedDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, VincoloAutorizzaExtendedDTO bean) throws SQLException {
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