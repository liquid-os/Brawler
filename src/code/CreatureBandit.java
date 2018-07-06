package code;

import java.awt.Graphics;
import java.awt.Image;

public class CreatureBandit extends ObjectEnemy{
	
	public CreatureBandit(int x, int y) {
		super(x, y);
		this.width = 30;
		this.height = 90;
		this.health = 5;
		this.healthMax = health;
		this.solid = true;
		this.damage = 1;
		this.speed = 3;
		this.attackspeedmillis = 1500;
	}
	
	public void onHit(Object attacker){
	}
	
	public void updateEnemy() {
	}
	
	public void drawEnemy(Graphics g) {
		Image tex;
		tex = trueDir==0?Bank.bandit_stand:Bank.banditLeft_stand;
		if(right){tex = Bank.bandit_run;}
		if(left){tex = Bank.banditLeft_run;}
		if(renderAttack){
			tex = trueDir==0?Bank.bandit_attack:Bank.banditLeft_attack;
		}
		g.drawImage(tex, posX, posY, width, height, null);
		this.renderCombatBars(g);
	}
}
