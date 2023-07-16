package org.project.nursecall.util;

import static org.project.nursecall.util.RetrofitClient.retrofitAPI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.project.nursecall.MainActivity;
import org.project.nursecall.R;
import org.project.nursecall.data.PasienData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {

    private Handler handler = new Handler();
    private Runnable refresh;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh = () -> {
            retrofitAPI.getPasien().enqueue(new Callback<ArrayList<PasienData>>() {
                @Override
                public void onResponse(Call<ArrayList<PasienData>> call, Response<ArrayList<PasienData>> response) {
                    if (response.isSuccessful()) for (int i = 0; i < response.body().size(); i++) {
                        if (response.body().get(i).getMsg_status() == 0) notification(getApplicationContext(),
                                response.body().get(i).getId(),
                                response.body().get(i).getName(),
                                response.body().get(i).getTime(),
                                false);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<PasienData>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
            handler.postDelayed(refresh, 5000);
        };
        handler.post(refresh);
        return super.onStartCommand(intent, flags, startId);

    }

    public static void notification(Context context, Integer id, String name, Integer time, Boolean isRemove) {
        if (isRemove) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return;
            NotificationManagerCompat.from(context).cancel(id);
        } else {
            Intent intent = new Intent(context, MainActivity.class)
                    .putExtra("id", id)
                    .putExtra("isOPEN", true)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Intent readIntent = new Intent(context, NotificationBroadcastReceiver.class).setAction("READ").putExtra("id", id);
            PendingIntent readPendingIntent = PendingIntent.getBroadcast(context, id, readIntent, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("id"))
                        .parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000L)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.app_name))
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(name)
                    .setWhen(date.getTime())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setColor(Color.BLACK)
                    .addAction(0, "Tandai sudah dibaca", readPendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(context.getString(R.string.app_name), context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("");
                context.getSystemService(NotificationManager.class).createNotificationChannel(channel);
            }

            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return;
            NotificationManagerCompat.from(context).notify(id, builder.build());
        }
    }
}
