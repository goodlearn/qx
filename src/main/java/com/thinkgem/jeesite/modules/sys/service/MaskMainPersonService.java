package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;

/**
 * 总负责人任务表Service
 * @author wzy
 * @version 2018-02-07
 */
@Service
@Transactional(readOnly = true)
public class MaskMainPersonService extends CrudService<MaskMainPersonDao, MaskMainPerson> {

	@Autowired
	private MaskSinglePersonDao maskSinglePersonDao;
	
	@Autowired
	private MaskContentDao maskContentDao;
	/**
	 * 详细内容
	 */
	public MaskMainPerson findDetails(String id) {
		MaskMainPerson mmpQuery = new MaskMainPerson();
		mmpQuery.setId(id);
		List<MaskMainPerson> mmpList = dao.findList(mmpQuery);
		if(null == mmpList || mmpList.size() == 0) {
			return null;
		}
		for(MaskMainPerson mmp : mmpList) {
			String mmpId = mmp.getId();
			MaskSinglePerson mspQuery = new MaskSinglePerson();
			mspQuery.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonDao.findList(mspQuery);
			mmp.setMspList(mspList);
			for(MaskSinglePerson msp :mspList) {
				MaskContent mcQuery = new MaskContent();
				mcQuery.setMspId(mcQuery.getId());
				List<MaskContent> mcList = maskContentDao.findList(mcQuery);
				msp.setMcList(mcList);
			}
		}
		return mmpList.get(0);
	}
	
	public MaskMainPerson get(String id) {
		return super.get(id);
	}
	
	public List<MaskMainPerson> findList(MaskMainPerson maskMainPerson) {
		return super.findList(maskMainPerson);
	}
	
	public Page<MaskMainPerson> findPage(Page<MaskMainPerson> page, MaskMainPerson maskMainPerson) {
		return super.findPage(page, maskMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void save(MaskMainPerson maskMainPerson) {
		super.save(maskMainPerson);
	}
	
	@Transactional(readOnly = false)
	public void delete(MaskMainPerson maskMainPerson) {
		super.delete(maskMainPerson);
	}
	
}