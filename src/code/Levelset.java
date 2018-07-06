package code;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Levelset {
	
	static Levelset[] all = new Levelset[20];
	int id = 0;
	String name;
	Image icon;
	List<Level> set = new ArrayList<Level>();

	public static final Levelset skyfire = new Levelset(0, "Skyfire Showdown", Bank.wip)
	.addLevels(Arrays.asList(
		Level.pass,
		Level.trail,
		Level.airship,
		Level.bandit,
		Level.masterBarbarian
	));
	
	public static final Levelset jungle = new Levelset(1, "Rumble in the Jungle", Bank.wip)
	.addLevels(Arrays.asList(
		Level.iceridge
	));
	
	public static final Levelset desert = new Levelset(2, "Land in the Sand", Bank.wip)
	.addLevels(Arrays.asList(
		Level.masterPriest
	));
	
	public static final Levelset iceridge = new Levelset(3, "Cool as Ice", Bank.wip)
	.addLevels(Arrays.asList(
		Level.iceridge,
		Level.monk,
		Level.fort
	));
	
	public static final Levelset hell = new Levelset(4, "Hellwrath Heist", Bank.wip)
	.addLevels(Arrays.asList(
		Level.masterPrince
	));
	
	public static final Levelset nether = new Levelset(5, "Netherblight Abyss", Bank.wip)
	.addLevels(Arrays.asList(
		Level.masterMesmer
	));
	
	public Levelset(int id, String name, Image icon) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		all[id] = this;
	}
	
	public Levelset addLevels(List<Level> set){
		this.set = set;
		return this;
	}
}
