package com.javayh.agent.core.utils;

import com.javayh.agent.core.constant.AgentConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haiji
 */
public class IpAddressUtil {


    /**
     * 获取用户真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String rip = request.getRemoteAddr();
        for (String header : AgentConstant.HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                rip = ip;
                break;
            }
        }
        int pos = rip.indexOf(',');
        if (pos >= 0) {
            rip = rip.substring(0, pos);
        }
        return rip;
    }

}