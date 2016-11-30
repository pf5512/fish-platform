package com.ippteam.fish.controller;

import com.ippteam.fish.service.FileServiceImpl;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

import static com.ippteam.fish.util.Final.REQUEST_ATTRIBUTE_SIGN;

/**
 * Created by isunimp on 16/11/24.
 */
@Controller("FileController")
@RequestMapping("/api/{version}/file")
public class FileController extends BaseController {

    @Autowired
    FileServiceImpl fileService;

    @ApiVersion(1)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void file(@PathVariable String id, HttpServletResponse response) throws Exception {
        ServletOutputStream stream = response.getOutputStream();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=" + new String(id.getBytes("gbk"), "ISO8859-1"));
        InputStream inputStream = fileService.file(id);
        byte[] bytes = new byte[1];
        while (-1 != inputStream.read(bytes, 0, 1)) {
            stream.write(bytes);
        }
        stream.flush();
        stream.close();
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam CommonsMultipartFile[] files, HttpServletRequest request) throws Exception {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        List<String> ids = new ArrayList<String>();
        for (CommonsMultipartFile file : files) {
            InputStream inputStream = file.getInputStream();
            byte[] bytes = file.getBytes();
            int length = bytes.length;
            String fileName = file.getName();
            String owner = authenticationService.getIdentify(sign.getToken());
            String id = fileService.upload(inputStream, fileName, owner);
            ids.add(id);
        }
        return new Result(0, null, ids);
    }
}