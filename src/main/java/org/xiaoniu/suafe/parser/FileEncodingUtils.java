package org.xiaoniu.suafe.parser;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * File encoding detection utility.
 */
public class FileEncodingUtils {
    private static final Log LOGGER = LogFactory.getLog(FileEncodingUtils.class);

    private static final int MINIMAL_CONFIDENCE_LEVEL = 50;

    /**
     * Try to detect the encoding of the provided file. If the encoding cannot be determined or if an error occurs
     * the provided, default encoding will be returned.
     *
     * @param file            File to test
     * @param defaultEncoding Encoding to return if file encoding cannot be determined
     * @return File encoding
     */
    public static String detect(final File file, final String defaultEncoding) {
        BufferedInputStream inputStream = null;

        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));

            final CharsetDetector charsetDetector = new CharsetDetector();
            charsetDetector.setText(inputStream);

            final CharsetMatch charsetMatch = charsetDetector.detect();
            if (charsetMatch == null) {
                return defaultEncoding;
            }

            final String estimatedEncoding = charsetMatch.getName();

            final boolean isReliable = Charset.isSupported(estimatedEncoding) &&
                    charsetMatch.getConfidence() >= MINIMAL_CONFIDENCE_LEVEL;

            return isReliable ? estimatedEncoding : defaultEncoding;
        }
        catch (final FileNotFoundException e) {
            LOGGER.error("Unable to find file to test", e);
            return defaultEncoding;
        }
        catch (final IOException e) {
            LOGGER.error("Error occurred reading file", e);
            return defaultEncoding;
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (final IOException ioe) {
                    // Do nothing
                }
            }
        }
    }
}
