package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PanelLocalMulti extends GridPanel{
	
	String clientUser = Properties.username;
	ArrayList<Coordinate> startLocations = new ArrayList<Coordinate>();
	boolean runDeath = false, win = false, draw = false;
	int winReward = 1+rand.nextInt(4), xp = 5, maxTime = -1;
	long endStart = -1, start = System.currentTimeMillis();
	String[] loopTracks = new String[]{""};
	WorldGenerator gen = new WorldGenerator();
	int gameType = 0;
	
	public PanelLocalMulti(String map){
		super(new Grid(Properties.width, null));
		Util.music = new ClipShell(Properties.selHero==Hero.snoopdog?"snooploop.wav":"ig.wav");
		Util.music.loop(-1);
		this.background = Color.WHITE;
		Display.reloadBackground();
		this.grid = new Grid(Properties.width, this);
		startGenY = gen.y*30;
		if(map==null){
			gen.generate(this, 200);
		}else{
			load(map);
		}
		this.renderObj = false;
	}
	
	public void onEnd(boolean vic){
		runDeath = true;
		if(vic){
			if(!Bank.setLockVal("achievements.HB", Achievement.win.id, true)){
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.win, 0, 0));
			}
			if(this.getPlayer(clientUser).fullHP()&!Bank.setLockVal("achievements.HB", Achievement.overpower.id, true)){Util.persistentGuis.add(new GUIAchievementNotify(Achievement.overpower, 0, 0));}
		}
		int pl = Analysis.increment(Util.stats, "played", 1);
		if(pl >= 100){
			if(!Bank.setLockVal("achievements.HB", Achievement.brawling.id, true)){Util.persistentGuis.add(new GUIAchievementNotify(Achievement.brawling, 0, 0));}
		}
		if(pl >= 500){
			if(!Bank.setLockVal("achievements.HB", Achievement.brawling1.id, true)){Util.persistentGuis.add(new GUIAchievementNotify(Achievement.brawling1, 0, 0));}
		}
		if(pl >= 1000){
			if(!Bank.setLockVal("achievements.HB", Achievement.brawling2.id, true)){Util.persistentGuis.add(new GUIAchievementNotify(Achievement.brawling2, 0, 0));}
		}
		if(pl >= 10000){
			if(!Bank.setLockVal("achievements.HB", Achievement.brawling3.id, true)){Util.persistentGuis.add(new GUIAchievementNotify(Achievement.brawling3, 0, 0));}
		}
		if(draw&&vic){
			winReward = (int) ((System.currentTimeMillis()-start) * this.winReward / (maxTime * 1.3F));
			xp = (int) ((System.currentTimeMillis()-start) * this.xp / (maxTime * 1.3F));
		}
		if(vic)
		Util.persistentGuis.add(new GUINotify(Bank.nexus, "Match Reward", winReward+" Nexus Runes Awarded", 0, 0));
		Util.persistentGuis.add(new GUINotify(Bank.puppet, "Match Reward", (vic?xp:xp/2)+" XP Awarded", 0, 0));
	}

	public void onUpdate() {
		if(endStart>-1){
			if(System.currentTimeMillis()-endStart >= 1000){
				Util.music.stop();
				if(!win)Display.currentScreen = new PanelRewards(-1, xp/2, getPlayer(clientUser).dealt, getPlayer(clientUser).healed, getPlayer(clientUser).taken);
				else Display.currentScreen = new PanelRewards(winReward, xp, getPlayer(clientUser).dealt, getPlayer(clientUser).healed, getPlayer(clientUser).taken);
			}
		}
		
		if(clicking){
			grid.setTileID(mousePoint.x, mousePoint.y, (byte) GridBlock.dirt.getID());
		}
		
		if(rightClicking){
			grid.setTileID(mousePoint.x, mousePoint.y, (byte) 0);
		}
		if(maxTime>0){
			if(System.currentTimeMillis()-start >= maxTime){
				int winner = 0;
				for(int i = 0; i < players.size(); ++i){
					if(players.get(i).respawns > players.get(winner).respawns)winner=i;
				}
				for(int i = 0; i < players.size(); ++i){
					if(players.get(i).respawns == players.get(winner).respawns){
						draw = true;
						this.shake(600, 11);
						if(!runDeath){
							endStart = System.currentTimeMillis();
							onEnd(true);
						}
					}
				}
				onDeath(winner, players.get(winner).getUsername().equals(Properties.username));
			}
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
	
	public void onDeath(int i, boolean isClient){
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
			Display.currentScreen = new PanelMenu(-1);
		}
	}

	public void renderScene(Graphics g) {
		g.drawImage(sky, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(bg, 0, 0, Properties.width, Properties.height, null);
	}
	
	public void renderForeground(Graphics g){
		for(int i = 0; i < objects.size(); ++i){
			if(i < objects.size()){
				if(objects.get(i)!=null)
				objects.get(i).drawOrigin(g);
			}
		}
		for(int i = 0 ; i < players.size(); ++i){
			players.get(i).drawPlayer(g);
		}
		fore.render(g, this);
		for(Player p : players){
			if(p.showHud)
			p.renderHUD(g, 20+Display.currentScreen.players.indexOf(p)*400, 20, 100);
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
			int a = (int) ((System.currentTimeMillis()-endStart) * Properties.width+400 / 600);
			int alpha = (int) ((System.currentTimeMillis()-endStart) * 255 / 400);
			if(alpha > 255) alpha = 255;
			Color color = new Color(255,255,255,alpha);
			g.setColor(color);
			g.fillOval(Properties.width/2-a/2, Properties.height/2-a/2, a, a);
			g.setColor(draw?Color.YELLOW:Color.RED);
			g.setFont(Util.largeNameFont);
			String str = draw?"IT'S A DRAW!":"FATALITY";
			g.drawString(str, Properties.width/2-g.getFontMetrics().stringWidth(str)/2, Properties.height/2-40);
		}
	}
	
	public void click(boolean b){
	}
}
