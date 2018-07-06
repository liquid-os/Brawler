package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectPsychicpulse extends Object{
	
	Object owner = null;
	long start = System.currentTimeMillis();
	boolean crit = false;
	int val, maxSize = 0, time = 300;
	ArrayList<Object> hit = new ArrayList<Object>();
	Image img = Bank.nova;

	public ObjectPsychicpulse(Object o, int x, int y, int w, int h, boolean c, int val) {
		super(x, y, w, h);
		this.crit = c;
		this.owner = o;
		this.val = val;
		this.setCanFall(false);
		this.maxSize = w;
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {	
		for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
			Object o = Display.currentScreen.objects.get(i);
			if(!hit.contains(o)&o!=owner){
				if(o.canHarm&&o.getHitbox().intersects(this.getHitbox())){
					o.hurt(val, crit, null);
					hit.add(o);
					for(int x = 0; x < 20; ++x){
						Particle pa = new Particle(o.posX+o.width/2, o.posY+o.height/2, Particle.LIQUID, new Color(100, 0, 220, 100));
						pa.phys.motionX = rand.nextInt(200)-rand.nextInt(200);
						pa.phys.motionY = rand.nextInt(200)-rand.nextInt(200);
						Display.currentScreen.objects.add(pa);
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
		if(System.currentTimeMillis()-start >= 300){
			kill();
		}
	}
	
	public void collideWithPlayer(Player p){
		if(!hit.contains(p)&p!=owner){
			if(p.canHarm&&p.getHitbox().intersects(this.getHitbox())){
				p.hurt(val, crit, null);
				hit.add(p);
				for(int i = 0; i < 20; ++i){
					Particle pa = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.LIQUID, new Color(100, 0, 220, 100));
					pa.phys.motionX = rand.nextInt(200)-rand.nextInt(200);
					pa.phys.motionY = rand.nextInt(200)-rand.nextInt(200);
					Display.currentScreen.objects.add(pa);
				}
			}
		}
	}
	
	public int getSize(){
		return maxSize - (int) ((System.currentTimeMillis()-start) * maxSize / time);
	}
	
	public Rectangle getHitbox(){
		int s = this.getSize();
		return new Rectangle(posX+(s/2), posY+(s/2), width-s, height-s);
	}

	public void draw(Graphics g) {
		int s = 0;
		s = getSize();
		g.drawImage(img, posX+s/2, posY+s/2, width-s, height-s, null);
	}
}
