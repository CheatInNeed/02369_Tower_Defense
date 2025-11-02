package io.github.cheatinneed.tower_defense.model.Map;

public class Map {
    private final int width, height;
    private final Tile[][] grid;


    public Map(int width, int height, int[] start, int[] end){
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];

        //creating a deafult map
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Tile(x, y, TileType.BUILDABLE);
            }
        }
    }
    /**
     * returns the tile type located at (x,y)
     * @param x
     * @param y
     * @return
     */
    public TileType getTileTypeAt(int x, int y) {
        return grid[x][y].getTileType();
    }

    /**
     * checks if the tile can accept a unit beeing placed
     * @param x
     * @param y
     * @return
     */
    public boolean canPlaceTower(int x,int y){
        if(getTileTypeAt(x,y).equals("BUILDABLE")){
            return true;
        }
        else{return false;}
    }
}
