package com.example.park.gugudan;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

public class MainActivity extends AppCompatActivity {

    Button btGugudan;
    ListView listView;
    ListAdapter listAdapter;
    ArrayAdapter<String> adapter;
    ArrayList<String> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        btGugudan = (Button)findViewById(R.id.startGame);

        btGugudan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_CODE = 1;

                Intent in = new Intent();
                in.setClass(MainActivity.this, Gugudan.class);
                startActivityForResult(in,1);
            }
        });
    }// end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            String answer = data.getStringExtra("answer");
            item.add("맞은갯수 : " + answer + "개");
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
    }
}
