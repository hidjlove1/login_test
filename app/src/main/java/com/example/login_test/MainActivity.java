package com.example.login_test;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TextInputEditText TextInputEditText_email, TextInputEditText_password;
    Button Button_login;
    String emailOK = "1234@gmail.com";
    String passwordOK = "1234";

    String InputEmail = "";
    String InputPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_email     =findViewById(R.id.TextInputEditText_email);
        TextInputEditText_password  =findViewById(R.id.TextInputEditText_password);
        Button_login                =findViewById(R.id.Button_login);

        // 1. 값을 가져온다. - 검사 (1234@gmail.com / 1234)
        // 2. 클릭을 감지한다.
        // 3. 1번의 값을 다음 액티비티에 넘긴다.

        Button_login.setClickable(false);
        TextInputEditText_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("CHO",  s + "," + count);
                if(s !=null) {
                    InputEmail = s.toString();
                    Button_login.setClickable(validation());
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        TextInputEditText_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("CHO",  s + "," + count);
                if(s != null) {
                    InputPassword = s.toString();
                    Button_login.setClickable(validation());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    //    Button_login.setClickable(true);
        Button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TextInputEditText_email.getText().toString();
                String password = TextInputEditText_password.getText().toString();

                Intent intent = new Intent(MainActivity.this, LoginResultActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });


    }
    public boolean validation() {
        return InputPassword.equals(passwordOK) && InputEmail.equals(emailOK);
    }
}
