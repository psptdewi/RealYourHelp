package com.example.rafif.realyourhelp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class solusiList extends ArrayAdapter<Solusi> {

    private Activity context;
    private List<Solusi> solusiList;

    public solusiList(Activity context, List<Solusi> solusiList){
        super(context, R.layout.list_layout, solusiList);
        this.context = context;
        this.solusiList = solusiList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.solusi_layout,null,true);

        TextView textViewSolusi = (TextView) listViewItem.findViewById(R.id.textViewSolusi);

        Solusi solusi = solusiList.get(position);

        textViewSolusi.setText(solusi.getSolusi());

        return listViewItem;
    }
}
