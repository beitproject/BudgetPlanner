package planner.budget.budgetplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    public static Cursor cursor_currentbal,cursor_balance,cursor_getmonthdata,cursor_getexpensedate,cursor_summonth,
            cursor_getdaydata,cursor_getsumdayamt;
    public static float view_bal;
    public static TextView display_balance;
    Cursor cursor_balance_id;
    int balance_id = 0;
    int bal_pie=0;
    int budget_pie=0;
    public static ArrayList<String> expense_getdate = new ArrayList<>();
    public static float expensemonthamt;
    public static ArrayList<String> editexpense_getdate = new ArrayList<>();
    public static ArrayList<String> expenseday_getdate = new ArrayList<>();
    public static ArrayList<String> editexpenseday_getdate = new ArrayList<>();
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
        cursor_getdaydata = dbhelper.getDaysData();




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

        //dayCalculateData();                 //To calculate day wise data for prediction csv

        calculateMonthData();               //To SUM month expense data

        new PredictionCSVTask().execute();          //For running ASYNC TASK in Background


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
            int size= al.size();
            //logic for the graph month bwlow and above 5 months
            if(size>=5){
                months = new String[size];
                array2 = new Float[size];
            for ( i =0;i<size;i++){
                array2[i]=0.0f;
                months[i]="";
            }}
            else{
                months = new String[5];
                array2 = new Float[5];
                for ( i =0;i<5;i++){
                    array2[i]=0.0f;
                    months[i]="";
                }
            }


                //checking if db is empty
                if (al.isEmpty()){
                for (i=0;i<size;i++){
                array2[i]=0.0f;
                }
                }
                else{                   //assigning values form the db to the array for DB
                //int size= al.size();
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
                        /*Intent intent = new Intent(MainActivity.this, budget_page.class);
                        startActivity(intent);*/
                        new PredictionCSVTask().execute();
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


    public class PredictionCSVTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
                    Toast.makeText(MainActivity.this, "Task Done", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (cursor_getexpensedate.moveToFirst()) {
                do {
                    expenseday_getdate.add(cursor_getexpensedate.getString(0));
                } while (cursor_getexpensedate.moveToNext());
            }
            for (int i = 0; i < expenseday_getdate.size(); i++) {
                String editedexpensedaydate = expenseday_getdate.get(i).substring(0, 10);
                editexpenseday_getdate.add(editedexpensedaydate);
                //Log.d("EditedExpenseDate->",editexpense_getdate.get(i));
            }

            //To get unique date
            Set<String> set_day = new HashSet<String>();
            for (int i = 0; i < editexpense_getdate.size(); i++)
                set_day.add(editexpenseday_getdate.get(i));
            Log.d("SETDATE->", String.valueOf(set_day));
            Set<String> set_day_asc = new TreeSet<String>(set_day);
            for (String tree : set_day_asc)
                Log.d("Tree-Day", tree);


            //***To delete day data before updating new**********
            if (cursor_getdaydata.getCount() >= 1) {
                dbhelper.deleteDaysData();
            }

            /*float get_predictamt;
              cursor_getsumdayamt = dbhelper.getSumDayData("2018-04-11");
                    if(cursor_getsumdayamt.moveToFirst()){
                       cursor_getsumdayamt.moveToFirst();
                       get_predictamt = cursor_getsumdayamt.getFloat(0);
                       Log.d("BG",String.valueOf(get_predictamt));
               }*/


            String predictiondate;
            float predictionamount, get_predictionamount;
            for (String s_day : set_day_asc) {
                String day_date = s_day;
                cursor_getsumdayamt = dbhelper.getSumDayData(day_date);
                if (cursor_getsumdayamt.moveToFirst()) {
                    cursor_getsumdayamt.moveToFirst();
                    get_predictionamount = cursor_getsumdayamt.getFloat(0);
                    Log.d("DAYSUM->", String.valueOf(get_predictionamount));
                    predictiondate = day_date;
                    predictionamount = get_predictionamount;
                    boolean isInserted = dbhelper.days_insertData(predictiondate, predictionamount);
                    if (isInserted = true)
                        Log.d("DAYSUM data ", "inserted");
                    else
                        Log.d("DAYSUM data", "not inserted");
                }
            }

            ///*****To create csv for algo input *****
            try {
                File folder = Environment.getExternalStorageDirectory();
                String fileName = folder.getPath() + "/MyExpenses.csv";
                File myFile = new File(fileName);
                if (myFile.exists())
                    myFile.delete();
                Log.d("File Loc", fileName);

                int rowcount = 0;
                int colcount = 0;
                File sdCardDir = Environment.getExternalStorageDirectory();
                String filename = "MyExpenses.csv";  //the name of the file to export with
                File saveFile = new File(sdCardDir, filename);
                FileWriter fw = new FileWriter(saveFile);
                Cursor cursor_csv = dbhelper.getDaysData();
                BufferedWriter bw = new BufferedWriter(fw);
                rowcount = cursor_csv.getCount();
                colcount = cursor_csv.getColumnCount();

                /*if (rowcount > 0) {
                     cursor_csv.moveToFirst();
                     for (int i = 0; i < colcount; i++) {
                        if (i != colcount - 1) {
                          bw.write(cursor_csv.getColumnName(i) + ",");
                       } else {
                           bw.write(cursor_csv.getColumnName(i));
                       }
                   }
               }
               bw.newLine();*/

                for (int i = 0; i < rowcount; i++) {
                    cursor_csv.moveToPosition(i);
                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1) {
                            bw.write(cursor_csv.getString(j) + ",");
                        } else {
                            bw.write(cursor_csv.getString(j));
                        }
                    }
                    bw.newLine();
                }
                bw.flush();
                //Toast.makeText(MainActivity.this, "Exported Data Successfully", Toast.LENGTH_LONG).show();


            } catch (Exception ex) {
                //if(sqLiteDatabase.isOpen()) {
                //sqLiteDatabase.close();
                //Toast.makeText(MainActivity.this, "" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                //}
            } finally {

            }


            //***************************************


            //*********To upload csv file to server

            int serverResponseCode = 0;
            String uploadServerUri = "http://192.168.1.103/FileUpload/upload.php";
            final String uploadFilePath = "/storage/emulated/0/";
            final String uploadFileName = "MyExpenses.csv";
            String fileName = uploadFilePath+""+uploadFileName;
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(fileName);

            // open a URL connection to the Servlet
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(uploadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file; filename="+fileName+""+lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"FileUploadComplete.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"MalFormedURLException",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            //***************************************

            return null;
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
