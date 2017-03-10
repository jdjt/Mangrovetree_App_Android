package com.jdjt.mangrovetreelibray.ioc.core;

import com.jdjt.mangrovetreelibray.ioc.annotation.InLayer;
import com.jdjt.mangrovetreelibray.ioc.entity.FragmentV4Entity;
import com.jdjt.mangrovetreelibray.ioc.interfaces.BeanFactory;
import com.jdjt.mangrovetreelibray.ioc.interfaces.LoonFragment;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.ioc.config.LoonConfig;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class AnalysisFragmentV4 extends AnalysisCore<FragmentV4Entity> implements Analysis<FragmentV4Entity> {

	@Override
	public FragmentV4Entity process() {
		if (LoonConfig.instance().isDepend()&&null!=clazz.getAnnotation(InLayer.class)) {
			BeanFactory.load(clazz, new Class[]{LoonFragment.class}, null);
			Ioc.getIoc().getLogger().i(" fragment "+clazz+" 挂载完毕");
		}
		return super.process();
	}
}
