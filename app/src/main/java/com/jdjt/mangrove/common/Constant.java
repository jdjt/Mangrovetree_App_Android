package com.jdjt.mangrove.common;


import com.jdjt.mangrove.BuildConfig;
import com.jdjt.mangrovetreelibray.ioc.annotation.NotProguard;

import okhttp3.MediaType;

/*
 * Author: wmy
 * Description:
 * History:
 */
public class Constant {

    public static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    public static final String fir_api_token = "9eb1714c859b86b023fe70b4ca592908";
    public static final String ALI_APPKEY = "23767275";
    public static final String ALI_APPSECRET = "d2e085d699afb44e2ef23f54d9c363db";
    @NotProguard
    public static class HttpUrl {
        /**
         * 系统配置的数据库名
         **/

        public static final String DATA_USER = "User";
        //        public static final String BASE = "http://mws.mymhotel.com/";// 服务器 外网
        public static final String SYW_PROJECTMANAGER = BuildConfig.API_SERVER_URL;// "http://syw.mymhotel.com/";

        //        public static final String BASE = "http://rc-ws.mymhotel.com/";
        public static final String BASE = "http://rc-ws.mymhotel.com/";
        public static final String LOGIN = BASE + "uum/mem/sso/login.json";
        //获取会员信息
        public static final String GETUSERINFO = BASE + "uum/mem/account/member_info.json";
        // 用户退出
        public static final String LOGOUT = BASE + "uum/mem/sso/logout.json";
        //用户注册
        public static final String REGISTER = BASE + "uum/mem/account/register.json";
        //用户账号重复验证
        public static final String CHECKACCOUNT = BASE + "uum/mem/account/check_account.json";
        //获取用户验证码
        public static final String GETCODE = BASE + "uum/common/captcha/gain_captcha.json";
        // 验证码验证
        public static final String CHECKCAPTCHA = BASE + "uum/common/captcha/check_captcha.json";
        //修改会员信息
        public static final String MODIFYMEMBER = BASE + "uum/mem/account/modify_member.json";
        // 找回密码
        public static final String RESETPASSWORD = BASE + "uum/mem/account/reset_password.json";
        //修改用户密码
        public static final String MODIFYPASSWORD = BASE + "uum/mem/account/modify_password.json";

        /**
         * 重新绑定
         */
        public static final String REBINDINGPHONE = BASE + "uum/mem/account/binding.json";

        /**
         * 业态详情
         */
        public static final String GET_ACTIVITYDETAIL = SYW_PROJECTMANAGER + "syw_projectmanager/activitydetail/getActivitydetail";

        /**
         * APP更新接口
         */
        public static final String UPDATESOFTADDRESS = BASE + "/common/versions";

        /**
         * 获取app更新 第三方平台fir
         */
        public static final String GET_FIRUPDATE = "http://api.fir.im/apps/latest/"+BuildConfig.APPLICATION_ID;


        public static final int LOGIN_KEY = 0;
        public static final int LOGOUT_KEY = 1;
        public static final int REGISTER_KEY = 2;
        public static final int GETCODE_KEY = 4;
        public static final int CHECKACCOUNT_KEY = 3;
        public static final int CHECKCAPTCHA_KEY = 5;
        public static final int MODIFYMEMBER_KEY = 6;
        public static final int RESETPASSWORD_KEY = 7;
        public static final int MODIFYPASSWORD_KEY = 8;
        public static final int GETUSERINFO_KEY = 11;
        public static final int REBINDINGPHONE_KEY = 12;
        public static final int GETACTIVITYDETAIL_KEY = 13;
        public static final int UPDATESOFTADDRESS_KEY = 14;
        public static final int GETFIRUPDATE_KEY = 15;

    }
}
