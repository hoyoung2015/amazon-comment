package us.codecraft.webmagic;

import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2015/10/28.
 */
public class RandomSleepSpider extends Spider{

    public RandomSleepSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }
    public static Spider create(PageProcessor pageProcessor){
        return new RandomSleepSpider(pageProcessor);
    }
    @Override
    protected void sleep(int time) {
        time += 50;
        time = (int) Math.round(Math.random()*time);
        logger.info("spider sleep "+time+" ms");
        try {
            Thread.sleep((long)time);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }
    }
}
