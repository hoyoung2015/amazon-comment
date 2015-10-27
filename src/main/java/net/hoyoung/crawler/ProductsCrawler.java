package net.hoyoung.crawler;

import net.hoyoung.utils.JDBCHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class ProductsCrawler implements PageProcessor{

    JdbcTemplate jdbcTemplate;

    public ProductsCrawler() {
        jdbcTemplate = JDBCHelper.createMysqlTemplate("mysql1",
                "jdbc:mysql://localhost/amazon?useUnicode=true&characterEncoding=utf8",
                "root", "", 5, 30);
    }

    public void process(Page page) {
        List<Selectable> list = page.getHtml().xpath("//div[@id=zg_nonCritical]/div").nodes();
        for(Selectable sele : list){
            String name = sele.xpath("//div[@class=zg_title]/a/text()").get();
            String detail_url = sele.xpath("//div[@class=zg_title]/a/@href").get();
            String comment_url = sele.xpath("//a[@class=a-link-normal]/@href").get();
            Date create_time = new Date();

            int status = jdbcTemplate.update("INSERT INTO products(name,detail_url,comment_url,create_time) VALUES (?,?,?,?)",
                    name,
                    detail_url,
                    comment_url,
                    create_time);
            if(status==1){
                System.out.println(name+" insert successful");
            }
        }
    }

    private Site site = Site.me()
            .setRetryTimes(5)
            .setSleepTime(300)
            .addHeader("Host", "www.amazon.cn")
            .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "http://www.amazon.cn/gp/bestsellers/books/ref=s9_acsd_ri_bw_clnk?pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=merchandised-search-5&pf_rd_r=1EYQ7KC6MY0KF0S415YS&pf_rd_t=101&pf_rd_p=249960232&pf_rd_i=658390051";
        Spider.create(new ProductsCrawler())
                .addUrl(url)
                .thread(1)
                .run();
    }
}
