package search.spider;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageSpider {

	protected String storePath;

	public PageSpider(String storePath)	{
		this.storePath = storePath;
		File storeDir = new File(storePath);
		storeDir.mkdir();
	}
	/*
	 * Spiders pages using breadth first search of links and stores the documents
	 * in storePath
	 */
	public void breadthFirstSpider(String url, int maxPages)	{
		try {
			int pagesSpidered = 0;
			Document startPage = Jsoup.connect(url).get();
			index("DOC" + pagesSpidered, startPage.toString());
			Set<String> alreadyIndexed = new HashSet<String>();
			alreadyIndexed.add(url);
			Elements links = startPage.select("a[href]");
			List<String> urlsToVisit = new LinkedList<String>();
			for (Element link : links)	{
				urlsToVisit.add(link.attr("abs:href"));
			}
			pagesSpidered++;
			while (pagesSpidered < maxPages)	{
				String nextUrl = urlsToVisit.remove(0);
				if (!alreadyIndexed.contains(nextUrl)) {
					processPage(nextUrl, "DOC" + pagesSpidered, urlsToVisit);
					pagesSpidered++;
					alreadyIndexed.add(nextUrl);
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	protected void processPage(String url, String docName, List<String> urlsToVisit)	{
		System.out.println("Processing page: " + url);
		try {
			Document page = Jsoup.connect(url).get();
			index(docName, page.toString());
			Elements links = page.select("a[href]");
			for (Element link : links)	{
				urlsToVisit.add(link.attr("abs:href"));
			}
		} catch (IOException e)	{
			e.printStackTrace();
		}
	}

	private void index(String docName, String document)	{
		try {
			FileWriter writer = new FileWriter(new File(storePath + "\\" + docName));
			BufferedWriter bufWriter = new BufferedWriter(writer);
			bufWriter.write(document);
			bufWriter.close();
		} catch (IOException e)	{
			e.printStackTrace();
		}
	}

}
