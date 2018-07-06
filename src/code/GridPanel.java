package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class GridPanel extends PanelBase{
	int scrollX = 0, scrollY = 0, scrollTime = 800;
	public Grid grid, scene, fore;
	public String name = "World";
	boolean renderScene = true, renderGrid = true, renderForeground = false;
	ArrayList<Point> spawns = new ArrayList<Point>();
	long scrollStart = -1;
	int tileSize = 30;
	boolean transRev = false;
	
	public GridPanel(Grid g){
		this.grid = new Grid(g.getCore()[0].length, this);
		this.scene = new Grid(g.getCore()[0].length, this);
		this.fore = new Grid(g.getCore()[0].length, this);
	}
	public abstract void onUpdate();
	public final void drawScreen(Graphics g){
		if(scrollYQueue>0){
			int amt = scrollYQueue/50;
			scrollY+=amt;
			scrollYQueue-=amt;
		}
		if(scrollYQueue<0){
			int amt = Math.abs(scrollYQueue)/50;
			scrollY-=amt;
			scrollYQueue+=amt;
		}
		this.renderScene(g);
		if(biomeTransition>-1){
			int a = (int)((System.currentTimeMillis()-biomeTransition) * 500 / 300);
			if(a>=250&!transRev)transRev = true;
			if(a>=500){
				transRev = false;
				biomeTransition=-1;
			}
			Color c = new Color(255,255,255,(transRev?(255-a/2):(a))>250?250:transRev?(255-a/2):(a));
			g.setColor(c);
			g.fillRect(0, 0, Properties.width, Properties.height);
		}
		/*if(renderScene)this.scene.render(g,this,Util.transparent);
		if(renderGrid)this.grid.render(g,this);
		if(renderForeground)this.fore.render(g,this);*/
		renderGrids(g);
		renderForeground(g);
	}
	public abstract void renderScene(Graphics g);
	public void renderForeground(Graphics g){}
	public void renderGrids(Graphics g){
		int scrollX = this.scrollX;
		int scrollY = this.scrollY;
		g.setColor(Util.transparent);
		for(int i = scrollX/tileSize; i < (scrollX+Properties.width)/tileSize+1; ++i){
			for(int j = scrollY/tileSize; j < (scrollY+Properties.height)/tileSize+1; ++j){
				for(int k = 0; k < (renderForeground?3:2); ++k){
					byte[][] core = (k == 0 ? scene.getCore() : k == 1 ? grid.getCore() : fore.getCore());
					if(i > 0 && i < core[0].length && j > 0 && j < core[1].length){
						if(core[i][j]>0&&GridBlock.all[core[i][j]]!=null){
							if(GridBlock.all[core[i][j]].getImage()!=null){
								g.drawImage(GridBlock.all[core[i][j]].getImage(), i*tileSize-scrollX, j*tileSize-scrollY, this.tileSize, this.tileSize, null);
								if(k==0){
									g.fillRect(i*tileSize-scrollX, j*tileSize-scrollY, this.tileSize, this.tileSize);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public Grid getGrid(){
		return this.grid;
	}
	public Grid getScene(){
		return this.scene;
	}
	public Grid getForeground(){
		return this.fore;
	}
	public void save(String n){
		String save = "0%";
		File file = new File(Bank.path+"maps/"+n+".HB");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		save+=grid.save(this, "")+"%";
		save+=scene.save(this, "")+"%";
		save+=fore.save(this, "")+"%";
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(save);
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void load(String name, boolean str){
		grid.load(name, 1, str);
		scene.load(name, 2, str);
		fore.load(name, 3, str);
		loadDat(name, str);
		this.name = name;
	}
	
	public void load(String name){
		grid.load(name, 1);
		scene.load(name, 2);
		fore.load(name, 3);
		loadDat(name, false);
		this.name = name;
	}
	
	public void loadDat(String n, boolean str){
		ArrayList<Point> ret = new ArrayList<Point>();
		String s = (str?n:(Bank.path+"maps/"+n+".HB"));
		String spl = s.split("%")[0];
		if(spl!=null){
			String[] dats = spl.split(";");
			if(dats!=null){
				for(int i = 0; i < dats.length; ++i){
					if(dats[i]!=null){
						if(dats[i].contains("=")){
							String cmd = dats[i].split("=")[0];
							String[] args = dats[i].split("=")[1].split(",");
							if(cmd.equalsIgnoreCase("addspawn")){
								ret.add(new Point(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
							}
							if(cmd.equalsIgnoreCase("setbg")){
								this.bg = Map.getBG(Integer.parseInt(args[0]));
							}
							if(cmd.equalsIgnoreCase("setsky")){
								this.sky = Map.getSky(Integer.parseInt(args[0]));
							}
						}
					}
				}
			}
		}
		this.spawns = ret;
	}
}
