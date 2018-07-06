package code;

import java.awt.Graphics;

public class ObjectCloud extends Object{
	
	int speed = 6, dir = 0;

	public ObjectCloud(int dir, int w, int h, int speed) {
		super(dir==0?-w:Properties.width, Util.rand.nextInt(Properties.height), w, h);
		this.speed = speed;
		this.solid = false;
		this.dir = dir;
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {
		this.phys.motionX = (dir==0?speed:-speed);
		if(posX<-width||posX>Properties.width)kill();
	}

	public void draw(Graphics g) {
		g.drawImage(Bank.skycloud, posX, posY, width, height, null);
	}
}
