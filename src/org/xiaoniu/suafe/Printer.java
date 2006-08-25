/*
 * Created on Jul 14, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xiaoniu.suafe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * @author spjohnso
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Printer implements Printable {
	private static PageFormat format  = new PageFormat();
	
	public static PageFormat getFormat() {
		return Printer.format;
	}
	
	public static void setFormat(PageFormat format) {
		Printer.format = format;
	}
	
	public int print(Graphics g, PageFormat format, int pageIndex) {
		int retval = Printable.PAGE_EXISTS;
		
		try {
			AttributedString mStyledText = new AttributedString(FileGenerator.generate());
			
			/* We'll assume that Jav2D is available.
			 */
			Graphics2D g2d = (Graphics2D) g;
			
			/* Move the origin from the corner of the Paper to the corner
			 * of the imageable area.
			 */
			g2d.translate(format.getImageableX(), format.getImageableY());
			
			/* Set the text color.
			 */
			g2d.setPaint(Color.black);
			
			/* Use a LineBreakMeasurer instance to break our text into
			 * lines that fit the imageable area of the page.
			 */
			Point2D.Float pen = new Point2D.Float();
			AttributedCharacterIterator charIterator = mStyledText.getIterator();
			LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator, g2d.getFontRenderContext());
			float wrappingWidth = (float) format.getImageableWidth();
			
			while (measurer.getPosition() < charIterator.getEndIndex()) {
				TextLayout layout = measurer.nextLayout(wrappingWidth);
				pen.y += layout.getAscent();
				float dx = layout.isLeftToRight()? 0 : (wrappingWidth - layout.getAdvance());
				
				layout.draw(g2d, pen.x + dx, pen.y);
				pen.y += layout.getDescent() + layout.getLeading();
				
			}
		}
		catch (Exception e) {
			retval = Printable.NO_SUCH_PAGE;
		}
		return retval;	
	}

}
