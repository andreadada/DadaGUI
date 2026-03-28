package me.mrbast.dadagui.config;

import me.mrbast.dadaconfig.logic.ConfigSection;
import me.mrbast.dadaconfig.logic.ConfigurationParser;
import me.mrbast.dadaconfig.logic.Parameters;
import me.mrbast.dadagui.AbstractItemBuilderGUIIngredient;
import me.mrbast.dadagui.GUIContext;
import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import me.mrbast.dadagui.paged.BackItem;
import me.mrbast.dadagui.paged.ForwardItem;
import me.mrbast.platform.Platform;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.builder.AbstractItemBuilder;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.*;
import java.util.function.Supplier;

public class GUIIngredientParser implements ConfigurationParser<IGUIIngredient> {

    private Platform platform;



    public interface TypeGUIIngredientParser {

        Optional<IGUIIngredient> parse(ConfigSection section, String path, Parameters parameters);

    }
    private final Map<String, TypeGUIIngredientParser> parser = new HashMap<>();



    public GUIIngredientParser(Platform platform) {

        this.platform = platform;

        parser.put("item", (section, path, parameters) -> {

            char c = getCharacter(section);

            AbstractItemBuilderGUIIngredient dadaItemBuilderGUIIngredient = new AbstractItemBuilderGUIIngredient(c);
            section.getSection("item").ifPresent(itemSection-> {
                Optional<DadaItemBuilder> x = itemSection.read(DadaItemBuilder.class);
                x.ifPresent(dadaItemBuilderGUIIngredient::setDadaItemBuilder);
            });


            return Optional.of(dadaItemBuilderGUIIngredient);
        });


        parser.put("back", ((section, path, parameters) -> {

            char c = getCharacter(section);
            return section.getSection("item").flatMap(itemSection -> itemSection.read(DadaItemBuilder.class)).map(dadaItemBuilder -> {
                return new IGUIIngredient() {
                    @Override
                    public char getChar() {
                        return c;
                    }

                    @Override
                    public Supplier<AbstractItem> getItemBuilder(GUIContext context) {
                        return ()-> new BackItem(dadaItemBuilder.getBuilder(), dadaItemBuilder.getDisplayName(), dadaItemBuilder.getLore(), section.readString("max-reached").orElse("<red>Cannot go further"));
                    }

                    @Override
                    public void add(Character ch, Gui.Builder<?, ?> gui, GUIContext context) {
                        gui.addIngredient(c, this.getItemBuilder(context));
                    }
                };


            });
        }));
        parser.put("forward", ((section, path, parameters) -> {

            char c = getCharacter(section);
            return section.getSection("item").flatMap(itemSection -> itemSection.read(DadaItemBuilder.class)).map(dadaItemBuilder -> new IGUIIngredient() {
                @Override
                public char getChar() {
                    return c;
                }

                @Override
                public Supplier<AbstractItem> getItemBuilder(GUIContext context) {
                    AbstractItemBuilder<ItemBuilder> builder = dadaItemBuilder.getBuilder();
                    return ()-> new ForwardItem(builder,  dadaItemBuilder.getDisplayName(), dadaItemBuilder.getLore(), section.readString("max-reached").orElse("<red>Cannot go further"));
                }

                @Override
                public void add(Character ch, Gui.Builder<?, ?> gui, GUIContext context) {
                    gui.addIngredient(c, this.getItemBuilder(context));
                }
            });

        }));

    }

    @Override
    public Optional<IGUIIngredient> read(ConfigSection section, String path, Parameters parameters) {

        return section.readString("type").map(type -> {


            TypeGUIIngredientParser pars = parser.get(type.toLowerCase());
            if(pars == null) return  null;
            Optional<IGUIIngredient> x = pars.parse(section, path, parameters);
            return x.orElse(null);

        });
    }

    @Override
    public void write(ConfigSection configuration, String path, IGUIIngredient object) {

    }

    private char getCharacter(ConfigSection section){
        StringBuilder character = new StringBuilder(section.getName());

        if(character.length() > 1){
            Optional<Character> charz = section.readChar("char");
            charz.ifPresent(c->{
                character.delete(0, character.length());
                character.append(c);
            });
        }
        return character.charAt(0);
    };
}
