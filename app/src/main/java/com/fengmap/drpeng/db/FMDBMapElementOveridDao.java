package com.fengmap.drpeng.db;

import android.util.Log;

import com.fengmap.android.utils.FMLog;
import com.fengmap.android.wrapmv.db.FMDatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;
import com.jdjt.mangrove.entity.Stores;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wmy
 * @Description:
 * @FileName:FMDBMapElementOveridDao
 * @Package com.fengmap.drpeng.db
 * @Date 2017/3/15 19:18
 */
public class FMDBMapElementOveridDao  {
    private Dao<Stores, Integer> mDao;

    public FMDBMapElementOveridDao() {
        try {
            this.mDao = FMDatabaseHelper.getDatabaseHelper().getDAO(Stores.class);
        } catch (SQLException var2) {
            FMLog.le("FMDBSearchElementDAO", var2.getMessage());
        }

    }

    /**
     * 查询所有数据
     * @return
     */
    public List<Stores> queryStoresAll() {
        try {
            return this.mDao.queryForAll();
        } catch (SQLException var2) {
            var2.printStackTrace();
            FMLog.le("FMDBSearchElementDAO#query", var2.getMessage());
            return new ArrayList(0);
        }
    }

    /**
     * 根据 typename 查询数据
     * @param typeName 类型名称
     * @return
     */
    public List<Stores> queryStoresByTypeName(String typeName) {
        try {

            return this.mDao.queryForEq("typename",typeName);
        } catch (SQLException var3) {

            var3.printStackTrace();
            FMLog.le("FMDBSearchElementDAO#query", var3.getMessage());
            return new ArrayList(0);
        }
    }

    /**
     * 分页搜索
     * @param name 产品名称
     * @param offset  = page*count
     * @return
     */
    public List<Stores> queryStoresByName(String name, long offset) {
        try {

            QueryBuilder pE = this.mDao.queryBuilder();
            pE.offset(Long.valueOf(offset)).limit(Long.valueOf(10L));
            Where where = pE.where();
            where.like("name", "%" + name + "%");
            PreparedQuery pq = pE.prepare();
            Ioc.getIoc().getLogger().e( pq.getStatement());
            return this.mDao.query(pq);
        } catch (SQLException var3) {

            var3.printStackTrace();
            FMLog.le("queryStoresByName", var3.getMessage());
            return new ArrayList(0);
        }
    }

    /**
     * 根据不同属性查询数据
     * @param mapId 地图id
     * @param groupId 组id
     * @param typename 二级标签名称
     * @param offset  显示页数  page*count
     * @return
     */
    public List<Stores> queryPrioritySubTypename(String mapId, int groupId, String typename, long offset) {
        try {
            if(mapId != null && !mapId.equals("") && groupId > 0) {
                String pE1 = "SELECT *,1 as orderid from stores WHERE subtypename like \'%" + typename + "%\' and mid = \'" + mapId + "\' and gid = " + groupId + " union " + " SELECT *,2 as orderid from stores WHERE subtypename like \'%" + typename + "%\' and mid = \'" + mapId + "\' and gid != " + groupId + " union " + " SELECT *,3 as orderid from stores WHERE subtypename like \'%" + typename + "%\' and mid != \'" + mapId + "\'" + " order by orderid asc" + " limit " + 10L + " offset " + offset;
                GenericRawResults where1 = this.mDao.queryRaw(pE1, new DataType[]{DataType.INTEGER, DataType.STRING, DataType.STRING, DataType.INTEGER, DataType.STRING, DataType.STRING, DataType.STRING, DataType.LONG, DataType.STRING, DataType.DOUBLE, DataType.DOUBLE, DataType.FLOAT, DataType.STRING, DataType.STRING, DataType.STRING}, new String[0]);

                List pq1 = where1.getResults();
                ArrayList elements = new ArrayList(pq1.size());
                Iterator var10 = pq1.iterator();

                while(var10.hasNext()) {
                    Object[] o = (Object[])var10.next();
                    Stores e = new Stores();
                    e.setId(((Integer)o[0]).intValue());
                    e.setMid((String)o[1]);
                    e.setFid( (String)o[2]);
                    e.setGid(((Integer)o[3]).intValue());
                    e.setFtype((String)o[4]);
                    e.setName((String)o[5]);
                    e.setEname((String)o[6]);
                    e.setType( ((Long)o[7]).longValue());
                    e.setTypename((String)o[8]);
                    e.setX(((Double)o[9]).doubleValue());
                    e.setY( ((Double)o[10]).doubleValue());
                    e.setZ(((Float)o[11]).floatValue());
                    e.setAddress((String)o[12]);
                    e.setSubtypename((String)o[13]);
                    e.setActivitycode((String)o[14]);
                    elements.add(e);
                }

                return elements;
            } else {
                QueryBuilder pE = this.mDao.queryBuilder();
                pE.offset(Long.valueOf(offset)).limit(Long.valueOf(10L));
                Where where = pE.where();
                where.like("subtypename", "%" + typename + "%");
                PreparedQuery pq = pE.prepare();
                Ioc.getIoc().getLogger().e( pq.getStatement());
                return this.mDao.query(pq);
            }
        } catch (SQLException var13) {
            FMLog.le("FMDBMapElementDAO#queryTypename", var13.getMessage());
            return new ArrayList(0);
        }
    }
    public void clear() {
        try {
            TableUtils.createTable(this.mDao);
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public boolean isSearchHistoryExist(String historyName) {
        return this.queryStoresByTypeName(historyName).size() > 0;
    }
}
