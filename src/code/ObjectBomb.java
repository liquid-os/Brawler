package code;

import java.awt.Graphics;

public class ObjectBomb extends Object {

	public ObjectBomb(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.canHarm = true;
		this.phys = new PhysicsPlug(this);
		this.health = 1;
		this.healthMax = 1;
	}

	public void update(double delta) {
	}

	public void draw(Graphics g) {
		g.drawImage(Bank.cannonball, posX, posY, width, height, null);
	}
}
