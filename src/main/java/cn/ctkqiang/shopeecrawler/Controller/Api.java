package cn.ctkqiang.shopeecrawler.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/api")
public class Api {

    @GetMapping("/scrape")
    @ResponseBody
    public ResponseEntity<String> scrapeShopeePage(
            @RequestParam(defaultValue = "https://shopee.tw/") String baseUrl,
            @RequestParam(defaultValue = "除蟲用品") String keyword,
            @RequestParam(defaultValue = "11040680") String categoryId) {

        try {

            Scrapper scraper = new Scrapper(baseUrl, keyword, categoryId);
            scraper.GetWebsData(true);

            return ResponseEntity.ok("爬取成功");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("爬取失败: " + e.getMessage());
        }
    }
}
