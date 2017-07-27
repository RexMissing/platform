package org.whut.meterFrameManagement.communicationframe.key;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by zhang_minzhong on 2017/4/13.
 */
public class KeyManager {
    private static String base = "0123456789ABCDEF";
    /**
     * 随机生成密钥
     */
    public static String createKeyStr() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        // 随机生成19位的字符串，再加上13位的表号，组成密钥
        for (int i = 0; i < 32; i++) {
            int index = random.nextInt(base.length());
            buffer.append(base.charAt(index));
        }
        return buffer.toString();
    }
    public static void main(String[] args) throws InterruptedException {
        Set<String> set = new HashSet<String>();
        String str = "";

        for (int i = 0; i < 50; i++) {
            str = createKeyStr();
            System.out.println(str);
            set.add(str);
        }
        System.out.println(set.size());
        set.removeAll(set);

    }
}
