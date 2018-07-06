package code;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import code.Packet.PacketType;

public class GameClient extends Thread{
	
	InetAddress ip;
	DatagramSocket socket;
	
	public GameClient(String address){
		System.out.println("Initiating game client "+address);
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("Client startup successful.");
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
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String d = new String(data).trim();
		String[] packetData = d.split("#");
		PacketType type = Packet.lookup(Integer.parseInt(packetData[0]));
		String name = null;
		if(type!=Packet.PacketType.START)
		name = packetData[1];
		Packet packet;
		switch(type){
		case INVALID:
			System.out.println("Invalid packet m8.");
			break;
		case LOGIN:
			if(Display.currentScreen.getPlayer(name)==null){
				System.out.println("Player "+name+" logging in");
				PlayerMP player = new PlayerMP(Hero.all[Integer.parseInt(packetData[2])], name, 0, 0, address, port);
				player.clientPlayer = (name==Properties.username)?true:false;
				player.mp = true;
				Display.currentScreen.addPlayer(player, false);
			}else{
				System.out.println(name+" already in game.");
			}
			break;
		case PLAYERCAST:
			packet = new Packet03PlayerCast(packetData[1], Integer.parseInt(packetData[2]), Integer.parseInt(packetData[3]), packetData[4]);
			if(Display.currentScreen.getPlayer(name)!=null&!Display.currentScreen.getPlayer(name).clientPlayer){
				Spell.all[Integer.parseInt(packetData[2])].cast(Display.currentScreen.getPlayer(name), Integer.parseInt(packetData[3]), packetData[4]);
			}
		break;
		case WORLD:
			File dir = new File(Bank.path+"maps/MP");
			dir.mkdirs();
			File f = new File(Bank.path+"maps/MP/"+packetData[2]+".HB");
			try {
				if(f.createNewFile()){
					PrintWriter pw = new PrintWriter(f);
					pw.write(packetData[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		break;
		case PLAYERMOVE:
			String pname = packetData[1];
			int direction = Integer.parseInt(packetData[2]);
			boolean onoff = packetData[3]=="0"?false:true;
			if(Display.currentScreen.getPlayer(pname)!=null&!Display.currentScreen.getPlayer(name).clientPlayer){
				if(direction==0)Display.currentScreen.getPlayer(pname).right = onoff;
				if(direction==1)Display.currentScreen.getPlayer(pname).left = onoff;
				if(direction==2)Display.currentScreen.getPlayer(pname).jump();
				else{
					Display.currentScreen.getPlayer(pname).dir = (onoff?direction:-1);
					if(!onoff)
					Display.currentScreen.getPlayer(pname).trueDir = direction;
				}
			}else{
				System.out.println("Cannot find player "+pname);
			}
			break;
		case START:
			Util.music.stop();
			PanelPlay pan = new PanelPlay(Bank.getLevelData("level1"), true);
			pan.players = Display.currentScreen.players;
			for(Player p : pan.players){
				if(p.getUsername().equals(Properties.username))p.phys.fixate = true;
			}
			Display.currentScreen = pan;
		break;
		}
	}

	public void send(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, Properties.port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
