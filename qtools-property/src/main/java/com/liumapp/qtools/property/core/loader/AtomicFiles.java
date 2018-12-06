package com.liumapp.qtools.property.core.loader;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

/**
 * file AtomicFiles.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * A utility for creating "atomic" file writers.
 *
 * <p>An atomic writer copies any existing file at the given path to a temporary location, then
 * writes to the same temporary location, before moving the file back to the desired output path
 * once the write is fully complete.</p>
 */
public final class AtomicFiles {
    private AtomicFiles() {}

    /**
     * Creates and returns an "atomic" writer factory for the given path.
     *
     * @param path The path
     * @param charset The charset to be used by the writer
     * @return The writer factory
     */
    @NonNull
    public static Callable<BufferedWriter> createAtomicWriterFactory(@NonNull Path path, @NonNull Charset charset) {
        Preconditions.checkNotNull(path, "path");
        return () -> createAtomicBufferedWriter(path, charset);
    }

    /**
     * Creates and returns an "atomic" writer for the given path.
     *
     * @param path The path
     * @param charset The charset to be used by the writer
     * @return The writer factory
     */
    @NonNull
    public static BufferedWriter createAtomicBufferedWriter(@NonNull Path path, @NonNull Charset charset) throws IOException {
        path = path.toAbsolutePath();

        Path writePath = getTemporaryPath(path.getParent(), path.getFileName().toString());
        if (Files.exists(path)) {
            Files.copy(path, writePath, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
        }

        BufferedWriter output = Files.newBufferedWriter(writePath, charset);
        return new BufferedWriter(new AtomicFileWriter(writePath, path, output));
    }

    @NonNull
    private static Path getTemporaryPath(@NonNull Path parent, @NonNull String key) {
        String fileName = System.nanoTime() + Preconditions.checkNotNull(key, "key").replaceAll("\\\\|/|:",
                "-") + ".tmp";
        return parent.resolve(fileName);
    }

    private static class AtomicFileWriter extends FilterWriter {
        private final Path targetPath, writePath;

        protected AtomicFileWriter(Path writePath, Path targetPath, Writer wrapping) {
            super(wrapping);
            this.writePath = writePath;
            this.targetPath = targetPath;
        }

        @Override
        public void close() throws IOException {
            super.close();
            Files.move(writePath, targetPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
