package com.ippteam.fish.pojo;

import com.ippteam.fish.util.aes.AES;
import com.ippteam.fish.util.Convert;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.exception.BusinessException;
import com.ippteam.fish.util.api.interceptor.SignCertificate;
import org.apache.log4j.Logger;

import static com.ippteam.fish.util.api.BusinessStatus.*;

/**
 * Created by isunimp on 16/11/15.
 */
public class RegNew {
    String account;
    String password;
    RegisterWay registerWay;
    String authCode;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RegisterWay getRegisterWay() {
        return registerWay;
    }

    public void setRegisterWay(RegisterWay registerWay) {
        this.registerWay = registerWay;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getPasswordPlain(String securityKey) throws Exception {
        // 将16进制字符串转换为buffer
        Logger logger = Logger.getLogger(SignCertificate.class);

        byte[] encryptedBuff;
        try {
            encryptedBuff = Convert.hexStrToBuff(password);
            if (!Verify.buffer(encryptedBuff)) {
                throw new BusinessException(AES_DECRYPT_FAIL);
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessException(AES_DECRYPT_FAIL);
        }
        // 解密
        byte[] decryptedBuff = AES.decrypt(encryptedBuff, securityKey);
        if (!Verify.buffer(decryptedBuff)) {
            throw new BusinessException(AES_DECRYPT_FAIL);
        }

        return new String(decryptedBuff);
    }
}