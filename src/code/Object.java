package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public abstract class Object {
	
	int posX, posY, width, height, health = 25;
	private boolean canFall = true, shouldRemove = false;
	PhysicsPlug phys = null;
	Random rand = new Random();
	ArrayList<Effect> effects = new ArrayList<Effect>();
	public boolean solid = true;
	Hat hat = null;
	int healthMax;
	public boolean canHarm = false;
	ArrayList<Textbox> chats = new ArrayList<Textbox>();
	
	public Object(int x, int y, int w, int h){
		this.setPosX(x);
		this.setPosY(y);
		this.setWidth(w);
		this.setHeight(h);
		healthMax = health;
	}
	
	public void addEffect(Effect e){
		boolean doAdd = true;
		if(this instanceof Player){
			if(((Player)this).hasSkill(Skill.pestilence)){
				for(Effect ef : effects){
					if(ef.type==e.type){
						ef.durationMax+=e.durationMax/2;
						if(e.value>ef.value)ef.value=e.value;
						doAdd = false;
					}
				}
			}
		}
		if(e.caster!=null){
			Player p = e.caster;
			int multi = 100+(p.getSkillTier(Skill.longevity)+p.getSkillTier(Skill.longevity1)+p.getSkillTier(Skill.longevity2)+p.getSkillTier(Skill.longevity3)+p.getSkillTier(Skill.longevity4))*5;
			double dl = e.durationMax/((double)100);
			dl*=multi;
			e.durationMax = (int)dl;
		}
		if(doAdd){
			Indicator ind = new Indicator(posX+(rand.nextInt(60)-rand.nextInt(60)), posY-80+(rand.nextInt(60)-rand.nextInt(60)), "<"+e.type.name+">");
			ind.c = Color.PINK;
			Display.currentScreen.objects.add(ind);
			effects.add(e);
		}
	}
	
	public boolean stunned(){
		boolean ret = countEffect(EffectType.stun)>0||countEffect(EffectType.concussion)>0||countEffect(EffectType.frozen)>0||countEffect(EffectType.prayer)>0;
		if(this instanceof Player){
			if(((Player)this).blockSide>-1)ret=true;
		}
		return ret;
	}
	
	public int countEffect(EffectType e){
		int ret = 0;
		for(int i = 0; i < effects.size(); ++i){
			if(effects.get(i).type.equals(e))++ret;
		}
		return ret;
	}
	
	public void removeEffect(EffectType e){
		for(int i = 0; i < effects.size(); ++i){
			if(effects.get(i).type.equals(e))effects.remove(i);
		}
	}
	
	public void removeEffect(EffectType e, int a){
		int done = 0;
		for(int i = 0; i < effects.size(); ++i){
			if(effects.get(i).type.equals(e)&&done<a){++done;effects.remove(i);}
		}
	}
	
	public boolean collideRight(){
		int yblocks = this.height/30;
		if(yblocks<1)yblocks=1;
		return Display.currentScreen.getGrid()==null?false:Display.currentScreen.getGrid().formTileBox(posX+width, posY, 1, yblocks).intersects(this.getHitbox());
	}
	
	public boolean collideLeft(){
		int yblocks = this.height/30;
		if(yblocks<1)yblocks=1;
		return Display.currentScreen.getGrid()==null?false:Display.currentScreen.getGrid().formTileBox(posX, posY, 1, yblocks).intersects(this.getHitbox());
	}
	
	public boolean collideUp(){
		return (Display.currentScreen.getGrid()==null?false:(Display.currentScreen.getGrid().formTileBox(posX, posY, 2, 1).intersects(this.getHitboxTop())&&Display.currentScreen.getGrid().getTileIDWithScroll(posX, posY-60)!=0?true:false));
		//return Display.currentScreen.getGrid()==null?false:Display.currentScreen.getGrid().formTileBox(posX, posY, 2, 1).intersects(this.getHitboxTop());
	}
	public boolean collideDown(){
		int xblocks = this.width/30;
		if(xblocks<1)xblocks=1;
		boolean ret = Display.currentScreen.getGrid()==null?false:Display.currentScreen.getGrid().formTileBox(posX, posY+height, xblocks+1, 1).intersects(this.getHitboxBottom());
		return ret;
	}
	
	public boolean collides(){
		return collideLeft()||collideRight()||collideUp();
	}
	
	public void kill(){
		this.shouldRemove = true;
	}
	
	public Rectangle getHitbox(){
		return new Rectangle(posX, posY, width, height);
	}
	
	public Rectangle getHitboxTop(){
		return new Rectangle(posX+width/2-1, posY, 2, 1);
	}
	
	public Rectangle getHitboxBottom(){
		return new Rectangle(posX+width/2-1, posY+height, 2, 1);
	}
	
	public boolean getCanFall(){
		return isCanFall();
	}
	
	public final void updateBase(double delta){
		for(int i = 0; i < effects.size(); ++i){
			if(effects.get(i).type.equals(EffectType.slam)){
				if(this.collideDown()){
					GridPanel panel = ((GridPanel)Display.currentScreen);
					int x = 0, y = 0;
					for(int j = 0; j < 3; ++j){
						byte id = (byte) panel.grid.getTileID(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY);
						if(id > 0){
							ObjectBlock.split(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY, id, 10);
							panel.grid.setTileID(posX+x*30+panel.scrollX, posY+height+y*30+panel.scrollY, (byte) 0);
						}
						x+=(rand.nextInt(2)-rand.nextInt(2));
						y+=(rand.nextInt(2)-rand.nextInt(2));
					}
					Display.currentScreen.shake(200, 15);
					new ClipShell("boom.wav", 3F).start();
					effects.get(i).active = false;
					addEffect(new Effect(2, EffectType.concussion, (this instanceof Player ? ((Player)this):null), 1750));
				}
			}
			if(i < effects.size()){
				if(effects.get(i)!=null){
				effects.get(i).tickBase(this);
				if(i < effects.size() && effects.get(i) != null){
						if(!effects.get(i).active){
							effects.get(i).type.onEnd(this, effects.get(i));
							effects.remove(i);
						}
					}
				}
			}
		}
		if(phys!=null)
		phys.execute(delta);
		update(delta);
	}
	
	public void heal(int v, boolean crit) {
		int i = v;
		health+=i;
		Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, "+"+i, crit?Util.critGreen:Color.GREEN));
		if(crit)
		Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2, "Critical Heal!", crit?Util.critGreen:Color.GREEN));
		if(health>healthMax)health=healthMax;
	}

	public boolean fullHP() {
		return health>=healthMax;
	}
	
	public abstract void update(double delta);
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void moveRight(int i) {
		posX+=i;
	}
	
	public void moveLeft(int i) {
		posX-=i;
	}
	
	public void moveUp(int i) {
		posY-=i;
	}
	
	public void moveDown(int i) {
		posY+=i;
	}

	public boolean isRemoving() {
		return shouldRemove;
	}

	public final void drawOrigin(Graphics g) {
		draw(g);
		if(hat!=null){
			hat.render(g, this);
		}		
		for(int i = 0; i < effects.size(); ++i){
			effects.get(i).type.render(effects.get(i),this, g);
		}
		if(!chats.isEmpty()){
			for(Textbox t : chats){
				t.draw(this, g);
			}
		}
	}
	
	public abstract void draw(Graphics g);

	public boolean isCanFall() {
		return canFall;
	}

	public void setCanFall(boolean canFall) {
		this.canFall = canFall;
	}

	public void hurt(final int v, boolean crit) {
		int val = v;
		if(this.countEffect(EffectType.moonlight) > 0)val+=val/2;
		for(int i = 0 ; i < effects.size(); ++i){
			Effect e = effects.get(i);
			if(e.type == EffectType.sanctuary){
				if(e.value > val){
					e.value -= val;
					val = 0;
				}else{
					val -= e.value;
					e.value = 0;
					effects.remove(i);
				}
			}
			if(e.type == EffectType.creeper && this instanceof Player){
				Display.currentScreen.objects.add(new ObjectHPOrb(((Player)this).getUsername(), posX+width/2-10, posY+height/2-10, 20, 20, val/5)); 
			}
			if(e.type == EffectType.concussion || e.type == EffectType.vulnerability){
				val*=(e.value);
			}
			if(e.type == EffectType.deadlyfocus){
				int a = val/2;
				if(a<1)++a;
				val+=a;
			}
			if(e.type == EffectType.darkresilience){
				int toh = v/5;
				if(v>0&&toh<=0)toh=1;
				e.dat+=toh;
			}
			if(this instanceof Player){
				if(this.countEffect(EffectType.axe) > 0){
					double vd = ((double)((double)val));
					vd/=((double)10);
					val-=(vd*((Player)this).getSkillTier(Skill.defender));
					if(val<=0)val=1;
				}
			}
			if(e.type == EffectType.ethereal)val/=(2*this.countEffect(EffectType.ethereal));
			if(e.type == EffectType.grudge){
				double ve = val;
				ve/=((double)4);
				e.value+=ve;
			}
			if(e.type == EffectType.pouch){
				e.dat+=val;
			}
		}
		if(this.countEffect(EffectType.shroud)>0||this.countEffect(EffectType.ichor)>0)val=0;
		this.health-=val;
		if(val>0)
		renderBlood(rand.nextInt(val+5)+val/10, 0, 0);
		if(health<=0){
			renderBlood(100, 0, 0);
		}
		Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, val+"", crit?Color.RED:Color.WHITE).setSize(60, crit));
		if(crit){
			new ClipShell("crit1.wav").start();
			Display.currentScreen.shake(450, 15);
			Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2+100, "Critical Strike!", crit?Color.RED:Color.WHITE).setSize(80, true));
		}
		if(hat!=null){
			hat.onWearerHit(this);
		}
		if(this instanceof Player){
			((Player)this).taken+=val;
		}
	}
	
	public void healRaw(int v, boolean crit){
		int i = v;
		health+=i;
		Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, "+"+i, crit?Util.critGreen:Color.GREEN));
		if(crit)
		Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2, "Critical Heal!", crit?Util.critGreen:Color.GREEN));
		if(this instanceof Player)((Player)this).healed+=i;
		if(health>healthMax)health=healthMax;
	}
	
	public void hurtRaw(final int v, boolean crit, Object proj) {
		int val = v;
		this.health-=val;
		if(val>0)
		renderBlood(rand.nextInt(val+5)+val/10, proj==null?(rand.nextInt(50)-rand.nextInt(50)):proj.phys.motionX, proj==null?(rand.nextInt(50)-rand.nextInt(50)):proj.phys.motionY);
		if(health<=0){
			renderBlood(200+val, 0, 0);
		}
		Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, val+"", crit?Color.RED:Color.WHITE).setSize(60, crit));
		if(crit){
			new ClipShell("crit1.wav").start();
			Display.currentScreen.shake(450, 15);
			Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2+100, "Critical Strike!", crit?Color.RED:Color.WHITE).setSize(80, true));
		}
		if(this instanceof Player){
			if(this.countEffect(EffectType.axe) > 0){
				double vd = ((double)((double)val));
				vd/=((double)10);
				val-=(vd*((Player)this).getSkillTier(Skill.defender));
				if(val<=0)val=1;
			}
		}
		if(hat!=null){
			hat.onWearerHit(this);
		}	
		if(proj!=null){
			if(((Player)((Projectile)proj).caster)!=null){
				((Player)((Projectile)proj).caster).dealt+=v;
			}
		}
		if(this instanceof Player){
			((Player)this).taken+=val;
		}
	}
	
	public void hurt(final int v, boolean crit, Object proj) {
		if(this instanceof Player){
			if(((Player)this).target==null && ((Player)this).dialog!=null){
				if(proj instanceof Projectile){
					Player nt = ((Player)((Projectile)proj).caster);
					((Player)this).target = nt;
				}
				if(proj instanceof Player){
					((Player)this).target = ((Player)proj);
				}
			}
		}
		int val = v;
		if(this.countEffect(EffectType.moonlight) > 0 || this.countEffect(EffectType.corrosion) > 0)val+=val/2;
		for(int i = 0 ; i < effects.size(); ++i){
			Effect e = effects.get(i);
			if(e.type == EffectType.sanctuary){
				if(e.value > val){
					e.value -= val;
					val = 0;
				}else{
					val -= e.value;
					e.value = 0;
					effects.remove(i);
				}
			}
			if(e.type == EffectType.creeper && this instanceof Player){
				Display.currentScreen.objects.add(new ObjectHPOrb(((Player)this).getUsername(), posX+width/2-10, posY+height/2-10, 20, 20, val/10)); 
			}
			if(e.type == EffectType.thorns && proj instanceof Projectile){
				Player p = ((Player)((Projectile)proj).caster);
				p.hurt(val, crit);
			}
			if(e.type == EffectType.concussion || e.type == EffectType.vulnerability){
				val*=(e.value);
			}
			if(e.type == EffectType.deadlyfocus){
				int a = val/2;
				if(a<1)++a;
				val+=a;
			}
			if(e.type == EffectType.frozen){
				if(crit){
					val*=2.5F;
					this.removeEffect(EffectType.frozen);
					for(int j = 0; j < 80; ++j){
						Particle particle = new Particle(posX+width/2, posY+height/2, Particle.SPARKLE, Color.CYAN);
						int s = 4+rand.nextInt(8);
						particle.width = s;
						particle.height = s;
						particle.phys.motionX = rand.nextInt(120)-rand.nextInt(120);
						particle.phys.motionY = rand.nextInt(120)-rand.nextInt(120);
						Display.currentScreen.objects.add(particle);
					}
				}else{
					val+=val/2;
				}
			}
			if(e.type == EffectType.darkresilience){
				int toh = v/5;
				if(((Player)this).getSkillTier(Skill.bloodboil)==2)toh=(v/5*2);
				if(v>0&&toh<=0)toh=1;
				e.dat+=toh;
			}
			if(this instanceof Player){
				if(this.countEffect(EffectType.axe) > 0){
					double vd = ((double)((double)val));
					vd/=((double)10);
					val-=(vd*((Player)this).getSkillTier(Skill.defender));
					if(val<=0)val=1;
				}
			}
			if(e.type == EffectType.planet){
				val*=2;
				e.durationMax+=(e.durationMax/10);
			}
			if(e.type == EffectType.ethereal)val/=(2*this.countEffect(EffectType.ethereal));
			if(e.type == EffectType.grudge){
				double ve = val;
				ve/=((double)4);
				e.value+=ve;
			}
			if(e.type == EffectType.pouch){
				e.dat+=val;
			}
		}
		if(this.countEffect(EffectType.shroud)>0||this.countEffect(EffectType.ichor)>0)val=0;
		this.health-=val;
		if(val>0&&proj!=null)
		renderBlood(rand.nextInt(val+5)+val/10, proj.phys.motionX, proj.phys.motionY);
		if(health<=0){
			renderBlood(100, 0, 0);
		}
		Display.currentScreen.objects.add(new Indicator(posX+width/2, posY, val+"", crit?Color.RED:Color.WHITE).setSize(60, crit));
		if(crit){
			new ClipShell("crit1.wav").start();
			Display.currentScreen.shake(450, 15);
			Display.currentScreen.objects.add(new Indicator(posX+width/2, Properties.height/2+100, "Critical Strike!", crit?Color.RED:Color.WHITE).setSize(80, true));
		}
		if(hat!=null){
			hat.onWearerHit(this);
		}	
		if(this instanceof ObjectEnemy){
			if(proj instanceof Projectile){
				((ObjectEnemy)this).tar = ((Projectile)proj).caster;
			}else{
				((ObjectEnemy)this).tar = proj;
			}
		}
		if(proj!=null){
			if(((Player)((Projectile)proj).caster)!=null){
				((Player)((Projectile)proj).caster).dealt+=v;
			}
		}
		if(this instanceof Player){
			((Player)this).taken+=val;
		}
	}
	
	public void jump(){
		phys.motionY = 100;
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

	public void collideWithPlayer(Player player) {
	}
}
