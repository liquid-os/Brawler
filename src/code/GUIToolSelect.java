package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;


public class GUIToolSelect extends GUI {
	
	PanelMapCreate p;
	int buttonSize, page = 0, pageSize = 8*4;
	Rectangle pencil, drag;
	
	public GUIToolSelect(PanelMapCreate p) {
		super(Properties.width-300, 250, 60, 140);
		this.p = p;
		buttonSize = 60;
		pencil = new Rectangle(x,y+20,60,60);
		drag = new Rectangle(x,y+20+60,60,60);
	}

	public void tick(double delta) {
	}
	
	public void click(boolean b){
		if(pencil.contains(Display.currentScreen.mousePoint)){
			confirm(0);
		}
		if(drag.contains(Display.currentScreen.mousePoint)){
			confirm(1);
		}
	}
	
	public void confirm(int i){
		p.tool = i;
		new ClipShell("punch.wav", 3F).start();
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent);
		//g.fillRect(0, 0, Properties.width, Properties.height);
		//g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		g.drawImage(pencil.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, pencil.x, pencil.y, buttonSize, buttonSize, null);
		//g.setColor(Util.blood);
		//g.fillRect(rect1.x,  rect1.y, buttonSize, buttonSize);
		g.setColor(Color.WHITE);
		g.setFont(Util.spellDesc);
		g.drawImage(pencil.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, pencil.x, pencil.y, buttonSize, buttonSize, null);
		g.drawImage(Bank.cursorPencil, pencil.x, pencil.y, buttonSize, buttonSize, null);
		Bank.drawSquare(g, pencil.x, pencil.y, buttonSize, buttonSize);
		g.drawImage(drag.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, drag.x, drag.y, buttonSize, buttonSize, null);
		g.drawImage(Bank.cursorDrag, drag.x, drag.y, buttonSize, buttonSize, null);
		Bank.drawSquare(g, drag.x, drag.y, buttonSize, buttonSize);
		g.setColor(Util.transparent_white);
		g.fillRect(x, y+20+(p.tool==1?buttonSize:0), buttonSize, buttonSize);
	}
}
