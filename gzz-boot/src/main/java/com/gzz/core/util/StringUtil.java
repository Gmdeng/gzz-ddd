package com.gzz.core.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Formatter;

/**
 * 字符串工具类
 */
public class StringUtil {
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
            "E", "F" };

    /**
     * byte数组转16进制字符串
     *      高位在前
     * @param hash
     * @return
     */
    public static String ToHex(byte hash[]) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            //算法一
            int n = hash[i];
            if (n < 0) n += 256;
            int d1 = n / 16; // 等于 (n& 0x0f0) >>> 4 | (n >>> 4) & 0x0f
            int d2 = n % 16; // 等于 n & 0X0F
            buf.append(hexDigits[d1] + hexDigits[d2]);
            //算法二
            //if ((hash[i] & 0xff) < 0x10) {
            //	buf.append("0");
            //}
            //buf.append(Long.toString(hash[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * byte数组转16进制
     * 高位在前
     * @param hash
     * @return
     */
    public static String ByteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
            formatter.format("%02x", b);
        String result = formatter.toString();
        formatter.close();
        return result.toUpperCase();
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对象不为空
     * @param value 待检查的对象
     * @return
     */
    public static boolean isNotEmpty(Object value) {
        if(value == null) return false;
        return !isEmpty(value.toString());
    }

    /**
     * 对象为空
     * @param value 待检查的对象
     * @return
     */
    public static boolean isNull(Object value) {
        if(value == null) return true;
        return false;
    }
    /**
     * 随机生成纯字母
     *
     * @param count
     *            长度
     * @return
     */
    public static String randomChar(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    /**
     * 随机生成数字各字母
     *
     * @param count
     *            长度
     * @return
     */
    public static String randomCharNum(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    /**
     * 随机生成数字
     *
     * @param count
     *            长度
     * @return
     */
    public static String randomNum(int count) {
        return RandomStringUtils.randomNumeric(count);
    }

}
