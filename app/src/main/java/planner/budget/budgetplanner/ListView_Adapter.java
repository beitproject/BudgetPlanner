package planner.budget.budgetplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 03-03-2018.
 */

public class ListView_Adapter extends ArrayAdapter<Cash_List_Item> {



   List list = new ArrayList();
    int [] images;
    Context mcontext;
    int pos=0;
    NavDrawer_Cash navDrawer_cash;
    int i=0;
    Cash_List_Item cashListItem;
    Cursor cash_cursor_category;
    //DatabaseHelper cash_dbhelper;
    //SQLiteDatabase sqLiteDatabase;
    String[] category_names={"Acc to Acc","Air Tickets","Beauty","Bike","Bills/Utilities","Books","Bus Fare"
            ,"Business","Cable","Car","Cash","CC Bill Payment","Cigarettes","Coffee","Courier",
            "Daily Care","Dining","Drinks","Education","Electricity","Electronics","EMI","Entertainment"
            ,"Finance","Fuel","Games","Gift","Gym","Health","Hotel","Household","Insurance","Investments"
            ,"Kids","Loan","Maintenance","Mobile","Music","Office","Rent","Salon","Savings","Shopping"
            ,"Tax","Train","Travel","Vacation","Water Bill","Others"};



    public ListView_Adapter(@NonNull Context context, int[] cat_images) {
        super(context, R.layout.listview_layout_row);
        this.images=cat_images;
        this.mcontext=context;
    }

    static class LayoutHandler{
        TextView AMOUNT, DESCRIPTION, CATEGORY, DATE;
        ImageView IMAGE;


    }

    public int image_index(){
        //category_icon=cashListItem.getList_category();
        //category_icon="Books";
      String category_icon = NavDrawer_Cash.forCategoryString();
      Log.d(category_icon,"Category ListAdapter");
        for(i=0;i<category_names.length;i++){
            if(category_names[i].equals(category_icon)){
                pos=i;
                break;
            }
            else{
                pos=category_names.length-1;}
        }
        Log.d(String.valueOf(pos),"pos");
        return pos;


        /*while(category_names[i] != "Abc"){
            i++;
        }
            pos=i;
        return pos;*/
    }



 /*   @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }*/


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        if(row == null){


            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.listview_layout_row, parent, false);
            layoutHandler = new LayoutHandler();
           layoutHandler.IMAGE=(ImageView)row.findViewById(R.id.list_imageview_icon);
            layoutHandler.AMOUNT=(TextView)row.findViewById(R.id.list_textview_amount);
            layoutHandler.DESCRIPTION= (TextView)row.findViewById(R.id.list_textview_desc);
            layoutHandler.CATEGORY= (TextView) row.findViewById(R.id.list_textview_category);
            layoutHandler.DATE= (TextView)row.findViewById(R.id.list_textview_date);
            row.setTag(layoutHandler);
        }else{
            layoutHandler = (LayoutHandler)row.getTag();
        }

        Cash_List_Item cashListItem =(Cash_List_Item) this.getItem(position);

       // pos=navDrawer_cash.image_index();
        image_index();

        layoutHandler.AMOUNT.setText(cashListItem.getToStringList_amount());
        layoutHandler.DESCRIPTION.setText(cashListItem.getList_description());
        layoutHandler.CATEGORY.setText(cashListItem.getList_category());
        layoutHandler.DATE.setText(cashListItem.getList_date());
        layoutHandler.IMAGE.setImageResource(images[pos]);
        return row;

    }
}
