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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditIncome extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper mdbhelper;
    Cursor cursor_balance;
    public ArrayList<Income_SpinnerItem> editincome_mSpinnerList;
    public EditIncome_Spinner_Adapter editincome_spinner_adapter;
    public FloatingActionButton delete_income_btn, edit_income_btn;
    EditText edit_income_amt,edit_income_desc;
    String editincome_clickedItemName;
    Button btn;     //Datepicker button
    int year_x, month_x, day_x;
    static final int Dialog_Id = 0;
    String FINAL_DATE,YEAR,MONTH,DAY;
    float ifedited_amount;
    Date editeddate;
    public boolean Categoryswitch;
    public int category_position;
    public String[] category_names={"Acc to Acc","CC Bill Payment", "Gifts", "Interest", "Mutual Funds", "Provident Fund", "Salary",
            "Savings", "Investments", "Others"};

    public int selectedId;
    public float selectedAmount;
    public String selectedDescription;
    public String selectedCategory;
    public String selectedDate;

    //**Balance variables
    public String balupdate_category,compare_balupdate_txnamt,compare_balupdate_ifeditedamt,balupdate_date;
    public int searchid;
    public float searchtxnamt,balupdate_balance,balupdate_txnamt,search_baltxnamt=0;   //balupdate_var are related to Balance functions********
    public Date search_baldate;
    public String search_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mdbhelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = mdbhelper.getReadableDatabase();
        cursor_balance = mdbhelper.balance_getData();

        delete_income_btn=(FloatingActionButton) findViewById(R.id.edit_income_deletebtn);
        edit_income_btn = (FloatingActionButton) findViewById(R.id.edit_income_editbtn);
        edit_income_amt =(EditText) findViewById(R.id.edit_income_amttextview);
        edit_income_desc = (EditText) findViewById(R.id.edit_income_desctextview);
        mdbhelper= new DatabaseHelper(this);

        //get the intent extra from NavDrawer_Income class
        Intent editincomeclass = getIntent();

        //get the itemid extra from NavDrawer_Cash
        selectedId = editincomeclass.getIntExtra("ID",-1);
        selectedAmount = editincomeclass.getFloatExtra("Amount",0);
        selectedDescription = editincomeclass.getStringExtra("Description");
        selectedCategory = editincomeclass.getStringExtra("Category");
        //Log.d("selectedCategory", selectedCategory);
        selectedDate = editincomeclass.getStringExtra("Date");
        Log.d("Intent",String.valueOf(selectedDate));

        //To assign the intent extra values to textviews
        edit_income_amt.setText(String.valueOf(selectedAmount));
        edit_income_desc.setText(selectedDescription);

        //to split date in required format
        String[] datesplit = selectedDate.split("-",0);
        datesplit[2]= datesplit[2].substring(0,2);

        //final Calendar cal = Calendar.getInstance();
        year_x = Integer.parseInt(datesplit[0]);
        month_x = Integer.parseInt(datesplit[1]);
        //month_x = month_x + 1;  //index starts from 0 So....
        day_x = Integer.parseInt(datesplit[2]);

        //Log.d("year_x",String.valueOf(year_x));
        //Log.d("month_x",String.valueOf(month_x));
        //Log.d("day_x",String.valueOf(day_x));

        editincome_initList(); //Spinner Function Call

        showDialogOnButtonClick();  //Calendar function call

        final Spinner editincome_spinnerItems = (Spinner) findViewById(R.id.edit_income_spinner);

        editincome_spinnerItems.setPrompt("Select a Category");

        editincome_spinner_adapter = new EditIncome_Spinner_Adapter(this, editincome_mSpinnerList);
        editincome_spinnerItems.setAdapter(editincome_spinner_adapter);

        editincome_spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //editincome_spinnerItems.setSelection(spinnerGetDataPosition());
                Income_SpinnerItem editincome_clickedItem = (Income_SpinnerItem) parent.getItemAtPosition(position);
                editincome_clickedItemName = editincome_clickedItem.getnCategoryName();
                //Categoryswitch = true;
                Toast.makeText(EditIncome.this, editincome_clickedItemName + "\t Category Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Delete record of passed extra Intent value
        delete_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdbhelper.deleteIncomeSelectedData(selectedId);
                edit_income_amt.setText("");
                edit_income_desc.setText("");
                Toast.makeText(EditIncome.this,"Data has been Deleted!",Toast.LENGTH_LONG).show();

                updateBalanceOnDeleteExpense();         //To update Balance on Income delete
            }
        });

        //Category switch value check
        /*if(Categoryswitch == true){
            Toast.makeText(EditIncome.this,""+editincome_clickedItemName,Toast.LENGTH_SHORT).show();
        }else{
            editincome_clickedItemName = selectedCategory;
            Toast.makeText(EditIncome.this, ""+editincome_clickedItemName,Toast.LENGTH_SHORT).show();
        }*/

        //Edit record of user edited data
        edit_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Date dateobj = Calendar.getInstance().getTime();
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
                    editeddate = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(FINAL_DATE);
                    Log.d("DATE-->",String.valueOf(editeddate));

                    //Log.d("Date will be updated",String.valueOf(editeddate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // ***** To fet the amount from Edit text and convert to float
                ifedited_amount = Float.valueOf(edit_income_amt.getText().toString());

                String editeddesc = edit_income_desc.getText().toString();
                if(!edit_income_amt.getText().toString().equals("") && !editeddesc.equals("")) {
                    mdbhelper.updateIncomeData(selectedId, ifedited_amount, editeddesc, editincome_clickedItemName, editeddate);
                    Toast.makeText(EditIncome.this,"Data Updated Successfully.",Toast.LENGTH_SHORT).show();
                    updateBalanceOnEditExpense();
                }
                else{
                    Toast.makeText(EditIncome.this,"Please enter some data!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void editincome_initList(){
        editincome_mSpinnerList = new ArrayList<>();
        editincome_mSpinnerList.add(new Income_SpinnerItem("Acc to Acc", R.drawable.vc_acc_to_acc));
        editincome_mSpinnerList.add(new Income_SpinnerItem("CC Bill Payment", R.drawable.vc_credit_card));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Gifts", R.drawable.vc_gift));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Interest", R.drawable.vc_interest));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Mutual Funds", R.drawable.vc_mutual_funds));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Provident Fund", R.drawable.vc_provident_funds));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Salary", R.drawable.vc_salary));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Savings", R.drawable.vc_savings));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Investments", R.drawable.vc_investments));
        editincome_mSpinnerList.add(new Income_SpinnerItem("Others", R.drawable.vc_others));

    }

    public int spinnerGetDataPosition(){

        //Log.d("spinnerGetDataPosition",selectedCategory);

        for(int i=0;i<category_names.length;i++){
            if(category_names[i].equals(selectedCategory)){
                //Log.d("inside if",selectedCategory);
                category_position = i;
                Log.d("categorypos",String.valueOf(category_position));
                //break;
            }
        }
        Log.d("return spinnerGetData",category_names[category_position]);
        Toast.makeText(EditIncome.this,"return"+category_names[category_position],Toast.LENGTH_SHORT).show();
        return category_position;
    }

    public void showDialogOnButtonClick(){
        btn = (Button) findViewById(R.id.edit_income_calendar_button);

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
            Toast.makeText(EditIncome.this, year_x + " / " + month_x + " / " + day_x, Toast.LENGTH_SHORT).show();
        }
    };


    public void updateBalanceOnEditExpense(){
        String search_category="";

        //Log.d("Selected Category->",check_category);
        //Log.d("SELECTED DATE->",selectedDate);
        if(cursor_balance.moveToFirst()){
            do{
                //balupdate_txnamt = cursor_balance.getFloat(2);
                balupdate_date = cursor_balance.getString(4);
                //compare_balupdate_txnamt = String.valueOf(balupdate_txnamt);

                //Log.d("BALANCE TXNAMT->",String.valueOf(balupdate_txnamt));
                //Log.d("BALANCE_DATE->",balupdate_date);
                //compare_balupdate_ifeditedamt = String.valueOf(ifedited_amount);
                if(balupdate_date.equals(selectedDate)){
                    search_date = balupdate_date;            //matching date from balance as in expense
                    //search_baltxnamt=balupdate_txnamt;       //matching amt from balance as in expense
                }    else{
                    search_date = selectedDate;
                }

            }while (cursor_balance.moveToNext());
        }

        Log.d("SEARCH_DATE->",search_date);

        try {
            search_baldate = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(search_date);
            Log.d("DAte SENT->",String.valueOf(search_baldate));
        }catch (ParseException e){
            e.printStackTrace();
        }

        Cursor data = mdbhelper.getBalanceId(search_baldate);
        while (data.moveToNext()){
            searchid = data.getInt(0);
            searchtxnamt = data.getFloat(1);
        }
        Log.d("BalanceID:",String.valueOf(searchid));
        Log.d("BalanceTNXAMT->",String.valueOf(searchtxnamt));

        //****To adjust balance before editing expense
        Cursor cursor_current_bal = mdbhelper.getCurrentBalance();
        if(cursor_current_bal.moveToFirst()){
            cursor_current_bal.moveToLast();
            balupdate_balance=cursor_current_bal.getFloat(0);
        }
        Log.d("Current Balance->",String.valueOf(balupdate_balance));

        //******To get previous TXNAMT
        /*cursor_bal_tnxamt= mdbhelper.getPrevTxnamt(searchid);
        while (cursor_bal_tnxamt.moveToNext()){
            searchtxnamt = cursor_bal_tnxamt.getFloat(0);
        }
        Log.d("Searchtxnamt->",String.valueOf(searchtxnamt));*/

        balupdate_balance = balupdate_balance - (searchtxnamt-ifedited_amount);   //to adjust balance before deleting
        //Log.d("Balance update",String.valueOf(balupdate_balance));

        //***To delete old record from balance table*****
        mdbhelper.deletebalance(searchid);
        String txntype="Credit";
        float txnamt = ifedited_amount;
        Date bal_date = editeddate;
        String category = editincome_clickedItemName;
        float balance = balupdate_balance;
        mdbhelper.balance_insertData(txntype,txnamt,category,bal_date,balance);
        Toast.makeText(EditIncome.this,"Balance Updated",Toast.LENGTH_SHORT).show();

        MainActivity.displayCurrentBalance();           //To display Balance-Homepage on income edit
    }

    //*****To update Balance on deleting Expense**************
    public void updateBalanceOnDeleteExpense(){
        int last_bal_id = 0;
        if(cursor_balance.moveToFirst()){
            do{
                balupdate_date = cursor_balance.getString(4);
                if(balupdate_date.equals(selectedDate)) {
                    search_date = balupdate_date;            //matching date from balance as in expense
                }   else{
                    search_date = selectedDate;
                }

            }while(cursor_balance.moveToNext());
        }
        try {
            search_baldate = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss").parse(search_date);
            Log.d("DAte SENT->",String.valueOf(search_baldate));
        }catch (ParseException e){
            e.printStackTrace();
        }

        Cursor data = mdbhelper.getBalanceId(search_baldate);
        while (data.moveToNext()){
            searchid = data.getInt(0);
            searchtxnamt = data.getFloat(1);
        }
        //****To adjust balance before editing expense
        Cursor cursor_current_bal = mdbhelper.getCurrentBalance();
        if(cursor_current_bal.moveToFirst()){
            cursor_current_bal.moveToLast();
            balupdate_balance=cursor_current_bal.getFloat(0);
        }

        balupdate_balance = balupdate_balance - searchtxnamt;   //to adjust balance before deleting

        mdbhelper.deletebalance(searchid);  //to delete balance data ,deleted from expense table

        Cursor cursor_last_bal = mdbhelper.getLastBalanceId();
        if(cursor_last_bal.moveToFirst()){
            cursor_last_bal.moveToLast();

            last_bal_id = cursor_last_bal.getInt(0);
        }

        //**To update last data of balance table with new balance
        mdbhelper.updateLastBalance(balupdate_balance,last_bal_id);
        //**********

        MainActivity.displayCurrentBalance();           //To display Balance-Homepage on income delete
    }

    //******************************************************


    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


