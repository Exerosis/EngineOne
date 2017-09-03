package me.engineone.thraxpvp;

import com.google.common.base.Splitter;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scoreboard {
    private boolean batching = false;
    private Objective primary;
    private Objective buffer;
    private final org.bukkit.scoreboard.Scoreboard board;

    public Scoreboard() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        primary = new Objective(board, "primary");
        buffer = new Objective(board, "buffer");
    }

    /**
     * Begins to batch updates, any calls to {@link #set(int, String)} will not result in a swap until {@link #endBatchedUpdates()} is called.
     */
    public void beginBatchedUpdates() {
        batching = true;
    }

    /**
     * Stops batching updates and swaps the {@link org.bukkit.scoreboard.Objective}s.
     */
    public void endBatchedUpdates() {
        batching = false;
        swap();
    }

    /**
     * @param score - The score value to give the new line.
     * @param line  - The text to display on the new line, 64 characters max.
     */
    public void set(int score, String line) {
        buffer.set(score, line);
        if (!batching)
            swap();
    }

    /**
     * Effectively the same as calling set without the swap.
     *
     * @param score - The score value to give the new line.
     * @param line  - The text to display on the new line, 64 characters max.
     */
    public void add(int score, String line) {
        buffer.set(score, line);
        primary.set(score, line);
    }

    /**
     * Removes the line with the given score.
     *
     * @param score - The score value associated with the target line.
     */
    public void remove(int score) {
        buffer.remove(score);
        primary.remove(score);
    }

    /**
     * Sets both {@link org.bukkit.scoreboard.Objective}s display names.
     *
     * @param title - The desired title for the {@link org.bukkit.scoreboard.Scoreboard}.
     */
    public void setTitle(String title) {
        buffer.objective.setDisplayName(title);
        primary.objective.setDisplayName(title);
    }

    /**
     * Returns the underlying {@link org.bukkit.scoreboard.Scoreboard}.
     *
     * @return - The underlying {@link org.bukkit.scoreboard.Scoreboard}.
     */
    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return board;
    }

    private void swap() {
        buffer.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Objective temp = primary;
        primary = buffer;
        buffer = temp;
    }

    class Objective {
        private final org.bukkit.scoreboard.Objective objective;
        private final Map<Integer, String> entries = new HashMap<>();

        Objective(org.bukkit.scoreboard.Scoreboard board, String name) {
            objective = board.registerNewObjective(name, "dummy");
        }

        //TODO in theory shift all the numbers down 1(maybe just change how these work entirely... I think it's possible to just give em all a 1)
        void remove(int id) {
            board.resetScores(entries.get(id));
            entries.remove(id);
        }

        void set(Integer score, String line) {
            Team team;
            if (!entries.containsKey(score))
                team = board.registerNewTeam(score.toString());
            else {
                team = board.getTeam(score.toString());
                board.resetScores(entries.get(score));
            }
            //TODO support 24 length instead of 16 max
            List<String> splitLine = Splitter.fixedLength(16).splitToList(line);
            if (splitLine.size() > 1) {
                team.setPrefix(splitLine.get(0));

                team.getEntries().clear();
                team.addEntry(splitLine.get(1));
                entries.put(score, splitLine.get(1));

                if (splitLine.size() > 2)
                    team.setSuffix(splitLine.get(2));
            } else {
                team.getEntries().clear();
                team.addEntry(splitLine.get(0));
                entries.put(score, splitLine.get(0));
            }
            objective.getScore(entries.get(score)).setScore(score);
        }
    }
}