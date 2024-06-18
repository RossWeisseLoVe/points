package com.dragon.flow.web.resource.privilege;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.dragon.flow.config.BaseContext;
import com.dragon.flow.constant.FlowConstant;
import com.dragon.flow.enm.privilege.SystemConfigEnum;
import com.dragon.flow.model.base.SystemConfig;
import com.dragon.flow.model.org.Role;
import com.dragon.flow.model.privilege.ACL;
import com.dragon.flow.model.privilege.User;
import com.dragon.flow.properties.SsoConfigProperties;
import com.dragon.flow.service.base.ISystemConfigService;
import com.dragon.flow.service.org.IRoleService;
import com.dragon.flow.service.privilege.IAclService;
import com.dragon.flow.service.privilege.IUserService;
import com.dragon.flow.web.resource.BaseResource;
import com.dragon.tools.common.ReturnCode;
import com.dragon.tools.vo.ReturnVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dragon.flow.constant.FlowConstant.LOGIN_USER;

/**
 * @program: flow
 * @description: 登录
 * @author: Bruce.Liu
 * @create: 2021-03-25 11:37
 **/
@RestController
@RequestMapping("/")
@CrossOrigin
public class LoginResource extends BaseResource {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAclService aclService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISystemConfigService systemConfigService;
    @Autowired
    private SsoConfigProperties ssoConfigProperties;

    /**
     * 用户登出
     *
     * @return
     */
    @PostMapping(value = "/logout", produces = "application/json")
    public ReturnVo logout() {
        ReturnVo vo = new ReturnVo(ReturnCode.SUCCESS, "退出成功！");
        //获取当前会话的Session,若无则返空
        SaSession saSession = StpUtil.getSession(false);
        //若当前session不为空,则从持久库中删除
        if (saSession != null) {
            saSession.logout();
        }
        StpUtil.logout();
        return vo;
    }

    /**
     * 用户登录
     *
     * @param loginUser - username 用户名
     * @param loginUser - password 密码
     * @return
     */
    @PostMapping(value = "/login", produces = "application/json")
    public ReturnVo<String> login(@RequestBody User loginUser) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.FAIL, "登录失败");
        if (StringUtils.isNotBlank(loginUser.getUsername()) && StringUtils.isNotBlank(loginUser.getPassword())) {
            ReturnVo<User> loginRetuenVo = userService.login(loginUser.getUsername(), loginUser.getPassword());
            if (loginRetuenVo.isSuccess()) {
                User user = loginRetuenVo.getData();
                returnVo = this.setSession(user, returnVo);
            } else {
                returnVo = new ReturnVo<>(ReturnCode.FAIL, loginRetuenVo.getMsg());
            }
        } else {
            returnVo.setMsg("用户名或密码为空！");
        }
        return returnVo;
    }

    @PostMapping(value = "/ssologin", produces = "application/json")
    public ReturnVo<String> ssologin(@RequestBody User loginUser) {
        ReturnVo<String> returnVo = new ReturnVo<>(ReturnCode.FAIL, "自动登录失败");
        if (StringUtils.isNotBlank(loginUser.getUserNo())) {
            String body = HttpRequest.post(ssoConfigProperties.getSsoUrl() + "?token=" + loginUser.getUserNo())
                    .header("appId", ssoConfigProperties.getSsoAppId())
                    .header("Authorization", ssoConfigProperties.getSsoAuthorization())
                    .execute().body();
            if (StringUtils.isNotBlank(body)) {
                JSONObject jsonObject = JSONObject.parseObject(body);
                String userName = jsonObject.getString("user_name");
                if (StringUtils.isNotBlank(userName)) {
                    User user = userService.getUserByUsername(userName);
                    if (user != null) {
                        returnVo = this.setSession(user, returnVo);
                    } else {
                        user = new User();
                        user.setUserNo(loginUser.getUserNo());
                        user.setUsername(loginUser.getUserNo());
                        user.setRealName(loginUser.getUserNo());
                        // 自动创建用户
                        userService.saveOrUpdate(user);
                        returnVo = this.setSession(user, returnVo);
//                        returnVo = new ReturnVo<>(ReturnCode.FAIL, "自动登录失败,未找到该用户,请确认username是否一致");
                    }
                }
            }
        } else {
            returnVo.setMsg("用户名或密码为空！");
        }
        return returnVo;
    }

    private ReturnVo<String> setSession(User user, ReturnVo<String> returnVo) {
        if (user != null) {
            StpUtil.login(user.getId());
            SaSession session = StpUtil.getSessionByLoginId(user.getId());
            session.set(LOGIN_USER, user);
            List<Role> roles = roleService.getRolesByPersonalId(user.getId());
            session.set(FlowConstant.LOGIN_ROLES, roles);
            Set<ACL> acls = aclService.getAclsByUserId(user.getId());
            session.set(FlowConstant.LOGIN_USER_ACLS, acls);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            String tokenValue = tokenInfo.getTokenValue();
            returnVo.setData(tokenValue);
            returnVo.setCode(ReturnCode.SUCCESS);
            returnVo.setMsg("登录成功");
        }
        return returnVo;
    }

    /**
     * 获取系统配置
     *
     * @return
     */
    @GetMapping(value = "/getSystemSettings", produces = "application/json")
    public ReturnVo getSystemSettings() {
        ReturnVo vo = new ReturnVo(ReturnCode.FAIL, "获取配置失败！");
        try {
            List<String> snList = Stream.of(SystemConfigEnum.values())
                    .map(SystemConfigEnum::getSn)
                    .collect(Collectors.toList());
            List<SystemConfig> systemConfigs = systemConfigService.getConfigBySns(snList);
            vo.setData(systemConfigs);
            vo.setCode(ReturnCode.SUCCESS);
            vo.setMsg("获取配置成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }
}
