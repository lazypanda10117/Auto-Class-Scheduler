//for this program, modules = all classes, no difference
public class Module {
	private final int moduleId;
	private final String moduleCode;
	private final String moduleName;
	private final int numOfClass;
	private final boolean isLab;
	private final int[] teacherIds;
	
	public Module(int moduleId, String moduleCode, int numOfClass, String moduleName, boolean isLab, int[] teacherIds){
		this.moduleId = moduleId;
		this.moduleCode = moduleCode;
		this.numOfClass = numOfClass;
		this.moduleName = moduleName;
		this.isLab = isLab;
		this.teacherIds = teacherIds;
	}

	public int getModuleId() {
		return moduleId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public int getNumOfClass(){
		return numOfClass;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public boolean getIsLab() {
		return isLab;
	}
	
	public int getRandomTeacherId() {
		return teacherIds[(int) (teacherIds.length*(Math.random()))];
	}
	
}
