package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class PanelAchievement extends PanelBase{
	
	int x = 20, stage = 0, buttonSize = 50, buttonsPerLine = Properties.width/60-1;
	int count = 200, h, sel = -1;
	Color red = new Color(255,0,0,100), green = new Color(0,255,0,100);
	String title;
	String[] desc;
	Image img;
	boolean[] locks = new boolean[Achievement.all.length];
	Rectangle back, forward, set;
	Image badge = Bank.heart;
	
	public PanelAchievement(Hero hero){
		int c = 0;
		String s = Bank.getRawdirDataLine(Bank.path+"data/achievements.HB");
		String[] ind = s.split("0");
		for(int i = 0; i < Achievement.all.length; ++i){
			locks[i] = ind[i].equals("1");
			if(Achievement.all[i]!=null)c++;
		}
		count = c;
		h = buttonSize+20+((20+(count/buttonsPerLine)*(buttonSize+10)));
		back = new Rectangle(20, h+20, 50, 40);
		set = new Rectangle(20+70, h+20, 250, 40);
		int badgeid = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "badge"));
		if(badgeid!=-1){
			badge = Achievement.all[badgeid].img;
		}
	}

	public void onUpdate() {
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_SPACE){
			confirm();
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			select(sel+1);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			select(sel-1);
		}
	}
	
	public void click(boolean left){
		if(back.contains(mousePoint)){
			confirm();
		}
		if(set.contains(mousePoint)){
			if(locks[sel]){
				Analysis.setKey(new File(Bank.path+"data/stats.HB"), "badge", sel+"");
				int badgeid = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "badge"));
				badge = Achievement.all[badgeid].img;
			}
		}
		for(int i = 0; i < count; ++i){
			Rectangle rect = new Rectangle(x+i*(buttonSize+10)-(i/buttonsPerLine)*(buttonSize+10)*(buttonsPerLine), 20+(i/buttonsPerLine)*(buttonSize+10), buttonSize, buttonSize);
			if(rect.contains(mousePoint)){
				select(i);
			}
		}
	}
	
	public void confirm(){
		Display.currentScreen = new PanelMenu(-1);
	}
	
	public void select(int i){
		new ClipShell("click.wav", 2f);
		if(sel==i){confirm();}else{
			if(i>=0&&i<=count){
				sel = i;
				title = Achievement.all[i].name;
				desc = Achievement.all[i].desc;
				img = Achievement.all[i].img;
				back = new Rectangle(20, h+130+desc.length*20, 50, 40);
				set = new Rectangle(20+70, h+130+desc.length*20, 250, 40);
			}
		}
	}

	public void drawScreen(Graphics g) {
		for(int i = 0; i < count; ++i){
			Rectangle rect = new Rectangle(x+i*(buttonSize+10)-(i/buttonsPerLine)*(buttonSize+10)*(buttonsPerLine), 20+(i/buttonsPerLine)*(buttonSize+10), buttonSize, buttonSize);
			g.drawImage(rect.contains(mousePoint)?Bank.buttonHover:Bank.button, rect.x, rect.y, rect.width, rect.height, null);
			g.drawImage(Bank.gradient, rect.x, rect.y, rect.width, rect.height, null);
			g.drawImage(Achievement.all[i].img, rect.x, rect.y, rect.width, rect.height, null);
			Bank.drawSquare(g, rect.x, rect.y, rect.width, rect.height);
			if(locks[i]==false){
				g.setColor(Util.transparent);
				g.fillRect(rect.x, rect.y, rect.width, rect.height);
				g.drawImage(Bank.lock, rect.x, rect.y, rect.width, rect.height, null);
			}
		}
		g.drawImage(Bank.gradient, 0, h, Properties.width, Properties.height-h, null);
		g.setColor(Util.transparent_dark);
		g.fillRect(0, h, Properties.width, Properties.height-h);
		if(sel>-1){
			g.drawImage(img, 0, h, 100, 100, null);
			g.setColor(Color.WHITE);
			g.setFont(Util.largeNameFont);
			g.drawString(title, 120, h+g.getFontMetrics().getHeight()-20);
			g.setFont(Util.spellDesc);
			g.setColor(locks[sel]?Color.GREEN:Color.RED);
			g.drawString(locks[sel]?"Achieved!":"Locked", 120, h+80);
			g.setColor(Color.WHITE);
			for(int i = 0; i < desc.length; ++i){
				g.drawString(desc[i], 20, h+130+i*20);
			}
		}
		g.drawImage(back.contains(mousePoint)?Bank.backHover:Bank.back, back.x, back.y, back.width, back.height, null);
		g.drawImage(set.contains(mousePoint)?Bank.buttonHover:Bank.button, set.x, set.y, set.width, set.height, null);
		Bank.drawSquare(g, set.x, set.y, set.width, set.height);
		g.setColor(set.contains(mousePoint)?Color.BLACK:Color.WHITE);
		g.setFont(Util.descFont);
		g.drawString("Set as Account Badge", set.x+set.width/2-g.getFontMetrics().stringWidth("Set as Account Badge")/2, set.y+set.height-set.height/2);
		g.drawImage(badge, 30+g.getFontMetrics().stringWidth("Your badge looks like this: "), set.y+set.height+10, 30, 30, null);
		g.setColor(Color.WHITE);
		g.drawString("Your badge looks like this: ", 20, set.y+set.height+30);
		Bank.drawSquare(g, 0, h, Properties.width, Properties.height-h);
	}
}
