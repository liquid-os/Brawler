package code;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Hero {
	
	static Hero[] all = new Hero[255];
	static Random rand = new Random();
	
	ArrayList<Spell> spells = new ArrayList<Spell>();
	Image portrait, portraitFlip;
	int id = 0, renderID = 0;
	String name, classname;
	boolean starter = true, randomInclusion = true;

	static final Hero barbarian = new Hero(0, "Fullangr", "Barbarian", Bank.portraitDruid, Bank.portraitDruidFlip).setRenderID(11).addSpell(Spell.shockwave).addSpell(Spell.bloodoath).addSpell(Spell.equipBlade).addSpell(Spell.ancestralhaste).addSpell(Spell.equipAxe).addSpell(Spell.rage).addSpell(Spell.equipMace).addSpell(Spell.grudge);
	static final Hero moonpriest = new Hero(1, "Luna", "Moonpriest", Bank.portraitLuna, Bank.portraitLunaFlip).setRenderID(4).addSpell(Spell.lunarblast).addSpell(Spell.dropcomet).addSpell(Spell.moonlight).addSpell(Spell.eclipse).addSpell(Spell.planet).addSpell(Spell.stardust).addSpell(Spell.cosmic).addSpell(Spell.cosmicblast).addSpell(Spell.cosmiclash);
	static final Hero ent = new Hero(2, "Leadbark", "Ent", Bank.portraitFarmer, Bank.portraitFarmerFlip).setRenderID(7).addSpell(Spell.oakfist).addSpell(Spell.spores).addSpell(Spell.clobber).addSpell(Spell.tree).addSpell(Spell.thorns).addSpell(Spell.spores1).addSpell(Spell.slam).addSpell(Spell.pinestrike).addSpell(Spell.woodwhirl).addSpell(Spell.vilespore);
	static final Hero monk = new Hero(3, "Lo'Shu", "Monk", Bank.portraitMonk, Bank.portraitMonkFlip).setRenderID(6).addSpell(Spell.jab).addSpell(Spell.chiheal).addSpell(Spell.tempeststrike).addSpell(Spell.twelvetemplestrike).addSpell(Spell.greatfirefist).addSpell(Spell.ninefuryfist).addSpell(Spell.chislice).addSpell(Spell.dragonkick).addSpell(Spell.chislumber).addSpell(Spell.zenkick).addSpell(Spell.meditate).addSpell(Spell.chikick).addSpell(Spell.craneleap).addSpell(Spell.tigerleap);
	static final Hero blackknight = new Hero(4, "Bezarus", "Blood Prince", Bank.portraitMonk, Bank.portraitMonkFlip).setRenderID(8).addSpell(Spell.reap).addSpell(Spell.bloodsteed).addSpell(Spell.bloodbolt).addSpell(Spell.sacrifice).addSpell(Spell.siphon).addSpell(Spell.rainofflesh).addSpell(Spell.mutilate).addSpell(Spell.epidemic).addSpell(Spell.bloodhawk);
	static final Hero wizard = new Hero(5, "Manath-Aran", "Archmage", Bank.portraitMage, Bank.portraitMageFlip).setRenderID(15).addSpell(Spell.icicle).addSpell(Spell.mindleap).addSpell(Spell.manabomb).addSpell(Spell.volatility).addSpell(Spell.moltensigil).addSpell(Spell.combustion).addSpell(Spell.bind).addSpell(Spell.cataclysm);
	static final Hero ranger = new Hero(6, "Hylenn", "Ranger", Bank.portraitMonk, Bank.portraitMonkFlip).setRenderID(10).addSpell(Spell.arrow).addSpell(Spell.shockarrow).addSpell(Spell.eagle).addSpell(Spell.firearrow).addSpell(Spell.ghostarrow).addSpell(Spell.blackarrow).addSpell(Spell.executioner).addSpell(Spell.barrage).addSpell(Spell.poisonshot).addSpell(Spell.spidershot);
	static final Hero assassin = new Hero(7, "Jegax", "Assassin", Bank.wip, Bank.wip).setRenderID(101).addSpell(Spell.stab).addSpell(Spell.dash).addSpell(Spell.shroud).addSpell(Spell.thunder).addSpell(Spell.crimson).addSpell(Spell.blackout).addSpell(Spell.cloakanddagger).addSpell(Spell.devour).addSpell(Spell.overpower).addSpell(Spell.ichor).addSpell(Spell.smokecloud).addSpell(Spell.bladevault).addSpell(Spell.exterminate).addSpell(Spell.timeflip).addSpell(Spell.steal).addSpell(Spell.daggerdance);
	static final Hero necro = new Hero(8, "Keralyn", "Heretic", Bank.portraitNecro, Bank.portraitNecroFlip).setRenderID(5).addSpell(Spell.bonefire).addSpell(Spell.voidbolt).addSpell(Spell.banshee).addSpell(Spell.submission).addSpell(Spell.maw).addSpell(Spell.creeper).addSpell(Spell.curse).addSpell(Spell.fever).addSpell(Spell.plagueblast);
	static final Hero frostcaller = new Hero(9, "Chill Bill", "Snowbro", Bank.portraitMonk, Bank.portraitMonkFlip).setRenderID(102).addSpell(Spell.chill).addSpell(Spell.flashfreeze).addSpell(Spell.glacialwinds).addSpell(Spell.frostlink).addSpell(Spell.frostwall).addSpell(Spell.penguin).addSpell(Spell.coldsnap).addSpell(Spell.snowman).addSpell(Spell.brittle);
	static final Hero warden = new Hero(10, "Sir Steeljaw", "Paladin", Bank.wip, Bank.wip).setRenderID(13).addSpell(Spell.castigate).addSpell(Spell.clemency).addSpell(Spell.sanctuary).addSpell(Spell.aegis).addSpell(Spell.bulwark).addSpell(Spell.reinforce).addSpell(Spell.lightflame).addSpell(Spell.rebuke);
	static final Hero snoopdog = new Hero(11, "Snoop Dog", "Kushlord", Bank.dancing, Bank.dancing).setStarterHero(false).setRandomInclusion(false).setRenderID(9).addSpell(Spell.joint).addSpell(Spell.puff).addSpell(Spell.quickscope).addSpell(Spell.dankblast);
	static final Hero ph = new Hero(12, "Placeholder", "PH", Bank.dancing, Bank.dancing).setStarterHero(false).setRandomInclusion(false).setRenderID(9);
	static final Hero mesmer = new Hero(13, "Illusius", "Mesmer", Bank.portraitMesmer, Bank.portraitMesmerFlip).setRenderID(14).addSpell(Spell.mindtwist).addSpell(Spell.brainrot).addSpell(Spell.focus).addSpell(Spell.deception).addSpell(Spell.psychnova).addSpell(Spell.shadeform).addSpell(Spell.psychmissile).addSpell(Spell.brainfry).addSpell(Spell.corruption).addSpell(Spell.mindshield);
	static final Hero chrono = new Hero(14, "Suut'Het", "Chrono Priest", Bank.wip, Bank.wip).setRenderID(12).addSpell(Spell.clockshock).addSpell(Spell.redirect).addSpell(Spell.shattertime).addSpell(Spell.brilliantsands).addSpell(Spell.eternitysands).addSpell(Spell.rabeam).addSpell(Spell.sandsoftime).addSpell(Spell.radiantsigil).addSpell(Spell.holynova);
	static final Hero worldbender = new Hero(15, "Nyrth", "Worldshaper", Bank.portraitDruid, Bank.portraitDruidFlip).setRenderID(2).addSpell(Spell.rockbolt).addSpell(Spell.grassbolt).addSpell(Spell.totemWater).addSpell(Spell.life).addSpell(Spell.earthbolt).addSpell(Spell.totemicflames).addSpell(Spell.electrify).addSpell(Spell.totemFire).addSpell(Spell.totemAir).addSpell(Spell.totemEarth).addSpell(Spell.stoneguard).addSpell(Spell.totemStorm);
	//static final Hero engineer = new Hero(16, "Whizzle", "Engineer", Bank.wip, Bank.wip).setRenderID(3).addSpell(Spell.castigate).addSpell(Spell.clemency).addSpell(Spell.sanctuary).addSpell(Spell.aegis).addSpell(Spell.bulwark).addSpell(Spell.lightflame).addSpell(Spell.rebuke);

	public Hero(int id, String name, String classname, Image port, Image port1){
		all[id] = this;
		this.id = id;
		this.name = name;
		this.classname = classname;
		this.portrait = port;
		this.portraitFlip = port1;
	}
	
	public static Hero getRandomHero(){
		int r = rand.nextInt(all.length);
		if(all[r]!=null){
			if(all[r].randomInclusion)
			return all[r];
		}
		return getRandomHero();
	}
	
	public Hero setStarterHero(boolean b){
		starter = b;
		return this;
	}
	
	public Hero setRandomInclusion(boolean b){
		randomInclusion = b;
		return this;
	}
	
	public Hero setRenderID(int id){
		this.renderID = id;
		return this;
	}
	
	public Hero addSpell(Spell s){
		this.spells.add(s);
		return this;
	}

	public void applyRenders(Player player) {
		int r = this.renderID;
		if(this==assassin){
			if(player.countEffect(EffectType.deadlyfocus) < 1){
				player.addEffect(new Effect(1, EffectType.deadlyfocus, player, -1));
			}
		}
		player.renderID = r;
		if(r == 0){
			player.width = 80;
			player.height = 150;
		}
		if(r == 1){
			player.width = 40;
			player.height = 90;
		}
		if(r == 2){
			player.width = 40;
			player.height = 90;
		}
		if(r == 3){
			player.width = 70;
			player.height = 130;
		}
		if(r == 4){
			player.width = 40;
			player.height = 90;
		}
		if(r == 5){
			player.width = 40;
			player.height = 90;
		}
		if(r == 6){
			player.width = 40;
			player.height = 90;
		}
		if(r == 7){
			player.width = 40;
			player.height = 120;
		}
		if(r == 8){
			player.width = 50;
			player.height = 90;
		}
		if(r == 9){
			player.width = 40;
			player.height = 90;
		}
		if(r == 10){
			player.width = 40;
			player.height = 90;
		}
		if(r == 11){
			player.width = 50;
			player.height = 90;
		}
		if(r == 12){
			player.width = 40;
			player.height = 90;
		}
		if(r == 13){
			player.width = 40;
			player.height = 90;
		}
		if(r == 14){
			player.width = 40;
			player.height = 90;
		}
		if(r == 15){
			player.width = 40;
			player.height = 90;
		}
		if(r == 101){
			player.width = 40;
			player.height = 90;
		}
		if(r == 102){
			player.width = 40;
			player.height = 90;
		}
		if(r == 103){
			player.width = 40;
			player.height = 90;
		}
	}
}
