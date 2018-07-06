package code;

import java.awt.Graphics;
import java.awt.Image;

public class CreatureBanshee extends ObjectEnemy{
	
	Object owner = null;
	
	public CreatureBanshee(Object owner, int x, int y, int hp, int dam) {
		super(x, y);
		this.owner = owner;
		this.width = 90+dam;
		this.height = 60+(dam/3*2);
		this.health = hp;
		this.healthMax = health;
		this.solid = true;
		this.damage = dam;
		this.speed = 4;
		this.attackspeedmillis = 500;
	}
	
	public void onHit(Object attacker){
		attacker.addEffect(new Effect(damage, EffectType.brainrot, null, 1500));
		owner.addEffect(new Effect(1, EffectType.mindcharge, null, -1));
	}
	
	public void collideWithPlayer(Player p){
		if(tar==null&&p!=owner)tar=p;
	}
	
	public void updateEnemy() {
		if(tar==null){
			for(Object o : Display.currentScreen.objects){
				if(o!=owner)tar=o;
			}
		}else{
			if(tar.health<=0)tar=null;
		}
	}
	
	public void drawEnemy(Graphics g) {
		Image tex;
		tex = trueDir==0?Bank.hexling_run:Bank.hexlingLeft_run;
		if(right){tex = Bank.hexling_run;}
		if(left){tex = Bank.hexlingLeft_run;}
		if(renderAttack){
			tex = trueDir==0?Bank.hexling_lurch:Bank.hexlingLeft_lurch;
		}
		g.drawImage(tex, posX, posY, width, height, null);
		this.renderCombatBars(g);
	}
}
