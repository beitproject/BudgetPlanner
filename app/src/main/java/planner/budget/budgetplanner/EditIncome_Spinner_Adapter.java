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
 * Created by Rohit on 18-03-2018.
 */

public class EditIncome_Spinner_Adapter extends ArrayAdapter<Income_SpinnerItem> {

    public EditIncome_Spinner_Adapter(@NonNull Context context, ArrayList<Income_SpinnerItem> spinnerList) {
        super(context, 0, spinnerList );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return edit_income_initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return edit_income_initView(position, convertView, parent);
    }

    private View edit_income_initView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.edit_income_spinner_row, parent, false);

        ImageView imageViewFlag = (ImageView) convertView.findViewById(R.id.edit_income_spinner_imageview);
        TextView textViewName = (TextView) convertView.findViewById(R.id.edit_income_spinner_textview);

        Income_SpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getnCategoryImage());
            textViewName.setText(currentItem.getnCategoryName());
        }

        return convertView;
    }
}
