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
 * Created by Rohit on 17-02-2018.
 */

public class Income_Spinner_Adapter extends ArrayAdapter<Income_SpinnerItem> {

    public Income_Spinner_Adapter(Context context, ArrayList<Income_SpinnerItem> spinnerList)   {
        super(context, 0, spinnerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.income_spinner_row, parent, false);

        ImageView imageViewFlag = (ImageView) convertView.findViewById(R.id.income_spinner_imageview);
        TextView textViewName = (TextView) convertView.findViewById(R.id.income_spinner_textview);

        Income_SpinnerItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getnCategoryImage());
            textViewName.setText(currentItem.getnCategoryName());
        }

        return convertView;
    }
}
