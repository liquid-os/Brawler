package code;

import java.awt.Graphics;

public class ObjectPenguin extends Object {
	
	Player owner;
	int interval = 1000, val = 0, dir = 0;
	long last = System.currentTimeMillis();

	public ObjectPenguin(Player p, int x, int y, int w, int h, int v) {
		super(x, y, w, h);
		owner = p;
		val = v;
		phys = new PhysicsPlug(this);
		canHarm = true;
		health = v;
		healthMax = health;
	}

	public void update(double delta) {
		if(last+interval < System.currentTimeMillis()){
			dir = (dir==0?1:0);
			Projectile proj = new Projectile(owner, posX+(dir==0?width:-30), posY+10, 30, 30, val, Bank.winds);
			proj.phys.motionX = (dir==0?125:-125);
			proj.solid = true;
			proj.knockback = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			Display.currentScreen.objects.add(proj);
			last = System.currentTimeMillis();
		}
		if(health<=0)kill();
	}

	public void draw(Graphics g) {
		g.drawImage(dir==0?Bank.penguin:Bank.penguinLeft, posX, posY, width, height, null);
	}
}
