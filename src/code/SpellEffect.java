package code;

import java.awt.Image;

public class SpellEffect {
	
	static SpellEffect[] all = new SpellEffect[255];
	
	Image icon;
	static byte PROJECTILE = 0, MELEE = 1, SELF = 2;
	byte id, rating = 1, type = 0;
	String name;
	String[] desc;
	
	public static final SpellEffect burn = new SpellEffect(0, "Inferno", 3, Bank.fire).setDesc(new String[]{"Deals 25% initial","damage every 0.5sec","for 2sec."});
	public static final SpellEffect stun = new SpellEffect(1, "Shock", 2, Bank.stun).setDesc(new String[]{"Applies a 1.25sec","stun."});
	public static final SpellEffect heal = new SpellEffect(2, "Mending", 5, Bank.plus).setDesc(new String[]{"Heals you."});
	public static final SpellEffect brainfry = new SpellEffect(3, "Time Warp", 5, Bank.spell_brainfry).setDesc(new String[]{"Increases cooldowns","by 50% for 4sec."});
	public static final SpellEffect manabite = new SpellEffect(4, "Mana Bite", 5, Bank.winds).setDesc(new String[]{"Consumes effects."});
	public static final SpellEffect chill = new SpellEffect(5, "Frostbite I", 5, Bank.winds).setDesc(new String[]{"20% chance to CHILL."});
	public static final SpellEffect rot = new SpellEffect(6, "Corrode", 5, Bank.glop).setDesc(new String[]{"Target takes","double damage for","4 seconds."});
	public static final SpellEffect armour = new SpellEffect(7, "Defend", 5, Bank.axe_run).setDesc(new String[]{"Reduces damage taken","by 50% for 12sec."});
	public static final SpellEffect shield = new SpellEffect(8, "Absorb", 5, Bank.winds).setDesc(new String[]{"Absorbs damage."});
	public static final SpellEffect speed = new SpellEffect(9, "Speed", 2, Bank.winds).setDesc(new String[]{"Increases speed by.","100% for 4sec."});
	public static final SpellEffect speed1 = new SpellEffect(10, "Speed I", 4, Bank.flag).setDesc(new String[]{"Increases speed by","100% for 8sec."});
	public static final SpellEffect speed2 = new SpellEffect(11, "Blurred Speed", 6, Bank.winds).setDesc(new String[]{"Increases speed by","100% for 5sec","and reduces damage","taken by 50%."});

	public SpellEffect(int id, String name, int r, Image tex) {
		this.id = (byte) id;
		this.rating = (byte) r;
		this.name = name;
		this.icon = tex;
		all[id] = this;
	}
	
	public SpellEffect setType(int t){
		this.type = (byte)t;
		return this;
	}
	
	public SpellEffect setDesc(String[] desc){
		this.desc = desc;
		return this;
	}
	
	public static SpellEffect getRandom(){
		int r = Util.rand.nextInt(all.length);
		if(all[r]!=null)return all[r];
		return getRandom();
	}
	
	public static SpellEffect getRandomUnlocked(){
		int r = Util.rand.nextInt(all.length);
		if(all[r]!=null&&Bank.hasLockVal("fx.HB", r))return all[r];
		return getRandomUnlocked();
	}
}
