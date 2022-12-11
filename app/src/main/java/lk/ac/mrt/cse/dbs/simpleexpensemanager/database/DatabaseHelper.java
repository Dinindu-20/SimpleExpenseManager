package lk.ac.mrt.cse.dbs.simpleexpensemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context,"Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(account_no TEXT primary key, bank TEXT, name TEXT, balance REAL)");
        DB.execSQL("create Table Transactiondetails(account_no TEXT primary key,date TEXT,expensetype TEXT,amount REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Userdetails");
        DB.execSQL("drop Table if exists Transactiondetails");
        onCreate(DB);

    }
}
