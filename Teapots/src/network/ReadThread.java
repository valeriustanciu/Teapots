package network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadThread extends Thread{
	
	public static ExecutorService pool = Executors.newFixedThreadPool(5);
	private static int BUF_SIZE = 1024;
	private String ip;
	private int port;
	private static INetwork network;
	
	public ReadThread(String ip, int port, INetwork network) {
		this.ip = ip;
		this.port = port;
		this.network = network;
	}
	
	public static void accept(SelectionKey key) throws IOException {
		
		System.out.print("ACCEPT: ");
		
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
		socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
		
		System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
	}
	
	public static void read(final SelectionKey key) throws IOException {
		
		System.out.print("READ: ");
		
		// remove all interests
		key.interestOps(0);
		
		pool.execute(new Runnable() {
			public void run() {
				int bytes;
				ByteBuffer buf = (ByteBuffer)key.attachment();
				SocketChannel socketChannel = (SocketChannel)key.channel();
				
				System.err.println("MyThread");
				
				try {
				
					while ((bytes = socketChannel.read(buf)) > 0);
					buf.flip();
					
					byte[] data = new byte[buf.limit()];
					buf.get(data);
					
					ByteArrayInputStream bis = new ByteArrayInputStream(data);
					ObjectInput in = null;
					
					Message msg = null;
					try {
						in = new ObjectInputStream(bis);
						msg = (Message) in.readObject();
					} catch (ClassNotFoundException e) {
						
					} finally {
						bis.close();
						in.close();
					}
					
					socketChannel.close();
					
					MessageHandler.HandleMessage(msg, network);
					
				} catch (IOException e) {
					System.out.println("Connection closed: " + e.getMessage());
					
					try {
						socketChannel.close();
					} catch (IOException exc) {}
				}
			}
		});
	}
	
	
	@Override
	public void run() {
		Selector selector						= null;
		ServerSocketChannel serverSocketChannel	= null;
		
		try {
			selector = Selector.open();
			
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.socket().bind(new InetSocketAddress(this.ip, this.port));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			while (true) {
				selector.select();
				
				for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isAcceptable())
						accept(key);
					else if (key.isReadable())
						read(key);
				}
			}
		}
		catch (Exception e) {
			
		}
		finally {
			if (selector != null)
				try {
					selector.close();
				} catch (IOException e) {}
			
			if (serverSocketChannel != null)
				try {
					serverSocketChannel.close();
				} catch (IOException e) {}
		}
	}

}
