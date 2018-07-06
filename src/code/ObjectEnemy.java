package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class ObjectEnemy extends Object{
	
	int damage = 1, speed = 1, dir = 0, range = 100, trueDir = 0;
	int attackspeedmillis = 500, attackrendermillis = 300;
	long lastAttack = System.currentTimeMillis(), startRender = System.currentTimeMillis();
	boolean left = false, right = false;
	Object tar = null;
	boolean renderAttack = false;
	ArrayList<SpellCast> spells = new ArrayList<SpellCast>();
	SpellCast currentSpell = null;
	int level = 3;
	MobAI ai = new MobAI(this);
	int dirChangeInt = 2000+Util.rand.nextInt(4000);
	long lastDirChange = System.currentTimeMillis();

	public ObjectEnemy(int x, int y) {
		super(x, y, 30, 90);
		phys = new PhysicsPlug(this).setMotionCap(80);
		width = 30;
		height = 90;
		damage = 2;
		this.solid = true;
		this.setCanFall(true);
		this.canHarm = true;
	}
	
	public void renderCombatBars(Graphics g){
		//Attack interval bar
		/*g.setColor(Color.BLACK);
		g.fillRect(this.posX+width/2-10, this.posY-36, 20, 6);
		g.setColor(Color.BLUE);
		g.fillRect(this.posX+width/2-10, this.posY-36, (int)(System.currentTimeMillis()-this.lastAttack>this.attackspeedmillis?this.attackspeedmillis:(System.currentTimeMillis()-this.lastAttack)) * 20 / this.attackspeedmillis, 6);
		g.setColor(Color.BLACK);
		g.drawRect(this.posX+width/2-10, this.posY-36, 20, 6);*/
		//Health bar
		g.setColor(Color.RED);
		g.fillRect(this.posX+width/2-15, this.posY-30, 30, 10);
		g.setColor(Color.GREEN);
		g.fillRect(this.posX+width/2-15, this.posY-30, this.health * 30 / healthMax, 10);
		g.setColor(Color.BLACK);
		g.drawRect(this.posX+width/2-15, this.posY-30, 30, 10);
	}
	
	public final void update(double d) {
		if(System.currentTimeMillis()>=startRender+attackrendermillis){
			renderAttack = false;
		}
		if(health<=0){
			this.kill();
		}
		if(!this.collideDown())phys.motionY-=3;
		if(right&!this.stunned())phys.motionX+=speed;
		if(left&!this.stunned())phys.motionX-=speed;
		if(tar!=null){
			ai.execute(tar, 1.0D);
			if(dir==0&&tar.getHitbox().intersects(new Rectangle(posX, this.posY, range+width, height))){
				if(System.currentTimeMillis() >= (currentSpell!=null?currentSpell.spell.cooldown/3:(lastAttack+attackspeedmillis))){
					attack(tar);
					lastAttack = System.currentTimeMillis();
				}
			}
			if(dir==1&&tar.getHitbox().intersects(new Rectangle(posX-range, this.posY, range+width, height))){
				if(System.currentTimeMillis() >= (currentSpell!=null?currentSpell.spell.cooldown/3:(lastAttack+attackspeedmillis))){
					attack(tar);
					lastAttack = System.currentTimeMillis();
				}
			}
		}
		this.updateEnemy();
	}
	
	public void jump(){
		if(this.collideDown()&!this.stunned())
		phys.motionY = 100;
	}
	
	public abstract void updateEnemy();
	
	public void renderAttack(Graphics g, Object o){
		
	}
	
	public double getDamageMod(){
		double level = this.level;
		return (double)((level/2+rand.nextInt(this.level))/20);
	}
	
	public void attack(Object o){
		this.renderAttack = true;
		startRender = System.currentTimeMillis();
		if(currentSpell==null){
			o.hurt(damage, false);
			o.renderBlood(damage, rand.nextInt(50)-rand.nextInt(50), rand.nextInt(80)+20);
		}else{
		//	currentSpell.cast(this, getDamageMod());
		}
		this.onAttack(o);
		currentSpell = null;
		for(int i = 0; i < spells.size(); ++i){
			if(spells.get(i).chance-rand.nextInt(spells.get(i).chance)<=0){
				currentSpell = spells.get(i);
			}
		}
	}
	
	public void onAttack(Object o){
		
	}
	
	public void die(Object o){
	}
	
	public void collideWithPlayer(Player p){
		tar = p;
	}

	public final void draw(Graphics g) {	
		drawEnemy(g);
		if(renderAttack)renderAttack(g, tar);
	}
	
	public abstract void drawEnemy(Graphics g);
}
