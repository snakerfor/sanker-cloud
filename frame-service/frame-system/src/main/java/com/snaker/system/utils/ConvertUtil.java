package com.snaker.system.utils;

import com.alibaba.fastjson.JSON;
import com.snaker.common.annotation.Excel;
import com.snaker.common.core.text.Convert;
import com.snaker.common.utils.DateUtils;
import com.snaker.common.utils.StringUtils;
import com.snaker.common.utils.reflect.ReflectUtils;
import com.snaker.dfs.domain.DfsFile;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Excel相关处理
 *
 * @author sfd
 */
public class ConvertUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(ConvertUtil.class);

    // 指定处理字段
    private String[] okFields = {};
    // 指定不处理字段
    private String[] noFields = {};
    /**
     * 实体对象
     */
    private Class<T> clazz;

    public ConvertUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public ConvertUtil(Class<T> clazz, String[] arr) {
        this.clazz = clazz;
        this.okFields = arr;
    }

    public ConvertUtil(Class<T> clazz, String[] arr, String[] no) {
        this.clazz = clazz;
        this.okFields = arr;
        this.noFields = no;
    }

    /**
     * @return java.lang.String
     * @Description 获取工作流数据
     * @Param [domain]
     * @Author snaker
     * @Date 2020/11/13 15:36
     **/
    public String getApproveData(T domain, Map<String, Map<String, String>> dicts) {
        return JSON.toJSONString(getApproveDataNoString(domain, dicts, true));
    }

    public String getApproveDataAgain(T domain, Map<String, Map<String, String>> dicts, boolean isShow) {
        return JSON.toJSONString(getApproveDataNoString(domain, dicts, isShow));
    }

    public List getApproveDataNoString(T domain, Map<String, Map<String, String>> dicts, boolean isShow) {
        Field[] fields = clazz.getDeclaredFields();
        List list = new ArrayList<Map<String, Object>>();
        for (Field field : fields) {
            // 指定字段处理
            if (this.okFields.length > 0 && !Arrays.asList(this.okFields).contains(field.getName())) {
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation != null && StringUtils.isNotEmpty(annotation.name())) {
                String s = annotation.filePropertyName();
                System.out.println(s);
                map.put("label", annotation.name());
                /* 数据处理 by snaker 2020/12/18 9:14 */
                // 数据为空,不在处理
                Object o = getValue(domain, field.getName());
                if (o != null) {
                    if (StringUtils.isNotEmpty(annotation.dateFormat())) {
                        Date value = (Date) getValue(domain, field.getName());
                        map.put("value", DateUtils.parseDateToStr(annotation.dateFormat(), value));
                    } else if (StringUtils.isNotEmpty(annotation.dictType())) {
                        String dd = Convert.toStr(getValue(domain, field.getName()));
                        if (dicts.get(annotation.dictType()) != null) {
                            if (StringUtils.isEmpty(dicts.get(annotation.dictType()).get(dd))) {
                                map.put("value", dicts.get(annotation.dictTypeOther()).get(dd));
                            } else {
                                map.put("value", dicts.get(annotation.dictType()).get(dd));
                            }
                        } else {
                            map.put("value", getValue(domain, field.getName()));
                        }
                    } else if (StringUtils.isNotEmpty(annotation.fileLinkId())) {
                        /* 附件处理 by snaker 2020/12/18 9:14 */
                        String names = (String) getValue(domain, field.getName());
                        String ids = (String) getValue(domain, annotation.fileLinkId());
                        if (StringUtils.isNotEmpty(ids) && StringUtils.isNotEmpty(names)) {
                            String[] ida = ids.split(",");
                            String[] namea = names.split(",");
                            List lis = new ArrayList<Map<String, String>>();
                            for (int i = 0; i < namea.length; i++) {
                                Map<String, String> m = new HashMap<String, String>();
                                m.put("id", ida[i]);
                                m.put("fileName", namea[i]);
                                lis.add(m);
                            }
                            map.put("files", lis);
                        } else {
                            map.put("value", names);
                        }
                    } else if (StringUtils.isNotEmpty(annotation.filePropertyName())) {
                        /* 附件处理，附件對象存儲的方法 by snaker 2020/12/18 9:14 */
                        List<Map<String, Object>> files = (List<Map<String, Object>>) getValue(domain, annotation.filePropertyName());
                        if (files != null && files.size() > 0) {
                            map.put("files", files);
                        } else {
                            String names = (String) getValue(domain, field.getName());
                            map.put("value", names);
                        }
                    } else {
                        map.put("value", getValue(domain, field.getName()));
                    }
                } else {
                    if (isShow) {
                        map.put("value", "");
                    } else {
                        continue;
                    }
                }
            }
            list.add(map);
        }
        return list;
    }

    public List getApproveDataNoString(T domain) {
        Field[] fields = clazz.getDeclaredFields();
        List list = new ArrayList<Map<String, Object>>();
        for (Field field : fields) {
            Map<String, Object> map = new HashMap<String, Object>();
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation != null && StringUtils.isNotEmpty(annotation.name())) {
                map.put("label", annotation.name());
                if (StringUtils.isNotEmpty(annotation.dateFormat())) {
                    Date value = (Date) getValue(domain, field.getName());
                    map.put("value", DateUtils.parseDateToStr(annotation.dateFormat(), value));
                } else {
                    map.put("value", getValue(domain, field.getName()));
                }
                list.add(map);
            }
        }
        return list;
    }

    public List getApproveData4List(List<T> domains) {
        List result = new ArrayList();
        for (T t : domains) {
            result.add(getApproveDataNoString(t));
        }
        return result;
    }

    public List getChangeData(T domain1, T domain2, Map<String, Map<String, String>> dicts, boolean isShow) {
        Field[] fields = clazz.getDeclaredFields();
        List list = new ArrayList<Map<String, Object>>();
        for (Field field : fields) {
            // 指定字段处理
            if (this.okFields.length > 0 && !Arrays.asList(this.okFields).contains(field.getName())) {
                continue;
            }
            // 指定不包含字段
            if (this.noFields.length > 0 && Arrays.asList(this.noFields).contains(field.getName())) {
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation != null && StringUtils.isNotEmpty(annotation.name())) {
                /* 数据处理 by snaker 2020/12/18 9:14 */
                // 数据为空,不在处理
                Object o1 = getValue(domain1, field.getName());
                Object o2 = getValue(domain2, field.getName());
                if (o1 != null || o2 != null) {
                    if (StringUtils.isNotEmpty(annotation.dateFormat())) {
                        Date value1 = (Date) o1;
                        Date value2 = (Date) o2;
                        if (value1 != null && value2 == null) {
                            map.put("after", DateUtils.parseDateToStr(annotation.dateFormat(), value1));
                            map.put("before", "");
                        } else if (value1 == null && value2 != null) {
                            map.put("after", "");
                            map.put("before", DateUtils.parseDateToStr(annotation.dateFormat(), value2));
                        } else {
                            if (value1.getTime() != value2.getTime()) {
                                map.put("after", DateUtils.parseDateToStr(annotation.dateFormat(), value1));
                                map.put("before", DateUtils.parseDateToStr(annotation.dateFormat(), value2));
                            }
                        }
                    } else if (StringUtils.isNotEmpty(annotation.dictType())) {
                        String dd1 = Convert.toStr(o1, "");
                        String dd2 = Convert.toStr(o2, "");
                        if (!dd1.equals(dd2)) {
                            if (dicts.get(annotation.dictType()) != null) {
                                map.put("after", dicts.get(annotation.dictType()).get(dd1));
                                map.put("before", dicts.get(annotation.dictType()).get(dd2));
                            } else {
                                map.put("after", dd1);
                                map.put("before", dd2);
                            }
                        }
                    } else if (StringUtils.isNotEmpty(annotation.fileLinkId())) {
                        /* 附件处理 by snaker 2020/12/18 9:14 */
                        String names1 = Convert.toStr(o1, "");
                        String names2 = Convert.toStr(o2, "");
                        if (!names1.equals(names2)) {
                            map.put("type", "FILE");
                            String ids1 = Convert.toStr(getValue(domain1, annotation.fileLinkId()), "");
                            if (StringUtils.isNotEmpty(ids1) && StringUtils.isNotEmpty(names1)) {
                                String[] ida = ids1.split(",");
                                String[] namea = names1.split(",");
                                List lis = new ArrayList<Map<String, String>>();
                                for (int i = 0; i < namea.length; i++) {
                                    Map<String, String> m = new HashMap<String, String>();
                                    m.put("id", ida[i]);
                                    m.put("fileName", namea[i]);
                                    lis.add(m);
                                }
                                map.put("after", lis);
                            } else {
                                map.put("after", names1);
                            }
                            String ids2 = Convert.toStr(getValue(domain2, annotation.fileLinkId()), "");
                            if (StringUtils.isNotEmpty(ids2) && StringUtils.isNotEmpty(names2)) {
                                String[] ida = ids2.split(",");
                                String[] namea = names2.split(",");
                                List lis = new ArrayList<Map<String, String>>();
                                for (int i = 0; i < namea.length; i++) {
                                    Map<String, String> m = new HashMap<String, String>();
                                    m.put("id", ida[i]);
                                    m.put("fileName", namea[i]);
                                    lis.add(m);
                                }
                                map.put("before", lis);
                            } else {
                                map.put("before", names2);
                            }
                        }
                    } else if (StringUtils.isNotEmpty(annotation.filePropertyName())) {
                        map.put("type", "FILE");
                        /* 附件处理，附件對象存儲的方法 by snaker 2020/12/18 9:14 */
                        List<DfsFile> files1 = (List<DfsFile>) getValue(domain1, annotation.filePropertyName());
                        List<DfsFile> files2 = (List<DfsFile>) getValue(domain2, annotation.filePropertyName());
                        if (files1 == null) {
                            files1 = new ArrayList<DfsFile>();
                        }
                        if (files2 == null) {
                            files2 = new ArrayList<DfsFile>();
                        }
                        boolean flag = false;
                        if (files1.size() != files2.size()) {
                            flag = true;
                        } else {
                            if (files1.size() > 0) {
                                for (DfsFile afterFile : files1) {
                                    boolean temFlag = false;
                                    for (DfsFile beforeFile : files2) {
                                        String afterFileId = afterFile.getId();
                                        String beforeFileId = beforeFile.getId();
                                        if (afterFileId.equals(beforeFileId)) {
                                            temFlag = true;
                                        }
                                    }
                                    if (!temFlag) {
                                        flag = true;
                                        break;
                                    }
                                }
                            } else {
                                flag = true;
                            }
                        }
                        if (flag) {
                            if (files1 != null && files1.size() > 0) {
                                map.put("after", files1);
                            } else {
                                map.put("after", Convert.toStr(o1, ""));
                            }
                            if (files2 != null && files2.size() > 0) {
                                map.put("before", files2);
                            } else {
                                map.put("before", Convert.toStr(o2, ""));
                            }
                        }
                    } else if (field.getType() == Long.class) {
                        String dd1 = Convert.toStr(o1, "");
                        String dd2 = Convert.toStr(o2, "");
                        if (!dd1.equals(dd2)) {
                            map.put("after", dd1);
                            map.put("before", dd2);
                        }
                    } else if (field.getType() == Integer.class) {
                        String dd1 = Convert.toStr(o1, "");
                        String dd2 = Convert.toStr(o2, "");
                        if (!dd1.equals(dd2)) {
                            map.put("after", dd1);
                            map.put("before", dd2);
                        }
                    } else if (field.getType() == Double.class) {
                        String dd1 = Convert.toStr(o1, "");
                        String dd2 = Convert.toStr(o2, "");
                        if (!dd1.equals(dd2)) {
                            map.put("after", dd1);
                            map.put("before", dd2);
                        }
                    } else if (field.getType() == Double.class) {
                        String dd1 = vNull(String.valueOf((Double) getValue(domain1, field.getName())));
                        String dd2 = vNull(String.valueOf((Double) getValue(domain2, field.getName())));
                        if (!dd1.equals(dd2)) {
                            map.put("after", dd1);
                            map.put("before", dd2);
                        }
                    } else {
                        String dd1 = Convert.toStr(o1, "");
                        String dd2 = Convert.toStr(o2, "");
                        if (!dd1.equals(dd2)) {
                            map.put("after", dd1);
                            map.put("before", dd2);
                        }
                    }
                }
                if (map.containsKey("before") && map.containsKey("after")) {
                    map.put("label", annotation.name());
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * @return T
     * @Description 比对两个实体，相同字段置空
     * @Param [domain1, domain2]
     * @Author snaker
     * @Date 2021/1/29 16:28
     **/
    public void setNullIfEqual(T domain1, T domain2, String[] noFields) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 指定不包含字段
            if (this.noFields.length > 0 && Arrays.asList(this.noFields).contains(field.getName())) {
                continue;
            }
            /* 数据处理 by snaker 2020/12/18 9:14 */
            // 数据为空,不在处理
            if (Arrays.asList(noFields).contains(field.getName())) {
                String o1 = Convert.toStr(getValue(domain1, field.getName()));
                String o2 = Convert.toStr(getValue(domain2, field.getName()));
                if(o1 == null){
                    o1 = "";
                }
                if(o2 == null){
                    o2 = "";
                }
                if (o1.equals(o2)) {
                    ReflectUtils.invokeSetter(domain2, field.getName(), null);
                }
            }
        }
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    @SneakyThrows
    private Object getValue(Object o, String name) {
        if (StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = clazz.getMethod(methodName);
            o = method.invoke(o);
        }
        return o;
    }

    // 字符串空值处理
    private String vNull(String p) {
        return p == null ? "" : p;
    }
}
