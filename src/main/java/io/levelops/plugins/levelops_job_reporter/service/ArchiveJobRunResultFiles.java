package io.levelops.plugins.levelops_job_reporter.service;

import hudson.Util;
import hudson.remoting.VirtualChannel;
import io.levelops.plugins.commons.service.ZipService;
import jenkins.MasterToSlaveFileCallable;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchiveJobRunResultFiles extends MasterToSlaveFileCallable<List<Byte>>  {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final String includes;

    public ArchiveJobRunResultFiles(String includes) {
        this.includes = includes;
    }

    @Override
    public List<Byte> invoke(File basedir, VirtualChannel channel) throws IOException {
        LOGGER.log(Level.FINEST, "starting invoke");
        File tempDir = null;
        File archiveFile = null;
        try {
            FileSet fileSet = Util.createFileSet(basedir, includes, null);
            final String[] includedFiles = fileSet.getDirectoryScanner().getIncludedFiles();
            if(includedFiles == null) {
                LOGGER.log(Level.FINE, "includedFiles is null, no results found for includes {0}", includes);
                return Collections.emptyList();
            }
            LOGGER.log(Level.FINEST, "includedFiles = {0}", includedFiles);

            tempDir = Files.createTempDirectory("levelops_archive_dir_").toFile();
            LOGGER.log(Level.FINEST, "tempDir = {0}", tempDir);
            for (String includedFile : includedFiles) {
                LOGGER.log(Level.FINEST, "includedFile = {0}", includedFile);

                File sourceFile = new File(basedir, includedFile);
                LOGGER.log(Level.FINEST, "sourceFile = {0}", sourceFile);

                String fileNameWithoutExtension = FilenameUtils.removeExtension(sourceFile.getName());
                String fileExtension = FilenameUtils.getExtension(sourceFile.getName());
                String destinationFileNameRandom = fileNameWithoutExtension + "_" + RandomStringUtils.randomAlphanumeric(5).toUpperCase() + "." + fileExtension;
                File destinationFileRandom = new File(tempDir, destinationFileNameRandom);
                LOGGER.log(Level.FINEST, "destinationFileRandom = {0}", destinationFileRandom);

                //FileUtils
                FileUtils.copyFile(sourceFile, destinationFileRandom);
            }
            LOGGER.log(Level.FINE, "files copied count {0}", includedFiles.length);
            archiveFile = Files.createTempFile("levelops_archive_file_", ".zip").toFile();
            LOGGER.log(Level.FINEST, "archiveFile {0}", archiveFile);
            ZipService zipService = new ZipService();
            LOGGER.log(Level.FINEST, "zipping results");
            zipService.zipDirectory(tempDir, archiveFile);
            LOGGER.log(Level.FINEST, "zipping results completed");
            final byte[] bytes = FileUtils.readFileToByteArray(archiveFile);
            if(bytes != null) {
                LOGGER.log(Level.FINE, "archive results zip size = {0}", bytes.length);
            } else {
                LOGGER.log(Level.FINE, "archive results zip size = 0");
            }
            return Arrays.asList(ArrayUtils.toObject(bytes));
        } finally {
            if (tempDir != null) {
                FileUtils.deleteDirectory(tempDir);
            }
            FileUtils.deleteQuietly(archiveFile);
        }
    }
}
