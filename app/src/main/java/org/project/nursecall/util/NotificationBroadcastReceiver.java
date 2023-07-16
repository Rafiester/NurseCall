package org.project.nursecall.util;

import static org.project.nursecall.util.NotificationService.notification;
import static org.project.nursecall.util.RetrofitClient.retrofitAPI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.project.nursecall.data.PasienData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new Task(goAsync(), intent, context).execute();

    }
    private static class Task extends AsyncTask<String, Integer, String> {

        private final PendingResult pendingResult;
        private final Intent intent;
        private final Context context;

        private Boolean iCheck = false;

        private Task(PendingResult pendingResult, Intent intent, Context context) {
            this.pendingResult = pendingResult;
            this.intent = intent;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            if (intent.getAction().equals("READ")) postRead();
            return null;
        }

        private void postRead() {
            Integer id = intent.getIntExtra("id", 0);
            retrofitAPI.postMsg_status(new PasienData(id)).enqueue(new Callback<PasienData>() {
                @Override
                public void onResponse(Call<PasienData> call, Response<PasienData> response) {
                    if (response.isSuccessful()) if (response.body().getStatus()) notification(context, id, null, null, true);
                }

                @Override
                public void onFailure(Call<PasienData> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pendingResult.finish();
        }
    }
}
