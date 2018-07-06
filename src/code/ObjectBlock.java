package code;

import java.awt.Graphics;
import java.awt.Image;

public class ObjectBlock extends Object{
	
	Image img;
	int blockID, maxTime = 1000;
	long start = System.currentTimeMillis();

	public ObjectBlock(int x, int y, int blockID) {
		super(x, y, 30, 30);
		solid = false;
		this.phys = new PhysicsPlug(this);
		phys.motionX = (rand.nextInt(190)-rand.nextInt(190));
		phys.motionY = 35+rand.nextInt(130);
		img = GridBlock.all[blockID].getImage();
		this.blockID = blockID;
	}

	public void update(double delta) {
		if(System.currentTimeMillis()-start >= maxTime){
			kill();
		}
		if(phys.motionY<=0)phys.motionY-=3;
		if(Display.currentScreen.clicking){
			if(this.getHitbox().contains(Display.currentScreen.mousePoint)){
				/*for(int i = 0;i < 30; ++i){
					Particle p = new Particle(posX+(this.phys.motionX<0?width:this.phys.motionX>0?0:width/2), posY+(this.phys.motionY<0?0:this.phys.motionY>0?height:height/2), Particle.CHARGED, rand.nextInt(2)==0?Color.RED:Color.YELLOW);
					p.phys.motionX = rand.nextInt(60)-rand.nextInt(60)-phys.motionX;
					p.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
					p.basePhysics = false;
					int s = 9+rand.nextInt(15);
					p.width = s;
					p.height = s;
					Display.currentScreen.objects.add(p);
				}*/
				split();
				if(width>=30)
				new ClipShell("swoosh.wav", 1F).start();
				this.kill();
			}
		}
	}
	
	public void split(){
		for(int i = 0; i < 6+rand.nextInt(6); ++i){
			int s = width/3;
			ObjectBlock block = new ObjectBlock(posX+width/2-s/2, posY+height/2-s/2, blockID);
			block.phys.motionX = phys.motionX+(rand.nextInt(30)-rand.nextInt(30));
			block.phys.motionY = 20+rand.nextInt(30);
			block.width = s;
			block.height = s;
			Display.currentScreen.objects.add(block);				
		}
	}
	
	public static void split(int x, int y, int blockID, int size){
		for(int i = 0; i < 6+Util.rand.nextInt(6); ++i){
			int s = size/3;
			ObjectBlock block = new ObjectBlock(x+size/2-s/2, y+size/2-s/2, blockID);
			block.phys.motionX = (Util.rand.nextInt(30)-Util.rand.nextInt(30));
			block.phys.motionY = 20+Util.rand.nextInt(30);
			block.width = s;
			block.height = s;
			Display.currentScreen.objects.add(block);				
		}
	}
	
	public static void split(int x, int y, int blockID, int size, PhysicsPlug ph){
		for(int i = 0; i < 6+Util.rand.nextInt(6); ++i){
			int s = size/3;
			ObjectBlock block = new ObjectBlock(x+size/2-s/2, y+size/2-s/2, blockID);
			block.phys.motionX = ph.motionX+(Util.rand.nextInt(30)-Util.rand.nextInt(30));
			block.phys.motionY = 20+Util.rand.nextInt(30);
			block.width = s;
			block.height = s;
			Display.currentScreen.objects.add(block);				
		}
	}
	
	public static void split(int x, int y, int blockID, int size, int amt, PhysicsPlug ph){
		for(int i = 0; i < amt; ++i){
			int s = size/3;
			ObjectBlock block = new ObjectBlock(x+size/2-s/2, y+size/2-s/2, blockID);
			block.phys.motionX = ph.motionX+(Util.rand.nextInt(30)-Util.rand.nextInt(30));
			block.phys.motionY = 20+Util.rand.nextInt(30);
			block.width = s;
			block.height = s;
			Display.currentScreen.objects.add(block);				
		}
	}

	public void draw(Graphics g) {
		g.drawImage(img, posX, posY, width, height, null);
		if(posY>=Properties.height)kill();
	}
}
