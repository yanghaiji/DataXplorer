package com.javayh.agent.common.handler;

/**
 * <p>
 * 操作类型
 * 用于统一定义操作的处理类型，比如，xxxSave xxxInit xxxPay
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-12-01
 */
public interface OperationHandler {

    /**
     * 放回真实的操作类型
     * @return {@link String}
     */
    String value();

    /**
     * 放回真实的操作类型对应的字典号，方便后期在字典里维护
     * @return {@link Integer}
     */
    Integer operation();
}
