package com.snaker.frame.controller;

import com.snaker.common.utils.NumberUtil;
import com.snaker.system.service.ISysFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.io.FileUtil;
import com.snaker.common.redis.util.RedisUtils;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.poi.ExcelUtil;
import com.snaker.dfs.feign.RemoteFtpService;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import static org.jsoup.helper.StringUtil.isNumeric;

import com.snaker.common.core.domain.R;
import com.snaker.common.core.controller.BaseController;
import com.snaker.frame.domain.TStudentScore;
import com.snaker.frame.service.ITStudentScoreService;
import com.snaker.system.service.ISysDictDataService;

/**
 * 学生成绩 提供者
 * 
 * @author snaker
 * @date 2021-11-09
 */
@RestController
@RequestMapping("score")
public class TStudentScoreController extends BaseController
{
	private final String template = "template/学生成绩表导入模板.xlsx";
	
	@Autowired
	private ITStudentScoreService tStudentScoreService;

	@Autowired
	private ISysDictDataService iSysDictDataService;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ISysFileService iSysFileService;
	
	/**
	 * 查询学生成绩
	 */
	@GetMapping("get/{id}")
	public TStudentScore get(@PathVariable("id") String id)
	{
		return tStudentScoreService.selectTStudentScoreById(id);
	}
	
	/**
	 * 查询学生成绩列表
	 */
	@GetMapping("list")
	public R list(TStudentScore tStudentScore)
	{
		startPage();
        return result(tStudentScoreService.selectTStudentScoreList(tStudentScore));
	}
	
	
	/**
	 * 新增保存学生成绩
	 */
	@PostMapping("save")
	public R addSave(@RequestBody TStudentScore tStudentScore)
	{
		if (!tStudentScoreService.checkConstrain(tStudentScore)) {
			return R.error("填入的内容已存在相同的数据");
		}
		tStudentScore.setCreateByUserName(getUserName());
		tStudentScore.setCreateBy(getCurrentUserIdString());
		tStudentScore.setUpdateByUserName(getUserName());
		tStudentScore.setUpdateBy(getCurrentUserIdString());
		tStudentScore.setCreateTime(new Date());
		tStudentScore.setUpdateTime(new Date());
		return toAjax(tStudentScoreService.insertTStudentScore(tStudentScore));
	}

	/**
	 * 修改保存学生成绩
	 */
	@PostMapping("update")
	public R editSave(@RequestBody TStudentScore tStudentScore)
	{
		tStudentScore.setUpdateByUserName(getUserName());
		tStudentScore.setUpdateBy(getCurrentUserIdString());
		tStudentScore.setUpdateTime(new Date());
		return toAjax(tStudentScoreService.updateTStudentScoreForForm(tStudentScore));
	}
	
	/**
	 * 删除学生成绩
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(tStudentScoreService.deleteTStudentScoreByIds(ids));
	}


	/**
	 * 批量导出学生成绩
	 */
	@PostMapping("/export")
	public R export(TStudentScore tStudentScore) {
		List<TStudentScore> list = tStudentScoreService.selectTStudentScoreList(tStudentScore);
		ExcelUtil<TStudentScore> util = new ExcelUtil<TStudentScore>(TStudentScore.class, new String[]{}, new String[]{"errMsg"});
		String[] dictTypes = {"exam_subject"};
		Map<String, Map<String, String>> dictDatasMap = iSysDictDataService.getDictDatasMap(dictTypes);
		return util.exportExcel(list, dictDatasMap, "学生成绩导出");
	}

	/**
	 * 批量导入学生成绩
	 */
	@PostMapping("import")
	public R batchImport(HttpServletRequest request)  throws Throwable{
		R r = null;
		File file = null;
		ExcelUtil excelUtil = new ExcelUtil<>();
		try {
			file = excelUtil.copyDirToTempDir(request);
			if (file == null) {
				return R.error("选择文件夹为空");
			}
			File mainExcel = excelUtil.getMainExcelNoDel(file);
			if (mainExcel == null) {
				return R.error("未找到Excel文件");
			}
			List<List<String>> list = excelUtil.importExcel(new FileInputStream(mainExcel));
			if (list.size() < 2) {
				return R.error("Excel文件无数据");
			}
			// 获取模板数据
			List<String> head = iSysFileService.readExcelTemplate(template);
			// 表格标题头验证
			if (!excelUtil.headCompare(list.get(0), head)) {
				return R.error("导入Excel文件与模板不一致");
			}
			List<TStudentScore> okList = new ArrayList<TStudentScore>();
			ArrayList<List<String>> errList = new ArrayList<>();
			// 数据验证
			Map<String, Map<String, String>> validMap = new HashMap<String, Map<String, String>>();
			validMap.put("exam_subject", iSysDictDataService.getLabelToValue("exam_subject"));
			// 唯一性约束
			Map<String, String> onlyMap = new HashMap<>();
			for (int i = 1; i < list.size(); i++) {
				List<String> row = list.get(i);
				String validResult = importRowValid(row, validMap,onlyMap);
				if (validResult == null) {
					TStudentScore entity = (TStudentScore) excelUtil.rowToEntity(row, TStudentScore.class, head);
					// @@ 添加附加处理逻辑 例如：关联表更新，状态变化
					entity.setSubjectCode(row.get(5));
					entity.setDelFlag("0");
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					entity.setCreateBy(getCurrentUserIdString());
					entity.setUpdateBy(getCurrentUserIdString());
					entity.setCreateByUserName(getUserName());
					entity.setUpdateByUserName(getUserName());
					okList.add(entity);
				} else {
					row.add(validResult);
					errList.add(row);
				}
			}
			if (okList.size() > 0) {
					tStudentScoreService.insertTStudentScoreList(okList);
			}
			r = new R().ok();
			// 整合错误信息存入redis,并设定2分钟过期，无下载自动过期（解决错误文件变为垃圾的问题）
			if (errList.size() > 0) {
				List<String> first = list.get(0);
				first.add("错误信息");
				errList.add(0, first);
				String errKey = UUID.randomUUID().toString();
				redisUtils.set("import-error-" + errKey, errList, 60 * 2);
				// 返回信息
				r.put("errRowCount", errList.size()-1);
				r.put("errKey", errKey);
			} else {
				r.put("errRowCount", 0);
			}
			r.put("successCount", okList.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[数据导入异常:{}]",e.getMessage());
		} finally {
			if (file != null) {
				FileUtil.del(file);
			}
		}
		return r;
	}

	private String importRowValid(List<String> row, Map<String, Map<String, String>> pro, Map<String, String> onlyMap) {
		// @@ 添加表格行元数据校验
         /* 导入数据验证
         1、是否必填、内容长度、数据格式
         2、是否字典表、外表存在
         3、多个相关字段逻辑验证
         by snaker 2020/12/31 13:09 */
		// 唯一性约束
		//必填项验证
		if (StringUtils.isEmpty(row.get(0))) {
			return "学号为必填项，不能为空";
		}
		if (StringUtils.isEmpty(row.get(3))) {
			return "科目代码为必填项，不能为空";
		}
		//验证字段类型和长度
		if (row.get(0).length() > 10 ) {
			return "学号内容长度超出10";
		}
		if (row.get(1).length() > 20 ) {
			return "姓名内容长度超出20";
		}
		if (StringUtils.isNotEmpty(row.get(2)) && NumberUtil.isNumber(row.get(2))) {
			return "成绩内容请填入数值";
		}
		if (row.get(3).length() > 10 ) {
			return "科目代码内容长度超出10";
		}
		//验证数据字典、键值组合
		if (!pro.get("exam_subject").containsKey(row.get(3))) {
			return "科目代码不存在，请核对";
		}else{
			row.add(5,pro.get("exam_subject").get(row.get(3)));
		}

		//@@数据唯一性验证
		TStudentScore entity = new TStudentScore();
		entity.setStudentNo(row.get(1));
		entity.setSubjectCode(row.get(4));
		if (!tStudentScoreService.checkConstrain(entity)) {
			return "系统中已经存在学号,科目代码,相同的数据}，请勿重复导入";
		}
		//IstInspectionItems entity= new IstInspectionItems();
		//entity.setItemName(row.get(0));
		//entity.setInspectionProductType(pro.get("inspection_product_type").get(row.get(1)));
		//if (!istInspectionItemsService.checkConstrain(entity)) {
		//	return "已经存在检测项目和检测产品类型相同的数据，请勿重复导入";
		//}
		return null;
	}
}
