package com.doctor.visit.web.rest.util;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.BusArticle;
import com.doctor.visit.web.rest.errors.UnAuthorizedException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class Utils {

    private Utils() {
    }

    /**
     * 生成 时间 格式化 不带间隔的id
     *
     * @return yyyyMMddHHmmssSSS
     */
    public static String generateOfDate() {
        Date now = new Date();
        SimpleDateFormat timeUidSdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return timeUidSdf.format(now);
    }

    /**
     * @return uuid
     */
    public static String generate() {
        return UUID.randomUUID().toString().replaceAll(Constants.MIDDLE_LINE, Constants.SPACE);
    }

    /**
     * @return 8 位 UUID
     */
    public static String generateOf8() {
        return generate().substring(0, 8);
    }

    /**
     * 文章生成html
     *
     * @param busArticle
     * @return 文件相对地址
     */
    public static String writeHtml(BusArticle busArticle, String rootPath) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        if (rootPath.endsWith("/")) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        String savePath = rootPath + File.separator + "html" + File.separator + ymd + File.separator;
        String saveUrl = "html/" + ymd + "/";
        String fileName = busArticle.getId() + ".html";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String html =
            "<!doctype html>" +
                "<html>" +
                "<head>" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />" +
                "<meta content='telephone=no' name='format-detection'> " +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'>" +
                "<title>" + busArticle.getTitle() + "</title>" +
                "</head>" +
                "<body> " +
                "<div class='warp' style='text-align:center;bold:1px;font-size:20px;'>" +
                busArticle.getForwardFrom() +
                "</div> " +
                "<div class='warp' id='nr'>" + busArticle.getContent() + "</div>" +
                //替换内容ip
                "<script> " +
                "function getUrl() {" +
                "    var href = location.href;" +
                "    var http = href.indexOf('http://') > 0 ? 'http://' : 'https://';" +
                "    var port = location.port;" +
                "    if (port === '80') {" +
                "        return http + location.hostname" +
                "    } else {" +
                "        return http + location.host;" +
                "    }" +
                "}" +
                "function setImgSrc(){" +
                "    for (var i = 0; i < document.getElementsByTagName('img').length; i++) {" +
                "        var _this = document.getElementsByTagName('img')[0];" +
                "        var src = _this.data('src');" +
                "        _this.setAttribute('src',getUrl() + src.substring(src.lastIndexOf(':') + 5))" +
                "    }" +
                "}" +
                "setImgSrc();" +
                "</script>" +
                "</body>" +
                "</html>";
        File file = new File(savePath + fileName);
        BufferedWriter writer = null;
        try {
            if (!file.exists() || !file.isDirectory()) {
                file.createNewFile();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
            writer.write(html);
            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveUrl + fileName;
    }

    /**
     * 获取token
     *
     * @param userId
     * @return
     */
    public static String createToken(Long userId) throws Exception {
        String tokenOrigin = userId + Constants.UNDERLINE + System.currentTimeMillis();
        return Des3Util.encode(tokenOrigin, Constants.des3Key);
    }

    /**
     * 通过token获取userId
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Long getUserId(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            throw new UnAuthorizedException("token is null . ");
        }
        String idTime = Des3Util.decode(token, Constants.des3Key);
        if (StringUtils.isBlank(idTime)) {
            throw new UnAuthorizedException("token is error . ");
        }
        String idStr = idTime.split(Constants.UNDERLINE)[0];
        if (StringUtils.isBlank(idStr)) {
            throw new UnAuthorizedException("token is error . ");
        }
        return Long.parseLong(idStr);
    }

    /**
     * 通过token获取userId，不抛异常
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Long getUserIdWithException(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String idTime = Des3Util.decode(token, Constants.des3Key);
        if (StringUtils.isBlank(idTime)) {
            return null;
        }
        String idStr = idTime.split(Constants.UNDERLINE)[0];
        if (StringUtils.isBlank(idStr)) {
            return null;
        }
        return Long.parseLong(idStr);
    }


    /**
     * 从header中获取token
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Long getUserId(HttpServletRequest request) throws Exception {
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new UnAuthorizedException("token is null . ");
        }
        return getUserId(token);
    }

    /**
     * 获取用户id信息，不抛异常
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Long getUserIdWithException(HttpServletRequest request) throws Exception {
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return getUserIdWithException(token);
    }

    /**
     * 判断id是否为空
     *
     * @param id
     * @return
     */
    public static boolean isBlank(Long id) {
        if (null == id) {
            return true;
        } else {
            if (id <= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

}
