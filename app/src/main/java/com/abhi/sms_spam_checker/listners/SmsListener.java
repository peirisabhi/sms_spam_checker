package com.abhi.sms_spam_checker.listners;

//import android.app.NotificationChannel;
//import android.app.NotificationManager;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.abhi.sms_spam_checker.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsListener extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SPMS SPAM";


    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default";

    final int spam_threshhold = 0;
    HashMap<String, Integer> SpamWord = new HashMap<String, Integer>();



    public SmsListener() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        this.abortBroadcast();
        if (intent.getAction() == SMS_RECEIVED) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                String msg, sender, fmsg;

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                    msg=messages[i].getMessageBody().toString()+"\n";
                    sender=messages[i].getOriginatingAddress().toString();
                    fmsg= "\nSMS from "+sender+" : "+msg;

                    Log.e("TAG", fmsg );
                    //Load Spam keywords in a Hashmap

                    SpamWord=loadSpamWords(context);

                    Pattern pattern = Pattern.compile("[\\w.]+@[\\w.]+");
                    Matcher matcher = pattern.matcher(msg);

                    String foundEmail = "";

                    while(matcher.find()){
                        foundEmail = matcher.group();
                    }

                    this.checkEmail(context);
                    //Check if the sms is spam or not
                    if(!isSpam(msg, sender)){
                        this.clearAbortBroadcast();
                    }
                    else{
                        Log.e("TAG", "spam found");

                        try{
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "c")
                                    .setSmallIcon(R.drawable.ic_stat_name)
                                    .setContentTitle("SPAM ALERT")
                                    .setContentText("You have a spam sms from " + sender)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// notificationId is a unique int for each notification that you must define
                            notificationManager.notify(2, builder.build());


                        }catch (Exception  e){
                            Log.e("TAG", "onReceive error: " + e.getLocalizedMessage() );
                        }


                    }//end if else
                }
            }
        }
    }


    public HashMap<String, Integer> loadSpamWords(Context context)
    {
        HashMap<String, Integer> SpamWord = new HashMap<String, Integer>();
        String line, word="";
        int weight=0;
        try
        {
            BufferedReader reader;
            final InputStream file = context.getAssets().open("spam_keywords.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            while((line = reader.readLine()) != null){
                StringTokenizer st = new StringTokenizer(line);
                while(st.hasMoreElements())
                {
                    word=st.nextElement().toString();
                    weight= Integer.parseInt(st.nextElement().toString());
                }
                SpamWord.put(word, weight);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
//        Log.e("TAG", SpamWord.toString());
        return SpamWord;
    }



    public boolean isSpam(String msg, String sender)
    {
        int weight=0;
        boolean flag=false;
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

//        //sender="abc";
//        if(!sender.matches("\\d{11}"))
//            return true;

        //work remaining:
        //1. work on URL shortners and domains

        //if(containsURL(msg))
        //	return true;

        StringTokenizer st= new StringTokenizer(msg);
        String word="";

        while(st.hasMoreElements())
        {
            word = st.nextElement().toString();

            if(word.matches("(.*%)|(.*!+)") || word.contains("$"))
                weight=weight+4;

            if(word.matches(regex))
                weight=weight+8;

            word=word.toLowerCase();

            if(SpamWord.containsKey(word)){
                weight=weight+SpamWord.get(word);
            }
        }

        if(weight<=spam_threshhold)
            flag=false;
        else
            flag=true;

        return flag;
    }

    public boolean containsURL(String msg)
    {
        boolean flag=false;
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        StringTokenizer st= new StringTokenizer(msg);
        String word="";

        while(st.hasMoreElements())
        {
            word = st.nextElement().toString();
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(word);
            flag=matcher.matches();
            if(flag)
                break;
        }
        return flag;

    }

    public void checkEmail(Context context){
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url ="https://api.emailverifyapi.com/api/a/v1?key=yourapikey&email=test@tester.com";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.e("TAG", "onResponse: " + response );
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage() );
//            }
//        });
//
//        queue.add(stringRequest);
//
    }
}