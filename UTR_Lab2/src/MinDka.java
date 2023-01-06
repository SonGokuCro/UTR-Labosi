import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MinDka {
    public static void main(String args[]) throws IOException {
        Set<String> states = new TreeSet<>();
        Set<String> alphabet = new TreeSet<>();
        Set<String> acceptStates = new TreeSet<>();
        String initialState;
        Map<String, TreeMap<String, String>> transitions = new TreeMap<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] states2 = reader.readLine().split(",");
        Collections.addAll(states, states2);
        String[] alphabet2 = reader.readLine().split(",");
        Collections.addAll(alphabet, alphabet2);
        String[] acceptS = reader.readLine().split(",");
        Collections.addAll(acceptStates, acceptS);
        initialState = reader.readLine();
        String s;
        while ((s = reader.readLine()) != null) {
            String[] s1 = s.split(",");
            String currentState = s1[0];
            String[] s2 = s1[1].split("->");
            String alpha = s2[0];
            String followingState = s2[1];
            if (!transitions.containsKey(currentState)) {
                transitions.put(currentState, new TreeMap<>() {{
                            put(alpha, followingState);
                        }}
                );
            }
            transitions.get(currentState).put(alpha, followingState);
        }

        DFA DKA = new DFA(states, alphabet, acceptStates, initialState, transitions);
        reachableStates(DKA);
        //equivalentStates(DKA);
        System.out.println(DKA);
    }

    private static void reachableStates(DFA DKA) {
        Set<String> reachable = new TreeSet<>();
        reachable.add(DKA.getInitialState());

        boolean change = true;
        while (change) {
            change = false;
            for (Map.Entry<String, TreeMap<String, String>> entry : DKA.getTransitions().entrySet()) {
                for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                    if (reachable.contains(entry.getKey())) {
                        boolean added = reachable.add(innerEntry.getValue());
                        if (added)
                            change = true;
                    }
                }
            }
        }


        Set<String> unReachable = DKA.getStates();
        unReachable.removeAll(reachable);

        DKA.getTransitions().keySet().removeAll(unReachable);
        DKA.getStates().clear();
        DKA.setStates(reachable);
        DKA.getAcceptStates().retainAll(reachable);
    }

    private static void equivalentStates(DFA DKA) {
        Set<String> acceptable = DKA.getAcceptStates();
        Set<String> unAcceptable = DKA.getStates();
        unAcceptable.removeAll(DKA.getAcceptStates());
        List<Set<String>> groups = new ArrayList<>();
        groups.add(acceptable);
        groups.add(unAcceptable);

        boolean done = true;
        while (done) {
            done = false;
            List<Set<String>> newGroups = new ArrayList<>();
            for (Set<String> set : groups) {
                Set<String> temp = new TreeSet<>();

                if (set.size() > 1) {

                    List<String> temp2 = new ArrayList<>();
                    temp2.addAll(set);

                    for (int i = 0; i < temp2.size(); i++) {
                        for (int j = i; j < temp2.size(); j++) {
                            boolean petlja = true;
                            if (i != j && temp2.size() > 1) {
                                String s1 = temp2.get(i);
                                String s2 = temp2.get(j);

                                if (petlja) {
                                    for (String s : DKA.alphabet) {
                                        if (petlja) {
                                            petlja = false;
                                            for (Set<String> grupa : groups) {
                                                String prijelaz1 = DKA.transitions.get(s1).get(s);
                                                String prijelaz2 = DKA.transitions.get(s2).get(s);
                                                if (grupa.contains(prijelaz1) && grupa.contains(prijelaz2)) {
                                                    petlja = true;
                                                }
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                                if (!petlja) {
                                    temp.add(s2);
                                    set.remove(s2);
                                    temp2.remove(s2);
                                    //stvorena je nova grupa
                                    done = true;
                                    j--;
                                }
                            }
                        }
                    }
                    if (temp.size() > 0) {
                        groups.add(temp);
                        break;
                    }
                }
            }
        }
        System.out.println(groups);
    }
}

class DFA {
    Set<String> states;
    Set<String> alphabet;
    Set<String> acceptStates;
    String initialState;
    Map<String, TreeMap<String, String>> transitions;

    public DFA(Set<String> states, Set<String> alphabet, Set<String> acceptStates, String initialState, Map<String, TreeMap<String, String>> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.acceptStates = acceptStates;
        this.initialState = initialState;
        this.transitions = transitions;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAcceptStates() {
        return acceptStates;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public String getInitialState() {
        return initialState;
    }

    public Map<String, TreeMap<String, String>> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String s : states) { sb.append(s + ","); }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");
        for(String a : alphabet) { sb.append(a + ","); }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");
        if(!acceptStates.isEmpty()) {
            for(String aS : acceptStates) { sb.append(aS + ","); }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("\n");
        sb.append(initialState + "\n");
        for(Map.Entry<String, TreeMap<String, String>> entry : transitions.entrySet()) {
            for(Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
                sb.append(entry.getKey()+ "," + entry2.getKey() + "->" + entry2.getValue() + "\n");
            }
        }
        return sb.toString();
    }
}

