package com.ippteam.fish.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by pactera on 16/10/31.
 */
public class AES {

    private static final String IV_STRING = "0102030405060708";

    public static String newSecretKey() {
        return com.ippteam.fish.util.Random._32String();
    }

    /**
     * 加密
     *
     * @param content     需要加密的内容
     * @param securityKey 加密密码
     * @return
     */
    public static byte[] encrypt(String content, String securityKey) throws Exception {
        if (null == securityKey || securityKey.length() != 16) {
            throw new NullPointerException("security key is invalid.");
        }

        byte[] byteContent = content.getBytes("UTF-8");

        // 注意，为了能与 iOS 统一
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = securityKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedBytes = cipher.doFinal(byteContent);
        return encryptedBytes;
    }

    /**
     * 解密
     *
     * @param content     待解密内容
     * @param securityKey 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String securityKey) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        if (null == securityKey || securityKey.length() != 16) {
            throw new NullPointerException("security key is invalid.");
        }

        byte[] enCodeFormat = securityKey.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] result = cipher.doFinal(content);
        return result;
    }
}
