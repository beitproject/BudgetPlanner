package planner.budget.budgetplanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit on 24-03-2018.
 */

public class Balance_ListView_Adapter extends ArrayAdapter<Balance_List_Item> {

    List list = new ArrayList();
    Context context;

    public Balance_ListView_Adapter(@NonNull Context context, int resource) {
        super(context, R.layout.balance_listview_layout_row);
        this.context = context;
    }

    static class LayoutHandler{
        TextView TXNTYPE, TXNAMT, CATEGORY, DATE, BALANCE;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        Balance_ListView_Adapter.LayoutHandler layoutHandler;

        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.balance_listview_layout_row, parent, false);
            layoutHandler = new Balance_ListView_Adapter.LayoutHandler();
            layoutHandler.TXNTYPE=(TextView)row.findViewById(R.id.balance_txntype_textview);
            layoutHandler.TXNAMT= (TextView)row.findViewById(R.id.balance_txnamt_textview);
            layoutHandler.CATEGORY= (TextView) row.findViewById(R.id.balance_category_textview);
            layoutHandler.DATE= (TextView)row.findViewById(R.id.balance_date_textview);
            layoutHandler.BALANCE= (TextView)row.findViewById(R.id.balance_balance_textview);
            row.setTag(layoutHandler);

        }  else {
            layoutHandler = (Balance_ListView_Adapter.LayoutHandler)row.getTag();
        }

        Balance_List_Item balanceListItem = (Balance_List_Item) this.getItem(position);

        layoutHandler.TXNTYPE.setText(balanceListItem.getBal_txntype());
        layoutHandler.TXNAMT.setText(balanceListItem.getToString_txnamt());
        layoutHandler.CATEGORY.setText(balanceListItem.getBal_category());
        //to format date to displayed on listview
        layoutHandler.DATE.setText(balanceListItem.getBal_date().substring(0,10));
        layoutHandler.BALANCE.setText(balanceListItem.getToString_balance());
        return row;
    }
}
