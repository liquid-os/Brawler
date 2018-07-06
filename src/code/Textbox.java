package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Textbox {
	
	String[] txt;
	int maxTime, w, h;
	long start = System.currentTimeMillis();
	Font font = Util.spellDesc;

	public Textbox(String[] txt, int maxTime) {
		this.txt = txt;
		this.maxTime = maxTime;
		this.w = 20;
		this.h = (Display.frame.getGraphics().getFontMetrics(font).getHeight()*(txt.length));
		for(String s : txt){
			int l = (Display.frame.getGraphics().getFontMetrics(font).stringWidth(s));
			if(l > w){
				w = l+20;
			}
		}
	}

	public void draw(Object o, Graphics g){
		g.setColor(Util.transparent);
		g.fillRect(o.posX+o.width/2-this.w/2, o.posY-h*(1+o.chats.indexOf(this))-60, w, h);
		g.setFont(Util.upgradeFont);
		g.setColor(Color.WHITE);
		for(int i = 0; i < txt.length; ++i){
			String s = txt[i];
			g.drawString(s, o.posX+o.width/2-g.getFontMetrics().stringWidth(s)/2, o.posY-h*(1+o.chats.indexOf(this))+20+i*25-60);
		}
		if(System.currentTimeMillis()-start >= maxTime){
			o.chats.remove(this);
		}
	}
}
