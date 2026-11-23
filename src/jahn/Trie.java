package jahn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
            int count = endsWord ? 1 : 0;

            for (Trie child : edges.values()) {
                count += child.size();
            }

            return count;
    }

    @Override
    public boolean exactMatch(String target) {
        target = target.toLowerCase();
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
        Stack<Object[]> stack = new Stack<>();

        stack.push(new Object[]{cur, prefix});

        while (!stack.isEmpty()) {
            Object[] entry = stack.pop();
            Trie node = (Trie) entry[0];
            String word = (String) entry[1];

            if (node.endsWord) {
                results.add(word);
            }

            for (Map.Entry<Character, Trie> edge : node.edges.entrySet()) {
                stack.push(new Object[]{
                        edge.getValue(),
                        word + edge.getKey()
                });
            }
        }

        return results.toArray(new String[0]);
    }

}
