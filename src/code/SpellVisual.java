package code;

import java.awt.Image;

public class SpellVisual {
	
	static SpellVisual[] all = new SpellVisual[255];
	
	byte id;
	String name;
	Image tex;
	
	public static final SpellVisual moon = new SpellVisual(0, "Moon", Bank.moon);
	public static final SpellVisual chaos = new SpellVisual(1, "Chaos Blast", Bank.voidbolt);
	public static final SpellVisual icicle = new SpellVisual(2, "Icicle", Bank.icicle);
	public static final SpellVisual flamering = new SpellVisual(3, "Flamering", Bank.firenova);
	public static final SpellVisual arrow = new SpellVisual(4, "Arrow", Bank.arrow);
	public static final SpellVisual planet = new SpellVisual(5, "Planet", Bank.planet);
	public static final SpellVisual psychnova = new SpellVisual(6, "Psychnova", Bank.nova);
	public static final SpellVisual brain = new SpellVisual(7, "Brain", Bank.brain);
	public static final SpellVisual psychic = new SpellVisual(8, "Psychic Missile", Bank.psychmissile);
	public static final SpellVisual fist = new SpellVisual(9, "Psychic Missile", Bank.fist);
	public static final SpellVisual clock = new SpellVisual(10, "Clock", Bank.clockproj);
	public static final SpellVisual shockwave = new SpellVisual(11, "Shockwave", Bank.slice);
	public static final SpellVisual shade = new SpellVisual(12, "Shadow", Bank.shade);
	public static final SpellVisual snowball = new SpellVisual(13, "Snowball", Bank.snowball);
	public static final SpellVisual bloodbolt = new SpellVisual(14, "Bloodbolt", Bank.bloodbolt);
	public static final SpellVisual dirt = new SpellVisual(15, "Dirt Bolt", Bank.dirtbolt);
	public static final SpellVisual earth = new SpellVisual(16, "Earth Bolt", Bank.earthbolt);
	public static final SpellVisual stone = new SpellVisual(17, "Stone Lance", Bank.rockbolt);
	public static final SpellVisual lifebolt = new SpellVisual(18, "Life Bolt", Bank.shamanbolt);
	public static final SpellVisual coil = new SpellVisual(19, "Green Coil", Bank.bansheecoil);
	public static final SpellVisual coil1 = new SpellVisual(20, "Purple Coil", Bank.coil);
	public static final SpellVisual cosmic = new SpellVisual(21, "Cosmic Bolt", Bank.cosblast);
	public static final SpellVisual goop = new SpellVisual(22, "Glop", Bank.glop);

	public SpellVisual(int id, String name, Image tex) {
		this.id = (byte) id;
		this.name = name;
		this.tex = tex;
		all[id] = this;
	}
	
	public static SpellVisual getRandom(){
		int r = Util.rand.nextInt(all.length);
		if(all[r]!=null)return all[r];
		return getRandom();
	}
	
	public static SpellVisual getRandomUnlocked(){
		int r = Util.rand.nextInt(all.length);
		if(all[r]!=null&&Bank.hasLockVal("vfx.HB", r))return all[r];
		else return getRandomUnlocked();
	}
}
