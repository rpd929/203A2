import java.io.*;
import java.text.*;


public class a2 {

   
    
    static double currentTime = 0;
    static int numberOfServers = 0;

   

    public static class CustomerQueue {
        static Customer[] customerQueue =  new Customer[500];
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static int index = 0;
        static int largestQueueSize = 0;
        static int customerAvgQueueSum = 0;
        static int customerAvgQueueCount;
      

        public static void push(Customer customer) {
            customerQueue[index] = customer;
            //System.out.println("Adding Customer: " + totalCustomers  );
            index = index + 1;
            customerAvgQueueCount++;
            customerAvgQueueSum = customerAvgQueueSum + index;
            if(index > largestQueueSize) {
                largestQueueSize = index;
            }
        }
    
        public static Customer top() {
    
            return customerQueue[index];
            
        }
    
        public static void pop() {
            index = index - 1;      
            customerAvgQueueCount++;
            customerAvgQueueSum = customerAvgQueueSum + index;
        }
    
        public static Boolean isEmpty() {   
            return(index == 0);
        }
      
    }

    public static class EventQueue {
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static boolean isEmpty = true;
        static int index = 0;
        static int totalEvents = 0;

        static Event[] eventQueue = new Event[100];
     

        public static void enqueue(Event event) {
            if(isEmpty) {
                eventQueue[enqueueIndex] = event;
                isEmpty = false;
            
            } else {
               eventQueue[enqueueIndex] = event;

            }

            //System.out.println("Adding Event: " + event.toString());
            enqueueIndex++;
        }

        public static Event dequeue() {
            
            Event event = EventQueue.eventQueue[0];
            EventQueue.swap(EventQueue.eventQueue, 0, index -1);
            EventQueue.eventQueue[index-1] = null;
            index = index - 1;
            EventQueue.makeheap(EventQueue.eventQueue);

            if(index >=  499 ||index == 0) {
                index = 0;
                isEmpty = true;
            }

            return event;
        }

        public static boolean isEmpty() {
            return isEmpty;
        }

        public static void push(Event event) {
           
            if(isEmpty ) {
                eventQueue[index] = event;
                isEmpty = false;
            
            } else {
               eventQueue[index] = event;

            }
            index++;
            totalEvents++;
            
        }
    
        public static void siftUp(int newEntryIndex) {
        
            int parent;
        
            if(newEntryIndex == 0) {
                return;
            } else {
                parent = (newEntryIndex -1) / 2;
                //System.out.println("Parent of index: " + newEntryIndex + " is index: " + parent);
                if (eventQueue[parent].eventTime < eventQueue[newEntryIndex].eventTime) {
                    return;
                } else {
                    if(index < 500) {
                        swap(eventQueue, newEntryIndex, parent);
                        siftUp(parent);
                    }
                }
        
            }
        }
        
        public static Event[] swap(Event[] eventQueue, int position1, int position2) {
            //System.out.println("Swapping: " + eventQueue[position1] + " with: " + eventQueue[position2]);
            Event temp = eventQueue[position1];
            eventQueue[position1] = eventQueue[position2];
            eventQueue[position2] = temp;
            return eventQueue;
        }

        public static Event[] makeheap(Event[] eventQueue) {
            for (int i = index  / 2; i >= 0; i--)  
            {
                siftDown(i);
            }
        
            return eventQueue;
        }

        public static void siftDown(int newNum) {

            //Child of the newNumber position is 2 * newNumber position + 1.
            int child = newNum * 2 + 1;
    
            //If the child position is not off the end of the array...
            if (child + 1 < index ) { 
    
                // If the right child is larger than the left, set child = right. 
                if (eventQueue[child].eventTime > eventQueue[child + 1].eventTime) {
                    child = child + 1;
                }
    
                /*If the parent is less thant than the child, call the swap function 
                    to swap parent and child, then call siftDown() to test whether
                    new parent is larger than it's two children. */
                if (eventQueue[newNum].eventTime > eventQueue[child].eventTime) {               
                    swap(eventQueue, newNum, child);
                    siftDown(child);
                }
            } else {
                return;
            }
    }

    }


    public static class Event {
        int eventType;
        double eventTime, tallyTime;
        boolean paymentType;
        int serverNumber;
        Customer customer;

        public Event(int eventType, double arrival, double tally, boolean payment, Customer customer) {
            this.eventType = eventType;
            eventTime = arrival;
            tallyTime = tally;
            paymentType = payment;
            this.customer = customer;

        }

        public Event(int eventType, double finishTime, double service, boolean payment, int server) {
            this.eventType = eventType;
            eventTime = finishTime;
            tallyTime = service;
            paymentType = payment;
            this.serverNumber = server;

        }

        public String toString() {
            String eventString = "Arrival: " + eventTime + " Tally: " + tallyTime + " Payment: ";
            if(paymentType) {
                eventString = eventString + " Card";

            } else {
                eventString = eventString + " Cash";

            }
            return eventString;

        }

    }
    public static class Customer {

        double arrivalTime, tallyTime;
        boolean paymentType;

        public Customer(double arrival, double tally, boolean payment) {
            arrivalTime = arrival;
            tallyTime = tally;
            paymentType = payment;

        }


        public String toString() {
            String customerString = "Arrival: " + arrivalTime + " Tally: " + tallyTime + " Payment: ";
            if(paymentType) {
                customerString = customerString + " Card";

            } else {
                customerString = customerString + " Cash";

            }

            return customerString;

        }
    }

    

    public static class Server {
        boolean busyFlag = false;
        double efficiencyFactor;
        int serverNumber;
       
        public Server(double efficiencyFactor) {
            this.efficiencyFactor = efficiencyFactor;
            this.serverNumber = numberOfServers;
            numberOfServers++;
        
        }

        public String toString() {

            return "Server: " + serverNumber + " Efficiency: " + efficiencyFactor;

        }

    }

    public static class ServerArray {
        static int index = 0;
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static boolean isEmpty = true;
        static int fastestServer = -1;
        static int totalServersAvailable;
        static Server[] serverQueue = new Server[50];
        static int[] customersServed = new int[20];

        public static void enqueue(Server server) {
            if(isEmpty) {
                serverQueue[enqueueIndex] = server;
                isEmpty = false;
            
            } else {
               serverQueue[enqueueIndex] = server;

            }

            //System.out.println("Adding server: " + server.toString());
            enqueueIndex++;
        }

        public static boolean hasIdleServer() {
            
            for(int x = 0; x < index; x++) {  
                if(serverQueue[x].busyFlag == false) {
                    return true;
                } else {
                
                }
            }
            return false;
        }

        public static int getServerPosition(int serverNumber) {
            for(int x = 0; x < index; x++ ) {
                if(serverQueue[x].serverNumber == serverNumber) {
                    return x;
                }
            }

            return -1;
        }

        public static int findFastestIdleServer() {
            int fastest = -1;

            for(int x = 0; x < index; x ++) {
                if(serverQueue[x].busyFlag == false ) {
                    if(fastest == -1) {
                        fastest = x;
                    } else if(serverQueue[x].efficiencyFactor < serverQueue[fastest].efficiencyFactor) {
                        fastest = x;
                    }
                }
            }
            customersServed[fastest]++;
            //System.out.println("Fastest server was: " + serverQueue[fastest].serverNumber + " Busy: " + serverQueue[fastest].busyFlag );
            return fastest;
        }

        public static Server dequeue() {
            
           
            Server server = serverQueue[0];
           // System.out.println("Event time: " + server.eventTime);
            swap(serverQueue, 0, index -1);
            serverQueue[index-1] = null;
            index = index - 1;
            makeheap(serverQueue);

            if(dequeueIndex >=  499 || serverQueue[dequeueIndex] == null) {
                enqueueIndex = 0;
                dequeueIndex = 0;
                isEmpty = true;
            }

            return server;
        }

        public static boolean isEmpty() {
            return isEmpty;
        }

        public static void push(Server server) {
            if(isEmpty) {
                serverQueue[index] = server;
                isEmpty = false;
            
            } else {
               serverQueue[index] = server;

            }
          
            index++;
            
        }
    
        public static void siftUp(int newEntryIndex) {
        
            int parent;
        
            if(newEntryIndex == 0) {
                return;
            } else {
                parent = (newEntryIndex -1) / 2;
                //System.out.println("Parent of index: " + newEntryIndex + " is index: " + parent);
                if (serverQueue[parent].efficiencyFactor < serverQueue[newEntryIndex].efficiencyFactor) {
                    return;
                } else {
                    if(index < 500) {
                        swap(serverQueue, newEntryIndex, parent);
                        siftUp(parent);
                    }
                }
        
            }
        }
        
        public static Server[] swap(Server[] serverQueue, int position1, int position2) {
            //System.out.println("Swapping: " + serverQueue[position1] + " with: " + serverQueue[position2]);
            Server temp = serverQueue[position1];
            serverQueue[position1] = serverQueue[position2];
            serverQueue[position2] = temp;
            return serverQueue;
        }

        public static Server[] makeheap(Server[] serverQueue) {
            for (int i = index  / 2; i >= 0; i--)  
            {
                siftDown(i);
            }
        
            return serverQueue;
        }

        public static void siftDown(int newNum) {

            //Child of the newNumber position is 2 * newNumber position + 1.
            int child = newNum * 2 + 1;
    
            //If the child position is not off the end of the array...
            if (child + 1 < index ) { 
    
                // If the right child is larger than the left, set child = right. 
                if (serverQueue[child].efficiencyFactor > serverQueue[child + 1].efficiencyFactor) {
                    child = child + 1;
                }
    
                /*If the parent is less thant than the child, call the swap function 
                    to swap parent and child, then call siftDown() to test whether
                    new parent is larger than it's two children. */
                if (serverQueue[newNum].efficiencyFactor > serverQueue[child].efficiencyFactor) {               
                    swap(serverQueue, newNum, child);
                    siftDown(child);
                }
            } else {
                return;
            }
    }

    static boolean isMinHeap(Server[] heap, int i, int n) { 
        // If a leaf node  
        if (i > (n - 2) / 2) { 

            return true; 
           
        } 
        if((2 * i + 2) == n) {
            if (heap[i].efficiencyFactor <= heap[(2 * i + 1)].efficiencyFactor) {
                //System.out.println("Server #" + heap[i].serverNumber + " EF: " + heap[i].efficiencyFactor + " less than Server#" +  heap[(2 * i + 1)].serverNumber + " EF: " +  heap[(2 * i + 1)].efficiencyFactor);
                return true;
            } else {
                return false;
            }

        }
        // If an internal node and is greater than its children, and  
        // same is recursively true for the children  
        if (heap[i].efficiencyFactor <= heap[(2 * i + 1)].efficiencyFactor && (heap[i].efficiencyFactor <= heap[(2 * i + 2)].efficiencyFactor || (2 * i + 2)  == n) 
                && isMinHeap(heap, 2 * i + 1, n) && isMinHeap(heap, 2 * i + 2, n) ) { 
            
            //System.out.println("Server #" + heap[i].serverNumber + " EF: " + heap[i].efficiencyFactor + " less than Server#" +  heap[(2 * i + 1)].serverNumber + " EF: " +  heap[(2 * i + 1)].efficiencyFactor + " and Server#" + heap[(2 * i + 2)].serverNumber + " EF: " + heap[(2 * i + 2)].efficiencyFactor );
            return true; 
        } 
        //System.out.println(heap[i] + "  greater than " + heap[(2 * i + 1)] + " or " + heap[(2 * i + 2)]);
        return false; 
    } 

      
    }





    public static void main(String[] args) {
       
        File file = new File("ass2.txt"); 
        int totalCustomers = 0;
        double[] serverBusyTime = new double[20];
        int noWaitCount = 0;
        double averageWaitTimeSum = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            boolean firstServer = true;
            boolean firstCustomer = true;
            

            // This section adds servers into a server heap
            String newCustomerLine = "";
            String[] parts;
            if(br.ready()) {
                newCustomerLine = br.readLine();
                newCustomerLine = br.readLine();
                parts = newCustomerLine.split("\t"); 
               
                while(parts.length == 1) {     
                    double serverEfficiency = Double.parseDouble(parts[0]);
                    Server newServer = new Server(serverEfficiency);
                    
                    ServerArray.push(newServer);
                    ServerArray.siftUp(ServerArray.index -1);

                    newCustomerLine = br.readLine();
                    parts = newCustomerLine.split("\t"); 
                }
                
            }
            
            //Read first customer record in from the file and add to eventQueue
            //newCustomerLine = br.readLine();
            //System.out.println(newCustomerLine);
            parts = newCustomerLine.split("\t"); 

            double arrivalTime = Double.parseDouble(parts[0]);
            double tallyTime = Double.parseDouble(parts[1]);
            boolean paymentType;
            
            if(parts[2].equals("card")) {
               paymentType = true;
            } else {
               paymentType = false;
            }
            
            Customer cust = new Customer(arrivalTime, tallyTime, paymentType);
            Event event = new Event(0, arrivalTime, tallyTime, paymentType, cust);
            EventQueue.push(event);
            EventQueue.siftUp(EventQueue.index - 1);



            //While the eventQueue isnt empty, get the newest event and perform required functions depending on event type
            while(EventQueue.isEmpty() == false) {
                Event evt = EventQueue.dequeue();
                
                //System.out.println("Event dequeued: " + evt.eventType + " " + evt.toString());
                currentTime = evt.eventTime;
                
                /*If the EventType is a CustomerArrival Event, set the server’s idle flag to busy,calculate the 
                server’s finish time from event's customer data, add ServerFinish event to the event queue */
                if(evt.eventType == 0) {  
                    totalCustomers++;   
                    if(ServerArray.hasIdleServer()) {
                        int fastestServer = ServerArray.findFastestIdleServer();
                        noWaitCount++;
                        ServerArray.serverQueue[fastestServer].busyFlag = true;
                        
                        //System.out.println("Customer#" + totalCustomers + " arrival, assigned to Server#" + ServerArray.serverQueue[fastestServer].serverNumber + " busy: " +  ServerArray.serverQueue[fastestServer].busyFlag);  
                        //System.out.println();
                        double paymentTime = 0;
                        
                        if(evt.paymentType) {
                            paymentTime = 0.7;
                        } else {
                            paymentTime = 0.3;
                        }

                        double serviceTime =  (evt.tallyTime *  ServerArray.serverQueue[fastestServer].efficiencyFactor) + paymentTime;
                        
                        double finishTime = currentTime + serviceTime;

                        Event serverFinish = new Event(1, finishTime, serviceTime, evt.paymentType, ServerArray.serverQueue[fastestServer].serverNumber);
                        double currentBusyCount = serverBusyTime[fastestServer];
                        serverBusyTime[fastestServer] = currentBusyCount + serviceTime;
                        EventQueue.push(serverFinish);
                        EventQueue.siftUp(EventQueue.index-1);
                        //System.out.println("Customer#" + totalCustomers + " arrival with service time: " + serviceTime + ", assigned to Server#" + ServerArray.serverQueue[fastestServer].serverNumber + " busy: " +  ServerArray.serverQueue[fastestServer].busyFlag);  

                       
                        
                    }  else {
                        // If there are no available servers, add the event's customer to the customerQueue
                        CustomerQueue.push(evt.customer);
                        //System.out.println("Customer: " + totalCustomers + " added to queue");
                        //System.out.println();
                    }
                    //If not end of the file, read in next customerArrival and add it to the EventQueue
                    if(br.ready()) {
                        newCustomerLine = br.readLine();
                        parts = newCustomerLine.split("\t"); 
            
                        arrivalTime = Double.parseDouble(parts[0]);
                        tallyTime = Double.parseDouble(parts[1]);
                    
                        
                        if(parts[2].equals("card")) {
                        paymentType = true;
                        } else {
                        paymentType = false;
                        }
                        
                        cust = new Customer(arrivalTime, tallyTime, paymentType);
                        event = new Event(0, arrivalTime, tallyTime, paymentType, cust);
                        EventQueue.push(event);
                        EventQueue.siftUp(EventQueue.index - 1);
                        //System.out.println("Customer Arrival# " + totalCustomers + " " + event.toString() );
                        //System.out.println();
                }
            // Else, it must be a serverFinish Event and we must set server to idle and collect stats
            } else {
                int serverNumber = evt.serverNumber;
                int serverPosition = ServerArray.getServerPosition(serverNumber);
                ServerArray.serverQueue[serverPosition].busyFlag = false;
                //System.out.println(ServerArray.serverQueue[serverPosition].busyFlag + " Server Finish: server#" + ServerArray.serverQueue[serverPosition].serverNumber + " finished serving " + evt.toString());
                //System.out.println();
                //If there are customers in the CustomerQueue...
                if(CustomerQueue.isEmpty() == false) {
                    
                    CustomerQueue.pop();
                    Customer customer = CustomerQueue.top();
                    double waitTime = currentTime - customer.arrivalTime;
                    averageWaitTimeSum = averageWaitTimeSum + waitTime;
                   

                    //Find fastest idle server and set server flag to busy, calculate finish time
                    int fastestServer = ServerArray.findFastestIdleServer();
                    
                    ServerArray.serverQueue[fastestServer].busyFlag = true;
                    double paymentTime = 0;
                        
                    if(evt.paymentType) {
                        paymentTime = 0.7;
                    } else {
                        paymentTime = 0.3;
                    }

                    double serviceTime =  (customer.tallyTime *  ServerArray.serverQueue[fastestServer].efficiencyFactor) + paymentTime;
                    double currentBusyCount = serverBusyTime[fastestServer];
                    //System.out.println("curent BC: " + currentBusyCount + " ST: " + serviceTime);
                    serverBusyTime[fastestServer] = currentBusyCount + serviceTime;
                    double finishTime = currentTime + serviceTime;

                    Event serverFinish = new Event(1, finishTime, serviceTime, customer.paymentType, ServerArray.serverQueue[fastestServer].serverNumber);
                    //System.out.println("Queued Customer " + " assigned to Server#" + ServerArray.serverQueue[fastestServer].serverNumber + "finish time: " + finishTime + ", tallyTime: " + evt.tallyTime + ", serviceTime: " + serviceTime +  ", EF: " + ServerArray.serverQueue[fastestServer].efficiencyFactor + ", PTime: " + paymentTime);
                    //System.out.println();
                    EventQueue.push(serverFinish);
                    EventQueue.siftUp(EventQueue.index-1);

                } else {
                    //System.out.println("Customer queue is empty!");
                }
            }
        }
    
            
            System.out.println("total customers: " + totalCustomers);
            System.out.println("total time: " + trimDouble(currentTime));
            System.out.println("largest queue length: " + CustomerQueue.largestQueueSize);
            System.out.println("Average Size of Customer Queue: " + trimDouble(((double)CustomerQueue.customerAvgQueueSum / (double)CustomerQueue.customerAvgQueueCount)));
            System.out.println("Average wait per customer: " + trimDouble((averageWaitTimeSum / totalCustomers)));
            System.out.println("% Customers with no wait: " + (trimDouble((noWaitCount / (double)totalCustomers) * 100)) + "%");
            System.out.println();
            
            for(int x = 0; x < ServerArray.index; x++) {
                double serverIdleTime = currentTime - serverBusyTime[x];
                System.out.println("Server#:" + ServerArray.serverQueue[x].serverNumber + " \t Efficiency: " +  trimDouble(ServerArray.serverQueue[x].efficiencyFactor) +  "\t Customers Served: " + ServerArray.customersServed[x] + "\t Total Idle Time: " + trimDouble(serverIdleTime));
            }
            //System.out.println(EventQueue.totalEvents);
           
          }  catch( Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
        }
    }

    public static String trimDouble(double d) {
        
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }
}

/* The structures used to store the Customer Queue was a stack, 
    which allowed the customers to be accessed in the correct 
    order with only no comparissons as the correct customer is 
    always at the front. The Event Queue and the Servers were 
    kept in a heap in order to optimise for speed.This allowed 
    for both the Events and the servers to be accessed quickly, 
    either by always taking from the 1st position in the case 
    of Event, and then re-making the heap to push the highest 
    priority to the 1st position, or searching through the heap 
    and finding the fastest idle server in the case of ServerQueue. */

    
