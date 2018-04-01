package planner.budget.budgetplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

public class NavDrawer_Balance extends AppCompatActivity {
    DatabaseHelper bal_dbhelper;
    SQLiteDatabase sqLiteDatabase;
    Button baldelbtn;
    ListView listView;
    Cursor cursor_balance;
    Balance_ListView_Adapter balance_listView_adapter;
    FloatingActionButton alldeletebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_balance);
        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.balance_list_view);
       // alldeletebtn = (FloatingActionButton) findViewById(R.id.all_bal_deletebtn);
        bal_dbhelper = new DatabaseHelper(this);
        sqLiteDatabase = bal_dbhelper.getReadableDatabase();
        cursor_balance = bal_dbhelper.balance_getData();
        balance_listView_adapter = new Balance_ListView_Adapter(NavDrawer_Balance.this,R.layout.balance_listview_layout_row);
        listView.setAdapter(balance_listView_adapter);

        //***To get balance data from db****
        if(cursor_balance.moveToFirst()){
            do {
                String bal_txntype,category,date;
                float bal_txnamt,balance;
                bal_txntype = cursor_balance.getString(1);
                bal_txnamt = cursor_balance.getFloat(2);
                category = cursor_balance.getString(3);
                date = cursor_balance.getString(4);
                balance = cursor_balance.getFloat(5);
                Balance_List_Item balance_list_item= new Balance_List_Item(bal_txntype,bal_txnamt,category,date,balance);
                balance_listView_adapter.add(balance_list_item);

            }while (cursor_balance.moveToNext());
        }

        //**********to Delete all Balance records
        /*alldeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bal_dbhelper.deleteAllBalance();
                Toast.makeText(NavDrawer_Balance.this,"All data Deleted",Toast.LENGTH_SHORT).show();
            }
        });*/

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
