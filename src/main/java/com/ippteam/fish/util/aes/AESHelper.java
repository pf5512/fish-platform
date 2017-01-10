package com.ippteam.fish.util.aes;

import com.ippteam.fish.util.Convert;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by isunimp on 17/1/10.
 */
public class AESHelper {

    public static String newSecretKey() {
        return com.ippteam.fish.util.Random._32String();
    }

    /**
     * 加密字符串
     *
     * @param content   待加密字符串
     * @param secretKey 秘钥
     * @return 16进制字符串
     * @throws UnsupportedEncodingException
     * @throws EncryptFailException
     */
    public static String encryptToHex(String content, String secretKey) throws UnsupportedEncodingException, EncryptFailException {
        return encryptToHex(content.getBytes("UTF-8"), secretKey);
    }

    /**
     * 加密buff
     *
     * @param content   待加密buff
     * @param secretKey 秘钥
     * @return 16进制字符串
     * @throws EncryptFailException
     */
    public static String encryptToHex(byte[] content, String secretKey) throws EncryptFailException {
        byte[] buff = encryptToBuff(content, secretKey);
        return Convert.buffToHexStr(buff);
    }

    /**
     * 加密字符串
     *
     * @param content   待加密字符串
     * @param secretKey 秘钥
     * @return buff
     * @throws UnsupportedEncodingException
     * @throws EncryptFailException
     */
    public static byte[] encryptToBuff(String content, String secretKey) throws UnsupportedEncodingException, EncryptFailException {
        return encryptToBuff(content.getBytes("UTF-8"), secretKey);
    }

    /**
     * 加密buff
     *
     * @param content   待加密buff
     * @param secretKey 秘钥
     * @return buff
     * @throws EncryptFailException
     */
    public static byte[] encryptToBuff(byte[] content, String secretKey) throws EncryptFailException {
        try {
            return AES.encrypt(content, secretKey);
        } catch (IllegalBlockSizeException e) {
            throw new EncryptFailException("Detailed Exception: IllegalBlockSizeException");
        } catch (BadPaddingException e) {
            throw new EncryptFailException("Detailed Exception: BadPaddingException");
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptFailException("Detailed Exception: NoSuchAlgorithmException");
        } catch (NoSuchPaddingException e) {
            throw new EncryptFailException("Detailed Exception: NoSuchPaddingException");
        } catch (InvalidKeyException e) {
            throw new EncryptFailException("Detailed Exception: InvalidKeyException");
        } catch (InvalidAlgorithmParameterException e) {
            throw new EncryptFailException("Detailed Exception: InvalidAlgorithmParameterException");
        }
    }

    /**
     * 解密
     *
     * @param hexStr    转为16进制字符串的密文
     * @param secretKey 秘钥
     * @return 原文字符串
     * @throws DecryptFailException
     */
    public static String decryptToStr(String hexStr, String secretKey) throws DecryptFailException, ConvertFailException {
        byte[] buff;
        try {
            buff = Convert.hexStrToBuff(hexStr);
        } catch (NumberFormatException e) {
            throw new ConvertFailException("hex str is invalid");
        }

        return decryptToStr(buff, secretKey);
    }

    /**
     * 解密
     *
     * @param cipher    密文
     * @param secretKey 秘钥
     * @return 原文字符串
     * @throws DecryptFailException
     */
    public static String decryptToStr(byte[] cipher, String secretKey) throws DecryptFailException {
        if (cipher == null || cipher.length == 0) {
            throw new DecryptFailException("hex str convert buff fail");
        }
        byte[] buff = decryptToBuff(cipher, secretKey);
        return new String(buff);
    }

    /**
     * 解密
     *
     * @param hexStr    转为16进制字符串的密文
     * @param secretKey 秘钥
     * @return 原文buff
     * @throws DecryptFailException
     */
    public static byte[] decryptToBuff(String hexStr, String secretKey) throws DecryptFailException {
        byte[] buff = Convert.hexStrToBuff(hexStr);
        return decryptToBuff(buff, secretKey);
    }

    /**
     * 解密
     *
     * @param cipher    密文
     * @param secretKey 秘钥
     * @return 原文buff
     * @throws DecryptFailException
     */
    public static byte[] decryptToBuff(byte[] cipher, String secretKey) throws DecryptFailException {
        try {
            return AES.decrypt(cipher, secretKey);
        } catch (InvalidKeyException e) {
            throw new DecryptFailException("Detailed Exception: InvalidKeyException");
        } catch (NoSuchAlgorithmException e) {
            throw new DecryptFailException("Detailed Exception: NoSuchAlgorithmException");
        } catch (NoSuchPaddingException e) {
            throw new DecryptFailException("Detailed Exception: NoSuchPaddingException");
        } catch (InvalidAlgorithmParameterException e) {
            throw new DecryptFailException("Detailed Exception: InvalidAlgorithmParameterException");
        } catch (IllegalBlockSizeException e) {
            throw new DecryptFailException("Detailed Exception: IllegalBlockSizeException");
        } catch (BadPaddingException e) {
            throw new DecryptFailException("Detailed Exception: BadPaddingException");
        } catch (UnsupportedEncodingException e) {
            throw new DecryptFailException("Detailed Exception: UnsupportedEncodingException");
        }
    }
}
