package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.BasePathUtils;
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
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904ByItem;
import com.thinkgem.jeesite.modules.sys.entity.Sf31904cCsItem;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.maskdispatch.MdControl;
import com.thinkgem.jeesite.modules.sys.service.BusinessAssembleService;
import com.thinkgem.jeesite.modules.sys.service.Item108t1000ByService;
import com.thinkgem.jeesite.modules.sys.service.Item108t2000hByService;
import com.thinkgem.jeesite.modules.sys.service.Item108t330ByService;
import com.thinkgem.jeesite.modules.sys.service.Item108t660ByService;
import com.thinkgem.jeesite.modules.sys.service.Item220DgDjService;
import com.thinkgem.jeesite.modules.sys.service.Item220tQgDjService;
import com.thinkgem.jeesite.modules.sys.service.Item220tZxByService;
import com.thinkgem.jeesite.modules.sys.service.ItemMt440ZjfqService;
import com.thinkgem.jeesite.modules.sys.service.ItemQx2b830eByService;
import com.thinkgem.jeesite.modules.sys.service.ItemQx2bMt4400DjService;
import com.thinkgem.jeesite.modules.sys.service.ItemSf31904KcDgDjService;
import com.thinkgem.jeesite.modules.sys.service.MaskContentService;
import com.thinkgem.jeesite.modules.sys.service.MaskDispatchService;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.Sf31904ByItemService;
import com.thinkgem.jeesite.modules.sys.service.Sf31904cCsItemService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.view.TemplateContent;
import com.thinkgem.jeesite.modules.sys.view.ViewMaskDesc;
import com.thinkgem.jeesite.modules.sys.view.ViewMaskSubmit;
import com.thinkgem.jeesite.modules.sys.view.ViewMcsi1;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import net.sf.json.JSONArray;
@Controller
@RequestMapping(value = "wmw")
public class WxWmwController extends WxBaseController{
	
	@Autowired
	private WxService wxService;

	@Autowired
	private WsMaskWcService wsMaskWcService;
	
	@Autowired
	private WorkShopMaskService workShopMaskService;
	
	@Autowired
	private MaskDispatchService maskDispatchService;
	
	@Autowired
	private WorkPersonService workPersonService;
	
	@Autowired
	private BusinessAssembleService businessAssembleService;
	
	@Autowired
	private MaskContentService maskContentService;
	
	@Autowired
	private MaskMainPersonService maskMainPersonService;
	
	@Autowired
	private MaskSinglePersonService maskSinglePersonService;
	
	
	@Autowired
	private Sf31904cCsItemService sf31904cCsItemService;
	@Autowired
	private Item220tZxByService item220tZxByService;
	@Autowired
	private Sf31904ByItemService sf31904ByItemService;
	@Autowired
	private Item220DgDjService item220DgDjService;
	@Autowired
	private ItemSf31904KcDgDjService itemSf31904KcDgDjService;
	@Autowired
	private Item108t2000hByService item108t2000hByService;
	@Autowired
	private ItemMt440ZjfqService itemMt440ZjfqService;
	@Autowired
	private Item220tQgDjService item220tQgDjService;
	@Autowired
	private ItemQx2bMt4400DjService itemQx2bMt4400DjService;
	@Autowired
	private ItemQx2b830eByService itemQx2b830eByService;
	@Autowired
	private Item108t330ByService item108t330ByService;
	@Autowired
	private Item108t660ByService item108t660ByService;
	@Autowired
	private Item108t1000ByService item108t1000ByService;
	
	//提交任务
	@RequestMapping(value = "utSubmit",method = RequestMethod.POST)
	@ResponseBody
	public String utSubmit(@RequestBody ViewMaskSubmit viewMaskSubmit, HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
			openId = (String)model.asMap().get("openId");
		}
		
		//任务号不存在
		String mspId = viewMaskSubmit.getSubmitMspId();
		if(null == mspId) {
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		//不存在该任务
		MaskSinglePerson msp = maskSinglePersonService.get(mspId);
		if(null == msp) {
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		String mmpId = msp.getMmpId();
		MaskMainPerson mmp = maskMainPersonService.get(mmpId);
		String yes = DictUtils.getDictValue("是", "yes_no", "1");
		String submit = mmp.getSubmitState();
		if(yes.equals(submit)) {
			return backJsonWithCode(errCode,ERR_MSP_SUBMIT_TOTAL);
		}
		
	    String empNo = findEmpNo(openId);
	    User user = UserUtils.findByEmpNo(empNo);
		JSONArray jsonOther = JSONArray.fromObject(viewMaskSubmit.getOtherdata());
		List<String> otherData = (List<String>)JSONArray.toCollection(jsonOther, String.class);
		JSONArray jsonCheck = JSONArray.fromObject(viewMaskSubmit.getCheckdata());
		List<ViewMaskDesc> checkData = (List<ViewMaskDesc>)JSONArray.toCollection(jsonCheck, ViewMaskDesc.class);
		maskSinglePersonService.submitSingleMask(user, mspId, checkData, otherData);
		return backJsonWithCode(successCode,"");
	}
	
	//查询任务
	@RequestMapping(value = "wmwMask",method = RequestMethod.GET)
	public String wmwMask(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		WsMaskWc wsMaskWc = wsMaskWcService.get(wmwId);
		if(null == wsMaskWc) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		MaskMainPerson query = new MaskMainPerson();
		query.setWsMaskWcId(wmwId);//该任务中审核人任务列表条件
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		//没有审核任务
		if(null == mmpList) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		List<MaskSinglePerson> retList = new ArrayList<MaskSinglePerson>();
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			MaskSinglePerson queryMsp = new MaskSinglePerson();
			queryMsp.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonService.findList(queryMsp);
			if(null != mspList) {
				for(MaskSinglePerson msp : mspList) {
					String mspId = msp.getId();
					MaskContent mcQuery = new MaskContent();
					mcQuery.setMspId(mspId);
					List<MaskContent> mcList = maskContentService.findList(mcQuery);
					for(MaskContent mc : mcList) {
						maskContentService.setTemplateContent(wmwId,mc);
					}
					msp.setMcList(mcList);
					msp.setDesc(msp.addDesc());
				}
				retList.addAll(mspList);//添加该员工在改审核任务下的任务列表
			}
		}
		
		if(retList.size() == 0) {
			model.addAttribute("message",ERR_NOT_MASK_LIST);
			return WX_ERROR;
		}
		
		ViewUnFinishMask vufm = new ViewUnFinishMask();
		vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());//车间任务ID
		vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());//车间任务名称
		vufm.setWsMaskWcId(wmwId);//班组发布任务
		vufm.setMspList(retList);//存放个人列表
		maskSinglePersonService.setPartNameForList(retList, wmwId);
		model.addAttribute("maskInfo",vufm);//任务列表
		return TASK_INFO;
	}
	
	//查询任务
	@RequestMapping(value = "csp",method = RequestMethod.GET)
	public String csp(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		WsMaskWc wsMaskWc = wsMaskWcService.get(wmwId);
		if(null == wsMaskWc) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		//是否提交
		if(null != wsMaskWcService.findSubmitMask(wmwId)) {
			model.addAttribute("message",ERR_MSP_SUBMIT);
			return WX_ERROR;
		}
		
		MaskMainPerson query = new MaskMainPerson();
		query.setWsMaskWcId(wmwId);//该任务中审核人任务列表条件
		
		List<MaskMainPerson> mmpList = maskMainPersonService.findList(query);
		//没有审核任务
		if(null == mmpList) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		List<MaskSinglePerson> retList = new ArrayList<MaskSinglePerson>();
		//看有没有任务
		for(MaskMainPerson forEntity:mmpList) {
			String mmpId = forEntity.getId();
			MaskSinglePerson queryMsp = new MaskSinglePerson();
			queryMsp.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonService.findList(queryMsp);
			if(null != mspList) {
				for(MaskSinglePerson msp : mspList) {
					String mspId = msp.getId();
					MaskContent mcQuery = new MaskContent();
					mcQuery.setMspId(mspId);
					List<MaskContent> mcList = maskContentService.findList(mcQuery);
					for(MaskContent mc : mcList) {
						maskContentService.setTemplateContent(wmwId,mc);
					}
					msp.setMcList(mcList);
					msp.setDesc(msp.addDesc());
				}
				retList.addAll(mspList);//添加该员工在改审核任务下的任务列表
			}
		}
		
		if(retList.size() == 0) {
			model.addAttribute("message",ERR_NOT_MASK_LIST);
			return WX_ERROR;
		}
		
		ViewUnFinishMask vufm = new ViewUnFinishMask();
		vufm.setWorkShopMaskId(wsMaskWc.getWsm().getId());//车间任务ID
		vufm.setWorkShopMaskName(wsMaskWc.getWsm().getName());//车间任务名称
		vufm.setWsMaskWcId(wmwId);//班组发布任务
		vufm.setMspList(retList);//存放个人列表
		maskSinglePersonService.setPartNameForList(retList, wmwId);
		model.addAttribute("maskInfo",vufm);//任务列表
		return CHECK_SUBMIT;
	}
	
	
	
	//查询任务
	@RequestMapping(value = "mcList",method = RequestMethod.GET)
	public String mcList(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		String mspId = request.getParameter("mspId");
		if(null == mspId) {
			//任务不存在
			model.addAttribute("message",ERR_MSP_ID_NULL);
			return WX_ERROR;
		}
		
		List<MaskContent> mcList = maskContentService.findMsListByMspId(mspId);
		if(null == mcList || mcList.size() == 0) {
			model.addAttribute("message",ERR_MSP_LIST_NULL);
			return WX_ERROR;
		}
		MaskSinglePerson msp = maskSinglePersonService.get(mspId);
		MaskMainPerson mmp = maskMainPersonService.get(msp.getMmpId());
		WsMaskWc wmw = wsMaskWcService.get(mmp.getWsMaskWcId());
		WorkShopMask wsm = workShopMaskService.get(wmw.getWorkShopMaskId());
		maskSinglePersonService.setPartName(msp, wmw.getId());
		for(MaskContent mc : mcList) {
			maskContentService.setTemplateContent(wmw.getId(),mc);
		}
		model.addAttribute("maskName",wsm.getName());
		model.addAttribute("msp",msp);
		model.addAttribute("mcList",mcList);
		return USER_TASK;
	}
	
	
	private String headPhotoPath(){
    	StringBuilder sb;
		try {
			sb = new StringBuilder(getSavePath(Global.WMW_IMAGE_PATH));
/*			sb.append(File.separator);
			sb.append(idCard);*/
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	 }
	
	 /**
     * 返回上传文件保存的位置
     * 
     * @return
     * @throws Exception
     */
    private String getSavePath(String savePath) throws Exception {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + savePath;
    }
	
	/**
	 * 上传个人图片信息
	 */
	@ResponseBody
	@RequestMapping(value="/upImage",method=RequestMethod.POST)
	public String upImage(HttpServletRequest request, HttpServletResponse response,Model model, RedirectAttributes redirectAttributes) {
	
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String mediaId = request.getParameter("serverId");
		String wmwId = request.getParameter("wmwId");
		logger.info("mediaId:"+mediaId);
		logger.info("wmwId:"+wmwId);
		try {
			request.setCharacterEncoding("UTF-8");
			//获取用户的身份证ID
			String dirParam = headPhotoPath();
			String filePath = wxService.downloadMedia(mediaId, dirParam,wmwId);
			logger.info("filePath:"+filePath);
			String isServer = DictUtils.getDictValue("isServer", "systemControl", "0");
			String httpProtocol = DictUtils.getDictValue("httpProtocol", "systemControl", "http");
			String url = null;
			if("0".equals(isServer)) {
				url = BasePathUtils.getBasePathNoServer(request,true);
			}else {
				url = BasePathUtils.getBasePathNoServer(request,false);
			}
		    if("https".equals(httpProtocol)) {
		    	url = url.replace("http", "https");
		    }
		    String[] paths = filePath.split("/");
		    String reqUrl = url + request.getContextPath() + "/" + Global.WMW_IMAGE_PATH + "/" + paths[paths.length-1];
		    logger.info(reqUrl);
			 //上传
      	    /*File fileName = new File(dirParam,openId + ".jpeg");
      	    CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)){
				  //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator<String> iter=multiRequest.getFileNames();
                while(iter.hasNext()){
               	 MultipartFile file=multiRequest.getFile(iter.next().toString());
                 if(file!=null){
                   	   //上传
                       file.transferTo(fileName);
                 }
               }
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return backJsonWithCode("0","成功");
	}
	
	//获取任务分配数据
	@RequestMapping(value = "allocation",method= RequestMethod.POST)
	@ResponseBody
	public String allocation(@RequestBody ViewMcsi1[] viewMcsi1s,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		//根据任务获取对应的业务对象
		
		String maskId = viewMcsi1s[0].getMaskId();
		//发布任务
		WsMaskWc wsMaskWc = wsMaskWcService.releasePd(maskId,UserUtils.findByEmpNo(empNo));
		
		//设置分配人员
		String workShopMaskId = wsMaskWc.getWorkShopMaskId();
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskService.get(workShopMaskId);
		//找到业务集号
		String bussinessAssembleId = workShopMask.getBussinessAssembleId();
		//找到业务集
		BusinessAssemble businessAssemble = businessAssembleService.get(bussinessAssembleId);
		//找到类型
		String type = businessAssemble.getType();
		
		//字典数据检验
		if(type.equals(DictUtils.getDictValue(Global.SF31904C_CS_ITEM, "bussinessType", "1"))) {
			//SF31904C卡车点检卡
			sf31904cCsItemService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_ZX_BY, "bussinessType", "1"))) {
			//220T自卸卡车保养单（电气部分）
			item220tZxByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.SF31904C_BY_ITEM, "bussinessType", "1"))) {
			//SF31904卡车保养单（电气部分）
			sf31904ByItemService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_DG_DJ_BY, "bussinessType", "1"))) {
			//220T卡车电工周点检卡（电气部分）
			item220DgDjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_SF31904_KC_DG_DJ, "bussinessType", "1"))) {
			//SF31904卡车电工周点检卡（电气部分）
			itemSf31904KcDgDjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			//108T卡车2000H及以上级别保养单(机械部分)
			item108t2000hByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_MT_440, "bussinessType", "1"))) {
			//MT4400卡车钳工周检分区
			itemMt440ZjfqService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_220T_QG_DJ, "bussinessType", "1"))) {
			//220T卡车钳工点检分区
			item220tQgDjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_MT_4400_DJ, "bussinessType", "1"))) {
			//汽修二班MT4400保养责任分区
			itemQx2bMt4400DjService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_QX2B_830_BY, "bussinessType", "1"))) {
			//汽修二班830E保养责任分区
			itemQx2b830eByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_330_BY, "bussinessType", "1"))) {
			//108T卡车330小时保养单(机械部分)
			item108t330ByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_660_BY, "bussinessType", "1"))) {
			//108T卡车660小时保养单(机械部分)
			item108t660ByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}else if(type.equals(DictUtils.getDictValue(Global.ITEM_108T_1000_BY, "bussinessType", "1"))) {
			//108T卡车1000小时保养单(机械部分)
			item108t1000ByService.createMask(viewMcsi1s,UserUtils.findByEmpNo(empNo));
			return backJsonWithCode(successCode,MSG_ALLOCATION_SUCCESS);
		}
		return backJsonWithCode(errCode,ERR_NOT_MASK_SERVICE);
	}
	
	/**
	 * 任务发布
	 */
	@RequestMapping(value="/releasePd",method=RequestMethod.GET)
	@ResponseBody
	public String releasePd(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		//验证任务是否结束
		String wsmId = request.getParameter("wsmId");
		if(null == wsmId) {
			//任务号不存在
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		if(null == workShopMaskService.get(wsmId)) {
			//任务不存在
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String validateMsg = wsMaskWcService.validateReleasePd(wsmId);
		if(null != validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,validateMsg);
		}
		
		return backJsonWithCode(successCode,null);
	}
	
	/**
	 * 任务发布
	 */
	@RequestMapping(value="/submitMask",method=RequestMethod.POST)
	@ResponseBody
	public String submitMask(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		//验证任务是否结束
		String wmwId = request.getParameter("wmwId");
		if(null == wmwId) {
			//任务号不存在
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		if(null == wsMaskWcService.validateWsmId(wmwId)) {
			//任务不存在
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String validateMsg = wsMaskWcService.validateSubmitState(wmwId);
		if(null == validateMsg) {
			//任务还未结束
			return backJsonWithCode(errCode,ERR_MASK_SUBMIT_NULL);
		}
		//发布任务
		WsMaskWc dealWmw = wsMaskWcService.get(wmwId);
		wsMaskWcService.submitMask(dealWmw,UserUtils.findByEmpNo(empNo));
		return backJsonWithCode(successCode,wmwId);
	}
	
	
	/**
	 * 页面跳转-分配页面
	 */
	@RequestMapping(value="/pallocation",method=RequestMethod.GET)
	public String pallocation(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
		}
		String maskId = request.getParameter("maskId");
		if(null == maskId) {
			//任务号不存在
			model.addAttribute("message",ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		if(null == workShopMaskService.get(maskId)) {
			//任务不存在
			model.addAttribute("message",ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		String empNo = findEmpNo(openId);
		if(null == empNo) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		if(null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message",ERR_WP_NULL);
			return WX_ERROR;
		}
		
		String validateMsg = wsMaskWcService.validateReleasePd(maskId);
		if(null != validateMsg) {
			//任务还未结束
			model.addAttribute("message",validateMsg);
			return WX_ERROR;
		}
	
		//分配页面
		MdControl stateParam = maskDispatchService.pcMaskDispatch(maskId,model,true,empNo);
		//找到车间任务
		WorkShopMask workShopMask = workShopMaskService.get(maskId);
		model.addAttribute("wsmName",workShopMask.getName());
		return stateParam.getValue();
	}
	
}
