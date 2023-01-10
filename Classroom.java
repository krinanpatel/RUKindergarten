package kindergarten;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student
 * in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given
 * seat), and
 * - a Student array parallel to seatingAvailability to show students filed into
 * seats
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in
 * studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs; // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability; // represents the classroom seats that are available to students
    private Student[][] studentsSitting; // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom(SNode l, SNode m, boolean[][] a, Student[][] s) {
        studentsInLine = l;
        musicalChairs = m;
        seatingAvailability = a;
        studentsSitting = s;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in
     * line.
     * 
     * Reads students from input file and inserts these students in alphabetical
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the
     * file, say x
     * 2) x lines containing one student per line. Each line has the following
     * student
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom(String filename) {
        StdIn.setFile(filename);
        int numberofstudents = StdIn.readInt();

        Student[] s = new Student[numberofstudents];

        for (int i = 0; i < numberofstudents; i++) {
            String firstName = StdIn.readString();
            String lastName = StdIn.readString();
            int height = StdIn.readInt();
            s[i] = new Student(firstName, lastName, height);
        }
        for (int i = 0; i < numberofstudents; i++) {
            for (int j = i + 1; j < numberofstudents; j++) {
                if (s[i].compareNameTo(s[j]) > 0) {
                    Student temp = s[j];
                    s[j] = s[i];
                    s[i] = temp;
                }
            }
        }
        if (studentsInLine == null) {
            studentsInLine = new SNode(s[0], null);
        }
        SNode current = studentsInLine;

        for (int i = 1; i < numberofstudents; i++) {
            SNode newNode = new SNode(s[i], null);
            current.setNext(newNode);
            current = current.getNext();
        }

    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of
     * available seats inside the classroom. Imagine that unavailable seats are
     * broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an
     * available seat)
     * 
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();
        int c = StdIn.readInt();

        seatingAvailability = new boolean[r][c];
        studentsSitting = new Student[r][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                seatingAvailability[i][j] = StdIn.readBoolean();
                // boolean seats = StdIn.readBoolean();
                // seatingAvailability[i][j] = seats;
            }
        }
       
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the
     * front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into
     * studentsSitting according to
     * seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents() {
        int row = seatingAvailability.length;
        int col = seatingAvailability[0].length;
        SNode temp = studentsInLine;
        //studentsInLine=musicalChairs; //fix
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (musicalChairs != null) {
                    studentsSitting[i][j] = musicalChairs.getStudent();
                    musicalChairs = null; //fix this bro
                }
                else if (temp != null) {
                    if (seatingAvailability[i][j] == true) {
                        studentsSitting[i][j] = temp.getStudent();
                        temp = temp.getNext();
                        studentsInLine = studentsInLine.getNext();
                    }
                }
  
            }
  
        }
    }
 
       /* //studentsInLine=musicalChairs; //fix
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (musicalChairs != null) {
                    studentsSitting[i][j] = musicalChairs.getStudent();
                    musicalChairs = null; //fix this bro
                }
                else if (temp != null) {
                    if (seatingAvailability[i][j] == true) {
                        studentsSitting[i][j] = temp.getStudent();
                        temp = temp.getNext();
                        studentsInLine = studentsInLine.getNext();
                    }
                }

            }

        }*/

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then
     * moves
     * into second row.
     */
    public void insertMusicalChairs() {
         
      SNode firstptr = null;
      for (int i=0; i<studentsSitting.length; ++i){
       for (int j=0; j<studentsSitting[0].length; ++j){
           if (studentsSitting[i][j]!=null){
               if (firstptr==null){
                   musicalChairs = new SNode(studentsSitting[i][j], null);
                   firstptr=musicalChairs;
                   studentsSitting[i][j]=null;
               } else {
                   musicalChairs.setNext(new SNode (studentsSitting[i][j], null));
                   studentsSitting[i][j]=null;
                   musicalChairs = musicalChairs.getNext();
               }
 
 
          
               }
           }
       }
       musicalChairs.setNext(firstptr);
      }

    /*   SNode firstptr = null;
       for (int i=0; i<studentsSitting.length; ++i){
        for (int j=0; j<studentsSitting[0].length; ++j){
            if (studentsSitting[i][j]!=null){
                if (firstptr==null){
                    musicalChairs = new SNode(studentsSitting[i][j], null);
                    firstptr=musicalChairs;
                    studentsSitting[i][j]=null;
                } else {
                    musicalChairs.setNext(new SNode (studentsSitting[i][j], null));
                    studentsSitting[i][j]=null;
                    musicalChairs = musicalChairs.getNext();
                }


            
                }
            }
        }
        musicalChairs.setNext(firstptr);*/
    
       
       
       
       
       
       
        // SNode oldLast = null;
        // for (int i = 0; i < studentsSitting.length; i++) {
        //     for (int j = 0; j < studentsSitting[0].length; j++) {
        //         SNode musicalChairs = new SNode(studentsSitting[i][j], null);
        //             musicalChairs.setNext(musicalChairs);
        //         } else {
        //             musicalChairs.setNext(oldLast.getNext());
        //             oldLast.setNext(musicalChairs);
        //         }
        //     }
        // }



    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is
     * only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using
     * StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first
     * student in the
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in
     * studentsInLine
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students
     * can be seated.
     */
    public void playMusicalChairs() {
       int overall=1;
        SNode current1 = musicalChairs.getNext();
        SNode front = musicalChairs;
        while (current1!=front){
            overall++;
            current1=current1.getNext();
        }
        int total=overall-1;
        Student [] height = new Student [total];

        for (int i=0; i<total; i++){
            int rand = StdRandom.uniform(overall);
            SNode curr =musicalChairs;
            for (int j=0; j<rand; j++){
                curr=curr.getNext();

            }
            height[i]=curr.getNext().getStudent();
            if (curr.getNext()==musicalChairs){
                musicalChairs=curr;
            }
            if (curr==musicalChairs){
                musicalChairs.setNext(curr.getNext().getNext());
            }
            else curr.setNext(curr.getNext().getNext());
            overall--;
        }
        for (int i=0; i<total; i++){
            for (int j=i+1; j<total; j++){
                Student tempo;
                if (height[i].getHeight()>=height[j].getHeight()){
                    tempo=height[i];
                    height[i]=height[j];
                    height[j]=tempo;
                }
            }

        }
        SNode first = new SNode();
        SNode curr2=studentsInLine;
        if (studentsInLine==null){
            first.setStudent(height[0]);
            studentsInLine = first;
            curr2 = studentsInLine;
        }
        else { //doesnt run
            while (curr2.getNext()!=null){
                curr2=curr2.getNext();
            }
            first.setStudent(height[0]);
            curr2.setNext(first);
        }
        if (height.length>1){
            for (int i=1; i<height.length; i++){
                SNode temp=new SNode();
                temp.setStudent(height[i]);
                curr2.setNext(temp);
                curr2=curr2.getNext();

            }
        }
        seatStudents();
        }


        // while (musicalChairs != null) {
        //         int b = 1;
        //         SNode pointer = musicalChairs.getNext();
        //      while (pointer != musicalChairs) { // coutning how b
        //             b++;
        //             pointer = pointer.getNext();
        //         }
        //         int n = StdRandom.uniform(0, b+1); // finding one to remove
        //         for (int i = 0; i < b - 1; i++) {
        //                         if (n == 0) {
        //                             current = musicalChairs.getNext();
        //                             musicalChairs.setNext(current.getNext());
        //                             putinline(current);;
        //                             break;
            
        //                         } else if (n - 1 == i) {
        //                             current = ptr.getNext(); //current is i every time
        //                             ptr.setNext(current.getNext());
        //                             putinline(current);
        //                             break;
            
        //                         } else {
        //                             ptr = ptr.getNext(); // pointer is one less than i at any moment
        //                         }
        //                     }
        //                     b--;
        //         SNode current = null;
        //         for (int i = 0; i < b; i++) {
                    
        //         }
        //     }
//---------here

        // while (musicalChairs != null) {
        //     int b = 1;
        //     SNode pointer = musicalChairs.getNext();

        //     while (pointer != musicalChairs) {
        //         b++;
        //         pointer = pointer.getNext();
        //     }

        //     while (musicalChairs != musicalChairs.getNext()) {
        //         SNode ptr = musicalChairs.getNext();
        //         int n = StdRandom.uniform(0, b-1); //changed to b from b+1
        //         SNode current = null;
        //         for (int i = 0; i < b-1; i++) { 
        //             if (n == 0) {
        //                 current = musicalChairs.getNext();
        //                 musicalChairs.setNext(current.getNext());
        //                // current.setNext(null);
        //                 break;

        //             } else if (n - 1 == i) {
        //                 current = ptr.getNext(); //current is i every time
        //                 ptr.setNext(current.getNext());
        //                // current.setNext(null);
        //                 break;

        //             } else {
        //                 ptr = ptr.getNext(); // pointer is one less than i at any moment
        //             }
        //         }
        //         b--;
                
        //     SNode newptr = studentsInLine;
        //     for (int i=0; i<seatingAvailability.length; i++){
        //         for (int j=0; j<seatingAvailability[0].length; j++){
        //             if (seatingAvailability[i][j] && newptr != null && musicalChairs !=null){
        //                 studentsSitting[i][j] = musicalChairs.getStudent();
        //                 musicalChairs = null;
        //             } else if (seatingAvailability[i][j] && newptr!=null){
        //                 studentsSitting[i][j]= pointer.getStudent(); 
        //                 newptr=ptr.getNext();
        //                 }
        //         if (pointer==null){
        //             studentsInLine=null;
        //         }

                    
    



    /**
     * Insert a student to wherever the students are at (ie. whatever activity is
     * not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * 
     * @param firstName the first name
     * @param lastName  the last name
     * @param height    the height of the student
     */
    public void addLateStudent(String firstName, String lastName, int height) {

        if (musicalChairs!=null){
            SNode child = new SNode();
            Student identity = new Student(firstName, lastName, height);
            child.setStudent(identity);
            child.setNext(musicalChairs.getNext());
            musicalChairs.setNext(child);
            musicalChairs=musicalChairs.getNext();
        }
        boolean check =true;
        if (studentsSitting!=null){
            boolean none=false; //empty or not
            boolean isSitting = false;
            for (int i =0; i<studentsSitting.length; i++){
                for (int j=0; j<studentsSitting[0].length; j++){
                    if (studentsSitting[i][j] !=null){
                        isSitting=true;
                    } else if (studentsSitting[i][j]==null){
                        none=true;
                    }
                }
            }
            if (isSitting && none && check){
                Student identity = new Student(firstName, lastName, height);
                for (int i=0; i<studentsSitting.length; i++){
                    for (int j=0; j<studentsSitting[0].length; j++){
                        if (studentsSitting[i][j]==null && check && seatingAvailability[i][j]==true){//check
                            studentsSitting[i][j]=identity;
                            check=false;
                            break;
                        }

                }
            }
        }
    }
        if (musicalChairs ==null && check){
            if (studentsInLine!=null){
                SNode curr = studentsInLine;
                while (curr.getNext()!=null){
                    curr=curr.getNext();
                }
                SNode temp=new SNode();
                Student identity = new Student (firstName, lastName, height);
                temp.setStudent(identity);
                curr.setNext(temp);
            }
            else if (studentsInLine==null){
                SNode LateS = new SNode();
                Student identity= new Student(firstName, lastName, height);
                LateS.setStudent(identity); 
                studentsInLine=LateS;       
            }
        }
        

    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName  the student's last name
     */
    private boolean theName(String target, Student s) {
        String childName = s.getFullName();
        return (target.toLowerCase().equals(childName.toLowerCase()));
    }
    public void deleteLeavingStudent(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        if (studentsInLine != null) {
            SNode node = new SNode();
            node.setNext(studentsInLine);
            SNode current = node;
            while (current.getNext() != null) {
                if (theName(fullName, current.getNext().getStudent())) {
                    current.setNext(current.getNext().getNext());
                } else {
                    current = current.getNext();
                }
            }
            studentsInLine = node.getNext();
        } else if (musicalChairs != null) {
            SNode curr = musicalChairs;
            if (theName(fullName, curr.getStudent()) && curr.getNext() == curr) {
                musicalChairs = null;
            } else {
                do {
                    if (theName(fullName, curr.getNext().getStudent())) {
                   
                        if (curr.getNext() == musicalChairs) {
                         
                            curr.setNext(musicalChairs.getNext());
                            musicalChairs = curr;
                        } else {
                            curr.setNext(curr.getNext().getNext());
                        }
                    } else {
                        curr = curr.getNext();
                    }
                } while (curr.getNext() != musicalChairs.getNext());
            }
        } else {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[0].length; j++) {
                    if ((studentsSitting[i][j] != null) && theName(fullName, studentsSitting[i][j])) {
                        studentsSitting[i][j] = null;
                    }
                }
            }


        }

    }
   

        /*  if (musicalChairs !=null){
            if (musicalChairs.getNext()!=null){
                int counter = 1;
                SNode head=musicalChairs;
                SNode curr=musicalChairs.getNext();
                while (head!=curr){
                    counter++;
                    curr=curr.getNext();

                }
                 curr=musicalChairs;
                 for (int i=0; i<counter+1; i++){
                     if (curr.getNext()!=musicalChairs && curr.getNext().getStudent()!=null){
                         curr.setNext(curr.getNext().getNext());
                     }
                     if (curr.getNext()==musicalChairs && curr.getNext().getStudent()!=null){
                         curr.setNext(curr.getNext().getNext());
                         musicalChairs=curr;
                     }
                     curr=curr.getNext();
                    }
            }
            else if (musicalChairs.getStudent().getFirstName().equals(firstName)){
                musicalChairs=null;
            }
        }
        if (studentsInLine!=null){
            for (int i=0; i<studentsSitting.length; i++){
                for (int j=0; j<studentsSitting[0].length; j++){
                    if (studentsSitting[i][j]!=null){
                        if (studentsSitting[i][j].getLastName().equals(lastName)){
                            studentsSitting[i][j]=null;
                        }


                    }
                }
            }
        }
        if (studentsInLine!=null){
            if (studentsInLine.getNext()!=null){
                if (studentsInLine.getStudent().getFirstName().equals(firstName)){
                    studentsInLine=studentsInLine.getNext();
                }
            else{
                SNode curr = studentsInLine;
                while (curr.getNext().getNext()!=null){
                    if (curr.getNext().getStudent().getFirstName().equals(firstName)){
                        curr.setNext(curr.getNext().getNext());
                    }
                    curr = curr.getNext();
                }
                if (curr.getNext().getStudent().getFirstName().equals(firstName)){
                    curr.setNext(null);
                }
            } 

            }
            else if (studentsInLine.getNext()==null){
                boolean firstCheck = studentsInLine.getStudent().getFirstName().equals(firstName);
                boolean lastCheck = studentsInLine.getStudent().getLastName().equals(lastName);
                if (firstCheck&&lastCheck) {
                    studentsInLine=null;
                }

            }      
        }

    }*/


    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        StdOut.println("Students in Line:");
        if (studentsInLine == null) {
            StdOut.println("EMPTY");
        }

        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print());
            if (ptr.getNext() != null) {
                StdOut.print(" -> ");
            }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        StdOut.println("Sitting Students:");

        if (studentsSitting != null) {

            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {

                    String stringToPrint = "";
                    if (studentsSitting[i][j] == null) {

                        if (seatingAvailability[i][j] == false) {
                            stringToPrint = "X";
                        } else {
                            stringToPrint = "EMPTY";
                        }

                    } else {
                        stringToPrint = studentsSitting[i][j].print();
                    }

                    StdOut.print(stringToPrint);

                    for (int o = 0; o < (10 - stringToPrint.length()); o++) {
                        StdOut.print(" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        StdOut.println("Students in Musical Chairs:");

        if (musicalChairs == null) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext()) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if (ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getSeatingAvailability() {
        return seatingAvailability;
    }

    public void setSeatingAvailability(boolean[][] a) {
        seatingAvailability = a;
    }

    public Student[][] getStudentsSitting() {
        return studentsSitting;
    }

    public void setStudentsSitting(Student[][] s) {
        studentsSitting = s;
    }

}
