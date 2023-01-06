//import java.util.*;
//
//public class DFA {
//    Set<String> states;
//    Set<String> alphabet;
//    Set<String> acceptStates;
//    String initialState;
//    Map<String, TreeMap<String, String>> transitions;
//
//    public DFA(Set<String> states, Set<String> alphabet, Set<String> acceptStates, String initialState, Map<String, TreeMap<String, String>> transitions) {
//        this.states = states;
//        this.alphabet = alphabet;
//        this.acceptStates = acceptStates;
//        this.initialState = initialState;
//        this.transitions = transitions;
//    }
//
//    public Set<String> getStates() {
//        return states;
//    }
//
//    public Set<String> getAcceptStates() {
//        return acceptStates;
//    }
//
//    public Set<String> getAlphabet() {
//        return alphabet;
//    }
//
//    public void setStates(Set<String> states) {
//        this.states = states;
//    }
//
//    public String getInitialState() {
//        return initialState;
//    }
//
//    public Map<String, TreeMap<String, String>> getTransitions() {
//        return transitions;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for(String s : states) { sb.append(s + ","); }
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append("\n");
//        for(String a : alphabet) { sb.append(a + ","); }
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append("\n");
//        for(String aS : acceptStates) { sb.append(aS + ","); }
//        sb.deleteCharAt(sb.length() - 1);
//        sb.append("\n");
//        sb.append(initialState + "\n");
//        for(Map.Entry<String, TreeMap<String, String>> entry : transitions.entrySet()) {
//            for(Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
//                sb.append(entry.getKey()+ "," + entry2.getKey() + "->" + entry2.getValue() + "\n");
//            }
//        }
//        return sb.toString();
//    }
//}
