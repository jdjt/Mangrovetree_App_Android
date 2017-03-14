package com.jdjt.mangrovetreelibray.ioc.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.jdjt.mangrovetreelibray.ioc.handler.Handler_TextStyle;
import com.jdjt.mangrovetreelibray.views.BadgeView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Enumeration;

public class CommonUtils {

	private static long lastClickTime;
	public static final String SERVER_IP="http://192.168.1.105/";
	public static final String SERVER_ADDRESS=SERVER_IP+"try_downloadFile_progress_server/index.php";//软件更新包地址
	public static final String UPDATESOFTADDRESS=SERVER_IP+"try_downloadFile_progress_server/update_pakage/baidu.apk";//软件更新包地址
	private static CommonUtils cu;
	private BadgeView badge;
	public static CommonUtils newInstence(){
		if(cu==null){
			cu=new CommonUtils();
		}
		return cu;
	}

	/**
	 * 防止重复点击
	 *
	 * @return 2014-6-26下午2:35:26
	 * @author wangmingyu
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 判断是否有长按动作发生
	 *
	 * @param lastX
	 *            按下时X坐标
	 * @param lastY
	 *            按下时Y坐标
	 * @param thisX
	 *            移动时X坐标
	 * @param donwY
	 *            移动时Y坐标
	 * @param lastDownTime
	 *            按下时间 *
	 * @param thisEventTime
	 *            移动时间 *
	 * @param longPressTime
	 *            判断长按时间的阀值
	 * @return 2014-6-26下午2:54:17
	 * @author wangmingyu
	 */
	public static boolean isLongPressed(float lastX, float lastY, float thisX,
			float donwY, long lastDownTime, long thisEventTime,
			long longPressTime) {
		float offsetX = thisX - lastX;
		float offsetY = donwY - lastY;
		long intervalTime = thisEventTime - lastDownTime;
		if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {
			return true;
		}
		return false;
	}

	/**
	 * 浮点数精确到2位小数
	 *
	 * @param scale
	 * @return
	 */
	public static String fomatFloat2(float scale) {

		DecimalFormat fnum = new DecimalFormat("##0.00");
		return fnum.format(scale);
	}

	public static BigDecimal zeroTransformNull(BigDecimal bigDecimal) {
		if (null == bigDecimal) {
			return new BigDecimal("0.00");
		} else {
			return bigDecimal.setScale(2);
		}
	}

	/**
	 * 错误信息提示
	 * @param failedView
	 * @param failureMessage
	 * @param context
	 */
	public static void onErrorToast(View failedView, String failureMessage, Context context) {
		if (failedView instanceof EditText) {
			failedView.requestFocus();
			Handler_TextStyle handler_TextStyle = new Handler_TextStyle();
			handler_TextStyle.setString(failureMessage);
			((EditText) failedView).setError(handler_TextStyle.getSpannableString());
		} else {
			Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
		}
    }

	/**
	 * 获取iPhone地址
	 * @return
	 * @author wangmingyu
	 * @2014-9-20@下午2:32:10
	 * String
	 */
	public static String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
               NetworkInterface intf = en.nextElement();
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
               {
                   InetAddress inetAddress = enumIpAddr.nextElement();
                   if (!inetAddress.isLoopbackAddress())
                   {
                       return inetAddress.getHostAddress().toString();
                   }
               }
           }
        }
        catch (SocketException ex)
        {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }
	public static String getPsdnIp() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
	                //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (Exception e) {
	    }
	    return "";
	}

	 public static String intToIp(int i) {

         return (i & 0xFF ) + "." +
       ((i >> 8 ) & 0xFF) + "." +
       ((i >> 16 ) & 0xFF) + "." +
       ( i >> 24 & 0xFF) ;
    }

	/*************************************************
	 * @Title: getVersionName
	 * @Description: TODO(获取当前版本号)
	 * @return
	 * @throws Exception
	 *             设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2015-1-30
	 *************************************************/
	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}

	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
		//如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			URL url = new URL(path);
			HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			//获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len ;
			int total=0;
			while((len =bis.read(buffer))!=-1){
				fos.write(buffer, 0, len);
				total+= len;
				//获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		}
		else{
			return null;
		}
	}
	/**
	 * 获取软件版本号
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			//注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
			verCode = context.getPackageManager().getPackageInfo(
					"com.android.mymhotel", 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("msg",e.getMessage());
		}
		return verCode;
	}
	/**
	 * 获取版本名称
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.android.mymhotel", 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			Log.e("msg",e.getMessage());
		}
		return verName;
	}

//	/**
//	 * 向服务器发送查询请求，返回查到的StringBuilder类型数据
//	 *
//	 * @param ArrayList
//	 *            <NameValuePair> vps POST进来的参值对
//	 * @return StringBuilder builder 返回查到的结果
//	 * @throws Exception
//	 */
//	public static StringBuilder post_to_server(List<NameValuePair> vps) {
//		CloseableHttpClient httpclient =   HttpClients.createDefault();
//		try {
//			HttpResponse response = null;
//			// 创建httpost.访问本地服务器网址
//			HttpPost httpost = new HttpPost(SERVER_ADDRESS);
//			StringBuilder builder = new StringBuilder();
//
//			httpost.setEntity(new UrlEncodedFormEntity(vps, "UTF-8"));
//			response = httpclient.execute(httpost); // 执行
//
//			if (response.getEntity() != null) {
//				// 如果服务器端JSON没写对，这句是会出异常，是执行不过去的
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(response.getEntity().getContent()));
//				String s = reader.readLine();
//				for (; s != null; s = reader.readLine()) {
//					builder.append(s);
//				}
//			}
//			return builder;
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("msg",e.getMessage());
//			return null;
//		} finally {
//			try {
//				httpclient.getConnectionManager().shutdown();// 关闭连接
//				// 这两种释放连接的方法都可以
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				Log.e("msg",e.getMessage());
//			}
//		}
//	}
//	public BadgeView careteMsg(Context context ,View view){
//		badge= new BadgeView(context, view);//创建一个BadgeView对象，view为你需要显示提醒信息的控件
//		badge.setWidthAndHight(18);
//		badge.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);//显示的位置.中间，还有其他位置属性
//		badge.setTextColor(Color.WHITE);  //文本颜色
//		badge.setBadgeBackgroundColor(Color.RED); //背景颜色
//		badge.setTextSize(12); //文本大小
////		badge.setHeight(pixels)
//		badge.setBadgeMargin(0, 5); //水平和竖直方向的间距
//		badge.setBadgeMargin(0);//各边间距
//		badge.setText("2");
//		/**
//		 * 可以设置一个动画
//		 */
////		        TranslateAnimation anim = new TranslateAnimation(-100, 0, 0, 0);
////		        anim.setInterpolator(new BounceInterpolator());
////		        anim.setDuration(1000);
////		        badge1.toggle(anim,null);
//		badge.toggle(); //设置，还要加这句,已经显示则影藏，否则显示
//		//单纯的显示badge1.show();
//		//badge1.hiden();影藏
//		return badge;
//	}
//
//	public void showMsg(){
//		badge.show();
//	}
//	public void hideMsg(){
//		badge.hide();
//	}
}
