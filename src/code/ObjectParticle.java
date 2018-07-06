package code;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class ObjectParticle extends Object{
	
	Random rand = new Random();
	Color color;

	public ObjectParticle(int x, int y, Color c) {
		super(x, y, 16, 16);
		this.phys = new PhysicsPlug(this);
		phys.motionX = (rand.nextInt(340)-rand.nextInt(340));
		phys.motionY = (rand.nextInt(340)-rand.nextInt(340));
		this.color = c;
	}

	public void update(double delta) {
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(this.getPosX(), getPosY(), getWidth(), getHeight());
		if(phys.motionX==0||phys.motionY==0)kill();
	}
}
