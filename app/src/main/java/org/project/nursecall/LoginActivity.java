package org.project.nursecall;

import static org.project.nursecall.util.RetrofitClient.retrofitAPI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import org.project.nursecall.data.LoginData;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPass;
    private Boolean doubleBackToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.mb_login).setOnClickListener(v -> postLogin());

    }

    private void postLogin() {
        etUser = findViewById(R.id.et_username);
        etPass = findViewById(R.id.et_password);
        retrofitAPI.postLogin(new LoginData(etUser.getText().toString(), etPass.getText().toString())).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) if (response.body().getStatus()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else Toast.makeText(LoginActivity.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                t.printStackTrace();
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