package org.whut.meterFrameManagement.communication.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-10-20
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
public class DataCodecFactory implements ProtocolCodecFactory{
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return new ResponseEncoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return new ResponseDecoder();
    }
}
