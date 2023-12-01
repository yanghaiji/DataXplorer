package com.javayh.agent.example.web;

import com.javayh.agent.common.LoggerReceived;
import com.javayh.agent.common.bean.TrackLogger;
import com.javayh.agent.common.constant.CrudEnum;
import com.javayh.agent.example.CustomEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
@Slf4j
@RestController
@RequestMapping(value = "test")
public class ExampleController {

    @GetMapping(value = "/agent")
    public String getName() {
        log.info("ewohefo");
        Map<String,Object> parameter = new HashMap<>();
        parameter.put("id",1);
        parameter.put("name","test"+1);
        LoggerReceived.received(parameter, CrudEnum.SAVE, null);
        return "JavaYh Agent";
    }


    @PostMapping(value = "/agent")
    public String getNam3(@RequestBody Map<String, Object> map) {
        TrackLogger build = TrackLogger.builder().parameter(map).type(CustomEnum.EXAMPLE).build();
        try {
            Object type = map.get("type");
            if (type.equals("ex")) {
                Integer.valueOf((Integer) type);
            }
        }catch (Exception e){
            build.setThrowable(e);
        }finally {
            LoggerReceived.received(build);
        }

        return "JavaYh Agent";
    }


    @GetMapping
    public String getName2() {
        log.info("ewohefo");
        return "JavaYh Agent";
    }
}
