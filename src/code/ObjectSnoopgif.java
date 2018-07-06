package code;

import java.awt.Graphics;
import java.awt.Image;

public class ObjectSnoopgif extends Object{
	
	Image img;
	int maxTime = -1;
	long start;

	public ObjectSnoopgif(boolean fall, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.phys = new PhysicsPlug(this);
		this.setCanFall(fall);
		this.img = getImage();
		solid = false;
		start = System.currentTimeMillis();
	}
	
	public ObjectSnoopgif(boolean fall, int x, int y, int w, int h, Image image, int maxTime) {
		super(x, y, w, h);
		this.phys = new PhysicsPlug(this);
		this.setCanFall(fall);
		this.img = image;
		solid = false;
		start = System.currentTimeMillis();
		this.maxTime = maxTime;
	}
	
	public ObjectSnoopgif(boolean fall, int x, int y, int w, int h, Image image, int maxTime, boolean solid) {
		super(x, y, w, h);
		this.phys = new PhysicsPlug(this);
		this.setCanFall(fall);
		this.img = image;
		this.solid = solid;
		start = System.currentTimeMillis();
		this.maxTime = maxTime;
	}
	
	public Image getImage(){
		int r = rand.nextInt(7);
		return r == 0 ? Bank.gif420 : r == 1? Bank.boogie : r == 2 ? Bank.skele : r == 3 ? Bank.edgy : r == 4 ? Bank.chicken : r == 5 ? Bank.puppet : r == 6 ? Bank.dancing : Bank.gif420;
	}

	public void update(double delta) {
		if(this.getCanFall()&&phys.motionY<=0)phys.motionY-=5;
		if(maxTime!=-1){
			if(System.currentTimeMillis()-start >= maxTime)this.kill();
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, posX, posY, width, height, null);
		if(posY>=Properties.height)kill();
	}
}
