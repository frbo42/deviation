package org.fb.deviation.scan;

import org.fb.deviation.model.DirNode;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ScanService {

    public DirNode scan(@Nonnull Path leftPath, @Nonnull Path rightPath) throws IOException {
        Collector collector = new Collector();

        Files.walkFileTree(leftPath, new LeftDiffVisitor(leftPath, collector));
        Files.walkFileTree(rightPath, new RightDiffVisitor(rightPath, collector));

        return collector.getRoot();
    }
}
