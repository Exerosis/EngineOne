package me.engineone.engine.utilites;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ArchiveUtils {
    private ArchiveUtils() {
    }

    public static void downloadFile(URL url, File directory) throws IOException {
        if (directory.isFile())
            throw new FileNotFoundException("Cannot download file into a file, please specify a directory instead!");
        if (!directory.exists() && !directory.mkdirs())
            throw new FileNotFoundException("Cannot locate or create a directory at the given location!");
        String urlPath = url.getPath();
        String fileName = urlPath.substring(urlPath.lastIndexOf('/') + 1);

        String[] fileComponents = fileName.split("\\.");
        String name = fileComponents[0].replace("%20", " ");
        String extension = "";
        if (fileComponents.length >= 2)
            extension = fileComponents[1];

        StringBuilder path = new StringBuilder(directory.getPath()).append('/').append(name);

        if (!extension.equals("zip"))
            Files.copy(url.openStream(), Paths.get(path.append(extension).toString()), StandardCopyOption.REPLACE_EXISTING);
        else
            unzipIntoDirectory(url.openStream(), new File(path.toString()));
    }

    public static void copyFile(File from, File into) {
        if (!from.exists())
            return;
        if (from.getPath().equals(into.getPath()))
            return;
        from.setReadable(true);
        if (into.exists())
            into.delete();
        try {
            StreamUtil.writeBuffer(into, new FileInputStream(from), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzipIntoDirectory(File file, File directory) {
        try {
            unzipIntoDirectory(new FileInputStream(file), directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void unzipIntoDirectory(InputStream inputStream, File directory) {
        if (directory.isFile())
            return;
        directory.mkdirs();

        try {
            inputStream = new BufferedInputStream(inputStream);
            inputStream = new ZipInputStream(inputStream);

            for (ZipEntry entry = null; (entry = ((ZipInputStream) inputStream).getNextEntry()) != null; ) {
                StringBuilder pathBuilder = new StringBuilder(directory.getPath()).append('/').append(entry.getName());
                File file = new File(pathBuilder.toString());

                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                StreamUtil.write(pathBuilder, inputStream, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(inputStream);
        }
    }

}