package com.javayh.agent.server.event.web;

import com.javayh.agent.common.bean.FrontendEventRequest;
import com.javayh.agent.common.context.TraceContext;
import com.javayh.agent.common.utils.Result;
import com.javayh.agent.server.logger.service.DataXplorerFrontendLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端埋点上报
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-11-03
 */
@RestController
@RequestMapping(value = "/fr/ev")
public class FrontendEventController {

    @Autowired
    private DataXplorerFrontendLoggerService frontendLoggerService;

    @GetMapping(value = "/trace")
    public Result trace() {
        return Result.ok(TraceContext.getTraceId());
    }


    @PutMapping(value = "/collect")
    public Result collect(@RequestBody FrontendEventRequest event) {
        frontendLoggerService.saveEvent(event);
        return Result.ok();
    }


}
