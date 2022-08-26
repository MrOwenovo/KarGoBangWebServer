package com.example.tool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Slf4j
public class PictureTools {

    /**
     * 将BufferedImage图片转化成字符串储存在数据库中
     */
    public static String imageToString(BufferedImage image,String format) {
        StringBuilder sb2 = new StringBuilder();
        byte[] img = getBytes(image,format);

        for (byte b : img) {
            if (sb2.length() == 0) {
                sb2.append(b);
            } else {
                sb2.append(",").append(b);
            }
        }
        return sb2.toString();

    }
    // 将BufferImage 转换为字节数组
    private static byte[] getBytes(BufferedImage image, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (format.equalsIgnoreCase("png"))
                ImageIO.write(image, "PNG", baos);
            if (format.equalsIgnoreCase("jpg"))
                ImageIO.write(image, "JPG", baos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }


//    ------------------    ----------------------------------------------------------------


    /**
     * 将数据库中的图像字符串解析成BufferedImage
     */
    public static BufferedImage stringToImage(String string) {
        if (string.contains(",")) {
            // 这里没有用自带的那个分割方法，原因是分割速度没有这个快，有人考证在分割字符长度很大的情况下，系统的分割方法容易造成内存溢出。
            // 还有subString方法，不知道最新版本的jdk改了源码了么
            String[] imagetemp = split(string, ",");
            byte[] image = new byte[imagetemp.length];
            for (int i = 0; i < imagetemp.length; i++) {
                image[i] = Byte.parseByte(imagetemp[i]);
            }
            return saveImage(image);
        } else {
            log.error("不能解析");
            return null;
        }
    }

    // 将byte[] 转换为BufferedImage
    private static BufferedImage readImage(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return ImageIO.read(bais);
    }

    // 保存图片
    @SneakyThrows
    public static BufferedImage saveImage(byte[] imgages) {
        BufferedImage bis = readImage(imgages);
        return bis;
    }

    // 分割字符串
    public static String[] split(String s, String token) {
        if (s == null)
            return null;
        if (token == null || s.length() == 0)
            return new String[] { s };
        int size = 0;
        String[] result = new String[4];
        while (s.length() > 0) {
            int index = s.indexOf(token);
            String splitOne = s;
            if (index > -1) {
                splitOne = s.substring(0, index);
                s = s.substring(index + token.length());
            } else {
                s = "";
            }
            if (size >= result.length) {
                String[] tmp = new String[result.length * 2];
                System.arraycopy(result, 0, tmp, 0, result.length);
                result = tmp;
            }
            if (splitOne.length() > 0) {
                result[size++] = splitOne;
            }
        }
        String[] tmp = result;
        result = new String[size];
        System.arraycopy(tmp, 0, result, 0, size);
        return result;
    }


    /**
     * 将BufferedImage转为image
     * @param image image图像
     * @return BufferedImage
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }

        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();

        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }
}