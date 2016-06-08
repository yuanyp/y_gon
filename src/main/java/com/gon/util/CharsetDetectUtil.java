package com.gon.util;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * 编码识别工具类
 * @author yuanyp
 *
 */
public class CharsetDetectUtil {
    public static String detect(byte[] content) {
        UniversalDetector detector = new UniversalDetector(null);
            //开始给一部分数据，让学习一下啊，官方建议是1000个byte左右（当然这1000个byte你得包含中文之类的）
        detector.handleData(content, 0, content.length);
            //识别结束必须调用这个方法
        detector.dataEnd();
        //神奇的时刻就在这个方法了，返回字符集编码。
        return detector.getDetectedCharset();
    }
}