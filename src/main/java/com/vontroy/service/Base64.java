package com.vontroy.service;

public class Base64 {
    private static final String ADX_BASE64CHAR = "oMq_rc1fuEbtXgBOkKlIxyzNFYd2Js0D-Tv8495A6iheCpWS3QGUamwVjRH7nLP";
    private static final char[] LOW = {0x0, 0x1, 0x3, 0x7, 0xF, 0x1F, 0x3F};
    private static final int[] ADX_BASE64VAL = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 32, -1, -1,
            30, 6, 27, 48, 36, 38, 40, 59, 35, 37, -1, -1, -1, -1, -1, -1,
            -1, 39, 14, 44, 31, 9, 24, 50, 58, 19, 28, 17, 61, 1, 23, 15,
            62, 49, 57, 47, 33, 51, 55, 46, 12, 25, 63, -1, -1, -1, -1, 3,
            -1, 52, 10, 5, 26, 43, 7, 13, 42, 41, 56, 16, 18, 53, 60, 0,
            45, 2, 4, 29, 11, 8, 34, 54, 20, 21, 22, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    public static String adx_base64_encode(String in_str) {
        return base64_encode(in_str, ADX_BASE64CHAR);
    }

    private static String base64_encode(String in_str, String base64char) {
        int strLen = in_str.length();
        StringBuilder outSB = new StringBuilder();
        if (strLen <= 0) {
            System.out.println("err");
            return "";
        }

        int len = strLen * 8 / 6 + (strLen % 3 > 0 ? 1 : 0);

        char b;
        int l = len;
        int n = 0;
        int in_idx = 0;

        while (l-- > 0) {
            b = 0;
            if (n > 0) {
                b |= (in_str.charAt(in_idx) & LOW[n]) << (6 - n);
                ++in_idx;
            }
            n = 6 - n;
            if (n > 0) {
                b |= (in_str.charAt(in_idx)) >> (8 - n);
                n = 8 - n;
            }
            outSB.append(base64char.charAt(b));
        }

        return outSB.toString();
    }

    public static String adx_base64_decode(String in_str) {
        return base64_decode(in_str, ADX_BASE64VAL);
    }

    private static String base64_decode(String in_str, int[] base64val) {
        int inStrLen = in_str.length();
        StringBuilder outSB = new StringBuilder();

        if (inStrLen == 0) {
            System.out.println("err");
            return "";
        }

        //TODO
        if (inStrLen == 1 && in_str.charAt(0) != 0 || inStrLen * 6 % 8 >= 6) {
            System.out.println("err");
            return "";
        }

        for (int i = 0; i < inStrLen; ++i) {
            if ((base64val[in_str.charAt(i)]) < 0) {
                return "";
            }
        }

        int l = inStrLen * 6 / 8;
        int n = 0;
        int in_idx = 0;
        while (l-- > 0) {
            if (n > 0) {
                outSB.append((base64val[in_idx] & LOW[n]) << (8 - n));
                ++in_idx;
            }
            n = 8 - n;
            if (n >= 6) {
                outSB.append(base64val[in_idx] << (n - 6));
                n -= 6;
                ++in_idx;
            }
            if (n > 0) {
                outSB.append(base64val[in_idx] >> (6 - n));
                n = 6 - n;
            }
        }

        return outSB.toString();
    }

    public static void main(String[] args) {
        String encStr = adx_base64_encode("ddd");
        String decStr = adx_base64_decode(encStr);
        System.out.println(encStr);
        System.out.println(decStr);
    }
}
