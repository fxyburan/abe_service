package com.vontroy.service;

import com.alibaba.fastjson.JSONObject;
import com.vontroy.common.abe_source.component.Decryptor;
import com.vontroy.common.abe_source.component.Encryptor;
import com.vontroy.common.abe_source.component.KGC;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.nio.charset.StandardCharsets;

/**
 * Created by vontroy on 7/15/17.
 */

@Path("/abe_service")
public class ABEService {
    private static KGC kgc;
    private static String publicKey;

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public static String init() {
        kgc = new KGC("center");

        //设置AttributeUniverse
        String[] attributesSet = {"school:pku", "academy:computer", "籍贯:北京", "age:130",
                "姓名:张强",
                "姓名:王芳",
                "姓名:李刚",
                "姓名:刘毅",
                "学号:1701110001",
                "学号:1701210002",
                "学号:1701210003",
                "学号:1401210002",
                "课程:密码学",
                "课程:操作系统",
                "课程:组成原理",
                "组号:os1",
                "组号:os2",
                "组号:crypto2",
                "院系:信息安全",
                "院系:软件开发",
                "身份:os助教",
                "身份:学生"
        };
        kgc.setAttributePool(attributesSet);

        publicKey = kgc.initialization();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("res", "true");

        return jsonObject.toString();
    }

    @GET
    @Path("/get_pk")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPK() {
        String jsonPK = publicKey;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("PK", jsonPK);
        return jsonObject.toString();
    }

    @POST
    @Path("/get_sk")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSK(@FormParam("attributes") String attributeJson,
                        @FormParam("sid") String sid) {
        String[] attributes = JSONObject.parseArray(attributeJson, String.class).toArray(new String[0]);

        String jsonSK = kgc.genSecretKey(attributes, sid);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("SK", jsonSK);
        return jsonObject.toString();
    }

    @POST
    @Path("/encrypt")
    @Produces(MediaType.APPLICATION_JSON)
    public String encrypt(@FormParam("message") String message,
                          @FormParam("policy") String policy,
                          @FormParam("sid") String sid) {
        Encryptor encryptor = new Encryptor(sid);
        String ciphertext = null;
        try {
            ciphertext = encryptor.encrypt(policy, publicKey, message, "abeTest".getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        if (ciphertext != null) {
            jsonObject.put("ct", ciphertext);
            return jsonObject.toString();
        }
        jsonObject.put("ct", "error");
        return jsonObject.toString();
    }

    @POST
    @Path("/decrypt")
    @Produces(MediaType.APPLICATION_JSON)
    public String decrypt(@FormParam("ct") String ciphertext,
                          @FormParam("sid") String sid,
                          @FormParam("sk") String sk) {
        Decryptor decryptor = new Decryptor(sid);
        String msg = null;
        try {
            msg = new String(decryptor.decrypt(ciphertext, sk), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        if (msg != null) {
            jsonObject.put("msg", msg);
            return jsonObject.toString();
        }
        jsonObject.put("msg", "error");
        return jsonObject.toString();
    }

//    public static void main(String[] args) throws Exception {
//        init();
//        String[] attributesSet = {"school:pku", "academy:computer", "籍贯:北京", "age:130"};
//        String jsonString = JSON.toJSONString(attributesSet);
//        System.out.println("jsonString: " + jsonString);
//        List<String> attributes = JSONObject.parseArray(jsonString, String.class);
//
//        String jsonSK = kgc.genSecretKey(attributes, publicKey);
//
//        System.out.println(jsonSK.toString());
//
//
//    }
}
