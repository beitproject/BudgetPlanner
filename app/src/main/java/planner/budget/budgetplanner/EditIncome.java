package planner.budget.budgetplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditIncome extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper mdbhelper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                DAY = Integer.toString(day_x);
                FINAL_DATE = DAY+"/"+MONTH+"/"+YEAR;
                try {
                    editeddate = new SimpleDateFormat("dd/mm/yyyy").parse(FINAL_DATE);
                    Log.d("Date will be updated",String.valueOf(editeddate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // ***** To fet the amount from Edit text and convert to float
                ifedited_amount = Float.valueOf(edit_income_amt.getText().toString());

                String editeddesc = edit_income_desc.getText().toString();
                if(!edit_income_amt.getText().toString().equals("") && !editeddesc.equals("")) {
                    mdbhelper.updateIncomeData(selectedId, ifedited_amount, editeddesc, editincome_clickedItemName, editeddate);
                    Toast.makeText(EditIncome.this,"Data Updated Successfully.",Toast.LENGTH_SHORT).show();
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


