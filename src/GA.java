import java.io.IOException;

public class GA {
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	protected int tournamentSize;
	
	public GA(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}

	public Population initPopulation(Schedule schedule) {
		Population population = new Population(this.populationSize, schedule); 
		return population;
	}
	
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}
	
	public boolean isTerminationConditionMet(Population population) {
		return population.getFittest(0).getFitness() >= 100.0; //depends on how optimal you want it to be, for speed, set to 98.5
	}
	
	public double calcFitness(
		Individual individual, Schedule schedule) throws IOException {
		Schedule localSchedule = new Schedule(schedule);
		localSchedule.createClasses(individual);
		double fitness = localSchedule.calculateScheduleScore();
		individual.setFitness(fitness);
		return fitness;
	}
	
	public void evalPopulation(Population population, Schedule schedule) throws IOException {
		double populationFitness = 0;
		for (Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, schedule);
		}
		population.setPopulationFitness(populationFitness);
	}
	
	public Individual selectParent(Population population) {
		Population tournament = new Population(this.tournamentSize);
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Individual tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}
		return tournament.getFittest(0);
	}
	
	public Population mutatePopulation(Population population, Schedule schedule) {
		Population newPopulation = new Population(this.populationSize);
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);
			Individual randomIndividual = new Individual(schedule);
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				if (populationIndex > this.elitismCount) {
					if (this.mutationRate > Math.random()) {
						individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
					}
				}
			}
			newPopulation.setIndividual(populationIndex, individual);
		}
		return newPopulation;
	}
	
	public Population crossoverPopulation(Population population) {
		Population newPopulation = new Population(population.size());
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				Individual offspring = new Individual(parent1.getChromosomeLength());
				Individual parent2 = selectParent(population);
				for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					if (0.5 > Math.random()){
						offspring.setGene(geneIndex, parent1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		return newPopulation;
	}
}
