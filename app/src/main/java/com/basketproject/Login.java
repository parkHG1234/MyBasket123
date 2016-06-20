package com.basketproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;
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
 * Created by park on 2016-03-23.
 */
public class Login extends AppCompatActivity{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "VUj5qJgHwKvnxLTPVrMqcKYzS";
    private static final String TWITTER_SECRET = "9xVLDFkAXqLgFeVivctLeuuXemrNQGT0Z4eDVMhu11dkrzapaV";


    EditText Login_EditText_Phone,Login_EditText_Password;
    Button Login_Button_Login,Login_Button_Join;
    String[][] parsedData;
    AlertDialog dlg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        setContentView(R.layout.layout_login);
        Login_EditText_Phone = (EditText) findViewById(R.id.Login_EditText_Phone);
        Login_EditText_Password = (EditText) findViewById(R.id.Login_EditText_Password);
        Login_Button_Login = (Button)findViewById(R.id.Login_Button_Login);
        Login_Button_Join = (Button)findViewById(R.id.Login_Button_Join);
        ///자동 로그인//////////////////////////////////////////////////////////
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences prefs_login = getSharedPreferences("basketball_user", MODE_PRIVATE);
        if(prefs_login.getString("login1", "").equals("succed"))
        {
            String msg_phone = prefs_login.getString("phone", "");
            String msg_pw = prefs_login.getString("pw", "");
            String result = SendByHttp(msg_phone,msg_pw);
            parsedData = jsonParserList(result);
//////////////로그인 성공시 메인 엑티비티 이동////////////////////////////////
            if(parsedData[0][0].equals("succed"))
            {
                Intent intent = new Intent(Login.this, MainActivity.class);
                intent.putExtra("Name",parsedData[0][3]);
                intent.putExtra("Birth",parsedData[0][5]);
                intent.putExtra("Sex",parsedData[0][4]);
                intent.putExtra("Position", parsedData[0][6]);
                intent.putExtra("Team", parsedData[0][7]);
                intent.putExtra("Id", parsedData[0][1]);
                startActivity(intent);
                finish();

            }
            else{
                dlg = new AlertDialog.Builder(this).setTitle("마이바스켓")
                        ////나중에 아이콘모양 넣기 .setIcon(R.drawable.icon)~~
                        .setMessage("서버와의 접속에 실패하였습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        }
        Login_Button_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_Button_Join(view);
            }
        });
        Login_Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Login_Button_Login(view);
            }
        });
    }
    /**
     * Google Play Service를 사용할 수 있는 환경이지를 체크한다.
     */
    public void Login_Button_Join(View view) {
        Intent intent = new Intent(Login.this, Join_Phone.class);
        startActivity(intent);
        finish();
    }

    public void Login_Button_Login(View view) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences prefs_login = getSharedPreferences("basketball_user", MODE_PRIVATE);
        SharedPreferences.Editor editor_login = prefs_login.edit();

       // mHandler = new Handler();

        String msg_phone = Login_EditText_Phone.getText().toString();
        String msg_pw = Login_EditText_Password.getText().toString();
        String result = SendByHttp(msg_phone, msg_pw);
        parsedData = jsonParserList(result);
//////////////로그인 성공시 메인 엑티비티 이동////////////////////////////////
        if(parsedData[0][0].equals("succed"))
        {
            editor_login.putString("phone", Login_EditText_Phone.getText().toString());
            editor_login.putString("pw", Login_EditText_Password.getText().toString());
            editor_login.putString("Team", parsedData[0][7]);
            editor_login.putString("login1", "succed");
            editor_login.commit();

            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("Name",parsedData[0][3]);
            intent.putExtra("Birth",parsedData[0][5]);
            intent.putExtra("Sex",parsedData[0][4]);
            intent.putExtra("Position", parsedData[0][6]);
            intent.putExtra("Team", parsedData[0][7]);
            intent.putExtra("Id", parsedData[0][1]);
            startActivity(intent);
            finish();
        }
        ////////////////실패시 다이얼 로그 띄웁니다.//////////////////////////////////////////////////
        else if(parsedData[0][0].equals("failed")){
            dlg = new AlertDialog.Builder(this).setTitle("바스켓")
                    ////나중에 아이콘모양 넣기 .setIcon(R.drawable.icon)~~
                    .setMessage("아이뒤 패스워드를 확인해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
        else{
            dlg = new AlertDialog.Builder(this).setTitle("바스켓")
                    ////나중에 아이콘모양 넣기 .setIcon(R.drawable.icon)~~
                    .setMessage("서버와의 접속에 실패하였습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }

    }

    private String SendByHttp(String msg_phone, String msg_pw) {
        if (msg_phone == null)
        {
            msg_phone = "";
        }
        try {
            HttpClient client = new DefaultHttpClient();
            String postURL = "http://210.122.7.195:8080/Web_basket/Login.jsp";
            HttpPost post = new HttpPost(postURL);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Phone", msg_phone));
            params.add(new BasicNameValuePair("Password", msg_pw));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = null;
            String result = "";

            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6", "msg7", "msg8"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++)
            {
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++)
                {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }

            }
            for(int i=0;i<parseredData.length;i++)
            {
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][0]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][1]);
                Log.i("JSON을 분석한 데이터"+i+":",parseredData[i][2]);
            }
            return parseredData;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
