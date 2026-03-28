package me.mrbast.dadagui.ingredient;

import me.mrbast.dadagui.GUIContext;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.function.Supplier;

public class SimpleGUIIngredient implements IGUIIngredient{

    private ItemBuilder itemBuilder;

    public SimpleGUIIngredient( Material material,  String displayName, int amount) {
        this.itemBuilder = new ItemBuilder(material).setAmount(amount);
        if(displayName != null) itemBuilder.setDisplayName(displayName);

    }

    public SimpleGUIIngredient( Material material,  String displayName) {
        this( material, displayName, 1);
    }
    public SimpleGUIIngredient(Material material) {
        this( material, null, 1);
    }



    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public Supplier<AbstractItem> getItemBuilder(GUIContext context) {
        return () -> new SimpleItem(itemBuilder);
    }

    @Override
    public void add(Character c, Gui.Builder<?, ?> gui, GUIContext context) {
        gui.addIngredient(c, this.getItemBuilder(context));
    }
}
