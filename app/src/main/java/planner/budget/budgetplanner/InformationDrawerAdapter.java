package planner.budget.budgetplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rohit on 15-01-2018.
 */

public class InformationDrawerAdapter extends RecyclerView.Adapter<InformationDrawerAdapter.MyViewHolder> {
    private Context context;
    private ClickListener clickListener;
    private LayoutInflater inflater;
    List<InformationDrawer> data= Collections.emptyList();
    public InformationDrawerAdapter(Context context, List<InformationDrawer> data) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.data=data;
        
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InformationDrawer current=data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.drawer_cash_title);
            icon= (ImageView) itemView.findViewById(R.id.drawer_cash_img);

        }

        @Override
        public void onClick(View v) {
            /*context.startActivity(new Intent(context, NavDrawer_Cash.class));*/
            if (clickListener!=null){
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }
}
