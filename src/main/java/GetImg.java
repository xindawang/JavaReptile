import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class GetImg {

    // 地址
    private static final String URL = "http://www.tooopen.com/view/1439719.html";

    public static void main(String[] args) {
        GetList gl = new GetList(URL);
        getImgFromList(gl.getList());
    }


    //对单个图像创建下载线程
    public static void getImg(String url) throws IOException {
        Date begindate2 = new Date();
        String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
        URL uri = new URL(url);
        InputStream in = uri.openStream();
        FileOutputStream fo = new FileOutputStream(new File("D:/reptile/"+imageName));
        byte[] buf = new byte[1024];
        int length = 0;
        System.out.println("开始下载:" + url);
        while ((length = in.read(buf, 0, buf.length)) != -1) {
            fo.write(buf, 0, length);
        }
        in.close();
        fo.close();
        System.out.println(imageName + "下载完成");
        //结束时间
        Date overdate2 = new Date();
        double time = overdate2.getTime() - begindate2.getTime();
        System.out.println("耗时：" + time / 1000 + "s");
    }

    //下载图片
    public static void getImgFromList(List<String> listImgSrc) {

        Date begindate = new Date();
        for (final String url : listImgSrc) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        getImg(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        Date overdate = new Date();
        double time = overdate.getTime() - begindate.getTime();
        System.out.println("总耗时：" + time / 1000 + "s");

    }
}