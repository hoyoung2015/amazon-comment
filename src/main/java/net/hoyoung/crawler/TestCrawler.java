package net.hoyoung.crawler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.hoyoung.utils.JDBCHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class TestCrawler implements PageProcessor{
    Logger logger = LoggerFactory.getLogger(this.getClass());
//    AtomicInteger atomicPage = new AtomicInteger();
    static JdbcTemplate jdbcTemplate;
    static {
        jdbcTemplate = JDBCHelper.createMysqlTemplate("mysql1",
                "jdbc:mysql://localhost/amazon?useUnicode=true&characterEncoding=utf8",
                "root", "", 5, 30);
        jdbcTemplate.execute("SET NAMES 'utf8mb4'");
    }
    public TestCrawler() {

    }
    Pattern votesRegx = Pattern.compile("(\\d+) 人中有 (\\d+) 人认为以下评论非常有用");
    Pattern starRegx = Pattern.compile("(\\d\\.?\\d*) 颗星，最多 5 颗星");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日");
    public void process(Page page) {
//        System.out.println(page.getRawText());
//        if(1==1) return;
        ObjectMapper jsonParser = new ObjectMapper();
        String[] arr = page.getRawText().split("\n");
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < arr.length-2; i++) {
            try {
                List<String> t = jsonParser.readValue(arr[i], new TypeReference<List<String>>() {
                });
                if(t.size()==3){
                    sb.append(t.get(2));
                }
            }catch (JsonMappingException e){
                logger.warn("JsonMappingException");
            }catch (JsonParseException e){
                logger.warn("JsonParseException");
            }catch (IOException e){
                logger.warn("jsonParser IOException");
            }
        }
        Html html = new Html(sb.toString());
        List<Selectable> nodes = html.xpath("//div[@class=review]").nodes();
        int pid = (Integer)page.getRequest().getExtra("pid");
        String product_name = (String) page.getRequest().getExtra("product_name");
        String asin = (String) page.getRequest().getExtra("asin");
        Matcher m;
        for (Selectable sele : nodes) {
            String t_review_votes = sele.xpath("//span[@class=review-votes]/text()").get();
            int review_votes = 0;
            int helpful_votes = 0;
            if(t_review_votes != null){
                t_review_votes = t_review_votes.replace(",", "");
                m = votesRegx.matcher(t_review_votes);
                if (m.find()) {
                    review_votes = Integer.valueOf(m.group(1));
                    helpful_votes = Integer.valueOf(m.group(2));
                }
            }
            float star = 0f;
            String t_star = sele.xpath("//i[@class=a-icon-star]/span/text()").get();
            m = starRegx.matcher(t_star);
            if (m.find()) {
                star = Float.valueOf(m.group(1));
            }
            String review_title = sele.xpath("//a[@class=review-title]/text()").get();
            Date review_date = null;
            try {
                review_date = simpleDateFormat.parse(sele.xpath("//span[@class=review-date]/text()").get().replace("于 ", ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String review_author = sele.xpath("//a[@class=author]/text()").get();
            String review_author_url = sele.xpath("//a[@class=author]/@href").get();

            //获取子评论id，如果后续需要采集，这个有用
            String thread_id = sele.xpath("//div[@class='a-section a-spacing-none review-comments']/@id").get();
            if (StringUtils.isEmpty(thread_id)) {
                thread_id = null;
            }
            String review_text = sele.xpath("//span[@class=review-text]/text()").get();
//            try {
//                review_text = new String(review_text.getBytes(),"gbk");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

            int status = jdbcTemplate.update("INSERT INTO comments(pid,product_name,review_votes,helpful_votes,star,review_title,review_date,review_author,review_author_url,thread_id,review_text,create_time) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                    pid,
                    product_name,
                    review_votes,
                    helpful_votes,
                    star,
                    review_title,
                    review_date,
                    review_author,
                    review_author_url,
                    thread_id,
                    review_text,
                    new Date());
            if(status==1){
                logger.info("comment insert successful");
            }

        }
        jdbcTemplate.update("INSERT INTO fetched_url(url,create_time) VALUES (?,?)",
                page.getRequest().getUrl(),
                new Date());
        logger.info("fetched_url:"+page.getRequest().getUrl());
        //检查是否有下一页
        if(html.xpath("//li[@class=a-last]/a").get() != null){
            int pageNum = (Integer)page.getRequest().getExtra("pageNum")+1;
            logger.info("go to page "+pageNum);
            page.addTargetRequest(getNextPageRequest(pid,product_name,asin,pageNum));
        }else {
            int status = jdbcTemplate.update("UPDATE products SET status=2 WHERE id=?",pid);
            if(status==1){
                System.out.println("product "+pid+" status update successful");
            }
        }
    }
    private Request getNextPageRequest(int pid,String product_name,String asin,int pageNum){
        Request req = new Request("http://www.amazon.cn/ss/customer-reviews/ajax/reviews/get/ref=cm_cr_pr_btm_link_2?r="+System.currentTimeMillis());
        req.putExtra("pid",pid);
        req.putExtra("product_name",product_name);
        req.putExtra("pageNum",pageNum);
        req.putExtra("asin",asin);

        req.setMethod(HttpConstant.Method.POST);
        List<NameValuePair> postDatas = new ArrayList<NameValuePair>();
        postDatas.add(new BasicNameValuePair("reviewerType", "all_reviews"));
        postDatas.add(new BasicNameValuePair("pageNumber", String.valueOf(pageNum)));
        postDatas.add(new BasicNameValuePair("reftag", "cm_cr_pr_btm_link_2"));
        postDatas.add(new BasicNameValuePair("deviceType", "desktop"));//not required
        postDatas.add(new BasicNameValuePair("pageSize", "50"));//最多50条
        postDatas.add(new BasicNameValuePair("asin", asin));//required 这个值在商品链接中可提取到
        postDatas.add(new BasicNameValuePair("scope", "reviewsAjax5"));//not required
        req.putExtra("nameValuePair",postDatas.toArray(new NameValuePair[postDatas.size()]));
        return req;
    }
    private Site site = Site.me()
            .setRetryTimes(5)
            .setSleepTime(2000)
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
//        int pid = 10;
//        String product_name = "棚车少年·第3辑(9-12)(中英双语)(套装共8册)(附CD)(当孩子遇到挫折，这套书能让他们笑对人生)";
//        String asin = "B00W8Y1410";
        Map<String, Object> pro = jdbcTemplate.queryForMap("SELECT id,name,asin FROM products WHERE id=?",18);

        int pid = (Integer)pro.get("id");
        String product_name = (String) pro.get("name");
        String asin = (String) pro.get("asin");

        int status = jdbcTemplate.queryForObject("SELECT status FROM products WHERE id=?",Integer.class,pid);
        if(status>=2){
            System.err.println("product "+pid+" has fetched comments");
            System.exit(0);
        }
        TestCrawler testCrawler = new TestCrawler();
        RandomSleepSpider.create(new TestCrawler())
                .addRequest(testCrawler.getNextPageRequest(pid,product_name,asin,1))
                .thread(1)
                .run();
    }
}
