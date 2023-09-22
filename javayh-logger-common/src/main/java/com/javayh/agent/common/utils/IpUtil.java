package com.javayh.agent.common.utils;

import com.javayh.agent.common.constant.AgentConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author haiji
 */
public class IpUtil {


    /**
     * 获取用户真实IP地址
     *
     * @param request
     * @return
     */
    @Deprecated
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


    public static String getIpAddr(HttpServletRequest request) {

        String ips = head(request, "X-Forwarded-For");

        String realIp = head(request, "X-Real-IP");

        if (StringUtils.isNotEmpty(realIp)) {
            return realIp;
        } else if (StringUtils.isNotEmpty(ips)) {
            ips = ips.replaceAll(" ", "");
            String[] array = ips.split(",");
            return array[array.length - 1];

        } else {
            return request.getRemoteAddr();
        }
    }

    private static String head(HttpServletRequest request, String s) {
        return request.getHeader(s);
    }
}