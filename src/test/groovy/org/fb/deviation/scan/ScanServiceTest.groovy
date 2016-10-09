package org.fb.deviation.scan

import org.fb.deviation.domain.DirNode
import org.fb.deviation.service.scan.ScanService
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ScanServiceTest extends Specification {

    public static final String BOTH1 = "both1"
    public static final String LEFT1 = "left1"
    public static final String RIGHT1 = "right1"
    @Rule
    TemporaryFolder temporaryFolder

    ScanService scanService = new ScanService()

    Path leftRoot
    Path rightRoot

    def setup() {
        File leftFolder = temporaryFolder.newFolder("left")
        leftRoot = Paths.get(leftFolder.toPath().toUri())
        File rightFolder = temporaryFolder.newFolder("right")
        rightRoot = Paths.get(rightFolder.toPath().toUri())

        Files.createDirectories(leftRoot.resolve(BOTH1))
        Files.createFile(leftRoot.resolve("both1/fileBoth11"))
        Files.createFile(leftRoot.resolve("both1/fileBothLeft11"))
        Files.createDirectories(leftRoot.resolve(LEFT1))
        Files.createFile(leftRoot.resolve("left1/fileLeft11"))

        Files.createDirectories(rightRoot.resolve(BOTH1))
        Files.createFile(rightRoot.resolve("both1/fileBoth11"))
        Files.createFile(rightRoot.resolve("both1/fileBothRight11"))
        Files.createDirectories(rightRoot.resolve(RIGHT1))
        Files.createFile(rightRoot.resolve("right1/fileRight11"))
    }


    def "Scan Folders"() {
        when:
        DirNode dirNode = scanService.scan(leftRoot, rightRoot)
        then:
        println(dirNode);
        dirNode.getDirs().size() == 3;
        //dirNode.get(Paths.get(LEFT1)) != null
        //dirNode.get(Paths.get(RIGHT1)) != null
    }

    /*def "scan files"() {
        when:
        DirNode dirNode =  scanService.scan(leftRoot, rightRoot)
        then:
        dirNode.getFiles() == 0
    }*/
}
