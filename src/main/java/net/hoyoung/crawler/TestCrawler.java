package net.hoyoung.crawler;

import net.hoyoung.utils.JDBCHelper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class TestCrawler implements PageProcessor{

    static JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = JDBCHelper.createMysqlTemplate("mysql1",
                "jdbc:mysql://localhost/amazon?useUnicode=true&characterEncoding=utf8",
                "root", "", 5, 30);
    }
    public TestCrawler() {

    }
    public void process(Page page) {
        System.out.println(page.getRawText());
    }
    private Site site = Site.me()
            .setRetryTimes(5)
            .setSleepTime(300)
            .addHeader("Host", "www.amazon.cn")
            .addHeader("Origin", "http://www.amazon.cn")
            .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new TestCrawler());
        Request req = new Request("http://www.amazon.cn/ss/customer-reviews/ajax/reviews/get/ref=cm_cr_pr_btm_link_5");
        req.setMethod(HttpConstant.Method.POST);
        List<NameValuePair> postDatas = new ArrayList<NameValuePair>();
        postDatas.add(new BasicNameValuePair("reviewerType", "all_reviews"));
        postDatas.add(new BasicNameValuePair("pageNumber", "5"));
        postDatas.add(new BasicNameValuePair("reftag", "cm_cr_pr_btm_link_5"));
        postDatas.add(new BasicNameValuePair("deviceType", "desktop"));//not required
        postDatas.add(new BasicNameValuePair("pageSize", "10"));
        postDatas.add(new BasicNameValuePair("asin", "B008AGHPM2"));//required 这个值在商品链接中可提取到
        postDatas.add(new BasicNameValuePair("scope", "reviewsAjax5"));//not required
        req.putExtra("nameValuePair",postDatas.toArray(new NameValuePair[postDatas.size()]));
        spider.addRequest(req).thread(1).run();
    }
}
