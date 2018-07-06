package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class GUISpellVisualSelect extends GUI {
	
	ArrayList<SpellVisual> avail = new ArrayList<SpellVisual>();
	PanelSpellCreate p;
	int buttonSize, page = 0, pageSize = 8*4;
	Rectangle back, forward;
	
	public GUISpellVisualSelect(PanelSpellCreate p) {
		super(Properties.width/2-Properties.width/(8), Properties.height/2-Properties.height/(8), Properties.width/4, Properties.height/4);
		this.p = p;
		buttonSize = w/(8);
		this.h = buttonSize*4+30;
		for(int i = 1; i < GridBlock.all.length; ++i){
			if(i<SpellVisual.all.length && SpellVisual.all[i]!=null)
			avail.add(SpellVisual.all[i]);
		}
		back = new Rectangle(x,y+h-30,30,30);
		forward = new Rectangle(x+w-30,y+h-30,30,30);
	}

	public void tick(double delta) {
	}
	
	public void click(boolean b){
		if(back.contains(Display.currentScreen.mousePoint)){
			if(page>0)page--;
			new ClipShell("click.wav", 2F).start();
		}
		if(forward.contains(Display.currentScreen.mousePoint)){
			page++;
			new ClipShell("click.wav", 2F).start();
		}
		if(new Rectangle(x,y,buttonSize,buttonSize).contains(Display.currentScreen.mousePoint)){
			confirm(0);
		}
		for(int j = 0; j < avail.size(); ++j){
			int i = j+1;
			Rectangle rect = new Rectangle(x+i*(buttonSize)-(i/(w/buttonSize))*(buttonSize)*(w/buttonSize), y+(i/(w/buttonSize))*(buttonSize), buttonSize, buttonSize);
			if(rect.contains(Display.currentScreen.mousePoint)){
				confirm(avail.get(j).id);
			}
		}
	}
	
	public void confirm(int i){
		p.spv = SpellVisual.all[i];
		((GUIButton)p.guis.get(p.guis.indexOf(p.vfc))).text = SpellVisual.all[i].name; 
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
		//g.setColor(Util.blood);
		//g.fillRect(rect1.x,  rect1.y, buttonSize, buttonSize);
		g.setColor(Color.WHITE);
		g.setFont(Util.spellDesc);
		g.drawString("Air", x+buttonSize/2-g.getFontMetrics().stringWidth("Air")/2, y+buttonSize/2);
		Bank.drawSquare(g, rect1.x, rect1.y, buttonSize, buttonSize);
		if(rect1.contains(Display.currentScreen.mousePoint)){
			g.setColor(Util.transparent_white);
			g.fillRect(rect1.x,  rect1.y, buttonSize, buttonSize);
		}
		g.drawImage(back.contains(Display.currentScreen.mousePoint)?Bank.backHover:Bank.back, back.x, back.y, back.width, back.height, null);
		g.drawImage(forward.contains(Display.currentScreen.mousePoint)?Bank.forwardHover:Bank.forward, forward.x, forward.y, forward.width, forward.height, null);
		for(int j = page*pageSize; j < (page+1)*pageSize; ++j){
			int i = j+1;
			if(avail.size() > j){
				Rectangle rect = new Rectangle(x+i*(buttonSize)-(i/(w/buttonSize))*(buttonSize)*(w/buttonSize), y+(i/(w/buttonSize))*(buttonSize), buttonSize, buttonSize);
				g.drawImage(rect.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, rect.x,  rect.y, buttonSize, buttonSize, null);
				//g.setColor(Util.setAlpha(Util.yellow, 200));
				//g.fillRect(rect.x,  rect.y, buttonSize, buttonSize);
				g.drawImage(avail.get(j).tex, rect.x+10,  rect.y+10, buttonSize-20, buttonSize-20, null);
				Bank.drawSquare(g, rect.x, rect.y, buttonSize, buttonSize);
				if(rect.contains(Display.currentScreen.mousePoint)){
					g.setColor(Util.transparent_white);
					g.fillRect(rect.x,  rect.y, buttonSize, buttonSize);
				}
			}
		}
	}
}
