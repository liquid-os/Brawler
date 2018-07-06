package code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Hat {
	
	static Hat[] all = new Hat[255];
	
	public static final Hat dirt = new Hat(0, "Dirty Boy", Bank.dirt, 0, 30).setSize(30, 30);
	public static final Hat rock = new Hat(1, "Rock Solid", Bank.stone, 0, 30).setSize(30, 30);
	public static final Hat water = new Hat(2, "Damp Noggin", Bank.water, 0, 30).setSize(30, 30);
	public static final Hat leaves = new Hat(3, "Leafy", Bank.leaves, 0, 30).setSize(30, 30);
	public static final Hat coil = new Hat(4, "Banshee Coil", Bank.bansheecoil, 0, 30).setSize(30, 30);
	public static final Hat freezingtemptest = new Hat(5, "Frozen Tempest", Bank.winds, 0, 15).setSize(30, 30);
	public static final Hat halo = new Hat(6, "Halo", Bank.holyNova, 0, 20).setSize(40, 40);
	public static final Hat bluehalo = new Hat(7, "Corrupted Halo", Bank.blueNova, 0, 20).setSize(40, 40);
	public static final Hat psychichalo = new Hat(8, "Psychic Ring", Bank.nova, 0, 20).setSize(40, 40);
	public static final Hat sigil = new Hat(9, "Sigil", Bank.ra, 0, -20).setSize(25, 50);
	public static final Hat bluesigil = new Hat(10, "Corrupted Sigil", Bank.raCorrupt, 0, -20).setSize(25, 50);
	public static final Hat goop = new Hat(11, "Goop Brain", Bank.glop, 0, 10).setSize(40, 30);
	public static final Hat moonlight = new Hat(11, "Moonlight", Bank.moonlight, 0, 0).setSize(30, 30);
	public static final Hat tree = new Hat(12, "Tree", Bank.tree, 0, 5).setSize(24, 70);
	public static final Hat fire = new Hat(13, "Flames", Bank.fire, 0, 4).setSize(26, 30);
	public static final Hat scales = new Hat(14, "Hat of Balancing", Bank.scales, 0, 2).setSize(38, 28);
	//public static final Hat bluesigil = new Hat(10, "Corrupted Sigil", Bank.raCorrupt, 0, -20).setSize(25, 50);
	
	int id, xmod = 0, ymod = 0, width = 40, height = 60;
	Image sprite = null;
	String name = "hat";
	
	public Hat(int id, String name, Image sprite, int xmod, int ymod){
		this.sprite = sprite;
		this.name = name;
		this.xmod = xmod;
		this.ymod = ymod;
		this.id = id;
		all[id] = this;
	}
	
	public Hat(int id){
		this.id = id;
		all[id] = this;
	}
	
	public Hat setSize(int w, int h){
		this.width = w;
		this.height = h;
		return this;
	}
	
	public void onWearerHit(Object p){
		if(this == dirt){
			int r = Util.rand.nextInt(3);
			ObjectBlock.split(p.posX+p.width/2-width/2+xmod, p.posY-height+ymod, GridBlock.dirt.getID(), r==0?30:r==1?20:10, p.phys);
		}
	}
	
	public void render(Graphics g, Object p){
		if(this==moonlight){
			g.drawImage(Bank.moonlight, p.posX+p.width/2-45, 0, 90, Properties.height, null);
		}else{
			if(sprite==null){
				
			}else{
				g.drawImage(sprite, p.posX+p.width/2-width/2+xmod, p.posY-height+ymod, width, height, null);
			}
		}
	}
}
