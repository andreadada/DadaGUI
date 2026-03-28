package me.mrbast.dadagui.ingredient;

import me.mrbast.dadagui.GUIContext;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.function.Supplier;

public class WrapperGUIIngredient implements IGUIIngredient{


    private Supplier<AbstractItem> supplier;


    public WrapperGUIIngredient(Supplier<AbstractItem> supplier) {
        this.supplier = supplier;
    }

    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public Supplier<AbstractItem> getItemBuilder(GUIContext context) {
        return supplier;
    }

    @Override
    public void add(Character c, Gui.Builder<?, ?> gui, GUIContext context) {
        gui.addIngredient(c, this.getItemBuilder(context));
    }
}
