package com.ippteam.fish.controller.manager;

import com.ippteam.fish.controller.BaseController;
import com.ippteam.fish.entity.User;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import com.ippteam.fish.pojo.Login;
import com.ippteam.fish.service.ManagerServiceImpl;
import com.ippteam.fish.service.UserService;
import com.ippteam.fish.util.Verify;
import com.ippteam.fish.util.api.BusinessStatus;
import com.ippteam.fish.util.api.exception.BusinessException;
import com.ippteam.fish.util.api.exception.ParameterException;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.ippteam.fish.util.Final.EXCEPTION_REQUEST_PARAMER_INVALID;
import static com.ippteam.fish.util.Final.REQUEST_ATTRIBUTE_SIGN;

/**
 * Created by isunimp on 16/12/29.
 */

@Controller
@RequestMapping("/api/{version}/manager")
public class ManagerController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    ManagerServiceImpl managerService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Login login, HttpServletRequest request) {
        if (!Verify.string(login.getAccount()) ||
                !Verify.string(login.getPassword())) {
            throw new ParameterException(EXCEPTION_REQUEST_PARAMER_INVALID);
        }

        User user = userService.login(login.getAccount(), login.getPassword());
        if (user == null) {
            throw new BusinessException(BusinessStatus.USERNAME_OR_PASSWORD_INVALID);
        }

        // 检查是否是管理员
        if (!user.getManager()) {
            throw new BusinessException(BusinessStatus.NOT_ADMINISTRATOR);
        }

        String token = authenticationService.certificate(user.getId().toString(), true);
        if (!Verify.string(token)) {
            throw new BusinessException(BusinessStatus.UNKNOWN_ERROR);
        }
        return new Result(0, null, token);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public Result reports(HttpServletRequest request) {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        String token = sign.getToken();
        if (authenticationService.isManager(token)) {
            throw new BusinessException(BusinessStatus.PERMISSION_DENIED);
        }
        List<Report> reports = managerService.reports();
        return new Result(0, null, reports);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/report/{id}", method = RequestMethod.POST)
    public Result report(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        String token = sign.getToken();
        if (authenticationService.isManager(token)) {
            throw new BusinessException(BusinessStatus.PERMISSION_DENIED);
        }
        Report report = managerService.reportById(id);
        return new Result(0, null, report);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/report/{id}/handled", method = RequestMethod.POST)
    public Result handled(@PathVariable(value = "id") String id, @RequestParam boolean banned, HttpServletRequest request) throws Exception {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        String token = sign.getToken();
        if (authenticationService.isManager(token)) {
            throw new BusinessException(BusinessStatus.PERMISSION_DENIED);
        }
        managerService.handled(id, banned);
        return new Result(0, null, true);
    }
}
