package cn.waggag.shit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.util.Random;

/**
 * @description: 狗屁不通文章生成器
 * @author: waggag
 * @Date: 2019/11/22
 * @Company: https://www.waggag.cn/
 */
public class BullShit {
    /**
     * @param title  标题
     * @param length 正文长度
     * @return content 返回正文长度
     */
    public static String Generator(String title, int length) {
        String content = "";
        //获取文件的真实路径
        String realPath = getRealPath("data.json");
        try {
            //读取文件中的数据
            FileInputStream fileInputStream = new FileInputStream(realPath);
            String data = IOUtils.toString(fileInputStream, "UTF-8");
            //解析字符串
            JSONObject jsonData = JSON.parseObject(data);
            JSONArray famous = JSON.parseArray(jsonData.get("famous").toString());
            JSONArray before = JSON.parseArray(jsonData.get("before").toString());
            JSONArray after = JSON.parseArray(jsonData.get("after").toString());
            JSONArray bosh = JSON.parseArray(jsonData.get("bosh").toString());
            while (content.length() < length) {
                //获取100以内的随机整数
                Random random = new Random();
                int rand = random.nextInt(100);
                if (rand < 10) {
                    content += "\r\n";
                } else if (rand < 20) {
                    content += ((String) famous.get(random.nextInt(famous.size())))
                            .replace("a", (String) before.get(random.nextInt(before.size())))
                            .replace("b", (String) after.get(random.nextInt(after.size())));
                } else {
                    content += ((String) bosh.get(random.nextInt(bosh.size())));
                    content = content.replace("x", title);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 根据文件名获取真实路径
     * @param fileName 文件名
     * @return realPath 真实路径
     */
    private static String getRealPath(String fileName) {
        String readPath = null;
        try {
            //根据类加载器获取src文件的绝对路径
            readPath = BullShit.class.getClassLoader().getResource(fileName).getPath();
        } catch (Exception e) {
            System.out.println(("File Not Found " + fileName + "   " + e.getMessage()));
        }
        return readPath;
    }

    public static void main(String[] args) {
        String generator = Generator("我爱Java", 8000);
        System.out.print(generator);
    }
}
