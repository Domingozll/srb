package com.wwj.srb.oss.controller.api;

import com.wwj.common.result.R;
import com.wwj.common.result.ResponseEnum;
import com.wwj.common.result.exception.BusinessException;
import com.wwj.srb.oss.service.FileStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(tags = "文件管理")
@RestController
//@CrossOrigin
@RequestMapping("/api/oss/file")
@RequiredArgsConstructor
public class FileController {

    private final FileStrategy fileStrategy;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "模块", required = true)
            @RequestParam("module") String module) {
        try {
            // 获取文件流
            InputStream inputStream = file.getInputStream();
            // 获取文件扩展名
            String originalFilename = file.getOriginalFilename();
            String url = fileStrategy.upload(inputStream, module, originalFilename);
            return R.ok().message("文件上传成功").data("url", url);
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除oss文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件", required = true)
            @RequestParam("url") String url) {
        fileStrategy.removeFile(url);
        return R.ok().message("文件删除成功");
    }
}
