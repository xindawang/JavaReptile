import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ACER on 2017/4/7.
 */
public class GetList {
    // 获取img标签正则
    private static final String IMG_URL = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMG_SRC_HTTP = "[a-zA-z]+://[^\\s]*\\.(jpg|png|gif|jpeg)";
    private static final String IMG_SRC = "//[^\\s]*\\.(jpg|png|gif|jpeg)";
    private String URL;

    public GetList(String url){
        this.URL = url;
    }

    //获取HTML内容
    public List<String> getList(){
        String HTML = null;
        try {
            HTML = getHtml(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取图片标签
        List<String> imgUrl = getImageUrl(HTML);
        //获取图片src地址
        return getImageSrc(imgUrl);
    }

    private String getHtml(String url)throws Exception{
        URL url1=new URL(url);
        URLConnection connection=url1.openConnection();
        InputStream in=connection.getInputStream();
        InputStreamReader isr=new InputStreamReader(in);
        BufferedReader br=new BufferedReader(isr);

        String line;
        StringBuffer sb=new StringBuffer();
        while((line=br.readLine())!=null){
            sb.append(line,0,line.length());
            sb.append('\n');
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    //获取ImageUrl地址
    private List<String> getImageUrl(String html){
        Matcher matcher= Pattern.compile(IMG_URL).matcher(html);
        List<String>listimgurl=new ArrayList<String>();
        while (matcher.find()){
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }

    //获取ImageSrc地址
    private List<String> getImageSrc(List<String> listimageurl){
        List<String> listImageSrc=new ArrayList<String>();
        for (String image:listimageurl){
            if (!image.contains("http:")) {
                Matcher matcher = Pattern.compile(IMG_SRC).matcher(image);
                while (matcher.find()){
                    listImageSrc.add("http:"+matcher.group().substring(0, matcher.group().length()));
                }
            }else {
                Matcher matcher = Pattern.compile(IMG_SRC_HTTP).matcher(image);
                while (matcher.find()) {
                    listImageSrc.add(matcher.group().substring(0, matcher.group().length()));
                }
            }
        }
        return listImageSrc;
    }
}
