package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ObjectHPOrb extends Object{
	
	String blacklist = null;
	int val;

	public ObjectHPOrb(String o, int x, int y, int w, int h, int val) {
		super(x, y, w, h);
		this.val = val;
		this.blacklist = o;
		this.setCanFall(true);
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {	
	}
	
	public void collideWithPlayer(Player p){
		if(blacklist==null||p.getUsername().equals(blacklist)){}else{
			p.healRaw(val, false);
			this.kill();
		}
	}
	
	public Rectangle getHitbox(){
		return new Rectangle(posX, posY, width, height);
	}

	public void draw(Graphics g) {
		g.drawImage(Bank.rawplus, posX, posY, width, height, null);
	}
}
