/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.univ.m2m.core;

/**
 *
 * @author hackman
 */
public class ObjectAuction {
    
    public String objectName;
    public int objectPrice;
    public int currentAuction;

    public ObjectAuction(String objectName, int objectPrice) {
        this.objectName = objectName;
        this.objectPrice = objectPrice;
        this.currentAuction = objectPrice;
    }
    
      public ObjectAuction() {
       
    }
    
 
}
