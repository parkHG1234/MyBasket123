package com.basketproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by park on 2016-03-20.
 */
public class Join_Personal2 extends Activity {
    String Sex="";
    String Position="";
    String Name="";
    String Password="";
    String Birth="";
    String Phone="";
    Button Join_Button_Join;
    EditText Join_EditText_Name_Name, Join_EditText_Name_FirstName, Join_EditText_Password;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join_personal2);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        Sex = intent.getStringExtra("Sex");
        Position = intent.getStringExtra("Position");
        Birth = intent.getStringExtra("Birth");
        Phone = intent.getStringExtra("Phone");
        Log.i("test2",Phone);

        Join_EditText_Name_Name = (EditText)findViewById(R.id.Join_EditText_Name_Name);
        Join_EditText_Name_FirstName = (EditText)findViewById(R.id.Join_EditText_Name_FirstName);
        Join_EditText_Password = (EditText)findViewById(R.id.Join_EditText_Password);
        Name = Join_EditText_Name_FirstName.getText().toString() + Join_EditText_Name_Name.getText().toString();
        Password = Join_EditText_Password.getText().toString();
        Join_Button_Join = (Button)findViewById(R.id.Join_Button_Join);

        Join_Button_Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    String postURL = "http://210.122.7.195:8080/Web_basket/Join.jsp";
                    HttpPost post = new HttpPost(postURL);

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("Phone", Phone));
                    params.add(new BasicNameValuePair("Password", Join_EditText_Password.getText().toString()));
                    params.add(new BasicNameValuePair("Name",Join_EditText_Name_FirstName.getText().toString() + Join_EditText_Name_Name.getText().toString()));
                    params.add(new BasicNameValuePair("Birth", Birth));
                    params.add(new BasicNameValuePair("Sex", Sex));
                    params.add(new BasicNameValuePair("Position", Position));

                    UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                    post.setEntity(ent);

                    HttpResponse response = client.execute(post);
                    BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                    String line = null;
                    String result = "";
                    while ((line = bufreader.readLine()) != null) {
                        result += line;
                    }
                    String[][] parsedData = jsonParserList(result);
                    if (parsedData[0][0].equals("succed")) {
                        Toast.makeText(Join_Personal2.this, "회원가입 완료", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Join_Personal2.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Join_Personal2.this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Join_Personal2.this, "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public String[][] jsonParserList(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
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
