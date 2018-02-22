package com.thinkgem.jeesite.modules.sys.excel.item108t2000hBy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.sys.dao.BusinessAssembleDao;
import com.thinkgem.jeesite.modules.sys.dao.Item108t2000hByDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskContentDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskMainPersonDao;
import com.thinkgem.jeesite.modules.sys.dao.MaskSinglePersonDao;
import com.thinkgem.jeesite.modules.sys.dao.WorkShopMaskDao;
import com.thinkgem.jeesite.modules.sys.dao.WsMaskWcDao;
import com.thinkgem.jeesite.modules.sys.entity.BusinessAssemble;
import com.thinkgem.jeesite.modules.sys.entity.Item108t2000hBy;
import com.thinkgem.jeesite.modules.sys.entity.MaskContent;
import com.thinkgem.jeesite.modules.sys.entity.MaskMainPerson;
import com.thinkgem.jeesite.modules.sys.entity.MaskSinglePerson;
import com.thinkgem.jeesite.modules.sys.entity.WorkShopMask;
import com.thinkgem.jeesite.modules.sys.entity.WsMaskWc;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

@Service
@Transactional(readOnly = true)
public class Item108t2000hByExcel {

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
	@Autowired
	private Item108t2000hByDao item108t2000hByDao;
	@Autowired
	private WorkShopMaskDao workShopMaskDao;
	
	private int rownum = 0;
	
	private int maxColumn = 9;
	
	private static Logger log = LoggerFactory.getLogger(Item108t2000hByExcel.class);
	
	
	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;
	

	
	//获取任务模板类型
	private String findBaType(String wsMaskWcId) {
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
	
	private List<Item108t2000hByMmp> createMmp() {
		String wmwId = "755e503ac9ed4d508cab5d7d3f5f9a34";
		WsMaskWc wmw = wsMaskWcDao.get(wmwId);
		String type = findBaType(wmwId);
		if(!type.equals(DictUtils.getDictValue(Global.ITEM_108T_2000H_BY, "bussinessType", "1"))) {
			return null;
		}
		MaskMainPerson queryMmp = new MaskMainPerson();
		queryMmp.setWsMaskWcId(wmwId);
		List<MaskMainPerson> mmpList = maskMainPersonDao.findList(queryMmp);
		List<Item108t2000hByMmp> mmpItemList = new ArrayList<Item108t2000hByMmp>();
		for(MaskMainPerson forMmp : mmpList) {
			String mmpId = forMmp.getId();
			MaskSinglePerson queryMsp = new MaskSinglePerson();
			queryMsp.setMmpId(mmpId);
			List<MaskSinglePerson> mspList = maskSinglePersonDao.findList(queryMsp);
			Item108t2000hByMmp mmpItem = new Item108t2000hByMmp();
			List<Item108t2000hByMsp> itemMspList = new ArrayList<Item108t2000hByMsp>();
			mmpItem.setMmp(forMmp);
			mmpItem.setMsps(itemMspList);
			mmpItem.setWmw(wmw);
			mmpItemList.add(mmpItem);
			for(MaskSinglePerson forMsp : mspList) {
				String mspId = forMsp.getId();
				MaskContent queryMc = new MaskContent();
				queryMc.setMspId(mspId);
				List<MaskContent> mcList = maskContentDao.findList(queryMc);
				Item108t2000hByMsp mspItem = new Item108t2000hByMsp();
				List<Item108t2000hByMc> mcItemList = new ArrayList<Item108t2000hByMc>();
				mspItem.setMcs(mcItemList);
				mspItem.setMsp(forMsp);
				for(MaskContent forMc : mcList) {
					String templateId = forMc.getTemplateId();
					Item108t2000hBy item108t2000hBy = item108t2000hByDao.get(templateId);
					Item108t2000hByMc item = new Item108t2000hByMc();
					item.setItem108t2000hBy(item108t2000hBy);
					item.setMc(forMc);
					mcItemList.add(item);
				}
				itemMspList.add(mspItem);
			}
		}
		return mmpItemList;
	}
	
	//标题
	private void setTitle(Sheet sheet) {
		Row titleRow = sheet.createRow(rownum);
		titleRow.setHeightInPoints(30);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(styles.get("title"));
		titleCell.setCellValue(Global.ITEM_108T_2000H_BY);
		sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
				titleRow.getRowNum(), 0, maxColumn - 1));
	}
	
	//第二行
	private void setRow2(Sheet sheet,SXSSFWorkbook wb) {
		Row headerRow = sheet.createRow(rownum);
		headerRow.setHeightInPoints(16);
		sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),
				headerRow.getRowNum(), 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),
				headerRow.getRowNum(), 3, 5));
		sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),
				headerRow.getRowNum(), 6, 8));
		
		Cell cell0 = headerRow.createCell(0);
		cell0.setCellStyle(styles.get("title"));
		cell0.setCellValue("设备编号:");

		Cell cell1 = headerRow.createCell(3);
		cell1.setCellStyle(styles.get("title"));
		cell1.setCellValue("运行小时:");
		
		Cell cell2 = headerRow.createCell(6);
		cell2.setCellStyle(styles.get("title"));
		cell2.setCellValue("保养日期:");
	}
	
	//第三行
	private void setRow3(Sheet sheet,SXSSFWorkbook wb) {
		CellStyle cellStyle = getCellStyle1(wb);
		Row row3 = sheet.createRow(rownum);
		row3.setHeightInPoints(16);
		Cell cellRow30 = row3.createCell(0);
		cellRow30.setCellValue("对象");
		cellRow30.setCellStyle(cellStyle);
		
		Cell cellRow31 = row3.createCell(1);
		cellRow31.setCellValue("序号");
		cellRow31.setCellStyle(cellStyle);
		
		Cell cellRow32 = row3.createCell(2);
		cellRow32.setCellValue("保养项目");
		cellRow32.setCellStyle(cellStyle);
		
		Cell cellRow33 = row3.createCell(3);
		cellRow33.setCellValue("保养标准");
		cellRow33.setCellStyle(cellStyle);
		
		Cell cellRow34 = row3.createCell(4);
		cellRow34.setCellValue("保养工具");
		cellRow34.setCellStyle(cellStyle);
		
		Cell cellRow35 = row3.createCell(5);
		cellRow35.setCellValue("所需工时");
		cellRow35.setCellStyle(cellStyle);
		
		Cell cellRow36 = row3.createCell(6);
		cellRow36.setCellValue("所需人数");
		cellRow36.setCellStyle(cellStyle);
		
		Cell cellRow37 = row3.createCell(7);
		cellRow37.setCellValue("检查结果");
		cellRow37.setCellStyle(cellStyle);
		
		Cell cellRow38 = row3.createCell(8);
		cellRow38.setCellValue("保养人");
		cellRow38.setCellStyle(cellStyle);
	}
	
	//第四行
	private void setRow4(SXSSFWorkbook wb,Sheet sheet,List<Item108t2000hByMmp> data) {
		for(Item108t2000hByMmp forI108bmmp : data) {
			List<Item108t2000hByMsp> mspList = forI108bmmp.getMsps();
			for(Item108t2000hByMsp forI108bmsp : mspList) {
				List<Item108t2000hByMc> i108mspList = forI108bmsp.getMcs();
				MaskSinglePerson msp = forI108bmsp.getMsp();
				setRow4Data(wb,sheet,msp,i108mspList);//设置数据
				rownum = i108mspList.size() + rownum;
			}
		}
	}
	
	//第五行
	private void setRow5(Sheet sheet,SXSSFWorkbook wb) {
		Row row = sheet.createRow(rownum);
		rownum = rownum + 10;
		row.setHeightInPoints(16);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				rownum, 0, maxColumn - 1));
		
		Cell cell0 = row.createCell(0);
		CellStyle cellStyleLast = wb.createCellStyle();
		cellStyleLast.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleLast.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		cell0.setCellStyle(cellStyleLast);
		cell0.setCellValue("存在问题及处理结果:");
	}
	
	//第六行
	private void setRow6(Sheet sheet,SXSSFWorkbook wb) {
		Row row = sheet.createRow(rownum);
		row.setHeightInPoints(16);
		rownum = rownum + 3;
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				rownum, 0, 2));
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				rownum, 3, 5));
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				rownum, 6, 8));
		
		CellStyle cellStyleLast = wb.createCellStyle();
		cellStyleLast.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleLast.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		
		Cell cell0 = row.createCell(0);
		cell0.setCellValue("保养负责人:");
		cell0.setCellStyle(cellStyleLast);
		
		Cell cell1 = row.createCell(3);
		cell1.setCellValue("司机:");
		cell1.setCellStyle(cellStyleLast);
		
		Cell cell2 = row.createCell(6);
		cell2.setCellValue("保养质检员:");
		cell2.setCellStyle(cellStyleLast);
	}
	
	//第七行
	private void setRow7(Sheet sheet,SXSSFWorkbook wb,String value) {
		Row row = sheet.createRow(rownum);
		row.setHeightInPoints(16);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				rownum, 0, maxColumn - 1));
		
		CellStyle cellStyleLast = wb.createCellStyle();
		cellStyleLast.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleLast.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.RED.getIndex());
		cellStyleLast.setFont(headerFont);
		
		Cell cell0 = row.createCell(0);
		cell0.setCellValue(value);
		cell0.setCellStyle(cellStyleLast);
	}
	
	private void setRowRemark(Sheet sheet,SXSSFWorkbook wb) {
		setRow7(sheet,wb,"注：保养人员每天按照此表进行保养，并将结果填入表中，表示如下：");
		rownum++;
		setRow7(sheet,wb,"“√”表示正常、完好");
		rownum++;
		setRow7(sheet,wb,"“×”表示有问题，处理后才能运行");
		rownum++;
		setRow7(sheet,wb,"每天保养由班长签字后，统一将此单交技术员，安排维护工作，并存档。");
	}
	
	//第四行数据
	private void setRow4Data(SXSSFWorkbook wb,Sheet sheet,MaskSinglePerson msp
			,List<Item108t2000hByMc> i108mspList) {
		int lastColumn = maxColumn-1;
		Row row4 = sheet.createRow(rownum);
		row4.setHeightInPoints(40);
		sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(),
				row4.getRowNum() + i108mspList.size() - 1, lastColumn, lastColumn));
		sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(),
				row4.getRowNum() + i108mspList.size() - 1, 0, 0));
		String partName = DictUtils.getDictLabel(msp.getPart(), Global.ITEM_108T_2000H_BY_DICT, "");
		Cell cell40 = row4.createCell(0);
		cell40.setCellValue(partName);
		CellStyle cellStyle = getCellStyle1(wb);
		cellStyle.setRotation((short)255);
		cell40.setCellStyle(cellStyle);
		Collections.sort(i108mspList,new Item108t2000hByMc());
		int num = rownum;
		for(Item108t2000hByMc forI108Bm : i108mspList) {
			if(num == rownum) {
				setRow4CellData(row4,forI108Bm,wb);
			}else {
				Row newRow = sheet.createRow(num);
				newRow.setHeightInPoints(40);
				setRow4CellData(newRow,forI108Bm,wb);
			}
			num++;
		}
		
		Cell cellLast = row4.createCell(lastColumn);
		cellLast.setCellValue(msp.getWp().getName());
		CellStyle cellStyleLast = wb.createCellStyle();
		cellStyleLast.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleLast.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyleLast.setRotation((short)255);
		cellLast.setCellStyle(cellStyleLast);
	}
	
	//设置边框粗细和文本对齐
	private CellStyle getCellStyle1(SXSSFWorkbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
		cellStyle.setWrapText(true);//设置自动换行
		return cellStyle;
	}
	
	//添加第四行数据
	private void setRow4CellData(Row row4,Item108t2000hByMc forI108Bm,SXSSFWorkbook wb) {
		CellStyle cellStyle = getCellStyle1(wb);
		String yesProblem = DictUtils.getDictValue("有", "have_no", "1");
		if(forI108Bm.getMc().getProblem().equals(yesProblem)) {
			Font headerFont = wb.createFont();
			headerFont.setColor(IndexedColors.RED.getIndex());
			cellStyle.setFont(headerFont);
		}
		int num = 1;
		Cell cell1 = row4.createCell(num);
		cell1.setCellValue(forI108Bm.getItem108t2000hBy().getNumber());
		cell1.setCellStyle(cellStyle);
		num++;
		
		Cell cell2 = row4.createCell(num);
		cell2.setCellValue(forI108Bm.getItem108t2000hBy().getByItem());
		cell2.setCellStyle(cellStyle);
		num++;
		
		Cell cell3 = row4.createCell(num);
		cell3.setCellValue(forI108Bm.getItem108t2000hBy().getByStandard());
		cell3.setCellStyle(cellStyle);
		num++;
		
		Cell cell4 = row4.createCell(num);
		cell4.setCellValue(forI108Bm.getItem108t2000hBy().getByTool());
		cell4.setCellStyle(cellStyle);
		num++;
		
		Cell cell5 = row4.createCell(num);
		cell5.setCellValue(forI108Bm.getItem108t2000hBy().getNeedTime());
		cell5.setCellStyle(cellStyle);
		num++;
		
		Cell cell6 = row4.createCell(num);
		cell6.setCellValue(forI108Bm.getItem108t2000hBy().getNeedPerson());
		cell6.setCellStyle(cellStyle);
		num++;
		
		//有无问题
		String noProblem = DictUtils.getDictValue("没有", "have_no", "1");
		Cell cell7 = row4.createCell(num);
		cell7.setCellValue(forI108Bm.getMc().getRemarks());
		cell7.setCellStyle(cellStyle);
		if(forI108Bm.getMc().getProblem().equals(noProblem)) {
			cell7.setCellValue("√");
		}else {
			cell7.setCellValue(forI108Bm.getMc().getRemarks());
		}
		num++;
	}
	
	public void createExcel(HttpServletResponse response) {
		try {
			SXSSFWorkbook wb;
			Sheet sheet;
            String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			List<Item108t2000hByMmp> data = createMmp();
			wb = new SXSSFWorkbook(500);
			sheet = wb.createSheet("Export");
			this.styles = createStyles(wb);
			rownum = 0;
			//标题
			setTitle(sheet);
			//第二行
			rownum++;
			setRow2(sheet,wb);
			//第三行
			rownum++;
			setRow3(sheet,wb);
			//第四行
			rownum++;
			setRow4(wb,sheet,data);
			//第五行
			setRow5(sheet,wb);
			//第六行
			rownum++;
			setRow6(sheet,wb);
			//备注
			rownum++;
			setRowRemark(sheet,wb);
			rownum = 0;
			
	        sheet.autoSizeColumn((short)2); //调整第三列宽度
	        sheet.autoSizeColumn((short)3); //调整第四列宽度
	        sheet.autoSizeColumn((short)4); //调整第五列宽度
			response.reset();
	        response.setContentType("application/octet-stream; charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
			wb.write(response.getOutputStream());
			wb.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		return styles;
	}
	
}
