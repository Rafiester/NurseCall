package org.project.nursecall;

import static org.project.nursecall.util.RetrofitClient.retrofitAPI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.project.nursecall.adapter.PasienAdapter;
import org.project.nursecall.data.PasienData;
import org.project.nursecall.util.CustomGridLayoutManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Boolean doubleBackToExit = false;
    private RecyclerView rvPasien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPasien = findViewById(R.id.rv_pasien);
        rvPasien.setLayoutManager(new CustomGridLayoutManager(this,2));
        getPasien();
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

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExit) finishAndRemoveTask();

        doubleBackToExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar dari aplikasi", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExit = false, 2000);
    }
}