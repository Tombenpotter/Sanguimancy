package tombenpotter.sanguimancy.api.bloodutils.api.compact;

import tombenpotter.sanguimancy.api.bloodutils.api.entries.IEntry;

import java.util.ArrayList;

public class Entry {
    public IEntry[] entry;
    public String name;
    public int indexPage;

    public Entry(IEntry[] entry, String name, int indexPage) {
        this.entry = entry;
        this.name = name;
        this.indexPage = indexPage - 1;
    }

    public Entry(ArrayList<IEntry> entry, String name, int indexPage) {
        this.entry = entry.toArray(new IEntry[entry.size()]);
        this.name = name;
        this.indexPage = indexPage - 1;
    }
}