package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Grid {
	public static int SMALL = 0, MEDIUM = 1, LARGE = 2, ENORMOUS = 3, MAX = 4;
	private int  tileSize = 30;
	public boolean growing = false;
	private byte[][] core;
	GridPanel p;
	static Image[] imgs = new Image[250];
	public Grid(int size, GridPanel p){
		this.p = p;
		int gridScale = size == 0 ? 256 : size == 1 ? 512 : size == 2 ? 768 : size == 3 ? 1024 : size == 4 ? Integer.MAX_VALUE : size;
		setCore(new byte[gridScale][gridScale]);
	}
	
	public Grid(int w, int h, GridPanel p){
		System.out.println("> PR-PROGRESS-LOG: Constructing grid of "+w+"x"+h);
		core = new byte[w][h];
		core[0] = new byte[w];
		core[1] = new byte[h];
		//renderview = new byte[Properties.width/tileSize+10+Properties.renderdist][Properties.height/tileSize+10+Properties.renderdist];
		//renderview[0] = new byte[Properties.width/tileSize+10+Properties.renderdist];
		//renderview[1] = new byte[Properties.height/tileSize+10+Properties.renderdist];
		System.out.println("> PR-PROGRESS-LOG: Construction grid of "+core[0].length+"x"+core[1].length+" completed");
		this.p = p;
	}
	
	/*public int getTileID(int x, int y){
		if(x+p.scrollX>0&&x+p.scrollX<getCore()[0].length*tileSize&&y+p.scrollY>0&&y+p.scrollY<getCore()[1].length*tileSize)return getCore()[(x+p.scrollX)/tileSize][(y+p.scrollY)/tileSize];else return 0;
	}*/
	
	public int getTileID(int x, int y){
		if(x>=0&&x/tileSize<getCore()[0].length&&y>=0&&y/tileSize<getCore()[1].length){
			return getCore()[(x)/tileSize][(y)/tileSize];
		}else return 0;
	}
	
	public byte getTileIDWithScroll(int x, int y) {
		if((x+p.scrollX)/tileSize < 0 || (y+p.scrollY)/tileSize < 0)return 0;else
		return (x<0||x>core[0].length*tileSize||y<0||y>core[1].length*tileSize)?0:getCore()[(x+p.scrollX)/tileSize][(y+p.scrollY)/tileSize];
	}
	
	public int getGridRelativeTileID(int x, int y){
		if(x+p.scrollX/tileSize>0&&x+p.scrollX/tileSize<getCore()[0].length&&y+p.scrollY/tileSize>0&&y+p.scrollY/tileSize<getCore()[1].length)return getCore()[x+p.scrollX/tileSize][y+p.scrollY/tileSize];else return 0;
	}
	
	public void setTileID(int x, int y, byte tileID){
		if(x>0&&x<getCore()[0].length*tileSize&&y>0&&y<getCore()[1].length*tileSize)getCore()[x/getTileSize()][y/getTileSize()] = tileID;
	}
	
	public void setGridRelativeTileIDWithScroll(int x, int y, byte tileID){
		if(x+p.scrollX/tileSize-p.scrollX%tileSize>0&&x+p.scrollX/tileSize-p.scrollX%tileSize<getCore()[0].length&&y+p.scrollY/tileSize-p.scrollY%tileSize>0&&y+p.scrollX/tileSize-p.scrollY%tileSize<getCore()[1].length)getCore()[x+p.scrollX/tileSize-p.scrollX%tileSize][y+p.scrollY/tileSize-p.scrollY%tileSize] = tileID;
	}
	
	public void setGridRelativeTileID(int x, int y, byte tileID){
		if(x>0&&x<getCore()[0].length&&y>0&&y<getCore()[1].length)getCore()[x][y] = tileID;
	}
	
	public void setGridRelativeTileIDNotify(int x, int y, byte tileID){
		if(x>0&&x<getCore()[0].length&&y>0&&y<getCore()[1].length){getCore()[x][y] = tileID;
		this.updateSurroundingGridRelative(x, y);}
	}
	
	public Rectangle formTileBox(int x, int y, int xTiles, int yTiles){
		Rectangle ret = new Rectangle();
		boolean b = false;
		byte shape = 0;
		//if(GridBlock.all[this.getTileID(x, y)].playerSolid())
		for(int i = 0; i < xTiles; ++i){
			for(int j = 0; j < yTiles; ++j){
				if(this.getTileID(x+p.scrollX+i*tileSize, y+p.scrollY+j*tileSize) > 0 && GridBlock.all[this.getTileID(x+i*tileSize+p.scrollX, y+j*tileSize+p.scrollY)].playerSolid())b = true;
				shape = GridBlock.all[this.getTileID(x+i*tileSize+p.scrollX, y+j*tileSize+p.scrollY)].getShape();
			}
		}
		if(b)
		ret.setBounds(x-x%tileSize+(shape>0?tileSize/2:0), y-y%tileSize+(shape>0?tileSize/2:0), tileSize*xTiles-(shape>0?tileSize/2:0), tileSize*yTiles-(shape>0?tileSize/2:0));
		return ret;
	}
	
	/*public void render(Graphics g, GridPanel p){
		for(int i = 0; i < (Properties.width)/tileSize+1; ++i){
			for(int j = 0; j < Properties.height/tileSize+1; ++j){
				if(i > 0 && i < this.core[0].length && j > 0 && j < this.core[1].length){
					if(getCore()[i][j]>0&&GridBlock.all[getCore()[i][j]]!=null){
						if(GridBlock.all[getCore()[i][j]].getImage()!=null){
							g.drawImage(GridBlock.all[getCore()[i][j]].getImage(), i*getTileSize()+p.scrollX, j*getTileSize()+p.scrollY, this.getTileSize(), this.getTileSize(), null);
						}
					}
				}
			}
		}
	}*/
	
	public void render(Graphics g, GridPanel p){
		for(int i = p.scrollX/tileSize; i < (p.scrollX+Properties.width)/tileSize+1; ++i){
			for(int j = p.scrollY/tileSize; j < (p.scrollY+Properties.height)/tileSize+1; ++j){
				if(i>-1&&j>-1)
				if(getCore()[i][j]>0&&getCore()[i][j]!=26){
					g.drawImage(imgs[getCore()[i][j]], i*getTileSize()-p.scrollX, j*getTileSize()-p.scrollY, this.getTileSize(), this.getTileSize(), null);
					g.setColor(Color.GREEN);
					g.fillRect(i-i%tileSize, j-j%tileSize, tileSize*1, tileSize*1);
				}
			}	
		}
	}
	
	public void render(Graphics g, GridPanel p, Color c){
		g.setColor(c);
		for(int i = p.scrollX/tileSize; i < (p.scrollX+Properties.width)/tileSize+1; ++i){
			for(int j = p.scrollY/tileSize; j < (p.scrollY+Properties.height)/tileSize+1; ++j){
				if(i>-1&&j>-1)
				if(getCore()[i][j] > 0&&getCore()[i][j]!=26){
					g.drawImage(imgs[getCore()[i][j]], i*getTileSize()-p.scrollX, j*getTileSize()-p.scrollY, this.getTileSize(), this.getTileSize(), null);
					g.fillRect(i*getTileSize()-p.scrollX, j*getTileSize()-p.scrollY, this.getTileSize(), this.getTileSize());
				}
			}
		}
	}
	
	public void render(Graphics g, GridPanel p, int ts){
		for(int i = 0; i < (Properties.width)/ts+1; ++i){
			for(int j = 0; j < (Properties.height)/ts+1; ++j){
				if(i > 0 && i < this.core[0].length && j > 0 && j < this.core[1].length){
					if(getCore()[i][j]>0&&getCore()[i][j]!=26){
						if(GridBlock.all[getCore()[i][j]].getImage()!=null){
							g.drawImage(imgs[getCore()[i][j]], i*ts, j*ts, ts, ts, null);
						}
					}
				}
			}
		}
	}
	
	public void render(Graphics g, GridPanel p, int ts, Color c){
		g.setColor(c);
		for(int i = 0; i < (Properties.width)/ts+1; ++i){
			for(int j = 0; j < (Properties.height)/ts+1; ++j){
				if(i > 0 && i < this.core[0].length && j > 0 && j < this.core[1].length){
					if(getCore()[i][j]>0&&getCore()[i][j]!=26){
						if(GridBlock.all[getCore()[i][j]].getImage()!=null){
							g.drawImage(imgs[getCore()[i][j]], i*ts, j*ts, ts, ts, null);
							g.fillRect(i*ts, j*ts, ts, ts);
						}
					}
				}
			}
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public byte[][] getCore() {
		return core;
	}

	public void setCore(byte[][] core) {
		this.core = core;
	}
	
	public String save(PanelBase pan, String m){
			System.out.println("> PR-PROGRESS-LOG: Save initiated...");
			saving = true;
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < getCore()[0].length; ++i){
				for(int j = 0; j < getCore()[1].length; ++j){	
					if(getCore()[i][j]!=0)sb.append(i+":"+j+":"+getCore()[i][j]+(";"));
				}
			}
			String s = sb.toString();
			saving = false;
			System.out.println("> PR-PROGRESS-LOG: Save complete.");
			return (s.isEmpty()||s.equals(""))?"0":sb.toString();
	}
	
	public void load(String worldname, int x){
		File file = new File(Bank.path+"maps/"+worldname+".HB");
		FileReader fw;
		System.out.println("Attempting to load grid #"+x+" from "+Bank.path+"maps/"+worldname+".HB");
		String line = null;
		try {
			fw = new FileReader(file);
			BufferedReader bw = new BufferedReader(fw);
			line = bw.readLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		load(line, x, true);
	}
	
	public void load(String dat, int x, boolean isString){
		String[] data = null;
		String line = dat.split("%")[x];
		if(line.length() > 1){
			data = line.split(";");
			String[] stra = data[data.length-1].split(":");
			this.core = new byte[800][600];
			for(int i = 0; i < data.length; ++i){
				String[] str = data[i].split(":");
				this.setGridRelativeTileID(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Byte.parseByte(str[2]));
			}
		}
	}
	
	public void loadRaw(String path, GridPanel pan){
		File file = new File(path+"/tiles.dat");
		//File[] playerfiles = new File(Bank.path+worldname+"/players/").listFiles();
		FileReader fw;
		String[] data = null;
		try {
			fw = new FileReader(file);
			BufferedReader bw = new BufferedReader(fw);
			data = bw.readLine().split(";");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] stra = data[data.length-1].split(":");
		pan.grid = new Grid(5000, 80, p);
		for(int i = 0; i < data.length; ++i){
			String[] str = data[i].split(":");
			pan.grid.setGridRelativeTileID(Integer.parseInt(str[0]), Integer.parseInt(str[1]), Byte.parseByte(str[2]));
		}
	}
	
	public int getGreatest(String[] data, int x){
		int r = 0;
		for(int i = 0; i < data.length; ++i){
			String[] str = data[i].split(":");
			if(Integer.parseInt(str[x]) > r)r = Integer.parseInt(str[x]);
		}
		return r;
	}
	
	Random rand = new Random();
	public boolean saving = false;
	
	public void breakTile(Player p, int x1, int y1){
		int x=x1/tileSize,y=y1/tileSize;
		if(GridBlock.all[this.getGridRelativeTileID(x, y)].getBreakable())
		GridBlock.all[this.getGridRelativeTileID(x, y)].onBreak(p, this, x, y);
		GridBlock.all[this.getGridRelativeTileID(x+1, y)].onUpdate(this, x+1, y);
		GridBlock.all[this.getGridRelativeTileID(x-1, y)].onUpdate(this, x-1, y);
		GridBlock.all[this.getGridRelativeTileID(x, y+1)].onUpdate(this, x, y+1);
		GridBlock.all[this.getGridRelativeTileID(x, y-1)].onUpdate(this, x, y-1);
	}
	
	public void breakGridRelativeTile(Player p, int x, int y){
		if(GridBlock.all[this.getGridRelativeTileID(x, y)].getBreakable())
		GridBlock.all[this.getGridRelativeTileID(x, y)].onBreak(p, this, x, y);
		GridBlock.all[this.getGridRelativeTileID(x+1, y)].onUpdate(this, x+1, y);
		GridBlock.all[this.getGridRelativeTileID(x-1, y)].onUpdate(this, x-1, y);
		GridBlock.all[this.getGridRelativeTileID(x, y+1)].onUpdate(this, x, y+1);
		GridBlock.all[this.getGridRelativeTileID(x, y-1)].onUpdate(this, x, y-1);
	}
	
	public void updateSurroundingGridRelative(int x, int y){
		GridBlock.all[this.getGridRelativeTileID(x, y)].onUpdate(this, x, y);
		GridBlock.all[this.getGridRelativeTileID(x+1, y)].onUpdate(this, x+1, y);
		GridBlock.all[this.getGridRelativeTileID(x-1, y)].onUpdate(this, x-1, y);
		GridBlock.all[this.getGridRelativeTileID(x, y+1)].onUpdate(this, x, y+1);
		GridBlock.all[this.getGridRelativeTileID(x, y-1)].onUpdate(this, x, y-1);
	}
	
	public void causeExplosion(int x, int y, int s){
		this.setGridRelativeTileID(x, y, (byte) 0);
		for(int i = 0; i < rand.nextInt(3)+s; ++i){
			this.setGridRelativeTileID(x+(rand.nextInt(i+1)-rand.nextInt(i+1)), y+(rand.nextInt(i+1)-rand.nextInt(i+1)), (byte) 0);
			this.setGridRelativeTileID(x+(rand.nextInt(i+1)-rand.nextInt(i+1)), y+(rand.nextInt(i+1)-rand.nextInt(i+1)), (byte) 0);
			//Util.renderLightning(x-20, y, x+20, y, 180, Display.frame.p.getGraphics());
		}
	}
}
