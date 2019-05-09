import java.util.ArrayList;
import java.util.List;

public class BasicComparison {
	
	private String text;
	private String pattern;	
	private List<Integer> findings = new ArrayList<Integer>();
	
	public BasicComparison(String text, String pattern) {
		this.text = text;
		this.pattern = pattern;
	}

	private int innercomparison (int postext,  char[] textchars, char[] patternchars){
		int innerPosText = postext;
		int matchCounter = 0;
		for (int pos2 = (patternchars.length-1); (0<=pos2); pos2--){
			if (textchars[innerPosText] == patternchars[pos2]) {
				innerPosText = innerPosText-1;
				matchCounter = matchCounter+1;
			}
			else {pos2=-1;};
		}
		return matchCounter;
	}
	
	
	public List<Integer> comparison (){
		char[] textchars = text.toCharArray();
		char[] patternchars = pattern.toCharArray();
		
		for (int pos = (patternchars.length-1); (textchars.length)>pos; pos++ ){
			if (textchars[pos] == patternchars[patternchars.length-1]) {
				int count= innercomparison(pos, textchars, patternchars);
				if (count == patternchars.length){	
					findings.add(pos-patternchars.length);
					
				}		
			}
		}
		
		return findings ;
	}
	
	
	
	public static void main(String[] args) {
		BasicComparison basicComparison = new BasicComparison("Morgen wird das Wetter besser, aber wie wird das Wetter übermorgen?", "Wetter");
		System.out.println(basicComparison.comparison());
	}

}
