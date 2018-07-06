package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Map {
	
	static Map[] all = new Map[255];
	
	int id = 0;
	String name;
	String leveldat = null, filepath = "local";
	Image icon;
	boolean starter = true;

	
	public static Map arena = new Map(0, "Reaveroc Arena", "Arena");
	public static Map sandhill = new Map(1, "Sandstorm Hill", "Sandhill");
	public static Map jungo = new Map(2, "Jungle Rumble", "Jungorama");
	public static Map trayat = new Map(30, "Trayat Gorge", "level1").setStarter(false);
	public static Map skyfire = new Map(31, "Skyfire Warpath", "level2").setStarter(false);
	public static Map bandit = new Map(32, "Mudeye Valley", "level3").setStarter(false);
	
	public Map(int id, String name, String datpath, boolean rawdir){
		all[id] = this;
		this.id = id;
		this.name = name;
		leveldat = Bank.getRawLevelData(datpath);
		icon = createImageFromData(leveldat);
		filepath = datpath;
	}
	
	public Map(int id, String name, String dat){
		all[id] = this;
		this.id = id;
		this.name = name;
		leveldat = dat;
		icon = createImageFromData(Bank.getLevelData(leveldat));
	}
	
	public Map setStarter(boolean b){
		this.starter = b;
		return this;
	}
	
	public void unlock(){
		Bank.setLockVal("maps.HB", id, true);
		Util.persistentGuis.add(new GUINotify(icon, "Map Unlocked!", name, 0, 0));
	}
	
	public Image createImageFromData(String dat){
		GridPanel gp = new PanelDummy(new Grid(750,null));
		Grid grid = new Grid(750, gp);
		Grid grid1 = new Grid(750, gp);
		Grid grid2 = new Grid(750, gp);
		BufferedImage ret = new BufferedImage(195, 140, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.createGraphics();
		grid.load(dat, 1, true);
		grid1.load(dat, 2, true);
		grid2.load(dat, 3, true);
		grid1.render(g, gp, 3, Util.transparent);
		grid.render(g, gp, 3);
		grid2.render(g, gp, 3);
		return ret;
	}
	
	public static BufferedImage getSky(){
		int r = Util.rand.nextInt(9);
		return r==0?Bank.sky:r==1?Bank.skyDark:r==2?Bank.skyRed:Bank.sky;
	}
	
	public static BufferedImage getSky(int r){
		return r==0?Bank.sky:r==1?Bank.skyDark:r==2?Bank.skyRed:Bank.sky;
	}
	
	public static BufferedImage getBG(){
		int r = Util.rand.nextInt(5);
		return r==0?Bank.hills:r==1?Bank.hillsRoll:r==2?Bank.hillsSand:r==3?Bank.hillsSnow:Bank.hillsHell;
	}
	
	public static BufferedImage getBG(int r){
		return r==0?Bank.hills:r==1?Bank.hillsRoll:r==2?Bank.hillsSand:r==3?Bank.hillsSnow:r==4?Bank.hillsHell:r==5?Bank.hillsCave:Bank.hills;
	}

	public String getLevelData() {
		return filepath=="local"?Bank.getLevelData(leveldat):Bank.getRawLevelData(filepath);
	}
}
