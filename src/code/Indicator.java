package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Indicator extends Object {
	
	String s = "0";
	int size = 20, speed = -5, dir = rand.nextInt(2);
	Color c = Color.YELLOW;

	public Indicator(int x, int y, String s) {
		super(x, y, 1, 1);
		this.s = s;
		this.solid = false;
		setCanFall(false);
	}
	
	public Indicator(int x, int y, String s, Color c) {
		super(x, y, 1, 1);
		this.s = s;
		this.c = c;
		this.solid = false;
		setCanFall(false);
	}
	
	public Indicator setSize(int size, boolean crit){
		if(crit)this.size = size;
		return this;
	}
	
	public boolean canDespawn() {
		return true;
	}
	
	int count(){
		int ret = 0;
		for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
			if(Display.currentScreen.objects.get(i) instanceof Indicator){
				++ret;
			}
		}
		return ret;
	}

	public void update(double delta) {
		posY-=speed;
		if(dir>-1)++speed;
		if(posY <= -80)this.kill();
		if(dir==0)this.moveRight(2);
		if(dir==1)this.moveLeft(2);
	}

	public void draw(Graphics g) {
		g.setFont(new Font(Font.DIALOG, 0, size+20-speed));
		g.setColor(Color.BLACK);
		g.drawString(s, this.posX-1-g.getFontMetrics().stringWidth(s)/2, this.posY-1);
		g.setColor(c);
		g.drawString(s, this.posX-g.getFontMetrics().stringWidth(s)/2, this.posY);
	}
}
