package planner.budget.budgetplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.commons.collections4.splitmap.AbstractIterableGetMapDecorator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rohit on 27-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "budgetplanner.db";
    public static final String EXPENSE_TABLE_NAME = "expense_table";
    public static final String INCOME_TABLE_NAME = "income_table";
    public static final String EXPENSE_ID = "ID";
    public static final String EXPENSE_AMT = "amount";
    public static final String EXPENSE_DESC = "DESCRIPTION";
    public static final String EXPENSE_CATEGORY = "CATEGORY";
    public static final String EXPENSE_DATE = "date";
    //public static Date EXPENSE_DATE = null;
    public static final String INCOME_ID = "ID";
    public static final String INCOME_AMT = "amount";
    public static final String INCOME_DESC = "DESCRIPTION";
    public static final String INCOME_CATEGORY = "CATEGORY";
    public static final String INCOME_DATE = "date";
    public static final String BALANCE_TABLE_NAME = "balance_table";
    public static final String BALANCE_ID = "ID";
    public static final String BALANCE_TXNTYPE = "txntype";
    public static final String BALANCE_TXNAMT = "txnamt";
    public static final String BALANCE_CATEGORY = "CATEGORY";
    public static final String BALANCE_DATE = "date";
    public static final String BALANCE_AMT = "balance";

    public DatabaseHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, 5); //Change version number when new table is made or edited
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table IF NOT EXISTS " + EXPENSE_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE TEXT)");
        db.execSQL("create table IF NOT EXISTS " + EXPENSE_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE DATE)");
        //db.execSQL("create table IF NOT EXISTS "+ INCOME_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE TEXT)");
        db.execSQL("create table IF NOT EXISTS "+ INCOME_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE DATE)");
        db.execSQL("create table IF NOT EXISTS "+ BALANCE_TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TXNTYPE TEXT, TXNAMT FLOAT, CATEGORY TEXT, DATE DATE, BALANCE FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ EXPENSE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+INCOME_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+BALANCE_TABLE_NAME);
        onCreate(db);
    }


    // to insert data into expense_table
    public boolean insertData(float amount, String description, String category, Date date ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_AMT,amount);
        contentValues.put(EXPENSE_DESC,description);
        contentValues.put(EXPENSE_CATEGORY,category);
        //contentValues.put(EXPENSE_DATE, date);   //Date.valueof(date)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
        //DateFormat dateFormat = DateFormat.getDateTimeInstance();
        contentValues.put(EXPENSE_DATE, dateFormat.format(date));
        long result= db.insert(EXPENSE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    //***to insert data into income_table
    public boolean income_insertData(float amount, String description, String category, Date date){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOME_AMT,amount);
        contentValues.put(INCOME_DESC,description);
        contentValues.put(INCOME_CATEGORY,category);
        //contentValues.put(INCOME_DATE,date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
        contentValues.put(INCOME_DATE, dateFormat.format(date));
        long result = db.insert(INCOME_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
        }

        //****to insert data into balance
    public boolean balance_insertData(String txntype, float txnamt, String category, Date bal_date, float balance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE_TXNTYPE,txntype);
        contentValues.put(BALANCE_TXNAMT,txnamt);
        contentValues.put(BALANCE_CATEGORY,category);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
        contentValues.put(BALANCE_DATE,dateFormat.format(bal_date));
        contentValues.put(BALANCE_AMT,balance);
        long result = db.insert(BALANCE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean balance_initinsertData(String init_txntype, float init_txnamt, String init_category, float init_bal){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BALANCE_TXNTYPE,init_txntype);
        contentValues.put(BALANCE_TXNAMT,init_txnamt);
        contentValues.put(BALANCE_CATEGORY,init_category);
        contentValues.put(BALANCE_DATE,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        contentValues.put(BALANCE_AMT, init_bal);
        long result = db.insert(BALANCE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

        //for displaying Expense Table data
    public Cursor getData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + EXPENSE_TABLE_NAME +" GROUP BY ID ORDER BY (ID) DESC ",null);
        return cursor;

    }

    //****for displaying Income Table data
    public Cursor income_getData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor_income = db.rawQuery("Select * from "+ INCOME_TABLE_NAME +" GROUP BY ID ORDER BY (ID) DESC ",null);
        return cursor_income;
    }

    //****for displaying Balance Table data
    public Cursor balance_getData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor_balance = db.rawQuery("Select * from " +BALANCE_TABLE_NAME, null);
        return cursor_balance;
    }

    // *******To export expense data to csv*******
    public Cursor csvexpense_getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select AMOUNT,DATE from "+ EXPENSE_TABLE_NAME ,null);
        return c;
    }


    //test method to retrieve category String
   public Cursor getCategoryString(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor_category=db.rawQuery("Select CATEGORY from " +EXPENSE_TABLE_NAME,null);
        return cursor_category;

    }

    public Cursor getIncomeCategoryString(){
       SQLiteDatabase db= this.getWritableDatabase();
       Cursor cursor_category=db.rawQuery("Select CATEGORY from " +INCOME_TABLE_NAME, null);
       return cursor_category;
    }

    /*public Cursor getListItemId(String obj){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "Select ID from "+EXPENSE_TABLE_NAME+" WHERE ";
    }*/

    //To delete expense data
    public void deleteSelectedData(int selectedId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+EXPENSE_TABLE_NAME+ " where " +EXPENSE_ID+ " = ' "+selectedId+ " ' ");
        Log.d("Database Helper","Record Deleted");
    }

    //To delete income data
    public void deleteIncomeSelectedData(int selectedId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from "+INCOME_TABLE_NAME+ " where " +INCOME_ID+ " = ' "+selectedId+ " '");
        Log.d("Database Helper","Record Deleted");
    }


    //To update Expense data on Edited by user
    public void updateExpenseData(int selectedId,Float edited_amt, String edited_desc, String edited_category, Date editeddate){
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");

        String query = "UPDATE "+EXPENSE_TABLE_NAME+ " SET AMOUNT = "+edited_amt+ " , DESCRIPTION = ' "+edited_desc+ " ' , " +
                        " CATEGORY = ' "+edited_category+ " ', DATE = '"+dateFormat.format(editeddate)+ " ' WHERE ID = ' "+selectedId+ " '";
        db.execSQL(query);
    }

    //To update Income data on Edited by user
    public void updateIncomeData(int selectedId,Float edited_amt, String edited_desc, String edited_category, Date editeddate){
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");

        String query = "UPDATE "+INCOME_TABLE_NAME+ " SET AMOUNT = "+edited_amt+ " , DESCRIPTION = ' "+edited_desc+ " ' , " +
                " CATEGORY = ' "+edited_category+ " ', DATE = '"+dateFormat.format(editeddate)+ " ' WHERE ID = ' "+selectedId+ " '";
        db.execSQL(query);

    }

    //******for testing balance module*****
    public void deletebalance(int searchid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+BALANCE_TABLE_NAME+" where ID ='" +searchid+ "'" ;
        db.execSQL(query);
    }

    //*****For retrieving ID of edited expense from Balance table****
    public Cursor getBalanceId(Date search_baldate){
        SQLiteDatabase db =this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
        String query="Select "+BALANCE_ID+","+BALANCE_TXNAMT+" from "+BALANCE_TABLE_NAME+ " where DATE = '"+dateFormat.format(search_baldate)+"'" ;
        //String query="Select ID from "+BALANCE_TABLE_NAME+ " where CATEGORY = 'Bills/Utilities'";
        Cursor data=db.rawQuery(query,null);
        return data;
    }


    //**To get Current Balance****
    public Cursor getCurrentBalance(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="Select BALANCE from "+BALANCE_TABLE_NAME;
        Cursor cursor_current_bal=db.rawQuery(query,null);
        return cursor_current_bal;
    }

    //*****To delete all balance records******
    public void deleteAllBalance(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from "+BALANCE_TABLE_NAME;
        db.execSQL(query);
    }

    //***********

    //*****************To delete all cash records******
    public void deleteAllCash(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete from "+EXPENSE_TABLE_NAME;
        db.execSQL(query);
    }
    //*********************

    //*To get prev txnamt from Balance table*******************
    public Cursor getPrevTxnamt(int searchid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select TXNAMT from "+BALANCE_TABLE_NAME+" where ID = '"+searchid+"' ";
        Cursor bal_txnamt = db.rawQuery(query,null);
        return bal_txnamt;
    }

    //**********************To get Balance ID to Update Balance last row on Expense/Income delete
    public Cursor getLastBalanceId(){
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "Select ID from "+BALANCE_TABLE_NAME;
        Cursor cursor_last_bal = db.rawQuery(query,null);
        return cursor_last_bal;
    }
    //********************************************************


    //***********to Update Balance last row on Expense/Income delete
    public void updateLastBalance(float balupdate_balance,int last_bal_id){
        SQLiteDatabase db= this.getWritableDatabase();
        String query= "Update "+BALANCE_TABLE_NAME+" set "+BALANCE_AMT+" = "+balupdate_balance+" where ID =' "+last_bal_id+"' ";
        db.execSQL(query);

    }

    //*******************************

    //****To update Balance to 0 after deleting all records
    public void updateBalanceOnAllDataDelete(int balance_id,float balupdate_bal){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Update "+BALANCE_TABLE_NAME+" set "+BALANCE_AMT+" = "+balupdate_bal+" where ID ='"+balance_id+ "' ";
        db.execSQL(query);
    }
}
