package com.sap.webservice;

import javax.xml.ws.Holder;
import java.util.Date;

public class HelloWorldClient {
    public static void main(String[] argv) {
        long startTime = new Date().getTime();
        ZCSRDDS service = new ZCSZPPF016().getZCSZPPF016();
        //invoke business method
        String iDate = "";
        String IMatnr = "3070801558";
        Holder<String> eData = new Holder<String>();
        TableOfZstrRddsVeridInfo tableOfZstrRddsVeridInfo = new TableOfZstrRddsVeridInfo();
        Holder<TableOfZstrRddsVeridInfo> etVerinfo = new Holder<TableOfZstrRddsVeridInfo>(tableOfZstrRddsVeridInfo);
        Holder<String> eMessage = new Holder<String>();
        Holder<String> eMsgty = new Holder<String>();

        System.out.println("=============zppf011===============");
//        service.zppf011(etVerinfo,iDate,IMatnr,eMessage,eMsgty);
//        for (ZstrRddsVeridInfo zstrRddsVeridInfo : etVerinfo.value.getItem()) {
//            System.out.println(zstrRddsVeridInfo.matnr +":" +zstrRddsVeridInfo.verid);
//        }

        System.out.println("=============zppf013===============");
//        TableOfZstrRddsGylxInfo tableOfZstrRddsGylxInfo = new TableOfZstrRddsGylxInfo();
//        Holder<TableOfZstrRddsGylxInfo> etGylxInfo = new Holder<TableOfZstrRddsGylxInfo>(tableOfZstrRddsGylxInfo);
//        service.zppf013(etGylxInfo,iDate,IMatnr,eMessage,eMsgty);
//        for (ZstrRddsGylxInfo zstrRddsGylxInfo : etGylxInfo.value.getItem()) {
//            System.out.println(zstrRddsGylxInfo.matnr +":" +zstrRddsGylxInfo.gylxBh);
//        }

        System.out.println("=============zppf016===============");

        TableOfZstrRddsBomlist2 tableOfZstrRddsBomlist2 = new TableOfZstrRddsBomlist2();
        Holder<TableOfZstrRddsBomlist2> eBomlist = new Holder<TableOfZstrRddsBomlist2>(tableOfZstrRddsBomlist2);
        service.zppf016(eBomlist,iDate,IMatnr,eMessage,eMsgty);

        for (ZstrRddsBomlist2 zstrRddsBomlist2 : eBomlist.value.getItem()) {
            System.out.println(zstrRddsBomlist2.werks +":" +zstrRddsBomlist2.bomno +":" +zstrRddsBomlist2.bmein
            +":" +zstrRddsBomlist2.bmeng+":" +zstrRddsBomlist2.kmpme+":" +zstrRddsBomlist2.kmpmg);
        }

        long endTime = new Date().getTime();
        System.out.println("second:" + (endTime - startTime)/1000);

    }
}
