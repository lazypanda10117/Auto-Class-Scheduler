import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Student {
	private final int studentId;
	private final int studentSchoolId;
	private final String lastName;
	private final String middleName;
	private final String firstName;
	private final int grade;
	private int[][] preferredClasses;
	private ArrayList<int[]> maxBlockAL = new ArrayList<int[]>();
	private ArrayList<String[]> maxClassAL = new ArrayList<String[]>();
	private ArrayList<Integer> maxPCL = new ArrayList<Integer>();
	
	public Student(int studentId, int studentSchoolId, String firstName, String middleName, String lastName, int grade, int[][] preferredClasses){
		this.studentId = studentId;
		this.studentSchoolId = studentSchoolId;
		this.lastName = lastName;
		this.middleName = middleName;
		this.firstName = firstName;
		this.grade = grade;
		this.preferredClasses = preferredClasses;
	}
	
	public int getStudentId() {
		return this.studentId;
	}
	
	public int getStudentSchoolId(){
		return this.studentSchoolId;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getMiddleName(){
		return this.middleName;
	}
	
	public int getGrade() {
		return this.grade;
	}
	
	public int[][] getPreferredClasses() {
		/*if(abc){
		for(int[] i : preferredClasses){
			for(int k: i){
				System.out.print(k+",");
			}
			System.out.println();
		}
		abc = false;
		}*/
		return this.preferredClasses;
	}
	
	public ArrayList<int[]> getMaxBlockAL(){
		return this.maxBlockAL;
	}
	
	public ArrayList<Integer> getMaxPCL(){
		return this.maxPCL;
	}
	
	public int numberFit(Class classes[]){
		HashMap<Integer, ArrayList> moduleSlots = new HashMap<Integer, ArrayList>(); 
		//construct block array for module
		for(Class c: classes){
			if(moduleSlots.get(c.getModuleId()) != null){
				ArrayList mBlocks = moduleSlots.get(c.getModuleId());
				mBlocks.add(c.getBlockId());
				moduleSlots.put(c.getModuleId(),mBlocks);
			}else{
				ArrayList mBlocks = new ArrayList();
				mBlocks.add(c.getBlockId());
				moduleSlots.put(c.getModuleId(),mBlocks);
			}
		}
		
		//traversal and backtracking algorithm
		int maxNumFit = 0;
		for(int j=0; j<preferredClasses.length; j++){
			List[] blocksOf7 = new List[7];
			int[] tempBlockList = new int[7];
			String[] tempClassList = new String[7];
			for(int i=0; i<preferredClasses[j].length; i++){
				//shuffle module slots
				Collections.shuffle(moduleSlots.get(preferredClasses[j][i]));
				blocksOf7[i] = moduleSlots.get(preferredClasses[j][i]);	
			}
			/*
			for(int i=0; i<blocksOf7.length; i++){
				int[][] indivBlockId = new int[blocksOf7.length][blocksOf7[i].size()];
				for(int k=0; k<blocksOf7[i].size(); k++){
					indivBlockId[i][k] = (int) blocksOf7[i].get(k);
				}
			}
			for(int preferredModuleId: preferredClasses){
				for(List modu: blocksOf7){
					for(Object bloc: modu){
						int blockId = (int)bloc;		
					}
				}
			}*/
			
			//to fix "might not get to loop 6 because some might not be able to fit a single class", we can set the loop so taht i.e. i0<=blocksOf7[0], and add if statement afterward stating that if i==0, it continues.
			outLoop:
			for(int i0=0; i0<blocksOf7[0].size();i0++){
				tempBlockList[0] = (int)blocksOf7[0].get(i0);
				for(int i1=0; i1<blocksOf7[1].size();i1++){
					tempBlockList[1] = (int)blocksOf7[1].get(i1);
					for(int i2=0; i2<blocksOf7[2].size();i2++){
						tempBlockList[2] = (int)blocksOf7[2].get(i2);
						for(int i3=0; i3<blocksOf7[3].size();i3++){
							tempBlockList[3] = (int)blocksOf7[3].get(i3);
							for(int i4=0; i4<blocksOf7[4].size();i4++){
								tempBlockList[4] = (int)blocksOf7[4].get(i4);
								for(int i5=0; i5<blocksOf7[5].size();i5++){
									tempBlockList[5] = (int)blocksOf7[5].get(i5);
									for(int i6=0; i6<blocksOf7[6].size();i6++){
										
										tempBlockList[6] = (int)blocksOf7[6].get(i6);
										//System.out.println((int)blocksOf7[6].get(i6));
										int tempNum = evaluateTempBlockList(tempBlockList);
										if(tempNum == 7){
											maxNumFit = 7;
											maxBlockAL.clear();
											maxBlockAL.add(tempBlockList);
											maxPCL.clear();
											maxPCL.add(j);
											//copyArray(tempBlockList,maxBlockList);								
											break outLoop;
										}else if(tempNum > maxNumFit){
											maxNumFit = tempNum;
											//copyArray(tempBlockList,maxBlockList);
										}
									}
								}
							}
						}
					}
				}
			}
			
		}
		return maxNumFit;
	}
	
	private void copyArray(int[] a, int[] b){
		for(int i=0; i<a.length; i++){
			b[i] = a[i];
		}
	}
	
	private int evaluateTempBlockList(int[] tbl){
		int numFit = 0;
		for(int i=0; i<tbl.length; i++){
			if(!containsInArray(tbl,i,tbl[i])){
				numFit++;
			}
		}
		return numFit;
	}
	
	private void resetBlockList(int[] x){
		for(int i=0; i<7; i++){
			x[i] = -1;
		}
	}
	
	private boolean containsInArray(int[] arr, int index, int x){
		if(arr.length < index){
			return false;
		}else{
			for(int i=0; i<index; i++){
				if(arr[i] == x){
					return true;
				}
			}
			return false;
		}
	}
	
}
