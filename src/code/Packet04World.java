package code;

public class Packet04World extends Packet{
	
	String worlddata, name = "mpworld"+Util.rand.nextInt(100);
	
	public Packet04World(byte[] data) {
		super(04);
		name = read(data)[2];
		worlddata = read(data)[1];
	}
	
	public Packet04World(String name, String wd) {
		super(04);
		this.name = name;
		this.worlddata = wd;
	}

	public byte[] getData() {
		return ("04#"+worlddata+"#"+name).getBytes();
	}
}
