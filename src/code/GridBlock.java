package code;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class GridBlock {
	
	private byte id = 0, shape = 0;
	private boolean psolid = false, osolid, canBreak = false;
	static GridBlock[] all = new GridBlock[200];
	private Image image;
	private String name;
	
	public static final GridBlock air = new GridBlock("Air", 0, null).setSolid(false);
	public static final GridBlock stone = new GridBlock("Stone", 1, Bank.stone).setSolid(true);
	public static final GridBlock dirt = new GridBlock("Dirt", 2, Bank.dirt).setSolid(true);
	public static final GridBlock grass = new GridBlock("Grass", 3, Bank.grass).setSolid(true);
	public static final GridBlock log = new GridBlock("Tree Log", 4, Bank.log).setSolid(true);
	public static final GridBlock leaves = new GridBlock("Tree Leaves", 5, Bank.leaves).setSolid(true);
	public static final GridBlock water = new GridBlock("Water", 6, Bank.water).setSolid(false);
	public static final GridBlock planks = new GridBlock("Wood Planks", 7, Bank.planks).setSolid(true);
	public static final GridBlock stonebrick = new GridBlock("Stone Bricks", 8, Bank.brick).setSolid(true);
	public static final GridBlock sand = new GridBlock("Sand", 9, Bank.sand).setSolid(true);
	public static final GridBlock cactus = new GridBlock("Cactus", 10, Bank.cactus).setSolid(true);
	public static final GridBlock snow = new GridBlock("Snow", 11, Bank.snow).setSolid(true);
	public static final GridBlock pillar = new GridBlock("Pillar", 12, Bank.pillar).setSolid(false);
	public static final GridBlock planks1 = new GridBlock("Dark Planks", 13, Bank.planks1).setSolid(true);
	public static final GridBlock stonebrick1 = new GridBlock("Dark Bricks", 14, Bank.brick1).setSolid(false);
	public static final GridBlock dirt1 = new GridBlock("Dark Dirt", 15, Bank.dirt1).setSolid(false);
	public static final GridBlock rocks = new GridBlock("Rock Pile", 16, Bank.rocks).setSolid(false);
	public static final GridBlock rampRight = new GridBlock("Ramp [>]", 17, Bank.rampright).setSolid(true).setShape((byte) 1);
	public static final GridBlock rampLeft = new GridBlock("Ramp [<]", 18, Bank.rampleft).setSolid(true).setShape((byte) 2);
	public static final GridBlock rampRightFlip = new GridBlock("Ramp [>v]", 19, Bank.rampright180).setSolid(true);
	public static final GridBlock rampLeftFlip = new GridBlock("Ramp [<v]", 20, Bank.rampleft180).setSolid(true);
	public static final GridBlock vine = new GridBlock("Vine", 21, Bank.vine).setSolid(false);
	public static final GridBlock ice = new GridBlock("Ice", 22, Bank.ice).setSolid(true);
	public static final GridBlock roughbrick = new GridBlock("Large Brick", 23, Bank.roughbrick).setSolid(true);
	public static final GridBlock bloodblock = new GridBlock("Brynthln", 24, Bank.bloodbrick).setSolid(true);
	public static final GridBlock ladder = new GridBlock("Ladder", 25, Bank.ladder).setSolid(false);
	public static final GridBlock boundary = new GridBlock("Boundary", 26, null).setSolid(true);
	public static final GridBlock fire = new GridBlock("Fire", 27, Bank.fire).setSolid(false);
	public static final GridBlock torchstand = new GridBlock("Torch Stand", 28, Bank.torchbottom).setSolid(false);
	public static final GridBlock torchtop = new GridBlock("Torch Top", 29, Bank.torchtop).setSolid(false);
	public static final GridBlock luna = new GridBlock("Cosmic Cube", 30, Bank.lunablock).setSolid(true);
	public static final GridBlock moon = new GridBlock("Moonstone", 31, Bank.moonblock).setSolid(true);
	public static final GridBlock sandstone = new GridBlock("Sandstone", 32, Bank.sandstone).setSolid(true);
	public static final GridBlock sandstoneRough = new GridBlock("Rough Sandstone", 33, Bank.sandstonerough).setSolid(true);
	public static final GridBlock rayblock = new GridBlock("Ray Block", 34, Bank.moonlight).setSolid(true);
	public static final GridBlock jungledirt = new GridBlock("Jungle Dirt", 35, Bank.jungledirt).setSolid(true);
	public static final GridBlock junglegrass = new GridBlock("Jungle Grass", 36, Bank.junglegrass).setSolid(true);
	public static final GridBlock junglewood = new GridBlock("Jungle Wood", 37, Bank.junglewood).setSolid(true);
	public static final GridBlock jungleleaves = new GridBlock("Jungle Leaves", 38, Bank.jungleleaves).setSolid(true);
	public static final GridBlock mushroom = new GridBlock("Red Mushroom", 39, Bank.mushroom).setSolid(false);
	public static final GridBlock weeds = new GridBlock("Dense Weeds", 40, Bank.weeds).setSolid(false);
	public static final GridBlock rope = new GridBlock("Rope", 41, Bank.rope).setSolid(false);
	public static final GridBlock fabric = new GridBlock("Fabric", 42, Bank.fabric).setSolid(true);
	public static final GridBlock fence = new GridBlock("Fence Top", 43, Bank.fence).setSolid(false);
	public static final GridBlock fenceBottom = new GridBlock("Fence Bottom", 44, Bank.fenceBottom).setSolid(false);
	public static final GridBlock torchLeft = new GridBlock("Torch Base <", 45, Bank.torchLeft).setSolid(false);
	public static final GridBlock torchRight = new GridBlock("Torch Base >", 46, Bank.torchRight).setSolid(false);
	public static final GridBlock shield = new GridBlock("Shield", 47, Bank.shield).setSolid(false);
	
	public GridBlock(String name, int id, Image img){
		this.id = (byte) id;
		this.setName(name);
		this.image = img;
		Grid.imgs[id] = img;
		all[id] = this;
	}
	
	public Image getImage(){
		return image;
	}
	
	public GridBlock setImage(BufferedImage img){
		this.image = img;
		return this;
	}

	private GridBlock setPlayerSolid(boolean f){
		psolid = f;
		return this;
	}
	
	private GridBlock setObjSolid(boolean f){
		osolid = f;
		return this;
	}
	
	private GridBlock setSolid(boolean f){
		psolid = f;
		osolid = f;
		return this;
	}
	
	public boolean playerSolid(){
		return psolid;
	}
	
	public boolean objSolid(){
		return osolid;
	}
	
	public int getID(){
		return this.id;
	}

	public void onUpdate(Grid grid, int x, int i) {
	}

	public boolean getBreakable() {
		return canBreak;
	}

	public void onBreak(Player p, Grid grid, int x, int y) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getShape() {
		return shape;
	}

	public GridBlock setShape(byte i) {
		this.shape = i;
		return this;
	}
}
