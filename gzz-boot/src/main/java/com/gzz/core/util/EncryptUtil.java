package com.gzz.core.util;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 加密解密
 *
 * @author G-m
 */
public class EncryptUtil {

    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
            -1, -1, -1};

    /**
     * 初始化HMAC密钥 MAC算法可选以下多种算法
     *
     * <pre>
     *
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     *
     * @return
     * @throws Exception
     */
    public static String BuildHmacKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Encode(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HMAC-MD5加密
     *
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static String HmacMD5(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64Decode(key), "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte s[] = mac.doFinal(data);
        return StringUtil.ByteToHex(s);
    }

    /**
     * HMAC-SHA256加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String HmacSHA256(String message, String secret) throws Exception {

        SecretKeySpec secretKey = new SecretKeySpec(Base64Decode(secret), "HmacSHA256");
        // Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] bytes = mac.doFinal(message.getBytes());

        return StringUtil.ByteToHex(bytes);
    }

    /**
     * BASE64加密
     *
     * @param data
     * @return
     */
    public static String Base64Encode(byte[] data) {
        StringBuffer sb = new StringBuffer();
        int len = data.length;
        int i = 0;
        int b1, b2, b3;

        while (i < len) {
            b1 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
                sb.append("==");
                break;
            }
            b2 = data[i++] & 0xff;
            if (i == len) {
                sb.append(base64EncodeChars[b1 >>> 2]);
                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
                sb.append("=");
                break;
            }
            b3 = data[i++] & 0xff;
            sb.append(base64EncodeChars[b1 >>> 2]);
            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
            sb.append(base64EncodeChars[b3 & 0x3f]);
        }
        return sb.toString();
    }

    /**
     * BASE64解密
     *
     * @param str
     * @return
     */
    public static byte[] Base64Decode(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;

        while (i < len) {
            /* b1 */
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1)
                break;
            /* b2 */
            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1)
                break;
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
            /* b3 */
            do {
                b3 = data[i++];
                if (b3 == 61)
                    return buf.toByteArray();
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1)
                break;
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
            /* b4 */
            do {
                b4 = data[i++];
                if (b4 == 61)
                    return buf.toByteArray();
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1)
                break;
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
        return buf.toByteArray();
    }

    /**
     * MD5加密
     *
     * @param origin 待加密串
     * @return
     */
    public static String MD5Encode(String origin) {
        return MD5Encode(origin, "UTF-8");
    }

    /**
     * MD5加密
     *
     * @param origin  待加密串
     * @param charset 字符编码 UTF-8,GBK
     * @return
     */
    public static String MD5Encode(String origin, String charset) {
        try {
            byte[] tmps = origin.getBytes(charset);
            return Encode(tmps, "MD5");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA加密
     *
     * @param origin 待加密串
     * @return
     */
    public static String SHAEncode(String origin) {
        return SHAEncode(origin, "UTF-8");
    }

    /**
     * SHA加密
     *
     * @param origin  待加密串
     * @param charset 字符编码 UTF-8,GBK
     * @return
     */
    public static String SHAEncode(String origin, String charset) {
        try {
            byte[] tmps = origin.getBytes(charset);
            return Encode(tmps, "SHA");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * SHA256加密
     *
     * @param origin 待加密串
     * @return
     */
    public static String SHA256Encode(String origin) {
        return SHA256Encode(origin, "UTF-8");
    }

    /**
     * SHA256加密
     *
     * @param origin  待加密码串
     * @param charset 字符编码 UTF-8,GBK
     * @return
     */
    public static String SHA256Encode(String origin, String charset) {
        try {
            byte[] tmps = origin.getBytes(charset);
            return Encode(tmps, "SHA-256");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密
     *
     * @param origin 待加密码串
     * @return
     */
    public static String AESEncode(String origin) {
        return SHA256Encode(origin, "UTF-8");
    }

    /**
     * AES加密
     *
     * @param origin  待加密码串
     * @param charset 字符编码 UTF-8,GBK
     * @return
     */
    public static String AESEncode(String origin, String charset) {
        try {
            byte[] tmps = origin.getBytes(charset);
            return Encode(tmps, "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param origin    待加密串
     * @param algorithm 加密方式 MD5/SHA-1/DSA/DESede/DES/Diffie-Hellman
     * @return
     */
    public static String Encode(byte[] origin, String algorithm) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(origin);
            return StringUtil.ByteToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URL加密成URL
     *
     * @param source
     * @return
     */
    public static String UrlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 新浪的短域名
     *
     * @param url
     * @return
     */
    public static String UrlToShort(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "wuguowei";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        // 对传入网址进行 MD5 加密
        String hex = MD5Encode(key + url);

        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl.toString();
    }

    /**
     * 签名生成方法
     *
     * @param parameters
     * @param key
     * @return
     */
    public static String Signature(SortedMap<Object, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && "".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        String sign = MD5Encode(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 计算CRC16校验码
     *
     * @param data 需要校验的字符串
     * @return 校验码
     */
    public static String CRC16Check(String data) {
        data = data.replace(" ", "");
        int len = data.length();
        if (!(len % 2 == 0))
            return "0000";

        int num = len / 2;
        byte[] para = new byte[num];
        for (int i = 0; i < num; i++) {
            System.out.println(data.substring(i * 2, 2 * (i + 1)));
            int value = Integer.valueOf(data.substring(i * 2, 2 * (i + 1)), 16);
            para[i] = (byte) value;
        }
        System.out.println("========================");
        return CRC16Check(para);
    }

    /**
     * 计算CRC16校验码
     *
     * @param bytes 字节数组
     * @return {@link String} 校验码
     * @since 1.0
     */
    public static String CRC16Check(byte[] bytes) {
        // CRC寄存器全为1
        int CRC = 0x0000ffff;
        // 多项式校验值
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        // 结果转换为16进制
        String result = Integer.toHexString(CRC).toUpperCase();
        if (result.length() != 4) {
            StringBuffer sb = new StringBuffer("0000");
            result = sb.replace(4 - result.length(), 4, result).toString();
        }
        // 交换高低位
        return result.substring(2, 4) + result.substring(0, 2);
    }
}