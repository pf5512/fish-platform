package com.ippteam.fish.controller;

import com.ippteam.fish.service.AuthCodeService;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.exception.BusinessException;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.ippteam.fish.util.api.BusinessStatus.EMAIL_INVALID;

/**
 * Created by isunimp on 16/11/17.
 */

@Controller("AuthCodeController")
@RequestMapping("/api/{version}/authcode")
public class AuthCodeController {

    @Autowired
    AuthCodeService authCodeService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public Result email(@RequestParam String email) throws Exception {
        if (!Verify.email(email)) {
            throw new BusinessException(EMAIL_INVALID);
        }
        return new Result(0, null, authCodeService.generate(email));
    }

    @ApiVersion(1)
    @RequestMapping(value = "/phone", method = RequestMethod.GET)
    public Result phone(@RequestParam String phone) {
        throw new BusinessException(BusinessStatus.UNDER_CONSTRUCTING);
    }
}
