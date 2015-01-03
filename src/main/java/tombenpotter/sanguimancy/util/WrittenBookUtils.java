package tombenpotter.sanguimancy.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;

public class WrittenBookUtils {

    public static ItemStack createFirstLoginBook() {
        ItemStack book = new ItemStack(Items.written_book);
        book.setTagInfo("author", new NBTTagString("Tombenpotter"));
        book.setTagInfo("title", new NBTTagString("Welcome to Sanguimancy"));
        NBTTagCompound nbttagcompound = book.getTagCompound();
        NBTTagList bookPages = new NBTTagList();
        for (String s : RandomUtils.parseString(StatCollector.translateToLocal("info.Sanguimancy.books.first.login"), 240)) {
            bookPages.appendTag(new NBTTagString(s));
        }
        nbttagcompound.setTag("pages", bookPages);
        return book;
    }
}
