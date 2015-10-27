package net.hoyoung;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/10/27.
 */
public class TestParseProductList {
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
        String str = FileUtils.readFileToString(new File("data/comment.html"));
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
    }


}
