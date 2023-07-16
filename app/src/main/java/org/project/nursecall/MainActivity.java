package org.project.nursecall;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static org.project.nursecall.util.RetrofitClient.retrofitAPI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.project.nursecall.adapter.PasienAdapter;
import org.project.nursecall.data.PasienData;
import org.project.nursecall.util.CustomGridLayoutManager;
import org.project.nursecall.util.NotificationService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Boolean doubleBackToExit = false;
    private RecyclerView rvPasien;
    private Handler handler = new Handler();
    private Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, 1);
        }

        rvPasien = findViewById(R.id.rv_pasien);
        rvPasien.setLayoutManager(new CustomGridLayoutManager(this,2));

//        getPasien();

        startService(new Intent(this, NotificationService.class));
    }

    private void getPasien() {
        retrofitAPI.getPasien().enqueue(new Callback<ArrayList<PasienData>>() {
            @Override
            public void onResponse(Call<ArrayList<PasienData>> call, Response<ArrayList<PasienData>> response) {
                if (response.isSuccessful()) {
                    rvPasien.getLayoutManager().onRestoreInstanceState(rvPasien.getLayoutManager().onSaveInstanceState());
                    rvPasien.setAdapter(new PasienAdapter(response.body(),MainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PasienData>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh = () -> {
            getPasien();
            handler.postDelayed(refresh, 5000);
        };
        handler.post(refresh);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_email)
                .setTitle(R.string.app_name)
                .setMessage("Apakah Anda ingin Logout?")
                .setPositiveButton("Iya", (d, w) -> {
                    getSharedPreferences("NURSECALL", Context.MODE_PRIVATE).edit().clear().apply();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .setNegativeButton("Tidak", (d, w) -> d.dismiss())
                .show();
    }
}