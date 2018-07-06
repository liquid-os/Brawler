package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class PanelSetSelect extends PanelBase {

	public PanelSetSelect() {
		int count = 0, w = Properties.width/4, h = 170, buttonsPerLine = 3;
		for(int i = 0; i < Levelset.all.length; ++i){
			if(Levelset.all[i]!=null){
				Levelset set = Levelset.all[i];
				GUIButton button = new GUIButton(set.icon, (Image)Bank.buttonHover, i, Properties.width/2-w/2-w+i*(w+10)-(i/buttonsPerLine)*(w+10)*(buttonsPerLine), 100+(count/3)*(h+20), w, h);
				guis.add(button);
				++count;
			}
		}
	}
	
	public void buttonReact(int id){
		Levelset set = Levelset.all[id];
		Display.currentScreen = new PanelLevelSelect(Properties.selHero, set);
	}

	public void onUpdate() {
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			Display.currentScreen = new PanelMenu(-1);
		}
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.woodbg, 0, 0, Properties.width, Properties.height, null);
		for(int i = 0; i < guis.size(); ++i){
			if(guis.get(i).hovered() && guis.get(i) instanceof GUIButton){
				GUIButton comp = (GUIButton)guis.get(i);
				int height = 100;
				g.setColor(Util.transparent);
				g.fillRect(0, 0, Properties.width, Properties.height);
				g.fillRect(0, Properties.height/2-height/2, Properties.width, height);
				g.setColor(Color.WHITE);
				g.drawString(comp.text, Properties.width/2-g.getFontMetrics().stringWidth(comp.text)/2, Properties.height/2);
			}
		}
	}
}
