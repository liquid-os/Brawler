package code;

public abstract class Packet {
	
	public static enum PacketType{
		INVALID(-1), LOGIN(00), DISCONNECT(01), PLAYERMOVE(02), PLAYERCAST(03), WORLD(04), START(05);
		
		private int id;
		
		private PacketType(int i){
			id = i;
		}
		
		public int getID(){
			return id;
		}
	}
	
	private byte packetID;
	
	public Packet(int id){
		packetID = (byte) id;
	}
	
	public final void write(GameClient c){
		if(c!=null)
		c.send(getData());
	}
	
	public final void write(GameServer c){
		if(c!=null)
		c.sendToAll(getData());
	}
	
	public String[] read(byte[] values){
		String data = new String(values).trim();
		return data.split("#");
	}
	
	public static PacketType lookup(String id){
		try{
			return lookup(Integer.parseInt(id));
		}catch(NumberFormatException e){
			return PacketType.INVALID;
		}
	}
	
	public static PacketType lookup(int id){
		for(PacketType p : PacketType.values()){
			if(p.getID() == id){
				return p;
			}
		}
		return PacketType.INVALID;
	}
	
	public abstract byte[] getData();
}
