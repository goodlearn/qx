package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.CarInfoDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkDepartmentDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultAssemble;
import com.thinkgem.jeesite.modules.sys.entity.CarInfo;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkDepartment;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;

/**
 * 基础信息
 * @author Administrator
 *
 */
public class BaseInfoUtils {

	//车间
	private static WorkShopDao workShopDao = SpringContextHolder.getBean(WorkShopDao.class);
	
	public static final String WORK_SHOP_LIST= "workShopMap";
	
	//部门
	private static WorkDepartmentDao workDepartmentDao = SpringContextHolder.getBean(WorkDepartmentDao.class);
	
	public static final String WORK_DEPARTMENT_LIST= "workDepartmentMap";
	
	//班组
	private static WorkClassDao workClassDao = SpringContextHolder.getBean(WorkClassDao.class);
	
	public static final String WORK_CLASS_LIST= "workClassMap";
	
	//所属结果集
	private static BusinessResultAssembleDao businessResultAssembleDao = SpringContextHolder.getBean(BusinessResultAssembleDao.class);
	
	public static final String BRA_LIST= "braMap";
	
	
	//所属结果集
	private static BusinessAssembleDao businessAssembleDao = SpringContextHolder.getBean(BusinessAssembleDao.class);
	
	public static final String BA_LIST= "baMap";
	
	//车型车号
	private static CarInfoDao carInfoDao = SpringContextHolder.getBean(CarInfoDao.class);
	
	public static final String CAR_INFO_LIST= "carInfoMap";
	
	/**
	 * 获取所有车间信息
	 * @return
	 */
	public static List<WorkShop> getAllWorkShopList(){
		@SuppressWarnings("unchecked")
		List<WorkShop> list = (List<WorkShop>)CacheUtils.get(WORK_SHOP_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkShop workShop : workShopDao.findAllList(new WorkShop())){
				list.add(workShop);
			}
			CacheUtils.put(WORK_SHOP_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取车间信息
	 * @return
	 */
	public static List<WorkShop> getWorkShopByIdList(String id){
		@SuppressWarnings("unchecked")
		List<WorkShop> list = getAllWorkShopList();
		List<WorkShop> rets = Lists.newArrayList();
		for(WorkShop entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
	
	/**
	 * 获取所有部门信息
	 * @return
	 */
	public static List<WorkDepartment> getAllDpList(){
		@SuppressWarnings("unchecked")
		List<WorkDepartment> list = (List<WorkDepartment>)CacheUtils.get(WORK_DEPARTMENT_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkDepartment wp : workDepartmentDao.findAllList(new WorkDepartment())){
				list.add(wp);
			}
			CacheUtils.put(WORK_DEPARTMENT_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取部门信息
	 * @return
	 */
	public static List<WorkDepartment> getDpByIdList(String id){
		@SuppressWarnings("unchecked")
		List<WorkDepartment> list = getAllDpList();
		List<WorkDepartment> rets = Lists.newArrayList();
		for(WorkDepartment entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
	
	/**
	 * 获取所有班组信息
	 * @return
	 */
	public static List<WorkClass> getAllClassList(){
		@SuppressWarnings("unchecked")
		List<WorkClass> list = (List<WorkClass>)CacheUtils.get(WORK_CLASS_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkClass cl : workClassDao.findAllList(new WorkClass())){
				list.add(cl);
			}
			CacheUtils.put(WORK_CLASS_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取班组信息
	 * @return
	 */
	public static List<WorkClass> getClassByIdList(String id){
		@SuppressWarnings("unchecked")
		List<WorkClass> list = getAllClassList();
		List<WorkClass> rets = Lists.newArrayList();
		for(WorkClass entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
	
	/**
	 * 获取结果集信息
	 * @return
	 */
	public static List<BusinessResultAssemble> getBraList(){
		@SuppressWarnings("unchecked")
		List<BusinessResultAssemble> list = (List<BusinessResultAssemble>)CacheUtils.get(BRA_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (BusinessResultAssemble bra : businessResultAssembleDao.findAllList(new BusinessResultAssemble())){
				list.add(bra);
			}
			CacheUtils.put(BRA_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取结果集信息
	 * @return
	 */
	public static List<BusinessResultAssemble> getBraByIdList(String id){
		@SuppressWarnings("unchecked")
		List<BusinessResultAssemble> list = getBraList();
		List<BusinessResultAssemble> rets = Lists.newArrayList();
		for(BusinessResultAssemble entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
	
	/**
	 * 获取业务集信息
	 * @return
	 */
	public static List<BusinessAssemble> getBaList(){
		@SuppressWarnings("unchecked")
		List<BusinessAssemble> list = (List<BusinessAssemble>)CacheUtils.get(BA_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (BusinessAssemble ba : businessAssembleDao.findAllList(new BusinessAssemble())){
				list.add(ba);
			}
			CacheUtils.put(BA_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取业务集信息
	 * @return
	 */
	public static List<BusinessAssemble> getBaByIdList(String id){
		@SuppressWarnings("unchecked")
		List<BusinessAssemble> list = getBaList();
		List<BusinessAssemble> rets = Lists.newArrayList();
		for(BusinessAssemble entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
	
	/**
	 * 获取车型车号信息
	 * @return
	 */
	public static List<CarInfo> getCiList(){
		@SuppressWarnings("unchecked")
		List<CarInfo> list = (List<CarInfo>)CacheUtils.get(CAR_INFO_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (CarInfo c : carInfoDao.findAllList(new CarInfo())){
				list.add(c);
			}
			CacheUtils.put(CAR_INFO_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取车型车号信息
	 * @return
	 */
	public static List<CarInfo> getCiByIdList(String id){
		@SuppressWarnings("unchecked")
		List<CarInfo> list = getCiList();
		List<CarInfo> rets = Lists.newArrayList();
		for(CarInfo entity : list) {
			if(id.equals(entity.getId())) {
				rets.add(entity);
			}
		}
		return rets;
	}
}
