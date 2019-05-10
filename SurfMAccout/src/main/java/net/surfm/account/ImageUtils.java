package net.surfm.account;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;

import net.surfm.constant.RandomConstant;

public class ImageUtils {

	public static String createRandomWordImg(OutputStream out) throws IOException{
		BufferedImage bi = ImageIO.read(ImageUtils.class.getResourceAsStream("/static/img/dot.jpg"));
        BufferedImage img = new BufferedImage(
        		bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(bi, 0, 0, null);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, (int)(bi.getHeight()*0.4f)));
        String s = RandomStringUtils.random(5, RandomConstant.ABC123_STRING);
        FontMetrics fm = g2d.getFontMetrics();
        int y = fm.getHeight();
        g2d.drawString(s, 20, y);
        g2d.dispose();
		ImageIO.write(img, "png", out);
		return s;
	}
	
}
