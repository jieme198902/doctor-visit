package com.doctor.visit.web.rest.vm.sys;

import com.doctor.visit.config.Constants;
import com.doctor.visit.config.domain.code.LoginCodeEnum;
import com.doctor.visit.config.domain.code.LoginProperties;
import com.doctor.visit.config.domain.code.SecurityProperties;
import com.doctor.visit.domain.*;
import com.doctor.visit.web.rest.util.ComResponse;
import com.doctor.visit.web.rest.util.Utils;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 权鉴接口
 */
@RestController
@Api("权鉴接口")
@RequestMapping(Constants.API_BASE_SYS)
public class SysAuthenticateResource {
    private final SecurityProperties properties;
    @Resource
    private LoginProperties loginProperties;

    public SysAuthenticateResource(SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRole.class)
    })
    @PostMapping("checkCode")
    @ApiOperation(value = "验证验证码")
    public Object authenticate(String uuid,String codeF) {
        // 查询验证码
        String code = Constants.codeMap.get(uuid);
        // 清除验证码
        Constants.codeMap.remove(uuid);
        if (StringUtils.isBlank(code)) {
            return ComResponse.fail("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(codeF) || !codeF.equalsIgnoreCase(code)) {
            return ComResponse.fail("验证码错误");
        }
        return ComResponse.ok();
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(dataTypeClass = SysRole.class)
    })
    @GetMapping("code")
    @ApiOperation(value = "获取验证码")
    public Object code() {
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = properties.getCodeKey() + Utils.generate();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        Constants.codeMap.put(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);

    }

}
