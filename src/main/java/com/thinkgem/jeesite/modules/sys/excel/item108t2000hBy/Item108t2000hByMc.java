package com.thinkgem.jeesite.modules.sys.excel.item108t2000hBy;

import java.util.Comparator;

import com.thinkgem.jeesite.modules.sys.entity.Item108t2000hBy;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;

public class Item108t2000hByMc implements Comparator<Item108t2000hByMc>{

	private MaskContent mc;
	
	private Item108t2000hBy item108t2000hBy;

	public MaskContent getMc() {
		return mc;
	}

	public void setMc(MaskContent mc) {
		this.mc = mc;
	}

	public Item108t2000hBy getItem108t2000hBy() {
		return item108t2000hBy;
	}

	public void setItem108t2000hBy(Item108t2000hBy item108t2000hBy) {
		this.item108t2000hBy = item108t2000hBy;
	}

	
	
	@Override
	public int hashCode() {
		return Integer.valueOf(item108t2000hBy.getNumber());
	}


	@Override
	public int compare(Item108t2000hByMc o1,Item108t2000hByMc o2) {
		Integer i1 = Integer.valueOf(o1.getItem108t2000hBy().getNumber());
		Integer i2 = Integer.valueOf(o2.getItem108t2000hBy().getNumber());
		return i1-i2;
	}
	
}
