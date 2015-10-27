package net.hoyoung.crawler;

import net.hoyoung.utils.JDBCHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class CommentsCrawler implements PageProcessor{

    static JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = JDBCHelper.createMysqlTemplate("mysql1",
                "jdbc:mysql://localhost/amazon?useUnicode=true&characterEncoding=utf8",
                "root", "", 5, 30);
    }
    public CommentsCrawler() {

    }
    Pattern starPattern = Pattern.compile("(\\d\\.\\d) 颗星，最多 5 颗星");
    public void process(Page page) {
        String t_star_str = page.getHtml().xpath("//div[@class=averageStarRatingIconAndCount]//span").get();
        Matcher m = starPattern.matcher(t_star_str);
        float avg_star = 0;//总星星数
        if(m.find()){
            avg_star = Float.valueOf(m.group(1));
        }
        String t_totalComment_str = page.getHtml().xpath("//span[@class=totalReviewCount]/text()").get().replace(",","");
        int total_comment = Integer.valueOf(t_totalComment_str);
        int[] star_details = new int[5];
        List<Selectable> list = page.getHtml().xpath("//table[@id=histogramTable]/tbody/tr").nodes();
        for (int i=0;i<star_details.length&&i<list.size();i++){
            Selectable sele = list.get(i);

            Selectable an = sele.xpath("/tr/td[3]/a/text()");
            if(an.get()==null){
                continue;
            }
            String t_total = an.get().replace(",", "");
            star_details[i] = Integer.valueOf(t_total);
        }
        Map<String, Object> pro = (Map<String, Object>) page.getRequest().getExtra("pro");
        int status = jdbcTemplate.update("UPDATE products SET status=?,total_comment=?,avg_star=?,star5=?,star4=?,star3=?,star2=?,star1=? where id=?",
                1,
                total_comment,
                avg_star,
                star_details[0],
                star_details[1],
                star_details[2],
                star_details[3],
                star_details[4],
                pro.get("id"));
        if(status==1){
            System.out.println(pro.get("id")+" update successful");
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
        Spider spider = Spider.create(new CommentsCrawler());
        List<Map<String, Object>> products = jdbcTemplate.queryForList("SELECT id,comment_url FROM products WHERE status=0");
        for (Map<String, Object> pro : products){
            Request req = new Request((String) pro.get("comment_url"));
            req.putExtra("pro",pro);
            spider.addRequest(req);
        }
        System.out.println("add "+products.size()+" url");
        spider.thread(1).run();
    }
}
