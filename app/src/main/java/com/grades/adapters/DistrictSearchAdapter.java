package com.grades.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.app.grades.R;
import com.grades.model.DistrictData;

import java.util.ArrayList;
import java.util.List;

public class DistrictSearchAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<DistrictData> items = new ArrayList<>();
    public List<DistrictData> temporarylist = new ArrayList<>();
    private OnDistrictSelectLister onDistrictSelectLister;

    public DistrictSearchAdapter(Context context, List<DistrictData> items, OnDistrictSelectLister onDistrictSelectLister) {
        this.context = context;
        this.items = items;
        this.temporarylist = items;
        this.onDistrictSelectLister = onDistrictSelectLister;
    }

    @Override
    public int getCount() {
        return temporarylist.size();
    }

    @Override
    public Object getItem(int position) {
        return temporarylist.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.districtName = convertView.findViewById(R.id.districtName);
            viewHolder.districtCode = convertView.findViewById(R.id.districtCode);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DistrictData item = temporarylist.get(position);

        viewHolder.districtName.setText(item.getDistrictName());
        if (item.getDistrictCode() != null && !item.getDistrictCode().isEmpty()) {
            viewHolder.districtCode.setText(item.getDistrictCode());
        } else {
            viewHolder.districtCode.setText("");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDistrictSelectLister != null) {
                    onDistrictSelectLister.onSelectItem(temporarylist.get(position).getDistrictName(),temporarylist.get(position).getDistrictCode(), temporarylist.get(position).getDistrictURL(), position);
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView districtName;
        TextView districtCode;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = items.size();
                    results.values = items;
                } else {//do the search
                    List<DistrictData> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (DistrictData s : items)
                        if (s.getDistrictName().toUpperCase().contains(searchStr))
                            resultsData.add(s);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                temporarylist = (List<DistrictData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

   public interface OnDistrictSelectLister {
        void onSelectItem(String name, String code, String Url, int position);

    }
}
