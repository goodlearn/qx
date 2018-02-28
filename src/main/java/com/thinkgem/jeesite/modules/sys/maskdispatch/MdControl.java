package com.thinkgem.jeesite.modules.sys.maskdispatch;

import java.util.List;

import org.springframework.ui.Model;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.MotorCheckSpotItem1Dao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 页面调度的时候，用来进行分发的类
 * 发动机 电工的表格不同，通过这个类，对每个任务请求返回不同的处理页面
 *
 */
public class MdControl {

	
	private WorkPersonDao workPersonDao;
	
	private WorkShopMaskDao workShopMaskDao;
	
	private WsMaskWcDao wsMaskWcDao;
	
	private BusinessAssembleDao businessAssembleDao;
	
	
	private MotorCheckSpotItem1Dao motorCheckSpotItem1Daol;
	
	public final static String ERROR_URL_PC = "modules/sys/sysError";
	//错误页面
	public final static String WX_ERROR = "modules/wxp/500";
	public final static String MOTOR_CHECK_SPOT_ITEM_1 = "modules/maskdispatch/mcsi1Form";//模板表1 发动机点检单一
	public final static String FITTER_CHECK_SPOT_ITEM_1 = "modules/maskdispatch/fcsi1Form";//汽修车间220T卡车钳工周检点检卡
	
	public final static String SF31904C_CS_ITEM = "modules/maskdispatch/sf31904ccsForm";//SF31904C卡车点检卡
	public final static String WX_SF31904C_CS_ITEM = "modules/wxp/sf31904ccstp";//SF31904C卡车点检卡

	public final static String ITEM_220T_ZX_BY = "modules/maskdispatch/zxby220tForm";//  220T自卸卡车保养单（电气部分）
	public final static String WX_ITEM_220T_ZX_BY = "modules/wxp/zxby220p";//  220T自卸卡车保养单（电气部分）
	
	public final static String SF31904C_BY_ITEM = "modules/maskdispatch/sf31904ByForm";// SF31904卡车保养单（电气部分）
	public final static String WX_SF31904C_BY_ITEM = "modules/wxp/sf31904Byp";//  SF31904卡车保养单（电气部分）
	
	public final static String ITEM_220T_DG_DJ = "modules/maskdispatch/item220DgDjForm";// 220T卡车电工周点检卡（电气部分）
	public final static String WX_ITEM_220T_DG_DJ = "modules/wxp/item220DgDjp";//  220T卡车电工周点检卡（电气部分）
	
	public final static String ITEM_SF31904_KC_DG_DJ = "modules/maskdispatch/itemsf31904kcDgDjForm";// 220T卡车电工周点检卡（电气部分）
	public final static String WX_ITEM_SF31904_KC_DG_DJ = "modules/wxp/itemsf31904kcDgDjp";//  220T卡车电工周点检卡（电气部分）
	
	public final static String ITEM_108T_2000H_BY = "modules/maskdispatch/item108t2000hByForm";// 220T卡车电工周点检卡（电气部分）
	public final static String WX_ITEM_108T_2000H_BY = "modules/wxp/item108t2000hByp";//  220T卡车电工周点检卡（电气部分）
	
	public final static String ITEM_MT_4400_ZJFQ = "modules/maskdispatch/itemMt440ZjfqForm";// MT4400卡车钳工周检分区
	public final static String WX_ITEM_MT_4400_ZJFQ = "modules/wxp/itemMt440Zjfqp";//  MT4400卡车钳工周检分区
	
	public final static String ITEM_220T_QG_DJ = "modules/maskdispatch/item220tQgDjForm";// 220T卡车钳工点检分区
	public final static String WX_ITEM_220T_QG_DJ = "modules/wxp/item220tQgDjp";//  220T卡车钳工点检分区
	
	public final static String ITEM_QX2B_MT_4400_DJ = "modules/maskdispatch/itemQx2bMt4400DjForm";// 汽修二班MT4400保养责任分区
	public final static String WX_ITEM_QX2B_MT_4400_DJ = "modules/wxp/itemQx2bMt4400Djp";//  汽修二班MT4400保养责任分区
	
	public final static String ITEM_QX2B_830_BY = "modules/maskdispatch/itemQx2b830eByForm";// 汽修二班830E保养责任分区
	public final static String WX_ITEM_QX2B_830_BY = "modules/wxp/itemQx2b830eByp";//  汽修二班830E保养责任分区
	
	public final static String ITEM_108T_330_BY = "modules/maskdispatch/item108t330ByForm";// 108T卡车330小时保养单(机械部分)
	public final static String WX_ITEM_108T_330_BY = "modules/wxp/item108t330Byp";//  108T卡车330小时保养单(机械部分)
	
	public final static String ITEM_108T_660_BY = "modules/maskdispatch/item108t660ByForm";// 108T卡车660小时保养单(机械部分)
	public final static String WX_ITEM_108T_660_BY = "modules/wxp/item108t660Byp";//  108T卡车660小时保养单(机械部分)
	
	public final static String ITEM_108T_1000_BY = "modules/maskdispatch/item108t1000ByForm";// 108T卡车1000小时保养单(机械部分)
	public final static String WX_ITEM_108T_1000_BY = "modules/wxp/item108t1000Byp";//  108T卡车1000小时保养单(机械部分)
	
	
	public final static String NO_MONITOR = "您不是班长，无操作权限";
	
	private String value = null;//返回值
	
	private Model model;//设置返回参数
	
	private String maskId;//调度的任务Id
	
	private String empNo;//员工号
	
	private boolean isWx = false;//默认不是微信
	
	public void pageDispatch() {
		
		//先看看是不是班长
		if(!isMonitor()) {
			if(isWx()) {
				model.addAttribute("message",NO_MONITOR);
				setValue(WX_ERROR);
			}else {
				model.addAttribute("message",NO_MONITOR);
				setValue(ERROR_URL_PC);
			}
		}
		
		
		
		//根据任务号 找到分配页面
		setMaskAllocation();
	}
	
	//根据任务号 设置分配页面
	private void setMaskAllocation() {
		
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(maskId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		//字典数据
		if(type.equals(DictUtils.getDictValue(Global.MOTOR_CHECK_SPOT_ITEM_1, "bussinessType", "1"))) {
			//模板表1 发动机点检单一
			setMotorCheckSpotItem1Data();//设置数据
		}else if(type.equals(DictUtils.getDictValue(Global.FITTER_CHECK_SPOT_ITEM_1, "bussinessType", "1"))) {
			setFitterCheckSpotItem1Data();
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			setSF31904CItemData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			set22TZxbyItemData();
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			setSF31904ByItemData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			setItem220TDgDjData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			setItemSf31904KcDgDjData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			setItem108t2000hByData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_MT_440, "bussinessType", "1"))) {
			setItemMt440Data();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_QG_DJ, "bussinessType", "1"))) {
			setItem220tQgDjData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_MT_4400_DJ, "bussinessType", "1"))) {
			setItemQx2bMt4400DjData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_830_BY, "bussinessType", "1"))) {
			setItemQx2b830eByData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_330_BY, "bussinessType", "1"))) {
			setItem108t330ByData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_660_BY, "bussinessType", "1"))) {
			setItem108t660ByData();
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_1000_BY, "bussinessType", "1"))) {
			setItem108t1000ByData();
		}
		setWps();//设置人员
	}
	
	// 108T卡车1000小时保养单(机械部分)
	private void setItem108t1000ByData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_108T_1000_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_108T_1000_BY);
		}else {
			setValue(ITEM_108T_1000_BY);
		}
	}
	
	// 108T卡车660小时保养单(机械部分)
	private void setItem108t660ByData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_108T_660_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_108T_660_BY);
		}else {
			setValue(ITEM_108T_660_BY);
		}
	}
	
	// 108T卡车330小时保养单(机械部分)
	private void setItem108t330ByData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_108T_330_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_108T_330_BY);
		}else {
			setValue(ITEM_108T_330_BY);
		}
	}
	
	// 汽修二班830E保养责任分区
	private void setItemQx2b830eByData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_QX2B_830_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_QX2B_830_BY);
		}else {
			setValue(ITEM_QX2B_830_BY);
		}
	}
	
	// 汽修二班MT4400保养责任分区
	private void setItemQx2bMt4400DjData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_QX2B_MT_4400_DJ_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_QX2B_830_BY);
		}else {
			setValue(ITEM_QX2B_830_BY);
		}
	}
	
	// MT4400卡车钳工周检分区
	private void setItemMt440Data() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_MT_440_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_MT_4400_ZJFQ);
		}else {
			setValue(ITEM_MT_4400_ZJFQ);
		}
	}
	
	// 220T卡车钳工点检分区
	private void setItem220tQgDjData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_220T_QG_DJ_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_220T_QG_DJ);
		}else {
			setValue(ITEM_220T_QG_DJ);
		}
	}
	
	// 108T卡车2000H及以上级别保养单(机械部分)
	private void setItem108t2000hByData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ITEM_108T_2000H_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_108T_2000H_BY);
		}else {
			setValue(ITEM_108T_2000H_BY);
		}
	}
	
	// SF31904卡车电工周点检卡（电气部分）
	private void setItemSf31904KcDgDjData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.SF31904_KC_DG_DJ));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_SF31904_KC_DG_DJ);
		}else {
			setValue(ITEM_SF31904_KC_DG_DJ);
		}
	}
	
	//220T卡车电工周点检卡（电气部分）
	private void setItem220TDgDjData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.DG_DJ_220T_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_220T_DG_DJ);
		}else {
			setValue(ITEM_220T_DG_DJ);
		}
	}
	
	//SF31904卡车保养单（电气部分）
	private void setSF31904ByItemData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.SF31904_BY_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_SF31904C_BY_ITEM);
		}else {
			setValue(SF31904C_BY_ITEM);
		}
	}
	
	//220T自卸卡车保养单（电气部分）
	private void set22TZxbyItemData() {
		model.addAttribute("parts", DictUtils.getDictList(Global.ZX_BY_220T_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_ITEM_220T_ZX_BY);
		}else {
			setValue(ITEM_220T_ZX_BY);
		}
	}
	
	//模板表 SF31904C卡车点检卡
	private void setSF31904CItemData() {
		model.addAttribute("sfccs", DictUtils.getDictList(Global.SF31904_CS_DICT));
		model.addAttribute("maskId",maskId);
		if(isWx()) {
			setValue(WX_SF31904C_CS_ITEM);
		}else {
			setValue(SF31904C_CS_ITEM);
		}
	}

	//模板表 发动机点检单一
	private void setMotorCheckSpotItem1Data() {
		model.addAttribute("mcsis", DictUtils.getDictList("motorCsItem1"));
		model.addAttribute("maskId",maskId);
		setValue(MOTOR_CHECK_SPOT_ITEM_1);
	}
	
	//模板表 汽修车间220T卡车钳工周检点检卡
	private void setFitterCheckSpotItem1Data() {
		model.addAttribute("fcsis", DictUtils.getDictList("fitterCsItem1"));
		model.addAttribute("maskId",maskId);
		setValue(FITTER_CHECK_SPOT_ITEM_1);
	}
	
	//设置人员
	private void setWps() {
		User user = UserUtils.findByEmpNo(empNo);
		if(user.isAdmin()) {
			model.addAttribute("wp",findWpByCid());
		}else {
			model.addAttribute("wp", findWpByCid());
		}
	}
	
	//查询本班级人员
	private List<WorkPerson> findWpByCid() {
		WorkPerson queryClass = new WorkPerson();
		queryClass.setNo(empNo);
		WorkPerson wp = workPersonDao.findByEmpNo(empNo);
		String classId = wp.getWorkClassId();
		WorkPerson queryWp = new WorkPerson();
		queryWp.setWorkClassId(classId);
		return workPersonDao.findList(queryWp);
	}
	
	//先看看是不是班长
	private boolean isMonitor() {
		User user = null;
		if(isWx()) {
			user = UserUtils.findByEmpNo(empNo);
		}else {
			user = UserUtils.getUser();
		}
		if(user.isAdmin()) {
			return true;//如果是管理员直接跳过
		}
		String empNo = user.getEmpNo();
		WorkPerson person = workPersonDao.findByEmpNo(empNo);
		String level = person.getLevel();
		String dictLevel = DictUtils.getDictValue("班长", "workPersonLevel", "1");
		if(dictLevel.equals(level)) {
			return true;
		}
		return false;
	}
	
	
	public BusinessAssembleDao getBusinessAssembleDao() {
		return businessAssembleDao;
	}

	public void setBusinessAssembleDao(BusinessAssembleDao businessAssembleDao) {
		this.businessAssembleDao = businessAssembleDao;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getMaskId() {
		return maskId;
	}

	public void setMaskId(String maskId) {
		this.maskId = maskId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public WorkPersonDao getWorkPersonDao() {
		return workPersonDao;
	}

	public void setWorkPersonDao(WorkPersonDao workPersonDao) {
		this.workPersonDao = workPersonDao;
	}

	public WorkShopMaskDao getWorkShopMaskDao() {
		return workShopMaskDao;
	}

	public void setWorkShopMaskDao(WorkShopMaskDao workShopMaskDao) {
		this.workShopMaskDao = workShopMaskDao;
	}

	public WsMaskWcDao getWsMaskWcDao() {
		return wsMaskWcDao;
	}

	public void setWsMaskWcDao(WsMaskWcDao wsMaskWcDao) {
		this.wsMaskWcDao = wsMaskWcDao;
	}


	public MotorCheckSpotItem1Dao getMotorCheckSpotItem1Daol() {
		return motorCheckSpotItem1Daol;
	}

	public void setMotorCheckSpotItem1Daol(MotorCheckSpotItem1Dao motorCheckSpotItem1Daol) {
		this.motorCheckSpotItem1Daol = motorCheckSpotItem1Daol;
	}

	public boolean isWx() {
		return isWx;
	}

	public void setWx(boolean isWx) {
		this.isWx = isWx;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
}
