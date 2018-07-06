package code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import code.Packet.PacketType;

public class GameServer extends Thread{

	ServerSocket server;
	DatagramSocket socket;
	ArrayList<PlayerMP> clients = new ArrayList<PlayerMP>();
	
	public GameServer(){
		System.out.println("Server starting on port "+Properties.port);
		try {
			//server = new ServerSocket(Properties.port);
			socket = new DatagramSocket(Properties.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started successfully.");
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			System.out.println("Client says: "+message);
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String d = new String(data).trim();
		String[] packetData = d.split("#");
		PacketType type = Packet.lookup(packetData[0]);
		Packet packet;
		PlayerMP player = null;
		switch(type){
			case INVALID:
			break;
			case LOGIN:
				packet = new Packet00Login(packetData[1], Integer.parseInt(packetData[2]));
				player = new PlayerMP(Hero.all[Integer.parseInt(packetData[2])], packetData[1], 0, 0, address, port);
				player.clientPlayer = false;
				System.out.println(address.getHostAddress()+" connected with username "+((Packet00Login)packet).username);
				registerConnection(player, (Packet00Login)packet);
				Display.currentScreen.players.add(player);
				Display.currentScreen.playerConnected((Packet00Login) packet);
				//this.send(data, address, Properties.port);
				packet.write(this);
				//Packet04World wp = new Packet04World(Display.currentScreen);
				//wp.write(this);
			break;
			case PLAYERMOVE:
				packet = new Packet02PlayerMove(packetData[1], Integer.parseInt(packetData[2]), Integer.parseInt(packetData[3]));
				packet.write(this);
			break;
			case PLAYERCAST:
				packet = new Packet03PlayerCast(packetData[1], Integer.parseInt(packetData[2]), Integer.parseInt(packetData[3]), packetData[4]);
				packet.write(this);
			break;
			case DISCONNECT:
				packet = new Packet01Disconnect(d);
				System.out.println(address.getHostAddress()+" left the game.");
				breakConnection(player, (Packet01Disconnect)packet);
			break;
			case WORLD:
				packet = new Packet04World(packetData[2], packetData[1]);
				packet.write(this);
			break;
			case START:
				packet = new Packet05Start();
				packet.write(this);
			break;
		}
	}

	private void breakConnection(PlayerMP player, Packet01Disconnect packet) {	
		clients.remove(getPlayerIndex(packet.username));
		packet.write(this);
	}

	private void registerConnection(PlayerMP player, Packet00Login packet) {
		boolean connected = false;
		for(PlayerMP p : clients){
			if(player.getUsername().equalsIgnoreCase(p.getUsername())){
				if(p.ip == null){
					p.ip = player.ip;
				}
				if(p.port == -1){
					p.port = player.port;
				}
				connected = true;
			}else{
				send(packet.getData(), p.ip, p.port);
			}
		}
		if(!connected){
			clients.add(player);
		}
	}
	
	public PlayerMP getPlayer(String user){
		for(int i = 0; i < clients.size(); ++i){
			return clients.get(i).getUsername() == user ? clients.get(i) : null;
		}
		return null;
	}
	
	public int getPlayerIndex(String user){
		int index = 0;
		for(int i = 0; i < clients.size(); ++i){
			if(clients.get(i).getUsername().equals(user))
				break;
		}
		index++;
		return index;
	}

	public void send(byte[] data, InetAddress ip, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendToAll(byte[] data) {
		for(PlayerMP p : clients){
			send(data, p.ip, p.port);
		}
	}
}
