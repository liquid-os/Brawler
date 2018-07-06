package code;

public class Packet02PlayerMove extends Packet{
	
	public String username;
	int dir, onoff;
	
	public Packet02PlayerMove(byte[] data) {
		super(02);
		username = read(data)[1];
		dir = Integer.parseInt(read(data)[2]);
		onoff = Integer.parseInt(read(data)[3]);
	}
	
	public Packet02PlayerMove(String user, int dir, int bool) {
		super(02);
		username = user;
		this.dir = dir;
		this.onoff = bool;
	}

	public byte[] getData() {
		return ("02#"+username+"#"+dir+"#"+onoff).getBytes();
	}
}
