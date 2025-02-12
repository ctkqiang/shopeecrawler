package cn.ctkqiang.shopeecrawler.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Scrapper extends Utilities {

    @Temporary
    private final String url = "https://shopee.tw/%E9%99%A4%E8%9A%A4%E7%94%A8%E5%93%81-cat.11040647.11040669.11040680?is_from_signup=true&is_from_login=true";

    @Override
    protected void ExportToCSV(List<Map<String, String>> Data, String FilePath) throws IOException {
        super.ExportToCSV(Data, FilePath);
    }

    public void GetWebsData() {
        System.out.println("Getting Web Data..."); // TODO Remove this

        String cssQuery = ".shopee-search-item-result__item";

        try {
            Document document = Jsoup.connect(this.url).get();
            Elements productNames = document.select(cssQuery);

            System.out.println(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
