package com.jdjt.mangrovetreelibray.ioc.plug.net;

import com.jdjt.mangrovetreelibray.ioc.handler.Handler_File;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.util.MD5;

import java.io.File;
import java.util.LinkedHashMap;

public class HttpCache {

	public static String getUrlCache(String url, LinkedHashMap<String, Object> params) {
		if (url == null) {
			return null;
		}
		for (String key : params.keySet()) {
			url = url+key+params.get(key);
        }
		int time = InternetConfig.defaultConfig().getSaveDate();

		String result = null;
		File file = new File(Ioc.getIoc().getApplication().getCacheDir(), getCacheDecodeString(url));
		if (file.exists() && file.isFile()) {
			long expiredTime = System.currentTimeMillis() - file.lastModified();
			Ioc.getIoc().getLogger().d("缓存了:" + expiredTime / 60000 + "分钟");
			if (time!=-1&&expiredTime>time) {
				file.delete();
				return null;
            }
			result = Handler_File.getAsString(file);
		}
		return result;
	}

	public static void setUrlCache(String data, String url) {
		File file = new File(Ioc.getIoc().getApplication().getCacheDir(), getCacheDecodeString(url));
		// 创建缓存数据到磁盘，就是创建文件
		Handler_File.write(file, data);
	}

	private static String getCacheDecodeString(String url) {
		// 1. 处理特殊字符
		return MD5.Md5(url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+"));
	}
}
