package org.whut.meterFrameManagement.communication.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-10-20
 * Time: 下午7:33
 * To change this template use File | Settings | File Templates.
 */
public class ResponseDecoder extends CumulativeProtocolDecoder {

    private static final PlatformLogger logger = PlatformLogger.getLogger(ResponseDecoder.class);

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        System.out.println("doDecode方法");
        int count = ioBuffer.remaining();
        byte[] bytes = new byte[count];
        for(int i = 0;ioBuffer.hasRemaining();i++){
            bytes[i] = ioBuffer.get();
        }
        //String s = new String(bytes,Charset.forName("utf-8"));
        IoBuffer buffer = IoBuffer.wrap(bytes);
        protocolDecoderOutput.write(buffer);
        return true;
    }
}
