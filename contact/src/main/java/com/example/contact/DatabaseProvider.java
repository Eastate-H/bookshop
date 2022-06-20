package com.example.contact;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.bookshop.MyDatabaseHelper;

public class DatabaseProvider extends ContentProvider {
    public static final int CONTACT_DIR = 0;
    public static final int CONTACT_ITEM = 1;
    public static final String AUTHORITY = "com.example.databasetest.provider";
    private static UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "contact", CONTACT_DIR);
        uriMatcher.addURI(AUTHORITY, "contact/#", CONTACT_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(), "Contact.db", null, 2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // 查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                cursor = db.query("Contact", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACT_ITEM:
                String contactId = uri.getPathSegments().get(1);
                cursor = db.query("Contact", projection, "id = ?", new String[]{contactId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {// 添加数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
            case CONTACT_ITEM:
                long newContactId = db.insert("Contact", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/contact/" + newContactId);
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CONTACT_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.databasetest. provider.contact";
            case CONTACT_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.databasetest. provider.contact";
            default:
                break;
        }
        return null;
    }
}