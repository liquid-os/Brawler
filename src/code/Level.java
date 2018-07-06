package code;

import java.awt.Point;
import java.util.ArrayList;

public class Level {
	
	static Level[] all = new Level[255];
	static ArrayList<Level> story = new ArrayList<Level>();
	ArrayList<Challenger> opponents = new ArrayList<Challenger>();
	String name;
	String[] desc;
	int id, level, grade, xp = 20, spawnX = 500, spawnY = 350;
	
	public Level(int id, String name, int level, int grade, boolean exclude){
		this.id = id;
		this.name = name;
		this.level = level;
		this.grade = grade;
		if(!exclude)story.add(this);
		all[id] = this;
	}
	
	public static Level pass = new Level(0, "The Journey Begins", 0, 0, false).addChallenger(Challenger.busterMcKnuckles).setDesc(new String[]{"You face the current brawl champion from your hometown","of Trayat. "});
	public static Level trail = new Level(1, "The Skyfire Trail", 1, 0, false).addChallenger(Challenger.ogre).addChallenger(Challenger.ogre1).setDesc(new String[]{"You begin your trek to the next village","on your quest, but run into some","trouble along the way."});
	public static Level bandit = new Level(2, "The Bandit Guild", 1, 0, false).addChallenger(Challenger.bandit).setDesc(new String[]{"A large floating island at","the end of the Skyfire Ridge","appears to be home to a few","wayward souls."});
	public static Level iceridge = new Level(3, "Iceridge Village", 1, 0, false).setDesc(new String[]{"You arrive at Iceridge and","ask around about nearby","brawl champions."});
	public static Level monk = new Level(4, "The Monk on the Mountain", 1, 0, false).setSpawn(450, 175*30).addChallenger(Challenger.monk).setDesc(new String[]{"You make the pilgrimage","up the frosty and","confront the legendary","monk Leng Shuang."});
	public static Level fort = new Level(5, "The Frostfort", 1, 0, false).setXP(12).setSpawn(764, 659).addChallenger(Challenger.gateguard).addChallenger(Challenger.iceguard).setDesc(new String[]{"You continue onward","into the fortress of","Frostfort. Here you","will encounter a worthy","challenger and those","who serve him."});
	public static Level airship = new Level(6, "Air Raid", 1, 0, false).setXP(40).setSpawn(764, 659).setDesc(new String[]{"."});
	
	
	public static Level masterPrince = new Level(190, "The King's Lair", 1, 0, false).addChallenger(Challenger.masterPrince).setDesc(new String[]{"The lair of the Blood King."});
	public static Level masterBarbarian = new Level(191, "The Armsmaster's Lodge", 1, 0, false).addChallenger(Challenger.masterBar).setDesc(new String[]{"The home of the Armsmaster."});
	public static Level masterPriest = new Level(192, "The Priestess' Temple", 1, 0, false).addChallenger(Challenger.masterPriest).setDesc(new String[]{"The temple of the Priestess."});
	public static Level masterMesmer = new Level(193, "The Trickster's Cove", 1, 0, false).addChallenger(Challenger.masterMesmer).setDesc(new String[]{"The lair of the Trickster."});
	
	public Level setDesc(String[] desc){
		this.desc = desc;
		return this;
	}
	
	public Level setXP(int xp){
		this.xp = xp;
		return this;
	}
	
	public Level setSpawn(int x, int y){
		this.spawnX = x;
		this.spawnY = y;
		return this;
	}
	
	public Level addChallenger(Challenger ch){
		this.opponents.add(ch);
		return this;
	}
	
	public void generate(GridPanel p){
		if(id==0){
			p.bg = Bank.hills;
			p.sky = Bank.sky;
			p.load(Bank.getLevelData("level1"), true);
			Player pl = Challenger.busterMcKnuckles.generate(p, 3000, 750, 30, 90, 1, true);
			p.addPlayer(pl, false);
		}
		if(id==1){
			p.bg = Bank.hillsRoll;
			p.sky = Bank.sky;
			p.load(Bank.getLevelData("level2"), true);
			p.objects.add(new ObjectSpawnEvent(0, 6690, 0, 1210, 630, 7900, 90, 12, 2250, p));
			p.objects.add(new ObjectPortal(13840, 480-30, 100, 120));
			p.objects.add(new ObjectHPOrb("", 8890, 310, 30, 30, 100));
			Player pl = Challenger.ogre.generate(p, 4890, 540, 80, 150, 1, false);
			Player pl1 = Challenger.ogre1.generate(p, 10000-p.scrollX, 430-p.scrollY, 80, 150, 1, false);
			p.addPlayer(pl, false);
			p.addPlayer(pl1, false);
			p.viewDistY = 1080;
		}
		if(id == 2){
			p.bg = Bank.hillsRoll;
			p.sky = Bank.sky;
			p.load(Bank.getLevelData("level3"), true);
			p.objects.add(new ObjectSpawnEvent(1, Properties.width/2-500, Properties.height/2-500, 1000, 1000, 1800, -120, 30, 2000, p));
		}
		if(id == 3){
			p.bg = Bank.hillsSnow;
			p.sky = Bank.skyDark;
			p.load(Bank.getLevelData("level4"), true);
			p.weather = Weather.snow;
			p.viewDistY = 3000;
			ObjectSpawnEvent spe = new ObjectSpawnEvent(2, 970-30, 1130-30, 30+30, 30+60, 970-15, 1130, 12, 10000, p);
			spe.started = true;
			spe.drawPortal = true;
			p.objects.add(spe);
			ObjectSpawnEvent spe1 = new ObjectSpawnEvent(3, 3230, 1740, 60, 90, 2010, 800, 25, 3500, p);
			spe1.started = true;
			spe1.spawns.add(new Point(370, 1030));
			spe1.spawns.add(new Point(2010, 800));
			spe1.spawns.add(new Point(680, 1850));
			spe1.spawns.add(new Point(3000, 1800));
			spe1.drawPortal = true;
			p.objects.add(spe1);
			p.objects.add(new ObjectSnowbro(500, 1000, 40, 90));
		}
		if(id == 4){
			p.bg = Bank.hillsSnow;
			p.sky = Bank.skyDark;
			p.load(Bank.getLevelData("level5"), true);
			p.weather = Weather.snow;
			p.viewDistY = 190*30;
			Player pl = Challenger.monk.generate(p, 1790, 3270, 40, 90, 1, true);
			p.addPlayer(pl, false);
		}
		if(id == 5){
			p.bg = Bank.hillsSnow;
			p.weather = Weather.snow;
			p.load(Bank.getLevelData("level6"), true);
			p.viewDistY = 3800;
			Player pl = Challenger.gateguard.generate(p, 1540, 665, 30, 90, 1, false);
			Player pl1 = Challenger.iceguard.generate(p, 2265, 435, 60, 120, 1, true);
			p.addPlayer(pl, false);
			p.addPlayer(pl1, false);
		}
		if(id == airship.id){
			p.bg = null;
			p.sky = Bank.sky;
			p.weather = Weather.cloudy;
			p.load(Bank.getLevelData("airship"), true);
			p.viewDistY = 3800;
			ObjectSpawnEvent spe1 = new ObjectSpawnEvent(4, 200, 200, 60, 90, 2010, 800, 25, 1800, p);
			spe1.started = true;
			spe1.drawPortal = false;
			spe1.spawns.add(new Point(840, 300));
			spe1.spawns.add(new Point(1100, 300));
			spe1.spawns.add(new Point(1500, 300));
			spe1.spawns.add(new Point(1850, 300));
			spe1.spawns.add(new Point(2140, 300));
			spe1.spawns.add(new Point(2450, 300));
			p.objects.add(spe1);
			Player pl = Challenger.olsen.generate(p, 1000, 700-90, 40, 90, 1, true);
			pl.strikeHammer = new SpellCast(Spell.firearrow);
			pl.superHammer = new SpellCast(Spell.shockwave);
			p.addPlayer(pl, false);
			pl.addEffect(new Effect(0,EffectType.grandhammer,pl,-1));
		}
		if(id == masterPrince.id){
			p.bg = Bank.hillsHell;
			p.sky = Bank.skyRed;
			p.load(Bank.getLevelData("MA_prince"), true);
			Player pl = Challenger.masterPrince.generate(p, 980, 560-90, 40, 90, 1, true);
			pl.addEffect(new Effect(0,EffectType.hellwrath,pl,-1));
			p.addPlayer(pl, false);
		}
		if(id == masterBarbarian.id){
			p.bg = Bank.hillsRoll;
			p.sky = Bank.sky;
			p.load(Bank.getLevelData("MA_bar"), true);
			Player pl = Challenger.masterBar.generate(p, 980, 560-60, 50, 120, 1, true);
			p.addPlayer(pl, false);
		}
		if(id == masterPriest.id){
			p.bg = Bank.hillsSand;
			p.sky = Bank.sky;
			p.load(Bank.getLevelData("MA_priest"), true);
			Player pl = Challenger.masterPriest.generate(p, 1200, 560-60, 40, 90, 1, true);
			p.addPlayer(pl, false);
		}
		if(id == masterMesmer.id){
			p.bg = Bank.hillsMoon;
			p.sky = Bank.skyDark;
			p.load(Bank.getLevelData("MA_mesmer"), true);
			Player pl = Challenger.masterMesmer.generate(p, 1000, 1210, 40, 90, 1, true);
			p.addPlayer(pl, false);
		}
	}
}
