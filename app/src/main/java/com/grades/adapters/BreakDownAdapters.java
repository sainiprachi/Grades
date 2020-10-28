package com.grades.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.grades.R;

import com.grades.model.BreakDownModel;
import com.grades.utils.UserPreference;

import java.util.List;

public class BreakDownAdapters extends RecyclerView.Adapter<BreakDownAdapters.ViewHolder> {
    List<BreakDownModel.DBean.ResultsBean>breakDownList;
    Context context;

    public BreakDownAdapters(Context context,List<BreakDownModel.DBean.ResultsBean>breakDownList){
        this.breakDownList=breakDownList;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.break_down_adapter, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BreakDownModel.DBean.ResultsBean breakDownModel=breakDownList.get(position);
        holder.txtSubject.setText(breakDownModel.getCategory());
        holder.txtPercentageOfGrade.setText(breakDownModel.getPercentOfGrade()+"% of Grade");
        holder.txtPercentage.setText(breakDownModel.getPercentEarned()+"%");
        holder.txtMark.setText(breakDownModel.getMaxPoints()+"/"+breakDownModel.getNumberOfPoints());
        String currentTheme= UserPreference.getInstance(context).getTheme();
        switch (currentTheme){
            case "Red":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_red);
                break;

            case "darkRed":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_darkred);

                break;


            case "orange":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_orange);

                break;

            case "darkOrange":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_orange);
                break;


            case "yellow":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_yellow);

                break;

            case "darkYellow":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_yellow);
                break;

            case "green":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_green);

                break;


            case "darkGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_green);
                break;

            case "lightGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_light_green);
                break;

            case "newGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_new_green);
                break;


            case "blue":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_blue);
                break;

            case "darkBlueNew":

                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_blue_new);
                break;

            case "purple":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_purple);
                break;

            case "darkPurple":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_purple);

                break;
        }

    }

    @Override
    public int getItemCount() {
        return breakDownList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlMainView;
        TextView txtSubject;
        TextView txtPercentageOfGrade;
        TextView txtPercentage;
        TextView txtMark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMainView=itemView.findViewById(R.id.rlMainView);
            txtSubject=itemView.findViewById(R.id.txtSubject);
            txtPercentageOfGrade=itemView.findViewById(R.id.txtPercentageOfGrade);
            txtPercentage=itemView.findViewById(R.id.txtPercentage);
            txtMark=itemView.findViewById(R.id.txtMark);
        }
    }
}
