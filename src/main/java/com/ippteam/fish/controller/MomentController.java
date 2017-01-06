package com.ippteam.fish.controller;

import com.ippteam.fish.entity.nosql.mongodb.Comment;
import com.ippteam.fish.entity.nosql.mongodb.Moment;
import com.ippteam.fish.entity.nosql.mongodb.Report;
import com.ippteam.fish.service.MomentServiceImpl;
import com.ippteam.fish.util.api.exception.ParameterException;
import com.ippteam.fish.util.api.pojo.Result;
import com.ippteam.fish.util.api.pojo.Sign;
import com.ippteam.fish.util.api.version.ApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            return new Result(0, null, momentService.getMoment(id));
        } catch (IllegalArgumentException e) {
            throw new ParameterException(EXCEPTION_REQUEST_ID_INVALID);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/{id}/report", method = RequestMethod.POST)
    public Result report(@PathVariable("id") String id, @RequestBody Report report) throws Exception {
        report.setToId(id);
        momentService.report(report);
        return new Result(0, null, true);
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping("/moments")
    public Result moments(@RequestParam double longitude, @RequestParam double latitude) throws Exception {
        return new Result(0, null, momentService.getMoments(longitude, latitude));
    }

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Moment moment, HttpServletRequest request) {
        Sign sign = (Sign) request.getAttribute(REQUEST_ATTRIBUTE_SIGN);
        moment.setPublisher(authenticationService.getIdentify(sign.getToken()));
        moment.setDate(new Date());
        momentService.addMoment(moment);
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
