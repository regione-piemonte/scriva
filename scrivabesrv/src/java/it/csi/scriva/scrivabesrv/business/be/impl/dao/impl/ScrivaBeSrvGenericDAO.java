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

import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.LongRowMapper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.StringRowMapper;
import it.csi.scriva.scrivabesrv.business.be.impl.dao.impl.mapper.TimestampRowMapper;
import it.csi.scriva.scrivabesrv.business.be.service.impl.BaseServiceImpl;
import it.csi.scriva.scrivabesrv.util.Constants;
import it.csi.scriva.scrivabesrv.util.hashing.HashingUtil;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * The type Scriva be srv generic dao.
 *
 * @param <T> the type parameter
 * @author CSI PIEMONTE
 */
@Component
public abstract class ScrivaBeSrvGenericDAO<T> extends BaseServiceImpl {

    private static final String className = "ScrivaBeSrvGenericDAO";


    /**
     * The constant LOGGER.
     */
    protected static Logger LOGGER = Logger.getLogger(Constants.LOGGER_NAME + ".dao");

    /**
     * The Template.
     */
    protected NamedParameterJdbcTemplate template;
    /**
     * The Table name.
     */
    protected String tableName;
    /**
     * The Table name storico.
     */
    protected String storicoTableName;

    /**
     * The constant ISNOTNULL.
     */
    public static final String ISNOTNULL = "ROW_ISNOT_NULL";
    /**
     * The constant ISNULL.
     */
    public static final String ISNULL = "ROW_IS_NULL";
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "";


    /**
     * Constructor for a standard table (without auto-incremented column)
     */
    protected ScrivaBeSrvGenericDAO() {
        super();
    }

    /**
     * Sets template.
     *
     * @param template the template
     */
    public void setTemplate(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    /**
     * Sets table name.
     *
     * @param tableName the table name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Gets table name.
     *
     * @return the table name
     */
    public String getTableName() {
        return this.tableName;
    }


    /**
     * Gets query storico.
     *
     * @param query the query
     * @return the query storico
     */
    public String getQueryStorico(String query) {
        return query.replace("_replaceTableName_", getStoricoTableName());
    }

    /**
     * Gets storico table name.
     *
     * @return the storico table name
     */
    public String getStoricoTableName() {
        return storicoTableName;
    }

    /**
     * Sets storico table name.
     *
     * @param storicoTableName the storico table name
     */
    public void setStoricoTableName(String storicoTableName) {
        this.storicoTableName = storicoTableName;
    }


    /**
     * Gets parameter value.
     *
     * @param filter the filter
     * @return the parameter value
     */
    protected MapSqlParameterSource getParameterValue(Map<String, Object> filter) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        for (String key : filter.keySet()) {
            Object value = filter.get(key);

            if (value != null) {
                if ((value instanceof Long) || (value instanceof Integer) || (value instanceof Float) || (value instanceof Double) || (value instanceof java.math.BigDecimal)) {
                    params.addValue(key, value, Types.NUMERIC);
                } else if (value instanceof java.util.Date) {
                    params.addValue(key, value, Types.TIMESTAMP);
                } else if (value instanceof Boolean) {
                    params.addValue(key, value, Types.BOOLEAN);
                } else if (value instanceof String) {
                    params.addValue(key, value, Types.VARCHAR);
                } else {
                    params.addValue(key, value);
                }
            } else {
                params.addValue(key, value, Types.NULL);
            }
        }
        return params;
    }

    /**
     * Find all list.
     *
     * @return the list
     * @throws DataAccessException the data access exception
     * @throws SQLException        the sql exception
     */
    public List<T> findAll() throws DataAccessException, SQLException {
        try {
            String query = "select * from " + getTableName();
            MapSqlParameterSource params = new MapSqlParameterSource();
            return template.query(query, params, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(className, "findAll", e);
            return null;
        } finally {
            logEnd(className, "findAll");
        }
    }

    /**
     * Find list by query e.
     *
     * @param <E>        the type parameter
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @return the e
     */
    public <E> E findListByQuery(String className, String methodName, String query, Map<String, Object> filter) {
        return this.findListByQuery(className, methodName, query, filter, null, null, Boolean.FALSE);
    }

    /**
     * Find list by query e.
     *
     * @param <E>       the type parameter
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @return the e
     */
    public <E> E findListByQuery(String className, String query, Map<String, Object> filter) {
        return this.findListByQuery(className, getMethodName(3), query, filter, null, null, Boolean.FALSE);
    }

    /**
     * Find list by query e.
     *
     * @param <E>       the type parameter
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @param offset    offset
     * @param limit     limit
     * @return the e
     */
    public <E> E findListByQuery(String className, String query, Map<String, Object> filter, String offset, String limit) {
        return this.findListByQuery(className, getMethodName(3), query, filter, offset, limit, Boolean.FALSE);
    }

    /**
     * Find simple dto list by query e.
     *
     * @param <E>        the type parameter
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @return the e
     */
    public <E> E findSimpleDTOListByQuery(String className, String methodName, String query, Map<String, Object> filter) {
        return this.findListByQuery(className, methodName, query, filter, null, null, Boolean.TRUE);
    }

    /**
     * Find simple dto list by query e.
     *
     * @param <E>       the type parameter
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @return the e
     */
    public <E> E findSimpleDTOListByQuery(String className, String query, Map<String, Object> filter) {
        return this.findListByQuery(className, getMethodName(3), query, filter, null, null, Boolean.TRUE);
    }


    /**
     * Find list long by query e.
     *
     * @param <E>        the type parameter
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @param fieldName  the field name
     * @return the e
     */
    public <E> E findListLongByQuery(String className, String methodName, String query, Map<String, Object> filter, String fieldName) {
        return this.findListLongByQuery(className, methodName, query, filter, null, null, fieldName);
    }

    /**
     * Find list long by query e.
     *
     * @param <E>       the type parameter
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @param fieldName the field name
     * @return the e
     */
    public <E> E findListLongByQuery(String className, String query, Map<String, Object> filter, String fieldName) {
        return this.findListLongByQuery(className, getMethodName(3), query, filter, null, null, fieldName);
    }

    /**
     * Find list by query e.
     *
     * @param <E>         the type parameter
     * @param className   the class name
     * @param methodName  the method name
     * @param query       the query
     * @param filter      the filter
     * @param offset      the offset
     * @param limit       the limit
     * @param isSimpleDTO the is simple dto
     * @return the e
     */
    @SuppressWarnings("unchecked")
    public <E> E findListByQuery(String className, String methodName, String query, Map<String, Object> filter, String offset, String limit, Boolean isSimpleDTO) {
        logBegin(className, methodName);
        E result = null;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (filter != null && filter.size() > 0) {
            params = getParameterValue(filter);
            logDebug(className, methodName, "filter :\n" + filter);
        }
        try {
            String q = getQuery(query, offset, limit);
            result = Boolean.TRUE.equals(isSimpleDTO) ? (E) template.query(q, params, getRowMapper()) : (E) template.query(q, params, getExtendedRowMapper());
            logDebug(className, methodName, "\nquery executed : \n" + q + "\n");
        } catch (Exception e) {
            logError(className, methodName, e);
        } finally {
            logEnd(className, methodName);
        }
        return result;
    }

    /**
     * Find list by query e.
     *
     * @param <E>         the type parameter
     * @param className   the class name
     * @param query       the query
     * @param filter      the filter
     * @param offset      the offset
     * @param limit       the limit
     * @param isSimpleDTO the is simple dto
     * @return the e
     */
    public <E> E findListByQuery(String className, String query, Map<String, Object> filter, String offset, String limit, Boolean isSimpleDTO) {
        return findListByQuery(className, getMethodName(3), query, filter, offset, limit, isSimpleDTO);
    }

    /**
     * Find list long by query e.
     *
     * @param <E>        the type parameter
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @param offset     the offset
     * @param limit      the limit
     * @param fieldName  the field name
     * @return the e
     */
    @SuppressWarnings("unchecked")
    public <E> E findListLongByQuery(String className, String methodName, String query, Map<String, Object> filter, String offset, String limit, String fieldName) {
        logBegin(className, methodName);
        E result = null;
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (filter != null && filter.size() > 0) {
            params = getParameterValue(filter);
            logDebug(className, methodName, "filter :\n" + filter);
        }
        try {
            String q = getQuery(query, offset, limit);
            result = (E) template.query(q, params, getLongRowMapper(fieldName));
            logDebug(className, methodName, "\nquery executed : \n" + q + "\n");
        } catch (Exception e) {
            logError(className, methodName, e);
        } finally {
            logEnd(className, methodName);
        }
        return result;
    }

    /**
     * Find by pk t.
     *
     * @param filter the filter
     * @return the t
     * @throws DataAccessException the data access exception
     * @throws SQLException        the sql exception
     */
    public T findByPK(Map<String, Object> filter) throws DataAccessException, SQLException {
        String methodname = "findByPK";
        try {
            String query = getPrimaryKeySelect();
            MapSqlParameterSource params = getParameterValue(filter);
            logDebug(className, methodname, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(query, params, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logError(className, methodname, e);
            return null;
        } finally {
            logEnd(className, methodname);
        }
    }

    /**
     * Find by pk t.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param filter     the filter
     * @return the t
     * @throws DataAccessException the data access exception
     * @throws SQLException        the sql exception
     */
    public T findByPK(String className, String methodName, Map<String, Object> filter) {
        try {
            String query = getPrimaryKeySelect();
            MapSqlParameterSource params = getParameterValue(filter);
            logDebug(className, methodName, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(query, params, getRowMapper());
        } catch (Exception e) {
            logError(className, methodName, e);
            return null;
        } finally {
            logEnd(className, methodName);
        }
    }

    /**
     * Find by pk t.
     *
     * @param className the class name
     * @param filter    the filter
     * @return the t
     */
    public T findByPK(String className, Map<String, Object> filter) {
        try {
            String query = getPrimaryKeySelect();
            MapSqlParameterSource params = getParameterValue(filter);
            logDebug(className, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(query, params, getRowMapper());
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Find simple dto by query t.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @return the t
     */
    public T findSimpleDTOByQuery(String className, String methodName, String query, Map<String, Object> filter) {
        try {
            MapSqlParameterSource params = getParameterValue(filter);
            logDebug(className, methodName, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(getQuery(query, null, null), params, getRowMapper());
        } catch (Exception e) {
            logError(className, methodName, e);
            return null;
        } finally {
            logEnd(className, methodName);
        }
    }

    /**
     * Find simple dto by query t.
     *
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @return the t
     */
    public T findSimpleDTOByQuery(String className, String query, Map<String, Object> filter) {
        try {
            MapSqlParameterSource params = getParameterValue(filter);
            logDebug(className, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(getQuery(query, null, null), params, getRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets json from query.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @param offset     the offset
     * @param limit      the limit
     * @param simple     the simple
     * @return the json from query
     */
    public String getJsonFromQuery(String className, String methodName, String query, Map<String, Object> filter, String offset, String limit, Boolean simple) {
        try {
            MapSqlParameterSource params = filter != null ? getParameterValue(filter) : null;
            logDebug(className, methodName, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(Boolean.TRUE.equals(simple) ? getQuery(query, null, null) : getJsonQuery(query, null, null), params, String.class);
        } catch (Exception e) {
            logError(className, methodName, e);
            return null;
        } finally {
            logEnd(className, methodName);
        }
    }

    /**
     * Gets json from query.
     *
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @param offset    the offset
     * @param limit     the limit
     * @param simple    the simple
     * @return the json from query
     */
    public String getJsonFromQuery(String className, String query, Map<String, Object> filter, String offset, String limit, Boolean simple) {
        try {
            MapSqlParameterSource params = filter != null ? getParameterValue(filter) : null;
            logDebug(className, "INPUT PARAMS : [query] : \n" + query + "\n [params] :\n" + params);
            return template.queryForObject(Boolean.TRUE.equals(simple) ? getQuery(query, null, null) : getJsonQuery(query, null, null), params, String.class);
        } catch (Exception e) {
            logError(className, e);
            return null;
        } finally {
            logEnd(className);
        }
    }

    /**
     * Gets json from query.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @param offset     the offset
     * @param limit      the limit
     * @return the json from query
     */
    public String getJsonFromQuery(String className, String methodName, String query, Map<String, Object> filter, String offset, String limit) {
        return getJsonFromQuery(className, methodName, query, filter, offset, limit, Boolean.FALSE);
    }

    /**
     * Gets json from query.
     *
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @param offset    the offset
     * @param limit     the limit
     * @return the json from query
     */
    public String getJsonFromQuery(String className, String query, Map<String, Object> filter, String offset, String limit) {
        return getJsonFromQuery(className, getMethodName(3), query, filter, offset, limit, Boolean.FALSE);
    }

    /**
     * Gets json from query simple.
     *
     * @param className  the class name
     * @param methodName the method name
     * @param query      the query
     * @param filter     the filter
     * @param offset     the offset
     * @param limit      the limit
     * @return the json from query simple
     */
    public String getJsonFromQuerySimple(String className, String methodName, String query, Map<String, Object> filter, String offset, String limit) {
        return getJsonFromQuery(className, methodName, query, filter, offset, limit, Boolean.TRUE);
    }

    /**
     * Gets json from query simple.
     *
     * @param className the class name
     * @param query     the query
     * @param filter    the filter
     * @param offset    the offset
     * @param limit     the limit
     * @return the json from query simple
     */
    public String getJsonFromQuerySimple(String className, String query, Map<String, Object> filter, String offset, String limit) {
        return getJsonFromQuery(className, getMethodName(3), query, filter, offset, limit, Boolean.TRUE);
    }

    /**
     * Gets query.
     *
     * @param query  the query
     * @param offset the offset
     * @param limit  the limit
     * @return the query
     */
    public String getQuery(String query, String offset, String limit) {
        String q = query.replace("_replaceTableName_", getTableName());
        StringBuilder sb = new StringBuilder(q);
        logDebug(className, "getQuery", "INPUT PARAMS : [query] : \n" + q + "\n [offset] : " + offset + " - [limit] : " + limit);
        if (null != offset && null != limit) {
            Integer calcOffset = Integer.parseInt(offset) <= 1 ? 0 : ((Integer.parseInt(offset) - 1) * Integer.parseInt(limit));
            logDebug(className, "getQuery", "[calcOffset] : " + calcOffset);
            sb.append(" OFFSET ").append(calcOffset);
        }
        if (null != limit) {
            sb.append(" LIMIT ").append(limit);
        }
        return sb.toString();
    }

    /**
     * Gets json query.
     *
     * @param query  the query
     * @param offset the offset
     * @param limit  the limit
     * @return the json query
     */
    public String getJsonQuery(String query, String offset, String limit) {
        String jsonSelect = "SELECT json_agg(row_to_json(json)) FROM (\n\n";
        String jsonAlias = "\n) json\n";
        return jsonSelect + getQuery(query, offset, limit) + jsonAlias;
    }

    /**
     * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from
     * the database
     *
     * @return String primary key select
     */
    public abstract String getPrimaryKeySelect();

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             row mapper
     * @throws SQLException the sql exception
     */
    public abstract RowMapper<T> getRowMapper() throws SQLException;

    /**
     * Returns a RowMapper for a new bean instance
     *
     * @return RowMapper<T>             extended row mapper
     * @throws SQLException the sql exception
     */
    public abstract RowMapper<?> getExtendedRowMapper() throws SQLException;

    /**
     * Gets long row mapper.
     *
     * @param fieldName the field name
     * @return the long row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<Long> getLongRowMapper(String fieldName) throws SQLException {
        return new LongRowMapper(fieldName);
    }

    public RowMapper<String> getStringRowMapper(String fieldName) {
        return new StringRowMapper(fieldName);
    }

    /**
     * Gets Timestamp row mapper.
     *
     * @param fieldName the field name
     * @return the long row mapper
     * @throws SQLException the sql exception
     */
    public RowMapper<Timestamp> getTimestampRowMapper(String fieldName) throws SQLException {
        return new TimestampRowMapper(fieldName);
    }

    /**
     * Generate gest uid string.
     *
     * @param stringToHash the string to hash
     * @return the string
     */
    protected String generateGestUID(String stringToHash) {
        try {
            return HashingUtil.encodeSH3(stringToHash);
        } catch (NoSuchAlgorithmException e) {
            logError(className, "generateGestUID", "Errore nella creazione del UID: Algoritmo non valido\n" + e);
            return String.valueOf(this.hashCode());
        } catch (Exception e) {
            logError(className, "generateGestUID", "Errore nella creazione del UID\n" + e);
            return String.valueOf(this.hashCode());
        }
    }

    /**
     * Gets query sorting segment.
     *
     * @param sort the sort
     * @return the query sorting segment
     */
    protected String getQuerySortingSegment(String sort) {
        String ascending = "ASC";
        String descending = "DESC";
        char order = '-';
        String sortField = null;
        String sortOrder = null;

        StringBuilder sql = new StringBuilder();
        if (sort != null && !sort.isEmpty()) {
            sortOrder = sort.charAt(0) == order ? descending : ascending;
            sortField = sortOrder.equals(descending) ? sort.substring(1) : sort;
            if (!sortField.isEmpty()) {
                sql.append(" ORDER BY ");
                sql.append(sortField).append(" ").append(sortOrder);
            }
        }
        return sql.toString();
    }

}