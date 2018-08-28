package com.imei666.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.imei666.android.mvp.model.dto.DaoMaster;
import com.imei666.android.mvp.model.dto.DaoSession;


public class DBUtil {

    private static DBUtil sInstance;
    private static DaoSession daoSession;
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase sqLiteDatabase;
    private static DaoMaster daoMaster;

    private DBUtil(){

    }

    public static synchronized DBUtil getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new DBUtil();
            helper = new DaoMaster.DevOpenHelper(ctx,"db_imei",null);
            sqLiteDatabase = helper.getWritableDatabase();
            daoMaster =   new DaoMaster(sqLiteDatabase);
            daoSession = daoMaster.newSession();
        }
        return sInstance;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
