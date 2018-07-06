package code;

import java.awt.Graphics;
import java.awt.Image;

public class CreatureTroll extends ObjectEnemy{
	
	long lastSlam = System.currentTimeMillis();

	public CreatureTroll(int x, int y) {
		super(x, y);
		this.width = 45;
		this.height = 90;
		this.health = 15;
		this.healthMax = health;
		this.solid = true;
		this.damage = 1;
		this.speed = 2;
		this.attackspeedmillis = 650;
	}
	
	int walkTick = 0;

	public void onHit(Object attacker){
	}
	
	public void updateEnemy() {
		if(tar!=null && System.currentTimeMillis()-lastSlam >= 4000){
			posY-=20;
			phys.motionY = 80;
			addEffect(new Effect(1,EffectType.slam,null,5000));
			lastSlam = System.currentTimeMillis();
		}
	}
	
	public void drawEnemy(Graphics g) {
		Image tex;
		tex = trueDir==0?Bank.troll_stand:Bank.trollLeft_stand;
		if(walkTick<=3)walkTick+=0.05F;
		int anim = (int) walkTick;
		if(right){tex = anim == 0 ? Bank.troll_walk0 : anim == 1 ? Bank.troll_walk1 : anim == 2 ? Bank.troll_walk2 : Bank.troll_walk0;}
		if(left){tex = anim == 0 ? Bank.trollLeft_walk0 : anim == 1 ? Bank.trollLeft_walk1 : anim == 2 ? Bank.trollLeft_walk2 : Bank.trollLeft_walk0;}
		if(renderAttack){
			tex = trueDir==0?Bank.troll_headbutt:Bank.trollLeft_headbutt;
		}
		if(this.countEffect(EffectType.slam) > 0){
			tex = trueDir==0?Bank.troll_slam:Bank.trollLeft_slam;
		}
		g.drawImage(tex, posX, posY, width, height, null);
		if(walkTick>3)walkTick = 0;
		this.renderCombatBars(g);
	}
}
