package com.yaoge.putao.study.spark;

import com.yaoge.putao.study.common.LoadProperties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Level;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RddTest {

    private static final Logger logger = LoggerFactory.getLogger(RddTest.class);
    private static SparkConf sc;
    private static JavaSparkContext jsc;
    //  private static SparkSession ss;

    static {
        String master = LoadProperties.getPropertityValue("spark.master");
        String appname = LoadProperties.getPropertityValue("spark.appname");
        org.apache.log4j.Logger.getLogger("org").setLevel(Level.ERROR);
        sc = new SparkConf().setAppName(appname);
        sc.setMaster("local");
        jsc = new JavaSparkContext(sc);
        //   ss = SparkSession.builder().config(sc).enableHiveSupport().getOrCreate();
        logger.info("init success");
    }


    @Test
    public void rdd_1() {
        JavaRDD<String> dataSourceRDD = jsc.textFile("/Users/luyao/codePlay/day_day_up/bigData/src/main/java/com/yaoge/putao/study/spark/data2.txt");
        //dataSourceRDD.map(x-> x.split(" ")[1]).collect().forEach(x ->System.out.println(x));
        //dataSourceRDD.map(x-> x.split(" ")[1]).mapToPair(x ->new Tuple2<>(x,1)).foreach(x -> System.out.println(x));

    }

    @Test
    public void rdd_2() {

        Configuration hbaseConf = HBaseConfiguration.create();
        hbaseConf.set("hbase.zookeeper.quorum", "10.37.129.94");
        hbaseConf.set("hbase.master", "10.37.129.94");
        String tableName = "testFor";
        hbaseConf.set(TableInputFormat.INPUT_TABLE, tableName);
        JavaPairRDD<ImmutableBytesWritable, Result> hbaseRDD = jsc.newAPIHadoopRDD(hbaseConf, TableInputFormat.class, ImmutableBytesWritable.class, Result.class);

        hbaseRDD.map(x -> x._2()).foreach(result -> {
            Arrays.stream(result.rawCells()).forEach(cell -> {
                byte[] rowKey = CellUtil.cloneRow(cell);
                byte[] family = CellUtil.cloneFamily(cell);
                byte[] cloumnName = CellUtil.cloneQualifier(cell);
                byte[] cloumnValue = CellUtil.cloneValue(cell);
                String cell_= "行键："+Bytes.toString(rowKey)+" 列簇："+Bytes.toString(family)+" 列："+Bytes.toString(cloumnName)+" 列值："+ Bytes.toString(cloumnValue);
                System.out.println(cell_);
            });
            System.out.println("------------------");
        });

    }
}
