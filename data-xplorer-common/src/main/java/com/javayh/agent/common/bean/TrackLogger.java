package com.javayh.agent.common.bean;

import com.javayh.agent.common.handler.OperationHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <p>
 * 埋点信息
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-12-01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackLogger {

   private Map<String,Object> parameter;

    private OperationHandler type;

    private Throwable throwable;
}
