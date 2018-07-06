package code;

public class MobAI {
	
	ObjectEnemy p;
	int range = 30, jumpSpace = 1000;
	int freeX = -1;
	long lastJump = -1;
	
	public MobAI(ObjectEnemy player){
		this.p = player;
	}
	
	public void execute(Object e, double delta){
		Coordinate d = new Coordinate(e.posX, e.posY);
		Grid grid = Display.currentScreen.getGrid();
		d = new Coordinate(e.posX, e.posY);
		if(System.currentTimeMillis()>=lastJump+jumpSpace){
			p.jump();
			lastJump = System.currentTimeMillis();
			jumpSpace = 850+Util.rand.nextInt(1351);
		}
		if(d.x-p.width > p.posX){p.right = true;p.left = false;p.trueDir = 0;}
		if(d.x < p.posX-p.width){p.left = true;p.right = false;p.trueDir = 1;}
	}
}
