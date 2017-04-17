package org.whut.meterFrameManagement.db.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by chenfu on 2017/3/30.
 */
public interface FrameKeyMapper {

    /**
     * 从数据库中取初始密钥
     *
     * @param meterID 表号
     * @return 初始密钥
     */
    public String getOldKey(String meterID);

    /**
     * 更新表具新密钥
     *
     * @param meterID 表号
     * @param newKey  新密钥
     */
    public void updateNewKey(@Param("meterID") String meterID, @Param("newKey") String newKey);

    /**
     * 从数据库中取新密钥
     *
     * @param meterID 表号
     * @return 新密钥
     */
    public String getNewKey(String meterID);

    public void changeKey(@Param("meterID")String meterID,@Param("oldKey") String oldKey,@Param("newKey") String newKey);
}
