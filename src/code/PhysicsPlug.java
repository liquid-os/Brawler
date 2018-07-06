package code;

import java.awt.Point;
import java.util.Random;

public class PhysicsPlug {
	
	//Object
	Object obj;
	Random rand = new Random();
	boolean fixate = false, execute = true;
	int scrollBorderY = 100, scrollBorderYTop = Properties.height/4;
	
	public PhysicsPlug(Object obj){
		this.obj = obj;
	}
	
	public PhysicsPlug(Object obj, boolean fixCam){
		this.obj = obj;
		this.fixate = fixCam;
	}
	
	public PhysicsPlug setMotionCap(int cap){
		this.motionCap = cap;
		return this;
	}
	
	public int getMotionCap(){
		return obj.countEffect(EffectType.grace) > 0 ? -1 : motionCap;
	}
	
	boolean player = false;
	//Mass in KG
	float mass = 25;
	//Velocity in m/s
	int velocity = 3;
	//Acceleration in m/s/s
	int acceleration = 4;
	//Gravity is g's
	float gravity = Properties.gravity;
	//Y motion
	int motionY = 0;
	//X motion
	int motionX = 0;
	public int div = 10;
	//Maximum motion either end of spectrum
	int motionCap = -1;
	
	public int getScrollBorderY(boolean bottom){
		if(!bottom){
			return 240;
		}
		return scrollBorderY;
	}
	
	public void execute(double delta){
		if(execute){
			int cogx = obj.posX+obj.getWidth()/2;
			int cogy = obj.posY+obj.getHeight()/2;
			Point cog = new Point(cogx, cogy);
			//if(obj.getCanFall() && obj.posY<Properties.height-80-obj.getHeight() && motionY <= 0)
			//obj.posY+=((mass*(gravity*10)));
			//int motionX = (int) (this.motionX*delta);
			//int motionY = (int) (this.motionY*delta);
			if(motionX > 0){
				if(motionX > getMotionCap() && getMotionCap() != -1)motionX = getMotionCap();
				--motionX;
				if(!obj.collideRight()|!obj.solid){
					if(fixate&&obj.posX>=Properties.width-Display.currentScreen.scrollBorder-obj.width){
						if(Display.currentScreen.getGrid()!=null){
							scrollRight(motionX/div>1?motionX/div:1);
						}else{
							obj.moveRight(motionX/div>1?motionX/div:1);
						}
					}else
					obj.moveRight(motionX/div>1?motionX/div:1);
					if(obj instanceof Player){
						((Player)obj).move+=(motionX/div>1?motionX/div:1);
					}
				}
				if(obj.collideRight()&&obj.solid){
					if(!GridBlock.all[Display.currentScreen.getGrid().getTileIDWithScroll(obj.posX+obj.width, obj.posY)].playerSolid()){
						if(fixate&&obj.posY<=getScrollBorderY(false))this.scrollUp(Display.currentScreen.getGrid().getTileSize());
						else obj.moveUp(Display.currentScreen.getGrid().getTileSize());
					}
					--motionX;
				}
			}
			if(motionX < 0){
				if(motionX < -getMotionCap() && getMotionCap() != -1)motionX = -getMotionCap();
				++motionX;
				if(!obj.collideLeft()|!obj.solid){
					if(fixate&&obj.posX<=Display.currentScreen.scrollBorder){
						if(Display.currentScreen.getGrid()!=null){
							scrollLeft((Math.abs(motionX)/div>1?Math.abs(motionX)/div:1));
						}else{
							obj.moveLeft(Math.abs(motionX)/div>1?Math.abs(motionX)/div:1);
						}
					}else
					obj.moveLeft(Math.abs(motionX)/div>1?Math.abs(motionX)/div:1);
				}
				if(obj instanceof Player){
					((Player)obj).move-=(motionX/div>1?motionX/div:1);
				}
				if(obj.collideLeft()&&obj.solid){
					if(!GridBlock.all[Display.currentScreen.getGrid().getTileIDWithScroll(obj.posX-30, obj.posY)].playerSolid()){
						if(fixate&&obj.posY<=getScrollBorderY(false))this.scrollUp(Display.currentScreen.getGrid().getTileSize());
						else obj.moveUp(Display.currentScreen.getGrid().getTileSize());
					}
					++motionX;
				}
			}
			if(motionY > 0){
				--motionY;
				if(!obj.collideUp()|!obj.solid){
					if(fixate&&obj.posY<=getScrollBorderY(false)){
						if(((GridPanel)Display.currentScreen).scrollY > -Display.currentScreen.viewDistY){
							scrollUp(motionY/div>1?motionY/div:1);
						}else
							obj.moveUp(motionY/div>1?motionY/div:1);
					}else{
						obj.moveUp(motionY/div>1?motionY/div:1);
					}
				}
			}
			if(motionY < 0){
				++motionY;
				if(!obj.collideDown()|!obj.solid){
					if(fixate&&obj.posY+obj.height>=Properties.height-getScrollBorderY(true)){
						if(Properties.height+((GridPanel)Display.currentScreen).scrollY < Display.currentScreen.viewDistY){
							scrollDown(Math.abs(motionY)/div>1?Math.abs(motionY)/div:1);
						}else
							obj.moveDown(Math.abs(motionY)/div>1?Math.abs(motionY)/div:1);
					}else{
						obj.moveDown(Math.abs(motionY)/div>1?Math.abs(motionY)/div:1);
					}
				}
			}
		}
	}
	
	public void scrollLeft(int i){
		Display.currentScreen.getGrid().p.scrollX-=i;
		for(int x = 0; x < Display.currentScreen.objects.size(); ++x){
			if(x <= Display.currentScreen.objects.size()){
				if(Display.currentScreen.objects.get(x)!=null){
					if(Display.currentScreen.objects.get(x)!=obj)
					Display.currentScreen.objects.get(x).posX+=i;
				}
			}
		}
	}
	
	public void scrollRight(int i){
		Display.currentScreen.getGrid().p.scrollX+=i;
		for(int x = 0; x < Display.currentScreen.objects.size(); ++x){
			if(x <= Display.currentScreen.objects.size()){
				if(Display.currentScreen.objects.get(x)!=null){
					if(Display.currentScreen.objects.get(x)!=obj)
					Display.currentScreen.objects.get(x).posX-=i;
				}
			}
		}
	}
	
	public void scrollUp(int i){
		Display.currentScreen.getGrid().p.scrollY-=i;
		for(int x = 0; x < Display.currentScreen.objects.size(); ++x){
			if(x <= Display.currentScreen.objects.size()){
				if(Display.currentScreen.objects.get(x)!=null){
					if(Display.currentScreen.objects.get(x)!=obj)
					Display.currentScreen.objects.get(x).posY+=i;
				}
			}
		}
	}
	
	public void scrollDown(int i){
		Display.currentScreen.getGrid().p.scrollY+=i;
		for(int x = 0; x < Display.currentScreen.objects.size(); ++x){
			if(x <= Display.currentScreen.objects.size()){
				if(Display.currentScreen.objects.get(x)!=null){
					if(Display.currentScreen.objects.get(x)!=obj)
					Display.currentScreen.objects.get(x).posY-=i;
				}
			}
		}
	}
}
