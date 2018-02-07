package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.BusinessResultAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.CarInfoDao;
import com.thinkgem.jeesite.modules.sys.dao.CarMotorCycleDao;
import com.thinkgem.jeesite.modules.sys.dao.CarWagonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkClassDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkDepartmentDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkKindDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.BusinessResultAssemble;
import com.thinkgem.jeesite.modules.sys.entity.CarInfo;
import com.thinkgem.jeesite.modules.sys.entity.CarMotorCycle;
import com.thinkgem.jeesite.modules.sys.entity.CarWagon;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkClass;
import com.thinkgem.jeesite.modules.sys.entity.WorkDepartment;
import com.thinkgem.jeesite.modules.sys.entity.WorkKind;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShop;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;

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
	
	//工种
	private static WorkKindDao workKindDao = SpringContextHolder.getBean(WorkKindDao.class);
	
	public static final String WORK_KIND_LIST= "workKindMap";
	
	//班组人员
	private static WorkPersonDao workPersonDao = SpringContextHolder.getBean(WorkPersonDao.class);
	
	public static final String WORK_PERSON_LIST= "workPersonMap";
	
	//车型
	private static CarMotorCycleDao carMotorCycleDao = SpringContextHolder.getBean(CarMotorCycleDao.class);
	
	public static final String CMC_LIST= "cmcMap";
	
	//车号
	private static CarWagonDao carWagonDao = SpringContextHolder.getBean(CarWagonDao.class);
	
	public static final String CAR_WAGON_LIST= "carWagonMap";
	
	//车间任务
	private static WorkShopMaskDao workShopMaskDao = SpringContextHolder.getBean(WorkShopMaskDao.class);
	
	public static final String WORK_SHOP_MASK_LIST= "workShopMaskMap";
	
	//班级关联车间任务
	private static WsMaskWcDao wsMaskWcDao = SpringContextHolder.getBean(WsMaskWcDao.class);
	
	public static final String WS_MASK_WC_LIST= "wsMaskWcMap";
	
	//总负责人任务
	private static MaskMainPersonDao maskMainPersonDao = SpringContextHolder.getBean(MaskMainPersonDao.class);
	
	public static final String MMP_LIST= "mmpMap";
	
	//个人负责人任务
	private static MaskSinglePersonDao maskSinglePersonDao = SpringContextHolder.getBean(MaskSinglePersonDao.class);
	
	public static final String MSP_LIST= "mspMap";
	
	//所属结果集
	private static BusinessResultAssembleDao businessResultAssembleDao = SpringContextHolder.getBean(BusinessResultAssembleDao.class);
	
	public static final String BRA_LIST= "braMap";
	
	
	//所属结果集
	private static BusinessAssembleDao businessAssembleDao = SpringContextHolder.getBean(BusinessAssembleDao.class);
	
	public static final String BA_LIST= "baMap";
	
	//根据个人任务查找部位
	public static String getPartDictValue(String part,String mspId, String defaultLabel){
		//查找个人任务
		MaskSinglePerson msp = maskSinglePersonDao.get(mspId);
		//查找总负责人号
		String mmpId = msp.getMmpId();
		//查找总负责人
		MaskMainPerson mmp = maskMainPersonDao.get(mmpId);
		//任务号
		String wmwId = mmp.getWsMaskWcId();
		//任务
		WsMaskWc wmw = wsMaskWcDao.get(wmwId);
		//车间任务号
		String workShopMaskId = wmw.getWorkShopMaskId();
		//车间任务
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		//字典数据检验
		if(type.equals(DictUtils.getDictValue(Global.MOTOR_CHECK_SPOT_ITEM_1, "businessResultType", "1"))) {
			//不是模板表1 发动机点检单一
			return DictUtils.getDictLabel(part, "motorCsItem1", "1");
		}
		return null;
	}
	
	/**
	 * 获取总负责人任务信息
	 * @return
	 */
	public static List<MaskSinglePerson> getAllmspList(){
		@SuppressWarnings("unchecked")
		List<MaskSinglePerson> list = (List<MaskSinglePerson>)CacheUtils.get(MSP_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (MaskSinglePerson forEntity : maskSinglePersonDao.findAllList(new MaskSinglePerson())){
				list.add(forEntity);
			}
			CacheUtils.put(MSP_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取总负责人任务信息
	 * @return
	 */
	public static List<MaskMainPerson> getAllmmpList(){
		@SuppressWarnings("unchecked")
		List<MaskMainPerson> list = (List<MaskMainPerson>)CacheUtils.get(MMP_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (MaskMainPerson forEntity : maskMainPersonDao.findAllList(new MaskMainPerson())){
				list.add(forEntity);
			}
			CacheUtils.put(MMP_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取所有车间班级任务关联信息
	 * @return
	 */
	public static List<WsMaskWc> getAllWmwList(){
		@SuppressWarnings("unchecked")
		List<WsMaskWc> list = (List<WsMaskWc>)CacheUtils.get(WS_MASK_WC_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (WsMaskWc forEntity : wsMaskWcDao.findAllList(new WsMaskWc())){
				list.add(forEntity);
			}
			CacheUtils.put(WS_MASK_WC_LIST, list);
		}
		return list;
	}
	
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
	 * 获取所有车型信息
	 * @return
	 */
	public static List<CarMotorCycle> getAllCmcList(){
		@SuppressWarnings("unchecked")
		List<CarMotorCycle> list = (List<CarMotorCycle>)CacheUtils.get(CMC_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (CarMotorCycle cmc : carMotorCycleDao.findAllList(new CarMotorCycle())){
				list.add(cmc);
			}
			CacheUtils.put(CMC_LIST, list);
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
	 * 获取所有工种信息
	 * @return
	 */
	public static List<WorkKind> getAllWorkKindList(){
		@SuppressWarnings("unchecked")
		List<WorkKind> list = (List<WorkKind>)CacheUtils.get(WORK_KIND_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkKind cl : workKindDao.findAllList(new WorkKind())){
				list.add(cl);
			}
			CacheUtils.put(WORK_KIND_LIST, list);
		}
		return list;
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
	 * 获取所有车间任务信息
	 * @return
	 */
	public static List<WorkShopMask> getAllWsmList(){
		@SuppressWarnings("unchecked")
		List<WorkShopMask> list = (List<WorkShopMask>)CacheUtils.get(WORK_SHOP_MASK_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkShopMask wsm : workShopMaskDao.findAllList(new WorkShopMask())){
				list.add(wsm);
			}
			CacheUtils.put(WORK_SHOP_MASK_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取所有车间任务发布信息
	 * @return
	 */
	public static List<WorkShopMask> getReleaseWsmList(){
		@SuppressWarnings("unchecked")
		List<WorkShopMask> list = Lists.newArrayList();
		for (WorkShopMask wsm : workShopMaskDao.findAllList(new WorkShopMask())){
			String yes = DictUtils.getDictValue("是", "yes_no", "1");
			if(yes.equals(wsm.getReleaseState())) {
				list.add(wsm);
			}
		}
		return list;
	}
	
	
	/**
	 * 获取所有班组人员信息
	 * @return
	 */
	public static List<WorkPerson> getAllPersonList(){
		@SuppressWarnings("unchecked")
		List<WorkPerson> list = (List<WorkPerson>)CacheUtils.get(WORK_PERSON_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (WorkPerson p : workPersonDao.findAllList(new WorkPerson())){
				list.add(p);
			}
			CacheUtils.put(WORK_PERSON_LIST, list);
		}
		return list;
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
	 * 获取车号信息
	 * @return
	 */
	public static List<CarWagon> getCwList(){
		@SuppressWarnings("unchecked")
		List<CarWagon> list = (List<CarWagon>)CacheUtils.get(CAR_WAGON_LIST);
		if (list==null || list.size() == 0){
			list = Lists.newArrayList();
			for (CarWagon c : carWagonDao.findAllList(new CarWagon())){
				list.add(c);
			}
			CacheUtils.put(CAR_WAGON_LIST, list);
		}
		return list;
	}
	
}
