package planner.budget.budgetplanner;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NavDrawer_Cash extends AppCompatActivity {

    ListView mListView;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper cash_dbhelper;
    Cursor cash_cursor;
    Cursor data;
    public static Cursor cash_cursor_category;
    ListView_Adapter listView_adapter;
    Cash_List_Item cashListItem;
    //int category_icon_index=0;
    //String category_icon="";
    //int i;
    public static String list_category_name="";
    String test_category;

   int[] images = {R.drawable.vc_acc_to_acc, R.drawable.vc_air_tickets, R.drawable.vc_beauty, R.drawable.vc_bike,
                    R.drawable.vc_bills_uilities,R.drawable.vc_books,R.drawable.vc_bus_fare,R.drawable.vc_business,
                    R.drawable.vc_cable,R.drawable.vc_car,R.drawable.vc_cash,R.drawable.vc_credit_card,
                    R.drawable.vc_cigarettes,R.drawable.vc_coffee,R.drawable.vc_courier,R.drawable.vc_daily_care,
                    R.drawable.vc_dinner,R.drawable.vc_drinks,R.drawable.vc_education,R.drawable.vc_electricity,
                    R.drawable.vc_electronics,R.drawable.vc_emi,R.drawable.vc_entertainment,R.drawable.vc_finance,
                    R.drawable.vc_fuel,R.drawable.vc_games,R.drawable.vc_gift,R.drawable.vc_gym,R.drawable.vc_health,
                    R.drawable.vc_hotel,R.drawable.vc_household,R.drawable.vc_insurance,R.drawable.vc_investments,
                    R.drawable.vc_kids,R.drawable.vc_loan,R.drawable.vc_maintenance,R.drawable.vc_mobile,
                    R.drawable.vc_music,R.drawable.vc_office,R.drawable.vc_rent,R.drawable.vc_salon,R.drawable.vc_savings,
                    R.drawable.vc_shopping,R.drawable.vc_tax,R.drawable.vc_train,R.drawable.vc_travel,R.drawable.vc_vacation,
                    R.drawable.vc_water_bill,R.drawable.vc_others};

   /* String[] category_names={"Acc to Acc","Air Tickets","Beauty","Bike","Bills/Utilities","Books","Bus Fare"
                            ,"Business","Cable","Car","Cash","CC Bill Payment","Cigarettes","Coffee","Courier",
                            "Daily Care","Dining","Drinks","Education","Electricity","Electronics","EMI","Entertainment"
                            ,"Finance","Fuel","Games","Gift","Gym","Health","Hotel","Household","Insurance","Investments"
                            ,"Kids","Loan","Maintenance","Mobile","Music","Office","Rent","Salon","Savings","Shopping"
                            ,"Tax","Train","Travel","Vacation","Water Bill","Others"};*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer__cash);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mListView = (ListView)findViewById(R.id.cash_list_view);
        cash_dbhelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase=cash_dbhelper.getReadableDatabase();
        cash_cursor = cash_dbhelper.getData();

       //
        // listView_adapter = new ListView_Adapter(getApplicationContext(),R.layout.listview_layout_row);
        // test method to retrieve category String
        cash_cursor_category=cash_dbhelper.getCategoryString();

        listView_adapter = new ListView_Adapter(NavDrawer_Cash.this, images);
        mListView.setAdapter(listView_adapter);

        //image_index();

        if(cash_cursor.moveToFirst()){

            do{
                String list_description, list_category, list_date;
                float list_amount;
                //ImageView imageView=null;
                list_amount = cash_cursor.getFloat(1);
               // Log.d(String.valueOf(list_amount),"AMOUNT");
                list_description = cash_cursor.getString(2);
                list_category = cash_cursor.getString(3);
                list_date = cash_cursor.getString(4);
                Cash_List_Item cashListItem = new Cash_List_Item(list_amount, list_description, list_category, list_date);
                listView_adapter.add(cashListItem);

            } while (cash_cursor.moveToNext());
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String l_description="",l_category="",l_date="";
                float l_amt=0;
                int l_id=0;
                int i=0;
                int db_count=cash_cursor.getCount();
                int row_position;

                long index = parent.getItemIdAtPosition(position);
                //Toast.makeText(NavDrawer_Cash.this,""+position,Toast.LENGTH_LONG).show();
                int no = (int)index;
                //row_position = db_count - no;
                //Toast.makeText(NavDrawer_Cash.this,""+row_position,Toast.LENGTH_LONG).show();

                //to retrieve onclick item data on ListView from Database
                           if(cash_cursor.moveToFirst()) {
                                for (i=0/*1*/;i<=no/*row_position*/;i++) {
                                    if (i == no/*(row_position-1)*/) {
                                        l_id = cash_cursor.getInt(0);
                                        l_amt = cash_cursor.getFloat(1);
                                        l_description = cash_cursor.getString(2);
                                        l_category = cash_cursor.getString(3);
                                        l_date = cash_cursor.getString(4);
                                        break;
                                    }
                                    cash_cursor.moveToNext();
                                }
                            }


                    Log.d("ID=",String.valueOf(l_id));
                    Log.d("l_description",l_description);
                    Log.d("l_category",l_category);
                    Log.d("l_date",l_date);
                    Log.d("Count",String.valueOf(db_count));
                    Intent edit_expense = new Intent(NavDrawer_Cash.this,EditExpense.class);
                    edit_expense.putExtra("ID",l_id);
                    edit_expense.putExtra("Amount",l_amt);
                    edit_expense.putExtra("Description",l_description);
                    edit_expense.putExtra("Category",l_category);
                    edit_expense.putExtra("Date", l_date);
                    startActivity(edit_expense);
                    finish();
                    //data = cash_dbhelper.deleteSelectedData(l_id,l_amt);
                    //Log.d("deleteSelectedData()","Data deleted");
                    listView_adapter.notifyDataSetChanged();

            }
        });
    }


    /*public int image_index() {
        category_icon = cashListItem.getList_category();

        for(i=0;i<category_names.length;i++){
            if(category_names[i] == category_icon)
                category_icon_index=i;
            else
                category_icon_index=category_names.length-1;
        }

        /*switch (category_icon){
            case "Acc to Acc": category_icon_index=0;

            case "Air Tickets":category_icon_index=1;

            case "Beauty":     category_icon_index=2;

            case "Bike":        category_icon_index=3;

            case "Bills/Utilities":category_icon_index=4;

            case "Books":       category_icon_index=5;

            case "Bus Fare":    category_icon_index=6;

            case "Business":    category_icon_index=7;

            case "Cable":       category_icon_index=8;

            case "Car":         category_icon_index=9;

            case "Cash":        category_icon_index=10;


        }*/
      //  return category_icon_index;
  //  }*/




    //test method for retrieving category String
       public static String forCategoryString(){
          // String list_category_name = "";
        try {


               if (cash_cursor_category.moveToFirst()) {
                   do {

                       list_category_name = cash_cursor_category.getString(3);
                       Log.d(list_category_name,"Category String");
                   } while ((cash_cursor_category.moveToNext()));
               }
           }catch(Exception e){
              // Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

           }
           //Log.d(list_category_name,"Correct");
           return list_category_name;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
