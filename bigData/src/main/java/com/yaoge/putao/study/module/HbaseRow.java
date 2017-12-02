package com.yaoge.putao.study.module;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class HbaseRow {

    private String tableName;
    private String rowKey;
    private String familyName;
    private String columnName;
    private String columnValue;

    public HbaseRow() {
    }

    public HbaseRow(String tableName, String rowKey, String familyName, String columnName, String columnValue) {
        this.tableName = tableName;
        this.rowKey = rowKey;
        this.familyName = familyName;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public String getTableName() {
        return tableName;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

//    public static void main(String[] args){
//        System.out.println(new HbaseRow("aa", "bb", "cc", "ddd"));
//    }


}
