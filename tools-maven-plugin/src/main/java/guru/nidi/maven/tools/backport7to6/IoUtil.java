/*
 * Copyright © 2014 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.maven.tools.backport7to6;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

class IoUtil {
    static void unzip(File jar, File target) throws IOException {
        final ZipFile in = new ZipFile(jar);
        final Enumeration<? extends ZipEntry> entries = in.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                final File file = new File(target, entry.getName());
                file.getParentFile().mkdirs();
                copy(in.getInputStream(entry), new FileOutputStream(file), true);
            }
        }
        in.close();
    }

    static void zip(File source, File target) throws IOException {
        final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));
        zip("", source, out);
        out.close();
    }

    private static void zip(String base, File source, ZipOutputStream target) throws IOException {
        final File[] files = source.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    final String fullName = base + file.getName() + "/";
                    target.putNextEntry(new ZipEntry(fullName));
                    target.closeEntry();
                    zip(fullName, file, target);
                } else {
                    target.putNextEntry(new ZipEntry(base + file.getName()));
                    copy(new FileInputStream(file), target, false);
                    target.closeEntry();
                }
            }
        }
    }

    private static void copy(InputStream in, OutputStream out, boolean closeOut) throws IOException {
        final byte[] buf = new byte[10000];
        int read;
        while ((read = in.read(buf)) > 0) {
            out.write(buf, 0, read);
        }
        in.close();
        if (closeOut) {
            out.close();
        }
    }

    static void copyRecursively(File source, File target) throws IOException {
        target.mkdirs();
        final File[] files = source.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    copyRecursively(file, new File(target, file.getName()));
                } else {
                    copy(new FileInputStream(file), new FileOutputStream(new File(target, file.getName())), true);
                }
            }
        }
    }

    static void deleteAll(File dir) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    deleteAll(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }
}
