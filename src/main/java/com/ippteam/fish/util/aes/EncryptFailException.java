package com.ippteam.fish.util.aes;

import java.security.GeneralSecurityException;

/**
 * Created by isunimp on 17/1/10.
 */
public class EncryptFailException extends GeneralSecurityException {
    public EncryptFailException(String msg) {
        super(msg);
    }
}
