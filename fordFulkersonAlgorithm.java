import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by shiyu on 15/11/24.
 */
public class fordFulkersonAlgorithm {
    public static int fordFulkerson(int[][] nums, int start, int end){
        int maxFlow = 0;
        int n = nums.length;
        //define an array named from to store the source vertex of one specified vertex and the initial value are all -1
        int[] from = new int[n];
        for(int i = 0; i < n; i++){
            from[i] = -1;
        }

        while(bfs(nums,start,end,from)){
            int min = Integer.MAX_VALUE;
            int vertex = end;

            //start from the end vertex to start vertex, traverse the whole path to find the minimum capacity
            while(vertex != start){
                int temp = from[vertex];
                min = Math.min(min,nums[temp][vertex]);
                vertex = temp;
            }
            maxFlow += min;
            vertex = end;

            /*
            traverse the whole path again to update capacity of each vertex, if it's forward edge, then capacity minus
            minimum value; Or if it's backward edge, the capacity should add the minimum value
            */
            while(vertex != start){
                int temp = from[vertex];
                nums[temp][vertex] -= min;
                nums[vertex][temp] += min;
                vertex = temp;
            }
        }
        return maxFlow;
    }

    //use breadth first search to find a path from start vertex to end vertex
    public static boolean bfs(int[][] nums, int start, int end,int[] from){
        int n = nums.length;
        //define a boolean array to check whether this vertex has been visited
        boolean[] visited = new boolean[n];
        visited[start] = true;
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(start);
        while(!list.isEmpty()){
            int temp = list.poll();
            /*
            check every edge, only edge with capacity which is not 0 and next vertex of
            this edge has not been visited is available
             */
            for(int i = 0; i < n; i++){
                if(nums[temp][i] != 0 && !visited[i]){
                    from[i] = temp;
                    visited[i] = true;
                    list.add(i);
                }
            }
        }
        //if end vertex is visited, so there exists a path from start vertex to end vertex
        if(visited[end]){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        //prompt user to input vertex number
        System.out.println("please input the number of vertex:");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[][] nums = new int[n][n];

        /*
        prompt user to input two vertexes and its capacity for each edge,
        eg: 0 1 5 means from vertex 0 to vertex 1 and the capacity of edge "01" is 5
        */
        System.out.println("please input the capacity of edge:");
        Scanner input1 = new Scanner(System.in);
        String line = " ";
        while(input1.hasNextLine() && !(line = input1.nextLine()).equals("")){
            String[] arr = line.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            int c = Integer.parseInt(arr[2]);
            nums[a][b] = c;
        }
        System.out.println("max flow is " + fordFulkerson(nums,0,n-1));
    }
}
