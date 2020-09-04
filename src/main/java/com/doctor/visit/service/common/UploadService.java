package com.doctor.visit.service.common;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusFile;
import com.doctor.visit.repository.BusFileMapper;
import com.doctor.visit.web.rest.util.IDKeyUtil;
import com.doctor.visit.web.rest.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * 文件上传
 */
@Service
public class UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    @Value("${custom.rootPath}")
    private String rootPath;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

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
    public String uploadFiles(BusFile busFile, HttpServletRequest request) {
        //先检查下是否有该业务数据
        String startUrl = datasourceUrl.split("\\?")[0];
        String databaseName = startUrl.substring(startUrl.lastIndexOf("/") + 1);
        int count = busFileMapper.selectBusExist(databaseName, busFile.getBus());
        if (count == 0) {
            return "业务不存在";
        }
        int dataCount = busFileMapper.selectBusByIdExist(busFile.getBus(), "id", busFile.getBusId());
        if (dataCount == 0) {
            return "业务数据不存在";
        }
        String folder = busFile.getBus();
        File dirRootPath = new File((rootPath.endsWith(Constants.SLASH) ? rootPath : (rootPath + Constants.SLASH)) + folder);
        if (!dirRootPath.exists()) {
            dirRootPath.mkdirs();
        }
        //是否删除之前的图片
        if ("1".equals(busFile.getDelBefore())) {
            BusFile delRecord = new BusFile();
            delRecord.setBusId(busFile.getBusId());
            delRecord.setBus(busFile.getBus());
            delRecord.setFileType(busFile.getFileType());
            busFileMapper.delete(delRecord);
        }
        //判断是否上传文件了
        if (request instanceof StandardMultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiValueMap = multipartHttpServletRequest.getMultiFileMap();
            multiValueMap.forEach((s, files) -> {
                if (null != files && !files.isEmpty()) {
                    for (MultipartFile multipartFile : files) {
                        // 获取文件的原名称
                        String fileName = multipartFile.getOriginalFilename().replace(".temp", "");
                        //文件后缀
                        String suffix = fileName.substring(fileName.lastIndexOf(Constants.POINT));
                        String newFileName = Utils.generateOfDate() + Constants.UNDERLINE + Utils.generateOf8() + suffix;
                        File destFile = new File(dirRootPath, newFileName);
                        try {
                            multipartFile.transferTo(destFile);
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
            });
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/doctor_visit?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true";
        String startUrl = url.split("\\?")[0];
        String databaseName = startUrl.substring(startUrl.lastIndexOf("/") + 1);
        System.out.println(databaseName);
    }
}
