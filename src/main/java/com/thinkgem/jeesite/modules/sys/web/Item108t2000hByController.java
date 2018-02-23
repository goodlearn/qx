package com.thinkgem.jeesite.modules.sys.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Item108t2000hBy;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.excel.item108t2000hBy.Item108t2000hByExcel;
import com.thinkgem.jeesite.modules.sys.service.Item108t2000hByService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;

/**
 * 108T卡车2000H及以上级别保养单(机械部分)Controller
 * @author wzy
 * @version 2018-02-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/item108t2000hBy")
public class Item108t2000hByController extends BaseController {

	@Autowired
	private Item108t2000hByService item108t2000hByService;
	
	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	@Autowired
	private Item108t2000hByExcel item108t2000hByExcel;
	
	//信息
	private final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	
	//成功
	private final String SUC_CODE = "0";
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= {RequestMethod.POST})
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s) {
		item108t2000hByService.createMask(viewMcsi1s,UserUtils.getUser());
		return backJsonWithCode(SUC_CODE,MSG_ALLOCATION_SUCCESS);
	}
	
	@RequiresPermissions("sys:item108t2000hBy:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
	public String exportFile(WsMaskWc wsMaskWc, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Date endDate = wsMaskWc.getEndDate();
			if(null == endDate) {
				addMessage(redirectAttributes, "请选择日期");
				return "redirect:"+Global.getAdminPath()+"/sys/item108t2000hBy/listWmw";
			}
			
			//设置查询条件
			wsMaskWc = new WsMaskWc();
			String type = DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1");
			WorkShopMask wsm = new WorkShopMask();
			BusinessAssemble ba = new BusinessAssemble();
			ba.setType(type);
			wsm.setBa(ba);
			wsMaskWc.setWsm(wsm);
			wsMaskWc.setEndDate(endDate);
			String dateParam = CasUtils.convertDate2YMDString(endDate);
			Date date = null;
			date = CasUtils.convertString2YMDDate(dateParam);
			String beginDate = CasUtils.convertDate2HMSString(Date2Utils.getDayStartTime(date));
			String queryEndDate = CasUtils.convertDate2HMSString(Date2Utils.getDayEndTime(date));
			wsMaskWc.setBeginQueryDate(beginDate);
			wsMaskWc.setEndQueryDate(queryEndDate);
			//查询数据
			Page<WsMaskWc> page = wsMaskWcService.findTypeListPage(new Page<WsMaskWc>(request, response), wsMaskWc); 
			if(null == page.getList() || page.getList().size() == 0) {
				addMessage(redirectAttributes, "没有对应数据");
				return "redirect:"+Global.getAdminPath()+"/sys/item108t2000hBy/listWmw";
			}
			
			//只有一条数据
			item108t2000hByExcel.createExcel(response,page.getList().get(0));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/sys/item108t2000hBy/listWmw";
	}
	
	@RequiresPermissions("sys:wsMaskWc:view")
	@RequestMapping(value = {"listWmw"})
	public String listWmw(WsMaskWc wsMaskWc,HttpServletRequest request, HttpServletResponse response, Model model) {
		wsMaskWc = new WsMaskWc();
		String type = DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1");
		WorkShopMask wsm = new WorkShopMask();
		BusinessAssemble ba = new BusinessAssemble();
		ba.setType(type);
		wsm.setBa(ba);
		wsMaskWc.setWsm(wsm);
		Page<WsMaskWc> page = wsMaskWcService.findTypeListPage(new Page<WsMaskWc>(request, response), wsMaskWc); 
		model.addAttribute("page", page);
		return "modules/item108t2000hBy/item108t2000hByWmwList";
	}
	
	@ModelAttribute
	public Item108t2000hBy get(@RequestParam(required=false) String id) {
		Item108t2000hBy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = item108t2000hByService.get(id);
		}
		if (entity == null){
			entity = new Item108t2000hBy();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:item108t2000hBy:view")
	@RequestMapping(value = {"list", ""})
	public String list(Item108t2000hBy item108t2000hBy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Item108t2000hBy> page = item108t2000hByService.findPage(new Page<Item108t2000hBy>(request, response), item108t2000hBy); 
		model.addAttribute("page", page);
		return "modules/item108t2000hBy/item108t2000hByList";
	}

	@RequiresPermissions("sys:item108t2000hBy:view")
	@RequestMapping(value = "form")
	public String form(Item108t2000hBy item108t2000hBy, Model model) {
		model.addAttribute("item108t2000hBy", item108t2000hBy);
		return "modules/item108t2000hBy/item108t2000hByForm";
	}

	@RequiresPermissions("sys:item108t2000hBy:edit")
	@RequestMapping(value = "save")
	public String save(Item108t2000hBy item108t2000hBy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, item108t2000hBy)){
			return form(item108t2000hBy, model);
		}
		item108t2000hByService.save(item108t2000hBy);
		addMessage(redirectAttributes, "保存108T卡车2000H及以上级别保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t2000hBy/?repage";
	}
	
	@RequiresPermissions("sys:item108t2000hBy:edit")
	@RequestMapping(value = "delete")
	public String delete(Item108t2000hBy item108t2000hBy, RedirectAttributes redirectAttributes) {
		item108t2000hByService.delete(item108t2000hBy);
		addMessage(redirectAttributes, "删除108T卡车2000H及以上级别保养单(机械部分)成功");
		return "redirect:"+Global.getAdminPath()+"/sys/item108t2000hBy/?repage";
	}

}