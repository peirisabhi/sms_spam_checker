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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.abhi.sms_spam_checker.R;
import com.abhi.sms_spam_checker.config.Config;
import com.abhi.sms_spam_checker.model.UrlSpam;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsListener extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SPMS SPAM";


    public static final String NOTIFICATION_CHANNEL_ID = "10001";
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
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                String msg, sender, fmsg;

                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    msg = messages[i].getMessageBody().toString() + "\n";
                    sender = messages[i].getOriginatingAddress().toString();
                    fmsg = "\nSMS from " + sender + " : " + msg;

                    Log.e("TAG", fmsg);

                    String url = containsURL(msg);

                    if (url != null) {
                        sendVirusTotalRequest(url, context, sender);
                    }

                }
            }
        }
    }


//    public void onReceive(Context context, Intent intent) {
//        this.abortBroadcast();
//        if (intent.getAction() == SMS_RECEIVED) {
//            Bundle bundle = intent.getExtras();
//            if (bundle != null) {
//                Object[] pdus = (Object[])bundle.get("pdus");
//                final SmsMessage[] messages = new SmsMessage[pdus.length];
//                String msg, sender, fmsg;
//
//                for (int i = 0; i < pdus.length; i++) {
//                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
//
//                    msg=messages[i].getMessageBody().toString()+"\n";
//                    sender=messages[i].getOriginatingAddress().toString();
//                    fmsg= "\nSMS from "+sender+" : "+msg;
//
//                    Log.e("TAG", fmsg );
//                    //Load Spam keywords in a Hashmap
//
//                    SpamWord=loadSpamWords(context);
//
//                    Pattern pattern = Pattern.compile("[\\w.]+@[\\w.]+");
//                    Matcher matcher = pattern.matcher(msg);
//
//                    String foundEmail = "";
//
//                    while(matcher.find()){
//                        foundEmail = matcher.group();
//                    }
//
//                    this.checkEmail(context);
//                    //Check if the sms is spam or not
//                    if(!isSpam(msg, sender)){
//                        this.clearAbortBroadcast();
//                    }
//                    else{
//                        Log.e("TAG", "spam found");
//
//                        try{
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "c")
//                                    .setSmallIcon(R.drawable.ic_stat_name)
//                                    .setContentTitle("SPAM ALERT")
//                                    .setContentText("You have a spam sms from " + sender)
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
////
////                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//                            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//// notificationId is a unique int for each notification that you must define
//                            notificationManager.notify(2, builder.build());
//
//
//                        }catch (Exception  e){
//                            Log.e("TAG", "onReceive error: " + e.getLocalizedMessage() );
//                        }
//
//
//                    }//end if else
//                }
//            }
//        }
//    }


    public HashMap<String, Integer> loadSpamWords(Context context) {
        HashMap<String, Integer> SpamWord = new HashMap<String, Integer>();
        String line, word = "";
        int weight = 0;
        try {
            BufferedReader reader;
            final InputStream file = context.getAssets().open("spam_keywords.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreElements()) {
                    word = st.nextElement().toString();
                    weight = Integer.parseInt(st.nextElement().toString());
                }
                SpamWord.put(word, weight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e("TAG", SpamWord.toString());
        return SpamWord;
    }


    public boolean isSpam(String msg, String sender) {
        int weight = 0;
        boolean flag = false;
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

//        //sender="abc";
//        if(!sender.matches("\\d{11}"))
//            return true;

        //work remaining:
        //1. work on URL shortners and domains

        //if(containsURL(msg))
        //	return true;

        StringTokenizer st = new StringTokenizer(msg);
        String word = "";

        while (st.hasMoreElements()) {
            word = st.nextElement().toString();

            if (word.matches("(.*%)|(.*!+)") || word.contains("$"))
                weight = weight + 4;

            if (word.matches(regex))
                weight = weight + 8;

            word = word.toLowerCase();

            if (SpamWord.containsKey(word)) {
                weight = weight + SpamWord.get(word);
            }
        }

        if (weight <= spam_threshhold)
            flag = false;
        else
            flag = true;

        return flag;
    }

//    public boolean containsURL(String msg)
//    {
//        boolean flag=false;
//        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
//        StringTokenizer st= new StringTokenizer(msg);
//        String word="";
//
//        while(st.hasMoreElements())
//        {
//            word = st.nextElement().toString();
//            Pattern patt = Pattern.compile(regex);
//            Matcher matcher = patt.matcher(word);
//            flag=matcher.matches();
//            if(flag)
//                break;
//        }
//        return flag;
//
//    }

    public String containsURL(String msg) {
        String re_url = "((?:http|https)(?::\\/{2}[\\w]+)(?:[\\/|\\.]?)(?:[^\\s\"]*))";

        String url = null;

        Pattern url_pattern = Pattern.compile(re_url, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matches = url_pattern.matcher(msg);

        if (matches.find()) {
            String group = matches.group(1);
            System.out.print("Found URL!" + group);

            url = group;
        }

        return url;

    }


    public void sendVirusTotalRequest(String url, Context context, String sender) {

        System.out.println("sendVirusTotalRequest ---- ");

        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("url", url);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonRequest.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.VIRUSTOTAL_CHECK_URL, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response", "Response is: " + response);
                System.out.println(response);

                String requestId = "";

                try {
                    String id = response.getJSONObject("data").getString("id");

                    requestId = id.split("-")[1];

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (requestId != "") {
                    getVirusTotalRequestData(requestId, context, sender);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }


    private void getVirusTotalRequestData(String requestId, Context context, String sender) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("request_id", requestId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonRequest.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.VIRUSTOTAL_GET_DATA_URL, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Response", "Response is: " + response);
                System.out.println(response);

                try {
                    int maliciousCount = response.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("malicious");
                    int suspiciousCount = response.getJSONObject("data").getJSONObject("attributes").getJSONObject("last_analysis_stats").getInt("suspicious");
                    System.out.println("maliciousCount ---- " + maliciousCount);
                    if (maliciousCount > 0) {
                        UrlSpam urlSpam = new UrlSpam();
                        urlSpam.setFondedAt(new Date());
                        urlSpam.setMaliciousCount(maliciousCount);
                        urlSpam.setRequestId(response.getJSONObject("data").getString("id"));
                        urlSpam.setTitle(response.getJSONObject("data").getJSONObject("attributes").getString("title"));
                        urlSpam.setUrl(response.getJSONObject("data").getJSONObject("attributes").getString("url"));
                        urlSpam.setSenderName(sender);
                        urlSpam.setSenderNumber(sender);

                        showNotification("You have a spam sms from " + sender, context);

                        saveSpamDetails("ped8xuvp7PdMuf5fU1dm", urlSpam);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Response", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }


    private void saveSpamDetails(String userDocId, UrlSpam urlSpam) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(userDocId)
                .collection("url_spams")
                .add(urlSpam)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        System.out.println("url spam saved");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }


    public void checkEmail(Context context) {
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


    void showNotification(String message, Context context) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "c")
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle("Malicious URL Found")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

//
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// notificationId is a unique int for each notification that you must define
            notificationManager.notify(2, builder.build());


        } catch (Exception e) {
            Log.e("TAG", "onReceive error: " + e.getLocalizedMessage());
        }
    }

}