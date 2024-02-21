package com.example.doangiuaki.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doangiuaki.DBhelper.SQLHelper;
import com.example.doangiuaki.R;
import com.example.doangiuaki.activity.chiTiet;
import com.example.doangiuaki.activity.suaBanBe;
import com.example.doangiuaki.model.FormatUtil;
import com.example.doangiuaki.model.banbe;

import java.util.List;

public class banbeAdapter extends ArrayAdapter<banbe> {
    int SUA_NE = 11;
    public banbeAdapter(@NonNull Context context, @NonNull List<banbe> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        banbe banbe = getItem(position);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_thong_tin, parent, false);
        }

        TextView tvhoTenne = (TextView) view.findViewById(R.id.tvhoTenne);
        ImageView imgviewavt = (ImageView) view.findViewById(R.id.imgviewavt);
        ImageButton btnmenune = (ImageButton) view.findViewById(R.id.btnmenune);
        TextView tvNgaySinhne = (TextView) view.findViewById(R.id.tvNgaySinhne);

        tvhoTenne.setText(banbe.getHoTen());
        tvNgaySinhne.setText(FormatUtil.formatDate(banbe.getNgaySinh()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(banbe.getAvt(), 0, banbe.getAvt().length);
        imgviewavt.setImageBitmap(bitmap);


        btnmenune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.bacham, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.itemchitiet) {
                            Intent intent = new Intent(getContext(), chiTiet.class);
                            intent.putExtra("BANBE", banbe);
                            getContext().startActivity(intent);
                        }
                        if (item.getItemId() == R.id.itemSua) {
                            Intent intent = new Intent(getContext(), suaBanBe.class);
                            intent.putExtra("BANBE", banbe);
                            ((Activity) getContext()).startActivityForResult(intent, SUA_NE );
                        }
                        if (item.getItemId() == R.id.itemXoa) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Ban co muon xoa khum ? ");
                            builder.setMessage("Ban muon thiet ha ?");
                            builder.setPositiveButton("XOA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLHelper sqlHelper = new SQLHelper(getContext());
                                    sqlHelper.deleteBanBe(banbe.getId());
                                    remove(banbe);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    Toast.makeText(getContext(),"Xoa xong roi ne ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog= builder.create();
                            dialog.show();
                        }
                        return false;
                    }
                });
            }
        });
        return view;
    }
}

