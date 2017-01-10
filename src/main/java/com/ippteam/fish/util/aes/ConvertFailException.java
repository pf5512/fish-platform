package com.ippteam.fish.util.aes;

import java.security.GeneralSecurityException;

/**
 * Created by isunimp on 17/1/10.
 */
public class ConvertFailException extends GeneralSecurityException {
    public ConvertFailException() {

    }

    public ConvertFailException(String msg) {
        super(msg);
    }
}
