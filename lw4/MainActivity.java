package com.example.lw4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    Intent openIntent;
    private String TAG = "Жизненный цикл";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openIntent = new Intent(MainActivity.this, SecondActivity.class);

        Button comeIn = (Button) findViewById(R.id.comeIn);
        EditText loginText    = (EditText) findViewById(R.id.login);
        EditText passwordText = (EditText) findViewById(R.id.password);

        SharedPreferences sharedPref    = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String login    = sharedPref.getString("login", "");
        String password = sharedPref.getString("password", "");

        if (!login.isEmpty() || !password.isEmpty())
        {
            loginText.setText(login);
            passwordText.setText(password);
        }

        comeIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText text = findViewById(R.id.login);
                String login = text.getText().toString();
                openIntent.putExtra("login", login);
                startActivity(openIntent);
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        EditText loginText    = (EditText) findViewById(R.id.login);
        EditText passwordText = (EditText) findViewById(R.id.password);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("login", loginText.getText().toString());
        editor.putString("password", passwordText.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }
}