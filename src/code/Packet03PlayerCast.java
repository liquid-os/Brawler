package code;

public class Packet03PlayerCast extends Packet{
	
	public String username, notes;
	int spellid, dam;
	
	public Packet03PlayerCast(byte[] data) {
		super(03);
		username = read(data)[1];
		spellid = Integer.parseInt(read(data)[2]);
		dam = Integer.parseInt(read(data)[3]);
		notes = read(data)[4];
	}
	
	public Packet03PlayerCast(String user, int id, int dam, String notes) {
		super(03);
		this.username = user;
		this.spellid = id;
		this.dam = dam;
		this.notes = notes;
	}
	public byte[] getData() {
		return ("03#"+username+"#"+spellid+"#"+dam+"#"+notes).getBytes();
	}
}
