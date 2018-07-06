package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class PanelHeroSelect extends PanelBase {
	
	ArrayList<Hero> heroes = new ArrayList<Hero>();
	Hero selHero = Hero.all[0];
	int buttonSize = 100;
	Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 36);
	Font classFont = new Font(Font.SERIF, Font.PLAIN, 24);
	//Rectangle 
	int power, fort, resto, xp, lvl, rotate = 0, runes;
	Point lastClick = new Point(0,0);
	long clickstart = -1;
	GUIButton confirm = null, mod;
	Rectangle bio = new Rectangle(Properties.width-500+5, 10, 40, 40);


	public PanelHeroSelect(){
		this.selHero = Properties.selHero;
		this.updateStats();
		this.gridEnabled = false;
		this.renderObj = false;
		for(int i = 0 ; i < Hero.all.length; ++i){
			if(Hero.all[i]!=null){
				if(Hero.all[i].starter)
				heroes.add(Hero.all[i]);
			}
		}
		boolean[] unlocked = Bank.produceLockvalArray("heroes.HB");
		for(int i = 0 ; i < unlocked.length; ++i){
			if(unlocked[i]){
				if(Hero.all[i]!=null){
					heroes.add(Hero.all[i]);
				}
			}
		}
		this.runes = Util.getRunes();
	}
	
	public static int getStatCap(int level, int statrank){
		return (int) (statrank==-1?Player.BASE_MAX+level*Player.MAX_INF:statrank==0?Player.BASE_POW+Player.POW_INF*level:statrank==1?Player.BASE_RESTO+Player.RESTO_INF*level:statrank==2?Player.BASE_FORT+Player.FORT_INF*level:level);
	}
	
	public void onUpdate() {
		if(rotate>5)rotate=0;else rotate++;
		if(confirm == null){
			confirm = new GUIButton("Confirm", 1, (Properties.width-500)/2-100-10, Properties.height-150-5, 220, 90).setColor(Color.GREEN);
			mod = new GUIButton("Customize", 2, (Properties.width-500)/2-100-10, 80-45, 220, 90).setColor(Color.CYAN);
			guis.add(confirm);
			guis.add(mod);
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			Hero newhero = heroes.get(heroes.indexOf(selHero)+1);
			if(newhero!=null)selHero = newhero;
			this.updateStats();
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			Hero newhero = heroes.get(heroes.indexOf(selHero)-1);
			if(newhero!=null)selHero = newhero;
			this.updateStats();
		}
	}
	
	public void buttonReact(){
	}
	
	public void confirm(){
		Properties.selHero = selHero;
		Display.currentScreen = new PanelMenu(-1);
	}
	
	public void releaseClick(boolean b){
		lastClick = mousePoint;
	
		if(bio.contains(mousePoint)){
			GUICharBio g = new GUICharBio(selHero.name, Bank.getBio("bio"+selHero.classname+".txt"), Properties.width/2-450, Properties.height/2-350, 900, 700);
			guis.add(g);
		}
		for(int i = 0; i < heroes.size(); ++i){
			Rectangle rect = new Rectangle(20+i*(buttonSize+20)-(i/5)*(buttonSize+20)*5, 200+(i/5)*(buttonSize+20), buttonSize, buttonSize);
			if(rect.contains(mousePoint)){
				selHero = heroes.get(i);
				power = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "power"));
				fort = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "fort"));
				resto = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "resto"));
				xp = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "xp"));
				lvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "lvl"));
			}
		}
	}
	
	public void buttonReact(int id){
		if(id == 1){
			confirm();
		}
		if(id == 2){
			Display.currentScreen = new PanelHeroCustomize(selHero);
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
			new ClipShell("dragonbreath.wav", -2F).start();;
		}
	}
	
	public boolean belowCap(int amt){
		int total = fort+power+resto;
		return amt<0||(total+amt <= getStatCap(lvl, -1));
	}
	
	Color overlay = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0.2F);

	public void drawScreen(Graphics g) {
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
		for(int i = 0; i < heroes.size(); ++i){
			Rectangle rect = new Rectangle(20+i*(buttonSize+20)-(i/5)*(buttonSize+20)*5, 200+(i/5)*(buttonSize+20), buttonSize, buttonSize);
			if(rect.contains(mousePoint)){
				g.drawImage(Bank.squaregrad, rect.x-10, rect.y-5, buttonSize+20, buttonSize+10, null);
			}
			g.drawImage(rect.contains(mousePoint)?Bank.marblebutton:Bank.gradient, rect.x,  rect.y, buttonSize, buttonSize, null);
			g.drawImage(heroes.get(i).portrait, rect.x+buttonSize/2, rect.y, buttonSize/2, buttonSize, null);
			Bank.drawSquare(g, rect.x, rect.y, buttonSize, buttonSize);
			if(selHero==heroes.get(i)){
				g.setColor(Color.YELLOW);
				g.drawRect(rect.x, rect.y, buttonSize, buttonSize);
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(Util.descTitleFont);
		
		for(int i = 0; i < objects.size(); ++i){
			if(objects.get(i)!=null){
				objects.get(i).drawOrigin(g);
			}
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
					Particle part = new Particle(80-30, 80-30, Particle.EXPLODE, Color.RED   );
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
