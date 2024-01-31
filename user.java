/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author 20121
 */
public class user {
    private int id;
    private String name;
    public user(String name,int id){
    this.id=id;
    this.name=name;
    }
    public String getname(){
    return name;
    }
     public int getid(){
    return id;
    }
    
}
