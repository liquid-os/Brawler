package code;

import java.awt.Image;
import java.util.ArrayList;

public class Skill {
	
	static Skill[] all = new Skill[255];
	
	int id = 0, maxRank = 1, rankCost = 1;
	float col = 0F, lvlInflation = 0F, costInflation = 0F, row = 0F;
	Hero heroReq = null;
	String name;
	String[] desc;
	int minlvl = 1;
	Image icon = null;
	ArrayList<Skill> preReqs = new ArrayList<Skill>();
	public static final Skill power = new Skill(33, 0, -1F, "Lesser Power", Bank.spell_rage, 5, 3, 0, new String[]{"Increases magnitude of","abilities by 1 point","per rank."});

	public static final Skill crit = new Skill(2, 0, 2, "Lesser Impact", Bank.arcane,  5, 3, 6, new String[]{"Increases crit chance by 2%","per rank."});
	public static final Skill haste = new Skill(1, 0, 4, "Lesser Haste", Bank.bansheecoil, 5, 3, 6, new String[]{"Reduces cooldowns by 5%","per rank."});
	public static final Skill agility = new Skill(3, 2.5F, 3F, "Agility", Bank.spell_kick, 4, 12, 10, new String[]{"Increases the speed that you","gain momentum by 25% per rank."}).addPrerequisite(Skill.haste);
	public static final Skill oblivion = new Skill(4, 0.5F, 3F, "Oblivion", Bank.portal, 1, 12, 10, new String[]{"Critical strikes will","deal 200% damage up from","150%."}).addPrerequisite(Skill.power).addPrerequisite(Skill.crit);
	public static final Skill midcrit = new Skill(5, 1F, 2F, "Impact", Bank.arcane, 5, 5, 15, new String[]{"Increases crit chance by 2%","per rank."}).addPrerequisite(Skill.crit);
	public static final Skill oblivion1 = new Skill(6, 1.5F, 3F, "Stormstrikes", Bank.spell_stormbolt, 1, 20, 20, new String[]{"Critical strikes will","deal 250% damage and","cause a lightning strike."}).addPrerequisite(Skill.oblivion).addPrerequisite(Skill.midcrit);
	public static final Skill sting = new Skill(10, 2F, 2.25F, "Poison Stings", Bank.spell_toxicarrow, 1, 30, 20, new String[]{"Your poison arrows","apply a damage over","time effect that","deals 100% extra damage","over 8sec, ticking every","second."}).setCostInflation(5).setLvlInflation(2).setHeroReq(Hero.ranger);
	public static final Skill starfire = new Skill(11, 2F, 2.25F, "Starfire", Bank.spell_stardust, 25, 6, 15, new String[]{"Increases crit chance","by 1% per rank."}).setCostInflation(1).setLvlInflation(2).setHeroReq(Hero.moonpriest);
	public static final Skill bloodmastery = new Skill(12, 2F, 2.25F, "Blood Mastery", Bank.siphon, 4, 3, 7, new String[]{"You master the","art of blood."}).setCostInflation(1).setLvlInflation(1).setHeroReq(Hero.blackknight);
	public static final Skill bloodboil = new Skill(13, 3.5F, 2.25F, "Dark Resilience", Bank.spell_bloodbeam, 2, 15, 18, new String[]{"When you take damage,","20% per rank is converted","into an effect that","heals you after 10sec."}).setCostInflation(5).setLvlInflation(5).addPrerequisite(Skill.bloodmastery).setHeroReq(Hero.blackknight);
	public static final Skill symbiosis = new Skill(14, 2F, 2.25F, "Symbiosis", Bank.spell_brainrot, 1, 7, 7, new String[]{"Corruption will no longer","consume your other DoT","effects."}).setHeroReq(Hero.mesmer);
	public static final Skill insanity = new Skill(15, 3.5F, 2.25F, "Insanity", Bank.occule, 1, 12, 6, new String[]{"Gives a 10%","chance to gain a","Mesmeric Charge when","you cast a spell."}).setHeroReq(Hero.mesmer);
	public static final Skill pestilence = new Skill(16, 4.5F, 1.8F, "Pestilence", Bank.spell_corruption, 1, 25, 12, new String[]{"Reapplying an existing","effect will increase","its duration by 50% of","the new effect's duration."}).setHeroReq(Hero.mesmer);
	public static final Skill sandguard = new Skill(17, 2F, 2.25F, "Sandguard", Bank.spell_sandsigil, 1, 20, 7, new String[]{"Your [Radiant Sigil] will","apply absorb effects to","itself each time it","releases a wave,","absorbing an amount","equal to 50% of the","damage dealt.","The absorb lasts","1.25 seconds."}).setHeroReq(Hero.chrono);
	public static final Skill ethereality = new Skill(18, 4.25F, 3.2F, "Ethereality", Bank.shade, 4, 5, 12, new String[]{"At max rank,","your crits give you","2 seconds of","[Ethereal Form]."}).setCostInflation(2F).setHeroReq(Hero.mesmer);
	public static final Skill jumpcharge = new Skill(19, 3F, 1F, "Alacrity", Bank.rune, 5, 4, 13, new String[]{"Increases jump","recharge time by","20ms per rank."}).setCostInflation(2F).addPrerequisite(Skill.agility);
	public static final Skill jumps = new Skill(20, 4.5F, 1F, "Physics Sucks", Bank.ent_fall0, 1, 30, 16, new String[]{"Awards an additional","jump."}).setCostInflation(2F).addPrerequisite(Skill.jumpcharge);
	public static final Skill defender = new Skill(21, 2F, 2.25F, "Ancestral Defence", Bank.axe_swing, 5, 3, 4, new String[]{"Reduces damage taken","by 10% per rank","while axe and","shield are up."}).setCostInflation(1.5F).setLvlInflation(1).setHeroReq(Hero.barbarian);
	public static final Skill taunt = new Skill(22, 3F, 2F, "Hateful Presence", Bank.axe_swing, 1, 15, 11, new String[]{"Dealing direct","damage will cause","enemies to attack","you instead of","their current target."}).setHeroReq(Hero.barbarian);
	public static final Skill wounds = new Skill(23, 3.5F, -0.5F, "Laceration", Bank.sword_run, 1, 15, 11, new String[]{"Dealing direct","damage will cause","enemies to attack","you instead of","their current target."}).setHeroReq(Hero.barbarian);
	public static final Skill bigbang = new Skill(24, 3.5F, -0.5F, "The Big Bang", Bank.planet, 1, 22, Spell.planet.minlvl+1, new String[]{"Your planets grow","at a massively accelerated","rate, but deal 75%","less damage."}).setHeroReq(Hero.moonpriest);
	public static final Skill traction = new Skill(25, 3.5F, 2F, "Planetary Traction", Bank.spell_summonplanet, 1, 11, Spell.planet.minlvl, new String[]{"Your planets will","now roll instead of","bouncing."}).setHeroReq(Hero.moonpriest);
	public static final Skill worldinflames = new Skill(26, 3F, 2.5F, "World in Flames", Bank.totemFire, 1, 16, 12, new String[]{"Your fire totems deal 50%","less dammage but have 100%","more range."}).setHeroReq(Hero.worldbender);
	public static final Skill longevity = new Skill(27, 0, 1F, "Lesser Longevity", Bank.clockproj, 5, 3, 6, new String[]{"Your effects last 5%","longer per rank."});
	public static final Skill longevity1 = new Skill(28, 1F, 1F + 0.25f, "Longevity", Bank.clockproj, 5, 3, 12, new String[]{"Your effects last 5%","longer per rank."}).addPrerequisite(Skill.longevity);
	public static final Skill longevity2 = new Skill(29, 2F, 1F + 0.25f * 2, "Greater Longevity", Bank.clockproj, 5, 3, 25, new String[]{"Your effects last 5%","longer per rank."}).addPrerequisite(Skill.longevity1);
	//public static final Skill leech = new Skill(30, 0, 3F, "Lesser Leeching", Bank.spell_reaper, 5, 3, 6, new String[]{"Your crits heal you","for 1% of your max HP","per rank."});
	public static final Skill longevity3 = new Skill(30, 3F, 1F + 0.25f * 3, "Master Longevity", Bank.clockproj, 5, 3, 40, new String[]{"Your effects last 5%","longer per rank."}).addPrerequisite(Skill.longevity2).setCostInflation(10);
	public static final Skill longevity4 = new Skill(31, 4F, 1F + 0.25f * 4, "Legendary Longevity", Bank.clockproj, 2, 3, 75, new String[]{"Your effects last 5%","longer per rank."}).addPrerequisite(Skill.longevity3).setCostInflation(20);
	//public static final Skill constitution = new Skill(8, 2F, -1F, "Constitution", Bank.rawplus, 4, 7, 8, new String[]{"You gain an additional","point of health for","each point of","Fortitude each rank."}).setLvlInflation(2).setCostInflation(4).addPrerequisite(Skill.haste);
	public static final Skill haste1 = new Skill(32, 1, 4F, "Haste", Bank.bansheecoil, 5, 10, 6, new String[]{"Reduces cooldowns by 5%","per rank."}).addPrerequisite(Skill.haste).setLvlInflation(2);
	public static final Skill power1 = new Skill(34, 1, -1F + 0.25f, "Power", Bank.spell_rage, 5, 6, 10, new String[]{"Increases magnitude of","abilities by 1 point","per rank."}).addPrerequisite(Skill.power).setLvlInflation(2);
	public static final Skill power2 = new Skill(35, 2, -1F + 0.25f * 2, "Greater Power", Bank.spell_rage, 3, 16, 20, new String[]{"Increases magnitude of","abilities by 1 point","per rank."}).addPrerequisite(Skill.power1).setLvlInflation(2);
	public static final Skill power3 = new Skill(36, 3, -1F + 0.25f * 3, "Master Power", Bank.spell_rage, 1, 20, 45, new String[]{"Increases magnitude of","abilities by 1 point","per rank."}).addPrerequisite(Skill.power2).setLvlInflation(2);
	public static final Skill fort = new Skill(37, 0, -2F, "Lesser Fortitude", Bank.plus, 5, 3, 0, new String[]{"Increases your health","with every rank."}).setLvlInflation(2);
	public static final Skill fort1 = new Skill(38, 1, -2F + 0.25f, "Fortitude", Bank.plus, 5, 3, 5, new String[]{"Increases your health","with every rank."}).addPrerequisite(Skill.fort).setLvlInflation(2);
	public static final Skill fort2 = new Skill(39, 2, -2F + 0.25f * 2, "Greater Fortitude", Bank.plus, 3, 3, 10, new String[]{"Increases your health","with every rank."}).addPrerequisite(Skill.fort1).setLvlInflation(2);
	public static final Skill fort3 = new Skill(40, 3, -2F + 0.25f * 3, "Master Fortitude", Bank.plus, 1, 3, 20, new String[]{"Increases your health","with every rank."}).addPrerequisite(Skill.fort2).setLvlInflation(2);
	public static final Skill constitution = new Skill(7, 5F, -2F, "Constitution", Bank.rawplus, 4, 5, 8, new String[]{"You gain 25% more HP","from Fortitude each rank."}).setLvlInflation(5).setCostInflation(20).addPrerequisite(Skill.haste).addPrerequisite(fort3);
	public static final Skill recovery = new Skill(41, 4F, -1F, "Recovery", Bank.spell_bandage, 1, 40, 25, new String[]{"Each time you cast a spell with","a cooldown greater than 3sec","you will be healed for 5%","of your max HP."}).setLvlInflation(2).setCostInflation(4).addPrerequisite(Skill.fort2).addPrerequisite(Skill.longevity1);
	public static final Skill rage = new Skill(42, 1.5F, 0.5F, "Dire Rage", Bank.spell_dragonkick, 5, 7, 12, new String[]{"Your power increases by 3 per rank","while you are below 25%","of your max HP."}).setLvlInflation(2).setCostInflation(4).addPrerequisite(Skill.power1).addPrerequisite(Skill.longevity1);
	public static final Skill defiance = new Skill(43, 0.5F, -1.5F, "Iron Will", Bank.shield, 3, 7, 12, new String[]{"When you are stunned","you will automatically break","the effect. Cannot occur","more than once every 30 seconds.","(Reduced by 5sec each rank)"}).setLvlInflation(2).setCostInflation(4).addPrerequisite(Skill.fort).addPrerequisite(Skill.power);
	public static final Skill retaliation = new Skill(44, 4F, 3.5F, "Retaliation", Bank.clock, 4, 20, 50, new String[]{"When you are critically","hit, you gain 1.5sec of 50%","cooldown reduction per rank."}).setLvlInflation(5).setCostInflation(5).addPrerequisite(Skill.haste1);
	public static final Skill resto = new Skill(45, 1F, -2.5F, "Restoration", Bank.heart, 4, 20, 25, new String[]{"Restores 2.5% of your max HP every"," 10 seconds per rank."}).setLvlInflation(5).setCostInflation(5).addPrerequisite(Skill.defiance).addPrerequisite(Skill.fort1);

	public Skill(int id, float row, float col, String name, Image icon, int maxRank, int cost, int minlvl, String[] desc) {
		this.id = id;
		this.rankCost = cost;
		this.maxRank = maxRank;
		this.name = name;
		this.desc = desc;
		this.icon = icon;
		this.minlvl = minlvl;
		this.row = row;
		this.col = col;
		all[id] = this;
	}
	
	public Skill setLvlInflation(float lvlInflation) {
		this.lvlInflation = lvlInflation;
		return this;
	}
	
	public Skill addPrerequisite(Skill s){
		preReqs.add(s);
		return this;
	}

	public Skill setCostInflation(float costInflation) {
		this.costInflation = costInflation;
		return this;
	}

	public Skill setHeroReq(Hero heroReq) {
		this.heroReq = heroReq;
		return this;
	}
	
	public int getMinLvl(int rank){
		return (int) (minlvl+rank*lvlInflation);
	}
	
	public int getCost(int rank){
		return (int) (rankCost+rank*costInflation);
	}
		
	public void onInit(Player p, int rank){
		if(this == defiance){
			p.addEffect(new Effect(0, EffectType.ironwilleffect, p, 20000));
		}
		if(this == resto){
			p.addEffect(new Effect(0, EffectType.resto, p, -1));
		}
		if(this==agility){
			p.phys.motionCap+=rank*15;
		}
		if(this==starfire){
			p.crit+=rank;
		}
		if(this==crit||this==midcrit){
			p.crit+=rank*2;
		}
		if(this==oblivion){
			p.critMultiplier = 2;
		}
		if(this==oblivion1){
			p.critMultiplier = 2.5D;
		}
		if(this==haste||this==haste1){
			p.cooldownMod-=rank*5;
		}
		if(this==jumpcharge){
			p.jumpCharge-=20*rank;
		}
		if(this==jumps){
			p.maxJumps = 2+rank;
		}
		if(this==constitution){
			double r = rank/2;
			p.fortMulti = (double)(4+r);
			p.setStats(p.fort, p.pow, p.pow);
		}
	}
}
