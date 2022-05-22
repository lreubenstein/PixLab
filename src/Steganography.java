import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Steganography {
    /**
     * Clear the lower (rightmost) bits in a pixel
     */
    public static void clearLow(Pixel p){
        int red = p.getRed();
        int green = p.getGreen();
        int blue = p.getBlue();

        red = red/4*4;
        green = green/4*4;
        blue = blue/4*4;

        p.setRed(red);
        p.setBlue(blue);
        p.setGreen(green);
    }
    public static Picture testClearLow(Picture p){
        Picture newpic = new Picture(p);
        Pixel[][] pixels = newpic.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                clearLow(pixelObj);
            }
        }
        return newpic;
    }

    public static void setLow(Pixel p, Color c){
        int red = p.getRed();
        int green = p.getGreen();
        int blue = p.getBlue();

        int red2 = c.getRed()/64;
        int green2 = c.getGreen()/64;
        int blue2 = c.getBlue()/64;

        red = red/4*4 + red2;
        green = green/4*4 + green2;
        blue = blue/4*4 + blue2;

        p.setRed(red);
        p.setBlue(blue);
        p.setGreen(green);
    }
    public static Picture testSetLow(Picture p, Color c){
        Picture newpic = new Picture(p);
        Pixel[][] pixels = newpic.getPixels2D();
        for (Pixel[] rowArray : pixels) {
            for (Pixel pixelObj : rowArray) {
                setLow(pixelObj, c);
            }
        }
        return newpic;
    }
    public static Picture revealPicture(Picture hidden){
        /** Sets the highest two bits oe each pixel's colors
         * to the lowest two bits of each pixel's colors
         */
        Picture copy = new Picture(hidden);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D();
        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++){
                Color col = source[r][c].getColor();
                int red = col.getRed();
                int green = col.getGreen();
                int blue = col.getBlue();

                red = (red%4)*64;
                green = (green%4)*64;
                blue = (blue%4)*64;
                pixels[r][c].setRed(red);
                pixels[r][c].setGreen(green);
                pixels[r][c].setBlue(blue);
            }

        }
        return copy;
    }

    /**
     * Determines whether sectet can behidden insource, which
     * is true if source and secret are the same simensions
     * @param source is not null
     * @param secret is not null
     * @return true if secret can be hidden in source, false otherwise
     */
    public static boolean canHide(Picture source, Picture secret){
//        return source.getHeight() == secret.getHeight() &&
//                source.getWidth() == secret.getWidth();
        return source.getHeight() >= secret.getHeight() &&
                source.getWidth() >= secret.getWidth();
    }

    public static Picture hidePicture(Picture source, Picture secret){

        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] secretPix = secret.getPixels2D();
        for(int r = 0; r < pixels.length; r++) {
            for (int c = 0; c < pixels[0].length; c++) {
                Color col = secretPix[r][c].getColor();
                setLow(pixels[r][c],col);
            }
        }
        return copy;
    }
    public static Picture hidePicture(Picture source, Picture secret,
                                      int startrow, int startcol){

        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] secretPix = secret.getPixels2D();
        int height = secretPix.length;
        int width = secretPix[0].length;

        for(int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Color col = secretPix[r][c].getColor();
                setLow(pixels[r+startrow][c+startcol],col);
            }
        }
        return copy;
    }
    public static boolean  isSame(Picture pict1, Picture pict2){
        Pixel[][] pixels1 = pict1.getPixels2D();
        Pixel[][] pixels2 = pict2.getPixels2D();
        if (pixels1.length != pixels2.length) return false;
        if (pixels1[0].length != pixels2[0].length) return false;

        for(int r = 0; r < pixels1.length; r++) {
            for (int c = 0; c < pixels1[0].length; c++) {
                Pixel p1 = pixels1[r][c];
                Pixel p2 = pixels2[r][c];
                if(p1.getRed()!= p2.getRed() ||
                        p1.getGreen() != p2.getGreen() ||
                        p1.getBlue() != p2.getBlue()) return false;
            }
        }
        return true;
    }
    public static ArrayList<Point> findDifferences(Picture pict1, Picture pict2){
        ArrayList<Point> plist = new ArrayList<Point>();
        Pixel[][] pixels1 = pict1.getPixels2D();
        Pixel[][] pixels2 = pict2.getPixels2D();
        if (pixels1.length != pixels2.length) return plist;
        if (pixels1[0].length != pixels2[0].length) return plist;

        for(int r = 0; r < pixels1.length; r++) {
            for (int c = 0; c < pixels1[0].length; c++) {
                Pixel p1 = pixels1[r][c];
                Pixel p2 = pixels2[r][c];
                if(p1.getRed()!= p2.getRed() ||
                        p1.getGreen() != p2.getGreen() ||
                        p1.getBlue() != p2.getBlue()) plist.add(new Point(r,c));
            }
        }
        return plist;
    }
    public static Picture showDifferentArea(Picture pict, ArrayList<Point> points){
        Picture newpict = new Picture(pict);
        Pixel[][] pixels = newpict.getPixels2D();
        int minrow = pict.getHeight();
        int maxrow = 0;
        int mincol = pict.getWidth();
        int maxcol = 0;
        for(Point p: points){
            int r = p.getRow();
            if(r > maxrow) maxrow = r;
            if(r < minrow) minrow = r;
            int c = p.getCol();
            if(c > maxcol) maxcol = c;
            if(c < mincol) mincol = c;
        }
        for(int r = minrow; r <= maxrow; r++){
            pixels[r][mincol].setColor(Color.PINK);
            pixels[r][maxcol].setColor(Color.PINK);
        }
        for(int c = mincol; c <= maxcol; c++){
            pixels[minrow][c].setColor(Color.PINK);
            pixels[maxrow][c].setColor(Color.PINK);
        }
        return newpict;
    }
    public static ArrayList<Integer> encodeString(String s){
        s = s.toUpperCase();
        ArrayList secret = new ArrayList<String>();
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < s.length(); i++){
            if(s.charAt(i)==' ') secret.add(27);
            else {
                int code = alpha.indexOf(s.charAt(i)) + 1;
                secret.add(code);
            }
        }
        secret.add(0);
        return secret;
    }
    public static String decodeString(ArrayList<Integer>codes){
        String answer = "";
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(Integer x: codes){
            if(x==27) answer += " ";
            else if(x==0) return answer;
            else {
                char c = alpha.charAt(x - 1);
                answer += c;
            }
        }
        return answer;
    }

    private static int [] getBitPairs(int num){
        int [] pairs = new int[3];
        pairs[2] = num/16;
        pairs[1] = num/4%4;
        pairs[0] = num%4;
        return pairs;
    }
    private static void setIntLow(Pixel p, int x){
        int [] nums = getBitPairs(x);
        int red = p.getRed();
        int green = p.getGreen();
        int blue = p.getBlue();

        red = red/4*4 + nums[0];
        green = green/4*4 + nums[1];
        blue = blue/4*4 + nums[2];

        p.setRed(red);
        p.setBlue(blue);
        p.setGreen(green);
    }
    private static int getIntLow(Pixel p){
        int red = p.getRed()%4;
        int green = p.getGreen()%4;
        int blue = p.getBlue()%4;
        int answer = blue*16+green*4+red;
        return answer;
    }
    public static Picture hideText(Picture source, String s){
        int counter = 0;
        ArrayList<Integer> code = encodeString(s);
        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D();

        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++){
                int x = code.get(counter);
                setIntLow(pixels[r][c], x);
                if(x==0) return copy;
                counter++;
            }
        }
        return copy;
    }
    public static String revlealText(Picture source){
        Picture copy = new Picture(source);
        Pixel[][] pixels = copy.getPixels2D();
        ArrayList<Integer> codes = new ArrayList<Integer>();

        for(int r = 0; r < pixels.length; r++){
            for(int c = 0; c < pixels[0].length; c++) {
                int x = getIntLow(pixels[r][c]);
                if(x == 0) return decodeString(codes);
                codes.add(x);
            }
        }
        String answer = decodeString(codes);
        return answer;
    }
    public static void main(String[] args) {
        // Activity 1
//        Picture beach = new Picture("beach.jpg");
//        beach.explore();
//        Picture copy = testClearLow(beach);
//        copy.explore();
//
//        Picture beach2 = new Picture("beach.jpg");
//        //beach2.explore();
//        Picture copy2 = testSetLow(beach2, Color.PINK);
//        copy2.explore();
//
//        Picture copy3 = revealPicture(copy2);
//        copy3.explore();

        // Activity 2
//        Picture swan = new Picture("swan.jpg");
//        Picture gorge = new Picture("gorge.jpg");
//        swan.explore();
//        gorge.explore();
//        if(canHide(swan,gorge)) {
//            Picture combined = hidePicture(swan, gorge);
//            combined.explore();
//            Picture revealed = revealPicture(combined);
//            revealed.explore();
//        }

//        Picture beach = new Picture("beach.jpg");
//        Picture robot = new Picture("robot.jpg");
//        Picture flower1 = new Picture("flower1.jpg");
//        beach.explore();
//        robot.explore();
//        flower1.explore();
//
//        Picture hidden1 = hidePicture(beach, robot, 65,208);
//        Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
//        hidden2.explore();
//
//        Picture unhidden = revealPicture(hidden2);
//        unhidden.explore();
//        Picture swan = new Picture("swan.jpg");
//        Picture swan2 = new Picture("swan.jpg");
//        System.out.println(isSame(swan, swan2));
//        swan = testClearLow(swan);
//        System.out.println(isSame(swan, swan2));
//        Picture arch = new Picture("arch.jpg");
//        Picture koala = new Picture("koala.jpg");
//        Picture robot1 = new Picture("robot.jpg");
//        ArrayList<Point>pointlist = findDifferences(arch, arch);
//        System.out.println("Pointlist comparing 2 identical pictures " + pointlist.size());
//        pointlist = findDifferences(arch, koala);
//        System.out.println("differences - different sized pictures" + pointlist.size());
//        Picture arch2 = hidePicture(arch, robot1, 65,102);
//        pointlist = findDifferences(arch, arch2);
//        System.out.println("differences - after hiding a picture" + pointlist.size());
//        arch.explore();
//        arch2.explore();

//        Picture hall  = new Picture("femaleLionAndHall.jpg");
//        Picture robot2 = new Picture("robot.jpg");
//        Picture flower2 = new Picture("flower1.jpg");
//        Picture hall2 = hidePicture(hall, robot2, 50,300);
//        Picture hall3 = hidePicture(hall2, flower2, 115, 275);
//        hall3.explore();
//        if(!isSame(hall, hall3)){
//            Picture hall4 = showDifferentArea(hall, findDifferences(hall,hall3));
//            hall4.explore();
//            Picture unhiddenHall3 = revealPicture(hall3);
//            unhiddenHall3.explore();
//        }
        ArrayList<Integer> secret = encodeString("Hello World");
        System.out.println(secret);
        String decoded = decodeString(secret);
        System.out.println(decoded);

//        for (int i = 0; i < 64; i++) {
//            System.out.println(Arrays.toString(getBitPairs(i)));
//        }


        String preamble = "We the People of the United States in Order to form a more perfect Union establish Justice insure domestic Tranquility provide for the common defence promote the general Welfare and secure the Blessings of Liberty to ourselves and our Posterity do ordain and establish this Constitution for the United States of America";
        String shortstr = "hello world";
        Picture robot = new Picture("robot.jpg");
        Picture hidden = hideText(robot, preamble);

        String uncoded = revlealText(hidden);
        System.out.println(uncoded);
    }
}
