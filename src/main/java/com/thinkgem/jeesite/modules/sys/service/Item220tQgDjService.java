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
import com.thinkgem.jeesite.modules.sys.entity.Item220tQgDj;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.Item220tQgDjDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 220T卡车钳工点检分区Service
 * @author wzy
 * @version 2018-02-28
 */
@Service
@Transactional(readOnly = true)
public class Item220tQgDjService extends CrudService<Item220tQgDjDao, Item220tQgDj> {

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
		if(!type.equals(DictUtils.getDictValue(Global.ITEM_220T_QG_DJ, "bussinessType", "1"))) {
			//220T卡车钳工点检分区
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
			Item220tQgDj queryMcsi1 = new Item220tQgDj();
			queryMcsi1.setPart(part);
			queryMcsi1.setAssembleId(bussinessAssembleId);
			List<Item220tQgDj> mcsi1List = dao.findList(queryMcsi1);
			
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
			
			for(Item220tQgDj forEntity :mcsi1List) {
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
	
	
	public Item220tQgDj get(String id) {
		return super.get(id);
	}
	
	public List<Item220tQgDj> findList(Item220tQgDj item220tQgDj) {
		return super.findList(item220tQgDj);
	}
	
	public Page<Item220tQgDj> findPage(Page<Item220tQgDj> page, Item220tQgDj item220tQgDj) {
		return super.findPage(page, item220tQgDj);
	}
	
	@Transactional(readOnly = false)
	public void save(Item220tQgDj item220tQgDj) {
		super.save(item220tQgDj);
	}
	
	@Transactional(readOnly = false)
	public void delete(Item220tQgDj item220tQgDj) {
		super.delete(item220tQgDj);
	}
	
}