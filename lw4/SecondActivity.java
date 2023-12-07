package com.example.lw4;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class SecondActivity extends Activity
{
    String login;
    ArrayList<String> selectedUsers;
    ArrayList<String> stringArray;
    ArrayAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle bundle = getIntent().getExtras();
        login = bundle.get("login").toString();

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        listView = findViewById(R.id.textList);

        selectedUsers = new ArrayList<String>();
        stringArray = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, stringArray);
        listView.setAdapter(adapter);
        TextView text = findViewById(R.id.textView);
        text.setText("Привет, " + login);
        //Toast.makeText(this, "Привет, " + login, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String tempp = sharedPref.getString(login, "");

        loadPreferences();

        if (!selectedUsers.isEmpty())
        {
            //selectedUsers =
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String user = (String) adapter.getItem(position);
                if (listView.isItemChecked(position))
                    selectedUsers.add(user);
                else
                    selectedUsers.remove(user);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                EditText editText = findViewById(R.id.editText);
                String userData = editText.getText().toString();

                adapter.add(userData);
                adapter.notifyDataSetChanged();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < selectedUsers.size(); i++)
                {
                    adapter.remove(selectedUsers.get(i));
                }

                listView.clearChoices();
                selectedUsers.clear();

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
//        for (int i = 0; i < selectedUsers.size(); i++)
//        {
//            editor.putString(login, selectedUsers.get(i).toString());
//        }
        editor.putString(login, stringArray.toString());
        editor.commit();
    }

    protected void loadPreferences()
    {
        SharedPreferences data = this.getPreferences(Context.MODE_PRIVATE);
        String dataset = data.getString(login, null);

        if (Objects.equals(dataset, null)) return;
        if (Objects.equals(dataset, "[]")) return;

        dataset = dataset.replaceAll("^\\[|\\]$", "");
        String[] temp = dataset.split(", ");
        adapter.addAll(temp);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}
