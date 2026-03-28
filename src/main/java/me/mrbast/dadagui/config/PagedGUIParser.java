package me.mrbast.dadagui.config;


import me.mrbast.dadaconfig.logic.ConfigSection;
import me.mrbast.dadaconfig.logic.ConfigurationParser;
import me.mrbast.dadaconfig.logic.Parameters;
import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import me.mrbast.dadagui.paged.PagedGUI;

import java.util.Optional;


public class PagedGUIParser implements ConfigurationParser<PagedGUI> {
    @Override
    public Optional<PagedGUI> read(ConfigSection section, String s, Parameters parameters) {
        String[] layout = section.getStringList("layout").toArray(new String[0]);
        PagedGUI gui = new PagedGUI(layout);


        section.getSection("ingredients").ifPresent(ingredients-> ingredients.getNodes().forEach(x -> {
            Optional<IGUIIngredient> item = x.read(IGUIIngredient.class);
            item.ifPresent(ingredient -> gui.addItem(ingredient.getChar(), ingredient));
        }));

        section.getSection("background").flatMap(backgroundSection -> backgroundSection.read(DadaItemBuilder.class)).ifPresent(gui::setBackground);


        gui.setKey(section.getName());

        section.readString("title").ifPresentOrElse(gui::setTitle, ()->gui.setTitle(section.getName()));

        return Optional.of(gui);
    }

    @Override
    public void write(ConfigSection configSection, String s, PagedGUI pagedGUI) {

    }
}
