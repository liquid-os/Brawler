package code;

import java.awt.Graphics;

public abstract class ObjectNPC extends Object {
	
	boolean right = false, left = false;
	int dir = 0, speed = 2, dirChangeInt = 2000+Util.rand.nextInt(4000);
	long lastDirChange = System.currentTimeMillis();

	public ObjectNPC(int x, int y, int w, int h) {
		super(x, y, w, h);
		phys = new PhysicsPlug(this).setMotionCap(70);
		this.setCanFall(true);
	}

	public final void update(double delta) {
		if(health<=0){
			this.kill();
		}
		if(this.getCanFall()){
			if(!collideDown()|!solid)phys.motionY-=3;
		}
		if(!this.collideUp()&&collideDown())posY-=((posY+((GridPanel)Display.currentScreen).scrollY)%30);
		if(left){
			phys.motionX-=speed;
			dir = 1;
		}
		if(right){
			phys.motionX+=speed;
			dir = 0;
		}
		if(((left&&this.collideLeft())||(right&&this.collideRight()))||(System.currentTimeMillis()-lastDirChange >= dirChangeInt)){
			if(!left&!right){
				if(rand.nextInt(2)==0)left=true;else right=true;
			}
			else if(left){
				if(rand.nextInt(2)==0){left = false; right = true;}else{
					left = false;
				}
			}
			else if(right){
				if(rand.nextInt(2)==0){right = false; left = true;}else{
					right = false;
				}
			}
			lastDirChange = System.currentTimeMillis();
			dirChangeInt = 4000+(rand.nextInt(6000));
		}
		updateNPC(delta);
	}

	public final void draw(Graphics g) {
		drawNPC(g);
	}
	
	public abstract void updateNPC(double d);
	public abstract void drawNPC(Graphics g);
}
