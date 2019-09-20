package drawing;

import java.awt.Graphics;
import java.awt.Image;

public class BackgroundImage extends Canvas{
	
	Image background;
	
	public BackgroundImage(Image bkg) {
		background = bkg;
		return;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		return;
	}
}
