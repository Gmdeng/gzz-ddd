package com.gzz.boot.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {
    private final static  String ENCRYPT_MODE = "MD5"; //
    private final static int ENCRYPT_ITERATIONS = 2;
    /**
     * 密码加密码
     * @param passwdTxt 密码明文
     * @param salt 密码盐
     * @return
     */
    public static String EncryptPassword(String passwdTxt, String salt){
        //String password = EncryptUtil.MD5Encode(passwdTxt + "=" + salt);
        String password = new SimpleHash(ENCRYPT_MODE, passwdTxt, ByteSource.Util.bytes(salt),
                ENCRYPT_ITERATIONS).toHex();
        return password;
    }

    /**
     * 密码校验
     * @param plaintext 明文
     * @param ciphertext 密文
     * @param salt 密码盐
     * @return
     */
    public static boolean checkPWD(String plaintext, String ciphertext, String salt){
        String pwd = EncryptPassword(plaintext, salt);
        return (pwd.equalsIgnoreCase(ciphertext));
    }
}
