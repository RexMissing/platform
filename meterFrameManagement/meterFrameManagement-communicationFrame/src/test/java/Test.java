import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.util.date.DateUtil;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/16.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        /*String s = "DDE24E838C7023840ADC1A146B4445817F488EBBA6FCDC179F3B469FC41C4EDB5A5A9A39E9139B93F5D76FBA0416E2DA2296FB69F7D9B954DBEF3F3FB7BC368F";
        byte[] bytes = new byte[s.length()/2];
        for(int i=0;i<bytes.length;i=i+2)
            bytes[i] = (byte) Integer.parseInt(s.substring(i, i + 2), 16);
        String keyStr = "640836FBC4A6FD68431AE03CF44C0232640836FBC4A6FD68431AE03CF44C0232";
        byte[] key = new byte[keyStr.length()/2];
        for(int i=0;i<key.length;i=i+2)
            key[i] = (byte)Integer.parseInt(keyStr.substring(i,i+2),16);
        byte[] result = AES.decrypt(bytes,key);
        System.out.println(Hex.BytesToHexString(result));*/
    }
}
