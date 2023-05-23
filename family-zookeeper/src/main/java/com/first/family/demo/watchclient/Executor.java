package com.first.family.demo.watchclient;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A Simple Watch Client
 * 启动参数: 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183 /watch data/znode-data seq.sh
 *
 * @description: <a href="https://zookeeper.apache.org/doc/current/javaExample.html#ch_Introduction"></a>
 * @author: cuiweiman
 * @date: 2022/11/16 16:22
 */
public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {

    private ZooKeeper zk;
    private DataMonitor dm;
    private String znode;
    private String fileName;
    private String[] exec;
    private Process child;

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("USAGE: Executor hostPort znode filename program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String znode = args[1];
        String filename = args[2];
        // 获取 命令参数值中的 进程脚本 路径, 以及脚本运行所需的参数
        String exec[] = new String[args.length - 3];
        System.arraycopy(args, 3, exec, 0, exec.length);

        Thread thread = new Thread(() -> {
            try {
                new Executor(hostPort, znode, filename, exec);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "DataMonitorListener");
        thread.start();
    }

    public Executor(String hostPort, String znode, String fileName, String[] exec) throws IOException {
        this.fileName = fileName;
        this.exec = exec;
        // watcher 类为 本身，收到 znode 状态变化通知后 会调用 process 方法
        zk = new ZooKeeper(hostPort, 30000, this);
        // 自定义的数据监听器，监听方法有 DataMonitorListener#exits 和 DataMonitorListener#closing
        dm = new DataMonitor(zk, znode, this);
    }

    @Override
    public void run() {
        // 创建线程,监听 DataMonitor, dm.dead=true, 监听器关闭，则线程执行结束并死亡
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 方法实现自 {@link Watcher}，实现后可以接收 znode 节点状态变化
     *
     * @param watchedEvent watcher 事件
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        // 跳转到 DataMonitor#handle 类进行处理
        dm.handle(watchedEvent);
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void exits(byte[] data) {
        if (data == null) {
            // data == null，杀死进程
            if (child != null) {
                System.out.println("Killing handle");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            child = null;
        } else {
            // data != null，则结束进程
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class StreamWriter extends Thread {
        OutputStream os;
        InputStream is;

        StreamWriter(InputStream is, OutputStream os) {
            this.is = is;
            this.os = os;
            start();
        }

        @Override
        public void run() {
            byte b[] = new byte[80];
            int rc;
            try {
                while ((rc = is.read(b)) > 0) {
                    os.write(b, 0, rc);
                }
            } catch (IOException e) {
            }

        }
    }

}
