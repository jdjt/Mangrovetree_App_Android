package com.jdjt.mangrove.util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 倒计时
 * @author Administrator
 *
 */
public class CountTimer extends CountDownTimer {
	public static int countMillis = 0;//验证倒计时秒数
	private View view;
	private String key;

	 public CountTimer(long millisInFuture, long countDownInterval, View view, String key) {
		super(millisInFuture, countDownInterval);
		this.view = view;
		this.key = key;

	}

//	 public CountTimer(long millisInFuture, long countDownInterval,View view) {
//			super(millisInFuture, countDownInterval);
//			this.view = view;
//		}
     @Override
     public void onFinish() {
    	 view.setClickable(true);
    	 setViewText(view,"验 证");
    	 countMillis = 0;
    	 MapVo.remove(key+"_uuid");
    	 MapVo.remove(key);
     }
     @Override
     public void onTick(long millisUntilFinished) {
    	 view.setClickable(false);
    	 setViewText(view,millisUntilFinished / 1000 + " 秒");
    	 countMillis = (int) (millisUntilFinished / 1000);
    	 if(key!=null)
    		 MapVo.set(key, (int) (millisUntilFinished / 1000));
         //Toast.makeText(NewActivity.this, millisUntilFinished / 1000 + "", Toast.LENGTH_LONG).show();//toast有显示时间延迟
     }

     /**
      * 设置文本内容
      * @param view
      * @param textContent
      */
     public void setViewText(View view, String textContent){
 		if (view instanceof Button) {
 			((Button)view).setText(textContent) ;

 		}else if(view instanceof TextView){

 			((TextView)view).setText(textContent) ;
 		}
     }
}
