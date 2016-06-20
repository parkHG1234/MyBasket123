package com.basketproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2016-04-14.
 */
public class Navigation_TeamInfo extends AppCompatActivity{
    TextView TeamInfo_TextView_TeamName,TeamInfo_TextView_TeamAddress, TeamInfo_TextView_HomeAndTime,TeamInfo_TextView_TeamIntro;
    ImageView TeamInfo_ImageView_Image1, TeamInfo_ImageView_Image2, TeamInfo_ImageView_Image3, TeamInfo_ImageView_Emblem;
    Button TeamInfo_Button_UniformTop,TeamInfo_Button_UniformBottom;
    LinearLayout TeamInfo_layout1,TeamInfo_layout2;
    static String Id="";
    String[][] parsedData;
    String ImageUrl1,ImageUrl2,ImageUrl3,ImageUrl4;
    Bitmap bmImg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teaminfo);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TeamInfo_TextView_TeamName = (TextView)findViewById(R.id.TeamInfo_TextView_TeamName);
        TeamInfo_TextView_TeamAddress = (TextView)findViewById(R.id.TeamInfo_TextView_TeamAddress);
        TeamInfo_TextView_HomeAndTime = (TextView)findViewById(R.id.TeamInfo_TextView_HomeAndTime);
        TeamInfo_TextView_TeamIntro = (TextView)findViewById(R.id.TeamInfo_TextView_TeamIntro);
        TeamInfo_ImageView_Image1 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image1);
        TeamInfo_ImageView_Image2 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image2);
        TeamInfo_ImageView_Image3 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image3);
        TeamInfo_ImageView_Emblem = (ImageView)findViewById(R.id.TeamInfo_ImageView_Emblem);
        TeamInfo_layout1 = (LinearLayout)findViewById(R.id.TeamInfo_layout1);
        TeamInfo_layout2 = (LinearLayout)findViewById(R.id.TeamInfo_layout2);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("Id");

        String result="";
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/NaviTeamInfo.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Id", Id));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        parsedData = jsonParserList(result);
        if(parsedData[0][0].equals(".")){
            TeamInfo_layout1.setVisibility(View.GONE);
            TeamInfo_layout2.setVisibility(View.VISIBLE);
        }
        else{
            TeamInfo_layout2.setVisibility(View.GONE);
            TeamInfo_layout1.setVisibility(View.VISIBLE);
            String TeamName = parsedData[0][0];
            String TeamAddress = parsedData[0][1];
            String HomeAndTime = parsedData[0][2];
            String TeamIntro = parsedData[0][3];
            String UniformTop = parsedData[0][4];
        String UniformBottom = parsedData[0][5];
        String Image1 = parsedData[0][6];
        String Image2 = parsedData[0][7];
        String Image3 = parsedData[0][8];
        String Emblem = parsedData[0][9];

        TeamInfo_TextView_TeamName.setText(TeamName);
        TeamInfo_TextView_TeamAddress.setText(TeamAddress);
        TeamInfo_TextView_HomeAndTime.setText(HomeAndTime);
        TeamInfo_TextView_TeamIntro.setText(TeamIntro);
        //URI 한글 인코딩
        try{
            String En_Image1 = URLEncoder.encode(Image1, "utf-8");
            String En_Image2 = URLEncoder.encode(Image2, "utf-8");
            String En_Image3 = URLEncoder.encode(Image3, "utf-8");
            String En_Emblem = URLEncoder.encode(Emblem, "utf-8");
            if(!Image1.equals(".")) {
                ImageUrl1 = "http://210.122.7.195:8080/Web_basket/imgs/Team/" + En_Image1 + ".jpg";
                back1 task1 = new back1();
                task1.execute(ImageUrl1);
            }
            if(!Image2.equals(".")) {
                ImageUrl2 = "http://210.122.7.195:8080/Web_basket/imgs/Team/" + En_Image2 + ".jpg";
                back2 task2 = new back2();
                task2.execute(ImageUrl2);
            }
            if(!Image3.equals(".")) {
                ImageUrl3 = "http://210.122.7.195:8080/Web_basket/imgs/Team/" + En_Image3 + ".jpg";
                back3 task3 = new back3();
                task3.execute(ImageUrl3);
            }
            if(!Image1.equals(".")) {
                ImageUrl4 = "http://210.122.7.195:8080/Web_basket/imgs/Emblem/" + En_Emblem + ".jpg";
                back4 task4 = new back4();
                task4.execute(ImageUrl4);
            }
            TeamInfo_ImageView_Image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Navigation_TeamInfo.this, CourtInfo_ImageFoucs.class);
                    intent.putExtra("ImageUrl1",ImageUrl1);
                    intent.putExtra("ImageUrl2",ImageUrl2);
                    intent.putExtra("ImageUrl3",ImageUrl3);
                    startActivity(intent);
                    finish();
                }
            });
        }
        catch (UnsupportedEncodingException e){

        }
        int UniformTop_color = Integer.parseInt(UniformTop);
        int UniformBottom_color = Integer.parseInt(UniformBottom);
        Navigation_TeamInfo.this.findViewById(R.id.TeamInfo_Button_UniformTop).setBackgroundColor(UniformTop_color);
        Navigation_TeamInfo.this.findViewById(R.id.TeamInfo_Button_UniformBottom).setBackgroundColor(UniformBottom_color);
        }
    }
    /////네비 탭 - 매 팀정보 : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9", "msg10"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
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
            TeamInfo_ImageView_Image1.setImageBitmap(bmImg);
        }

    }
    private class back2 extends AsyncTask<String, Integer,Bitmap> {

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
            TeamInfo_ImageView_Image2.setImageBitmap(bmImg);
        }

    }
    private class back3 extends AsyncTask<String, Integer,Bitmap> {

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
            TeamInfo_ImageView_Image3.setImageBitmap(bmImg);
        }

    }
    private class back4 extends AsyncTask<String, Integer,Bitmap> {

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

            TeamInfo_ImageView_Emblem.setImageBitmap(bmImg);
            getCircleBitmap(bmImg);
        }

    }
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth()/2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
