package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;

public class PanelRewards extends PanelBase {
	
	int runes = 0, time = 700, rotate = 0, stage = 0, exp = 0, last, lastexp, lastlvl, xpdraw = 0;
	long start = System.currentTimeMillis();
	boolean spat = false, spat1 = false, spat2 = false;
	Rectangle confirm = new Rectangle(Properties.width/2-100, Properties.height/2+240, 200, 100);
	
	int dam = -1, heal = -1, tkn = -1;
	
	Image badge = Util.getBadge();

	public PanelRewards(int runes, int exp, int dam, int heal, int tkn) {
		new ClipShell(runes==-1?"scream.wav":"cheer.wav", 2F).start();
		this.dam = dam;
		this.heal = heal;
		this.tkn = tkn;
		this.runes = runes;
		this.exp = exp;	
		last = Util.getRunes();
		lastexp = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "xp"));
		lastlvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"));
		if(lastexp+exp >= Player.getMaxXP(lastlvl)){
			lastexp-=Player.getMaxXP(lastlvl);
			lastlvl++;
			Util.persistentGuis.add(new GUINotify(Bank.spell_brainfry, "Level Up!","Level "+lastlvl, 0, 0));
		}
		if(lastlvl >= 10 &! Bank.hasLockVal("achievements.HB", Achievement.level10.id)){
			Bank.setLockVal("achievements.HB", Achievement.level10.id, true);
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.level10, 0, 0));
		}
		if(lastlvl >= 20 &! Bank.hasLockVal("achievements.HB", Achievement.level20.id)){
			Bank.setLockVal("achievements.HB", Achievement.level20.id, true);
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.level20, 0, 0));
		}
		if(lastlvl >= 50 &! Bank.hasLockVal("achievements.HB", Achievement.level50.id)){
			Bank.setLockVal("achievements.HB", Achievement.level50.id, true);
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.level50, 0, 0));
		}
		if(lastlvl >= 100 &! Bank.hasLockVal("achievements.HB", Achievement.level100.id)){
			Bank.setLockVal("achievements.HB", Achievement.level100.id, true);
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.level100, 0, 0));
		}
		if(lastlvl >= 250 &! Bank.hasLockVal("achievements.HB", Achievement.level250.id)){
			Bank.setLockVal("achievements.HB", Achievement.level250.id, true);
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.level250, 0, 0));
		}
		if(runes>=0)Bank.setContentsRawdir(Bank.path+"data/Core.HB", (last+runes)+"");
		if(exp>=0)Analysis.setKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "xp", ""+(lastexp+exp));
		Analysis.setKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl", ""+(lastlvl));		
		this.renderObj = false;
		int prevh = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "healed"));
		Analysis.setKey(new File(Bank.path+"data/stats.HB"), "healed", (prevh+heal)+"");
		int prevd = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "dmg"));
		Analysis.setKey(new File(Bank.path+"data/stats.HB"), "dmg", (prevd+dam)+"");
		int prevt = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "tkn"));
		Analysis.setKey(new File(Bank.path+"data/stats.HB"), "tkn", (prevt+tkn)+"");
		if(prevh+heal >= 5000){
			if(!Bank.setLockVal("achievements.HB", Achievement.healer.id, true)){
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.healer, 0, 0));
			}
		}
		if(prevd+dam >= 7000){
			if(!Bank.setLockVal("achievements.HB", Achievement.fighter.id, true)){
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.fighter, 0, 0));
			}		
		}
		if(prevt+tkn >= 8500){
			if(!Bank.setLockVal("achievements.HB", Achievement.tank.id, true)){
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.tank, 0, 0));
			}		
		}
	}
	
	public void click(boolean b){
		if(confirm.contains(mousePoint)){	
			Display.currentScreen = new PanelMenu(-1);
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelMenu(-1);
		}
	}

	public void onUpdate() {
		if(rotate>5)rotate=0;
		else rotate++;
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.windowbg,0,0,Properties.width,Properties.height,null);
		g.drawImage(Bank.squaregrad,Properties.width/2-220-40,0,440+80,Properties.height,null);
		g.drawImage(Bank.flag, Properties.width/2-220-400, Properties.height/2-45-70, 400, 100, null);
		g.drawImage(Bank.flagGrey, Properties.width/2-220-400, Properties.height/2-45+40, 400, 100, null);
		g.drawImage(Bank.tknbanner, Properties.width/2-220-400+75, Properties.height/2-40+40, 300, 80, null);
		g.drawImage(Bank.dmgbanner, Properties.width/2-220-400+75, Properties.height/2-40-70, 300, 80, null);
		g.drawImage(Bank.flagGreen, Properties.width/2+220, Properties.height/2-45, 400, 100, null);
		g.drawImage(Bank.healbanner, Properties.width/2+220+25, Properties.height/2-40, 300, 80, null);
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownBold);
		g.drawString(dam+"", Properties.width/2-220-400+75+150-g.getFontMetrics().stringWidth(dam+"")/2-40, Properties.height/2+40-70-5);
		g.drawString(heal+"", Properties.width/2+220+25+150-g.getFontMetrics().stringWidth(heal+"")/2-40, Properties.height/2+40);
		g.drawString(tkn+"", Properties.width/2-220-400+75+150-g.getFontMetrics().stringWidth(tkn+"")/2-40+5, Properties.height/2+40+40-5);
		g.setColor(Color.BLACK);
		g.drawString(dam+"", Properties.width/2-220-400+75+150-g.getFontMetrics().stringWidth(dam+"")/2-40-1, Properties.height/2+40-1-70-5);
		g.drawString(heal+"", Properties.width/2+220+25+150-g.getFontMetrics().stringWidth(heal+"")/2-40-1, Properties.height/2+40-1);
		g.drawString(tkn+"", Properties.width/2-220-400+75+150-g.getFontMetrics().stringWidth(tkn+"")/2-40-1+5, Properties.height/2+40+40-1-5);
		g.drawImage(Bank.sidebar,Properties.width/2-220,0,440,Properties.height,null);
		for(int i = 0; i < objects.size(); ++i){
			if(objects.get(i)!=null){
				objects.get(i).draw(g);
			}
		}
		int shift = 25;
		g.drawImage(Bank.squaregrad, Properties.width/2-80-15, shift-20, 160+30, 240+40, null);
		g.drawImage(Bank.heroportrait, Properties.width/2-80, shift, 160, 240, null);
		g.drawImage(badge, Properties.width/2-80, shift+240-40, 40, 40, null);
		Bank.drawSquare(g, Properties.width/2-80,shift+240-40, 40, 40);
		g.drawImage(Properties.selHero.portrait, Properties.width/2-80, shift, 160, 240, null);
		g.drawImage(Bank.squaregrad, Properties.width/2-110-20, shift+240-4, 220+40, 30+8, null);
		g.setColor(Color.BLACK);
		g.fillRect(Properties.width/2-110, shift+240, 220, 30);
		if(xpdraw < ((lastexp+exp) * 220 / Player.getMaxXP(lastlvl)))++xpdraw;
		if(xpdraw > 220){
			xpdraw -= Player.getMaxXP(lastlvl);
			lastexp -= Player.getMaxXP(lastlvl);
			lastlvl++;
		}
		g.setColor(Util.purple);
		g.fillRect(Properties.width/2-110, shift+240, xpdraw, 30);
		g.setColor(Color.BLACK);
		g.drawRect(Properties.width/2-110, shift+240, 220, 30);
		Bank.drawSquare(g, Properties.width/2-80, shift, 160, 240);
		g.drawImage(Bank.button, Properties.width/2-20, shift+240-20, 40, 40, null);
		g.setColor(Util.transparent_green);
		g.fillRect(Properties.width/2-20, shift+240-20, 40, 40);
		Bank.drawSquare(g, Properties.width/2-20, shift+240-20, 40, 40);
		g.setColor(Color.BLACK);
		g.setFont(Util.descTitleFont);
		g.drawString(lastlvl+"", Properties.width/2-g.getFontMetrics().stringWidth(lastlvl+"")/2, 25+240+5);
		if(System.currentTimeMillis() >= start+time){
			if(!spat){
				for(int i = 0; i < 500; ++i){
					Particle p = new Particle(Properties.width/2, Properties.height/2, Particle.CHARGED, runes>=0?(rand.nextInt(2)==0?Color.YELLOW:Color.CYAN):Color.RED);
					p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
					p.phys.motionY = rand.nextInt(400)-rand.nextInt(400);
					this.objects.add(p);
				}
				spat = true;
			}
			g.drawImage(Bank.nexus, Properties.width/2-80, Properties.height/2-80, 160, 160, null);
			if(stage==0){
				int t = (int) ((System.currentTimeMillis()-(start+time)) * (400) / 600);
				int a = (int) ((System.currentTimeMillis()-(start+time)) * (200) / 600);
				if(a>250)a=250;
				g.setColor(runes>=0?new Color(0,0,0,a):new Color(255,0,0,a));
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 500-t));
				g.drawString(runes>=0?(runes+""):("X"), Properties.width/2-g.getFontMetrics().stringWidth(runes>=0?(runes+""):("X"))/2, Properties.height/2+40);
				if(System.currentTimeMillis()-(start+time) >= 600){
					stage = 1;
				}
			}else{
				g.setColor(runes>=0?Color.WHITE:Color.RED);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
				g.drawString(runes>=0?(runes+""):("X"), Properties.width/2-g.getFontMetrics().stringWidth(runes>=0?(runes+""):("X"))/2, Properties.height/2+40);
				if(exp > 0 &! spat1){
					int t = (int) ((System.currentTimeMillis()-(start+time+600)) * (200) / 600);
					g.setColor(Color.BLACK);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, (240+60)-t));
					g.drawString("+"+exp+" experience!", Properties.width/2-g.getFontMetrics().stringWidth("+"+exp+" experience!")/2, Properties.height/2+200);
					if(System.currentTimeMillis()-(start+time+600) >= 600){
						for(int i = 0; i < 500; ++i){
							Particle p = new Particle(Properties.width/2, Properties.height/4, Particle.CHARGED, Color.GREEN);
							p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
							p.phys.motionY = rand.nextInt(400)-rand.nextInt(400);
							this.objects.add(p);
						}
						spat1 = true;
					}
				}
				if(spat1){
					g.setColor(Util.purple);
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
					g.drawString("+"+exp+" experience!", Properties.width/2-g.getFontMetrics().stringWidth("+"+exp+" experience!")/2, Properties.height/2+200);
					/*if(!spat2){
						int t = (int) ((System.currentTimeMillis()-(start+time+600+600)) * (Properties.width/2-200) / 600);
						int p = lastexp * 400 / Player.getMaxXP(lastlvl);
						g.setColor(Color.GREEN);
						g.fillRect(-400+t, Properties.height/4+200, p, 30);
						g.setColor(Color.BLACK);
						g.drawRect(-400+t, Properties.height/4+200, 400, 30);
						g.fillRect(t, Properties.height/4+200-20, 40, 40);
						g.setColor(Color.GREEN);
						g.setFont(Util.descTitleFont);
						g.drawString(lastlvl+"", t, Properties.height/4+200-20);
						if(System.currentTimeMillis()-(start+time+600*2) >= 600){
							spat2 = true;
						}
					}else{
						int p = lastexp * 400 / Player.getMaxXP(lastlvl);
						g.drawImage(Bank.squaregrad_gr, Properties.width/2-200-30, Properties.height/4+200, 400+60, 30+20, null);
						g.setColor(Color.BLACK);
						g.fillRect(Properties.width/2+200, Properties.height/4+200-20, 400, 30);
						g.setColor(Color.GREEN);
						g.fillRect(Properties.width/2-200, Properties.height/4+200, p, 30);
						g.setColor(Color.BLACK);
						g.drawRect(Properties.width/2-200, Properties.height/4+200, 400, 30);
						//g.fillRect(Properties.width/2+200, Properties.height/4+200-20, 40, 40);
						g.setColor(Color.GREEN);
						g.setFont(Util.descTitleFont);
						//g.drawString(lastlvl+"", Properties.width/2+200, Properties.height/4+200-20);
					}*/
				}
			}
		}else{
			int y = (int) ((System.currentTimeMillis()-start) * (Properties.height/2-80) / time);
			g.drawImage(Bank.nexus, Properties.width/2-80, y, 160, 160, null);
		}
		if(confirm.contains(mousePoint)){
			g.drawImage(Bank.squaregrad_gr, confirm.x-10, confirm.y-5, confirm.width+20, confirm.height+10, null);
		}
		g.drawImage(Bank.confbutton, confirm.x, confirm.y, confirm.width, confirm.height, null);
		g.setFont(Util.cooldownBold);
		g.setColor(Color.WHITE);
		g.drawString("Confirm", confirm.x+confirm.width/2-g.getFontMetrics().stringWidth("Confirm")/2, confirm.y+confirm.height/5*3);
	}
}
