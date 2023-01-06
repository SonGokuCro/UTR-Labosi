import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;



public class SimPa {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = reader.readLine().split("\\|");
        reader.readLine();
        reader.readLine();
        reader.readLine();
        String[] acceptingStates = reader.readLine().split(",");
        String startState = reader.readLine();
        String startingStack = reader.readLine();

        HashMap<StateInputStack, StateStack> transitions = new HashMap<>();

        String s;
        while ((s = reader.readLine()) != null) {
            var temp = s.split("->");

            var state = temp[0].split(",")[0];
            var input = temp[0].split(",")[1];
            var stack = temp[0].split(",")[2];

            var nextState = temp[1].split(",")[0];
            var nextStack = temp[1].split(",")[1];

            transitions.put(new StateInputStack(state, input, stack),
                    new StateStack(nextState, nextStack));
        }

        for (String input : inputs)
            System.out.println(simulate(input.split(","), Arrays.asList(acceptingStates), startState, startingStack,
                    transitions));

    }

    public static String simulate(String[] inputs, List<String> acceptingStates, String startState,
                                  String startingStack,
                                  Map<StateInputStack, StateStack> transitions) {

        String input;
        String stack;
        String state = startState;
        String stackHistory = startingStack;
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < inputs.length) {
            sb.append(state + "#" + (stackHistory.length() > 0 ? stackHistory : "$") + "|");

            input = inputs[i++];
            stack = stackHistory.length() > 0 ? stackHistory.charAt(0) + "" : "$";
            if (stackHistory.length() > 0)
                stackHistory = stackHistory.substring(1);

            var epsilonTransition = transitions.getOrDefault(new StateInputStack(state, "$", stack), null);
            var nextStateStack = transitions.getOrDefault(new StateInputStack(state, input, stack), epsilonTransition);

            if (nextStateStack == null)
                return sb.append("fail|0").toString();

            state = nextStateStack.state;
            if (nextStateStack == epsilonTransition)
                i--;

            if (!nextStateStack.stack.equals("$"))
                stackHistory = nextStateStack.stack + stackHistory;

        }

        sb.append(state + "#" + (stackHistory.length() > 0 ? stackHistory : "$") + "|");

        if (acceptingStates.contains(state))
            return sb.append("1").toString();

        while (stackHistory.length() > 0 && !acceptingStates.contains(state)) {
            stack = stackHistory.length() > 0 ? stackHistory.charAt(0) + "" : "$";
            if (stackHistory.length() > 0)
                stackHistory = stackHistory.substring(1);

            var nextStateStack = transitions.getOrDefault(new StateInputStack(state, "$", stack), null);

            if (nextStateStack == null)
                return sb.append("0").toString();

            state = nextStateStack.state;

            if (!nextStateStack.stack.equals("$"))
                stackHistory = nextStateStack.stack + stackHistory;

            sb.append(state + "#" + (stackHistory.length() > 0 ? stackHistory : "$") + "|");
        }

        if (acceptingStates.contains(state))
            return sb.append("1").toString();

        return sb.append("0").toString();
    }

}

class StateInputStack {
    public final String state;
    public final String input;
    public final String stack;

    public StateInputStack(String state, String input, String stack) {
        this.state = state;
        this.input = input;
        this.stack = stack;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != StateInputStack.class)
            return false;

        StateInputStack other = (StateInputStack) obj;
        return this.state.equals(other.state) && this.input.equals(other.input) && this.stack.equals(other.stack);
    }

    @Override
    public int hashCode() {
        return state.hashCode() ^ input.hashCode() + stack.hashCode();
    }
}

class StateStack {
    public final String state;
    public final String stack;

    public StateStack(String state, String stack) {
        this.state = state;
        this.stack = stack;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != StateStack.class)
            return false;

        StateStack other = (StateStack) obj;
        return this.state.equals(other.state) && this.stack.equals(other.stack);
    }

    @Override
    public int hashCode() {
        return state.hashCode() ^ stack.hashCode();
    }
}