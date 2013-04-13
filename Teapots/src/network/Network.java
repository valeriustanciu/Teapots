package network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mediator.IMediatorNetwork;

public class Network implements INetwork {
	private static int BUF_SIZE = 1024;
	private IMediatorNetwork mediator;
	private NetworkInfo users;
	
	public Network(IMediatorNetwork med) {
		this.mediator = med;
		
		ArrayList<UserInfo> usersInfo = med.getLoggedUsersFromServer();
		this.users = new NetworkInfo();
		
		for (int i = 0; i < usersInfo.size(); i++) {
			this.users.addUser(usersInfo.get(i).getUsername(), 
							   usersInfo.get(i).getIp(),
							   usersInfo.get(i).getPort());
		}
		
		Thread readThread = new Thread () {
			public ExecutorService pool = Executors.newFixedThreadPool(5);
			
			
			public void read(final SelectionKey key) throws IOException {
				
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
							
							// check for EOF
							if (bytes == -1)
								throw new IOException("EOF");
							
							
							System.out.println(buf);
							
							// if buffer is full, write it back, flipping it first
							if (! buf.hasRemaining()) {
								buf.flip();
								key.interestOps(SelectionKey.OP_WRITE);
								
								//Channels.newChannel(System.out).write(buf);
								//buf.clear();
								
							// if not full, continue filling it
							} else
								key.interestOps(SelectionKey.OP_READ);
							
							// either way, make the selector aware of our new interests
							key.selector().wakeup();
							
						} catch (IOException e) {
							System.out.println("Connection closed: " + e.getMessage());
							
							try {
								socketChannel.close();
							} catch (IOException exc) {}
						}
					}
				});
			}
			
			
			public void accept(SelectionKey key) throws IOException {
				System.out.print("ACCEPT: ");
				
				ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
				SocketChannel socketChannel = serverSocketChannel.accept();
				socketChannel.configureBlocking(false);
				ByteBuffer buf = ByteBuffer.allocateDirect(BUF_SIZE);
				socketChannel.register(key.selector(), SelectionKey.OP_READ, buf);
				
				System.out.println("Connection from: " + socketChannel.socket().getRemoteSocketAddress());
			}
			
			@Override
			public void run() {
				Selector selector						= null;
				ServerSocketChannel serverSocketChannel	= null;
				
				try {
					selector = Selector.open();
					
					serverSocketChannel = ServerSocketChannel.open();
					serverSocketChannel.configureBlocking(false);
					serverSocketChannel.socket().bind(new InetSocketAddress(mediator.getOwnInfoFromServer().getIp(),
																			mediator.getOwnInfoFromServer().getPort()));
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
		};
		
		Thread writeThread = new Thread () {
			public ExecutorService pool = Executors.newFixedThreadPool(5);
			
			public void connect (SelectionKey key) throws IOException {
				
				System.out.print("CONNECT: ");
				
				SocketChannel socketChannel = (SocketChannel)key.channel();
				if (! socketChannel.finishConnect()) {
					System.err.println("Eroare finishConnect");
				}
				
				//socketChannel.close();
				key.interestOps(SelectionKey.OP_WRITE);
			}
			
			public void write(SelectionKey key) throws IOException {
				
				System.out.println("WRITE: ");
				
				int bytes;
				ByteBuffer buf = (ByteBuffer)key.attachment();		
				SocketChannel socketChannel = (SocketChannel)key.channel();
				
				try {
					while ((bytes = socketChannel.write(buf)) > 0);
					
					if (! buf.hasRemaining()) {
						buf.clear();
						key.interestOps(SelectionKey.OP_READ);
					}
					
				} catch (IOException e) {
					System.out.println("Connection closed: " + e.getMessage());
					socketChannel.close();
					
				}
			}

			@Override
			public void run () {
				
			}
		};
	}
	
	
	//TODO
	public void logOut (String username) {
		//cand un user face logout, se anunta toti ceilalti useri;
		//se apeleaza metoda "userLoggedOut" din mediator
		return;
	}
	
	
	//TODO
	public void acceptOffer (String localUser, String remoteUser, String service) {
		//cand un buyer accepta o oferta, el notifica sellerul;
		//acesta va primi "buyerAcceptedOffer" in mediator
		return;
	}
	
	
	//TODO
	public void refuseOffer (String localUser, String remoteUser, String service) {
		//cand un buyer refuza o oferta, el notifica sellerul;
		//acesta va primi "buyerRefusedOffer" in mediator
		return;
	}
	
	
	//TODO
	public void makeOffer(String localUser, String remoteUser, String service) {
		//cand un seller face o oferta, el notifica buyerul;
		//acesta va primi "sellerMadeOffer" in mediator
		return;
	}
	
	
	//TODO
	public void dropAuction(String localUser, String remoteUser, String service) {
		//cand un seller renunta la o licitatie, el notifica buyerul;
		//acesta va primi "sellerDroppedAuction" in mediator
		return;
	}
	
	
	//TODO
	public void addService (String user, String service) {
		//cand un buyer activeaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userActivatedService" din mediator
		return;
	}
	
	
	//TODO
	public void removeService (String user, String service) {
		//cand un buyer dezactiveaza un serviciu, sunt notificati toti selerii activi;
		//se apeleaza metoda "userDeactivatedService" din mediator
		return;
	}
	
}
