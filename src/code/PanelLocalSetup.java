package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;

public class PanelLocalSetup extends PanelBase {
	
	Hero h1 = Properties.selHero, h2 = Hero.getRandomHero();
	int level = 20;
	GUIButton start = new GUIButton(Bank.marblebutton, Bank.marblebutton, "Begin", 1, Properties.width/2-150, 580, 300, 100);
	Rectangle hr1 = new Rectangle(Properties.width/2-200, 100, 180, 300), hr2 = new Rectangle(Properties.width/2+20, 100, 180, 300);
	
	public PanelLocalSetup(){
		start.textColor = Color.BLACK;
		guis.add(start);
	}
	
	public void onUpdate() {
	}
	
	public void click(boolean b){
		if(hr1.contains(mousePoint)){
			h1 = Hero.all[Integer.parseInt(JOptionPane.showInputDialog("Enter new hero ID"))];
		}
		
		if(hr2.contains(mousePoint)){
			h2 = Hero.all[Integer.parseInt(JOptionPane.showInputDialog("Enter new hero ID"))];
		}
	}
	
	public void buttonReact(int id){
		if(id==1){
			Util.music.stop();
			PanelPlay pan = new PanelPlay(Properties.map);
			Display.currentScreen = pan;
			Player p = new Player(h1, Properties.username, Properties.width/2-15, 250).loadLocalSpells();
			p.phys.fixate = true;
			Display.currentScreen.addPlayer(p, true);
			Player e = new Player(h2, Properties.username+"1", rand.nextInt(Properties.width), 250, Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"))).loadLocalSpells();
			//e.target = Display.currentScreen.players.get(0);
			e.focus = true;
			e.showHud = true;
			Display.currentScreen.addPlayer(e, true);
			e.setStats(level*2, (int)(level*1.5F), (int)(level*1.5F));
			p.setStats(level*2, (int)(level*1.5F), (int)(level*1.5F));
			e.keySetID = 1;
			p.panel = pan;
			e.panel = pan;
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)Display.currentScreen = new PanelMenu(-1);
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.woodbg, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(Bank.squaregrad, hr1.x-15, hr1.y-20, hr1.width+30, hr1.height+40, null);
		g.drawImage(Bank.squaregrad, hr2.x-15, hr2.y-20, hr2.width+30, hr2.height+40, null);
		g.drawImage(Bank.heroportrait, hr1.x, hr1.y, hr1.width, hr1.height, null);
		g.drawImage(Bank.heroportrait, hr2.x, hr2.y, hr2.width, hr2.height, null);
		g.drawImage(h1.portrait, hr1.x, hr1.y, hr1.width, hr1.height, null);
		g.drawImage(h2.portrait, hr2.x, hr2.y, hr2.width, hr2.height, null);
		g.setFont(Util.cooldownFont);
		g.setColor(Color.WHITE);
		g.drawString("Player 1", hr1.x, hr1.y-55);
		g.drawString("Player 2", hr2.x, hr2.y-55);
		g.setFont(Util.largeNameFont);
		g.setColor(Color.BLACK);
		g.drawString("Base level "+level, Properties.width/2-g.getFontMetrics().stringWidth("Base level "+level)/2-2, 480-2);
		g.setColor(Color.WHITE);
		g.drawString("Base level "+level, Properties.width/2-g.getFontMetrics().stringWidth("Base level "+level)/2, 480);
	}
}
