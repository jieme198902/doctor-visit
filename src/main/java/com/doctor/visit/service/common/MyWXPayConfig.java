package com.doctor.visit.service.common;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 微信支付
 */
public class MyWXPayConfig implements WXPayConfig {

    private String appid;
    private String apiKey;

    private String mchId;

    private byte[] certData;

    public MyWXPayConfig(String appid,  String apiKey ,String mchId) {
        this.appid = appid;
        this.mchId = mchId;
        this.apiKey = apiKey;
        try{
            FileInputStream  inputStream = new FileInputStream("/usr/local/webapps/cert/wx/pay_cert.p12");
            this.certData = IOUtils.toByteArray(inputStream);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getAppID() {
        return this.appid;
    }

    @Override
    public String getMchID() {
        return this.mchId;
    }

    @Override
    public String getKey() {
        return this.apiKey;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
