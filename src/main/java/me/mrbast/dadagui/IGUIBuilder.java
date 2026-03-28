package me.mrbast.dadagui;

import xyz.xenondevs.invui.gui.Gui;

public interface IGUIBuilder<G extends Gui,  S extends Gui.Builder<G, S>> {



    Gui.Builder<G, S> get();
}
