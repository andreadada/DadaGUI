package me.mrbast.dadagui.ingredient;

import me.mrbast.dadagui.GUIContext;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.function.Supplier;

public interface IGUIIngredient {

    @Deprecated
    public char getChar();
    public Supplier<AbstractItem> getItemBuilder(GUIContext context);

    void add(Character c, Gui.Builder<?, ?> gui, GUIContext context);
}
