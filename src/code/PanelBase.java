package code;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public abstract class PanelBase{
	
	public Player player = null;
	
	public Color background = Color.BLACK;
	
	public Random rand = new Random();
	
	public boolean clicking = false, midclicking = false, rightClicking = false, renderObj = true, renderGuis = true, runBaseProfiler = this instanceof GridPanel;
	
	long shakeStart = -1, biomeTransition = -1;
	int shakeMillis = 0, shakeAmt = 20;
	
	int viewDistY = 2000, scrollBorder = 400, scrollYQueue = 0;
	
	public Point mousePoint;
	
	Grid grid;
	boolean gridEnabled = true;
	
	Weather weather = null;
	
	int windX = 0, windY = 0;
	
	public ArrayList<Object> objects = new ArrayList<Object>();
	public ArrayList<Biome> biomes = new ArrayList<Biome>();
	
	public ArrayList<GUI> guis = new ArrayList<GUI>();
	int selGui = -1, startGenY = Properties.height/2;
	
	BufferedImage bg = Map.getBG(), sky = Map.getSky();

	public ArrayList<Player> players = new ArrayList<Player>();
	
	public PanelBase(Color c, int frames){background = c;}
	
	public PanelBase(){}
	
	public void buttonReact(int id){
		
	}
	
	public void playerConnected(Packet00Login login){
		
	}
	
	public void scrollLeft(int i){
		getGrid().p.scrollX-=i;
		for(int x = 0; x < objects.size(); ++x){
			objects.get(x).posX+=i;
		}
	}
	
	public void scrollRight(int i){
		getGrid().p.scrollX+=i;
		for(int x = 0; x < objects.size(); ++x){
			objects.get(x).posX-=i;
		}
	}
	
	public void scrollUp(int i){
		getGrid().p.scrollY-=i;
		for(int x = 0; x < objects.size(); ++x){
			objects.get(x).posY+=i;
		}
	}
	
	public void scrollDown(int i){
		getGrid().p.scrollY+=i;
		for(int x = 0; x < objects.size(); ++x){
			objects.get(x).posY-=i;
		}
	}
	
	public void removePlayer(String name){
		for(int i = 0; i < players.size(); ++i){
			if(players.get(i).getUsername().equals(name)){
				players.remove(i);
			}
		}
	}
	
	public void addPlayer(Player p, boolean b){
		if(b)p.posY = startGenY-p.height;
		this.objects.add(p);
		this.players.add(p);
	}
	
	public void addPlayer(String s, Hero h, boolean b){
		Player p = new Player(h, s, 0, 0);
		if(b)p.posY = startGenY-p.height;
		this.objects.add(p);
		this.players.add(p);
	}
	
	public Object getObjects(){
		for(int ols = 0; ols < objects.size(); ++ols){
			return objects.get(ols);
		}
		return null;
	}
	
	public void shake(int millis, int amt){
		this.shakeStart = System.currentTimeMillis();
		this.shakeMillis = millis;
		this.shakeAmt = amt;
	}
	
	public void update(double delta)
	{
		if(guis.size()<=selGui){
			selGui=-1;
		}
		if(shakeStart>-1){
			Display.frame.p.setLocation(rand.nextInt(shakeAmt)-rand.nextInt(shakeAmt), rand.nextInt(shakeAmt)-rand.nextInt(shakeAmt));
			if(System.currentTimeMillis()-shakeStart>=shakeMillis){
				Display.frame.p.setLocation(0, 0);
			}
		}
		for(int i = 0; i < objects.size(); ++i){
			if(i<objects.size() && objects.get(i)!=null){
				if(!objects.get(i).isRemoving()){
					for(int j = 0; j < players.size(); ++j){
						if(players.get(j).getHitbox().intersects(objects.get(i).getHitbox())){
							if(objects.get(i).countEffect(EffectType.frozen)<=0)
							objects.get(i).collideWithPlayer(players.get(j));
						}
					}
					if(objects.get(i) instanceof Player){
						if(runBaseProfiler){
							objects.get(i).updateBase(delta);
						}
					}else
					objects.get(i).updateBase(delta);
				}else{
					objects.remove(i);
				}
			}
		}
		if(weather!=null){
			weather.update(this);
		}
		onUpdate();
	}
	
	public void renderObjects(Graphics g){
		for(int i = 0; i < objects.size(); ++i){
			objects.get(i).drawOrigin(g);
		}
	}
	
	public void renderGuis(Graphics g){
		for(int i = 0; i < guis.size(); ++i){
			guis.get(i).drawBase(g);
		}
	}
	
	public final void drawOrigin(Graphics g)
	{	
		drawScreen(g);
		if(renderObj){
			for(int i = 0; i < objects.size(); ++i){
				objects.get(i).drawOrigin(g);
			}
		}
		if(renderGuis){
			for(int i = 0; i < guis.size(); ++i){
				guis.get(i).drawBase(g);
			}
		}
		for(int i = 0; i < Util.persistentGuis.size(); ++i){
			Util.persistentGuis.get(i).drawBase(g);
		}
	}

	public abstract void onUpdate();
	
	public abstract void drawScreen(Graphics g);
	
	public Class getItemsInList(ArrayList l)
	{
		for(int i = 0; i < l.size(); ++i)
		{
			return (Class) l.get(i);
		}
		return null;
	}
	
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	
	public void mouseWheelMoved(MouseWheelEvent  e){}

	public void releaseClick(boolean b) {
	}

	public void click(boolean b) {
	}

	public Grid getGrid() {
		return grid;
	}

	public Player getPlayer(String username) {
		for(int i = 0; i < players.size(); ++i){
			if(players.get(i)!=null){
				if(players.get(i).getUsername().equals(username))return players.get(i);
			}
		}
		return null;
	}
}
