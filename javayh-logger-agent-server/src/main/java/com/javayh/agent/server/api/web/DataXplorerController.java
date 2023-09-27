package com.javayh.agent.server.api.web;

import com.javayh.agent.common.utils.Result;
import com.javayh.agent.server.api.entity.DataGrowthDTO;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.api.service.DataXplorerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
@RestController
@RequestMapping(value = "/bi")
public class DataXplorerController {

    @Autowired
    private DataXplorerService dataXplorerService;

    /**
     * 查询所有服务的请求方式和来源
     *
     * @return 最终返回结构 {@link Result}
     */
    @GetMapping(value = "request/source")
    public Result queryAppRequestSource() {
        return Result.ok();
    }

    @GetMapping("/top-urls")
    public Result topUrls() {
        List<UrlDataDTO> urlData = dataXplorerService.getTop10Urls();
        return Result.ok(urlData);
    }

    /**
     * 常用的微服务
     */
    @GetMapping("/pie-chart-data")
    public Result coreBusinessMicroservices() {
        List<MicroservicesDataDTO> dataDTOS = dataXplorerService.coreBusinessMicroservices();
        return Result.ok(dataDTOS);
    }

    /**
     * 服务的报错率
     */
    @GetMapping("/service-error-rate")
    public Result serviceErrorRate() {
        List<MicroservicesDataDTO> dataDTOS = dataXplorerService.serviceErrorRate();
        return Result.ok(dataDTOS);
    }

    /**
     * 服务的进7日增长
     */
    @GetMapping("/data-growth")
    public Result dataGrowth() {
        DataGrowthDTO dataDTOS = dataXplorerService.dataGrowth();
        return Result.ok(dataDTOS);
    }
}
