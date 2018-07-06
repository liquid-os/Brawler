package code;

import java.awt.Graphics;
import java.awt.Image;

public class CreatureEagle extends Object{
	
	Object owner = null;
	long lastAttack = System.currentTimeMillis();
	int attackSpeed = 3000, speed = 4, damage = 3;
	Object tar = null;
	Coordinate dest = null;
	int startY = 0;
	
	public CreatureEagle(Object owner, int x, int y, int dam) {
		super(x, y, 200, 160);
		this.startY = y;
		this.dest = new Coordinate(Properties.width/2+((GridPanel)Display.currentScreen).scrollX, y);
		this.owner = owner;
		this.width = 30;
		this.height = 90;
		this.solid = false;
		this.canHarm = false;
		this.damage = dam;
		this.speed = 4;
		this.attackSpeed = 3000;
	}
	
	public void onHit(Object attacker){
	}
	
	public void collideWithPlayer(Player p){
		if(tar==null&&p!=owner)tar=p;
	}
	
	public void update(double delta) {
		if(System.currentTimeMillis()-lastAttack >= attackSpeed){
			dest = new Coordinate(tar.posX, tar.posY);
		}
		if(tar==null){
			for(Object o : Display.currentScreen.objects){
				boolean target = true;
				if(o instanceof CreatureEagle){
					if(((CreatureEagle)o).owner==owner)target=false;
				}
				if(o==owner|!o.canHarm)target=false;
				if(target)tar=o;
			}
		}else{
			if(tar==owner||tar.health<=0)tar=null;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(Bank.eagle, posX, posY, width, height, null);
	}
}
