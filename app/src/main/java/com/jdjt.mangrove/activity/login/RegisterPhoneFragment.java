package com.jdjt.mangrove.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by huyanan on 2017/3/9.
 */

public class RegisterPhoneFragment extends BaseFragment implements ValidationListener {

	@Telphone(empty = false, message = "请输入正确的手机号", order = 1)
	@InjectView(value=R.id.register_account)
    EditText register_account;//账号

	@Password(maxLength = 18, minLength = 6, message = "请输入长度6-18位由字母数字_和-组成的密码", order = 3)
	@InjectView(value=R.id.register_password)
    EditText register_password;//密码

	@TextRule(maxLength = 6, minLength = 1, message = "输入验证码", order = 2)
	@InjectView(value=R.id.register_security_code)
    EditText register_security_code;//验证码

	@InjectView(value=R.id.register_valitation)
    Button register_valitation;//验证码按钮
	@InjectView(value=R.id.register_email_btn)
    Button register_email_btn;//邮箱注册
	@InjectView(value=R.id.register_agree_btn)
	SlipButton  register_agree_btn;//同意条款
	Boolean agree_flag = true;
	//验证
	Validator validator;

	//计时
	CountTimer mc;

	Map<String, String> regMap;

	InternetConfig inConfig;

	String uuid;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.register, container, false);
		Handler_Inject.injectFragment(this, rootView);
		return rootView;
	}

	/**
	 * 初始化
	 * @function  init
	 * @description  初始化
	 * @return  void
	 */
	@InjectInit
	public void init(){
		Ioc.getIoc().getLogger().i("进入注册界面，初始化ui");
		inConfig = new InternetConfig();
		//获取验证码60秒钟内的uuid,如果有则取,如果没有则重新生成
		if(MapVo.get("register_tel_valitation_uuid")!=null){
			uuid = MapVo.get("register_tel_valitation_uuid").toString();
		}else{
			uuid = Uuid.getUuid();//给初始值
		}
		register_email_btn.setText("无法获得手机验证码?");

		register_agree_btn.setCheck(true);
		//设置监听
		register_agree_btn.setOnChangedListener(new OnChangedListener() {
			@Override
			public void OnChanged(boolean CheckState) {
				agree_flag = CheckState;

			}
		});
	}


	/**
	 * 提交，找回密码,验证
	 * @function  click
	 * @description  提交,找回密码,验证点击事件
	 * @param view
	 * @return  void
	 */
	@InjectMethod(@InjectListener(ids = { R.id.register_button,R.id.register_valitation,R.id.register_email_btn, R.id.register_agree_text }, listeners = { OnClick.class }))
	public void click(View view) {
		switch (view.getId()) {
		//提交注册
		case R.id.register_button:

			//验证
			validator = new Validator(this);
			validator.setValidationListener(this);
			validator.validate();

			break;

		//发送验证码
		case R.id.register_valitation:
			String account = register_account.getText().toString().trim();
			//验证账号 邮箱,手机
			if(!account.matches(Rules.REGEX_TELPHONE)){
				CommonUtils.onErrorToast(register_account, "请输入正确的手机号",activity);
				return;
			}
			//验证手机是否联网
			if(!Handler_Network.isNetworkAvailable(activity)){
				Toast.makeText(activity, "手机未联网", Toast.LENGTH_SHORT).show();
				return;
			}


			//一分钟内不能重复点击
			if(MapVo.get("register_tel_valitation")!=null){
				Toast.makeText(activity, "验证点击过于频繁,请一分钟后再重新验证", Toast.LENGTH_SHORT).show();
				return ;
			}else{
				 uuid = Uuid.getUuid();//用于参数的uuid
				 MapVo.set("register_tel_valitation_uuid", uuid);
			}
			((LoginAndRegisterFragmentActivity)activity).showDialog();
			inConfig.setKey(4);
			//验证账号是否已注册
			FastHttpHander.ajaxString(Url.MEM_CHECK_ACCOUNT,"{'account':'"+account+"'}",inConfig,this);

			break;
		//邮箱注册
		case R.id.register_email_btn:
			FragmentEventEntity crg = new FragmentEventEntity();
			crg.setFragment(new RegisterEmailFragment());
			crg.setLayoutid(R.id.tab_login_register);
			((LoginAndRegisterFragmentActivity)activity).eventBus.post(crg);
			break;
		case  R.id.register_agree_text:
			startActivity(new Intent(activity,HotelAgreement.class).putExtra("type", 4));
			break;
		default:
			break;
		}
	}

	/**
	 * 前端验证通过
	 * @function  onValidationSucceeded
	 * @description  前端验证通过
	 */
	@Override
	public void onValidationSucceeded() {
		Ioc.getIoc().getLogger().i("注册前端验证成功");
		//Toast.makeText(activity, "前端验证通过", Toast.LENGTH_SHORT).show();
		if(!agree_flag){
			Toast.makeText(activity, "请阅读用户协议并接受所有条款", Toast.LENGTH_SHORT).show();
			return;
		}
		//封装参数数据
		setRegParams();

		//验证手机是否联网
		if(!Handler_Network.isNetworkAvailable(activity)){
			Toast.makeText(activity, "手机未联网", Toast.LENGTH_SHORT).show();
			return;
		}

		Ioc.getIoc().getLogger().i("调用后台注册接口：["+Url.METHOD_REG+"]");

		inConfig.setKey(2);
		this.setRegParams();//设置注册数据
		((LoginAndRegisterFragmentActivity)activity).showDialog();;
		//调用后台注册接口
		FastHttpHander.ajaxString(Url.METHOD_REG,Handler_Json.beanToJson(regMap),inConfig,this);

	}

	/**
	 * 验证码成功返回结果
	 * @function  codeResultOk
	 * @description  验证码成功返回结果
	 * @param r
	 */
	@InjectHttpOk(1)
	private void codeResultOk(ResponseEntity r) {

		Ioc.getIoc().getLogger().i("验证码接口状态返回结果【"+r+"】");

		// 返回OK
		ResultParse.isResultOK(r, activity);
	}

	/**
	 * 注册成功返回结果
	 * @function  regResultOk
	 * @description  注册成功返回结果
	 * @param r
	 */
	@InjectHttpOk(2)
	private void regResultOk(ResponseEntity r) {
		Ioc.getIoc().getLogger().i("注册状态返回结果【"+r+"】");


		//注册成功 返回OK
		if(ResultParse.isResultOK(r, activity)){


			Ioc.getIoc().getLogger().i("注册成功");

			//调用登录接口

			Ioc.getIoc().getLogger().i("调用后台登录接口：["+Url.METHOD_LOGIN+"]");

			//设置登录数据
			setLoginParams();

			inConfig.setKey(3);
			//调用后台登录接口
			FastHttpHander.ajaxString(Url.METHOD_LOGIN,Handler_Json.beanToJson(regMap),inConfig,this);

		}else{
			((LoginAndRegisterFragmentActivity)activity).dismissDialog();
		}
	}


	/**
	 * 登录成功返回结果
	 * @function  loginResultOk
	 * @description 注册成功返回结果
	 * @param r
	 */
	@InjectHttpOk(3)
	private void loginResultOk(ResponseEntity r) {
		Ioc.getIoc().getLogger().i("登录接口状态返回结果【"+r+"】");

		//登录成功 返回OK
		if(ResultParse.isResultOK(r, activity)){
			//获取返回内容 ticket
			String jsonString = r.getContentAsString();

			Ioc.getIoc().getLogger().i("ticket数据【"+jsonString+"】");

			//存放ticket
			MapVo.map = Handler_Json.JsonToCollection(jsonString);//设置ticket TODO

			//存储用户名密码到本地
			Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "account", regMap.get("account"));
			Handler_SharedPreferences.WriteSharedPreferences(activity, "user", "password", regMap.get("password"));


			Ioc.getIoc().getLogger().i("登录成功");

			activity.setResult(Activity.RESULT_OK, new Intent(activity, AccountActivity.class));

			activity.finish();
		}

		((LoginAndRegisterFragmentActivity)activity).dismissDialog();
	}

	/**
	 * 验证账号返回结果
	 * @function  checkAccountResultOk
	 * @description  注册成功返回结果
	 * @param r
	 */
	@InjectHttpOk(4)
	private void checkAccountResultOk(ResponseEntity r) {
		Ioc.getIoc().getLogger().i("验证账号返回结果【"+r+"】");
		((LoginAndRegisterFragmentActivity)activity).dismissDialog();

		//注册成功 返回OK
		if(ResultParse.isResultOK(r, activity)){
			String result = Handler_Json.getValue("result", r.getContentAsString()).toString();
			//账号重复
			if(result.equals("1")){
				CommonUtils.onErrorToast(register_account, "该手机号已注册", activity);
				return;
			}

			//不重复
			//调用后台接口，往手机上发送验证码
			 mc = new CountTimer(60000, 1000,register_valitation,"register_tel_valitation");
		     mc.start();

			inConfig.setKey(1);
			this.setCodeParams();//设置验证码数据

			//调用后台验证码
			FastHttpHander.ajaxString(Url.METHOD_GETCODE,Handler_Json.beanToJson(regMap),inConfig,this);
		}
	}

	/**
	 * 请求数据失败处理
	 * @function  resultErr
	 * @description  请求数据失败处理
	 * @param r
	 */
	@InjectHttpErr(value = { 1,2,3,4 })
	private void resultErr(ResponseEntity r) {
		switch (r.getKey()) {
		//验证码
		case 1:
			Ioc.getIoc().getLogger().i("获取验证码失败");
			Toast.makeText(activity, "获取验证码失败，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		//注册
		case 2:
			Ioc.getIoc().getLogger().i("注册失败");
			Toast.makeText(activity, "注册失败，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		//登录
		case 3:
			Ioc.getIoc().getLogger().i("登录失败");
			Toast.makeText(activity, "登录失败，请检查网络", Toast.LENGTH_SHORT).show();
			break;
		//账号验证
		case 4:
			Ioc.getIoc().getLogger().i("账号验证失败");
			Toast.makeText(activity, "账号验证失败，请检查网络", Toast.LENGTH_SHORT).show();
			break;


		default:
			break;
		}

		((LoginAndRegisterFragmentActivity)activity).dismissDialog();
	}

	/**
	 * 前端验证失败
	 * @function  onValidationFailed
	 * @description   前端验证失败
	 * @param failedView
	 * @param failedRule
	 */
	@Override
	public void onValidationFailed(View failedView, Rule<?> failedRule) {
		String message = failedRule.getFailureMessage();
		if (failedView instanceof EditText) {
			failedView.requestFocus();
			Handler_TextStyle handler_TextStyle = new Handler_TextStyle();
			handler_TextStyle.setString(message);
//			handler_TextStyle.setForegroundColor(Color.RED, 0, message.length());
//			handler_TextStyle.setBackgroundColor(Color.RED, 0, message.length());
			((EditText) failedView).setError(handler_TextStyle.getSpannableString());
		} else {
			Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
		}
    }


	/**
	 * 封装注册参数数据
	 * @function  setRegParams
	 * @description  封装注册参数数据
	 * @return
	 */
	public Map<String,String> setRegParams(){

		regMap  = new HashMap<String, String>();
		regMap.put("account",register_account.getText().toString().trim());
		regMap.put("password",register_password.getText().toString().trim());
		regMap.put("code", register_security_code.getText().toString().trim());
		regMap.put("uuid", uuid);

		return regMap;
	}

	/**
	 * 封装登录参数数据
	 * @function  setLoginParams
	 * @description  封装登录参数数据
	 * @return
	 */
	public Map<String,String> setLoginParams(){

		regMap  = new HashMap<String, String>();
		regMap.put("account",register_account.getText().toString().trim());
		regMap.put("password",register_password.getText().toString().trim());

		return regMap;
	}

	/**
	 * 封装验证码参数数据
	 * @function  setCodeParams
	 * @description  封装验证码参数数据
	 * @return
	 */
	public Map<String,String> setCodeParams(){

		regMap  = new HashMap<String, String>();
		regMap.put("account",register_account.getText().toString().trim());
		regMap.put("logicFlag","1");
		regMap.put("uuid",uuid);

		return regMap;
	}

}
