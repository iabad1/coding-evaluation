package com.aa.act.interview.org;

import java.util.Optional;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Organization {

    private Position root;
    private int currentEmployeeCount;
    
    public Organization() {
        root = createOrganization();
        currentEmployeeCount = 0;
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */

    /*
            vp sale
            /    \     \
            sp    sp

     */
    public Optional<Position> hire(Name person, String title) {
        //your code here

        //root copy
        Position rootCopy = root;

        //find position
        Optional<Position> foundOptionalPosition = searchForPosition(rootCopy, title);
        if(foundOptionalPosition.isPresent()){
            Position foundPosition = foundOptionalPosition.get();
            if(!foundPosition.isFilled()){
                //Create an Employee
                Optional<Employee> newEmpl = Optional.of(new Employee(setCurrentEmployeeCount(), person));
                foundPosition.setEmployee(newEmpl);
                return Optional.of(foundPosition);
            }
        }
        return Optional.empty();

    }

    /**
     * @param startPosition
     * @param title
     * @return the position at which the title matches the position title
     */
    private Optional<Position> searchForPosition(Position startPosition, String title){

        //check root
        if(startPosition.getTitle().equals(title)){
            return Optional.of(startPosition);
        }
        //iterate through children
        Queue<Position> queuePosition = new LinkedList<Position>();
        queuePosition.add(startPosition);
        Position current;

        while(!queuePosition.isEmpty()){
            //remove front of queue
            current = queuePosition.poll();
            Collection<Position> directReports = current.getDirectReports();
            for(Position pos : directReports){
                //check title match
                if(pos.getTitle().equals(title)){
                    return Optional.of(pos);
                }
                else{
                    queuePosition.add(pos);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * sets the employee count as an employee is being created
     * @return the employee identification nunmber
     */
    private int setCurrentEmployeeCount(){
        return currentEmployeeCount++;
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
