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
package guru.nidi.tools.dependency;

import java.io.*;

class IoUtil {
    private IoUtil() {
    }

    static File fileEnding(File file, File baseDir, String ending) {
        return new File(baseDir, fileEnding(file, ending));
    }

    static String fileEnding(File file, String ending) {
        return file.getName().substring(0, file.getName().length() - 4) + ending;
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        final byte[] buf = new byte[10000];
        int read;
        while ((read = in.read(buf)) > 0) {
            out.write(buf, 0, read);
        }
    }
}
