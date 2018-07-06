package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

public class Particle extends Object{
	
	static int SHARDS = 0, DUST = 1, SPARKLE = 2, GOO = 3, LIQUID = 4, CHARGED = 5, POOF = 6, GAS = 7, ZIP = 8, EXPLODE = 9, SNOW = 10, NOVA = 100;
	int physics = 0;
	long start = System.currentTimeMillis();
	Polygon shardpoly;
	int motionX = 0, motionY = 0;
	int[] xpoints = new int[rand.nextInt(20)+3];
	int[] ypoints = new int[xpoints.length];
	Color color;
	private int alpha = 255, speed = 7;
	boolean canDrop = true, basePhysics = true;
	public int maxTime = -1;
	boolean isBlood = false;
	int bloodDir = -1;
	Image img = null;

	public Particle(int x, int y, int phys, Color c) {
		super(x, y, 0, 0);
		this.phys = new PhysicsPlug(this);
		setCanFall(false);
		if(c==null)color=new Color(rand.nextFloat(),rand.nextFloat(),rand.nextFloat());else color = c;
		width = rand.nextInt(10)+3;
		height = width;
		this.physics = phys;
		this.solid = false;
		if(this.physics == SHARDS){
			for(int i = 0; i < xpoints.length; ++i){
				xpoints[i] = getPosX()+width/2+(rand.nextInt(width*2)-rand.nextInt(width*2));
				ypoints[i] = posY+height/2+(rand.nextInt(height*2)-rand.nextInt(height*2));
			}
			shardpoly = new Polygon(xpoints, ypoints, xpoints.length);
		}
		if(physics == LIQUID){
			width = rand.nextInt(10)+3;
			height = width+(width/3*2);
			width/=2;
		}
		if(physics == EXPLODE){
			int r = rand.nextInt(20)+8;
			this.width = r;
			this.height = r;
			motionX=(rand.nextInt(500)-rand.nextInt(500));
			motionY=(rand.nextInt(500)-rand.nextInt(500));
		}
		if(physics == CHARGED){
			motionX=(rand.nextInt(1000)-rand.nextInt(1000));
			motionY=(rand.nextInt(1000)-rand.nextInt(1000));
		}
		if(physics == DUST){
			motionX=(rand.nextInt(40)-rand.nextInt(40));
			motionY=rand.nextInt(50);
		}
	}
	
	public Particle setVelocity(int x, int y){
		this.motionX = x;
		this.motionY = y;
		return this;
	}
	
	public Particle(int x, int y, int phys, Color c, int size) {
		this(x, y, phys, c);
		width = rand.nextInt(size)+3;
		height = width;
		this.physics = phys;
		if(physics == LIQUID){
			width = rand.nextInt(size)+3;
			height = width+(width/3*2);
			width/=2;
		}
	}

	public void update(double delta) {
		if(!basePhysics)phys.execute(delta);
		if(isBlood){
			if(bloodDir==-1){
				if(collides()){
					phys.motionX = 0;
					phys.motionY = 0;
					if(Display.currentScreen.gridEnabled){
						if(Display.currentScreen.getGrid().getTileID(posX, posY+30) == 0){
							bloodDir = 1;
						}else{
							if(Display.currentScreen.getGrid().getTileID(posX+30, posY) != 0 || Display.currentScreen.getGrid().getTileID(posX-30, posY) != 0)
							bloodDir = 0;
							else bloodDir = 1;
						}
					}
				}
			}
			if(System.currentTimeMillis()-start >= 2000)kill();
		}
		if(maxTime > -1){
			if(System.currentTimeMillis()-start >= maxTime)kill();
		}
		if(!canDrop){
			if(phys.motionY<=0)kill();
		}
		if(physics==SNOW){
			int pow = rand.nextInt(50)+20;
			if(phys.motionX==0)phys.motionX=(rand.nextInt(2)==0?pow:-pow);
			phys.motionY = -5;
		}
		if(phys.motionX==0&&phys.motionY==0&!basePhysics&!isBlood&physics!=SNOW)kill();
		if(basePhysics){
			if(physics == POOF){
				motionX+=(rand.nextInt(2)-rand.nextInt(2));
				motionY+=(rand.nextInt(2)-rand.nextInt(2));
				if(System.currentTimeMillis() >= start+1200){
					kill();
				}
			}
			if(physics == ZIP){
				motionX+=(rand.nextInt(2)-rand.nextInt(2));
				motionY+=speed;
				if(posY<=0){
					kill();
				}
			}
			if(physics == EXPLODE){
				if(System.currentTimeMillis() >= start+1000 || (motionY==0&&motionX==0)){
					kill();
				}
			}
			if(physics == CHARGED){
				if(getPosX() >= Properties.width)motionX+=200;
				if(getPosX() <= -width)motionX-=200;
				if(posY >= Properties.height)motionY+=200;
				if(posY <= -height)motionY-=200;
				motionX+=(rand.nextInt(60)-rand.nextInt(60));
				motionY+=(rand.nextInt(60)-rand.nextInt(60));
				if(rand.nextInt(300) == 0 && !Display.currentScreen.clicking){
					kill();
				}
			}
			if(physics == GOO){
				if(rand.nextInt(30) == 0){
					height+=speed*2;
				}
			}
			if(physics == LIQUID){
				if(posY >= Properties.height-80-height/2){
					if(rand.nextInt(100) == 0)height-=speed;
					if(rand.nextInt(30) == 0){
						width+=speed*4;
						moveLeft(speed*2);
					}
				}else{
					posY+=speed;
				}
			}
			if(physics == SPARKLE){
				if(motionX == 0){
					motionX  = rand.nextInt(30)-rand.nextInt(30);
				}
				if(rand.nextInt(100) == 0 && width > 0){
					--width;
					--height;
				}
				if(height <= 1 || width <= 1){
					kill();
				}
				if(posY >= Properties.height){
					kill();
				}
				if(rand.nextInt(2)==0)posY+=speed;
			}
			if(physics == SHARDS){
				for(int i = 0; i < xpoints.length; ++i){
					xpoints[i] = getPosX()+width/2+(rand.nextInt(width*2)-rand.nextInt(width*2));
					ypoints[i] = posY+height/2+(rand.nextInt(height*2)-rand.nextInt(height*2));
				}
				shardpoly = new Polygon(xpoints, ypoints, xpoints.length);
			}
			if(physics!=GAS&&physics!=POOF){
				if(!(physics == CHARGED && Display.currentScreen.clicking)){
					if(motionY == 0 && physics != SPARKLE && physics != LIQUID && physics != EXPLODE)posY+=speed;
					if(motionX > 0){motionX--;moveLeft(motionX/100 >= 1 ? motionX/100 : 1);}
					if(motionX < 0){motionX++;moveRight(Math.abs(motionX)/100 >= 1 ? Math.abs(motionX)/100 : 1);}
					if(motionY > 0){motionY--;posY-=(motionY/100 >= 1 ? motionY/100 : 1);}
					if(motionY < 0){motionY++;posY+=(Math.abs(motionY)/100 >= 1 ? Math.abs(motionY)/100 : 1);}
				}else{
					if(getPosX()>Display.currentScreen.mousePoint.x+(rand.nextInt(60)-rand.nextInt(60)))moveLeft(30);
					if(getPosX()<Display.currentScreen.mousePoint.x+(rand.nextInt(60)-rand.nextInt(60)))moveRight(30);
					if(posY>Display.currentScreen.mousePoint.y+(rand.nextInt(60)-rand.nextInt(60)))posY-=30;
					if(posY<Display.currentScreen.mousePoint.y+(rand.nextInt(60)-rand.nextInt(60)))posY+=30;
				}
			}
		}
		if(this.getPosX() > Properties.width|| this.posY < 0||this.posY > Properties.height||this.getPosX() < 0){
			kill();
		}
		if(isBlood){
			if(this.collides()){
				if(rand.nextInt(10) == 0){
					if(bloodDir == 0){
						width+=speed*4;
						moveLeft(speed*2);
						height--;
					}else{
						if(width>6){
							width-=2;
							moveRight(1);
						}
						if(rand.nextInt(50) == 0)width-=speed;
						height+=speed*4;
						moveDown(speed*2);
						if(!collides()){
							bloodDir = -1;
						}
					}
				}
			}else{
				if(bloodDir==-1)
				posY+=speed;
			}
		}
		if(width==0||height==0)kill();
	}

	public void draw(Graphics g) {
		if(img!=null){
			g.drawImage(img, getPosX(), posY, width, height, null);
		}else{
			if(alpha>=255)
			g.setColor(color);
			else g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
			if(physics == GOO){g.fillOval(getPosX(), posY, width, height);}
			if(physics == ZIP){g.fillOval(getPosX(), posY, width, height);}
			if(physics == EXPLODE){g.fillOval(getPosX(), posY, width, height);}
			if(physics == SPARKLE){if(System.currentTimeMillis()-start >= 200)kill();g.drawImage(Bank.sparkle, getPosX(), posY, width, height, null);}
			//if(physics == POOF){for(int i = 0; i < rand.nextInt(10); ++i){int s = rand.nextInt(width*6)+1;g.drawImage(Bank.pull("cloud"), getPosX()-(rand.nextInt(s*2)-rand.nextInt(s*2)), posY-(rand.nextInt(s*2)-rand.nextInt(s*2)), s, s, null);}}
			if(physics == CHARGED){g.fillOval(getPosX(), posY, width, height);}
			if(physics == LIQUID){g.fillOval(getPosX(), posY, width, height);}
			if(physics == NOVA){System.out.println("drawing nova");int ds = (int) ((System.currentTimeMillis() - this.start) * maxTime / width); g.drawOval(getPosX()-ds/2, posY-ds/2, ds, ds);}
			if(physics == SNOW){g.fillOval(posX, posY, width, height);}
			if(physics == SHARDS){g.fillPolygon(shardpoly);}
			if(physics == DUST){
				for(int i = 0; i < 3+width/4; ++i){
					int s = rand.nextInt(4)+1;
					if(width>0&&height>0)
					g.fillOval(getPosX()+rand.nextInt(width), posY+rand.nextInt(height), s, s);
				}
			}
		}
	}
}
