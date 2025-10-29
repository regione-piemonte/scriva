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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.StatoOsservazioneContatoreDAO;
import it.csi.scriva.scrivabesrv.dto.StatoOsservazioneContatoreDTO;
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
public class StatoOsservazioneContatoreDAOImpl extends ScrivaBeSrvGenericDAO<StatoOsservazioneContatoreDTO> implements StatoOsservazioneContatoreDAO {

	private final String className = this.getClass().getSimpleName();
	private static final String QUERY_STATO_OSSERVAZIONE_CONTATORE ="select coalesce (sdso.cod_stato_osservazione , 'TUTTE') as stato_osservazione, count(1) as numero_osservazione \r\n" +
			"from scriva_d_stato_osservazione sdso \r\n" +
			"left outer join scriva_r_istanza_osservazione srio on  srio.id_stato_osservazione=sdso.id_stato_osservazione "
	         + "  ";

	private static final String WHERE_CODICE_FISCALE_ATTORE = " and srio.cf_osservazione_ins = :cfAttore \n";

	
	private static final String QUERY_STATO_OSSERVAZIONE_CONTATORE_NEW="  SELECT 'TUTTE' AS stato_osservazione, count(1) as numero_osservazione \r\n" + 
			"FROM scriva_r_istanza_osservazione srio \r\n" + 
			"WHERE srio.cf_osservazione_ins = :cfAttore\r\n" + 
			"union\r\n" + 
			"SELECT sdso.cod_stato_osservazione AS stato_osservazione, CASE WHEN count(srio.id_istanza)=0 THEN 0 ELSE count(1) END AS numero_osservazione \r\n" + 
			"FROM scriva_d_stato_osservazione sdso \r\n" + 
			"LEFT OUTER JOIN scriva_r_istanza_osservazione srio ON (srio.id_stato_osservazione=sdso.id_stato_osservazione AND srio.cf_osservazione_ins = :cfAttore)\r\n" + 
			"GROUP BY sdso.cod_stato_osservazione   order by stato_osservazione  ";


	@Override
	public List<StatoOsservazioneContatoreDTO> loadStatoOsservazioniContatore(String cfAttore) {
		logBegin(className);
		Map<String, Object> map = new HashMap<>();
	/*	String query = QUERY_STATO_OSSERVAZIONE_CONTATORE;
		if (cfAttore != null) {
			map.put("cfAttore", cfAttore);
			query += WHERE_CODICE_FISCALE_ATTORE;
		}
		query += " group by rollup(sdso.cod_stato_osservazione) ";*/
		String query = QUERY_STATO_OSSERVAZIONE_CONTATORE_NEW;
		map.put("cfAttore", cfAttore);
		return findListByQuery(className, query, map);
	}


	@Override
	public String getPrimaryKeySelect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowMapper<StatoOsservazioneContatoreDTO> getRowMapper() throws SQLException {
		return new StatoOsservazioneContatoreRowMapper();
	}

	@Override
	public RowMapper<?> getExtendedRowMapper() throws SQLException {
		return new StatoOsservazioneContatoreRowMapper();
	}


	public static class StatoOsservazioneContatoreRowMapper implements RowMapper<StatoOsservazioneContatoreDTO> {

		public StatoOsservazioneContatoreRowMapper() throws SQLException {
			// Instantiate class
		}

		@Override
		public StatoOsservazioneContatoreDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			StatoOsservazioneContatoreDTO bean = new StatoOsservazioneContatoreDTO();
			populateBean(rs, bean);
			return bean;
		}

		private void populateBean(ResultSet rs, StatoOsservazioneContatoreDTO bean) throws SQLException {
			bean.setStatoOsservazione(rs.getString("stato_osservazione"));
			bean.setNumeroOsservazione(rs.getLong("numero_osservazione"));

		}
	}

}