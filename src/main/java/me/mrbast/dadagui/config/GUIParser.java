package me.mrbast.dadagui.config;

import me.mrbast.dadaconfig.logic.ConfigSection;
import me.mrbast.dadaconfig.logic.ConfigurationParser;
import me.mrbast.dadaconfig.logic.Parameters;
import me.mrbast.dadagui.IGUI;
import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.NormalGUI;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import me.mrbast.dadagui.paged.PagedGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GUIParser implements ConfigurationParser<IGUI> {


    public interface GUIConsumer {

        IGUI get(String[] layout);

    }

    private final Map<String, GUIConsumer> guiSuppliers = new HashMap<>();

    public GUIParser(){

        guiSuppliers.put("normal", NormalGUI::new);
        guiSuppliers.put("paged", PagedGUI::new);

    }

    public IGUI getGUI(String key, String[] layout) {
        return guiSuppliers.getOrDefault(key, NormalGUI::new).get(layout);
    }



    @Override
    public Optional<IGUI> read(ConfigSection section, String path, Parameters parameters) {


        return section.readString("key").map(key -> {

            String[] layout = section.getStringList("layout").toArray(new String[0]);
            IGUI gui = getGUI(key,  layout);


            section.getSection("ingredients").ifPresent(ingredients-> ingredients.getNodes().forEach(x -> {
                Optional<IGUIIngredient> item = x.read(IGUIIngredient.class);
                item.ifPresent(ingredient -> gui.addItem(ingredient.getChar(), ingredient));
            }));

            section.getSection("background").flatMap(backgroundSection -> backgroundSection.read(DadaItemBuilder.class)).ifPresent(gui::setBackground);


            gui.setKey(section.getName());

            section.readString("title").ifPresentOrElse(gui::setTitle, ()->gui.setTitle(section.getName()));

            return gui;
        });



    }

    @Override
    public void write(ConfigSection configuration, String path, IGUI object) {

    }
}