package planner.budget.budgetplanner;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.Locale;

/**
 * Created by Gaurav on 30-Mar-18.
 */

public class Piegraph {
public GraphicalView getGraphicalView(Context context,int largeslide,int mediumslide){
    CategorySeries series =new CategorySeries("Remaining Bugdet");

    int[] portions ={largeslide,mediumslide};
    String[] seriesNames = new String[] {"Budget","Balance"};

    int numSlides = 2;

    for(int i =0 ;i<numSlides;i++){
        series.add(seriesNames[i],portions[i]);
    }
    DefaultRenderer defaultRenderer= new DefaultRenderer();
    SimpleSeriesRenderer simpleSeriesRenderer = null;

    int[] colors = {Color.RED,Color.GREEN};
    for(int i =0 ;i<numSlides;i++){
        simpleSeriesRenderer = new SimpleSeriesRenderer();
        simpleSeriesRenderer.setColor(colors[i]);
        defaultRenderer.addSeriesRenderer(simpleSeriesRenderer);
    }

    return ChartFactory.getPieChartView(context,series,defaultRenderer);
}

}

