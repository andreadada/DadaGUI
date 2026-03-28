package me.mrbast.dadagui;

import me.mrbast.dadagui.ingredient.IGUIIngredient;
import me.mrbast.dadagui.itembuilder.DadaItemBuilder;
import org.apache.logging.log4j.util.TriConsumer;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class GeneralGUI implements IGUI{

    protected String key;

    protected Supplier<Gui.Builder<?, ?>> supplierBuilder;

    protected Map<Character, IGUIIngredient> ingredients = new HashMap<>();

    protected DadaItemBuilder background;

    protected String[] structure;
    protected String title;

    protected List<TriConsumer<IGUI, GUIContext, Gui.Builder<?, ?>>> consumer;
    protected TriConsumer<IGUI, GUIContext,  Window.Builder.Normal.Single> windowConsumer;
    protected List<BiConsumer<IGUI, GUIContext>> closeConsumer;

    public GeneralGUI(Supplier<Gui.Builder<?, ?>> supplierBuilder) {
        this.supplierBuilder = supplierBuilder;
    }

    public GeneralGUI(Gui.Builder<?, ?> builder) {
        this.supplierBuilder = () -> builder;

    }

    public GeneralGUI(String[] layout){
        this.structure = layout;
    }


    @Override
    public Gui.Builder<?, ?> prepare(GUIContext context) {
        if(supplierBuilder == null) return null;

        Gui.Builder<?, ?> builder = supplierBuilder.get();

        if(this.background != null) builder.setBackground(this.background.getBuilder());

        ingredients.forEach((c, ingredient)->{
            ingredient.add(c, builder, context);
        });


        if(this.consumer != null) consumer.forEach(consumer -> {
            consumer.accept(this, context, builder);
        });
        return builder;
    }

    @Override
    public void open(GUIContext context) {
        Gui.Builder<?, ?> builder = prepare(context);
        open(builder, context);
    }

    @Override
    public void open(Gui.Builder<?, ?> builder, GUIContext context) {

        if(builder == null) return;


        Window.Builder.Normal.Single window = Window.single();

        window
                .setViewer(context.getPlayer())
                .setTitle(title != null && !title.isEmpty() ? title : "")
                .setGui(builder);


        if(closeConsumer != null) window.addCloseHandler(()-> closeConsumer.forEach(consumer -> consumer.accept(this, context)));
        if(windowConsumer != null) windowConsumer.accept(this, context, window);


        window.build().open();
    }

    @Override
    public void addItem(char character, IGUIIngredient item) {
        this.ingredients.put(character, item);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setWindowConsumer(TriConsumer<IGUI, GUIContext, Window.Builder.Normal.Single> windowConsumer) {
        this.windowConsumer = windowConsumer;
    }

    @Override
    public void setConsumer(TriConsumer<IGUI, GUIContext, Gui.Builder<?, ?>> consumer) {
        this.consumer = Collections.singletonList(consumer);
    }

    @Override
    public void addConsumer(TriConsumer<IGUI, GUIContext, Gui.Builder<?, ?>> consumer) {
        if(this.consumer == null) {
            this.consumer = new ArrayList<>();
        }
        this.consumer.add(consumer);
    }


    @Override
    public void setCloseConsumer(BiConsumer<IGUI, GUIContext> closeConsumer) {
        this.closeConsumer = Collections.singletonList(closeConsumer);
    }

    @Override
    public void addCloseConsumer(BiConsumer<IGUI, GUIContext> closeConsumer) {
        if(this.closeConsumer == null) {
            this.closeConsumer = new ArrayList<>();
        }
        this.closeConsumer.add(closeConsumer);
    }

    @Override
    public void setBackground(DadaItemBuilder background) {
        this.background = background;
    }

    @Override
    public DadaItemBuilder getBackground() {
        return background;
    }
}
