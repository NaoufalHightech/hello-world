package de.redsix.pdfcompare;


public class HighlightingText {

    private int markAreaX = 0;
    private int markAreaY = 0;
    private int markAreaWidth = 0;
    private int markAreaHeight = 0;
    private int resultImageWdth = 0;
    private int blockChange = 0;
    
	public int getMarkAreaX() {
		return markAreaX;
	}
	public void setMarkAreaX(int markAreaX) {
		this.markAreaX = markAreaX;
	}
	public int getMarkAreaY() {
		return markAreaY;
	}
	public void setMarkAreaY(int markAreaY) {
		this.markAreaY = markAreaY;
	}
	public int getMarkAreaWidth() {
		return markAreaWidth;
	}
	public void setMarkAreaWidth(int markAreaWidth) {
		this.markAreaWidth = markAreaWidth;
	}
	public int getMarkAreaHeight() {
		return markAreaHeight;
	}
	public void setMarkAreaHeight(int markAreaHeight) {
		this.markAreaHeight = markAreaHeight;
	}
	public int getResultImageWdth() {
		return resultImageWdth;
	}
	public void setResultImageWdth(int resultImageWdth) {
		this.resultImageWdth = resultImageWdth;
	}
	public int getBlockChange() {
		return blockChange;
	}
	public void setBlockChange(int blockChange) {
		this.blockChange = blockChange;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + markAreaHeight;
		result = prime * result + markAreaWidth;
		result = prime * result + markAreaX;
		result = prime * result + markAreaY;
		result = prime * result + resultImageWdth;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HighlightingText other = (HighlightingText) obj;
		if (markAreaHeight != other.markAreaHeight)
			return false;
		if (markAreaWidth != other.markAreaWidth)
			return false;
		if (markAreaX != other.markAreaX)
			return false;
		if (markAreaY != other.markAreaY)
			return false;
		if (resultImageWdth != other.resultImageWdth)
			return false;
		return true;
	}
    
	/*public boolean equals(Object obj) {
		
		
		
		return true;
	}
	*/
    
	
}
