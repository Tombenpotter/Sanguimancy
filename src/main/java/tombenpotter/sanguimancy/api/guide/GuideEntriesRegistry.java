package tombenpotter.sanguimancy.api.guide;

import java.util.ArrayList;
import java.util.HashMap;

public class GuideEntriesRegistry {

    public static ArrayList<Tab> tabList = new ArrayList<Tab>();
    public static HashMap<Tab, ArrayList<Page>> tabMap = new HashMap<Tab, ArrayList<Page>>();

    public static void registerTab(Tab tab) {
        tabList.add(tab);
    }

    public static void registerPage(Tab tab, Page page) {
        if (tabMap.get(tab) == null) tabMap.put(tab, new ArrayList<Page>());
        tabMap.get(tab).add(page);
    }
}
