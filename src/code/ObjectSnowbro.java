package code;

import java.awt.Graphics;
import java.awt.Image;

public class ObjectSnowbro extends ObjectNPC{

	public ObjectSnowbro(int x, int y, int w, int h) {
		super(x, y, w, h);
		this.canHarm = true;
		this.phys.setMotionCap(45);
		this.health = 12;
		this.healthMax = health;
	}

	public void updateNPC(double delta) {
	}

	public void drawNPC(Graphics g) {
		Image tex;
		tex = dir==0?Bank.snowbro_stand:Bank.snowbroLeft_stand;
		if(right){tex = Bank.snowbro_walk;}
		if(left){tex = Bank.snowbroLeft_walk;}
		if(!left&!right){
			tex = (dir==0?Bank.snowbro_sit:Bank.snowbroLeft_sit);
		}
		if((this.phys.motionY < -10 || this.phys.motionY > 10) &! this.collideDown()){
			//tex = trueDir == 0 ? (rand.nextInt(2)==0?Bank.snowbro_fall0:Bank.snowbro_fall1) : (rand.nextInt(2)==0?Bank.snowbroLeft_fall0:Bank.snowbroLeft_fall1);
		}
		g.drawImage(tex, posX, posY, width, height, null);
	}
}
