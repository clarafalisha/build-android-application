package com.byngetutor.booking_hotel_191074;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HotelAdapter extends BaseAdapter {
    Activity activity;
    List<Data> items;
    private LayoutInflater inflater;

    public HotelAdapter(Activity activity, List<Data> items) {
        this.activity=activity;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater==null)
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view=inflater.inflate(R.layout.activity_designlist,null);

        TextView id = view.findViewById(R.id.tv_id);
        TextView nama = view.findViewById(R.id.tv_nama);
        TextView nowa = view.findViewById(R.id.tv_nowa);
        TextView tipekamar = view.findViewById(R.id.tv_tipekamar);
        TextView jumlahorang = view.findViewById(R.id.tv_orang);
        TextView jumlahhari = view.findViewById(R.id.tv_hari);
        TextView total = view.findViewById(R.id.tv_total);

        Data data = items.get(i);
        id.setText(data.getId_hotel());
        nama.setText(data.getNama());
        nowa.setText(data.getNo_wa());
        tipekamar.setText(data.getTipe_kamar());
        jumlahorang.setText(data.getJumlah_orang());
        jumlahhari.setText(data.getJumlah_hari());
        total.setText(data.getTotal());

        return view;
    }
}
