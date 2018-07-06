package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class GUIAchievementNotify extends GUI {
	
	long start = -1;
	int destY = 0, maxTime = 5000, fallTime = 600;
	Color color = Color.WHITE, textColor = Color.white, titleColor = Color.GREEN;
	String name;
	Image icon;

	public GUIAchievementNotify(Achievement a, int x, int y) {
		super(x, 0, 0, 80);
		int count = 0;
		for(GUI gui : Util.persistentGuis){
			if(gui instanceof GUINotify || gui instanceof GUIAchievementNotify){
				++count;
			}
		}
		this.destY = count*h;
		this.setColor(Util.transparent_dark);
		start = System.currentTimeMillis();
		icon = a.img;
		name = a.name;
		w = 300;
		if(Display.frame.p.getGraphics().getFontMetrics(Util.descTitleFont).stringWidth("Achievement Get!")+100 > w)w = Display.frame.p.getGraphics().getFontMetrics(Util.descTitleFont).stringWidth("Achievement Get!")+100;
		int nameLength = Display.frame.getGraphics().getFontMetrics(Util.descTitleFont).stringWidth(a.name)+100;
		if(nameLength > w)w = nameLength;
		showSelection = false;
	}

	public void tick(double delta) {
	}
	
	public void click(boolean b){
	}
	
	public GUIAchievementNotify setColor(Color c){
		color = new Color(c.getRed(),c.getGreen(),c.getBlue(),100);
		return this;
	}
	
	public GUIAchievementNotify setColor(int r, int g, int b){
		color = new Color(r,g,b,100);
		return this;
	}

	public void draw(Graphics g, int x, int y) {
		if(System.currentTimeMillis()-start >= maxTime){
			Util.persistentGuis.remove(this);
		}
		int newy = (int) ((System.currentTimeMillis()-start) * destY / fallTime);
		if(newy>destY)newy=destY;
		y = newy;
		g.drawImage(icon, x, y, h, h, null);
		g.drawImage(Bank.button, x+h, y, w-h, h, null);
		g.setColor(color);
		g.fillRect(x+h, y, w-h, h);
		g.setColor(Color.BLACK);
		g.drawRect(x+h, y, w-h, h);
		g.setColor(Color.GREEN);
		g.setFont(Util.descTitleFont);
		g.drawString("Achievement Get!", x+h+5, y+20);
		g.setColor(Color.WHITE);
		g.drawString(name, x+h+5, y+40);
		g.setColor(Util.transparent_white);
	}
}
