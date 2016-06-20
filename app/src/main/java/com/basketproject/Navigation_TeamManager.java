package com.basketproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by park on 2016-04-04.
 */
public class Navigation_TeamManager extends AppCompatActivity{
    String[][] parsedData;
    static String Id="",Team="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teammanager);
        Button TeamManager_Button_TeamMake = (Button)findViewById(R.id.TeamManager_Button_TeamMake);
        Button TeamManager_Button_MemberManager = (Button)findViewById(R.id.TeamManager_Button_MemberManager);
        Button TeamManager_Button_ScheduleManager = (Button)findViewById(R.id.TeamManager_Button_ScheduleManager);
        Button TeamManager_Button_TeamIntroManager = (Button)findViewById(R.id.TeamManager_Button_TeamIntroManager);
        Button TeamManager_Button_NoticeManager = (Button)findViewById(R.id.TeamManager_Button_NoticeManager);
        TextView TeamManager_TextView_TeamName = (TextView)findViewById(R.id.TeamManager_TextView_TeamName);
        TextView TeamManager_TextView_Duty = (TextView)findViewById(R.id.TeamManager_TextView_Duty);
        LinearLayout TeamManager_layout1 = (LinearLayout)findViewById(R.id.TeamManager_layout1);
        LinearLayout TeamManager_layout2 = (LinearLayout)findViewById(R.id.TeamManager_layout2);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("Id");

        if(Team.equals(".")){
            TeamManager_layout1.setVisibility(View.GONE);
            TeamManager_layout2.setVisibility(View.VISIBLE);
        }
        else{
            TeamManager_layout1.setVisibility(View.VISIBLE);
            TeamManager_layout2.setVisibility(View.GONE);
            String result="";
            try {
                HttpClient client = new DefaultHttpClient();
                String postURL = "http://210.122.7.195:8080/Web_basket/TeamManager.jsp";
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
            if(parsedData[0][0].equals("succed")){
                TeamManager_Button_TeamMake.setVisibility(View.GONE);
                TeamManager_TextView_TeamName.setText(parsedData[0][1]);
                TeamManager_TextView_Duty.setText(parsedData[0][2]);
                if(parsedData[0][3].equals("1")){
                    TeamManager_Button_MemberManager.setVisibility(View.VISIBLE);
                }
                if(parsedData[0][4].equals("1")){
                    TeamManager_Button_ScheduleManager.setVisibility(View.VISIBLE);
                }
                if(parsedData[0][5].equals("1")){
                    TeamManager_Button_TeamIntroManager.setVisibility(View.VISIBLE);
                }
                if(parsedData[0][6].equals("1")){
                    TeamManager_Button_NoticeManager.setVisibility(View.VISIBLE);
                }
            }else if(parsedData[0][0].equals("failed")){
                TeamManager_Button_TeamMake.setVisibility(View.VISIBLE);
            }

            TeamManager_Button_TeamMake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_TeamMake = new Intent(Navigation_TeamManager.this, Navigation_TeamManager_TeamMake1.class);
                    startActivity(intent_TeamMake);
                }
            });
            TeamManager_Button_ScheduleManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_Schedule = new Intent(Navigation_TeamManager.this, Navigation_TeamManager_Schedule.class);
                    startActivity(intent_Schedule);
                }
            });
            TeamManager_Button_TeamIntroManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_TeamIntro = new Intent(Navigation_TeamManager.this, Navigation_TeamManager_TeamIntro.class);
                    intent_TeamIntro.putExtra("Id", Id);
                    startActivity(intent_TeamIntro);
                }
            });
        }

    }
    /////매칭 탭 - out : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7"};
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
