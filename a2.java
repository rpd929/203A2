import java.io.*;
public class a2 {

   
    
    static double currentTime = 0;

    public static class CustomerQueue {
        static Customer[] customerQueue =  new Customer[500];
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static int index = 0;

        public static void push(Customer customer) {
            customerQueue[index] = customer;
            System.out.println("Adding: " + customer.toString());
            index = index + 1;
        }
    
        public static Customer top() {
    
            return customerQueue[index];
            
        }
    
        public static void pop() {
            index = index - 1;      
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

        static Event[] eventQueue = new Event[500];
        static ServerArray.Node root = null;

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
            EventQueue.swap(EventQueue.eventQueue, 0, EventQueue.index -1);
            EventQueue.eventQueue[EventQueue.index-1] = null;
            EventQueue.index = EventQueue.index - 1;
            EventQueue.makeheap(EventQueue.eventQueue);

            if(dequeueIndex >=  499 || eventQueue[dequeueIndex] == null) {
                enqueueIndex = 0;
                dequeueIndex = 0;
                isEmpty = true;
            }

            return event;
        }

        public static boolean isEmpty() {
            return isEmpty;
        }

        public static void push(Event event) {
            System.out.println("Inserting " + event.eventTime + " at position:  " + index );
            eventQueue[index] = event;
            index++;
            
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

    static boolean isMinHeap(Event[] heap, int i, int n) { 
        // If a leaf node  
        if (i > (n - 2) / 2) { 
            System.out.println("here");
            return true; 
           
        } 

        if((2 * i + 2) == n) {
            if (heap[i].eventTime <= heap[(2 * i + 1)].eventTime) {
                return true;
            } else {
                return false;
            }

        }
        // If an internal node and is greater than its children, and  
        // same is recursively true for the children  
        if (heap[i].eventTime <= heap[(2 * i + 1)].eventTime && (heap[i].eventTime <= heap[(2 * i + 2)].eventTime || (2 * i + 2)  == n) 
                && isMinHeap(heap, 2 * i + 1, n) && isMinHeap(heap, 2 * i + 2, n) ) { 
            return true; 
        } 
        //System.out.println(heap[i] + "  greater than " + heap[(2 * i + 1)] + " or " + heap[(2 * i + 2)]);
        return false; 
    } 


    }

    public static class ServerFinish {
        double finishTime;
        int serverNumber;
    }

    public static class Event {
        int eventType;
        double eventTime, tallyTime;
        boolean paymentType;
        Server server;

        public Event(int eventType, double arrival, double tally, boolean payment) {
            this.eventType = eventType;
            eventTime = arrival;
            tallyTime = tally;
            paymentType = payment;

        }

        public Event(int eventType, double arrival, double tally, boolean payment, Server server) {
            this.eventType = eventType;
            eventTime = arrival;
            tallyTime = tally;
            paymentType = payment;
            this.server = server;

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
        }

        public String toString() {

            return "Server: " + serverNumber + " Efficiency: " + efficiencyFactor;

        }

        public void addCustomer(Customer cust) {
            busyFlag = true;
            double serviceTime;
        
            if(cust.paymentType) {
               serviceTime = (cust.tallyTime * efficiencyFactor) + 0.7;

            } else {
               serviceTime = (cust.tallyTime * efficiencyFactor) + 0.7;

            }

            double serverFinishTime = currentTime + serviceTime;

            Event event = new Event(1, serverFinishTime, cust.tallyTime, cust.paymentType, this);

            EventQueue.enqueue(event);
        }

        public void removeCustomer() {
;
            busyFlag = false;

        }

    }

    public static class ServerArray {
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static boolean isEmpty = true;
        static int fastestServer = 1;
        static Node root = new Node();

        public static class Node {
            Server contents = null;
            Node left;
            Node right;
        }
        public static Node find(Customer stuffToFind, Node node) {
            Node returnNode;
            if(stuffToFind.arrivalTime == node.contents.efficiencyFactor) {
                return node;
            } else if (stuffToFind.arrivalTime < node.contents.efficiencyFactor) {
                returnNode = find(stuffToFind, node.left);
                return returnNode;
            } else {
                returnNode =find(stuffToFind, node.right); 
                return returnNode;
            }
        }
    
        public static void insert(Server server, Node node) {
          
            Node next;
            boolean left;
        
            if(server.efficiencyFactor == node.contents.efficiencyFactor) {   
                return;
            } else if (server.efficiencyFactor < node.contents.efficiencyFactor) {
                next = node.left;
                left = true;
               
        
            } else {
              
                next = node.right;
                left = false;
            }
            
            if(next != null) {
                insert(server, next);
            } else {
                next = new Node();
              
            
                next.contents = server;
             
                if(left) {
                    node.left = next;
                    System.out.println("Inserting " + server.efficiencyFactor + " left");
                   
                } else {
                    node.right = next;
                    System.out.println("Inserting " + server.efficiencyFactor + " left");
                
                }
                
            }
        }
    
    
    
        public static Node insertFirst(Server server) {
    
            Node start = new Node();
            start.contents = server;
        
            return start;
        }
    
        public static void visit(Node node) {
            if(node.left != null) {
                visit(node.left);
            }

            System.out.println(node.contents.efficiencyFactor);
        
            if(node.right != null) {
                visit(node.right);
            }
        }
        
        

        static Server[] serverArray = new Server[20];

        public static void enqueue(Server server) {
            if(isEmpty) {
                serverArray[enqueueIndex] = server;
                isEmpty = false;
            
            } else {
                serverArray[enqueueIndex] = server;

            }

            System.out.println("Adding: " + server.toString());
            server.serverNumber = enqueueIndex;
            enqueueIndex++;
            serverArray[enqueueIndex] = server;
            
            findSmallest();
        }

        public static boolean isEmpty() {
            return isEmpty;
        }

        public static Server dequeue() {
            
            Server returnedServer = serverArray[dequeueIndex];
            System.out.println("Dequeuing: " + returnedServer.toString());
            serverArray[dequeueIndex] = null;
            dequeueIndex++;

            if(dequeueIndex >=  20 || serverArray[dequeueIndex] == null) {
                enqueueIndex = 0;
                dequeueIndex = 0;
                isEmpty = true;
            }

            findSmallest();
            return returnedServer;
            
        }

        public static void findSmallest() {

            for(int x = 0; x < enqueueIndex; x++) {
                if (serverArray[x].efficiencyFactor < serverArray[fastestServer].efficiencyFactor && serverArray[x].busyFlag == false) {
                    fastestServer = x;
                    System.out.println("fastest = " + fastestServer);
                }
                
            }
        }

        
    }





    public static void main(String[] args) {
       
        File file = new File("test.txt"); 
        ServerArray serverArray = new ServerArray();
        
    
    
      
        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); 
            boolean first = true;
            while (br.ready()) {
                String newCustomerLine = br.readLine();
                String[] parts = newCustomerLine.split("\t");
                System.out.println("partsLength: " + parts.length);
                if(parts.length == 1) {

                    if(first) {
                        first = false;
                        double serverEfficiency = Double.parseDouble(parts[0]);
                        Server newServer = new Server(serverEfficiency);
                        ServerArray.Node node = new ServerArray.Node();
                        node = ServerArray.insertFirst(newServer);
                        ServerArray.root = node;


                    } else {
                        double serverEfficiency = Double.parseDouble(parts[0]);
                        Server newServer = new Server(serverEfficiency);
                        ServerArray.Node node = new ServerArray.Node();
                        node.contents = newServer;
                        ServerArray.insert(newServer, ServerArray.root);

                    }
                } else {
                    double arrivalTime = Double.parseDouble(parts[0]);
                    double tallyTime = Double.parseDouble(parts[1]);
                    boolean paymentType;
                    
                    if(parts[2].equals("card")) {
                       paymentType = true;
                    } else {
                       paymentType = false;
                    }
                    
                    Customer cust = new Customer(arrivalTime, tallyTime, paymentType);
                    Event event = new Event(0, arrivalTime, tallyTime, paymentType);
                    EventQueue.push(event);
                    EventQueue.siftUp(EventQueue.index - 1);
                   
                }   
                /*
                for(int x = 0; x < 5; x++) { 
                    if(x == 4) {
                        System.out.print(EventQueue.eventQueue[x].eventTime);
                    } else {
                        System.out.print(EventQueue.eventQueue[x].eventTime + ", ");
                    }
                
                }
                */
            }
            
            for(int x = 0; x < EventQueue.index ; x++) { 
                if(x == EventQueue.index -1) {
                    System.out.println(EventQueue.eventQueue[x].eventTime);
                } else {
                    System.out.print(EventQueue.eventQueue[x].eventTime + ", ");
                }
            
            }

            if(EventQueue.isMinHeap(EventQueue.eventQueue, 0, EventQueue.index)) {
                System.out.println("Is Min Heap?: True");
            } else {
                System.out.println("Is Min Heap?: False");
            }

            for(int x = 0; x < EventQueue.index; x++) { 
               
                System.out.println(EventQueue.dequeue().eventTime);
            }

            ServerArray.visit(ServerArray.root);
          }  catch( Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
        }
        
        

    }
}

    
