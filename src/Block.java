
public class Block {
	private final int blockId;
    private final String blockTime;
    public Block(int blockId, String blockTime){
    	this.blockId = blockId;
    	this.blockTime = blockTime;
    }
    public int getBlockId(){
    	return this.blockId;
    }
    public String getBlockTime(){
    	return this.blockTime;
    }
}
