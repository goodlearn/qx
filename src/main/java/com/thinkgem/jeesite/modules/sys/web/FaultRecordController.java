package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.entity.FaultRecord;
import com.thinkgem.jeesite.modules.sys.service.FaultRecordService;

/**
 * 故障记录表Controller
 * @author wzy
 * @version 2018-02-08
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/faultRecord")
public class FaultRecordController extends BaseController {

	@Autowired
	private FaultRecordService faultRecordService;
	
	@ModelAttribute
	public FaultRecord get(@RequestParam(required=false) String id) {
		FaultRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = faultRecordService.get(id);
		}
		if (entity == null){
			entity = new FaultRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:faultRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(FaultRecord faultRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FaultRecord> page = faultRecordService.findPage(new Page<FaultRecord>(request, response), faultRecord); 
		model.addAttribute("page", page);
		return "modules/faultrecord/faultRecordList";
	}

	@RequiresPermissions("sys:faultRecord:view")
	@RequestMapping(value = "form")
	public String form(FaultRecord faultRecord, Model model) {
		model.addAttribute("faultRecord", faultRecord);
		return "modules/faultrecord/faultRecordForm";
	}

	@RequiresPermissions("sys:faultRecord:edit")
	@RequestMapping(value = "save")
	public String save(FaultRecord faultRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, faultRecord)){
			return form(faultRecord, model);
		}
		faultRecordService.save(faultRecord);
		addMessage(redirectAttributes, "保存故障记录表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/faultRecord/?repage";
	}
	
	@RequiresPermissions("sys:faultRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(FaultRecord faultRecord, RedirectAttributes redirectAttributes) {
		faultRecordService.delete(faultRecord);
		addMessage(redirectAttributes, "删除故障记录表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/faultRecord/?repage";
	}

	/**
	 * 导出数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:faultRecord:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FaultRecord faultRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "故障记录数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FaultRecord> page = faultRecordService.findPage(new Page<FaultRecord>(request, response, -1), faultRecord);
    		new ExportExcel("故障记录数据", FaultRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/faultRecord/?repage";
    }
}