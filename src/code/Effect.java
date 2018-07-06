package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Effect {

	EffectType type;
	int durationMax = 0, ticks = 0, maxTicks = 0;
	boolean active = true, useValueSet = true;
	int x, y, infoW = 1, infoH = 1, infoFlag = 0, boxH = 0, size = 50;
	Player caster;
	long start;
	int interval = 20, value = 0, dat = 0, dat1 = 0;
	int[] values;
	
	public Effect(int val, EffectType e, Player caster, int dur){
		this.caster = caster;
		this.type = e;
		this.interval = e.interval;
		this.value = val;
		this.start = System.currentTimeMillis();
		this.durationMax = dur;
		this.maxTicks = durationMax/interval;
		this.values = new int[maxTicks+1];
		//this.setValues(val, caster);
	}
	
	public Effect setValues(int base, Object p){
		boolean pl = false;
		if(p instanceof Player)pl = true;
		for(int i = 0; i < maxTicks+1; ++i){
			int n = pl ? ((int) (base*((Player) p).getDamage())) : base;
			values[i] = (n<=0?1:n);
		}
		return this;
	}
	
	public void setHB(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	int length = 50;
	
	public final void tickBase(Object p){
		if(System.currentTimeMillis()-start >= durationMax&&durationMax!=-1){
			active = false;
		}
		if((System.currentTimeMillis()-start)/interval > ticks){
			++ticks;
			type.tick(p, this);
		}
	}
	
	public final void draw(int x, int y, Player p, Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, size, size);
		g.drawImage(type.img, x, y, size, size, null);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, size, size);
		g.setColor(Util.transparent);
		if(durationMax!=-1){
			int h = (int) ((System.currentTimeMillis()-start) * size / durationMax);
			g.fillRect(x, y, size, size-h);
		}
		if(new Rectangle(x,y,size,size).contains(p.panel.mousePoint)){
			g.setFont(Util.descTitleFont);
			length = g.getFontMetrics().stringWidth(type.name);
			if(g.getFontMetrics().stringWidth(durationMax-(System.currentTimeMillis()-start)+" remaining") > length)length = g.getFontMetrics().stringWidth(durationMax-(System.currentTimeMillis()-start)+" remaining");
			if(type.desc!=null)
			for(int j = 0; j < type.desc.length; ++j){
				if(type.desc[j]!=null){
					g.setFont(Util.descFont);
					if(g.getFontMetrics().stringWidth(type.desc[j]) > length)length = g.getFontMetrics().stringWidth(type.desc[j]);
				}
			}
			infoH=50+(type.desc.length)*20;
			infoW=30+length;
			g.drawImage(Bank.squaregrad, p.panel.mousePoint.x-10, p.panel.mousePoint.y-5, infoW+20, infoH+10, null);
			g.drawImage(Bank.guisquare, p.panel.mousePoint.x, p.panel.mousePoint.y, infoW, infoH, null);
			g.setColor(Color.WHITE);
			g.drawRect(p.panel.mousePoint.x, p.panel.mousePoint.y, infoW, infoH);
			g.setFont(Util.descTitleFont);
			g.drawString(type.name, p.panel.mousePoint.x+infoW/2-g.getFontMetrics().stringWidth(type.name)/2, p.panel.mousePoint.y+25);
			g.drawString(durationMax-(System.currentTimeMillis()-start)+" remaining", p.panel.mousePoint.x+infoW/2-(g.getFontMetrics().stringWidth(durationMax-(System.currentTimeMillis()-start)+" remaining")/2), p.panel.mousePoint.y+35);
			g.setFont(Util.descFont);
			if(type.desc!=null)
			for(int i = 0; i < type.desc.length; ++i){
				if(type.desc[i]!=null)
				g.drawString(type.desc[i].replace("%v", value+"").replace("%dat", dat+"").replace("%i", interval+"").replace("%d", (double)(((double)durationMax)/1000)+"").replace("%t", (value*maxTicks)+""), p.panel.mousePoint.x+infoW/2-g.getFontMetrics().stringWidth(type.desc[i])/2, p.panel.mousePoint.y+45+i*15);
			}
		}
		type.render(this,p,g);
	}
	
	public Image getSprite(){
		return type.img;
	}
}
