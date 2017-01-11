package com.ippteam.fish.pojo;

import com.ippteam.fish.util.aes.AESHelper;
import com.ippteam.fish.util.api.exception.CertificationException;
import org.apache.log4j.Logger;

import static com.ippteam.fish.util.Final.EXCEPTION_AES_DECRYPT_FAIL;

/**
 * Created by isunimp on 16/11/14.
 */
public class Login {
    String account;
    String password;
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
