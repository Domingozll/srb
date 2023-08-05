package com.wwj.srb.oss.properties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

/**
 * @author zhanglin
 */
@Slf4j
@RequiredArgsConstructor
public class FileSystemUtil {

    public static final String PREFIX = "fs";

    public static String getFilePath(String module, String fileName) {
        return getDirPath(module) + fileName;
    }

    public static String getDirPath(String module) {
        String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
        return module + timeFolder;
    }
}
