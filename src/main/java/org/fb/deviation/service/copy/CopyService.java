package org.fb.deviation.service.copy;

import org.fb.deviation.domain.DNode;
import org.fb.deviation.fx.common.Pref;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class CopyService {

    private final Pref pref;

    @Autowired
    CopyService(Pref pref) {
        this.pref = pref;
    }

    public void copy(DNode item) {
        try {
            File leftRoot = pref.lastLeftRoot();
            File rightRoot = pref.lastRightRoot();
            Path relativePath = item.getRelativePath();
            Path from = leftRoot.toPath().resolve(relativePath);
            Path to = rightRoot.toPath().resolve(relativePath);
            CopyOption[] options = {
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
