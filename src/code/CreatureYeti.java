package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class CreatureYeti extends ObjectEnemy{


	public CreatureYeti(int x, int y) {
		super(x, y);
		this.width = 75;
		this.height = 150;
		this.health = 15;
		this.healthMax = health;
		this.solid = true;
		this.damage = 4;
		this.speed = 2;
		this.attackspeedmillis = 1250;
		this.left = true;
	}
	
	int walkTick = 0;

	public void onHit(Object attacker){
	}
	
	public void updateEnemy() {
		if(tar==null){
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
			for(Object o : Display.currentScreen.objects){
				if(o instanceof ObjectSnowbro){
					if(o.getHitbox().intersects(new Rectangle(posX+width/2-250, posY, 500, height)))
					tar = o;
				}
			}
		}else{
			if(tar.health<=0)tar=null;
		}
	}
	
	public void collideWithPlayer(Player p){
		
	}
	
	public void drawEnemy(Graphics g) {
		Image tex;
		tex = trueDir==0?Bank.yeti_walk0:Bank.yetiLeft_walk0;
		if(walkTick<=3)walkTick+=0.05F;
		int anim = (int) walkTick;
		if(right){tex = anim == 0 ? Bank.yeti_walk0 : anim == 1 ? Bank.yeti_walk1 : anim == 2 ? Bank.yeti_walk2 : Bank.yeti_walk0;}
		if(left){tex = anim == 0 ? Bank.yetiLeft_walk0 : anim == 1 ? Bank.yetiLeft_walk1 : anim == 2 ? Bank.yetiLeft_walk2 : Bank.yetiLeft_walk0;}
		if(renderAttack){
			tex = trueDir==0?Bank.yeti_punch:Bank.yetiLeft_punch;
		}
		if(tar!=null){
			g.setColor(Color.RED);
			for(int i = 0; i < 4; ++i)
			g.drawLine(posX+width/2, posY+25+i, tar.posX+tar.width/2, tar.posY+tar.height/2+i);
		}
		g.drawImage(tex, posX, posY, width, height, null);
		if(walkTick>3)walkTick = 0;
		this.renderCombatBars(g);
	}
}
