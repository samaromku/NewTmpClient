package com.example.andrey.newtmpclient.service.gps;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.andrey.newtmpclient.App;
import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.needdoingtasks.NeedDoingTasksActivity;
import com.example.andrey.newtmpclient.entities.Address;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.entities.TaskEnum;
import com.example.andrey.newtmpclient.entities.UserCoords;
import com.example.andrey.newtmpclient.managers.AddressManager;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.managers.UserCoordsManager;
import com.example.andrey.newtmpclient.managers.UsersManager;
import com.example.andrey.newtmpclient.service.gps.di.GpsComponent;
import com.example.andrey.newtmpclient.service.gps.di.GpsModule;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.DistanceUtil;
import com.example.andrey.newtmpclient.storage.MyLocation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class GpsService extends IntentService implements GpsView{
    private static final String TAG = "GpsService";
    UserCoordsManager userCoordsManager = UserCoordsManager.INSTANCE;
    private static final int INTERVAL = 1000 * 60 * 3;
    private UsersManager usersManager = UsersManager.INSTANCE;
    private TasksManager tasksManager = TasksManager.INSTANCE;
    private AddressManager addressManager = AddressManager.INSTANCE;
    private List<Address>userAddresses = new ArrayList<>();
    private static final double MIN_DISTANCE = 0.1;
    @Inject
    GpsPresenter presenter;


    public static Intent newIntent(Context context){
        return new Intent(context, GpsService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((GpsComponent) App.getComponentManager()
                .getPresenterComponent(getClass(), new GpsModule(this))).inject(this);
    }

    public GpsService() {
        super(TAG);
    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = GpsService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(isOn){
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTERVAL, pi);
        }else{
            am.cancel(pi);
            pi.cancel();
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        new Thread(() -> {
            Looper.prepare();
            Log.i(TAG, "onHandleIntent: start...........................................................");
                MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                    @Override
                    public void gotLocation(final Location location) {
                        if (UsersManager.INSTANCE.getUser() != null) {
                            UserCoords userCoords = new UserCoords(location.getLatitude(), location.getLongitude());
                            if(userCoordsManager.getUserCoords()!=null &&
                                    userCoords.getLat()==userCoordsManager.getUserCoords().getLat()&&
                                    userCoords.getLog()==userCoordsManager.getUserCoords().getLog())
                            {
                                Log.i(TAG, "gotLocation: same coordinates");
                                return;
                            }
                            userCoordsManager.addUserCoords(userCoords);
                            userCoordsManager.setUserCoords(userCoords);
                            userCoordsManager.setLocation(location);

                            presenter.addCoordinates(userCoords);
                            AuthChecker.serverErrorStopService(getApplicationContext());
                            getDistances(userCoords.getLat(), userCoords.getLog());
                        }
                    }
                };
                MyLocation myLocation = new MyLocation();
                myLocation.getLocation(getApplicationContext(), locationResult);
        }).start();

    }

    private void getDistances(double userLat, double userLon){
        DistanceUtil distanceUtil = new DistanceUtil();
        userAddresses.clear();
        for(Task t:tasksManager.usersTask(usersManager.getUser())){
            userAddresses.add(addressManager.getUserAddressById(t.getAddressId()));
        }
        tasksManager.removeDoing();
        for(Address a: userAddresses) {
            if (a != null) {
                double lat1 = Double.parseDouble(a.getCoordsLat());
                double log1 = Double.parseDouble(a.getCoordsLon());

                double distance = distanceUtil.getDistance(log1, lat1, userLat, userLon);
                if (distance <= MIN_DISTANCE) {
                    for (Task t : tasksManager.usersTask(usersManager.getUser())) {
                        if (t.getAddressId() == a.getId() && t.getStatus().equals(TaskEnum.DISTRIBUTED_TASK)) {
                            askForDoingTask(t);
                            tasksManager.addNeedDoing(t);
                        }
                    }
                }
            }
        }
    }

    private void askForDoingTask(Task task) {
        sendNotification(task);
    }

    private void sendNotification(Task task) {
        Intent intent = new Intent(this, NeedDoingTasksActivity.class);
        intent.putExtra(TASK_NUMBER, task.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        /*String bigText = "Адрес: " + task.getAddress()+
                "\nСтатус: " + task.getStatus()+
                "\nВажность: " + task.getImportance()+
                "\nВыполнить до: " + task.getDoneTime() +
                "\nЧто сделать: " + task.getBody();*/

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle("Начать выполнение?")
                .setContentText("Есть заявки поблизости")
               /* .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))*/
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

