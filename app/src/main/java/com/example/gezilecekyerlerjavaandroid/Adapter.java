package com.example.gezilecekyerlerjavaandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> ulkeS;
    ArrayList<String> adS;

    public Adapter(Context context, ArrayList<String> ulkeS, ArrayList<String> adS) {
        this.context = context;
        this.ulkeS = ulkeS;
        this.adS = adS;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ulkeS.toArray().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.newui,null);
        TextView txtViewTitle = convertView.findViewById(R.id.textViewAd);
        TextView txtViewPrice = convertView.findViewById(R.id.textViewUlke);

        txtViewPrice.setText(ulkeS.get(position));
        txtViewTitle.setText(adS.get(position));

        return convertView;
    }
}
