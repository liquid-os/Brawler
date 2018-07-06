package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PanelLevelSelect extends PanelBase{
	
	int x = 20, stage = 0, buttonSize = 50, buttonsPerLine = Properties.width/60-1;
	int count = 200, h, sel = -1;
	Color red = new Color(255,0,0,100), green = new Color(0,255,0,100);
	String title;
	String[] desc;
	int level;
	ArrayList<Challenger> opponents;
	Levelset set = null;
	List<Level> levels;
	
	public PanelLevelSelect(Hero hero, Levelset set){
		this.set = set;
		levels = set.set;
		stage = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+hero.classname+".HB"), "cpl"+set.id));
		if(stage<0)stage=0;
		h = buttonSize+20+((20+(count/buttonsPerLine)*(buttonSize+10)));
	}

	public void onUpdate() {
	}
	
	public void click(boolean left){
		if(opponents!=null){
			for(int i = 0; i < opponents.size(); ++i){
				Rectangle rect = new Rectangle(20+i*100, h+130+desc.length*20+40, 80, 80);
				if(rect.contains(mousePoint)){
					guis.add(new GUIInspect(opponents.get(i), Properties.width/2, Properties.height/2));
				}
			}
		}
		for(int i = 0; i < levels.size(); ++i){
			Rectangle rect = new Rectangle(x+i*(buttonSize+10)-(i/buttonsPerLine)*(buttonSize+10)*(buttonsPerLine), 20+(i/buttonsPerLine)*(buttonSize+10), buttonSize, buttonSize);
			if(rect.contains(mousePoint)){
				select(i);
			}
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelSetSelect();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
			select(sel);
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(sel>0)select(sel-1);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(sel<count)select(sel+1);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			if(sel-buttonsPerLine>=0)select(sel-buttonsPerLine);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(sel+buttonsPerLine<=levels.size())select(sel+buttonsPerLine);
		}
	}
	
	public void confirm(){
		Util.music.stop();
		Display.currentScreen = new PanelCampaign(levels.get(sel), set);
		Player p = new Player(Properties.selHero, Properties.username, levels.get(sel).spawnX-((GridPanel)Display.currentScreen).scrollX, levels.get(sel).spawnY-((GridPanel)Display.currentScreen).scrollY).loadLocalSpells();
		p.phys.fixate = true;
		Display.currentScreen.addPlayer(p, false);
	}
	
	public void select(int i){
		if(sel==i){confirm();}else{
			sel = i;
			title = levels.get(i).name;
			desc = levels.get(i).desc;
			level = levels.get(i).level;
			opponents = levels.get(i).opponents;
		}
	}

	public void drawScreen(Graphics g) {
		for(int i = 0; i < levels.size(); ++i){
			Rectangle rect = new Rectangle(x+i*(buttonSize+10)-(i/buttonsPerLine)*(buttonSize+10)*(buttonsPerLine), 20+(i/buttonsPerLine)*(buttonSize+10), buttonSize, buttonSize);
			g.drawImage(rect.contains(mousePoint)?Bank.buttonHover:Bank.button, rect.x, rect.y, rect.width, rect.height, null);
			g.setColor(i>stage?Util.transparent:Color.WHITE);
			g.setFont(Util.spellDesc);
			g.drawString((i+1)+"", rect.x+rect.width/2-g.getFontMetrics().stringWidth((i+1)+"")/2, rect.y+rect.height/2);
			Bank.drawSquare(g, rect.x, rect.y, rect.width, rect.height);
			if(i>stage){
				g.setColor(Util.transparent);
				g.fillRect(rect.x, rect.y, rect.width, rect.height);
				//g.drawImage(Bank.lock, rect.x, rect.y, rect.width, rect.height, null);
			}
		}
		g.drawImage(Bank.gradient, 0, h, Properties.width, Properties.height-h, null);
		g.setColor(Util.transparent_dark);
		g.fillRect(0, h, Properties.width, Properties.height-h);
		if(sel>-1){
			g.setColor(Color.WHITE);
			g.setFont(Util.largeNameFont);
			g.drawString(title, 20, h+g.getFontMetrics().getHeight());
			g.setFont(Util.spellDesc);
			g.setColor(Color.YELLOW);
			g.drawString("Level "+(sel+1), 20, h+100);
			g.setColor(Color.WHITE);
			for(int i = 0; i < desc.length; ++i){
				g.drawString(desc[i], 20, h+130+i*20);
			}
			g.setFont(Util.dialogBold);
			g.drawString("Major Opponents", 20, h+130+desc.length*20+20);
			if(opponents!=null){
				for(int i = 0; i < opponents.size(); ++i){
					Rectangle rect = new Rectangle(20+i*100, h+130+desc.length*20+40, 80, 80);
					g.drawImage(Bank.gradient, rect.x, rect.y, rect.width, rect.height, null);
					g.drawImage(opponents.get(i).icon, rect.x, rect.y, rect.width, rect.height, null);
					Bank.drawSquare(g, rect.x, rect.y, rect.width, rect.height);
					if(rect.contains(mousePoint)){
						g.setColor(Util.transparent_dark);
						g.fillRect(rect.x, rect.y, rect.width, rect.height);
						g.setColor(Color.WHITE);
						g.setFont(Util.descFont);
						g.drawString("Click to", rect.x+rect.width/2-g.getFontMetrics().stringWidth("Click to")/2, rect.y+40);
						g.drawString("Inspect", rect.x+rect.width/2-g.getFontMetrics().stringWidth("Inspect")/2, rect.y+50);
					}
				}
			}
		}
		Bank.drawSquare(g, 0, h, Properties.width, Properties.height-h);
	}
}
