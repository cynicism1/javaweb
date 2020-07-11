package atguigu2.NIO;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/5 16:41
 */
/*
 * 一、使用NIO完成网络通信的三个核心
 *  1.通道(Channel): 负责连接
 *         |--- java.nio.channels.Channel接口
 *              |--- socketChannel
 *              | --- ServerSocketChannel
 *              | --- DatagramChannel
 *
 *              |--- Pipe.SinkChannel
 *              | --- Pipe.SourceChannel
 */
public class TestBlockingNIO {
    //客户端
    @Test
    public void client() {
        SocketChannel open = null;
        FileChannel open1 = null;
        try {
            //1.获取通道
            open = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            open1 = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            //2.分配指定大小的缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //3.读取本地文件，并发送到服务端
            while (open1.read(allocate) != -1) {
                allocate.flip();
                open.write(allocate);
                allocate.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (open != null)
                    open.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (open1 != null)
                    open1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //服务端
    @Test
    public void srver() {
        ServerSocketChannel open = null;
        FileChannel open1 = null;
        SocketChannel accept = null;
        try {
            open = ServerSocketChannel.open();
            open1 = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            //绑定连接 端口号
            open.bind(new InetSocketAddress(9898));
            //3.获取客户端的通道
            accept = open.accept();
            //4.分配指定大小的缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //4.接受客户端的数据并保存到本地
            while (accept.read(allocate) != -1) {
                allocate.flip();
                open1.write(allocate);
                allocate.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6. 关闭
            try {
                if (open != null)
                    open.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (open1 != null)
                    open1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (accept != null)
                    accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
