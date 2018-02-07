package planner.budget.budgetplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    private ArrayList<SpinnerItem> mSpinnerList;
    private Spinner_Adapter mAdapter;
    Button btn;
    int year_x, month_x, day_x;
    static final int Dialog_Id = 0;
    private FloatingActionButton mFABexpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        initList(); //Spinner function call

        showDialogOnButtonClick();  //Calendar function call

        Spinner spinnerItems = (Spinner) findViewById(R.id.spinner);

        spinnerItems.setPrompt("Select a Category");

        mAdapter = new Spinner_Adapter(this, mSpinnerList);
        spinnerItems.setAdapter(mAdapter);

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem clickedItem = (SpinnerItem) parent.getItemAtPosition(position);
                String clickedItemName = clickedItem.getCategoryName();
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

      /*  mFABexpense = (FloatingActionButton) findViewById(R.id.expense_submit_btn);

        mFABexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expense_intent = new Intent(AddExpense.this, NavDrawer_SpendSummary.class);
                startActivity(expense_intent);
            }
        });*/
    }

    //Method to add elements to Spinner
    private void initList(){
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
            Toast.makeText(AddExpense.this, year_x + " / " + month_x + " / " + day_x, Toast.LENGTH_LONG).show();
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
