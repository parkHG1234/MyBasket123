package com.basketproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by park on 2016-04-16.
 */
public class Match_In_CustomList_MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Match_In_CustomList_MyData> arrData;
    private LayoutInflater inflater;
    public Match_In_CustomList_MyAdapter(Context c, ArrayList<Match_In_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return arrData.size();
    }
    public Object getItem(int position) {
        return arrData.get(position).getTeamName();
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_customlist_in, parent, false);
        }
        String TeamName = arrData.get(position).getTeamName();
        String Address = arrData.get(position).getAddress();
        String Date = arrData.get(position).getDate();
        String Time = arrData.get(position).getTime();
        final TextView Match_In_CustomList_Schedule = (TextView)convertView.findViewById(R.id.Match_In_CustomList_Schedule);
        Match_In_CustomList_Schedule.setText(TeamName+"/"+Address+"/"+Date+"/"+Time);
        Match_In_CustomList_Schedule.setSelected(true);
        Match_In_CustomList_Schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, TeamInfo.class);
                intent.putExtra("TeamName",arrData.get(position).getTeamName());

                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
