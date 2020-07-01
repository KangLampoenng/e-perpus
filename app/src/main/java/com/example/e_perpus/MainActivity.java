package com.example.e_perpus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    TextView TextView2;
    FirebaseAuth autoLog;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoLog = FirebaseAuth.getInstance();
        if (autoLog.getCurrentUser() != null){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        TextView2=(TextView)findViewById(R.id.textView2);
        TextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void register(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
