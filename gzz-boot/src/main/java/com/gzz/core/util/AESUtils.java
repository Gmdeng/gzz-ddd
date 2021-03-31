package com.gzz.core.util;
import org.springframework.util.Base64Utils;

import java.util.Base64;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES工具类，密钥必须是16位字符串
 * 会生成密匙，原文加密，可以根据密匙进行解密
 */
public class AESUtils {

    /**偏移量,必须是16位字符串*/
    private static final String IV_STRING = "16-Bytes--String";

    /**
     * 默认的密钥
     */
    public static final String DEFAULT_KEY = "1bd83b249a414036";


    // 加密算法 AES
    private static final String KEY_ALGORITHM = "AES";
    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHM_STR = "AES/CBC/PKCS5Padding";
    // AES supports 128, 192 and 256 bit keys, so the number of bytes needs to be 16, 24, or 32
    private static final int INIT_SIZE = 128;

    private static final String CHARSET = "utf-8";
    /**
     * 产生随机密钥(这里产生密钥必须是16位)
     */
    public static String generateKey() {
        String key = UUID.randomUUID().toString();
        key = key.replace("-", "").substring(0, 16);// 替换掉-号
        return key;
    }

    public static String encryptData(String key, String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptData(String key, String content) {
        try {
            // base64 解码
            byte[] encryptedBytes = Base64.getDecoder().decode(content);
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);
            return new String(result, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param content 加密的字符串
     * @param key     key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key) throws Exception {
        if (key.length() != 16) {
            throw new Exception("ASE密钥格式不对");
        }
        KeyGenerator gen = KeyGenerator.getInstance(KEY_ALGORITHM);
        gen.init(INIT_SIZE);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        //iv 与key一样
        byte[] keyBytes = key.getBytes(CHARSET);
        byte[] ivBytes = key.getBytes(CHARSET);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM), new IvParameterSpec(ivBytes));
        byte[] b = cipher.doFinal(content.getBytes(CHARSET));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.getEncoder().encodeToString(b);

    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param key        解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String key) throws Exception {
        if (key.length() != 16) {
            throw new Exception("ASE密钥格式不对");
        }
        KeyGenerator gen = KeyGenerator.getInstance(KEY_ALGORITHM);
        gen.init(INIT_SIZE);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        byte[] keyBytes = key.getBytes(CHARSET);
        byte[] ivBytes = key.getBytes(CHARSET);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, KEY_ALGORITHM), new IvParameterSpec(ivBytes));
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static void main(String[] args) {
        //加密
        String key = generateKey();
        String str = encryptData(key, "eyond黄家驹");
        String _str = AESUtils.decryptData(key, str);
        System.out.println("生成key:"+key);
        System.out.println("aes加密后: " + str);
        System.out.println("aes解密后: " + _str);


        String key1 = "fa9353c6179dfbfa";
        try {
            String s = encrypt("123423", key1);
            System.out.println(s);
            String ss = decrypt(s, key1);
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}