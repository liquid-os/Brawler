package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectSmokecloud extends Object{
	
	Object owner = null;
	long start = System.currentTimeMillis();
	boolean crit = false;
	int val;
	ArrayList<Object> hit = new ArrayList<Object>();

	public ObjectSmokecloud(Object o, int x, int y, int w, int h, boolean c, int val) {
		super(x, y, w, h);
		this.crit = c;
		this.owner = o;
		this.val = val;
		this.setCanFall(false);
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {	
		for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
			Object o = Display.currentScreen.objects.get(i);
			if(o instanceof Particle || o instanceof ObjectBlock){}else{
				if(!hit.contains(o)&o!=owner&&o!=this){
					if(o.getHitbox().intersects(this.getHitbox())){
						o.addEffect(new Effect(20, EffectType.cloud, (Player) owner, 6000));
						hit.add(o);
					}
				}
			}
		}
		for(int i = 0; i < Display.currentScreen.players.size(); ++i){
			if(Display.currentScreen.players.get(i)!=null){
				Player pl = Display.currentScreen.players.get(i);
				if(pl.getHitbox().intersects(getHitbox())){
					collideWithPlayer(pl);
				}
			}
		}
		if(System.currentTimeMillis()-start >= 800){
			kill();
		}
	}
	
	public void collideWithPlayer(Player p){
		if(!hit.contains(p)&p!=owner){
			if(p.canHarm&&p.getHitbox().intersects(this.getHitbox())){
				p.addEffect(new Effect(20, EffectType.cloud, (Player) owner, 3250));
				hit.add(p);
			}
		}
	}
	
	public int getSize(){
		return 200 - (int) ((System.currentTimeMillis()-start) * 200 / 300);
	}
	
	public Rectangle getHitbox(){
		int s = this.getSize();
		return new Rectangle(posX+(s/2), posY+(s/2), width-s, height-s);
	}

	public void draw(Graphics g) {
		int s = 0;
		s = getSize();
		g.drawImage(Bank.cloud, posX+s/2, posY+s/2, width-s, height-s, null);
	}
}
