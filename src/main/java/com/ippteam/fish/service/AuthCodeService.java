package com.ippteam.fish.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * Created by isunimp on 16/11/19.
 */
public interface AuthCodeService {

    public long generate(String account);

    public boolean verify(String authCode, String account);

    public boolean delete(String authCode);
}
