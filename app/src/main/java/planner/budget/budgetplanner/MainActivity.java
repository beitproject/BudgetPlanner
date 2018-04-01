package planner.budget.budgetplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    PieChart piechart;
    private FloatingActionButton mFAB_item1;
    private FloatingActionButton mFAB_item2;
    SQLiteDatabase sqLiteDatabase;
    public static DatabaseHelper dbhelper;
    public static Cursor cursor_balance,cursor_currentbal;
    public static float view_bal;
    public static TextView display_balance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //to implement toolbar and navigation drawer
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        //database call
        dbhelper = new DatabaseHelper(this);
        sqLiteDatabase = dbhelper.getReadableDatabase();
        cursor_balance = dbhelper.balance_getData();

        display_balance = (TextView) findViewById(R.id.Balance_Homepage);

        //to implement onClickListener for AddIncome
        mFAB_item1= (FloatingActionButton) findViewById(R.id.menu_item1);

        mFAB_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent item1_intent=new Intent(MainActivity.this,AddIncome.class);
                startActivity(item1_intent);
            }
        });

        //to implement onClickListener for AddExpense
        mFAB_item2= (FloatingActionButton) findViewById(R.id.menu_item2);

        mFAB_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent item2_intent=new Intent(MainActivity.this,AddExpense.class);
                startActivity(item2_intent);
            }
        });

        initbalance(); //initialize balance func call

        displayCurrentBalance();            //To display current Balance on Cardview

        //to implement bar graph and pie chart
        try {

            GraphView graph = (GraphView) findViewById(R.id.graph);
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                    new DataPoint(0, 20),
                    new DataPoint(1, 50),
                    new DataPoint(2, 30),
                    new DataPoint(3, 10),
                    new DataPoint(4, 55)
            });
            graph.addSeries(series);
            piechart = (PieChart) findViewById(R.id.pie_chart);

            piechart.setUsePercentValues(true);
            piechart.getDescription().setEnabled(false);
            piechart.setTransparentCircleRadius(61f);

            ArrayList<PieEntry> values = new ArrayList<>();
            values.add(new PieEntry(50, "Spent"));
            values.add(new PieEntry(50, "Balance"));

            PieDataSet dataSet = new PieDataSet(values, "Balance Budget");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


            PieData data = new PieData(dataSet);
            data.setValueTextColor(Color.RED);
            data.setValueTextSize(10f);
            piechart.setData(data);


            CardView card2 = (CardView) findViewById(R.id.card2);
            card2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, budget_page.class);
                    startActivity(intent);
                }
            });

            CardView card3 = (CardView) findViewById(R.id.card3);
            card3.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this,Spend_page.class);
                    startActivity(intent);
                }
            });
            CardView card4 = (CardView) findViewById(R.id.card4);
            card4.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this,top_spend.class);
                    startActivity(intent);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //**To initialize balance record**********
    public void initbalance(){
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
    }

    public static void displayCurrentBalance(){
        cursor_currentbal = dbhelper.getCurrentBalance();
        while(cursor_currentbal.moveToNext()){
            view_bal = cursor_currentbal.getFloat(0);
        }
        display_balance.setText(String.valueOf(view_bal));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
