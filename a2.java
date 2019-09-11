public class a2 {

   
    static int index = 0;

    public static class CustomerQueue {
        static boolean isEmpty = true;
        static Customer[] customerQueue =  new Customer[500];
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;


        public static void enqueue(Customer cust) {
            if(isEmpty) {
                customerQueue[enqueueIndex] = cust;
                isEmpty = false;
            
            } else {
                customerQueue[enqueueIndex] = cust;

            }

            System.out.println("Adding: " + cust.toString());
            enqueueIndex++;
        }

        public static boolean isEmpty() {
            return isEmpty;
        }

        public static Customer dequeue() {
            
            Customer cust = CustomerQueue.customerQueue[dequeueIndex];
            System.out.println("Dequeuing: " +cust.toString());
            customerQueue[dequeueIndex] = null;
            dequeueIndex++;

            if(dequeueIndex >=  499 || customerQueue[dequeueIndex] == null) {
                enqueueIndex = 0;
                dequeueIndex = 0;
                isEmpty = true;
            }

            return cust;
        }

        
    }

    public static class EventQueue {
        static int enqueueIndex = 0;
        static int dequeueIndex = 0;
        static boolean isEmpty = true;

        static Event[] eventQueue = new Event[500];
        static Node root = null;

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
            
            Event event = eventQueue[dequeueIndex];
            System.out.println("Dequeuing: " + event.toString());
            eventQueue[dequeueIndex] = null;
            dequeueIndex++;

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


    }

    public static class Event {
        int eventType;
        double arrivalTime, tallyTime;
        boolean paymentType;

        public Event(int eventType, double arrival, double tally, boolean payment) {
            this.eventType = eventType;
            arrivalTime = arrival;
            tallyTime = tally;
            paymentType = payment;

        }

        public String toString() {
            String eventString = "Arrival: " + arrivalTime + " Tally: " + tallyTime + " Payment: ";
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

    public static class Node {
        Customer contents = null;
        Node left;
        Node right;
    }

    public static Node find(Customer stuffToFind, Node node) {
        Node returnNode;
        if(stuffToFind.arrivalTime == node.contents.arrivalTime) {
            return node;
        } else if (stuffToFind.arrivalTime < node.contents.arrivalTime) {
            returnNode = find(stuffToFind, node.left);
            return returnNode;
        } else {
            returnNode =find(stuffToFind, node.right); 
            return returnNode;
        }
    }

    public static void insert(Customer cust, Node node) {
   
        Node next;
        boolean left;
    
        if(cust.arrivalTime == node.contents.arrivalTime) {
          
            return;
        } else if (cust.arrivalTime < node.contents.arrivalTime) {
            next = node.left;
            left = true;
    
        } else {
            next = node.right;
            left = false;
        }
        
        if(next != null) {
            insert(cust, next);
        } else {
            next = new Node();
        
            next.contents = cust;
         
            if(left) {
                node.left = next;
               
            } else {
                node.right = next;
            
            }
            
        }
    }

    public static Node insertFirst(Customer cust) {

        Node start = new Node();
        start.contents = cust;
    
        return start;
    }

    public static void visit(Node node) {
        if(node.left != null) {
            visit(node.left);
        }
    
        Event event = new Event(0, node.contents.arrivalTime, 0, false);
        EventQueue.enqueue(event);
     
    
    
        if(node.right != null) {
            visit(node.right);
        }
    }
    
    



    public static void main(String[] args) {
       
        
        System.out.println("----------- Customer Enqueue --------------");
        
        for(int x = 0; x < 10; x++) {
            
            double arrival = Math.random() * 100;
            Customer cust = new Customer(arrival, 0, false);
            CustomerQueue.enqueue(cust);
            //System.out.println(CustomerQueue.customerQueue[CustomerQueue.enqueueIndex- 1].toString());

        }

        System.out.println("-----------Customer Dequeue --------------");

        while(!CustomerQueue.isEmpty()) { 
           //System.out.println("Dequeuing: " + CustomerQueue.customerQueue[CustomerQueue.dequeueIndex].toString());
            Customer cust = CustomerQueue.dequeue();

            if(EventQueue.root == null) {
                EventQueue.root = insertFirst(cust);
            }

            insert(cust, EventQueue.root);

        }


        System.out.println("----------- Event Dequeue --------------");
        
        visit(EventQueue.root);

        while(!EventQueue.isEmpty()) { 
            //System.out.println("Dequeuing: " + CustomerQueue.customerQueue[CustomerQueue.dequeueIndex].toString());
             Event event = EventQueue.dequeue();
 
         }



      
        
        

    }
}

    