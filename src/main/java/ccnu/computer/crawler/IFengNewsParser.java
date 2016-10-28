package ccnu.computer.crawler;

import java.util.Arrays;
import java.util.Set;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import ccnu.computer.model.Text;
import ccnu.computer.summary.HyperGraph;
import ccnu.computer.util.CrawlerUtil;

/*
 * 对于一个新闻网页，爬取相关内容分为以下几步：
 * （1）首先是拼凑一个搜索网页的网址，例如："http://search.sina.com.cn/?q="+ URLEncoder.encode(title, "utf-8")+"&c=news&from=channel&ie=utf-8";
 * （2）其次是获取第一步获取了多少个记录，一般换算成页来计算，方便于下一步数据 的爬取
 * （3）通过第二步获取的页数来拼成多个网址
 * （4）获取第三步中每一个网址上的新闻链接
 * （5）通过第四部的新闻链接网址获取新闻的内容
 * 
 * 
 * */

@Component("iFengNewsParser")
public class IFengNewsParser extends AbstractNewsParser {

//http://news.sogou.com/news?query=site:ifeng.com%20%D1%CE%B3%C7%D4%E2%C1%FA%BE%ED%B7%E7%B1%F9%B1%A2&manual=true&mode=1&sort=0&p=42230302
//http://news.sogou.com/news?query=site:qq.com%20%BB%AA%CE%AA%D6%D0%D0%CB%B3%F6%C9%EE%DB%DA&manual=true&mode=1&sort=0&p=42230302
	@Override
	public String getUrl(String title){
		return getUrl(title,"http://news.sogou.com/news?query=site:ifeng.com ","&manual=true&mode=1&sort=0&p=42230302");
	}
//http://news.sogou.com/news?mode=1&manual=true&query=site%3Aifeng.com+%D1%CE%B3%C7%D4%E2%C1%FA%BE%ED%B7%E7%B1%F9%B1%A2&sort=0&page=2&p=42230302&dp=1
//http://news.sogou.com/news?mode=1&manual=true&query=site%3Aqq.com+%BB%AA%CE%AA%D6%D0%D0%CB%B3%F6%C9%EE%DB%DA&sort=0&page=1&p=42230302&dp=1
	@Override
	public String getUrl(String title,int page){
		return getUrl(title,page,"http://news.sogou.com/news?mode=1&manual=true&query=site%3Aifeng.com+",
		        		"&sort=0&page=","&p=42230302&dp=1");
	}

	@Override
	public int getUrlsize(String url) {
		// TODO Auto-generated method stub
		return getUrlsize(url, "span.filt-result");
	}

	@Override
	public Set<String> getItemUrl(String url) {
		// TODO Auto-generated method stub
		return getItemUrl(url, ".*","h3.vrTitle");
	}
	@Override
	public void parse(Document doc, String url,String topic) {
		// TODO Auto-generated method stub
		Text text= new Text();
		String title = CrawlerUtil.getTitle(doc, "div#artical");
		text.setUrl(url);
		text.setTopic(topic);
		text.setTitle(title);
		System.out.println(title);
		String time = CrawlerUtil.getTime(doc, Arrays.asList(new String[]{"span.ss01","artInfo"}));
		text.setDate(time);
		System.out.println(time);
		String content = CrawlerUtil.getContent(doc, Arrays.asList(new String[]{"div#main_content","div.js_selection_area"}));
		text.setContent(content);
		System.out.println(content);
		text.setIsLabel("未标记");
		//text.setSummary(HyperGraph.getSummary(content, 200));
		if(!"".equals(content)){
			textDao.add(text);
		}
		text=null;
	}
}
