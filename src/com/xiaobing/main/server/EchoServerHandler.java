package com.xiaobing.main.server;

import io.netty.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
/**
 * Server Channel Handler
 * Override method to implement fpr Business Logic
 * @author cxshi1
 *
 */
@Sharable/* Mark this Handler can be shared be many Channel safety*/
public class EchoServerHandler extends ChannelInboundHandlerAdapter  {
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
		//Read Message
		ByteBuf inBuf = (ByteBuf) msg;
		System.out.println("Server received: "+inBuf.toString(CharsetUtil.UTF_8));
		
		byte[] src = {'o'};
		inBuf.writeBytes(src);
		
		//Write message to client
		ctx.write(inBuf);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		// writeAndFlush pending message(stored in ChannelOutBoundBuffer), try write to socket and close the channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
		//Handler Exception 
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
}
