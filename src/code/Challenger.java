package code;

import java.awt.Image;
import java.util.ArrayList;

public class Challenger {
	
	static Challenger[] all = new Challenger[255];
	
	String name;
	Dialog dialog;
	int id, fort = 0, pow = 0, resto = 0, renderID = 0, level = 1;
	Spell[] spells = new Spell[6];
	Image icon;
	
	public static final Challenger busterMcKnuckles = new Challenger(0, Bank.buster_walk, "Buster McKnuckles", Dialog.trayatBrawl, 100, 7).setSpells(Spell.punch, Spell.enrage, Spell.bandage).setStats(-10, 11, 11);
	public static final Challenger ogre = new Challenger(1, Bank.troll_slam, "Chief Gork", Dialog.ogre, 0, 11).setSpells(Spell.oakfist, Spell.clobber, Spell.slam).setStats(80, 9, -6);
	public static final Challenger ogre1 = new Challenger(2, Bank.troll_slam, "Chief Merk", Dialog.ogre1, 0, 13).setSpells(Spell.shamanblast, Spell.shamanheal, Spell.meteor).setStats(40, 18, 4);
	public static final Challenger bandit = new Challenger(3, Bank.bandit_run, "Randall Mudeye", Dialog.bandit, 101, 17).setSpells(Spell.crimson, Spell.cloakanddagger, Spell.whirlAxe).setStats(30, 9, 4);
	public static final Challenger monk = new Challenger(4, Bank.monk_cast, "Leng Shuang", Dialog.monk, 6, 20).setSpells(Spell.jab, Spell.zenkick, Spell.flashfreeze, Spell.tigerleap, Spell.craneleap, Spell.chiheal).setStats(45, 12, 4);
	public static final Challenger iceguard = new Challenger(5, Bank.spell_flashfreeze, "Guard Captain Oreth", Dialog.captain, 11, 17).setSpells(Spell.reinforce, Spell.reinforce, Spell.lightguard).setStats(120, 12, 4);
	public static final Challenger gateguard = new Challenger(6, Bank.torchtop, "Gatekeeper Kyirg", Dialog.gatekeeper, 103, 17).setSpells(Spell.arrow, Spell.lightflame, Spell.glacialwinds).setStats(50, 12, 4);
	public static final Challenger olsen = new Challenger(7, Bank.airship, "Admiral Olsen", Dialog.olsen, Hero.barbarian.renderID, 20).setSpells(Spell.arrow, Spell.shockwave, Spell.poisonshot).setStats(0, 12, 8);

	public static final Challenger masterLuna = new Challenger(200, Bank.spell_moonlight, "Turalla, Queen of Stars", Dialog.bandit, Hero.moonpriest.renderID, 17).setSpells(Spell.lunarblast, Spell.lunarblast, Spell.dropcomet).setStats(100, 12, 4);
	public static final Challenger masterPrince = new Challenger(201, Bank.spell_reap, "Pravos, the Blood King", Dialog.masterPrince, Hero.blackknight.renderID, 17).setSpells(Spell.reap, Spell.bloodsteed, Spell.mindshield).setStats(100, 12, 4);
	public static final Challenger masterBar = new Challenger(202, Bank.icon_axe, "Gthirum, Master of Arms", Dialog.masterBarbarian, Hero.barbarian.renderID, 17).setSpells(Spell.shockwave, Spell.rage, Spell.equipAxe).setStats(200, 40, -20);
	public static final Challenger masterPriest = new Challenger(203, Bank.ra, "Pheros, Priestess of Sand", Dialog.masterPriest, Hero.chrono.renderID, 17).setSpells(Spell.clockshock, Spell.radiantsigil, Spell.holynova).setStats(350, 35, 0);
	public static final Challenger masterMesmer = new Challenger(204, Bank.nova, "Pseis The Trickster", Dialog.masterMesmer, Hero.mesmer.renderID, 17).setSpells(Spell.mindtwist, Spell.brainfry, Spell.mindleap, Spell.corruption, Spell.focus, Spell.maw).setStats(230, 35, 60);

	public Challenger(int id, Image icon, String name, Dialog dia, int render, int lvl) {
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.dialog = dia;
		this.renderID = render;
		this.level = lvl;
		all[id] = this;
	}
	
	public Challenger setStats(int fort, int pow, int resto){
		this.fort = fort;
		this.pow = pow;
		this.resto = resto;
		return this;
	}
	
	public Challenger setSpells(Spell s1, Spell s2, Spell s3){
		spells[0] = s1;
		spells[1] = s2;
		spells[2] = s3;
		return this;
	}
	
	public Challenger setSpells(Spell s1, Spell s2, Spell s3, Spell s4, Spell s5, Spell s6){
		spells[0] = s1;
		spells[1] = s2;
		spells[2] = s3;
		spells[3] = s4;
		spells[4] = s5;
		spells[5] = s6;
		return this;
	}
	
	public Player generate(PanelBase p, int x, int y, int w, int h, int dir, boolean focusKill){
		Player pl = new Player(Hero.ph, "en", x, y, level).loadSpells(spells[0], spells[1], spells[2]).setStats(fort, pow, resto);
		if(spells[3]!=null)pl.spells.add(new SpellCast(spells[3]));
		if(spells[4]!=null)pl.spells.add(new SpellCast(spells[4]));
		if(spells[5]!=null)pl.spells.add(new SpellCast(spells[5]));
		pl.dialog = dialog;
		pl.renderID = renderID;
		pl.trueDir = dir;
		pl.width = w;
		pl.height = h;
		pl.focus = focusKill;
		return pl;
	}
	
	public Player generate(PanelBase p, int x, int y, int w, int h, int dir, boolean focusKill, Object tar){
		Player pl = new Player(Hero.ph, "en", x, y, level).loadSpells(spells[0], spells[1], spells[2]).setStats(fort, pow, resto);
		if(spells[3]!=null)pl.spells.add(new SpellCast(spells[3]));
		if(spells[4]!=null)pl.spells.add(new SpellCast(spells[4]));
		if(spells[5]!=null)pl.spells.add(new SpellCast(spells[5]));
		pl.dialog = dialog;
		pl.renderID = renderID;
		pl.trueDir = dir;
		pl.width = w;
		pl.height = h;
		pl.focus = focusKill;
		pl.target = (Player)tar;
		return pl;
	}
}
