package code;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Filestream {
	
	public static Image getImageFromPath(String s)
	{
		try {
			return ImageIO.read(Filestream.class.getResource(s));
		} catch (IOException e) {
			System.out.println("Image file not found @ "+s);
		}
		return null;
	}
	
	public static Image loadGif(final String url) {
	    try {
	        final Toolkit tk = Toolkit.getDefaultToolkit();
	        final URL path = Filestream.class.getResource(url);
	        final Image img = tk.createImage(path);
	        tk.prepareImage(img, -1, -1, null);
	        return img;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	} 
	
	public boolean resExists(String file){
		boolean ret = false;
		if(Bank.resPack!=null){
			File f = new File(Bank.path+"resourcepacks/"+Bank.resPack+"/"+file);
			if(f.exists())ret = true;
		}
		return ret;
	}
	
	public String getResPath(){
		return Bank.path+"resourcepacks/"+Bank.resPack+"/";
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
