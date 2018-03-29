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

/**
 * Created by Rohit on 15-03-2018.
 */

public class EditExpense_Spinner_Adapter extends ArrayAdapter<SpinnerItem> {
    public EditExpense_Spinner_Adapter(@NonNull Context context, ArrayList<SpinnerItem> spinnerList) {
        super(context, 0, spinnerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return editexpense_initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return editexpense_initView(position, convertView, parent);
    }

    private View editexpense_initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.edit_expense_spinner_row, parent, false);
        }
        ImageView imageViewFlag = (ImageView) convertView.findViewById(R.id.edit_expense_spinner_imageview);
        TextView textViewName = (TextView) convertView.findViewById(R.id.edit_expense_spinner_textview);

        SpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getCategoryImage());
            textViewName.setText(currentItem.getCategoryName());
        }

        return convertView;
    }
}
