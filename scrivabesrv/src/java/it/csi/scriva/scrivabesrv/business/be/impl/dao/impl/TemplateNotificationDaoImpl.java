/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
/**
 * 
 */
package it.csi.scriva.scrivabesrv.business.be.impl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;


import it.csi.scriva.scrivabesrv.business.be.impl.dao.TemplateNotificationDao;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.NotificaDAOImpl.NotificaRowMapper;
import it.csi.scriva.scrivabesrv.dto.NotificaDTO;
import it.csi.scriva.scrivabesrv.dto.NotificaExtendedDTO;
import it.csi.scriva.scrivabesrv.dto.TemplateExtendedDTO;


/**
 * @author CSI PIEMONTE
 *
 * @param
 * @return
 */
public class TemplateNotificationDaoImpl extends ScrivaBeSrvGenericDAO<NotificaExtendedDTO> implements TemplateNotificationDao{

	private final String className = this.getClass().getSimpleName();
	private static final String QUERY_PROFILO_FO ="select distinct\n" + 
		"p.cod_profilo_app,\n" + 
		"a.id_compilante,\n" + 
		"a.cf_attore as cf,\n" + 
		"stc.des_email_compilante as rif_canale\n" + 
		"from\n" + 
		"SCRIVA_R_ISTANZA_ATTORE a\n" + 
		"inner join scriva_d_profilo_app p on \tp.id_profilo_app = a.id_profilo_app\n" + 
		"inner join scriva_t_compilante stc on \tstc.id_compilante = a.id_compilante\n" + 
		"where (1=1)\n" + 
		"and a.data_revoca is null\n" + 
		"and a.id_compilante is not null\n"; 
		
	
	private static final String QUERY_PROFILO_FO_WHERE_ID_ISTANZA = " and ID_ISTANZA = :idIstanza\n"  ;
	private static final String QUERY_PROFILO_FO_WHERE_COD_PROFILO_APP = " and cod_profilo_app in(:codProfiloApp)\n";
		
	private static final String QUERY_PROFILO_BO = "select distinct\n" + 
			"cod_profilo_app,\n" + 
			"stf.id_funzionario,\n" + 
			"cf_funzionario as cf,\n" + 
			"des_email_funzionario as rif_canale\n" + 
			"from\n" + 
			"scriva_t_funzionario stf\n" + 
			"inner join scriva_r_funzionario_profilo srfp on srfp.id_funzionario = stf.id_funzionario\n" + 
			"inner join scriva_d_profilo_app sdpa on sdpa.id_profilo_app = srfp.id_funzionario\n" + 
			"where (1=1)\n" + 
			"and srfp.data_fine_validita is null\n";

	private static final String QUERY_PROFILO_BO_WHERE_COD_PROFILO_APP = " and sdpa.cod_profilo_app in(:codProfiloApp)\n";
	
	private static final String QUERY_AC_PRINCIPALE_FUNZIONARI = "select\n" + 
			"stf.id_funzionario,\n" + 
			"stf.nome_funzionario,\n" + 
			"stf.cognome_funzionario ,\n" + 
			"stf.cf_funzionario as cf,\n" + 
			"stf.des_email_funzionario as rif_canale\n" + 
			"from\n" + 
			"scriva_r_istanza_competenza sric\n" + 
			"inner join scriva_r_funzionario_compete srfc on\n" + 
			"srfc.id_competenza_territorio = sric.id_competenza_territorio\n" + 
			"inner join scriva_t_funzionario stf on\n" + 
			"stf.id_funzionario = srfc.id_funzionario\n" + 
			"and id_istanza = :idIstanza\n" + 
			"and sric.data_fine_validita is null\n" + 
			"and sric.flg_autorita_principale = 1\n" + 
			"and stf.data_fine_validita is null\n" + 
			"and stf.id_funzionario in (\n" + 
			"select\n" + 
			"funzionario.id_funzionario\n" + 
			"from\n" + 
			"(\n" + 
			"select\n" + 
			"f.*\n" + 
			"from\n" + 
			"scriva_r_tipo_adempi_profilo srtap\n" + 
			"inner join scriva_d_tipo_adempimento t on\n" + 
			"t.id_tipo_adempimento = srtap.id_tipo_adempimento\n" + 
			"inner join scriva_d_adempimento a on\n" + 
			"a.id_tipo_adempimento = t.id_tipo_adempimento\n" + 
			"inner join scriva_d_profilo_app sdpa on\n" + 
			"sdpa.id_profilo_app = srtap.id_profilo_app\n" + 
			"inner join scriva_r_funzionario_profilo srfp on\n" + 
			"srfp.id_profilo_app = sdpa.id_profilo_app\n" + 
			"inner join scriva_t_funzionario f on\n" + 
			"f.id_funzionario = srfp.id_funzionario\n" + 
			"where\n" + 
			"a.id_adempimento in(\n" + 
			"select\n" + 
			"id_adempimento\n" + 
			"from\n" + 
			"scriva_t_istanza\n" + 
			"where\n" + 
			"id_istanza = :idIstanza)\n" + 
			"and sdpa.id_componente_app =(\n" + 
			"select\n" + 
			"id_componente_app\n" + 
			"from\n" + 
			"scriva_d_componente_app\n" + 
			"where\n" + 
			"cod_componente_app = 'BO')\n" + 
			"and t.flg_attivo = 1\n" + 
			"and a.flg_attivo = 1\n" + 
			"and f.data_fine_validita is null\n" + 
			"and sdpa.cod_profilo_app like '%RW'\n" + 
			"and srfp.data_fine_validita is null) as funzionario)\n";;
	
			
	private static final String QUERY_AC_SECONDARIA_FUNZIONARI = "select\n" + 
			"stf.id_funzionario,\n" + 
			"stf.nome_funzionario,\n" + 
			"stf.cognome_funzionario ,\n" + 
			"stf.cf_funzionario as cf,\n" + 
			"stf.des_email_funzionario as rif_canale\n" + 
			"from\n" + 
			"scriva_r_istanza_competenza sric\n" + 
			"inner join scriva_r_funzionario_compete srfc on\n" + 
			"srfc.id_competenza_territorio = sric.id_competenza_territorio\n" + 
			"inner join scriva_t_funzionario stf on\n" + 
			"stf.id_funzionario = srfc.id_funzionario\n" + 
			"and id_istanza = :idIstanza\n" + 
			"and sric.data_fine_validita is null\n" + 
			"and sric.flg_autorita_principale = 1\n" + 
			"and stf.data_fine_validita is null\n" + 
			"and stf.id_funzionario in (\n" + 
			"select\n" + 
			"funzionario.id_funzionario\n" + 
			"from\n" + 
			"(\n" + 
			"select\n" + 
			"f.*\n" + 
			"from\n" + 
			"scriva_r_tipo_adempi_profilo srtap\n" + 
			"inner join scriva_d_tipo_adempimento t on\n" + 
			"t.id_tipo_adempimento = srtap.id_tipo_adempimento\n" + 
			"inner join scriva_d_adempimento a on\n" + 
			"a.id_tipo_adempimento = t.id_tipo_adempimento\n" + 
			"inner join scriva_d_profilo_app sdpa on\n" + 
			"sdpa.id_profilo_app = srtap.id_profilo_app\n" + 
			"inner join scriva_r_funzionario_profilo srfp on\n" + 
			"srfp.id_profilo_app = sdpa.id_profilo_app\n" + 
			"inner join scriva_t_funzionario f on\n" + 
			"f.id_funzionario = srfp.id_funzionario\n" + 
			"where\n" + 
			"a.id_adempimento in(\n" + 
			"select\n" + 
			"id_adempimento\n" + 
			"from\n" + 
			"scriva_t_istanza\n" + 
			"where\n" + 
			"id_istanza = :idIstanza)\n" + 
			"and sdpa.id_componente_app =(\n" + 
			"select\n" + 
			"id_componente_app\n" + 
			"from\n" + 
			"scriva_d_componente_app\n" + 
			"where\n" + 
			"cod_componente_app = 'BO')\n" + 
			"and t.flg_attivo = 1\n" + 
			"and a.flg_attivo = 1\n" + 
			"and f.data_fine_validita is null\n" + 
			"and sdpa.cod_profilo_app like '%READ'\n" + 
			"and srfp.data_fine_validita is null) as funzionario)\n";
	
	private static final String QUERY_ATTORE_FO_COMPILANTI = "SELECT\n" + 
			"stc.id_compilante,\n" + 
			"stc.cf_compilante,\n" + 
			"stc.cognome_compilante as cf,\n" + 
			"stc.nome_compilante,\n" + 
			"stc.des_email_compilante as rif_canale\n" + 
			"FROM\n" + 
			"scriva.scriva_t_compilante stc\n" + 
			"WHERE (1=1)\n"; 
			
	private static final String QUERY_ATTORE_FO_COMPILANTI_WHERE_CF_COMPILANTE = "AND stc.cf_compilante = :cfCompilante\n";
	
	
	private static final String QUERY_ATTORE_FO_FUNZIONARI = "SELECT\n" + 
			"stf.id_funzionario,\n" + 
			"stf.cf_funzionario as cf,\n" + 
			"stf.nome_funzionario,\n" + 
			"stf.cognome_funzionario,\n" + 
			"stf.num_telefono_funzionario,\n" + 
			"stf.des_email_funzionario as rif_canale\n" + 
			"FROM\n" + 
			"scriva.scriva_t_funzionario stf\n" + 
			"WHERE (1=1)\n";
					
	private static final String QUERY_ATTORE_FO_FUNZIONARI_WHERE_CF_FUNZIONARIO = "AND stf.cf_funzionario = :cfFunzionario\n";

	private static final String QUERY_PROFILO_RICHIEDENTE_SELECT_APP_IO= "SELECT\n" + 
	//	"stsi.id_soggetto_istanza ,\n" + 
		"stsi.cf_soggetto as cf,\n" + 
		"stsi.nome,\n" + 
		"stsi.cognome,\n" + 
		"stsi.cf_soggetto as rif_canale\n" + 
		"FROM scriva_t_soggetto_istanza stsi\n";
	
	private static final String QUERY_PROFILO_RICHIEDENTE_SELECT_EMAIL= "SELECT\n" + 
		//	"stsi.id_soggetto_istanza ,\n" + 
			"stsi.cf_soggetto as cf,\n" + 
			"stsi.nome,\n" + 
			"stsi.cognome,\n" + 
			"stsi.des_email as rif_canale\n" + 
			"FROM scriva_t_soggetto_istanza stsi\n";
	
	private static final String QUERY_PROFILO_RICHIEDENTE_SELECT_PEC="SELECT\n" + 
		//	"stsi.id_soggetto_istanza ,\n" + 
			"stsi.cf_soggetto as cf,\n" + 
			"stsi.nome,\n" + 
			"stsi.cognome,\n" + 
			//"stsi.des_pec as email\n" + 
			"COALESCE(des_pec, des_email) as rif_canale\n" +
			"FROM scriva_t_soggetto_istanza stsi\n";
	
	
	private static final String QUERY_PROFILO_RICHIEDENTE_WHERE_PF="where (1=1) \n"
			+ "and id_soggetto_padre is null\n"
			+ "and id_tipo_soggetto in (select id_tipo_soggetto from scriva_d_tipo_soggetto where cod_tipo_soggetto ='PF')\n"
			+ "and ID_ISTANZA = :idIstanza"	;
	
	private static final String QUERY_PROFILO_RICHIEDENTE_WHERE_PG="where (1=1)\n" + 
			"and id_soggetto_padre is null\n" + 
			"and id_tipo_soggetto in (select id_tipo_soggetto from scriva_d_tipo_soggetto where cod_tipo_soggetto in ('PB','PG'))\n" + 
			"and ID_ISTANZA = :idIstanza\n";
	
	private static final String QUERY_PROFILO_RICHIEDENTE_WHERE_LR="where (1=1)\n"
			+ "and id_soggetto_padre is not null\n"
			+ "and ID_ISTANZA = :idIstanza";

	
			
	//TODO rivedere le query: se il parametro fondamentale è nullo deve saltare fuori un'eccezione. non eseguire la query senza filtro
	  /**
   	 * Load compilanti.
   	 *
   	 * @param idIstanza the id istanza
   	 * @param codProfiloApp the cod profilo app
   	 * @return the list
   	 */
   	@Override
	    public List<NotificaExtendedDTO> loadCompilanti(Long idIstanza, String codProfiloApp ) {
	        logBegin(className);	        
	        Map<String, Object> map = new HashMap<>();
	        String query = QUERY_PROFILO_FO;
	        
	        if (idIstanza != null) {
	            map.put("idIstanza", idIstanza);
	            query += QUERY_PROFILO_FO_WHERE_ID_ISTANZA;
	        }
	        if (codProfiloApp != null) {
	            map.put("codProfiloApp", codProfiloApp);
	            query += QUERY_PROFILO_FO_WHERE_COD_PROFILO_APP;
	        }
	        
	        logInfo(className, "Query: [" + query+ "] ");
	        	               
	        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
	        
	        return result;
	    }
	   
	 /**
   	 * Load funzionari.
   	 *
   	 * @param codProfiloApp the cod profilo app
   	 * @return the list
   	 */
   	@Override
	    public List<NotificaExtendedDTO> loadFunzionari(String codProfiloApp ) {
	        logBegin(className);	        
	        Map<String, Object> map = new HashMap<>();
	        String query = QUERY_PROFILO_BO;
	        

	        if (codProfiloApp != null) {
	            map.put("codProfiloApp", codProfiloApp);
	            query += QUERY_PROFILO_BO_WHERE_COD_PROFILO_APP;
	        }
	        
	        logInfo(className, "Query: [" + query+ "] ");
	        
	        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
	        
	        return result;
	    }
   	
	   /**
	    * Load funzionari AC.
	    *
	    * @param idIstanza the id istanza
	    * @return the list
	    */
	@Override
	public List<NotificaExtendedDTO> loadFunzionariAcPrincipale(Long idIstanza) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_AC_PRINCIPALE_FUNZIONARI;
        
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);  
        }
        
        logInfo(className, "Query: [" + query+ "] ");
        
        return findListByQuery(className,  query, map);
	}
	
   	@Override
    public List<NotificaExtendedDTO> loadCompilanteInLinea(String cfCompilante ) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_ATTORE_FO_COMPILANTI;
        

        if (cfCompilante != null) {
            map.put("cfCompilante", cfCompilante);
            query += QUERY_ATTORE_FO_COMPILANTI_WHERE_CF_COMPILANTE;
        }
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
    }
   	
   	
   	@Override
    public List<NotificaExtendedDTO> loadFunzionarioInLinea(String cfFunzionario ) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_ATTORE_FO_FUNZIONARI;
        

        if (cfFunzionario != null) {
            map.put("cfFunzionario", cfFunzionario);
            query += QUERY_ATTORE_FO_FUNZIONARI_WHERE_CF_FUNZIONARIO;
        }
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
    }
	  
	/**
	 * Load funzionari ac secondaria.
	 *
	 * @param idIstanza the id istanza
	 * @return the list
	 */
	@Override
	public List<NotificaExtendedDTO> loadFunzionariAcSecondaria(Long idIstanza) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
        String query = QUERY_AC_SECONDARIA_FUNZIONARI;
        
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);  
        }
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
	}
	
   	@Override
    public List<NotificaExtendedDTO> loadSoggettoPG(Long idIstanza, boolean emailOPec ) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
      
        	String query = (emailOPec) ? QUERY_PROFILO_RICHIEDENTE_SELECT_EMAIL:QUERY_PROFILO_RICHIEDENTE_SELECT_PEC;
     
        
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += QUERY_PROFILO_RICHIEDENTE_WHERE_PG;
        }
     
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
    }
   	
   	
   	@Override
    public List<NotificaExtendedDTO> loadSoggettoPF(Long idIstanza, Boolean emailOPec ) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();

      
		String query;

		if (emailOPec == null) {
			query = QUERY_PROFILO_RICHIEDENTE_SELECT_APP_IO;
		} else {
			query = emailOPec ? QUERY_PROFILO_RICHIEDENTE_SELECT_EMAIL : QUERY_PROFILO_RICHIEDENTE_SELECT_PEC;
		}
     
        
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += QUERY_PROFILO_RICHIEDENTE_WHERE_PF;
        }
     
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
    }
   	
   	@Override
    public List<NotificaExtendedDTO> loadSoggettoLR(Long idIstanza, Boolean emailOPec ) {
        logBegin(className);	        
        Map<String, Object> map = new HashMap<>();
      
		String query;

		if (emailOPec == null) {
			query = QUERY_PROFILO_RICHIEDENTE_SELECT_APP_IO;
		} else {
			query = emailOPec ? QUERY_PROFILO_RICHIEDENTE_SELECT_EMAIL : QUERY_PROFILO_RICHIEDENTE_SELECT_PEC;
		}
     
        
        if (idIstanza != null) {
            map.put("idIstanza", idIstanza);
            query += QUERY_PROFILO_RICHIEDENTE_WHERE_LR;
        }
     
        
        logInfo(className, "Query: [" + query+ "] ");
        
        List<NotificaExtendedDTO> result = findListByQuery(className,  query, map);
        
        return result;
    }
	   
	/**
	*
	* @return
	*
	*/
	@Override
	public String getPrimaryKeySelect() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	*
	* @return
	* @throws SQLException
	*
	*/
	@Override
	public RowMapper<NotificaExtendedDTO> getRowMapper() throws SQLException {
		// TODO Auto-generated method stub
		return new NotificaRowMapper();
	}

	/**
	*
	* @return
	* @throws SQLException
	*
	*/
	@Override
	public RowMapper<?> getExtendedRowMapper() throws SQLException {
		// TODO Auto-generated method stub
		return new NotificaRowMapper();
	}
	
    public static class NotificaRowMapper implements RowMapper<NotificaExtendedDTO> {

        /**
         * Instantiates a new Canale notifica row mapper.
         *
         * @throws SQLException the sql exception
         */
	    public NotificaRowMapper() throws SQLException {
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
	    public NotificaExtendedDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	NotificaExtendedDTO bean = new NotificaExtendedDTO();
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
	    public void populateBean(ResultSet rs, NotificaExtendedDTO bean) throws SQLException {

	    	bean.setCfDestinatario(rs.getString("cf"));
	    	bean.setRifCanale(rs.getString("rif_canale"));

	    }
    }

	
	
}
