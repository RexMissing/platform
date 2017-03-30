package org.whut.meterFrameManagement.communicationframe.key;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by chenfu on 2017/3/30.
 */
public class GenerateFrameKey {

    private static String base = "abcdef0123456789";

    public static void main(String[] args) throws InterruptedException {
        Set<String> set = new HashSet<String>();
        String str = "";
        for (int i = 0; i < 50; i++) {
            str = createKeyStr("0120151212163");
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
    public static String createKeyStr(String meterID) {
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
