package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;

public class GUIConfirm extends GUI {
	
	String text = null;
	int actionID = 0;
	Rectangle conf = new Rectangle(x+w/2-80, y+h-10-30, 160, 30), exit = new Rectangle(x+w-20, y, 20, 20);
	String icon = "!", conftext = "Confirm";

	public GUIConfirm(int w, int h, String text, String icon, int actionid) {
		super(Properties.width/2-w/2, Properties.height/2-h/2, w, h);
		showSelection = false;
		this.text = text;
		this.actionID = actionid;
		this.icon = icon;
	}

	public void tick(double delta) {
	}
	
	public void confirm(){
		if(actionID == 0){
			Display.currentScreen.guis.remove(this);
		}
		if(actionID == 1){
			int last = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"));
			if(last-80>=0){
				last-=80;
				Bank.setContentsRawdir(Bank.path+"data/Core.HB", last+"");
				GUIConfirm success = new GUIConfirm(w, h, "Transaction Successful!", ":)", 0);
				success.conftext = "Radical.";
				Display.currentScreen.guis.add(success);
				Analysis.setKey(new File(Bank.path+"data/trial.HB"), "losses", "0");
				Analysis.setKey(new File(Bank.path+"data/trial.HB"), "haspass", "1");
				Display.currentScreen.guis.remove(this);
				Display.currentScreen.guis.add(new GUINotify(Bank.totemEarth, "Grand Trial Pass Purchased!", "Congrats dude/dudette.", 0, 0));
				if(!Bank.hasLockVal("achievements.HB", Achievement.asyouare.id)){
					Display.currentScreen.guis.add(new GUIAchievementNotify(Achievement.asyouare, 0, 0));
					Bank.setLockVal("achievements.HB", Achievement.asyouare.id, true);
				}
			}else{
				GUIConfirm fail = new GUIConfirm(w, h, "Insufficient Funds!", "X", 0);
				fail.conftext = "Dang.";
				Display.currentScreen.guis.add(fail);
				Display.currentScreen.guis.remove(this);
			}
		}
	}
	
	public void click(boolean b){
		//conf = new Rectangle(x+w/2-80, y+h-10-30, 160, 30);
		if(exit.contains(Display.currentScreen.mousePoint)){
			Display.currentScreen.guis.remove(this);
		}
		if(conf.contains(Display.currentScreen.mousePoint)){
			confirm();
		}
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent_dark);
		g.fillRect(0, 0, Properties.width, Properties.height);
		g.drawImage(Bank.woodboard, x, y, w, h, null);
		g.setColor(icon=="!"?Color.YELLOW:icon==":)"?Color.GREEN:Color.RED);
		g.fillOval(x+10, y+10, h-20, h-20);
		g.setColor(Color.BLACK);
		g.drawOval(x+10, y+10, h-20, h-20);
		String n = icon;
		g.setFont(Util.largeNameFont);
		g.drawString(n, x+10+(h-20)/2-g.getFontMetrics().stringWidth(n)/2, y+10+(h-20)/3*2);
		g.drawRect(x, y, w, h);
		g.setColor(Color.RED);
		g.fillRect(x+w-20, y, 20, 20);
		g.setColor(Color.BLACK);
		g.setFont(Util.descTitleFont);
		g.drawRect(x+w-20, y, 20, 20);
		g.setColor(Color.WHITE);
		g.drawString("X", x+w-10-g.getFontMetrics().stringWidth("X")/2, y+15);
		g.setColor(Color.WHITE);
		g.setFont(Util.dialogFont);
		g.drawString(text, x+w/2-g.getFontMetrics().stringWidth(text)/2+30, y+30);
		g.setColor(Util.transparent);
		g.fillRect(conf.x, conf.y, conf.width, conf.height);
		g.setColor(Color.WHITE);
		if(conf.contains(Display.currentScreen.mousePoint)){
			g.drawRect(conf.x, conf.y, conf.width, conf.height);
		}
		g.drawString(conftext, conf.x+conf.width/2-g.getFontMetrics().stringWidth(conftext)/2, conf.y+conf.height/3*2);
	}
}
