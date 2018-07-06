package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GUIButton extends GUI {
	
	Color color = Color.WHITE, textColor = Color.white;
	String text;
	Font font = Util.cooldownFont, hoverFont = Util.cooldownBold;
	int reactID = 0, arrowDir = -1;
	Image img = null, hover = null;

	public GUIButton(String s, int id, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.text = s;
		this.reactID = id;
		setColor(Color.BLUE);
		this.showSelection = false;
	}
	
	public GUIButton(Image img, Image hover, int id, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.img = img;
		this.hover = hover;
		this.reactID = id;
		this.showSelection = false;
	}
	
	public GUIButton(Image img, Image hover, String txt, int id, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.img = img;
		this.hover = hover;
		this.reactID = id;
		this.text = txt;
		this.showSelection = false;
	}

	public void tick(double delta) {
	}
	
	public GUIButton setColor(Color c){
		color = new Color(c.getRed(),c.getGreen(),c.getBlue(),100);
		return this;
	}
	
	public GUIButton setColor(int r, int g, int b){
		color = new Color(r,g,b,100);
		return this;
	}
	
	public void releaseClick(boolean l){
		if(this.getRect().contains(Display.currentScreen.mousePoint)){
			new ClipShell("click.wav", 3F).start();
			confirm();
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE){
			confirm();
		}
	}
	
	public void confirm(){
		Display.currentScreen.buttonReact(reactID);
		
	}

	public void draw(Graphics g, int x, int y) {
		if(arrowDir==-1){
			if(img == null)
			g.drawImage(Bank.squaregrad, x-15 ,y-6, w+30, h+12, null);
			if(img!=null){
				g.drawImage(hovered()?hover:img, x, y, w, h, null);
				if(img==Bank.custbutton){
					g.setColor(hovered()?Color.WHITE:Color.BLACK);
					g.drawRect(x, y, w, h);
				}
				if(text!=null){
					g.setColor(textColor);
					g.setFont(hovered()?hoverFont:font);
					g.drawString(text, x+w/2-g.getFontMetrics().stringWidth(text)/2, y+h/2);
				}
			}else{
				if(hovered()){
					g.drawImage(Bank.buttondecoHover, x, y, w, h, null);
					g.setColor(color);
					//g.fillRect(x, y, w, h);
					g.setColor(textColor);
					g.setFont(hoverFont);
					g.drawString(text, x+w/2-g.getFontMetrics().stringWidth(text)/2, y+h/2+10);
				}else{
					g.drawImage(Bank.buttondeco, x, y, w, h, null);
					g.setColor(color);
					//g.fillRect(x, y, w, h);
					g.setColor(textColor);
					g.setFont(font);
					g.drawString(text, x+w/2-g.getFontMetrics().stringWidth(text)/2, y+h/3*2);
				}
			}
		}else{
			Polygon p = new Polygon();
			p.xpoints = new int[]{x+(arrowDir==1?w:0), (arrowDir==0?(x+w):(x)), x+(arrowDir==1?w:0)};
			p.ypoints = new int[]{y, y+h/2, y+h};
			p.npoints = 3;
			g.setColor(Color.BLACK);
			g.fillPolygon(p);
			if(this.getRect().contains(Display.currentScreen.mousePoint)){
				g.setColor(Color.WHITE);
				if(Display.currentScreen.clicking)g.fillPolygon(p);else g.drawPolygon(p);
			}
		}
	}

	public GUI setArrow(int i) {
		arrowDir = i;
		return this;
	}
}
