package com.panshi.hujin2.message.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * @author ycg 2018/11/28 15:46
 */
public class QrBarCodeUtil {
    private static final String CODE = "utf-8";
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成一维码（128）
     *
     * @param str
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage getBarcode(String str, Integer width, Integer height) {
        if (width == null || width < 200) {
            width = 200;
        }
        if (height == null || height < 200) {
            height = 200;
        }
        // 文字编码
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, CODE);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, hints);
            return toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getBarcodeWriteFile(String str, Integer width, Integer height) throws IOException {
        BufferedImage image = getBarcode(str, width, height);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

    /**
     * 转换成图片
     *
     * @param matrix
     * @return
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void main(String[] args) {
        try {
            File file = new File("http://192.168.24.100:9041/images/1111.png");
            // 生成一维码
            getBarcodeWriteFile("34191.09008 00323.700039 60948.860006 7 76540000107000", 200, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
