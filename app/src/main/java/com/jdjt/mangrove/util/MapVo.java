package com.jdjt.mangrove.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * （在整个app 生命周期结束后 此线程关闭）
 * @author wangmingyu
 *
 */
public class MapVo {
	public static Map<String , Object> map = null;

	public MapVo(){

	}

    /**
     * 获得线程中保存的属性.
     *
     * @param attribute
     *            属性名称
     * @return 属性值
     */
    public static Object get(String attribute) {
    	 if (map == null) {
             map = getInstanceMap();
         }
        return map.get(attribute);
    }
    /**
     * 删除缓存
     * @param attribute
     * @return
     * 2014-7-11上午11:27:11
     * @author wangmingyu
     */
    public static Object remove(String attribute) {
   	 if (map == null) {
            map = getInstanceMap();
        }
       return map.remove(attribute);
   }
    /**
     * 获得线程中保存的属性，使用指定类型进行转型.
     *
     * @param attribute
     *            属性名称
     * @param clazz
     *            类型
     * @param <T>
     *            自动转型
     * @return 属性值
     */
    public static <T> T get(String attribute, Class<T> clazz) {

        return (T) get(attribute);
    }

    /**
     * 设置制定属性名的值.
     *
     * @param attribute
     *            属性名称
     * @param value
     *            属性值
     */
    public static void set(String attribute, Object value) {

        if (map == null) {
            map = getInstanceMap();
        }

        map.put(attribute, value);
    }

    /**
     * 获取map
     * @return
     */
    public static Map<String, Object> getInstanceMap(){
    	 if (map == null) {
             map = new HashMap<String , Object>();
         }

    	 return map;
    }

}
