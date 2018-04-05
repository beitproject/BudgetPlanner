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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    PieChart piechart;
    private FloatingActionButton mFAB_item1;
    private FloatingActionButton mFAB_item2;
    BarChart barchart2;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper dbhelper;
    Cursor cursor_graph;
    Float[] amt_array;
    Float [] array2 ;
    Float data=55.0f;

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

        //to implement bar graph and pie chart
        try {

           dbhelper = new DatabaseHelper(getApplicationContext());
            sqLiteDatabase=dbhelper.getReadableDatabase();
            cursor_graph = dbhelper.getGraphData();
            int i =0;
            ArrayList<Float> al= new ArrayList<>();
            // amt_array  = new Float[5];
             //amt_array[0]=60.0f;
            if(cursor_graph.moveToFirst()) {

                do{
                //data = cursor_graph.getFloat(0);
                    al.add(cursor_graph.getFloat(0));
                    Log.d("hello", data.toString());
                    i++;
                }while(cursor_graph.moveToNext());}

                int size= al.size();
            array2 = new Float[size];
                for(i= 0;i<=size;i++){
                    array2[i]=al.get(i);
                }
        } catch (Exception e) {
            e.printStackTrace();

        }


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

                barEntries.add(new BarEntry(array2[0], 0));
                barEntries.add(new BarEntry(array2[1], 1));
                barEntries.add(new BarEntry(array2[2], 2));
                barEntries.add(new BarEntry(array2[3], 3));
                barEntries.add(new BarEntry(array2[4], 4));

                BarDataSet barDataSet = new BarDataSet(barEntries, "dates");

                ArrayList<String> theDates = new ArrayList<>();
                theDates.add("JAN");
                theDates.add("FEB");
                theDates.add("MAR");
                theDates.add("APR");
                theDates.add("MAY");


                BarData theData = new BarData(theDates, barDataSet);
                barchart2.setScaleEnabled(false);
                barchart2.setDoubleTapToZoomEnabled(false);
                barchart2.setData(theData);




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



        Piegraph yummyPie = new Piegraph();
        GraphicalView graphicalView = yummyPie.getGraphicalView(this,50 , 50);
        LinearLayout pieGraph = (LinearLayout) findViewById(R.id.pie_chart);
        pieGraph.addView(graphicalView);

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
