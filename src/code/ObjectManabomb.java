package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ObjectManabomb extends Object{
	
	String owner = null;
	long start = -1;
	boolean crit = false;
	int val;

	public ObjectManabomb(String o, int x, int y, int w, int h, boolean c, int val) {
		super(x, y, w, h);
		this.crit = c;
		this.owner = o;
		this.val = val;
		this.setCanFall(false);
		this.phys = new PhysicsPlug(this);
	}

	public void update(double delta) {	
		Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
		p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionX/2);
		p.phys.motionY = rand.nextInt(60)-rand.nextInt(60)-(this.phys.motionY/2);
		p.basePhysics = false;
		int s = 8+rand.nextInt(7);
		p.width = s;
		p.height = s;
		Display.currentScreen.objects.add(p);
		if(start > -1){
			if(System.currentTimeMillis()-start >= 300){
				for(int j = 0; j < 100; ++j){
					Particle p1 = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
					p.phys.motionX = rand.nextInt(560)-rand.nextInt(560)-(this.phys.motionX/2);
					p.phys.motionY = rand.nextInt(560)-rand.nextInt(560)-(this.phys.motionY/2);
					p.basePhysics = false;
					int s1 = 14+rand.nextInt(18);
					p1.width = s1;
					p1.height = s1;
					Display.currentScreen.objects.add(p1);
				}
				for(int j = 0; j < 30; ++j){
					Particle p1 = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.NOVA, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
					p.phys.motionX = rand.nextInt(160)-rand.nextInt(160)-(this.phys.motionX/2);
					p.phys.motionY = rand.nextInt(160)-rand.nextInt(160)-(this.phys.motionY/2);
					p.basePhysics = false;
					p.maxTime = 1650;
					int s1 = 180;
					p1.width = s1;
					p1.height = s1;
					Display.currentScreen.objects.add(p1);
				}
				for(int i = 0; i < Display.currentScreen.players.size(); ++i){
					if(Display.currentScreen.players.get(i)!=null){
						Player pl = Display.currentScreen.players.get(i);
						if(pl.getHitbox().intersects(getHitbox())){
							pl.hurt(20+val, crit);
						}
					}
				}
				Display.currentScreen.shake(200, 15);
				this.kill();
			}
		}
	}
	
	public void collideWithPlayer(Player p){
		if(!p.getUsername().equals(owner) && start == -1){
			Display.currentScreen.shake(300, 3);
			start = System.currentTimeMillis();
		}
	}
	
	public Rectangle getHitbox(){
		return start==-1?(new Rectangle(posX, posY, width, height)):new Rectangle(posX-50, posY-50, width+100, height+100);
	}

	public void draw(Graphics g) {
		int s = 0;
		if(start > -1){
			s = (int) ((System.currentTimeMillis()-start) * 100 / 300);
		}
		g.drawImage(Bank.arcane, posX-s/2, posY-s/2, width+s, height+s, null);
		g.setColor(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0.9F));
		g.fillOval(posX-(s+10)/2, posY-(s+10)/2, width+(s+10), height+(s+10));
	}
}
