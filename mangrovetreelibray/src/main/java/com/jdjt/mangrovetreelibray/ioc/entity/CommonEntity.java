package com.jdjt.mangrovetreelibray.ioc.entity;

import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InAllEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InBeanEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InDestroyEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InHttpEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InLayerEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InMethodEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InOnNewEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InPLayerEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InPauseEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InPullRefreshEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InRestartEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InResumeEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InSourceEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InStartEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InStopEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InSubscribeEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InVaErEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InVaOkEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InViewEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.InitEntity;
import com.jdjt.mangrovetreelibray.ioc.ioc.entity.ModuleEntity;
import com.jdjt.mangrovetreelibray.ioc.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

public class CommonEntity implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 7848386334182782135L;
	private InDestroyEntity destroy;
	private InitEntity init;
	private InLayerEntity layer;
	private InOnNewEntity newIntent;
	private InPauseEntity pauseEntity;
	private InPLayerEntity pLayer;
	private InRestartEntity restartEntity;
	private InResumeEntity resumeEntity;
	private InStartEntity startEntity;
	private InStopEntity stopEntity;
	private InVaOkEntity inVaOk;
	private InVaErEntity inVaER;
	private InPullRefreshEntity pullRefresh;

	private ArrayList<InSubscribeEntity> subscribeEntities = null;
	private ArrayList<ModuleEntity> modules = null;
	private ArrayList<InMethodEntity> methods = null;
	private ArrayList<InSourceEntity> resources = null;
	private ArrayList<InViewEntity> views = null;
	private ArrayList<InBeanEntity> inBeanEntitys = null;
	private ArrayList<InAllEntity> alls = null;
	private HashMap<Integer, ArrayList<InHttpEntity>> https = null;

	private boolean isEmpty = true;

	public ArrayList<InAllEntity> getAll() {
		if (alls == null) {
			return Util.emptyList();
		}
		return alls;
	}

	public void setAll(InAllEntity all) {
		if (alls == null) {
			alls = new ArrayList<InAllEntity>();
		}
		this.alls.add(all);
	}

	public HashMap<Integer, ArrayList<InHttpEntity>> getHttps() {
		if (https == null) {
			return Util.emptyMap();
		}
		return https;
	}

	public void setHttp(int key, InHttpEntity http) {
		if (https == null) {
			https = new HashMap<Integer, ArrayList<InHttpEntity>>();
		}
		if (!https.containsKey(key)) {
			https.put(key, new ArrayList<InHttpEntity>());
		}
		this.https.get(key).add(http);
	}

	public ArrayList<InMethodEntity> getInMethod() {
		if (methods == null) {
			return Util.emptyList();
		}
		return methods;
	}

	public void setInMethod(InMethodEntity method) {
		if (this.methods == null) {
			this.methods = new ArrayList<InMethodEntity>();
		}
		this.methods.add(method);
	}

	public ArrayList<InSubscribeEntity> getInSubscribe() {
		if (subscribeEntities == null) {
			return Util.emptyList();
		}
		return subscribeEntities;
	}

	public void setInSubscribe(InSubscribeEntity method) {
		if (this.subscribeEntities == null) {
			this.subscribeEntities = new ArrayList<InSubscribeEntity>();
		}
		this.subscribeEntities.add(method);
	}

	public InLayerEntity getInLayer() {
		return layer;
	}

	public void setInLayer(InLayerEntity inLayer) {
		this.layer = inLayer;
	}

	public ArrayList<InSourceEntity> getInResource() {
		if (resources == null) {
			return Util.emptyList();
		}
		return resources;
	}

	public void setInResource(InSourceEntity inResource) {
		if (this.resources == null) {
			this.resources = new ArrayList<InSourceEntity>();
		}
		this.resources.add(inResource);
	}

	public ArrayList<InViewEntity> getInView() {
		if (views == null) {
			return Util.emptyList();
		}
		return views;
	}

	public void setInView(InViewEntity inView) {
		if (this.views == null) {
			this.views = new ArrayList<InViewEntity>();
		}
		this.views.add(inView);
	}

	public InLayerEntity getLayer() {
		return layer;
	}

	public void setLayer(InLayerEntity layer) {
		this.layer = layer;
	}

	public ArrayList<InSourceEntity> getResource() {
		if (resources == null) {
			return Util.emptyList();
		}
		return resources;
	}

	public void setResource(InSourceEntity resource) {
		if (this.resources == null) {
			this.resources = new ArrayList<InSourceEntity>();
		}
		this.resources.add(resource);
	}

	public ArrayList<InViewEntity> getView() {
		if (views == null) {
			return Util.emptyList();
		}
		return views;
	}

	public void setView(InViewEntity view) {
		if (views == null) {
			this.views = new ArrayList<InViewEntity>();
		}
		this.views.add(view);
	}

	public InOnNewEntity getNewIntent() {
		return newIntent;
	}

	public void setNewIntent(InOnNewEntity newIntent) {
		this.newIntent = newIntent;
	}

	public InDestroyEntity getDestroy() {
		return destroy;
	}

	public void setDestroy(InDestroyEntity destroy) {
		this.destroy = destroy;
	}

	public InitEntity getInit() {
		return init;
	}

	public void setInit(InitEntity init) {
		this.init = init;
	}

	public InPauseEntity getPauseEntity() {
		return pauseEntity;
	}

	public void setPauseEntity(InPauseEntity pauseEntity) {
		this.pauseEntity = pauseEntity;
	}

	public void setPLayer(InPLayerEntity pLayer) {
		this.pLayer = pLayer;
	}

	public void setRestartEntity(InRestartEntity restartEntity) {
		this.restartEntity = restartEntity;
	}

	public void setResumeEntity(InResumeEntity resumeEntity) {
		this.resumeEntity = resumeEntity;
	}

	public void setStartEntity(InStartEntity startEntity) {
		this.startEntity = startEntity;
	}

	public void setStopEntity(InStopEntity stopEntity) {
		this.stopEntity = stopEntity;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public InPLayerEntity getPLayer() {
		return pLayer;
	}

	public InRestartEntity getRestartEntity() {
		return restartEntity;
	}

	public InResumeEntity getResumeEntity() {
		return resumeEntity;
	}

	public InStartEntity getStartEntity() {
		return startEntity;
	}

	public InStopEntity getStopEntity() {
		return stopEntity;
	}

	public InPullRefreshEntity getPullRefresh() {
		return pullRefresh;
	}

	public void setPullRefresh(InPullRefreshEntity pullRefresh) {
		this.pullRefresh = pullRefresh;
	}

	public ArrayList<InBeanEntity> getInBeanEntity() {
		if (inBeanEntitys == null) {
			return Util.emptyList();
		}
		return inBeanEntitys;
	}

	public void setInBeanEntity(InBeanEntity inBeanEntity) {
		if (inBeanEntitys == null) {
			this.inBeanEntitys = new ArrayList<InBeanEntity>();
		}
		this.inBeanEntitys.add(inBeanEntity);
	}

	public ArrayList<ModuleEntity> getModuleEntity() {
		if (modules == null) {
			return Util.emptyList();
		}
		return modules;
	}

	public void setModuleEntity(ModuleEntity moduleEntity) {
		if (modules == null) {
			this.modules = new ArrayList<ModuleEntity>();
		}
		this.modules.add(moduleEntity);
	}

	public InVaOkEntity getInVaOk() {
		return inVaOk;
	}

	public void setInVaOk(InVaOkEntity inVaOk) {
		this.inVaOk = inVaOk;
	}

	public InVaErEntity getInVaER() {
		return inVaER;
	}

	public void setInVaER(InVaErEntity inVaER) {
		this.inVaER = inVaER;
	}

	@Override
	public String toString() {
		return "CommonEntity [destroy=" + destroy + ", init=" + init + ", layer=" + layer + ", newIntent=" + newIntent + ", pauseEntity=" + pauseEntity + ", pLayer=" + pLayer + ", restartEntity=" + restartEntity + ", resumeEntity=" + resumeEntity + ", startEntity=" + startEntity + ", stopEntity=" + stopEntity + ", modules=" + modules + ", methods=" + methods + ", resources=" + resources + ", pullRefresh=" + pullRefresh + ", views=" + views + ", inBeanEntitys=" + inBeanEntitys + ", alls=" + alls + ", https=" + https + ", isEmpty=" + isEmpty + "]";
	}

}
