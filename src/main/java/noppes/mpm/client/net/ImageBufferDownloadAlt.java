package noppes.mpm.client.net;

import net.minecraft.client.renderer.ImageBufferDownload;
import noppes.mpm.ModelData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageBufferDownloadAlt extends ImageBufferDownload {
    private ModelData data;

    public ImageBufferDownloadAlt(ModelData data) {
        this.data = data;
    }

    @Override
    public BufferedImage parseUserSkin(BufferedImage bufferedImage) {
        if (bufferedImage == null)
            return null;

        if (data != null && bufferedImage.getWidth() != bufferedImage.getHeight())
            bufferedImage = transformToNewFormat(bufferedImage);

        return bufferedImage;
    }

    private BufferedImage transformToNewFormat(BufferedImage image) {
        final int resolution = image.getWidth();

        final int limbAreaY = resolution / 4;
        final int limbAreaSize = resolution / 4;
        final int bodyAreaSize = (resolution * 3) / 8;
        final int handsOffset = limbAreaSize + bodyAreaSize;

        final int flippedY = resolution - limbAreaSize;
        final int flippedLegsX = limbAreaSize;
        final int flippedHandsX = limbAreaSize + limbAreaSize;

        BufferedImage newImage = new BufferedImage(resolution, resolution, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();

        g2d.drawImage(image, 0, 0, null);

        BufferedImage leg = flipLimb(image.getSubimage(0, limbAreaY, limbAreaSize, limbAreaSize));
        g2d.drawImage(leg, flippedLegsX, flippedY, null);

        BufferedImage hand = flipLimb(image.getSubimage(handsOffset, limbAreaY, limbAreaSize, limbAreaSize));
        g2d.drawImage(hand, flippedHandsX, flippedY, null);

        g2d.dispose();
        return newImage;
    }

    private BufferedImage flipLimb(BufferedImage image) {
        final int res = image.getWidth();
        final int shortS = res / 4;
        final int longS = res - shortS;
        final int slot1 = 0;
        final int slot2 = shortS;
        final int slot3 = shortS * 2;
        final int slot4 = shortS * 3;

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = newImage.createGraphics();

        BufferedImage top = image.getSubimage(slot2, 0, shortS, shortS);
        drawFlipped(g2d, top, slot2, 0);

        BufferedImage bot = image.getSubimage(slot3, 0, shortS, shortS);
        drawFlipped(g2d, bot, slot3, 0);

        BufferedImage right = image.getSubimage(slot1, shortS, shortS, longS);
        drawFlipped(g2d, right, slot3, shortS);

        BufferedImage front = image.getSubimage(slot2, shortS, shortS, longS);
        drawFlipped(g2d, front, slot2, shortS);

        BufferedImage left = image.getSubimage(slot3, shortS, shortS, longS);
        drawFlipped(g2d, left, slot1, shortS);

        BufferedImage back = image.getSubimage(slot4, shortS, shortS, longS);
        drawFlipped(g2d, back, slot4, shortS);

        g2d.dispose();
        return newImage;
    }

    private void drawFlipped(Graphics2D g2d, BufferedImage image, int x, int y) {
        g2d.drawImage(image, x + image.getWidth(), y, -image.getWidth(), image.getHeight(), null);
    }
}
