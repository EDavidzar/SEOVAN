/*
 * To change this template, choose Tools | Templates
 * *
 */
package beleris.es.finalutils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 ** @author Emilio David Diaus López 2008-2021
 */
public class GraphicImageManager {

    BufferedImage Image_buiffer;
    Graphics2D gGraphic;
    File FImageOutput = new File("graficos.png");

    /**
     *
     */
    public GraphicImageManager() {
        Image_buiffer = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        gGraphic = Image_buiffer.createGraphics();
    }

    /**
     *
     * @param imx
     * @param imy
     */
    public GraphicImageManager(int imx, int imy) {
        Image_buiffer = new BufferedImage(imx, imy, BufferedImage.TYPE_INT_ARGB);
        gGraphic = Image_buiffer.createGraphics();
    }

    /**
     *
     * @param x
     * @param y
     * @param sx
     * @param sy
     */
    public void DrawWhiteBackGround(int x, int y, int sx, int sy) {
        gDrawBackground(x, y, sx, sy, Color.white);
    }

    /**
     *
     * @param x
     * @param y
     * @param sx
     * @param sy
     * @param inColor
     */
    public void gDrawBackground(int x, int y, int sx, int sy, Color inColor) {
        gGraphic.setColor(inColor);
        gGraphic.fillRect(x, y, sx, sy);

    }

    /**
     *
     * @param x
     * @param y
     * @param sx
     * @param sy
     */
    public void DrawFrame(int x, int y, int sx, int sy) {
        String tmpTitle = "GRAFICOS";
        Font FB = new Font("Tahoma", Font.BOLD, 18);
        // get metrics from the graphics
        FontMetrics metrics = gGraphic.getFontMetrics(FB);
        // get the height of a line of text in this font and render context
        //int alto = metrics.getHeight();
        // get the advance of my text in this font and render context
        int iwidth = metrics.stringWidth(tmpTitle);
        // calculate the size of a box to hold the text with some padding.
        gGraphic.setFont(FB);
        gGraphic.setColor(Color.black);
        gGraphic.drawString(tmpTitle, x + sx / 2 - iwidth / 2, y + 25);
        gGraphic.drawRect(x, y, sx, sy);
        gGraphic.drawRect(x + 1, y + 1, sx - 2, sy - 2);
        gGraphic.drawRect(x + 5, y + 5, sx - 11, sy - 11);
        gGraphic.drawRect(x + 6, y + 6, sx - 13, sy - 13);
    }

    /**
     *
     * @param Titulo
     * @param porcentaje
     * @param x
     * @param y
     * @param micolor
     */
    public void WritePercentBar(String Titulo, float porcentaje, int x, int y, Color micolor) {
        Font FB = gGraphic.getFont();
        FontMetrics metrics = gGraphic.getFontMetrics(FB);
        // get the height of a line of text in this font and render context
        int alto = metrics.getHeight();
        // get the advance of my text in this font and render context
        //int iwidth = metrics.stringWidth(Titulo);
        //Grafico.setBackground(micolor);
        gGraphic.setColor(Color.black);
        gGraphic.drawString(Titulo, x, y);
        gGraphic.setColor(micolor);
        gGraphic.draw3DRect(x, y + alto / 4 + 1, 500, 25, true);
        gGraphic.fill3DRect(x, y + alto / 4 + 1, (int) (500.0 * porcentaje), 25, true);
    }

    /**
     *
     */
    public void WriteImage() {
        try {
            ImageIO.write(Image_buiffer, "png", FImageOutput);
        } catch (IOException e) {
        }

    }

    /**
     *
     * @param x
     * @param y
     * @param fPercentAfirmativeTotalQuestions
     * @param fPorcenFalladas
     * @param fPorcetExclu
     */
    public void DrawArc(int x, int y, float fPercentAfirmativeTotalQuestions, float fPorcenFalladas, float fPorcetExclu) {
        int radio = 200;
        int zf = 0, zcounter = 0;
        zf = (int) (fPercentAfirmativeTotalQuestions * 360);
        gGraphic.setColor(Color.blue);
        gGraphic.fillArc(x, y, radio, radio, zcounter, zf);
        zcounter += zf;
        gGraphic.setColor(Color.red);
        zf = (int) (fPorcenFalladas * 360);
        gGraphic.fillArc(x, y, radio, radio, zcounter, zf);
        gGraphic.setColor(Color.gray);
        zcounter += zf;
        zf = 360 - zcounter;
        gGraphic.fillArc(x, y, radio, radio, zcounter, zf);
    }
}
