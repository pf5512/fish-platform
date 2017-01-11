package com.ippteam.fish.pojo;

import com.ippteam.fish.util.aes.AESHelper;
import com.ippteam.fish.util.api.exception.CertificationException;
import org.apache.log4j.Logger;

import static com.ippteam.fish.util.Final.EXCEPTION_AES_DECRYPT_FAIL;

/**
 * Created by isunimp on 16/11/15.
 */
public class RegNew {
    String account;
    String password;
    RegisterWay registerWay;
    String authCode;

    private static Logger logger = Logger.getLogger(Login.class);

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

    public String getPasswordPlain(String secretKey) {
        try {
            return AESHelper.decryptToStr(this.getPassword(), secretKey);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new CertificationException(EXCEPTION_AES_DECRYPT_FAIL);
        }
    }
}