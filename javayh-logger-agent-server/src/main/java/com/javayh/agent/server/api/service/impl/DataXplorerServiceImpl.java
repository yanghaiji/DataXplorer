package com.javayh.agent.server.api.service.impl;

import com.javayh.agent.server.api.entity.DataGrowthDTO;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.api.service.DataXplorerService;
import com.javayh.agent.server.logger.service.DataXplorerLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
@Service
public class DataXplorerServiceImpl implements DataXplorerService {

    @Autowired
    private DataXplorerLoggerService dataXplorerLoggerService;


    /**
     * 获取top 10 url
     *
     * @return
     */
    @Override
    public List<UrlDataDTO> getTop10Urls() {
        return dataXplorerLoggerService.getTop10Urls();
    }

    /**
     * 常用微服务
     *
     * @return
     */
    @Override
    public List<MicroservicesDataDTO> coreBusinessMicroservices() {
        return dataXplorerLoggerService.coreBusinessMicroservices();
    }

    /**
     * 微服务报错率
     */
    @Override
    public List<MicroservicesDataDTO> serviceErrorRate() {
        return dataXplorerLoggerService.serviceErrorRate();
    }

    @Override
    public DataGrowthDTO dataGrowth() {
        List<MicroservicesDataDTO> dataDTOS = dataXplorerLoggerService.dataGrowth();
        List<String> collect = dataDTOS.stream().map(MicroservicesDataDTO::getDate).distinct().sorted().collect(Collectors.toList());
        DataGrowthDTO dto = new DataGrowthDTO();
        dto.setDates(collect);
        List<String> appName = new LinkedList<>();
        List<List<Double>> total = new LinkedList<>();
        Map<String, List<MicroservicesDataDTO>> dataMap = dataDTOS.stream().collect(Collectors.groupingBy(MicroservicesDataDTO::getAppName));
        dataMap.forEach((k, v) -> {
            appName.add(k);
            List<Double> data = new LinkedList<>();
            v.forEach(o->{
                data.add(o.getTotal());
            });
            total.add(data);

        });
        dto.setAppName(appName);
        dto.setTotal(total);
        return dto;
    }
}
