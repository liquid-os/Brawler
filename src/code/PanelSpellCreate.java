package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

public class PanelSpellCreate extends PanelBase {
	
	Hero selHero = Hero.all[0];
	int buttonSize = 60;
	static byte PROJECTILE = 0, MELEE = 1, SELF = 2;
	Font nameFont = new Font(Font.MONOSPACED, Font.BOLD, 36);
	Font classFont = new Font(Font.SERIF, Font.PLAIN, 24);
	Rectangle confirm = new Rectangle((Properties.width-500)/2-100-10, Properties.height-150-5, 220, 90);
	//Rectangle 
	int xp, lvl, runes;
	Point lastClick = new Point(0,0);
	long clickstart = -1;
	SpellVisual spv = SpellVisual.brain;
	ArrayList<SpellEffect> spx = new ArrayList<SpellEffect>();
	int power, type = 0, vis, cooldown, force, effect, backdraft;
	Projectile proj = new Projectile(null, Properties.width/2, Properties.height/2, 170, 100, 1, Bank.firenova);
	GUITextBox name = new GUITextBox(Bank.custbutton, Bank.woodboard, "name", 20, 30, 450, 60).setLabel("Name");
	GUITextBox damage = new GUITextBox(Bank.custbutton, Bank.woodboard, "3", 20, 140, 220, 60).setLabel("Damage");
	GUITextBox launchspeed = new GUITextBox(Bank.custbutton, Bank.woodboard, "160", 20+230, 140, 220, 60).setLabel("Launch Speed");
	GUITextBox lashbox = new GUITextBox(Bank.custbutton, Bank.woodboard, "0", 500, 30, 220, 60).setLabel("Backlash");
	GUITextBox effectbox = new GUITextBox(Bank.custbutton, Bank.woodboard, "0", 500+230, 30, 220, 60).setLabel("Effect");
	GUITextBox w = new GUITextBox(Bank.custbutton, Bank.woodboard, "200", 500, 140, 220, 60).setLabel("Width");
	GUITextBox h = new GUITextBox(Bank.custbutton, Bank.woodboard, "80", 500+230, 140, 220, 60).setLabel("Height");
	GUIButton vfc = new GUIButton(Bank.custbutton, Bank.woodboard, spv.name, 4, 1000, 30, 220, 60);
	GUIButton conf = new GUIButton("Submit", 1, 20, 220, 120, 60).setColor(Color.GREEN);
	GUIButton rdm = new GUIButton("Randomize", 2, 20+140, 220, 200, 60).setColor(Color.MAGENTA);
	GUIButton typerotate = new GUIButton(Bank.custbutton, Bank.custbutton, "Projectile", 3, 1000, 140, 200, 60);

	public PanelSpellCreate(){
		this.updateStats();
		this.gridEnabled = false;
		//this.renderObj = false;
		this.runes = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
		proj.phys.execute = false;
		proj.visEffect = 2;
		proj.phys.motionX = 100;
		objects.add(proj);
		guis.add(name);
		guis.add(damage);
		guis.add(lashbox);
		guis.add(effectbox);
		guis.add(launchspeed);
		guis.add(w);
		guis.add(h);
		guis.add(vfc);
		guis.add(rdm);
		guis.add(conf);
		guis.add(typerotate);
	}
	
	public int getStatCap(int level, int statrank){
		return (int) (statrank==0?1.5*level:statrank==1?2*level:statrank==2?2.5*level:level);
	}
	
	public void updateProj(){
		objects.remove(proj);
		int mx = Integer.parseInt(launchspeed.text);
		int wth = Integer.parseInt(w.text);
		int ht = Integer.parseInt(h.text);
		int eff = Integer.parseInt(effectbox.text);
		int dmg = Integer.parseInt(damage.text);
		proj = new Projectile(null, Properties.width/2-wth/2, Properties.height/2-ht/2, wth, ht, dmg, spv.tex);
		proj.phys.motionX = mx;
		//proj.visEffect = spv.;
		proj.effect = eff;
		proj.phys.execute = false;
		objects.add(proj);
	}
	
	public void onUpdate() {
	}
	
	public void keyPressed(KeyEvent e){
	}
	
	public void buttonReact(int id){
		if(id==1){
			System.out.println("clicking");
			updateProj();
		}
		if(id==2){
			w.text = (rand.nextInt(200)+20)+"";
			h.text = (rand.nextInt(170)+10)+"";
			spv = SpellVisual.getRandomUnlocked();
			effectbox.text = (rand.nextInt(50))+"";
			launchspeed.text = (80+rand.nextInt(200))+"";
			this.updateProj();
		}
		if(id==3){
			if(type==0){
				typerotate.text = "Close Range";
				type=1;
			}
			if(type==1){
				typerotate.text = "Self Cast";
				type=2;
			}
			if(type==2){
				typerotate.text = "Projectile";
				type=0;
			}
		}
		if(id==4){
			GUISpellVisualSelect g = new GUISpellVisualSelect(this);
			guis.add(g);
		}
	}
	
	public void confirm(){
		Properties.selHero = selHero;
		File f = new File(Bank.path+"chars/"+selHero.classname+".HB");
		Display.currentScreen = new PanelHeroCustomize(selHero);
	}
	
	public void releaseClick(boolean b){
		lastClick = mousePoint;
		if(confirm.contains(mousePoint)){
			confirm();
		}
	}
	
	public void updateStats(){
		xp = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "xp"));
		lvl = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+selHero.classname+".HB"), "lvl"));
		runes = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
	}
	
	public boolean unlocked(Spell s){
		return true;
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.hillsRoll, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(Bank.woodbg, 0, -400, Properties.width, 700, null);
		Bank.drawSquare(g, 0, 0, Properties.width, 300);
	}
}
