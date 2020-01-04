package com.qingying0.aqachat.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.IOException;

public class QiNiuUpload {
    public static String ACCESS_KEY = "6HE9Muc0ex3A6yTFZrGdC_goONfDCui62iqoiKZ9";
    public static String SECRET_KEY = "1dgYoH1aQr1VIPWej6pUgQzsNu1Kl5Tr-pHNppbS";

    //要上传的空间
    private static String bucketname = "zyi";

    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    private static Configuration cfg = new Configuration(Zone.zone1());
    private static UploadManager uploadManager = new UploadManager(cfg);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public  static String getUpToken(){
        return auth.uploadToken(bucketname);
    }

    public static String upload(byte[] file,String key) throws IOException {
        try {
            //调用put方法上传
            Response response = uploadManager.put(file, key, getUpToken());
            //打印返回的信息
            DefaultPutRet set = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return "http://q3jbezsht.bkt.clouddn.com/" + set.key;
        } catch (QiniuException e) {
            System.out.println("exception = " + e);
            e.printStackTrace();
            Response r = e.response;
            return null;
        }
    }
}
