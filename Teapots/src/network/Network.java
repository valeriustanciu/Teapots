package network;

import mediator.IMediatorNetwork;

public class Network implements INetwork{
	private IMediatorNetwork mediator;
	
	
	public Network(IMediatorNetwork med) {
		this.mediator = med;
	}
	
}
