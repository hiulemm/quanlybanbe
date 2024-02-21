package com.example.doangiuaki.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.doangiuaki.DBhelper.SQLHelper;
import com.example.doangiuaki.R;
import com.example.doangiuaki.model.FormatUtil;
import com.example.doangiuaki.model.banbe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class suaBanBe extends AppCompatActivity {
    FloatingActionButton btnSua,btnTroLai;

    ImageView imgView;
    EditText edtten, edtbietdanh, edtfb,edtig,edtzalo, edtemail;
    Button btnngaysinh, btnavt;
    TextView tvngaysinh;
    SQLHelper db;
    Calendar ngaySinh;
    banbe banbe;
    private static final int CHUPANH = 3;
    private static final int TAIANH = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_ban_be);
        addControls();
        addEvents();
        getIntendData();

    }
    private void addControls(){

        edtten = findViewById(R.id.edtten);
        edtbietdanh = findViewById(R.id.edtbietdanh);
        edtfb = findViewById(R.id.edtfb);
        edtig = findViewById(R.id.edtig);
        edtzalo = findViewById(R.id.edtzalo);
        edtemail = findViewById(R.id.edtemail);

        btnngaysinh = findViewById(R.id.btnngaysinh);
        tvngaysinh = findViewById(R.id.tvngaysinh);

        btnTroLai = findViewById(R.id.btnTroLai);
        btnSua = findViewById(R.id.btnSua);
        btnavt = findViewById(R.id.btnavt);

        imgView = findViewById(R.id.imgView);
        ngaySinh = Calendar.getInstance();
    }
    private void getIntendData(){
        Intent intent = getIntent();
        if(intent.hasExtra("BANBE")){
            banbe = (banbe) intent.getSerializableExtra("BANBE");
            edtten.setText(banbe.getHoTen());
            edtbietdanh.setText(banbe.getBietDanh());
            ngaySinh.setTime(banbe.getNgaySinh());
            tvngaysinh.setText(FormatUtil.formatDate(ngaySinh.getTime()));
            edtfb.setText(banbe.getFaceboook());
            edtig.setText(banbe.getInstagram());
            edtzalo.setText(banbe.getZalo());
            edtemail.setText(banbe.getGmail());
            Bitmap bitmap = BitmapFactory.decodeByteArray(banbe.getAvt(),0, banbe.getAvt().length);
            imgView.setImageBitmap(bitmap);
        }
    }
    private void addEvents(){
        btnavt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();
            }
            private void chonAnh(){
                PopupMenu popupMenu = new PopupMenu(suaBanBe.this, btnavt);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(suaBanBe.this, listener, ngaySinh.get(Calendar.YEAR),
                        ngaySinh.get(Calendar.MONTH), ngaySinh.get(Calendar.DATE));
                datePickerDialog.show();
            }

        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonSua();
            }
            private void chonSua(){
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
                intent.putExtra("UpdateBanBe", banbe);
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
}