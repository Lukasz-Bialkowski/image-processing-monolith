package uni.master.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uni.master.entity.ColorRGB;
import uni.master.entity.ColorXY;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CalculationService {

    private double countDistance(ColorXY color1, ColorXY color2) {
        return Math.sqrt(
                (color1.x - color2.x) * (color1.x - color2.x) +
                        (color1.y - color2.y) * (color1.y - color2.y));
    }

    private double countDistance(ColorRGB color1, ColorRGB color2) {
        return Math.sqrt(
                (color1.r - color2.r) * (color1.r - color2.r) +
                        (color1.g - color2.g) * (color1.g - color2.g) +
                        (color1.b - color2.b) * (color1.b - color2.b));
    }

    private int[][] pixelAdjustment(int[][] map, int w, int h, int p, int x, int y) {
        double d;
        ColorRGB color = new ColorRGB(map[x][y]);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                d = countDistance(new ColorXY(x, y), new ColorXY(i, j));
                ColorRGB iColor = new ColorRGB(map[i][j]);
                // roznica w czerwonym
                int rDif = (color.r - iColor.r) / 2;
                // roznica w zielonym
                int gDif = (color.g - iColor.g) / 2;
                // roznica w niebieskim
                int bDif = (color.b - iColor.b) / 2;
                rDif *= Math.exp((-(d * d) / 5)) * (1 / (p + 1));
                gDif *= Math.exp((-(d * d) / 5)) * (1 / (p + 1));
                bDif *= Math.exp((-(d * d) / 5)) * (1 / (p + 1));
                map[i][j] += ((rDif << 16) & 0x00ff0000) | ((gDif << 8) & 0x0000ff00) | (bDif & 0x000000ff);
                //distance factor * change amount
            }
        }
        return map;
    }

    private ColorXY findBestPixel(int[][] map, int w, int h, int color) {
        ColorXY bestPixel = new ColorXY(0, 0);
        double lowdist = countDistance(new ColorRGB(map[0][0]), new ColorRGB(color));
        double temp;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                temp = countDistance(new ColorRGB(map[i][j]), new ColorRGB(color));
                if (temp < lowdist) {
                    lowdist = temp;
                    bestPixel = new ColorXY(i, j);
                }
            }
        }
        return bestPixel;
    }

    public void calculate(String imageId, int loops) throws Exception {
        /**
         * Initialization
         * */
        BufferedImage sourceImage = ImageIO.read(new ClassPathResource("static/assets/"+ imageId).getFile());
        int w = sourceImage.getWidth();
        int h = sourceImage.getHeight();
        BufferedImage trainingImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        BufferedImage finalImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int color;
        int[][] inputMap = new int[w][h];
        int[][] trainingMap = new int[w][h];

        /**
         * Source image map processing
         * */
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                color = sourceImage.getRGB(i, j);
                inputMap[i][j] = color;
                int r = 128 * (w - i) / w + 128 * j / h;
                int g = 128 * (w - i) / w + 128 * (h - j) / h;
                int b = 128 * i / w + 128 * (h - j) / h;
                trainingMap[i][j] = (r << 16) | (g << 8) | b;
            }
        }

        /**
         * Training map calculation
         * */
        for (int z = 0; z < loops; z++) {
            System.out.println("Loop #" + z);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    ColorXY bestPixel = findBestPixel(trainingMap, w, h, inputMap[i][j]);
                    trainingMap = pixelAdjustment(trainingMap, w, h, z, bestPixel.x, bestPixel.y);
                    trainingImage.setRGB(i, j, trainingMap[i][j]);
                    System.out.print("(" + i + ", " + j + ")");
                }
                System.out.println();
            }
        }

        /**
         * Final image generation
         * */
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                ColorXY bestPixel = findBestPixel(trainingMap, w, h, inputMap[i][j]);
                finalImage.setRGB(i, j, trainingMap[bestPixel.x][bestPixel.y]);
            }
        }

        /**
         * Save output to files
         * */
//        Path path = Paths.get(System.getProperty("user.home"));
//        if(!Files.exists(Paths.get(path.toString(), "images")))
//            Files.createDirectory(Paths.get(path.toString(), "images"));
//
//        ImageIO.write(trainingImage, "jpg",
//                new File(Paths.get(path.toString(), "images") + File.separator + "p" + imageId));
//        ImageIO.write(finalImage, "jpg",
//                new File(Paths.get(path.toString(), "images") + File.separator + "f" + imageId));
        ImageIO.write(trainingImage, "jpg", new File("src/main/resources/static/assets/p" + imageId));
        ImageIO.write(finalImage, "jpg", new File("src/main/resources/static/assets/f" + imageId));
    }
}
