package ru.nyrk.gisgmp;


import org.apache.commons.vfs2.FileChangeEvent;
import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.provider.FileProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nyrk.gisgmp.core.mess.MessageExecuttor;
import ru.nyrk.gisgmp.core.scaner.path.ScannerPath;
import ru.nyrk.vfs.MyVFS;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class BisLoaderSniffer {
    private static final Logger log = LoggerFactory.getLogger(BisLoaderSniffer.class);
    @Value("${bis.path.import}")
    String monitorPath;

    @Value("${bis.path.job}")
    String jobPath;

    @Qualifier("scannerPath")
    @Autowired
    private ScannerPath scannerPath;

    @Qualifier("messageExecuttor")
    @Autowired
    private MessageExecuttor executtor;
    private FileObject fileJobPath;


    public BisLoaderSniffer() {
    }


    class FileAlterationListener implements FileListener {


        @Override
        public void fileCreated(FileChangeEvent event) throws Exception {
            FileObject file = event.getFile();
            try {
                log.info("LOG00040:load file {}", file);
                long id = executtor.loadMessageFromFileBisFormat(file);
                log.debug("SUCCESS load file {}", file);
                moveFileObrab(file, id, false);
            } catch (Throwable th) {
                log.error("LOG00050:" + file, th);
                try {
                    moveFileObrab(file, 0, true);
                } catch (Exception e) {
                    log.error("LOG00030:", e);
                }
            }
        }

        @Override
        public void fileDeleted(FileChangeEvent event) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void fileChanged(FileChangeEvent event) throws Exception {
            fileCreated(event);
        }
    }


    private DateTimeFormatter formatterPrintDate = DateTimeFormat.forPattern("yyyyMMdd");

    private void moveFileObrab(FileObject file, long id, boolean isError) throws IOException {

        FileObject destJobPath = fileJobPath;
        if (isError) {
            destJobPath = fileJobPath.resolveFile("error");
        }
        destJobPath = destJobPath.resolveFile(LocalDate.now().toString(formatterPrintDate));
        if (!destJobPath.exists()) destJobPath.createFolder();
        FileObject fileDest = destJobPath.resolveFile(file.getName().getBaseName() + "." + String.valueOf(id));
        while (fileDest.exists()) {
            fileDest = destJobPath.resolveFile(fileDest.getName().getBaseName() + "." + String.valueOf(id));
        }
        file.moveTo(fileDest);
    }

    @PostConstruct
    public void start() throws Exception {

        log.info("LOG00100: {}", monitorPath);
        fileJobPath = MyVFS.getManager().resolveFile(jobPath);

        FileAlterationListener fl = new FileAlterationListener();
        FileObject path = MyVFS.getManager().resolveFile(monitorPath);
        scannerPath.addListener(path, fl);
        FileObject[] fileList = path.getChildren();

        for (FileObject file : fileList) {
            fl.fileCreated(new FileChangeEvent(file));
        }

    }


}
