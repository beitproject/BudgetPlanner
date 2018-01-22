package planner.budget.budgetplanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            toolbar= (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayShowHomeEnabled(false);
            NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), toolbar);


            GraphView graph = (GraphView) findViewById(R.id.graph);
            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, -1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),
                    new DataPoint(4, 6)
            });
            graph.addSeries(series);
            piechart =(PieChart)findViewById(R.id.pie_chart);

            piechart.setUsePercentValues(true);
            piechart.getDescription().setEnabled(false);
            piechart.setTransparentCircleRadius(61f);

            ArrayList<PieEntry> values = new ArrayList<>();
            values.add(new PieEntry(50,"Spent"));
            values.add(new PieEntry(50,"Balance"));

            PieDataSet dataSet = new PieDataSet(values,"Balance Budget");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


            PieData data = new PieData(dataSet);
            data.setValueTextColor(Color.RED);
            data.setValueTextSize(10f);


            piechart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
