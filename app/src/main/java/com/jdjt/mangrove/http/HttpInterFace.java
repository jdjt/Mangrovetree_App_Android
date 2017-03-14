package com.jdjt.mangrove.http;


import com.jdjt.mangrove.common.Constant;
import com.jdjt.mangrovetreelibray.ioc.annotation.InNet;
import com.jdjt.mangrovetreelibray.ioc.annotation.InParam;
import com.jdjt.mangrovetreelibray.ioc.annotation.InPost;

/**
 * 网络接口范例 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * -----------------------------------------------
 */
@InNet(Constant.HttpUrl.class)
public interface HttpInterFace {
    /**
     * 登陆接口
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.LOGIN)
    public void login(@InParam String json);

    /**
     * 获取会员信息
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.GETUSERINFO)
    public void getUserInfo(@InParam String json);

    /**
     * 用户登出
     */
    @InPost(Constant.HttpUrl.LOGOUT)
    public void logout();

    /**
     * 用户注册
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.REGISTER)
    public void register(@InParam String json);

    /**
     * 用户账号重复验证
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.CHECKACCOUNT)
    public void checkAccount(@InParam String json);

    /**
     * 验证码验证
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.CHECKCAPTCHA)
    public void checkCaptcha(@InParam String json);

    /**
     * 修改账户信息
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.MODIFYMEMBER)
    public void modifyMember(@InParam String json);

    /**
     * 找回密码
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.RESETPASSWORD)
    public void resetPassword(@InParam String json);

    /**
     * 获取验证码
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.GETCODE)
    public void getCode(@InParam String json);

    /**
     * 修改密码
     *
     * @param json
     */
    @InPost(Constant.HttpUrl.MODIFYPASSWORD)
    public void modifyPassword(@InParam String json);

    /**
     * 重新绑定账号
     * @param json
     */
    @InPost(Constant.HttpUrl.REBINDINGPHONE)
    public void reBindingPhone(@InParam String json);

}
