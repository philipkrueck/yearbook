package de.pomc.yearbook.dummyData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageLoaderImpl implements ImageLoader {

    @Override
    public byte[] loadImageFromPath(String path) {
        BufferedImage bufferedImage;

        try {
            File file = new File(path);
            bufferedImage = ImageIO.read(file);
        } catch (IOException ioException) {
            System.out.println("Failed to read in image file name: " + ioException.getMessage());
            return null;
        }

        return byteArrayFromBufferedImage(bufferedImage);
    }

    private byte[] byteArrayFromBufferedImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException ioException) {
            System.out.println("Failed to write outputStream to: " + ioException.getMessage());
            return null;
        }

        return outputStream.toByteArray();
    }
}
