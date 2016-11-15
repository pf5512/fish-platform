package com.ippteam.fish.util.api;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import jodd.JoddDefault;
import jodd.io.StreamUtil;

/**
 *
 * 封装HttpServletRequest，
 *
 * Created by isunimp on 16/11/3.
 */
public class BodyReaderWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderWrapper(HttpServletRequest request)
            throws IOException {
        super(request);
        body = StreamUtil.readBytes(request.getReader(), JoddDefault.encoding);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            public int read() throws IOException {
                return bais.read();
            }

            public boolean isFinished() {
                return false;
            }

            public boolean isReady() {
                return false;
            }

            public void setReadListener(ReadListener readListener) {
            }
        };
    }
}