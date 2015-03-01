package com.cout970.worldeditor;

import com.cout970.worldeditor.world.Chunk;
import com.cout970.worldeditor.world.ChunkStorage;

public class World {

	public static long time = 0;
	
	public static void tick(){
		time++;
		if(time % 60 == 0){
			RenderManager.reloadChunks();
		}
		for(Chunk c : ChunkStorage.storage){
			for(int x=0;x < c.Blocks[0][0].length;x++)
				for(int y=0;y < c.Blocks.length;y++)
					for(int z=0;z < c.Blocks[0].length;z++){
						if(c.Blocks[y][z][x] != null){
							c.Blocks[y][z][x].updateBlock();
						}
					}
		}
	}
}
