package atguigu.NIO;

/**
 * @author Cynicism
 * @Package_name
 * @since 2020/7/5 11:20
 */

import com.sun.jmx.snmp.SnmpPduTrap;
import org.junit.Test;
import sun.nio.cs.CharsetMapping;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.channels.FileChannel.MapMode;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 一、通道(Channel) ：用于源节点与目标节点的连接，在java NIO 中负责缓冲区中的数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * <p>
 * 二、通道主要实现类：
 * java.nio.channel.Channel 接口：
 * |---FileChannel
 * |---SocketChannel
 * |---ServerSocketChannel
 * |---DatagramChannel
 * <p>
 * 三、获取通道
 * 1.java 针对支持通道的类提供了 getChannel() 方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * 网络IO:
 * socket
 * ServerSocket
 * DatagramSocket
 * <p>
 * 2.在jdk 1.7 中的NIO.2 针对各个通道提供了静态方法 open();
 * 3.在jdk 1.7 中的NIO.2 中的Files 工具类的newByteChannel();
 * <p>
 * 四：通道之间的传输
 * transferFrom()
 * transferTo()
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取(Scattering reads) :将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Writer): 将多个缓冲区中的数据聚集到通道中
 * <p>
 * 六、 字符集：charset
 * 编码：字符串 -- > 字节数组
 * 解码： 字节数组 -- > 字符串
 */
public class TestChannel {
    //字符串
    @Test
    public void test6() throws CharacterCodingException {
        Charset gbk = Charset.forName("GBK");
        //获取编码器与解码器
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        CharsetDecoder charsetDecoder = gbk.newDecoder();
        CharBuffer allocate = CharBuffer.allocate(1024);
        allocate.put("尚硅谷威武");
        allocate.flip();
        ByteBuffer encode = charsetEncoder.encode(allocate);
        for (int i = 0; i < 10; i++) {
            System.out.println(encode.get());
        }
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.get());

    }
    @Test
    public void test5() {
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = stringCharsetSortedMap.entrySet();
        for (Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    //分散(Scatter)与聚集(Gather)
    @Test

    public void test4() throws Exception {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        //1.获取通道
        FileChannel channel = rw.getChannel();
        //2.分配指定大小的缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(100);
        ByteBuffer allocate1 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffer = {allocate, allocate1};
        channel.read(buffer);
        for (ByteBuffer byteBuffer : buffer) {
            byteBuffer.flip();
        }
        System.out.println(new String(buffer[0].array(), 0, buffer[0].limit()));
        System.out.println("*****************分隔1****************");
        System.out.println(new String(buffer[1].array(), 0, buffer[1].limit()));

    }

    //通道之间的传输数据
    @Test
    public void test3() {
        FileChannel open = null;
        FileChannel open1 = null;
        try {
            open = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            open1 = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            //open.transferTo(0, open.size(), open1);
            open1.transferFrom(open, 0, open.size());
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

    //2.只用直接缓冲区完成文件的复制（内存映射文件）
    @Test
    public void test2() {
        long start = System.currentTimeMillis();
        FileChannel open = null;
        FileChannel open1 = null;
        try {
            open = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            open1 = FileChannel.open(Paths.get("4.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
            //内存映射文件
            MappedByteBuffer map = open.map(MapMode.READ_ONLY, 0, open.size());
            MappedByteBuffer map1 = open1.map(MapMode.READ_WRITE, 0, open.size());
            //直接对缓冲区进行数据的读写操作
            byte[] dst = new byte[map.limit()];
            map.get(dst);
            map1.put(dst);
            long end = System.currentTimeMillis();
            System.out.println("耗费时间为：" + (end - start));
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

    //1.利用通道完成文件的复制(非直接缓冲区)
    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel channel = null;
        FileChannel channel1 = null;
        try {
            fileInputStream = new FileInputStream("1.jpg");
            fileOutputStream = new FileOutputStream("2.jpg");
            //获取通道
            channel = fileInputStream.getChannel();
            channel1 = fileOutputStream.getChannel();
            //分配指定大小的缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //将通道的数据插入缓冲区
            while (channel.read(allocate) != -1) {
                allocate.flip();//切换成读取数据模式
                //将缓冲区的数据写入通道
                channel1.write(allocate);
                allocate.clear();//清空缓冲区
            }
            long end = System.currentTimeMillis();
            System.out.println("耗费时间为：" + (end - start));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (channel != null)
                    channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (channel1 != null)
                    channel1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
