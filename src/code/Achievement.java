package code;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Achievement {
	
	static Achievement[] all = new Achievement[200];

	int id = 0;
	String name;
	String[] desc;
	Image img;
	
	public Achievement(int id, String name, Image img){
		all[id] = this;
		this.id = id;
		this.name = name;
		this.img = img;
	}
	
	public Achievement setDesc(String[] desc){
		this.desc = desc;
		return this;
	}
	
	public static final Achievement win = new Achievement(0, "Victory", Bank.fist).setDesc(new String[]{"Win an Arena match (Quick Game)."});
	public static final Achievement overpower = new Achievement(1, "Overpower", Bank.knifeRight).setDesc(new String[]{"Win a match on full health."});
	public static final Achievement combo = new Achievement(2, "Combo Crit", Bank.explosion).setDesc(new String[]{"Score 3 consecutive critical strikes."});
	public static final Achievement grease = new Achievement(3, "Grease Lightning", Bank.spell_stormbolt).setDesc(new String[]{"Get struck by lightning."});
	public static final Achievement brawling = new Achievement(4, "O' Brawling Love", Bank.spell_demolish).setDesc(new String[]{"Play 100 Arena games (Quick Games) of Hero Brawl!"});
	public static final Achievement campaign = new Achievement(5, "I've Had The Time Of My Life", Bank.spell_torch).setDesc(new String[]{"Complete the Hero Brawl campaign."});
	public static final Achievement secret = new Achievement(6, "You Nosey Little Git", Bank.stun).setDesc(new String[]{"Find the SECRET level!"});
	public static final Achievement hat = new Achievement(7, "I Heard You Like Hats", Bank.hattext).setDesc(new String[]{"Get a HAT!"});
	public static final Achievement kush = new Achievement(8, "The Big One", Bank.gif420).setDesc(new String[]{"Score a critical strike for exactly four hundred and twenty.","","Reward: Snoop Dog Hero"});
	public static final Achievement map = new Achievement(9, "Cartographer", Bank.block).setDesc(new String[]{"Create and save a new level in the Level Studio."});
	public static final Achievement brawling1 = new Achievement(10, "O' Loving Hate", Bank.siphon).setDesc(new String[]{"Play 500 Arena games (Quick Games) of Hero Brawl!"});
	public static final Achievement brawling2 = new Achievement(11, "Really though?", Bank.portal).setDesc(new String[]{"Play 1,000 Arena games (Quick Games) of Hero Brawl!"});
	public static final Achievement brawling3 = new Achievement(12, "#1 Fan", Bank.totemflames).setDesc(new String[]{"Play 10,000 Arena games (Quick Games) of Hero Brawl!"});
	public static final Achievement healer = new Achievement(13, "The Healer", Bank.healbanner).setDesc(new String[]{"Accumulate a total of 5000 healing taken."});
	public static final Achievement fighter = new Achievement(14, "The Fighter", Bank.dmgbanner).setDesc(new String[]{"Accumulate a total of 7000 damage dealt."});
	public static final Achievement tank = new Achievement(15, "The Tank", Bank.tknbanner).setDesc(new String[]{"Accumulate a total of 8500 damage taken."});
	public static final Achievement level10 = new Achievement(16, "Pit Fighter", Bank.level10).setDesc(new String[]{"Reach level 10."});
	public static final Achievement level20 = new Achievement(17, "Gladiator", Bank.level20).setDesc(new String[]{"Reach level 20."});
	public static final Achievement level50 = new Achievement(18, "Grand Gladiator", Bank.level50).setDesc(new String[]{"Reach level 50."});
	public static final Achievement level100 = new Achievement(19, "Champion of the Arena", Bank.level100).setDesc(new String[]{"Reach level 100."});
	public static final Achievement level250 = new Achievement(20, "Hero of the Brawl", Bank.level250).setDesc(new String[]{"Reach level 250."});
	public static final Achievement asyouare = new Achievement(21, "Come As You Are", Bank.nirvana).setDesc(new String[]{"Purchase a run in the Grand Trial."});
	public static final Achievement aced = new Achievement(22, "Ace", Bank.flash).setDesc(new String[]{"Win at least 8 matches","of the Grand Trial with","no losses."});
	public static final Achievement fullwin = new Achievement(23, "World Tour", Bank.planet).setDesc(new String[]{"Win 12 matches in a single Grand Trial."});
	public static final Achievement birthday = new Achievement(24, "Birthday Bash", Bank.cake).setDesc(new String[]{"Open the game on its release anniversary."});
	public static final Achievement birthday1 = new Achievement(25, "Birthday Bash", Bank.cake1).setDesc(new String[]{"Open the game on the dev's birthday."});
	public static final Achievement birthday2 = new Achievement(26, "Ode to Joy", Bank.cake2).setDesc(new String[]{"Open the game on Lily's birthday."});
	public static final Achievement birthday3 = new Achievement(27, "This Is The Worst Day Of My Life", Bank.cake3).setDesc(new String[]{"Launch the game on Lili's birthday."});
	public static final Achievement beta = new Achievement(28, "All Systems Go", Bank.scales).setDesc(new String[]{"Play in the BETA.","","Reward: Hat of Balancing"});
}
