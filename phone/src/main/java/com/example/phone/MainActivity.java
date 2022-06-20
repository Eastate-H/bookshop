package com.example.phone;


import java.util.ArrayList;
import java.util.HashMap;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvList = (ListView) findViewById(R.id.lv_list);
        ArrayList<HashMap<String, String>> readContact = readContact();
        // System.out.println(readContact);
        lvList.setAdapter(new SimpleAdapter(this, readContact, R.layout.contact_list_item,
                new String[] { "name", "phone" }, new int[] { R.id.tv_name, R.id.tv_phone }));

    }

    private ArrayList<HashMap<String, String>> readContact() {
        // 首先从raw_contacts中读取联系人的额id("contact_id")
        // 再根据contact_id从data中查询出相应的联系人姓名和电话
        // 然后根据mimetype来区分联系人姓名和电话

        ArrayList<HashMap<String, String>> list = new ArrayList();

        Uri rawContactUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datatUri = Uri.parse("content://com.android.contacts/data");
        // 从raw_contacts中读取联系人的额id("contact_id")
        Cursor rawContacts = getContentResolver().query(rawContactUri, new String[] { "contact_id" }, null, null, null);
        if (rawContacts != null) {
            while (rawContacts.moveToNext()) {
                String contactId = rawContacts.getString(0);
                System.out.println("contactId==" + contactId);
                Cursor data = getContentResolver().query(datatUri, new String[] { "data1", "mimetype" }, "contact_id=?",
                        new String[] { contactId }, null);
                if (data != null) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    while (data.moveToNext()) {
                        String data1 = data.getString(0);
                        String mimetype = data.getString(1);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            map.put("phone", data1);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            map.put("name", data1);
                        }

                    }
                    list.add(map);
                    data.close();
                }
            }

            rawContacts.close();
            // ontact_id从data中查询出相应的联系人姓名和电话
        }
        return list;
    }

}