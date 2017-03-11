package com.jdjt.mangrovetreelibray.ioc.entity;

import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InNetEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InPlugInitEntity;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class OrtherEntity extends CommonEntity implements Serializable {

	@Override
    public String toString() {
	    return "OrtherEntity [netEntity=" + netEntity + ", initEntity=" + initEntity + "]";
    }
	private InNetEntity netEntity;
	private ArrayList<InPlugInitEntity> initEntity = new ArrayList<InPlugInitEntity>();

	public ArrayList<InPlugInitEntity> getInitEntity() {
		return initEntity;
	}

	public void setInitEntity(InPlugInitEntity initEntity) {
		this.initEntity.add(initEntity);
	}

	public InNetEntity getNetEntity() {
		return netEntity;
	}

	public void setNetEntity(InNetEntity netEntity) {
		this.netEntity = netEntity;
	}
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = -2747426928687927384L;
}
