package org.ws2811.jni;

import java.io.*;
import java.nio.file.*;

import org.slf4j.*;

final class NativeLibraryLoader {
    private static final String LIB_NAME = "libws2811-jni";
    private static final Logger LOGGER = LoggerFactory.getLogger(NativeLibraryLoader.class);
    private static boolean mAlreadyLoaded;

    // prevent instantiation
    private NativeLibraryLoader() {}

    public static synchronized void load() {
        if (mAlreadyLoaded) {
            LOGGER.debug("Library [{}] is already loaded");
            return;
        }
        // There's no reason putting this back if false, even if it fails.
        mAlreadyLoaded = true;

        // Constructing file name
        String version = Ws2811Library.class.getPackage().getImplementationVersion();
        String fileName = LIB_NAME + "-" + version + ".so";
        String path = "/lib/" + fileName;

        LOGGER.debug("Attempting to extract and load [{}] from jar", path);
        try {
            loadLibraryFromClasspath(path);
            LOGGER.debug("Library [{}] loaded successfully", fileName);
        } catch (Exception | UnsatisfiedLinkError error) {
            LOGGER.error("Unable to load [{}] using path: [{}]", fileName, path, error);
        }
    }

    private static void loadLibraryFromClasspath(String path) throws IOException {
        Path inputPath = Paths.get(path);

        String fileNameFull = inputPath.getFileName().toString();
        int dotIndex = fileNameFull.indexOf('.');

        String fileName = fileNameFull.substring(0, dotIndex);
        String extension = fileNameFull.substring(dotIndex);

        Path target = Files.createTempFile(fileName, extension);
        File targetFile = target.toFile();
        targetFile.deleteOnExit();

        try (InputStream source = Ws2811Library.class.getResourceAsStream(inputPath.toString())) {
            if (source == null) throw new FileNotFoundException("File " + inputPath + " was not found in classpath.");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Load the library
        System.load(target.toAbsolutePath().toString());
    }
}
