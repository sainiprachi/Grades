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
import com.grades.roomdatabase.model.ClassModel;
import com.grades.utils.UserPreference;

import java.util.List;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ViewHolder> {
    OnClickParent onClickParent;
    Context context;
    List<ClassModel>getClasses;


    public ClassesAdapter(Context context, OnClickParent onClickParent,List<ClassModel>getClasses){
        this.onClickParent=onClickParent;
        this.context=context;
        this.getClasses=getClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.classes_adapter, null);
        return new ViewHolder(itemLayoutView);
    }

    public interface OnClickParent{
        void OnClickPosition(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
     holder.rlMainView.setOnClickListener(view -> onClickParent.OnClickPosition(position));
        ClassModel result=getClasses.get(position);
     holder.txtClasses.setText(result.getClassName());
     holder.txtPercentage.setText(result.getMark()+" "+result.getPercentGrade()+"%");
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
        return getClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlMainView;
        TextView txtClasses;
        TextView txtPercentage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMainView=itemView.findViewById(R.id.rlMainView);
            txtClasses=itemView.findViewById(R.id.txtClasses);
            txtPercentage=itemView.findViewById(R.id.txtPercentage);
        }
    }
}
