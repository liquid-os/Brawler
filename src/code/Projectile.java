package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Object{
	
	int maxTime = -1, val = 0, effect = 0, visEffect = 0, dir = 0, animCycle = 0;
	long start = System.currentTimeMillis();
	Image right, left;
	Object caster;
	boolean killXLoss = true;
	boolean killYLoss = false;
	boolean destroyOnHit = true, crosswall = false, crit = false, knockback = true, flag = false;
	public String notes = "";
	boolean init = false;

	public Projectile(Object caster, int x, int y, int w, int h, int val, BufferedImage r) {
		super(x, y, w, h);
		this.val = val;
		this.caster = caster;
		this.right = r;
		this.left = Bank.flip(r);
		this.phys = new PhysicsPlug(this);
	}
	
	public Projectile(Object caster, int x, int y, int w, int h, int val, Image r) {
		super(x, y, w, h);
		this.val = val;
		this.caster = caster;
		this.right = r;
		this.left = r;
		this.phys = new PhysicsPlug(this);
	}
	
	public Projectile(Object caster, int x, int y, int w, int h, int val, Image r, Image l) {
		super(x, y, w, h);
		this.val = val;
		this.caster = caster;
		this.right = r;
		this.left = l;
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {
		if(phys.motionX!=0){
			dir = (phys.motionX>0?0:1);
		}
		if(!init){
			if(crit){
				posY-=height/2;
				width*=(1.5f);
				height*=(1.5f);
				phys.motionX*=(1.5f);
				phys.motionY*=(1.5f);
				new ClipShell("boom.wav").start();
			}
			init=true;
		}
		if(right.equals(Bank.planet)){
			if(!((Player)caster).hasSkill(Skill.traction)){
				if(collideDown()){
					if(dir==0){
						if(phys.motionX < 20)phys.motionX = 20;
					}
					else {
						if(phys.motionX > -20)phys.motionX = -20;
					}
					new ClipShell("boom.wav").start();
					Display.currentScreen.shake(300, 8);
					phys.motionY = 40;
				}else{
					if(phys.motionY > -20 && phys.motionY < 20)phys.motionY--;
				}
			}
		}
		if(phys.motionX==0&&killXLoss)this.kill();
		if(phys.motionY==0&&killYLoss)this.kill();
		this.dir = phys.motionX >= 0 ? 0 : 1;
		if(maxTime>-1){
			if(System.currentTimeMillis()-start>=maxTime)kill();
		}
		if(effect==24||effect==25){
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.LIQUID, Color.GREEN);
			p.isBlood = true;
			p.img = Bank.glop;
			p.phys.motionY = rand.nextInt(10);
			Display.currentScreen.objects.add(p);
		}
		if(visEffect > 200){
			int m = 120;
			if(visEffect-200==GridBlock.stonebrick.getID())m=180;
			else phys.motionY-=3;
			phys.motionX = (dir==0?m:-m);
			int s = 15+rand.nextInt(11);
			PhysicsPlug pp = new PhysicsPlug(this);
			pp.motionX = -phys.motionX;
			ObjectBlock.split(posX+width/2, posY+height/2, visEffect-200, s, 1, pp);
			if(collideDown())kill();
		}
		if(visEffect==7){
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
			p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionX/2);
			p.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionY/2);
			p.basePhysics = false;
			int s = 8+rand.nextInt(7);
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(effect==3){
			for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Object o = Display.currentScreen.objects.get(i);
				if(o instanceof Player){}else{
					if(o!=this&&o.getHitbox().intersects(getHitbox())){
						if(o.phys!=null){
							o.phys.motionX = o.phys.motionX > 0 ? 3 : o.phys.motionX < 0 ? -3 : 0;
							o.phys.motionY = o.phys.motionY > 0 ? 3 : o.phys.motionY < 0 ? -3 : 0;
						}
					}
				}
			}
		}
		if(effect==17){
			Particle particle = new Particle(posX+width/2, posY+height/2, Particle.SPARKLE, Color.CYAN);
			int s = 3+rand.nextInt(5);
			particle.width = s;
			particle.height = s;
			particle.phys.motionX = -(rand.nextInt(30)-rand.nextInt(30)+phys.motionX);
			particle.phys.motionY = 30+rand.nextInt(41);
			Display.currentScreen.objects.add(particle);
		}
		if(effect==2)phys.motionY+=5;
		if(effect==22){
			phys.motionY--;
			if(this.collideDown())kill();
		}
		if(effect==20){
			if(animCycle>10){
				animCycle=0;
				new ClipShell("chaosblast.wav").start();
			}
			int s = (animCycle+1)*3;
			Particle par = new Particle(posX+(dir==0?0:width), posY+height/2-s/2, Particle.EXPLODE, new Color(125-animCycle*10, 0, 250-animCycle*15));
			par.width = s;
			par.height = s;
			par.phys.motionX = (dir==0?-50:50);
			par.basePhysics = false;
			Display.currentScreen.objects.add(par);
			par.maxTime = 100;
			++animCycle;
		}
		if(effect==19){
			int maxP = 20;
			int mx = 40+rand.nextInt(200), my = rand.nextInt(40)-rand.nextInt(40);
			int mp = 1, mpx = 2;
			for(int i = 0; i < maxP; ++i){
				int s = i/2+1;
				Particle part = new Particle(posX+(dir==1?width+20:-20-s), posY+height/2-(s/2), Particle.EXPLODE, new Color((int)(200+i*2.5), (int)(200+i*2.5), (int)(200+i*2.5), 50+i*3));
				part.width = s;
				part.height = s;
				part.maxTime = 450;
				part.phys.motionX = (dir==1?(i*mpx+mx):-(i*mpx+mx));
				part.phys.motionY = (rand.nextInt(2)==0?(i*mp+my):-(i*mp+my));
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(effect==6){
			phys.motionX+=(phys.motionX>0?5:-5);
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.ZIP, new Color(185, 0, 240));
			p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionX/2);
			p.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionY/2);
			p.basePhysics = true;
			p.maxTime = 200;
			int s = 4+rand.nextInt(7);
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(visEffect==1){
			Particle p = new Particle(this.posX+(this.phys.motionX<0?this.width:this.phys.motionX>0?0:rand.nextInt(this.width)), this.posY+(this.phys.motionY<0?0:this.phys.motionY>0?this.height:rand.nextInt(this.height )), Particle.CHARGED, new Color(185, 0, 240));
			p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionX/2);
			p.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionY/2);
			p.basePhysics = false;
			int s = 4+rand.nextInt(7);
			p.maxTime = 200;
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(effect==2){
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, rand.nextInt(2)==0?Color.GRAY:Color.LIGHT_GRAY);
			p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionX/2);
			p.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionY/2);
			p.basePhysics = false;
			int s = 8+rand.nextInt(7);
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(visEffect==2||visEffect==8||effect==18){
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, rand.nextInt(2)==0?Color.RED:Color.YELLOW);
			p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-phys.motionX;
			p.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
			p.basePhysics = false;
			int s = 4+rand.nextInt(7);
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(visEffect==3){
			Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.EXPLODE, rand.nextInt(2)==0?Color.WHITE:Color.CYAN);
			p.phys.motionX = -(phys.motionX/2);
			p.phys.motionY = rand.nextInt(20)-rand.nextInt(20);
			p.basePhysics = false;
			p.maxTime = 150;
			int s = 2+rand.nextInt(5);
			p.width = s;
			p.height = s;
			Display.currentScreen.objects.add(p);
		}
		if(visEffect==10){
			Color bc = Util.blood;
			Particle sp = new Particle(posX+(rand.nextInt(width)), posY+height/3, Particle.LIQUID, bc);
			sp.solid = true;
			sp.phys.motionX= -phys.motionX;
			sp.phys.motionY = rand.nextInt(20);
			if(sp.physics==Particle.LIQUID){
				sp.isBlood = true;
				sp.basePhysics = false;
			}
			Display.currentScreen.objects.add(sp);		
		}
		for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
			Object o = Display.currentScreen.objects.get(i);
			if(o!=null){
				if(o instanceof Player){
					Player p = (Player)o;
					if(p!=caster&&this.getHitbox().intersects(p.getHitbox())){
						this.onHit(p);
					}
				}
				else{
					if(o.canHarm){
						if(this.getHitbox().intersects(o.getHitbox())){
							this.onHit(o);
						}
					}
				}
			}
		}
		if(effect==27){
			int maxP = 20;
			int mx = rand.nextInt(100), my = rand.nextInt(140)-rand.nextInt(140);
			int mp = 3+rand.nextInt(3), mpx = 2+rand.nextInt(3);
			int rax = rand.nextInt(2), ray = rand.nextInt(2);
			for(int i = 0; i < maxP; ++i){
				int s = i/2+5;
				Particle part = new Particle(posX+width/2, posY+height/2-(s/2), Particle.EXPLODE, new Color(200-i*4, 0, 250-i*5, 180));
				part.width = s;
				part.height = s;
				part.maxTime = 450;
				part.phys.motionX = -phys.motionX+(rax==0?(i*mpx+mx):-(i*mpx+mx));
				part.phys.motionY = (ray==0?(i*mp+my):-(i*mp+my));
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(effect==30){
			phys.motionX = (dir==0?90:-90);
			if(animCycle>19){
				animCycle=0;
			}
			int s = (int) (((animCycle>10?20-animCycle:animCycle)+1)*1.5F);
			Particle par = new Particle(posX+(dir==0?0:width), posY+height/2-s/2, Particle.EXPLODE, new Color(125-((animCycle>10?20-animCycle:animCycle)+1)*10, 0, 250-((animCycle>10?20-animCycle:animCycle)+1)*15));
			par.width = s;
			par.height = s;
			par.phys.motionX = (dir==0?-40:40);
			par.basePhysics = false;
			Display.currentScreen.objects.add(par);
			par.maxTime = 100;
			++animCycle;
			if(flag){
				phys.motionY+=5;
				if(phys.motionY>=40)flag=false;
			}else{
				phys.motionY-=5;
				if(phys.motionY<=-40)flag=true;
			}
		}
		if(effect==31){
			phys.motionX = (dir==0?120:-120);
			if(animCycle>19){
				animCycle=0;
			}
			int s = (int) (((animCycle>10?20-animCycle:animCycle)+1)*3);
			Particle par = new Particle(posX+(dir==0?0:width), posY+height/2-s/2, Particle.EXPLODE, new Color(125-((animCycle>10?20-animCycle:animCycle)+1)*10, 0, 250-((animCycle>10?20-animCycle:animCycle)+1)*15));
			par.width = s;
			par.height = s;
			par.phys.motionX = (dir==0?-40:40);
			par.basePhysics = false;
			Display.currentScreen.objects.add(par);
			par.maxTime = 100;
			++animCycle;
			if(flag){
				phys.motionY+=5;
				if(phys.motionY>=40)flag=false;
			}else{
				phys.motionY-=5;
				if(phys.motionY<=-40)flag=true;
			}
		}
		if(effect==36){
			int r = rand.nextInt(3);
			Color bc = r==0?Color.YELLOW:r==1?Util.orange:Color.RED;
			Particle sp = new Particle(posX+(rand.nextInt(width)), posY+height/3, Particle.LIQUID, bc);
			sp.solid = true;
			sp.phys.motionX= -phys.motionX;
			sp.phys.motionY = rand.nextInt(20);
			sp.isBlood = true;
			sp.basePhysics = false;
			Display.currentScreen.objects.add(sp);
			//
			Particle pa = new Particle(posX+(phys.motionX<0?width:phys.motionX>0?0:rand.nextInt(width)), posY+(phys.motionY<0?0:phys.motionY>0?height:rand.nextInt(height)), Particle.CHARGED, bc);
			pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(phys.motionX/2);
			pa.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(phys.motionY/2);
			pa.basePhysics = false;
			int s = 4+rand.nextInt(7);
			pa.maxTime = 200;
			pa.width = s;
			pa.height = s;
			Display.currentScreen.objects.add(pa);
		}
		if(effect==33){
			ObjectBlock.split(posX+width/2, posY+height, GridBlock.dirt.getID(), 16, 3, phys);
		}
		if(effect==34){
			phys.motionX = (dir==0?45:-45);
			if(!collideDown()){
				phys.motionX = (dir==0?60:-60);
				phys.motionY = -80;
			}
		}
		if(effect==14){
			if(collideDown()){
				Display.currentScreen.getGrid().setGridRelativeTileIDWithScroll(posX, posY+height+1, (byte) 0);
			}
		}
		if(!crosswall){
			if(collides())kill();
		}
	}
	
	public void onHit(Object o){
		Player caster = (Player)this.caster;
		boolean canRem = true;
		if(effect == 34 &! collideDown())canRem = false;
		if(crit && canRem && destroyOnHit){
			if(o instanceof Player){
				Player p = ((Player)o);
				int ret = p.getSkillTier(Skill.retaliation);
				if(ret > 0){
					p.addEffect(new Effect(0, EffectType.retaliation, p, 1500 * ret));
				}
			}
			for(int q = 0; q < 14; ++q){
				int maxP = 20;
				int mx = rand.nextInt(100), my = rand.nextInt(140)-rand.nextInt(140);
				int mp = 3+rand.nextInt(3), mpx = 2+rand.nextInt(3);
				int rax = rand.nextInt(2), ray = rand.nextInt(2);
				for(int i = 0; i < maxP; ++i){
					int r = rand.nextInt(3);
					int rr = (r==0?200:0)+rand.nextInt(256-(r==0?200:0))-(r==2||r==1?150:0);
					int rg = (r==1?200:0)+rand.nextInt(256-(r==1?200:0))-(r==0||r==2?150:0);
					int rb = (r==2?200:0)+rand.nextInt(256-(r==2?200:0))-(r==0||r==1?150:0);
					if(rr<0)rr=0;
					if(rg<0)rg=0;
					if(rb<0)rb=0;
					int s = i/2+5;
					Particle part = new Particle(o.posX+o.width/2, o.posY+o.height/2-(s/2), Particle.EXPLODE, new Color(200, 220, 0, 100));
					part.width = s;
					part.height = s;
					part.maxTime = 450;
					part.phys.motionX = (rax==0?(i*mpx+mx):-(i*mpx+mx));
					part.phys.motionY = (ray==0?(i*mp+my):-(i*mp+my));
					part.basePhysics = false;
					Display.currentScreen.objects.add(part);
				}
			}
		}
		if(o instanceof Player){
			Player newo = ((Player)o);
			int x = o.posX+o.width/2;
			if(newo.blockSide==0&&this.posX+this.width/2 > x){
				if(this.phys.motionX > 0)phys.motionX = -phys.motionX; else phys.motionX = Math.abs(phys.motionX);
				if(this.phys.motionY > 0)phys.motionY = -phys.motionY; else phys.motionY = Math.abs(phys.motionY);
				//if(phys.motionY==0)phys.motionY = (phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX)/3;
				if(phys.motionY==0)phys.motionY = (rand.nextInt(phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX)-rand.nextInt(phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX))/3;
				this.caster = (Player)o;
				this.dir = newo.blockSide;
				this.val/=2;
				//this.kill();
				return;
			}
			if(newo.blockSide==1&&this.posX+this.width/2 < x){
				if(this.phys.motionX > 0)phys.motionX = -phys.motionX; else phys.motionX = Math.abs(phys.motionX);
				if(this.phys.motionY > 0)phys.motionY = -phys.motionY; else phys.motionY = Math.abs(phys.motionY);
				//if(phys.motionY==0)phys.motionY = (phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX)/3;
				if(phys.motionY==0)phys.motionY = (rand.nextInt(phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX)-rand.nextInt(phys.motionX < 0 ? Math.abs(phys.motionX) : phys.motionX))/3;
				this.caster = (Player)o;
				this.dir = newo.blockSide;
				this.val/=2;
				//this.kill();
				return;
			}
		}
		if(effect==37){
			
		}
		if(right.equals(Bank.planet)){
			new ClipShell("rubble1.wav").start();
		}
		if(caster.hasSkill(Skill.oblivion1)){
			if(crit){
				ObjectLightningbolt bolt = new ObjectLightningbolt(o.posX+o.width/2-40, Properties.height);
				bolt.width = (int) (40*2);
				Display.currentScreen.objects.add(bolt);
			}
		}
		if(caster.countEffect(EffectType.sporeholder) > 0){
			Effect e = null;
			for(int i = 0; i < caster.effects.size(); ++i){
				Effect eff = caster.effects.get(i);
				if(eff.type==EffectType.sporeholder)e = eff;
			}
			o.addEffect(new Effect(e.value, EffectType.decay, caster, 4000));
		}
		if(this.right.equals(Bank.fist)){
			new ClipShell("punch.wav").start();
		}
		if(this.right.equals(Bank.arrow)){
			new ClipShell("slice.wav").start();
			int side = (posX+width/2 > o.posX+o.width/2 ? 1 : 0);
			Effect ef = new Effect(side==0?(-rand.nextInt(o.width/2)):(rand.nextInt(o.width/2)-width), EffectType.arrow, caster, 4000);
			ef.dat = Util.dif(posY-height/2, o.posY)+rand.nextInt(20)-rand.nextInt(20);
			ef.dat1 = side;
			o.addEffect(ef);
		}
		if(caster.hero.equals(Hero.snoopdog)){
			new ClipShell(rand.nextInt(2)==0?"omg.wav":"yes.wav", 3F).start();
			for(int i = 0; i < 30; ++i){
				int s = rand.nextInt(21)+10;
				ObjectSnoopgif obj = new ObjectSnoopgif(true, o.posX+o.width/2, o.posY+o.height/2, s, s);
				obj.img = Bank.hitmarker;
				obj.phys.motionX = rand.nextInt(100)-rand.nextInt(100);
				obj.phys.motionY = 30+rand.nextInt(80);
				Display.currentScreen.objects.add(obj);
			}
			if(crit){
				ObjectSnoopgif obj = new ObjectSnoopgif(false, o.posX+o.width/2-70, o.posY+o.height-220, 140, 220);
				obj.img = Bank.explosion;
				new ClipShell("boom.wav", 6F).start();
				caster.addEffect(new Effect(1, EffectType.high, caster, 2000));
				Display.currentScreen.objects.add(obj);
			}
		}
		if(caster.countEffect(EffectType.stardust) > 0){
			int i = caster.countEffect(EffectType.stardust);
			o.addEffect(new Effect(1000*1, EffectType.stun, caster, 1000*i));
		}
		if(effect==14||effect==22){
			o.hurt(val, crit, this);
		}
		if(effect==23){
			o.addEffect(new Effect(val/3, EffectType.rapidAging, caster, 2000));
			o.hurt(val, crit, this);
		}
		if(effect==24){
			o.addEffect(new Effect(val, EffectType.corrosion, caster, 3000));
			o.hurt(val, crit, this);
		}
		if(effect==25){
			o.addEffect(new Effect(val, EffectType.venom, caster, 2000));
			o.hurt(val, crit, this);
		}
		if(effect==26){
			o.addEffect(new Effect(val, EffectType.brainrot, caster, 2500));
		}
		if(effect==27){
			o.addEffect(new Effect(val, EffectType.brainfry, caster, 4000));
		}
		if(effect==28){
			o.addEffect(new Effect(val, EffectType.corruption, caster, 8000));
		}
		if(effect==29){
			o.hurt(val, crit, this);
			ArrayList<Effect> efs = caster.effects;
			caster.effects = o.effects;
			o.effects = efs;
		}
		if(effect==30||effect==31){
			o.hurt(val, crit, this);
		}
		if(effect==32){
			o.addEffect(new Effect(val, EffectType.hemmorage, caster, 2000));
		}		
		if(effect==33){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(2, EffectType.concussion, caster, 1200));
		}
		if(effect==34&&this.collideDown()){
			o.hurt(val/2, crit, this);
			o.addEffect(new Effect(val/3, EffectType.flames, caster, 2000));
		}
		if(crit){
			if(effect==36){
				o.addEffect(new Effect(1000, EffectType.stun, null, 1000));
			}
			if(o.countEffect(EffectType.volatility) > 0){
				new ClipShell("boom.wav").start();
				for(int i = 0; i < o.effects.size(); ++i){
					Effect e = o.effects.get(i);
					if(e.type==EffectType.volatility){
						o.phys.motionY+=120;
						o.hurt(e.value, crit);
						o.addEffect(new Effect(3000, EffectType.stun, caster, 3000));
						o.removeEffect(EffectType.volatility, 10);
						for(int x = 0; x < 40; ++x){
							Color color = (rand.nextInt(2)==0?Color.YELLOW:Color.RED);
							Particle pa = new Particle(posX+rand.nextInt(o.width), o.posY+o.height-12, Particle.CHARGED, color);
							pa.phys.motionX = rand.nextInt(40)-rand.nextInt(40);
							pa.phys.motionY = -rand.nextInt(165);
							pa.basePhysics = false;
							int s = 4+rand.nextInt(7);
							pa.maxTime = 450;
							pa.width = s;
							pa.height = s;
							Display.currentScreen.objects.add(pa);
						}
					}
				}
			}
		}
		if(effect==35){
			o.hurt(val/2, crit, this);
			o.addEffect(new Effect(val*2, EffectType.volatility, caster, 5000));
		}
		if(caster.countEffect(EffectType.sigil) > 0){
			for(int i = 0; i < caster.effects.size(); ++i){
				Effect e = caster.effects.get(i);
				if(e.type==EffectType.sigil){
					o.hurt(val*2, crit, this);
					o.effects.add(new Effect(e.value, EffectType.sigil, caster, -1));
					caster.removeEffect(EffectType.sigil);
				}
			}
		}
		if(effect==19){
			new ClipShell("shadebuff.wav").start();
			o.hurt(val+o.effects.size()*(val/4), crit, this);
			o.effects.clear();
			o.addEffect(new Effect(val, EffectType.haunt, caster, 6000));
		}
		if(effect==20){
			o.hurt(val, crit, this);
			if(crit){
				caster.heal(val/2, true);
			}
			for(int q = 0; q < 14; ++q){
				int maxP = 20;
				int mx = rand.nextInt(100), my = rand.nextInt(140)-rand.nextInt(140);
				int mp = 3+rand.nextInt(3), mpx = 2+rand.nextInt(3);
				int rax = rand.nextInt(2), ray = rand.nextInt(2);
				for(int i = 0; i < maxP; ++i){
					int s = i/2+5;
					Particle part = new Particle(o.posX+o.width/2, o.posY+o.height/2-(s/2), Particle.EXPLODE, new Color(200-i*4, 0, 250-i*5, 180));
					part.width = s;
					part.height = s;
					part.maxTime = 450;
					part.phys.motionX = (rax==0?(i*mpx+mx):-(i*mpx+mx));
					part.phys.motionY = (ray==0?(i*mp+my):-(i*mp+my));
					part.basePhysics = false;
					Display.currentScreen.objects.add(part);
				}
			}
		}
		if(effect==21){
			o.hurt((o.health<=o.healthMax/5?val*5:val), crit, this);
			o.renderBlood(30, 0, 0);
		}
		if(effect==4){
			new ClipShell("shortstatic.wav").start();
			if(o.phys!=null){
				o.phys.motionX = 0;
				o.phys.motionY = 0;
			}
			if(o.countEffect(EffectType.electrified) > 0){
				caster.hurt(val*(1+o.countEffect(EffectType.electrified)), crit);
				caster.addEffect(new Effect(200, EffectType.electrified, caster, 2000));
			}
			o.addEffect(new Effect(200, EffectType.electrified, caster, 2000));
			o.hurt(val, crit, this);
		}
		if(effect==5){
			new ClipShell("laserbub.wav").start();
			if(o.phys!=null){
				o.phys.motionX = 0;
				o.phys.motionY = 0;
			}
			o.hurt(val, crit, this);
		}
		if(effect==8){
			o.addEffect(new Effect(val, EffectType.stun, caster, val));
		}
		if(effect==6){
			o.hurt(val, crit, this);
			for(int i = 0; i < 40; ++i){
				Particle p = new Particle(posX, posY, Particle.CHARGED, Color.BLACK);
				p.basePhysics = false;
				p.phys.motionX = rand.nextInt(200)-rand.nextInt(200);
				p.phys.motionY = 250+rand.nextInt(151);
				int s = 4+rand.nextInt(7);
				p.width = s;
				p.height = s;
				Display.currentScreen.objects.add(p);
			}
		}
		if(effect==7){
			o.addEffect(new Effect(val, EffectType.banshee, caster, 4000+val*10));
		}
		if(effect==10){
			o.addEffect(new Effect(val, EffectType.suffering, caster, 9000+val*25));
		}
		if(effect==11){
			o.addEffect(new Effect(val, EffectType.creeper, caster, 4000+val*10));
		}
		if(effect==12){
			o.addEffect(new Effect(val, EffectType.cataclysm, caster, 10000));
		}
		if(effect==13){
			o.hurt(val, crit, this);
			caster.healRaw(val/2, false);
		}
		if(effect==15){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(val/5, EffectType.dragonfire, caster, 5000));
		}
		if(effect==16){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(val, EffectType.chill, caster, 3000+val*5));
		}
		if(effect==17){
			new ClipShell("frostimpact.wav").start();
			o.hurt(val, crit, this);
			for(int i = 0; i < 30; ++i){
				Particle particle = new Particle(o.posX+o.width/2, o.posY+o.height/2, Particle.SPARKLE, Color.CYAN);
				int s = 4+rand.nextInt(8);
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(30)-rand.nextInt(30)+phys.motionX;
				particle.phys.motionY = 30+rand.nextInt(41);
				Display.currentScreen.objects.add(particle);
			}
			if(this.notes.contains("chl")){
				new ClipShell("magicwhip.wav").start();
				boolean chilled = false;
				for(Effect e : o.effects){
					if(e.type.equals(EffectType.chill))chilled=true;
				}
				if(chilled)
					o.addEffect(new Effect(val, EffectType.frozen, caster, 3000+val*5));
				else
					o.addEffect(new Effect(val, EffectType.chill, caster, 3000+val*5));
			}
		}
		if(effect==18){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(val/3, EffectType.burn, caster, 2000));
		}
		if(knockback){
			if(o.phys!=null){
				o.phys.motionX += phys.motionX/3 + rand.nextInt(phys.motionX/4>0?phys.motionX/4:1);
				int y = phys.motionX/2;
				if(y<0)y=Math.abs(phys.motionX);
				o.phys.motionY += 10+y/2;
			}
		}
		if(effect == 0){
			o.hurt(val, crit, this);
		}
		if(effect == 2){
			o.hurt(val, crit, this);
			o.phys.motionY=200;
		}
		if(effect == 1){
			if(o.countEffect(EffectType.moonlight) <= 0){
				o.addEffect(new Effect(1, EffectType.moonlight, caster, 5000));
			}
		}
		if(visEffect==2){
			for(int i = 0; i < 30; ++i){
				Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, rand.nextInt(2)==0?Color.RED:Color.YELLOW);
				p.phys.motionX = rand.nextInt(120)-rand.nextInt(120);
				p.phys.motionY = rand.nextInt(120)-rand.nextInt(120);
				p.basePhysics = false;
				int s = 9+rand.nextInt(12);
				p.width = s;
				p.height = s;
				Display.currentScreen.objects.add(p);
			}
		}
		if(effect==39){
			o.addEffect(new Effect(val, EffectType.curse, caster, 5000));
		}
		if(effect==40){
			o.addEffect(new Effect(val, EffectType.plague, caster, 5000));
		}
		if(effect==41){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(val, EffectType.fever, caster, 10000));
			((Player)o).respawns++;
		}
		if(effect==42){
			o.hurt(val, crit, this);
			o.addEffect(new Effect(val, EffectType.fever, caster, -1));
			((Player)o).respawns++;
		}
		if(destroyOnHit&&canRem)
		this.kill();
	}

	public void draw(Graphics g) {
		if(effect==34){
			int h = height;
			if(!collideDown()){
				h = 30;
				g.drawImage(phys.motionX > 0 ? Bank.firemissileRight : Bank.firemissile, posX+width/2-15, posY+height-h, 30, 30, null);
			}
			else g.drawImage(Bank.totemflames, posX, posY+height-h, width, h, null);
		}
		if(effect==33){
			int hei = (int) ((System.currentTimeMillis()-start) * height / maxTime);
			int h = height-hei;
			g.drawImage(phys.motionX > 0 ? Bank.slice : Bank.sliceLeft, posX, posY+height-h, width, h, null);
		}
		if(effect==12){
			g.drawImage(Bank.fire, posX, posY-6, width, height, null);
			g.drawImage(Bank.firenova, posX-5, posY-5, width+10, height+10, null);
		}
		if(effect==35){
			int size = rand.nextInt(width);
			g.drawImage(Bank.firenova, posX+width/2-size/2, posY+height/2-size/2, size, size, null);
		}
		if(visEffect==4){
			int e =  rand.nextInt(5);
			BufferedImage i = e == 0 ? Bank.slice0 : e == 1 ? Bank.slice1 : e == 2 ? Bank.slice2 : e == 3 ? Bank.slice3 : e == 4 ? Bank.slice4 : Bank.slice0;
			g.drawImage(i, posX, posY, width, height, null);
		}
		else if(visEffect==5){
			for(int i = 0; i < 50; ++i){
				int s = rand.nextInt(3);
				g.drawImage(rand.nextInt(2)==0?Bank.spark0:Bank.spark1, posX+rand.nextInt(width), posY+rand.nextInt(height), s==0?20:s==1?30:40, s==0?20:s==1?30:40, null);
			}
		}
		else if(visEffect==8){
			g.drawImage(dir==1?(rand.nextInt(2)==0?Bank.skull0:Bank.skull1):(rand.nextInt(2)==0?Bank.skull0left:Bank.skull1left), posX, posY, width, height, null);
		}
		else if(visEffect==-1){
		}
		else if(visEffect==6){
			for(int i = 0 ; i < 30; ++i){
				g.setColor(rand.nextInt(2) == 0 ? Color.YELLOW : Color.CYAN);
				g.drawLine(posX+width/2, posY+height/2, posX+width/2+(rand.nextInt(width*20)-rand.nextInt(width*20))-phys.motionX, posY+height/2+(rand.nextInt(width*20)-rand.nextInt(width*20)));
			}
		}else{
			g.drawImage(dir==0?right:left, posX, posY, width, height, null);
		}
	}
}
