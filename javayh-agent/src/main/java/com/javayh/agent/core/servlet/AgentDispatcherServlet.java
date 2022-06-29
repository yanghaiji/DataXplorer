package com.javayh.agent.core.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 自定义 DispatcherServlet 来分派 HttpServletRequestWrapper
 * https://www.cnblogs.com/keeya/p/13634015.html
 * </p>
 *
 * @author haiji
 */
public class AgentDispatcherServlet extends DispatcherServlet {

    /**
     * 包装成我们自定义的request
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new AgentHttpServletRequestWrapper(request), response);
    }
}