package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.DatabaseHelper;

public class StableTransactionAccountDAO implements TransactionDAO {
    private DatabaseHelper databaseHelper;


    public StableTransactionAccountDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper= databaseHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_no", accountNo);
        contentValues.put("date", String.valueOf(date));
        contentValues.put("expensetype",String.valueOf(expenseType));
        contentValues.put("amount",amount);
        long result = DB.insert("Transactiondetails",null,contentValues);

    }

    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {
        List<Transaction> transactionList=new ArrayList<>();
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor=DB.rawQuery("SELECT * FROM Tansactiondetails",null);
        if(cursor != null && cursor.moveToFirst()){
            do {
                String acc_no = cursor.getString(cursor.getColumnIndex("account_no"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String expensetype = cursor.getString(cursor.getColumnIndex("expensetype"));
                double amount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount")));
                Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(date);
                ExpenseType expenseType;
                expenseType = ExpenseType.valueOf(expensetype);
                Transaction transaction= new Transaction(date1,acc_no,expenseType,amount);
                transactionList.add(transaction);

            }while(cursor.moveToNext());
            cursor.close();

        }


        return transactionList;


    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {
        List<Transaction> transactionList=new ArrayList<>();
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor=DB.rawQuery("SELECT * FROM Transactiondetails ORDER BY date DESC",null);
        if(cursor != null && cursor.moveToFirst() && limit>0 ){
            do {
                String acc_no = cursor.getString(cursor.getColumnIndex("account_no"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String expensetype = cursor.getString(cursor.getColumnIndex("expensetype"));
                double amount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("amount")));
                Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(date);
                ExpenseType expenseType;
                expenseType = ExpenseType.valueOf(expensetype);
                Transaction transaction= new Transaction(date1,acc_no,expenseType,amount);
                transactionList.add(transaction);
                limit -= 1;

            }while(cursor.moveToNext());
            cursor.close();

        }

        return transactionList;

    }
}
