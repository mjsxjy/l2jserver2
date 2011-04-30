package com.l2jserver.game.net.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.l2jserver.util.BlowFishKeygen;

public class Lineage2Decrypter extends OneToOneDecoder {
	public static final String HANDLER_NAME = "crypto.decoder";

	private boolean enabled = false;
	private final byte[] key = new byte[16];

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		if (!(msg instanceof ChannelBuffer))
			return msg;
		if (!enabled)
			return msg;
		final ChannelBuffer buffer = (ChannelBuffer) msg;
		
		System.out.println("Decrypting...");
		
		final int offset = buffer.readerIndex();
		final int size = buffer.readableBytes();
		int temp = 0;
		for (int i = 0; i < size; i++) {
			int temp2 = buffer.getUnsignedByte(offset + i) & 0xFF;
			buffer.setByte(offset + i, (byte) (temp2 ^ key[i & 15] ^ temp));
			temp = temp2;
		}

		int old = key[8] & 0xff;
		old |= key[9] << 8 & 0xff00;
		old |= key[10] << 0x10 & 0xff0000;
		old |= key[11] << 0x18 & 0xff000000;

		old += size;

		key[8] = (byte) (old & 0xff);
		key[9] = (byte) (old >> 0x08 & 0xff);
		key[10] = (byte) (old >> 0x10 & 0xff);
		key[11] = (byte) (old >> 0x18 & 0xff);

		return buffer;
	}
	
	public byte[] enable() {
		byte[] key = BlowFishKeygen.getRandomKey();
		this.setKey(key);
		this.setEnabled(true);
		return key;
	}

	public void setKey(byte[] key) {
		for (int i = 0; i < 16; i++) {
			this.key[i] = key[i];
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}