import org.whut.meterFrameManagement.util.date.DateUtil;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/16.
 */
public class Test {
    public static void main(String[] args) {
        String s = "00022232";
        byte[] aBytes = Hex.hexStringToBytes(s,4);
        System.out.println(aBytes[aBytes.length-1]);
        int b = 0;
        int k = 1;
        for(int i=0;i<aBytes.length;i++){
            b+=Byte.toUnsignedInt(aBytes[i])*(1000000/k);
            k*=100;
        }
        System.out.println(b);
    }
}
