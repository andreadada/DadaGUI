package me.mrbast.dadagui.paged;


import me.mrbast.dadagui.DadaGUI;
import me.mrbast.platform.Platform;
import me.mrbast.platform.format.Format;
import me.mrbast.platform.util.LoreUtil;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.AbstractItemBuilder;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

import java.util.List;

public class ForwardItem extends PageItem {

    private Platform platform;

    private AbstractItemBuilder<ItemBuilder> wrapper;

    private String displayName;
    private String lore;
    private String maxReached;


    public ForwardItem(AbstractItemBuilder<ItemBuilder> builder, String displayName, String lore, String maxReached) {
        super(true);
        this.wrapper = builder;
        this.displayName = displayName;
        this.lore = lore;
        this.maxReached = maxReached;
        this.platform = DadaGUI.getPlatform();
    }
    public ForwardItem(Platform platform, AbstractItemBuilder<ItemBuilder> builder, String displayName, String lore, String maxReached) {
        super(true);
        this.wrapper = builder;
        this.displayName = displayName;
        this.lore = lore;
        this.maxReached = maxReached;
        this.platform = platform;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> gui) {

        if(displayName != null) wrapper.setDisplayName(platform.getFormatter(displayName).format(Format.of("maxpage", "currentpage").as(String.valueOf(gui.getPageAmount()), String.valueOf(gui.getCurrentPage()))));
        List<String> loreList = gui.hasNextPage() && lore != null ?
                LoreUtil.toLore(platform.getFormatter(lore).format(Format.of("maxpage", "currentpage").as(String.valueOf(gui.getPageAmount()), String.valueOf(gui.getCurrentPage()+1))))
                : LoreUtil.toLore(platform.getFormatter(maxReached).getClean());
        wrapper.setLegacyLore(loreList);

        return wrapper;
    }

}