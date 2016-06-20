package com.basketproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2016-03-28.
 */
public class Match_Out_CustomList_MyAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Match_Out_CustomList_MyData> arrData;
    private LayoutInflater inflater;
    public Match_Out_CustomList_MyAdapter(Context c, ArrayList<Match_Out_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return arrData.size();
    }
    public Object getItem(int position) {
        return arrData.get(position).getCourtName();
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_customlist_out, parent, false);
        }

        TextView CourtName = (TextView)convertView.findViewById(R.id.Match_Out_CustomList_CourtName);
        CourtName.setText(arrData.get(position).getCourtName());
        TextView CourtAddress = (TextView)convertView.findViewById(R.id.Match_Out_CustomList_Address);
        CourtAddress.setText(arrData.get(position).getCourtAddress());
        TextView CourtPlayer = (TextView)convertView.findViewById(R.id.Match_Out_CustomList_Player);
        CourtPlayer.setText(arrData.get(position).getCourtPlayer());
        final ImageButton Match_Out_CustomList_NewSpeed = (ImageButton)convertView.findViewById(R.id.Match_Out_CustomList_NewSpeed);
        Match_Out_CustomList_NewSpeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, Match_Out_NewSpeed.class);
                intent.putExtra("CourtName",arrData.get(position).getCourtName());

                context.startActivity(intent);
            }
        });
        final ImageButton Match_Out_CustomList_Information = (ImageButton)convertView.findViewById(R.id.Match_Out_CustomList_Infomation);
        Match_Out_CustomList_Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourtInfo.class);
                intent.putExtra("CourtName",arrData.get(position).getCourtName());

                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
