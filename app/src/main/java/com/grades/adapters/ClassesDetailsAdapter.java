package com.grades.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.grades.R;
import com.grades.activities.DetailsofClass;
import com.grades.model.ClassesDetailModel;
import com.grades.utils.UserPreference;

import java.util.List;

public class ClassesDetailsAdapter extends RecyclerView.Adapter<ClassesDetailsAdapter.ViewHolder> {
    OnClickParent onClickParent;
    Context context;
    private boolean isClickCalculator;
    List<ClassesDetailModel.DBean.ResultsBean> resultsBeans;

    public ClassesDetailsAdapter(List<ClassesDetailModel.DBean.ResultsBean> resultsBeans, Context context, OnClickParent onClickParent, boolean isClickCalculator) {
        this.onClickParent = onClickParent;
        this.context = context;
        this.isClickCalculator = isClickCalculator;
        this.resultsBeans = resultsBeans;
    }

    @NonNull
    @Override
    public ClassesDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.classes_detail_adapter, null);
        return new ViewHolder(itemLayoutView);
    }

    public interface OnClickParent {
        void onClickItem(int position);
        void onSwipeSeekBar(int position);
    }

    int checkedPosition = -1;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ClassesDetailModel.DBean.ResultsBean resultsBean = resultsBeans.get(position);
        holder.txtType.setText(resultsBean.getType());
        holder.txtSubject.setText(resultsBean.getDescription());
        holder.txtPercentage.setText(String.format("%.2f", resultsBean.getPercent())+"%");
        holder.txtMark.setText(String.format("%.2f", resultsBean.getScore()) + "/" + resultsBean.getMaxScore());
        if (resultsBeans.get(position).isSeekBarVisible()){
            holder.llSeekBar.setVisibility(View.VISIBLE);
        }else {
            holder.llSeekBar.setVisibility(View.GONE);

        }
        holder.txtDrop.setText("Drop");
        int maxScore=(int)resultsBean.getMaxScore();
        holder.seekBar.setMax(maxScore);
        int value = (int)resultsBean.getScore();
        holder.seekBar.setProgress(value);
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    if (resultsBeans.get(0).isAddedMock()){
                        double mark=seekBar.getProgress();
                        double max=seekBar.getMax();
                        double percent=mark/max *100;
                        holder.txtMark.setText(seekBar.getProgress()+ "/" +seekBar.getMax());
                        holder.txtPercentage.setText(String.format("%.2f", percent)+"%");
                        resultsBeans.get(0).setScore(mark);
                            resultsBeans.get(0).setIsGraded(true);


                        resultsBeans.get(0).setPercent(percent);
                        onClickParent.onSwipeSeekBar(0);

                    }else {
                        double mark=seekBar.getProgress();
                        double max=seekBar.getMax();
                        double percent=mark/max *100;
                        holder.txtMark.setText(seekBar.getProgress()+ "/" +seekBar.getMax());
                        holder.txtPercentage.setText(String.format("%.2f", percent)+"%");
                        resultsBeans.get(position).setScore(mark);
                        resultsBean.setMaxScore(max);
                        resultsBean.setPercent(percent);
                        onClickParent.onSwipeSeekBar(position);
                    }


                }



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        String currentTheme = UserPreference.getInstance(context).getTheme();


        if (resultsBean.isShowImpact()){
            holder.ivTriangle.setVisibility(View.VISIBLE);
           if (resultsBean.getPercent()==0||resultsBean.getPercent()==0.0){
               holder.ivTriangle.setImageResource(R.drawable.ic_down_triangle);
           }else {
               holder.ivTriangle.setImageResource(R.drawable.ic_up_arrow);
           }
        }else {
            holder.ivTriangle.setVisibility(View.GONE);
        }

        if(resultsBean.isSeekBarVisible()==true)
        {
          holder.llSeekBar.setVisibility(View.VISIBLE);
        }else{
            holder.llSeekBar.setVisibility(View.GONE);
        }

        holder.rlMainView.setOnClickListener(view -> {
            ((DetailsofClass)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < resultsBeans.size(); i++) {
                        resultsBeans.get(i).setSeekBarVisible(false);

                    }
                    resultsBeans.get(position).setSeekBarVisible(true);
                    onClickParent.onClickItem(position);
                   // notifyDataSetChanged();

                }
            });

        });

       holder.cardDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.txtDrop.getText().toString().equals("Drop")){
                    holder.txtDrop.setText("Undrop");
                    holder.txtMark.setText("--");
                    holder.txtPercentage.setText("--");
                }else {
                    holder.txtDrop.setText("Drop");
                    holder.txtPercentage.setText(String.format("%.2f", resultsBeans.get(position).getPercent())+"%");
                    holder.txtMark.setText(resultsBeans.get(position).getScore() + "/" + resultsBeans.get(position).getMaxScore());

                }

            }
        });
        switch (currentTheme) {
            case "Red":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_red);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.red));
                break;

            case "darkRed":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_darkred);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkRed));
                break;


            case "orange":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_orange);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.orange));
                break;

            case "darkOrange":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_orange);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkOrange));
                break;


            case "yellow":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_yellow);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.yellow));
                break;

            case "darkYellow":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_yellow);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkYellow));
                break;

            case "green":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_green);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.green));

                break;


            case "darkGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_green);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkGreen));
                break;

            case "lightGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_light_green);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.lightGreen));
                break;

            case "newGreen":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_new_green);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.newGreen));
                break;


            case "blue":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_blue);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.blue));
                break;

            case "darkBlueNew":

                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_blue_new);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkBlueNew));
                break;

            case "purple":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_purple);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.purple));
                break;

            case "darkPurple":
                holder.rlMainView.setBackgroundResource(R.drawable.shape_dark_purple);
                holder.txtDrop.setTextColor(context.getResources().getColor(R.color.darkPurple));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return resultsBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlMainView;
        LinearLayout llSeekBar;
        TextView txtSubject;
        TextView txtDrop, txtType, txtPercentage, txtMark;
        ImageView ivTriangle;
        SeekBar seekBar;
        Handler handler=new Handler(Looper.myLooper());
        CardView cardDrop;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlMainView = itemView.findViewById(R.id.rlMainViewDetail);
            txtDrop = itemView.findViewById(R.id.txtDrop);
            cardDrop=itemView.findViewById(R.id.cardDrop);
            llSeekBar = itemView.findViewById(R.id.llSeekBar);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtType = itemView.findViewById(R.id.txtType);
            txtPercentage = itemView.findViewById(R.id.txtPercentage);
            txtMark = itemView.findViewById(R.id.txtMark);
            ivTriangle=itemView.findViewById(R.id.ivTriangle);
            seekBar=itemView.findViewById(R.id.seekBar);



        }
    }








}
