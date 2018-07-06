package code;

import java.awt.Image;

public class Spec {
	
	static Spec[] all = new Spec[255];
	
	byte id = 0, minlvl = 0;
	Image icon;
	String[] desc;
	boolean starter = false;
	Hero req = null;
	String name;
	
	public static final Spec trinity = new Spec(0, "The Recruit", 5, null, Bank.torchtop).setDesc(new String[]{"Increases all stats","by 20%."});
	public static final Spec priest = new Spec(1, "The Doctor", 5, null, Bank.spell_healingsands).setDesc(new String[]{"Increases RESTO","by 50%."});
	public static final Spec fighter = new Spec(2, "The Slayer", 5, null, Bank.sword_hit).setDesc(new String[]{"Increases POWER","by 50%."});
	public static final Spec tank = new Spec(3, "The Guardian", 5, null, Bank.axe_stand).setDesc(new String[]{"Increases FORTITUDE","by 50%."});
	public static final Spec rogue = new Spec(4, "The Rogue", 5, null, Bank.axe_stand).setDesc(new String[]{"Increases POWER","by 25%","Also increases speed","by 50%."});
	public static final Spec scholar = new Spec(5, "The Scholar", 5, null, Bank.axe_stand).setDesc(new String[]{"Increases POWER","by 20%.","Awards 5 more","trait points."});
	public static final Spec scholar1 = new Spec(6, "The Royal Scholar", 5, null, Bank.axe_stand).setDesc(new String[]{"Increases POWER","by 10%.","Awards 10 more","trait points."});
	public static final Spec lifesteal = new Spec(7, "The Reaper", 5, null, Bank.axe_stand).setDesc(new String[]{"Heals you for 10%","of damage you deal."});
	
	public Spec(int id, String n, int lvl, Hero h, Image ico){
		this.id = (byte) id;
		this.icon = ico;
		if(lvl==-1)starter=true;
		this.req = h;
		this.name = n;
	}
	
	public Spec setDesc(String[] desc){
		this.desc = desc;
		return this;
	}
}
