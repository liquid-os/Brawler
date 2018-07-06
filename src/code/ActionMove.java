package code;

import java.awt.Graphics;
import java.awt.Image;

public class ActionMove {
	
	static ActionMove[] all = new ActionMove[255];
	public byte id = 0, val = 3, statType = 0;
	int cooldown = 500;
	Image img;
	String name;
	String[] desc = new String[1];
	
	public static final ActionMove block = new ActionMove(0, "Block", Bank.ent_swing, 4, 9000).setDesc(new String[]{"Blocks an attack."});
	public static final ActionMove energize = new ActionMove(1, "Energize", Bank.ent_swing, 4, 5500).setDesc(new String[]{"Refreshes your jumps."});
	public static final ActionMove clarity = new ActionMove(2, "Clarity", Bank.ent_swing, 1, 14000).setDesc(new String[]{"Instantly finishes your","cooldowns."});
	public static final ActionMove enrage = new ActionMove(3, "Enrage", Bank.ent_swing, 1, 13000).setDesc(new String[]{"Increases damage dealt","by 100% for 3sec."});
	public static final ActionMove sprint = new ActionMove(4, "Sprint", Bank.ent_swing, 1, 8000).setDesc(new String[]{"Increases your movement","speed for 3sec."});
	public static final ActionMove iron = new ActionMove(5, "Iron Aspect", Bank.ent_swing, 1, 20000).setDesc(new String[]{"Increases damage deaalt","by 200% 4sec and","disables movement for the","duration."});
	public static final ActionMove meteor = new ActionMove(6, "Meteor Shower", Bank.ent_swing, 1, 30000).setDesc(new String[]{"Rains meteors from the sky","for 7 sec."});
	public static final ActionMove tidalfury = new ActionMove(7, "Tidal Fury", Bank.ent_swing, 1, 30000).setDesc(new String[]{"Washes away ."});
	
	public ActionMove(int id, String name, Image icon, int val, int cooldown){
		this.id = (byte) id;
		this.name = name;
		this.img = icon;
		this.val = (byte) val;
		this.cooldown = cooldown;
		all[id] = this;
	}
	
	public ActionMove setDesc(String[] desc){
		this.desc = desc;
		return this;
	}

	public void onUse(Player p, double str){
		double value = str;
	}

	public long getCooldownMillis() {
		return cooldown;
	}
}
