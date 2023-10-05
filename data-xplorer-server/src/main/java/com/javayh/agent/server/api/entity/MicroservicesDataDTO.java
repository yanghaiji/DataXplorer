package com.javayh.agent.server.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author haiji
 */
@Data
public class MicroservicesDataDTO {

    /**
     * appName
     */
    private String appName;

    /**
     * 访问的频次
     */
    private Double total;

    /**
     * 入库时间
     */
    private String date;

    /**
     * 容错率
     */
    private Double rate;


}
