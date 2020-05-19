package me.elb1to.scrape.event;

import me.elb1to.scrape.scoreboard.Board;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoardCreateEvent extends Event {
    private static final HandlerList handlers;
    private final Board board;
    private final Player player;

    public BoardCreateEvent(final Board board, final Player player) {
        this.board = board;
        this.player = player;
    }

    public HandlerList getHandlers() {
        return BoardCreateEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return BoardCreateEvent.handlers;
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer() {
        return this.player;
    }

    static {
        handlers = new HandlerList();
    }
}