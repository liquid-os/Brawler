package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

public class GUIMapSelect extends GUI {
	
	ArrayList<Map> maps = new ArrayList<Map>();
	int sel = 0;
	Rectangle open = new Rectangle(x+10, y+10, 30, 30), start = new Rectangle(x+w/2-200, y+h-70, 400, 40), maprect = new Rectangle(x+w/2-150, y+h/2-110, 300, 220);
	Polygon lpoly = new Polygon(new int[]{x+60+10,x+10,x+60+10}, new int[]{y+h/2-30,y+h/2,y+h/2+30}, 3);
	Polygon rpoly = new Polygon(new int[]{x+w-60-10,x+w-10,x+w-60-10}, new int[]{y+h/2-30,y+h/2,y+h/2+30}, 3);
	PanelCustomize pan;
	
	public GUIMapSelect(PanelCustomize pan, int x, int y, int w, int h) {
		super(x, y, w, h);
		showSelection = false;
		boolean[] locks = Bank.produceLockvalArray("maps.HB");
		for(int i = 0; i < Map.all.length; ++i){
			if(Map.all[i]!=null){
				if(Map.all[i].starter||locks[i])
				maps.add(Map.all[i]);
			}
		}
		File[] files = new File(Bank.path+"maps/").listFiles();
		for(int i = 0; i < files.length; ++i){
			if(files[i].isFile()){
				Map m = new Map(29, files[i].getName().replace(".HB", ""), Bank.path+"maps/"+files[i].getName(), true);
				maps.add(m);
			}
		}
		this.pan = pan;
	}
	
	public void click(boolean b){
		if(start.contains(Display.currentScreen.mousePoint)){
			pan.map = maps.get(sel);
			pan.mapbtn.text = "Map: "+maps.get(sel).name;
			pan.guis.remove(this);
		}
		if(lpoly.contains(Display.currentScreen.mousePoint)){
			if(sel>0)--sel;
		}
		if(rpoly.contains(Display.currentScreen.mousePoint)){
			if(sel>=maps.size()-1)sel=0;else ++sel;
		}
	}

	public void tick(double delta) {
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent_dark);
		g.fillRect(0, y, x, h);
		g.fillRect(0, 0, Properties.width, y);
		g.fillRect(0, y+h, Properties.width, Properties.height-h-y);
		g.fillRect(x+w, y, Properties.width-w-x, h);
		g.drawImage(Bank.gradient, x, y, w, h, null);
		//g.setColor(Map.getSkyColor(0));
		g.setColor(Color.CYAN);
		g.fillRect(x+w/2-150, y+h/2-110, 300, 220);
		Map map = maps.get(sel);
		g.drawImage(map.icon, x+w/2-150, y+h/2-110, 300, 220, null);
		g.setColor(maprect.contains(Display.currentScreen.mousePoint)?Util.transparent_white:Util.transparent_dark);
		g.fillRect(x+w/2-150, y+h/2-15, 300, 30);
		g.setColor(maprect.contains(Display.currentScreen.mousePoint)?Color.BLACK:Color.WHITE);
		g.setFont(Util.descTitleFont);
		g.drawString(map.name, x+w/2-g.getFontMetrics().stringWidth(map.name)/2, y+h/2+5);
		g.setColor(maprect.contains(Display.currentScreen.mousePoint)?Color.WHITE:Color.BLACK);
		g.drawRect(x+w/2-150, y+h/2-110, 300, 220);
		g.setColor(Color.BLACK);
		g.fillPolygon(lpoly);
		g.fillPolygon(rpoly);
		if(rpoly.contains(Display.currentScreen.mousePoint)){
			g.setColor(Color.WHITE);
			g.drawPolygon(rpoly);
		}
		if(lpoly.contains(Display.currentScreen.mousePoint)){
			g.setColor(Color.WHITE);
			g.drawPolygon(lpoly);
		}
		g.drawImage(open.contains(Display.currentScreen.mousePoint)?Bank.openHover:Bank.open, open.x, open.y, open.width, open.height, null);
		if(open.contains(Display.currentScreen.mousePoint)){
			g.setColor(Util.transparent_dark);
			g.setFont(Util.descFont);
			g.fillRect(open.x+open.width+5, open.y, 160, open.height);
			g.setColor(Color.WHITE);
			g.drawString("Open From File", open.x+open.width+5+80-g.getFontMetrics().stringWidth("Open From File")/2, open.y+20);
		}
		g.drawImage(Bank.custbutton, start.x, start.y, start.width, start.height, null);
		g.setColor(start.contains(Display.currentScreen.mousePoint)?Color.WHITE:Color.BLACK);
		g.drawRect(start.x, start.y, start.width, start.height);
		g.setColor(Color.WHITE);
		g.drawString("Select", start.x+start.width/2-g.getFontMetrics().stringWidth("Select")/2, start.y+25);
	}
}
