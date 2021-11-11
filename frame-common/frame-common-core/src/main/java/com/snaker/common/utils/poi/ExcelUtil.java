package com.snaker.common.utils.poi;

import cn.hutool.core.io.FileUtil;
import com.snaker.common.annotation.Excel;
import com.snaker.common.annotation.Excel.Type;
import com.snaker.common.core.domain.R;
import com.snaker.common.core.text.Convert;
import com.snaker.common.exception.BusinessException;
import com.snaker.common.utils.DateUtils;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.ToolUtil;
import com.snaker.common.utils.file.FileUtils;
import com.snaker.common.utils.reflect.ReflectUtils;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Excel相关处理
 *
 * @author sfd
 */
public class ExcelUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Field> fields;

    /**
     * 字典表映射数据
     */
    private Map<String, Map<String, String>> dictMaps;

    /**
     * 手动配置字段
     */
    private String[] outFields = {};

    /**
     * 手动配置不处理字段
     */
    private String[] noFields = {};

    /**
     * 实体对象
     */
    public Class<T> clazz;


    public ExcelUtil() {
    }

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ExcelUtil(Class<T> clazz, String[] out) {
        this.clazz = clazz;
        this.outFields = out;
    }

    public ExcelUtil(Class<T> clazz, String[] out, String[] in) {
        this.clazz = clazz;
        this.outFields = out;
        this.noFields = in;
    }

    public void init(List<T> list, String sheetName, Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 读取第一个sheet ,首行，首列
     *
     * @param input 输入流
     * @return 转换后集合
     */
    public List<List<String>> importExcel(InputStream is) throws Exception {
        return importExcel(StringUtils.EMPTY, is, 0, 0);
    }

    /**
     * 读取第一个sheet ,首行，首列
     *
     * @param file 文件
     * @return 转换后集合
     */
    public List<List<String>> importExcel(File file) {
        InputStream is = null;
        List list = new ArrayList<List<String>>();
        try {
            is = new FileInputStream(file);
            list = importExcel(StringUtils.EMPTY, is, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导入表格读取异常", e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param input     输入流
     * @return 转换后集合
     */
    public List<List<String>> importExcel(String sheetName, InputStream is, int rowGo, int cellGo) throws Exception {
        this.type = Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<List<String>> list = new ArrayList<List<String>>();
        Sheet sheet = null;
        if (StringUtils.isNotEmpty(sheetName)) {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = wb.getSheet(sheetName);
        } else {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = wb.getSheetAt(0);
        }
        if (sheet == null) {
            throw new IOException("文件sheet不存在");
        }
        int rows = sheet.getLastRowNum() + 1;
        if (rows > 0) {
            // 读取起始行
            int rowStart = 0;
            if (rowGo > 0) {
                rowStart = rowGo;
            }
            for (int i = rowStart; i < rows; i++) {
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                // 读取起始列
                int cellStart = 0;
                if (cellGo > 0) {
                    cellStart = cellGo;
                }
                List<String> rowList = new ArrayList<String>();
                DataFormatter formatter = new DataFormatter();
                for (int column = cellStart; column < cellNum; column++) {
                    Cell cell = row.getCell(column);
                    rowList.add(formatter.formatCellValue(cell));
                }
                list.add(rowList);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public R exportExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public R exportExcel(List<T> list, Map<String, Map<String, String>> dictMaps, String sheetName) {
        this.dictMaps = dictMaps;
        this.init(list, sheetName, Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public R importTemplateExcel(String sheetName) {
        this.init(null, sheetName, Type.IMPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public R exportExcel() {
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);
                Cell cell = null; // 产生单元格
                // 产生一行
                Row row = sheet.createRow(0);
                // 写入各个字段的列头名称
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    Excel attr = field.getAnnotation(Excel.class);
                    // 创建列
                    cell = row.createCell(i);
                    // 设置列中写入内容为String类型
                    cell.setCellType(CellType.STRING);
                    CellStyle cellStyle = this.wb.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);
                    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    if (attr.name().indexOf("注：") >= 0) {
                        Font font = this.wb.createFont();
                        font.setColor(HSSFFont.COLOR_RED);
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
                        sheet.setColumnWidth(i, 6000);
                    } else {
                        Font font = this.wb.createFont();
                        // 粗体显示
                        font.setBold(true);
                        // 选择需要用到的字体格式
                        cellStyle.setFont(font);
                        cellStyle.setFillForegroundColor(HSSFColorPredefined.LIGHT_YELLOW.getIndex());
                        // 设置列宽
                        sheet.setColumnWidth(i, (int) ((attr.width() + 0.72) * 256));
                        row.setHeight((short) (attr.height() * 20));
                    }
                    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cellStyle.setWrapText(true);
                    cell.setCellStyle(cellStyle);
                    // 写入列名
                    if (StringUtils.isNotEmpty(sheetName) && sheetName.contains("产成品") && "物料类别".equals(attr.name())) {
                        cell.setCellValue("产品大类");
                    } else {
                        cell.setCellValue(attr.name());
                    }
                    // 如果设置了提示信息则鼠标放上去提示.
                    if (StringUtils.isNotEmpty(attr.prompt())) {
                        // 这里默认设了2-101列提示.
                        setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, i, i);
                    }
                    // 如果设置了combo属性则本列只能选择不能输入
                    if (attr.combo().length > 0) {
                        // 这里默认设了2-101列只能选择不能输入.
                        setXSSFValidation(sheet, attr.combo(), 1, 100, i, i);
                    }
                }
                if (Type.EXPORT.equals(type)) {
                    fillExcelData(index, row, cell);
                }
            }
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            this.wb.write(out);
            return R.ok(filename);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     * @param cell  类型单元格
     */
    public void fillExcelData(int index, Row row, Cell cell) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        // 写入各条记录,每条记录对应excel表中的一行
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            for (int j = 0; j < fields.size(); j++) {
                // 获得field.
                Field field = fields.get(j);
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                Excel attr = field.getAnnotation(Excel.class);
                try {
                    // 设置行高
                    row.setHeight((short) (attr.height() * 20));
                    // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                    if (attr.isExport()) {
                        // 创建cell
                        cell = row.createCell(j);
                        cell.setCellStyle(cs);
                        if (vo == null) {
                            // 如果数据存在就填入,不存在填入空格.
                            cell.setCellValue("");
                            continue;
                        }
                        // 用于读取对象中的属性
                        Object value = getTargetValue(vo, field, attr);
                        String dateFormat = attr.dateFormat();
                        String readConverterExp = attr.readConverterExp();
                        String dictType = attr.dictType();
                        String dictTypeOther = attr.dictTypeOther();
                        if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                            cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
                        } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                            cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                        } else if (StringUtils.isNotEmpty(dictType) && dictMaps != null) {
                            String v = "";
                            if (dictMaps.get(dictType) != null) {
                                v = dictMaps.get(dictType).get(String.valueOf(value));
                            }
                            if (StringUtils.isEmpty(v)) {
                                if (StringUtils.isNotEmpty(dictTypeOther)) {
                                    if (dictMaps.get(dictTypeOther) != null) {
                                        v = dictMaps.get(dictTypeOther).get(String.valueOf(value));
                                    }
                                }
                            }
                            cell.setCellValue(v);
                        } else {
                            cell.setCellType(CellType.STRING);
                            // 如果数据存在就填入,不存在填入空格.
                            cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
                        }
                    }
                } catch (Exception e) {
                    log.error("导出Excel失败{}", e);
                }
            }
        }
    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String convertByExp(String propertyValue, String converterExp) throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     * @throws Exception
     */
    public static String reverseByExp(String propertyValue, String converterExp) throws Exception {
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyValue;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename) {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename) {
        String downloadPath = ToolUtil.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * 获取bean中的属性值
     *
     * @param vo    实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.indexOf(".") > -1) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = new ArrayList<Field>();
        List<Field> tempFields = new ArrayList<>();
        Class<?> tempClass = clazz;
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        while (tempClass != null) {
            tempClass = tempClass.getSuperclass();
            if (tempClass != null) {
                tempFields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            }
        }
        putToFields(tempFields);
    }

    /**
     * @return java.util.List<java.lang.reflect.Field>
     * @Description 获取类属性集合
     * @Param [clazz]
     * @Author snaker
     * @Date 2021/1/22 15:06
     **/
    private <T> List<Field> getFieldsList(Class<T> clazz) {
        List<Field> fields = new ArrayList<>();
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            while (clazz != null) {
                clazz = (Class<T>) clazz.getSuperclass();
                if (clazz != null) {
                    fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
                }
            }
        }
        return fields;
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.reflect.Field>
     * @Description 获取字段名称-属性对象的映射关系
     * @Param [clazz]
     * @Author snaker
     * @Date 2021/3/16 16:49
     **/
    private <T> Map<String, Field> getFieldsMap(Class<T> clazz) {
        Map<String, Field> fields = new HashMap<String, Field>();
        if (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                Excel excel = declaredField.getAnnotation(Excel.class);
                if (excel != null && excel.name() != null) {
                    fields.put(excel.name(), declaredField);
                }
            }
            while (clazz != null) {
                clazz = (Class<T>) clazz.getSuperclass();
                if (clazz != null) {
                    Field[] superDeclaredFields = clazz.getDeclaredFields();
                    for (Field declaredField : superDeclaredFields) {
                        Excel excel = declaredField.getAnnotation(Excel.class);
                        if (excel != null && excel.name() != null) {
                            fields.put(excel.name(), declaredField);
                        }
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 放到字段集合中
     */
    private void putToFields(List<Field> fields) {
        /* 指定字段情况下，导出字段顺序按照数组顺序 by snaker 2021/3/23 10:08 */
        if (this.outFields.length > 0) {
            for (String field : this.outFields) {
                List<Field> collect = fields.stream().filter(s -> s.getName().equals(field)).collect(Collectors.toList());
                if (collect.size() > 0) {
                    this.fields.add(collect.get(0));
                }
            }
            return;
        }
        for (Field field : fields) {
            Excel attr = field.getAnnotation(Excel.class);
            if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
                if (this.noFields.length > 0 && Arrays.asList(this.noFields).contains(field.getName())) {
                    continue;
                }
                this.fields.add(field);
            }
        }
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

    /**
     * 创建工作表
     *
     * @param sheetName，指定Sheet名称
     * @param sheetNo             sheet数量
     * @param index               序号
     */
    public void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    val = cell.getNumericCellValue();
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel
                        // 日期格式转换
                    } else {
                        if ((Double) val % 1 > 0) {
                            val = new DecimalFormat("0.00").format(val);
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellTypeEnum() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }
            }
        } catch (Exception e) {
            log.error("[获取cell value异常]", e);
            return val;
        }
        return val;
    }

    /**
     * @return java.util.List<java.util.List < java.lang.String>>
     * @Description 读Excel 文件，
     * @Param [mainFile]
     * @Author snaker
     * @Date 2021/1/13 16:24
     **/

    // 读取文件夹中表格数据
    public List<List<String>> readExcel(File mainFile) throws Throwable {
        List<List<String>> result = null;
        FileInputStream fileInputStream = null;
        String mainDirPath = null;
        try {
            mainDirPath = mainFile.getParentFile().getAbsolutePath();
            log.info("[主文件路径:{}]", mainDirPath);
            //读取excel
            fileInputStream = new FileInputStream(mainFile);
            result = importExcel(fileInputStream);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            FileUtil.del(mainDirPath);
        }
        return result;
    }

    // 缓存上传文件夹
    public File copyDirToTempDir(HttpServletRequest request) throws IOException {
        Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap();
        Set<String> keySet = fileMap.keySet();
        if (CollectionUtils.isEmpty(keySet)) {
            return null;
        }
        String tempDirPath = FileUtils.getSessionIsolatedPath(UUID.randomUUID().toString());
        for (String key : keySet) {
            MultipartFile multipartFile = fileMap.get(key);
            String originalFilename = multipartFile.getOriginalFilename();
            originalFilename = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);
            String tempFilePath = tempDirPath + originalFilename;
            log.info("[临时文件位置:{}]", tempFilePath);
            File newFile = new File(tempFilePath);
            org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), newFile);
        }
        return new File(tempDirPath);
    }

    //获取文件夹内的主数据excel文件
    public File getMainExcel(File mainFile) {
        File f = null;
        if (mainFile != null) {
            File[] unZipFiles = mainFile.listFiles();
            for (File file : unZipFiles) {
                String name = file.getName();
                if (name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls")) {
                    f = file;
                }
            }
        }
        // 如果不存在主文件，删除缓存目录
        if (f == null) {
            FileUtil.del(mainFile.getParentFile().getAbsolutePath());
        }
        return f;
    }

    //获取文件夹内的主数据excel文件，不存在就不删除
    public File getMainExcelNoDel(File mainFile) {
        File f = null;
        if (mainFile != null) {
            File[] unZipFiles = mainFile.listFiles();
            for (File file : unZipFiles) {
                String name = file.getName();
                if (name.toLowerCase().endsWith(".xlsx") || name.toLowerCase().endsWith(".xls")) {
                    f = file;
                }
            }
        }
        return f;
    }

    // List<List<String>> -> List<T>
    @SneakyThrows
    public <E> E rowToEntity(List<String> row, Class<E> c, List<String> head) {
        //初始化序号-属性映射关系.
        Map<String, Field> allFields = getFieldsMap(c);
        // 定义一个map用于存放列的序号和field.
        Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
        for (int col = 0; col < head.size(); col++) {
            Field field = allFields.get(head.get(col));
            Excel attr = (field != null ? field.getAnnotation(Excel.class) : null);
            if (field != null && attr != null && (attr.type() == Excel.Type.ALL || attr.type() == Excel.Type.IMPORT)) {
                // 设置类的私有字段属性可访问.
                field.setAccessible(true);
                fieldsMap.put(col, field);
            } else {
                // 未配置或不需要导入字段，写入null
                fieldsMap.put(col, null);
            }
        }
        E entity = c.newInstance();
        for (int i = 0; i < fieldsMap.size(); i++) {
            Field field = fieldsMap.get(i);
            // 未匹配field 跳过
            if (field == null) {
                continue;
            }
            // 取得类型,并根据对象类型设置值.
            Class<?> fieldType = field.getType();
            Object val = null;
            if (String.class == fieldType) {
                val = row.get(i);
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                val = Convert.toInt(row.get(i));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                val = Convert.toLong(row.get(i));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                val = Convert.toDouble(row.get(i));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                val = Convert.toFloat(row.get(i));
            } else if (BigDecimal.class == fieldType) {
                val = Convert.toBigDecimal(row.get(i));
            } else if (Date.class == fieldType) {
                val = DateUtils.parseDate(row.get(i));
            }
            if (StringUtils.isNotNull(fieldType)) {
                Excel attr = field.getAnnotation(Excel.class);
                String propertyName = field.getName();
                if (StringUtils.isNotEmpty(attr.targetAttr())) {
                    propertyName = field.getName() + "." + attr.targetAttr();
                } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                    val = reverseByExp(row.get(i), attr.readConverterExp());
                }
                ReflectUtils.invokeSetter(entity, propertyName, val);
            }
        }
        return entity;
    }

    /**
     * 导出错误信息
     *
     * @return 结果
     */
    public void exportExcel(List<List<String>> list, OutputStream out) {
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            SXSSFWorkbook wb = new SXSSFWorkbook(500);
            // sheet 遍历
            for (int index = 0; index <= sheetNo; index++) {
                SXSSFSheet sheet = wb.createSheet();
                // 设置工作表的名称.
                // 写入各个字段的列头名称
                int startNo = index * sheetSize;
                int endNo = Math.min(startNo + sheetSize, list.size());
                // 行遍历
                for (int i = startNo; i < endNo; i++) {
                    Row row = sheet.createRow(i - startNo);
                    // 得到导出对象.
                    List<String> rowList = list.get(i);
                    // 列遍历
                    for (int j = 0; j < rowList.size(); j++) {
                        Cell cell1 = row.createCell(j);
                        cell1.setCellValue(rowList.get(j));
                    }
                }
            }
            wb.write(out);
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
            throw new BusinessException("导出Excel失败，请联系网站管理员！");
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    // 导入Excel 与模板比对
    public boolean headCompare(List<String> src, List<String> target) {
        if (src == null || target == null) {
            return false;
        }
        // 先比对大小
        if (src.size() != target.size()) {
            return false;
        }
        for (int i = 0; i < src.size(); i++) {
            if (!src.get(i).equals(target.get(i))) {
                return false;
            }
        }
        return true;
    }

}