package search.algorithms;

import search.SearchAlgorithm;

/**
 * This class allows creating the instance of an algorithm given its name.
 */
public class AlgorithmLoader{
	/**
	 * Takes the name of the class implementing the search algorithm and
	 * returns an instance.
	 */
	public static SearchAlgorithm getAlgorithm(String algorithmName){
		try{
			Class algorithmClass = Class.forName(algorithmName);
			SearchAlgorithm algorithm = (SearchAlgorithm) algorithmClass.newInstance();
			return algorithm;
		}
		catch (Exception E){
			System.out.println("Impossible to build an instance of "+algorithmName);
			System.exit(-1);
		}
		return null;
	}
}
