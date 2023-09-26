package com.javayh.agent.server.api.entity;

import lombok.Data;

/**
 * @author haiji
 */
@Data
public class UrlDataDTO {

    /**
     * url
     */
    private String url;

    /**
     * 访问的频次
     */
    private Integer requests;


}
