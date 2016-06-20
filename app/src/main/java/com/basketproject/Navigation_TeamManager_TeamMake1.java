package com.basketproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by park on 2016-04-04.
 */
public class Navigation_TeamManager_TeamMake1 extends AppCompatActivity {
    String Str_TeamManager_TeamMake_EditText_TeamName="";
    String Str_TeamManager_TeamMake_EditText_TeamAddress_do="";
    String Str_TeamManager_TeamMake_EditText_TeamAddress_se="";
    String Str_TeamManager_TeamMake_EditText_HomeCourtAndTime;
    String Str_TeamManager_TeamMake_EditText_TeamIntro;
    EditText TeamManager_TeamMake_EditText_TeamName;
    EditText TeamManager_TeamMake_EditText_TeamAddress;
    EditText TeamManager_TeamMake_EditText_HomeCourtAndTime;
    EditText TeamManager_TeamMake_EditText_TeamIntro;
    Spinner TeamManager_TeamMake_Spinner_Address_Do;
    Spinner TeamManager_TeamMake_Spinner_Address_Se;
    static String Id="";
    ArrayAdapter<CharSequence> adspin1, adspin2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teammanager_teammake1);
        TeamManager_TeamMake_EditText_TeamName = (EditText)findViewById(R.id.TeamManager_TeamMake_EditText_TeamName);
        TeamManager_TeamMake_EditText_HomeCourtAndTime = (EditText)findViewById(R.id.TeamManager_TeamMake_EditText_HomeCourtAndTime);
        TeamManager_TeamMake_EditText_TeamIntro = (EditText)findViewById(R.id.TeamManager_TeamMake_EditText_TeamIntro);
        TeamManager_TeamMake_Spinner_Address_Do = (Spinner)findViewById(R.id.TeamManager_TeamMake_Spinner_Address_Do);
        TeamManager_TeamMake_Spinner_Address_Se = (Spinner)findViewById(R.id.TeamManager_TeamMake_Spinner_Address_Se);
        Button TeamManager_TeamMake1_Button_Next = (Button)findViewById(R.id.TeamManager_TeamMake_Button_Next);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("Id");
        adspin1 = ArrayAdapter.createFromResource(Navigation_TeamManager_TeamMake1.this, R.array.spinner_do, R.layout.zfile_spinner_test);
        adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TeamManager_TeamMake_Spinner_Address_Do.setAdapter(adspin1);
        TeamManager_TeamMake_Spinner_Address_Do.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adspin1.getItem(i).equals("서울")) {
                    Str_TeamManager_TeamMake_EditText_TeamAddress_do = "서울";
                    //두번째 스피너 이벤트
                    adspin2 = ArrayAdapter.createFromResource(Navigation_TeamManager_TeamMake1.this, R.array.spinner_do_seoul, R.layout.zfile_spinner_test);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    TeamManager_TeamMake_Spinner_Address_Se.setAdapter(adspin2);
                    TeamManager_TeamMake_Spinner_Address_Se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Str_TeamManager_TeamMake_EditText_TeamAddress_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (adspin1.getItem(i).equals("경기도")) {
                    Str_TeamManager_TeamMake_EditText_TeamAddress_do = "경기도";
                    adspin2 = ArrayAdapter.createFromResource(Navigation_TeamManager_TeamMake1.this
                            , R.array.spinner_do_Gyeonggido, R.layout.zfile_spinner_test);
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    TeamManager_TeamMake_Spinner_Address_Se.setAdapter(adspin2);
                    TeamManager_TeamMake_Spinner_Address_Se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Str_TeamManager_TeamMake_EditText_TeamAddress_se = adspin2.getItem(i).toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TeamManager_TeamMake1_Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Str_TeamManager_TeamMake_EditText_TeamName = TeamManager_TeamMake_EditText_TeamName.getText().toString();
                Str_TeamManager_TeamMake_EditText_HomeCourtAndTime = TeamManager_TeamMake_EditText_HomeCourtAndTime.getText().toString();
                Str_TeamManager_TeamMake_EditText_TeamIntro = TeamManager_TeamMake_EditText_TeamIntro.getText().toString();
                if(Str_TeamManager_TeamMake_EditText_TeamName.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"팀명을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent1 = new Intent(getApplicationContext(), Navigation_TeamManager_TeamMake2.class);
                    intent1.putExtra("Id", Id);
                    intent1.putExtra("TeamName", Str_TeamManager_TeamMake_EditText_TeamName);
                    intent1.putExtra("TeamAddress_do", Str_TeamManager_TeamMake_EditText_TeamAddress_do);
                    intent1.putExtra("TeamAddress_se", Str_TeamManager_TeamMake_EditText_TeamAddress_se);
                    intent1.putExtra("HomeAndTime", Str_TeamManager_TeamMake_EditText_HomeCourtAndTime);
                    intent1.putExtra("TeamIntro", Str_TeamManager_TeamMake_EditText_TeamIntro);
                    startActivity(intent1);
                    finish();
                }
            }
        });

    }
}
