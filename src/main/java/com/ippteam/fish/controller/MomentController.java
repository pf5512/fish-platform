package com.ippteam.fish.controller;

import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import com.ippteam.fish.pojo.Moment;
import com.ippteam.fish.pojo.User;
import com.ippteam.fish.service.MomentServiceImpl;
import com.ippteam.fish.util.Reflection;
import com.ippteam.fish.util.api.exception.ParameterException;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ippteam.fish.util.Final.EXCEPTION_REQUEST_ID_INVALID;
import static com.ippteam.fish.util.Final.REQUEST_ATTRIBUTE_SIGN;

/**
 * Created by isunimp on 16/12/7.
 */
@Controller("MomentController")
@RequestMapping("/api/{version}/moment")
public class MomentController extends BaseController {

    @Autowired
    MomentServiceImpl momentService;

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping("/{id}")
    public Result moment(@PathVariable(value = "id") String id) throws Exception {
        try {
            com.ippteam.fish.entity.nosql.mongodb.Moment m = momentService.getMoment(id);
            com.ippteam.fish.entity.User u = userService.getUserById(new Integer(m.getPublisher()));
            Moment moment = new Moment();
            Reflection.objectValueTransfer(moment, m, true);
            User user = new User();
            Reflection.objectValueTransfer(user, u, true);
            moment.setUser(user);
            return new Result(0, null, moment);
        } catch (IllegalArgumentException e) {
            throw new ParameterException(EXCEPTION_REQUEST_ID_INVALID);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/{id}/report", method = RequestMethod.POST)
    public Result report(@PathVariable("id") String id, @RequestBody Report report, HttpServletRequest request) throws Exception {
        report.setToId(id);
        report.setInformant(this.getUserId(request).toString());
        momentService.report(report);
        return new Result(0, null, true);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping("/moments")
    public Result moments(@RequestParam double longitude, @RequestParam double latitude) throws Exception {
        List<com.ippteam.fish.entity.nosql.mongodb.Moment> moments = momentService.getMoments(longitude, latitude);
        List<Moment> momentPjs = new ArrayList<Moment>();
        for (com.ippteam.fish.entity.nosql.mongodb.Moment moment : moments) {
            Moment momentPj = new Moment();
            Reflection.objectValueTransfer(momentPj, moment, true);

            User userPj = new User();
            com.ippteam.fish.entity.User user = userService.getUserById(new Integer(moment.getPublisher()));
            Reflection.objectValueTransfer(userPj, user, true);

            momentPj.setUser(userPj);
            momentPjs.add(momentPj);
        }
        return new Result(0, null, momentPjs);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Moment moment, HttpServletRequest request) throws Exception {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        moment.setPublisher(authenticationService.getIdentify(sign.getToken()));
        moment.setDate(new Date());
        com.ippteam.fish.entity.nosql.mongodb.Moment m = new com.ippteam.fish.entity.nosql.mongodb.Moment();
        Reflection.objectValueTransfer(m, moment, true);
        momentService.addMoment(m);
        return new Result(0, null, true);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping("/like/{id}")
    public Result like(@PathVariable(value = "id") String id) {
        return new Result(0, null, momentService.like(id));
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping("/comment/{id}")
    public Result comment(@PathVariable(value = "id") String id,
                          @RequestParam String content,
                          HttpServletRequest request) {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        Comment comment = new Comment();
        comment.setTo(id);
        comment.setFrom(authenticationService.getIdentify(sign.getToken()));
        comment.setContent(content);
        comment.setDate(new Date());
        List<Comment> comments = momentService.addComment(id, comment);
        return new Result(0, null, comments);
    }
}
