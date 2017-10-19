
package com.index;

import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;

public class TrieTree implements java.io.Serializable {

    public TrieTree() {
    }

    private TrieTree.TrieNode root = new TrieTree.TrieNode();

//    public long counter = 0;//Record the size of the TrieTree

    private class TrieNode implements java.io.Serializable {
        private long index = -1L;
        private Map<Character, TrieTree.TrieNode> son = new TreeMap();

        TrieNode() {
        }
    }

    public void insert(String word, long index) {
        if(!StringUtils.isBlank(word)) {
//            counter++;

            TrieTree.TrieNode node = this.root;
            char[] letters = word.toCharArray();

            for(int i = 0; i < letters.length; ++i) {
                char c = letters[i];
                if(node.son.containsKey(c) && i < letters.length - 1) {
                    node = node.son.get(c);
                } else {
                    TrieTree.TrieNode trieNode = new TrieTree.TrieNode();
                    if(i == letters.length - 1) {
                        trieNode.index = index;
                    }

                    node.son.put(c, trieNode);
                    node = trieNode;
                }
            }

        }
    }

    public long getIndex(String word) {
        if(StringUtils.isBlank(word)) {
            return -1L;
        } else {
            TrieTree.TrieNode node = this.root;
            char[] letters = word.toCharArray();

            for(int i = 0; i < letters.length; ++i) {
                char c = letters[i];

                if(!node.son.containsKey(c)) {
                    return -2L;
                }

                if(i == letters.length - 1) {
                    return node.son.get(c).index;
                }

                node = node.son.get(c);
            }

            return -3L;
        }
    }

/*    public boolean upgrade(String word, long index) {
        if(StringUtils.isBlank(word)) {
            return false;
        } else {
            TrieTree.TrieNode node = this.root;
            char[] letters = word.toCharArray();

            for(int i = 0; i < letters.length; ++i) {
                char c = letters[i];
                if(i == letters.length - 1) {
                    ((TrieTree.TrieNode)node.son.get(Character.valueOf(c))).index = index;
                    return true;
                }

                if(!node.son.containsKey(Character.valueOf(c))) {
                    return false;
                }

                node = (TrieTree.TrieNode)node.son.get(Character.valueOf(c));
            }

            return false;
        }
    }*/


}
