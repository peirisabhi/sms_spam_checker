package com.abhi.sms_spam_checker.config;

public class Config {

    public static final String IP = "192.168.1.2";

    public static final String VIRUSTOTAL_API_KEY = "1e27233aec739ae32df8d43e58693c370dfdb4b36641ad34a81574000c18e546";

    public static final String VIRUSTOTAL_CHECK_URL = "http://"+IP+"/sms_spam_checker/virusTotalRequest.php";
    public static final String VIRUSTOTAL_GET_DATA_URL = "http://"+IP+"/sms_spam_checker/virusTotalGetResponse.php";
}
