package com.vontroy.common.abe_source.component;

import com.vontroy.common.abe_source.abe.ABE;
import com.vontroy.common.abe_source.abe.Key;
import com.vontroy.common.abe_source.schemes.ZJLW15;

public class ParameterCenter {

    private static ABE scheme = new ZJLW15();

    public static String[] getParameter() {

        String[] objKeys = new String[2];
        Key[] keys = scheme.setup();
        objKeys[0] = keys[0].toJSONString();
        objKeys[1] = keys[1].toJSONString();
        return objKeys;
    }

}
