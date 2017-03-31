package org.whut.meterFrameManagement.db.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.service.FrameKeyService;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by chenfu on 2017/3/30.
 */
@Component
public class FrameKeyBusiness {
    @Autowired
    private FrameKeyService frameKeyService;

    private static String base = "abcdef0123456789";

    public String getKey(String meterID) {
        return frameKeyService.getKey(meterID);
    }

    public void updatekey(String meterID, String newKey) {
        frameKeyService.updatekey(meterID, newKey);
    }

    public static void main(String[] args) throws InterruptedException {
        Set<String> set = new HashSet<String>();
        String str = "";
        FrameKeyBusiness business = new FrameKeyBusiness();
        for (int i = 0; i < 50; i++) {
            str = business.createKeyStr("0120151212163");
            System.out.println(str);
            set.add(str);
        }
        System.out.println(set.size());
    }

    /**
     * 随机生成密钥
     *
     * @param meterID 表号
     * @return 密钥
     */
    public String createKeyStr(String meterID) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        // 随机生成19位的字符串，再加上13位的表号，组成密钥
        for (int i = 0; i < 19; i++) {
            int index = random.nextInt(base.length());
            buffer.append(base.charAt(index));
        }
        return (buffer.toString() + meterID).toUpperCase();
    }


}
