import java.util.ArrayList;

public class ValueIteration {
	
	static ArrayList<State> states = new ArrayList<State>();
	static State.Action policy[] = new State.Action[4];
	static double[] utilities = new double[4], diff = new double[4];
	static double lastDiff;
	static double diffTotal = 1.0;
	static int count = 1;
	static final double GAMMA = .3;
	static long startTime;
	
	public static void main(String[] args){
		Double d;
		initStates();
		
		for(State s: states)
			System.out.println(s);
		startTime = System.currentTimeMillis();
		do{
			iterate();
			
			d = Math.abs((diffTotal - lastDiff)/lastDiff);
			
			if((count-1) % 5 == 0){
				System.out.println("\nITERATION " + (count-1));
				for(State s: states)
					System.out.println(s);
				System.out.println("Change: " + Double.toString(diffTotal));
				System.out.println("% Change: " + d);
			}
			
		}while(diffTotal != 0.0 && d != 0.0);// && count <= 100);
		
		double runtime = (System.currentTimeMillis() - startTime)/1000.0;
		
		System.out.println("\nTotal Iterations: " + (count - 1));
		System.out.println("FINAL UTILITIES:");
		for(State s: states)
			System.out.println(s);

		System.out.println("Runtime: " + runtime + "sec");
		
	}
	
	private static void iterate(){
		State s;
		State.Best b;
		lastDiff = diffTotal;
		diffTotal = 0;
		
		for(int i = 0; i < states.size(); i++){
			s = states.get(i);
			b = calculateUtility(s);
			s.bestAction.add(b.action);
			s.utilities.add(b.utility);
			diff[i] = Math.abs(s.utilities.get(count-1) - b.utility);
			diffTotal += diff[i];
			utilities[i] = b.utility;
			policy[i] = b.action;
		}
		
		count++;
	}
	
	private static State.Best calculateUtility(State s){
		double u, u1, u2;
		State.Action a, a1, a2;
		
		if(s == null)
			return new State.Best(0.0, null);
		
		u = s.reward;
		
		//Utility for action 1
		a1 = s.actions.get(0);
		u1 = (a1.probs[0] * a1.states[0].utilities.get(count-1)) 
				+ (a1.probs[1] * a1.states[1].utilities.get(count-1));
		
		//Utility for action 2
		a2 = s.actions.get(1);
		u2 = (a2.probs[0] * a2.states[0].utilities.get(count-1)) 
				+ (a2.probs[1] * a2.states[1].utilities.get(count-1));
		
		if(u1 > u2){
			u += u1 * GAMMA;
			a = a1;
		}else{
			u += u2 * GAMMA;
			a = a2;
		}
		
		return new State.Best(u, a);
	}
	
	private static void initStates(){
		State s[] = new State[4];
		s[0] = new State("s1", 0.0);
		s[1] = new State("s2", 0.0);
		s[2] = new State("s3", 1.0);
		s[3] = new State("s4", 0.0);
		
		s[0].addAction(new State.Action("a1", s[0], .2, s[1], .8));
		s[0].addAction(new State.Action("a2", s[0], .2, s[3], .8));
		s[1].addAction(new State.Action("a2", s[1], .2, s[2], .8));
		s[1].addAction(new State.Action("a3", s[1], .2, s[0], .8));
		s[2].addAction(new State.Action("a4", s[1], 1.0, s[2], 0.0));
		s[2].addAction(new State.Action("a3", s[3], 1.0, s[2], 0.0));
		s[3].addAction(new State.Action("a1", s[3], .1, s[2], .9));
		s[3].addAction(new State.Action("a4", s[3], .2, s[0], .8));
		
		states.add(s[0]);
		states.add(s[1]);
		states.add(s[2]);
		states.add(s[3]);
	}
}
