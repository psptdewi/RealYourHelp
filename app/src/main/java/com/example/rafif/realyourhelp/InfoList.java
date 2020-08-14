package com.example.rafif.realyourhelp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class InfoList extends ArrayAdapter<Pasien>{

    private Activity context;
    private List<Pasien> hasilList;

    public InfoList(Activity context, List<Pasien> hasilList){
        super(context, R.layout.list_layout, hasilList);
        this.context = context;
        this.hasilList = hasilList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewSolusi = (TextView) listViewItem.findViewById(R.id.textViewSolusi);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);

        Pasien pasien = hasilList.get(position);

        textViewSolusi.setText(pasien.getSolusi());
        textViewTanggal.setText(pasien.getTanggal());
        textViewTanggal.setText(pasien.getJam());
        textViewName.setText(pasien.getName());

        return listViewItem;
    }
}
