package me.mrbast.dadagui;

import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import org.apache.logging.log4j.util.TriConsumer;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

import java.util.function.BiConsumer;

public interface IGUI {

    public Gui.Builder<?, ?> prepare(GUIContext context);
    public void open(GUIContext context);
    public void open(Gui.Builder<?, ?> builder, GUIContext context);
    void addItem(char character, IGUIIngredient item);

    String getKey();
    void setKey(String key);


    void setBackground(DadaItemBuilder dadaItemBuilder);
    DadaItemBuilder getBackground();

    void setTitle(String title);
    String getTitle();


    void setWindowConsumer(TriConsumer<IGUI, GUIContext,  Window.Builder.Normal.Single> windowConsumer);
    void setConsumer(TriConsumer<IGUI, GUIContext, Gui.Builder<?, ?>> consumer);
    void addConsumer(TriConsumer<IGUI, GUIContext, Gui.Builder<?, ?>> consumer);
    void setCloseConsumer(BiConsumer<IGUI, GUIContext> closeConsumer);
    void addCloseConsumer(BiConsumer<IGUI, GUIContext> closeConsumer);
}
