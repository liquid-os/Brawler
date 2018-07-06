package code;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PanelWorldmap extends PanelBase{
	
	int scrollX = 0, scrollY = 0;
	BufferedImage map;
	int selQuest = 0;
	
	public PanelWorldmap(){
		
	}

	public void onUpdate() {
	}

	public void drawScreen(Graphics g) {
		g.drawImage(map, scrollX, scrollY, 3000, 3000, null);
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			selQuest++;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			selQuest--;
		}
	}
}
