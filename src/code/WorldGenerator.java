package code;

import java.awt.image.BufferedImage;
import java.util.Random;

public class WorldGenerator {
	
	Random rand = new Random();
	int x = 0;
	int y = Properties.height/30-6;
	int lastType = 0;
	int minChange = 4;
	int lastChange = 0;

	public void generate(GridPanel p, int dist){
		for(int i = 0; i < Properties.height/p.tileSize; ++i){
			p.grid.setGridRelativeTileID(1, i, (byte) GridBlock.boundary.getID());
			p.grid.setGridRelativeTileID(2, i, (byte) GridBlock.boundary.getID());
			p.grid.setGridRelativeTileID(dist-1, i, (byte) GridBlock.boundary.getID());
			p.grid.setGridRelativeTileID(dist-2, i, (byte) GridBlock.boundary.getID());
		}
		while(x < dist){
			genRandom(p);
		}
	}
	
	public BufferedImage getBG(int t){
		int r = 0;
		BufferedImage tex = null;
		if(t==0||t==2){
			r = rand.nextInt(3);
			tex = (r == 0 ? Bank.hills : r == 1 ? Bank.hillsRoll : r == 2 ? Bank.hillsSnow : Bank.hills);
		}
		if(t==1)tex=Bank.hillsSand;
		if(t==3)tex=Bank.hillsHell;
		if(t==4)tex=Bank.hillsSnow;
		if(t==5)tex=Bank.hills;
		if(t==6)tex=Bank.hillsMoon;
		return tex;
	}
	
	public BufferedImage getSky(int t){
		int r = rand.nextInt(2);
		BufferedImage tex = Bank.sky;
		if(r==0)tex=Bank.sky;
		if(r==1)tex=Bank.skyDark;
		if(t==3)tex=Bank.skyRed;
		return tex;
	}
	
	public Weather getWeather(int t){
		Weather ret = null;
		if(t==0||t==2){
			if(rand.nextInt(8)==0)ret=Weather.rain;
			if(rand.nextInt(20)==0)ret=Weather.storm;
		}
		if(t==1){
			if(rand.nextInt(7)==0)ret=Weather.sandstorm;
		}
		if(t==4){
			if(rand.nextInt(7)==0)ret=Weather.snow;
		}
		return ret;
	}
	
	public void genRandom(GridPanel p){
		Grid grid = p.grid;
		Grid scene = p.scene;
		Grid fore = p.fore;
		int r = rand.nextInt(6);
		System.out.println("Generating chunk with GenID "+r);
		int dist = 0;
		dist = 50+rand.nextInt(51);
		if(lastType<5)r=5;
		if(lastType==5)r=rand.nextInt(7);
		if(x==0)r=rand.nextInt(2);
		lastType = r;
		Biome b = new Biome(x, dist, getBG(r), getSky(r), getWeather(r));
		p.biomes.add(b);
		if(r!=5){
			for(int i = 0; i < dist; ++i){
				byte id = (byte) (r==0?(GridBlock.dirt.getID()):r==1?GridBlock.sand.getID():r==2?GridBlock.stone.getID():r==3?GridBlock.bloodblock.getID():r==4?GridBlock.snow.getID():GridBlock.dirt.getID());
				for(int j = 0; j < 20; ++j){
					id = (byte) (r==0?(j==0?GridBlock.grass.getID():GridBlock.dirt.getID()):r==1?GridBlock.sand.getID():r==2?GridBlock.stone.getID():r==3?GridBlock.bloodblock.getID():r==4?GridBlock.snow.getID():r==6?GridBlock.moon.getID():GridBlock.dirt.getID());
					grid.setGridRelativeTileID(x+i, y+j, id);
				}
				if(r==2){
					if(rand.nextInt(30)==0){
						int w = 4+rand.nextInt(5), h = 7+rand.nextInt(6), gr = rand.nextInt(2);
						for(int j = 0; j < h; ++j){
							if(gr==0){
								grid.setGridRelativeTileID(x+i-1, y-j, (byte) GridBlock.ladder.getID());
								scene.setGridRelativeTileID(x+i-1, y-j, (byte) GridBlock.stone.getID());
							}
							for(int k = 0; k < w; ++k){
								Grid g = gr==0?grid:scene;
								g.setGridRelativeTileID(x+i+k, y-j, id);
								if(rand.nextInt(10)==0&&w>1)--w;
							}
						}
					}
					if(rand.nextInt(20)==0){
						int lx = x+i , ly = y+rand.nextInt(5);
						for(int j = 0; j < 15+rand.nextInt(20); ++j){
							if(grid.getGridRelativeTileID(lx, ly)!=0){
								grid.setGridRelativeTileID(lx, ly, (byte) GridBlock.air.getID());
								scene.setGridRelativeTileID(lx, ly, (byte) GridBlock.roughbrick.getID());
								lx+=(rand.nextInt(2)-rand.nextInt(2));
								ly+=(rand.nextInt(2)-rand.nextInt(2));
							}
						}
					}
				}
				if(r==4){
					if(rand.nextInt(30)==0){
						int sy = rand.nextInt(Properties.height/60);
						int lx = x+i , ly = sy;
						for(int j = 0; j < 8+rand.nextInt(10); ++j){
							grid.setGridRelativeTileID(lx, ly, (byte) GridBlock.ice.getID());
							lx+=(rand.nextInt(2)-rand.nextInt(2));
							ly+=(rand.nextInt(2)-rand.nextInt(2));
						}
						for(int j = 0; j < Properties.height/30; ++j){
							scene.setGridRelativeTileID(x+i+Util.dif(x+i, lx)/2-1, sy+j, (byte) GridBlock.ice.getID());
						}
					}
					if(rand.nextInt(20)==0){
						int lx = x+i , ly = y, gr = rand.nextInt(2);
						for(int j = 0; j < 15+rand.nextInt(20); ++j){
							if(grid.getGridRelativeTileID(lx, ly)!=0){
								grid.setGridRelativeTileID(lx, ly, (byte) GridBlock.air.getID());
								(gr==0?grid:scene).setGridRelativeTileID(lx, ly, (byte) GridBlock.ice.getID());
								lx+=(rand.nextInt(2)-rand.nextInt(2));
								ly+=(rand.nextInt(2)-rand.nextInt(2));
							}
						}
					}
				}
				if(r==6){
					if(rand.nextInt(6)==0){
						int lx = x+i , ly = y, gr = rand.nextInt(2);
						for(int j = 0; j < 15+rand.nextInt(20); ++j){
							if(grid.getGridRelativeTileID(lx, ly)!=0){
								grid.setGridRelativeTileID(lx, ly, (byte) GridBlock.air.getID());
								(gr==0?grid:scene).setGridRelativeTileID(lx, ly, (byte) GridBlock.luna.getID());
								lx+=(rand.nextInt(2)-rand.nextInt(2));
								ly+=(rand.nextInt(2)-rand.nextInt(2));
							}
						}
					}
				}
				if(r==3){
					if(rand.nextInt(7)==0){
						grid.setGridRelativeTileID(x+i, y-1, (byte) GridBlock.fire.getID());
					}
					if(rand.nextInt(14)==0){
						int height = 6+rand.nextInt(7);
						for(int j = 0; j < height; ++j){
							scene.setGridRelativeTileID(x+i, y-j, (byte) GridBlock.bloodblock.getID());
						}
						grid.setGridRelativeTileID(x+i, y-height, (byte) GridBlock.bloodblock.getID());
						grid.setGridRelativeTileID(x+i, y-height-1, (byte) GridBlock.fire.getID());
					}
				}
				if(r==0){
					if(rand.nextInt(11)==0){
						grid.setGridRelativeTileID(x+i, y-1, (byte) GridBlock.rocks.getID());
					}
					if(rand.nextInt(2)==0){
						grid.setGridRelativeTileID(x+i, y-1, (byte) GridBlock.weeds.getID());
					}
					if(rand.nextInt(20)==0){
						grid.setGridRelativeTileID(x+i, y-1, (byte) GridBlock.mushroom.getID());
					}
					if(rand.nextInt(9)==0){
						int height = 8+rand.nextInt(6);
						for(int j = 0; j < height; ++j){
							scene.setGridRelativeTileID(x+i, y-j, (byte) GridBlock.log.getID());
						}
						for(int j = 0; j < 5; ++j)
						fore.setGridRelativeTileID(x+i-2+j, y-height+1, (byte) GridBlock.leaves.getID());
						for(int j = 0; j < 3; ++j)
						fore.setGridRelativeTileID(x+i-1+j, y-height, (byte) GridBlock.leaves.getID());
						fore.setGridRelativeTileID(x+i, y-height-1, (byte) GridBlock.leaves.getID());
					}
				}
				if(r==1){
					if(rand.nextInt(7)==0){
						for(int j = 0; j < 3+rand.nextInt(3); ++j){
							scene.setGridRelativeTileID(x+i, y-j, (byte) GridBlock.cactus.getID());
						}
					}
					if(rand.nextInt(15)==0){
						grid.setGridRelativeTileID(x+i, y-1, (byte) GridBlock.rocks.getID());
					}
				}
				if(rand.nextInt(250)==0){
					int w = 4+rand.nextInt(7);
					int h = 15+rand.nextInt(11);
					for(int j = 0; j < w+4; ++j){
						grid.setGridRelativeTileID(x+i+j-2, y-h, (byte) GridBlock.roughbrick.getID());
						grid.setGridRelativeTileID(x+i+j-2, y-h-5, (byte) GridBlock.roughbrick.getID());
						grid.setGridRelativeTileID(x+i-2, y-h-1, (byte) GridBlock.torchstand.getID());
						grid.setGridRelativeTileID(x+i-2, y-h-2, (byte) GridBlock.torchtop.getID());
						grid.setGridRelativeTileID(x+i+w+1, y-h-1, (byte) GridBlock.torchstand.getID());
						grid.setGridRelativeTileID(x+i+w+1, y-h-2, (byte) GridBlock.torchtop.getID());
					}
					for(int j = 0; j < 6; ++j){
						fore.setGridRelativeTileID(x+i, y-h-j, (byte) GridBlock.roughbrick.getID());
						fore.setGridRelativeTileID(x+i+w-1, y-h-j, (byte) GridBlock.roughbrick.getID());
					}
					for(int j = 0; j < w; ++j){
						for(int g = 0; g < 10; ++g){
							grid.setGridRelativeTileID(x+i+j, y+g, id);
						}
						for(int k = 0; k < h; ++k){
							scene.setGridRelativeTileID(x+i+j, y-k, (byte) GridBlock.roughbrick.getID());
							grid.setGridRelativeTileID(x+i+w/2-1, y-k, (byte) GridBlock.ladder.getID());
							grid.setGridRelativeTileID(x+i+w/2, y-k, (byte) GridBlock.ladder.getID());
						}
					}
				}
				if(i-lastChange >= minChange){
					if(rand.nextInt(2)==0)y++;else y--;
					lastChange = i;
				}
				if(y>=Properties.height/30-2)y=(Properties.height/30-2);
			}
		}
		/*if(r==2){
			int yy = 1, dir = 0;
			int h = 7+rand.nextInt(12);
			for(int i = 0; i < dist; ++i){
				for(int j = 0; j < 20; ++j){
					grid.setGridRelativeTileID(x+i, y+j, (byte) (r==0?(j==0?GridBlock.grass.getID():GridBlock.dirt.getID()):(GridBlock.sand.getID())));
				}
				grid.setGridRelativeTileID(x+i, y, (byte) GridBlock.stonebrick.getID());
				for(int j = 0; j < yy; ++j){
					grid.setGridRelativeTileID(x+i, y-h-j, (byte) GridBlock.stonebrick.getID());
				}
				if(dir==0){
					yy++;
					if(yy>=dist/2)dir=1;
				}else{
					yy--;
				}
				if(rand.nextInt(10) == 0){
					for(int j = 0; j < h; ++j)
					fore.setGridRelativeTileID(x+i, y-j, (byte) GridBlock.stonebrick.getID());
				}
				for(int j = 0; j < h; ++j){
					scene.setGridRelativeTileID(x+i, y-j, (byte) GridBlock.stonebrick.getID());
				}
			}
		}*/
		if(r==5){
			byte blockid = (byte) (rand.nextInt(2)==0?GridBlock.planks.getID():GridBlock.stonebrick.getID());
			for(int i = 0; i < dist; ++i){
				for(int j = 0; j < 3; ++j){
					if(j==0)grid.setGridRelativeTileID(x+i, y+j, blockid);
					scene.setGridRelativeTileID(x+i, y+j, blockid);
				}
			}
			if(blockid==GridBlock.planks.getID()){
				grid.setGridRelativeTileID(x+1, y+1, (byte) GridBlock.rampRightFlip.getID());
				grid.setGridRelativeTileID(x, y+2, (byte) GridBlock.rampRightFlip.getID());
				grid.setGridRelativeTileID(x, y+1, (byte) GridBlock.planks.getID());
				
				grid.setGridRelativeTileID(x+dist-2, y+1, (byte) GridBlock.rampLeftFlip.getID());
				grid.setGridRelativeTileID(x+dist-1, y+2, (byte) GridBlock.rampLeftFlip.getID());
				grid.setGridRelativeTileID(x+dist-1, y+1, (byte) GridBlock.planks.getID());
			}
		}
		/*if(r==4){
			int randnum = rand.nextInt(3);
			byte blockid = (byte) (randnum==0?GridBlock.dirt.getID():randnum==1?GridBlock.sand.getID():GridBlock.stonebrick.getID());
			int h = 8+rand.nextInt(7);
			for(int i = 0; i < dist; ++i){
				for(int j = 0; j < 3; ++j){
					if(j==0&&blockid==GridBlock.dirt.getID())fore.setGridRelativeTileID(x+i, y-h-3, (byte) GridBlock.grass.getID());
					else fore.setGridRelativeTileID(x+i, y-h-3+j, blockid);
				}
				for(int j = 0; j < h; ++j){
					scene.setGridRelativeTileID(x+i, y-h+j, blockid);
				}
				for(int j = 0; j < 20; ++j)grid.setGridRelativeTileID(x+i, y+j, blockid);
				y+=(rand.nextInt(2)-rand.nextInt(2));
				if(y>=Properties.height/30-2)y=(Properties.height/30-2);
			}
		}*/
		x+=dist;
	}
}
