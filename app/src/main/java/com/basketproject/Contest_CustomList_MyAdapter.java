package com.basketproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by park on 2016-04-17.
 */
public class Contest_CustomList_MyAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Contest_CustomList_MyData> arrData;
    private LayoutInflater inflater;
    public Contest_CustomList_MyAdapter(Context c, ArrayList<Contest_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return arrData.size();
    }
    public Object getItem(int position) {
        return arrData.get(position).getContestName();
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_customlist_contest, parent, false);
        }
        String ContestName = arrData.get(position).getContestName();
        String Address = arrData.get(position).getAddress();
        Log.i("gg","하하");
        String Point = arrData.get(position).getPoint();

        TextView Contest_CustomList_ContestName = (TextView)convertView.findViewById(R.id.Contest_CustomList_ContestName);
        TextView Contest_CustomList_Address = (TextView)convertView.findViewById(R.id.Contest_CustomList_Address);

        Contest_CustomList_ContestName.setText(ContestName);
        Contest_CustomList_Address.setText(Address);
        return convertView;
    }
}
