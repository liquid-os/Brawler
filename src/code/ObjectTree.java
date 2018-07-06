package code;

import java.awt.Graphics;

public class ObjectTree extends Object {
	
	int amt = 1, chargeTime = 4000, charges = 6;
	boolean ready = false;
	long start = System.currentTimeMillis();
	Object owner;

	public ObjectTree(Object o, int x, int y, int amt) {
		super(x, y, 80, 160);
		this.owner = o;
		this.canHarm = true;
		this.health = 10;
		this.healthMax = health;
		phys = new PhysicsPlug(this);
		this.amt = amt;
	}

	public void update(double delta) {
		width = 80-(healthMax-health)*5;
		height = 160-(healthMax-health)*10;
		if(!collideDown())phys.motionY-=3;
		if(!ready){
			if(System.currentTimeMillis()-start >= chargeTime){
				ready = true;
			}
		}
		if(health<=0)kill();
	}
	
	public void collideWithPlayer(Player p){
		if(ready&&charges>0&&p==owner){
			--charges;
			p.heal(amt, false);
			ready = false;
			start = System.currentTimeMillis();
		}
		if(charges<=0)kill();
	}

	public void draw(Graphics g) {
		g.drawImage(ready?Bank.tree:Bank.tree_bare, posX, posY, width, height, null);
	}
}
