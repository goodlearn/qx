package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.Date2Utils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.MonthMask;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWc;
import com.thinkgem.jeesite.modules.sys.entity.MonthMaskWs;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.WorkPerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.service.MaskMainPersonService;
import com.thinkgem.jeesite.modules.sys.service.MaskSinglePersonService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWcService;
import com.thinkgem.jeesite.modules.sys.service.MonthMaskWsService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.WorkPersonService;
import com.thinkgem.jeesite.modules.sys.service.WorkShopMaskService;
import com.thinkgem.jeesite.modules.sys.service.WsMaskWcService;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.view.ViewUnFinishMask;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * 首页
 * 
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "mmc")
public class WxMonthMaskController extends WxBaseController {

	@Autowired
	private WorkPersonService workPersonService;
	

	@Autowired
	private MonthMaskService monthMaskService;
	
	@Autowired
	private MonthMaskWsService monthMaskWsService;
	
	@Autowired
	private MonthMaskWcService monthMaskWcService;

	// 导航
	private final String NAVIGAION_1 = "任务执行";
	private final String NAVIGAION_2 = "任务分配";

	/**
	 * 页面跳转 -- 获取首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/indexInfo", method = RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}

		// 查询员工
		WorkPerson loginPerson = workPersonService.findByEmpNo(empNo);
		if (null == loginPerson) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}

		// 员工级别
		String level = loginPerson.getLevel();

		// 按照级别处理
		if (DictUtils.getDictValue("班长", "workPersonLevel", "2").equals(level)) {
			return monitorProcess(model, loginPerson);
		} else if (DictUtils.getDictValue("工作人员", "workPersonLevel", "0").equals(level)) {
			return workPersonLevelProcess(model, loginPerson);
		} else {
			model.addAttribute("message", ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}

	// 工作人员级别信息处理
	private String workPersonLevelProcess(Model model, WorkPerson loginPerson) {
		model.addAttribute("fullName", loginPerson.getFullName());
		model.addAttribute("userName", loginPerson.getName());
		model.addAttribute("isMonitor", "no");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		model.addAttribute("navigaionList", navigaionList);
		model.addAttribute("mmwcList", getMmsList(loginPerson));//月度任务


		return MONTH_MASK_INDEX_INFO;
	}

	// 班长级别信息处理
	private String monitorProcess(Model model, WorkPerson loginPerson) {
		model.addAttribute("fullName", loginPerson.getFullName());
		model.addAttribute("userName", loginPerson.getName());
		model.addAttribute("isMonitor", "yes");
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_1);
		navigaionList.add(NAVIGAION_2);
		model.addAttribute("navigaionList", navigaionList);

		model.addAttribute("mmwcListNoA", monthMaskWcService.findClassMcs(loginPerson.getNo()));//分配到情况
		model.addAttribute("mmwcList", getMmsList(loginPerson));//月度任务
		
		/**
		 * 任务发布列表
		 */
		/*
		 * //查询对象 WorkShopMask queryWsm = new WorkShopMask(); //发布状态 String no =
		 * DictUtils.getDictValue("是", "yes_no", "1"); queryWsm.setReleaseState(no);
		 * //班级 queryWsm.setWorkClassId(loginPerson.getWorkClassId()); //查询车间任务
		 * List<WorkShopMask> wsmList = workShopMaskService.findList(queryWsm); //结果
		 * model.addAttribute("wsmList",wsmList);
		 */

		return MONTH_MASK_INDEX_INFO;
	}
	
	//查询任务数据
	private List<MonthMaskWc> getMmsList(WorkPerson loginPerson){
		List<MonthMaskWc> mmwList = monthMaskWcService.findMaskMmwcs(loginPerson.getNo());//该员工的任务
		//设置员工的已经添加的任务
		monthMaskService.setMonthMask(mmwList);//设置任务数据
		
		
		
		return mmwList;
	}
	
	
	//页面跳转-获取分配页面
	@RequestMapping(value = "allocationPage")
	public String allocationPage(MonthMaskWc monthMaskWc, Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		WorkPerson wp = workPersonService.findByEmpNo(empNo);
		if (null == wp) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}
		
		String mmwcId = request.getParameter("mmwcId");
		if (null == mmwcId) {
			model.addAttribute("message", ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		MonthMaskWc mmwc = monthMaskWcService.get(mmwcId);
		if (null == mmwc) {
			model.addAttribute("message", ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		//设置班级
		mmwc.setWp(wp);
		
		model.addAttribute("monthMaskWc", mmwc);
		
		if (null != mmwc.getMmws()) {
			model.addAttribute("monthMaskWs", mmwc.getMmws());//加入任务
		}
		model.addAttribute("wps", workPersonService.findWpsByUser(empNo,true));
		
		
		return MONTH_MASK_ALLOCATION_PAGE;
	}
	@RequestMapping(value = "addCarPage")
	//页面跳转-添加车辆任务
	public String addCarPage(MonthMask monthMask, HttpServletResponse response,Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}
		
		String mmwcId = request.getParameter("monthMaskWcId");
		if (null == mmwcId) {
			model.addAttribute("message", ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		MonthMaskWc mmwc = monthMaskWcService.get(mmwcId);
		if(null == mmwc) {
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		MonthMaskWs mmws = mmwc.getMmws();
		if(null == mmws) {
			model.addAttribute("message", ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		model.addAttribute("mmwc", mmwc);
		//车型
		model.addAttribute("cmcList", BaseInfoUtils.getAllCmcList());
		//车号
		model.addAttribute("cwList", BaseInfoUtils.getCwList());

		if(null!=monthMask&&null!=monthMask.getId()) {
			monthMask = monthMaskService.get(monthMask.getId());
		}
		model.addAttribute("mm", monthMask);
		
		return MONTH_MASK_ADD_CAR_PAGE;
	}
	
	@RequestMapping(value = "addNoCarPage")
	//页面跳转-添加非车辆任务
	public String addNoCarPage(MonthMask monthMask, HttpServletResponse response,Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}
		
		String mmwcId = request.getParameter("monthMaskWcId");
		if (null == mmwcId) {
			model.addAttribute("message", ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		MonthMaskWc mmwc = monthMaskWcService.get(mmwcId);
		if(null == mmwc) {
			model.addAttribute("message", ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		model.addAttribute("mmwc", mmwc);
		if(null!=monthMask&&null!=monthMask.getId()) {
			monthMask = monthMaskService.get(monthMask.getId());
		}
		model.addAttribute("mm", monthMask);
		return MONTH_MASK_ADD_NO_CAR_PAGE;
	}
	
	//页面跳转-添加任务
	@RequestMapping(value = "addCar")
	@ResponseBody
	public String addCar(MonthMaskWc monthMaskWc, HttpServletResponse response,Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenIdForJson(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return backJsonWithCode(errCode,errUrl);
				} else {
					return backJsonWithCode(errCode,regUrl);
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			return backJsonWithCode(errCode,ERR_WP_NULL);
		}
		
		String mmwcId = request.getParameter("monthMaskWcId");
		if (null == mmwcId) {
			return backJsonWithCode(errCode,ERR_WSM_ID_NULL);
		}
		
		MonthMaskWc mmwc = monthMaskWcService.get(mmwcId);
		if(null == mmwc) {
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		MonthMaskWs mmws = mmwc.getMmws();
		if(null == mmws) {
			return backJsonWithCode(errCode,ERR_WSM_NULL);
		}
		
		MonthMask mmwcConf = new MonthMask();
		mmwcConf.setMonthMaskWcId(mmwcId);
		//验证数量
		if(!monthMaskService.validateNum(mmwcConf)) {
			return backJsonWithCode(errCode,ERR_MONTH_MASK_NUM_LIMITED);
		}
		
		//任务类型
		String type = mmws.getType();
		String value = DictUtils.getDictValue("车辆任务", "monthMaskType", "车辆任务");
		if(value.equals(type)) {
			//车辆任务
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", successCode);
			map.put("type", "0");
			map.put("mmwcId", mmwcId);
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return jsonResult;
		}else {
			//非车辆任务
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("code", successCode);
			map.put("type", "1");
			map.put("mmwcId", mmwcId);
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return jsonResult;
		}
	}
	
	//页面跳转-获取分配页面
	@RequestMapping(value = "allocationMmWp")
	public String allocationMmWp(MonthMaskWc monthMaskWc, HttpServletResponse response,Model model,HttpServletRequest request) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		/**
		 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
		 */
		String empNo = findEmpNo(openId);
		if (null == empNo) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}

		if (null == workPersonService.findByEmpNo(empNo)) {
			model.addAttribute("message", ERR_WP_NULL);
			return WX_ERROR;
		}
		
		String monthMaskWsId = request.getParameter("monthMaskWsId");
		if (null == monthMaskWsId) {
			model.addAttribute("message", ERR_WSM_ID_NULL);
			return WX_ERROR;
		}
		
		String mmwcId = request.getParameter("id");
		if (null == mmwcId) {
			model.addAttribute("message", ERR_WSM_NULL);
			return WX_ERROR;
		}
		
		String workPersonId = request.getParameter("workPersonId");
		if (null == workPersonId) {
			model.addAttribute("message", ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		monthMaskWc = monthMaskWcService.get(mmwcId);
		monthMaskWc.setWorkPersonId(workPersonId);
		monthMaskWcService.saveWxEntity(monthMaskWc);
	
		return indexInfo(request,response,model);
	}
	
	
	//保存
	@RequestMapping(value = "saveCarMask",method=RequestMethod.POST)
	public String saveCarMask(MonthMask monthMask,HttpServletResponse response,Model model,HttpServletRequest request) {
		try {
			String openId = null;
			if (null != Global.TEST_WX_OPEN_ID) {
				// 微信测试
				openId = Global.TEST_WX_OPEN_ID;
			} else {
				// 是否已经注册并且激活
				openId = (String) model.asMap().get("openId");
				String regUrl = validateRegByOpenId(openId, model);
				if (null != regUrl) {
					// 有错误信息
					String errUrl = (String) model.asMap().get("errUrl");
					if (null != errUrl) {
						// 看是否有错误
						return errUrl;
					} else {
						return regUrl;
					}
				}
			}
			/**
			 * 需要获取员工号 查询员工信息后，获得任务，因为没有连接微信，所以暂时不写
			 */
			String empNo = findEmpNo(openId);
			if (null == empNo) {
				model.addAttribute("message", ERR_EMP_NO_NULL);
				return WX_ERROR;
			}

			if (null == workPersonService.findByEmpNo(empNo)) {
				model.addAttribute("message", ERR_WP_NULL);
				return WX_ERROR;
			}
			
			
			String mmwcId = request.getParameter("monthMaskWcId");
			if (null == mmwcId) {
				model.addAttribute("message", ERR_WSM_ID_NULL);
				return WX_ERROR;
			}
			
			MonthMaskWc mmwc = monthMaskWcService.get(mmwcId);
			if(null == mmwc) {
				model.addAttribute("message", ERR_WSM_NULL);
				return WX_ERROR;
			}
			
			MonthMaskWs mmws = mmwc.getMmws();
			if(null == mmws) {
				model.addAttribute("message", ERR_WSM_NULL);
				return WX_ERROR;
			}
			
			MonthMask mmwcConf = new MonthMask();
			mmwcConf.setMonthMaskWcId(mmwcId);
			//验证数量
			if(!monthMaskService.validateNum(mmwcConf)) {
				model.addAttribute("message", ERR_MONTH_MASK_NUM_LIMITED);
				return WX_ERROR;
			}
		
			User user = UserUtils.get(Global.DEFAULT_ID_SYS_MANAGER);
			monthMaskService.saveWx(monthMask,user);
			model.addAttribute("message", MSG_MM_SUCCESS);
			return indexInfo(request,response,model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("message", ERR_FRR_SAVE);
		return WX_ERROR;
	}

}
