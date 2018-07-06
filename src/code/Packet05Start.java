package code;

public class Packet05Start extends Packet{
		
	public Packet05Start() {
		super(05);
	}
	
	public byte[] getData() {
		return ("05#").getBytes();
	}
}
