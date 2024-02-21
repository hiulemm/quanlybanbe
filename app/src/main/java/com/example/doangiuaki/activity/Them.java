package com.example.doangiuaki.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.doangiuaki.DBhelper.SQLHelper;
import com.example.doangiuaki.R;
import com.example.doangiuaki.model.FormatUtil;
import com.example.doangiuaki.model.banbe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Them extends AppCompatActivity {
    FloatingActionButton btnThem, btnTroLai;
    ImageView imgView;
    EditText edtten, edtbietdanh, edtfb,edtig,edtzalo, edtemail;
    Button btnngaysinh, btnavt;
    TextView tvngaysinh;
    SQLHelper sqlHelper;
    Calendar ngaySinh;
    banbe banbe = new banbe();
    private static final int CHUPANH = 1;
    private static final int TAIANH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);
        addConTrols();
        addEvents();
    }

    private void addConTrols() {
        edtten = findViewById(R.id.edtten);
        edtbietdanh = findViewById(R.id.edtbietdanh);
        edtfb = findViewById(R.id.edtfb);
        edtig = findViewById(R.id.edtig);
        edtzalo = findViewById(R.id.edtzalo);
        edtemail = findViewById(R.id.edtemail);

        ngaySinh = Calendar.getInstance();
        btnngaysinh = findViewById(R.id.btnngaysinh);
        tvngaysinh = findViewById(R.id.tvngaysinh);

        btnTroLai = findViewById(R.id.btnTroLai);
        btnThem = findViewById(R.id.btnThem);
        btnavt = findViewById(R.id.btnavt);
        imgView = findViewById(R.id.imgView);
        sqlHelper= new SQLHelper(this);
    }

    private void addEvents() {
        btnavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();
            }
            private void chonAnh(){
                PopupMenu popupMenu = new PopupMenu(Them.this, btnavt);
                popupMenu.getMenuInflater().inflate(R.menu.menuavt, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.itemChupAnh){
                            Intent chupAnh = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(chupAnh, CHUPANH);
                        }
                        if(item.getItemId()==R.id.itemTaiAnh) {
                            Intent taiAnh = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(taiAnh, TAIANH);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        btnngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
            private void chonNgay(){
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ngaySinh.set(Calendar.YEAR, year);
                        ngaySinh.set(Calendar.MONTH, monthOfYear);
                        ngaySinh.set(Calendar.DATE,dayOfMonth);
                        tvngaysinh.setText(FormatUtil.formatDate(ngaySinh.getTime()));
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(Them.this, listener, ngaySinh.get(Calendar.YEAR),
                        ngaySinh.get(Calendar.MONTH), ngaySinh.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonThem();
            }
            private void chonThem(){
                banbe.setHoTen(edtten.getText().toString());
                banbe.setBietDanh(edtbietdanh.getText().toString());
                banbe.setNgaySinh(ngaySinh.getTime());
                banbe.setFaceboook(edtfb.getText().toString());
                banbe.setInstagram(edtig.getText().toString());
                banbe.setZalo(edtzalo.getText().toString());
                banbe.setGmail(edtemail.getText().toString());
                imgView.setDrawingCacheEnabled(true);
                Bitmap bitmap = ((BitmapDrawable)imgView.getDrawable()).getBitmap();
                ByteArrayOutputStream khoanh= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 20, khoanh);
                byte[] bytes= khoanh.toByteArray();
                banbe.setAvt(bytes);

                Intent intent= getIntent();
                intent.putExtra("AddBanBe", banbe);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        btnTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if(requestCode == TAIANH){
                Uri taiAnh = data.getData();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), taiAnh);
                    int anh = (int) (bitmap.getHeight()*(256.0/ bitmap.getWidth()));
                    Bitmap scaled= Bitmap.createScaledBitmap(bitmap, 256, anh, true);
                    imgView.setImageBitmap(scaled);
                    banbe.setAvt(getBytes(scaled));
                }catch (IOException e){
                    e.printStackTrace();
                }
            } else if (requestCode==CHUPANH) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                imgView.setImageBitmap(bitmap);
                banbe.setAvt(getBytes(bitmap));

            }
        }
    }
    private byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 20 , stream);
        return stream.toByteArray();
    }
}