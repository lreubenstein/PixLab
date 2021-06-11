import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /** Method to set the blue to 0 */
  public void zeroBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setBlue(0);
      }
    }
  }
  /** Method to set the red and green to 0 */
  public void keepOnlyBlue()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }
 
  /** Creates a negative of the picture.  Takes the color of each pixel and sets it to 
   * 255 - that color */
  public void negate()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {    	
    	 pixelObj.setRed(255-pixelObj.getRed());    	
    	 pixelObj.setGreen(255-pixelObj.getGreen());
    	 pixelObj.setBlue(255-pixelObj.getBlue());
      }
    }
  }
  
  /** Takes the average of red,green and blue and sets all 3 to the same value to 
   * create a gray image */
  public void grayscale()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {   
//    	  int red = pixelObj.getRed();
//    	  int green = pixelObj.getGreen();
//    	  int blue = pixelObj.getBlue();
//    	  int gray = (red + green + blue)/3;
    	  int gray = (int)pixelObj.getAverage();
    	 pixelObj.setRed(gray);    	
    	 pixelObj.setGreen(gray);
    	 pixelObj.setBlue(gray);
      }
    }
  }
  
  
  public void fixUnderwater()
  {
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels)
    {
      for (Pixel pixelObj : rowArray)
      {    	
    	 pixelObj.setRed(pixelObj.getRed()*3);    	
    	 
      }
    }
  }
  
  /** Method that mirrors the picture around a 
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; col < width / 2; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    } 
  }
  
  /** Method that mirrors the picture around a 
   * vertical mirror in the center of the picture
   * from right to left */
 public void mirrorVerticalRightToLeft()
 {
   Pixel[][] pixels = this.getPixels2D();
   Pixel leftPixel = null;
   Pixel rightPixel = null;
   int width = pixels[0].length;
   for (int row = 0; row < pixels.length; row++)
   {
     for (int col = 0; col < width / 2; col++)
     {
       leftPixel = pixels[row][col];
       rightPixel = pixels[row][width - 1 - col];
       leftPixel.setColor(rightPixel.getColor());
     }
   } 
 }
 
 /** Method that mirrors the picture around a 
  * vertical mirror in the center of the picture
  * from left to right */
public void mirrorHorizontal()
{
  Pixel[][] pixels = this.getPixels2D();
  Pixel topPixel = null;
  Pixel bottomPixel = null;
  int height = pixels.length;
  for (int row = 0; row < pixels.length/2; row++)
  {
    for (int col = 0; col < pixels[0].length; col++)
    {
      topPixel = pixels[row][col];
      bottomPixel = pixels[height - 1 - row][col];
      bottomPixel.setColor(topPixel.getColor());
    }
  } 
}

public void mirrorHorizontalBottomToTop()
{
  Pixel[][] pixels = this.getPixels2D();
  Pixel topPixel = null;
  Pixel bottomPixel = null;
  int height = pixels.length;
  for (int row = 0; row < pixels.length/2; row++)
  {
    for (int col = 0; col < pixels[0].length; col++)
    {
      topPixel = pixels[row][col];
      bottomPixel = pixels[height - 1 - row][col];
      topPixel.setColor(bottomPixel.getColor());
    }
  } 
}

/** Method that mirrors the picture around a 
 * vertical mirror in the center of the picture
 * from left to right */
public void mirrorDiagonal()
{
 Pixel[][] pixels = this.getPixels2D();
 Pixel blPixel = null;
 Pixel trPixel = null;
 int width = pixels[0].length;
 int height = pixels.length;
 int size = Math.min(width,height);
 for (int row = 0; row < size - 1; row++)
 {
   for (int col = 0; col < row - 1; col++)
   {
     //blPixel = pixels[height - 1 - row][col];
     //trPixel = pixels[row][width - 1 - col];
     //trPixel.setColor(blPixel.getColor());
     
     blPixel = pixels[row][col];
     trPixel = pixels[col][row];
     trPixel.setColor(blPixel.getColor());
   }
 } 
}

  /** Mirror just part of a picture of a temple */
  public void mirrorTemple()
  {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 27; row < 97; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++)
      {
        count++;
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
    System.out.println(count);
  }
  
  /** Mirror just part of a picture of a temple */
  public void mirrorArms()
  {
    int mirrorPoint = 190;  //row
    Pixel topPixel = null;
    Pixel bottomPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 155; row < mirrorPoint; row++)
    {
      // left arm
      for (int col = 100; col < 170; col++)
      {
        count++;
        topPixel = pixels[row][col];      
        bottomPixel = pixels[mirrorPoint - row + mirrorPoint][col];                       
        bottomPixel.setColor(topPixel.getColor());
      }
   // right arm
      for (int col = 237; col < 300; col++)
      {
        count++;
        topPixel = pixels[row][col];      
        bottomPixel = pixels[mirrorPoint - row + mirrorPoint][col];                       
        bottomPixel.setColor(topPixel.getColor());
      }
    }
  }
 
  /** Mirror just part of a picture of a temple */
  public void mirrorGull()
  {
    int mirrorPoint = 350;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    
    Pixel[][] pixels = this.getPixels2D();
    
    // loop through the rows
    for (int row = 225; row < 325; row++)
    {
      // loop from 13 to just before the mirror point
      for (int col = 230; col < mirrorPoint; col++)
      {
    
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }
  
  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol)
  {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++)
    {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++)
      {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  public void copy(Picture fromPic, int startRow, int startCol,
		  int newStartRow, int newEndRow, int newStartCol, int newEndCol)
{
	Pixel fromPixel = null;
	Pixel toPixel = null;
	Pixel[][] toPixels = this.getPixels2D();
	Pixel[][] fromPixels = fromPic.getPixels2D();
	for (int fromRow = newStartRow, toRow = startRow; 
	  fromRow < newEndRow &&
	  toRow < toPixels.length; 
	  fromRow++, toRow++)
	{
		for (int fromCol = newStartCol, toCol = startCol; 
		    fromCol < newEndCol &&
		    toCol < toPixels[0].length;  
		    fromCol++, toCol++)
		{
		 fromPixel = fromPixels[fromRow][fromCol];
		 toPixel = toPixels[toRow][toCol];
		 toPixel.setColor(fromPixel.getColor());
		}
	}   
}
  
  
  /** Method to create a collage of several pictures */
  public void createCollage()
  {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }
  
  /** Method to create a collage of several pictures */
  public void myCollage()
  {
	Picture lion = new Picture("femaleLionAndHall.jpg");
	this.copy(lion, 0,0,215, 430, 280, 525);
    this.copy(lion, 215, 0, 215, 430, 280, 525);
    //Picture flowerNoBlue = new Picture(flower2);
    //lion.zeroBlue();
    //this.copy(flowerNoBlue,300,0);
    //this.copy(flower1,400,0);
    //this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("mycollage.jpg");
  }
  
  
  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist)
  {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++)
    {
      for (int col = 0; 
           col < pixels[0].length-1; col++)
      {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }
  
  /** Method to show large changes in color 
   * @param edgeDist the distance for finding edges
   */
 public void edgeDetection2(int edgeDist)
 {
   Pixel leftPixel = null;
   Pixel rightPixel = null;
   Pixel bottomPixel = null;
   Pixel[][] pixels = this.getPixels2D();
   Color rightColor = null;
   Color bottomColor = null;
   for (int row = 0; row < pixels.length-1; row++)
   {
     for (int col = 0; 
          col < pixels[0].length-1; col++)
     {
       leftPixel = pixels[row][col];
       rightPixel = pixels[row][col+1];
       bottomPixel = pixels[row+1][col];
       rightColor = rightPixel.getColor();
       bottomColor = bottomPixel.getColor();
       if (leftPixel.colorDistance(rightColor) > edgeDist ||
    		   leftPixel.colorDistance(bottomColor) > edgeDist)
         leftPixel.setColor(Color.BLACK);
       else
         leftPixel.setColor(Color.WHITE);
     }
   }
 }
 
 /** method to "green screen" a picture.
  * @param bg - the picture being used for the background
  * @param col - the color used for the screen
  */
 public void chromakey(Picture bg, Color col)
 {
	 Pixel bgPixel = null;
	 Pixel fgPixel = null;
	 Pixel[][] fgPixels = this.getPixels2D();
	 Pixel[][] bgPixels = bg.getPixels2D();
	 
	 for(int r = 0; r < fgPixels.length; r++)
	 {
		 for(int c = 0; c < fgPixels[0].length; c++ )
		 {
			 fgPixel = fgPixels[r][c];
			 bgPixel = bgPixels[r][c];
			 //Color fgColor = fgPixel.getColor();
			 //if (fgPixel.colorDistance(col) < 20)
			 if(fgPixel.getBlue() > fgPixel.getRed())
			 {
				 Color bgColor = bgPixel.getColor();
				 fgPixel.setColor(bgColor);
			 }
		 }
	 }
	 
 }
  
  /* Main method for testing - each class in Java can have a main 
   * method 
   */
  public static void main(String[] args) 
  {
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
    //Picture p = new SimplePicture();
  }
  
} // this } is the end of class Picture, put all new methods before this
