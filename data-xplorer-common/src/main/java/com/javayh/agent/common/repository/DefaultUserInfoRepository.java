package com.javayh.agent.common.repository;

import com.javayh.agent.common.bean.DataXplorerUserInfoResponse;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 默认的用户信息
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-11-06
 */
@Component
public class DefaultUserInfoRepository implements UserInfoRepository {
    /**
     * 查询用户详细信息
     *
     * @return 用户信息 {@link DataXplorerUserInfoResponse}
     */
    @Override
    public DataXplorerUserInfoResponse queryUser() {
        return DataXplorerUserInfoResponse.builder()
                .id("2023")
                .userName("DataXplorer")
                .nickName("DataXplorer")
                .email("DataXplorer@DataXplorer.com").build();
    }

    /**
     * 查询用户详细信息
     *
     * @return 用户信息 {@link DataXplorerUserInfoResponse}
     */
    @Override
    public String queryUserName() {
        return queryUser().getEmail();
    }
}
