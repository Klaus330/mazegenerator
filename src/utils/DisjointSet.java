package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DisjointSet{

    private final List<Map<Integer, Set<Integer>>> disjointSet;

    public DisjointSet() {
        disjointSet = new ArrayList<>();
    }

    //Create disjoint sets containing only an element
    public void createSet(int element) {
        Map<Integer, Set<Integer>> correspondingTree = new HashMap<>();
        Set<Integer> tree = new HashSet<>();

        tree.add(element);
        correspondingTree.put(element, tree);

        disjointSet.add(correspondingTree);
    }

    //Make the union between two sets, each containing one of the given elements
    public void union(int first, int second) {

        int firstIndex = findSet(first);
        int secondIndex = findSet(second);

        Set<Integer> firstForest = null;
        Set<Integer> secondForest = null;

        for (Map<Integer, Set<Integer>> map : disjointSet) {
            if (map.containsKey(firstIndex)) {
                firstForest = map.get(firstIndex);
            } else if (map.containsKey(secondIndex)) {
                secondForest = map.get(secondIndex);
            }
        }

        if (firstForest != null && secondForest != null) {
            firstForest.addAll(secondForest);
        }

        for (int index = 0; index < disjointSet.size(); index++) {

            Map<Integer, Set<Integer>> map = disjointSet.get(index);

            if (map.containsKey(firstIndex)) {
                map.put(firstIndex, firstForest);
            } else if (map.containsKey(secondIndex)) {
                map.remove(secondIndex);
                disjointSet.remove(index);
            }
        }
    }

    //Find the set the element is belonging to
    public int findSet(int element) {

        for (Map<Integer, Set<Integer>> map : disjointSet) {
            Set<Integer> keySet = map.keySet();

            for (Integer key : keySet) {
                Set<Integer> set = map.get(key);
                if (set.contains(element)) {
                    return key;
                }
            }
        }
        return -1;
    }

}