package me.mrbast.dadagui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GUIContext {

    private Player player;
    private final Map<String, Object> values  = new HashMap<>();

    public <E> E get(String key) {
        try{
            return (E) values.get(key);
        }catch (ClassCastException e){
            return null;
        }
    }

    public <E> Optional<E> getOptional(Class<? extends E> clazz, String key) {
        return Optional.ofNullable(clazz.cast(values.get(key)));
    }
    public <E> Optional<E> getOrCreate(Class<? extends E> clazz, String key, E object) {
        return Optional.of(clazz.cast(values.computeIfAbsent(key, (x)->object)));
    }

    public <E> void set(String key, E value) {
        values.put(key, value);
    }

    public GUIContext(Player player) {
        this.player = player;
    }

    public GUIContext put(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}