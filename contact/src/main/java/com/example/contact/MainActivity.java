package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加数据
                Uri uri = Uri.parse("content://com.example.contact.provider/contact");
                ContentValues values = new ContentValues();
                values.put("name", "XXX");
                values.put("gender", "f");
                values.put("phone", 123456780);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        /*
        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 查询数据
                Uri uri = Uri.parse("content://com.example.contact.provider/contact");
                Cursor cursor = getContentResolver().query(uri, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String gender = cursor.getString(cursor.getColumnIndex("gender"));
                        int phone = cursor.getInt(cursor.getColumnIndex("phone"));
                        Log.d("MainActivity", "contact name is " + name);
                        Log.d("MainActivity", "contact gender is " + gender);
                        Log.d("MainActivity", "contact phone is " + phone);
                    }
                    cursor.close();
                }
            }
        });*/


    }
}