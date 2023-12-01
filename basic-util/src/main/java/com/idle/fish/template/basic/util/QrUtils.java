package com.idle.fish.template.basic.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author idle fish
 * @since 2023/11/29
 */
@Slf4j
@UtilityClass
public class QrUtils {
    /**
     * 生成二维码，以及底部的文本
     *
     * @param data       二维码内容
     * @param width      宽度
     * @param height     高度，如果不设置底部文本，则建议高度和宽度保持一致；如果设置底部文本，则高度需要高一些
     * @param filePath   生成的二维码保存路径
     * @param bottomText 底部文本，可以为空
     */
    public void generateQRCode(String data, int width, int height, String filePath, String bottomText) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            // 设置字符编码
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.displayName());
            // 错误纠正级别
            hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
            // 二维码边距
            hints.put(EncodeHintType.MARGIN, 1);

            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            // 创建BufferedImage对象来表示QR码
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // 控制二维码的颜色
                    image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            if (StringUtils.isNotEmpty(bottomText)) {
                // 将QR码保存到文件
                Graphics2D g2 = image.createGraphics();
                // 设置生成图片的文字样式
                Font font = new Font("黑体", Font.BOLD, 25);
                g2.setFont(font);
                g2.setPaint(Color.BLACK);
                // 设置字体在图片中的位置 在这里是居中
                FontRenderContext context = g2.getFontRenderContext();
                Rectangle2D bounds = font.getStringBounds(bottomText, context);
                double x = (width - bounds.getWidth()) / 2;
                //double y = (height - bounds.getHeight()) / 2; //Y轴居中
                double y = (height - bounds.getHeight());
                double ascent = -bounds.getY();
                double baseY = y + ascent;
                // 防止生成的文字带有锯齿
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                // 在图片上生成文字
                g2.drawString(bottomText, (int) x, (int) baseY);
            }
            File qrCodeFile = new File(filePath);
            ImageIO.write(image, "png", qrCodeFile);
        } catch (Exception e) {
            log.error("生成二维码失败", e);
        }
    }
}
