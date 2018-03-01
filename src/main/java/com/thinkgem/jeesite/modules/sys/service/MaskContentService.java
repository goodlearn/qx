package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Item108t1000By;
import com.thinkgem.jeesite.modules.sys.entity.Item108t2000hBy;
import com.thinkgem.jeesite.modules.sys.entity.Item108t330By;
import com.thinkgem.jeesite.modules.sys.entity.Item108t660By;
import com.thinkgem.jeesite.modules.sys.entity.Item220DgDj;
import com.thinkgem.jeesite.modules.sys.entity.Item220tQgDj;
import com.thinkgem.jeesite.modules.sys.entity.Item220tZxBy;
import com.thinkgem.jeesite.modules.sys.entity.ItemMt440Zjfq;
import com.thinkgem.jeesite.modules.sys.entity.ItemQx2b830eBy;
import com.thinkgem.jeesite.modules.sys.entity.ItemQx2bMt4400Dj;
import com.thinkgem.jeesite.modules.sys.entity.ItemSf31904KcDgDj;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904ByItem;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904cCsItem;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.view.TemplateContent;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.Item108t1000ByDao;
import com.thinkgem.jeesite.modules.sys.dao.Item108t2000hByDao;
import com.thinkgem.jeesite.modules.sys.dao.Item108t330ByDao;
import com.thinkgem.jeesite.modules.sys.dao.Item108t660ByDao;
import com.thinkgem.jeesite.modules.sys.dao.Item220DgDjDao;
import com.thinkgem.jeesite.modules.sys.dao.Item220tQgDjDao;
import com.thinkgem.jeesite.modules.sys.dao.Item220tZxByDao;
import com.thinkgem.jeesite.modules.sys.dao.ItemMt440ZjfqDao;
import com.thinkgem.jeesite.modules.sys.dao.ItemQx2b830eByDao;
import com.thinkgem.jeesite.modules.sys.dao.ItemQx2bMt4400DjDao;
import com.thinkgem.jeesite.modules.sys.dao.ItemSf31904KcDgDjDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.Sf31904ByItemDao;
import com.thinkgem.jeesite.modules.sys.dao.Sf31904cCsItemDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;

/**
 * 任务内容表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskContentService extends CrudService<MaskContentDao, MaskContent> {


	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	
	@Autowired
	private WsMaskWcDao wsMaskWcDao;
	

	@Autowired
	private BusinessAssembleDao businessAssembleDao;
	
	
	@Autowired
	private Sf31904cCsItemDao sf31904cCsItemService;
	@Autowired
	private Item220tZxByDao item220tZxByService;
	@Autowired
	private Sf31904ByItemDao sf31904ByItemService;
	@Autowired
	private Item220DgDjDao item220DgDjService;
	@Autowired
	private ItemSf31904KcDgDjDao itemSf31904KcDgDjService;
	@Autowired
	private Item108t2000hByDao item108t2000hByService;
	@Autowired
	private ItemMt440ZjfqDao itemMt440ZjfqService;
	@Autowired
	private Item220tQgDjDao item220tQgDjService;
	@Autowired
	private ItemQx2bMt4400DjDao itemQx2bMt4400DjService;
	@Autowired
	private ItemQx2b830eByDao itemQx2b830eByService;
	@Autowired
	private Item108t330ByDao item108t330ByService;
	@Autowired
	private Item108t660ByDao item108t660ByService;
	@Autowired
	private Item108t1000ByDao item108t1000ByService;
	
	
	

	//获取任务模板类型
	public String findBaType(String wsMaskWcId) {
		//依据任务号找到车间任务号
		WsMaskWc wsMaskWc = wsMaskWcDao.get(wsMaskWcId);
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskDao.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleDao.get(bussinessAssembleId);
		//找到类型
		return businessAssemble.getType();
		
	}
	/**
	 * 根据任务号填充模板信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public void setTemplateContent(String wsMaskWcId,MaskContent maskContent) {
		String type = findBaType(wsMaskWcId);
		String templateId = maskContent.getTemplateId();
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			if(null!=templateId) {
				Sf31904cCsItem sf31904cCsItem = sf31904cCsItemService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(sf31904cCsItem.getItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item220tZxBy item220tZxBy = item220tZxByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item220tZxBy.getItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			if(null!=templateId) {
				Sf31904ByItem sf31904ByItem = sf31904ByItemService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(sf31904ByItem.getItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item220DgDj item220DgDj = item220DgDjService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item220DgDj.getCheckStandard());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			if(null!=templateId) {
				ItemSf31904KcDgDj itemSf31904KcDgDj = itemSf31904KcDgDjService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(itemSf31904KcDgDj.getCheckStandard());
				maskContent.setTc(templateContent);
			}
			
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item108t2000hBy item108t2000hBy = item108t2000hByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item108t2000hBy.getByItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_MT_440, "bussinessType", "1"))) {
			if(null!=templateId) {
				ItemMt440Zjfq itemMt440Zjfq = itemMt440ZjfqService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(itemMt440Zjfq.getCheckContent());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_QG_DJ, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item220tQgDj item220tQgDj = item220tQgDjService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item220tQgDj.getCheckContent());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_MT_4400_DJ, "bussinessType", "1"))) {
			if(null!=templateId) {
				ItemQx2bMt4400Dj itemQx2bMt4400Dj = itemQx2bMt4400DjService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(itemQx2bMt4400Dj.getCheckContent());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_830_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				ItemQx2b830eBy itemQx2b830eBy = itemQx2b830eByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(itemQx2b830eBy.getCheckContent());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_330_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item108t330By item108t330By = item108t330ByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item108t330By.getByItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_660_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item108t660By item108t660By = item108t660ByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item108t660By.getByItem());
				maskContent.setTc(templateContent);
			}
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_1000_BY, "bussinessType", "1"))) {
			if(null!=templateId) {
				Item108t1000By item108t1000By = item108t1000ByService.get(templateId);
				TemplateContent templateContent = new TemplateContent();
				templateContent.setItem(item108t1000By.getByItem());
				maskContent.setTc(templateContent);
			}
		}
		
	}
	
	public MaskContent get(String id) {
		return super.get(id);
	}
	
	public List<MaskContent> findList(MaskContent maskContent) {
		return super.findList(maskContent);
	}
	
	public Page<MaskContent> findPage(Page<MaskContent> page, MaskContent maskContent) {
		return super.findPage(page, maskContent);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskContent maskContent) {
		super.save(maskContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskContent maskContent) {
		super.delete(maskContent);
	}

	//依据maskSinglePersonId查询任务
	public List<MaskContent> findMsListByMspId(String mspId){
		MaskContent query = new MaskContent();
		query.setMspId(mspId);
		return findList(query);
	}
}