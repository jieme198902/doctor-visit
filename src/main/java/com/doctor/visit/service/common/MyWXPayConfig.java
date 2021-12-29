package com.doctor.visit.service.common;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 微信支付
 */
public class MyWXPayConfig implements WXPayConfig {
    /**
     * 小程序appid
     * @return
     */
    private String appid;
    /**
     * 商户平台-账户中心-API安全中的密钥
     */
    private String apiKey;
    /**
     * 商户号
     * @return
     */
    private String mchId;

    private byte[] certData;

    /**
     * 构造器
     * @param appid 小程序appid
     * @param apiKey 商户平台-账户中心-API安全中的密钥
     * @param mchId 商户号
     */
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

    /**
     * 小程序appid
     * @return
     */
    @Override
    public String getAppID() {
        return this.appid;
    }

    /**
     * 商户号
     * @return
     */
    @Override
    public String getMchID() {
        return this.mchId;
    }

    /**
     * 商户平台-账户中心-API安全中的密钥
     */
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
