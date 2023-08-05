package com.wwj.srb.oss.properties;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnProperty(prefix = FileSystemUtil.PREFIX, name = "type", havingValue = "ALI")
@ConfigurationProperties(prefix = OssProperties.PREFIX)
public class OssProperties implements InitializingBean {

    public static final String PREFIX = "fs.oss";

    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;

    public static String ENDPOINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    public static String HOST;

    /**
     * 当私有成员被赋值后，此方法自动被调用，从而初始化常量
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
        HOST = "https://" + BUCKET_NAME + "." + ENDPOINT + "/";
    }

    public static String getUrl(String key) {
        return HOST + key;
    }
}
