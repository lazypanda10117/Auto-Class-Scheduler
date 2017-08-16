import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Scheduler {
	public static void main(String[] args) throws IOException{
		final int generationPopulation = 100;
		final double mutationRate = 0.02;
		final double crossoverRate = 0.9;
		final int elitismCount = 2; //how many of the top individual in one generation's population should be passed on to next generation of evolution.
		final int tournmentSize = 5;
		final int roundsOfEvolution = 100;
		
		Interpretation in = new Interpretation();
		Schedule schedule = initializeSchedule();
		GA ga = new GA(generationPopulation, mutationRate, crossoverRate, elitismCount, tournmentSize);//100,0.01,0.9,2,5
		int generation = 1;
		Population population = ga.initPopulation(schedule);
		while (!ga.isTerminationConditionMet(generation, roundsOfEvolution) && !ga.isTerminationConditionMet(population)){
			//crossover
			population = ga.crossoverPopulation(population);
			//mutate
			population = ga.mutatePopulation(population,schedule);
			//evaluate
			ga.evalPopulation(population, schedule);
			Schedule sche = new Schedule(schedule);
			sche.createClasses(population.getFittest(0));
			sche.calculateScheduleScore();
			double fitness = population.getFittest(0).getFitness();

			double[] dA = sche.getScores();
			//if you want, you can write all the details of this generation's best schedule to excel by extracting the data in "sche"
			in.writeIntToSheet(6, generation, 0, generation);
			in.writeDouToSheet(6, generation, 1, fitness);
			in.writeDouToSheet(6, generation, 2, dA[0]);
			in.writeDouToSheet(6, generation, 3, dA[1]);
			in.writeIntToSheet(6, generation, 4, (int)dA[2]);
			in.writeIntToSheet(6, generation, 5, (int)dA[3]);
			if(generation%10 == 1){
				System.out.println("Generation: " + generation + " Best Fitness: " + fitness);
			}
			generation++;
		}
		//show final fitness and schedule
		schedule.createClasses(population.getFittest(0));
		System.out.println("\n");
        System.out.println("Final Score: " + schedule.calculateFScheduleScore(generation));
        System.out.println("Optimal Solution found in " + (generation) + " generations");
        System.out.println("Final solution fitness: " + population.getFittest(0).getFitness());
        System.out.println();
        Class classes[] = schedule.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Module: " + 
            		schedule.getModule(bestClass.getModuleId()).getModuleCode());
            System.out.println("Room: " + 
            		(int)Double.parseDouble(schedule.getRoom(bestClass.getRoomId()).getRoomNumber()));
            System.out.println("Professor: " + 
            		schedule.getTeacher(bestClass.getTeacherId()).getteacherName());
            System.out.println("Time: " + 
            		schedule.getBlock(bestClass.getBlockId()).getBlockTime());
            
            System.out.println();
            System.out.println("-----");
            in.writeIntToSheet(5, classIndex, 0, classIndex);
			in.writeStrToSheet(5, classIndex, 1, schedule.getModule(bestClass.getModuleId()).getModuleCode());
			in.writeStrToSheet(5, classIndex, 2, schedule.getModule(bestClass.getModuleId()).getModuleName());
			in.writeStrToSheet(5, classIndex, 3, schedule.getTeacher(bestClass.getTeacherId()).getteacherName());
			in.writeIntToSheet(5, classIndex, 4, (int)Double.parseDouble(schedule.getRoom(bestClass.getRoomId()).getRoomNumber()));
			in.writeStrToSheet(5, classIndex, 5, schedule.getBlock(bestClass.getBlockId()).getBlockTime());
            classIndex++;
        }
	}
	
	private static Schedule initializeSchedule() {
		Schedule schedule = new Schedule();
		Interpretation in = new Interpretation();
		//initialize room
		for(int i=0; i<in.getRoomList().size()-1; i++){
			schedule.addRoom((int)(Double.parseDouble((String)in.getRoom(i).get(0))), (String)in.getRoom(i).get(1), (int)(Double.parseDouble((String)in.getRoom(i).get(2))), (boolean)(Boolean.parseBoolean((String)in.getRoom(i).get(3))));
		}
		
		schedule.addBlock(1, "Block 1");
		schedule.addBlock(2, "Block 2");
		schedule.addBlock(3, "Block 3");
		schedule.addBlock(4, "Block 4");
		schedule.addBlock(5, "Block 5");
		schedule.addBlock(6, "Block 6");
		schedule.addBlock(7, "Block 7");

		HashMap<String, Integer> teacherMap = new HashMap<String, Integer>();
		for(int i=0; i<in.getTeacherList().size()-1; i++){
			schedule.addTeachers((int)Double.parseDouble((String)in.getTeacher(i).get(0)), (String)in.getTeacher(i).get(1));
			teacherMap.put((String)in.getTeacher(i).get(1),(int)Double.parseDouble((String)in.getTeacher(i).get(0)));
		}

		HashMap<String, Integer> moduleMap = new HashMap<String, Integer>();		
		for(int i=0; i<in.getModuleList().size()-1; i++){
			schedule.addModule((int)Double.parseDouble((String) in.getModule(i).get(0)), (String)in.getModule(i).get(1), (int)Double.parseDouble((String) in.getModule(i).get(2)), (String)in.getModule(i).get(3), (boolean)Boolean.parseBoolean((String)in.getModule(i).get(5)), getTeacherIds(teacherMap,(String)in.getModule(i).get(4)));		
			//System.out.println((String)in.getModule(i).get(1) +"      "+ (int)Double.parseDouble((String) in.getModule(i).get(2)));
			moduleMap.put((String)in.getModule(i).get(1), (int)Double.parseDouble((String) in.getModule(i).get(0)));
		}	
		
		//initialize students

		for(int i=0; i<in.getStudentList().size()-1; i++){
			String[][] tempSAA = create2DModuleArray(in.getStudent(i));
			int[][] tempIAA = new int[tempSAA.length][tempSAA[0].length];
			for(int k=0; k<tempSAA.length; k++){
				for(int j=0; j<tempSAA[k].length; j++){
					tempIAA[k][j] = moduleMap.get(tempSAA[k][j]);
				}
				
			}
			schedule.addStudent((int)Double.parseDouble((String)in.getStudent(i).get(0)), (int)Double.parseDouble((String)in.getStudent(i).get(1)), (String)in.getStudent(i).get(2), ((String)in.getStudent(i).get(3)), (String)in.getStudent(i).get(4), (int)Double.parseDouble((String)in.getStudent(i).get(5)), tempIAA);
		}
		schedule.setNumClasses();
		schedule.generateModuleArray();
		return schedule;
	}
	
	public static int[] getTeacherIds(HashMap<String, Integer> hs, String tS){
		String[] sA = tS.split(",");
		int[] tA = new int[sA.length];
		for(int i=0; i<sA.length; i++){
			int tId = hs.get(sA[i]);
			tA[i] = tId;
			//System.out.println(tA[i]);
		}
		return tA;
	}
	
	public static String[][] create2DModuleArray(List li){
		String[] m1 = ((String) li.get(6)).split(",");
		String[] m2 = ((String) li.get(7)).split(",");
		String[] m3 = ((String) li.get(8)).split(",");
		String[] m4 = ((String) li.get(9)).split(",");
		String[] m5 = ((String) li.get(10)).split(",");
		String[] m6 = ((String) li.get(11)).split(",");
		String[] m7 = ((String) li.get(12)).split(",");
		int totalCombination = (m1.length)*(m2.length)*(m3.length)*(m4.length)*(m5.length)*(m6.length)*(m7.length);
		String[][] totalS = new String[totalCombination][7];
		int counter = 0;
		boolean bl;
		for(int i1=0; i1<m1.length; i1++){
			for(int i2=0; i2<m2.length; i2++){
				for(int i3=0; i3<m3.length; i3++){
					for(int i4=0; i4<m4.length; i4++){
						for(int i5=0; i5<m5.length; i5++){
							for(int i6=0; i6<m6.length; i6++){
								for(int i7=0; i7<m7.length; i7++){
									bl = true;
									totalS[counter][0] = m1[i1].trim();
									totalS[counter][1] = m2[i2].trim();
									totalS[counter][2] = m3[i3].trim();
									totalS[counter][3] = m4[i4].trim();
									totalS[counter][4] = m5[i5].trim();
									totalS[counter][5] = m6[i6].trim();
									totalS[counter][6] = m7[i7].trim();
									for(int j=6; j<=0; j--){
										if(containsInArray(totalS[counter],j,totalS[counter][j])){
											bl = false;
											break;
										}
										
									}
									if(bl){
										counter++;										
									}
								}
							}
						}
					}
				}
			}
		}
		String[][] finalS =  new String[counter][7];
		for(int i=0; i<counter;i++){
			for(int j=0; j<7; j++){
				finalS[i][j] = totalS[i][j];
			}
		}
		return finalS;
	}
	
	private static boolean containsInArray(String[] arr, int index, String x){
		if(arr.length < index){
			return false;
		}else{
			for(int i=0; i<index; i++){
				if(arr[i].equals(x)){
					return true;
				}
			}
			return false;
		}
	}
}
