//Citation: http://www.dreamincode.net/forums/topic/57590-os-scheduling-algorithm-implementation/

public class Scheduler
{
    public String s_name;
    public static String s_sequence;
    public int i_arrival,i_duration, i_durStat, i_deadline, i_priority;
    private int finTime;
    static int i_counter=2,i_time=0,loc=0;
    static int [] a= new int[i_counter];	
    static Scheduler [] o_p = new Scheduler[i_counter];
    //static Scanner input = new Scanner(System.in);
    public Scheduler()
    {
    };
    void initialize(String name, int arrival, int duration, int deadline, int priority){
        for(int i=0;i<i_counter;i++)
         a[i]=0;
/*        System.out.print("Enter process Name::");
        s_name = input.next(); 
        System.out.print("Enter Arrival Time::");
        i_arrival=input.nextInt();
        System.out.print("Enter  duration::");
        i_duration=input.nextInt(); */
        s_name = name;
        s_sequence="";
        i_arrival = arrival;
        i_duration = duration;
        i_durStat = duration;
        i_deadline = deadline;
        i_priority = priority;
    }
     
    static void output(){
        System.out.print("Process Name|Arrival Time  |  Duration   |   Deadline  |  Priority\n"
                        +"------------|--------------|-------------|-------------|----------\n");
        for(int i = 0; i < i_counter; i++)
                         System.out.print("\n"+o_p[i].s_name+"\t\t"+o_p[i].i_arrival
                        		 +"\t\t"+o_p[i].i_duration+"\t\t"+o_p[i].i_deadline+"\t\t"+
                        		 o_p[i].i_priority+"\n");
    }
    
    static int cost(int x){
    	int cost = o_p[x].i_priority;
    	if(o_p[x].i_deadline!=-1){
    		int y = o_p[x].i_deadline-(i_time + o_p[x].i_durStat);
    		if(y<=1)
    			cost--;
    		if(y<=0)
    			cost--;
    		if(y<=-1)
    			cost--;
    	}
    	return cost;
    }
    
    static int waitTime(){
    	int waitTime =0;
    	for(int i=0; i < i_counter; i++){
    		waitTime += (o_p[i].finTime+1)-(o_p[i].i_durStat+o_p[i].i_arrival);
    	}
    	return waitTime;
    }
    
    
    static int tardiness(){
    	int tardiness =0;
    	for(int i=0; i < i_counter; i++){
    		if(o_p[i].i_deadline!=-1&&(o_p[i].finTime-o_p[i].i_deadline+1>0)){
    			tardiness+=o_p[i].finTime-o_p[i].i_deadline+1;
    		}
    	}
    	return tardiness;
    }
    
    static int check_arrival(){
        for(int i=0;i< i_counter;i++)
          if((o_p[i].i_duration>0)&&(o_p[i].i_arrival==i_time)){
               a[i]=1; 
               loc=i;  
          }
        if(a[loc]==0){
              for(int i=0;i< i_counter;i++)
                 if(a[i]==1)
                   loc=i;
          }

        for(int j=0;j< i_counter;j++){
                    if((o_p[j].i_duration>0)&&(a[j]==1)){
                    	if (cost(j)<cost(loc)){
                    		loc = j;
                    	}
                     }
                }
        return loc;
    }
    static void make_string(String process_name){
        s_sequence=s_sequence+"  "+process_name;
    }
    static void process(){
        int loc2;
        loc2=check_arrival();	
        o_p[loc2].i_duration--;
        if(o_p[loc2].i_duration==0){	
        	o_p[loc2].finTime = i_time;
        	a[loc2]=0;	
        }
        i_time++;	
        make_string(o_p[loc2].s_name);       
    }
    
    static void solution(){
        int value=0;
        for(int i=0;i<i_counter;i++)
            value+=o_p[i].i_duration;
        for(int i=0;i<value;i++)
            process();
    }
    
	public static void main(String args[]){
		i_counter = 2;
	    for(int i=0;i<i_counter;i++){
	        o_p[i] = new Scheduler();
	    }
	    for(int i = 1; i<=6; i++){   	
		    i_time = 0;
	    	loc = 0;
	        o_p[0].initialize("a", 0, 10, -1, 2);
	        o_p[1].initialize("b", 0, 3, 6, i);
	        System.out.println("\n");
	        //o_p[2].initialize("c", 1, 3, -1, 3);
	        //o_p[3].initialize("d", 2, 4, 8, 1);
	            output();
	            solution();
	        //output();
	        System.out.println(s_sequence);
	        System.out.println("Avaerage Wait Time: "+waitTime()+"/"+i_counter);
	        System.out.println("Total Tardiness: "+tardiness());
	        //System.exit(0);
	    }
	    
	    i_counter = 2;
	    for(int i=0;i<i_counter;i++){
	        o_p[i] = new Scheduler();
	    }
	    for(int i = 1; i<=6; i++){   	
		    i_time = 0;
	    	loc = 0;
	        o_p[0].initialize("a", 0, 3, -1, 2);
	        o_p[1].initialize("b", 0, 10, 20, i);
	        System.out.println("\n");
	        //o_p[2].initialize("c", 1, 3, -1, 3);
	        //o_p[3].initialize("d", 2, 4, 8, 1);
	            output();
	            solution();
	        //output();
	        System.out.println(s_sequence);
	        System.out.println("Avaerage Wait Time: "+waitTime()+"/"+i_counter);
	        System.out.println("Total Tardiness: "+tardiness());
	        //System.exit(0);
	    }
	    
	    //testing numerous trials of 10 processes with variable start times, priorities, 
	    //processing times, and some with deadlines.
	    System.out.println("\n");
	    int rangeOfStarts = 10;
	    int maxDuration = 5;
	    int maxPriority = 5;
	    int trials = 1;
	    int sumOfAvs = 0;
	    int sumOfTards = 0;
	    for(int j=0; j<trials; j++){
		    i_counter = 10;
		    o_p = new Scheduler[i_counter];
		    a = new int[i_counter];
		    for(int i=0;i<i_counter;i++){
		        o_p[i] = new Scheduler();
		    }
		    i_time = 0;
		    loc = 0;
		    for(int i = 0; i<10; i++){
		    	int x = (int)(Math.random()*rangeOfStarts);
		    	int y = (int)(Math.random()*maxDuration+1);
		    	int z = (int)(Math.random()*maxPriority+1);
		    	int w = 2*y+x;
		    	String l = ""+i;
			    if(i%2==1)
			    	o_p[i].initialize(l, x, y, -1, z);
			    else
			    	o_p[i].initialize(l, x, y, w, z);
		    }
		        //System.out.println("\n");
		        //o_p[2].initialize("c", 1, 3, -1, 3);
		        //o_p[3].initialize("d", 2, 4, 8, 1);
		    
		    //output() run before solution() will display the initial process parameters
		           output();
		           solution();
		        //output();
		        System.out.println("The sequence is: "+s_sequence);
		        System.out.println("Avaerage Wait Time: "+waitTime()+"/"+i_counter);
		        //System.out.println(waitTime()/i_counter);
		        System.out.println("Total Tardiness: "+tardiness());
		        // System.out.println(tardiness());
		        //System.exit(0);
		        sumOfAvs += waitTime()/10;
		        sumOfTards += tardiness();
	    	}
	    System.out.println("Average of the Average Wait times: "+sumOfAvs/trials);
	    System.out.println("Average Tardiness:"+sumOfTards/trials);
	  	}
	

}

