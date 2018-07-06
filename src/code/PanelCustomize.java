package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class PanelCustomize extends PanelBase{
	
	GUIButton load, save, start, mapbtn;
	Map map = null;
	//gameMode : 0 = DM, 1 = Holdout
	int gameMode = 0, respawns = 1, rounds = 1;
	int maxTime = -1;
	GUITextBox name = new GUITextBox("GameLoadout_"+rand.nextInt(501), Properties.width/2-250, 40, 500, 80);
	
	public PanelCustomize(){
		load = new GUIButton(Bank.open, Bank.openHover, 0, 20, 20, 80, 80);
		save = new GUIButton(Bank.save, Bank.saveHover, 1, 120, 20, 80, 80);
		start = new GUIButton("Begin", 10, Properties.width/2-150, 700, 300, 80).setColor(Color.GREEN);
		mapbtn = new GUIButton(Bank.custbutton, Bank.custbutton, "Map: Unselected", 11, Properties.width/2-250, 600, 500, 70);
		guis.add(load);
		guis.add(save);
		guis.add(start);
		guis.add(mapbtn);
		guis.add(name);
		addButton(2, Properties.width/2-200, 200, 400, 70);
		addButton(4, Properties.width/2-200, 300, 400, 70);
		addButton(6, Properties.width/2-200, 400, 400, 70);
		addButton(8, Properties.width/2-200, 500, 400, 70);
	}
	
	public void addButton(int id, int x, int y, int w, int h){
		guis.add(new GUIButton(null, null, id, x-h-20, y, h, h).setArrow(1));
		guis.add(new GUIButton(null, null, id+1, x+w+20, y, h, h).setArrow(0));
	}
	
	public void load(){
		try{
			JFileChooser fc = new JFileChooser(Bank.path+"loadouts");
			fc.showOpenDialog(null);
			File f = fc.getSelectedFile();
			name.text = f.getName().replace(".HB", "");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String read = br.readLine();
			String[] vals = read.split(";");
			for(String s : vals){
				String id = s.split("=")[0];
				String val = s.split("=")[1];
				if(id=="gm"){gameMode=Integer.parseInt(val);}
				if(id=="rs"){respawns=Integer.parseInt(val);}
				if(id=="tm"){maxTime=Integer.parseInt(val);}
				if(id=="mp"){
					if(val.contains("/")){
						File mapfile = new File(val);
						map = new Map(29, mapfile.getName().replace(".HB", ""), mapfile.getPath(), true);	
						mapbtn.text = map.name;
					}else{
						map = Map.all[Integer.parseInt(val)];
						mapbtn.text = map.name;
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelMenu(-1);
		}
	}
	
	public String getSave(){
		StringBuilder sb = new StringBuilder();
		sb.append("gm="+gameMode+";");
		sb.append("rs="+respawns+";");
		sb.append("tm="+maxTime+";");
		sb.append("mp="+(map.filepath=="local"?map.id:map.filepath)+";");
		return sb.toString();
	}
	
	public void save(){
		String ret = getSave();
		File f = new File(Bank.path+"loadouts/"+name.text+".HB");
		try {
			f.createNewFile();                                      
			BufferedWriter pw = new BufferedWriter(new FileWriter(f));
			pw.write(ret); 
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void buttonReact(int id){
		if(id==0){
			this.load();
		}
		if(id==1){
			this.save();
		}
		if(id==2){goLeft(0);}
		if(id==3){goRight(0);}
		if(id==4){goLeft(1);}
		if(id==5){goRight(1);}
		if(id==6){goLeft(2);}
		if(id==7){goRight(2);}
		if(id==8){goLeft(3);}
		if(id==9){goRight(3);}
		if(id==10){
			start();
		}
		if(id==11){
			guis.add(new GUIMapSelect(this, Properties.width/2-300, Properties.height/2-250, 600, 500));
		}
	}
	
	public void start(){
		Util.music.stop();
		PanelPlay pan = new PanelPlay(null);
		if(map!=null)pan.load(map.getLevelData(), true);
		pan.maxTime = ((maxTime<0)?-1:((maxTime+1)*15*1000));
		Display.currentScreen = pan;
		Player p = new Player(Properties.selHero, Properties.username, Properties.width/2-15, 250).loadLocalSpells();
		p.phys.fixate = true;
		Display.currentScreen.addPlayer(p, true);
		Player e = new Player(Hero.getRandomHero(), "enemy", rand.nextInt(Properties.width), 250, Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"))).loadRandomSpells();
		e.target = Display.currentScreen.players.get(0);
		Display.currentScreen.addPlayer(e, true);
		p.respawns = respawns;
		e.respawns = respawns;
		e.focus = true;
		p.panel = pan;
		e.panel = pan;
		pan.bg = Map.getBG(rand.nextInt(4));
		pan.sky = Map.getSky(rand.nextInt(2));
		pan.winReward = respawns*(4+rand.nextInt(8));
		pan.xp = respawns*(6+rand.nextInt(12));
	}
	
	public void goLeft(int o){
		if(o==0){
			if(gameMode>0)--gameMode;
		}
		if(o==1){
			if(respawns>1)--respawns;
		}
		if(o==2){
			if(maxTime>-1)--maxTime;
		}
		if(o==3){
			if(rounds>1)--rounds;
		}
	}
	
	public void goRight(int o){
		if(o==0){
			++gameMode;
		}
		if(o==1){
			++respawns;
		}
		if(o==2){
			++maxTime;
		}
		if(o==3){
			++rounds;
		}
	}

	public void onUpdate() {
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.windowbg, 0, 0, 1920+(Properties.width-1920 > 0 ? Properties.width-1920 : 0), 1080+(Properties.height-1080 > 0 ? Properties.height-1080 : 0), null);
		if(map!=null){
			g.drawImage(map.icon, 0, 0, Properties.width, Properties.height, null);
		}
		drawBox(g, 0);
		drawBox(g, 1);
		drawBox(g, 2);
		drawBox(g, 3);
	}
	
	public void drawBox(Graphics g, int index){
		Rectangle rect = new Rectangle(Properties.width/2-200, 200+index*100, 400, 70);
		g.drawImage(Bank.custbutton, rect.x, rect.y, rect.width, rect.height, null);
		g.setColor(rect.contains(mousePoint)?Color.WHITE:Color.BLACK);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownFont);
		if(index == 0){
			String txt = gameMode==0?"Deathmatch":gameMode==1?"Holdout":gameMode==2?"Speedrun":gameMode==3?"Invasion":"//unknown//";
			g.drawString(txt, Properties.width/2-g.getFontMetrics().stringWidth(txt)/2, 200+index*100+40);
		}
		if(index == 1){
			String txt = "Respawns: "+respawns;
			g.drawString(txt, Properties.width/2-g.getFontMetrics().stringWidth(txt)/2, 200+index*100+40);
		}
		if(index == 2){
			String txt = "Max Time: "+(maxTime==-1?"Infinite":(((maxTime+1)*15)+"sec"));
			g.drawString(txt, Properties.width/2-g.getFontMetrics().stringWidth(txt)/2, 200+index*100+40);
		}
		if(index == 3){
			String txt = "Rounds: "+(rounds+"");
			g.drawString(txt, Properties.width/2-g.getFontMetrics().stringWidth(txt)/2, 200+index*100+40);
		}
	}
}
