package me.mrbast.dadagui.paged;


import me.mrbast.dadagui.GeneralGUI;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Marker;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class PagedGUI extends GeneralGUI {


    public PagedGUI(char c, Marker marker, String[] layout, String title) {
        super(() -> {
            return ((PagedGui.Builder)PagedGui.items().setStructure(layout)).addIngredient(c, marker);
        });
        this.title = title;
    }

    public PagedGUI(String[] layout) {
        super(()->PagedGui.items().setStructure(layout));
    }

    public PagedGUI(char c, Marker marker, String[] layout) {
        super(()->PagedGui.items().setStructure(layout).addIngredient(c, marker));

    }


    public void addContent(AbstractItem item){
        this.addConsumer(((igui, guiContext, builder) -> {
            ((PagedGui.Builder<Item>) builder).addContent(item);
        }));
    }

    public void addContent(Gui.Builder<?, ?> builder, AbstractItem item){
        ((PagedGui.Builder<Item>) builder).addContent(item);;
    }
}
