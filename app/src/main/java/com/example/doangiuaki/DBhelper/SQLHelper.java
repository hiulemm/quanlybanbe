package com.example.doangiuaki.DBhelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doangiuaki.model.banbe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    //tablename
    private static final String DATABASE_NAME = "banbe.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "banbe";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_HOTEN = "hoten";
    private static final String COLUMN_BIETDANH = "bietdanh";
    private static final String COLUMN_NGAYSINH = "ngaysinh";
    private static final String COLUMN_FACEBOOK = "facebook";
    private static final String COLUMN_INSTAGRAM = "instagram";
    private static final String COLUMN_ZALO = "zalo";
    private static final String COLUMN_GMAIL = "gmail";
    private static final String COLUMN_AVT = "avt";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY ,"
                + COLUMN_HOTEN + " TEXT,"
                + COLUMN_BIETDANH + " TEXT,"
                + COLUMN_NGAYSINH + " DATE,"
                + COLUMN_FACEBOOK + " TEXT,"
                + COLUMN_INSTAGRAM + " TEXT,"
                + COLUMN_ZALO + " TEXT,"
                + COLUMN_GMAIL + " TEXT,"
                + COLUMN_AVT + " BLOB" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBanBe(banbe banbe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, banbe.getHoTen());
        values.put(COLUMN_BIETDANH, banbe.getBietDanh());
        values.put(COLUMN_NGAYSINH, banbe.getNgaySinh().getTime());
        values.put(COLUMN_FACEBOOK, banbe.getFaceboook());
        values.put(COLUMN_INSTAGRAM, banbe.getInstagram());
        values.put(COLUMN_ZALO, banbe.getZalo());
        values.put(COLUMN_GMAIL, banbe.getGmail());
        values.put(COLUMN_AVT, banbe.getAvt());

        long id = db.insert(TABLE_NAME, null,values);
        banbe.setId((int)id);
        db.close();
    }

    public void updateBanBe(banbe banbe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HOTEN, banbe.getHoTen());
        values.put(COLUMN_BIETDANH, banbe.getBietDanh());
        values.put(COLUMN_NGAYSINH, banbe.getNgaySinh().getTime());
        values.put(COLUMN_FACEBOOK, banbe.getFaceboook());
        values.put(COLUMN_INSTAGRAM, banbe.getInstagram());
        values.put(COLUMN_ZALO, banbe.getZalo());
        values.put(COLUMN_GMAIL, banbe.getGmail());
        values.put(COLUMN_AVT, banbe.getAvt());

        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(banbe.getId())};

        int count= db.update(TABLE_NAME, values, selection,selectionArgs);
        db.close();
    }

    public void deleteBanBe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + "=?";
        db.delete(TABLE_NAME, selection,
                new String[]{String.valueOf(id)});
        db.close();
    }
    @SuppressLint("Range")
    public List<banbe> getAllbanbes(){
        List<banbe> banbeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                banbe banbe= new banbe();
                banbe.setId((int)cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                banbe.setHoTen(cursor.getString(cursor.getColumnIndex(COLUMN_HOTEN)));
                banbe.setBietDanh(cursor.getString(cursor.getColumnIndex(COLUMN_BIETDANH)));
                banbe.setNgaySinh(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_NGAYSINH))));
                banbe.setFaceboook(cursor.getString(cursor.getColumnIndex(COLUMN_FACEBOOK)));
                banbe.setInstagram(cursor.getString(cursor.getColumnIndex(COLUMN_INSTAGRAM)));
                banbe.setZalo(cursor.getString(cursor.getColumnIndex(COLUMN_ZALO)));
                banbe.setGmail(cursor.getString(cursor.getColumnIndex(COLUMN_GMAIL)));
                banbe.setAvt(cursor.getBlob(cursor.getColumnIndex(COLUMN_AVT)));
                banbeList.add(banbe);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return banbeList;
    }


}
