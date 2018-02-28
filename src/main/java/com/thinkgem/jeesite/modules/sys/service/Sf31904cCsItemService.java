package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904cCsItem;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.Sf31904cCsItemDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * SF31904C卡车点检卡Service
 * @author wzy
 * @version 2018-02-14
 */
@Service
@Transactional(readOnly = true)
public class Sf31904cCsItemService extends CrudService<Sf31904cCsItemDao, Sf31904cCsItem> {

	
	@Autowired
	private WorkPersonDao workPersonDao;
	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	@Autowired
	private BusinessAssembleDao businessAssembleDao;
	@Autowired
	private MaskMainPersonDao maskMainPersonDao;
	@Autowired
	private MaskSinglePersonDao maskSinglePersonDao;
	@Autowired
	private MaskContentDao maskContentDao;
	
	public Sf31904cCsItem get(String id) {
		return super.get(id);
	}
	
	public List<Sf31904cCsItem> findList(Sf31904cCsItem sf31904cCsItem) {
		return super.findList(sf31904cCsItem);
	}
	
	public Page<Sf31904cCsItem> findPage(Page<Sf31904cCsItem> page, Sf31904cCsItem sf31904cCsItem) {
		return super.findPage(page, sf31904cCsItem);
	}
	
	@Transactional(readOnly = false)
	public void save(Sf31904cCsItem sf31904cCsItem) {
		super.save(sf31904cCsItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sf31904cCsItem sf31904cCsItem) {
		super.delete(sf31904cCsItem);
	}
	
	//获取今天的任务id
	private String getMaskId(String workShopMaskId) {
		String maskId = null;
		try {
			WsMaskWc query = new WsMaskWc();
			query.setWorkShopMaskId(workShopMaskId);
			String dateParam = CasUtils.convertDate2YMDString(new Date());
			Date date = null;
			date = CasUtils.convertString2YMDDate(dateParam);
			String beginDate = CasUtils.convertDate2HMSString(Date2Utils.getDayStartTime(date));
			String endDate = CasUtils.convertDate2HMSString(Date2Utils.getDayEndTime(date));
			query.setBeginQueryDate(beginDate);
			query.setEndQueryDate(endDate);
			List<WsMaskWc> exitList = wsMaskWcDao.findList(query);
			WsMaskWc wsMaskWc = exitList.get(0);//今天只有一个任务 如果没有或者过多 均为异常情况
			maskId = wsMaskWc.getId();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return maskId;
	}
	
	/**
	 * 生成任务
	 * @param ViewMcsi1
	 */
	@Transactional(readOnly = false)
	public void createMask(ViewMcsi1[] viewMcsi1s,User user) {
		//获取总任务号
		String workShopMaskId = viewMcsi1s[0].getMaskId();
		String maskId = getMaskId(workShopMaskId);
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		//字典数据检验
		if(!type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			//不是SF31904C卡车点检卡
			return;
		}
		 
		String empNo = user.getEmpNo();
		WorkPerson wp = workPersonDao.findByEmpNo(empNo);//员工
		//提交状态
		String noSubmit = DictUtils.getDictValue("否", "yes_no", "0");
		//有无问题
		String noProblem = DictUtils.getDictValue("没有", "have_no", "1");
		
		//主负责人
		MaskMainPerson maskMainPerson = new MaskMainPerson();
		String maskMainPersonId = IdGen.uuid();
		maskMainPerson.setId(maskMainPersonId);
		maskMainPerson.setWsMaskWcId(maskId);
		maskMainPerson.setWorkPersonId(wp.getId());
		maskMainPerson.setSubmitState(noSubmit);
		maskMainPerson.setCreateBy(user);
		maskMainPerson.setCreateDate(new Date());
		maskMainPerson.setUpdateBy(user);
		maskMainPerson.setUpdateDate(new Date());
		maskMainPersonDao.insert(maskMainPerson);
		
		for(ViewMcsi1 viewMsci1 : viewMcsi1s) {
			
			
			
			String singleEmpNo = viewMsci1.getEmpno();
			String part = viewMsci1.getName();//部位
			
			//依据任务集和部位号 查询个人需要操作的行项
			Sf31904cCsItem queryMcsi1 = new Sf31904cCsItem();
			queryMcsi1.setPart(part);
			queryMcsi1.setAssembleId(bussinessAssembleId);
			List<Sf31904cCsItem> mcsi1List = dao.findList(queryMcsi1);
			
			WorkPerson swp = workPersonDao.findByEmpNo(singleEmpNo);//员工
			String singlePersonId = IdGen.uuid();
			//各负责人
			MaskSinglePerson maskSinglePerson = new MaskSinglePerson();
			maskSinglePerson.setId(singlePersonId);
			maskSinglePerson.setWorkPersonId(swp.getId());
			maskSinglePerson.setMmpId(maskMainPersonId);
			maskSinglePerson.setPart(part);
			maskSinglePerson.setSubmitState(noSubmit);
			maskSinglePerson.setCreateBy(user);
			maskSinglePerson.setCreateDate(new Date());
			maskSinglePerson.setUpdateBy(user);
			maskSinglePerson.setUpdateDate(new Date());
			maskSinglePersonDao.insert(maskSinglePerson);
			
			for(Sf31904cCsItem forEntity :mcsi1List) {
				//每个人关联的任务保存
				MaskContent mcEntity= new MaskContent();
				String mcEntityId = IdGen.uuid();
				mcEntity.setId(mcEntityId);
				mcEntity.setMspId(singlePersonId);
				mcEntity.setTemplateId(forEntity.getId());
				mcEntity.setProblem(noProblem);
				mcEntity.setCreateBy(user);
				mcEntity.setCreateDate(new Date());
				mcEntity.setUpdateBy(user);
				mcEntity.setUpdateDate(new Date());
				maskContentDao.insert(mcEntity);
			}
		}
	}
	
}