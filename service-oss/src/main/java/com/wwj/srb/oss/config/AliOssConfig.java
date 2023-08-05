package com.wwj.srb.oss.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wwj.srb.oss.properties.FileSystemUtil;
import com.wwj.srb.oss.properties.OssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhanglin
 * @CreateTime: 2023-08-05  16:15
 */
@EnableConfigurationProperties(FileSystemUtil.class)
@Configuration
@ConditionalOnProperty(prefix = FileSystemUtil.PREFIX, name = "type", havingValue = "ALI")
@RequiredArgsConstructor
public class AliOssConfig {

    private final OssProperties ossProperties;

    @Bean
    public OSS getOssClient() {
        OSS ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(), ossProperties.getKeyId(), ossProperties.getKeySecret());
        return ossClient;
    }
}
