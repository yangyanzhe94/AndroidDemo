package com.yyz.base.db.appdb.table;

/**
 * Created by Administrator on 2017/7/4.
 */

public final class AppData {
    /**
     * 字段
     */
    public static final class Columns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String TYPE = "type";

    }

    /**
     * 表名
     */
    public static final String TABLE_NAME = "app_data";
    /**
     * 创建表的SQL
     */
    public static final String CREATE_SQL = "create table " + TABLE_NAME
            + " (" + Columns.ID + " integer primary key autoincrement," + Columns.NAME + " varchar(200),"
            + Columns.TYPE + " INTEGER," + ")";

    /**
     * 删除表的SQL
     */
    public static final String DROP_SQL = "drop table if exists " + TABLE_NAME;


    /**
     * 添加一条数据的SQL
     */
    public static final String INSERT_SQL = "insert into " + TABLE_NAME
            + " (" + Columns.NAME + "," + Columns.TYPE + "," + ") values (?,?)";

    /**
     * 根据ID更新数据的SQL
     */
    public static final String UPDATE_SQL = "update " + TABLE_NAME +
            " set " + Columns.TYPE + "=?  where " + Columns.ID + "=?";

    /**
     * 根据ID删除一条历史查询记录数据
     */
    public static final String DELETE_SQL = "delete from " + TABLE_NAME + " where _id=?";


    /**
     * 根据id，查询数据的SQL
     */
    public static final String QUERY_ID_SQL = "select * from " + TABLE_NAME
            + " where " + Columns.ID + "=?";

    /**
     * 根据id，type 查询数据的SQL
     */
    public static final String QUERY_TYPE_SQL_ID = "select * from " + TABLE_NAME
            + " where " + Columns.ID + "=? and " + Columns.TYPE + "=?";

    /**
     * 删除所有数据的SQL
     */
    public static final String DELETE_ALL_SQL = "delete from " + TABLE_NAME;
}
