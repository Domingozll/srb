package com.wwj.srb.oss.service.impl;

import com.wwj.common.result.exception.BusinessException;
import com.wwj.srb.oss.properties.FileSystemUtil;
import com.wwj.srb.oss.properties.LocalProperties;
import com.wwj.srb.oss.service.FileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Author: zhanglin
 * @CreateTime: 2023-08-05  15:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = FileSystemUtil.PREFIX, name = "type", havingValue = "LOCAL")
public class LocalFileStrategyImpl implements FileStrategy {

    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        if (StringUtils.isBlank(module)) {
            throw new BusinessException("LocalFileStrategyImpl upload error.module is blank.");
        }

        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("LocalFileStrategyImpl upload error.fileName is blank.");
        }

        String filePath = LocalProperties.LOCAL_PATH + FileSystemUtil.getFilePath(module, fileName);
        File file = new File(filePath);
        try (InputStream in = inputStream) {
            if (!file.exists()) {
                //文件不存在则创建文件，先创建目录
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            log.error("LocalFileStrategyImpl upload Exception.", e);
            throw new BusinessException("LocalFileStrategyImpl upload Exception." + e.getMessage());
        }

        return filePath;
    }

    @Override
    public void removeFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new BusinessException("LocalFileStrategyImpl removeFile error.filePath is blank.");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            log.warn("LocalFileStrategyImpl removeFile warnning.file not existis.filePath:{}", filePath);
            return;
        }

        file.delete();
    }
}
