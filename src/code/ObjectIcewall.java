package code;

import java.awt.Graphics;

public class ObjectIcewall extends Object{

	int startX = 0, startY = 0;
	long start = System.currentTimeMillis();
	int riseTime = 400, dir = 0;

	public ObjectIcewall(int x, int y, int w, int h, int dir, int hp) {
		super(x, y, w, h);
		this.dir = dir;
		this.canHarm = true;
		startX = x;
		startY = y;
		this.health = hp;
		this.healthMax = hp;
	}

	public void update(double delta) {	
	}

	public void draw(Graphics g) {
		int h = (int) ((System.currentTimeMillis()-start) * height / riseTime);
		if(h>height)h = height;
		g.drawImage(dir==0?Bank.icewall_flip:Bank.icewall, posX, posY+height-h, width, h, null);
	}
}
