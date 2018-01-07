package com.vontroy.common.abe_source.example;

import com.vontroy.common.abe_source.component.Decryptor;
import com.vontroy.common.abe_source.component.KGC;
import com.vontroy.common.abe_source.component.ParameterCenter;

public class Test {

    private static String objPK = "";
    private static String objMSK = "";
    private static KGC kgc = null;
    private static String targetDirURL = "C:\\Users\\vontroy\\Desktop\\tmp\\fs";

    private void initialization() {

        String[] objKey = ParameterCenter.getParameter();
        KGC kgc = new KGC("center");
        String[] attributesSet = {"school:pku", "academy:computer", "籍贯:北京", "age:130",
                "姓名:张强",
                "姓名:刘毅",
                "学号:1701110001",
                "学号:1401210002",
                "课程:密码学",
                "课程:操作系统",
                "课程:组成原理",
                "院系:信息安全系",
                "院系:软件开发系",
                "身份:os助教",
                "身份:学生"
        };
        kgc.setAttributePool(attributesSet);
        kgc.setPK(objKey[0]);
        kgc.setMSK(objKey[1]);
        Test.objPK = objKey[0];
        Test.objMSK = objKey[1];
        Test.kgc = kgc;
    }

    private String getSK(String[] attrSet, String ID) {

        return kgc.genSecretKey(attrSet, ID);
    }

    private String getCT(String policy, String fileURL, String dataOwnerID) {


        Example example = new Example();
        String ciphertextURL = null;
        try {
            ciphertextURL = example.encFile(fileURL, targetDirURL, policy, Test.objPK, "try it!".getBytes("utf-8"),
                    dataOwnerID);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ciphertextURL;
    }

    private boolean judge(String objSK, String obPK, String ID) {

        Decryptor decryptor = new Decryptor("decryptor");
        return decryptor.Judge(objSK, objPK, ID);
    }

    public void getMessage(String ciphertextURL, String objSK) {
        // obtain the plaintext
        Example example = new Example();

        boolean flag;
        try {
            flag = example.decFile(ciphertextURL, targetDirURL, objSK, "jerry");
            if (flag)
                System.out.println("Decryption Operates Successfully!");
            else
                System.out.println("Decryption Operates Unsuccessfully!");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        Test test = new Test();

        // Init
        test.initialization();

        // Obtain the SK;
        String[] attrStrings = {"school:pku", "academy:computer"};
        String userID = "Jack";
        String objSK = test.getSK(attrStrings, userID);


        // Create CT
        String dataOwnerID = "Lily";
        String policy = "(school:pku and academy:computer) or (籍贯:北京  and age:130)";
        String fileURL = "C:\\Users\\vontroy\\Desktop\\tmp\\fs\\plaintext.txt";
        String ciphertextURL = test.getCT(policy, fileURL, dataOwnerID);

        // Get Plaintext
        test.getMessage(ciphertextURL, objSK);

        // Malicious user ID

        // Decryption Key legal? userID : mID
        boolean flag = test.judge(objSK, Test.objPK, userID);

        if (flag) {

            System.out.println("This is a legal key for user:" + "olivia" + "!");

        } else {

            System.out.println("This is a illegal key for user:" + "olivia" + "!");

        }

    }

}
