package code;

import java.awt.Graphics;
import java.awt.Image;

public class CreatureSoldier extends ObjectEnemy{
	
	Object owner = null;
	
	public CreatureSoldier(Object owner, int x, int y, int hp, int dam) {
		super(x, y);
		this.owner = owner;
		this.width = 30;
		this.height = 90;
		this.health = hp;
		this.healthMax = health;
		this.solid = true;
		this.damage = dam;
		this.speed = 4;
		this.attackspeedmillis = 1000;
	}
	
	public void onHit(Object attacker){
		addEffect(new Effect(damage, EffectType.sanctuary, null, -1));
	}
	
	public void collideWithPlayer(Player p){
		if(tar==null&&p!=owner)tar=p;
	}
	
	public void updateEnemy() {
		if(tar==null){
			for(Object o : Display.currentScreen.objects){
				boolean target = true;
				if(o instanceof CreatureSoldier){
					if(((CreatureSoldier)o).owner==owner)target=false;
				}
				if(o==owner|!o.canHarm)target=false;
				if(target)tar=o;
			}
		}else{
			if(tar==owner||tar.health<=0)tar=null;
		}
	}
	
	public void drawEnemy(Graphics g) {
		Image tex, tex1;
		tex = trueDir==0?Bank.soldier_stand:Bank.soldierLeft_stand;
		tex1 = trueDir==0?Bank.hammer_stand:Bank.hammerLeft_stand;
		if(right){tex = Bank.soldier_walk;tex1 = Bank.hammer_walk;}
		if(left){tex = Bank.soldierLeft_walk;tex1 = Bank.hammerLeft_walk;}
		if(renderAttack){
			tex = trueDir==0?Bank.soldier_attack:Bank.soldierLeft_attack;
			tex1 = trueDir==0?Bank.hammer_attack:Bank.hammerLeft_attack;
		}
		int w = height*2, h = height+width;
		g.drawImage(tex, posX, posY, width, height, null);
		g.drawImage(tex1, posX+width/2-w/2, posY+height-h, w, h, null);
		this.renderCombatBars(g);
		if(((Player)owner).getUsername()==Properties.username){
			g.drawImage(Bank.rawplus, posX+width/2-10, posY-70, 20, 20, null);
		}
	}
}
