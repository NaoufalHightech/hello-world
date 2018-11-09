package de.redsix.pdfcompare;

import static de.redsix.pdfcompare.PdfComparator.MARKER_WIDTH;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiffImage {

    private static final Logger LOG = LoggerFactory.getLogger(DiffImage.class);
    /*package*/ static final int MARKER_RGB = color(51, 153, 255);
    private final ImageWithDimension expectedImage;
    private final ImageWithDimension actualImage;
    private final int page;
    private final Exclusions exclusions;
    private DataBuffer expectedBuffer;
    private DataBuffer actualBuffer;
    private int expectedImageWidth;
    private int expectedImageHeight;
    private int actualImageWidth;
    private int actualImageHeight;
    private int resultImageWidth;
    private int resultImageHeight;
    private BufferedImage resultImage;
    private BufferedImage resultExpectBuffImage;
    private BufferedImage resultActualBuffImage;
    private int diffAreaX1, diffAreaY1, diffAreaX2, diffAreaY2;
    private final ResultCollector compareResult;
    private PageDiffCalculator diffCalculator;
    Graphics2D exeGraphics;
    Graphics2D actGraphics;
    int normalElement;
    int actualElement;
    int expectedElement;
    private Map<Integer, Set<HighlightingText>> mapOfMarkObj = new HashMap<>();
     Set<HighlightingText> nonDuplicateMapOfMarkObj = new LinkedHashSet<>();
    private Set<HighlightingText> listOfMarkObj = new LinkedHashSet<>();
    private HighlightingText highObj = new HighlightingText();
    int blockCount = 0;
    
    int lexeX = 0;
    int lexeY = 0;
    int lmarkWid = 0;
    int lmarkHei = 0;
    int lexeWidth = 0;
    int lexeHeight = 0;
    
    

	int currentXValue = 0;
	int previousXValue = 0;
	int diffXValue = 0;
	int currentYValue = 0;
	int previousYValue = 0;
	int diffYValue = 0;
    

    public DiffImage(final ImageWithDimension expectedImage, final ImageWithDimension actualImage, final int page,
            final Exclusions exclusions, final ResultCollector compareResult) {
        this.expectedImage = expectedImage;
        this.actualImage = actualImage;
        this.page = page;
        this.exclusions = exclusions;
        this.compareResult = compareResult;
    }

    public BufferedImage getImage() {
        return resultImage;
    }
    
    public BufferedImage getExpectedImage() {
        return resultExpectBuffImage;
    }
    
    public BufferedImage getActualImage() {
        return resultActualBuffImage;
    }

    public void diffImages() {

    	
        BufferedImage expectBuffImage = this.expectedImage.bufferedImage;
        BufferedImage actualBuffImage = this.actualImage.bufferedImage;
        expectedBuffer = expectBuffImage.getRaster().getDataBuffer();
        actualBuffer = actualBuffImage.getRaster().getDataBuffer();

        expectedImageWidth = expectBuffImage.getWidth();
        expectedImageHeight = expectBuffImage.getHeight();
        actualImageWidth = actualBuffImage.getWidth();
        actualImageHeight = actualBuffImage.getHeight();

        resultImageWidth = Math.max(expectedImageWidth, actualImageWidth);
        resultImageHeight = Math.max(expectedImageHeight, actualImageHeight);
        resultImage = new BufferedImage(resultImageWidth, resultImageHeight, actualBuffImage.getType());
        resultExpectBuffImage = new BufferedImage(resultImageWidth, resultImageHeight, actualBuffImage.getType());
        resultActualBuffImage = new BufferedImage(resultImageWidth, resultImageHeight, actualBuffImage.getType());
        
        DataBuffer resultBuffer = resultImage.getRaster().getDataBuffer();
        DataBuffer actualBuffer = resultActualBuffImage.getRaster().getDataBuffer();
        DataBuffer expectBuffer = resultExpectBuffImage.getRaster().getDataBuffer();
        
        diffCalculator = new PageDiffCalculator(resultImageWidth * resultImageHeight, Environment.getAllowedDiffInPercent());

        int localExpectedElement;
        int localActualElement;
        final PageExclusions pageExclusions = exclusions.forPage(page);

        exeGraphics = expectedImage.bufferedImage.createGraphics();
        exeGraphics.setColor(new Color(255, 255, 0, 127));

        actGraphics = actualImage.bufferedImage.createGraphics();
        actGraphics.setColor(new Color(255, 255, 0, 127));


        for (int y = 0; y < resultImageHeight; y++) {
            final int expectedLineOffset = y * expectedImageWidth;
            final int actualLineOffset = y * actualImageWidth;
            final int resultLineOffset = y * resultImageWidth;
   

            for (int x = 0; x < resultImageWidth; x++) {


            	localExpectedElement = getExpectedElement(x, y, expectedLineOffset);
            	localActualElement = getActualElement(x, y, actualLineOffset);
                int element = getElement(localExpectedElement, localActualElement);
                // Page Exclusions contains x and y, dont perform any thing
                if (pageExclusions.contains(x, y)) {
                    element = ImageTools.fadeExclusion(element);
                    if (localExpectedElement != localActualElement) {
                        diffCalculator.diffFoundInExclusion();
                    }
                } else {
                    if (localExpectedElement != localActualElement) {
                  		listOfMarkObj = extendDiffArea(x, y, new HighlightingText());	
                        diffCalculator.diffFound();
                    }
                }
                resultBuffer.setElem(x + resultLineOffset, element);
                actualBuffer.setElem(x + actualLineOffset, this.getActualElement());
                expectBuffer.setElem(x + expectedLineOffset, this.getExpectedElement());
                //resultBuffer.setElem(x, element);
            }
        }
        
        
        if (diffCalculator.differencesFound()) {
        	LOG.info("Differences found at { page: {}, x1: {}, y1: {}, x2: {}, y2: {} }", page, diffAreaX1, diffAreaY1, diffAreaX2, diffAreaY2);
        }
        final float maxWidth = Math.max(expectedImage.width, actualImage.width);
        final float maxHeight = Math.max(expectedImage.height, actualImage.height);


        mapOfMarkObj.forEach((key,value)->{
        	
        	nonDuplicateMapOfMarkObj.addAll(value);
         
  
        });

     
        
        for(HighlightingText hobj : nonDuplicateMapOfMarkObj){

        	lexeX = hobj.getMarkAreaX();
            lexeY = hobj.getMarkAreaY();
            lmarkWid = hobj.getMarkAreaWidth();
            lmarkHei = hobj.getMarkAreaHeight();
            lexeWidth = lmarkWid - lexeX;
            lexeHeight = lmarkHei - lexeY;

        	System.out.println(hobj.getBlockChange()+" "+hobj.getMarkAreaX() +" "+ hobj.getMarkAreaY() +" "+ hobj.getMarkAreaWidth() +" "+ hobj.getMarkAreaHeight());
            
        	 actGraphics.fill3DRect(lexeX, lexeY, lexeWidth, lexeHeight, true);
             exeGraphics.fill3DRect(lexeX, lexeY, lexeWidth, lexeHeight, true);
                            
        }

        
      
        
        //compareResult.addPage(diffCalculator, page, new ImageWithDimension(resultExpectBuffImage, maxWidth, maxHeight), new ImageWithDimension(resultActualBuffImage, maxWidth, maxHeight));
        //compareResult.addPage(diffCalculator, page, new ImageWithDimension(resultExpectBuffImage, maxWidth, maxHeight), new ImageWithDimension(resultActualBuffImage, maxWidth, maxHeight), new ImageWithDimension(resultImage, maxWidth, maxHeight));
        compareResult.addPage(diffCalculator, page, expectedImage, actualImage, new ImageWithDimension(resultImage, maxWidth, maxHeight));
        
        
    }

    private Set<HighlightingText> extendDiffArea(final int x, final int y, HighlightingText highObj) {

    	currentXValue = x;
    	currentYValue = y;
    	
    	    	
        if(diffAreaX1 == 0 || x < diffAreaX1) {
            diffAreaX1 = x;
        }
        diffAreaX2 = Math.max(diffAreaX2, x);
        
        if(diffAreaY1 == 0 || y < diffAreaY1) {
            diffAreaY1 = y;
        }
        diffAreaY2 = Math.max(diffAreaY2, y);
        
	        if(previousXValue == 0) {
	        	previousXValue = currentXValue;	
	        }
	        diffXValue = currentXValue - previousXValue;
	        previousYValue = currentXValue;
	        
	        if(previousYValue == 0) {
	        	previousYValue = currentYValue;	
	        }
	        diffYValue = currentYValue - previousYValue;
	        previousYValue = currentYValue;
        
        
       if(diffYValue>10) {
    	   
    	   highObj.setMarkAreaX(diffAreaX1);
           highObj.setMarkAreaWidth(diffAreaX2);
           highObj.setMarkAreaY(diffAreaY1);
           highObj.setMarkAreaHeight(diffAreaY2);
           
    	    highObj.setBlockChange(blockCount);
        	listOfMarkObj.add(highObj);
	        mapOfMarkObj.put(blockCount, listOfMarkObj);
	        blockCount++;
	        //diffAreaX1 = 0;
	        //diffAreaY1 = diffAreaY2;
	   }
        
		
        return listOfMarkObj;
    }

    /*private void extendDiffArea(final int x, final int y) {
    	*//*System.out.print(x +", ");
    	System.out.print(y +", ");*//*
        if (!diffCalculator.differencesFound()) {
            diffAreaX1 = x;
            diffAreaY1 = y;
        }
        diffAreaX1 = Math.min(diffAreaX1, x);
        diffAreaX2 = Math.max(diffAreaX2, x);
        diffAreaY1 = Math.min(diffAreaY1, y);
        diffAreaY2 = Math.max(diffAreaY2, y);
    }*/

    private int getElement(final int expectedElement, final int actualElement) {
        if (expectedElement != actualElement) {
            int expectedDarkness = calcCombinedIntensity(expectedElement);
            int actualDarkness = calcCombinedIntensity(actualElement);
            if (expectedDarkness > actualDarkness) {
                this.setExpectedElement(color(0, 0, levelIntensity(expectedDarkness, 204)));
                this.setExpectedElement(color(0, 0, levelIntensity(expectedDarkness, 204)));
                return color(0, 0, levelIntensity(expectedDarkness, 204));
            } else {
                this.setActualElement(color(0, levelIntensity(actualDarkness, 180), 0));
                return color(0, levelIntensity(actualDarkness, 180), 0);
            }
        } 
         else {
        	this.setExpectedElement(expectedElement);
            this.setActualElement(actualElement);
            //this.setNormalElement(ImageTools.normalElement(expectedElement));
            return ImageTools.normalElement(expectedElement);
        }
    }

    private int getExpectedElement(final int x, final int y, final int expectedLineOffset) {
        if (x < expectedImageWidth && y < expectedImageHeight) {
            return expectedBuffer.getElem(x + expectedLineOffset);
        }
        return 0;
    }

    private int getActualElement(final int x, final int y, final int actualLineOffset) {
        if (x < actualImageWidth && y < actualImageHeight) {
            return actualBuffer.getElem(x + actualLineOffset);
        }
        return 0;
    }

    /**
     * Levels the color intensity to at least 50 and at most maxIntensity.
     *
     * @param darkness     color component to level
     * @param maxIntensity highest possible intensity cut off
     * @return A value that is at least 50 and at most maxIntensity
     */
    private static int levelIntensity(final int darkness, final int maxIntensity) {
        return Math.min(maxIntensity, Math.max(50, darkness));
    }

    /**
     * Calculate the combined intensity of a pixel and normalizes it to a value of at most 255.
     *
     * @param element
     * @return
     */
    private static int calcCombinedIntensity(final int element) {
        final Color color = new Color(element);
        return Math.min(255, (color.getRed() + color.getGreen() + color.getRed()) / 3);
    }





// Goal is mark the exact text, not the top and left

    //mark(resultBuffer, x+10, y+10, resultImageWidth, MARKER_RGB); --> Tried adding 10 to both x & y. But the mark on Y-axis is gone.

    //mark(actualBuffer, x+100, y, resultImageWidth, MARKER_RGB); --> Tried adding 100 to x. Mark moved 100 pixels on X-axis.

    //mark(expectBuffer, y, x, resultImageWidth, MARKER_RGB);  --> Tried exchanging x and y parameters, no use.

    //private static void mark(final DataBuffer image, final int x, final int y, final int imageWidth, final int imageHeight, final int markerRGB) {
    //|--> Tried above no use, see the output in diffOutputFile_12.PDF


    private static void mark(final DataBuffer image, final int x, final int y, final int imageWidth, final int markerRGB) {

        final int yOffset = y * imageWidth;
        LOG.info(" Values in Mark x: "+x+" y: "+y+" imageWidth: "+imageWidth);


        // started the value of i with y --> But is resulted in both x-Axis & y-Axis loss.
        // started the value of i with yOffset --> But is resulted in both x-Axis & y-Axis loss.
        // for (int i = y; i < yOffset+MARKER_WIDTH; i++) {  --> Loop is running for long time
        for (int i = x; i < (x + MARKER_WIDTH); i++) {
            //int xValue = x + i * imageWidth;
            //image.setElem(xValue, markerRGB);
            //image.setElem(y + i * imageWidth, markerRGB); --> Tried adding y, same as x. But the mark on Y-axis is gone.
            //image.setElem(i, markerRGB); //--> Tried removed yOffset. But the mark on Y-axis is gone.
            //int yValue = x + i * imageWidth + yOffset; // --> This removed both X and Y -Axis.
            //int yValue = x + i + yOffset; -->  --> This removed both X and Y -Axis.
            for (int yValue = i; yValue < yOffset; yValue++) {

                //image.setElem(xValue+yOffset, markerRGB); --> This result in Y-axis loss
                //image.setElem(x+yValue, markerRGB); --> This result in both Axis loss
                //image.setElem((x*y)+i, markerRGB); --> output in sampleDiffFile_8.pdf
                //image.setElem((x*y)+i, markerRGB); --> output in sampleDiffFile_8.pdf
                //image.setElem((xValue+yValue)/2, markerRGB); --> output is not expected -- se in sampleDiffFile_9
                image.setElem(yValue, markerRGB);
                LOG.info(" Values in loop x= "+i+" (yValue)= "+yValue);
            }




            //image.setElem(y + i * imageHeight, markerRGB);
        }

        // Below for loop is taking for ever.
        /*for (int i=minX; i<maxX; i++){
            image.setElem(i, markerRGB);
            for(int j=minY; i<maxY; j++){
                image.setElem(y, markerRGB);
            }
        }*/
    }



    /*private static void mark(final DataBuffer image, final int x, final int y, final int imageWidth, final int markerRGB) {
        final int yOffset = y * imageWidth;
        for (int i = 0; i < MARKER_WIDTH; i++) {
        	//image.setElem(x + i * imageWidth, markerRGB);
        	image.setElem(x + i * imageWidth, markerRGB);
            image.setElem(i + yOffset, markerRGB);
        }
    }*/

    public static int color(final int r, final int g, final int b) {
    	LOG.trace("rgb :"+r+ ","+g+","+b);
        return new Color(r, g, b).getRGB();
    }

    @Override
    public String toString() {
        return "DiffImage{" +
                "page=" + page +
                '}';
    }

	/*public int getNormalElement() {
		return normalElement;
	}

	public void setNormalElement(int normalElement) {
		this.normalElement = normalElement;
	}*/

	public int getActualElement() {
		return actualElement;
	}

	public void setActualElement(int actualElement) {
		this.actualElement = actualElement;
	}

	public int getExpectedElement() {
		return expectedElement;
	}

	public void setExpectedElement(int expectedElement) {
		this.expectedElement = expectedElement;
	}
    
    
    
}
