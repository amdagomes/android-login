package com.ifpb.loginapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int RESPONSE_SUCCESS = 200;
    private final String ACTION_LOGIN = "login_request";

    private Button btnAcessar;
    private EditText email, password;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            btnAcessar.setText("Acessar");
            btnAcessar.setEnabled(true);

            int responseRequestPost = intent.getIntExtra("response", 401);

            if (responseRequestPost == RESPONSE_SUCCESS){
                Toast.makeText(getBaseContext(), "Sucesso",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "Falha",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAcessar = findViewById(R.id.btnAcessar);
        email = findViewById(R.id.inptEmail);
        password = findViewById(R.id.inptPassword);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(broadcastReceiver, new IntentFilter(ACTION_LOGIN));
    }

    public void acessar(View view) {
        btnAcessar.setEnabled(false);
        btnAcessar.setText("Aguarde");

        Intent intent = new Intent(this, LoginService.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", email.getText().toString());
        intent.setAction(ACTION_LOGIN);

        startService(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

}
