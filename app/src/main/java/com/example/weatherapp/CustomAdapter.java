package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_listview,null);

        ThoiTiet thoiTiet = arrayList.get(i);

        TextView tvDay = view.findViewById(R.id.tv_Ngaythang);
        TextView tvTrangthai = view.findViewById(R.id.tv_trangthai);
        TextView tvTempmax = view.findViewById(R.id.tv_maxTemp);
        TextView tvTempmin = view.findViewById(R.id.tv_minTemp);
        ImageView imageViewStatus = (ImageView) view.findViewById(R.id.img_Trangthai);



        tvDay.setText(thoiTiet.Day);
        tvTrangthai.setText(thoiTiet.TrangThai);
        tvTempmax.setText(thoiTiet.Maxtemp+"°C");
        tvTempmin.setText(thoiTiet.Mintemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/wn/"+thoiTiet.Image+".png").into(imageViewStatus);




        return view;
    }
}
