package planner.budget.budgetplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 10-03-2018.
 */

public class Income_ListView_Adapter extends ArrayAdapter<Income_List_Item> {

    List list = new ArrayList();
    int[] images;
    Context ncontext;
    int pos = 0;
    NavDrawer_Income navDrawer_income;
    int i=0;

    String[] category_names={"Acc to Acc","CC Bill Payment", "Gifts", "Interest", "Mutual Funds", "Provident Fund", "Salary",
                                "Savings", "Investments", "Others"};


    public Income_ListView_Adapter(@NonNull Context context, int[] cat_images) {
        super(context, R.layout.income_listview_layout_row);
        this.ncontext = context;
        this.images = cat_images;
    }

    static class LayoutHandler{
        TextView AMOUNT, DESCRIPTION, CATEGORY, DATE;
        ImageView IMAGE;
    }


    public int image_index(){
        //category_icon=cashListItem.getList_category();
        //category_icon="Books";
        String category_icon = NavDrawer_Income.forIncomeCategoryString();
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


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        LayoutHandler layoutHandler;

        if (row == null){


            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.income_listview_layout_row, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.IMAGE=(ImageView)row.findViewById(R.id.incomelist_imageview_icon);
            layoutHandler.AMOUNT=(TextView)row.findViewById(R.id.incomelist_textview_amount);
            layoutHandler.DESCRIPTION= (TextView)row.findViewById(R.id.incomelist_textview_desc);
            layoutHandler.CATEGORY= (TextView) row.findViewById(R.id.incomelist_textview_category);
            layoutHandler.DATE= (TextView)row.findViewById(R.id.incomelist_textview_date);
            row.setTag(layoutHandler);
        }else{
            layoutHandler = (Income_ListView_Adapter.LayoutHandler)row.getTag();
        }

        Income_List_Item incomeListItem = (Income_List_Item) this.getItem(position);

        image_index();

        layoutHandler.AMOUNT.setText(incomeListItem.getToStringIncome_list_amount());
        layoutHandler.DESCRIPTION.setText(incomeListItem.getIncome_list_description());
        layoutHandler.CATEGORY.setText(incomeListItem.getIncome_list_category());
        //to format date to displayed on listview
        layoutHandler.DATE.setText(incomeListItem.getIncome_list_date().substring(0,10));
        layoutHandler.IMAGE.setImageResource(images[pos]);
        return row;
        }

    }
