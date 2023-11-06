package com.javayh.agent.example;

import com.javayh.agent.common.bean.DataXplorerUserInfoResponse;
import com.javayh.agent.common.repository.UserInfoRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-11-06
 */
@Primary
@Component
public class ExampleUserInfoRepositoryImpl implements UserInfoRepository {
    /**
     * 查询用户详细信息
     *
     * @return 用户信息 {@link DataXplorerUserInfoResponse}
     */
    @Override
    public DataXplorerUserInfoResponse queryUser() {
        return DataXplorerUserInfoResponse.builder()
                .id("2023")
                .userName("ExampleUser")
                .nickName("ExampleUser")
                .email("ExampleUser@DataXplorer.com").build();
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
