
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class FileUtils {

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a megabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * The number of bytes in a gigabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = ONE_KB * ONE_GB;

    /**
     * The number of bytes in a terabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = ONE_KB * ONE_TB;

    /**
     * The number of bytes in a petabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = ONE_KB * ONE_PB;

    /**
     * The number of bytes in an exabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);

    /**
     * The number of bytes in a zettabyte.
     */
    public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));

    /**
     * The number of bytes in a yottabyte.
     */
    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);

    /**
     * An empty array of type <code>File</code>.
     */
    public static final File[] EMPTY_FILE_ARRAY = new File[0];

    public static File getFile(final File directory, final String... names) {
        if (directory == null) {
            throw new NullPointerException("directory must not be null");
        }
        if (names == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = directory;
        for (final String name : names) {
            file = new File(file, name);
        }
        return file;
    }

    /**
     * Construct a file from the set of name elements.
     *
     * @param names the name elements
     * @return the file
     * @since 2.1
     */
    public static File getFile(final String... names) {
        if (names == null) {
            throw new NullPointerException("names must not be null");
        }
        File file = null;
        for (final String name : names) {
            if (file == null) {
                file = new File(name);
            } else {
                file = new File(file, name);
            }
        }
        return file;
    }

    /**
     * Returns the path to the system temporary directory.
     *
     * @return the path to the system temporary directory.
     *
     * @since 2.0
     */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Returns a {@link File} representing the system temporary directory.
     *
     * @return the system temporary directory.
     *
     * @since 2.0
     */
    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }

    /**
     * Returns the path to the user's home directory.
     *
     * @return the path to the user's home directory.
     *
     * @since 2.0
     */
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    /**
     * Returns a {@link File} representing the user's home directory.
     *
     * @return the user's home directory.
     *
     * @since 2.0
     */
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }

    //-----------------------------------------------------------------------
    /**
     * Opens a {@link FileInputStream} for the specified file, providing better
     * error messages than simply calling
     * <code>new FileInputStream(file)</code>.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * An exception is thrown if the file does not exist. An exception is thrown
     * if the file object exists but is a directory. An exception is thrown if
     * the file exists but cannot be read.
     *
     * @param file the file to open for input, must not be {@code null}
     * @return a new {@link FileInputStream} for the specified file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if the file object is a directory
     * @since 1.3
     */
    public static FileInputStream openInputStream(final File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    //-----------------------------------------------------------------------
    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist. The file will
     * be created if it does not exist. An exception is thrown if the file
     * object exists but is a directory. An exception is thrown if the file
     * exists but cannot be written to. An exception is thrown if the parent
     * directory cannot be created.
     *
     * @param file the file to open for output, must not be {@code null}
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @since 1.3
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * <p>
     * The parent directory will be created if it does not exist. The file will
     * be created if it does not exist. An exception is thrown if the file
     * object exists but is a directory. An exception is thrown if the file
     * exists but cannot be written to. An exception is thrown if the parent
     * directory cannot be created.
     *
     * @param file the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the end of the
     * file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws IOException if the file object is a directory
     * @since 2.1
     */
    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canWrite() == false) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            final File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a human-readable version of the file size, where the input
     * represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of whole GB,
     * i.e. the size is rounded down to the nearest GB boundary.
     * </p>
     * <p>
     * Similarly for the 1MB and 1KB boundaries.
     * </p>
     *
     * @param size the number of bytes
     * @return a human-readable display value (includes units - EB, PB, TB, GB,
     * MB, KB or bytes)
     * @see <a href="https://issues.apache.org/jira/browse/IO-226">IO-226 -
     * should the rounding be changed?</a>
     * @since 2.4
     */
    // See https://issues.apache.org/jira/browse/IO-226 - should the rounding be changed?
    public static String byteCountToDisplaySize(final BigInteger size) {
        String displaySize;

        if (size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_EB_BI)) + " EB";
        } else if (size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_PB_BI)) + " PB";
        } else if (size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_TB_BI)) + " TB";
        } else if (size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_GB_BI)) + " GB";
        } else if (size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_MB_BI)) + " MB";
        } else if (size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
            displaySize = String.valueOf(size.divide(ONE_KB_BI)) + " KB";
        } else {
            displaySize = String.valueOf(size) + " bytes";
        }
        return displaySize;
    }

    /**
     * Returns a human-readable version of the file size, where the input
     * represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of whole GB,
     * i.e. the size is rounded down to the nearest GB boundary.
     * </p>
     * <p>
     * Similarly for the 1MB and 1KB boundaries.
     * </p>
     *
     * @param size the number of bytes
     * @return a human-readable display value (includes units - EB, PB, TB, GB,
     * MB, KB or bytes)
     * @see <a href="https://issues.apache.org/jira/browse/IO-226">IO-226 -
     * should the rounding be changed?</a>
     */
    // See https://issues.apache.org/jira/browse/IO-226 - should the rounding be changed?
    public static String byteCountToDisplaySize(final long size) {
        return byteCountToDisplaySize(BigInteger.valueOf(size));
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the same behaviour as the "touch" utility on Unix. It creates
     * a new file with size 0 or, if the file exists already, it is opened and
     * closed without modifying it, but updating the file date and time.
     * <p>
     * NOTE: As from v1.3, this method throws an IOException if the last
     * modified date of the file cannot be set. Also, as from v1.3 this method
     * creates parent directories if they do not exist.
     *
     * @param file the File to touch
     * @throws IOException If an I/O problem occurs
     */
    public static void touch(final File file) throws IOException {
        if (!file.exists()) {
            openOutputStream(file).close();
        }
        final boolean success = file.setLastModified(System.currentTimeMillis());
        if (!success) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Converts a Collection containing java.io.File instanced into array
     * representation. This is to account for the difference between
     * File.listFiles() and FileUtils.listFiles().
     *
     * @param files a Collection containing java.io.File instances
     * @return an array of java.io.File
     */
    public static File[] convertFileCollectionToFileArray(final Collection<File> files) {
        return files.toArray(new File[files.size()]);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts an array of file extensions to suffixes for use with
     * IOFileFilters.
     *
     * @param extensions an array of extensions. Format: {"java", "xml"}
     * @return an array of suffixes. Format: {".java", ".xml"}
     */
    private static String[] toSuffixes(final String[] extensions) {
        final String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            suffixes[i] = "." + extensions[i];
        }
        return suffixes;
    }

    //-----------------------------------------------------------------------
    /**
     * Convert from a <code>URL</code> to a <code>File</code>.
     * <p>
     * From version 1.1 this method will decode the URL. Syntax such as
     * <code>file:///my%20docs/file.txt</code> will be correctly decoded to
     * <code>/my docs/file.txt</code>. Starting with version 1.5, this method
     * uses UTF-8 to decode percent-encoded octets to characters. Additionally,
     * malformed percent-encoded octets are handled leniently by passing them
     * through literally.
     *
     * @param url the file URL to convert, {@code null} returns {@code null}
     * @return the equivalent <code>File</code> object, or {@code null} if the
     * URL's protocol is not <code>file</code>
     */
    public static File toFile(final URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        } else {
            String filename = url.getFile().replace('/', File.separatorChar);
            filename = decodeUrl(filename);
            return new File(filename);
        }
    }

    /**
     * Decodes the specified URL as per RFC 3986, i.e. transforms
     * percent-encoded octets to characters by decoding with the UTF-8 character
     * set. This function is primarily intended for usage with
     * {@link java.net.URL} which unfortunately does not enforce proper URLs. As
     * such, this method will leniently accept invalid characters or malformed
     * percent-encoded octets and simply pass them literally through to the
     * result string. Except for rare edge cases, this will make unencoded URLs
     * pass through unaltered.
     *
     * @param url The URL to decode, may be {@code null}.
     * @return The decoded URL or {@code null} if the input was {@code null}.
     */
    static String decodeUrl(final String url) {
        String decoded = url;
        if (url != null && url.indexOf('%') >= 0) {
            final int n = url.length();
            final StringBuilder buffer = new StringBuilder();
            final ByteBuffer bytes = ByteBuffer.allocate(n);
            for (int i = 0; i < n;) {
                if (url.charAt(i) == '%') {
                    try {
                        do {
                            final byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
                            bytes.put(octet);
                            i += 3;
                        } while (i < n && url.charAt(i) == '%');
                        continue;
                    } catch (final RuntimeException e) {
                        // malformed percent-encoded octet, fall through and
                        // append characters literally
                    } finally {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(StandardCharsets.UTF_8.decode(bytes).toString());
                            bytes.clear();
                        }
                    }
                }
                buffer.append(url.charAt(i));
                i++;
            }
            decoded = buffer.toString();
        }
        return decoded;
    }

    /**
     * Converts each of an array of <code>URL</code> to a <code>File</code>.
     * <p>
     * Returns an array of the same size as the input. If the input is
     * {@code null}, an empty array is returned. If the input contains
     * {@code null}, the output array contains {@code null} at the same index.
     * <p>
     * This method will decode the URL. Syntax such as
     * <code>file:///my%20docs/file.txt</code> will be correctly decoded to
     * <code>/my docs/file.txt</code>.
     *
     * @param urls the file URLs to convert, {@code null} returns empty array
     * @return a non-{@code null} array of Files matching the input, with a
     * {@code null} item if there was a {@code null} at that index in the input
     * array
     * @throws IllegalArgumentException if any file is not a URL file
     * @throws IllegalArgumentException if any file is incorrectly encoded
     * @since 1.1
     */
    public static File[] toFiles(final URL[] urls) {
        if (urls == null || urls.length == 0) {
            return new File[0];
        }
        final File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; i++) {
            final URL url = urls[i];
            if (url != null) {
                if (url.getProtocol().equals("file") == false) {
                    throw new IllegalArgumentException(
                            "URL could not be converted to a File: " + url);
                }
                files[i] = toFile(url);
            }
        }
        return files;
    }

    /**
     * Converts each of an array of <code>File</code> to a <code>URL</code>.
     * <p>
     * Returns an array of the same size as the input.
     *
     * @param files the files to convert, must not be {@code null}
     * @return an array of URLs matching the input
     * @throws IOException if a file cannot be converted
     * @throws NullPointerException if the parameter is null
     */
    public static URL[] toURLs(final File[] files) throws IOException {
        final URL[] urls = new URL[files.length];

        for (int i = 0; i < urls.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }

        return urls;
    }

    //-----------------------------------------------------------------------
    /**
     * Copies a file to a directory preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to a file of
     * the same name in the specified destination directory. The destination
     * directory is created if it does not exist. If the destination file
     * exists, then this method will overwrite it.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the file's last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that the operation will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param srcFile an existing file to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be
     * {@code null}
     *
     * @throws NullPointerException if source or destination is null
     * @throws IOException if source or destination is invalid
     * @see #copyFile(File, File, boolean)
     */
    public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    /**
     * Copies a file to a directory optionally preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to a file of
     * the same name in the specified destination directory. The destination
     * directory is created if it does not exist. If the destination file
     * exists, then this method will overwrite it.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the file's last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that the
     * operation will succeed. If the modification operation fails, no
     * indication is provided.
     *
     * @param srcFile an existing file to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be
     * {@code null}
     * @param preserveFileDate true if the file date of the copy should be the
     * same as the original
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @see #copyFile(File, File, boolean)
     * @since 1.3
     */
    public static void copyFileToDirectory(final File srcFile, final File destDir, final boolean preserveFileDate)
            throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (destDir.exists() && destDir.isDirectory() == false) {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
        final File destFile = new File(destDir, srcFile.getName());
        copyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * Copies a file to a new location preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to the
     * specified destination file. The directory holding the destination file is
     * created if it does not exist. If the destination file exists, then this
     * method will overwrite it.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the file's last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that the operation will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param srcFile an existing file to copy, must not be {@code null}
     * @param destFile the new file, must not be {@code null}
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @see #copyFileToDirectory(File, File)
     * @see #copyFile(File, File, boolean)
     */
    public static void copyFile(final File srcFile, final File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to a new location.
     * <p>
     * This method copies the contents of the specified source file to the
     * specified destination file. The directory holding the destination file is
     * created if it does not exist. If the destination file exists, then this
     * method will overwrite it.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the file's last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that the
     * operation will succeed. If the modification operation fails, no
     * indication is provided.
     *
     * @param srcFile an existing file to copy, must not be {@code null}
     * @param destFile the new file, must not be {@code null}
     * @param preserveFileDate true if the file date of the copy should be the
     * same as the original
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @see #copyFileToDirectory(File, File, boolean)
     * @see #doCopyFile(File, File, boolean)
     */
    public static void copyFile(final File srcFile, final File destFile,
            final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        final File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }

    /**
     * Internal copy file method. This caches the original file length, and
     * throws an IOException if the output file length is different from the
     * current input file length. So it may fail if the file changes size. It
     * may also fail with "IllegalArgumentException: Negative size" if the input
     * file is truncated part way through copying the data and the new file size
     * is less than the current position.
     *
     * @param srcFile the validated source file, must not be {@code null}
     * @param destFile the validated destination file, must not be {@code null}
     * @param preserveFileDate whether to preserve the file date
     * @throws IOException if an error occurs
     * @throws IOException if the output file length is not the same as the
     * input file length after the copy completes
     * @throws IllegalArgumentException "Negative size" if the file is truncated
     * so that the size is less than the position
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        try (FileInputStream fis = new FileInputStream(srcFile);
                FileChannel input = fis.getChannel();
                FileOutputStream fos = new FileOutputStream(destFile);
                FileChannel output = fos.getChannel()) {
            final long size = input.size(); // TODO See IO-386
            long pos = 0;
            long count;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        }

        final long srcLen = srcFile.length(); // TODO See IO-386
        final long dstLen = destFile.length(); // TODO See IO-386
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '"
                    + srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Copies a directory to within another directory preserving the file dates.
     * <p>
     * This method copies the source directory and all its contents to a
     * directory of the same name in the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the files' last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that those operations will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param srcDir an existing directory to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be
     * {@code null}
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @since 1.2
     */
    public static void copyDirectoryToDirectory(final File srcDir, final File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (srcDir.exists() && srcDir.isDirectory() == false) {
            throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
        }
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (destDir.exists() && destDir.isDirectory() == false) {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
        copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
    }

    public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    /**
     * Copies a whole directory to a new location.
     * <p>
     * This method copies the contents of the specified source directory to
     * within the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the files' last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that
     * those operations will succeed. If the modification operation fails, no
     * indication is provided.
     *
     * @param srcDir an existing directory to copy, must not be {@code null}
     * @param destDir the new directory, must not be {@code null}
     * @param preserveFileDate true if the file date of the copy should be the
     * same as the original
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @since 1.1
     */
    public static void copyDirectory(final File srcDir, final File destDir,
            final boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    /**
     * Copies a filtered directory to a new location preserving the file dates.
     * <p>
     * This method copies the contents of the specified source directory to
     * within the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the files' last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that those operations will succeed. If the modification
     * operation fails, no indication is provided.
     * </p>
     * <h3>Example: Copy directories only</h3>
     * <pre>
     *  // only copy the directory structure
     *  FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY);
     * </pre>
     *
     * <h3>Example: Copy directories and txt files</h3>
     * <pre>
     *  // Create a filter for ".txt" files
     *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
     *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
     *
     *  // Create a filter for either directories or ".txt" files
     *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
     *
     *  // Copy using the filter
     *  FileUtils.copyDirectory(srcDir, destDir, filter);
     * </pre>
     *
     * @param srcDir an existing directory to copy, must not be {@code null}
     * @param destDir the new directory, must not be {@code null}
     * @param filter the filter to apply, null means copy all directories and
     * files should be the same as the original
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @since 1.4
     */
    public static void copyDirectory(final File srcDir, final File destDir,
            final FileFilter filter) throws IOException {
        copyDirectory(srcDir, destDir, filter, true);
    }

    /**
     * Copies a filtered directory to a new location.
     * <p>
     * This method copies the contents of the specified source directory to
     * within the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * {@code true} tries to preserve the files' last modified date/times using
     * {@link File#setLastModified(long)}, however it is not guaranteed that
     * those operations will succeed. If the modification operation fails, no
     * indication is provided.
     * </p>
     * <h3>Example: Copy directories only</h3>
     * <pre>
     *  // only copy the directory structure
     *  FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
     * </pre>
     *
     * <h3>Example: Copy directories and txt files</h3>
     * <pre>
     *  // Create a filter for ".txt" files
     *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
     *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
     *
     *  // Create a filter for either directories or ".txt" files
     *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
     *
     *  // Copy using the filter
     *  FileUtils.copyDirectory(srcDir, destDir, filter, false);
     * </pre>
     *
     * @param srcDir an existing directory to copy, must not be {@code null}
     * @param destDir the new directory, must not be {@code null}
     * @param filter the filter to apply, null means copy all directories and
     * files
     * @param preserveFileDate true if the file date of the copy should be the
     * same as the original
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @since 1.4
     */
    public static void copyDirectory(final File srcDir, final File destDir,
            final FileFilter filter, final boolean preserveFileDate) throws IOException {
        checkFileRequirements(srcDir, destDir);
        if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
        if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }

        // Cater for destination being directory within the source directory (see IO-141)
        List<String> exclusionList = null;
        if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
            final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
            if (srcFiles != null && srcFiles.length > 0) {
                exclusionList = new ArrayList<>(srcFiles.length);
                for (final File srcFile : srcFiles) {
                    final File copiedFile = new File(destDir, srcFile.getName());
                    exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
        doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
    }

    /**
     * checks requirements for file copy
     *
     * @param src the source file
     * @param dest the destination
     * @throws FileNotFoundException if the destination does not exist
     */
    private static void checkFileRequirements(final File src, final File dest) throws FileNotFoundException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }

    /**
     * Internal copy directory method.
     *
     * @param srcDir the validated source directory, must not be {@code null}
     * @param destDir the validated destination directory, must not be
     * {@code null}
     * @param filter the filter to apply, null means copy all directories and
     * files
     * @param preserveFileDate whether to preserve the file date
     * @param exclusionList List of files and directories to exclude from the
     * copy, may be null
     * @throws IOException if an error occurs
     * @since 1.1
     */
    private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter,
            final boolean preserveFileDate, final List<String> exclusionList)
            throws IOException {
        // recurse
        final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) {  // null if abstract pathname does not denote a directory, or if an I/O error occurs
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (destDir.isDirectory() == false) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else if (!destDir.mkdirs() && !destDir.isDirectory()) {
            throw new IOException("Destination '" + destDir + "' directory cannot be created");
        }
        if (destDir.canWrite() == false) {
            throw new IOException("Destination '" + destDir + "' cannot be written to");
        }
        for (final File srcFile : srcFiles) {
            final File dstFile = new File(destDir, srcFile.getName());
            if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                if (srcFile.isDirectory()) {
                    doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                } else {
                    doCopyFile(srcFile, dstFile, preserveFileDate);
                }
            }
        }

        // Do this last, as the above has probably affected directory metadata
        if (preserveFileDate) {
            destDir.setLastModified(srcDir.lastModified());
        }
    }

    /**
     * Copies a file or directory to within another directory preserving the
     * file dates.
     * <p>
     * This method copies the source file or directory, along all its contents,
     * to a directory of the same name in the specified destination directory.
     * <p>
     * The destination directory is created if it does not exist. If the
     * destination directory did exist, then this method merges the source with
     * the destination, with the source taking precedence.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the files' last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that those operations will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param src an existing file or directory to copy, must not be
     * {@code null}
     * @param destDir the directory to place the copy in, must not be
     * {@code null}
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @see #copyDirectoryToDirectory(File, File)
     * @see #copyFileToDirectory(File, File)
     * @since 2.6
     */
    public static void copyToDirectory(final File src, final File destDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (src.isFile()) {
            copyFileToDirectory(src, destDir);
        } else if (src.isDirectory()) {
            copyDirectoryToDirectory(src, destDir);
        } else {
            throw new IOException("The source " + src + " does not exist");
        }
    }

    /**
     * Copies a files to a directory preserving each file's date.
     * <p>
     * This method copies the contents of the specified source files to a file
     * of the same name in the specified destination directory. The destination
     * directory is created if it does not exist. If the destination file
     * exists, then this method will overwrite it.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the file's last
     * modified date/times using {@link File#setLastModified(long)}, however it
     * is not guaranteed that the operation will succeed. If the modification
     * operation fails, no indication is provided.
     *
     * @param srcs a existing files to copy, must not be {@code null}
     * @param destDir the directory to place the copy in, must not be
     * {@code null}
     *
     * @throws NullPointerException if source or destination is null
     * @throws IOException if source or destination is invalid
     * @see #copyFileToDirectory(File, File)
     * @since 2.6
     */
    public static void copyToDirectory(final Iterable<File> srcs, final File destDir) throws IOException {
        if (srcs == null) {
            throw new NullPointerException("Sources must not be null");
        }
        for (final File src : srcs) {
            copyFileToDirectory(src, destDir);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Deletes a directory recursively.
     *
     * @param directory directory to delete
     * @throws IOException in case deletion is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or
     * is not a directory
     */
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        if (!isSymlink(directory)) {
            cleanDirectory(directory);
        }

        if (!directory.delete()) {
            final String message
                    = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    /**
     * Deletes a file, never throwing an exception. If file is a directory,
     * delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be
     * deleted.</li>
     * </ul>
     *
     * @param file file or directory to delete, can be {@code null}
     * @return {@code true} if the file or directory was deleted, otherwise
     * {@code false}
     *
     * @since 1.4
     */
    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (final Exception ignored) {
        }

        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean
     * @throws IOException in case cleaning is unsuccessful
     * @throws IllegalArgumentException if {@code directory} does not exist or
     * is not a directory
     */
    public static void cleanDirectory(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Lists files in a directory, asserting that the supplied directory
     * satisfies exists and is a directory
     *
     * @param directory The directory to list
     * @return The files in the directory, never null.
     * @throws IOException if an I/O error occurs
     */
    private static File[] verifiedListFiles(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    //-----------------------------------------------------------------------
    /**
     * Waits for NFS to propagate a file creation, imposing a timeout.
     * <p>
     * This method repeatedly tests {@link File#exists()} until it returns true
     * up to the maximum time specified in seconds.
     *
     * @param file the file to check, must not be {@code null}
     * @param seconds the maximum time in seconds to wait
     * @return true if file exists
     * @throws NullPointerException if the file is {@code null}
     */
    public static boolean waitFor(final File file, final int seconds) {
        final long finishAt = System.currentTimeMillis() + (seconds * 1000L);
        boolean wasInterrupted = false;
        try {
            while (!file.exists()) {
                final long remaining = finishAt - System.currentTimeMillis();
                if (remaining < 0) {
                    return false;
                }
                try {
                    Thread.sleep(Math.min(100, remaining));
                } catch (final InterruptedException ignore) {
                    wasInterrupted = true;
                } catch (final Exception ex) {
                    break;
                }
            }
        } finally {
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
        }
        return true;
    }

    /**
     * Writes a byte array to a file creating the file if it does not exist.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created if
     * they do not exist.
     *
     * @param file the file to write to
     * @param data the content to write to the file
     * @throws IOException in case of an I/O error
     * @since 1.1
     */
    public static void writeByteArrayToFile(final File file, final byte[] data) throws IOException {
        writeByteArrayToFile(file, data, false);
    }

    /**
     * Writes a byte array to a file creating the file if it does not exist.
     *
     * @param file the file to write to
     * @param data the content to write to the file
     * @param append if {@code true}, then bytes will be added to the end of the
     * file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final boolean append)
            throws IOException {
        writeByteArrayToFile(file, data, 0, data.length, append);
    }

    /**
     * Writes {@code len} bytes from the specified byte array starting at offset
     * {@code off} to a file, creating the file if it does not exist.
     *
     * @param file the file to write to
     * @param data the content to write to the file
     * @param off the start offset in the data
     * @param len the number of bytes to write
     * @throws IOException in case of an I/O error
     * @since 2.5
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final int off, final int len)
            throws IOException {
        writeByteArrayToFile(file, data, off, len, false);
    }

    /**
     * Writes {@code len} bytes from the specified byte array starting at offset
     * {@code off} to a file, creating the file if it does not exist.
     *
     * @param file the file to write to
     * @param data the content to write to the file
     * @param off the start offset in the data
     * @param len the number of bytes to write
     * @param append if {@code true}, then bytes will be added to the end of the
     * file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.5
     */
    public static void writeByteArrayToFile(final File file, final byte[] data, final int off, final int len,
            final boolean append) throws IOException {
        try (OutputStream out = openOutputStream(file, append)) {
            out.write(data, off, len);
        }
    }

    /**
     * Writes the <code>toString()</code> value of each item in a collection to
     * the specified <code>File</code> line by line. The default VM encoding and
     * the specified line ending will be used.
     *
     * @param file the file to write to
     * @throws IOException in case of an I/O error
     * @since 2.1
     *
     * public static void writeLines(final File file, final Collection<?> lines,
     * final String lineEnding, final boolean append) throws IOException {
     * writeLines(file, null, lines, lineEnding, append); }
     *
     * //-----------------------------------------------------------------------
     * /** Deletes a file. If file is a directory, delete it and all
     * sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     * (java.io.File methods returns a boolean)</li>
     * </ul>
     * @throws NullPointerException if the directory is {@code null}
     * @throws FileNotFoundException if the file was not found
     */
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message
                        = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    /**
     * Schedules a file to be deleted when JVM exits. If file is directory
     * delete it and all sub-directories.
     *
     * @param file file or directory to delete, must not be {@code null}
     * @throws NullPointerException if the file is {@code null}
     * @throws IOException in case deletion is unsuccessful
     */
    public static void forceDeleteOnExit(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    /**
     * Schedules a directory recursively for deletion on JVM exit.
     *
     * @param directory directory to delete, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException in case deletion is unsuccessful
     */
    private static void deleteDirectoryOnExit(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        directory.deleteOnExit();
        if (!isSymlink(directory)) {
            cleanDirectoryOnExit(directory);
        }
    }

    /**
     * Cleans a directory without deleting it.
     *
     * @param directory directory to clean, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException in case cleaning is unsuccessful
     */
    private static void cleanDirectoryOnExit(final File directory) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDeleteOnExit(file);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * Makes a directory, including any necessary but nonexistent parent
     * directories. If a file already exists with specified name but it is not a
     * directory then an IOException is thrown. If the directory cannot be
     * created (or does not already exist) then an IOException is thrown.
     *
     * @param directory directory to create, must not be {@code null}
     * @throws NullPointerException if the directory is {@code null}
     * @throws IOException if the directory cannot be created or the file
     * already exists but is not a directory
     */
    public static void forceMkdir(final File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                final String message
                        = "File "
                        + directory
                        + " exists and is "
                        + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else if (!directory.mkdirs()) {
            // Double-check that some other thread or process hasn't made
            // the directory in the background
            if (!directory.isDirectory()) {
                final String message
                        = "Unable to create directory " + directory;
                throw new IOException(message);
            }
        }
    }

    /**
     * Makes any necessary but nonexistent parent directories for a given File.
     * If the parent directory cannot be created then an IOException is thrown.
     *
     * @param file file with parent to create, must not be {@code null}
     * @throws NullPointerException if the file is {@code null}
     * @throws IOException if the parent directory cannot be created
     * @since 2.5
     */
    public static void forceMkdirParent(final File file) throws IOException {
        final File parent = file.getParentFile();
        if (parent == null) {
            return;
        }
        forceMkdir(parent);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the size of the specified file or directory. If the provided
     * {@link File} is a regular file, then the file's length is returned. If
     * the argument is a directory, then the size of the directory is calculated
     * recursively. If a directory or subdirectory is security restricted, its
     * size will not be included.
     * <p>
     * Note that overflow is not detected, and the return value may be negative
     * if overflow occurs. See {@link #sizeOfAsBigInteger(File)} for an
     * alternative method that does not overflow.
     *
     * @param file the regular file or directory to return the size of (must not
     * be {@code null}).
     *
     * @return the length of the file, or recursive size of the directory,
     * provided (in bytes).
     *
     * @throws NullPointerException if the file is {@code null}
     * @throws IllegalArgumentException if the file does not exist.
     *
     * @since 2.0
     */
    public static long sizeOf(final File file) {

        if (!file.exists()) {
            final String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectory0(file); // private method; expects directory
        } else {
            return file.length();
        }

    }

    /**
     * Returns the size of the specified file or directory. If the provided
     * {@link File} is a regular file, then the file's length is returned. If
     * the argument is a directory, then the size of the directory is calculated
     * recursively. If a directory or subdirectory is security restricted, its
     * size will not be included.
     *
     * @param file the regular file or directory to return the size of (must not
     * be {@code null}).
     *
     * @return the length of the file, or recursive size of the directory,
     * provided (in bytes).
     *
     * @throws NullPointerException if the file is {@code null}
     * @throws IllegalArgumentException if the file does not exist.
     *
     * @since 2.4
     */
    public static BigInteger sizeOfAsBigInteger(final File file) {

        if (!file.exists()) {
            final String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectoryBig0(file); // internal method
        } else {
            return BigInteger.valueOf(file.length());
        }

    }

    /**
     * Counts the size of a directory recursively (sum of the length of all
     * files).
     * <p>
     * Note that overflow is not detected, and the return value may be negative
     * if overflow occurs. See {@link #sizeOfDirectoryAsBigInteger(File)} for an
     * alternative method that does not overflow.
     *
     * @param directory directory to inspect, must not be {@code null}
     * @return size of directory in bytes, 0 if directory is security
     * restricted, a negative number when the real total is greater than
     * {@link Long#MAX_VALUE}.
     * @throws NullPointerException if the directory is {@code null}
     */
    public static long sizeOfDirectory(final File directory) {
        checkDirectory(directory);
        return sizeOfDirectory0(directory);
    }

    // Private method, must be invoked will a directory parameter
    /**
     * the size of a director
     *
     * @param directory the directory to check
     * @return the size
     */
    private static long sizeOfDirectory0(final File directory) {
        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            return 0L;
        }
        long size = 0;

        for (final File file : files) {
            try {
                if (!isSymlink(file)) {
                    size += sizeOf0(file); // internal method
                    if (size < 0) {
                        break;
                    }
                }
            } catch (final IOException ioe) {
                // Ignore exceptions caught when asking if a File is a symlink.
            }
        }

        return size;
    }

    // Internal method - does not check existence
    /**
     * the size of a file
     *
     * @param file the file to check
     * @return the size of the file
     */
    private static long sizeOf0(final File file) {
        if (file.isDirectory()) {
            return sizeOfDirectory0(file);
        } else {
            return file.length(); // will be 0 if file does not exist
        }
    }

    /**
     * Counts the size of a directory recursively (sum of the length of all
     * files).
     *
     * @param directory directory to inspect, must not be {@code null}
     * @return size of directory in bytes, 0 if directory is security
     * restricted.
     * @throws NullPointerException if the directory is {@code null}
     * @since 2.4
     */
    public static BigInteger sizeOfDirectoryAsBigInteger(final File directory) {
        checkDirectory(directory);
        return sizeOfDirectoryBig0(directory);
    }

    // Must be called with a directory
    /**
     * Finds the size of a directory
     *
     * @param directory The directory
     * @return the size
     */
    private static BigInteger sizeOfDirectoryBig0(final File directory) {
        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            return BigInteger.ZERO;
        }
        BigInteger size = BigInteger.ZERO;

        for (final File file : files) {
            try {
                if (!isSymlink(file)) {
                    size = size.add(sizeOfBig0(file));
                }
            } catch (final IOException ioe) {
                // Ignore exceptions caught when asking if a File is a symlink.
            }
        }

        return size;
    }

    // internal method; if file does not exist will return 0
    /**
     * Returns the size of a file
     *
     * @param fileOrDir The file
     * @return the size
     */
    private static BigInteger sizeOfBig0(final File fileOrDir) {
        if (fileOrDir.isDirectory()) {
            return sizeOfDirectoryBig0(fileOrDir);
        } else {
            return BigInteger.valueOf(fileOrDir.length());
        }
    }

    /**
     * Checks that the given {@code File} exists and is a directory.
     *
     * @param directory The {@code File} to check.
     * @throws IllegalArgumentException if the given {@code File} does not exist
     * or is not a directory.
     */
    private static void checkDirectory(final File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Tests if the specified <code>File</code> is newer than the reference
     * <code>File</code>.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param reference the <code>File</code> of which the modification date is
     * used, must not be {@code null}
     * @return true if the <code>File</code> exists and has been modified more
     * recently than the reference <code>File</code>
     * @throws IllegalArgumentException if the file is {@code null}
     * @throws IllegalArgumentException if the reference file is {@code null} or
     * doesn't exist
     */
    public static boolean isFileNewer(final File file, final File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!reference.exists()) {
            throw new IllegalArgumentException("The reference file '"
                    + reference + "' doesn't exist");
        }
        return isFileNewer(file, reference.lastModified());
    }

    /**
     * Tests if the specified <code>File</code> is newer than the specified
     * <code>Date</code>.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param date the date reference, must not be {@code null}
     * @return true if the <code>File</code> exists and has been modified after
     * the given <code>Date</code>.
     * @throws IllegalArgumentException if the file is {@code null}
     * @throws IllegalArgumentException if the date is {@code null}
     */
    public static boolean isFileNewer(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileNewer(file, date.getTime());
    }

    /**
     * Tests if the specified <code>File</code> is newer than the specified time
     * reference.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param timeMillis the time reference measured in milliseconds since the
     * epoch (00:00:00 GMT, January 1, 1970)
     * @return true if the <code>File</code> exists and has been modified after
     * the given time reference.
     * @throws IllegalArgumentException if the file is {@code null}
     */
    public static boolean isFileNewer(final File file, final long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() > timeMillis;
    }

    //-----------------------------------------------------------------------
    /**
     * Tests if the specified <code>File</code> is older than the reference
     * <code>File</code>.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param reference the <code>File</code> of which the modification date is
     * used, must not be {@code null}
     * @return true if the <code>File</code> exists and has been modified before
     * the reference <code>File</code>
     * @throws IllegalArgumentException if the file is {@code null}
     * @throws IllegalArgumentException if the reference file is {@code null} or
     * doesn't exist
     */
    public static boolean isFileOlder(final File file, final File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        }
        if (!reference.exists()) {
            throw new IllegalArgumentException("The reference file '"
                    + reference + "' doesn't exist");
        }
        return isFileOlder(file, reference.lastModified());
    }

    /**
     * Tests if the specified <code>File</code> is older than the specified
     * <code>Date</code>.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param date the date reference, must not be {@code null}
     * @return true if the <code>File</code> exists and has been modified before
     * the given <code>Date</code>.
     * @throws IllegalArgumentException if the file is {@code null}
     * @throws IllegalArgumentException if the date is {@code null}
     */
    public static boolean isFileOlder(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileOlder(file, date.getTime());
    }

    /**
     * Tests if the specified <code>File</code> is older than the specified time
     * reference.
     *
     * @param file the <code>File</code> of which the modification date must be
     * compared, must not be {@code null}
     * @param timeMillis the time reference measured in milliseconds since the
     * epoch (00:00:00 GMT, January 1, 1970)
     * @return true if the <code>File</code> exists and has been modified before
     * the given time reference.
     * @throws IllegalArgumentException if the file is {@code null}
     */
    public static boolean isFileOlder(final File file, final long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() < timeMillis;
    }

    /**
     * Moves a file to a directory.
     *
     * @throws NullPointerException if source or destination is {@code null}
     * @throws IOException if source or destination is invalid
     * @since 1.4
     *
     * public static void moveFileToDirectory(final File srcFile, final File
     * destDir, final boolean createDestDir) throws IOException { if (srcFile ==
     * null) { throw new NullPointerException("Source must not be null"); } if
     * (destDir == null) { throw new NullPointerException("Destination directory
     * must not be null"); } if (!destDir.exists() && createDestDir) {
     * destDir.mkdirs(); } if (!destDir.exists()) { throw new
     * FileNotFoundException("Destination directory '" + destDir + "' does not
     * exist [createDestDir=" + createDestDir + "]"); } if
     * (!destDir.isDirectory()) { throw new IOException("Destination '" +
     * destDir + "' is not a directory"); } moveFile(srcFile, new File(destDir,
     * srcFile.getName())); }
     *
     * /**
     * Moves a file or directory to the destination directory.
     * <p>
     * When the destination is on another file system, do a "copy and delete".
     * @throws NullPointerException if source or destination is {@code null}
     * @since 1.4
     *
     * public static void moveToDirectory(final File src, final File destDir,
     * final boolean createDestDir) throws IOException { if (src == null) {
     * throw new NullPointerException("Source must not be null"); } if (destDir
     * == null) { throw new NullPointerException("Destination must not be
     * null"); } if (!src.exists()) { throw new FileNotFoundException("Source '"
     * + src + "' does not exist"); } if (src.isDirectory()) {
     * moveDirectoryToDirectory(src, destDir, createDestDir); } else {
     * moveFileToDirectory(src, destDir, createDestDir); } }
     *
     * /**
     * Determines whether the specified file is a Symbolic Link rather than an
     * actual file.
     * <p>
     * Will not return true if there is a Symbolic Link anywhere in the path,
     * only if the specific file is.
     * <p>
     * When using jdk1.7, this method delegates to
     * {@code boolean java.nio.file.Files.isSymbolicLink(Path path)}
     *
     * <b>Note:</b> the current implementation always returns {@code false} if
     * running on jkd1.6 and the system is detected as Windows using
     * {@link FilenameUtils#isSystemWindows()}
     * <p>
     * For code that runs on Java 1.7 or later, use the following method
     * instead:
     * <br> {@code boolean java.nio.file.Files.isSymbolicLink(Path path)}
     *
     * @param file the file to check
     * @return true if the file is a Symbolic Link
     * @since 2.0
     */
    public static boolean isSymlink(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        return Files.isSymbolicLink(file.toPath());
    }

    private FileUtils() {
    }
}
