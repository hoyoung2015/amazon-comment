package net.hoyoung;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.RandomSleepSpider;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2015/10/28.
 */
public class TestRandomCrawler implements PageProcessor{
    @Override
    public void process(Page page) {
        System.out.println(page.getRequest().getUrl());
    }
    private Site site = Site.me()
            .setRetryTimes(5)
            .setSleepTime(2000)
            .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");

    @Override
    public Site getSite() {
        return site;
    }
    public static void main(String[] args) {
        RandomSleepSpider.create(new TestRandomCrawler())
                .addUrl("http://news.xidian.edu.cn/list-1-1.html")
                .addUrl("http://news.xidian.edu.cn/list-1-2.html")
                .addUrl("http://news.xidian.edu.cn/list-1-3.html")
                .thread(2)
                .run();
    }
}
