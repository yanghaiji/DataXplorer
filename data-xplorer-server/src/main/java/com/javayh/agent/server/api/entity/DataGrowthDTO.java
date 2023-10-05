package com.javayh.agent.server.api.entity;

import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-26
 */
@Data
public class DataGrowthDTO {


    private List<String> dates;

    private List<String> appName;

    private List<List<Double>> total;
}
