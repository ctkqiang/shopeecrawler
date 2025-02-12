package cn.ctkqiang.shopeecrawler.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.ctkqiang.shopeecrawler.Constants.Names;

@Deprecated(since = "CAPTCHA", forRemoval = true)
public class Scrapper extends Utilities {
    private final int TIMEOUT = 5000;

    private String baseURL;

    @Temporary
    private final String url = "https://shopee.tw/%E9%99%A4%E8%9A%A4%E7%94%A8%E5%93%81-cat.11040647.11040669.11040680?is_from_signup=true&is_from_login=true";

    public Scrapper(String _url) {
        this.baseURL = Optional.ofNullable(_url)
                .filter(u -> !u.isEmpty())
                .orElse(this.url);
    }

    @Override
    protected void ExportToCSV(List<Map<String, String>> Data, String FilePath) throws IOException {
        super.ExportToCSV(Data, FilePath);
    }

    @SuppressWarnings("unused")
    public void GetWebsData() {
        String cssQuery = ".break-words";

        try {
            if (this.IsWorking(new URL(this.baseURL))) {

                Document document = Jsoup.connect(this.url)
                        .userAgent(Names.WEB_AGENT)
                        .header(Names.HEADER[0],
                                Names.HEADER[1])
                        .timeout(this.TIMEOUT)
                        .get();

                Elements productNames = document.select(cssQuery);

                System.out.println(document);
            }

            return;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean IsWorking(URL url) {
        return super.IsWorking(url);
    }

}
