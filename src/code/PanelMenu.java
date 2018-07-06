package code;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PanelMenu extends PanelBase {
	
	String user = Properties.username;
	int drawX = 0, stage = 0, buttonpop = 0;
	long start = System.currentTimeMillis(), select = 0;
	Font f0 = new Font(Font.MONOSPACED, Font.PLAIN, 24);
	Font f1 = new Font(Font.MONOSPACED, Font.BOLD, 64);
	Font f2 = new Font(Font.MONOSPACED, Font.PLAIN, 24);
	Font f3 = new Font(Font.MONOSPACED, Font.BOLD, 24);
	Font f4 = new Font(Font.MONOSPACED, Font.BOLD, 64);
	Rectangle b1 = null, b2 = null, b3 = null, b4 = null;
	int selbutton = 0;
	Hero selected = Hero.getRandomHero(), left = Hero.getRandomHero(), right = Hero.getRandomHero();
	Rectangle mapbb = null;
	int runes = 0, rotate = 0, lvl = 1;
	String s = null;
	GUIButton play = null, editor, localmulti, heromenu, chievs, campaign, custom, temp, fsupdate, settings, trial;
	Spell[] spells = new Spell[3];
	Image badge = Util.getBadge();
	String splash;
	
	private String URLRead(final String name) {
	    URL url;
		try {
			url = new URL(name);
		    final BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		    final String totalText = in.readLine();
		    in.close();
		    System.out.println(totalText);
		    return totalText;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PanelMenu(){
		if(Analysis.getKey(new File(Bank.path+"data/lock.HB"), "00x0").equalsIgnoreCase("1")){
			Properties.legal = true;
		}
		splash = Util.getSplashText();
		if(!Util.music.playing){
			Util.music = new ClipShell("menu.wav");
			Util.music.loop(-1);
		}
		user = Properties.username;
		this.gridEnabled = false;
		this.renderObj = false;
		this.renderGuis = false;
		Bank.init();
		int last = 0;
		if(Bank.getRawdirDataLine(Bank.path+"data/Core.HB")!=null)
		last = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
		this.runes = last;
		for(int i = 0; i < 3; ++i){
			spells[i] = Spell.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "sp"+(i+1)))];
		}
		//lvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"));
		if(!Properties.legal){
			JOptionPane.showMessageDialog(null, "Sorry, you do not have access to the beta.");
			System.exit(0);
		}
	}
	
	public PanelMenu(int stage){
		if(Analysis.getKey(new File(Bank.path+"data/lock.HB"), "00x0").equalsIgnoreCase("1")){
			Properties.legal = true;
		}
		if(Properties.beta){
			if(!Bank.hasLockVal("achievements.HB", Achievement.beta.id)){
				guis.add(new GUIAchievementNotify(Achievement.beta, 0, 0));
				Bank.setLockVal("achievements.HB", Achievement.beta.id, true);
				Bank.setLockVal("hats.HB", Hat.scales.id, true);
			}
		}
		splash = Util.getSplashText();
		if(!Util.music.playing){
			Util.music = new ClipShell("menu.wav");
			Util.music.loop(-1);
		}
		user = Properties.username;
		this.stage = stage;
		this.gridEnabled = false;
		this.renderObj = false;
		this.renderGuis = false;
		int last = 0;
		if(Bank.getRawdirDataLine(Bank.path+"data/Core.HB")!=null)
		last = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
		this.runes = last;
		Bank.init();
		for(int i = 0; i < 3; ++i){
			spells[i] = Spell.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "sp"+(i+1)))];
		}
		lvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"));
		if(!Properties.legal){
			JOptionPane.showMessageDialog(null, "Sorry, you do not have access to the beta.");
			System.exit(0);
		}
	}
	
	public void onUpdate() {
		if(rotate>5){
			rotate = 0;
		}else{
			rotate++;
		}
		if(stage<=-1){
			//new SpellCast(Spell.getRandomSpell()).cast(new Player(Hero.getRandomHero(), "", r==0?0:Properties.width-30, rand.nextInt(Properties.height)), 1);
			/*Particle p = new Particle(rand.nextInt(Properties.width), rand.nextInt(Properties.height), rand.nextInt(7), new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat()));
			p.basePhysics = true;
			p.phys.motionX = rand.nextInt(200)-rand.nextInt(200);
			p.phys.motionY = rand.nextInt(200)-rand.nextInt(200);
			objects.add(p);*/
			/*int d = rand.nextInt(2);
			ObjectBlock block = new ObjectBlock(d==0?-30:Properties.width, Properties.height/2-(60+rand.nextInt(121)), rand.nextInt(13));
			block.phys.motionX = (d==0?(30+rand.nextInt(100)):-(30+rand.nextInt(100)));
			block.phys.motionY = -rand.nextInt(80)+20;
			Display.currentScreen.objects.add(block);*/
			int s = rand.nextInt(4)+1;
			Particle p = new Particle(rand.nextInt(Properties.width), 0, Particle.SNOW, new Color(255,255,255,156+rand.nextInt(100)));
			p.canDrop = true;
			p.width = s*5;
			p.height = s*5;
			objects.add(p);
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_Q){
			Display.currentScreen = new PanelCredits();
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ESCAPE||e.getKeyCode()==KeyEvent.VK_ENTER){
			if(Properties.beta){
				Util.persistentGuis.add(new GUINotify(Bank.planet, "Welcome to BETA!", "Beta build "+Properties.ver, 0, 0));
			}
			stage=-1;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			if(selGui<4)++selGui;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			if(selGui>0)--selGui;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(selGui==0)selGui=4;
			if(selGui==5)selGui=0;
			if(selGui==4)selGui=5;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(selGui==0)selGui=5;
			if(selGui==5)selGui=4;
			if(selGui==4)selGui=0;
		}
		if(e.getKeyCode()==KeyEvent.VK_M){
			new ClipShell("click.wav").start();
			JFileChooser fc = new JFileChooser(Bank.path+"maps");
			fc.showOpenDialog(null);
			File f = fc.getSelectedFile();
			Properties.map = f.getName().replace(".HB", "");
		}
		if(e.getKeyCode() == KeyEvent.VK_T){
			Display.currentScreen = new PanelLobby();
		}
		if(e.getKeyCode() == KeyEvent.VK_L){
			Bank.client = new GameClient(JOptionPane.showInputDialog("Enter IP"));
			Bank.client.start();
			Packet00Login pkt;
			pkt = new Packet00Login(JOptionPane.showInputDialog("Enter Username"), Properties.selHero.id);
			pkt.write(Bank.client);
		}
		if(e.getKeyCode()==KeyEvent.VK_SEMICOLON){
			StringBuilder sb = new StringBuilder();
			JFileChooser fc = new JFileChooser();
			fc.showOpenDialog(null);
			File f = fc.getSelectedFile();
			try(BufferedReader br = new BufferedReader(new FileReader(f))) {
				for(String line; (line = br.readLine()) != null;) {
					sb.append(line);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				File f1 = new File(Bank.path+"tracks/"+f.getName());
				f1.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f1));
				bw.write(sb.toString());
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void buttonReact(){
		if(selbutton==20){
			try {
				Desktop.getDesktop().browse(new URI("http://www.reddit.com/r/Herobrawl/comments/2i2p0a/hero_brawl_beta_bug_report_thread/"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		if(selbutton==1){
			Util.music.stop();
			PanelPlay pan = new PanelPlay(Properties.map);
			Display.currentScreen = pan;
			Player p = new Player(Properties.selHero, Properties.username, Properties.width/2-15, 250).loadLocalSpells();
			p.phys.fixate = true;
			Display.currentScreen.addPlayer(p, true);
			Player e = new Player(Hero.getRandomHero(), "enemy", rand.nextInt(Properties.width), 250, Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"))).loadRandomSpells();
			e.target = Display.currentScreen.players.get(0);
			Display.currentScreen.addPlayer(e, true);
			e.focus = true;
			p.panel = pan;
			e.panel = pan;
		}
		if(selbutton==3){
			Display.currentScreen = new PanelHeroSelect();
		}
		if(selbutton==2){
			PanelMapCreate pan = new PanelMapCreate(new Grid(750,null),"map",0,0);
			pan.grid = new Grid(750,pan);
			Display.currentScreen = pan;
		}
		if(selbutton==4){
			Display.currentScreen = new PanelAchievement(Properties.selHero);
		}
		if(selbutton==5){
			Display.currentScreen = new PanelSetSelect();
		}
		if(selbutton==6){
			Display.currentScreen = new PanelCustomize();
		}
		if(selbutton==7){
			Display.currentScreen = new PanelSkilltree();
		}
		if(selbutton==8){
			for(int i = 0; i < Hero.all.length; ++i){
				if(Hero.all[i]!=null){
					File f = new File(Bank.path+"chars/"+Hero.all[i].classname+".HB");
					StringBuilder sb = new StringBuilder();
					for(int x = 0; x < 75; ++x)sb.append("0:");
					Analysis.addKeyNoReplace(f, "sp1", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(0).id:0));
					Analysis.addKeyNoReplace(f, "sp2", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(1).id:0));
					Analysis.addKeyNoReplace(f, "sp3", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(2).id:0));
					Analysis.addKeyNoReplace(f, "power", "0");
					Analysis.addKeyNoReplace(f, "fort", "0");
					Analysis.addKeyNoReplace(f, "resto", "0");
					Analysis.addKeyNoReplace(f, "lvl", "1");
					Analysis.addKeyNoReplace(f, "ccl", "0");
					Analysis.addKeyNoReplace(f, "hat", "-1");
					Analysis.addKeyNoReplace(f, "skills", sb.toString());
				}
			}
			JOptionPane.showMessageDialog(null, "File Structure Update Complete\nDebug Successful, Missing Keys Updated.");
		}
		if(selbutton==9){
			Display.currentScreen = new PanelHeroCustomize(Properties.selHero);
		}
		if(selbutton==10){
			Display.currentScreen = new PanelLocalSetup();
		}
		if(selbutton==11){
			if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "haspass"))==1){
				Display.currentScreen = new PanelTrial();
			}else
			guis.add(new GUIConfirm(Properties.width/3, 100, "Pay 80 runes for entry?", "!", 1));
		}
	}
	
	public void buttonReact(int id){
		if(id==8||id==11||id==20){
			selbutton = id;
			buttonReact();
		}else{
			selbutton = id;
			select = System.currentTimeMillis();
		}
	}
	
	public void releaseClick(boolean left){
	}
	
	boolean cracked = false;
	Color sky = new Color(0,145,255,255);

	public void drawScreen(Graphics g) {
		if(stage<=-1){
			g.drawImage(Bank.windowbg, 0, 0, 1920+(Properties.width-1920 > 0 ? Properties.width-1920 : 0), 1080+(Properties.height-1080 > 0 ? Properties.height-1080 : 0), null);
			//g.setColor(sky);
			//g.fillRect(0, 0, Properties.width, Properties.height);
			if(play==null){
				play = new GUIButton("Quick Game", 1, Properties.width/2-150, 300+0, 300, 50).setColor(Color.GREEN);
				trial = new GUIButton("Grand Trial", 11, Properties.width/2-150, 300+75, 300, 50).setColor(Color.MAGENTA);
				campaign = new GUIButton("Campaign", 5, Properties.width/2-150+350, 300+0, 300, 50).setColor(Color.RED);
				custom = new GUIButton("Custom Game", 6, Properties.width/2-150-350, 300+0, 300, 50).setColor(Color.YELLOW);
				localmulti = new GUIButton("Local Multiplayer", 10, Properties.width/2-190, 300+300-80, 380, 50).setColor(Color.PINK);
				editor = new GUIButton("Level Studio", 2, Properties.width/2-150, 300+150, 300, 50).setColor(Color.BLUE);
				heromenu = new GUIButton("Hero Select", 3, Properties.width/2-150, 300+225, 300, 50).setColor(Color.MAGENTA);
				chievs = new GUIButton("Achievements", 4, Properties.width/2-150, 300+300, 300, 50).setColor(Color.WHITE);
				temp = new GUIButton(Bank.bandit_run, Bank.bandit_attack, 7, 300, 20, 50, 80);
				fsupdate = new GUIButton(Bank.custbutton, Bank.custbutton, "Check/Fix File Structure [*Alpha]", 8, Properties.width/2-405, 300+400, 800, 50);
				guis.add(play);
				guis.add(editor);
				guis.add(chievs);
				guis.add(campaign);
				guis.add(custom);
				guis.add(fsupdate);
				guis.add(localmulti);
				guis.add(trial);
				/*b1 = new Rectangle(Properties.width/2-240-50, Properties.height/2-260, 120, 120);
				b2 = new Rectangle(Properties.width/2-120-25, Properties.height/2-260, 120, 120);
				b3 = new Rectangle(Properties.width/2, Properties.height/2-260, 120, 120);
				b4 = new Rectangle(Properties.width/2+120+25, Properties.height/2-260, 120, 120);
				mapbb = new Rectangle(Properties.width/2-150, Properties.height/2-60, 300, 200);*/
			}
			int y = 0;
			if(select>0){
				y = (int) ((System.currentTimeMillis()-select) * (Properties.height/2) / 500);
				if(select+500 <= System.currentTimeMillis()){
					buttonReact();
				}
			}
			g.setFont(f4);
			g.setColor(Color.WHITE);
			g.drawImage(Bank.logo, Properties.width/2-300, -50, 600, 350, null);
			//g.drawString("Hero Brawl 2", Properties.width/2-g.getFontMetrics().stringWidth("Hero Brawl 2")/2, 100);			
			g.setColor(Color.BLACK);
			g.drawImage(Bank.nexus, 320, 20, 120, 120, null);
			g.setColor(Color.WHITE);
			g.setFont(Util.largeNameFont);
			g.drawString(runes+"", 320+60-g.getFontMetrics().stringWidth(runes+"")/2, 95);
			this.renderObjects(g);
			Rectangle hrect = new Rectangle(150-80, 80, 160, 240), crect = new Rectangle(150-90, 350, 180, 50), srect = new Rectangle(150-75, 350+80, 150, 50), lrect = new Rectangle(150-90, 350+160, 180, 50);
			g.drawImage(Bank.sidebar, 0, 0, 300, Properties.height, null);
			g.drawImage(hrect.contains(mousePoint)?Bank.squaregrad_gr:Bank.squaregrad, 150-80-20, 80-25, 160+40, 240+50, null);
			g.drawImage(Bank.heroportrait, 150-80, 80, 160, 240, null);
			g.drawImage(crect.contains(mousePoint)?Bank.squaregrad_gr:Bank.squaregrad, crect.x-15, crect.y-7, crect.width+30, crect.height+14, null);
			g.drawImage(srect.contains(mousePoint)?Bank.squaregrad_gr:Bank.squaregrad, srect.x-15, srect.y-7, srect.width+30, srect.height+14, null);
			g.drawImage(lrect.contains(mousePoint)?Bank.squaregrad_gr:Bank.squaregrad, lrect.x-15, lrect.y-7, lrect.width+30, lrect.height+14, null);
			g.drawImage(Bank.marblebutton, crect.x, crect.y, crect.width, crect.height, null);
			g.drawImage(Bank.marblebutton, srect.x, srect.y, srect.width, srect.height, null);
			g.drawImage(Bank.marblebutton, lrect.x, lrect.y, lrect.width, lrect.height, null);
			Bank.drawSquare(g, crect.x, crect.y, crect.width, crect.height);
			Bank.drawSquare(g, srect.x, srect.y, srect.width, srect.height);
			Bank.drawSquare(g, lrect.x, lrect.y, lrect.width, lrect.height);
			g.setColor(Color.BLACK);
			g.setFont(Util.spellDesc);
			g.drawString("Change Hero", crect.x+crect.width/2-g.getFontMetrics().stringWidth("Change Hero")/2, crect.y+crect.height/2);
			g.drawString("Traits", srect.x+srect.width/2-g.getFontMetrics().stringWidth("Traits")/2, srect.y+srect.height/2);
			g.drawString("Cuztomize", lrect.x+lrect.width/2-g.getFontMetrics().stringWidth("Customize")/2, lrect.y+lrect.height/2);
			g.drawImage(Properties.selHero.portrait, 150-80, 80, 160, 240, null);
			g.drawImage(badge, 150-80, 80+240-40, 40, 40, null);
			Bank.drawSquare(g, 150-80, 80+240-40, 40, 40);
			g.setColor(Util.transparent);
			g.fillRect(150-80, 80+240/2-15, 160, 30);
			g.fillRect(150-80, 80+320/2-10, 160, 20);
			g.setColor(Color.WHITE);
			g.setFont(Util.descTitleFont);
			g.drawString(Properties.selHero.name, 150-g.getFontMetrics().stringWidth(Properties.selHero.name)/2, 80+240/2+5);
			g.setFont(Util.smallDescFont);
			g.drawString("Level "+lvl+" "+Properties.selHero.classname, 150-g.getFontMetrics().stringWidth("Level "+lvl+" "+Properties.selHero.classname)/2, 80+320/2+5);
			Bank.drawSquare(g, 150-80, 80, 160, 240);
			Bank.drawSquare(g, 0, 0, 300, Properties.height);
			for(int i = 0; i < 3; ++i){
				g.drawImage(Bank.marblebutton, 150-60-30+i*60, 580, 60, 60, null);
				g.drawImage(spells[i].sprite, 150-60-30+i*60, 580, 60, 60, null);
				Bank.drawSquare(g, 150-60-30+i*60, 580, 60, 60);
			}
			g.setColor(Color.YELLOW);
			g.setFont(Util.cooldownFont);
			g.drawString(splash, (Properties.width-300)/2+150-g.getFontMetrics().stringWidth(splash)/2, 240);
			if(clicking){
				if(hrect.contains(mousePoint)){
					buttonReact(3);
				}
				if(crect.contains(mousePoint)){
					buttonReact(3);
				}
				if(srect.contains(mousePoint)){
					buttonReact(7);
				}
				if(lrect.contains(mousePoint)){
					buttonReact(9);
				}
			}
			if(select>0){
				int a = (int) ((System.currentTimeMillis()-select) * 250 / 500);
				if(a>255)a=255;
				g.setColor(new Color(0,0,0,a));
				g.fillRect(0, 0, Properties.width, Properties.height);
			}
		}else{
			if(stage==0){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, Properties.width, Properties.height);
				int x = (int) ((System.currentTimeMillis()-start) * Properties.width/2 / 2000);
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, x, Properties.height);
				g.fillRect(Properties.width-x, 0, x, Properties.height);
				if(start+2000 <= System.currentTimeMillis())stage=1;
			}
			if(stage==1){
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Properties.width, Properties.height);
				int logoy = (int) ((System.currentTimeMillis()-2000-start) * (Properties.height/2) / 2000);
				g.setColor(Color.WHITE);
				g.setFont(f0);
				if(start+4000 <= System.currentTimeMillis()){
					g.setFont(f1);
					int l = g.getFontMetrics().stringWidth("Brawl");
					//g.drawString("Hero", Properties.width/2-l-25, Properties.height/2);
					//g.drawString("Brawl", Properties.width/2+25, Properties.height/2);
					g.drawImage(Bank.logo, Properties.width/2-250, Properties.height/2-(350/2), 500, 350, null);
				}else{
					g.drawString("Hero ", Properties.width/2-g.getFontMetrics().stringWidth("Hero "), logoy);
					g.drawString("Brawl", Properties.width/2, Properties.height-logoy);
				}
				if(start+5000 <= System.currentTimeMillis())stage=2;
			}
			if(stage==2){
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Properties.width, Properties.height);
				for(int i = 0; i < objects.size(); ++i){
					objects.get(i).drawOrigin(g);
				}
				g.setFont(f1);
				g.setColor(Color.WHITE);
				int l = g.getFontMetrics().stringWidth("Brawl");
				//g.drawString("Hero", Properties.width/2-l-25, Properties.height/2);
				//g.drawString("Brawl", Properties.width/2+25, Properties.height/2);
				int s = (int) ((System.currentTimeMillis()-5000-start) * (Properties.width-80) / 2000);
				g.drawImage(Bank.logo, Properties.width/2-250, Properties.height/2-(350/2), 500, 350, null);
				//g.setFont(new Font(Font.SERIF, Font.BOLD, );
				//g.setColor(Color.CYAN);
				//g.drawString("2", Properties.width/2-g.getFontMetrics().stringWidth("2")/2, Properties.height/2+100);
				int s1 = Properties.width-s>150?Properties.width-s:150;
				g.drawImage(Bank.num2, Properties.width/2-s1/2, Properties.height/2-150+s1, s1, s1/5*4, null);
				if(start+6500 <= System.currentTimeMillis()&!cracked){
					for(int i = 0; i < 250+rand.nextInt(201); ++i){
						objects.add(new ObjectParticle(Properties.width/2, Properties.height/2+100, rand.nextInt(2)==0?Color.LIGHT_GRAY:Color.GRAY));
					}
					cracked = true;
				}
				if(start+8000 <= System.currentTimeMillis()){
					stage=3;
				}
			}
			if(stage==3){
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Properties.width, Properties.height);
				for(int i = 0; i < objects.size(); ++i){
					objects.get(i).drawOrigin(g);
				}
				g.setColor(Color.WHITE);
				g.setFont(f1);
				g.drawImage(Bank.logo, Properties.width/2-250, Properties.height/2-(350/2), 500, 350, null);
				g.setFont(new Font(Font.SERIF, Font.BOLD, 80));
				g.setColor(Color.CYAN);
				//g.drawString("2", Properties.width/2-g.getFontMetrics().stringWidth("2")/2, Properties.height/2+100);
				g.drawImage(Bank.num2, Properties.width/2-150/2, Properties.height/2, 150, 150/5*4, null);
				g.setColor(Color.WHITE);
				int s = (int) ((System.currentTimeMillis()-8000-start) * (Properties.width*1.5) / 1000);
				g.fillOval(Properties.width/2-s/2, Properties.height/2-s/2, s, s);
				if(start+9000 <= System.currentTimeMillis()){
					if(Properties.beta){
						Util.persistentGuis.add(new GUINotify(Bank.planet, "Welcome to BETA!", "Beta build "+Properties.ver, 0, 0));
					}
					stage=-1;
				}
			}
		}
		this.renderGuis(g);
	}
}
