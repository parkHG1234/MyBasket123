package com.basketproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

/**
 * Created by park on 2016-03-20.
 */
public class Join_Phone extends Activity{
    String Phone="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join_phone);
        final EditText Join_EditText_Phone = (EditText)findViewById(R.id.Join_EditText_Phone);
        Button Login_Button_PhoneNext = (Button)findViewById(R.id.Join_Button_PhoneNext);
        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        digitsButton.setAuthTheme(R.style.CustomDigitsTheme);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                Phone = phoneNumber;
                Join_EditText_Phone.setText(Phone);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

        Login_Button_PhoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Join_Phone.this, Join_Personal1.class);
                intent.putExtra("Phone", Phone);
                startActivity(intent);
                finish();
            }
        });

    }
}
