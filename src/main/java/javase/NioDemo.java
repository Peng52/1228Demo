package javase;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class NioDemo {
    // NIO 的测试demo
    @Test
    public void test() throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream("test");
        FileChannel channel = outputStream.getChannel();

        System.out.println("dout");
    }
    @Test
    public void testRandomAccessFile() throws IOException {
        File file = new File("C:\\Users\\PENG\\Desktop\\test.txt");
        File outFile = new File("C:\\Users\\PENG\\Desktop\\copyTest.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(outFile,"rw");
        FileChannel Outchannel = randomAccessFile.getChannel();
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        // 将 Channel 中的数据全部或者是部分映射成 Buff 中去
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        Outchannel.position(outFile.length());
        Outchannel.write(buffer);

    }

    /**
     * {@link Set}
     */
    // CharSet 字符集 编码 & 解码
    @Test
    public void testChartSet(){
        // 获取Java支持的字符集 CharSet是不可变类
        SortedMap<String, Charset> chartSetMap= Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = chartSetMap.entrySet();
        for (Map.Entry<String,Charset> entry : entries){

            String key = entry.getKey();
            Charset value = entry.getValue();
           // System.out.println("key : " + key + "value : " +value);
        }

        // 获取操作系统使用的 文件编码格式
        Properties properties = System.getProperties();
        String property = properties.getProperty("file.encoding");
        System.out.println(property);

    }



    // 通过CharSet字符集 将ByteBuffer转化为 CharBuffer
    /**
    * 通过 CharSet 类提供的方法 就可以进行简单的编码和解码操作
    *
    * */
    @Test
    public void testByteBuffer2CharBuffer() throws CharacterCodingException {
        // 创建一个指定字符编码的 字符集
        Charset charset = Charset.forName("GBK");
        // 创建对应的字符编码的解码器和编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        // 创建一个CharBuffer,并初始化
        CharBuffer charBuffer= CharBuffer.allocate(10);
        charBuffer.put("彭");
        charBuffer.put("程");
        charBuffer.flip();
        // 将 CharBuffer 转化为 ByteBuffer
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < encode.capacity(); i++) {
            encode.get(i);
            System.out.println(encode.get(i));
        }
    }

    // Java 7 改进的 NIO.2
    /**
     *  1. Path接口代表一个和平台无关的平台路径
     *  两个工具类 :
     *  2.Paths : 两个返回Path的静态工厂方法
     *  3.Files : 大量的静态工厂方法来操作文件
     *
     *
     */
    @Test
    public void testPathFile(){
        Path path = Paths.get(".");
        int nameCount = path.getNameCount();
        System.out.println("path 包含的路径数量：" + nameCount);
        Path path1 = path.toAbsolutePath();
        System.out.println("获取绝对路径：" + path1);
        System.out.println("获取绝对路径的根路径 ： " + path1.getRoot());

    }

    // map里面可以存入同一个Key吗？
    // 可以的，但是会覆盖之前的key的旧值
    @Test
    public void testMapKey(){

        HashMap<Long, Long> hashMap = new HashMap<>(10);
        Long put = hashMap.put(1L, 1L);
        hashMap.put(1L,2L);
        System.out.println(hashMap);
        log.error("测试日志输出......");

    }

    //  测试 BufferReader  字符输出流
    /**
     * bufferReader中读进来 readerLine() 是否可以循环调用读取
     */
    @Test
    public void testBufferReader() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\新建文本文档.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        //bufferedReader.skip(s.length());
        //List<Object> collect = bufferedReader.lines().collect(Collectors.toList());
        String s1 = bufferedReader.readLine();
        System.out.println(s);



    }




}
