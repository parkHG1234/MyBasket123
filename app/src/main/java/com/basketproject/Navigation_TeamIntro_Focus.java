package com.basketproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2016-06-16.
 */
public class Navigation_TeamIntro_Focus extends Activity {
    ImageView TeamInfo_Foucs_ImageView_Emblem;
    Button TeamInfo_Foucs_Button_UniformTop,TeamInfo_Foucs_Button_UniformBottom;
    TextView TeamInfo_Foucs_TextView_TeamName,TeamInfo_Foucs_TextView_TeamAddress,TeamInfo_Foucs_TextView_HomeAndTime, TeamInfo_Foucs_TextView_TeamIntro;
    ImageView TeamInfo_ImageView_Image1,TeamInfo_ImageView_Image2,TeamInfo_ImageView_Image3;
    static String TeamName;
    String[][] parsedData;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_teamintro_focus);

        TeamInfo_Foucs_ImageView_Emblem = (ImageView)findViewById(R.id.TeamInfo_Foucs_ImageView_Emblem);
        TeamInfo_Foucs_Button_UniformTop = (Button)findViewById(R.id.TeamInfo_Foucs_Button_UniformTop);
        TeamInfo_Foucs_Button_UniformBottom = (Button)findViewById(R.id.TeamInfo_Foucs_Button_UniformBottom);
        TeamInfo_Foucs_TextView_TeamName= (TextView)findViewById(R.id.TeamInfo_Foucs_TextView_TeamName);
        TeamInfo_Foucs_TextView_TeamAddress = (TextView)findViewById(R.id.TeamInfo_Foucs_TextView_TeamAddress);
        TeamInfo_Foucs_TextView_HomeAndTime = (TextView)findViewById(R.id.TeamInfo_Foucs_TextView_HomeAndTime);
        TeamInfo_Foucs_TextView_TeamIntro = (TextView)findViewById(R.id.TeamInfo_Foucs_TextView_TeamIntro);
        TeamInfo_ImageView_Image1 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image1);
        TeamInfo_ImageView_Image2 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image2);
        TeamInfo_ImageView_Image3 = (ImageView)findViewById(R.id.TeamInfo_ImageView_Image3);

        Intent intent1 = getIntent();
        TeamName = intent1.getStringExtra("TeamName");
        Log.i("test", TeamName);
        String result="";
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/Navi_TeamIntro_Focus.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("TeamName", TeamName));

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
    }
    /////대회 탭  받아온 json 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10"};
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
}
