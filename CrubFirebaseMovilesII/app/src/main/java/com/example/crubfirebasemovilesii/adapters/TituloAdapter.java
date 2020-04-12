package com.example.crubfirebasemovilesii.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.crubfirebasemovilesii.R;
import com.example.crubfirebasemovilesii.models.TituloModel;

import java.util.ArrayList;

public class TituloAdapter extends BaseAdapter {

    private final Context context;
    private TituloModel model;
    private ArrayList<TituloModel> list;

    public TituloAdapter(Context context, ArrayList<TituloModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_titulo, viewGroup, false);
        }

        TextView tv_item_titulos = itemView.findViewById(R.id.tv_item_titulo);
        model = list.get(i);
        tv_item_titulos.setText(model.getMtitulo());

        return itemView;
    }
}
