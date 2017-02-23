package org.whut.meterFrameManagement.util.hex;

public class Hex {

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] decode(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String encode(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将byte转成16进制字符串
     *
     * @param buf
     * @return
     */
    public static String encode2(byte buf) {

        String hex = Integer.toHexString(buf & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }

        return hex.toUpperCase();
    }

    /**
     * 将字节数组转成16进制字符串，不考虑符号问题
     *
     * @param bytes
     * @return
     */
    public static String BytesToHexString(byte[] bytes) {
        String s = "";
        String tempStr = "";
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            temp = Byte.toUnsignedInt(bytes[i]);
            tempStr = Integer.toHexString(temp);
            if (tempStr.length() == 1)
                tempStr = "0" + tempStr;
            s += tempStr;
        }
        return s;
    }

    //将16进制字符串转成字节数组，2个字符一个字节,len:数组长度
    public static byte[] hexStringToBytes(String s,int len){
        if(s==null)
            return new byte[0];
        while(s.length()/2<len){
            s = "0" + s;
        }
        System.out.println(s);
        byte[] bytes = new byte[s.length()/2];
        for(int i=0;i<bytes.length;i++){
            bytes[i] = (byte)Integer.parseInt(s.substring(i*2,i*2+2),16);
        }
        return bytes;
    }
}
