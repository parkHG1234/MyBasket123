package com.basketproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by park on 2016-05-31.
 */
public class Match_Out_NewSpeed_CustomList_MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Match_Out_NewSpeed_CustomList_MyData> arrData;
    private LayoutInflater inflater;
    String str_Emblem, str_TeamName, str_Name, str_Time, str_Memo;
    Bitmap bmImg;
    ImageView Emblem;
    public Match_Out_NewSpeed_CustomList_MyAdapter(Context c, ArrayList<Match_Out_NewSpeed_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_customlist_newspeed, parent, false);
        }
        str_Emblem = arrData.get(position).getEmblem();
        str_TeamName = arrData.get(position).getTeamName();
        str_Name = arrData.get(position).getName();
        str_Time = arrData.get(position).getTime();
        str_Memo = arrData.get(position).getMemo();

        Emblem = (ImageView) convertView.findViewById(R.id.NewSpeed_CustomList_Emblem);

        TextView TeamName = (TextView) convertView.findViewById(R.id.NewSpeed_CustomList_TeamName);
        TeamName.setText(str_TeamName);
        Log.i("test1", str_TeamName);
        TextView Name = (TextView) convertView.findViewById(R.id.NewSpeed_CustomList_Name);
        Name.setText(str_Name);
        TextView Time = (TextView) convertView.findViewById(R.id.NewSpeed_CustomList_Time);
        Time.setText(str_Time);
        TextView Comment = (TextView) convertView.findViewById(R.id.NewSpeed_CustomList_Comment);
        Comment.setText(str_Memo);
        try{
            String En_Emblem = URLEncoder.encode(str_Emblem, "utf-8");
            if(str_Emblem.equals(".")) {
                Emblem.setImageResource(R.drawable.basic_image);
            }
            else
            {
                String ImageUrl = "http://210.122.7.195:8080/Web_basket/imgs/Emblem/" + En_Emblem + ".jpg";
                back1 task1 = new back1();
                task1.execute(ImageUrl);
            }

        }
        catch (UnsupportedEncodingException e){

        }
        return convertView;
    }
    private class back1 extends AsyncTask<String, Integer,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);


            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            Emblem.setImageBitmap(bmImg);
        }

    }
}
