package calculadora.com.calculadora;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class log_activity extends AppCompatActivity {
    private Realm realm;

    TextInputLayout us,ps;
    Button b;
    CheckBox remember;
    Button reg;
    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_activity);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        remember = findViewById(R.id.checkremember);

        us = findViewById(R.id.usernamelog);
        ps = findViewById(R.id.password);
        b = findViewById(R.id.buttonsign);
        remember = findViewById(R.id.checkremember);
        reg = findViewById(R.id.buttonlogreg);

        final SharedPreferences settings = getSharedPreferences("GENERAL", 0);
        check = settings.getBoolean("rememberme",false);
        remember.setChecked(check);
        if (Objects.equals(check,true) ) {
            us.getEditText().setText(settings.getString("user",""));
            ps.getEditText().setText(settings.getString("password",""));
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tryname = us.getEditText().getText().toString().trim();
                String trypass = ps.getEditText().getText().toString().trim();

                NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                RealmResults<User> login = realm.where(User.class)
                                    .equalTo("Username",tryname)
                                    .and()
                                    .equalTo("pass",trypass).findAll();
                if (!Objects.equals(login.size(),0)) {
                    check = remember.isChecked();
                    //Obtenemos el editor
                    SharedPreferences.Editor editor = settings.edit();
                    //Editamos
                    editor.putBoolean("rememberme", check);
                    if (Objects.equals(check,true) ) {
                        editor.putString("user", tryname);
                        editor.putString("password", trypass);
                    }
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(),nav_drawer.class);
                    mNotificationManager.cancel(1);
                    login = realm.where(User.class).equalTo("Username",tryname).findAll();
                    String n = login.get(0).getNombre();
                    String ap = login.get(0).getApellido();
                    Toast.makeText(getApplicationContext(), "Welcome "+ n + " " + ap, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel("channel1", "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.setDescription("Channel description");
                        notificationChannel.enableLights(true);
                        notificationChannel.setLightColor(Color.RED);
                        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        notificationChannel.enableVibration(true);
                        mNotificationManager.createNotificationChannel(notificationChannel);
                    }
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel1")
                            .setSmallIcon(android.R.drawable.stat_notify_error)
                            .setContentTitle("LogIn Error")
                            .setContentText("Wrong Username/password. Register for free bellow!");
                    Intent resultIntent = new Intent(getApplicationContext(), log_activity.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
                    // Añade la pila para el Intent,pero no el intent en sí
                    stackBuilder.addParentStack(registerActivity.class);
                    // Añadimos el intent que empieza la activity que está en el top de la pila
                    stackBuilder.addNextIntent(resultIntent);
                    //El pending intent será el que se ejecute cuando la notificación sea pulsada
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    mBuilder.setContentIntent(resultPendingIntent);
                    // mId nos permite actualizar las notificaciones en un futuro
                    // Notificamos
                    Notification noti = mBuilder.build();
                    //O patrón de vibración propio
                    noti.vibrate = new long[]{500, 110, 500, 110, 40, 450, 110, 200, 110, 170, 40, 500};

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        noti.color = Color.CYAN;
                    }
                    noti.flags |= Notification.FLAG_INSISTENT;
                    noti.flags |= Notification.FLAG_NO_CLEAR;
                    noti.flags |= Notification.FLAG_SHOW_LIGHTS;
                    mNotificationManager.notify(1, noti);
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),registerActivity.class);
                startActivity(intent);
            }
        });

    }
}
