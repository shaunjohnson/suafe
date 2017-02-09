/*
 * Copyright (c) 2006-2017 by LMXM LLC <suafe@lmxm.net>
 *
 * This file is part of Suafe.
 *
 * Suafe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Suafe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Suafe.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.xiaoniu.suafe;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * Handles authz printing task.
 *
 * @author Shaun Johnson
 */
public final class Printer implements Printable {
    private static PageFormat format = new PageFormat();

    /**
     * Gets the current <code>PageFormat<code> object.
     *
     * @return <code>PageFormat</code> object
     */
    @Nonnull
    public static PageFormat getFormat() {
        return Printer.format;
    }

    /**
     * Sets the current <code>PageFormat</code> object.
     *
     * @param format <code>PageFormat</code> object
     */
    public static void setFormat(@Nonnull final PageFormat format) {
        Printer.format = format;
    }

    /**
     * Prints the authz document.
     *
     * @param g         Graphics object
     * @param format    PageFormat object
     * @param pageIndex
     */
    public int print(@Nonnull final Graphics g, @Nonnull final PageFormat format, final int pageIndex) {
        int retval = Printable.PAGE_EXISTS;

        try {
//			final AttributedString mStyledText = new AttributedString(FileGenerator.generate());
            final AttributedString mStyledText = new AttributedString("not implemented");

			/* We'll assume that Jav2D is available.
			 */
            final Graphics2D g2d = (Graphics2D) g;
			
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
            final Point2D.Float pen = new Point2D.Float();
            final AttributedCharacterIterator charIterator = mStyledText.getIterator();
            final LineBreakMeasurer measurer = new LineBreakMeasurer(charIterator, g2d.getFontRenderContext());
            final float wrappingWidth = (float) format.getImageableWidth();

            while (measurer.getPosition() < charIterator.getEndIndex()) {
                final TextLayout layout = measurer.nextLayout(wrappingWidth);
                pen.y += layout.getAscent();
                float dx = layout.isLeftToRight() ? 0 : (wrappingWidth - layout.getAdvance());

                layout.draw(g2d, pen.x + dx, pen.y);
                pen.y += layout.getDescent() + layout.getLeading();

            }
        }
        catch (final Exception e) {
            retval = Printable.NO_SUCH_PAGE;
        }

        return retval;
    }
}
