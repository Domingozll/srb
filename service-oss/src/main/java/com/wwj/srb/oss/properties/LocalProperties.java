package com.wwj.srb.oss.properties;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConditionalOnProperty(prefix = FileSystemUtil.PREFIX, name = "type", havingValue = "LOCAL")
@ConfigurationProperties(prefix = LocalProperties.PREFIX)
public class LocalProperties implements InitializingBean {

    public static final String PREFIX = "fs.local";

    /**
     * 本地目录
     */
    private String localPath;

    public static String LOCAL_PATH;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOCAL_PATH = localPath;
    }


}