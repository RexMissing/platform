package client;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by zhang_minzhong on 2017/2/21.
 */
public class T {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("10.27.6.212",6601);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            byte[] request = {104,(byte)161 ,48, 49, 50 ,48, 49, 53, 49, 50, 49, 50, 49, 54, 51, 22};
            for(int i=0;i<request.length;i++){
                System.out.print((char)request[i]+" ");
            }
            System.out.println();
            os.write(request);
            byte[] response = new byte[128];
            is.read(response);
            for(int i=0;i<response.length;i++){
                System.out.print(Byte.toUnsignedInt(response[i])+" ");
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("异常");
            e.printStackTrace();
        }
    }
}
