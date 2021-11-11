package com.snaker.frame.controller.mytest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        List<Bom> BomList = getBomList();
        Map<String,String> childCodemap = new HashMap<String,String>();
        for (Bom Bom : BomList) {
            String parentCode = Bom.getParentCode();//父级物料编码
            String childCode = Bom.getChildCode();//组件物料编码

            if(childCodemap.containsKey(childCode)){
                //若Map中已存在组件物料编码，则父级物料编码已;隔开存储
                String parentCodeValue = childCodemap.get(childCode);
                if(childCodemap.containsKey(parentCode)){
                    parentCode = getParentCode(parentCode,childCodemap.get(parentCode));
                }
                parentCodeValue = parentCodeValue.concat(";").concat(parentCode);
                childCodemap.put(childCode,parentCodeValue);
            }else{
                //若不在组件物料编码，若已存在父级物料编码，则进行拼接祖迹ID
                if(childCodemap.containsKey(parentCode)){
                    String pCode = getParentCode(parentCode,childCodemap.get(parentCode));
                    childCodemap.put(childCode,pCode);
                }else{
                    //其他情况，存到Map中
                    childCodemap.put(childCode,parentCode);
                }
            }
        }

        //遍历输出Map中的数据
        for (String s : childCodemap.keySet()) {
            if("B".equals(s)){
                System.out.println(s + ":" + childCodemap.get(s));
            }
        }

    }

    /**
     *
     * @param parentCode
     * @param pCode
     * @return
     */
    public static String getParentCode(String parentCode,String pCode){
        String pCodeMiddle = ";";
        if(pCode.contains(";")){
            String pCodeStr[] = pCode.split(";");
            for (String code : pCodeStr) {
                pCodeMiddle = pCodeMiddle.concat(code).concat("-").concat(parentCode).concat(";");
            }
            pCode = pCodeMiddle.substring(1,pCodeMiddle.length()-1);
        }else{
            pCode = pCode.concat("-").concat(parentCode);
        }

        return pCode;
    }

    public static List<Bom> getBomList(){
        Bom Bom00 = new Bom("A00K1","K","M");
        Bom Bom01 = new Bom("A00K2","K","Q");
        Bom Bom1 = new Bom("A00M","M","A");
        Bom Bom2 = new Bom("A00Q","Q","A");
        Bom Bom3 = new Bom("A00B","A","B");
        Bom Bom4 = new Bom("A00C1","P","B");

        List<Bom> list = new ArrayList<Bom>(10);
        list.add(Bom00);
        list.add(Bom01);
        list.add(Bom1);
        list.add(Bom2);
        list.add(Bom3);
        list.add(Bom4);

        return list;
    }
}
