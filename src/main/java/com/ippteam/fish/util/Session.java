package com.ippteam.fish.util;

import com.ippteam.fish.entity.User;
import com.ippteam.fish.util.api.entity.Credential;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by isunimp on 16/11/15.
 */
public class Session {

    public static Credential login(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        Credential credential = new Credential();
        session.setAttribute(Final.KEY_CREDENTIAL, credential);
        return credential;
    }

    public static Credential demand(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Credential credential = (Credential) session.getAttribute(Final.KEY_CREDENTIAL);
        return credential;
    }

    public static void logout(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.removeAttribute(Final.KEY_CREDENTIAL);
    }
}
