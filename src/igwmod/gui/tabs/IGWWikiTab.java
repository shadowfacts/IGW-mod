package igwmod.gui.tabs;

import igwmod.ConfigHandler;
import igwmod.gui.GuiWiki;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class IGWWikiTab extends BaseWikiTab{

    public IGWWikiTab(){
        pageEntries.add("intro");
        pageEntries.add("for_servers");
        if(ConfigHandler.debugMode) {
            pageEntries.add("dev_intro");
            pageEntries.add("dev_item_and_block");
            pageEntries.add("dev_page_commands");
            pageEntries.add("dev_for_modders");
        }
    }

    @Override
    public String getName(){
        return "igwmod.wikitab.igwmod.name";
    }

    @Override
    public ItemStack renderTabIcon(GuiWiki gui){
        return new ItemStack(Blocks.LOG);
    }

    @Override
    protected String getPageName(String pageEntry){
        return I18n.format("igwtab.entry." + pageEntry);
    }

    @Override
    protected String getPageLocation(String pageEntry){
        return "igwmod:igwtab/" + pageEntry;
    }
}
