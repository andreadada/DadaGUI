package me.mrbast.dadagui;

import me.mrbast.dadaconfig.logic.ConfigSection;
import me.mrbast.dadagui.config.*;
import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import me.mrbast.dadagui.paged.PagedGUI;
import me.mrbast.platform.Platform;

public class DadaGUI {


    private static Platform PLATFORM;
    public static Platform getPlatform(){
        if(PLATFORM == null){
            throw new RuntimeException("No platform has been set in DadaGUI.init()");
        }
        return PLATFORM;
    }



    public static void init(Platform platform){
        DadaGUI.PLATFORM = platform;
        ConfigSection.register(DadaItemBuilder.class, new DadaItemBuilderParser(platform));
        ConfigSection.register(IGUI.class, new GUIParser());
        ConfigSection.register(PagedGUI.class, new PagedGUIParser());
        ConfigSection.register(NormalGUI.class, new NormalGUIParser());
        ConfigSection.register(IGUIIngredient.class, new GUIIngredientParser(platform));
    }



}
