package ccnu.computer.util;

import java.io.IOException;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ccnu.computer.crawler.Fetch;
import ccnu.computer.crawler.Parser;

public class Test {
	private Fetch fetch;
	
	public Fetch getFetch() {
		return fetch;
	}
	@Resource
	public void setFetch(Fetch fetch) {
		this.fetch = fetch;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Test().start();
	}

	
	public void start(){
		this.setFetch(new Fetch());
		fetch.setT("华为中兴出深圳");
		fetch.setSelect("凤凰新闻");
		new Thread(fetch).start();
	}
	public static Document getDocs(String url) {
		Document doc = null;
		try {
			doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.1.141.64 Safari/537.31")
					.timeout(100000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
		return doc;
	}
}
