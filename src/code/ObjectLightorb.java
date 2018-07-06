package code;

import java.awt.Color;
import java.awt.Graphics;

public class ObjectLightorb extends Object {

	Object caster;
	boolean holy = false;
	
	public ObjectLightorb(Object caster, int x, int y, int w, int h, boolean holy) {
		super(x, y, w, h);
		this.caster = caster;
		this.solid = false;
		this.phys = new PhysicsPlug(this);
		this.holy = holy;
		width = 40;
		height = 40;
	}

	public void update(double delta) {
		if(phys.motionX==0||phys.motionY==0){
			phys.motionX = (rand.nextInt(100)-rand.nextInt(100));
			phys.motionY = (rand.nextInt(100)-rand.nextInt(100));
		}
		int s = 3+rand.nextInt(4);
		Particle p = new Particle(posX+width/2-s/2, posY+height/2-s/2, Particle.CHARGED, holy?Color.YELLOW:Util.purple);
		p.motionX = (rand.nextInt(200)-rand.nextInt(200));
		p.motionY = (rand.nextInt(200)-rand.nextInt(200));
		p.maxTime = 80;
		Display.currentScreen.objects.add(p);
	}

	public void draw(Graphics g) {
		//g.drawImage(holy?Bank.lightorb:Bank.shadoworb, posX, posY, width, height, null);
	}
}
