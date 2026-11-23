package jahn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie implements AutoCompleter {

    private boolean endsWord;
    private final Map<Character, Trie> edges;

    public Trie() {
        this.endsWord = false;
        this.edges = new ListMap<>();
    }

    public Trie(List<String> words) {
        this();
        for (String w : words) {
            add(w.toLowerCase());
        }
    }

    @Override
    public boolean add(String word) {
        Trie current = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            Trie next = current.edges.get(c);
            if (next == null) {
                next = new Trie();
                current.edges.put(c, next);
            }
            current = next;
        }
        if (current.endsWord) {
            return false;
        }
        current.endsWord = true;
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean exactMatch(String target) {
        Trie cur = this;

        for (char c : target.toCharArray()) {
            cur = cur.edges.get(c);
            if (cur == null) {
                return false;
            }
        }

        return cur.endsWord;
    }

    @Override
    public String[] allMatches(String prefix) {
        Trie cur = this;

        for (char c : prefix.toCharArray()) {
            cur = cur.edges.get(c);
            if (cur == null) {
                return new String[0];
            }
        }
        List<String> results = new ArrayList<>();
        return results.toArray(new String[0]);
    }

    @Override
    public String getBackingClass() {
        return "jahn.jahn.ListMap";
    }
}
