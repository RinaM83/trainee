package api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLTokenExtractor {
    public String extractCsrfTokenFromHTML(String html) {
        Document doc = Jsoup.parse(html);
        String csrfToken = doc.select("meta[name=csrf-token]").attr("content");
        return csrfToken;
    }
}
