package com.jdjt.mangrovetreelibray.ioc.entity;

import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InAfterEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InBeforeEntity;

import java.io.Serializable;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class ActivityEntity extends CommonEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -2747426928687927384L;

	private InAfterEntity after;
	private InBeforeEntity before;

	public InAfterEntity getAfter() {
		return after;
	}

	public void setAfter(InAfterEntity after) {
		this.after = after;
	}

	public InBeforeEntity getBefore() {
		return before;
	}

	public void setBefore(InBeforeEntity before) {
		this.before = before;
	}
}
