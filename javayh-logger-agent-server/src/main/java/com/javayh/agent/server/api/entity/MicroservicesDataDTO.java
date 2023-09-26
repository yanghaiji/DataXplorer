package com.javayh.agent.server.api.entity;

import lombok.Data;

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


}
