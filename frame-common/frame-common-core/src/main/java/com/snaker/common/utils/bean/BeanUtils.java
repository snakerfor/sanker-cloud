package com.snaker.common.utils.bean;

import com.snaker.common.utils.reflect.ReflectUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 *
 * @author sfd
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * Bean方法名中属性名开始的下标
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /**
     * 匹配getter方法的正则表达式
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /**
     * 匹配setter方法的正则表达式
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     *
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    public static void setFieldNull(Object obj, Set<String> fields) {
        if (obj == null || CollectionUtils.isEmpty(fields)) {
            return;
        }

        Field[] allFields = obj.getClass().getDeclaredFields();
        for (int index = 0; index < allFields.length; index++) {
            Field field = allFields[index];
            String fieldName = field.getName();
            if (fields.contains(fieldName)) {
                ReflectUtils.setFieldValue(obj, fieldName, null);
            }
        }
    }

    public static void setFieldNull(Object obj, String[] fields) {
        setFieldNull(obj, new HashSet<>(Arrays.asList(fields)));
    }

    public static Field[] fetchAllFieldWithParentClass(Class tempClass) {
        List<Field> fieldList = new ArrayList<>();
        while (tempClass != null && !tempClass.getName().toLowerCase().equals("java.lang.object")) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }


    public static void copyPropertiesWithAppointProperties(Object source, Object target, String[] copyProperties) {
        HashSet<String> copyPropertiesSet = new HashSet<>(Arrays.asList(copyProperties));

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        if (!sourceClass.equals(targetClass)) throw new RuntimeException("class不一致!");

        Field[] allFields = fetchAllFieldWithParentClass(sourceClass);

        String[] ignoreFields = Arrays.stream(allFields).map(Field::getName).filter(item -> !copyPropertiesSet.contains(item)).toArray(String[]::new);

        copyProperties(source, target, ignoreFields);
    }

}
