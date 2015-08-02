package org.ws2811;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

final class NativeLibraryLoader {
    private final static String LIB_NAME = "libws2811-jni";

    private static boolean mAlreadyLoaded;

    // prevent instantiation
    private NativeLibraryLoader() {}

    public static synchronized void load() {

        if (mAlreadyLoaded) {
            return;
        }
        // There's no reason putting this back if false, even if it fails.
        mAlreadyLoaded = true;

        // Constructing file name
        String fileName = LIB_NAME;
        String version = Ws2811Library.class.getPackage().getImplementationVersion();
        if (null != version && version.length() > 0) {
            fileName += "-" + version;
        }
        fileName += ".so";

        String path = "/lib/" + fileName;
        System.out.println("Attempting to load [" + fileName + "] using path: [" + path + "]");
        try {
            loadLibraryFromClasspath(path);
            System.out.println("Library [" + fileName + "] loaded successfully using embedded resource file: [" + path + "]");
        } catch (Exception | UnsatisfiedLinkError e) {
            System.out.println("Unable to load [" + fileName + "] using path: [" + path + "]");
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
            if (source == null) {
                throw new FileNotFoundException("File " + inputPath + " was not found in classpath.");
            }
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Load the library
        System.load(target.toAbsolutePath().toString());
    }
}
