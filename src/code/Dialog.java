package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Dialog{
	
	String[][] logs = new String[255][3];
	String[][] text = new String[255][0];
	String name = "";
	static String[] surnames = new String[]{"Everwind","Candlebright","Winterkin","Newhearth","Stonehearth","Godfrey","Wilford","Grimm","Crux","Valenheim","Leberuj","Wickham","Sting","Winger","Farhill","Longshore","Lateshore","Lakehearth","Lakewater","Manfrey","Briars","Thornbush","Rosebush","Queng","Zhang","Zi'Pao","Xi'Keng","Everbright","Oakwood","Greatoak","Redcloack","Whitecloak","Bluecloak","Wintercloak","Springstep","Mayweather","Reynholm","Redweather","Fairwing","Fairweather","Flowerkin","Bloomhill","Evergreen","Murkdeep","Murkhollow","Grayshift","Nightwisp","Krolex"};
	static String[] maleNames = new String[]{"Adam","Peter","William","Will","Willace","Ronald","Arthur","Mitchell","Michael","David","Henry","Thomas","Darcy","Benjamin","Robert","Dean","Ben","Niles","Jerome","Troy","Joseph","Daniel","Jack","Kai","Quei","Zueng"};
	static String[] femNames = new String[]{"Hope","Grace","Penelope","Stacy","Lilianna","Anne","Anna","Clarissa","Chloe","Camille","Arabella","Bella","Elizabeth","Madeline","Lily","Caitlynne","Lee","Leah","Lauren","Emma","Rose","Rosaline","Auraille","Remi","Moora","Zayell","Mary","Zhu","Zhe'She","Xuing","Quing","Jennifer"};
	Color nameColor = Color.YELLOW;	
	boolean auto = true;
	Image image, bg = null, sky = null;
	
	public static final Dialog trayatBrawl = new Dialog("Knuckles McBuster")
	.addStageText(new String[]{"Well, who's this? You think","you can just talk to ME?"," I am sooooo much","better than you.","You have no idea."}, 0)
	.addDialogOption("Is your refridgerator running?", 0, 0)
	.addDialogOption("YOU THINK YOU'RE BETTER THAN ME?!", 0, 1)
	.addDialogOption("I heard you're a brawl 'champion'.", 0, 2)
	.addStageText(new String[]{"You know, I think it is.",}, 1)
	.addDialogOption("Well you had better go catch it!", 1, 0)
	.addDialogOption("Wait nevermind I forgot the joke.", 1, 1)
	.setScene(null, Bank.woodbg)
	.setPortraitImage(Bank.busterLeft_stand);
	
	public static final Dialog ogre = new Dialog("Chief Gork")
	.addStageText(new String[]{"Uurgh! Gork SMASH puny creature!"}, 0)
	.addDialogOption("DO IT! I DARE YOU!", 0, 0)
	.setPortraitImage(Bank.trollLeft_slam);
	
	public static final Dialog ogre1 = new Dialog("Shaman-Chief Merk")
	.addStageText(new String[]{"I am Shaman-Chief Merk!","What you doing here?"}, 0)
	.addDialogOption("Just making my way down town.", 0, 0)
	.setPortraitImage(Bank.trollLeft_walk0);
	
	public static final Dialog bandit = new Dialog("Randall Mudeye", false);
	
	public static final Dialog monk = new Dialog("Leng Shuang")
	.addStageText(new String[]{"Welcome, pilgrim.","How did you find the trip?"}, 0)
	.addDialogOption("Fine, thanks. How has your day been?", 0, 0)
	.addDialogOption("Enough talk kiddo, let's rumble!", 0, 1)
	.addStageText(new String[]{"Oh, it has been good.","I have been listening","to Jimi Hendrix for","hours now. It soothes","the soul!"}, 1)
	.addDialogOption("Rock solid! Jegax loves his use of FEEDBACK.", 1, 0)
	.addDialogOption("Hendrix? You're dead, son!", 1, 1)
	.setPortraitImage(Bank.portraitMonk);
	
	public static final Dialog gatekeeper = new Dialog("Gatekeeper Kyrig")
	.addStageText(new String[]{"Halt! Who goes there?"}, 0)
	.addDialogOption("I am your worst nightmare.", 0, 0)
	.addDialogOption("You don't need to know.", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog olsen = new Dialog("Admiral Olsen")
	.addStageText(new String[]{"My ship is under siege!"}, 0)
	.addDialogOption("Don't worry about a thing.", 0, 0)
	.addDialogOption("That's no good.", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog captain = new Dialog("Guard Captain Oreth", false);
	
	public static final Dialog masterPrince = new Dialog("Blood King")
	.addStageText(new String[]{"Welcome m8"}, 0)
	.addDialogOption("Fight me m80", 0, 0)
	.addDialogOption("Gotta run seeya m8", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog masterLuna = new Dialog("Moon Priest")
	.addStageText(new String[]{"Welcome m8"}, 0)
	.addDialogOption("Fight me m80", 0, 0)
	.addDialogOption("Gotta run seeya m8", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog masterBarbarian = new Dialog("Master of Arms")
	.addStageText(new String[]{"Welcome m8"}, 0)
	.addDialogOption("Fight me m80", 0, 0)
	.addDialogOption("Gotta run seeya m8", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog masterPriest = new Dialog("Priestess of the Sands")
	.addStageText(new String[]{"Welcome m8"}, 0)
	.addDialogOption("Fight me m80", 0, 0)
	.addDialogOption("Gotta run seeya m8", 0, 1)
	.setPortraitImage(Bank.wip);
	
	public static final Dialog masterMesmer = new Dialog("The Trickster")
	.addStageText(new String[]{"Do not trust your senses,","for I will tell them lies."}, 0)
	.addDialogOption("Go me", 0, 0)
	.addDialogOption("Gotta run", 0, 1)
	.setPortraitImage(Bank.portraitLuna);

	public Dialog(String name) {
		this.name = name;
	}
	
	public Dialog(String name, boolean auto) {
		this.name = name;
		this.auto = auto;
	}

	public void update() {
	}
	
	public void clearDialogs(){
		logs = new String[255][3];
		text = new String[255][0];
	}
	
	public Dialog addDialogOption(String msg, int stage, int op){
		logs[stage][op] = msg;
		return this;
	}
	
	public Dialog addStageText(String[] msg, int stage){
		text[stage] = msg;
		return this;
	}
	
	public Dialog setPortraitImage(Image i){
		image = i;
		return this;
	}
	
	public void onDialogSelect(Object o, PanelDialog panel, int stage, int option){
		if(this==trayatBrawl){
			if(stage==0){
				switch(option){
				case 0:
					panel.stage = 1;
					break;
				case 1:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"Bitch I KNOW I'm","better than you!"}, 1850));
					break;
				case 2:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"You heard right!","I'm gonna show you","exactly what that means."}, 1850));
					break;
				}
			}
			if(stage==1){
				switch(option){
				case 0:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"I hate jokes!"}, 1250));
					break;
				case 1:
					panel.stage = 0;
					break;
				}
			}
		}
		if(this==ogre||this==ogre1){
			Display.currentScreen = panel.lastPanel;
			((Player)o).target = panel.user;
		}
		if(this==monk){
			if(stage==0){
				switch(option){
				case 0:
					panel.stage = 1;
					break;
				case 1:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"It is not wise to","wish your own demise!"}, 1850));
					o.posY = 3270;
					break;
				}
			}
			if(stage==1){
				switch(option){
				case 0:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"You think that's funny?"}, 1250));
					break;
				case 1:
					Display.currentScreen = panel.lastPanel;
					((Player)o).target = panel.user;
					o.chats.add(new Textbox(new String[]{"You are wrong!"}, 1250));					
					break;
				}
			}
		}
		if(this==gatekeeper){
			switch(option){
			case 0:
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				o.chats.add(new Textbox(new String[]{"You can not pass!"}, 2250));
			case 1:
				Display.currentScreen = panel.lastPanel;
			}
		}
		if(this==captain){
			switch(option){
			case 0:
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				o.chats.add(new Textbox(new String[]{"En garde!"}, 2250));
			case 1:
				Display.currentScreen = panel.lastPanel;
			}
		}
		if(this==masterPrince){
			switch(option){
			case 0:
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				o.chats.add(new Textbox(new String[]{"With pleasure!"}, 1250));
			case 1:
				Display.currentScreen = panel.lastPanel;
			}
		}
		if(this==masterBarbarian){
			switch(option){
			case 0:
				o.effects.add(new Effect(0, EffectType.sword, (Player) o, -1));
				o.chats.add(new Textbox(new String[]{"First, let's draw some blood!","I will introduce","you to my blade."}, 4000));
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				break;
			}
		}
		if(this==masterPriest){
			switch(option){
			case 0:
				o.effects.add(new Effect(0, EffectType.windhaste, (Player) o, -1));
				o.chats.add(new Textbox(new String[]{"Let's make this quick!"}, 4000));
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				break;
			}
		}
		if(this==masterMesmer){
			switch(option){
			case 0:
				o.effects.add(new Effect(0, EffectType.ethereal, (Player) o, -1));
				o.chats.add(new Textbox(new String[]{"Now you see me..."}, 4000));
				Display.currentScreen = panel.lastPanel;
				((Player)o).target = panel.user;
				break;
			}
		}
	}
	
	public void dialogRender(Graphics g, int x, int y, int w, int h) {
		if(image!=null)g.drawImage(image, x, y, w, h, null);
	}
	
	public Dialog setScene(Image sky, Image bg){
		this.sky = sky;
		this.bg = bg;
		return this;
	}

	public void openChat(Player player, Object o) {
		Display.currentScreen = new PanelDialog(player, o, player.panel, this);
	}
}
