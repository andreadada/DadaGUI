package me.mrbast.dadagui.itembuilder;

import xyz.xenondevs.invui.item.builder.AbstractItemBuilder;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class DadaItemBuilder implements Cloneable {

    private Supplier<AbstractItemBuilder<ItemBuilder>> builder;
    private Set<Consumer<AbstractItemBuilder<ItemBuilder>>> listeners = new HashSet<>();
    private String lore;
    private String displayName;

    public DadaItemBuilder(Supplier<AbstractItemBuilder<ItemBuilder>> builder) {
        this.builder = builder;
    }

    public void addConsumer(Consumer<AbstractItemBuilder<ItemBuilder>> consumer) {
        listeners.add(consumer);
    }

    public Supplier<AbstractItemBuilder<ItemBuilder>> getSupplier() {
        return builder;
    }

    public AbstractItemBuilder<ItemBuilder> getBuilder(){

        AbstractItemBuilder<ItemBuilder> s = builder.get();
        listeners.forEach(consumer -> consumer.accept(s));
        s.setDisplayName(displayName);
        String[] split = lore.split("\r?\n");
        s.setLegacyLore(List.of(split));
        return s;

    }

    @Override
    public DadaItemBuilder clone() {
        try {
            DadaItemBuilder clone = (DadaItemBuilder) super.clone();
            this.builder = () -> this.builder.get();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "DadaItemBuilder{" +
                "builder=" + builder +
                ", listeners=" + listeners +
                ", lore='" + lore + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
