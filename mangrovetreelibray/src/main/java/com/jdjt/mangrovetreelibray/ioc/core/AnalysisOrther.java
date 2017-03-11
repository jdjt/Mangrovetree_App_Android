package com.jdjt.mangrovetreelibray.ioc.core;

import com.jdjt.mangrovetreelibray.ioc.annotation.InModule;
import com.jdjt.mangrovetreelibray.ioc.entity.OrtherEntity;
import com.jdjt.mangrovetreelibray.ioc.interfaces.BeanFactory;
import com.jdjt.mangrovetreelibray.ioc.interfaces.LoonModule;
import com.jdjt.mangrovetreelibray.ioc.ioc.Ioc;
import com.jdjt.mangrovetreelibray.ioc.ioc.config.LoonConfig;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.ModuleEntity;
import com.jdjt.mangrovetreelibray.ioc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class AnalysisOrther extends AnalysisCore<OrtherEntity> implements Analysis<OrtherEntity> {

	@Override
	public OrtherEntity process() {
		// -----------------------------------------------------------------------------------
		// 判断是否是 组件
		loadPlug(clazz);
		if (LoonConfig.instance().isDepend() && clazz.getAnnotation(InModule.class) != null) {
			InModule module = clazz.getAnnotation(InModule.class);
			if (module.value() != LoonConstant.Number.ID_NONE) {
				ModuleEntity entity = new ModuleEntity();
				entity.setId(module.value());
				object.setModuleEntity(entity);
				object.setEmpty(false);
				BeanFactory.load(clazz, new Class[] { LoonModule.class }, null);
				Ioc.getIoc().getLogger().i(" 模块" + clazz + " 挂载完毕");
			}
		}
		return super.process();
	}

}
