package ch02DataAcquisiotion.wikipedia;

import info.bliki.api.Page;
import info.bliki.api.User;
import info.bliki.wiki.filter.SectionHeader;
import info.bliki.wiki.model.ITableOfContent;
import info.bliki.wiki.model.Reference;
import info.bliki.wiki.model.WikiModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Aspire on 12.06.2017.
 */
public class Handler {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/template.properties"));

        User user = new User((String)prop.get("wikiName"),
                (String)prop.get("wikiPassword"),
                "https://en.wikipedia.org/w/api.php");

        user.login();

        String[]titles = {"Data science"};
        List<Page>pageList = user.queryContent(titles);

        for(Page page: pageList){
            WikiModel wikiModel = new WikiModel("${image}", "${title}");

            String htmlText = wikiModel.render(page.toString());

            System.out.println(htmlText);

            System.out.println("Title: " + page.getTitle()
                    + "\nPage ID: " + page.getPageid()
                    + "\nTimestamp: " + page.getCurrentRevision().getTimestamp());

            List<Reference>referenceList = wikiModel.getReferences();
            if(referenceList!=null) {
                System.out.println("Number of references: " + referenceList.size());

                for (Reference reference : referenceList) {
                    System.out.println(reference.getRefString());
                }
            }

            ITableOfContent toc = wikiModel.getTableOfContent();
            if(toc!=null) {
                List<SectionHeader> sections = toc.getSectionHeaders();
                for (SectionHeader sh : sections) {
                    System.out.println(sh.getFirst());
                }
            }
        }
    }
}
