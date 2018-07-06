package code;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Packet00Login extends Packet{
	
	public String username;
	int x, y, classid = 0;
	
	public Packet00Login(byte[] data) {
		super(00);
		username = read(data)[1];
		classid = Integer.parseInt(read(data)[2]);
	}
	
	public Packet00Login(String user, int heroID) {
		super(00);
		this.username = user;
		this.classid = heroID;
	}
	
	public byte[] getData() {
		return ("00#"+username+"#"+classid).getBytes();
	}
}
