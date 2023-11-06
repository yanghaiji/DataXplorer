package com.javayh.agent.common.repository;

import com.javayh.agent.common.bean.DataXplorerUserInfoResponse;

/**
 * <p>
 * 有户信息
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-11-06
 */
public interface UserInfoRepository {

    /**
     * 查询用户详细信息
     *
     * @return 用户信息 {@link DataXplorerUserInfoResponse}
     */
    DataXplorerUserInfoResponse queryUser();


    /**
     * 查询用户详细信息
     *
     * @return 用户信息 {@link DataXplorerUserInfoResponse}
     */
    String queryUserName();
}
