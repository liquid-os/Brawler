package code;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main{
	
		public static Frame f;
	
		public static void main(String[] args)
		{
			Main m = new Main();
			m.start();
		}
		
		public void start(){
			f = new Frame();
			if(Properties.beta){
				final JFrame frame = new JFrame();
				frame.setUndecorated(false);
				frame.setTitle("Bug Reporter");
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setBounds(20, 20, 260, 80);
				frame.setVisible(true);
				frame.setIconImage(Bank.bug);
				frame.setEnabled(true);
				frame.setAlwaysOnTop(true);
				JButton bug = new JButton();
				bug.setBackground(Color.RED);
				bug.setForeground(Color.WHITE);
				bug.setText("Report Issue");
				frame.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e) {
						frame.dispose();
					}
				});
				bug.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
						if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
						try {
						   desktop.browse(new URL("http://www.reddit.com/r/Herobrawl/comments/2i2p0a/hero_brawl_beta_bug_report_thread/").toURI());
						} catch (Exception ex) {
						   ex.printStackTrace();
						}
					}
				}
			});
			frame.add(bug);
		}
	}
}
