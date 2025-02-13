package cn.ctkqiang.shopeecrawler.Controller;

// Java standard imports
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.io.StringReader;

// OkHttp imports
import java.util.concurrent.TimeUnit;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Jsoup imports
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// Spring imports
import org.slf4j.Logger;
import org.springframework.lang.NonNull;

// Gson imports
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// Project imports
import cn.ctkqiang.shopeecrawler.Constants.Names;

@Deprecated(since = "The project have CAPTCHA and blocked by Shopee", forRemoval = false)
public class Scrapper extends Utilities {
    // HTTP Client configuration
    private static OkHttpClient HTTP_CLIENT;
    private final Logger logger = Utilities.logger;
    private final int TIMEOUT = 5000;

    // Pagination settings
    private final int limit = 10;
    private final int pages = 3;

    // URLs
    private String baseURL;

    private String category;
    private String keyword;

    @Temporary
    private final String url = "https://shopee.tw/%E9%99%A4%E8%9A%A4%E7%94%A8%E5%93%81-cat.11040647.11040669.11040680?is_from_signup=true&is_from_login=true";
    @Temporary
    private static final String API_URL = "https://shopee.tw/api/v4/search/search_items";

    public Scrapper(String _url, String keyword, String category) {
        this.baseURL = Optional.ofNullable(_url)
                .filter(u -> !u.isEmpty())
                .orElse(this.url);
        this.keyword = keyword;
        this.category = category;
    }

    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        HTTP_CLIENT = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    protected void ExportToCSV(List<Map<String, String>> Data, String FilePath) throws IOException {
        super.ExportToCSV(Data, FilePath);
    }

    @SuppressWarnings("unused")
    public void GetWebsData(@NonNull boolean isRepeat) throws IOException {
        this.logger.debug("Repeat: " + isRepeat);

        if (isRepeat) {
            for (int page = 0; page < this.pages; page++) {
                int newest = page * this.limit;

                String _url = "https://shopee.tw/api/v4/search/search_items"
                        + "?by=relevancy&limit=60&newest=0&order=desc&page_type=search"
                        + "&scenario=PAGE_CATEGORY_SEARCH&source=SRP&version=2"
                        + "&fe_categoryids=" + category
                        + "&keyword=" + keyword;

                Request request = new Request.Builder()
                        .url(_url)
                        .header("User-Agent", Names.WEB_AGENT)
                        .header("X-CSRFToken", "04LbcdZA22ckKvNHe9acXyufHdXJDoOS")
                        .header("Cookie", "SPC_EC=your_spc_ec_value; SPC_SI=your_spc_si_value;")
                        .header("Referer", "https://shopee.tw/")
                        .header("Accept", "application/json")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("X-Shopee-Language", "zh-Hant")
                        .build();

                System.out.println(request);

                try (Response response = Scrapper.HTTP_CLIENT.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        System.out.println("Response: " + response.body().string());
                    } else {
                        System.out.println("Failed: " + response.code());
                    }
                }
            }
        } else {
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
    }

    @Override
    public boolean IsWorking(URL url) {
        return super.IsWorking(url);
    }

    private void ParseResponse(String json, int pageNumber) {
        try {
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
            com.google.gson.stream.JsonReader reader = new com.google.gson.stream.JsonReader(new StringReader(json));
            reader.setLenient(true);

            JsonObject jsonObject = parser.parse(reader).getAsJsonObject();
            if (!jsonObject.has("items")) {
                logger.error("No items found in response");
                return;
            }

            JsonArray items = jsonObject.getAsJsonObject("items").getAsJsonArray("items");
            logger.info("Processing page {} with {} items", pageNumber, items.size());

            System.out.println("\n--- Page " + pageNumber + " Results ---");

            for (var item : items) {
                JsonObject itemObj = item.getAsJsonObject().getAsJsonObject("item_basic");

                String name = itemObj.get("name").getAsString();
                long price = itemObj.get("price").getAsLong() / 100000; // Shopee stores price in cents
                int sold = itemObj.get("historical_sold").getAsInt();
                String itemId = itemObj.get("itemid").getAsString();
                String shopId = itemObj.get("shopid").getAsString();

                String productUrl = String.format("https://shopee.tw/product/%s/%s", shopId, itemId);

                logger.info(
                        "Name: %s | Price: %d TWD | Sold: %d | URL: %s%n",
                        name, price, sold, productUrl);
            }
        } catch (Exception e) {
            logger.error("Failed to parse response: {}", e.getMessage());
        }
    }

}
