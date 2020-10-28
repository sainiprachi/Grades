package com.grades.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.grades.R;
import com.grades.roomdatabase.model.ClassModel;
import com.grades.utils.UserPreference;

import java.util.List;

public class EditClassesAdapter extends RecyclerView.Adapter<EditClassesAdapter.ViewHolder> {
    Context context;
    List<ClassModel> getClassesList;
    onCLickView onCLickView;

   public interface onCLickView{
        void getOnCLickPos(int position);
    }

    public EditClassesAdapter(Context context, List<ClassModel> getClassesList,onCLickView onCLickView){
      this.context=context;
      this.getClassesList=getClassesList;
      this.onCLickView=onCLickView;
    }

    @NonNull
    @Override
    public EditClassesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.edit_classes_adapter, null);
        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull EditClassesAdapter.ViewHolder holder, int position) {
        ClassModel classModel=getClassesList.get(position);
        holder.txtSubject.setText(classModel.getClassName());
        String theme = UserPreference.getInstance(context).getTheme();
        switch (theme) {
            case "Red":
              holder.txtSubject.setTextColor(context.getResources().getColor(R.color.red));


                break;

            case "darkRed":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkRed));
                break;


            case "orange":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.orange));



                break;

            case "darkOrange":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkOrange));
                break;


            case "yellow":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.yellow));

                break;

            case "darkYellow":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkYellow));
                break;

            case "green":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.green));


                break;


            case "darkGreen":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkGreen));
                break;

            case "lightGreen":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.lightGreen));
                break;

            case "newGreen":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.newGreen));
                break;


            case "blue":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.blue));
                break;

            case "darkBlueNew":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkBlueNew));

                break;

            case "purple":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.purple));
                break;

            case "darkPurple":
                holder.txtSubject.setTextColor(context.getResources().getColor(R.color.darkPurple));

                break;
        }
        holder.ivHide.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hidden));

        holder.ivHide.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {

                if (holder.ivHide.getDrawable().getConstantState() == ContextCompat.getDrawable(context,R.drawable.ic_eye).getConstantState()){
                    Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();
                    classModel.setSelectAlpha(false);
                    onCLickView.getOnCLickPos(position);
                    holder.ivHide.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hidden));
                }else {
                    holder.rlParent.setAlpha(0.10f);
                    classModel.setSelectAlpha(true);
                    onCLickView.getOnCLickPos(position);
                    holder.ivHide.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_eye));


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return getClassesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubject;
        ImageView ivHide;
        RelativeLayout rlParent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSubject=itemView.findViewById(R.id.txtSubject);
            ivHide=itemView.findViewById(R.id.ivHide);
            rlParent=itemView.findViewById(R.id.rlParent);
        }
    }
}
