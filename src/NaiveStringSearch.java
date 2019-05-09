import java.util.ArrayList;
import java.util.List;

public class NaiveStringSearch {

	private char[] textchars; 
	private char[] patternchars; 
	private List<Integer> findings = new ArrayList<Integer>();

	
	public NaiveStringSearch(String text, String pattern) {
		this.textchars = text.toCharArray();
		this.patternchars = pattern.toCharArray();
	}

	private boolean innercomparison (int postext){
		int indexT = postext;
		for (int indexP =0; (indexP<patternchars.length); indexP++){
			if (textchars[indexT] == patternchars[indexP]) {
				indexT++ ;
				if (indexP==0){return true ;}
			}
			else {indexP=patternchars.length;};
		}
		return false ;
	}

	public List<Integer> comparison (){
		for (int pos=0; pos<(textchars.length); pos++ ){
			if (textchars[pos] == patternchars[0]) {
				boolean match= innercomparison(pos);
				if (match){					
					findings.add(pos);					
				}	
			}
		}
		return findings ;
	}
	
		
	public static void main(String[] args) {
		NaiveStringSearch naiveComparison = new NaiveStringSearch("Morgen wird das Wetter besser, aber wie wird das Wetter übermorgen?", "Wetter");
		System.out.println(naiveComparison.comparison());
	}
}
