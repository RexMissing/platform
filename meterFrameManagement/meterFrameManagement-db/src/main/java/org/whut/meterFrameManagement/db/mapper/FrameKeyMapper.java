package org.whut.meterFrameManagement.db.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by chenfu on 2017/3/30.
 */
public interface FrameKeyMapper {

    /**
     * 将密钥保存到数据库中
     *
     * @param meterID 表号
     * @param keyStr  密钥
     */
    public void add2DB(@Param("meterID")String meterID, @Param("keyStr")String keyStr);

    /**
     * 从数据库中取密钥
     *
     * @param meterID 表号
     * @return 密钥
     */
    public String getKey(String meterID);

    /**
     * 更新数据库中的密钥
     *
     * @param meterID 表号
     * @param newKey  新密钥
     */
    public void updatekey(@Param("meterID")String meterID, @Param("newKey")String newKey);

}
