package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PanelMapSetup extends PanelBase{
	
	Rectangle conf = new Rectangle(Properties.width/2-100, Properties.height/5*4, 200, 140);
	String name = "";
	int selbg = 0, selsky = 0, stage = 0;
	BufferedImage bg = Bank.bg0, sky = Map.getSky(0);
	Polygon p = new Polygon();

	public PanelMapSetup() {
		updatePoly();
	}

	public void onUpdate() {
	}
	
	public void updatePoly(){
		Graphics g = Display.frame.getGraphics();
		p.xpoints = new int[]{200+(stage==2?g.getFontMetrics(Util.cooldownFont).stringWidth(name):0), 240+(stage==2?g.getFontMetrics(Util.cooldownFont).stringWidth(name):0), 200+(stage==2?g.getFontMetrics(Util.cooldownFont).stringWidth(name):0)};
		p.ypoints = new int[]{40+stage*100+(stage==3?50:0), 40+stage*100+40+(stage==3?50:0), 40+stage*100+80+(stage==3?50:0)};
		p.npoints = 3;
	}
	
	public void keyReleased(KeyEvent e){
		if(stage==3)
		if(e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ESCAPE){
			confirm();
		}
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_UP&&stage>0){
			stage--;
			updatePoly();
			new ClipShell("click.wav", 3F).start();
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN&&stage<3){
			stage++;
			updatePoly();
			new ClipShell("click.wav", 3F).start();
		}
		if(stage==0){
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				selbg++;
				new ClipShell("swoosh.wav", 1F).start();
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				selbg--;
				new ClipShell("swoosh.wav", 1F).start();
			}
			bg = Map.getBG(selbg);
		}
		if(stage==1){
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				selsky++;
				new ClipShell("swoosh.wav", 1F).start();
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				selsky--;
				new ClipShell("swoosh.wav", 1F).start();
			}
			sky = Map.getSky(selsky);
		}
		if(stage==2){
			char c = e.getKeyChar();
			if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
				char[] chars = name.toCharArray();
				name="";
				for(int i = 0; i < chars.length-1; ++i){
					name+=chars[i];
				}
				new ClipShell("click.wav", 3F).start();
			}
			if(Character.isLetterOrDigit(c)||c==' '||c=='_'){
				name+=c;
				new ClipShell("click.wav", 3F).start();
			}
			updatePoly();
		}
	}
	

	public void drawScreen(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0,Properties.width,Properties.height);
		if(conf.contains(mousePoint)){
			g.drawImage(Bank.squaregrad_gr, conf.x-10, conf.y-5, conf.width+20, conf.height+10, null);
		}
		g.drawImage(Bank.confbutton, conf.x, conf.y, conf.width, conf.height, null);
		g.drawImage(sky, 0, 0, Properties.width, Properties.height, null);
		g.drawImage(bg, 0, 0, Properties.width, Properties.height, null);
		g.setFont(Util.largeNameFont);
		g.setColor(Color.BLACK);
		g.drawString("Map Name: "+name, Properties.width/2-g.getFontMetrics().stringWidth("Map Name: "+name)/2, 700);
		g.setColor(Util.transparent);
		g.fillRect(0, 40, 200, 80);
		g.fillRect(0, 40+100, 200, 80);
		g.fillRect(0, 40+200, g.getFontMetrics(Util.cooldownFont).stringWidth(name)+200, 80);
		if(stage<3)g.fillPolygon(p);
		g.setColor(Util.transparent_dark);
		if(stage>=3)g.fillPolygon(p);
		g.fillRect(0, 40+350, 200, 80);
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownFont);
		g.drawString("Scene #"+selbg, 20, 80);
		g.drawString("Sky #"+selsky, 20, 80+100);
		g.drawString("Name: "+name, 20, 80+200);
		g.drawString("Finish", 20, 80+350);
	}
	
	public void releaseClick(boolean left){
		if(conf.contains(mousePoint)){
			//confirm();
		}
	}
	
	public void confirm(){
		new ClipShell("punch.wav", 3F).start();
		PanelMapCreate pan = new PanelMapCreate(new Grid(750,null), name, selbg, selsky);
		pan.grid = new Grid(750,pan);
		Display.currentScreen = pan;
	}
}
