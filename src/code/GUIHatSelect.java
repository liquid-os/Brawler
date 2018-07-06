package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

public class GUIHatSelect extends GUI {
	
	ArrayList<Hat> avail = new ArrayList<Hat>();
	Hero hero;
	int buttonSize, page = 0, pageSize = 5*4;
	
	public GUIHatSelect(Hero h) {
		super(Properties.width/2-Properties.width/4, 20, Properties.width/2, Properties.height/2);
		this.hero = h;
		buttonSize = w/5;
		this.h = buttonSize*4;
		String s = Bank.getRawdirDataLine(Bank.path+"data/hats.HB");
		String[] sa = s.split("0");
		for(int i = 0; i < sa.length; ++i){
			if(sa[i].equals("1"))avail.add(Hat.all[i]);
		}
	}

	public void tick(double delta) {
	}
	
	public void click(boolean b){
		if(new Rectangle(x,y,buttonSize,buttonSize).contains(Display.currentScreen.mousePoint)){
			confirm(-1);
		}
		for(int j = 0; j < avail.size(); ++j){
			int i = j+1;
			Rectangle rect = new Rectangle(x+i*(buttonSize)-(i/(w/buttonSize))*(buttonSize)*5, y+(i/5)*(buttonSize), buttonSize, buttonSize);
			if(rect.contains(Display.currentScreen.mousePoint)){
				confirm(avail.get(j).id);
			}
		}
	}
	
	public void confirm(int i){
		Analysis.setKey(new File(Bank.path+"chars/"+hero.classname+".HB"), "hat", i+"");
		new ClipShell("punch.wav", 3F).start();
		Display.currentScreen.guis.remove(this);
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent);
		//g.fillRect(0, 0, Properties.width, Properties.height);
		//g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		Rectangle rect1 = new Rectangle(x, y, buttonSize, buttonSize);
		g.drawImage(rect1.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, x, y, buttonSize, buttonSize, null);
		g.setColor(Util.blood);
		g.fillRect(rect1.x,  rect1.y, buttonSize, buttonSize);
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownFont);
		g.drawString("No Hat", x+buttonSize/2-g.getFontMetrics().stringWidth("No Hat")/2, y+buttonSize/2);
		Bank.drawSquare(g, rect1.x, rect1.y, buttonSize, buttonSize);
		if(rect1.contains(Display.currentScreen.mousePoint)){
			g.setColor(Util.transparent_white);
			g.fillRect(rect1.x,  rect1.y, buttonSize, buttonSize);
		}
		for(int j = page*pageSize; j < (page+1)*pageSize; ++j){
			int i = j+1;
			if(avail.size() > j){
				if(avail.get(j)!=null){
					Rectangle rect = new Rectangle(x+i*(buttonSize)-(i/(w/buttonSize))*(buttonSize)*5, y+(i/5)*(buttonSize), buttonSize, buttonSize);
					g.drawImage(Bank.marblebutton, rect.x,  rect.y, buttonSize, buttonSize, null);
					g.drawImage(avail.get(j).sprite, rect.x+10,  rect.y+10, buttonSize-20, buttonSize-20, null);
					Bank.drawSquare(g, rect.x, rect.y, buttonSize, buttonSize);
					if(rect.contains(Display.currentScreen.mousePoint)){
						g.setColor(Util.transparent_white);
						g.fillRect(rect.x,  rect.y, buttonSize, buttonSize);
					}
				}
			}
		}
	}
}
