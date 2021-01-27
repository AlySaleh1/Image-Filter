import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {

    public static String changeSeparator(String path) {
        String newPath = path;
        newPath = newPath.replace("\\","\\\\");
        return newPath;
    }
    public static void main(String[] args) throws IOException {

        System.out.print("Please enter an input image path: ");
        Scanner scan = new Scanner(System.in);
        String inputPath = changeSeparator(scan.nextLine());
        StringBuilder tmpPath = new StringBuilder(inputPath);

        if (inputPath.charAt(0) == '\"' && inputPath.charAt(inputPath.length() - 1) == '\"') {
            tmpPath = tmpPath.deleteCharAt(inputPath.length() - 1);
            tmpPath = tmpPath.deleteCharAt(0);
        }
        inputPath = tmpPath.toString();

        System.out.println("Please enter the path of the output folder: ");
        String outputPath = changeSeparator(scan.nextLine()) + "\\\\FilteredImg.png";
        scan.close();
        System.out.println("File will be saved at: " + outputPath);
        scan.close();

        File original = new File(inputPath);
        BufferedImage img = ImageIO.read(original);

        int width = img.getWidth();
        int height = img.getHeight();

        BufferedImage edit = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int rgb = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        File newFile = new File(outputPath);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgb = img.getRGB(x, y);
                Color pixel = new Color(rgb, true);
                //https://www.techrepublic.com/blog/how-do-i/how-do-i-convert-images-to-grayscale-and-sepia-tone-using-c/
                red = (int) Math.floor(0.393 * pixel.getRed() + 0.769 * pixel.getGreen() + 0.189 * pixel.getBlue());
                green = (int) Math.floor(0.349 * pixel.getRed() + 0.686 * pixel.getGreen() + 0.168 * pixel.getBlue());
                blue = (int) Math.floor(0.272 * pixel.getRed() + 0.534 * pixel.getGreen() + 0.131 * pixel.getBlue());

                //check if any color has a value over 255
                if(red > 255) red = 255;
                if (green > 255) green=255;
                if (blue > 255) blue=255;

                Color sepiaPixel = new Color(red, green, blue);
                edit.setRGB(x, y, sepiaPixel.getRGB());
            }
        }
        ImageIO.write(edit, "PNG", newFile);
    }
}
