package com.javayh.agent.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 有户信息
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-11-06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataXplorerUserInfoResponse {

    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;


}
