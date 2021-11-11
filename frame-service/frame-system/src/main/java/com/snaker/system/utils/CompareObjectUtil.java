package com.snaker.system.utils;

import com.snaker.common.utils.StringUtils;
import com.snaker.system.service.ISysDictDataService;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//@Component
public class CompareObjectUtil<T> {

    //忽略的属性-->2021年3月2日 修改成 需比较的属性 by zhangjie
    private List<String> compareFields = Arrays.asList("");

    //日期属性，需要格式化日期
    private List<String> dateFields = Arrays.asList("");

    //数据字典属性，需要转换成中文显示
    private List<String> dicFields = Arrays.asList("");

    //数据字典Map属性
    private Map<String,String> dicMaps = new HashMap<String,String>();

    //数据字典属性,多选，需要转换成中文显示
    private List<String> dicMutiFields = Arrays.asList("");

    //数据字典Map属性,多选
    private Map<String,String> dicMutiMaps = new HashMap<String,String>();

    private ISysDictDataService iSysDictDataService;

    private CompareStatus status;
    /**
     * 之前原始的值
     */
    private T original;
    /**
     * 当前的值
     */
    private T current;

    public CompareObjectUtil(ISysDictDataService iSysDictDataService) {
        this.iSysDictDataService = iSysDictDataService;
    }

    public CompareStatus getStatus() {
        return status;
    }


    public T getOriginal() {
        return original;
    }


    public void setOriginal(T original) {
        this.original = original;
    }


    public T getCurrent() {
        return current;
    }


    public void setCurrent(T current) {
        this.current = current;
    }

    public List<String> getCompareFields() {
        return compareFields;
    }

    public void setCompareFields(List<String> compareFields) {
        this.compareFields = compareFields;
    }

    public List<String> getDateFields() {
        return dateFields;
    }

    public void setDateFields(List<String> dateFields) {
        this.dateFields = dateFields;
    }

    public List<String> getDicFields() {
        return dicFields;
    }

    public void setDicFields(List<String> dicFields) {
        this.dicFields = dicFields;
    }

    public Map<String, String> getDicMaps() {
        return dicMaps;
    }

    public void setDicMaps(Map<String, String> dicMaps) {
        this.dicMaps = dicMaps;
    }

    public List<String> getDicMutiFields() {
        return dicMutiFields;
    }

    public void setDicMutiFields(List<String> dicMutiFields) {
        this.dicMutiFields = dicMutiFields;
    }

    public Map<String, String> getDicMutiMaps() {
        return dicMutiMaps;
    }

    public void setDicMutiMaps(Map<String, String> dicMutiMaps) {
        this.dicMutiMaps = dicMutiMaps;
    }

    public List contrastObj(Class<T> cls, List finalChangeList, Map<String, String> labelNameMap) throws Exception {
        if(this.original==null){
            this.status=CompareStatus.NEW;
            return finalChangeList;
        }
        if(this.status==CompareStatus.REMOVE){
            return finalChangeList;
        }
        boolean isEqual=true;
        //Class clazz = this.original.getClass();
        Field[] fields = cls.getDeclaredFields();
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
        for (Field field : fields) {
            String fieldName = field.getName();
            if (compareFields.contains(fieldName)) {
                Map<String, Object> map = new HashMap<String, Object>();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, cls);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(this.original);
                Object o2 = getMethod.invoke(this.current);
                String s1 = o1 == null ? "" : o1.toString();//避免空指针异常
                String s2 = o2 == null ? "" : o2.toString();//避免空指针异常
                //思考下面注释的这一行：会bug的，虽然被try catch了，程序没报错，但是结果不是我们想要的
                //if (!o1.toString().equals(o2.toString())) {
                if (!s1.equals(s2)) {
                    isEqual = false;
                    map.put("label", labelNameMap.get(fieldName));
                    if(dateFields.contains(fieldName)){
                        s2 = "".equals(s2)?"":sfd.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(s2));
                        s1 = "".equals(s1)?"":sfd.format(new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(s1));
                    }
                    if(dicFields.contains(fieldName) && StringUtils.isNotNull(dicMaps.get(fieldName))){
                        s2 = "".equals(s2)?"":iSysDictDataService.getDicLabel(dicMaps.get(fieldName), s2);
                        s1 = "".equals(s1)?"":iSysDictDataService.getDicLabel(dicMaps.get(fieldName), s1);
                    }
                    if(dicMutiFields.contains(fieldName) && StringUtils.isNotNull(dicMutiMaps.get(fieldName))){
                        s2 = getFinalStr(s2,fieldName);
                        s1 = getFinalStr(s1,fieldName);
                    }
                    map.put("after", s2);
                    map.put("before", s1);
                    finalChangeList.add(map);
                }
            }
        }
        if(isEqual){
            this.status=CompareStatus.NO_CHANGE;
        }else{
            this.status=CompareStatus.CHANGE;
        }

        return finalChangeList;
    }

    /**
     * List数据变更对比
     * 比较两个相同泛型的List集合中值是否相同
     * @param newList
     * @param oldList
     * @return true有变更 false无变更
     */
    public  Boolean listModifyContrast(List<? extends Object> newList, List<? extends Object> oldList){
        boolean flag = false;
        if (newList != null && oldList != null){
            if (newList.size() != oldList.size()){
                flag = true;
            }else {
                int same = 0;

                for (Object newObj : newList) {
                    for (Object oldObj : oldList) {
                        if (isBaseType(newObj)){
                            if (newObj == oldObj){
                                same ++;
                            }
                        }else {
                            if (!objModifyContrast(newObj,oldObj,false)){
                                same ++;
                            }
                        }
                    }
                }
                if (same != newList.size()){
                    flag = true;
                }
            }
        }else {
            if (newList == null && oldList == null){
                flag = false;
            }else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 判断obj是否基本数据类型
     * 这里Integer和int 都认定为基本类型
     * @param obj
     * @return
     */
    private boolean isBaseType(Object obj){
        Class aClass = obj.getClass();
        if (aClass == Integer.class || aClass ==Integer.TYPE
                || aClass == Byte.class || aClass == Byte.TYPE
                || aClass == Short.class || aClass == Short.TYPE
                || aClass == Character.class || aClass == Character.TYPE
                || aClass == Long.class || aClass == Long.TYPE
                || aClass == Double.class || aClass == Double.TYPE
                || aClass == Float.class || aClass == Float.TYPE
                || aClass == Boolean.class || aClass == Boolean.TYPE
        ){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 单个对象-属性修改对比
     * 属性如果是集合类型，则要求泛型需要重写hashCode和equals方法
     * @param newObj
     * @param oldObj
     * @param reset 是否重置属性
     * @return true有变更 false无变更
     */
    public  Boolean objModifyContrast(Object newObj,Object oldObj,boolean reset){
        boolean flag = false;

        if (newObj != null && oldObj != null){
            Map<String, Object> newMap = getReflexMap(newObj);
            Map<String, Object> oldMap = getReflexMap(oldObj);

            int diff = 0;
            for (String field : newMap.keySet()) {
                if (compareFields.contains(field)){

                    Object newFieldValue = newMap.get(field);
                    Object oldFieldValue = oldMap.get(field);
                    if (newFieldValue != null && oldFieldValue != null){
                        if (newFieldValue.equals(oldFieldValue)){
                            if (reset){
                                newMap.put(field,null);
                                oldMap.put(field,null);
                            }
                        }else {
                            diff ++;
                        }
                    }else {
                        if (!(newFieldValue == null && oldFieldValue == null)){
                            diff ++;
                        }
                    }
                }
            }

            if (diff > 0){
                setReflexObj(newMap, newObj);
                setReflexObj(oldMap, oldObj);
                flag = true;
            }else {
                flag = false;
            }
        }else {
            if (newObj == null && oldObj == null){
                flag = false;
            }else {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 获取对象的属性名 + 属性值
     * @param obj
     * @return
     */
    public  Map<String,Object> getReflexMap(Object obj){
        Map<String,Object> map = new HashMap<>();

        Class<? extends Object> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String fieldName = declaredField.getName();
            Object fieldValue = null;
            try {
                fieldValue = declaredField.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            map.put(fieldName,fieldValue);
        }
        return map;
    }


    /**
     * 设置 对象的 属性名+属性值
     * @param map
     * @return
     */
    public  Object setReflexObj(Map<String,Object> map,Object obj){
        Set<String> fieldSet = map.keySet();
        Class<? extends Object> aClass = obj.getClass();
        for (String field : fieldSet) {
            try {
                Field declaredField = aClass.getDeclaredField(field);
                declaredField.setAccessible(true);
                declaredField.set(obj,map.get(field));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private String getFinalStr(String str,String fieldName){
        if(! "".equals(str)){
            if(str.contains(",")){
                StringBuilder sb = new StringBuilder();
                String[] arr = str.split(",");
                for (String s : arr) {
                    sb.append(",");
                    sb.append(iSysDictDataService.getDicLabel(dicMutiMaps.get(fieldName), s));
                }

                str = sb.toString().startsWith(",")?sb.toString().substring(1):sb.toString();
            }else{
                str = iSysDictDataService.getDicLabel(dicMutiMaps.get(fieldName), str);
            }
        }
        return str;
    }
}
