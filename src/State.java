import java.util.ArrayList;

public class State {
	
	String name;
	ArrayList<Action> actions;
	ArrayList<Action> bestAction;
	ArrayList<Double> utilities;
	double reward;
	
	public State(String name, double reward){
		this.name = name;
		this.reward = reward;
		utilities = new ArrayList<Double>();
		utilities.add(5.0);
		actions = new ArrayList<Action>();
		bestAction = new ArrayList<Action>();
	}
	
	public String toString(){
		String s = name + ": [";
		int i = utilities.size();
		
		if(i > 1)
			s += "Best Action = " + bestAction.get(i-2) + ", "; 
		
		s +="Utility = " + utilities.get(i-1) + "]";
		return s;
	}
	
	public void addAction(Action a){
		actions.add(a);
	}
	
	public static class Action{
		String name;
		State states[] = new State[2];
		double probs[] = new double[2];
		
		public Action(String name, State s1, double d1, State s2, double d2){
			this.name = name;
			states[0] = s1;
			states[1] = s2;
			probs[0] = d1;
			probs[1] = d2;
		}
		
		public String toString(){
			return name;
		}
	}
	
	public static class Best{
		double utility;
		Action action;
		
		public Best(double d, Action a){
			utility = d;
			action = a;
		}
	}
}
