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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by park on 2016-06-13.
 */
public class Navi_TeamIntro_CustomList_MyAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Navi_TeamIntro_CustomList_MyData> arrData;
    private LayoutInflater inflater;
    TextView Navi_TeamIntro_CustomList_TeamName;
    ImageView Navi_TeamIntro_CustomList_Emblem;
    String ImageUrl1;
    Bitmap bmImg;
    String TeamName, Emblem;
    public Navi_TeamIntro_CustomList_MyAdapter(Context c, ArrayList<Navi_TeamIntro_CustomList_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_customlist_teamintro, parent, false);
        }
        Navi_TeamIntro_CustomList_TeamName = (TextView)convertView.findViewById(R.id.Navi_TeamIntro_CustomList_TeamName);
        Navi_TeamIntro_CustomList_Emblem = (ImageView)convertView.findViewById(R.id.Navi_TeamIntro_CustomList_Emblem);

        TeamName = arrData.get(position).getTeamName();
        Emblem = arrData.get(position).getEmblem();
        Navi_TeamIntro_CustomList_TeamName.setText(TeamName);
        //URI 한글 인코딩
        try{
            String En_Emblem = URLEncoder.encode(Emblem, "utf-8");
            if(!Emblem.equals(".")) {
                ImageUrl1 = "http://210.122.7.195:8080/Web_basket/imgs/Emblem/" + En_Emblem + ".jpg";
                back1 task1 = new back1();
                task1.execute(ImageUrl1);
            }
        }catch (UnsupportedEncodingException e){

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Navigation_TeamIntro_Focus.class);
                intent.putExtra("TeamName",TeamName);

                context.startActivity(intent);
            }
        });
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
            Navi_TeamIntro_CustomList_Emblem.setImageBitmap(bmImg);
        }

    }
}
