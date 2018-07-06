package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PanelCredits extends PanelBase {
	
	int current = 0;
	Font font = Util.cooldownBold, font1 = Util.cooldownFont;
	long last = System.currentTimeMillis();
	String[] credits = new String[]{
			"Code#Adam Shannon",
			"Engine#Adam Shannon",
			"Design#Adam Shannon",
			"Marketing#Adam Shannon",
			"Art#Adam Shannon",
			"Sound#Adam Shannon",
			"Music#Adam Shannon",
			"Music#Kevin Macleod",
			" #--------",
			"Alpha/Beta Testing#Alex Palmer",
			"Alpha/Beta Testing#Adam Shannon",
			" #--------",
			" #Thanks For Playing",
			" # ",
	};

	public PanelCredits() {
	}

	public void onUpdate() {
		if(System.currentTimeMillis() >= last+1000 && current < credits.length){
			String role  = credits[current].split("#")[0];
			String cred = credits[current].split("#")[1];
			int l = Display.frame.getGraphics().getFontMetrics(font).stringWidth(role)/2;
			int l1 = Display.frame.getGraphics().getFontMetrics(font1).stringWidth(cred)/2;
			Indicator ind = new Indicator(Properties.width/2, Properties.height-80, role);
			ind.c = Color.YELLOW;
			ind.dir = -1;
			ind.speed = 2;
			Indicator ind1 = new Indicator(Properties.width/2, Properties.height-80+50, cred);
			ind1.c = Color.WHITE;
			ind1.dir = -1;
			ind1.speed = 2;
			Display.currentScreen.objects.add(ind);
			Display.currentScreen.objects.add(ind1);
			++current;
			last = System.currentTimeMillis();
		}
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.chicken, 0, 0, Properties.width, Properties.height, null);
		int bcount = Properties.width/80;
		for(int i = 0; i < bcount; ++i){
			g.drawImage(Bank.grass, i*80, Properties.height-80, 80, 80, null);
		}
	}
}
