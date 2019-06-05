package Tools;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CompressedImage {
    private BufferedImage bi = null;
    byte[] imageBytes;
    int width;
    int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CompressedImage(BufferedImage bi) {
        width = bi.getWidth();
        height = bi.getHeight();

        this.bi = bi;

        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        ImageOutputStream jpgStream = null;
        try {
            jpgStream = ImageIO.createImageOutputStream(imageStream);
            boolean b = ImageIO.write(bi, "png", jpgStream);
            System.out.println(b);
            jpgStream.close();
            imageBytes = imageStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        if (bi == null) {
            try {
                ByteArrayInputStream input = new ByteArrayInputStream(imageBytes);
                bi = ImageIO.read(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bi;
    }

    public void dispose() {
        bi.flush();
        bi = null;
    }
}
