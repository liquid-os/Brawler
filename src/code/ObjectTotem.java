package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ObjectTotem extends Object{
	
	int element = 0, val = 0, range = 400, interval = 2000, ticks = 0;
	Object caster = null;
	long last = System.currentTimeMillis();

	public ObjectTotem(Object caster, int totem, int x, int y, int val) {
		super(x, y, 60, 100);
		this.phys = new PhysicsPlug(this);
		this.element = totem;
		this.val = val;
		this.caster = caster;
		if(element==0){
			range = ((Player)caster).hasSkill(Skill.worldinflames)?600:300;
			interval = 3000;
		}
		if(element==1){
			range = 400;
			interval = 4000;
		}
		if(element==2){
			range = 600;
			interval = 2000;
		}
		if(element==3){
			range = 450;
			interval = 3000;
		}
	}
	
	public void update(double delta) {
		if(!collideDown())phys.motionY-=3;
		if(System.currentTimeMillis()-last >= interval){
			ticks++;
			switch(element){
			case 0:
				new ClipShell("dragonbreath.wav").start();
				for(int i = 0; i < 2; ++i){
					int v = val/2;
					if(v<1)v++;
					Projectile proj = new Projectile(((Player) caster), this.posX+this.width/2-this.width, this.posY+this.height-60, this.width*2, 60, v, Bank.totemflames);
					proj.knockback = false;
					proj.phys.motionX = i == 0 ? 225 : -225;
					proj.killYLoss = false;
					proj.destroyOnHit = true;
					proj.visEffect = -1;
					proj.effect = 34;
					proj.crosswall = false;
					proj.solid = true;
					proj.crit = false;
					Display.currentScreen.objects.add(proj);
				}
				interval-=ticks*100;
				if(interval<=0)this.kill();
				break;
			case 1:
				if(caster.getHitbox().intersects(getRangeBox()))caster.heal(val, false);
				break;
			case 2:
				if(caster.getHitbox().intersects(getRangeBox())){
					caster.addEffect(new Effect(val, EffectType.windhaste, (Player) caster, interval/4*3));
				}
				break;
			case 3:
				if(caster.getHitbox().intersects(getRangeBox())){
					caster.addEffect(new Effect(val, EffectType.sanctuary, (Player) caster, interval/3*2));
				}
				break;
			}
			Color c = element==0?Color.RED:element==1?Color.CYAN:element==2?Color.YELLOW:Color.GREEN;
			for(int i = 0; i < 30; ++i){
				int s = 4+rand.nextInt(7);
				Particle p = new Particle(posX+width/2-s/2, posY+height/2-s/2, Particle.EXPLODE, c);
				p.phys.motionX = rand.nextInt(100)-rand.nextInt(100);
				p.phys.motionY = rand.nextInt(100)-rand.nextInt(100);
				p.width = s;
				p.height = s;
				Display.currentScreen.objects.add(p);
			}
			last = System.currentTimeMillis();
		}
	}
	
	public Rectangle getRangeBox(){
		return new Rectangle(posX+width/2-range/2, posY+height/2-range/2, range, range);
	}

	public void draw(Graphics g) {
		g.drawImage(element==0?Bank.totemFire:element==1?Bank.totemWater:element==2?Bank.totemStorm:Bank.totemEarth, posX, posY, width, height, null);
		Color c = element==0?Color.RED:element==1?Color.CYAN:element==2?Color.YELLOW:Color.GREEN;
		Color col = new Color(c.getRed(), c.getGreen(), c.getBlue(), 80);
		g.setColor(col);
		g.drawOval(posX+width/2-range/2, posY+height/2-range/2, range, range);
	}
}
