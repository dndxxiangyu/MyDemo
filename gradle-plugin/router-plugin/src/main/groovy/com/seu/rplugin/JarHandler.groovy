package com.seu.rplugin

import com.android.build.api.transform.JarInput
import org.apache.commons.codec.digest.DigestUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

/**
 * 替换jar包中的class类。
 */
public class JarHandler {
    public void replaceJarFile(JarInput jarInput, byte[] fileByteCode, String fileName) throws IOException {
        String jarPathAndName = jarInput.file.absolutePath
        File jarFile = new File(jarPathAndName)
        File tempJarFile = new File(jarPathAndName + ".tmp")
        JarFile jar = new JarFile(jarFile);
        boolean jarWasUpdated = false;

        try {
            JarOutputStream tempJar =
                    new JarOutputStream(new FileOutputStream(tempJarFile));

            // Allocate a buffer for reading entry data.

            byte[] buffer = new byte[1024];
            int bytesRead;

            try {
                // Open the given file.

                try {
                    // Create a jar entry and add it to the temp jar.

                    JarEntry entry = new JarEntry(fileName);
                    tempJar.putNextEntry(entry);
                    tempJar.write(fileByteCode);

                } catch (Exception ex) {
                    System.out.println(ex);

                    // Add a stub entry here, so that the jar will close without an
                    // exception.

                    tempJar.putNextEntry(new JarEntry("stub"));
                }


                // Loop through the jar entries and add them to the temp jar,
                // skipping the entry that was added to the temp jar already.
                InputStream entryStream = null;
                for (Enumeration entries = jar.entries(); entries.hasMoreElements();) {
                    // Get the next entry.

                    JarEntry entry = (JarEntry) entries.nextElement();

                    // If the entry has not been added already, so add it.

                    if (!entry.getName().equals(fileName)) {
                        // Get an input stream for the entry.

                        entryStream = jar.getInputStream(entry);
                        tempJar.putNextEntry(entry);

                        while ((bytesRead = entryStream.read(buffer)) != -1) {
                            tempJar.write(buffer, 0, bytesRead);
                        }
                    } else
                        System.out.println("1-Does Equal");
                }
                if (entryStream != null)
                    entryStream.close();
                jarWasUpdated = true;
                System.out.println("2-Does Equal");
            }
            catch (Exception ex) {
                System.out.println("3-Does Equal");
                System.out.println(ex);

                // IMportant so the jar will close without an
                // exception.

                tempJar.putNextEntry(new JarEntry("stub"));
            }
            finally {
                tempJar.close();
            }
        }
        finally {

            jar.close();

            if (!jarWasUpdated) {
                tempJarFile.delete();
            }
        }

        if (jarWasUpdated) {
            if (jarFile.delete()) {
                if (tempJarFile.renameTo(jarFile)) {
                    System.out.println(jarPathAndName + " updated.");
                }
            } else
                System.out.println("Could Not Delete JAR File");
        }
    }
}