package code;

import java.awt.Graphics;
import java.util.ArrayList;

public class PanelSettings extends PanelBase{
	
	GUIKey left = new GUIKey("Run Left", Keybind.LEFT, 30, 80, 50, 50);
	GUIKey right = new GUIKey("Run Right", Keybind.RIGHT, 30, 80+60, 50, 50);
	GUIKey jump = new GUIKey("Jump", Keybind.JUMP, 30, 80+60*2, 50, 50);

	public PanelSettings() {
		guis.add(left);
		guis.add(right);
		guis.add(jump);
	}

	public void onUpdate() {
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.windowbg, 0, 0, Properties.width, Properties.height, null);
	}
}
