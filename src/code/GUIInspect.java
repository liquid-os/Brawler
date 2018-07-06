package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GUIInspect extends GUI {
	
	Challenger chal;
	Rectangle s1, s2, s3, s4, s5, s6;
	Spell spell;

	public GUIInspect(Challenger c, int w, int h) {
		super(Properties.width/2-w/2, Properties.height/2-h/2, w, h);
		chal = c;
		s1 = new Rectangle(x+h/2-80, y+h-20-50, 50, 50);
		s2 = new Rectangle(x+h/2-25, y+h-20-50, 50, 50);
		s3 = new Rectangle(x+h/2+30, y+h-20-50, 50, 50);
		s4 = new Rectangle(x+h/2+30+55, y+h-20-50, 50, 50);
		s5 = new Rectangle(x+h/2+30+55*2, y+h-20-50, 50, 50);
		s6 = new Rectangle(x+h/2+30+55*3, y+h-20-50, 50, 50);
		this.showSelection = false;
	}

	public void tick(double delta) {
	}
	
	public void click(boolean b){
		if(new Rectangle(x+w-200, y, 200, 40).contains(Display.currentScreen.mousePoint)){
			Display.currentScreen.guis.remove(this);
		}
		if(s1.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[0];
		}
		if(s2.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[1];
		}
		if(s3.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[2];
		}
		if(s4.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[3];
		}
		if(s5.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[4];
		}
		if(s6.contains(Display.currentScreen.mousePoint)){
			spell = chal.spells[5];
		}
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(Bank.button, x, y, w, h, null);
		g.setColor(Util.transparent_dark);
		g.fillRect(x, y, w, h);
		g.setColor(Util.transparent);
		g.fillRect(x, y, w, 40);
		g.fillRect(x+w-200, y, 200, h);
		g.setColor(Color.YELLOW);
		g.setFont(Util.dialogFont);
		g.drawString(chal.name, x+w/2-g.getFontMetrics().stringWidth(chal.name)/2, y+25);
		g.drawImage(s1.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s1.x, s1.y, s1.width, s1.height, null);
		g.drawImage(s2.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s2.x, s2.y, s2.width, s2.height, null);
		g.drawImage(s3.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s3.x, s3.y, s3.width, s3.height, null);
		if(chal.spells[3]!=null)g.drawImage(s4.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s4.x, s4.y, s4.width, s4.height, null);
		if(chal.spells[4]!=null)g.drawImage(s5.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s5.x, s5.y, s5.width, s6.height, null);
		if(chal.spells[5]!=null)g.drawImage(s6.contains(Display.currentScreen.mousePoint)?Bank.buttonHover:Bank.button, s6.x, s6.y, s6.width, s6.height, null);
		g.drawImage(chal.spells[0].sprite, s1.x, s1.y, s1.width, s1.height, null);
		g.drawImage(chal.spells[1].sprite, s2.x, s2.y, s2.width, s2.height, null);
		g.drawImage(chal.spells[2].sprite, s3.x, s3.y, s3.width, s3.height, null);
		if(chal.spells[3]!=null)g.drawImage(chal.spells[3].sprite, s4.x, s4.y, s4.width, s4.height, null);
		if(chal.spells[4]!=null)g.drawImage(chal.spells[4].sprite, s5.x, s5.y, s5.width, s5.height, null);
		if(chal.spells[5]!=null)g.drawImage(chal.spells[5].sprite, s6.x, s6.y, s6.width, s6.height, null);
		g.setFont(Util.descTitleFont);
		g.setColor(chal.fort>0?Color.GREEN:Color.RED);
		g.drawString("Fortitude: "+chal.fort, x+w-200+5, y+55);
		g.setColor(chal.pow>0?Color.GREEN:Color.RED);
		g.drawString("Power: "+chal.pow, x+w-200+5, y+75);
		g.setColor(chal.resto>0?Color.GREEN:Color.RED);
		g.drawString("Resto: "+chal.resto, x+w-200+5, y+95);
		g.setColor(Color.WHITE);
		if(spell!=null){
			g.drawImage(spell.sprite, x+w-200, y+100, 70, 70, null);
			g.setFont(Util.descTitleFont);
			g.drawString(spell.name, x+w-130, y+130);
			g.setFont(Util.smallDescFont);
			g.drawString("Cooldown: "+spell.cooldown, x+w-200+5, y+185);
			g.drawString("Base Strength: "+spell.baseval, x+w-200+5, y+200);
			g.setColor(Color.YELLOW);
			if(spell.desc!=null){
				for(int i = 0; i < spell.desc.length; ++i){
					g.drawString(spell.desc[i], x+w-200+5, y+215+i*15);
				}
			}
		}
		g.setColor(Util.blood);
		g.fillRect(x+w-200, y, 200, 40);
		g.setColor(Color.WHITE);
		g.drawString("Close", x+w-100-g.getFontMetrics().stringWidth("Close")/2, y+20);
		if(new Rectangle(x+w-200, y, 200, 40).contains(Display.currentScreen.mousePoint)){
			g.drawRect(x+w-200, y, 200, 40);
		}
		g.drawLine(x+w-200, y+100, x+w, y+100);
	}
}
