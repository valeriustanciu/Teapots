package network;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MessageHandler {
	
	private MessageHandler() {}
	
	public static void HandleMessage(Message msg, INetwork network) {
		
		if (msg.getAction().equals("makeOffer")) {
			network.sellerMadeOffer(msg.getFromUser(), msg.getToUser(), 
					msg.getArgs().get(0), Integer.parseInt(msg.getArgs().get(1)));
		}
		else if (msg.getAction().equals("dropAuction")) {
			network.sellerDroppedAuction(msg.getFromUser(), msg.getArgs().get(0));
		}
		else if (msg.getAction().equals("acceptOffer")) {
			network.buyerAcceptedOffer(msg.getFromUser(), msg.getArgs().get(0));
		}
		else if (msg.getAction().equals("refuseOffer")) {
			network.buyerRefusedOffer(msg.getFromUser(), msg.getArgs().get(0));
		}
		else if (msg.getAction().equals("addService")) {
			network.userActivatedService(msg.getFromUser(), msg.getArgs().get(0));
		}
		else if (msg.getAction().equals("removeService")) {
			network.userDeactivatedService(msg.getFromUser(), msg.getArgs().get(0));
		}
		else if (msg.getAction().equals("logOut")) {
			network.userLoggedOut(msg.getFromUser());
		}
		else if (msg.getAction().equals("transfer")) {
			// in fromUser -> numele fisierului la gazda
			// in toUser -> numele fisierului la receptie (cum il salvez eu)
			
			saveFile(msg.getToUser(), msg.getArgs().get(0));
		}
	}
	
	public static void saveFile(String fileName, String fileContent) {
		try {
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(fileContent);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
