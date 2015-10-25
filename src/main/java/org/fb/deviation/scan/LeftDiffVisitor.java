package org.fb.deviation.scan;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class LeftDiffVisitor extends SimpleFileVisitor<Path> {

    private final Path rootPath;
    private final Collector collector;

    LeftDiffVisitor(Path rootPath, Collector collector) {
        this.rootPath = rootPath;
        this.collector = collector;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path relativeFolder = rootPath.relativize(dir);
        collector.addLeftDir(relativeFolder);
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path relativeFolder = rootPath.relativize(file.getParent());
        Path relativeFile = rootPath.relativize(file);
        collector.addLeftFile(relativeFile, relativeFolder);
        return super.visitFile(file, attrs);
    }
}
