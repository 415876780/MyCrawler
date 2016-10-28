package ccnu.computer.crawler;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import ccnu.computer.util.BeanUtil;
@Component
public class Fetch implements Runnable {
	private String starturl;
	private String t;
    private ExecutorService executorService;
    private String select;
    private CountDownLatch countDownLatch;
    
    
    public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public void setT(String t) {
		this.t = t;
	}
	public Fetch()  {
    	this.executorService = Executors.newFixedThreadPool(100);
    	
    }
    
    private Parser parse;
    
    public Parser getParse() {
		return parse;
	}
    
   
    // @Resource(name="sinaNewsParser")
    //@Resource(name="weiBoParser")
	public void setParse(Parser parse) {
		this.parse = parse;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		if("新浪新闻".equals(select)){
			parse=(Parser) BeanUtil.getBean("sinaNewsParser");
		}
		if("新浪微博".equals(select)){
			parse=(Parser) BeanUtil.getBean("weiboParser");
		}
		if("腾讯新闻".equals(select)){
			parse=(Parser) BeanUtil.getBean("tencentNewsParser");
		}
		if("凤凰新闻".equals(select)){
			parse=(Parser) BeanUtil.getBean("iFengNewsParser");
		}
		
		starturl =parse.getUrl(t);
    	int pagesize=parse.getUrlsize(starturl);
    	System.out.println("获取："+pagesize+"页");
    	System.out.println("爬取："+starturl);
        countDownLatch = new CountDownLatch(pagesize);
        
        for(int i=1;i<=1;i++) {
        	String u=parse.getUrl(t, i);
        	System.out.println("正在获取："+u);
        	  try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
            executorService.execute(new FetchItem(u));
        }
	}

/*	public void fetch(String t) throws UnsupportedEncodingException {
    	starturl =parse.getUrl(t);
    	int pagesize=parse.getUrlsize(starturl);
    	System.out.println("获取："+pagesize+"页");
    	System.out.println("爬取："+starturl);
        countDownLatch = new CountDownLatch(pagesize);
        for(int i=1;i<=1;i++) {
        	String u=parse.getUrl(t, i);
        	System.out.println("正在获取："+u);
            executorService.execute(new FetchItem(u));
        }
        try {
            countDownLatch.await();
            System.out.println("爬取完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private class FetchItem implements Runnable {
    	private String u;
    	private Set<String> urls;
    	public FetchItem(String u){
    		this.u=u;
    	}
        public void run() {
            try {
            	urls=parse.getItemUrl(u);
            	for(String url:urls){
            		Document doc= parse.getDocs(url);
            		parse.parse(doc, url,t);
            	}
            } finally {
                countDownLatch.countDown();
            }
        }
    }


}