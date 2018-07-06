package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUICharBio extends GUI {
	
	File file;
	String name = null;
	String[] strs = new String[30];
	Rectangle close = new Rectangle();

	public GUICharBio(String name, String[] f, int x, int y, int w, int h) {
		super(x, y, w, h);
		strs = f;
		this.name = name;
		/*int index = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(f))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	strs[index] = line;
		    	++index;
		        // process the line.
		    }
		    // line is not visible here.
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		close.setBounds(x+w-40, y, 40, 40);
		showSelection = false;
	}
	
	public void click(boolean b){
		if(close.contains(Display.currentScreen.mousePoint)){
			Display.currentScreen.guis.remove(this);
		}
	}

	public void tick(double delta) {
	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(Util.transparent_dark);
		g.fillRect(0, 0, Properties.width, Properties.height);
		g.drawImage(Bank.woodboard, x, y, w, h, null);
		g.setColor(Color.RED);
		g.fillRect(close.x, close.y, close.width, close.height);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, w, h);
		g.drawRect(close.x, close.y, close.width, close.height);
		g.setFont(Util.spellNameFont);
		g.drawString("X", close.x+close.width/2-g.getFontMetrics().stringWidth("X")/2, close.y+close.height/4*3);
		g.setFont(Util.largeNameFont);
		g.drawString(name, x+w/2-g.getFontMetrics().stringWidth(name)/2, y+45);
		g.setFont(Util.cooldownFont);
		if(strs!=null)
		for(int i = 0; i < strs.length; ++i){
			if(strs[i]!=null)
			g.drawString(strs[i], x+w/2-g.getFontMetrics().stringWidth(strs[i])/2, y+90+i*30);
		}
	}
}
