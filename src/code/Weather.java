package code;

import java.awt.Color;
import java.util.Random;

public class Weather {
	
	static Weather[] all = new Weather[255];
	
	int id = 0, interval = 12000;
	String name;
	Random rand = new Random();
	long last = System.currentTimeMillis();

	public static final Weather rain = new Weather(0, "Rain");
	public static final Weather storm = new Weather(1, "Thunderstorm");
	public static final Weather snow = new Weather(2, "Snow");
	public static final Weather sandstorm = new Weather(3, "Sandstorm");
	public static final Weather cloudy = new Weather(4, "Cloudy Skies");
	
	public Weather(int id, String n){
		this.id = id;
		this.name = n;
	}
	
	public void update(PanelBase pan){
		if(this==cloudy){
			if(System.currentTimeMillis()-last >= interval){
				last = System.currentTimeMillis();
				int dir = 1, w = 50+rand.nextInt(60);
				int h = w/3*2;
				ObjectCloud cloud = new ObjectCloud(dir, w, h, 10+rand.nextInt(51));
				Display.currentScreen.objects.add(cloud);
				interval = 300+(rand.nextInt(800)-rand.nextInt(800));
			}
		}
		if(this==snow){
			int s = rand.nextInt(4)+1;
			Particle p = new Particle(rand.nextInt(Properties.width), 0, Particle.SNOW, new Color(255,255,255,100+rand.nextInt(100)));
			p.canDrop = true;
			p.width = s*2;
			p.height = s*2;
			pan.objects.add(p);
		}
		if(this==rain){
			int s = rand.nextInt(4)+1;
			Particle p = new Particle(rand.nextInt(Properties.width), 0, Particle.LIQUID, Util.waterBlue);
			p.canDrop = true;
			p.isBlood = true;
			p.width = s*2;
			p.height = s*2;
			pan.objects.add(p);
		}
		if(this==sandstorm){
			int s = rand.nextInt(4)+1;
			Particle p = new Particle(0, rand.nextInt(Properties.height), Particle.CHARGED, Util.yellow);
			p.canDrop = true;
			p.basePhysics = false;
			p.phys.motionX = Properties.width/5;
			p.phys.motionY = 30+rand.nextInt(200);
			p.width = s*2;
			p.height = s*2;
			pan.objects.add(p);
		}
		if(this==storm){
			int s = rand.nextInt(4)+1;
			Particle p = new Particle(rand.nextInt(Properties.width), 0, Particle.LIQUID, Util.waterBlue);
			p.canDrop = true;
			p.isBlood = true;
			p.width = s*2;
			p.height = s*2;
			pan.objects.add(p);
			if(System.currentTimeMillis()-last >= interval){
				last = System.currentTimeMillis();
				pan.objects.add(new ObjectLightningbolt(rand.nextInt(Properties.width), Properties.height));
				interval = 12000+(rand.nextInt(8000)-rand.nextInt(8000));
			}
		}
	}
}
