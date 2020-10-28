package com.grades.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.grades.R;
import com.grades.model.ThemeModel;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {
    List<ThemeModel>themeModelList;
    OnClickThemeListener onClickThemeListener;

    Context context;
    public ThemeAdapter(List<ThemeModel>themeModelList,Context context,OnClickThemeListener onClickThemeListener){
      this.themeModelList=themeModelList;
      this.context=context;
      this.onClickThemeListener=onClickThemeListener;
    }


    public interface OnClickThemeListener{
      void onClickPos(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.theme_adapter, null);
       return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (themeModelList.get(position).getColorString()!=null){
            String color= themeModelList.get(position).getColorString().substring(3).toUpperCase();
            String newColor="#"+color;
            Log.d("colooor","color"+newColor);
            holder.ivColor.setBackgroundColor(Color.parseColor(newColor));

        }

        holder.rlColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickThemeListener.onClickPos(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return themeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlColor;
        ImageView ivColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlColor=itemView.findViewById(R.id.rlColor);
            ivColor=itemView.findViewById(R.id.ivIcon);
        }
    }
}
