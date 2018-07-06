package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GUIKey extends GUI {
	
	Font font = Util.cooldownFont, hoverFont = Util.cooldownBold;
	int reactID = 0;
	int key = 0;
	String keydesc;
	Image img = null, hover = null;
	
	public GUIKey(String txt, int id, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.reactID = id;
		this.keydesc = txt;
		this.showSelection = false;
	}

	public void tick(double delta) {
	}
	
	public void releaseClick(boolean l){
		if(this.getRect().contains(Display.currentScreen.mousePoint)){
			new ClipShell("click.wav", 3F).start();
			confirm();
		}
	}
	
	public void keyReleased(KeyEvent e){
		key = e.getKeyCode();
		Display.currentScreen.selGui = -1;
	}
	
	public void confirm(){
		Display.currentScreen.buttonReact(reactID);
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(Bank.key, x, y, w, h, null);
		g.drawImage(Bank.marblebutton, x+w+10, y, 200, h, null);
		Bank.drawSquare(g, x+w+10, y, 200, h);
		g.setColor(Color.BLACK);
		g.setFont(Util.cooldownFont);
		String s = new KeyEvent(Display.frame, key, 0, 0, 0).getKeyChar()+"";
		g.drawString(s, x+w/2-g.getFontMetrics().stringWidth(s)/2, y+h-h/4);
		g.drawString(keydesc, x+w+15, y+h-h/4);
	}
}
