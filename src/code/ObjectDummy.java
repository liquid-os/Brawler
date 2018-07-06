package code;

import java.awt.Graphics;
import java.awt.Image;

public class ObjectDummy extends ObjectEnemy{
	
	Image tex;

	public ObjectDummy(Image tex, int x, int y, int w, int h) {
		super(x, y);
		this.tex = tex;
		health = 1;
	}

	public void updateEnemy() {	
	}

	public void drawEnemy(Graphics g) {
		g.drawImage(tex, posX, posY, width, height, null);
	}
}
