package com.wwj.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.wwj.srb.oss.properties.FileSystemUtil;
import com.wwj.srb.oss.properties.OssProperties;
import com.wwj.srb.oss.service.FileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @Author: zhanglin
 * @CreateTime: 2023-08-05  15:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = FileSystemUtil.PREFIX, name = "type", havingValue = "ALI")
public class AliOssFileStrategyImpl implements FileStrategy {

    private final OSS ossClient;

    @Override
    public String upload(InputStream inputStream, String module, String fileName) {

        // 判断BUCKET_NAME是否存在
        if (!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)) {
            // 若不存在，则创建
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            // 设置访问权限为读写
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }
        String key = FileSystemUtil.getFilePath(module, fileName);
        // 上传文件流
        ossClient.putObject(OssProperties.BUCKET_NAME, key, inputStream);
        // 返回文件的url地址
        return OssProperties.getUrl(key);
    }

    @Override
    public void removeFile(String url) {
        // 文件原url：https://srb-file-wwj.oss-cn-beijing.aliyuncs.com/test/2021/05/05c8c9f2ed-3f01-4a31-a38b-6ceeda2e97a5.png
        // objectName：test/2021/05/05c8c9f2ed-3f01-4a31-a38b-6ceeda2e97a5.png
        // 截掉原url前面的部分字符串
        String objectName = url.substring(OssProperties.HOST.length());
        // 删除文件
        ossClient.deleteObject(OssProperties.BUCKET_NAME, objectName);
    }
}
