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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.achartengine.GraphicalView;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    PieChart piechart;
    private FloatingActionButton mFAB_item1;
    private FloatingActionButton mFAB_item2;
    BarChart barchart2;
    public static Cursor cursor_graph;
    Float[] amt_array;
    Float [] array2 ;
    Float data=55.0f;
    SQLiteDatabase sqLiteDatabase;
    public static DatabaseHelper dbhelper;
    public static Cursor cursor_currentbal,cursor_balance,cursor_getmonthdata,cursor_getexpensedate,cursor_summonth;
    public static float view_bal;
    public static TextView display_balance;
    Cursor cursor_balance_id;
    int balance_id = 0;
    int bal_pie=0;
    int budget_pie=0;
    public static ArrayList<String> expense_getdate = new ArrayList<>();
    public static float expensemonthamt;
    public static ArrayList<String> editexpense_getdate = new ArrayList<>();
    public static ArrayList<String> distinctexpensedate = new ArrayList<>();
    //public static ArrayList<String> compareeditexpense_getdate = new ArrayList<>();
    public static ArrayList<String> duplicateexpensedate = new ArrayList<>();
    float bar0,bar1,bar2,bar3,bar4;
    String m0="",m1="",m2="",m3="",m4="";
    String[] months;



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
        cursor_balance_id = dbhelper.getLastBalanceId();
        cursor_getexpensedate = dbhelper.getUniqueMonthDate();
        cursor_getmonthdata = dbhelper.getMonthData();




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

        updateBalanceOnAllDataDeleted();            //To set balance to 0 when all data deleted

        displayCurrentBalance();            //To display current Balance on Cardview

        calculateMonthData();               //To SUM month expense data
        months = new String[5];
        array2 = new Float[5];
        for (int i =0;i<5;i++){
            array2[i]=0.0f;
            months[i]="";
        }

        //to implement bar graph and pie chart
        try {

           //dbhelper = new DatabaseHelper(getApplicationContext());
            //sqLiteDatabase=dbhelper.getReadableDatabase();
            cursor_graph = dbhelper.getMonthData();
            int i =0;
            ArrayList<Float> al= new ArrayList<>();
            ArrayList<String> as = new ArrayList<>();
            // amt_array  = new Float[5];
             //amt_array[0]=60.0f;
            if(cursor_graph.moveToFirst()) {

                do{
                //data = cursor_graph.getFloat(0);
                    al.add(cursor_graph.getFloat(1));
                    as.add(cursor_graph.getString(0));
                    i++;
                }while(cursor_graph.moveToNext());}

                //checking if db is empty
                if (al.isEmpty()){
                for (i=0;i<=4;i++){
                array2[i]=0.0f;
                }
                }
                else{                   //assigning values form the db to the array for DB
                int size= al.size();
            //array2 = new Float[size];
                for(i= 0;i<size;i++){
                    array2[i]=al.get(i);
                    months[i]=as.get(i);

                }}

            bar0=array2[0];
            bar1=array2[1];
            bar2=array2[2];
            bar3=array2[3];
            bar4=array2[4];

            m0=months[0].toString();
            m1=months[1].toString();
            m2=months[2].toString();
            m3=months[3].toString();
            m4=months[4].toString();


           /* Calendar calendar = Calendar.getInstance();
            Date d1 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d3 = calendar.getTime();*/

               /* GraphView graph = (GraphView) findViewById(R.id.graph);
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                        new DataPoint(0, 30),
                        new DataPoint(1, 50),
                        new DataPoint(2, 30),
                        new DataPoint(3, 20),
                        new DataPoint(4, 20),
                        new DataPoint(5, 30)

                });


                series.setSpacing(20);
                // series.setDataWidth(0.02);
                graph.addSeries(series);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MainActivity.this));
                graph.getGridLabelRenderer().setNumHorizontalLabels(5);
                graph.getViewport().setMaxX(series.getHighestValueX() + 1);
                //graph.getViewport().setMinX(d1.getTime());
                graph.getViewport().setScrollable(true);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getGridLabelRenderer().setHumanRounding(false);
                graph.getGridLabelRenderer().setLabelsSpace(1);                   /*  OLD GRAPH DATA PART*/



    barchart2 = (BarChart) findViewById(R.id.bar2);

    ArrayList<BarEntry> barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(bar0, 0));         //values in array are from the DB
                        barEntries.add(new BarEntry(bar1, 1));
                        barEntries.add(new BarEntry(bar2, 2));
                        barEntries.add(new BarEntry(bar3, 3));
                        barEntries.add(new BarEntry(bar4, 4));



    BarDataSet barDataSet = new BarDataSet(barEntries, "dates");

    ArrayList<String> theDates = new ArrayList<>();
    theDates.add(m0);
    theDates.add(m1);
    theDates.add(m2);
    theDates.add(m3);
    theDates.add(m4);


    BarData theData = new BarData(theDates, barDataSet);
    barchart2.setScaleEnabled(false);
    barchart2.setDoubleTapToZoomEnabled(false);
    barchart2.setData(theData);

        } catch (Exception e) {
            e.printStackTrace();

        }



/*
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
            piechart.setData(data);*/


                CardView card2 = (CardView) findViewById(R.id.card2);
                card2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, budget_page.class);
                        startActivity(intent);
                    }
                });

                CardView card3 = (CardView) findViewById(R.id.card3);
                card3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, Spend_page.class);
                        startActivity(intent);
                    }
                });
                CardView card4 = (CardView) findViewById(R.id.card4);
                card4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, top_spend.class);
                        startActivity(intent);
                    }
                });


        bal_pie=(Integer)(Math.round(view_bal));
        budget_pie=(Integer)(Math.round(view_bal));

        Piegraph yummyPie = new Piegraph();
        GraphicalView graphicalView = yummyPie.getGraphicalView(this,bal_pie , 50);
        LinearLayout pieGraph = (LinearLayout) findViewById(R.id.pie_chart);
        pieGraph.addView(graphicalView);

    }



    //**To initialize balance record**********
        public void initbalance () {
        ///*****for initializing Balance data
        if (cursor_balance.getCount() == 0) {
            //**values for initialize balance
            String init_txntype = "INIT";
            float init_txnamt = 0;
            String init_category = "Others";
            float init_bal = 0;
            boolean isInserted = dbhelper.balance_initinsertData(init_txntype, init_txnamt, init_category, init_bal);
            if (isInserted = true)
                Log.d("Init data ", "inserted");
            else
                Log.d("Init data", "not inserted");

        }
    }

    public void updateBalanceOnAllDataDeleted() {
        if (cursor_balance_id.getCount() == 1 && cursor_balance_id.moveToFirst()) {
            cursor_balance_id.moveToLast();
            balance_id = cursor_balance_id.getInt(0);
            float balupdate_bal = 0;
            dbhelper.updateBalanceOnAllDataDelete(balance_id, balupdate_bal);
        }

    }

    public static void displayCurrentBalance() {
        cursor_currentbal = dbhelper.getCurrentBalance();
        while (cursor_currentbal.moveToNext()) {
            view_bal = cursor_currentbal.getFloat(0);
        }
        display_balance.setText(String.valueOf(view_bal));
    }

    //To calculate and store Total month expense**********
    public void calculateMonthData() {
        if (cursor_getexpensedate.moveToFirst()) {
            do {
                expense_getdate.add(cursor_getexpensedate.getString(0));
            } while (cursor_getexpensedate.moveToNext());
        }
        /*for(int i=0;i<expense_getdate.size();i++){
            Log.d("ExpenseDate->",expense_getdate.get(i));
        }*/

        //to edit the retrieved date to yyyy-mm
        for (int i = 0; i < expense_getdate.size(); i++) {

            String editedexpensedate = expense_getdate.get(i).substring(0, 7);
            editexpense_getdate.add(editedexpensedate);
            //Log.d("EditedExpenseDate->",editexpense_getdate.get(i));
        }

        /*for (int i = 0; i < editexpense_getdate.size(); i++) {
            String transferdate = editexpense_getdate.get(i);
            compareeditexpense_getdate.add(transferdate);
            Log.d("compare->", compareeditexpense_getdate.get(i));
        }*/


        /*int j;
        int eqcount = 0;
        for(int i=0;i<editexpense_getdate.size();i++){
            for(j=0;j<compareeditexpense_getdate.size();j++){
                if(editexpense_getdate.get(i).equals(compareeditexpense_getdate.get(j))) {
                    eqcount = 1;
                    }
                }
                if(eqcount == 0){
                    String movetransferdate = editexpense_getdate.get(i);
                    distinctexpensedate.add(movetransferdate);
                    Log.d("DistinctDates->", distinctexpensedate.get(i));
                }

            }*/
        //To get unique date ****************
        Set<String> set = new HashSet<String>();
        for(int i=0;i<editexpense_getdate.size();i++)
        set.add(editexpense_getdate.get(i));
        Log.d("Set", String.valueOf(set));
        for (String s:set){
            Log.d("String",s);
        }
        //set.addAll(Arrays.asList(editexpense_getdate));

        //*Treeset to arrange month in ascending order**********
        Set<String> set_asc = new TreeSet<String>(set);
        for(String s1:set_asc)
            Log.d("Treeset->",s1);

        /*for(int i=0;i<distinctexpensedate.size();i++) {
            String date1 = distinctexpensedate.get(i);
            cursor_summonth = dbhelper.getSumMonthData(date1);
            if (cursor_summonth.moveToFirst()) {
                cursor_summonth.moveToFirst();
                expensemonthamt = cursor_summonth.getFloat(0);
                Log.d("Month->", String.valueOf(expensemonthamt));
            }
        }*/

        //*****To delete monthdata before updating new
        if(cursor_getmonthdata.getCount()>=1){
            dbhelper.deleteMonthData();
        }


        String year_split,month_split,monthtitle;
        float monthsumamt;
        for(String s:set_asc){
            String date1 = s;
            cursor_summonth = dbhelper.getSumMonthData(date1);
            if(cursor_summonth.moveToFirst()) {
                cursor_summonth.moveToFirst();
                expensemonthamt = cursor_summonth.getFloat(0);
                Log.d("MonthSUM->", String.valueOf(expensemonthamt));
                year_split = s.substring(0, 4);
                //Log.d("Year->",year_split);
                month_split = s.substring(5, 7);
                //Log.d("Month->",month_split);

                //***Logic for converting month index to Textlabel (01->JAN)****
                switch (month_split) {
                    case "01":
                        month_split = "JAN";
                        break;
                    case "02":
                        month_split = "FEB";
                        break;
                    case "03":
                        month_split = "MAR";
                        break;
                    case "04":
                        month_split = "APR";
                        break;
                    case "05":
                        month_split = "MAY";
                        break;
                    case "06":
                        month_split = "JUNE";
                        break;
                    case "07":
                        month_split = "JULY";
                        break;
                    case "08":
                        month_split = "AUG";
                        break;
                    case "09":
                        month_split = "SEPT";
                        break;
                    case "10":
                        month_split = "OCT";
                        break;
                    case "11":
                        month_split = "NOV";
                        break;
                    case "12":
                        month_split = "DEC";
                        break;
                }
                //******************************************

                    monthtitle = month_split.concat(year_split);
                    monthsumamt = expensemonthamt;

                boolean isInserted = dbhelper.month_insertData(monthtitle, monthsumamt);
                    if (isInserted = true)
                        Log.d("MonthSUM data ", "inserted");
                    else
                        Log.d("MonthSUM data", "not inserted");
                }
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public SQLiteDatabase getWritableDatabase() {
        return getWritableDatabase();
    }

}
