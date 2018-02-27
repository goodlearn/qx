package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMaskDesc;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 个人负责人任务表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskSinglePersonService extends CrudService<MaskSinglePersonDao, MaskSinglePerson> {

	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	
	@Autowired
	private BusinessAssembleDao businessAssembleDao;
	
	@Autowired
	private MaskContentDao maskContentDao;
	
	public MaskSinglePerson get(String id) {
		return super.get(id);
	}
	
	public List<MaskSinglePerson> findList(MaskSinglePerson maskSinglePerson) {
		return super.findList(maskSinglePerson);
	}
	
	public Page<MaskSinglePerson> findPage(Page<MaskSinglePerson> page, MaskSinglePerson maskSinglePerson) {
		return super.findPage(page, maskSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskSinglePerson maskSinglePerson) {
		super.save(maskSinglePerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskSinglePerson maskSinglePerson) {
		super.delete(maskSinglePerson);
	}
	
	//查找未提交的
	public MaskSinglePerson findSubmitById(String id) {
		MaskSinglePerson queryMsp = dao.get(id);
		String submitState  = queryMsp.getSubmitState();
		String yesSubmit = DictUtils.getDictValue("是", "yes_no", "0");
		if(yesSubmit.equals(submitState)) {
			return queryMsp;
		}
		return queryMsp;
	}
	
	/**
	 * 根据任务模板和部位序号找到部位名称
	 */
	public void setPartNameForList(List<MaskSinglePerson> msps,String wsmId) {
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(wsmId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_CS_DICT, "");
				msp.setPartName(partName);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.ZX_BY_220T_DICT, "");
				msp.setPartName(partName);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_BY_DICT, "");
				msp.setPartName(partName);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.DG_DJ_220T_DICT, "");
				msp.setPartName(partName);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_KC_DG_DJ, "");
				msp.setPartName(partName);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			for(MaskSinglePerson msp : msps) {
				String partName = DictUtils.getDictLabel(msp.getPart(), Global.ITEM_108T_2000H_BY_DICT, "");
				msp.setPartName(partName);
			}
		}
	}
	
	/**
	 * 根据任务模板和部位序号找到部位名称
	 */
	public void setPartName(MaskSinglePerson msp,String wsmId) {
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(wsmId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_CS_DICT, "");
			msp.setPartName(partName);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.ZX_BY_220T_DICT, "");
			msp.setPartName(partName);
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_BY_DICT, "");
			msp.setPartName(partName);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.DG_DJ_220T_DICT, "");
			msp.setPartName(partName);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.SF31904_KC_DG_DJ, "");
			msp.setPartName(partName);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			String partName = DictUtils.getDictLabel(msp.getPart(), Global.ITEM_108T_2000H_BY_DICT, "");
			msp.setPartName(partName);
		}
	}
	
	//单个人提交任务
	@Transactional(readOnly = false)
	public void submitSingleMask(User user,String submitMspId,List<ViewMaskDesc> params,List<String> remarks) {
		MaskSinglePerson msp = dao.get(submitMspId);
		String yesSubmit = DictUtils.getDictValue("是", "yes_no", "0");
		msp.setSubmitState(yesSubmit);
		msp.setUpdateBy(user);
		msp.setUpdateDate(new Date());
		dao.update(msp);
		
		
		MaskContent queryMc = new MaskContent();
		queryMc.setMspId(submitMspId);
		List<MaskContent> mcList = maskContentDao.findList(queryMc);
		if(null!=params && params.size() > 0) {
			submitContentUpdate(user,params,mcList);//循环更新
		}
		
		//有无问题
		String haveProblem = DictUtils.getDictValue("有", "have_no", "1");
		//看是否有新的内容
		if(null !=remarks && remarks.size() > 0) {
			//记录新内容
			for(String remark :remarks) {
				MaskContent mcEntity= new MaskContent();
				String mcEntityId = IdGen.uuid();
				mcEntity.setId(mcEntityId);
				mcEntity.setMspId(submitMspId);
				mcEntity.setTemplateId(null);
				mcEntity.setProblem(haveProblem);
				mcEntity.setCreateBy(user);
				mcEntity.setCreateDate(new Date());
				mcEntity.setUpdateBy(user);
				mcEntity.setUpdateDate(new Date());
				mcEntity.setRemarks(remark);
				maskContentDao.insert(mcEntity);
			}
		}
	}
	
	//循环更新
	@Transactional(readOnly = false)
	private void submitContentUpdate(User user,List<ViewMaskDesc> params,List<MaskContent> mcList) {
		String haveProblem = DictUtils.getDictValue("有", "have_no", "1");
		for(ViewMaskDesc forVmd : params) {
			String mcId = forVmd.getProbTxt();
			String mcRemarks = forVmd.getProbDesc();
			for(MaskContent forMc : mcList) {
				String queryMcId = forMc.getId();
				if(queryMcId.equals(mcId)) {
					forMc.setProblem(haveProblem);
					forMc.setRemarks(mcRemarks);
					forMc.setUpdateBy(user);
					forMc.setUpdateDate(new Date());
					maskContentDao.update(forMc);
				}
			}
		}
	}
	
}