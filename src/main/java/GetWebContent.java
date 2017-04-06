import sun.net.URLCanonicalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ACER on 2017/4/6.
 */
public class GetWebContent {
    public static void main(String [] args){
        String urlString = "http://www.baidu.com";
        String result = getWeb(urlString);
        System.out.println(result);
    }

    private static String getWeb(String urlString) {
        URL url;
        BufferedReader in = null;
        String result ="";

        try {
            url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine())!=null) {
                result += line + "\n";
            }
        } catch (MalformedURLException e) {
            System.err.println("MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException");
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
