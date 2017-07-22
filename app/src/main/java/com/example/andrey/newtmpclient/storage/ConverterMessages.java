package com.example.andrey.newtmpclient.storage;

import android.util.Log;

import com.example.andrey.newtmpclient.managers.TokenManager;
import com.example.andrey.newtmpclient.network.CheckNetwork;
import com.example.andrey.newtmpclient.network.HttpSender;
import com.example.andrey.newtmpclient.network.Request;

public class ConverterMessages {
    private JsonParser parser = new JsonParser();
    private HttpSender sender = new HttpSender();
    private CheckNetwork checkNetwork = CheckNetwork.instance;
    private DataMaker dataMaker = new DataMaker();
    private boolean isAuthSuccess;
    private TokenManager tokenManager = TokenManager.instance;

    public boolean isAuthSuccess() {
        return isAuthSuccess;
    }

    public void setAuthSuccess(boolean authSuccess) {
        isAuthSuccess = authSuccess;
    }

    //отправляем запрос с реквестом и переменной, определяющей, к какой сети будет запрос, внешней или внутренней
    public void sendMessageToServer(Request request) {
        if(tokenManager.getToken()!=null) {
            request.setToken(tokenManager.getToken());
        }
        Log.i("Converter", "sendMessage: "  + " " + parser.requestToServer(request));
        //вызываем метод отправки запроса, к урлу нужно прибавить часть сервлета, к которому будет запрос

        String response = sender.post(sender.trueUrl(checkNetwork.isNetworkInsideOrOutside()), parser.requestToServer(request));
        Log.i("Converter", "message from server: "+response);
        //вызываем поток обработки запроса
//        new DataWorker(response).start();
        if(response!=null){
            dataMaker.workWithData(response);
        }
    }

}
