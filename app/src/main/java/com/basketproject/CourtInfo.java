package com.basketproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

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
 * Created by park on 2016-04-01.
 */
public class CourtInfo extends NMapActivity {
    static String CourtName="";
    NMapView mMapView ;
    NMapController mMapController;
    NMapError err;
    TextView CourtInfo_TextVIew_CourtName, CourtInfo_TextVIew_CourtAddress, CourtInfo_TextVIew_CourtCount,CourtInfo_TextVIew_CourtFloor;
    ImageView CourtInfo_ImageView_Image1,CourtInfo_ImageView_Image2,CourtInfo_ImageView_Image3;
    String[][] parsedData;
    Bitmap bmImg;
    String ImageUrl1,ImageUrl2,ImageUrl3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CourtInfo.this);
        setContentView(R.layout.layout_courtinfo);
        CourtInfo_TextVIew_CourtName = (TextView)findViewById(R.id.CourtInfo_TextVIew_CourtName);
        CourtInfo_TextVIew_CourtAddress = (TextView)findViewById(R.id.CourtInfo_TextVIew_CourtAddress);
        CourtInfo_TextVIew_CourtCount = (TextView)findViewById(R.id.CourtInfo_TextVIew_CourtCount);
        CourtInfo_TextVIew_CourtFloor = (TextView)findViewById(R.id.CourtInfo_TextVIew_CourtFloor);
        CourtInfo_ImageView_Image1 = (ImageView)findViewById(R.id.CourtInfo_ImageView_Image1);
        CourtInfo_ImageView_Image2 = (ImageView)findViewById(R.id.CourtInfo_ImageView_Image2);
        CourtInfo_ImageView_Image3 = (ImageView)findViewById(R.id.CourtInfo_ImageView_Image3);
        LinearLayout MapContainer = (LinearLayout)findViewById(R.id.CourtInfo_Layout_NaverMap);

        Intent intent = getIntent();
        CourtName = intent.getStringExtra("CourtName");
        String result="";
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/CourtInfo.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("CourtName", CourtName));

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
        String CourtName =parsedData[0][0];
        String CourtAddress =parsedData[0][1];
        String CourtCount =parsedData[0][2];
        String CourtFloor =parsedData[0][3];
        String CourtMapX =parsedData[0][4];
        String CourtMapy =parsedData[0][5];
        String Image1 =parsedData[0][6];
        String Image2 =parsedData[0][7];
        String Image3 =parsedData[0][8];
        CourtInfo_TextVIew_CourtName.setText(CourtName);
        CourtInfo_TextVIew_CourtAddress.setText(CourtAddress);
        CourtInfo_TextVIew_CourtCount.setText(CourtCount);
        CourtInfo_TextVIew_CourtFloor.setText(CourtFloor);
        //URI 한글 인코딩
        try{
            String En_Image1 = URLEncoder.encode(Image1, "utf-8");
            String En_Image2 = URLEncoder.encode(Image2, "utf-8");
            String En_Image3 = URLEncoder.encode(Image3, "utf-8");
            ImageUrl1 = "http://210.122.7.195:8080/Web_basket/imgs/Court/"+En_Image1+".jpg";
            back1 task1 = new back1();
            task1.execute(ImageUrl1);
            ImageUrl2 = "http://210.122.7.195:8080/Web_basket/imgs/Court/"+En_Image2+".jpg";
            back2 task2 = new back2();
            task2.execute(ImageUrl2);
            ImageUrl3 = "http://210.122.7.195:8080/Web_basket/imgs/Court/"+En_Image3+".jpg";
            back3 task3 = new back3();
            task3.execute(ImageUrl3);
            CourtInfo_ImageView_Image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CourtInfo.this, CourtInfo_ImageFoucs.class);
                    intent.putExtra("ImageUrl1",ImageUrl1);
                    intent.putExtra("ImageUrl2",ImageUrl2);
                    intent.putExtra("ImageUrl3",ImageUrl3);
                    startActivity(intent);
                    finish();
                }
            });
        }catch (UnsupportedEncodingException e){

        }

        String clientId = "QvBjrtcBorvUrccyP2hr";
// create map view
        mMapView = new NMapView(this);
// set Client ID for Open MapViewer Library
        mMapView.setClientId(clientId);
// 생성된 네이버 지도 객체를 LinearLayout에 추가시킨다.
        MapContainer.addView(mMapView);
// initialize map view
        mMapView.setClickable(true);
// use map controller to zoom in/out, pan and set map center, zoom level etc.
      mMapController = mMapView.getMapController();
        onMapInitHandler(mMapView,err);
        // use built in zoom controls
        mMapView.setBuiltInZoomControls(true, null);
    }
    public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {
        if (errorInfo == null) { // success
            //double a = Integer.parseInt(parsedData[0][5]);
            //double b = Integer.parseInt(parsedData[0][4]);
            mMapController.setMapCenter(new NGeoPoint(126.6507714, 37.4504064), 18);
        } else { // fail
            Log.e("gg", "onMapInitHandler: error=" + errorInfo.toString());
        }
    }
    /////매칭 탭 - out : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9"};
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
            CourtInfo_ImageView_Image1.setImageBitmap(bmImg);
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
            CourtInfo_ImageView_Image2.setImageBitmap(bmImg);
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
            CourtInfo_ImageView_Image3.setImageBitmap(bmImg);
        }

    }
}
