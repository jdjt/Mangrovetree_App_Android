package com.jdjt.mangrove.entity;


import android.support.v4.app.Fragment;

/**
 * 用于方法注册。eventbus
 * @author wangmingyu
 *
 */
public class FragmentEventEntity {

	/**
	 * fragment 页面
	 * 2014-6-18下午4:24:30
	 * @author wangmingyu
	 */
	private Fragment fragment;
	/**
	 * Activity fragment页面id
	 * 2014-6-18下午4:24:44
	 * @author wangmingyu
	 */
	private int layoutid;
	public Fragment getFragment() {
		return fragment;
	}
	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
	public int getLayoutid() {
		return layoutid;
	}
	public void setLayoutid(int layoutid) {
		this.layoutid = layoutid;
	}

}
