package me.mrbast.dadagui;

import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.function.Supplier;

public class AbstractItemBuilderGUIIngredient implements IGUIIngredient {

    private char character;
    //private DadaItemBuilder dadaItemBuilder;
    private Supplier<AbstractItem> supplier;

    public AbstractItemBuilderGUIIngredient(char character) {
        this.character = character;
    }
    public AbstractItemBuilderGUIIngredient(char character, Supplier<AbstractItem> supplier) {
        this.character = character;
        this.supplier = supplier;
    }

    @Override
    public char getChar() {
        return character;
    }

    @Override
    public Supplier<AbstractItem> getItemBuilder(GUIContext context) {
        return supplier;
    }

    @Override
    public void add(Character c, Gui.Builder<?, ?> gui, GUIContext context) {
        AbstractItem s = supplier.get();
        gui.addIngredient(character, s);
    }


    public void setDadaItemBuilder(DadaItemBuilder dadaItemBuilder) {
        this.supplier = ()->new SimpleItem(dadaItemBuilder.getBuilder().get());
    }
}
