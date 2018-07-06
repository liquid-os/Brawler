package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class PanelMapCreate extends GridPanel {
	
	long lastSel = -1;
	int blockType = 0;
	BufferedImage bg = null, sky = null;	
	int x, y;
	//0 = Block Brush, 1 = Scroller, 2 = Add Spawn
	int tool = 0;
	Point lastClick;
	Rectangle hud = new Rectangle(0,0,Properties.width,30);
	GUIButton newmap = null, open, save, layer, block, opentools;
	int placelayer = 0;
	ArrayList<Point> spawns = new ArrayList<Point>();
	GUIToolSelect tools;
	int gw, gh;

	public PanelMapCreate(Grid g, String name, int bg, int sky) {
		super(g);
		this.grid = new Grid(750, this);
		this.name = name;
		this.sky = Map.getSky(sky);
		this.bg = Map.getBG(bg);
		this.renderGuis = false;
		this.gw = grid.getCore()[0].length;
		this.gh = grid.getCore()[1].length;
		this.tools = new GUIToolSelect(this);
	}
	
	public boolean mouseOnGUI(){
		boolean ret = false;
		for(GUI gui : guis){
			if(gui.getRect().contains(mousePoint))ret=true;
		}
		return ret;
	}

	public void onUpdate() {
		if((tool==1)&&lastClick!=null){
			if(mousePoint.x > lastClick.x){
				int dist = Util.xDist(mousePoint, lastClick);
				lastClick.x+=dist;
				scrollX-=dist;
			}
			if(mousePoint.x < lastClick.x){
				int dist = Util.xDist(mousePoint, lastClick);
				lastClick.x-=dist;
				scrollX+=dist;
			}
			if(mousePoint.y > lastClick.y){
				int dist = Util.yDist(mousePoint, lastClick);
				lastClick.y+=dist;
				scrollY-=dist;
			}
			if(mousePoint.y < lastClick.y){
				int dist = Util.yDist(mousePoint, lastClick);
				lastClick.y-=dist;
				scrollY+=dist;
			}
		}
		if(newmap==null){
			newmap = new GUIButton(Bank.rawplus, Bank.rawplushover, 1, 20, 20, 110, 110);
			open = new GUIButton(Bank.open, Bank.openHover, 2, 20+130, 20, 110, 110);
			save = new GUIButton(Bank.save, Bank.saveHover, 3, 20+130*2, 20, 110, 110);
			layer = new GUIButton(Bank.layer0, Bank.layer0Hover, 4, 20+130*4, 20, 110, 110);
			block = new GUIButton(Bank.block, Bank.blockHover, 5, 20+130*5, 20, 110, 110);
			opentools = new GUIButton(Bank.tools, Bank.toolsHover, 6, 20+130*6, 20, 110, 110);
			guis.add(newmap);
			guis.add(open);
			guis.add(save);
			guis.add(layer);
			guis.add(block);
			guis.add(opentools);
		}
		if(clicking&!hud.contains(mousePoint)&!mouseOnGUI()){
			if(lastClick==null){
				lastClick = new Point(mousePoint.x, mousePoint.y);
			}
			if(tool==0){
				x = mousePoint.x-mousePoint.x%30;
				y = mousePoint.y-mousePoint.y%30;
				getSelGrid().setTileID(x+scrollX-scrollX%30, y+scrollY-scrollY%30, (byte) blockType);
			}
		}
		if(rightClicking){
			if(tool==0){
				x = mousePoint.x-mousePoint.x%30;
				y = mousePoint.y-mousePoint.y%30;
				getSelGrid().setTileID(x+scrollX-scrollX%30, y+scrollY-scrollY%30, (byte) 0);
			}
		}
		int i = 3;
		/*if(mousePoint.x > Properties.width-80)scrollX+=i;
		if(mousePoint.x < 80)scrollX-=i;
		if(mousePoint.y > Properties.height-80)scrollY+=i;
		if(mousePoint.y < 80)scrollY-=i;*/
		if(hud.contains(mousePoint)){
			hud = new Rectangle(0,0,Properties.width,150);
			if(newmap.h<110){
				for(GUI gui : guis){
					if(gui instanceof GUIButton)
					gui.h = 110;
				}
			}
		}else{
			hud = new Rectangle(0,0,Properties.width,30);
			if(newmap.h>0){
				for(GUI gui : guis){
					if(gui instanceof GUIButton)
					gui.h = 0;
				}
			}
		}
	}
	
	public void releaseClick(boolean b){
		lastClick = null;
	}

	public void buttonReact(int i){
		if(i==1){
			Display.currentScreen = new PanelMapSetup();
		}
		if(i==2){
			new ClipShell("click.wav").start();
			JFileChooser fc = new JFileChooser(Bank.path+"maps");
			fc.showOpenDialog(null);
			File f = fc.getSelectedFile();
			load(f.getName().replace(".HB", ""));
		}
		if(i==3){
			new ClipShell("click.wav").start();
			save(name);
			Util.persistentGuis.add(new GUINotify(Bank.save, "Save Complete", Bank.path+"maps/"+name+".HB", 0, hud.height));
			if(!Bank.hasLockVal("achievements.HB", Achievement.map.id)){
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.map, 0, 0));
				Bank.setLockVal("achievements.HB", Achievement.map.id, true);
			}
		}
		if(i==4){
			if(placelayer<2)++placelayer;
			else placelayer = 0;
		}
		if(i==5){
			guis.add(new GUIBlockSelect(this));
		}
		if(i==6){
			if(guis.contains(tools))guis.remove(tools);else{guis.add(tools);}
			new ClipShell("click.wav").start();
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			getSelGrid().setTileID(x+scrollX, y+scrollY, (byte) blockType);
			new ClipShell("punch.wav", 3F).start();
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			getSelGrid().setTileID(x+scrollX, y+scrollY, (byte) 0);
			new ClipShell("swoosh.wav", 1F).start();
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			byte[][] newgrid = new byte[600][600];
			byte[][] newfore = new byte[600][600];
			byte[][] newbg = new byte[600][600];
			for(int i = 0; i < 600; ++i){
				for(int j = 0; j < 600; ++j){
					byte id = (byte) grid.getTileIDWithScroll(i*30, j*30);
					byte id1 = (byte) fore.getTileIDWithScroll(i*30, j*30);
					byte id2 = (byte) scene.getTileIDWithScroll(i*30, j*30);
					if(id!=0)newgrid[i][j+1] = id;
					if(id1!=0)newfore[i][j+1] = id1;
					if(id2!=0)newbg[i][j+1] = id2;
				}
			}
			this.grid.setCore(newgrid);
			this.fore.setCore(newfore);
			this.scene.setCore(newbg);
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(GridBlock.all[blockType-1]!=null){
				blockType--;
				new ClipShell("click.wav", 3F).start();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(GridBlock.all[blockType+1]!=null){
				blockType++;
				new ClipShell("click.wav", 3F).start();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			getSelGrid().save(this, name);
			Display.currentScreen = new PanelMenu(-1);
		}else{
			Integer i = Integer.parseInt(e.getKeyChar()+"");
			if(i!=null&&GridBlock.all[i]!=null)
			blockType = i;
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_W){
			int newX = x-x%getSelGrid().getTileSize();
			int newY = y-y%getSelGrid().getTileSize()-getSelGrid().getTileSize();
			x = newX;
			y = newY;
		}
		if(e.getKeyCode()==KeyEvent.VK_S){
			int newX = x-x%getSelGrid().getTileSize();
			int newY = y-y%getSelGrid().getTileSize()+getSelGrid().getTileSize();
			x = newX;
			y = newY;
		}
		if(e.getKeyCode()==KeyEvent.VK_A){
			int newX = x-x%getSelGrid().getTileSize()-getSelGrid().getTileSize();
			int newY = y-y%getSelGrid().getTileSize();
			x = newX;
			y = newY;
		}
		if(e.getKeyCode()==KeyEvent.VK_D){
			int newX = x-x%getSelGrid().getTileSize()+getSelGrid().getTileSize();
			int newY = y-y%getSelGrid().getTileSize();
			x = newX;
			y = newY;
		}
	}
	
	public Grid getSelGrid(){
		return placelayer==0?grid:placelayer==1?scene:placelayer==2?fore:grid;
	}

	public void renderScene(Graphics g) {
		g.drawImage(sky, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(bg, 0, 0, Properties.width, Properties.height, null);
	}
	
	public void renderForeground(Graphics g){
		fore.render(g, this);
		g.setColor(Util.transparent_white);
		g.setColor(Util.transparent);
		if(tool==0)g.drawRect(mousePoint.x-(mousePoint.x%30)-(scrollX%30), mousePoint.y-(mousePoint.y%30)-(scrollY%30), 30, 30);
		g.drawImage(GridBlock.all[blockType].getImage(), 20, hud.height+20, 80, 80, null);
		g.setColor(Color.WHITE);
		g.drawString(GridBlock.all[blockType].getName(), 20+40-g.getFontMetrics().stringWidth(GridBlock.all[blockType].getName())/2, hud.height+60);
		g.setFont(Util.cooldownBold);
		g.drawString("Layer: "+(placelayer==0?"Level":placelayer==1?"Background Scene":placelayer==2?"Foreground":"Unknown"), 20, hud.height+140);
		g.setFont(Util.descTitleFont);
		g.drawString("Grid Size = "+gw+" x "+gh+" "+"("+(gw*gh)+" tiles, "+(gw*gh*30)+" pixels)", 20, hud.height+170);
		g.drawString("Scroll: X = "+scrollX+" ("+(float)(scrollX/30)+" tiles) "+", Y = "+scrollY+" ("+(float)(scrollY/30)+" tiles) ", 20, hud.height+190);
		g.drawString("Mouse Pos: X = "+(mousePoint.x+scrollX)+" ("+(float)((mousePoint.x+scrollX)/30)+" tiles) "+", Y = "+(mousePoint.y+scrollY)+" ("+(float)((mousePoint.y+scrollY)/30)+" tiles) ", 20, hud.height+210);
		g.drawImage(Bank.gradient, hud.x, hud.y, hud.width, hud.height, null);
		g.setColor(Util.transparent_dark);
		g.fillRect(hud.x, hud.y, hud.width, hud.height);
		Bank.drawSquare(g, hud.x, hud.y, hud.width, hud.height);
		if(hud.contains(mousePoint)){this.renderGuis(g);}
		for(GUI gui : guis){
			if(gui instanceof GUIBlockSelect || gui instanceof GUIToolSelect)gui.drawBase(g);
		}
		g.drawImage(tool==1?Bank.cursorDrag:Bank.cursorPencil, mousePoint.x-12, mousePoint.y-12, 24, 24, null);
	}
}
