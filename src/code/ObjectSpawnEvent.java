package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ObjectSpawnEvent extends Object {
	
	boolean started = false;
	//SPAWNID : 0 = troll, 
	int spawnID = 0, spawnX = 0, spawnY = 0, spawnAmt = 10, spawnInt = 3000, spawned = 0;
	long lastSpawn = System.currentTimeMillis();
	GridPanel p;
	Object tar;
	int portalWidth = 60, portalHeight = 90;
	boolean ended = false, drawPortal = false;
	ArrayList<Point> spawns = new ArrayList<Point>();

	public ObjectSpawnEvent(int id, int x, int y, int w, int h, int sx, int sy, int sa, int si, GridPanel p) {
		super(x, y, w, h);
		this.spawnX = sx;
		this.spawnY = sy;
		this.spawnAmt = sa;
		this.spawnInt = si;
		this.spawnID = id;
		this.p = p;
	}
	
	public void update(double delta) {
		if(started&&(spawned<=spawnAmt||spawnAmt==-1)){
			if(System.currentTimeMillis()-lastSpawn>=spawnInt){
				Object obj = null;
				if(spawnID==0){
					Point point = getSpawnPoint();
					int spawnX = point.x, spawnY = point.y;
					obj = new CreatureTroll(spawnX-p.scrollX, spawnY-p.scrollY);
					((ObjectEnemy)obj).tar = tar;
				}
				if(spawnID==1){
					Point point = getSpawnPoint();
					int spawnX = point.x, spawnY = point.y;
					obj = new CreatureBandit(-p.scrollX+(rand.nextInt(2)==0?spawnX:0), spawnY-p.scrollY);
					((ObjectEnemy)obj).tar = tar;
				}
				if(spawnID==2){
					Point point = getSpawnPoint();
					int spawnX = point.x, spawnY = point.y;
					obj = new ObjectSnowbro(spawnX-p.scrollX, spawnY-p.scrollY, 40, 90);
				}
				if(spawnID==3){
					Point point = getSpawnPoint();
					int spawnX = point.x, spawnY = point.y;
					obj = new CreatureYeti(spawnX-p.scrollX, spawnY-p.scrollY);
				}
				if(spawnID==4){
					Point point = getSpawnPoint();
					int spawnX = point.x, spawnY = point.y;
					obj = new CreatureBandit(spawnX-p.scrollX, spawnY-p.scrollY);
					obj.addEffect(new Effect(1, EffectType.parachute, null, -1));
					((ObjectEnemy)obj).tar = tar;
					((ObjectEnemy)obj).health = 18;
					((ObjectEnemy)obj).healthMax = 18;
					((ObjectEnemy)obj).damage = 6;
				}
				Display.currentScreen.objects.add(obj);
				++spawned;
				if((spawnAmt==-1||spawned>=spawnAmt)&!ended){
					onEnd();
				}
				lastSpawn = System.currentTimeMillis();
			}
		}
	}
	
	public Point getSpawnPoint(){
		if(!spawns.isEmpty()){
			int r = rand.nextInt(spawns.size());
			return spawns.get(r);
		}
		return new Point(spawnX, spawnY);
	}
	
	public void onEnd(){
		if(spawnID==0){
			new ClipShell("rubble.wav", 5F).start();
			for(int i = 0; i < 13; ++i){
				for(int j = 0; j < 5; ++j){
					p.getGrid().setGridRelativeTileID(263+i, 16+j, (byte)0);
					ObjectBlock.split((263+i)*30-p.scrollX, (16+j)*30-p.scrollY, GridBlock.stonebrick.getID(), 15);
				}
			}
		}
		if(spawnID==1){
			p.addPlayer(Challenger.bandit.generate(p, Properties.width/2-25, 100, 50, 140, 1, true, tar), false);
		}
		if(spawnID==3){
			((PanelCampaign)Display.currentScreen).win();
		}
		ended = true;
	}
	
	public void collideWithPlayer(Player p){
		if(!started){
			tar=p;
			started=true;
		}
		if(tar==null)tar=p;
	}

	public void draw(Graphics g) {
		if(this.drawPortal){
			for(int i = 0; i < spawns.size(); ++i){
				Point p = spawns.get(i);
				g.drawImage(Bank.portal, p.x, p.y, portalWidth, portalHeight, null);
			}
			g.drawImage(Bank.portal, spawnX, spawnY, portalWidth, portalHeight, null);
		}
	}
}
