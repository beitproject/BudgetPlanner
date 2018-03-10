package planner.budget.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

public class NavDrawer_Income extends AppCompatActivity {

    ListView nListView;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper income_dbhelper;
    Cursor income_cursor;
    Income_ListView_Adapter income_listView_adapter;
    public static String list_category_name;
    public static Cursor income_cursor_category;

    int[] images = {R.drawable.vc_acc_to_acc, R.drawable.vc_credit_card, R.drawable.vc_gift, R.drawable.vc_interest,
                    R.drawable.vc_mutual_funds, R.drawable.vc_provident_funds, R.drawable.vc_salary,
                    R.drawable.vc_savings, R.drawable.vc_investments, R.drawable.vc_others};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer_income);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nListView = (ListView) findViewById(R.id.income_list_view);
        income_dbhelper = new DatabaseHelper(getApplicationContext());
        sqLiteDatabase = income_dbhelper.getReadableDatabase();
        income_cursor = income_dbhelper.income_getData();
        income_cursor_category=income_dbhelper.getIncomeCategoryString();
        income_listView_adapter = new Income_ListView_Adapter(NavDrawer_Income.this, images);
        nListView.setAdapter(income_listView_adapter);


        if(income_cursor.moveToFirst()){

            do {
                String income_list_description, income_list_category, income_list_date;
                float income_list_amount;
                income_list_amount = income_cursor.getFloat(1);
                income_list_description = income_cursor.getString(2);
                income_list_category = income_cursor.getString(3);
                income_list_date = income_cursor.getString(4);
                Income_List_Item incomeListItem = new Income_List_Item(income_list_amount, income_list_description, income_list_category, income_list_date);
                income_listView_adapter.add(incomeListItem);

            }while (income_cursor.moveToNext());
        }

    }

    //test method for retrieving category String
    public static String forIncomeCategoryString(){
        // String list_category_name = "";
        try {


            if (income_cursor_category.moveToFirst()) {
                do {

                    list_category_name = income_cursor_category.getString(3);
                    Log.d(list_category_name,"Category String");
                } while ((income_cursor_category.moveToNext()));
            }
        }catch(Exception e){
            // Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }
        Log.d(list_category_name,"Correct");
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



