package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class Player extends Object{

	private String username = "missingo";
	int dir = -1, trueDir = 0, cooldownMod = 0, startX = 0, startY = 0;
	GridPanel panel = null;
	ArrayList<SpellCast> spells = new ArrayList<SpellCast>();
	Object target = null;
	Hero hero;
	HeroAI ai = new HeroAI(this);
	int pow, fort, resto, crit = 7, atkRenderID = 0;
	boolean left = false, right = false, renderAtk = false;
	int renderID = rand.nextInt(4), jumpCharge = 750, dealt = 0, taken = 0, healed = 0, jumpChargeReset = 750;
	long lastAttack = -1, lastCharge = System.currentTimeMillis();
	boolean clientPlayer = true, showHud = true;
	int move = 0, respawns = 1, jumps = 2, maxJumps = 2;
	ArrayList<SkillShell> skills = new ArrayList<SkillShell>();
	float walkTick = 0;
	public double critMultiplier = 1.5F, fortMulti = 4F;
	Biome lastBiome = null;
	int keySetID = 0;
	int blockSide = -1;
	
	static double POW_INF = 4.65d, RESTO_INF = 2.8d, FORT_INF = 7.3d, MAX_INF = 5.65d;
	static byte BASE_POW = 0, BASE_RESTO = 0, BASE_FORT = 0, BASE_MAX = 4;
	static byte BASE_RUNES = 10;
	
	public Dialog dialog = null;
	boolean openDialog = true;
	long lastSnoop = System.currentTimeMillis();
	public boolean focus = false, mp = Bank.client!=null, updt = true;
	SpellCast unequip = new SpellCast(Spell.unequip);
	SpellCast strikeAxe = new SpellCast(Spell.strikeAxe), whirlAxe = new SpellCast(Spell.whirlAxe);
	SpellCast strikeMace = new SpellCast(Spell.strikeMace), stunMace = new SpellCast(Spell.stunMace);
	SpellCast strikeSword = new SpellCast(Spell.strikeBlade), superSword = new SpellCast(Spell.superBlade);
	SpellCast strikeHammer = new SpellCast(Spell.royalstrike), superHammer = new SpellCast(Spell.holycharge);
	SpellCast shootplanet = new SpellCast(Spell.planetlaunch);
	private long blockStart = -1;
	
	public Player(Hero h, String user, int x, int y) {
		super(x, y, 30, 60);
		String str = Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "skills");
		String[] stra = str.split(":");
		for(int i = 0; i < stra.length; ++i){
			int j = Integer.parseInt(stra[i]);
			if(j>0){
				skills.add(new SkillShell(Skill.all[i], j));
				Skill.all[i].onInit(this, j);
			}
		}
		this.username = user;
		this.phys = new PhysicsPlug(this, user==Properties.username?true:false).setMotionCap(120);
		this.setCanFall(false);
		this.hero = h;
		pow = this.getSkillTier(Skill.power) + this.getSkillTier(Skill.power1) + this.getSkillTier(Skill.power2) + this.getSkillTier(Skill.power3);
		resto = this.getSkillTier(Skill.power) + this.getSkillTier(Skill.power1) + this.getSkillTier(Skill.power2) + this.getSkillTier(Skill.power3);
		fort = this.getSkillTier(Skill.fort) + this.getSkillTier(Skill.fort1) + this.getSkillTier(Skill.fort2) + this.getSkillTier(Skill.fort3);
		//pow = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+h.classname+".HB"), "power"));
		//fort = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+h.classname+".HB"), "fort"));
		//resto = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+h.classname+".HB"), "resto"));
		int hatID = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+h.classname+".HB"), "hat"));
		if(hatID>-1)hat = Hat.all[hatID];
		this.setStats(fort, pow, resto);
		this.canHarm = true;
		h.applyRenders(this);
		startX = x;
		startY = y;
		if(Display.currentScreen instanceof GridPanel){}else updt = false;
		if(this.hasSkill(Skill.bloodboil)){
			addEffect(new Effect(0,EffectType.darkresilience,this,-1));
		}
		if(this.hasSkill(Skill.defiance)){
			Effect eff = new Effect(0, EffectType.ironwill,this,-1);
			addEffect(eff);
			eff.interval = (40 - 3 * this.getSkillTier(Skill.defiance)) * 1000;
		}
	}
	
	public SpellCast getSpell(int id){
		if(countEffect(EffectType.planet)>0){
			if(spells.get(id).spell==Spell.planet)return shootplanet;
		}
		if(countEffect(EffectType.mace)>0){return (id==0?strikeMace:id==1?stunMace:id==2?unequip:unequip);}
		else if(countEffect(EffectType.axe)>0){return (id==0?strikeAxe:id==1?whirlAxe:id==2?unequip:unequip);}
		else if(countEffect(EffectType.sword)>0){return (id==0?strikeSword:id==1?superSword:id==2?unequip:unequip);}
		else if(countEffect(EffectType.grandhammer)>0){return (id==0?strikeHammer:id==1?superHammer:id==2?unequip:unequip);}
		else {
			return spells.get(id);
		}
	}
	
	public Player(Hero h, String user, int x, int y, int level) {
		super(x, y, 30, 60);
		this.username = user;
		this.phys = new PhysicsPlug(this).setMotionCap(120);
		this.setCanFall(false);
		this.hero = h;
		//int maxP = PanelHeroSelect.getStatCap(level, -1);
		int stat = rand.nextInt(3);
		pow = (int) rand.nextInt(20);
		resto = (int) rand.nextInt(20);
		fort = (int) rand.nextInt(20) * 2;
		if(stat==0)pow*= 1.2F;
		if(stat==1)resto*= 1.2F;
		if(stat==2)fort*= 1.5F;
		canHarm = true;
		h.applyRenders(this);
		startX = x;
		startY = y;
		if(this.hasSkill(Skill.bloodboil)){
			addEffect(new Effect(0,EffectType.darkresilience,this,-1));
		}
		this.setStats(fort, pow, resto);
	}
	
	public void onDeath(){
		if(dialog==Dialog.gatekeeper){
			for(int i = 0; i < 5; ++i){
				for(int j = 0; j < 14; ++j){
					Display.currentScreen.getGrid().setTileID(1550+(i+1)*30+30, 660+90-10*30+j*30, (byte) 0);
				}
			}
		}
	}
	
	public Player setStats(int f, int p, int r){
		this.fort = f;
		this.pow = p;
		this.resto = r;
		this.health = 400 + (int) (fort*(fortMulti));
		this.healthMax = health;
		return this;
	}
	
	public boolean hasSkill(Skill s){
		boolean ret = false;
		for(SkillShell sk : skills){
			if(sk.skill==s&&sk.rank>=sk.skill.maxRank){ret=true;}
		}
		return ret;
	}
	
	public boolean hasSkill(Skill s, int a){
		boolean ret = false;
		for(SkillShell sk : skills){
			if(sk.skill.equals(sk)){
				if(sk.rank>=a)ret=true;
			}
		}
		return ret;
	}
	
	public int getSkillTier(Skill s){
		int ret = 0;
		for(int i = 0; i < skills.size(); ++i){
			if(skills.get(i).skill.equals(s))ret=skills.get(i).rank;
		}
		return ret;
	}
	
	public static int getMaxXP(int lvl){
		return 10+lvl*5;
	}
	
	public Player loadLocalSpells(){
		File f = new File(Bank.path+"chars/"+hero.classname+".HB");
		spells.add(new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(f, "sp1"))]));
		spells.add(new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(f, "sp2"))]));
		spells.add(new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(f, "sp3"))]));
		onSpellInit();
		return this;
	}
	
	public Player loadHeroSpells(){
		spells.add(new SpellCast(hero.spells.get(0)));
		spells.add(new SpellCast(hero.spells.get(1)));
		spells.add(new SpellCast(hero.spells.get(2)));
		onSpellInit();
		return this;
	}
	
	public Player loadRandomSpells(){
		Spell basic = hero.spells.get(0);
		if(rand.nextInt(10)==0)basic = hero.spells.get(rand.nextInt(hero.spells.size()));
		spells.add(new SpellCast(basic));
		spells.add(new SpellCast(hero.spells.get(rand.nextInt(hero.spells.size()))));
		spells.add(new SpellCast(hero.spells.get(rand.nextInt(hero.spells.size()))));
		onSpellInit();
		return this;
	}
	
	public void onSpellInit(){
		for(SpellCast sp : spells){
			if(sp.spell==Spell.grudge){
				addEffect(new Effect(0,EffectType.grudge,this,-1));
			}
		}
	}
	
	public String getUsername(){
		return username;
	}	

	public void update(double delta) {
		if(this.stunned() && this.countEffect(EffectType.ironwilleffect) >= 1){
			this.removeEffect(EffectType.ironwilleffect);
			this.removeEffect(EffectType.stun);
		}
		if(hero==Hero.snoopdog&&rand.nextInt(300)==0&&System.currentTimeMillis()-lastSnoop >= 650){
			new ClipShell("smoke.wav", 3F).start();
			lastSnoop = System.currentTimeMillis();
		}
		if(blockSide>-1){
			int dur = (int) (System.currentTimeMillis() - blockStart);
			if(dur>1000){
				this.blockSide = -1;
				this.blockStart = -1;
				addEffect(new Effect(2, EffectType.vulnerability, this, 2000));
			}
		}
		if(jumps<maxJumps){
			if(System.currentTimeMillis()-lastCharge >= jumpCharge){
				jumps++;
				lastCharge = System.currentTimeMillis();
				if(jumpCharge!=jumpChargeReset)jumpCharge=jumpChargeReset;
			}
		}else{
			lastCharge = System.currentTimeMillis();
		}
		if(target!=null){
			if(!(target.countEffect(EffectType.shroud) > 0))
			ai.execute(target, delta);
			else{
				phys.motionX = 0;
			}
		}
		if(panel==null){
			if(Display.currentScreen instanceof GridPanel)
			panel = ((GridPanel)Display.currentScreen);
		}
		if(this.dialog==Dialog.olsen){
			for(int i = 0; i < panel.objects.size(); ++i){
				Object o = panel.objects.get(i);
				if(target==null){
					if(o instanceof CreatureBandit){
						this.target = o;
					}
				}else{
					if(target.health<=0||Util.dif(target.posX, posX) >= 900)this.target=null;
				}
			}
		}
		if(this.getUsername().equals(Properties.username)){
			if(lastBiome==null){
				if(!panel.biomes.isEmpty()){
					for(Biome b : panel.biomes){
						int x = (posX+panel.scrollX)/30;
						if(x > b.x && x < b.x+b.bWidth){
							lastBiome = b;
							b.apply(panel);
						}
					}
				}
			}
			if(!panel.biomes.isEmpty()){
				for(Biome b : panel.biomes){
					int x = (posX+panel.scrollX)/30;
					if(x > b.x && x < b.x+b.bWidth){
						if(lastBiome!=b){
							lastBiome = b;
							b.apply(panel);
						}
					}
				}
			}
		}
		byte blockid = (byte) panel.grid.getTileID(posX+panel.scrollX+width/2, posY+height+panel.scrollY);
		if(GridBlock.all[blockid].getShape()==1)phys.motionX-=2;
		if(GridBlock.all[blockid].getShape()==2)phys.motionX+=2;
		if(blockid==GridBlock.water.getID()){
			Particle p = new Particle(posX+width/2, posY+height/2, Particle.LIQUID, Util.waterBlue);
			p.phys.motionX=(rand.nextInt(100)-rand.nextInt(100));
			p.phys.motionY=40+rand.nextInt(120);
			p.isBlood = true;
			panel.objects.add(p);
		}
		if(blockid==GridBlock.ladder.getID()){
			if(phys.motionY>5)phys.motionY=50;
			if(phys.motionY<-3)phys.motionY=-3;
		}
		if(posY>=Properties.height+panel.viewDistY){
			health--;
		}
		for(int i = 0; i < effects.size(); ++i){
			if(effects.get(i).type.equals(EffectType.slam)){
				if(this.collideDown()){
					int x = 0, y = 0;
					for(int j = 0; j < 3; ++j){
						byte id = (byte) panel.grid.getTileID(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY);
						if(id > 0){
							ObjectBlock.split(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY, id, 10);
							panel.grid.setTileID(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY, (byte) 0);
						}
						x+=(rand.nextInt(2)-rand.nextInt(2));
						y+=(rand.nextInt(2)-rand.nextInt(2));
						for(int k = 0; k < 100; ++k){
							int s = 4+rand.nextInt(6);
							ObjectBlock obj = new ObjectBlock(this.posX+width/2-s/2, posY+height, id);
							obj.width = s;
							obj.height = s;
							obj.maxTime = 2500;
							obj.phys.motionX = (rand.nextInt(200)-rand.nextInt(200));
							obj.phys.motionY = 50+rand.nextInt(40);
							Display.currentScreen.objects.add(obj);
						}
					}
					Rectangle rect = new Rectangle(posX+width/2-125, posY+height/2-125, 250, 250);
					for(int k = 0; k < Display.currentScreen.objects.size(); ++k)
					if(k<Display.currentScreen.objects.size()){
						Object o = Display.currentScreen.objects.get(k);
						if(o!=null){
							if(o.canHarm&&o!=this&&o.collideDown()){
								if(o.getHitbox().intersects(rect)){
									o.hurt(effects.get(i).value, true, null);
								}
							}
						}
					}
					Display.currentScreen.shake(500, 15);
					new ClipShell("boom.wav", 6F).start();
					effects.get(i).active = false;
					addEffect(new Effect(2, EffectType.concussion, this, 1750));
				}
			}
			if(effects.get(i).type.equals(EffectType.bloodsteed)){
				phys.motionX+=(trueDir==0?3:-3);
				this.renderBlood(1, -phys.motionX, 40+rand.nextInt(60));
				for(int j = 0; j < Display.currentScreen.objects.size(); ++j){
					if(j < Display.currentScreen.objects.size()){
						if(Display.currentScreen.objects.get(j) instanceof Player){
							if(Display.currentScreen.objects.get(j)!=this && Display.currentScreen.objects.get(j).getHitbox().intersects(this.getHitbox())){
								Display.currentScreen.objects.get(j).hurt(effects.get(i).value, false);
								Display.currentScreen.objects.get(j).addEffect(new Effect(3000, EffectType.stun, this, 3000));
								effects.get(i).active = false;
							}
						}
					}
				}
			}
			if(effects.get(i).type.equals(EffectType.holycharge)){
				phys.motionX+=(trueDir==0?3:-3);
				for(int j = 0; j < Display.currentScreen.objects.size(); ++j){
					if(j < Display.currentScreen.objects.size()){
						if(Display.currentScreen.objects.get(j) instanceof Player){
							if(Display.currentScreen.objects.get(j)!=this && Display.currentScreen.objects.get(j).getHitbox().intersects(this.getHitbox())){
								Display.currentScreen.objects.get(j).hurt(effects.get(i).value, false);
								effects.get(i).active = false;
							}
						}
					}
				}
			}
		}
		if(collideUp()){
			if(!collideDown())
			++posY;
			if(phys.motionY>0){
				for(int i = 0; i < Math.abs(phys.motionY); ++i){
					int x = Math.abs(phys.motionY);
					Particle p = new Particle(posX+width/2, posY, Particle.DUST, Color.GREEN);
					p.phys.motionX = rand.nextInt(10+x/3)-rand.nextInt(10+x/3)-phys.motionX/2;
					p.phys.motionY = rand.nextInt(1+x/6);
					p.canDrop = false;
					Display.currentScreen.objects.add(p);
				}
				phys.motionY = 0;
			}
		}
		if(right&!stunned()&!isBlocking()){
			if(!collideRight())
			phys.motionX+=(getSpeed() + this.getSkillTier(Skill.agility) * getSpeed() * 0.5);
			else phys.motionX = 0;
		}
		if(left&!stunned()&!isBlocking()){
			if(!collideLeft())
			phys.motionX-=(getSpeed() + this.getSkillTier(Skill.agility) * getSpeed() * 0.5);
			else phys.motionX = 0;
		}
		if(!this.collideDown()){
			phys.motionY-=5;
		}else{
			if(!this.collideUp())posY-=((posY+panel.scrollY)%30);
			if(phys.motionY < 0){
				byte id = (byte) Display.currentScreen.getGrid().getTileIDWithScroll(posX+Display.currentScreen.getGrid().getTileSize(), posY+height+1);
				if(id<=0){
					id = (byte) GridBlock.dirt.getID();
				}
				PhysicsPlug ph = new PhysicsPlug(this);
				ph.motionX = -this.phys.motionX;
				ph.motionY = Math.abs(this.phys.motionY)+rand.nextInt(10)-rand.nextInt(10);
				ObjectBlock.split(posX+width/2, posY+height-10, id, height/15+10-rand.nextInt(10)+Math.abs(phys.motionY)/10+rand.nextInt(11)-rand.nextInt(11), Math.abs(phys.motionY)/3, ph);
				phys.motionY = 0;
			}
		}
	}
	
	public boolean isBlocking(){
		return blockSide > -1;
	}
	
	public void renderBlood(int damage, int motionx, int motiony){
		for(int i = 0; i < 10+(damage<30?damage:30); ++i){
			Color bc = Util.blood;
			Particle sp = new Particle(posX+(rand.nextInt(width)), posY+height/3, rand.nextInt(2)==0?Particle.LIQUID:Particle.DUST, bc);
			sp.solid = true;
			sp.phys.motionX=rand.nextInt((motionx/20)>0?motionx/20:1);
			sp.phys.motionY=rand.nextInt((motiony/20)>0?motiony/20:1);
			if(sp.physics==Particle.LIQUID){
				sp.isBlood = true;
				sp.basePhysics = false;
			}
			Display.currentScreen.objects.add(sp);
		}
	}
	
	public boolean renderHit(){
		return System.currentTimeMillis() >= lastAttack+150+(hero!=Hero.ranger?50:0) ? false : true;
	}
	
	public int getSpeed(){
		int s = 3;
		s+=this.countEffect(EffectType.grace);
		s+=this.countEffect(EffectType.ichor);
		s+=this.countEffect(EffectType.sandsoftime);
		if(this.countEffect(EffectType.holycharge) > 0 || this.countEffect(EffectType.shroud) > 0 || this.countEffect(EffectType.bloodsteed) > 0 || this.countEffect(EffectType.windhaste) > 0)s*=2;
		s-=countEffect(EffectType.mindcharge);
		s-=countEffect(EffectType.mace)*2;
		s-=countEffect(EffectType.cloud)*2;
		s-=countEffect(EffectType.planet);
		if(s<0)s=0;
		return s;
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==(keySetID==0?Keybind.RIGHT:Keybind.RIGHT1)){
			right = true;
			dir = 0;
			trueDir = 0;
			if(mp){
				Packet02PlayerMove pkt = new Packet02PlayerMove(this.getUsername(), 0, 1);
				pkt.write(Bank.client);
			}
		}
		if(e.getKeyCode()==(keySetID==0?Keybind.LEFT:Keybind.LEFT1)){
			left = true;
			dir = 1;
			trueDir = 1;
			if(mp){
				Packet02PlayerMove pkt = new Packet02PlayerMove(this.getUsername(), 1, 1);
				pkt.write(Bank.client);
			}
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.JUMP:Keybind.JUMP1)){
			jump();
			if(mp){
				Packet02PlayerMove pkt = new Packet02PlayerMove(this.getUsername(), 2, 1);
				pkt.write(Bank.client);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_G){
			System.out.println(posX+":"+posY);
			System.out.println((posX+((GridPanel)Display.currentScreen).scrollX)+":"+posY);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK1:Keybind.ATK1_1) || e.getKeyCode() == (keySetID==0?Keybind.ATKLEFT:Keybind.ATKLEFT_1) || e.getKeyCode() == (keySetID==0?Keybind.ATKRIGHT:Keybind.ATKRIGHT_1)){
			renderAtk = true;
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK2:Keybind.ATK2_1)){
			renderAtk = true;
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK3:Keybind.ATK3_1)){
			renderAtk = true;
		}
		if(e.getKeyCode()==Keybind.BLOCK && jumps > 0){
			if(blockSide==-1){
				this.blockStart = System.currentTimeMillis();
				--jumps;
				jumpCharge = 3000;
			}
			blockSide=this.trueDir;
		}
	}
	
	public void jump(){
		//if(collideDown())
		if(!stunned()&&jumps>0){
			if(phys.motionY<0)phys.motionY = 100;else phys.motionY += 100;
			new ClipShell("collect.wav", -3F).start();
			--jumps;
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==(keySetID==0?Keybind.TALK:Keybind.TALK1)){
			for(Player p : Display.currentScreen.players){
				if(p.dialog!=null&&p.openDialog){
					Rectangle r = new Rectangle(p.posX-100, p.posY+p.height/2-100, 200+p.width, 200);
					if(p!=this&&getHitbox().intersects(r)){
						PanelBase pan = Display.currentScreen;
						Display.currentScreen = new PanelDialog(this, p, pan, p.dialog);
						p.openDialog = false;
					}
				}
			}
		}
		if(e.getKeyCode()==Keybind.BLOCK){
			//addEffect(new Effect(2, EffectType.vulnerability, this, (int) ((System.currentTimeMillis()-blockStart)*1.5f)));
			blockSide = -1;
			blockStart  = -1;
		}
		if(e.getKeyCode()==KeyEvent.VK_P){
			this.addEffect(new Effect(1, EffectType.parachute, this, -1));
		}
		if(e.getKeyCode()==(keySetID==0?Keybind.RIGHT:Keybind.RIGHT1)){
			right = false;
			dir = -1;
			if(mp){
				Packet02PlayerMove pkt = new Packet02PlayerMove(this.getUsername(), 0, 0);
				pkt.write(Bank.client);
			}
		}
		if(e.getKeyCode()==(keySetID==0?Keybind.LEFT:Keybind.LEFT1)){
			left = false;
			dir = -1;
			if(mp){
				Packet02PlayerMove pkt = new Packet02PlayerMove(this.getUsername(), 1, 0);
				pkt.write(Bank.client);
			}
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK1:Keybind.ATK1_1)){
			this.getSpell(0).cast(this);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATKLEFT:Keybind.ATKLEFT_1)){
			trueDir = 1;
			this.getSpell(0).cast(this);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATKRIGHT:Keybind.ATKRIGHT_1)){
			trueDir = 0;
			this.getSpell(0).cast(this);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK2:Keybind.ATK2_1)){
			this.getSpell(1).cast(this);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK3:Keybind.ATK3_1)){
			this.getSpell(2).cast(this);
		}
		if(e.getKeyCode() == (keySetID==0?Keybind.ATK4:Keybind.ATK4_1)){
			this.getSpell(3).cast(this);
		}
	}
	
	public double getDamage(){
		int r = resto/5;
		double pow = 0.8D+rand.nextDouble()+rand.nextDouble()+rand.nextDouble()+rand.nextDouble()-rand.nextDouble()-rand.nextDouble()+this.pow;
		if(this.health <= healthMax / 4){
			pow += (this.getSkillTier(Skill.rage) * 3);
		}
		double f = (double)((pow+rand.nextInt((int)(pow+1)))/(double)15);
		return f;
	}
	
	public double getHealpow(){
		double resto = this.resto;
		double f = (double)((resto+rand.nextInt((int)(resto+1)))/(double)15);
		return f;
	}
	
	public void renderHUD(Graphics g, int x, int y, int scale){
		if(target!=null||this.getUsername().equals(Properties.username)||showHud){
			for(int i = 0; i < spells.size(); ++i){
				if(getSpell(i).ready(this)){
					g.drawImage(Bank.squaregrad_gr, x+i*(scale+20)-15, y-15, scale+30, scale+30, null);
				}
				g.drawImage(Bank.paper, x+i*(scale+20), y, scale, scale, null);
				g.drawImage(getSpell(i).spell.sprite, x+i*(scale+20), y, scale, scale, null);
				if(this.stunned()|!this.getSpell(i).ready(this))g.drawImage(Bank.lock, x+i*(scale+20), y, scale, scale, null);
				//if(spells.get(i).ready(this) && spells.get(i).spell.cooldown+System.currentTimeMillis() < spells.get(i).coolstart+540){
				//	g.drawImage(Bank.flash, x+i*(scale+20), y, scale, scale, null);
				//}
				if(!this.getSpell(i).ready(this)){
					this.getSpell(i).renderOverlay(g, this, x+i*(scale+20), y, scale);
				}
				Bank.drawSquare(g, x+i*(scale+20), y, scale, scale);
			}
			int hbw = (scale+20)*spells.size()-60;
			g.drawImage(Bank.custbutton, x, y+40+scale, hbw, scale/2, null);
			g.setColor(Util.transparent_green);
			int hd = health * (hbw) / healthMax;
			g.fillRect(x, y+40+scale, hd, scale/2);
			//g.drawImage(Bank.grad, x, y+40+scale, hbw, scale/2, null);
			Bank.drawSquare(g, x, y+40+scale, hbw, scale/2);
			g.drawImage(Bank.heart, x+(hbw)/2-30, y+40+scale+scale/4-30, 60, 60, null);
			g.setColor(Color.WHITE);
			g.setFont(Util.spellDesc);
			g.drawString(respawns+"", x+(hbw)/2-g.getFontMetrics().stringWidth(respawns+"")/2, y+40+scale+scale/4);
			for(int i = 0; i < effects.size(); ++i){
				effects.get(i).draw(x+i*50, y+scale+scale/2+40, this, g);
			}
			g.setColor(Color.BLACK);
			int sz = jumps< maxJumps ? ((int) ((System.currentTimeMillis()-lastCharge) * (scale/2) / jumpCharge)):scale/2;
			g.fillOval(x+hbw+10-5, y+40+scale-5, scale/2+10, scale/2+10);
			g.setColor(Color.YELLOW);
			g.fillOval(x+hbw+10+scale/4-sz/2, y+40+scale+scale/4-sz/2, sz, sz);
			//g.drawImage(Bank.grad, x+hbw+10, y+40+scale, scale/2, scale/2, null);
			g.setFont(Util.cooldownFont);
			g.setColor(Color.BLACK);
			g.drawString(jumps+"", x+hbw+10+scale/4-g.getFontMetrics().stringWidth(jumps+"")/2, y+40+scale+35);
			g.setFont(Util.descTitleFont);
			g.drawString(health+"/"+healthMax, x+20, y+40+scale+scale/3);
		}
	}

	public void drawPlayer(Graphics g) {	
		if(health>0){
			Image tex = this.getTexAndDraw(g);
		}
		if(dialog!=null&&openDialog){
			g.setColor(dialog.nameColor);
			g.setFont(Util.descTitleFont);
			g.drawString(dialog.name, posX+width/2-g.getFontMetrics().stringWidth(dialog.name)/2, posY-25);
			Rectangle r = new Rectangle(posX+width/2-100-width/2, posY+height/2-100, 200+width, 200);
			for(Player p : Display.currentScreen.players){
				if(p!=this&&p.getHitbox().intersects(r)){
					g.setColor(Color.WHITE);
					g.setFont(Util.upgradeFont);
					String s = "Press ENTER to Talk";
					g.drawString(s, posX+width/2-g.getFontMetrics().stringWidth(s)/2, posY-50);
				}	
			}
		}
	}

	public void heal(int v, boolean crit) {
		int i = v;
		i+=resto/3;
		if(resto>=1)i+=rand.nextInt(resto);
		if(this.countEffect(EffectType.haunt) > 0){
			new ClipShell("whisper.wav").start();
			i/=2;
		}
		if(this.countEffect(EffectType.fury) <= 0){
			health+=i;
			Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, "+"+i, crit?Util.critGreen:Color.GREEN));
			if(crit)
			Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2, "Critical Heal!", crit?Util.critGreen:Color.GREEN));
		}
		healed+=i;
		if(health>healthMax)health=healthMax;
	}

	public boolean fullHP() {
		return health>=healthMax;
	}

	public Player loadSpells(Spell s1, Spell s2, Spell s3) {
		spells.add(new SpellCast(s1));
		spells.add(new SpellCast(s2));
		spells.add(new SpellCast(s3));
		return this;
	}

	public void draw(Graphics g) {
	}

	public Image getTexAndDraw(Graphics g) {
		int width = this.width;
		int height = this.height;
		Image tex = null;
		if(renderID == 0){
			tex = trueDir==0?Bank.troll_stand:Bank.trollLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.troll_walk0 : anim == 1 ? Bank.troll_walk1 : anim == 2 ? Bank.troll_walk2 : Bank.troll_walk0;}
			if(left){tex = anim == 0 ? Bank.trollLeft_walk0 : anim == 1 ? Bank.trollLeft_walk1 : anim == 2 ? Bank.trollLeft_walk2 : Bank.trollLeft_walk0;}
			if(renderHit()){
				tex = trueDir==0?Bank.troll_punch:Bank.trollLeft_punch;
			}
			if(countEffect(EffectType.slam) > 0){
				tex = trueDir==0?Bank.troll_slam:Bank.trollLeft_slam;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 1){
			tex = trueDir==0?Bank.human_stand:Bank.humanLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.human_walk0 : anim == 1 ? Bank.human_walk1 : anim == 2 ? Bank.human_walk2 : Bank.human_walk0;}
			if(left){tex = anim == 0 ? Bank.humanLeft_walk0 : anim == 1 ? Bank.humanLeft_walk1 : anim == 2 ? Bank.humanLeft_walk2 : Bank.humanLeft_walk0;}
			if(renderHit()){
				tex = trueDir==0?Bank.human_cast:Bank.humanLeft_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 2){
			tex = trueDir==0?Bank.human1_stand:Bank.human1Left_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.human1_walk0 : anim == 1 ? Bank.human1_walk1 : anim == 2 ? Bank.human1_walk2 : Bank.human1_walk0;}
			if(left){tex = anim == 0 ? Bank.human1Left_walk0 : anim == 1 ? Bank.human1Left_walk1 : anim == 2 ? Bank.human1Left_walk2 : Bank.human1Left_walk0;}
			if(renderHit()){
				tex = trueDir==0?Bank.human1_cast:Bank.human1Left_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 3){
			tex = trueDir==0?Bank.yeti_walk0:Bank.yetiLeft_walk0;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.yeti_walk0 : anim == 1 ? Bank.yeti_walk1 : anim == 2 ? Bank.yeti_walk2 : Bank.yeti_walk0;}
			if(left){tex = anim == 0 ? Bank.yetiLeft_walk0 : anim == 1 ? Bank.yetiLeft_walk1 : anim == 2 ? Bank.yetiLeft_walk2 : Bank.yetiLeft_walk0;}
			if(renderHit()){
				tex = trueDir==0?Bank.yeti_punch:Bank.yetiLeft_punch;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 4){
			tex = trueDir==0?Bank.luna_stand:Bank.lunaLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.luna_walk0 : anim == 1 ? Bank.luna_walk1 : anim == 2 ? Bank.luna_walk2 : Bank.luna_walk0;}
			if(left){tex = anim == 0 ? Bank.lunaLeft_walk0 : anim == 1 ? Bank.lunaLeft_walk1 : anim == 2 ? Bank.lunaLeft_walk2 : Bank.lunaLeft_walk0;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown() ){
				tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.luna_fall0:Bank.luna_fall1) : (rand.nextInt(2)==0?Bank.lunaLeft_fall0:Bank.lunaLeft_fall1);
			}
			if(renderHit()){
				tex = trueDir==0?Bank.luna_cast:Bank.lunaLeft_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 5){
			tex = trueDir==0?Bank.kera_stand:Bank.keraLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.kera_walk0 : anim == 1 ? Bank.kera_walk1 : anim == 2 ? Bank.kera_walk2 : Bank.kera_walk0;}
			if(left){tex = anim == 0 ? Bank.keraLeft_walk0 : anim == 1 ? Bank.keraLeft_walk1 : anim == 2 ? Bank.keraLeft_walk2 : Bank.keraLeft_walk0;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown() ){
				tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.kera_fall0:Bank.kera_fall1) : (rand.nextInt(2)==0?Bank.keraLeft_fall0:Bank.keraLeft_fall1);
			}
			if(renderHit()){
				tex = trueDir==0?Bank.kera_cast:Bank.keraLeft_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 6){
			tex = trueDir==0?Bank.monk_stand:Bank.monkLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = (int) walkTick;
			if(right){tex = anim == 0 ? Bank.monk_walk0 : anim == 1 ? Bank.monk_walk1 : anim == 2 ? Bank.monk_walk2 : Bank.monk_walk0;}
			if(left){tex = anim == 0 ? Bank.monkLeft_walk0 : anim == 1 ? Bank.monkLeft_walk1 : anim == 2 ? Bank.monkLeft_walk2 : Bank.monkLeft_walk0;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown() ){
				tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.monk_fall0:Bank.monk_fall1) : (rand.nextInt(2)==0?Bank.monkLeft_fall0:Bank.monkLeft_fall1);
			}
			if(renderHit()){
				if(atkRenderID==1)tex = trueDir==0?Bank.monk_kick:Bank.monkLeft_kick;
				else tex = trueDir==0?Bank.monk_cast:Bank.monkLeft_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 7){
			tex = trueDir==0?Bank.ent_stand:Bank.entLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = walkTick >= 0 && walkTick < 1 ? 0 : walkTick >= 1 && walkTick < 2 ? 1 : walkTick >=2 && walkTick < 3 ? 2 : 0;
			if(right){tex = anim == 0 ? Bank.ent_walk0 : anim == 1 ? Bank.ent_walk1 : anim == 2 ? Bank.ent_walk2 : Bank.ent_walk0;}
			if(left){tex = anim == 0 ? Bank.entLeft_walk0 : anim == 1 ? Bank.entLeft_walk1 : anim == 2 ? Bank.entLeft_walk2 : Bank.entLeft_walk0;}
			if(renderAtk&&(!left&!right)){
				tex = trueDir==0?Bank.ent_swing:Bank.entLeft_swing;
			}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.ent_fall0:Bank.ent_fall1) : (rand.nextInt(2)==0?Bank.entLeft_fall0:Bank.entLeft_fall1);
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.ent_punch:Bank.entLeft_punch;
			}
			if(this.countEffect(EffectType.whirl) > 0){
				tex = (rand.nextInt(2)==0?Bank.ent_punch:Bank.entLeft_punch);
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 8){
			tex = trueDir==0?Bank.prince_stand:Bank.princeLeft_stand;
			if(walkTick<=3)walkTick+=0.05F;
			int anim = walkTick >= 0 && walkTick < 1 ? 0 : walkTick >= 1 && walkTick < 2 ? 1 : walkTick >=2 && walkTick < 3 ? 2 : 0;
			if(right){tex = anim == 0 ? Bank.prince_walk0 : anim == 1 ? Bank.prince_walk1 : anim == 2 ? Bank.prince_walk2 : Bank.prince_walk0;}
			if(left){tex = anim == 0 ? Bank.princeLeft_walk0 : anim == 1 ? Bank.princeLeft_walk1 : anim == 2 ? Bank.princeLeft_walk2 : Bank.princeLeft_walk0;}
			if(renderAtk&&(!left&!right)){
				tex = trueDir==0?Bank.prince_swing:Bank.princeLeft_swing;
			}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				tex = (trueDir == 0 ?Bank.prince_fall:Bank.princeLeft_fall);
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.prince_attack:Bank.princeLeft_attack;
				if(this.atkRenderID==1)tex = trueDir==0?Bank.prince_cast:Bank.princeLeft_cast;
			}
			if(walkTick>3)walkTick = 0;
		}
		else if(renderID == 9){
			tex = Bank.dancing;
		}
		else if(renderID == 10){
			tex = trueDir==0?Bank.ranger_stand:Bank.rangerLeft_stand;
			if(right)tex = Bank.ranger_run;
			if(left)tex = Bank.rangerLeft_run;
			if(renderAtk&&(!left&!right)){
				tex = trueDir==0?Bank.ranger_draw:Bank.rangerLeft_draw;
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.ranger_release:Bank.rangerLeft_release;
			}
			if(countEffect(EffectType.barrage) > 0)
				tex = trueDir==0?Bank.ranger_barrage:Bank.rangerLeft_barrage;
		}
		else if(renderID == 11){
			tex = trueDir==0?Bank.bar_stand:Bank.barLeft_stand;
			if(right)tex = Bank.bar_walk;
			if(left)tex = Bank.barLeft_walk;
			if(renderAtk&&(!left&!right)){
				if(countEffect(EffectType.mace)>0||countEffect(EffectType.axe)>0)
					tex = trueDir==0?Bank.bar_maceswing:Bank.barLeft_maceswing;
				if(countEffect(EffectType.sword)>0)
					tex = trueDir==0?Bank.bar_swordswing:Bank.barLeft_swordswing;
			}
			if(this.renderHit()){
				if(countEffect(EffectType.mace)>0)
					tex = trueDir==0?Bank.bar_maceattack:Bank.barLeft_maceattack;
				if(countEffect(EffectType.sword)>0||countEffect(EffectType.axe)>0)
					tex = trueDir==0?Bank.bar_swordattack:Bank.barLeft_swordattack;
			}	
			if(this.countEffect(EffectType.whirl) > 0)tex = Bank.bar_whirl;
		}
		else if(renderID == 12){
			tex = trueDir==0?Bank.priest_stand:Bank.priestLeft_stand;
			if(right)tex = Bank.priest_walk;
			if(left)tex = Bank.priestLeft_walk;
			if(renderAtk&&(!left&!right)){
				tex = trueDir==0?Bank.priest_swing:Bank.priestLeft_swing;
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.priest_cast:Bank.priestLeft_cast;
			}
		}
		else if(renderID == 13){
			boolean grand = false;
			if(this.countEffect(EffectType.grandhammer) > 0)grand = true;
			Image tex1 = grand?(trueDir==0?Bank.grandhammer_stand:Bank.grandhammerLeft_stand):(trueDir==0?Bank.hammer_stand:Bank.hammerLeft_stand);
			tex = trueDir==0?Bank.warden_stand:Bank.wardenLeft_stand;
			if(right){
				tex = Bank.warden_walk;
				tex1 = grand?Bank.grandhammer_walk:Bank.hammer_walk;
			}
			if(left){
				tex = Bank.wardenLeft_walk;
				tex1 = grand?Bank.grandhammerLeft_walk:Bank.hammerLeft_walk;
			}
			if(renderAtk&&(!left&!right)){
				tex = trueDir==0?Bank.warden_swing:Bank.wardenLeft_swing;
				tex1 = grand?(trueDir==0?Bank.grandhammer_swing:Bank.grandhammerLeft_swing):(trueDir==0?Bank.hammer_swing:Bank.hammerLeft_swing);
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.warden_attack:Bank.wardenLeft_attack;
				tex1 = grand?(trueDir==0?Bank.grandhammer_attack:Bank.grandhammerLeft_attack):(trueDir==0?Bank.hammer_attack:Bank.grandhammerLeft_attack);
			}
			int w = height*2;
			int h = height+width;
			g.drawImage(tex1, posX+width/2-w/2, posY+height-h, w, h, null);	
		}
		else if(renderID == 14){
			tex = trueDir==0?Bank.mesmer_stand:Bank.mesmerLeft_stand;
			if(right){tex = Bank.mesmer_walk;}
			if(left){tex = Bank.mesmerLeft_walk;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				//tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.mesmer_fall0:Bank.mesmer_fall1) : (rand.nextInt(2)==0?Bank.mesmerLeft_fall0:Bank.mesmerLeft_fall1);
			}
			if(this.renderHit()||renderAtk){
				tex = trueDir==0?Bank.mesmer_cast:Bank.mesmerLeft_cast;
			}
		}
		else if(renderID == 15){
			tex = trueDir==0?Bank.mage_stand:Bank.mageLeft_stand;
			if(right){tex = Bank.mage_walk;}
			if(left){tex = Bank.mageLeft_walk;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				//tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.mage_fall0:Bank.mage_fall1) : (rand.nextInt(2)==0?Bank.mageLeft_fall0:Bank.mageLeft_fall1);
			}
			if(this.renderHit()||renderAtk){
				tex = trueDir==0?Bank.mage_cast:Bank.mageLeft_cast;
			}
		}
		else if(renderID == 100){
			tex = trueDir==0?Bank.buster_stand:Bank.busterLeft_stand;
			if(right){tex = Bank.buster_walk;}
			if(left){tex = Bank.busterLeft_walk;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.buster_fall0:Bank.buster_fall1) : (rand.nextInt(2)==0?Bank.busterLeft_fall0:Bank.busterLeft_fall1);
			}
			if(this.renderHit()||renderAtk){
				tex = trueDir==0?Bank.buster_cast:Bank.busterLeft_cast;
			}
		}
		else if(renderID == 101){
			tex = trueDir==0?Bank.bandit_stand:Bank.banditLeft_stand;
			if(right){tex = Bank.bandit_run;}
			if(left){tex = Bank.banditLeft_run;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				//tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.bandit_fall0:Bank.bandit_fall1) : (rand.nextInt(2)==0?Bank.banditLeft_fall0:Bank.banditLeft_fall1);
			}
			if(this.renderHit()||renderAtk){
				tex = trueDir==0?Bank.bandit_attack:Bank.banditLeft_attack;
			}
		}
		else if(renderID == 102){
			tex = trueDir==0?Bank.snowbro_stand:Bank.snowbroLeft_stand;
			if(right){tex = Bank.snowbro_walk;}
			if(left){tex = Bank.snowbroLeft_walk;}
			if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
				//tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.snowbro_fall0:Bank.snowbro_fall1) : (rand.nextInt(2)==0?Bank.snowbroLeft_fall0:Bank.snowbroLeft_fall1);
			}
			if(this.renderHit()||renderAtk){
				tex = trueDir==0?Bank.snowbro_cast:Bank.snowbroLeft_cast;
			}
		}
		else if(renderID == 103){
			boolean grand = false;
			if(this.countEffect(EffectType.grandhammer) > 0)grand = true;
			Image tex1 = grand?(trueDir==0?Bank.grandhammer_stand:Bank.grandhammerLeft_stand):(trueDir==0?Bank.hammer_stand:Bank.hammerLeft_stand);
			tex = trueDir==0?Bank.soldier_stand:Bank.soldierLeft_stand;
			if(right){
				tex = Bank.soldier_walk;
				tex1 = grand?Bank.grandhammer_walk:Bank.hammer_walk;
			}
			if(left){
				tex = Bank.soldierLeft_walk;
				tex1 = grand?Bank.grandhammerLeft_walk:Bank.hammerLeft_walk;
			}
			if(renderAtk&&(!left&!right)){
				tex1 = grand?(trueDir==0?Bank.grandhammer_swing:Bank.grandhammerLeft_swing):(trueDir==0?Bank.hammer_swing:Bank.hammerLeft_swing);
			}
			if(this.renderHit()){
				tex = trueDir==0?Bank.soldier_attack:Bank.soldierLeft_attack;
				tex1 = grand?(trueDir==0?Bank.grandhammer_attack:Bank.grandhammerLeft_attack):(trueDir==0?Bank.hammer_attack:Bank.grandhammerLeft_attack);
			}
			int w = height*2;
			int h = height+width;
			g.drawImage(tex1, posX+width/2-w/2, posY+height-h, w, h, null);		
		}
		if(this.countEffect(EffectType.ethereal) > 0){
			tex = Bank.shade;
		}
		if(this.countEffect(EffectType.bloodsteed) > 0){
			if(trueDir==0)tex = rand.nextInt(2)==0?Bank.horse0:Bank.horse1;
			if(trueDir==1)tex = rand.nextInt(2)==0?Bank.horse0Left:Bank.horse1Left;
			int w = width;
			width = (int) (height);
			height = (int) (w*1.5F);
		}
		if(this.countEffect(EffectType.ichor) > 0){
			tex = Bank.glop;
			int w = 120-phys.motionX;
			if(w<10)w=10;
			width = w;
			height = 10+(phys.motionX<0?Math.abs(phys.motionX):phys.motionX)/2;
		}
		g.drawImage(tex, posX, posY+this.height-height, width, height, null);
		if(this.blockSide>-1){
			int sw = 25, sh = 30;
			g.drawImage(Bank.shield, posX+width/2-sw/2, posY-20-sh, sw, sh, null);
			g.drawImage(blockSide==0?Bank.block_right:Bank.block_left, posX+width/2-(blockSide==1?width:0), posY+this.height-height, width, height, null);
		}
		return tex;
	}
}
