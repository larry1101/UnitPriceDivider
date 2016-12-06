package com.dou.unitpricedivider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-12-6.
 */

public class UPAdapter extends BaseAdapter {

    public final class ListItem{
        public TextView count, price, uprice;
    }

    private LayoutInflater inflater;
    private List data;

    public UPAdapter(Context context, List data){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItem listitem = null;
        Map<String, Float> it = (Map<String, Float>) data.get(i);
        if (view == null){
            listitem = new ListItem();

            view = inflater.inflate(R.layout.item_layout, null);

            listitem.count = (TextView)view.findViewById(R.id.textView_count);
            listitem.price = (TextView)view.findViewById(R.id.textView_price);
            listitem.uprice = (TextView)view.findViewById(R.id.textView_u_price);

            listitem.count.setText(it.get("count")+"");
            listitem.price.setText(it.get("price")+"");

            String usf;

            usf = String.format("%.8f",it.get("unit"));

            listitem.uprice.setText(usf);

            view.setTag(listitem);

        }else {
            listitem = (ListItem)view.getTag();
        }

        return view;
    }
}
