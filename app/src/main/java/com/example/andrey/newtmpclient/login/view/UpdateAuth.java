package com.example.andrey.newtmpclient.login.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.andrey.newtmpclient.network.Client;
import com.example.andrey.newtmpclient.network.Request;
import com.example.andrey.newtmpclient.service.GpsService;
import com.example.andrey.newtmpclient.storage.AuthChecker;
import com.example.andrey.newtmpclient.storage.ConverterMessages;

import java.net.ConnectException;

/**
 * Created by andrey on 13.07.2017.
 */

public class UpdateAuth extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;
    private Context context;
    private Request request;
    private Intent intent;
    private ConverterMessages converter = new ConverterMessages();
    private Client client = Client.INSTANCE;


    public UpdateAuth(Context context, Request request, Intent intent){
        this.context = context;
        this.dialog = new ProgressDialog(context);
        this.request = request;
        this.intent = intent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        converter.sendMessageToServer(request);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //стартуем сервис
        //если авторизация выполнена, выполняем нужные действия
        if(client.isAuth()) {
            context.startService(GpsService.newIntent(context));
            GpsService.setServiceAlarm(context, true);
            context.startActivity(intent);
        }
        //иначе показываем тост, что авторизация не выполнена.
        else{
            Toast.makeText(context, "Вы не авторизированы", Toast.LENGTH_SHORT).show();
        }
        AuthChecker.checkServerErrorRedirectLoginActivity(context);
        super.onPostExecute(aVoid);
        dialog.dismiss();
    }
}
