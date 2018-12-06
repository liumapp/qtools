package com.liumapp.qtools.property.core.loader;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * file CommentHandler.java
 * author liumapp
 * github https://github.com/liumapp
 * email liumapp.com@gmail.com
 * homepage http://www.liumapp.com
 * date 2018/12/6
 * Interface specifying methods for handling abstract comments
 */
public interface CommentHandler {

    /**
     * Defines the handlers behaviour for reading comments.
     *
     * @param reader The reader
     * @return The comment
     * @throws IOException If any IO error occurs in the process
     */
    @NonNull
    Optional<String> extractHeader(@NonNull BufferedReader reader) throws IOException;

    /**
     * Converts the given lines into a comment
     *
     * @param lines The lines to make a comment
     * @return The transformed lines
     */
    @NonNull
    Collection<String> toComment(@NonNull Collection<String> lines);

}
