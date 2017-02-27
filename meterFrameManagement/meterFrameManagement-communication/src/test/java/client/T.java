package client;


/**
 * Created by zhang_minzhong on 2017/2/21.
 */
public class T {
    public static void main(String[] args) {
        String hex = Integer.toHexString(23450);
        System.out.println(hex);
        System.out.println(Integer.parseInt("5b",16)+","+Integer.parseInt("9a",16));

        /*int a = 12930;
        System.out.println(Integer.toHexString(a));
        byte b=(byte)a;
        System.out.println(b);
        System.out.println(Byte.toUnsignedInt(b));
        System.out.println(Integer.toHexString(Byte.toUnsignedInt(b)));*/
    }
}
