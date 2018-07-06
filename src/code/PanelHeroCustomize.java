package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class PanelHeroCustomize extends PanelBase {
	
	ArrayList<Hero> heroes = new ArrayList<Hero>();
	Hero selHero = Hero.all[0];
	int buttonSize = 60;
	Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 36);
	Font classFont = new Font(Font.SERIF, Font.PLAIN, 24);
	//Rectangle 
	int power, fort, resto, xp, lvl, rotate = 0, runes, selSpell = -1;
	//Rectangle fort1, fort0, power1, power0, heal1, heal0;
	Rectangle bio;
	Point lastClick = new Point(0,0);
	long clickstart = -1, start = System.currentTimeMillis();
	Spell sp1, sp2, sp3;
	Rectangle sp1rect = new Rectangle(200, 30, 100, 100), sp2rect = new Rectangle(320, 30, 100, 100), sp3rect = new Rectangle(440, 30, 100, 100);
	Spell lastSpell = null;
	GUIButton confirm = null;
	boolean dropped = true;
	Rectangle newspell;
	
	public PanelHeroCustomize(Hero h){
		this.selHero = h;
		this.updateStats();
		this.gridEnabled = false;
		this.renderObj = false;
		this.runes = Util.getRunes();
		File f = new File(Bank.path+"chars/"+h.classname+".HB");
		sp1 = Spell.all[Integer.parseInt(Analysis.getKey(f, "sp1"))];
		sp2 = Spell.all[Integer.parseInt(Analysis.getKey(f, "sp2"))];
		sp3 = Spell.all[Integer.parseInt(Analysis.getKey(f, "sp3"))];
		for(int i = 0; i < 50; ++i){
			Bank.setLockVal("hats.HB", i, true);
		}
		newspell = new Rectangle(20+selHero.spells.size()*(buttonSize+20)-(selHero.spells.size()/5)*(buttonSize+20)*5, 200+(selHero.spells.size()/5)*(buttonSize+20), buttonSize, buttonSize);
	}
	
	public int getStatCap(int level, int statrank){
		return (int) (statrank==-1?Player.BASE_MAX+level*Player.MAX_INF:statrank==0?Player.BASE_POW+Player.POW_INF*level:statrank==1?Player.BASE_RESTO+Player.RESTO_INF*level:statrank==2?Player.BASE_FORT+Player.FORT_INF*level:level);
	}
	
	public void onUpdate() {
		if(confirm==null){
			confirm = new GUIButton("Confirm", 1, (Properties.width-500)/2-100-10, Properties.height-150-5, 220, 90).setColor(Color.GREEN);
			guis.add(confirm);
		}
	}
	
	public void keyPressed(KeyEvent e){
	}
	
	public void buttonReact(int id){
		if(id==1){
			confirm();
		}
		if(id==2){
			guis.add(new GUIHatSelect(selHero));
		}
	}
	
	public void confirm(){
		Properties.selHero = selHero;
		File f = new File(Bank.path+"chars/"+selHero.classname+".HB");
		Analysis.setKey(f, "sp1", sp1.id+"");
		Analysis.setKey(f, "sp2", sp2.id+"");
		Analysis.setKey(f, "sp3", sp3.id+"");
		Display.currentScreen = new PanelHeroSelect();
	}
	
	public void releaseClick(boolean b){
		lastClick = mousePoint;
		if(selSpell>-1){
			if(sp1rect.contains(mousePoint)&&Spell.all[selSpell].minlvl<=lvl){
				sp1 = Spell.all[selSpell];
			}
			if(sp2rect.contains(mousePoint)&&Spell.all[selSpell].minlvl<=lvl){
				sp2 = Spell.all[selSpell];
			}
			if(sp3rect.contains(mousePoint)&&Spell.all[selSpell].minlvl<=lvl){
				sp3 = Spell.all[selSpell];
			}
			selSpell = -1;
		}
		if(bio.contains(mousePoint)){
			GUICharBio g = new GUICharBio(selHero.name, Bank.getBio("bio"+selHero.classname+".txt"), Properties.width/2-450, Properties.height/2-350, 900, 700);
			guis.add(g);
		}
		Polygon hatbutton = new Polygon();
		hatbutton.xpoints = new int[]{Properties.width-500+3, Properties.width-500+3, Properties.width-500+150};
		hatbutton.ypoints = new int[]{Properties.height/4-150, Properties.height/4-3, Properties.height/4-3};
		hatbutton.npoints = 3;
		if(hatbutton.contains(mousePoint)){
			buttonReact(2);
		}
		if(newspell.contains(mousePoint)){
			Display.currentScreen = new PanelSpellCreate();
		}
	}
	
	public void updateStats(){
		power = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "power"));
		fort = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "fort"));
		resto = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "resto"));
		xp = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "xp"));
		lvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "lvl"));
		runes = Util.getRunes();
	}
	
	public void upgrade(String stat, int amt){
		if(belowCap(amt)){
			int lastcore = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
			if(lastcore>=amt){
				System.out.println("Modding "+stat+" by "+amt);
				int newcore = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"))-amt;
				Bank.setContentsRawdir(Bank.path+"data/Core.HB", ""+newcore);
				File f = new File(Bank.path+"chars/"+selHero.classname+".HB");
				int newval = Integer.parseInt(Analysis.getKey(f, stat))+amt;
				Analysis.setKey(f, stat, newval+"");
			}else{
				System.out.println("Insufficient [nexus rune] to mod "+stat+" by "+amt);
			}
		}else{
			new ClipShell("dragonbreath.wav", -2F).start();
		}
	}
	
	public boolean belowCap(int amt){
		int total = fort+power+resto;
		return amt<0||(total+amt <= getStatCap(lvl, -1));
	}
	
	public boolean unlocked(Spell s){
		return s.minlvl <= lvl;
	}

	public void drawScreen(Graphics g) {
		/*if(!dropped){
			int x = (int) ((System.currentTimeMillis()-start) * Properties.width / 1500);
			g.drawImage(Bank.woodbg, x-Properties.width, 0, Properties.width, Properties.height, null);
			if(x >= Properties.width)dropped=true;
		}*/
		if(dropped){
			g.drawImage(Bank.woodbg, 0, 0, Properties.width, Properties.height, null);
			g.setColor(Util.transparent_dark);
			g.fillRect(Properties.width-500, 0, 500, Properties.height);
			g.drawImage(selHero.portrait, Properties.width-500, Properties.height/4, 500, Properties.height, null);
			Bank.drawSquare(g, Properties.width-500, 0, 500, Properties.height);
			g.setColor(Util.transparent);
			g.drawImage(Bank.woodboard, Properties.width-500, 0, 500, Properties.height/4, null);
			g.fillRect(Properties.width-500, 0, 500, Properties.height/4);
			Bank.drawSquare(g, Properties.width-500, 0, 500, Properties.height/4);
			g.setColor(Color.WHITE);
			g.setFont(nameFont);
			g.drawString(selHero.name, (Properties.width-250)-g.getFontMetrics().stringWidth(selHero.name)/2, 40);
			g.setFont(classFont);
			g.drawString("Level "+lvl+" "+selHero.classname, (Properties.width-250)-g.getFontMetrics().stringWidth("Level "+lvl+" "+selHero.classname)/2, 70);
			g.setFont(Util.cooldownBold);
			g.setColor(Color.WHITE);
			for(int i = 0; i < heroes.size(); ++i){
				Rectangle rect = new Rectangle(20+i*(buttonSize+20)-(i/5)*(buttonSize+20)*5, 200+(i/5)*(buttonSize+20), buttonSize, buttonSize);
				if(rect.contains(mousePoint)){
					g.drawImage(Bank.squaregrad, rect.x-10, rect.y-5, buttonSize+20, buttonSize+10, null);
				}
				g.drawImage(Bank.heroportrait, rect.x,  rect.y, buttonSize, buttonSize, null);
				g.drawImage(heroes.get(i).portrait, rect.x+buttonSize/2, rect.y, buttonSize/2, buttonSize, null);
				Bank.drawSquare(g, rect.x, rect.y, buttonSize, buttonSize);
			}
			g.setColor(Color.WHITE);
			g.setFont(Util.descTitleFont);
			g.drawImage(sp1.sprite, sp1rect.x, sp1rect.y, sp1rect.width, sp1rect.height, null);
			g.drawImage(sp2.sprite, sp2rect.x, sp2rect.y, sp2rect.width, sp2rect.height, null);
			g.drawImage(sp3.sprite, sp3rect.x, sp3rect.y, sp3rect.width, sp3rect.height, null);
			for(int i = 0; i < selHero.spells.size(); ++i){
				Rectangle rect = new Rectangle(20+i*(buttonSize+20)-(i/5)*(buttonSize+20)*5, 200+(i/5)*(buttonSize+20), buttonSize, buttonSize);
				Spell spell = selHero.spells.get(i);
				if(rect.contains(mousePoint)){
					if(unlocked(spell)){
						g.drawImage(Bank.squaregrad_gr, rect.x-10, rect.y-10, rect.width+20, rect.height+20, null);
					}else{
						g.drawImage(Bank.squaregrad, rect.x-10, rect.y-10, rect.width+20, rect.height+20, null);
					}
					if(clicking&&selSpell==-1){
						selSpell = spell.id;
						lastSpell = spell;
					}
				}
				g.drawImage(spell.sprite, rect.x, rect.y, rect.width, rect.height, null);
				if(!unlocked(spell)){
					g.setColor(Util.transparent);
					g.fillRect(rect.x, rect.y, rect.width, rect.height);
					g.setColor(Color.WHITE);
					g.drawString(spell.minlvl+"", rect.x+rect.width/2-g.getFontMetrics().stringWidth(spell.minlvl+"")/2, rect.y+rect.height/2);
					g.drawImage(Bank.lock, rect.x, rect.y, rect.width, rect.height, null);
				}
			}
			g.drawImage(Bank.rawplus, newspell.x, newspell.y, newspell.width, newspell.height, null);
			if(newspell.contains(mousePoint))g.drawImage(Bank.squaregrad_gr, newspell.x-10, newspell.y-10, newspell.width+20, newspell.height+20, null);
			if(selSpell>-1){
				g.drawImage(Bank.squaregrad_gr, mousePoint.x-40-15, mousePoint.y-40-15, 80+30, 80+30, null);
				g.drawImage(Spell.all[selSpell].sprite, mousePoint.x-40, mousePoint.y-40, 80, 80, null);
			}
			if(lastSpell!=null){
				g.setColor(Util.transparent_dark);
				g.fillRect(Properties.width-500, Properties.height/4, 500, Properties.height/4*3);
				g.setColor(Color.WHITE);
				g.drawImage(lastSpell.sprite, Properties.width-500+3, Properties.height/4+3, 100, 100, null);
				g.setFont(Util.spellNameFont);
				g.drawString(lastSpell.name, Properties.width-500+120, Properties.height/4+40);
				g.setFont(Util.cooldownFont);
				g.setColor(Color.GREEN);
				g.drawString("Cooldown: "+lastSpell.cooldown, Properties.width-500+10, Properties.height/4+140);
				g.drawString("Base Strength: "+lastSpell.baseval, Properties.width-500+10, Properties.height/4+180);
				g.setColor(lvl >= lastSpell.minlvl ? Color.GREEN : Color.RED);
				g.drawString("Min Level: "+lastSpell.minlvl, Properties.width-500+10, Properties.height/4+220);
				g.setColor(Color.WHITE);
				g.drawString("Stat Type: ", Properties.width-500+10, Properties.height/4+260);
				g.setColor(Color.GREEN);
				g.drawString((lastSpell.statType==0?"Power":"Resto"), Properties.width-500+10+g.getFontMetrics().stringWidth("Stat Type: "), Properties.height/4+260);
				g.setColor(Color.YELLOW);
				g.drawString("Description", Properties.width-500+10, Properties.height/4+300);
				g.setColor(Color.WHITE);
				g.setFont(Util.spellDesc);
				if(lastSpell.desc!=null)
				for(int i = 0; i < lastSpell.desc.length; ++i){
					g.drawString(lastSpell.desc[i], Properties.width-500+10, Properties.height/4+340+30*i);
				}
			}
			g.setFont(Util.descTitleFont);

			for(int i = 0; i < objects.size(); ++i){
				if(objects.get(i)!=null){
					objects.get(i).drawOrigin(g);
				}
			}
			Polygon hatbutton = new Polygon();
			hatbutton.xpoints = new int[]{Properties.width-500+3, Properties.width-500+3, Properties.width-500+150};
			hatbutton.ypoints = new int[]{Properties.height/4-150, Properties.height/4-3, Properties.height/4-3};
			hatbutton.npoints = 3;
			g.setColor(Color.BLACK);
			g.fillPolygon(hatbutton);
			g.drawImage(Bank.hattext, Properties.width-500+2, Properties.height/4-150-2, 150, 150, null);
			if(!hatbutton.contains(mousePoint)){
				g.setColor(Util.transparent);
				g.fillPolygon(hatbutton);
			}else{
				g.setColor(Color.WHITE);
				g.drawPolygon(hatbutton);
			}
			if(clickstart > -1){
				boolean reduction = false;
				if(reduction){
					int x = (int) ((System.currentTimeMillis()-clickstart) * Util.xDist(lastClick, new Point(80,80)) / 300);
					int y = (int) ((System.currentTimeMillis()-clickstart) * Util.yDist(new Point(Properties.width-100, Properties.height/2), new Point(80,80)) / 300);
					g.drawImage(Bank.nexus, reduction?(Properties.width-100-x):(80-30+x), reduction?(Properties.height/2+80-y):(80-30+y), 60, 60, null);
					for(int i = 0; i < 3+rand.nextInt(7); ++i){
						Particle part = new Particle(reduction?(Properties.width-100-x):(80-30+x), reduction?(Properties.height/2+80-y):(80-30+y), Particle.CHARGED, new Color(190, 0, 255, 255));
						part.phys.motionX = rand.nextInt(2) == 0 ? -50 : 50;
						part.phys.motionY = rand.nextInt(2) == 0 ? -50 : 50;
						part.maxTime = 100;
						objects.add(part);
					}
				}
				if(!reduction){
					for(int i = 0; i < 30+rand.nextInt(7); ++i){
						Particle part = new Particle(80-30, 80-30, Particle.EXPLODE, Color.RED);
						part.phys.motionX = rand.nextInt(2) == 0 ? -50 : 50;
						part.phys.motionY = rand.nextInt(2) == 0 ? -50 : 50;
						int s = 20+rand.nextInt(41);
						part.width = s;
						part.height = s;
						part.maxTime = 100;
						objects.add(part);
					}
				}
				this.updateStats();
				//if(System.currentTimeMillis()-clickstart > 300){
					long a = ((System.currentTimeMillis()-clickstart) * 100 / 600);
					if(a > 255)a = 255;
					int re = 0, gr = 0, bl = 0;
					g.setColor(new Color(re,gr,bl,(int)a));
					g.fillRect(Properties.width-500, Properties.height/4, 500, Properties.height-Properties.height/4);
				//}
				if(System.currentTimeMillis()-clickstart > 600)clickstart=-1;
			}
			int w = xp * 300 / (Player.getMaxXP(lvl));
			g.setColor(new Color(200, 0, 255, 255));
			g.fillRect(Properties.width-250-150, 80, w, 20);
			g.setColor(Color.BLACK);
			g.drawRect(Properties.width-250-150, 80, 300, 20);
			g.drawImage(Bank.nexus, 0, 0, 160, 160, null);
			g.setColor(Color.WHITE);
			g.setFont(Util.largeNameFont);
			g.drawString(runes+"", 80-g.getFontMetrics().stringWidth(runes+"")/2, 100);
		}
	}
}
