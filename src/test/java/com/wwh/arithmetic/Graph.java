package com.wwh.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Graph {
    public static void main(String[] args) {
        Graph graph = new Graph();
        int[][] grid = {{2,1,1},{1,1,0},{0,1,1}};
        System.out.println(graph.orangesRotting(grid));
    }

    // 岛屿数量
    public int numIslands(char[][] grid) {
        int res = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j] == '1'){
                    res++;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }
    void dfs(char[][] grid, int r, int c){
        if(!inArea(grid, r, c) || grid[r][c] != '1'){
            return;
        }
        grid[r][c] = '2';
        dfs(grid, r+1, c);
        dfs(grid, r-1, c);
        dfs(grid, r, c+1);
        dfs(grid, r, c-1);
    }
    boolean inArea(char[][] grid, int r, int c){
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }

    // 烂橘子
    public int orangesRotting(int[][] grid) {
        Queue<Point> queue = new ArrayDeque<>();
        int time = 0;
        boolean freshFlag = false;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if (grid[i][j] == 2) {
                    queue.offer(new Point(i, j));
                }
                if (grid[i][j] == 1) {
                    freshFlag = true;
                }
            }
        }
        int size = queue.size();
        if(size == 0){
            if (freshFlag) {
                return -1;
            }else{
                return 0;
            }
        }
        while (!queue.isEmpty()) {
            for(int i=0;i<size;i++){
                rotOrange(queue, grid);
            }
            size = queue.size();
            time++;
        }
        boolean impossible = false;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if (grid[i][j] == 1) {
                    impossible = true;
                }
            }
        }
        if (impossible) {
            return -1;
        }
        return time-1;
    }
    boolean inArea(int[][] grid, int r, int c){
        return r>=0 && r<grid.length && c>=0 && c<grid[0].length;
    }
    private static class Point{
        int x;
        int y;
        public Point (int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    void rotOrange (Queue<Point> queue, int[][] grid){
        Point point = queue.poll();
        int x = point.x;
        int y = point.y;
        if (inArea(grid, x-1, y) && grid[x-1][y] == 1) {
            grid[x-1][y] = 2;
            queue.offer(new Point(x-1, y));
        }
        if (inArea(grid, x+1, y) && grid[x+1][y] == 1) {
            grid[x+1][y] = 2;
            queue.offer(new Point(x+1, y));
        }
        if (inArea(grid, x, y+1) && grid[x][y+1] == 1) {
            grid[x][y+1] = 2;
            queue.offer(new Point(x, y+1));
        }
        if (inArea(grid, x, y-1) && grid[x][y-1] == 1) {
            grid[x][y-1] = 2;
            queue.offer(new Point(x, y-1));
        }
    }
    // 课程表拓扑排序（广度优先搜索）
    // 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
    // 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indeg = new int[numCourses];
        List<List<Integer>> edges = new ArrayList<>();
        int visited = 0;
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        for (int[] edge : prerequisites) {
            edges.get(edge[1]).add(edge[0]);
            indeg[edge[0]]++;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()){
            Integer poll = queue.poll();
            visited++;
            List<Integer> list = edges.get(poll);
            for (Integer to : list) {
                indeg[to]--;
                if (indeg[to] == 0) {
                    queue.offer(to);
                }
            }
        }
        return visited == numCourses;
    }
}
