package com.fengmap.drpeng.db;

import com.fengmap.android.utils.FMLog;
import com.fengmap.android.wrapmv.db.FMDatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.jdjt.mangrove.entity.Stores;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Stores> queryStoresAll() {
        try {
            return this.mDao.queryForAll();
        } catch (SQLException var2) {
            var2.printStackTrace();
            FMLog.le("FMDBSearchElementDAO#query", var2.getMessage());
            return new ArrayList(0);
        }
    }

    public List<Stores> queryStoresByTypeName(String typeName) {
        try {

            return this.mDao.queryForEq("typename",typeName);
        } catch (SQLException var3) {

            var3.printStackTrace();
            FMLog.le("FMDBSearchElementDAO#query", var3.getMessage());
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
