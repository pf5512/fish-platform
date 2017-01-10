package com.ippteam.fish.util.aes;

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

    /**
     * 加密
     *
     * @param content   需要加密的内容(原文)
     * @param secretKey 加密密码
     * @return
     */
    public static byte[] encrypt(byte[] content, String secretKey) throws IllegalBlockSizeException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        // 注意，为了能与 iOS 统一
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        return cipher.doFinal(content);
    }

    /**
     * 解密
     *
     * @param cipher    待解密内容(密文)
     * @param secretKey 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] cipher, String secretKey) throws InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        byte[] enCodeFormat = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");

        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);

        Cipher _cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        _cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        return _cipher.doFinal(cipher);
    }
}
