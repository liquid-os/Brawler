package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class PanelDialog extends PanelBase{
	
	PanelBase lastPanel = null;
	Dialog dia = null;
	Player p;
	Object o;
	Color color = Color.BLUE;
	int stage = 0;
	public Player user = null;
	boolean canExit = true;
	Image bg = Bank.woodbg, sky = Bank.sky, land = Bank.hillsRoll;
	
	public PanelDialog(Player p, Object o, PanelBase lastPanel, Dialog d){
		this.lastPanel = lastPanel;
		this.p = p;
		this.o = o;
		this.dia = d;
		if(dia.sky!=null)sky=dia.sky;
		else sky = lastPanel.sky;
		if(dia.bg!=null)land=dia.bg;
		else bg = lastPanel.bg;
		Display.reload = true;
		this.user = p;
		this.background = color.BLUE;
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_1){
			dia.onDialogSelect(o, this, stage, 0);
		}
		if(e.getKeyCode()==KeyEvent.VK_2){
			dia.onDialogSelect(o, this, stage, 1);
		}
		if(e.getKeyCode()==KeyEvent.VK_3){
			dia.onDialogSelect(o, this, stage, 2);
		}
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE&&canExit){
			Display.currentScreen = lastPanel;
		}
	}

	public void onUpdate() {
	}

	public void drawScreen(Graphics g) {
		g.drawImage(sky, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(land, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(p.hero.portraitFlip, -40, Properties.height-(Properties.height/4*3), Properties.width/4, Properties.height/4*3, null);
		dia.dialogRender(g, Properties.width-(Properties.width/4-40), Properties.height-(Properties.height/4*3), Properties.width/4, Properties.height/4*3);
		g.setColor(Util.transparent_dark);
		g.drawImage(Bank.squaregrad, -20, Properties.height-Properties.height/5-20, Properties.width+40, Properties.height/5+40, null);
		g.drawImage(Bank.windowbg, 0, Properties.height-Properties.height/4, Properties.width, Properties.height/2, null);
		Bank.drawSquare(g, 0, Properties.height-Properties.height/4, Properties.width, Properties.height/4);
		g.drawImage(Bank.squaregrad, Properties.width/2-(Properties.width/3)/2-40, 0, Properties.width/3+80, Properties.height/2+80, null);
		g.drawImage(Bank.woodboard, Properties.width/2-(Properties.width/3)/2, 40, Properties.width/3, Properties.height/2, null);
		Bank.drawSquare(g, Properties.width/2-(Properties.width/3)/2, 40, Properties.width/3, Properties.height/2);
		g.setColor(Color.WHITE);
		g.setFont(Util.dialogFont);
		for(int i = 0; i < 3; ++i){
			if(dia.logs[stage][i]!=null)
			g.drawString((i+1)+". "+dia.logs[stage][i], Properties.width/2-g.getFontMetrics().stringWidth((1+i)+". "+dia.logs[stage][i])/2, 40+Properties.height-Properties.height/4+i*45);
		}
		String[] text = dia.text[stage];
		for(int i = 0; i < text.length; ++i){
			g.drawString(text[i], Properties.width/2-g.getFontMetrics().stringWidth(text[i])/2, 130+i*30);
		}
		g.setColor(dia.nameColor);
		g.setFont(Util.spellNameFont);
		g.drawString(dia.name, Properties.width/2-g.getFontMetrics().stringWidth(dia.name)/2, 85);
		//g.drawImage(Bank.pull("cursor"), mousePoint.x, mousePoint.y, null);
	}
}
