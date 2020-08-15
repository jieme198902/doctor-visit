package com.doctor.visit.service;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFile;
import com.doctor.visit.repository.BusFileMapper;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


/**
 * 文件上传
 */
@Service
@EnableAsync
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${custom.rootPath}")
    private String rootPath;

    private final BusFileMapper busFileMapper;

    public UploadService(BusFileMapper busFileMapper) {
        this.busFileMapper = busFileMapper;
    }

    /**
     * 上传文件
     *
     * @param busFile
     * @param request
     * @return
     */
    @Async
    public void uploadFiles(BusFile busFile, HttpServletRequest request) {
        if (request instanceof StandardMultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiValueMap = multipartHttpServletRequest.getMultiFileMap();
            List<MultipartFile> fileList = Lists.newArrayList();
            multiValueMap.forEach((s, files) -> fileList.addAll(files));

            if (fileList.isEmpty()) {
                return;
            }
            String folder = busFile.getBus();

            File dirRootPath = new File((rootPath.endsWith(Constants.SLASH) ? rootPath : (rootPath + Constants.SLASH)) + folder);
            if (!dirRootPath.exists()) {
                dirRootPath.mkdirs();
            }
            if(busFile.isDelBefore()){
                BusFile delRecord = new BusFile();
                delRecord.setBusId(busFile.getBusId());
                delRecord.setBus(busFile.getBus());
                delRecord.setFileType(busFile.getFileType());
                busFileMapper.delete(delRecord);
            }
            for (MultipartFile multipartFile : fileList) {
                // 获取文件的原名称
                String fileName = multipartFile.getOriginalFilename().replace(".temp", "");
                //文件后缀
                String suffix = fileName.substring(fileName.lastIndexOf(Constants.POINT));
                String newFileName = Utils.generateOfDate() + Constants.UNDERLINE + Utils.generateOf8() + suffix;
                File destFile = new File(dirRootPath, newFileName);
                try {
//                    multipartFile.transferTo(destFile);
//                    FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),destFile);
                    Files.copy(multipartFile.getInputStream(),destFile.toPath());

                    try {
                        Runtime.getRuntime().exec("chmod -R 755 " + destFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String abPath = folder + Constants.SLASH + newFileName;
                busFile.setId(IDKeyUtil.generateId());
                busFile.setFilePath(abPath);
                busFileMapper.insertSelective(busFile);
            }
        }
    }
}
