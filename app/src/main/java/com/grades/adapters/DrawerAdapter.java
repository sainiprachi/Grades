package com.grades.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.grades.R;
import com.grades.model.DrawerItemModel;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    public List<DrawerItemModel> stList;
    Context context;

    private EventListener mEventListener;

    public interface EventListener {
        void onItemViewClicked(int position);
    }


    public DrawerAdapter(List<DrawerItemModel> drawerItemList, Context context,EventListener mEventListener) {
        this.stList = drawerItemList;
        this.context = context;
        this.mEventListener=mEventListener;
    }

    // Create new views
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_drawer, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        DrawerItemModel item = stList.get(position);
        viewHolder.ivIcon.setImageDrawable(item.getIcon());
        viewHolder.txtTitle.setText(item.getTitle());
        viewHolder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mEventListener.onItemViewClicked(position);
            }
        });
        if (item.getTitle().equals("Log Out")){
            viewHolder.ivIcon.setVisibility(View.GONE);
        }else {
            viewHolder.ivIcon.setVisibility(View.VISIBLE);
        }





    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView txtTitle;
        RelativeLayout rlParent;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivIcon=itemLayoutView.findViewById(R.id.ivIcon);
            txtTitle=itemLayoutView.findViewById(R.id.txtTitle);
            rlParent=itemLayoutView.findViewById(R.id.rlParent);


        }
    }

    // method to access in activity after updating selection
    public List<DrawerItemModel> getStudentist() {
        return stList;
    }

    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }
}