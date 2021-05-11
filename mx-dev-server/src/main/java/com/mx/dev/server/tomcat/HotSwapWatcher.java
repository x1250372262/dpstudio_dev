
package com.mx.dev.server.tomcat;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * @Author: mengxiang.
 * @Date: 2021/04/10.
 * @Time: 09:00 下午.
 * @Description: 监听class文件变动 触发自定义classloader
 */
public class HotSwapWatcher extends Thread {


    protected List<Path> watchingPaths;
    private WatchKey watchKey;
    protected volatile boolean running = true;

    public HotSwapWatcher() {
        setName("YMPHotSwapWatcher");
        setDaemon(false);
        setPriority(Thread.MAX_PRIORITY);
        this.watchingPaths = buildWatchingPaths();
    }

    protected List<Path> buildWatchingPaths() {
        Set<String> watchingDirSet = new HashSet<>();
        String[] classPathArray = System.getProperty("java.class.path").split(File.pathSeparator);
        for (String classPath : classPathArray) {
            buildDirs(new File(classPath.trim()), watchingDirSet);
        }

        List<String> dirList = new ArrayList<>(watchingDirSet);
        Collections.sort(dirList);

        List<Path> pathList = new ArrayList<>(dirList.size());
        for (String dir : dirList) {
            pathList.add(Paths.get(dir));
        }

        return pathList;
    }

    private void buildDirs(File file, Set<String> watchingDirSet) {
        if (file.isDirectory()) {
            watchingDirSet.add(file.getPath());

            File[] fileList = file.listFiles();
            for (File f : fileList) {
                buildDirs(f, watchingDirSet);
            }
        }
    }

    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected void doRun() throws IOException {


        WatchService watcher = FileSystems.getDefault().newWatchService();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                watcher.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }));

        for (Path path : watchingPaths) {
            path.register(
                    watcher,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE
            );
        }

        while (running) {
            try {
                watchKey = watcher.take();
                if (watchKey == null) {
                    continue;
                }
            } catch (Throwable e) {
                running = false;
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                break;
            }

            List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
            for (WatchEvent<?> event : watchEvents) {
                String fileName = event.context().toString();
                Path path = (Path) watchKey.watchable();
                String pathDir = path.toString();
                pathDir = pathDir.substring(pathDir.indexOf("classes") + 8);
                pathDir = pathDir.replace(File.separator, ".");
                if (fileName.endsWith(".class")) {
                    try {
                        fileName = fileName.substring(0, fileName.indexOf("."));
                        new TomcatClassLoader().loadClass(pathDir+"."+fileName, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    resetWatchKey();
                    while ((watchKey = watcher.poll()) != null) {
                        watchKey.pollEvents();
                        resetWatchKey();
                    }
                    break;
                }
            }
            resetWatchKey();
        }
    }

    private void resetWatchKey() {
        if (watchKey != null) {
            watchKey.reset();
            watchKey = null;
        }
    }

    public void exit() {
        running = false;
        try {
            this.interrupt();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}







