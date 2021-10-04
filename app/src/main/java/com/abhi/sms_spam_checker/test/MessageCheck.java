package com.abhi.sms_spam_checker.test;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageCheck {

    public static void main(String[] args) {
        containsURL("http://www.test1.com");
    }


    public static boolean containsURL(String msg)
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
}
