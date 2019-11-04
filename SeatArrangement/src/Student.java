/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author night
 */
public class Student {
    private String name;
    private boolean mustFront;
    private boolean mustBack;
    private Student deskmate;

    public Student(String name, boolean mustFront, boolean mustBack, Student deskmate) {
        this.name = name;
        this.mustFront = mustFront;
        this.mustBack = mustBack;
        this.deskmate = deskmate;
    }

    public Student(String name){
        this.name=name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMustFront() {
        return mustFront;
    }

    public void setMustFront(boolean mustFront) {
        this.mustFront = mustFront;
    }

    public boolean isMustBack() {
        return mustBack;
    }

    public void setMustBack(boolean mustBack) {
        this.mustBack = mustBack;
    }
}
