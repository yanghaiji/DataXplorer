package com.javayh.agent.core.interceptor;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.constant.LoggerType;
import com.javayh.agent.common.context.TraceContext;
import com.javayh.agent.common.utils.IpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-07-05
 */
@Slf4j
public class AgentLogFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate tmp) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String url = tmp.url();
            String query = tmp.queryLine();
            String body = Arrays.toString(tmp.body());
            String method = tmp.method();
            LoggerCollector collector = LoggerCollector.builder()
                    .url(url).query(query).ip(IpUtil.getIpAddr(request))
                    .type(LoggerType.FEIGN.value()).method(method)
                    .body(body)
                    .createTime(new Date()).traceId(TraceContext.getTraceId()).build();
            log.info("AgentLogFeign ==> {}", collector);
        }
    }
}
