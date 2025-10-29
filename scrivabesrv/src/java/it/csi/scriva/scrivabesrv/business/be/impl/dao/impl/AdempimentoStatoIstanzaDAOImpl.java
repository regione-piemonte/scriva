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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.AdempimentoStatoIstanzaDAO;
import it.csi.scriva.scrivabesrv.dto.AdempimentoStatoIstanzaDTO;
import it.csi.scriva.scrivabesrv.dto.ClasseAllegatoDTO;
import it.csi.scriva.scrivabesrv.dto.StatoIstanzaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Adempimento stato istanza dao.
 *
 * @author CSI PIEMONTE
 */
public class AdempimentoStatoIstanzaDAOImpl extends ScrivaBeSrvGenericDAO<AdempimentoStatoIstanzaDTO> implements AdempimentoStatoIstanzaDAO {

    private final String className = this.getClass().getSimpleName();

    private static final String WHERE_ID_ADEMPIMENTO = "AND srsia.id_adempimento = :idAdempimento\n";

    private static final String QUERY_LOAD_ADEMPI_STATO_ISTANZA = "SELECT DISTINCT sdsi.*, sdca.*\n" +
            "FROM _replaceTableName_ srsia\n" +
            "INNER JOIN scriva_d_stato_istanza sdsi ON sdsi.id_stato_istanza = srsia.id_stato_istanza\n" +
            "LEFT JOIN scriva_d_classe_allegato sdca ON sdca.id_classe_allegato = srsia.id_classe_allegato\n" +
            "WHERE 1 = 1\n";

    /**
     * Load adempi stato istanza list.
     *
     * @param idAdempimento the id adempimento
     * @return the list
     */
    @Override
    public List<AdempimentoStatoIstanzaDTO> loadAdempiStatoIstanza(Long idAdempimento) {
        logBegin(className);
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_LOAD_ADEMPI_STATO_ISTANZA;
        if (idAdempimento != null) {
            map.put("idAdempimento", idAdempimento);
            query += WHERE_ID_ADEMPIMENTO;
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
        return null;
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoStatoIstanzaDTO> getRowMapper() throws SQLException {
        return new AdempimentoStatoIstanzaRowMapper();
    }

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    @Override
    public RowMapper<AdempimentoStatoIstanzaDTO> getExtendedRowMapper() throws SQLException {
        return new AdempimentoStatoIstanzaRowMapper();
    }

    /**
     * The type Adempi esito procedimento row mapper.
     */
    public static class AdempimentoStatoIstanzaRowMapper implements RowMapper<AdempimentoStatoIstanzaDTO> {

        /**
         * Instantiates a new Ambito row mapper.
         *
         * @throws SQLException the sql exception
         */
        public AdempimentoStatoIstanzaRowMapper() throws SQLException {
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
        public AdempimentoStatoIstanzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdempimentoStatoIstanzaDTO bean = new AdempimentoStatoIstanzaDTO();
            populateBean(rs, bean);
            return bean;
        }

        private void populateBean(ResultSet rs, AdempimentoStatoIstanzaDTO bean) throws SQLException {
            StatoIstanzaDTO statoIstanza = new StatoIstanzaDTO();
            populateStatoIstanzaBean(rs, statoIstanza);
            if (statoIstanza.getIdStatoIstanza() > 0) {
                bean.setStatoIstanza(statoIstanza);
            }

            ClasseAllegatoDTO classeAllegato = new ClasseAllegatoDTO();
            populateClasseAllegatoBean(rs, classeAllegato);
            if (classeAllegato.getIdClasseAllegato() > 0) {
                bean.setClasseAllegato(classeAllegato);
            }
        }

        private void populateStatoIstanzaBean(ResultSet rs, StatoIstanzaDTO statoIstanza) throws SQLException {
            statoIstanza.setIdStatoIstanza(rs.getLong("id_stato_istanza"));
            statoIstanza.setCodiceStatoIstanza(rs.getString("cod_stato_istanza"));
            statoIstanza.setDescrizioneStatoIstanza(rs.getString("des_stato_istanza"));
            statoIstanza.setFlgStoricoIstanza(rs.getInt("flg_storico_istanza") > 0 ? Boolean.TRUE : Boolean.FALSE);
            statoIstanza.setIndVisibile(rs.getString("ind_visibile"));
            statoIstanza.setDesEstesaStatoIstanza(rs.getString("des_estesa_stato_istanza"));
            statoIstanza.setLabelStato(rs.getString("label_stato"));
            statoIstanza.setFlgAggiornaOggetto(rs.getInt("flg_aggiorna_oggetto") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }

        private void populateClasseAllegatoBean(ResultSet rs, ClasseAllegatoDTO classeAllegato) throws SQLException {
            classeAllegato.setIdClasseAllegato(rs.getLong("id_classe_allegato"));
            classeAllegato.setCodClasseAllegato(rs.getString("cod_classe_allegato"));
            classeAllegato.setDesClasseAllegato(rs.getString("des_classe_allegato"));
            classeAllegato.setOrdinamentoClasseAllegato(rs.getLong("ordinamento_classe_allegato"));
            classeAllegato.setFlgAttivo(rs.getInt("flg_attivo") > 0 ? Boolean.TRUE : Boolean.FALSE);
        }
    }
}