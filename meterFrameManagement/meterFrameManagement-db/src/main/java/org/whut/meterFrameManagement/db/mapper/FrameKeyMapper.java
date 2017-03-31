package org.whut.meterFrameManagement.db.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by chenfu on 2017/3/30.
 */
public interface FrameKeyMapper {

    /**
     * 从数据库中取密钥（当前使用的密钥）
     *
     * @param meterID 表号
     * @return 密钥
     */
    public String getKey(String meterID);

    /**
     * 更新表具当前使用的密钥
     *
     * @param meterID 表号
     * @param newKey  新密钥
     */
    public void updatekey(@Param("meterID")String meterID, @Param("newKey")String newKey);

}
