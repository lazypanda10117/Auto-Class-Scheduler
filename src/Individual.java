
public class Individual {
	private int[] chromosome;
	private double fitness = -1;
	
	public Individual(Schedule schedule) {
		int numClasses = schedule.getNumClasses();
		// 1 gene for room, 1 for time, 1 for professor
		int chromosomeLength = numClasses * 3;
		// Create random individual
		int newChromosome[] = new int[chromosomeLength];
		int chromosomeIndex = 0;
		// Loop through groups

		for(int i=0; i<schedule.getNumClasses(); i++){
			int blockId = schedule.getRandomBlock().getBlockId();
			newChromosome[chromosomeIndex] = blockId;
			chromosomeIndex++;
			int roomId = schedule.getRandomRoom().getRoomId();
			newChromosome[chromosomeIndex] = roomId;
			chromosomeIndex++;
			Module module = schedule.getModule(schedule.getModuleIdFromModuleArray(i)+1);	
			//System.out.println(module);
			newChromosome[chromosomeIndex] = module.getRandomTeacherId();
			chromosomeIndex++;
		}
		this.chromosome = newChromosome;
	}
	
	//this is invalid but will be rewritten by crossover, so doesn't matter
	public Individual(int chromosomeLength) {
		int[] individual;
		individual = new int[chromosomeLength];
		for (int gene = 0; gene < chromosomeLength; gene++) {
		       individual[gene] = gene;
		}
		this.chromosome = individual;
	}
	
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	
	public int[] getChromosome() {
		return this.chromosome;
	}
	
	public int getChromosomeLength() {
		return this.chromosome.length;
	}
	
	public int getGene(int index) {
		return this.chromosome[index];
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}
	
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] + ",";
		}
		return output;
	}
}
