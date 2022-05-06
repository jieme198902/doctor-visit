package com.doctor.visit.web.rest.util;

import com.doctor.visit.config.Constants;
import com.doctor.visit.domain.SysMenu;
import com.doctor.visit.domain.dto.SysMenuDto;
import com.doctor.visit.repository.SysButtonMapper;
import com.doctor.visit.web.rest.errors.UnAuthorizedException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

public final class Utils {

    private static Gson gson = new Gson();

    private Utils() {
    }

    /**
     * 排序: url?sort=edit_time,desc&sort=id,asc
     * @param pageable
     * @return
     */
    public static String orderBy (Pageable pageable){
        if(pageable.getSort().isSorted()) {
            StringBuffer stringBuffer = new StringBuffer();
            pageable.getSort().forEach(order ->
                {
                    Sort.Direction direction = order.getDirection();
                    String property = order.getProperty();
                    stringBuffer.append(property);
                    stringBuffer.append(" ");
                    stringBuffer.append(direction.name());
                    stringBuffer.append(" , ");
                }
            );
            String orderBy = stringBuffer.toString();
            if (orderBy.endsWith(", ")) {
                orderBy = orderBy.substring(0, orderBy.length() - 2);
            }
            return orderBy;
        }else{
            return null;
        }
    }

    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
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

    public static class BusHtml {
        public Long busId;
        public String bus;
        public String title;
        public String forwardFrom;
        public String content;

        public BusHtml() {
        }

        /**
         * @param busId
         * @param bus
         * @param title
         * @param forwardFrom
         * @param content
         */
        public BusHtml(Long busId, String bus, String title, String forwardFrom, String content) {
            this.busId = busId;
            this.bus = bus;
            this.title = title;
            this.forwardFrom = forwardFrom;
            this.content = content;
        }
    }

    /**
     * @param s
     * @param t
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> T fromJson(S s, Type t) {
        return gson.fromJson(gson.toJson(s), t);
    }


    public static String toJson(Object obj) {
        if (null == obj) {
            return "null";
        }
        return gson.toJson(obj);
    }


    /**
     * @param s
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String s, Type t) {
        return gson.fromJson(s, t);
    }

    /**
     * 横向递归遍历数据并填充
     * <p>
     * 把列表转换为树结构
     *
     * @param originalList 原始list数据
     * @return 组装后的集合
     * <p>
     * 博山区的父级id
     * a2057d70-95e5-11ea-a573-0050568e7d19
     */
    public List<SysMenuDto> getTree(List<SysMenuDto> originalList) {
        // 获取根节点，即找出父节点为空的对象
        List<SysMenuDto> topList = Lists.newArrayList();
        for (int i = 0; i < originalList.size(); i++) {
            SysMenuDto t = originalList.get(i);
            Long parentId = t.getPid();
            //parentId是null的是顶级菜单
            if (null == parentId) {
                topList.add(t);
            }
        }
        // 将根节点从原始list移除，减少下次处理数据
        originalList.removeAll(topList);

        // 递归封装树
        fillTree(topList, originalList);

        return topList;
    }

    /**
     * 封装树
     *
     * @param parentList   要封装为树的父对象集合
     * @param originalList 原始list数据
     */
    private void fillTree(List<SysMenuDto> parentList, List<SysMenuDto> originalList) {
        for (int i = 0; i < parentList.size(); i++) {
            List<SysMenuDto> children = fillChildren(parentList.get(i), originalList);

            if (children.isEmpty()) {
                continue;
            }

            originalList.removeAll(children);
            fillTree(children, originalList);
        }
    }

    /**
     * 将菜单集合转换成树状结构的数据
     *
     * @param trees
     * @param sysButtonMapper
     * @return
     */
    public static List<SysMenuDto> menuListToTree(List<SysMenu> trees, SysButtonMapper sysButtonMapper) {
        return menuListToTree(trees, sysButtonMapper, null);
    }

    /**
     * 将菜单集合转换成树状结构的数据
     *
     * @param trees
     * @param sysButtonMapper
     * @param roleId
     * @return
     */
    public static List<SysMenuDto> menuListToTree(List<SysMenu> trees, SysButtonMapper sysButtonMapper, String roleId) {
        List<SysMenuDto> copyMenus = Utils.fromJson(trees, new TypeToken<List<SysMenuDto>>() {
        }.getType());
        List<SysMenuDto> rootTrees = Lists.newArrayList();
        for (SysMenuDto tree : copyMenus) {
            if (null == tree.getPid() || 0 == tree.getPid()) {
                rootTrees.add(tree);
            }
            if (StringUtils.isNotBlank(roleId)) {
                tree.setButtons(sysButtonMapper.selectByMenuIdAndRoleId(tree.getId(), roleId));
            } else {
                tree.setButtons(sysButtonMapper.selectByMenuId(tree.getId()));
            }
            tree.setButton(tree.getIsDel());
            tree.setIsDel(null);
            for (SysMenuDto t : copyMenus) {
                if (null != t.getPid() && t.getPid().equals(tree.getId())) {
                    if (tree.getChildren() == null) {
                        List<SysMenuDto> children = Lists.newArrayList();
                        children.add(t);
                        tree.setChildren(children);
                    } else {
                        tree.getChildren().add(t);
                    }
                    tree.getChildren().sort((o1, o2) -> {
                        if (null != o1.getSortBy() && null != o2.getSortBy()) {
                            return o1.getSortBy() - o2.getSortBy();
                        }
                        return 0;
                    });
                }
            }
        }
        rootTrees.sort((o1, o2) -> {
            if (null != o1.getSortBy() && null != o2.getSortBy()) {
                return o1.getSortBy() - o2.getSortBy();
            }
            return 0;
        });
        return rootTrees;
    }

    /**
     * 封装子对象
     *
     * @param parent       父对象
     * @param originalList 待处理对象集合
     */
    private List<SysMenuDto> fillChildren(SysMenuDto parent, List<SysMenuDto> originalList) {
        List<SysMenuDto> childList = new ArrayList<>();
        Long parentId = parent.getId();
        for (int i = 0; i < originalList.size(); i++) {
            SysMenuDto t = originalList.get(i);
            Long childParentId = t.getPid();
            if (parentId == childParentId) {
                childList.add(t);
            }
        }
        if (!childList.isEmpty()) {
            parent.setChildren(childList);
        }
        return childList;
    }

    /**
     * 文章生成html
     *
     * @param busHtml
     * @return 文件相对地址
     */
    public static String writeHtml(BusHtml busHtml, String rootPath) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        if (rootPath.endsWith("/")) {
            rootPath = rootPath.substring(0, rootPath.length() - 1);
        }
        String savePath = rootPath + File.separator + "html" + File.separator + busHtml.bus + File.separator + ymd + File.separator;
        String saveUrl = "html/" + File.separator + busHtml.bus + File.separator + ymd + "/";
        String fileName = busHtml.busId + ".html";
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
                "<title>" + busHtml.title + "</title>" +
                "</head>" +
                "<body> " +
                "<div class='warp' style='text-align:center;bold:1px;font-size:20px;'>" +
                busHtml.title +
                "</div> " +
                "<div class='warp' style='text-align:right;bold:1px;font-size:14px;'>" +
                busHtml.forwardFrom +
                "</div> " +
                "<div class='warp' id='nr'>" + busHtml.content + "</div>" +
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
            //// true to append   false to overwrite.
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "utf-8"));
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
        String tokenOrigin = System.currentTimeMillis() + Constants.UNDERLINE + userId + Constants.UNDERLINE + generate();
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
        String idStr = idTime.split(Constants.UNDERLINE)[1];
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
    public static Long getUserIdWithoutException(String token) throws Exception {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        String idTime = Des3Util.decode(token, Constants.des3Key);
        if (StringUtils.isBlank(idTime)) {
            return null;
        }
        String idStr = idTime.split(Constants.UNDERLINE)[1];
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

    public static String getRandomCode(boolean num, int size) {

        String ZiMu = num ? "1234567890" : "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGJKLZXCVBNM1234567890";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(ZiMu.length());
            char c = ZiMu.charAt(index);
            result += c;
        }
        return result;
    }


    public static String getGroupNoName(Integer groupNo) {
        switch (groupNo) {
            case 1:
                return "一组";
            case 2:
                return "二组";
            case 3:
                return "三组";
            case 4:
                return "四组";
            case 5:
                return "五组";
            case 9:
                return "科外泵";
        }
        return "未知";
    }

    public static Integer getGroupNoValue(String groupName) {
        switch (groupName) {
            case "一组":
                return 1;
            case "二组":
                return 2;
            case "三组":
                return 3;
            case "四组":
                return 4;
            case "五组":
                return 5;
            case "科外泵":
                return 9;
        }
        return 0;
    }

    public static String orderNo() {
        return generateOfDate() + getRandomCode(true, 4);
    }

    /**
     * 获取用户id信息，不抛异常
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Long getUserIdWithoutException(HttpServletRequest request) throws Exception {
        String token = request.getHeader(Constants.TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return getUserIdWithoutException(token);
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
