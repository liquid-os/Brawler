package code;

import java.awt.image.BufferedImage;

public class Biome {
	
	int x, bWidth = 1;
	BufferedImage bg, sky;
	Weather weather = null;

	public Biome(int x, int bWidth, BufferedImage bg, BufferedImage sky, Weather weather) {
		this.x = x;
		this.bWidth = bWidth;
		this.bg = bg;
		this.sky = sky;
		this.weather = weather;
	}
	
	public void apply(PanelBase p){
		p.biomeTransition = System.currentTimeMillis();
		if(bg!=null)p.bg = this.bg;
		if(sky!=null)p.sky = this.sky;
		p.weather = this.weather;
	}
}
