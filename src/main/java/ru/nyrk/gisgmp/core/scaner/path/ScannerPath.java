package ru.nyrk.gisgmp.core.scaner.path;

import org.apache.commons.vfs2.FileListener;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ScannerPath {
    DefaultFileMonitor monitor;

    public ScannerPath() {

    }

    @PostConstruct
    public void start() throws Exception {
        if (monitor != null)
            monitor.start();
    }

    @PreDestroy
    public void stop() throws Exception {
        if (monitor != null)
            monitor.stop();
    }


    public void addListener(FileObject path, FileListener listener) throws Exception {
        stop();
        try {
            monitor = new DefaultFileMonitor(listener);
            monitor.addFile(path);
        } finally {
            start();
        }

    }


}
