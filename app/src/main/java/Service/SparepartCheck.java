package Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.siba.PengadaanActivity;
import com.example.siba.R;

import java.util.List;

import API.ApiClient;
import API.ApiInterface;
import Models.sparepart;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartCheck extends BroadcastReceiver {

    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("onReceive", "yey");

        Call<List<sparepart>> call = apiInterface.cekStokSparepart();

        call.enqueue(new Callback<List<sparepart>>() {
            @Override
            public void onResponse(Call<List<sparepart>> call, Response<List<sparepart>> response) {
                if(response.isSuccessful()) {
                    Log.d("isSuccess", "yey");
                    //notif

                    setNotification(context);
                }
                else {
                    Log.e("isFail", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<sparepart>> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void setNotification(Context context) {
        NotificationCompat.Builder notification_builder;
        String chanel_id = "3000";
        CharSequence name = "Channel Name";
        String description = "Chanel Description";

        Intent open_activity_intent = new Intent(context, PengadaanActivity.class);
        PendingIntent pending_intent = PendingIntent
                .getActivity(context, 0, open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notification_manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notification_manager.createNotificationChannel(mChannel);
            notification_builder = new NotificationCompat.Builder(context, chanel_id);
        } else {
            notification_builder = new NotificationCompat.Builder(context);
        }
        notification_builder.setSmallIcon(R.drawable.ic_atmaauto)
                .setContentTitle("Sparepart kurang cuuyy")
                .setContentText("MAKANYA CEK SPAREPART TERUS")
                .setAutoCancel(true)
                .setContentIntent(pending_intent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, notification_builder.build());
    }

}
