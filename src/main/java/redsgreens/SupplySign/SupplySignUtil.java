package redsgreens.SupplySign;

import org.bukkit.Material;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;

public class SupplySignUtil {
	
	// check to see if this is a chest without a supply sign already on it
	public static boolean isValidChest(Block b){
		if(b.getType() != Material.CHEST)
			return false;

		Block[] adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};

		for(int i=0; i<adjBlocks.length; i++){
			if(adjBlocks[i].getState() instanceof WallSign){
				Sign sign = (Sign)adjBlocks[i].getState();
				// Check the front side of the sign
				SignSide frontSide = sign.getSide(Side.FRONT);
				if (frontSide.getLine(0).equals("§1[Supply]")) {
					return false;
				}

				// Check the back side of the sign if needed
				SignSide backSide = sign.getSide(Side.BACK);
				if (backSide.getLine(0).equals("§1[Supply]")) {
					return false;
				}
			}
		}
		
		return true;
	}

	// check to see if this is a dispenser without a supply sign already on it
	public static boolean isValidDispenser(Block b){
		if(b.getType() != Material.DISPENSER)
			return false;

		Block[] adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};

		for(int i=0; i<adjBlocks.length; i++){
			if(adjBlocks[i].getState() instanceof WallSign){
				Sign sign = (Sign)adjBlocks[i].getState();
				// Check the front side of the sign
				SignSide frontSide = sign.getSide(Side.FRONT);
				if (frontSide.getLine(0).equals("§1[Supply]")) {
					return false;
				}

				// Check the back side of the sign if needed
				SignSide backSide = sign.getSide(Side.BACK);
				if (backSide.getLine(0).equals("§1[Supply]")) {
					return false;
				}
			}
		}
		
		return true;
	}

	// check to see if this is a single wide chest
	public static boolean isSingleChest(Block b){
		
		if(b.getType() != Material.CHEST)
			return false;

		Block[] adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};

		for(int i=0; i<adjBlocks.length; i++)
			if(adjBlocks[i].getType() == Material.CHEST)
				return false;
	
		return true;
	}

	// check to see if this is a single wide chest
	public static boolean isDoubleChest(Block b){
		
		if(b.getType() != Material.CHEST)
			return false;

		Block[] adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};

		for(int i=0; i<adjBlocks.length; i++)
			if(adjBlocks[i].getType() == Material.CHEST)
				return true;
	
		return false;
	}

	// find a sign attached to a chest
	public static Sign getAttachedSign(Block b){
		if((b.getType() != Material.CHEST) && (b.getType() != Material.DISPENSER))
			return null;

		Block[] adjBlocks;

		if(isSingleChest(b) || (b.getType() == Material.DISPENSER))
			// it's a single chest or dispenser, so check the four adjacent blocks
			adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};
			
		else if (isDoubleChest(b)){
			// it's a double, so find the other half and check faces of both blocks
			Block b2 = findOtherHalfofChest(b);
			adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST), b2.getRelative(BlockFace.NORTH), b2.getRelative(BlockFace.EAST), b2.getRelative(BlockFace.SOUTH), b2.getRelative(BlockFace.WEST)};
		}
		else
			return null;

		for(int i=0; i<adjBlocks.length; i++)
			if(isSupplySign(adjBlocks[i]))
				return (Sign)adjBlocks[i].getState();
		
		return null;
	}

	public static Block findOtherHalfofChest(Block b)
	{
		// didn't find one, so find the other half of the chest and check it's faces
		Block[] adjBlocks = new Block[]{b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.SOUTH), b.getRelative(BlockFace.WEST)};
		for(int i=0; i<adjBlocks.length; i++)
			if(adjBlocks[i].getType() == Material.CHEST)
				return adjBlocks[i]; 
		
		return null;
	}
	
	// get the block that has a wall sign on it
	public static Block getBlockBehindWallSign(Sign sign)
	{
		Block blockAgainst = null;
		Block signBlock = sign.getBlock();
		BlockData sbd = signBlock.getBlockData();
		
		if(signBlock.getState() instanceof WallSign)
		{

			switch(((Directional) sbd).getFacing()){ // determine sign direction and get block behind it
			case NORTH: // facing north
				blockAgainst = signBlock.getRelative(BlockFace.SOUTH);
				break;
			case SOUTH: // facing south
				blockAgainst = signBlock.getRelative(BlockFace.NORTH);
				break;
			case WEST: // facing west
				blockAgainst = signBlock.getRelative(BlockFace.EAST);
				break;
			case EAST: // facing east
				blockAgainst = signBlock.getRelative(BlockFace.WEST);
				break;
			}
		}
		
		return blockAgainst;
	}

	public static String stripColorCodes(String str)
	{
		return str.replaceAll("\u00A7[0-9a-fA-F]", "");
	}

	public static Boolean isSupplySign(Sign sign) {
		// Check the front side of the sign
		SignSide frontSide = sign.getSide(Side.FRONT);
		if (frontSide.getLine(0).equals("§1[Supply]")) {
			return true;
		}

		// Check the back side of the sign if needed
		SignSide backSide = sign.getSide(Side.BACK);
		if (backSide.getLine(0).equals("§1[Supply]")) {
			return true;
		}

		return false;
	}

	public static SignSide getActiveSide(Sign sign) {
		SignSide frontSide = sign.getSide(Side.FRONT);
		SignSide backSide = sign.getSide(Side.BACK);
		if (backSide.getLine(0).equals("§1[Supply]")) {
			return backSide;
		}

		return frontSide;
	}
	
	public static Boolean isSupplySign(Block b)
	{
		if(!(b.getState() instanceof Sign) && !(b.getState() instanceof WallSign))
			return false;
		else
			return isSupplySign((Sign)b.getState());

	}
}
