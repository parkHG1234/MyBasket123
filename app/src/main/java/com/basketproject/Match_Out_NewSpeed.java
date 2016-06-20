package com.basketproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.widget.AbsListView.OnScrollListener;
/**
 * Created by park on 2016-05-31.
 */
public class Match_Out_NewSpeed extends Activity{
    Match_Out_NewSpeed_CustomList_MyAdapter match_Out_NewSpeed_CustomList_MyAdapter;
    ArrayList<Match_Out_NewSpeed_CustomList_MyData> match_Out_NewSpeed_CustomList_MyData;
    String[][] parsedData;
    static String CourtName="";
    static String EditerView = "close";

    ListView NewSpeed_ListView_NewSpeed;
    LinearLayout NewSpeed_Layout_Editer;

    private LayoutInflater mInflater;
    private boolean mLockListView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_match_out_newspeed);
        NewSpeed_ListView_NewSpeed = (ListView)findViewById(R.id.NewSpeed_ListView_NewSpeed);
        mLockListView = true;

        Intent intent = getIntent();
        CourtName = intent.getStringExtra("CourtName");
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/Match_Out_NewSpeed.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            Log.i("CourtName",CourtName);
            params.add(new BasicNameValuePair("CourtName", CourtName));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            String result = "";
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            parsedData = Out_NewSpeedList_jsonParserList(result);
            Out_NewSpeed_setData();
            match_Out_NewSpeed_CustomList_MyAdapter = new Match_Out_NewSpeed_CustomList_MyAdapter(Match_Out_NewSpeed.this, match_Out_NewSpeed_CustomList_MyData);
            //리스트뷰에 어댑터 연결

            NewSpeed_ListView_NewSpeed.setAdapter(match_Out_NewSpeed_CustomList_MyAdapter);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Out_NewSpeed_setData()
    {
        match_Out_NewSpeed_CustomList_MyData = new ArrayList<Match_Out_NewSpeed_CustomList_MyData>();
        for(int i =0; i<parsedData.length; i++)
        {
            match_Out_NewSpeed_CustomList_MyData.add(new Match_Out_NewSpeed_CustomList_MyData(parsedData[i][0],parsedData[i][1],parsedData[i][2],parsedData[i][3],parsedData[i][4]));
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    /////매칭 탭 - out-newspeed : 받아온 json 파싱합니다.//////////////////////////////////////////////////////////
    public String[][] Out_NewSpeedList_jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5"};
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
