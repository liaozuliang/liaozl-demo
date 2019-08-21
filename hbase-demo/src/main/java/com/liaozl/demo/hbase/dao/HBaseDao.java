package com.liaozl.demo.hbase.dao;

import com.liaozl.demo.hbase.dao.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description:
 * @version: v1.0.0
 * @author: liaozuliang
 * @date: 2019/8/20 15:43
 */
@Slf4j
@Service
public class HBaseDao {

    @Value("${hbase.zookeeper.quorum}")
    private String hbaseZookeeperQuorum;

    @Value("${hbase.zookeeper.clientPort}")
    private String hbaseZookeeperClientPort;

    private Connection conn;

    public HBaseDao() {

    }

    private Connection getConnection() {
        if (conn == null) {
            synchronized (HBaseDao.class) {
                if (conn == null) {
                    try {
                        Configuration conf = HBaseConfiguration.create();
                        conf.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
                        conf.set("hbase.zookeeper.property.clientPort", hbaseZookeeperClientPort);

                        conn = ConnectionFactory.createConnection(conf);
                    } catch (Exception e) {
                        log.error("create connection error: ", e);
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return conn;
    }

    public boolean tableExists(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            return admin.tableExists(TableName.valueOf(tableName));
        } catch (IOException e) {
            log.error("tableExists error: ", e);
            throw new RuntimeException(e);
        }
    }

    public boolean namespaceExists(String namespace) {
        boolean flag = true;

        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            NamespaceDescriptor namespaceDescriptor = admin.getNamespaceDescriptor(namespace);
            String name = namespaceDescriptor.getName();
            if (name == null) {
                flag = false;
            }
        } catch (NamespaceNotFoundException e) {
            log.error("namespaceExists error: ", e);
            flag = false;
        } catch (IOException e) {
            log.error("namespaceExists error: ", e);
            throw new RuntimeException(e);
        }

        return flag;
    }

    public void deleteNamespace(String namespace) {
        if (namespaceExists(namespace)) {
            try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
                Pattern compile = Pattern.compile(namespace + ":.*");
                admin.disableTables(compile);
                admin.deleteTables(compile);
                admin.deleteNamespace(namespace);
            } catch (IOException e) {
                log.error("deleteNamespace error: ", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void createNamespace(String namespace) {
        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            admin.createNamespace(NamespaceDescriptor.create(namespace).build());
        } catch (IOException e) {
            log.error("createNamespace error: ", e);
            throw new RuntimeException(e);
        }
    }

    public void createTable(String tableName, List<String> familyNames) {
        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            TableDescriptorBuilder.ModifyableTableDescriptor desc = new TableDescriptorBuilder.ModifyableTableDescriptor(TableName.valueOf(tableName));
            // 添加列簇
            if (familyNames != null && familyNames.size() > 0) {
                for (String familyName : familyNames) {
                    ColumnFamilyDescriptor familyDescriptor = new ColumnFamilyDescriptorBuilder.ModifyableColumnFamilyDescriptor(Bytes.toBytes(familyName));
                    desc.setColumnFamily(familyDescriptor);
                }
            }
            admin.createTable(desc);
        } catch (IOException e) {
            log.error("createTable error: ", e);
            throw new RuntimeException(e);
        }
    }

    public void disableTable(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            admin.disableTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            log.error("disableTable error: ", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteTable(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin) getConnection().getAdmin()) {
            if (!admin.tableExists(TableName.valueOf(tableName))) {
                return;
            }

            if (admin.isTableEnabled(TableName.valueOf(tableName))) {
                disableTable(tableName);
            }
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            log.error("deleteTable error: ", e);
            throw new RuntimeException(e);
        }
    }


    public void putRow(String tableName, String rowKey, List<ColumnData> columnDataList) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));

            if (columnDataList != null && columnDataList.size() > 0) {
                for (ColumnData columnData : columnDataList) {
                    put.addColumn(Bytes.toBytes(columnData.getFamilyName()), Bytes.toBytes(columnData.getColumnName()), Bytes.toBytes(columnData.getValue()));
                }
            }

            table.put(put);
        } catch (IOException e) {
            log.error("putRow error: ", e);
            throw new RuntimeException(e);
        }
    }

    public <T extends BaseEntity> T getRow(String tableName, String rowKey, Class<T> tClass) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));

            Result result = table.get(get);
            if (result != null) {
                T t = tClass.newInstance();
                t.setRowKey(Bytes.toString(result.getRow()));
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);

                for (Cell cell : result.listCells()) {
                    String cellName = new String(CellUtil.cloneQualifier(cell));
                    if (!"class".equals(cellName)) {
                        beanWrapper.setPropertyValue(cellName, new String(CellUtil.cloneValue(cell)));
                    }
                }

                return t;
            }
        } catch (Exception e) {
            log.error("getRow error: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public <T extends BaseEntity> List<T> scanRows(String tableName, String rowKeyStartWith, Class<T> tClass) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();

            Filter filter = new PrefixFilter(Bytes.toBytes(rowKeyStartWith));
            //filter = new ValueFilter(CompareOperator.EQUAL, new SubstringComparator(rowKeyStartWith));
            //filter = new SingleColumnValueFilter(Bytes.toBytes("FamilyName"), Bytes.toBytes("ColumnName"), CompareOperator.EQUAL, new RegexStringComparator(".*2322"));
            scan.setFilter(filter);

            ResultScanner results = table.getScanner(scan);
            if (results != null) {
                List<T> rowDataList = new ArrayList<>();

                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    Result result = iterator.next();
                    if (result != null) {
                        T t = tClass.newInstance();
                        t.setRowKey(Bytes.toString(result.getRow()));
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);

                        for (Cell cell : result.listCells()) {
                            String cellName = new String(CellUtil.cloneQualifier(cell));
                            if (!"class".equals(cellName)) {
                                beanWrapper.setPropertyValue(cellName, new String(CellUtil.cloneValue(cell)));
                            }
                        }

                        rowDataList.add(t);
                    }
                }

                return rowDataList;
            }
        } catch (Exception e) {
            log.error("scanRows error: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public <T extends BaseEntity> List<T> scanRows(String tableName, Class<T> tClass) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));

            Scan scan = new Scan();
            ResultScanner results = table.getScanner(scan);
            if (results != null) {
                List<T> rowDataList = new ArrayList<>();

                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    Result result = iterator.next();
                    if (result != null) {
                        T t = tClass.newInstance();
                        t.setRowKey(Bytes.toString(result.getRow()));
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);

                        for (Cell cell : result.listCells()) {
                            String cellName = new String(CellUtil.cloneQualifier(cell));
                            if (!"class".equals(cellName)) {
                                beanWrapper.setPropertyValue(cellName, new String(CellUtil.cloneValue(cell)));
                            }
                        }

                        rowDataList.add(t);
                    }
                }

                return rowDataList;
            }
        } catch (Exception e) {
            log.error("scanRows error: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public <T extends BaseEntity> List<T> scanRows(String tableName, List<ColumnData> equalColumnConditions, Class<T> tClass) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();


            FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);

            if (equalColumnConditions != null && equalColumnConditions.size() > 0) {
                for (ColumnData columnData : equalColumnConditions) {
                    SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes(columnData.getFamilyName()), Bytes.toBytes(columnData.getColumnName()), CompareOperator.EQUAL, new SubstringComparator(columnData.getValue()));

                    filter.setFilterIfMissing(true);
                    filter.setLatestVersionOnly(true);

                    filters.addFilter(filter);
                }
            }

            scan.setFilter(filters);
            ResultScanner results = table.getScanner(scan);

            if (results != null) {
                List<T> rowDataList = new ArrayList<>();

                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    Result result = iterator.next();
                    if (result != null) {
                        T t = tClass.newInstance();
                        t.setRowKey(Bytes.toString(result.getRow()));
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);

                        for (Cell cell : result.listCells()) {
                            String cellName = new String(CellUtil.cloneQualifier(cell));
                            if (!"class".equals(cellName)) {
                                beanWrapper.setPropertyValue(cellName, new String(CellUtil.cloneValue(cell)));
                            }
                        }

                        rowDataList.add(t);
                    }
                }

                return rowDataList;
            }
        } catch (Exception e) {
            log.error("scanRows error: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public <T extends BaseEntity> List<T> scanRows(String tableName, String startRowKey, int pageSize, Class<T> tClass) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();

            Filter pageFilter = new PageFilter(pageSize);
            scan.setFilter(pageFilter);

            if (StringUtils.isNotBlank(startRowKey)) {
                scan.withStartRow(Bytes.toBytes(startRowKey), false);
            }

            ResultScanner results = table.getScanner(scan);

            if (results != null) {
                List<T> rowDataList = new ArrayList<>();

                Iterator<Result> iterator = results.iterator();
                while (iterator.hasNext()) {
                    Result result = iterator.next();
                    if (result != null) {
                        T t = tClass.newInstance();
                        t.setRowKey(Bytes.toString(result.getRow()));
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(t);

                        for (Cell cell : result.listCells()) {
                            String cellName = new String(CellUtil.cloneQualifier(cell));
                            if (!"class".equals(cellName)) {
                                beanWrapper.setPropertyValue(cellName, new String(CellUtil.cloneValue(cell)));
                            }
                        }

                        rowDataList.add(t);
                    }
                }

                return rowDataList;
            }
        } catch (Exception e) {
            log.error("scanRows error: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    public void deleteRow(String tableName, String rowKey) {
        try {
            HTable table = (HTable) getConnection().getTable(TableName.valueOf(tableName));
            Delete del = new Delete(Bytes.toBytes(rowKey));
            table.delete(del);
        } catch (IOException e) {
            log.error("deleteRow error: ", e);
            throw new RuntimeException(e);
        }
    }

}