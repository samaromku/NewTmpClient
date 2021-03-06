package com.example.andrey.newtmpclient.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.andrey.newtmpclient.R;
import com.example.andrey.newtmpclient.activities.taskactivity.OneTaskActivity;
import com.example.andrey.newtmpclient.entities.Task;
import com.example.andrey.newtmpclient.managers.TasksManager;
import com.example.andrey.newtmpclient.network.Response;
import com.example.andrey.newtmpclient.storage.JsonParser;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.andrey.newtmpclient.storage.Const.TASK_NUMBER;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    JsonParser parser = new JsonParser();
    private static final String TAG = "MyFirebaseMsgService";
    TasksManager tasksManager = TasksManager.INSTANCE;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData().get("task_body"));

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        Log.d(TAG, "Long lived task is done.");
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Log.i(TAG, "sendNotification: " + messageBody);
        Response response = parser.parseFromServerUserTasks(messageBody);
        String respStr = response.getResponse();
        Task task = response.getTask();
        if(tasksManager.isTaskInList(task)){
            tasksManager.updateTask(task);
        }else {
            tasksManager.addTask(task);
        }

        System.out.println(task.getId() + " from service");
        Intent intent = new Intent(this, OneTaskActivity.class);
        intent.putExtra(TASK_NUMBER, task.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String bigText = "Адрес: " + task.getAddress()+
                        "\nСтатус: " + task.getStatus()+
                        "\nВажность: " + task.getImportance()+
                        "\nВыполнить до: " + task.getDoneTime() +
                        "\nЧто сделать: " + task.getBody();

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notify)
                .setContentTitle(respStr)
                .setContentText(task.getBody())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}