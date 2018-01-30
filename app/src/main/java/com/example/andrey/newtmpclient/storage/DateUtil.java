package com.example.andrey.newtmpclient.storage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.andrey.newtmpclient.storage.Const.DATE_FORMAT_FROM_SERVER;

public class DateUtil {
    private DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
    private SimpleDateFormat forServer = new SimpleDateFormat("yy-MM-dd HH:mm");
    private DateFormat fromClient = new SimpleDateFormat(DATE_FORMAT_FROM_SERVER);

    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public String dateForServer(Date date){
        return forServer.format(date);
    }

    public String currentDateForServer(){
        return forServer.format(date);
    }

    public String currentDate(){
        return dateFormat.format(date);
    }

    public String getDDFromYY(String date){
        try {
            Date date1 = forServer.parse(date);
            return fromClient.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }
}
