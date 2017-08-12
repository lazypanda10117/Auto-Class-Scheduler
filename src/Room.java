
public class Room {
	private final int roomId;
    private final String roomNumber;
    private final int capacity;
    private final boolean isLab;
    
	public Room(int roomId, String roomNumber, int capacity, boolean isLab){
		this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isLab = isLab;
	}
	
	public int getRoomId() {
        return this.roomId;
	}
	
	public String getRoomNumber() {
	    return this.roomNumber;
	}
	
	public int getRoomCapacity() {
	    return this.capacity;
	}
	
	public boolean getIsLab(){
		return this.isLab;
	}
	
}
