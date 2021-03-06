package planner.budget.budgetplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Locale;

public class AddExpense extends AppCompatActivity {

    public ArrayList<SpinnerItem> mSpinnerList;
    public Spinner_Adapter mAdapter;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor_balance;
    Button btn;
    int year_x, month_x, day_x;
    static final int Dialog_Id = 0;
    private FloatingActionButton mFABexpense;
    DatabaseHelper dbhelper;
    EditText amt,desc, edit_category, edit_date;
    String clickedItemName;
    Date date;
    String FINAL_DATE,YEAR,MONTH,DAY;
    float floatamt;

    MainActivity mainActivity;//MainActivity class object



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        //database call
        dbhelper = new DatabaseHelper(this);
        sqLiteDatabase = dbhelper.getReadableDatabase();
        cursor_balance = dbhelper.balance_getData();


        amt = (EditText)findViewById(R.id.expense_editamt);
        desc = (EditText)findViewById(R.id.expense_editspend);
        mFABexpense = (FloatingActionButton) findViewById(R.id.expense_submit_btn);

        //initbalance(); //initialize balance func call

        addData();      //method call to add data to db

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        initList(); //Spinner function call

        showDialogOnButtonClick();  //Calendar function call

       final Spinner spinnerItems = (Spinner) findViewById(R.id.spinner);

        spinnerItems.setPrompt("Select a Category");


        mAdapter = new Spinner_Adapter(this, mSpinnerList);
        spinnerItems.setAdapter(mAdapter);

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinnerItems.setSelection(48); //default Category selection
                SpinnerItem clickedItem = (SpinnerItem) parent.getItemAtPosition(position);
                clickedItemName = clickedItem.getCategoryName();
                Toast.makeText(AddExpense.this, clickedItemName + "\t Category Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //to implement Submit Floating Action Button


        /*mFABexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expense_intent = new Intent(AddExpense.this, NavDrawer_SpendSummary.class);
                startActivity(expense_intent);
            }
        });*/

    }

    //Method for adding data to database
      public void addData(){
        mFABexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatamt = Float.valueOf(amt.getText().toString());
                YEAR = Integer.toString(year_x);
                MONTH = Integer.toString(month_x);
                //*****For month format mm to (2->02)
                if(MONTH.length()==1){
                    String for_month="0";
                    MONTH=for_month.concat(MONTH);
                }
                //************************************

                DAY = Integer.toString(day_x);

                //******For day format dd to(2->02)
                if(DAY.length()==1){
                    String for_day="0";
                    DAY=for_day.concat(DAY);
                }
                //********************************
                FINAL_DATE = YEAR+"-"+MONTH+"-"+DAY;
                Log.d("FINAL DATE",FINAL_DATE);

                //****for appending unique date to datepicker output
                //Date dateobj = Calendar.getInstance().getTime();
                Date dateobj = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
                String time=dateFormat.format(dateobj);
                Log.d("TIME---->",time);
                //****************************

                //***********for cropping only time out of total date****
                time = time.substring(10,19);
                Log.d("Edited TIME->>",time);
                //*******************************

                FINAL_DATE = FINAL_DATE.concat(time);
                Log.d("************",FINAL_DATE);
                //String Dateupdate = FINAL_DATE.concat(time);
                Log.d("FINAL DATE->",FINAL_DATE);
                //FINAL_DATE = "2018-02-29 18:55:55";
                try {
                    date = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(FINAL_DATE);

                    Log.d("DATE-->",String.valueOf(date));
                    //Log.d("TestTime->",testtime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                    boolean isInserted = dbhelper.insertData(floatamt, desc.getText().toString(), clickedItemName, date);
                    if (isInserted = true)
                        Toast.makeText(AddExpense.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(AddExpense.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

                    //*************To update Balance****
                    addBalanceData();

                //Toast.makeText(AddExpense.this,"Data is Empty Cannot be Inserted",Toast.LENGTH_SHORT).show();

                //**************************************
                //On data inserted redirect to display Cash Transactions
                    Intent tocash = new Intent(AddExpense.this,NavDrawer_Cash.class);
                    startActivity(tocash);
                    finish();
            }
        });
    }

    public void addBalanceData(){
          String txntype = "Debit";
          float txnamt = floatamt;
          String category = clickedItemName;
          Date bal_date = date;
          float balance = 0;
          float prev_bal;

        //***To check initial balance data
          if(cursor_balance.moveToFirst()){
              cursor_balance.moveToLast();
              prev_bal=cursor_balance.getFloat(5);
              balance = prev_bal - txnamt;  //last value of balance from db - expense amt
          }else{
            cursor_balance.moveToFirst();
            prev_bal=cursor_balance.getFloat(5);
            balance = prev_bal - txnamt;
          }
          //****************************************************

        boolean isInserted = dbhelper.balance_insertData(txntype,txnamt,category,bal_date,balance);
          if(isInserted=true)
              Toast.makeText(AddExpense.this,"Balance Updated",Toast.LENGTH_SHORT).show();
          else
              Toast.makeText(AddExpense.this, "Balance not Updated",Toast.LENGTH_SHORT).show();

          MainActivity.displayCurrentBalance();         //To update Homepage-Balance on new Expense

    }

   /* public void initbalance(){
        ///*****for initializing Balance data
        if(cursor_balance.getCount() == 0){
            //**values for initialize balance
            String init_txntype = "INIT";
            float init_txnamt = 0;
            String init_category = "Others";
            float init_bal = 0;
            boolean isInserted = dbhelper.balance_initinsertData(init_txntype,init_txnamt,init_category,init_bal);
            if(isInserted=true)
                Log.d("Init data ","inserted");
            else
                Log.d("Init data","not inserted");

        }
    }*/

    //Method to add elements to Spinner
    public void initList(){
        mSpinnerList = new ArrayList<>();
        mSpinnerList.add(new SpinnerItem("Acc to Acc", R.drawable.vc_acc_to_acc));
        mSpinnerList.add(new SpinnerItem("Air Tickets", R.drawable.vc_air_tickets));
        mSpinnerList.add(new SpinnerItem("Beauty", R.drawable.vc_beauty));
        mSpinnerList.add(new SpinnerItem("Bike", R.drawable.vc_bike));
        mSpinnerList.add(new SpinnerItem("Bills/Utilities", R.drawable.vc_bills_uilities));
        mSpinnerList.add(new SpinnerItem("Books", R.drawable.vc_books));
        mSpinnerList.add(new SpinnerItem("Bus Fare", R.drawable.vc_bus_fare));
        mSpinnerList.add(new SpinnerItem("Business", R.drawable.vc_business));
        mSpinnerList.add(new SpinnerItem("Cable", R.drawable.vc_cable));
        mSpinnerList.add(new SpinnerItem("Car", R.drawable.vc_car));
        mSpinnerList.add(new SpinnerItem("Cash", R.drawable.vc_cash));
        mSpinnerList.add(new SpinnerItem("CC Bill Payment", R.drawable.vc_credit_card));
        mSpinnerList.add(new SpinnerItem("Cigarettes", R.drawable.vc_cigarettes));
        mSpinnerList.add(new SpinnerItem("Coffee", R.drawable.vc_coffee));
        mSpinnerList.add(new SpinnerItem("Courier", R.drawable.vc_courier));
        mSpinnerList.add(new SpinnerItem("Daily Care", R.drawable.vc_daily_care));
        mSpinnerList.add(new SpinnerItem("Dining", R.drawable.vc_dinner));
        mSpinnerList.add(new SpinnerItem("Drinks", R.drawable.vc_drinks));
        mSpinnerList.add(new SpinnerItem("Education", R.drawable.vc_education));
        mSpinnerList.add(new SpinnerItem("Electricity", R.drawable.vc_electricity));
        mSpinnerList.add(new SpinnerItem("Electronics", R.drawable.vc_electronics));
        mSpinnerList.add(new SpinnerItem("EMI", R.drawable.vc_emi));
        mSpinnerList.add(new SpinnerItem("Entertainment", R.drawable.vc_entertainment));
        mSpinnerList.add(new SpinnerItem("Finance", R.drawable.vc_finance));
        mSpinnerList.add(new SpinnerItem("Fuel", R.drawable.vc_fuel));
        mSpinnerList.add(new SpinnerItem("Games", R.drawable.vc_games));
        mSpinnerList.add(new SpinnerItem("Gift", R.drawable.vc_gift));
        mSpinnerList.add(new SpinnerItem("Gym", R.drawable.vc_gym));
        mSpinnerList.add(new SpinnerItem("Health", R.drawable.vc_health));
        mSpinnerList.add(new SpinnerItem("Hotel", R.drawable.vc_hotel));
        mSpinnerList.add(new SpinnerItem("Household", R.drawable.vc_household));
        mSpinnerList.add(new SpinnerItem("Insurance", R.drawable.vc_insurance));
        mSpinnerList.add(new SpinnerItem("Investments", R.drawable.vc_investments));
        mSpinnerList.add(new SpinnerItem("Kids", R.drawable.vc_kids));
        mSpinnerList.add(new SpinnerItem("Loan", R.drawable.vc_loan));
        mSpinnerList.add(new SpinnerItem("Maintenance", R.drawable.vc_maintenance));
        mSpinnerList.add(new SpinnerItem("Mobile", R.drawable.vc_mobile));
        mSpinnerList.add(new SpinnerItem("Music", R.drawable.vc_music));
        mSpinnerList.add(new SpinnerItem("Office", R.drawable.vc_office));
        mSpinnerList.add(new SpinnerItem("Rent", R.drawable.vc_rent));
        mSpinnerList.add(new SpinnerItem("Salon", R.drawable.vc_salon));
        mSpinnerList.add(new SpinnerItem("Savings", R.drawable.vc_savings));
        mSpinnerList.add(new SpinnerItem("Shopping", R.drawable.vc_shopping));
        mSpinnerList.add(new SpinnerItem("Tax", R.drawable.vc_tax));
        mSpinnerList.add(new SpinnerItem("Train", R.drawable.vc_train));
        mSpinnerList.add(new SpinnerItem("Travel", R.drawable.vc_travel));
        mSpinnerList.add(new SpinnerItem("Vacation", R.drawable.vc_vacation));
        mSpinnerList.add(new SpinnerItem("Water Bill", R.drawable.vc_water_bill));
        mSpinnerList.add(new SpinnerItem("Others", R.drawable.vc_others));
    }

    public void showDialogOnButtonClick(){
        btn = (Button) findViewById(R.id.calendar_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Dialog_Id);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if (id == Dialog_Id){
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            Toast.makeText(AddExpense.this, year_x + " / " + month_x + " / " + day_x, Toast.LENGTH_SHORT).show();
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
