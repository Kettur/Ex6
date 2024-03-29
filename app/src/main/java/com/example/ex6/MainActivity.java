package com.example.ex6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.view.View;

import com.example.ex6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NotificationChannel channel;
    private NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
                //drawBanner();
            }
        });

        channel = new NotificationChannel(
                "MyNotification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
        manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStop() {
        super.onStop();
        sendNotification();
        drawBanner();
    }

    public void sendNotification(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.POST_NOTIFICATIONS}, 1);
            return;
        }
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(MainActivity.this, channel.getId())
                .setSmallIcon(R.drawable.notification_vector)
                .setContentTitle("WellHello")
                .setContentText(binding.editText.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(1, builder.build());
    }
    public void drawBanner(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {Manifest.permission.SYSTEM_ALERT_WINDOW}, 2);
            return;
        }
        startForegroundService(new Intent(this, MyService.class).putExtra("info", binding.editText.getText().toString()));
    }
}