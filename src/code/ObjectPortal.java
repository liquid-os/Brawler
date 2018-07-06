package code;

import java.awt.Graphics;

public class ObjectPortal extends Object {

	public ObjectPortal(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void update(double delta) {
	}
	
	public void collideWithPlayer(Player p){
		if(Display.currentScreen instanceof PanelCampaign){
			PanelCampaign pan = ((PanelCampaign)Display.currentScreen);
			if(pan.endStart==-1){
				pan.endStart = System.currentTimeMillis();
				pan.win = true;
				pan.runDeath = true;
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(Bank.portal, posX, posY, width, height, null);
	}
}
