import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoyerMoore {	
	private char[] textchars; 
	private char[] patternchars; 
	private List<Integer> findings = new ArrayList<Integer>();
	private HashMap<Character, Integer> badChar = new HashMap<>(); 
	private int leftIndexSuffixes;
	private int rightIndexSuffixes;	
	
	
	public BoyerMoore(String text, String pattern) {
		this.textchars = text.toCharArray();
		this.patternchars = pattern.toCharArray();
	}

	private int innercomparison (int postext){
		int innerPosText = postext;
		int matchCounter = 0;
		for (int pos2 = (patternchars.length-1); (0<=pos2); pos2--){
			if (textchars[innerPosText] == patternchars[pos2]) {
				innerPosText--;
				matchCounter++;
			}
			else {pos2=-1;};
		}
		return matchCounter;
	}
	
	private HashMap<Character, Integer> createBadCharTable (char[] patternchars){
		for (int posPattern = 0; (patternchars.length>posPattern); posPattern++){
			if(!badChar.containsKey(patternchars[posPattern])){	
				badChar.put(patternchars[posPattern], (patternchars.length-1-posPattern));
			} else {
				badChar.replace(patternchars[posPattern],(patternchars.length-1-posPattern));		
			};
		}	
		return badChar;
	}

	//Good suffixes	
		private int[] createGoodSuffixes (char[] patternchars){
			int[] suffixes = new int[patternchars.length];
			rightIndexSuffixes =patternchars.length-1;
			leftIndexSuffixes= 0;
			for (int index = patternchars.length-2; (index>=0); index--){
				if(index>rightIndexSuffixes && suffixes[index+patternchars.length-1-leftIndexSuffixes]< index-rightIndexSuffixes){
					suffixes[index]= suffixes[index+patternchars.length-1-leftIndexSuffixes];
					//System.out.println("s2 if1" + Arrays.toString(suffixes));
				} else {
					if(index<rightIndexSuffixes){
						rightIndexSuffixes = index;
						leftIndexSuffixes= index;
					}
					while(rightIndexSuffixes>=0 && patternchars[rightIndexSuffixes] == patternchars[rightIndexSuffixes+ patternchars.length-1-leftIndexSuffixes]){
					rightIndexSuffixes = rightIndexSuffixes-1;
					suffixes[index] = leftIndexSuffixes-rightIndexSuffixes;
					}							
				};					
			}
			//System.out.println("suffixe " + Arrays.toString(suffixes));	
			return suffixes;
		}	
	
// Good suffix heuristics	
	private int[] createGoodSuffixTable (char[] patternchars, int[] suffixes){
		int[] goodsuffixes = new int[patternchars.length];
		for(int index = 0; index< patternchars.length;index++){
			goodsuffixes[index]=patternchars.length;
		}	
		for (int rightIndex = patternchars.length-1; (rightIndex>=0); rightIndex--){	
			if(suffixes[rightIndex] == rightIndex+1){
				for (int leftIndex = 0; leftIndex <= patternchars.length-1-rightIndex; leftIndex++){
					if (goodsuffixes[leftIndex] == patternchars.length){
						goodsuffixes[leftIndex] = patternchars.length-1-rightIndex;
					}
				}
			}
		}
		
		for (int rightIndex1 = 0; rightIndex1>=patternchars.length-2; rightIndex1++){
			goodsuffixes[patternchars.length-1-suffixes[rightIndex1]] = patternchars.length-1-rightIndex1;
		}
		goodsuffixes[patternchars.length-1]=0;
		return goodsuffixes;
	}	

	private int evaluateShift(int bad, int good){
		if (bad>good && bad>0){
			return bad;
		}
		else		
		{if (good>bad && good>0) {return good;}
		else {return 1;}
		}
	}
	
	
	public List<Integer> comparison (){
		int bad;
		
		HashMap<Character, Integer> badCharTable = createBadCharTable(patternchars);
		int[] goodSuffixTable = createGoodSuffixTable(patternchars,createGoodSuffixes(patternchars));	
				
		for (int postext = (patternchars.length-1); (textchars.length)>postext;  ){
				if (textchars[postext] == patternchars[patternchars.length-1]) {
					int count= innercomparison(postext);
					if (count == patternchars.length){					
						findings.add(postext-patternchars.length);			
						postext= postext+1;
					}
					else {
						if (!badCharTable.containsKey(textchars[postext-count])){
							bad= patternchars.length-1;}
						else{
							bad= badCharTable.get(textchars[postext]);}
						int good= goodSuffixTable[patternchars.length-1-count];
						int shift= evaluateShift(bad, good);
						postext = postext-count+shift;
					}	
				}
				else {
					if (!badCharTable.containsKey(textchars[postext])){
						bad= patternchars.length-1;}
					else{
						bad= badCharTable.get(textchars[postext]);
								}
					int good= goodSuffixTable[patternchars.length-1];
					int shift= evaluateShift(bad, good);
					postext = postext+shift;
					}
				}		
		return findings;
	}
	
	
	
	public static void main(String[] args) {
		//ruft andere file tf ;)
		//ComparisonTF basicComparison = new ComparisonTF("Morgen wird das Wetter besser, aber wie wird das Wetter übermorgen?", "Wetter");
		BoyerMoore comparison = new BoyerMoore("Morgen wird das abcab besser, aber wie wird das abcab übermorgen?", "abcab");
		System.out.println(comparison.comparison());
	}

	
}
