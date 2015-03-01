package com.cout970.worldeditor.world;

import java.util.ArrayList;
import java.util.List;

import com.cout970.worldeditor.RenderManager;
import com.cout970.worldeditor.WorldEditor;
import com.cout970.worldeditor.util.Side;
import com.google.gson.annotations.Expose;


public class Block {

	@Expose
	public Material material;
	@Expose
	public boolean voidness;
	
	public Location location = new Location();
	@Expose
	public double calor = 25;
	
	public int counter = 0;
	
	public boolean faceN;
	public boolean faceS;
	public boolean faceE;
	public boolean faceW;
	public boolean faceU;
	public boolean faceD;
	
	public Block(){}

	public Block(Material m){
		material = m;
	}

	public double getX(){
		return location.X;
	}
	
	public double getY(){
		return location.Y;
	}
	
	public double getZ(){
		return location.Z;
	}	
	
	public boolean shouldRenderSide(Side s){
		if(s == Side.UP)return faceU;
		if(s == Side.DOWN)return faceD;
		if(s == Side.NORTH)return faceN;
		if(s == Side.SOUTH)return faceS;
		if(s == Side.EAST)return faceE;
		if(s == Side.WEST)return faceW;
		return false;
	}
	
	public void setRenderizable(Side s,boolean ren){
		if(s == Side.UP) faceU = ren;
		if(s == Side.DOWN) faceD = ren;
		if(s == Side.NORTH) faceN = ren;
		if(s == Side.SOUTH) faceS = ren;
		if(s == Side.EAST) faceE = ren;
		if(s == Side.WEST) faceW = ren;
	}
	
	public String toString(){
		return "x:"+getX()+" y:"+getY()+" z:"+getZ()+" Material: "+material;
	}
	
	public void updateBlock(){
		boolean update = false;
		List<Block> vecinos = new ArrayList<Block>();
		
		if(material.getMaterialName().equalsIgnoreCase("HOT")){
			calor += 15;
		}else if(material.getMaterialName().equalsIgnoreCase("air")){
			if(calor > 25)calor -= (calor*calor)*0.001;
		}else if(material.getMaterialName().equalsIgnoreCase("lava")){
			if(calor < 500){
				material = new Material("STONE", 0.5f,0.5f,0.5f);
				update = true;
			}
		}
		if(calor >= 1000)counter++;
		for(Side s : Side.values()){
			Block b = WorldEditor.getBlock((int)(location.X+s.OffsetX), (int)(location.Y+s.OffsetY), (int)(location.Z+s.OffsetZ));
			if(b == null)continue;
			if(counter > 60){
				if(!b.material.getMaterialName().equalsIgnoreCase("lava") && !b.material.getMaterialName().equalsIgnoreCase("air") && !b.material.getMaterialName().equalsIgnoreCase("hot")){
					b.material = new Material("LAVA", 0.8f,0.8f,0.8f);
					update = true;
				}
			}
			vecinos.add(b);
		}
		if(counter > 60)counter = 0;
		for(Block c : vecinos){
			double dif = calor - c.calor;
			dif *= 0.025;
			calor -= dif;
			c.calor += dif;
		}
		if(update)
			RenderManager.reloadChunks();
	}
}
