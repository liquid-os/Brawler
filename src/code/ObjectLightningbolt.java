package code;

import java.awt.Color;
import java.awt.Graphics;

public class ObjectLightningbolt extends Object{
	
	long start = System.currentTimeMillis();

	public ObjectLightningbolt(int x, int y) {
		super(x, y, 30+Util.rand.nextInt(61), Properties.height-y);
	}

	public void update(double delta) {
		if(System.currentTimeMillis()-start >= 1000)kill();
	}

	public void draw(Graphics g) {
		g.setColor(rand.nextInt(2)==0?Util.transparent_white:(rand.nextInt(2)==0?Color.YELLOW:Color.WHITE));
		int[] xjoints = new int[]{rand.nextInt(width)-rand.nextInt(width), rand.nextInt(width)-rand.nextInt(width), rand.nextInt(width)-rand.nextInt(width), rand.nextInt(width)-rand.nextInt(width)};
		for(int i = 0; i < width; ++i){
			for(int j = 0; j < 4; ++j){
				g.drawLine(posX+xjoints[(j-1)>0?j-1:0]+i, j*height/4, posX+xjoints[j]+i, posY);
			}
		}
	}
}
