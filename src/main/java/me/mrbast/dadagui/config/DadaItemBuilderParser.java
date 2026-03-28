package me.mrbast.dadagui.config;

import me.mrbast.dadaconfig.logic.ConfigSection;
import me.mrbast.dadaconfig.logic.ConfigurationParser;
import me.mrbast.dadaconfig.logic.Parameters;
import me.mrbast.dadagui.DadaGUI;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import me.mrbast.dadagui.itembuilder.MetaItemBuilder;
import me.mrbast.platform.Platform;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import xyz.xenondevs.invui.item.builder.AbstractItemBuilder;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.*;
import java.util.function.Supplier;

public class DadaItemBuilderParser implements ConfigurationParser<DadaItemBuilder> {

    private Platform platform;

    public DadaItemBuilderParser(Platform platform) {
        this.platform = platform;
    }
    public DadaItemBuilderParser() {
        this.platform = DadaGUI.getPlatform();
    }


    @Override
    public Optional<DadaItemBuilder> read(ConfigSection section, String path, Parameters parameters) {


        Optional<String> flag_ = section.readString("flag");

        Supplier<AbstractItemBuilder<ItemBuilder>> builder = () -> section.read(Material.class, "material").map(material -> {


            switch (material) {

                case POTION: {
                    return section.readString( "type").map(type-> new MetaItemBuilder(material, (itemStack -> {
                        PotionType potionType = PotionType.valueOf(type);
                        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                        Objects.requireNonNull(potionMeta).setBasePotionData(new PotionData(potionType));
                        Objects.requireNonNull(itemStack).setItemMeta(potionMeta);
                    }))).orElse(null);
                }


            }

            return new ItemBuilder(material);
        }).orElse(null);



        AbstractItemBuilder<ItemBuilder> itemBuilder = builder.get();
        if(itemBuilder != null && flag_.isPresent()) {

            switch (flag_.get()){

                case "hide_all": {
                    itemBuilder.setItemFlags(new ArrayList<>(Arrays.asList(ItemFlag.HIDE_ATTRIBUTES,
                            ItemFlag.HIDE_ENCHANTS,
                            ItemFlag.HIDE_POTION_EFFECTS,
                            ItemFlag.HIDE_ATTRIBUTES,
                            ItemFlag.HIDE_UNBREAKABLE,
                            ItemFlag.HIDE_DESTROYS)));
                }break;
                default:{
                    String[] pieces = flag_.get().split(", ");
                    itemBuilder.setItemFlags(Arrays.stream(pieces).map(ItemFlag::valueOf).toList());
                }break;


            }

            builder = () -> itemBuilder;


        }

        DadaItemBuilder parser = new DadaItemBuilder(builder);
        section.readString( "name").ifPresent(name -> {
            //parser.addConsumer((consumedBuilder) -> consumedBuilder.setDisplayName(platform.format(name)));
            parser.setDisplayName(platform.format(name));
        });
        section.readString("lore").ifPresent(lore->{
            String[] split = platform.format(lore).split("\r?\n");
            //parser.addConsumer((consumedBuilder) -> consumedBuilder.setLegacyLore(List.of(split)));
            parser.setLore(platform.format(lore));
        });
        return Optional.of(parser);


    }

    @Override
    public void write(ConfigSection configuration, String path, DadaItemBuilder object) {

    }


}
