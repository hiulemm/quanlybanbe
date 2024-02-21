package com.example.doangiuaki.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doangiuaki.R;
import com.example.doangiuaki.model.FormatUtil;
import com.example.doangiuaki.model.banbe;

import java.util.Calendar;

public class chiTiet extends AppCompatActivity {
    TextView tvTenne, tvBietDanhne, tvNgaySinhne,Facebookne,zalone,instagramne,emailne;
    Button btnquaylaine;
    ImageView imgView;
    Calendar ngaySinh;
    banbe banbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        addControls();
        getIntendData();
        addEvents();
    }

    private void addControls() {
        tvTenne = findViewById(R.id.tvTenne);
        tvBietDanhne = findViewById(R.id.tvBietDanhne);
        tvNgaySinhne= findViewById(R.id.tvNgaySinhne);
        Facebookne = findViewById(R.id.Facebookne);
        zalone = findViewById(R.id.zalone);
        instagramne= findViewById(R.id.instagramne);
        emailne = findViewById(R.id.emailne);
        btnquaylaine = findViewById(R.id.btnquaylaine);
        imgView = findViewById(R.id.imgView);
        ngaySinh = Calendar.getInstance();
    }
    private void getIntendData(){
        Intent intent = getIntent();
        if(intent.hasExtra("BANBE")){
            banbe = (banbe) intent.getSerializableExtra("BANBE");
            tvTenne.setText(banbe.getHoTen());
            tvBietDanhne.setText(banbe.getBietDanh());
            ngaySinh.setTime(banbe.getNgaySinh());
            tvNgaySinhne.setText(FormatUtil.formatDate(ngaySinh.getTime()));
            Facebookne.setText(banbe.getFaceboook());
            instagramne.setText(banbe.getInstagram());
            zalone.setText(banbe.getZalo());
            emailne.setText(banbe.getGmail());

            Bitmap bitmap = BitmapFactory.decodeByteArray(banbe.getAvt(),0, banbe.getAvt().length);
            imgView.setImageBitmap(bitmap);
        }
    }
    private void addEvents(){
        btnquaylaine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}