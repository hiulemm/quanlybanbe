package com.example.doangiuaki.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doangiuaki.DBhelper.SQLHelper;
import com.example.doangiuaki.R;
import com.example.doangiuaki.adapter.banbeAdapter;
import com.example.doangiuaki.model.banbe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageButton imgbtnThem;
    ListView lvne;
    int THEM_NE= 29;
    int SUA_NE = 11;
    BottomNavigationView menu;

   banbeAdapter banbeAdapter;
    SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }
    private void addControls(){
        sqlHelper = new SQLHelper(MainActivity.this);
        lvne = (ListView) findViewById(R.id.lvne);
        List<banbe> banbes = sqlHelper.getAllbanbes();
        banbeAdapter = new banbeAdapter(this,banbes);
        lvne.setAdapter(banbeAdapter);
        registerForContextMenu(lvne);
    }
    private void addEvents(){
        imgbtnThem = findViewById(R.id.imgbtnThem);
        imgbtnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Them.class);
                startActivityForResult(intent,THEM_NE);
            }
        });
        BottomNavigationView menu = findViewById(R.id.menu);
        menu.setSelectedItemId(R.id.itemBB);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.itemTB){
                    Intent intent = new Intent(getApplicationContext(), thongbao.class);
                    startActivity(intent);
                    return true;
                }else if(item.getItemId()==R.id.itemTK){
                    Intent intent = new Intent(getApplicationContext(), taiKhoan.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == THEM_NE && resultCode == RESULT_OK){
            banbe addBanBe= (banbe) data.getSerializableExtra("AddBanBe");
            sqlHelper.addBanBe(addBanBe);
            List<banbe> banbes = sqlHelper.getAllbanbes();
            banbeAdapter.clear();
            banbeAdapter.addAll(banbes);
            banbeAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Them roi ne ", Toast.LENGTH_SHORT).show();

        }
        else if (requestCode== SUA_NE && resultCode == RESULT_OK) {
            banbe addBanBe = (banbe) data.getSerializableExtra("UpdateBanBe");
            sqlHelper.updateBanBe(addBanBe);
            List<banbe> banbes = sqlHelper.getAllbanbes();
            banbeAdapter.clear();
            banbeAdapter.addAll(banbes);
            banbeAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Sua roi ne ", Toast.LENGTH_SHORT).show();

        }
    }
}