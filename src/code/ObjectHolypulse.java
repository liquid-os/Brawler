package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectHolypulse extends Object{
	
	Object owner = null;
	long start = System.currentTimeMillis();
	boolean crit = false, corrupted = false;
	int val;
	ArrayList<Object> hit = new ArrayList<Object>();
	int maxW = 500;

	public ObjectHolypulse(Object o, int x, int y, int s, boolean c, int val) {
		super(x, y, 20, 20);
		this.maxW = s;
		this.corrupted = c;
		this.owner = o;
		this.val = val;
		this.setCanFall(false);
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {	
		for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
			Object o = Display.currentScreen.objects.get(i);
			if(o instanceof ObjectSandsigil){}else{
				if(o!=null){
					if(!hit.contains(o)&o!=owner){
						if(o.canHarm&&o.getHitbox().intersects(this.getHitbox())){
							if(corrupted)o.heal(val, crit);
							else o.hurt(val, crit, null);
							hit.add(o);
						}
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
		if(System.currentTimeMillis()-start >= 1500){
			kill();
		}
	}
	
	public void collideWithPlayer(Player p){
		if(!hit.contains(p)){
			if(p!=owner&&p.getHitbox().intersects(this.getHitbox())){
				if(corrupted)p.heal(val, crit);
				else p.hurt(val, crit, null);				
				hit.add(p);
				for(int i = 0; i < 20; ++i){
					Particle pa = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.LIQUID, Color.YELLOW);
					pa.phys.motionX = rand.nextInt(200)-rand.nextInt(200);
					pa.phys.motionY = rand.nextInt(200)-rand.nextInt(200);
					Display.currentScreen.objects.add(pa);
				}
			}
		}
	}
	
	public int getSize(){
		return (int) ((System.currentTimeMillis()-start) * maxW / 1500);
	}
	
	public Rectangle getHitbox(){
		int s = this.getSize();
		return new Rectangle(posX-s/2, posY-s/2, width+s, height+s);
	}

	public void draw(Graphics g) {
		int s = 0;
		s = getSize();
		g.drawImage(corrupted?Bank.blueNova:Bank.holyNova, posX-s/2, posY-s/2, width+s, height+s, null);
	}
}
