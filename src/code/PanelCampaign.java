package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PanelCampaign extends GridPanel{
	
	String clientUser = Properties.username;
	ArrayList<Coordinate> startLocations = new ArrayList<Coordinate>();
	boolean runDeath = false, win = false, draw = false;
	int winReward = 1+rand.nextInt(4), xp = 3+rand.nextInt(6), maxTime = -1;
	long endStart = -1, start = System.currentTimeMillis();
	String[] loopTracks = new String[]{""};
	Level level;
	Levelset set;
	
	public PanelCampaign(Level level, Levelset set){
		super(new Grid(Properties.width, null));
		this.set = set;
		Util.music = new ClipShell(Properties.selHero==Hero.snoopdog?"snooploop.wav":"ig.wav");
		Util.music.loop(-1);
		this.background = Color.WHITE;
		Display.reloadBackground();
		this.grid = new Grid(Properties.width, this);
		this.level = level;
		level.generate(this);
		if(scrollX < level.spawnX-Properties.width/2)scrollRight(Util.dif(scrollX, level.spawnX-Properties.width/2));
		if(scrollX > level.spawnX-Properties.width/2)scrollLeft(Util.dif(scrollX, level.spawnX-Properties.width/2));
		if(scrollY < level.spawnX-Properties.height/2)scrollDown(Util.dif(scrollY, level.spawnY-Properties.height/2));
		if(scrollY > level.spawnX-Properties.height/2)scrollUp(Util.dif(scrollY, level.spawnY-Properties.height/2));
		this.renderObj = false;
		this.xp = level.xp;
	}

	public void onUpdate() {
		if(endStart>-1){
			if(System.currentTimeMillis()-endStart >= 1200){
				Util.music.stop();
				if(!win)Display.currentScreen = new PanelRewards(-1, 0, getPlayer(clientUser).dealt, getPlayer(clientUser).healed, getPlayer(clientUser).taken);
				else Display.currentScreen = new PanelRewards(0, xp, getPlayer(clientUser).dealt, getPlayer(clientUser).healed, getPlayer(clientUser).taken);
				if(win&&Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "cpl"+set.id))<level.id+1)Analysis.setKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "cpl"+set.id, (level.id+1)+"");
				Display.currentScreen = new PanelLevelSelect(Properties.selHero, set);
			}
		}
		if(clicking){
			grid.setTileID(mousePoint.x, mousePoint.y, (byte) GridBlock.dirt.getID());
		}
		if(rightClicking){
			grid.setTileID(mousePoint.x, mousePoint.y, (byte) 0);
		}
		if(!runDeath){
			for(int i = 0; i < players.size(); ++i){
				if(players.get(i)!=null){
					if(players.get(i).health<=0&&endStart==-1){
						players.get(i).respawns--;
						if(players.get(i).getUsername().equals(clientUser)){
							onDeath(i, true);
						}else{	
							onDeath(i, false);
						}
					}
				}
			}
		}
	}
	
	public void lose(){
		win = false;
		this.endStart = System.currentTimeMillis();
		this.shake(600, 18);
		for(int x = 0 ; x < 100; ++x){
			Particle p = new Particle(Properties.width/2, Properties.height/2, Particle.LIQUID, Color.RED);
			p.isBlood = true;
			p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
			p.phys.motionY = rand.nextInt(150);
			objects.add(p);
		}
		onEnd(false);
	}
	
	public void win(){
		win = true;
		this.endStart = System.currentTimeMillis();
		this.shake(600, 18);
		for(int x = 0 ; x < 100; ++x){
			Particle p = new Particle(Properties.width/2, Properties.height/2, Particle.LIQUID, Color.RED);
			p.isBlood = true;
			p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
			p.phys.motionY = rand.nextInt(150);
			objects.add(p);
		}
		onEnd(true);
	}
	
	public void onDeath(int i, boolean isClient){
		players.get(i).onDeath();
		if(isClient){
			if(players.get(i).respawns<=0){
				win = false;
				this.endStart = System.currentTimeMillis();
				this.shake(600, 18);
				for(int x = 0 ; x < 100; ++x){
					Particle p = new Particle(Properties.width/2, Properties.height/2, Particle.LIQUID, Color.RED);
					p.isBlood = true;
					p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
					p.phys.motionY = rand.nextInt(150);
					objects.add(p);
				}
				onEnd(false);
			}else{
				players.get(i).health = players.get(i).healthMax;
				players.get(i).posX = players.get(i).startX;
				players.get(i).posY = players.get(i).startY;
				players.get(i).phys.motionX = 0;
				players.get(i).phys.motionY = 0;
				objects.add(new ObjectSnoopgif(false, players.get(i).startX+players.get(i).width/2-30, players.get(i).startY+players.get(i).height/2-50, 60, 100, Bank.portal, 500));
			}
		}else{
			if(players.get(i).respawns<=0){
				if(players.get(i).focus){
					new ClipShell("crit.wav").start();
					win = true;
					this.endStart = System.currentTimeMillis();		
					this.shake(600, 18);
					for(int x = 0 ; x < 100; ++x){
						Particle p = new Particle(Properties.width/2, Properties.height/2, Particle.LIQUID, Color.RED);
						p.isBlood = true;
						p.phys.motionX = rand.nextInt(400)-rand.nextInt(400);
						p.phys.motionY = rand.nextInt(150);
						objects.add(p);
					}
					onEnd(true);
				}else{
					objects.remove(players.get(i));
					players.remove(i);
				}
			}else{
				players.get(i).health = players.get(i).healthMax;
				players.get(i).posX = players.get(i).startX;
				players.get(i).posY = players.get(i).startY;
				players.get(i).phys.motionX = 0;
				players.get(i).phys.motionY = 0;
				objects.add(new ObjectSnoopgif(false, players.get(i).startX+players.get(i).width/2-30, players.get(i).startY+players.get(i).height/2-50, 60, 100, Bank.portal, 500));							}
		
		}
	}
	
	public void onEnd(boolean vic){
		runDeath = true;
		if(draw&&vic){
			winReward = (int) ((System.currentTimeMillis()-start) * this.winReward / (maxTime * 1.3F));
			xp = (int) ((System.currentTimeMillis()-start) * this.xp / (maxTime * 1.3F));
		}
		if(vic){
			Util.persistentGuis.add(new GUINotify(Bank.icon, "Level "+(level.id+1)+" Complete!", xp+" XP Awarded", 0, 0));
			if(!Bank.hasLockVal("maps.HB", 30+level.id)){
				if(Map.all[30+level.id]!=null)Map.all[30+level.id].unlock();
			}
		}
	}
	
	public void keyPressed(KeyEvent e){
		for(int i = 0; i < players.size(); ++i){
			if(players.get(i).getUsername().equals(clientUser))players.get(i).keyPressed(e);
		}
	}
	
	public void keyReleased(KeyEvent e){
		for(int i = 0; i < players.size(); ++i){
			if(players.get(i).getUsername().equals(clientUser))players.get(i).keyReleased(e);
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Util.music.stop();
			Display.currentScreen = new PanelLevelSelect(Properties.selHero, set);
		}
	}

	public void renderScene(Graphics g) {
		g.drawImage(sky, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(bg, 0, 0, Properties.width, Properties.height, null);
	}
	
	public void renderForeground(Graphics g){
		for(int i = 0; i < objects.size(); ++i){
			objects.get(i).drawOrigin(g);
		}
		for(int i = 0 ; i < players.size(); ++i){
			players.get(i).drawPlayer(g);
		}
		fore.render(g, this);
		for(Player p : players){
			p.renderHUD(g, 20+Display.currentScreen.players.indexOf(p)*400, 20, 100);
		}
		if(level==Level.iceridge){
			int count = 0;
			for(Object o: objects){
				if(o instanceof ObjectSnowbro)count++;
			}
			g.drawImage(Bank.squaregrad, Properties.width/2-100-10, 100-4, 200+20, 50+8, null);
			g.drawImage(Bank.custbutton, Properties.width/2-100, 100, 200, 50, null);
			String s = count+" Villagers Alive";
			g.setColor(Color.WHITE);
			g.drawString(s, Properties.width/2-g.getFontMetrics().stringWidth(s)/2, 100+35);
			if(count<=0&&endStart==-1){
				lose();
			}
		}
		if(maxTime>0){
			int w = (int) ((System.currentTimeMillis()-start) * 300 / maxTime);
			int r = (int) ((System.currentTimeMillis()-start) * 255 / maxTime);
			String rem = (maxTime/1000-(System.currentTimeMillis()-start)/1000)+"sec";
			//int s = (int) ((System.currentTimeMillis()-start) * 90 / maxTime);
			if(r > 255)r=255;
			Color c = new Color(r, 0, 0, 255);
			g.setColor(Color.BLACK);
			g.fillRect(Properties.width/2-150, 100, 300, 40);
			g.fillRect(Properties.width/2-40, 100-30, 80, 30);
			g.setColor(Color.WHITE);
			g.setFont(Util.descFont);
			g.drawString(rem, Properties.width/2-g.getFontMetrics().stringWidth(rem)/2, 90);
			g.setColor(c);
			g.fillRect(Properties.width/2-150, 100, w, 40);
			g.setColor(Color.BLACK);
			g.drawRect(Properties.width/2-150, 100, 300, 40);
		//	g.fillOval(Properties.width/2-45, 90, 90, 90);
			g.drawImage(Bank.clock, Properties.width/2-150+w, 90, 60, 60, null);
		}
		if(endStart>-1){
			int a = (int) ((System.currentTimeMillis()-endStart) * Properties.width+400 / 1000);
			int alpha = (int) ((System.currentTimeMillis()-endStart) * 255 / 400);
			if(alpha > 255) alpha = 255;
			Color color = new Color(0,0,0,alpha);
			g.setColor(color);
			g.fillOval(Properties.width/2-a/2, Properties.height/2-a/2, a, a);
			g.setColor(win?Color.GREEN:Color.RED);
			g.setFont(Util.largeNameFont);
			String str = win?"VICTORY":"FATALITY";
			g.drawString(str, Properties.width/2-g.getFontMetrics().stringWidth(str)/2, Properties.height/2-40);
		}
	}
	
	public void click(boolean b){
	}
}
