package com.yaoge.putao.study.hbase;

import com.yaoge.putao.study.common.LoadProperties;
import com.yaoge.putao.study.module.HbaseRow;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HbaseClient {

    private static final Logger logger = LoggerFactory.getLogger(HbaseClient.class);

    private static Configuration configuration;
    private static Connection connection;
    private static Table table;

    //init
    static {
        String zookeepers = LoadProperties.getPropertityValue("hbase.zookeeper.quorum");
        String hbaseMaster = LoadProperties.getPropertityValue("hbase.master");
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zookeepers);
        configuration.set("hbase.master", hbaseMaster);
        try {
            connection = ConnectionFactory.createConnection(configuration);
            logger.info("init connection success");
        } catch (IOException e) {
            logger.error("init connection fail", e);
        }
    }

    // 获得连接
    public static Connection getCon() {
        if (connection == null || connection.isClosed()) {
            try {
                connection = ConnectionFactory.createConnection(configuration);
                logger.info("create connection success");
            } catch (IOException e) {
                logger.error("create connection fail", e);
            }
        }
        return connection;
    }

    // 关闭连接
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.error("close hbase connection success");
            } catch (IOException e) {
                logger.error("close hbase connection fail", e);

            }
        }
    }


    //获得表
    private static Table getTable(String tableName) {
        try {
            Admin admin=getCon().getAdmin();
            if(!admin.tableExists(TableName.valueOf(tableName))){
                return null;
            }
            table = connection.getTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            logger.error("get table fail ", e);
        }
        return table;
    }

    //创建表
    public void createTable(String tableName, String... familyNames) {
        if (StringUtils.isBlank(tableName) || familyNames == null) {
            return;
        }
        try {
            Admin admin=getCon().getAdmin();
            if(!admin.tableExists(TableName.valueOf(tableName))){
                return;
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            admin.createTable(tableDescriptor);
            admin.close();
        } catch (IOException e) {
            logger.error("create table fail ", e);
        }
    }

    //判断一个对象的所有字段是否为空
    public static <T> boolean checkObjectAllFields(T t) {
        if (t == null) {
            return false;
        }
        String object = ToStringBuilder.reflectionToString(t, ToStringStyle.SIMPLE_STYLE);
        //long bankNums = Splitter.on(",").trimResults().splitToList(object).stream().filter(filed -> StringUtils.isBlank(filed)).count();
        //System.out.println(bankNums);
       // return bankNums > 0 ? false : true;
        return false;
    }


    public void put(HbaseRow hbaseRow) {
        if (checkObjectAllFields(hbaseRow)) {
            table = getTable(hbaseRow.getTableName());
            Put put = new Put(Bytes.toBytes(hbaseRow.getRowKey()));
            put.addColumn(Bytes.toBytes(hbaseRow.getFamilyName()), Bytes.toBytes(hbaseRow.getColumnName()), Bytes.toBytes(hbaseRow.getColumnValue()));
            try {
                table.put(put);
            } catch (IOException e) {
                logger.error("put hbaseRow fail ", e);
            } finally {
                try {
                    table.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    public Map<String, String> getOneRow(String tableName, String rowkey) {
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            Table table = getTable(tableName);
//            Get get = new Get(Bytes.toBytes(rowkey));
//            Result result = table.get(get);
//            for (Cell cell : result.rawCells()) {
//                byte[] f = CellUtil.cloneFamily(cell);
//                byte[] q = CellUtil.cloneQualifier(cell);
//                byte[] v = CellUtil.cloneValue(cell);
//                String f_q = Bytes.toString(f) + ":" + Bytes.toString(q);
//                String value = Bytes.toString(v);
//                map.put(f_q, value);
//            }
//            table.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

//    public long rowCount(String tableName) {
//        long count = 0;
//        try {
//            Table table = getTable(tableName);
//            Scan scan = new Scan();
//            scan.setFilter(new FirstKeyOnlyFilter());
//            ResultScanner resultScanner = table.getScanner(scan);
//            for (Result result : resultScanner) {
//                count += result.size();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    public void deleteOneRow(String tableName, String rowKey) {
//        try {
//            Table table = getTable(tableName);
//            Delete delete = new Delete(Bytes.toBytes(rowKey));
//            table.delete(delete);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("-------------------删除一行数据成功----------------");
//    }
//
//    public void dropTable(String tableName) {
//        try {
//            if (!admin.isTableDisabled(TableName.valueOf(tableName))) {
//                admin.disableTable(TableName.valueOf(tableName));
//                admin.deleteTable(TableName.valueOf(tableName));
//                System.out.println("-------------------删除表成功----------------");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public List<String> scanTable(String tableName) {
//        try {
//            Table table = getTable(tableName);
//            Scan scan = new Scan();
//            ResultScanner resultScanner = table.getScanner(scan);
//            List<String> list = new ArrayList<String>();
//            for (Result rs : resultScanner) {
//                for (Cell cell : rs.rawCells()) {
//                    byte[] k = CellUtil.cloneRow(cell);
//                    byte[] f = CellUtil.cloneFamily(cell);
//                    byte[] q = CellUtil.cloneQualifier(cell);
//                    byte[] v = CellUtil.cloneValue(cell);
//                    long t = cell.getTimestamp();
//                    String oneRow= Bytes.toString(k)+"\t"+"\t"+"\t"+"cloumn="+Bytes.toString(f)+":"+ Bytes.toString(q)+", "+"timestamp="+t+", "+"value="+Bytes.toString(v);
//                    System.out.println(oneRow);
//                    list.add(oneRow);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<String> descTable(String tableName, String columnName) {
//        List<String> list = new ArrayList<String>();
//        try {
//            HTableDescriptor tableDesc = admin.getTableDescriptor(TableName.valueOf(tableName));
//            HColumnDescriptor[] columnDesc = tableDesc.getColumnFamilies();
//            for (int i = 0; i < columnDesc.length; i++) {
//                list.add(columnDesc[i].toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    public static void main(String[] args) {
        HbaseClient hbaseClient = new HbaseClient();
//         HbaseRow row = new HbaseRow("testFor", "", "cc", "ddd");
//         hbaseClient.put(row);
    }


}
