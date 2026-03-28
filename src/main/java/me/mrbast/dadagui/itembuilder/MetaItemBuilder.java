package me.mrbast.dadagui.itembuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.builder.AbstractItemBuilder;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class MetaItemBuilder extends AbstractItemBuilder<ItemBuilder> {

    public static interface Meta{
        void set(ItemStack itemStack);
    }


    private Material material;
    private Meta meta;



    public MetaItemBuilder(@NotNull Material material) {
        super(material);
    }

    public MetaItemBuilder(Material material, Meta object) {
        super(material);
        this.meta = object;

    }

    @Override
    public @NotNull ItemStack get() {
        return get(null);
    }

    @Override
    public @NotNull ItemStack get(String lang) {
        ItemStack itemStack =  super.get(lang);
        meta.set(itemStack);
        return itemStack;


    }
}