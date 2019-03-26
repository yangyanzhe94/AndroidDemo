package com.yyz.base.utils;

import android.util.Base64;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用户信息加解密
 * Created by Administrator on 2016/3/10.
 */
public class AESUserUtils {
    private static String SECRET = "r87EUq3t5dIID2Kv";
    private static String IV = "iW4qOsr8d5DKS6wD";
    private static String CIPHER = "AES/CBC/PKCS7Padding";

    public static String encrypt(String source) {
        try {
            if (SECRET == null || SECRET.length() != 16) {
                return null;
            }
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
            byte[] encrypted = cipher.doFinal(source.getBytes());
            return Base64.encodeToString(encrypted, Base64.CRLF);
        } catch (Exception e) {
            return source;
        }
    }

    public static String decrypt(String source) {
        if (SECRET == null || SECRET.length() != 16) {
            return null;
        }
        try {
            byte[] raw = SECRET.getBytes("ASCII");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
            byte[] encrypted1 = Base64.decode(source, Base64.CRLF);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception ex) {
            return source;
        }
    }

    /**
     * 环信解密，环信接收到创建群组或者禁言信息
     * @param source
     * @return
     */
    public static String decryptHx(String source) {
        String SECRET = "N8PmUqf7t7dE3G6b";
        String IV = "hXkqxZB76RL8M67k";
        String CIPHER = "AES/CBC/PKCS7Padding";
        if (SECRET == null || SECRET.length() != 16) {
            return null;
        }
        try {
            byte[] raw = SECRET.getBytes("ASCII");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
            byte[] encrypted1 = Base64.decode(source, Base64.DEFAULT );
            byte[] original = cipher.doFinal(encrypted1);
            if(original.length<=0){
                return source;
            }else{

                return new String(original);
            }
        } catch (Exception ex) {
            return source;
        }
    }
}
