package net.hoyoung;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class TestParseProductList {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void test() throws IOException {
//        String str = FileUtils.readFileToString(new File("data/product_list.html","UTF-8"));
        String str = FileUtils.readFileToString(new File("data/product_list.html"));
        Html html = new Html(str);

        List<Selectable> list = html.xpath("//div[@id=zg_nonCritical]/div").nodes();
        for(Selectable sele : list){
            String name = sele.xpath("//div[@class=zg_title]/a/text()").get();
            String detail_url = sele.xpath("//div[@class=zg_title]/a/@href").get();
            String comment_url = sele.xpath("//a[@class=a-link-normal]/@href").get();
            Date create_time = new Date();
        }

    }
    Pattern starPattern = Pattern.compile("(\\d\\.\\d) 颗星，最多 5 颗星");
    @Test
    public void test2() throws IOException {
        String str = FileUtils.readFileToString(new File("data/product_comment.html"));
        Html html = new Html(str);

        String t_star_str = html.xpath("//div[@class=averageStarRatingIconAndCount]//span").get();
        Matcher m = starPattern.matcher(t_star_str);
        float total_star = 0;//总星星数
        if(m.find()){
            total_star = Float.valueOf(m.group(1));
        }
        String t_totalComment_str = html.xpath("//span[@class=totalReviewCount]/text()").get().replace(",","");
        int total_comment = Integer.valueOf(t_totalComment_str);
        int[] star_details = new int[5];
        List<Selectable> list = html.xpath("//table[@id=histogramTable]/tbody/tr").nodes();
        for (int i=0;i<star_details.length&&i<list.size();i++){
            Selectable sele = list.get(i);

            Selectable an = sele.xpath("/tr/td[3]/a/text()");
            if(an.get()==null){
                continue;
            }
            String t_total = an.get().replace(",", "");
            star_details[i] = Integer.valueOf(t_total);
        }

        for (int i = 0; i < star_details.length; i++) {
            System.out.println(star_details[i]);
        }
        String asin = null;
        String t_asin = html.xpath("//span[@class=writeReviewButton]//a/@href").get();
        m = Pattern.compile("asin=(\\w+)").matcher(t_asin);
        if(m.find()){
            asin = m.group(1);
        }
        System.out.println(asin);
    }
    @Test
    public void test3() throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        String s = FileUtils.readFileToString(new File("data/comment.html"));
        String[] arr = s.split("\n");
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
            }
        }
        Html html = new Html(sb.toString());
        List<Selectable> nodes = html.xpath("//div[@class=review]").nodes();
        for (Selectable sele : nodes){
            String t_review_votes = sele.xpath("//span[@class=review-votes]/text()").get().replace(",","");
            Matcher m = votesRegx.matcher(t_review_votes);
            int review_votes = 0;
            int helpful_votes = 0;
            if(m.find()){
                review_votes = Integer.valueOf(m.group(1));
                helpful_votes = Integer.valueOf(m.group(2));
            }

            float star = 0f;
            String t_star = sele.xpath("//i[@class=a-icon-star]/span/text()").get();
            m = starRegx.matcher(t_star);
            if(m.find()){
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
            if(StringUtils.isEmpty(thread_id)){
                thread_id = null;
            }
            String review_text = sele.xpath("//span[@class=review-text]/text()").get();
            System.out.println(review_text);
            System.out.println("----------------------------------------");
        }
        System.out.println(html.xpath("//li[@class=a-last]//a").get());
    }
    Pattern votesRegx = Pattern.compile("(\\d+) 人中有 (\\d+) 人认为以下评论非常有用");
    Pattern starRegx = Pattern.compile("(\\d\\.?\\d*) 颗星，最多 5 颗星");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日");
    @Test
    public void test4() throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        String s = FileUtils.readFileToString(new File("data/comment_end.html"));
        String[] arr = s.split("\n");
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
            }
        }
        Html html = new Html(sb.toString());

        System.out.println(html.xpath("//li[@class=a-last]/a").get()==null);
    }
}
