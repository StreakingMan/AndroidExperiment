package com.maxstudio.androidexperiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 40344 on 2017/8/11.
 */

public class CommodityListAdapter extends ArrayAdapter<Commodity> {
    private int resourceId;
    private int mSelect = 0;    //选中项
    public CommodityListAdapter(Context context, int textViewResourceId, List<Commodity> objects){
        super(context, textViewResourceId, objects);
        resourceId =textViewResourceId;
    }

    //刷新选中项
    public void changeSelected(int position){
        if(position != mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Commodity commodity = getItem(position);
        View view;
        CommodityListAdapter.ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new CommodityListAdapter.ViewHolder();
            viewHolder.id = view.findViewById(R.id.tv_id);
            viewHolder.name = view.findViewById(R.id.tv_name);
            viewHolder.price = view.findViewById(R.id.tv_price);
            viewHolder.num = view.findViewById(R.id.tv_num);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (CommodityListAdapter.ViewHolder) view.getTag();
        }

        if(mSelect==position){
            //选中状态

        }else{
            //非选中状态
        }

        viewHolder.id.setText(commodity.getId());
        viewHolder.name.setText(commodity.getName());
        viewHolder.price.setText(Float.toString(commodity.getPrice()));
        viewHolder.num.setText(Integer.toString(commodity.getNum()));
        return view;
    }
    class ViewHolder{
        TextView id;
        TextView name;
        TextView price;
        TextView num;
    }



}
