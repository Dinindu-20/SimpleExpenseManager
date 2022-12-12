package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.database.DatabaseHelper;


public class StableMemoryAccountDAO implements AccountDAO{
    private DatabaseHelper databaseHelper;

    public StableMemoryAccountDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accnumber = new ArrayList<>();
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT account_no FROM UserDetails ",null);
        if(cursor!=null && cursor.moveToFirst()  ){
            do {
                accnumber.add(cursor.getString(cursor.getColumnIndex("account_no")));
            }while(cursor.moveToNext());
            cursor.close();
            
        }
        return accnumber;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts=new ArrayList<>();
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails ",null);
        if(cursor!=null && cursor.moveToFirst()){
            do {
                String acc_no = cursor.getString(cursor.getColumnIndex("account_no"));
                String bank = cursor.getString(cursor.getColumnIndex("bank"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double balance = Double.parseDouble(cursor.getString(cursor.getColumnIndex("balance")));
                Account account = new Account(acc_no,bank,name,balance);
                accounts.add(account);
            }while(cursor.moveToNext());
            cursor.close();

        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM UserDetails WHERE account_no=?", new String[]{accountNo}, null);
        Account account = null;
        if ( cursor != null && cursor.moveToFirst()) {
            do {
                String acc_no = cursor.getString(cursor.getColumnIndex("account_no"));
                String bank = cursor.getString(cursor.getColumnIndex("bank"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double balance = Double.parseDouble(cursor.getString(cursor.getColumnIndex("balance")));
                account = new Account(acc_no, bank, name, balance);

            } while (cursor.moveToNext());
            cursor.close();

        }

        return account;

    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("account_no", account.getAccountNo());
        contentValues.put("bank", account.getBankName());
        contentValues.put("name", account.getAccountHolderName());
        contentValues.put("balance", account.getBalance());
        long results = DB.insert("Userdetails", null, contentValues);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase DB = databaseHelper.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE account_no = ?",new String[]{accountNo},null);
        if (cursor.getCount() >0){
            long result = DB.delete("Userdetails","account_no=?",new String[]{accountNo});
        }
        cursor.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase DB = this.databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor;
        cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE account_no= ?", new String[]{accountNo},null);
        if (cursor != null && cursor.moveToFirst()){
            String Initial= cursor.getString(cursor.getColumnIndex("balance"));
            double values = Double.parseDouble(Initial);
            if (expenseType == ExpenseType.EXPENSE && amount >= values ){
                contentValues.put("balance",amount-values);

            }
            else{
                contentValues.put("balance",amount+values);
            }
            cursor.close();

            DB.insert("Userdetails",null,contentValues);
        }




    }


}
