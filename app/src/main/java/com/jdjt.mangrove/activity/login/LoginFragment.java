package com.jdjt.mangrove.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mymhotel.R;
import com.android.mymhotel.common.activity.newmain.MainActivity;
import com.android.mymhotel.common.base.BaseFragment;
import com.android.mymhotel.common.contanst.Constants;
import com.android.mymhotel.common.contanst.HeaderConst;
import com.android.mymhotel.common.contanst.Url;
import com.android.mymhotel.common.dto.UrlDbModel;
import com.android.mymhotel.restaurant.orderseat.adapter.RestaurantTelPopupWindow;
import com.android.pc.ioc.app.Ioc;
import com.android.pc.ioc.db.sqlite.DbUtils;
import com.android.pc.ioc.db.sqlite.Selector;
import com.android.pc.ioc.inject.InjectHttp;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectListener;
import com.android.pc.ioc.inject.InjectMethod;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.FastHttpHander;
import com.android.pc.ioc.internet.InternetConfig;
import com.android.pc.ioc.internet.ResponseEntity;
import com.android.pc.ioc.util.MapVo;
import com.android.pc.ioc.verification.Rule;
import com.android.pc.ioc.verification.Rules;
import com.android.pc.ioc.verification.Validator;
import com.android.pc.ioc.verification.Validator.ValidationListener;
import com.android.pc.ioc.verification.annotation.Password;
import com.android.pc.ioc.verification.annotation.TelphoneOrEmail;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.android.pc.util.Handler_Json;
import com.android.pc.util.Handler_Network;
import com.android.pc.util.Handler_SharedPreferences;
import com.android.pc.util.Handler_String;
import com.android.pc.util.Handler_TextStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huyanan on 2017/3/9.
 */

public class LoginFragment extends FragmentActivity {
    @TelphoneOrEmail(empty = false, message = "请输入正确的手机号或者邮箱", order = 1)
    @InjectView(value = R.id.login_account)
    EditText login_account;//账号

    @Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 2)
    @InjectView(value = R.id.login_password)
    EditText login_password;//密码
    @InjectView(value = R.id.login_holiday_book_btn)
    Button login_holiday_book_btn;//酒店度假卡登录
    @InjectView(value = R.id.login_server_button)
    Button login_server_button;//切换服务器！！！！！！！！！

    RestaurantTelPopupWindow resPop;
    DbUtils db;
    String tel45 = "(测试版本)";


    //验证
    Validator validator;

    Map<String, String> loginMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login, container, false);
        Handler_Inject.injectFragment(this, rootView);
        return rootView;
    }

    /**
     * 初始化
     *
     * @return void
     * @function init
     * @description 初始化
     */
    @InjectInit
    public void init() {

        Ioc.getIoc().getLogger().i("loginfragment requestCode : " + this.getTargetRequestCode());
        db = Ioc.getIoc().getDb("/sdcard/MTM/", Constants.APP_DATABASE);
        String account = Handler_SharedPreferences.getValueByName(activity, "user", "account", 0).toString();
        String password = Handler_SharedPreferences.getValueByName(activity, "user", "password", 0).toString();
        login_account.setText(account);
        login_password.setText(password);
        //酒店预订度假卡登录
        if (this.getTargetRequestCode() == 200) {
            login_holiday_book_btn.setVisibility(View.VISIBLE);
        }


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String text = sharedPreferences.getString("text", "") ;
        System.out.println("text===" + text);
        if(text.equals("")){
            login_server_button.setText("正式版本");
        }else {
            login_server_button.setText(text);
        }
    }

    public void addNewOrderButton() {

//        SharedPreferences sp = getSharedPreferences("test",
//                this.MODE_PRIVATE);
//        SharedPreferences.Editor text = sp.edit();
//        text.putString("","");

        resPop = new RestaurantTelPopupWindow(getActivity(),
                getlist(), new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UrlDbModel urlDbModel = new UrlDbModel();
                urlDbModel.setId("serviceUrl");
                switch (i) {
                    case 0:// 选择正式版本
                        urlDbModel.setUrl("mws.mymhotel.com/");
                        login_server_button.setText("");
                        SharedPreferences sp0 = getActivity().getSharedPreferences("test", getActivity().MODE_PRIVATE);
                        //拿到sp的编辑器
                        SharedPreferences.Editor ed0 = sp0.edit();
                        ed0.clear();
                        ed0.putString("text","正式版本");
                        //提交
                        ed0.commit();
                        String text1 = sp0.getString("text", "");
                        login_server_button.setText(text1);
                        System.out.println("text===******" + text1);
                        break;
                    case 1:// 选择测试版本
                        urlDbModel.setUrl("rc-ws.mymhotel.com/");

                        login_server_button.setVisibility(View.VISIBLE);
                        SharedPreferences sp = getActivity().getSharedPreferences("test", getActivity().MODE_PRIVATE);
                        //拿到sp的编辑器
                        SharedPreferences.Editor ed = sp.edit();
                        ed.clear();
                        ed.putString("text", "（测试版本）" );
                        //提交
                        ed.commit();
                        String text2 = sp.getString("text", "");
                        login_server_button.setText(text2);
                        System.out.println("text===******" + text2);

                        break;
                    case 2:// 选择测试版本
                        urlDbModel.setUrl("192.168.1.45:8181/");
                        login_server_button.setVisibility(View.VISIBLE);
                        SharedPreferences sp1 = getActivity().getSharedPreferences("test", getActivity().MODE_PRIVATE);
                        //拿到sp的编辑器
                        SharedPreferences.Editor ed1 = sp1.edit();
                        ed1.clear();
                        ed1.putString("text", "测试服务器（专线）");
                        //提交
                        ed1.commit();
                        String text3 = sp1.getString("text", "");
                        login_server_button.setText(text3);
                        System.out.println("text===******" + text3);
                        break;
                }

                Selector selector = Selector.from(UrlDbModel.class);
                selector.select(" * ");

                List<UrlDbModel> list = db.findAll(selector);

                if (list != null && list.size() > 0) {
                    Ioc.getIoc().getLogger().d("aaaaaaaaaaa--------" + list.size());
                    db.saveOrUpdate(urlDbModel);
                } else {
                    db.save(urlDbModel);
                }
                resPop.dismiss();
            }

        });


        if (!resPop.isShowing()) {
            resPop.showAtLocation(resPop.getContentView(), Gravity.BOTTOM
                    | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        }

    }



    /**
     * 组装新建订单选择菜单列表数据
     *
     * @return
     */
    public ArrayList<HashMap<String, String>> getlist() {
        String[] tels = {"选择正式版本", "选择测试版本(45)", "测试服务器(专线)"};

        Handler_SharedPreferences.WriteSharedPreferences(activity, "tel45", "tel45", tel45);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (String tel : tels) {
            HashMap<String, String> map = new HashMap<>();
            map.put("tel", tel);
            list.add(map);

        }

        return list;

    }


    /**
     * 提交，找回密码,度假卡预订登录
     *
     * @param view
     * @return void
     * @function click
     * @description 提交, 找回密码, 度假卡预订登录单击事件
     */
    @InjectMethod(@InjectListener(ids = {R.id.login_button, R.id.login_findpwd_button, R.id.login_holiday_book_btn,R.id.login_server_button}, listeners = {OnClick.class}))
    public void click(View view) {
        switch (view.getId()) {
            //提交
            case R.id.login_button:
                //验证
                validator = new Validator(this);
                validator.setValidationListener(this);
                validator.validate();

                break;

            //找回密码
            case R.id.login_findpwd_button:

                startActivity(new Intent(activity, FindPasswordActivity.class));
                break;
            //度假卡预订
            case R.id.login_holiday_book_btn:

                startActivity(new Intent(activity, HolidayCardLoginActivity.class));
                activity.finish();// 由于在酒店预订也有统一方法，故 在此 跳转到 卡登录时候要关闭此页面，否则会有冲突
                break;

            //切换服务器
            case R.id.login_server_button:
                addNewOrderButton();
                break;

            default:
                break;
        }
    }

    /**
     * 前端验证通过
     *
     * @function onValidationSucceeded
     * @description 前端验证成功
     */
    @Override
    public void onValidationSucceeded() {
        Ioc.getIoc().getLogger().i("登录前端验证成功");
        //Toast.makeText(this, "前端验证通过", Toast.LENGTH_SHORT).show();

        loginMap = new HashMap<String, String>();
        loginMap.put("account", login_account.getText().toString().trim());
        loginMap.put("password", login_password.getText().toString().trim());

        //验证手机是否联网
        if (!Handler_Network.isNetworkAvailable(activity)) {
            Toast.makeText(activity, "手机未联网", Toast.LENGTH_SHORT).show();
            return;
        }

        Ioc.getIoc().getLogger().i("调用后台登录接口：[" + Url.METHOD_LOGIN + "]");
        ((LoginAndRegisterFragmentActivity) activity).showDialog();
        //调用后台登录接口
        FastHttpHander.ajaxString(Url.METHOD_LOGIN, Handler_Json.beanToJson(loginMap), new InternetConfig(), this);

    }

    /**
     * 调用登录接口返回结果
     *
     * @param r 返回结果实体类
     * @return void
     * @function result
     * @description 调用登录接口返回结果
     */
    @InjectHttp
    public void result(ResponseEntity r) {
        Ioc.getIoc().getLogger().i("登录接口状态返回结果【" + r + "】");
        ((LoginAndRegisterFragmentActivity) activity).dismissDialog();
        //获取头部信息
        Map<String, String> map = r.getHeaders();

        switch (r.getStatus()) {
            case FastHttp.result_ok:

                //登录成功 返回OK
                String[] msgArray = null;
                if (!Handler_String.isBlank(map.get(HeaderConst.MYMHOTEL_MESSAGE)))
                    msgArray = map.get(HeaderConst.MYMHOTEL_MESSAGE).split("\\|");
                // 成功 返回OK
                if ("OK".equals(map.get(HeaderConst.MYMHOTEL_STATUS))) {
                    // Toast.makeText(context, msgArray[1], Toast.LENGTH_SHORT).show();


                    //获取返回内容 ticket
                    String jsonString = r.getContentAsString();

                    Ioc.getIoc().getLogger().i("ticket数据【" + jsonString + "】");

                    //存放ticket
                    MapVo.map = Handler_Json.JsonToCollection(jsonString);//设置ticket TODO

                     //存储用户名密码到本地
                    Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "account", loginMap.get("account"));
                    Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "password", loginMap.get("password"));
                    Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "loginType", getLoginType(loginMap.get("account")));
                    Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "ticket", MapVo.map.get("ticket"));
                    Ioc.getIoc().getLogger().i("登录成功");
//					activity.setResult(Activity.RESULT_OK, new Intent(activity, MainActivity.class));
//					new Intent(activity, MainActivity.class)
                    Intent intent = new Intent(activity,
                            MainActivity.class);
                    startActivity(intent);
                    activity.finish();
                    //				当ticket失效时，重新登陆
                } else if (msgArray[0].equals("EBA013") || msgArray[0].equals("TICKET_ISNULL") ||
                        msgArray[0].equals("TOKEN_INVALID") || msgArray[0].equals("UNLOGIN") ||
                        msgArray[0].equals("EBF001") || msgArray[0].equals("ES0003")) {
                    String jsonString = r.getContentAsString();
                    //存放ticket
                    MapVo.map = Handler_Json.JsonToCollection(jsonString);


                } else {
                    Toast.makeText(activity, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }

                break;

            //登录失败
            case FastHttp.result_net_err:
                Ioc.getIoc().getLogger().i("登录失败,请检查网络");
                Toast.makeText(activity, "登录失败,请检查网络", Toast.LENGTH_SHORT).show();
                break;
        }


    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            Handler_TextStyle handler_TextStyle = new Handler_TextStyle();
            handler_TextStyle.setString(message);
//            handler_TextStyle.setBackgroundColor(Color.RED, 0, message.length());
            ((EditText) failedView).setError(handler_TextStyle.getSpannableString());
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 登录时用的邮箱或者手机
     *
     * @param account
     * @return int
     * @function getLoginType
     * @description 获取登录类型 1:手机 2:邮箱 3:系统自动生成的账号 暂时没用
     */
    public int getLoginType(String account) {
        //1:表示手机
        if (account.matches(Rules.REGEX_TELPHONE)) {
            return 1;
        }
        //2:表示邮件
        else if (account.matches(Rules.REGEX_EMAIL)) {
            return 2;
        }
        //3:表示系统自动生成的账号:暂时没用
        else {
            return 3;
        }
    }

}
