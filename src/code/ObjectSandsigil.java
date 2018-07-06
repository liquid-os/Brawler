package code;

import java.awt.Graphics;

public class ObjectSandsigil extends Object {
	
	Player owner;
	int interval = 2000, val = 0, dir = 0;
	long last = System.currentTimeMillis();

	public ObjectSandsigil(Player p, int x, int y, int w, int h, int v) {
		super(x, y, w, h);
		this.phys = new PhysicsPlug(this);
		owner = p;
		val = v;
		canHarm = true;
		health = v;
		healthMax = health;
		if(owner.hasSkill(Skill.sandguard))addEffect(new Effect(v/2, EffectType.sanctuary, p, 1250));
	}
	
	public boolean isCorrupted(){
		return health < healthMax;
	}

	public void update(double delta) {
		if(last+interval < System.currentTimeMillis()){
			Object obj = null;
			if(owner.hasSkill(Skill.sandguard))addEffect(new Effect(val/2, EffectType.sanctuary, owner, 1250));
			if(this.isCorrupted()){
				obj = new ObjectHolypulse(owner, posX+width/2-10, posY+height/2-10, 850, true, val);
				owner.hurt(val, false, null);
			}else{
				obj = new ObjectHolypulse(owner, posX+width/2-10, posY+height/2-10, 850, false, val);
				owner.heal(val/2, false);
			}
			Display.currentScreen.objects.add(obj);
			last = System.currentTimeMillis();
		}
		if(health<=0)kill();
	}

	public void draw(Graphics g) {
		g.drawImage(this.isCorrupted()?Bank.raCorrupt:Bank.ra, posX, posY, width, height, null);
	}
}
