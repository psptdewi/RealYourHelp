package com.example.rafif.realyourhelp;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<Gejala> dataGejalas;
    public ArrayList<Gejala> gejalanyas = new ArrayList<>();

    public MyAdapter(Context context,ArrayList<Gejala> modelArrayList) {
        this.context = context;
        this.dataGejalas = modelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return dataGejalas.size();
    }

    @Override
    public Object getItem(int position) {
        return dataGejalas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gejala, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_gejala);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.textGejala);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvAnimal.setText(dataGejalas.get(position).getNama());
        holder.checkBox.setChecked(dataGejalas.get(position).getSelected());

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tempview = (View) holder.checkBox.getTag(R.integer.btnplusview);
                TextView tv = (TextView) tempview.findViewById(R.id.textGejala);
                Integer pos = (Integer) holder.checkBox.getTag();
                Toast.makeText(context, dataGejalas.get(pos).getId() + " dipilih", Toast.LENGTH_SHORT).show();

                if (dataGejalas.get(pos).getSelected()) {
                    dataGejalas.get(pos).setSelected(false);
                } else {
                    dataGejalas.get(pos).setSelected(true);
                    gejalanyas.add(dataGejalas.get(pos));
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        protected CheckBox checkBox;
        private TextView tvAnimal;
    }

}