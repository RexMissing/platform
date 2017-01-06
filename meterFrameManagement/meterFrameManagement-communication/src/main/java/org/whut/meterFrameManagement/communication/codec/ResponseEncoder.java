package org.whut.meterFrameManagement.communication.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-10-20
 * Time: 下午7:54
 * To change this template use File | Settings | File Templates.
 */
public class ResponseEncoder extends ProtocolEncoderAdapter {

    private static final PlatformLogger logger = PlatformLogger.getLogger(ResponseEncoder.class);

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        System.out.print("encode方法");
        IoBuffer buffer = (IoBuffer)o;
        byte[] bytes = buffer.array();
        IoBuffer ioBuffer = IoBuffer.allocate(bytes.length);
        ioBuffer.put(bytes);
        ioBuffer.flip();
        protocolEncoderOutput.write(ioBuffer);
    }
}
