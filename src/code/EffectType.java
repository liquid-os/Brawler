package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EffectType {
	
	String[] desc;
	String name;
	Random rand = new Random();
	public static EffectType[] all = new EffectType[200];
	boolean negative = false;
	
	public static final EffectType moonlight = new EffectType(0, "Moonlight", 500, 0, 10, Bank.spell_moonstrike, new String[]{"Taking 50% additional damage."});
	public static final EffectType twelvetemple = new EffectType(1, "Twelve Temple Strike", 100, 0, 1, Bank.spell_jab, new String[]{"Twelve Temple Strike!"});
	public static final EffectType clemency = new EffectType(2, "Clemency", 500, 0, 2, Bank.spell_retribution, new String[]{"Healing increases over time."});
	public static final EffectType sanctuary = new EffectType(3, "Absorb", 1000, 0, 5, Bank.spell_defend, new String[]{"Absorbs %v damage."});
	public static final EffectType electrified = new EffectType(4, "ELECTRIFIED!", 400, 0, 5, Bank.spell_stormbolt, new String[]{"Don't touch anything."});
	public static final EffectType shroud = new EffectType(5, "Shroud", 400, 0, 5, Bank.spell_shroud, new String[]{"Shrouded, 200% move speed. Next attack deals 100% more damage."});
	public static final EffectType feedback = new EffectType(6, "Feedback", 400, 0, 5, Bank.spell_feedback, new String[]{"The next attack you make will hurt you."});
	public static final EffectType banshee = new EffectType(7, "Banshee's Curse", 1000, 0, 5, Bank.spell_banshee, new String[]{"Deals %t damage over %d sec. Upon ending, a","healthpack will spawn for the original caster."});
	public static final EffectType hellforged = new EffectType(8, "Hellforged Seal", 500, 0, 5, Bank.spell_inferno, new String[]{"Deals %t damage over %d sec."});
	public static final EffectType stun = new EffectType(9, "Stun", 10000, 0, 5, Bank.stun, new String[]{"Unable to act for %v milliseconds."});
	public static final EffectType stardust = new EffectType(10, "Stardust", 10000, 0, 5, Bank.spell_moonstrike, new String[]{"Your attacks stun for 1sec per stack."});
	public static final EffectType creeper = new EffectType(11, "Creeper Leeching", 1000, 0, 5, Bank.spell_creeper, new String[]{"When you take damage, a healthpack spawns for 20% the amount."});
	public static final EffectType suffering = new EffectType(12, "Suffering Eternal", 2500, 0, 5, Bank.spell_maw, new String[]{"Deals %t damage over %d sec."});
	public static final EffectType cataclysm = new EffectType(13, "Cataclysm", 1000, 0, 5, Bank.spell_cataclysm, new String[]{"Deals damage on end.","Damage increased by %v per sec.","%dat damage pending."});
	public static final EffectType spores = new EffectType(14, "Spores", 1000, 0, 5, Bank.spell_spores, new String[]{"Heals on end.","Heal increased by %v per sec.","%dat healing pending."});
	public static final EffectType thorns = new EffectType(15, "Thorns", 1000, 0, 5, Bank.spell_thorns, new String[]{"Reflects all damage.","Does not absorb any damage."});
	public static final EffectType fury = new EffectType(16, "Blood Fury", 1000, 0, 5, Bank.spell_sacrifice, new String[]{"Dealing 300% damage.","Cannot be healed."});
	public static final EffectType bloodsteed = new EffectType(17, "Bloodsteed", 1000, 0, 5, Bank.spell_bloodsteed, new String[]{"Morphed into a Bloodsteed."});
	public static final EffectType high = new EffectType(18, "High as a Kite", 500, 0, 5, Bank.spell_puff, new String[]{"Is that supposed to happen?"});
	public static final EffectType slam = new EffectType(19, "SLAM!", 500, 0, 5, Bank.fist, new String[]{"Falling from the stratosphere."});
	public static final EffectType concussion = new EffectType(20, "Concussion", 500, 0, 5, Bank.stun, new String[]{"Stunned, taking %v00% damage."});
	public static final EffectType invasion = new EffectType(21, "Invasion!", 2000, 0, 5, Bank.stun, new String[]{"Enemies approach."});
	public static final EffectType dragonfire = new EffectType(22, "Long-Fire", 2000, 0, 5, Bank.spell_dragonkick, new String[]{"Dealing %v damage every second."});
	public static final EffectType chill = new EffectType(23, "Chilled", 500, 0, 5, Bank.spell_frostlink, new String[]{"Slowed."});
	public static final EffectType frozen = new EffectType(24, "Frozen", 2000, 0, 5, Bank.spell_flashfreeze, new String[]{"Unable to act. Taking 50% increased damage.","Critical strikes shatter the ice, freeing you but dealing 150% damage."});
	public static final EffectType frostbite = new EffectType(25, "Frostbite", 500, 0, 5, Bank.spell_bones, new String[]{"Taking %v damage every","0.5sec."});
	public static final EffectType burn = new EffectType(26, "Burning", 500, 0, 5, Bank.spell_firearrow, new String[]{"Taking %v damage every","0.5sec."});
	public static final EffectType haunt = new EffectType(27, "Haunted", 5000, 0, 5, Bank.spell_ghostarrow, new String[]{"Taking 50% less healing."});
	public static final EffectType hunt = new EffectType(27, "Hunted", 5000, 0, 5, Bank.spell_hunt, new String[]{"Someone's following you."});
	public static final EffectType arrow = new EffectType(28, "Ouch!", 5000, 0, 5, Bank.spell_arrow, new String[]{"You've been shot!"});
	public static final EffectType barrage = new EffectType(29, "Barrage", 200, 0, 5, Bank.spell_hunt, new String[]{"Firing arrows every","0.2 seconds."});
	public static final EffectType darkresilience = new EffectType(30, "Dark Resilience", 10000, 0, 5, Bank.spell_bloodbolt, new String[]{"Healed for %v every 10sec."});
	public static final EffectType rapidAging = new EffectType(31, "Rapid Aging", 1000, 0, 5, Bank.spell_clockshock, new String[]{"Hurt for %v every second."});
	public static final EffectType venom = new EffectType(32, "Spider Venom", 250, 0, 5, Bank.spell_venomarrow, new String[]{"Slowed."});
	public static final EffectType corrosion = new EffectType(33, "Toxic Corrosion", 1000, 0, 5, Bank.spell_toxicarrow, new String[]{"Taking 50% additional damage."});
	public static final EffectType mindcharge = new EffectType(34, "Mesmeric Charge", 1000, 0, 5, Bank.occule, new String[]{"Healed for 2% max HP every second."});
	public static final EffectType brainrot = new EffectType(35, "Brain Rot", 700, 0, 5, Bank.spell_brainrot, new String[]{"Taking %v damage every 700ms."});
	public static final EffectType ethereal = new EffectType(36, "Ethereal Form", 700, 0, 5, Bank.shade, new String[]{"In ethereal form."});
	public static final EffectType brainfry = new EffectType(37, "Mental Decay", 1000, 0, 5, Bank.spell_brainfry, new String[]{"Deals %v damage every second and increases cooldowns by 100%."});
	public static final EffectType corruption = new EffectType(38, "Psychic Corruption", 1000, 0, 5, Bank.spell_corruption, new String[]{"Deals %v damage every second.","Consumes one effect each tick to increase damage by 50%."});
	public static final EffectType timerip = new EffectType(39, "Disruption", 2000, 0, 5, Bank.spell_shattertime, new String[]{"Deals %v damage every 2sec."});
	public static final EffectType sands = new EffectType(40, "Veil of Sand", 2000, 0, 5, Bank.spell_sandsoftime, new String[]{"Healed for %v every 2 seconds."});
	public static final EffectType rablessing = new EffectType(41, "Blessing of Ra", 1000, 0, 5, Bank.spell_sandsoftime, new String[]{"Healed for %v every second."});
	public static final EffectType axe = new EffectType(42, "Equip Axe and Shield", 1000, 0, 5, Bank.icon_axe, new String[]{"Axe and Shield equipped."});
	public static final EffectType sword = new EffectType(43, "Equip Blade", 1000, 0, 5, Bank.icon_blade, new String[]{"Blade is equipped."});
	public static final EffectType mace = new EffectType(44, "Equip Mace", 1000, 0, 5, Bank.icon_axe, new String[]{"Mace is equipped."});
	public static final EffectType hemmorage = new EffectType(45, "Hemmorage", 500, 0, 5, Bank.spell_bleed, new String[]{"Deals damage and","slows every 0.5sec."});
	public static final EffectType whirl = new EffectType(46, "Whirl", 100, 0, 5, Bank.icon_axe, new String[]{"Whirling."});
	public static final EffectType bloodoath = new EffectType(47, "Blood Oath", 1000, 0, 5, Bank.spell_bleed, new String[]{"Healed every 0.5sec"});
	public static final EffectType haste = new EffectType(48, "Ancestral Haste", 5000, 0, 5, Bank.spell_haste, new String[]{"Cooldowns decreased by 50%."});
	public static final EffectType rage = new EffectType(49, "Ancient Rage", 5000, 0, 5, Bank.spell_rage, new String[]{"Damage output increased by 100%."});
	public static final EffectType grudge = new EffectType(50, "Thousand Year Grudge", 1000, 0, 5, Bank.spell_rage, new String[]{"%v damage ready to release."});
	public static final EffectType suppression = new EffectType(51, "Suppression", 5000, 0, 5, Bank.spell_rage, new String[]{"%v damage ready to release."});
	public static final EffectType flames = new EffectType(52, "Totemic Flames", 500, 0, 5, Bank.fire, new String[]{"%v damage dealy every 0.5sec"});
	public static final EffectType windhaste = new EffectType(53, "Windborne Haste", 500, 0, 5, Bank.bar_whirl, new String[]{"%v damage dealy every 0.5sec"});
	public static final EffectType grandhammer = new EffectType(54, "Royal Warhammer", 5000, 0, 5, Bank.grandhammer_walk, new String[]{"Royal Warhammer is equipped."});
	public static final EffectType holycharge = new EffectType(55, "Crusade", 5000, 0, 5, Bank.grandhammer_walk, new String[]{"Charging."});
	public static final EffectType charging = new EffectType(56, "Alignment", 5000, 0, 5, Bank.occule, new String[]{"Charging up a missile."});
	public static final EffectType planet = new EffectType(57, "Incubating Planet", 5000, 0, 5, Bank.planet, new String[]{"Incubating a planet."});
	public static final EffectType quickness = new EffectType(58, "Cosmic Quickness", 5000, 0, 5, Bank.spell_quickness, new String[]{"Next spell triggers no cooldown."});
	public static final EffectType sigil = new EffectType(59, "Molten Sigil", 1000, 0, 5, Bank.fire, new String[]{"Taking damage every second. Next spell will transfer the effect."});
	public static final EffectType volatility = new EffectType(60, "Volatility", 1000, 0, 5, Bank.firenova, new String[]{"Critical hits against you deal extra damage and stun."});
	public static final EffectType pacify = new EffectType(61, "Pacify", 1000, 0, 5, Bank.flash, new String[]{"All abilities used for the duration have their effect nullified."});
	public static final EffectType sporeholder = new EffectType(62, "Vile Spores", 1000, 0, 5, Bank.spell_vilespores, new String[]{"All abilities used for the duration have their effect nullified."});
	public static final EffectType decay = new EffectType(63, "Fungal Decay", 1000, 0, 5, Bank.spell_fungus, new String[]{"You take %v damage every second for 4 seconds."});
	public static final EffectType grace = new EffectType(64, "Grace", 5000, 0, 5, Bank.holyNova, new String[]{"Movement cap removed. Speed increased."});
	public static final EffectType cloud = new EffectType(65, "Strangled", 5000, 0, 5, Bank.cloud, new String[]{"Speed reduced. Effectiveness of abilities halved."});
	public static final EffectType ichor = new EffectType(66, "Ichor Split", 5000, 0, 5, Bank.glop, new String[]{"Moving fast and taking no damage."});
	public static final EffectType cloakanddagger = new EffectType(67, "Cloak and Dagger", 200, 0, 5, Bank.spell_cloakanddagger, new String[]{"Slashing up and taking names."});
	public static final EffectType pouch = new EffectType(68, "Scarlet Pouch", 200, 0, 5, Bank.spell_thiefbag, new String[]{"Slashing up and taking no damage."});
	public static final EffectType mending = new EffectType(69, "Shirathi Mending", 500, 0, 5, Bank.spell_bandage, new String[]{"Healing and cleansing effects."});
	public static final EffectType resolve = new EffectType(70, "Resolve of Shirath", 5000, 0, 5, Bank.spell_resolve, new String[]{"Taking normal damage."});
	public static final EffectType deadlyfocus = new EffectType(71, "Broken Resolve", 5000, 0, 5, Bank.spell_shattered, new String[]{"Increases damage taken by 50%."});
	public static final EffectType curse = new EffectType(72, "Curse of Ereth", 5000, 0, 5, Bank.spell_curse, new String[]{"Applies a random effect upon ending."});
	public static final EffectType fever = new EffectType(73, "Fever of Undeath", 5000, 0, 5, Bank.spell_fever, new String[]{"If you die while under this effect, you are returned to full HP."});
	public static final EffectType plague = new EffectType(74, "Midnight Plague", 300, 0, 5, Bank.spell_plague1, new String[]{"Deals %v damage or %v healing every 2 sec. Equal chances."});
	public static final EffectType sandsoftime = new EffectType(75, "Sands of Time", 300, 0, 5, Bank.spell_hourglass, new String[]{"Cooldowns reduced."});
	public static final EffectType rainofflesh = new EffectType(76, "Rain of Flesh", 3000, 0, 5, Bank.spell_rainofflesh, new String[]{"Ready to rumble."});
	public static final EffectType arm = new EffectType(77, "Arm of Hellwrath", 3000, 0, 5, Bank.spell_bloodhawk, new String[]{"Ready to rumble."});
	public static final EffectType hellwrath = new EffectType(78, "Hellwrath Embrace", 3000, 0, 5, Bank.spell_rainofflesh, new String[]{"Ready to rumble."});
	public static final EffectType parachute = new EffectType(79, "Parachute", 50, 0, 5, Bank.parachute, new String[]{"Parachuting."});
	public static final EffectType prayer = new EffectType(80, "Prayerstate", 50, 0, 5, Bank.spell_holynova, new String[]{"Praying."});
	public static final EffectType vulnerability = new EffectType(81, "Vulnerability", 1000, 0, 5, Bank.spell_holynova, new String[]{"Taking 2x damage."});
	public static final EffectType reflect = new EffectType(82, "Arcane Reflection", 1000, 0, 5, Bank.spell_defend, new String[]{"Reflecting projectiles."});
	public static final EffectType ironwill = new EffectType(83, "Breaking Chains...", 1000, 0, 5, Bank.spell_defend, new String[]{"Periodically resisting stuns."});
	public static final EffectType ironwilleffect = new EffectType(84, "Iron Will", 1000, 0, 5, Bank.spell_defend, new String[]{"Next stun will be resisted."});
	public static final EffectType retaliation = new EffectType(85, "Retaliation", 1000, 0, 5, Bank.clockproj, new String[]{"Reduces cooldowns by 50%."});
	public static final EffectType resto = new EffectType(86, "Restoration", 10000, 0, 5, Bank.clockproj, new String[]{"Restores HP every 10sec."});

	int interval = 0;
	int type = 0, value = 1;
	Image img;

	public EffectType(int id, String n, int interval, int type, int val, Image img){
		this.all[id] = this;
		this.interval = interval;
		this.type = type;
		this.value = val;
		this.img = img;
		this.name = n;
	}
	
	public EffectType(int id, String n, int interval, int type, int val, Image img, String[] d){
		this.all[id] = this;
		this.interval = interval;
		this.type = type;
		this.value = val;
		this.img = img;
		this.name = n;
		this.desc = d;
	}
	
	public void tick(Object p, Effect e){
		int val = e.value;
		int i = (int) ((System.currentTimeMillis()-e.start) * e.maxTicks / e.durationMax);
		//val =  (i >= e.values.length ? e.value : e.values[i]);
		if(this == twelvetemple && p instanceof Player){
			Player pl = ((Player)p);
			pl.renderAtk = true;
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(pl, p.posX+(pl.trueDir == 0 ? p.width-10 : -10), p.posY+i, 20, 20, val, Bank.fist);
			proj.phys.motionX = pl.trueDir == 0 ? (50+i*2) : -(50+i*2);
			proj.maxTime = 100;
			proj.knockback = false;
			proj.effect = 0;
			Display.currentScreen.objects.add(proj);
			if(rand.nextInt(3)==0)pl.renderAtk = false;
		}
		if(this == resto){
			float pct = ((Player)p).getSkillTier(Skill.resto);
			p.healRaw((int) (pct * ((float)(p.healthMax / 100))), false);
		}
		if(this==parachute){
			p.phys.motionY = -20;
			p.phys.motionX = 0;
			if(p.collideDown())e.active = false;
		}
		if(this==darkresilience){
			p.heal(e.dat, false);
			e.dat = 0;
		}
		if(this==sigil){
			p.hurt(val, false);
		}
		if(this == ironwill){
			p.addEffect(new Effect(0, EffectType.ironwilleffect, (Player) p, 20000));
		}
		if(this==hemmorage){
			new ClipShell(rand.nextInt(2)==0?"bloodspurt1.wav":"bloodspurt2.wav").start();
			p.hurt(val, false, null);
			if(p.phys!=null)
			p.phys.motionX = 0;
			p.renderBlood(50, rand.nextInt(20)-rand.nextInt(20), rand.nextInt(20)-rand.nextInt(20));
		}
		if(this==decay){
			p.hurt(val, false);
		}
		if(this==whirl){
			Player pl = ((Player)p);
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(pl, p.posX+p.width/4, p.posY+p.height/4, p.width, p.height/2, (int) (val), (e.dat==0?Bank.slice:Bank.sliceLeft));
			proj.phys.motionX = e.dat == 0 ? (150) : -(150);
			proj.maxTime = 120;
			proj.knockback = false;
			proj.effect = 0;
			Display.currentScreen.objects.add(proj);
			if(e.dat==0)p.phys.motionX+=50;else p.phys.motionX-=50;
			if(e.dat == 0)e.dat = 1;else e.dat = 0;
		}
		if(this==cloakanddagger){
			Player pl = ((Player)p);
			if(e.dat==0)p.moveLeft(100);
			if(e.dat==1)p.moveRight(100);
			if(e.dat==2){
				p.moveLeft(20);
				p.phys.motionX = -60;
				p.phys.motionY = 90;
			}
			if(e.dat==3){
				p.moveRight(20);
				p.phys.motionX = 60;
				p.phys.motionY = 90;
			}
			new ClipShell("shadebuff.wav").start();
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(pl, p.posX+p.width/4, p.posY+p.height/4, p.width, p.height/2, (int) (val), (e.dat==0||e.dat==2?Bank.slice:Bank.sliceLeft));
			proj.phys.motionX = (e.dat == 0 || e.dat == 2 ? (150) : -(150));
			proj.phys.motionY = (e.dat == 2 || e.dat == 3 ? (-140) : 0);
			proj.maxTime = 120;
			proj.knockback = false;
			proj.effect = 0;
			proj.effect = 32;
			Display.currentScreen.objects.add(proj);
			e.dat++;
			if(e.dat > 3)e.dat = 0;
		}
		if(this==sands){
			p.heal(val, false);
			for(int j = 0; j < 30; ++j){
				int s = rand.nextInt(4)+1;
				Particle pa = new Particle(p.posX+p.width/2-s/2, p.posY+p.height/3, Particle.DUST, Color.YELLOW);
				pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60);
				pa.phys.motionY = 15+rand.nextInt(30);
				Display.currentScreen.objects.add(pa);
			}
		}
		if(this==barrage){
			new ClipShell("arrowhit"+(rand.nextInt(2)==0?"":"1")+".wav").start();
			Player pl = ((Player)p);
			pl.phys.motionX += (pl.trueDir==0?-40:40);
			pl.phys.motionY+=2;
			pl.renderAtk = true;
			Projectile proj = new Projectile(pl, p.posX+p.width/2-20, p.posY+p.height/3, 40, 14, val, Bank.arrow);
			proj.knockback = false;
			proj.phys.motionX = pl.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.effect = 22;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = false;
			Display.currentScreen.objects.add(proj);
			if(rand.nextInt(3)==0)pl.renderAtk = false;
		}
		if(this == bloodoath){
			new ClipShell(rand.nextInt(2)==0?"bloodspurt1.wav":"bloodspurt2.wav").start();
			p.renderBlood(50, rand.nextInt(20)-rand.nextInt(20), rand.nextInt(20)-rand.nextInt(20));
			p.heal(val, false);
		}
		if(this == clemency){
			p.heal(i, false);
		}
		if(this == mindcharge){
			p.healRaw(p.healthMax/50, false);
		}
		if(this == invasion){
			Display.currentScreen.objects.add(new CreatureTroll(rand.nextInt(Properties.width), rand.nextInt(Properties.height)));
		}
		if(this == electrified){
			new ClipShell("shortstatic.wav").start();
			p.phys.motionX = 0;
			p.phys.motionY = 0;
		}
		if(this == venom){
			p.phys.motionX = 0;
			for(int j = 0; j < 30; ++j){
				Particle particle = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.LIQUID, Color.GREEN);
				int s = 4+rand.nextInt(8);
				particle.isBlood = true;
				particle.img = Bank.glop;
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(60)-rand.nextInt(60);
				particle.phys.motionY = 30+rand.nextInt(41);
				Display.currentScreen.objects.add(particle);
			}
		}
		if(this == chill){
			if(p.phys!=null)
			p.phys.motionX = 0;
			for(int j = 0; j < 30; ++j){
				Particle particle = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.SPARKLE, Color.CYAN);
				int s = 4+rand.nextInt(8);
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(60)-rand.nextInt(60);
				particle.phys.motionY = 30+rand.nextInt(41);
				Display.currentScreen.objects.add(particle);
			}
		}
		if(this == high){
			new ClipShell("boom.wav", 4F).start();
			Display.currentScreen.objects.add(new ObjectSnoopgif(true, rand.nextInt(Properties.width), rand.nextInt(Properties.height), 80+rand.nextInt(221), 80+rand.nextInt(221)));
		}
		if(this == suffering){
			p.hurt(val, false);
			Particle par = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:p.width/2), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:p.height/2), Particle.EXPLODE, Util.transparent);
			par.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-p.phys.motionX;
			par.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
			par.basePhysics = false;
			int s = 4+rand.nextInt(7);
			par.width = s;
			par.height = s;
			Display.currentScreen.objects.add(par);
		}
		if(this==sigil||this==flames||this==dragonfire||this==burn||this==frostbite||this==rapidAging||this==brainrot||this==brainfry||this==timerip){
			p.hurt(val, false, null);
			if(e.caster!=null)
			e.caster.dealt+=val;
		}
		if(this == banshee){
			if(e.caster!=null)
			e.caster.dealt+=val;
			p.hurt(val, false);
			val*=1.25F;
			if(p.countEffect(banshee)>=4){
				p.removeEffect(banshee, 100);
				p.addEffect(new Effect(val*4, hellforged, (name==null?null:Display.currentScreen.getPlayer(this.name)), 7000));
			}
		}
		if(this == hellforged){
			p.hurt(val, true);
			if(e.caster!=null)
			e.caster.dealt+=val;
		}
		if(this == cataclysm){
			e.dat+=val;
		}
		if(this == spores){
			e.dat+=val;
		}
		if(this==corruption){
			if(e.caster!=null)
			e.caster.dealt+=val;
			p.hurt(val, false, null);
			if(p.effects.size() > 0){
				boolean done = false;
				if(!done){
					for(int j = 0; j < p.effects.size(); ++j){
						Effect eff = p.effects.get(j);
						if(eff!=null){
							if((e.caster.hasSkill(Skill.symbiosis)?(eff.type!=brainrot&&eff.type!=brainfry):true)&&eff.type!=this){
								val+=val/2;
								p.removeEffect(p.effects.get(j).type);
								done = true;
								new ClipShell("staticlaunch.wav").start();
								for(int q = 0; q < 14; ++q){
									int maxP = 20;
									int mx = rand.nextInt(100), my = rand.nextInt(140)-rand.nextInt(140);
									int mp = 3+rand.nextInt(3), mpx = 2+rand.nextInt(3);
									int rax = rand.nextInt(2), ray = rand.nextInt(2);
									for(int x = 0; x < maxP; ++x){
										int s = x/2+5;
										Particle part = new Particle(p.posX+p.width/2, p.posY+p.height/2-(s/2), Particle.EXPLODE, new Color(200-x*4, 0, 250-x*5, 180));
										part.width = s;
										part.height = s;
										part.maxTime = 450;
										part.phys.motionX = (rax==0?(x*mpx+mx):-(x*mpx+mx));
										part.phys.motionY = 100+(ray==0?(x*mp+my):-(x*mp+my));
										part.basePhysics = false;
										Display.currentScreen.objects.add(part);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public EffectType getRandomEffect(){
		int r = rand.nextInt(all.length);
		if(all[r]!=null)return all[r]; else return getRandomEffect();
	}
	
	public void onEnd(Object p, Effect e){
		int val = e.value;
		if(this==resolve){
			p.addEffect(new Effect(1, EffectType.deadlyfocus, (Player) p, -1));
		}
		if(this==curse){
			p.addEffect(new Effect(val, getRandomEffect(), e.caster, e.durationMax));
		}
		if(this==fever && p instanceof Player){
			((Player)p).respawns--;
		}
		if(this==arm){
			Player pl = ((Player)p);
			pl.renderAtk = true;
			Projectile proj = new Projectile(pl, p.posX, p.posY, p.width, p.height, e.value, Bank.bag);
			proj.effect = 13;
			proj.knockback = true;
			proj.maxTime = 100;
			proj.visEffect = -1;
			proj.phys.motionX = pl.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = false;
			Display.currentScreen.objects.add(proj);
		}
		if(this==pouch && p instanceof Player){
			Player pl = ((Player)p);
			pl.renderAtk = true;
			Projectile proj = new Projectile(pl, p.posX+p.width/2-20, p.posY+p.height/3, 40, 40, e.dat, Bank.bag);
			proj.effect = 32;
			proj.knockback = true;
			proj.visEffect = 2;
			proj.phys.motionX = pl.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = false;
			Display.currentScreen.objects.add(proj);
		}
		if(this==charging){
			Player pl = ((Player)p);
			pl.phys.motionX += (pl.trueDir==0?-100:100);
			pl.renderAtk = true;
			Projectile proj = new Projectile(pl, p.posX+p.width/2-40, p.posY+p.height/3, 80, 40, val, Bank.cosblast);
			proj.knockback = true;
			proj.visEffect = 1;
			proj.phys.motionX = pl.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = false;
			Display.currentScreen.objects.add(proj);
		}
		if(this == banshee){
			Display.currentScreen.objects.add(new ObjectHPOrb((p instanceof Player ? ((Player)p).getUsername() : null), p.posX+p.width/2-10, p.posY+p.height/2-10, 20, 20, val*(e.maxTicks))); 
		}
		if(this==planet){
			int size = 0;
			size = (int) ((System.currentTimeMillis()-e.start) * 300 / e.durationMax);
			if(size > 300)size = 300;
			for(int i = 0; i < 50; ++i){
				PhysicsPlug ph = new PhysicsPlug(p);
				if(rand.nextInt(2)==0)ph.motionX = (-rand.nextInt(200)); else ph.motionX = rand.nextInt(200);
				ph.motionY = -(rand.nextInt(60)+40);
				ObjectBlock.split(p.posX+p.width/2-size/2, p.posY-60-size/2, GridBlock.dirt.getID(), 8+rand.nextInt(12), ph);
			}
			p.hurtRaw(p.healthMax/2, true, null);
			p.addEffect(new Effect(1, EffectType.stun, (Player)p, 3000));
		}
		if(this==cataclysm){
			new ClipShell("dragonbreath.wav", 1F).start();
			new ClipShell("punch.wav", 3F).start();
			p.hurt(e.dat, true);
			if(e.caster!=null)
			e.caster.dealt+=e.dat;
			Display.currentScreen.shake(300, 11);
			for(int i = 0; i < 30; ++i){
				Particle par = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:p.width/2), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:p.height/2), Particle.EXPLODE, rand.nextInt(2)==0?Color.RED:Color.YELLOW);
				par.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-p.phys.motionX;
				par.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
				par.basePhysics = false;
				int s = 5+rand.nextInt(9);
				par.width = s;
				par.height = s;
				Display.currentScreen.objects.add(par);
			}
		}
		if(this==spores){
			p.heal(e.dat, true);
			Display.currentScreen.shake(300, 11);
			for(int i = 0; i < 30; ++i){
				Particle par = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:p.width/2), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:p.height/2), Particle.EXPLODE, rand.nextInt(2)==0?Color.GREEN:Color.MAGENTA);
				par.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-p.phys.motionX;
				par.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
				par.basePhysics = false;
				int s = 5+rand.nextInt(9);
				par.width = s;
				par.height = s;
				Display.currentScreen.objects.add(par);
			}
		}
	}
	
	public EffectType flagNegative(boolean b){
		this.negative = b;
		return this;
	}

	public void render(Effect e, Object p, Graphics g) {
		int val = e.value;
		if(this==rainofflesh){
			if(rand.nextInt(15)==0){
				p.renderBlood(60, rand.nextInt(100)-rand.nextInt(100), rand.nextInt(100)-rand.nextInt(100));
			}
		}
		if(this==pacify){
			g.drawImage(Bank.shade, p.posX+p.width/2-20, p.posY-50, 40, 40, null);
		}
		if(this==parachute){
			int w = p.width+p.width/2, h = p.height;
			g.drawImage(Bank.parachute, p.posX+p.width/2-w/2, p.posY-h+p.height/4, w, h, null);
		}
		if(this==pouch){
			g.drawImage(Bank.bag, p.posX+p.width/2-20, p.posY-60, 40, 40, null);
		}
		if(this==charging){
			Particle pa = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:rand.nextInt(p.width)), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:rand.nextInt(p.height )), Particle.CHARGED, new Color(185, 0, 240));
			pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionX/2);
			pa.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionY/2);
			pa.basePhysics = false;
			int s = 4+rand.nextInt(7);
			pa.maxTime = 200;
			pa.width = s;
			pa.height = s;
			Display.currentScreen.objects.add(pa);
			int size = 20;
			int dist = 80-(int) ((System.currentTimeMillis() - e.start) * 80 / e.durationMax);
			g.drawImage(Bank.grayplanet, p.posX+p.width/2-dist-size/2, p.posY-size-dist, size, size, null);
			g.drawImage(Bank.brightplanet, p.posX+p.width/2+dist-size/2, p.posY-size-dist, size, size, null);
			g.drawImage(Bank.planet, p.posX+p.width/2-dist/2-size/2, p.posY-size-10-dist, size, size, null);
			g.drawImage(Bank.pinkplanet, p.posX+p.width/2+dist/2-size/2, p.posY-size-10-dist, size, size, null);
			g.drawImage(Bank.moon, p.posX+p.width/2-size/2, p.posY-size*2-dist, size, size, null);
		}
		if(this==grace){
			Particle pa = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:rand.nextInt(p.width)), p.posY+(p.phys.motionY<0?(rand.nextInt(p.height)):p.phys.motionY>0?(rand.nextInt(p.height)):p.height), Particle.CHARGED, rand.nextInt(2)==0?Color.WHITE:Color.YELLOW);
			pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionX/2);
			pa.phys.motionY = 80+rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionY/2);
			pa.basePhysics = false;
			int s = 4+rand.nextInt(7);
			pa.maxTime = 200;
			pa.width = s;
			pa.height = s;
			Display.currentScreen.objects.add(pa);
		}
		if(this==sigil){
			Color color = (rand.nextInt(2)==0?Color.YELLOW:Color.RED);
			Particle pa = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:rand.nextInt(p.width)), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:rand.nextInt(p.height)), Particle.CHARGED, color);
			pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionX/2);
			pa.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionY/2);
			pa.basePhysics = false;
			int s = 4+rand.nextInt(7);
			pa.maxTime = 200;
			pa.width = s;
			pa.height = s;
			Display.currentScreen.objects.add(pa);
		}
		if(this==volatility){
			int w = p.width;
			g.drawImage(Bank.fire, p.posX, p.posY-w+5, w, w, null);
			Color color = (rand.nextInt(2)==0?Color.YELLOW:Color.RED);
			Particle pa = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:rand.nextInt(p.width)), p.posY, Particle.CHARGED, color);
			pa.phys.motionX = rand.nextInt(20)-rand.nextInt(20);
			pa.phys.motionY = rand.nextInt(100);
			pa.basePhysics = false;
			int s = 4+rand.nextInt(7);
			pa.maxTime = 200;
			pa.width = s;
			pa.height = s;
			Display.currentScreen.objects.add(pa);
		}
		if(this==cloakanddagger){
			Color color = Color.BLACK;
			for(int i = 0; i < 5; ++i){
				Particle pa = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:rand.nextInt(p.width)), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:rand.nextInt(p.height)), Particle.CHARGED, color);
				pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionX/2);
				pa.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(p.phys.motionY/2);
				pa.basePhysics = false;
				int s = 4+rand.nextInt(7);
				pa.maxTime = 200;
				pa.width = s;
				pa.height = s;
				Display.currentScreen.objects.add(pa);
			}
			g.setColor(color);
			g.drawOval(p.posX+p.width/2-p.height/2, p.posY, p.height, p.height);
		}
		if(this==planet){
			int size = 0;
			size = (int) ((System.currentTimeMillis()-e.start) * 300 / e.durationMax);
			if(size > 300)size = 300;
			Image planet = e.dat == 0 ? Bank.planet : e.dat == 1 ? Bank.brightplanet : e.dat == 2 ? Bank.pinkplanet : e.dat == 3 ? Bank.grayplanet : Bank.planet;
			g.drawImage(Bank.planetglow, p.posX+p.width/2-size/2-40, p.posY-60-size-40, size+80, size+80, null);
			g.drawImage(planet, p.posX+p.width/2-size/2, p.posY-60-size, size, size, null);
		}
		if(this==electrified){
			for(int i = 0 ; i < 20+rand.nextInt(31); ++i){
				g.setColor(rand.nextInt(2) == 0 ? Color.YELLOW : Color.CYAN);
				g.drawLine(p.posX+p.width/2, p.posY+p.height/2, p.posX+p.width/2+(rand.nextInt(p.height*2)-rand.nextInt(p.height*2)), p.posY+p.height/2+(rand.nextInt(p.height*2)-rand.nextInt(p.height*2)));
			}
		}
		if(this==mindcharge){
				g.drawImage(Bank.occule, p.posX+p.width/2-50, p.posY-40, 30, 30, null);
				if(p.countEffect(this)>1)
					g.drawImage(Bank.occule, p.posX+p.width/2-15, p.posY-60, 30, 30, null);
				if(p.countEffect(this)>2)
					g.drawImage(Bank.occule, p.posX+p.width/2+15, p.posY-40, 30, 30, null);
		}
		if(this==sanctuary){
			int r = rand.nextInt(7)+2;
			g.drawImage(e.dat1==0?Bank.holyNova:e.dat1==1?Bank.nova:Bank.planetglow, p.posX+p.width/2-40-r/2, p.posY-10, 80+r, p.height+20, null);
		}
		if(this==hellforged){
			Particle par = new Particle(p.posX+(p.phys.motionX<0?p.width:p.phys.motionX>0?0:p.width/2), p.posY+(p.phys.motionY<0?0:p.phys.motionY>0?p.height:p.height/2), Particle.CHARGED, rand.nextInt(2)==0?Color.RED:Color.YELLOW);
			par.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-p.phys.motionX;
			par.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
			par.basePhysics = false;
			int s = 4+rand.nextInt(7);
			par.width = s;
			par.height = s;
			Display.currentScreen.objects.add(par);
		}
		if(this==arrow){
			int dir = e.dat1;
			g.drawImage(dir==0?Bank.arrow:Bank.arrowLeft, p.posX+p.width/2+val+(p.phys.motionX!=0?rand.nextInt(2)-rand.nextInt(2):0), p.posY+e.dat+(p.phys.motionX!=0?rand.nextInt(2)-rand.nextInt(2):0), 40, 14, null);
		}
		if(this==feedback){
			g.drawImage(Bank.spell_feedback, p.posX+p.width/2-10, p.posY-40, 20, 20, null);
		}
		if(this==shroud){
			g.setColor(Util.transparent);
			g.fillRect(p.posX, p.posY, p.width, p.height);
		}
		if(this==high){
			g.setColor(new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat(),0.6F));
			g.fillRect(0, 0, Properties.width, Properties.height);
		}
		if(this==flames){
			g.drawImage(Bank.totemflames, p.posX, p.posY+p.height-70, p.width, 70, null);
		}
		if(this == frozen){
			g.setColor(Util.waterBlue);
			g.fillRect(p.posX-20, p.posY-20, p.width+40, p.height+20);
		}
		if(this==hunt){
			g.drawImage(Bank.huntarrow, p.posX+p.width/2-20, p.posY-50, 40, 50, null);
			g.setColor(Color.RED);
			g.drawLine(p.posX+p.width/2, p.posY, e.caster.posX+e.caster.width/2, e.caster.posY+20);
		}
		if(this==darkresilience){
			int s = e.dat;
			if(s>60)s=60;
			g.drawImage(Bank.siphon, p.posX+p.width/2-s/2, p.posY-s, s, s, null);
		}
		if(this==stun||this==concussion){
			for(int i = 0; i < 3; ++i){
				g.setColor(Color.WHITE);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10+rand.nextInt(30)));
				g.drawString("?", p.posX+p.width/2-g.getFontMetrics().stringWidth("?")/2+(rand.nextInt(60)-rand.nextInt(60)), p.posY-rand.nextInt(50)+rand.nextInt(50));
			}
		}
		if(this==rage){
			for(int i = 0; i < 10; ++i){
				int s = rand.nextInt(9)+3;
				g.setColor(Color.RED);
				g.fillOval(p.posX+rand.nextInt(p.width)+rand.nextInt(10)-rand.nextInt(10)-s/2, p.posY-10+rand.nextInt(p.height/3+10), s, s);
			}
		}
		if(this==mace){
			int w = p.height*2;
			int h = p.height+p.width;
			Player pl = ((Player)p);
			Image tex = null;
			tex = (pl.trueDir==0?Bank.mace_stand:Bank.maceLeft_stand);
			if(pl.right)tex = Bank.mace_run;
			if(pl.left)tex = Bank.maceLeft_run;
			if(pl.renderAtk)tex = (pl.trueDir==0?Bank.mace_swing:Bank.maceLeft_swing);
			if(pl.renderHit())tex = (pl.trueDir==0?Bank.mace_hit:Bank.maceLeft_hit);
			g.drawImage(tex, p.posX+p.width/2-w/2, p.posY+p.height-h, w, h, null);
		}
		if(this==sword){
			int w = p.height*2;
			int h = p.height+p.width;
			Player pl = ((Player)p);
			Image tex = null;
			tex = (pl.trueDir==0?Bank.sword_stand:Bank.swordLeft_stand);
			if(pl.right)tex = Bank.sword_run;
			if(pl.left)tex = Bank.swordLeft_run;
			if(pl.renderAtk)tex = (pl.trueDir==0?Bank.sword_swing:Bank.swordLeft_swing);
			if(pl.renderHit())tex = (pl.trueDir==0?Bank.sword_hit:Bank.swordLeft_hit);
			g.drawImage(tex, p.posX+p.width/2-w/2, p.posY+p.height-h, w, h, null);
		}
		if(this==axe){
			int w = p.height*2;
			int h = p.height+p.width;
			Player pl = ((Player)p);
			Image tex = null;
			tex = (pl.trueDir==0?Bank.axe_stand:Bank.axeLeft_stand);
			if(pl.right)tex = Bank.axe_run;
			if(pl.left)tex = Bank.axeLeft_run;
			if(pl.renderAtk)tex = (pl.trueDir==0?Bank.axe_swing:Bank.axeLeft_swing);
			if(pl.renderHit())tex = (pl.trueDir==0?Bank.axe_hit:Bank.axeLeft_hit);
			if(p.countEffect(EffectType.whirl) > 0)tex = Bank.axe_whirl;
			g.drawImage(tex, p.posX+p.width/2-w/2, p.posY+p.height-h, w, h, null);
		}
		if(this==arm){
			int w = p.height*2;
			int h = p.height+p.width;
			Player pl = ((Player)p);
			Image tex = null;
			tex = (pl.trueDir==0?Bank.princearm_right:Bank.princearm_left);
			g.drawImage(tex, p.posX+p.width/2-w/2, p.posY+p.height-h, w, h, null);
		}
		if(this==hellwrath){
			int w = p.height*4;
			int h = (p.height+p.width)*2;
			Player pl = ((Player)p);
			Image tex = null;
			tex = Bank.princearm_aura;
			g.drawImage(tex, p.posX+p.width/2-w/2, p.posY+p.height-(h/4*3), w, h, null);
		}
		if(this==windhaste){
			for(int i = 0; i < 7; ++i){
				int s = 8+rand.nextInt(10);
				g.drawImage(rand.nextInt(2)==0?Bank.slice:Bank.sliceLeft, p.posX+rand.nextInt(p.width), p.posY+rand.nextInt(p.height), s, s*2, null);
			}
		}
	}
}
