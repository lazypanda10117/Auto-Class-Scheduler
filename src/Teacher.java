
public class Teacher {
	private final int teacherId;
    private final String teacherName;
    
    public Teacher(int teacherId, String teacherName){
         this.teacherId = teacherId;
         this.teacherName = teacherName;
    }
    
    public int getteacherId(){
         return this.teacherId;
    }
    
    public String getteacherName(){
         return this.teacherName;
    }
}
