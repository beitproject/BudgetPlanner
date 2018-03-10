package planner.budget.budgetplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String INCOME_ID = "ID";
    public static final String INCOME_AMT = "amount";
    public static final String INCOME_DESC = "DESCRIPTION";
    public static final String INCOME_CATEGORY = "CATEGORY";
    public static final String INCOME_DATE = "date";

    public DatabaseHelper(Context context/*, String name, SQLiteDatabase.CursorFactory factory, int version*/) {
        super(context, DATABASE_NAME, null, 2); //Change version number when new table is made or edited
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS " + EXPENSE_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE TEXT)");
        db.execSQL("create table IF NOT EXISTS "+ INCOME_TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT FLOAT, DESCRIPTION TEXT, CATEGORY TEXT, DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ EXPENSE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+INCOME_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(float amount, String description, String category, String date ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXPENSE_AMT,amount);
        contentValues.put(EXPENSE_DESC,description);
        contentValues.put(EXPENSE_CATEGORY,category);
        contentValues.put(EXPENSE_DATE, date);
        long result= db.insert(EXPENSE_TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean income_insertData(float amount, String description, String category, String date){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(INCOME_AMT,amount);
        contentValues.put(INCOME_DESC,description);
        contentValues.put(INCOME_CATEGORY,category);
        contentValues.put(INCOME_DATE,date);
        long result = db.insert(INCOME_TABLE_NAME, null, contentValues);
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

    public Cursor income_getData(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor_income = db.rawQuery("Select * from "+ INCOME_TABLE_NAME +" GROUP BY ID ORDER BY (ID) DESC ",null);
        return cursor_income;
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
}
