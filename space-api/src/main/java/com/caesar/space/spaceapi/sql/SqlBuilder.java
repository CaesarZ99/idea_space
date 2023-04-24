package com.caesar.space.spaceapi.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <h3>SqlBuilder</h3>
 * <p>sql构建操作类</p>
 *
 * @author : Caesar·Liu
 * @date : 2023-04-23 17:38
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SqlBuilder {
    public static final String DELETE = " DELETE ";
    public static final String INSERT = " INSERT ";
    public static final String SELECT = " SELECT ";
    public static final String UPDATE = " UPDATE ";
    public static final String SET = " SET ";
    public static final String INTO = " INTO ";
    public static final String VALUES = " VALUES ";
    public static final String WHERE = " WHERE ";
    public static final String FROM = " FROM ";


    private String sql;

    private String table;

    private String operate;

    private String where;

    private String column;
    private String setColumns;
    private String columns;
    private String values;

    public static class Delete {
        public static String build(SqlBuilder sqlBuilder) {
            String operate = sqlBuilder.operate;
            String table = sqlBuilder.table;
            String conditions = sqlBuilder.where;
            String sql = operate + table + WHERE + conditions;
            if (StringUtils.isEmpty(sql.trim()) || StringUtils.isEmpty(conditions) || !DELETE.equalsIgnoreCase(operate)) {
                return null;
            }
            if (sql.contains(";")) {
                return null;
            }
            return sql;
        }
    }

    public static class Update {
        public static String build(SqlBuilder sqlBuilder) {
            String operate = sqlBuilder.operate;
            String table = sqlBuilder.table;
            String conditions = sqlBuilder.where;
            String setColumns = sqlBuilder.setColumns;
            String sql = operate + " " + table + SET + setColumns + WHERE + conditions;
            if (StringUtils.isEmpty(sql.trim()) || StringUtils.isEmpty(conditions) || !UPDATE.equalsIgnoreCase(operate)) {
                return null;
            }
            if (sql.contains(";")) {
                return null;
            }
            return sql;
        }
    }

    public static class Insert {
        public static String build(SqlBuilder sqlBuilder) {
            String operate = sqlBuilder.operate;
            String table = sqlBuilder.table;
            String conditions = sqlBuilder.where;
            String columns = sqlBuilder.columns;
            String values = sqlBuilder.values;
            String sql = operate + INTO + table + "(" + columns + ") " + VALUES + " (" + values + ") where " + conditions;
            if (StringUtils.isEmpty(sql.trim()) || StringUtils.isEmpty(conditions) || !INSERT.equalsIgnoreCase(operate)) {
                return null;
            }
            if (sql.contains(";")) {
                return null;
            }
            return sql;
        }
    }

    public static class Select {
        public static String build(SqlBuilder sqlBuilder) {
            String operate = sqlBuilder.operate;
            String table = sqlBuilder.table;
            String conditions = sqlBuilder.where;
            String columns = sqlBuilder.columns;
            String sql = operate + columns + FROM + table + WHERE + conditions;
            if (StringUtils.isEmpty(sql.trim()) || StringUtils.isEmpty(conditions) || !SELECT.equalsIgnoreCase(operate)) {
                return null;
            }
            if (sql.contains(";")) {
                return null;
            }
            return sql;
        }
    }

}
