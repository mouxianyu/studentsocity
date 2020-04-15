package com.mouxianyu.studentsociety.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @Description 图形验证码
 * @Author 张桂树 guishu.zhang@luckincoffee.com
 * @Date 2019/8/29 9:14
 */
public class ImageCheckCode {
    private int width=70;
    private int height=35;
    private Random random=new Random();
    private String[] fontNames={"Bradley Hand ITC","Footlight MT","Garamond","Gill Sans MT","HarringTon","Ink Free","Algerian"};
    private Color bgColor=new Color(255,255,255);
    private String text;

    /**
     * 生成随机字体颜色
     * @return java.awt.Color
     */
    private Color randomColor(){
        int red= random.nextInt(150);
        int green=random.nextInt(150);
        int blue=random.nextInt(150);
        return new Color(red,green,blue);
    }

    /**
     * 使用随机字体
     * @return java.awt.Font
     */
    private Font randomFont(){
        int index=random.nextInt(fontNames.length);
        String fontName=fontNames[index];
        //获得随机样式，0表示无样式，1表示粗体，2表示斜体，3表示斜粗体
        int style=random.nextInt(4);
        int fontSize=random.nextInt(5)+24;
        return new Font(fontName,style,fontSize);
    }

    /**
     * 画干扰线
     * @param image 图片
     */
    private void drawLine(BufferedImage image){
        int num=3;
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        for(int i=0;i<num;i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);

            //设置笔刷大小
            g2.setStroke(new BasicStroke(1.5F));
            //干扰线颜色
            g2.setColor(randomColor());
            g2.drawLine(x1,y1,x2,y2);
        }
    }

    /**
     * 生成一个随机字符
     * @return char
     */
    private char randomChar(){
        String code = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        int index=random.nextInt(code.length());
        return code.charAt(index);
    }

    /**
     * 创建BufferedImage
     * @return java.awt.image.BufferedImage
     */
    private BufferedImage createImage(){
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2= (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0,0,width,height);
        return image;
    }

    /**
     * 获取验证码图片
     * @return java.awt.image.BufferedImage
     */
    public BufferedImage getCheckCodeImage(){
        BufferedImage image=createImage();
        Graphics2D g2= (Graphics2D) image.getGraphics();
        //存储验证码文本
        StringBuilder sb=new StringBuilder();

        int charNum = 4;
        for (int i = 0; i< charNum; i++){
            String s=randomChar()+"";
            sb.append(s);
            double x=i*1.0*width/4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s,(int)x,height-5);
        }

        this.text=sb.toString();
        drawLine(image);
        return image;
    }

    /**
     * 获取验证码文本
     * @return java.lang.String
     */
    public String getText(){
        return text;
    }

    /**
     * 输出验证码图片
     * @param image tup
     */
    public static void output(BufferedImage image, OutputStream outputStream) throws IOException {
        ImageIO.write(image,"JPEG",outputStream);
    }
}
