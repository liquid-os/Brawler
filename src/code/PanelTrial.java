package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;

public class PanelTrial extends PanelBase {
	
	GUIButton start = new GUIButton("Begin", 1, Properties.width/2-125, Properties.height/4*3, 250, 60).setColor(Color.GREEN);
	GUIButton claim = new GUIButton("Claim Rewards", 2, Properties.width/2-125, Properties.height/4*3, 250, 60).setColor(Color.CYAN);
	Player next = null;
	int level = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"));
	Hero rnd = Hero.getRandomHero();
	SpellCast sp1, sp2, sp3;
	int losses = 0, wins = 0;
	String name = getName();
	boolean avail = true;
	
	public PanelTrial() {
		if(!Bank.hasLockVal("achievements.HB", Achievement.asyouare.id)){
			Util.persistentGuis.add(new GUIAchievementNotify(Achievement.asyouare, 0, 0));
			Bank.setLockVal("achievements.HB", Achievement.asyouare.id, true);
		}
		guis.add(start);
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "losses"))!=-1){
			losses = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "losses"));
		}
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "wins"))!=-1){
			wins = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "wins"));
		}
		readName();
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "hero"))==-1){
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "hero", rnd.id+"");
		}else{
			rnd = Hero.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "hero"))];
		}
		next = new Player(rnd, "enemy", 400, 100);
		if(spellsValid()){
			next.loadSpells(sp1.spell, sp2.spell, sp3.spell);
		}else{
			next.loadRandomSpells();
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp1", next.spells.get(0).spell.id+"");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp2", next.spells.get(1).spell.id+"");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp3", next.spells.get(2).spell.id+"");
			sp1 = next.spells.get(0);
			sp2 = next.spells.get(1);
			sp3 = next.spells.get(2);
		}
		next.setStats((int) (30 + rand.nextInt(10)), (int) (30 + rand.nextInt(10)), (int) (30 + rand.nextInt(10)));
		if(wins>=8&&losses<=0){
			if(!Bank.hasLockVal("achievements.HB", Achievement.aced.id)){
				Bank.setLockVal("achievements.HB", Achievement.aced.id, true);
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.aced, 0, 0));
			}
		}
		if(wins>=12){
			if(!Bank.hasLockVal("achievements.HB", Achievement.fullwin.id)){
				Bank.setLockVal("achievements.HB", Achievement.fullwin.id, true);
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.fullwin, 0, 0));
			}
			guis.remove(start);
			guis.add(claim);
			avail=false;
		}
		if(losses>=3){
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "haspass", "0");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "hero", "-1");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp1", "-1");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp2", "-1");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp3", "-1");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "name", "-1");
			Analysis.setKey(new File(Bank.path+"data/trial.HB"), "wins", "0");
			guis.remove(start);
			guis.add(claim);
			avail=false;
		}
	}
	
	public void readName(){
		try{
			int i = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "name"));
			if(i==-1){Analysis.setKey(new File(Bank.path+"data/trial.HB"), "name", name);}
		}catch(NumberFormatException e){
			name = Analysis.getKey(new File(Bank.path+"data/trial.HB"), "name");
			e.printStackTrace();
		}
	}
	
	public void buttonReact(int id){
		if(id==1&&avail){
			Util.music.stop();
			PanelPlayTrial pan = new PanelPlayTrial(Properties.map);
			Display.currentScreen = pan;
			Player p = new Player(Properties.selHero, Properties.username, Properties.width/2-15, 250).loadLocalSpells();
			p.phys.fixate = true;
			Display.currentScreen.addPlayer(p, true);
			Player e = next;
			e.target = Display.currentScreen.players.get(0);
			Display.currentScreen.addPlayer(e, true);
			e.focus = true;
			p.panel = pan;
			e.panel = pan;
		}
		if(id==2){
			award();
			Display.currentScreen = new PanelMenu(-1);
		}
	}
	
	public String getName(){
		StringBuilder sb = new StringBuilder();
		int r = rand.nextInt(2);
		sb.append(r==0?Dialog.maleNames[rand.nextInt(Dialog.maleNames.length)]:Dialog.femNames[rand.nextInt(Dialog.femNames.length)]);
		sb.append(" ");
		sb.append(Dialog.surnames[rand.nextInt(Dialog.surnames.length)]);
		return sb.toString();
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)Display.currentScreen=new PanelMenu(-1);
		if(e.getKeyCode()==KeyEvent.VK_R)Display.currentScreen=new PanelTrial();
	}
	
	public boolean spellsValid(){
		int validation = 0;
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp1"))!=-1){
			++validation;
			sp1 = new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp1"))]);
		}
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp2"))!=-1){
			++validation;
			sp2 = new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp2"))]);
		}
		if(Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp3"))!=-1){
			++validation;
			sp3 = new SpellCast(Spell.all[Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/trial.HB"), "sp3"))]);
		}
		return validation>=3?true:false;
	}
	
	public void award(){
		int runes = wins*50;
		int last = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
		Bank.setContentsRawdir(Bank.path+"data/Core.HB", (last+runes)+"");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "haspass", "0");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "wins", "0");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "losses", "0");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp1", "-1");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "sp2", "-1");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "name", "-1");
		Analysis.setKey(new File(Bank.path+"data/trial.HB"), "hero", "-1");
		Util.persistentGuis.add(new GUINotify(Bank.nexus, "Loot!", runes+" runes awarded,", 0, 0));
	}

	public void onUpdate() {
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.windowbg, 0 ,0, Properties.width, Properties.height, null);
		g.drawImage(Bank.squaregrad, Properties.width/2-150-15, 100-20, 300+30, 400+40, null);
		g.drawImage(Bank.heroportrait, Properties.width/2-150, 100, 300, 400, null);
		g.drawImage(rnd.portrait, Properties.width/2-150, 100, 300, 400, null);
		Bank.drawSquare(g, Properties.width/2-150, 100, 300, 400);
		g.setColor(Util.transparent_dark);
		g.fillRect(Properties.width/2-150, 100+400-100, 300, 100);
		g.setFont(Util.dialogFont);
		g.setColor(Util.transparent_buttonwhite);
		g.drawString(rnd.classname+" ["+level+"]", Properties.width/2-g.getFontMetrics().stringWidth(rnd.classname+" ["+level+"]")/2, 100+400-20);
		g.setColor(Color.WHITE);
		g.drawString(name, Properties.width/2-g.getFontMetrics().stringWidth(name)/2, 100+400-70);
		for(int i = 0; i < 3; ++i){
			Spell s = (i==0?sp1.spell:i==1?sp2.spell:i==2?sp3.spell:sp1.spell);
			g.drawImage(Bank.marblebutton, Properties.width/2-30-80+i*80, Properties.height/3*2-45, 60, 60, null);
			g.drawImage(s.sprite, Properties.width/2-30-80+i*80, Properties.height/3*2-45, 60, 60, null);
			Bank.drawSquare(g, Properties.width/2-30-80+i*80, Properties.height/3*2-45, 60, 60);
		}
		if(!avail){
			g.setColor(Util.transparent_dark);
			g.fillRect(0, 0, Properties.width, Properties.height);
			g.setColor(Color.WHITE);
			g.setFont(Util.largeNameFont);
			String ls = "Game over. Good run!";
			g.drawString(ls, Properties.width/2-g.getFontMetrics().stringWidth(ls)/2, Properties.height/2-60);
		}
		g.setFont(Util.cooldownBold);
		g.setColor(Color.WHITE);
		g.drawString("Losses", 30, 40);
		g.drawRect(20, 50, 3*100, 100);
		g.drawRect(20, 10, g.getFontMetrics().stringWidth("Losses")+20, 40);
		g.setColor(Color.WHITE);
		g.drawString("Wins", 30, 280-20);
		g.drawRect(20, 280-10, 3*100, 100*4);
		g.drawRect(20, 280-20-30, g.getFontMetrics().stringWidth("Wins")+20, 40);
		for(int i = 0; i < 3; ++i){
			int x = 30, y = 60;
			g.setFont(Util.largeNameFont);
			g.setColor(Color.RED);
			g.drawImage(Bank.custbutton, x+i*100, y, 80, 80, null);
			if(losses>=i+1)
				g.drawString("X", x+i*100+40-g.getFontMetrics().stringWidth("X")/2, y+55);
			Bank.drawSquare(g, x+i*100, y, 80, 80);
		}
		for(int i = 0; i < 12; ++i){
			int x = 30, y = 280;
			g.setFont(Util.largeNameFont);
			g.setColor(Color.GREEN);
			g.drawImage(Bank.custbutton, x+i*100-(i/3)*100*3, y+(i/3)*100, 80, 80, null);
			if(wins>=i+1)g.drawString("O", x+i*100+40-g.getFontMetrics().stringWidth("O")/2-(i/3)*100*3, y+55+(i/3)*100);
			Bank.drawSquare(g, x+i*100-(i/3)*100*3, y+(i/3)*100, 80, 80);
		}
	}
}
