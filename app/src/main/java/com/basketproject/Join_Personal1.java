package com.basketproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by park on 2016-03-20.
 */
public class Join_Personal1 extends Activity {
    String Sex="",Position="" , Birth="", Phone="";
    Button Join_Button_Sex_M, Join_Button_Sex_W;
    RadioGroup Join_RadioGroup;
    RadioButton rd;
    DatePicker Join_datePicker;
    int choice_birth=0, choice_sex=0,choice_position=0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join_personal1);
        Intent intent = getIntent();
        Phone = intent.getStringExtra("Phone");
        Join_datePicker = (DatePicker) findViewById(R.id.Join_datePicker);
        Join_datePicker.init(Join_datePicker.getYear(),
                Join_datePicker.getMonth(),
                Join_datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth)
                    {
                        Birth = String.format("%d / %d / %d ", year, monthOfYear+1, dayOfMonth);
                    }
                });

        Join_RadioGroup = (RadioGroup) findViewById(R.id.Join_RadioGroup);
        Join_Button_Sex_M = (Button)findViewById(R.id.Join_Button_Sex_M);
        Join_Button_Sex_W = (Button)findViewById(R.id.Join_Button_Sex_W);

        rd = (RadioButton) findViewById(Join_RadioGroup.getCheckedRadioButtonId());
        Join_Button_Sex_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_sex=1;
                Join_Button_Sex_M.setBackgroundColor(getResources().getColor(R.color.buttonTouch));
                Join_Button_Sex_W.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Sex = "M";
            }
        });

        Join_Button_Sex_W.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice_sex = 1;
                Join_Button_Sex_W.setBackgroundColor(getResources().getColor(R.color.buttonTouch));
                Join_Button_Sex_M.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Sex = "W";
            }
        });
        Join_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                choice_position = 1;
            }
        });
        Position=rd.getText().toString();
        Button Login_Button_PersonalNext = (Button)findViewById(R.id.Join_Button_PersonalNext);
        Login_Button_PersonalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice_sex == 1 && choice_position == 1) {
                    Intent intent = new Intent(Join_Personal1.this, Join_Personal2.class);
                    intent.putExtra("Sex", Sex);
                    intent.putExtra("Position", Position);
                    intent.putExtra("Birth", Birth);
                    Log.i("test",Phone);
                    intent.putExtra("Phone", Phone);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Join_Personal1.this, "체크확인", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
